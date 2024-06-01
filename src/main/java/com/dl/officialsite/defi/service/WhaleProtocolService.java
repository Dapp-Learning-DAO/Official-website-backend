package com.dl.officialsite.defi.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dl.officialsite.defi.dao.BatchRepository;
import com.dl.officialsite.defi.dao.WhaleProtocolRepository;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleProtocol;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @ClassName WhaleProtocolService
 * @Author jackchen
 * @Date 2024/4/14 18:21
 * @Description WhaleProtocolService
 **/
@Service
@Slf4j
public class WhaleProtocolService {

    private final WhaleProtocolRepository whaleProtocolRepository;

    private final BatchRepository batchRepository;

    public WhaleProtocolService(WhaleProtocolRepository whaleProtocolRepository,
        BatchRepository batchRepository) {
        this.whaleProtocolRepository = whaleProtocolRepository;
        this.batchRepository = batchRepository;
    }

    private static Integer first = 1000;

    private static Integer skip = 0;

    private Long getOneYearBefore() {
        // 获取当前时间的时间戳
        Instant now = Instant.now();

        // 获取一年前的时间戳
        Instant oneYearAgo = now.minusSeconds(365 * 24 * 60 * 60); // 365天 * 24小时 * 60分钟 * 60秒

        // 输出时间戳
        log.info("当前时间戳：" + now.getEpochSecond());
        log.info("一年前的时间戳：" + oneYearAgo.getEpochSecond());
        return oneYearAgo.getEpochSecond();

    }

