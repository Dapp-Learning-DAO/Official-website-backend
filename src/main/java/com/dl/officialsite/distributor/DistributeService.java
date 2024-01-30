package com.dl.officialsite.distributor;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.enums.DistributeClaimerStatusEnums;
import com.dl.officialsite.common.enums.DistributeStatusEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.MerkleTree;
import com.dl.officialsite.common.utils.UserSecurityUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.config.ConstantConfig;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimer;
import com.dl.officialsite.distributor.distributeClaimer.DistributeClaimerRepository;
import com.dl.officialsite.distributor.vo.DistributeInfoVo;
import com.dl.officialsite.distributor.vo.GetDistributeByPageReqVo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberManager;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.redpacket.RedPacketRepository;
import com.dl.officialsite.tokenInfo.TokenInfo;
import com.dl.officialsite.tokenInfo.TokenInfoManager;
import com.dl.officialsite.tokenInfo.TokenInfoRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.web3j.crypto.Hash;

@Service
@Slf4j
@Configuration
public class DistributeService {

    @Autowired
    private RedPacketRepository redPacketRepository;

    @Autowired
    private ChainConfig chainConfig;

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    private String lastUpdateTimestamp = "";

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
            JsonArray distributesArray = data.getAsJsonArray("distributes");
            JsonArray lastupdatesArray = data.getAsJsonArray("lastupdates");
            log.info("lastupdatesArray" + lastupdatesArray.toString());

            if (lastupdatesArray.size() != 0) {
                String lastTimestampFromGraph = lastupdatesArray.get(0).getAsJsonObject().get("lastupdateTimestamp")
                        .getAsString();

                if (Objects.equals(lastTimestampFromGraph, lastUpdateTimestamp)) {
                    return;
                } else {
                    lastUpdateTimestamp = lastTimestampFromGraph;
                }
            }

