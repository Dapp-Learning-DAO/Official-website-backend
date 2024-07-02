package com.dl.officialsite.defi.handler;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dl.officialsite.defi.dao.BatchRepository;
import com.dl.officialsite.defi.dao.WhaleProtocolRepository;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleProtocol;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @ClassName AaveWhaleProtocolService
 * @Author jackchen
 * @Date 2024/4/22 22:51
 * @Description aave whale protocol service
 **/
@Service
@Slf4j
public class AaveWhaleProtocolHandler extends AbstractWhaleProtocolHandler {

    private final WhaleProtocolRepository whaleProtocolRepository;

    private final BatchRepository batchRepository;

    public AaveWhaleProtocolHandler(WhaleProtocolRepository whaleProtocolRepository,
        BatchRepository batchRepository) {
        this.whaleProtocolRepository = whaleProtocolRepository;
        this.batchRepository = batchRepository;
    }

    private static Integer first = 1000;

    private static Integer skip = 0;

    @Override
    public void initWhaleProtocol() {
        while (true) {
            List<WhaleProtocol> insertWhaleProtocolList = new ArrayList<>();
            String jsonStr = requestProtocolGraph(first, skip, "aave", "https://api.thegraph.com/subgraphs/name/aave/protocol-v2");
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

    @Override
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

    @Override
    public WhaleProtocol convertToWhaleProtocol(JSONObject account) {
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

}
