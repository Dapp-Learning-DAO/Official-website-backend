package com.dl.officialsite.defi.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dl.officialsite.defi.dao.BatchRepository;
import com.dl.officialsite.defi.dao.WhaleRepository;
import com.dl.officialsite.defi.dao.WhaleTxRowRepository;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleTxRow;
import com.dl.officialsite.defi.vo.params.QueryWhaleParams;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * @ClassName WhaleService
 * @Author jackchen
 * @Date 2024/4/4 11:41
 * @Description 大户地址
 **/
@Service
@Slf4j
public class WhaleService {

    private final WhaleRepository whaleRepository;

    private final WhaleTxRowRepository whaleTxRowRepository;

    private final BatchRepository batchRepository;

    private static String aaveUrl = "https://api.thegraph.com/subgraphs/name/messari/aave-v3"
        + "-ethereum";

    private static Integer first = 1000;

    private static Integer skip = 0;

    private static  Set<String> addressSet = new HashSet<>();

    public WhaleService(WhaleRepository whaleRepository, WhaleTxRowRepository whaleTxRowRepository,
        BatchRepository batchRepository) {
        this.whaleRepository = whaleRepository;
        this.whaleTxRowRepository = whaleTxRowRepository;
        this.batchRepository = batchRepository;
    }

    public void aaveListener() {
        List<Whale> whaleList = new ArrayList<>();
        List<WhaleTxRow> whaleTxRowList = new ArrayList<>();
        //查询数据库最新的数据，并且获取时间戳
        WhaleTxRow whaleTxRowData = whaleTxRowRepository.findLastWhaleTxRow();
        WhaleTxRow afterWhaleTxRow = whaleTxRowRepository.findAgoWhaleTxRow();
        Long afterWhaleTxRowId = afterWhaleTxRow.getId();
        Whale afterWhale = whaleRepository.findByAgoWhale();
        Long afterWhaleId = afterWhale.getId();
        Long lastTimestamp = whaleTxRowData.getCreateTime();
        //查询aave1000条数据。筛选时间戳大与数据库最新时间戳的数据
        String jsonStr = requestAaveGraph(100, 0);
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if (jsonObject.toString().contains("errors")) {
            throw new RuntimeException("解析aave的graph失败");
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray borrows = data.getJSONArray("borrows");
        for (int i = 0; i < borrows.size(); i++) {
            Whale whale = convertToWhale((JSONObject) borrows.get(i));
            //查询whale address是否落库
            Whale isHave = whaleRepository.findByAddress(whale.getAddress());
            if (ObjectUtils.isEmpty(isHave)) {
                afterWhaleId = afterWhaleId + 1;
                whale.setId(afterWhaleId);
                whaleList.add(whale);
            }
            WhaleTxRow whaleTxRow = convertToWhaleTxRow((JSONObject) borrows.get(i));
            if (whaleTxRow.getCreateTime() > lastTimestamp) {
                afterWhaleTxRowId = afterWhaleTxRowId + 1;
                whaleTxRow.setId(afterWhaleTxRowId);
                whaleTxRowList.add(whaleTxRow);
            }
        }
        insertWhaleAndTx(whaleList, whaleTxRowList);
    }

    public void insertWhaleAndTx(List<Whale> whaleList, List<WhaleTxRow> whaleTxRowList) {
        log.info("开始插入数据");
        log.info("whaleList.size() = {}-------------------", whaleList.size());
        log.info("whaleTxRowList.size() = {}-------------------", whaleTxRowList.size());
        if (!whaleList.isEmpty()) {
            batchRepository.batchInsert(whaleList);
        }
        if (!whaleTxRowList.isEmpty()) {
            batchRepository.batchInsert(whaleTxRowList);
        }
    }

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

    private WhaleTxRow convertToWhaleTxRow(JSONObject tx) {
        WhaleTxRow whaleTxRow = new WhaleTxRow();
        String txhash = tx.getStr("hash");
        String debtTokenAddress = tx.getJSONObject("asset").getStr("id");
        String debtTokenSymbol = tx.getJSONObject("asset").getStr("symbol");
        String timestamp = tx.getStr("timestamp");
        String debtAmount = tx.getStr("amount");
        String debtAmountUsd = tx.getStr("amountUSD");
        String address = tx.getJSONObject("account").getStr("id");
        whaleTxRow.setTxhash(txhash);
        whaleTxRow.setDebtTokenAddress(debtTokenAddress);
        whaleTxRow.setDebtTokenSymbol(debtTokenSymbol);
        whaleTxRow.setCreateTime(Long.parseLong(timestamp));
        BigDecimal debtAmountBigDecimal = new BigDecimal(debtAmount);
        whaleTxRow.setDebtAmount(debtAmountBigDecimal);
        BigDecimal debtAmountUsdBigDecimal = new BigDecimal(debtAmountUsd);
        whaleTxRow.setDebtAmountUsd(debtAmountUsdBigDecimal);
        whaleTxRow.setProtocol("aave");
        whaleTxRow.setWhaleAddress(address);
        return whaleTxRow;
    }

    private Whale convertToWhale(JSONObject tx) {
        Whale whale = new Whale();
        String address = tx.getJSONObject("account").getStr("id");
        Long createTime = tx.getLong("timestamp");
        whale.setAddress(address);
        whale.setCreateTime(createTime);
        return whale;
    }

    public void init() {
        //获取一年之前的时间戳
        Long oneYearBefore = getOneYearBefore();
        //每次比较时间戳与一年之前的时间戳大小，如果大于一年之前的时间戳，就插入数据
        int mark = 1;
        while (true) {
            List<Whale> whaleList = new ArrayList<>();
            List<WhaleTxRow> whaleTxRowList = new ArrayList<>();
            String jsonStr = requestAaveGraph(first, skip);
            JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            if (jsonObject.toString().contains("errors")) {
                break;
            }
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray borrows = data.getJSONArray("borrows");
            for (int i = 0; i < borrows.size(); i++) {
                Whale whale = convertToWhale((JSONObject) borrows.get(i));
                whale.setId((long) i + skip);
                if (!addressSet.contains(whale.getAddress())) {
                    whaleList.add(whale);
                    addressSet.add(whale.getAddress());
                }
                WhaleTxRow whaleTxRow = convertToWhaleTxRow((JSONObject) borrows.get(i));
                whaleTxRow.setId((long) i + skip);
                if (whaleTxRow.getCreateTime() > oneYearBefore) {
                    whaleTxRowList.add(whaleTxRow);
                } else {
                    mark = 0;
                    break;
                }
            }
            insertWhaleAndTx(whaleList, whaleTxRowList);
            skip += 1000;
            if (mark == 0) {
                break;
            }
        }
    }

    private String requestAaveGraph(Integer first, Integer skip) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = "{\"query\":\"{\\n  borrows(\\n    first: " + first + "\\n    skip:"
            + " "+ skip +"\\n  "
            + "  "
            + "orderBy: timestamp\\n    orderDirection: desc\\n    where: {asset_in: [\\\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\\\", \\\"0xdac17f958d2ee523a2206206994597c13d831ec7\\\", \\\"0x6b175474e89094c44da98b954eedeac495271d0f\\\", \\\"0x3Fe6a295459FAe07DF8A0ceCC36F37160FE86AA9\\\"], amountUSD_gt: \\\"100000\\\"}\\n  ) {\\n    account {\\n      id\\n    }\\n    asset {\\n      id\\n      symbol\\n      name\\n    }\\n    hash\\n    amount\\n    amountUSD\\n    timestamp\\n  }\\n}\",\"extensions\":{}}";
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

    public Page<WhaleTxRow> queryWhale(Pageable pageable, QueryWhaleParams query) {
        Specification<WhaleTxRow> queryParam = new Specification<WhaleTxRow>() {
            @Override
            public Predicate toPredicate(Root<WhaleTxRow> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.hasText(query.getWhaleAddress())) {
                    predicates.add(criteriaBuilder.equal(root.get("whaleAddress"), query.getWhaleAddress()));
                }
                if (!ObjectUtils.isEmpty(query.getWhaleTxUSD())) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("debtAmountUsd"), query.getWhaleTxUSD()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return whaleTxRowRepository.findAll(queryParam, pageable);
    }
}
