package com.dl.officialsite.wish;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.params.ApplyWishParam;
import com.dl.officialsite.wish.params.EditWishParam;
import com.dl.officialsite.wish.params.QueryWishParam;
import com.dl.officialsite.wish.result.WishDetailResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxl.job.core.context.XxlJobHelper;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @ClassName WishService
 * @Author jackchen
 * @Date 2024/12/25 16:51
 * @Description WishService
 **/
@Service
@Slf4j
public class WishService {

    @Resource
    private WishRepository wishRepository;

    @Resource
    private WishApplyRepository wishApplyRepository;

    @Resource
    private MemberRepository memberRepository;

    @Autowired
    private ChainConfig chainConfig;

    public CloseableHttpClient httpClient = HttpClients.createDefault();


    @Transactional
    public Wish add(AddWishParam addWishParam, String address) {
        Wish wish = addWishParam.toWish();
        wishRepository.save(wish);
        return wish;
    }

    public void edit(EditWishParam editWishParam, String address) {
        Long id = editWishParam.getId();
        Wish wish = wishRepository.findById(id).orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_WISH));
        BeanUtils.copyProperties(editWishParam, wish);
        wishRepository.save(wish);
    }

    public void delete(Long id, String address) {
        wishRepository.deleteById(id);
    }

    public Page<Wish> list(QueryWishParam queryWishParam, Pageable pageable) {
        Page<Wish> page = wishRepository.findAll(
            (Specification<Wish>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
                if (StringUtils.hasText(queryWishParam.getTitle())) {
                    predicates.add(
                        criteriaBuilder.like(root.get("title"), "%" + queryWishParam.getTitle() + "%"));
                }
                if (StringUtils.hasText(queryWishParam.getDescription())) {
                    predicates.add(
                        criteriaBuilder.like(root.get("description"), "%" + queryWishParam.getDescription() + "%"));
                }
                if (StringUtils.hasText(queryWishParam.getTag())) {
                    predicates.add(
                        criteriaBuilder.like(root.get("tag"), "%" + queryWishParam.getTag() + "%"));
                }
                if (StringUtils.hasText(queryWishParam.getAmount())) {
                    predicates.add(
                        criteriaBuilder.like(root.get("amount"), "%" + queryWishParam.getAmount() + "%"));
                }
                query.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return page;
    }

    public WishDetailResult get(Long id, String address) {
        WishApply example = new WishApply();
        example.setWishId(id);
        List<WishApply> wishApplyList = wishApplyRepository.findAll(Example.of(example),
            Sort.by(Direction.DESC, "createTime"));
        WishDetailResult wishDetailResult = wishRepository.findById(id)
            .map(this::buildWishDetailResult)
            .orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_WISH));
        wishDetailResult.setWishApplyList(wishApplyList);
        Member creator = memberRepository.findByAddress(wishDetailResult.getCreateAddress())
            .orElse(new Member());
        wishDetailResult.setCreator(creator);
        return wishDetailResult;
    }

    private WishDetailResult buildWishDetailResult(Wish wish) {
        WishDetailResult wishDetailResult = new WishDetailResult();
        BeanUtils.copyProperties(wish, wishDetailResult);
        return wishDetailResult;
    }

    @Transactional
    public void like(Long wishId, String address) {
        Member member =
            memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
                CodeEnums.NOT_FOUND_MEMBER));
        member.setLiked(1);
        memberRepository.save(member);
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));
        if (ObjectUtils.isEmpty(wish.getLikeNumber())) {
            wish.setLikeNumber(0);
        }
        wish.setLikeNumber(wish.getLikeNumber() + 1);
        wishRepository.save(wish);
    }

    @Transactional
    public void apply(String address, ApplyWishParam applyWishParam) {
        Wish wish = wishRepository.findById(applyWishParam.getWishId()).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));
        Member member =
            memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
                CodeEnums.NOT_FOUND_MEMBER));
        WishApply wishApply = new WishApply();
        wishApply.setWishId(wish.getId());
        wishApply.setMemberId(member.getId());
        wishApply.setMemberName(member.getNickName());
        wishApply.setMemberAddress(member.getAddress());
        wishApply.setAmount(applyWishParam.getAmount());
        wishApply.setTokenSymbol(applyWishParam.getTokenSymbol());
        wishApply.setToken(applyWishParam.getToken());
        wishApplyRepository.save(wishApply);

    }

    /**
     * 定时查询愿望清单中的amount
     */
    public void updateWishTokenAmount() {
        XxlJobHelper.log("WishService updateWishTokenAmount start");
        for (String chainId : chainConfig.getIds()) {
            try {
                updateWishForChain(chainId);
            } catch (Exception e) {
                XxlJobHelper.log("updateWishForChain:  " + chainId + " error:" + e.getMessage());
            }
        }

    }


    private void updateWishForChain(String chainId) {

        String jsonResponse = "";
        try {
            HttpEntity entity = getHttpEntityFromChain(chainId);
            jsonResponse = EntityUtils.toString(entity);
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray vaultsArray = data.getAsJsonArray("Vault");
            List<Wish> wishList = wishRepository.findByChainId(chainId);
            if (wishList.isEmpty()) {
                return;
            }

            for (Wish wish : wishList) {
                for (int i = 0; i < vaultsArray.size(); i++) {
                    JsonObject vault = vaultsArray.get(i).getAsJsonObject();
                    if (vault.get("vaultId").getAsString().equals(wish.getVaultId())) {
                        wish.setAmount(vault.get("totalAmount").getAsString());
                        if (vault.getAsJsonArray("claims").size() > 0) {
                            wish.setStatus(1);
                        }
                    }
                }
            }
            wishRepository.saveAll(wishList);

        } catch (IOException e) {
            log.error("定时更新愿望清单失败{}", e);
        }
    }



    private HttpEntity getHttpEntityFromChain(String chainId)  throws IOException{
        HttpPost request = null;
        switch (chainId) {
            case Constants.CHAIN_ID_OP:  // op
                request = new HttpPost("https://indexer.dev.hyperindex.xyz/31816b0/v1/graphql");
                break;
            case Constants.CHAIN_ID_SEPOLIA: //sepolia
                request = new HttpPost("https://indexer.dev.hyperindex.xyz/2cf0e82/v1/graphql");
                break;

        }
        request.setHeader("Content-Type", "application/json");
        String graphQL = buildGraphQL();
        request.setEntity(new StringEntity(graphQL));
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    private String buildGraphQL() {
        return "{\"query\":\"query {\\n  Vault(limit: 50) {\\n    id\\n    vaultId\\n    "
            + "creator\\n    createdAt\\n    message\\n    token\\n    totalAmount\\n    totalClaimedAmount\\n    lockTime\\n    donations {\\n      donor\\n      amount\\n      token\\n    }\\n    claims {\\n      claimer\\n      token\\n      amount\\n    }\\n    settlements {\\n      claimer\\n      maxClaimableAmount\\n    }\\n  }\\n}\"}";
    }
}



