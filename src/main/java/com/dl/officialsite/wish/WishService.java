package com.dl.officialsite.wish;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_REPEAT_LIKE;

import com.dl.officialsite.bot.constant.BotEnum;
import com.dl.officialsite.bot.constant.ChannelEnum;
import com.dl.officialsite.bot.event.EventNotify;
import com.dl.officialsite.bot.event.NotifyMessageFactory;
import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.sharing.Share;
import com.dl.officialsite.sharing.SharingService;
import com.dl.officialsite.wish.domain.Wish;
import com.dl.officialsite.wish.domain.WishApply;
import com.dl.officialsite.wish.domain.WishLike;
import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.params.ApplyWishParam;
import com.dl.officialsite.wish.params.DonationWishParam;
import com.dl.officialsite.wish.params.EditWishParam;
import com.dl.officialsite.wish.params.QueryWishParam;
import com.dl.officialsite.wish.repository.WishApplyRepository;
import com.dl.officialsite.wish.repository.WishLikeRepository;
import com.dl.officialsite.wish.repository.WishRepository;
import com.dl.officialsite.wish.result.WishDetailResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxl.job.core.context.XxlJobHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.springframework.context.ApplicationContext;
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

    @Resource
    private SharingService sharingService;

    @Resource
    private WishLikeRepository wishLikeRepository;

    @Resource
    private ApplicationContext applicationContext;

    public CloseableHttpClient httpClient = HttpClients.createDefault();


    @Transactional
    public Wish add(AddWishParam addWishParam, String address) {
        Wish wish = addWishParam.toWish();
        wishRepository.save(wish);

        // 查找分享创建者的信息
        Member creatorInfo = memberRepository.findByAddress(address)
            .orElseThrow(() -> new IllegalArgumentException("Member not found by address: " + address));

        // 格式化日期
        String formattedDate = formatDate(wish.getCreateTime());

        // 发布事件通知
        publishShareCreationEvent(creatorInfo, wish, formattedDate);
        return wish;
    }

    private String formatDate(Date date) {
        // 格式化日期为字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void publishShareCreationEvent(Member creatorInfo, Wish wish, String formattedDate) {
        // 发布创建分享事件的通知
        applicationContext.publishEvent(new EventNotify(
            Member.class,
            BotEnum.TELEGRAM,
            ChannelEnum.GENERAL,
            NotifyMessageFactory.sharingMessage(
                "👏Create New Wish👏",
                creatorInfo.getNickName(),
                wish.getTitle(),
                formattedDate
            )
        ));
    }

    public void edit(EditWishParam editWishParam, String address) {
        Share share = sharingService.querySharing(editWishParam.getShareId());
        Long id = editWishParam.getId();
        Wish wish = wishRepository.findById(id).orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_WISH));
        BeanUtils.copyProperties(editWishParam, wish);
        wish.setAmount(editWishParam.getDonationAmount());
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
        wishLikeRepository.findByMemberIdAndWishId(creator.getId(), id).ifPresent(wishLike -> wishDetailResult.setLiked(1));
        sharingService.querySharingByWishId(id).ifPresent(share -> {
            wishDetailResult.setShareAddress(share.getMemberAddress());
            wishDetailResult.setShareUrl(share.getSharingDoc());
            wishDetailResult.setShareUser(share.getPresenter());
        });
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
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));
        if (ObjectUtils.isEmpty(wish.getLikeNumber())) {
            wish.setLikeNumber(0);
        }
        wish.setLikeNumber(wish.getLikeNumber() + 1);
        wishLikeRepository.findByMemberIdAndWishId(member.getId(), wishId).ifPresent(wishLike -> {
            throw new BizException(NOT_REPEAT_LIKE);
        });
        wishRepository.save(wish);
        WishLike wishLike = new WishLike();
        wishLike.setMemberId(member.getId());
        wishLike.setWishId(wish.getId());
        wishLikeRepository.save(wishLike);

    }

    @Transactional
    public void donation(String address, DonationWishParam donationWishParam) {
        Wish wish = wishRepository.findById(donationWishParam.getWishId()).orElseThrow(() -> new BizException(
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
        wishApply.setAmount(donationWishParam.getAmount());
        wishApply.setTokenSymbol(donationWishParam.getTokenSymbol());
        wishApply.setToken(donationWishParam.getToken());
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
        try {
            HttpEntity entity = getHttpEntityFromChain(chainId);
            String jsonResponse = EntityUtils.toString(entity);
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray vaultsArray = data.getAsJsonArray("Vault");

            // 构建一个vaultId到vault对象的映射，避免嵌套循环
            Map<String, JsonObject> vaultsMap = new HashMap<>();
            for (int i = 0; i < vaultsArray.size(); i++) {
                JsonObject vault = vaultsArray.get(i).getAsJsonObject();
                vaultsMap.put(vault.get("vaultId").getAsString(), vault);
            }

            // 查找并更新wish列表
            List<Wish> wishList = wishRepository.findByChainId(chainId);
            if (wishList.isEmpty()) {
                return;
            }
            List<Wish> deleteWishList = new ArrayList<>();

            for (Wish wish : wishList) {
                JsonObject vault = vaultsMap.get(wish.getVaultId());
                if (vault != null) {
                    wish.setTargetAmount(vault.get("totalAmount").getAsString());

                    // 如果有claims，更新状态
                    if (vault.getAsJsonArray("claims").size() > 0) {
                        wish.setStatus(1);
                    }
                    // 计算所有donations的总金额
                    JsonArray donations = vault.getAsJsonArray("donations");
                    if (donations.size() > 0) {
                        BigDecimal totalAmount = new BigDecimal(0);
                        for (int j = 0; j < donations.size(); j++) {
                            String amount = donations.get(j).getAsJsonObject().get("amount").getAsString();
                            totalAmount = totalAmount.add(new BigDecimal(amount));
                        }
                        wish.setAmount(totalAmount.toPlainString());
                    }
                } else {
                    deleteWishList.add(wish);
                }
            }

            // 批量保存更新后的wish
            wishRepository.saveAll(wishList);
            wishRepository.deleteAll(deleteWishList);

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
            case Constants.CHAIN_ID_OP_SEPOLIA: //sepolia
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

    public Wish apply(Long wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));
        wish.setApply(1);
        wishRepository.save(wish);
        return wish;
    }
}