            List<DistributeInfo> distributeList = distributeManager.findUnfinishedDistributeByChainId(chainId);
            log.info("distributeList size " + distributeList.size());
            for (int i = 0; i < distributesArray.size(); i++) {
                // Access each element in the array
                JsonObject distributeObject = distributesArray.get(i).getAsJsonObject();

                String id = distributeObject.get("id").getAsString();
                for (int j = 0; j < distributeList.size(); j++) {
                    DistributeInfo distribute = distributeList.get(j);

                    if (!Objects.equals(distribute.getId(), id)) {
                        continue;
                    }

                    //// 0 uncompleted 1 completed 2 overtime 3 refund
                    Boolean allClaimed = distributeObject.get("allClaimed").getAsBoolean();
                    Boolean refunded = distributeObject.get("refunded").getAsBoolean();
                    log.info("****** refunded" + refunded);
                    log.info("****** allClaimed" + allClaimed);
                    if (distribute.getExpireTime() < System.currentTimeMillis() / 1000) {
                        distribute.setStatus(DistributeStatusEnums.TIME_OUT.getData());
                    }
                    if (allClaimed) {
                        distribute.setStatus(DistributeStatusEnums.COMPLETED.getData());
                    }
                    if (refunded) {
                        distribute.setStatus(DistributeStatusEnums.REFUND.getData());
                    }
                    distributeRepository.save(distribute);

                    JsonArray claimers = distributeObject.getAsJsonArray("claimers");
                    // ArrayList<String> claimersList = new ArrayList<>();
                    // ArrayList<String> claimedValueList = new ArrayList<>();
                    for (int k = 0; k < claimers.size(); k++) {
                        String claimer = claimers.get(k).getAsJsonObject().get("claimer").getAsString();
                        String claimedValue = claimers.get(k).getAsJsonObject().get("claimedValue").getAsString();

                        // check member
                        Member claimerRow = memberManager.getMemberByAddress(claimer);
                        if (Objects.isNull(claimerRow)) {
                            log.warn("invalid claimMember:{} claimedValue:{}", claimer, claimedValue);
                            continue;
                        }

                        // check claimerRow
                        Optional<DistributeClaimer> opRsp = distributeClaimerRepository
                                .findByDistributeAndClaimer(distribute.getId(), claimerRow.getId());
                        if (!opRsp.isPresent()) {
                            log.warn("invalid distribute:${} claimMember:{}", distribute.getId(), claimer);
                            continue;
                        }

                        // check amount
                        DistributeClaimer updateRow = opRsp.get();
                        if (updateRow.getDistributeAmount().toString() != claimedValue) {
                            log.warn("distribute:${} claimMember:{} configAmount:{} claimed:${}", distribute.getId(),
                                    claimer, updateRow.getDistributeAmount(), claimedValue);
                        }
                        updateRow.setDistributeAmount(BigDecimal.valueOf(Long.valueOf(claimedValue)));

                        // check status
                        if (updateRow.getStatus().toString() != DistributeClaimerStatusEnums.UN_CLAIM.getData()
                                .toString()) {
                            log.warn("distribute:${} claimMember:{}  claimed:${} dataStatus:{}", distribute.getId(),
                                    claimer, claimedValue, updateRow.getStatus().toString());
                        }
                        updateRow.setStatus(DistributeClaimerStatusEnums.CLAIMED.getData());

                        distributeClaimerRepository.save(updateRow);
                    }
                    // distribute.setClaimedAddress(claimersList);
                    // distribute.setClaimedValues(claimedValueList);

                }
            }
        }

    }

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
        switch (chainId) {
            case Constants.CHAIN_ID_OP: // op
                request = new HttpPost("https://api.studio.thegraph.com/query/64274/optimism/version/latest");
                break;
            case Constants.CHAIN_ID_SEPOLIA: // sepolia
                request = new HttpPost("https://api.studio.thegraph.com/query/64274/sepolia/version/latest");
                break;
            case Constants.CHAIN_ID_SCROLL: // scrool
                request = new HttpPost("https://api.studio.thegraph.com/query/64274/scroll/version/latest");
                break;
            case Constants.CHAIN_ID_ARBITRUM: // arbitrum
                request = new HttpPost("https://api.studio.thegraph.com/query/64274/arbitrum/version/latest");
                break;

        }
        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis / 1000 - 3600 * 24 * 90;
        // time = Math.max(time, 1703751860);
        String creationTimeGtValue = String.valueOf(time);

        String graphQL = "\" {" +
                "  distributes (where: { creationTime_gt: " + creationTimeGtValue + " }) {" +
                "    id     " +
                "    refunded   " +
                "   lock " +
                "    name       " +
                "    creationTime   " +
                "    allClaimed  " +
                "    claimers {" +
                "    claimer" +
                "    claimedValue " +
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
    public DistributeInfo createDistribute(DistributeInfo param) {
        log.info("[createDistribute] param : ", String.valueOf(param));

        // current user TODO test.dev
        String creatorAddress =  "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if(constantConfig.getLoginFilter())
             creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        param.setCreatorId(member.getId());
        // check ID
        if (!Objects.isNull(param.getId()))
            throw new BizException(CodeEnums.ID_NEED_EMPTY);
        // check chain
        if (!Arrays.asList(chainConfig.getIds()).contains(String.valueOf(param.getChainId())))
            throw new BizException(CodeEnums.INVALID_CHAIN_ID);
        // check token
        this.tokenInfoManager.requireTokenIdIsValid(param.getTokenId());
        // distribute key
        String distributeKey = buildDistributeContractId(creatorAddress, param.getMessage());
        if(StringUtils.isBlank(param.getContractKey())){
            param.setContractKey(distributeKey);
        } else if(!distributeKey.equals(param.getContractKey())){
            throw new BizException(CodeEnums.DISTRIBUTE_KEY_NOT_MATCH);
        }

        // save
        param.setStatus(DistributeStatusEnums.UN_COMPLETED.getData());
        return distributeRepository.save(param);
    }

    // update distribute
    public void deleteDistribute(Long id) {
        log.info("[deleteDistribute] id :{} ", id);

        // check id
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        // current user TODO test.dev
        String creatorAddress =  "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if(constantConfig.getLoginFilter())
            creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        if (distributeInfo.getCreatorId() != member.getId())
            throw new BizException(CodeEnums.ONLY_CREATOE);

        // check status
        if (distributeInfo.getStatus() != DistributeStatusEnums.UN_COMPLETED.getData())
            throw new BizException(CodeEnums.NOT_SUPPORT_MODIFY);

        // delete
        distributeRepository.deleteById(id);
    }

    // query detail
    public DistributeInfoVo queryDistributeDetail(Long id) {
        log.info("[queryDistributeDetail] id :{} ", id);

        // check id
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(id);

        // copy attribute
        DistributeInfoVo distributeInfoVo = new DistributeInfoVo();
        BeanUtils.copyProperties(distributeInfo, distributeInfoVo);

        // query member
        Optional<Member> memberOptional = memberRepository.findById(id);
        memberOptional.ifPresent(memberRow -> distributeInfoVo.setCreatorAddress(memberRow.getAddress()));

        // query token
        Optional<TokenInfo> tokenOptional = tokenInfoRepository.findById(id);
        tokenOptional.ifPresent(row -> distributeInfoVo.setTokenAddress(row.getTokenAddress()));

        return distributeInfoVo;
    }

    // query by page
    public Page<DistributeInfo> queryDistributeByPage(GetDistributeByPageReqVo param) {
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
                    Member member = memberManager.getMemberByAddress(param.getCreator());
                    String creatorAddress = Objects.isNull(member) ? null : member.getAddress();
                    predicates.add(criteriaBuilder.equal(root.get("creatorId"), creatorAddress));
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

        return distributeRepository.findAll(queryParam, pageable);
    }


    // query detail
    public String buildAndSaveMerkleTreeRoot(Long distributeId) {
        log.info("[buildAndSaveMerkleTreeRoot] distributeId :{} ", distributeId);
        // check distribute
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(distributeId);
        if (distributeInfo.getStatus() != DistributeStatusEnums.UN_COMPLETED.getData())
            return distributeInfo.getMerkleRoot();

        // current user TODO test.dev
        String creatorAddress = "0x1F7b953113f4dFcBF56a1688529CC812865840e2";
        if (constantConfig.getLoginFilter())
            creatorAddress = UserSecurityUtils.getUserLogin().getAddress();
        Member member = this.memberManager.requireMemberAddressExist(creatorAddress);
        if (distributeInfo.getCreatorId() != member.getId())
            throw new BizException(CodeEnums.ONLY_CREATOE);

        // build tree
        MerkleTree merkleTree = buildTreeByDistributeId(distributeInfo);
        // build root
        String merkleRoot =  merkleTree.buildTreeRoot();
        //save
        distributeInfo.setMerkleRoot(merkleRoot);
        distributeRepository.save(distributeInfo);
        return merkleRoot;
    }

    // query detail
    public String generateMerkleProof(Long distributeId,Long claimerId) {
        log.info("[buildAndSaveMerkleTreeProof] distributeId :{} claimerId:{}", distributeId,claimerId);
        // check distribute
        DistributeInfo distributeInfo = distributeManager.requireIdIsValid(distributeId);
        // build tree
        MerkleTree merkleTree = buildTreeByDistributeId(distributeInfo);
        //check member
        Optional<DistributeClaimer> opRsp = distributeClaimerRepository.findByDistributeAndClaimer(distributeId,claimerId);
        if(!opRsp.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);
        //build proof
        return merkleTree.generateProof(opRsp.get().getId());

    }


    private MerkleTree buildTreeByDistributeId(DistributeInfo distributeInfo) {
        Long distributeId = distributeInfo.getId();
        // check token
        TokenInfo tokenInfo = tokenInfoManager.requireTokenIdIsValid(distributeInfo.getTokenId());

        // get all claimer
        Optional<List<DistributeClaimer>> allDistributeClaimer = distributeClaimerRepository
                .findByDistribute(distributeId);
        if (!allDistributeClaimer.isPresent())
            throw new BizException(CodeEnums.INVALID_ID);

        // 准备待加入默克尔树的数据块
        List<String> leaves = new ArrayList<>();
        for (int i = 0; i < allDistributeClaimer.get().size(); i++) {
            DistributeClaimer distributeClaimer = allDistributeClaimer.get().get(i);
            Member claimer = memberManager.requireMembeIdExist(distributeClaimer.getClaimerId());
            BigInteger tokenAmount = distributeClaimer.getDistributeAmount()
                    .multiply(BigDecimal.TEN.pow(tokenInfo.getTokenDecimal())).toBigInteger();
            String leafData = distributeClaimer.getId() + claimer.getAddress() + tokenAmount;
            leaves.add(leafData);
        }

        // 使用MerkleTree类的静态方法createTree()生成默克尔树
        MerkleTree merkleTree = new MerkleTree(leaves);
        return merkleTree;
    }


    public static String buildDistributeContractId(String address, String message) {
        // Address addr = new Address(address);
        // Utf8String utf8Str = new Utf8String(message);
        return  Hash.sha3(address.concat(message));
//      return Hash.sha2_256(address.concat(message).getBytes(StandardCharsets.UTF_8)).toString();
    }

}