    private String requestAaveGraph(Integer first, Integer skip) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = "{\"query\":\"{\\n  accounts(\\n    where: {positions_: {side: BORROWER, timestampClosed: null, asset_in: [\\\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\\\", \\\"0xdac17f958d2ee523a2206206994597c13d831ec7\\\", \\\"0x6b175474e89094c44da98b954eedeac495271d0f\\\", \\\"0x3Fe6a295459FAe07DF8A0ceCC36F37160FE86AA9\\\"], timestampOpened_gt: 1680942374, timestampOpened_lt: 1712478374}, borrows_: {amountUSD_gt: 20000}}\\n    skip: "+ skip +"\\n    first: " + first +"\\n  ) {\\n    id\\n    positionCount\\n    openPositionCount\\n    positions(where: {timestampClosed: null}) {\\n      id\\n      timestampOpened\\n      timestampClosed\\n      side\\n      isCollateral\\n      type\\n      principal\\n      balance\\n      depositCount\\n      withdrawCount\\n      borrowCount\\n      repayCount\\n      borrows {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      deposits {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      repays {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      asset {\\n        id\\n        symbol\\n        name\\n        decimals\\n      }\\n    }\\n  }\\n}\",\"extensions\":{}}";
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
            .url("https://api.thegraph.com/subgraphs/name/messari/aave-v3-ethereum")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("请求aave的graph失败");
            throw new RuntimeException(e);
        };
        String jsonStr = null;
        try {
            jsonStr = response.body().string();
        } catch (IOException e) {
            log.error("解析aave的graph返回失败");
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    public void initWhaleProtocol() {
        while (true) {
            List<WhaleProtocol> insertWhaleProtocolList = new ArrayList<>();
            String jsonStr = requestAaveGraph(first, skip);
            JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            if (jsonObject.toString().contains("error")) {
                log.error("解析aave的graph失败");
                log.error(jsonObject.toString());
                break;
            }
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray accounts = data.getJSONArray("accounts");
            if (accounts.isEmpty()) {
                break;
            }
            //获取最后的whale数据
            WhaleProtocol lastWhaleProtocol = whaleProtocolRepository.findAgoWhaleTxRow();
            for (int i = 0; i < accounts.size(); i++) {
                WhaleProtocol whaleProtocol = convertToWhaleProtocol((JSONObject) accounts.get(i));
                if (ObjectUtils.isEmpty(lastWhaleProtocol)) {
                    whaleProtocol.setId((long) i + 1);
                } else {
                    whaleProtocol.setId((long) i + lastWhaleProtocol.getId() + 1);
                }
                insertWhaleProtocolList.add(whaleProtocol);
            }
            insertWhaleProtocol(insertWhaleProtocolList);
            skip += 1000;
        }
    }

    public void insertWhaleProtocol(List<WhaleProtocol> insertWhaleProtocolList) {
        log.info("开始插入数据");
        log.info("insertWhaleProtocolList.size() = {}-------------------", insertWhaleProtocolList.size());
        if (!insertWhaleProtocolList.isEmpty()) {
            if (insertWhaleProtocolList.size() <= 1000) {
                batchRepository.batchInsert(insertWhaleProtocolList);
            } else {
                int batches = insertWhaleProtocolList.size() / 1000;
                for (int i = 0; i <= batches; i++) {
                    int start = i * 1000;
                    int end = (i + 1) * 1000;
                    if (end > insertWhaleProtocolList.size()) {
                        end = insertWhaleProtocolList.size();
                    }
                    List<WhaleProtocol> sublist = insertWhaleProtocolList.subList(start, end);
                    batchRepository.batchInsert(sublist);
                }
            }
        }
        log.info("插入数据结束");
    }

    private WhaleProtocol convertToWhaleProtocol(JSONObject account) {
        WhaleProtocol whaleProtocol = new WhaleProtocol();
        //总负债
        BigDecimal totalDebt = new BigDecimal(0);
        //总质押
        BigDecimal totalCollateral = new BigDecimal(0);
        //总borrows
        BigDecimal totalBorrow = new BigDecimal(0);
        //总repays
        BigDecimal totalRepay = new BigDecimal(0);
        //总deposit
        BigDecimal totalDeposit = new BigDecimal(0);
        JSONArray positions = account.getJSONArray("positions");
        Whale whale = convertToWhale(account);
        //处理borrows
        for (int i = 0; i < positions.size(); i++) {
            //todo 处理positions为空的情况
            JSONObject position = (JSONObject) positions.get(i);
            JSONArray borrows = position.getJSONArray("borrows");
            for (int j = 0; j < borrows.size(); j++) {
                JSONObject borrow = (JSONObject) borrows.get(j);
                String amountUSD = borrow.getStr("amountUSD");
                totalBorrow = totalBorrow.add(new BigDecimal(amountUSD));
            }
            //处理deposit
            Boolean isCollateral = position.getBool("isCollateral", false);
            if (isCollateral) {
                JSONArray deposits = position.getJSONArray("deposits");
                for (int k = 0; k < deposits.size(); k++) {
                    JSONObject deposit = (JSONObject) deposits.get(k);
                    String amountUSD = deposit.getStr("amountUSD");
                    totalDeposit = totalDeposit.add(new BigDecimal(amountUSD));
                }
            }
            //处理repays
            JSONArray repays = position.getJSONArray("repays");
            for (int l = 0; l < repays.size(); l++) {
                JSONObject repay = (JSONObject) repays.get(l);
                String amountUSD = repay.getStr("amountUSD");
                totalRepay = totalRepay.add(new BigDecimal(amountUSD));
            }
        }
        totalDebt = totalBorrow.subtract(totalRepay);
        //处理WhaleProtocol
        whaleProtocol.setTotalDebt(totalDebt.toString());
        whaleProtocol.setTotalSupply(totalDeposit.toString());
        whaleProtocol.setChainId(1);
        whaleProtocol.setProtocolName("aave");
        whaleProtocol.setWhaleAddress(whale.getAddress());
        return whaleProtocol;
    }

    private Whale convertToWhale(JSONObject account) {
        Whale whale = new Whale();
        String address = account.getStr("id");
        whale.setAddress(address);
        return whale;
    }

    public Page<WhaleProtocol> queryWhaleProtocol(String address, Pageable pageable) {
        Specification<WhaleProtocol> queryParam = new Specification<WhaleProtocol>() {
            @Override
            public Predicate toPredicate(Root<WhaleProtocol> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.hasText(address)) {
                    predicates.add(criteriaBuilder.equal(root.get("whaleAddress"), address));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return whaleProtocolRepository.findAll(queryParam, pageable);
    }
}

