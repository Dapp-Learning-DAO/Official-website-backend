package com.dl.officialsite.distributor;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeClaimerStatusEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.enums.TokenStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.config.ConstantConfig;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimer;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerManager;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerRepository;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.DistributeInfoVo.ClaimerInfo;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;
import com.dl.officialsite.distributor.vo.GetDistributeClaimerRspVo;
import com.dl.officialsite.member.MemberManager;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.tokenInfo.TokenInfo;
import com.dl.officialsite.tokenInfo.TokenInfoManager;
import com.dl.officialsite.tokenInfo.TokenInfoRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "Distribute")
@Configuration
public class DistributeService {

    @Autowired
    private ChainConfig chainConfig;

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    // private String lastUpdateTimestamp = "";

    @Autowired
    private DistributeRepository distributeRepository;

    @Autowired
    private DistributeClaimerRepository distributeClaimerRepository;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private TokenInfoManager tokenInfoManager;

    @Autowired
    private DistributeManager distributeManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Autowired
    private DistributeClaimerManager distributeClaimerManager;

    @Autowired
    private ConstantConfig constantConfig;

    @Scheduled(cron = "${jobs.distribute.corn:0/10 * * * * ?}")
    public void updateDistributeStatus() {
        log.info("schedule task begin --------------------- ");
        for (String chainId : chainConfig.getIds()) {
            try {
                updateDistributeStatusByChainId(chainId);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("updateDistributeStatusByChainId:  " + chainId + " error:" + e.getMessage());
            }
        }
    }

    private void updateDistributeStatusByChainId(String chainId) throws IOException {
        log.info("chain_id " + chainId);
        HttpEntity entity = getHttpEntityFromChain(chainId);
        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);

            if (jsonResponse.contains("errors")) {
                log.info("response from the graph: chainId{}, data {} ", chainId, jsonResponse);
                return;
            }
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray distributesArray = data.getAsJsonArray("distributors");
            JsonArray lastupdatesArray = data.getAsJsonArray("lastupdates");
            log.info("lastupdatesArray" + lastupdatesArray.toString());

            // if (lastupdatesArray.size() != 0) {
            // String lastTimestampFromGraph =
            // lastupdatesArray.get(0).getAsJsonObject().get("lastupdateTimestamp")
            // .getAsString();

            // if (Objects.equals(lastTimestampFromGraph, lastUpdateTimestamp)) {
            // return;
            // } else {
            // lastUpdateTimestamp = lastTimestampFromGraph;
            // }
            // }

