package com.dl.officialsite.wish;

import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.wish.params.AddWishParam;
import com.dl.officialsite.wish.params.ApplyWishParam;
import com.dl.officialsite.wish.params.EditWishParam;
import com.dl.officialsite.wish.params.QueryWishParam;
import com.dl.officialsite.wish.result.WishDetailResult;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class WishService {

    @Resource
    private WishRepository wishRepository;

    @Resource
    private WishLikeRepository wishLikeRepository;

    @Resource
    private MemberRepository memberRepository;


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
        return wishRepository.findById(id).map(this::buildWishDetailResult).orElseThrow(() -> new BizException(CodeEnums.NOT_FOUND_WISH));
    }

    private WishDetailResult buildWishDetailResult(Wish wish) {
        WishDetailResult wishDetailResult = new WishDetailResult();
        BeanUtils.copyProperties(wish, wishDetailResult);
        return wishDetailResult;
    }

    @Transactional
    public void like(Long wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new BizException(
            CodeEnums.NOT_FOUND_WISH
        ));
        if (ObjectUtils.isEmpty(wish.getLikeNumber())) {
            wish.setLikeNumber(0);
        }
        wish.setLikeNumber(wish.getLikeNumber() + 1);
        wishRepository.save(wish);
    }

    public Page<WishApply> getLikeList(Long wishId, Pageable pageable) {
        Page<WishApply> page = wishLikeRepository.findAll(
            (Specification<WishApply>) (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new LinkedList<>();
                predicates.add(criteriaBuilder.equal(root.get("wishId"), wishId));
                query.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable);
        return page;
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
        wishApply.setAmount(applyWishParam.getAmount());
        wishApply.setTokenSymbol(applyWishParam.getTokenSymbol());
        wishLikeRepository.save(wishApply);

    }
}



