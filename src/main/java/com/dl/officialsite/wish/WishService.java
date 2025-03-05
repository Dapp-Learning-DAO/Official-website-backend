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
import com.dl.officialsite.wish.enums.DonateStatusEnum;
import com.dl.officialsite.wish.enums.WishStatusEnum;
import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.params.ApplyWishParam;
import com.dl.officialsite.wish.params.DonationWishParam;
import com.dl.officialsite.wish.params.EditWishParam;
import com.dl.officialsite.wish.params.QueryWishParam;
import com.dl.officialsite.wish.params.SettleWishParam;
import com.dl.officialsite.wish.repository.WishApplyRepository;
import com.dl.officialsite.wish.repository.WishLikeRepository;
import com.dl.officialsite.wish.repository.WishRepository;
import com.dl.officialsite.wish.result.WishApplyResult;
import com.dl.officialsite.wish.result.WishDetailResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxl.job.core.context.XxlJobHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
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


        // 查找分享创建者的信息
        Member creatorInfo = memberRepository.findByAddress(address)
            .orElseThrow(() -> new IllegalArgumentException("Member not found by address: " + address));
        wish.setCreateAddress(creatorInfo.getAddress());
        wish.setCreateUser(creatorInfo.getNickName());
        wishRepository.save(wish);

        addWishApplyUser(wish, creatorInfo, addWishParam);

        // 格式化日期
        String formattedDate = formatDate(wish.getCreateTime());

        // 发布事件通知
        publishShareCreationEvent(creatorInfo, wish, formattedDate);
        return wish;
    }

    private void addWishApplyUser(Wish wish, Member creatorInfo, AddWishParam addWishParam) {
        WishApply wishApply = new WishApply();
        wishApply.setWishId(wish.getId());
        wishApply.setMemberId(creatorInfo.getId());
        wishApply.setMemberName(creatorInfo.getNickName());
        wishApply.setMemberAddress(creatorInfo.getAddress());
        wishApply.setAmount(addWishParam.getDonationWishParam().getAmount());
        wishApply.setTokenSymbol(addWishParam.getDonationWishParam().getTokenSymbol());
        wishApply.setToken(addWishParam.getDonationWishParam().getToken());
        wishApply.setChainId(addWishParam.getDonationWishParam().getChainId());
        wishApplyRepository.save(wishApply);
    }

    private String formatDate(LocalDateTime date) {
        // 使用 Java 8+ 的 DateTimeFormatter 进行格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
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
                if (StringUtils.hasText(queryWishParam.getChainId())) {
                    predicates.add(
                        criteriaBuilder.equal(root.get("chainId"), queryWishParam.getChainId()));
                }
                predicates.add(
                    criteriaBuilder.equal(root.get("createStatus"), 1));
                query.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return page;
    }

    public WishDetailResult get(Long id, String address) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnorePaths("status");
        WishApply example = new WishApply();
        example.setWishId(id);
        List<WishApply> wishApplyList = wishApplyRepository.findAll(Example.of(example, matcher),
            Sort.by(Direction.DESC, "createTime"));
        WishDetailResult wishDetailResult = wishRepository.findById(id)
            .map(this::buildWishDetailResult)
            .orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_WISH));
        List<WishApplyResult> wishApplyResultList = wishApplyList.stream().map(this::buildWishApply).collect(Collectors.toList());
        wishDetailResult.setWishApplyList(wishApplyResultList);
        Member creator = memberRepository.findByAddress(wishDetailResult.getCreateAddress())
            .orElse(new Member());

        Member loginUser = memberRepository.findByAddress(address)
            .orElse(new Member());
        wishDetailResult.setCreator(creator);
        wishLikeRepository.findByMemberIdAndWishId(loginUser.getId(), id).ifPresent(wishLike -> wishDetailResult.setLiked(1));
        sharingService.querySharingByWishId(id).ifPresent(share -> {
            wishDetailResult.setShareId(share.getId());
            wishDetailResult.setShareTitle(share.getTheme());
            wishDetailResult.setShareAddress(share.getMemberAddress());
            wishDetailResult.setShareUser(share.getPresenter());
            Member shareMember =
                memberRepository.findByAddress(share.getMemberAddress()).orElseThrow(() -> new BizException(
                    CodeEnums.NOT_FOUND_MEMBER));
            wishDetailResult.setShareGithubId(shareMember.getGithubId());
            wishDetailResult.setShareTweetId(shareMember.getTweetId());
        });
        return wishDetailResult;
    }

    private WishDetailResult buildWishDetailResult(Wish wish) {
        WishDetailResult wishDetailResult = new WishDetailResult();
        BeanUtils.copyProperties(wish, wishDetailResult);
        return wishDetailResult;
    }

    private WishApplyResult buildWishApply(WishApply wishApply) {
        WishApplyResult wishApplyResult = new WishApplyResult();
        BeanUtils.copyProperties(wishApply, wishApplyResult);
        return wishApplyResult;
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
        wishApply.setChainId(donationWishParam.getChainId());
        wishApplyRepository.save(wishApply);

    }

    /**
     * 定时查询愿望清单中的amount
     */
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void updateWishTokenAmount() {
        for (String chainId : chainConfig.getIds()) {
            try {
                updateWishForChain(chainId);
            } catch (Exception e) {
                XxlJobHelper.log("updateWishForChain:  " + chainId + " error:" + e.getMessage());
            }
        }

    }



    public void updateWishForChain(String chainId) {
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

            for (Wish wish : wishList) {
                JsonObject vault = vaultsMap.get(wish.getVaultId());
                if (vault != null) {
                    wish.setTargetAmount(vault.get("totalAmount").getAsString());

                    //判断是否过期
                    isWishExpired(wish, vault);

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
                    wish.setCreateStatus(1);
                    //update wish apply status
                    syncWishApplyStatus(wish, donations,chainId);
                }
            }

            // 批量保存更新后的wish
            wishRepository.saveAll(wishList);

        } catch (IOException e) {
            log.error("定时更新愿望清单失败{}", e);
        }
    }

    private void isWishExpired(Wish wish, JsonObject vault) {
        // 获取 lockTime 并转换为 long
        String lockTimeStr = vault.get("lockTime").getAsString();
        long lockTime = Long.parseLong(lockTimeStr) * 1000; // 时间戳应为毫秒级

        // 获取当前时间戳（毫秒）
        long currentTime = Instant.now().toEpochMilli();

        // 判断是否过期
        if (lockTime < currentTime) {
            wish.setStatus(WishStatusEnum.FINISH.getStatus());
        }
        int wishStatus = (lockTime < currentTime) ? 1 : 0;
        wish.setStatus(wishStatus);
    }

    private void syncWishApplyStatus(Wish wish, JsonArray donations, String chainId) {
        // 提取所有 donor 地址到 Set，提高匹配效率
        Set<String> donorAddresses = StreamSupport.stream(donations.spliterator(), false)
            .map(donation -> donation.getAsJsonObject().get("donor").getAsString())
            .collect(Collectors.toSet());

        // 过滤出需要更新状态的 wishApply
        List<WishApply> updatedWishApplies = wishApplyRepository.findByWishIdAndChainId(wish.getId(), chainId).stream()
            .filter(wishApply -> donorAddresses.contains(wishApply.getMemberAddress()))
            .peek(wishApply -> wishApply.setStatus(DonateStatusEnum.SUCCESS.getDesc()))
            .collect(Collectors.toList());

        // 批量保存更新的 wishApply
        if (!updatedWishApplies.isEmpty()) {
            wishApplyRepository.saveAll(updatedWishApplies);
        }
    }




    private HttpEntity getHttpEntityFromChain(String chainId)  throws IOException{
        HttpPost request = null;
        switch (chainId) {
            case Constants.CHAIN_ID_OP_SEPOLIA: //sepolia
                request = new HttpPost("https://indexer.dev.hyperindex.xyz/a228a53/v1/graphql");
                break;
            case Constants.CHAIN_ID_OP:
                request = new HttpPost("https://indexer.dev.hyperindex.xyz/0e66bcb/v1/graphql");
            default:
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

    @Transactional
    public void settle(String address, SettleWishParam settleWishParam) {
        Wish wish =
            wishRepository.findById(settleWishParam.getWishId()).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));

        Member member =
            memberRepository.findByAddress(address).orElseThrow(() -> new BizException(
                CodeEnums.NOT_FOUND_MEMBER));

        wish.setSettleUser(member.getNickName());
        wish.setSettleAddress(member.getAddress());
        wish.setSettleTime(LocalDateTime.now());
        wish.setStatus(WishStatusEnum.SETTLE.getStatus());
        wishRepository.save(wish);
    }
}



