package com.dl.officialsite.defi.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dl.officialsite.defi.dao.BatchRepository;
import com.dl.officialsite.defi.dao.WhaleChainTokenRepository;
import com.dl.officialsite.defi.dao.WhaleChainValueRepository;
import com.dl.officialsite.defi.dao.WhaleRepository;
import com.dl.officialsite.defi.dao.WhaleTxRowRepository;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleChainToken;
import com.dl.officialsite.defi.entity.WhaleChainValue;
import com.dl.officialsite.defi.entity.WhaleTxRow;
import com.dl.officialsite.defi.vo.params.QueryWhaleParams;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Value("${debank.key:111}")
    private String key;

    private final WhaleRepository whaleRepository;

    private final WhaleTxRowRepository whaleTxRowRepository;

    private final BatchRepository batchRepository;

    private final WhaleChainValueRepository whaleChainValueRepository;

    private final WhaleChainTokenRepository whaleChainTokenRepository;

    private static String aaveUrl = "https://api.thegraph.com/subgraphs/name/messari/aave-v3"
        + "-ethereum";

    private static Integer first = 1000;

    private static Integer skip = 0;

    private static  Set<String> addressSet = new HashSet<>();

    public WhaleService(WhaleRepository whaleRepository, WhaleTxRowRepository whaleTxRowRepository,
        BatchRepository batchRepository, WhaleChainValueRepository whaleChainValueRepository,
        WhaleChainTokenRepository whaleChainTokenRepository) {
        this.whaleRepository = whaleRepository;
        this.whaleTxRowRepository = whaleTxRowRepository;
        this.batchRepository = batchRepository;
        this.whaleChainValueRepository = whaleChainValueRepository;
        this.whaleChainTokenRepository = whaleChainTokenRepository;
    }

    @Scheduled(cron =  "${jobs.defi.corn: 0 30 * * * * ?}")
    @ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
    public void aaveListener() {
        List<Whale> whaleList = new ArrayList<>();
        List<WhaleTxRow> insertWhaleTxRowList = new ArrayList<>();
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
        if (jsonObject.toString().contains("error")) {
            throw new RuntimeException("解析aave的graph失败");
        }
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray positions = data.getJSONArray("positions");
        if (positions.isEmpty()) {
            return;
        }
        for (int i = 0; i < positions.size(); i++) {
            Whale whale = convertToWhale((JSONObject) positions.get(i));
            //查询whale address是否落库
            Whale isHave = whaleRepository.findByAddress(whale.getAddress());
            if (ObjectUtils.isEmpty(isHave)) {
                afterWhaleId = afterWhaleId + 1;
                whale.setId(afterWhaleId);
                whaleList.add(whale);
            }
            List<WhaleTxRow> whaleTxRows = convertToWhaleTxRow((JSONObject) positions.get(i)
                , whale);
            for (WhaleTxRow whaleTxRow : whaleTxRows) {
                if (whaleTxRow.getCreateTime() > lastTimestamp) {
                    afterWhaleTxRowId = afterWhaleTxRowId + 1;
                    whaleTxRow.setId(afterWhaleTxRowId);
                    insertWhaleTxRowList.add(whaleTxRow);
                }
            }

        }
        insertWhaleAndTx(whaleList, insertWhaleTxRowList);
    }

    public void insertWhaleAndTx(List<Whale> whaleList, List<WhaleTxRow> whaleTxRowList) {
        log.info("开始插入数据");
        log.info("whaleList.size() = {}-------------------", whaleList.size());
        log.info("whaleTxRowList.size() = {}-------------------", whaleTxRowList.size());
        if (!whaleList.isEmpty()) {
            batchRepository.batchInsert(whaleList);
        }
        if (!whaleTxRowList.isEmpty()) {
            if (whaleTxRowList.size() <= 1000) {
                batchRepository.batchInsert(whaleTxRowList);
            } else {
                int batches = whaleTxRowList.size() / 1000;
                for (int i = 0; i <= batches; i++) {
                    int start = i * 1000;
                    int end = (i + 1) * 1000;
                    if (end > whaleTxRowList.size()) {
                        end = whaleTxRowList.size();
                    }
                    List<WhaleTxRow> sublist = whaleTxRowList.subList(start, end);
                    batchRepository.batchInsert(sublist);
                }
            }
        }
        log.info("插入数据结束");
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

    private List<WhaleTxRow> convertToWhaleTxRow(JSONObject account, Whale whale) {
        JSONArray positions = account.getJSONArray("positions");
        List<WhaleTxRow> whaleTxRowList = new ArrayList<>();
        //处理borrows
        for (int i = 0; i < positions.size(); i++) {
            //todo 处理positions为空的情况
            JSONObject position = (JSONObject) positions.get(i);
            JSONArray borrows = position.getJSONArray("borrows");
            JSONObject asset = position.getJSONObject("asset");
            String id = asset.getStr("id");
            String symbol = asset.getStr("symbol");
            String decimals = asset.getStr("decimals");
            for (int j = 0; j < borrows.size(); j++) {
                JSONObject borrow = (JSONObject) borrows.get(i);
                WhaleTxRow whaleTxRow = new WhaleTxRow();
                String txhash = borrow.getStr("hash");
                String timestamp = borrow.getStr("timestamp");
                String debtAmount = borrow.getStr("amount");
                String debtAmountUsd = borrow.getStr("amountUSD");
                whaleTxRow.setTxhash(txhash);
                whaleTxRow.setDebtTokenAddress(id);
                whaleTxRow.setDebtTokenSymbol(symbol);
                whaleTxRow.setCreateTime(Long.parseLong(timestamp));
                calculateWhaleTxRowAmount(borrow, decimals, whaleTxRow);
                BigDecimal debtAmountUsdBigDecimal = new BigDecimal(debtAmountUsd);
                whaleTxRow.setDebtAmountUsd(debtAmountUsdBigDecimal);
                whaleTxRow.setProtocol("aave");
                whaleTxRow.setChainId("1");
                whaleTxRow.setWhaleAddress(whale.getAddress());
                whaleTxRowList.add(whaleTxRow);
            }
        }
        return whaleTxRowList;
    }


    private Whale convertToWhale(JSONObject account) {
        Whale whale = new Whale();
        String address = account.getStr("id");
        whale.setAddress(address);
        return whale;
    }

    private void calculateWhaleAmount(JSONObject tx, Whale whale) {
        JSONObject asset = tx.getJSONObject("asset");
        String decimals = asset.getStr("decimals");
        String principal = tx.getStr("balance");
        BigDecimal amountUsd = convertUnit(principal, decimals);
        whale.setAmountUsd(amountUsd);
    }

    private void calculateWhaleTxRowAmount(JSONObject tx, String decimals, WhaleTxRow whaleTxRow) {
        String amount = tx.getStr("amount");
        BigDecimal afterAmount = convertUnit(amount, decimals);
        whaleTxRow.setDebtAmount(afterAmount);
    }

    private BigDecimal convertUnit(String value, String unit) {
        BigDecimal beforeValue = new BigDecimal(value);
        BigDecimal decimals = new BigDecimal("1E" + unit);
        return beforeValue.divide(decimals, 2, RoundingMode.HALF_UP);
    }

    public void init() {
        //获取一年之前的时间戳
        Long oneYearBefore = getOneYearBefore();
        //每次比较时间戳与一年之前的时间戳大小，如果大于一年之前的时间戳，就插入数据
        while (true) {
            List<Whale> insertWhaleList = new ArrayList<>();
            List<WhaleTxRow> insertWhaleTxRowList = new ArrayList<>();
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
            Whale lastWhale = whaleRepository.findLastWhale();
            for (int i = 0; i < accounts.size(); i++) {
                Whale whale = convertToWhale((JSONObject) accounts.get(i));
                if (ObjectUtils.isEmpty(lastWhale)) {
                    whale.setId((long) i + 1);
                } else {
                    whale.setId((long) i + lastWhale.getId() + 1);
                }
                if (!addressSet.contains(whale.getAddress())) {
                    insertWhaleList.add(whale);
                    addressSet.add(whale.getAddress());
                }
                List<WhaleTxRow> whaleTxRows = convertToWhaleTxRow((JSONObject) accounts.get(i)
                    , whale);
                for (WhaleTxRow whaleTxRow : whaleTxRows) {
                    if (whaleTxRow.getCreateTime() > oneYearBefore) {
                        insertWhaleTxRowList.add(whaleTxRow);
                    }
                }
            }
            initInsertWhaleTxRowListId(insertWhaleTxRowList);
            insertWhaleAndTx(insertWhaleList, insertWhaleTxRowList);
            skip += 1000;
        }
    }

    private void initInsertWhaleTxRowListId(List<WhaleTxRow> insertWhaleTxRowList) {
        //获取最后的whaleTxRow数据
        WhaleTxRow lastWhaleTxRow = whaleTxRowRepository.findAgoWhaleTxRow();
        for (int i = 0; i < insertWhaleTxRowList.size(); i++) {
            WhaleTxRow whaleTxRow = insertWhaleTxRowList.get(i);
            if (ObjectUtils.isEmpty(lastWhaleTxRow)) {
                whaleTxRow.setId((long) i + 1);
            } else {
                whaleTxRow.setId((long) i + lastWhaleTxRow.getId() + 1);
            }
        }
    }

    private String requestAaveGraph(Integer first, Integer skip) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String a =
            "{\"query\":\"{\\n  positions(\\n    first: " + first +"\\n    skip: "+ skip +"\\n    where: {side: BORROWER, timestampClosed: null, asset_in: [\\\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\\\", \\\"0xdac17f958d2ee523a2206206994597c13d831ec7\\\", \\\"0x6b175474e89094c44da98b954eedeac495271d0f\\\", \\\"0x5f98805A4E8be255a32880FDeC7F6728C6568bA0\\\"], borrows_: {amountUSD_gt: 10000}, timestampOpened_gt: 1680855974, timestampOpened_lt: 1712478374}\\n    orderBy: timestampOpened\\n    orderDirection: desc\\n  ) {\\n    id\\n    timestampOpened\\n    timestampClosed\\n    account {\\n      id\\n      positionCount\\n      openPositionCount\\n    }\\n    principal\\n    balance\\n    borrows {\\n      hash\\n      amount\\n      amountUSD\\n      timestamp\\n    }\\n    asset {\\n      id\\n      symbol\\n      name\\n      decimals\\n    }\\n  }\\n}\",\"extensions\":{}}";
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

    //getUserTotalBalance
    public List<WhaleChainValue> getUserTotalBalance(String whaleAddress,Integer update) {
        if (update == 0) {
            Whale whale = whaleRepository.findByAddress(whaleAddress);
            if (!ObjectUtils.isEmpty(whale)) {
                List<WhaleChainValue> chainValues = whaleChainValueRepository.findByWhaleAddress(whaleAddress);
                return chainValues;
            }
        }
        List<String> chainIds = Stream.of("1", "56", "137", "250", "43114", "43113")
            .collect(Collectors.toList());
        String baseUrl = "https://pro-openapi.debank.com/v1/user/total_balance";
        OkHttpClient client = new OkHttpClient();
        // 构建URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("id", whaleAddress); // 添加参数
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("AccessKey", key)
            .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("请求debank失败");
            throw new RuntimeException(e);
        };
        //.code 200
        String jsonStr = null;
        try {
            jsonStr = response.body().string();
        } catch (IOException e) {
            log.error("解析debank返回失败");
            throw new RuntimeException(e);
        }
        Whale whale = whaleRepository.findByAddress(whaleAddress);
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String totalUsdValue = jsonObject.getStr("total_usd_value");
        BigDecimal amountUsd = new BigDecimal(totalUsdValue);
        whale.setAmountUsd(amountUsd);
        whaleRepository.save(whale);
        JSONArray chainList = jsonObject.getJSONArray("chain_list");
        List<WhaleChainValue> chainValues = new ArrayList<>();
        for (int i = 0; i < chainList.size(); i++) {
            JSONObject chainInfo = (JSONObject) chainList.get(i);
            String chainId = chainInfo.getStr("community_id");
            String chainName = chainInfo.getStr("name");
            String usdValue = chainInfo.getStr("usd_value");
            if (chainIds.contains(chainId)) {
                WhaleChainValue whaleChainValue = new WhaleChainValue();
                whaleChainValue.setWhaleAddress(whaleAddress);
                whaleChainValue.setChainId(chainId);
                whaleChainValue.setChainName(chainName);
                whaleChainValue.setValue(usdValue);
                chainValues.add(whaleChainValue);

            }
        }
        whaleChainValueRepository.saveAll(chainValues);
        return chainValues;
    }

    //getUserTokenList
    public List<WhaleChainToken> getUserTokenList(String whaleAddress,Integer update) {
        List<String> chainNames = Stream.of("eth", "base", "op", "arb", "matic")
            .collect(Collectors.toList());
        if (update == 0) {
            Whale whale = whaleRepository.findByAddress(whaleAddress);
            if (!ObjectUtils.isEmpty(whale)) {
                List<WhaleChainToken> chainTokens = whaleChainTokenRepository.findByWhaleAddress(whaleAddress);
                return chainTokens;
            }
        }
        String baseUrl = "https://pro-openapi.debank.com/v1/user/all_token_list";
        OkHttpClient client = new OkHttpClient();
        // 构建URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("id", whaleAddress); // 添加参数
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("AccessKey", key)
            .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("请求debank失败");
            throw new RuntimeException(e);
        };
        //.code 200
        String jsonStr = null;
        try {
            jsonStr = response.body().string();
        } catch (IOException e) {
            log.error("解析debank返回失败");
            throw new RuntimeException(e);
        }
        JSONArray data = JSONUtil.parseArray(jsonStr);
        List<WhaleChainToken> chainTokens = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject tokenInfo = (JSONObject) data.get(i);
            String chainName = tokenInfo.getStr("chain");
            String tokenAddress = tokenInfo.getStr("id");
            String tokenSymbol = tokenInfo.getStr("symbol");
            String amount = tokenInfo.getStr("amount");
            String price = tokenInfo.getStr("price");
            String decimals = tokenInfo.getStr("decimals");
            if (chainNames.contains(chainName)) {
                WhaleChainToken whaleChainToken = new WhaleChainToken();
                whaleChainToken.setWhaleAddress(whaleAddress);
                whaleChainToken.setChainName(chainName);
                whaleChainToken.setTokenAddress(tokenAddress);
                whaleChainToken.setTokenSymbol(tokenSymbol);
                whaleChainToken.setAmount(amount);
                whaleChainToken.setPrice(price);
                whaleChainToken.setDecimals(Integer.parseInt(decimals));
                chainTokens.add(whaleChainToken);
            }
    }
        WhaleChainToken agoWhaleChainToken = whaleChainTokenRepository.findAgoWhaleChainToken();
        for (int i = 0; i < chainTokens.size(); i++) {
            WhaleChainToken whaleChainToken = chainTokens.get(i);
            if (ObjectUtils.isEmpty(agoWhaleChainToken)) {
                whaleChainToken.setId((long) i + 1);
            } else {
                whaleChainToken.setId((long) i + agoWhaleChainToken.getId() + 1);
            }
        }
        batchRepository.batchInsert(chainTokens);
        //whaleChainTokenRepository.saveAll(chainTokens);
        return chainTokens;
    }

    public Page<Whale> queryWhale(Pageable pageable, QueryWhaleParams query) {
        Specification<Whale> queryParam = new Specification<Whale>() {
            @Override
            public Predicate toPredicate(Root<Whale> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.hasText(query.getWhaleAddress())) {
                    predicates.add(criteriaBuilder.equal(root.get("whaleAddress"), query.getWhaleAddress()));
                }
                if (!ObjectUtils.isEmpty(query.getWhaleUSD())) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("amountUsd"), query.getWhaleUSD()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return whaleRepository.findAll(queryParam, pageable);
    }

    public Page<WhaleTxRow> queryWhaleTx(Pageable pageable, String address) {
        Specification<WhaleTxRow> queryParam = new Specification<WhaleTxRow>() {
            @Override
            public Predicate toPredicate(Root<WhaleTxRow> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("whaleAddress"), address));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return whaleTxRowRepository.findAll(queryParam, pageable);
    }
}