            List<DistributeInfo> distributeList = distributeManager.findUnfinishedDistributeByChainId(chainId);
            log.info("distributeList size " + distributeList.size());
            for (int i = 0; i < distributesArray.size(); i++) {
                // Access each element in the array
                JsonObject distributeObject = distributesArray.get(i).getAsJsonObject();

                String redpacketId = distributeObject.get("redpacketId").getAsString();
                String distributorContractAddress = distributeObject.get("id").getAsString();
                Long expireTimestamp = distributeObject.get("expireTimestamp").getAsLong();
                for (int j = 0; j < distributeList.size(); j++) {
                    DistributeInfo distribute = distributeList.get(j);

                    if (!Objects.equals(distribute.getContractKey(), redpacketId)) {
                        continue;
                    }

                    // creating -> unClaim
                    distributeClaimerRepository.updateClaimStatus(distribute.getId(),
                            DistributeClaimerStatusEnums.UN_CLAIM.getData(),
                            DistributeClaimerStatusEnums.CREATING.getData());

                    Boolean allClaimed = distributeObject.get("allClaimed").getAsBoolean();
                    Boolean refunded = distributeObject.get("refunded").getAsBoolean();
                    log.info(
                            "******redPacketId:" + redpacketId + " refunded:" + refunded + " allClaimed:" + allClaimed);

                    if (distribute.getStatus() == null) {
                        log.info(distribute.getName() + "****** upchain successfully");
                        distribute.setStatus(DistributeStatusEnums.COMPLETED.getData());
                        distribute.setContractAddress(distributorContractAddress);
                    }
                    if (allClaimed) {
                        distribute.setStatus(DistributeStatusEnums.ALL_CLAIMED.getData());
                    }
                    if (refunded || expireTimestamp < System.currentTimeMillis() / 1000) {
                        // unClaim -> expire
                        distributeClaimerRepository.updateClaimStatus(distribute.getId(),
                                DistributeClaimerStatusEnums.NOT_CLAIM_AND_EXPIRE.getData(),
                                DistributeClaimerStatusEnums.UN_CLAIM.getData());
                        if (expireTimestamp < System.currentTimeMillis() / 1000)
                            distribute.setStatus(DistributeStatusEnums.TIME_OUT.getData());
                        if (refunded)
                            distribute.setStatus(DistributeStatusEnums.REFUND.getData());

                    }
                    distribute.setExpireTime(expireTimestamp);
                    distributeRepository.save(distribute);

                    JsonArray claimers = distributeObject.getAsJsonArray("claimers");
                    log.info("******redPacketId:" + redpacketId + " claimersSize:" + claimers.size());
                    for (int k = 0; k < claimers.size(); k++) {
                        String claimer = claimers.get(k).getAsJsonObject().get("claimer").getAsString();
                        String claimedValue = claimers.get(k).getAsJsonObject().get("amount").getAsString();
                        log.info("redpacketId:{} claimer:{} claimed:{}", redpacketId, claimer, claimedValue);

                        // check claimerRow
                        Optional<DistributeClaimer> opRsp = distributeClaimerRepository
                                .findByDistributeAndClaimer(distribute.getId(), claimer);

                        if (!opRsp.isPresent()) {
                            log.warn("invalid distribute:{} claimMember:{}", distribute.getId(), claimer);
                            continue;
                        }

                        // check amount
                        DistributeClaimer updateRow = opRsp.get();
                        // updateRow.setStatus(DistributeClaimerStatusEnums.UN_CLAIM.getData());
                        if (!StringUtils.equals(updateRow.getDistributeAmount().toString(), claimedValue)) {
                            log.warn("distribute:${} claimMember:{} configAmount:{} claimed:{}", distribute.getId(),
                                    claimer, updateRow.getDistributeAmount(), claimedValue);
                        }
                        // updateRow.setDistributeAmount(BigDecimal.valueOf(Long.valueOf(claimedValue)));
                        updateRow.setStatus(DistributeClaimerStatusEnums.CLAIMED.getData());

                        distributeClaimerRepository.save(updateRow);
                    }
                }
            }
        }

    }

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
        switch (chainId) {
            // case Constants.CHAIN_ID_OP: // op
            // request = new
            // HttpPost("https://api.studio.thegraph.com/query/55957/optimism-merkledistributor/version/latest");
            // break;
            case Constants.CHAIN_ID_SEPOLIA: // sepolia
                request = new HttpPost(
                        "https://api.studio.thegraph.com/query/55957/sepolia-merkledistributor/version/latest");
                break;
            // case Constants.CHAIN_ID_SCROLL: //scrool
            // request = new
            // HttpPost("https://api.studio.thegraph.com/query/55957/scroll-merkledistributor/version/latest");
            // break;
            // case Constants.CHAIN_ID_ARBITRUM: //arbitrum
            // request = new
            // HttpPost("https://api.studio.thegraph.com/query/55957/arbitrum-merkledistributor/version/latest");
            // break;

        }

        if (request == null)
            return null;
        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis / 1000 - 3600 * 24 * 90;
        // time = Math.max(time, 1703751860);
        String creationTimeGtValue = String.valueOf(time);

        String graphQL = "\" {" +
                "  distributors (where: { creationTimestamp_gt: " + creationTimeGtValue + " }) {" +
                "    id     " +
                "    refunded   " +
                "    name       " +
                "    creationTimestamp   " +
                "    expireTimestamp   " +
                "    redpacketId   " +
                "    allClaimed  " +
                "    claimers {" +
                "    claimer" +
                "    amount " +
                "    }" +
                " }" +
                "  lastupdates (orderBy : lastupdateTimestamp , orderDirection: desc) { lastupdateTimestamp } " +
                "}\"";

        String query = "{ \"query\": " +
                graphQL +
                " }";

        request.setEntity(new StringEntity(query));
        HttpResponse response = httpClient.execute(request);
        // System.out.println("response" + response);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    // create new distribute
    @Transactional(rollbackOn = Exception.class)
    public DistributeInfoVo createDistribute(DistributeInfoVo param) {
        log.info("[createDistribute] param : ", String.valueOf(param));

        // current user TODO test.dev
        String creatorAddress = "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if (constantConfig.getLoginFilter())
            creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        // Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        param.setCreator(creatorAddress);
        // check ID
        if (!Objects.isNull(param.getId()))
            throw new BizException(CodeEnums.ID_NEED_EMPTY);
        // check chain
        if (!Arrays.asList(chainConfig.getIds()).contains(String.valueOf(param.getChainId())))
            throw new BizException(CodeEnums.INVALID_CHAIN_ID);

        // check distribute claimer

        Set<String> claimerAddressSet = param.getClaimerList().stream().map(x -> x.getAddress())
                .collect(Collectors.toSet());

        if (claimerAddressSet.size() != param.getClaimerList().size())
            throw new BizException(CodeEnums.DUPLICATE_CLAIMER);
        // if (claimedSet.size() != param.getClaimedValues().size())
        // throw new BizException(CodeEnums.SIZE_NOT_MATCH);
        // check and save token
        Long tokenId = param.getTokenId();
        if (null == tokenId) {
            Optional<TokenInfo> tokenOptional = this.tokenInfoRepository.findByChainAndAddress(param.getChainId(),
                    param.getToken());
            if (tokenOptional.isPresent()) {
                tokenId = tokenOptional.get().getId();
            } else {
                TokenInfo newToken = new TokenInfo();
                newToken.setChainId(param.getChainId());
                newToken.setTokenAddress(param.getToken());
                newToken.setTokenName(param.getTokenName());
                newToken.setTokenDecimal(param.getTokenDecimal());
                newToken.setTokenSymbol(param.getTokenSymbol());
                newToken.setStatus(TokenStatusEnums.NORMAL.getData());

                TokenInfo tokenInfo = tokenInfoRepository.save(newToken);
                tokenId = tokenInfo.getId();
            }
        } else {
            tokenInfoManager.requireIdIsValid(tokenId);
        }

        // save distribute
        DistributeInfo newDistributeInfo = new DistributeInfo();
        BeanUtils.copyProperties(param, newDistributeInfo);
        newDistributeInfo.setTokenId(tokenId);
        newDistributeInfo.setNumber(param.getClaimerList().size());
        newDistributeInfo.setStatus(DistributeStatusEnums.UN_COMPLETED.getData());
        DistributeInfo savedDistribute = distributeRepository.save(newDistributeInfo);

        // check and save claimer
        for (int i = 0; i < param.getClaimerList().size(); i++) {
            String claimer = param.getClaimerList().get(i).getAddress();
            BigDecimal value = param.getClaimerList().get(i).getValue();
            // save claimer
            DistributeClaimer newRow = new DistributeClaimer();
            newRow.setDistributeId(savedDistribute.getId());
            newRow.setChainId(savedDistribute.getChainId());
            newRow.setClaimer(claimer);
            newRow.setDistributeAmount(value);
            newRow.setStatus(DistributeClaimerStatusEnums.CREATING.getData());
            distributeClaimerRepository.save(newRow);
        }

        return queryDistributeDetail(savedDistribute.getId());
    }

    // update distribute
    public void deleteDistribute(Long id) {
        log.info("[deleteDistribute] id :{} ", id);

        // check id
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        // current user TODO test.dev
        String creatorAddress = "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if (constantConfig.getLoginFilter())
            creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        // Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        if (!distributeInfo.getCreator().equals(creatorAddress))
            throw new BizException(CodeEnums.ONLY_CREATOE);

        // check status
        if (distributeInfo.getStatus() != DistributeStatusEnums.UN_COMPLETED.getData())
            throw new BizException(CodeEnums.NOT_SUPPORT_MODIFY);

        // delete
        distributeRepository.deleteById(id);
    }

    // query detail
    @Transactional(rollbackOn = Exception.class)
    public DistributeInfoVo queryDistributeDetail(Long id) {
        log.info("[queryDistributeDetail] id :{} ", id);

        // check id
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        return convertDistributeInfoToDistributeInfoVo(distributeInfo);
    }

    // query by page
    public Page<DistributeInfoVo> queryDistributeByPage(GetDistributeByPageReqVo param) {
        log.info("[queryDistributeByPage]");

        Pageable pageable = PageRequest.of(param.getPageNumber() - 1, param.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<DistributeInfo> queryParam = new Specification<DistributeInfo>() {
            @Override
            public Predicate toPredicate(Root<DistributeInfo> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (param.getId() != null) {
                    log.info(String.valueOf(param.getId()));
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + param.getId() + "%"));
                }
                if (param.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + param.getName() + "%"));
                }
                if (param.getChainId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("chainId"), param.getChainId()));
                }
                if (param.getCreator() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("creator"), param.getCreator()));
                }
                if (param.getExpireTime() != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("expireTime"), param.getExpireTime()));
                }
                if (param.getCreateTime() != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), param.getCreateTime()));
                }
                if (param.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), param.getStatus()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Page<DistributeInfo> dbRsp = distributeRepository.findAll(queryParam, pageable);
        List<DistributeInfoVo> voList = dbRsp.getContent().stream()
                .map(this::convertDistributeInfoToDistributeInfoVo)
                .collect(Collectors.toList());
        return new PageImpl<>(voList, pageable, dbRsp.getTotalElements());

    }

    // query time out but not claim distribute
    public List<DistributeInfoVo> getDistributeByChainAndClaimerAndStatus(String claimer, String chainId,
            List<Integer> claimedStatus) {
        List<DistributeInfoVo> result = Arrays.asList();

        // claimer List
        Optional<List<DistributeClaimer>> distributeClaimerOp = distributeClaimerRepository
                .findByChainAndClaimerAndStatus(chainId, claimer, claimedStatus);
        // Arrays.asList(DistributeClaimerStatusEnums.NOT_CLAIM_AND_EXPIRE.getData()));
        if (!distributeClaimerOp.isPresent())
            return result;
        Set<Long> distributeIds = distributeClaimerOp.get().stream().map(DistributeClaimer::getDistributeId)
                .collect(Collectors.toSet());

        // query distribute
        List<DistributeInfo> dbRsp = distributeRepository.findByIds(distributeIds);
        result = dbRsp.stream()
                .map(this::convertDistributeInfoToDistributeInfoVo)
                .collect(Collectors.toList());
        return result;
    }

    public DistributeInfoVo convertDistributeInfoToDistributeInfoVo(DistributeInfo distribute) {

        // copy attribute
        DistributeInfoVo distributeInfoVo = new DistributeInfoVo();
        BeanUtils.copyProperties(distribute, distributeInfoVo);

        // // query member
        // Optional<Member> memberOptional =
        // memberRepository.findById(distribute.getCreatorId());
        // memberOptional.ifPresent(memberRow ->
        // distributeInfoVo.setCreator(memberRow.getAddress()));

        // query token
        Optional<TokenInfo> tokenOptional = tokenInfoRepository.findById(distribute.getTokenId());
        tokenOptional.ifPresent(row -> {
            distributeInfoVo.setToken(row.getTokenAddress());
            distributeInfoVo.setTokenDecimal(row.getTokenDecimal());
            distributeInfoVo.setTokenName(row.getTokenName());
            distributeInfoVo.setTokenSymbol(row.getTokenSymbol());
        });

        // List<String> claimerList = new ArrayList<>();
        // List<BigDecimal> valueList = new ArrayList<>();
        // List<Integer> statusList = new ArrayList<>();
        List<ClaimerInfo> claimerInfoList = new ArrayList<>();
        Optional<List<DistributeClaimer>> distributeClaimerOp = distributeClaimerRepository
                .findByDistribute(distribute.getId());
        if (distributeClaimerOp.isPresent()) {
            List<DistributeClaimer> rowList = distributeClaimerOp.get();
            for (int i = 0; i < rowList.size(); i++) {
                GetDistributeClaimerRspVo rspVo = distributeClaimerManager
                        .convertToGetDistributeClaimerRspVo(rowList.get(i));
                ClaimerInfo claimerInfo = new ClaimerInfo();
                claimerInfo.setAddress(rspVo.getClaimer());
                claimerInfo.setValue(rspVo.getDistributeAmount().stripTrailingZeros());
                claimerInfo.setStatus(rspVo.getStatus());
                claimerInfoList.add(claimerInfo);
            }
            distributeInfoVo.setClaimerList(claimerInfoList);
        }
        return distributeInfoVo;
    }

}
