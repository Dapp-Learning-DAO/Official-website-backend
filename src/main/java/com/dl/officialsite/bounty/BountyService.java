package com.dl.officialsite.bounty;

import static com.dl.officialsite.common.enums.CodeEnums.NOT_FOUND_BOUNTY;

import com.dl.officialsite.bounty.vo.BountySearchVo;
import com.dl.officialsite.bounty.vo.BountyVo;
import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.exception.BizException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @ClassName BountyService
 * @Author jackchen
 * @Date 2024/1/26 19:15
 * @Description BountyService
 **/
@Service
public class BountyService {

    private final BountyRepository bountyRepository;

    public BountyService(BountyRepository bountyRepository) {
        this.bountyRepository = bountyRepository;
    }

    public void add(BountyVo bountyVo, String address) {
        Bounty bounty = new Bounty();
        BeanUtils.copyProperties(bountyVo, bounty);
        bountyRepository.save(bounty);
    }

    public void update(BountyVo bountyVo, String address) {
        Bounty bounty = bountyRepository.findById(bountyVo.getId())
            .orElseThrow(() -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        BeanUtils.copyProperties(bountyVo, bounty);
        bountyRepository.save(bounty);
    }

    public Page<BountyVo> search(BountySearchVo bountySearchVo, Pageable pageable) {
        return bountyRepository.findAll((Specification<Bounty>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (bountySearchVo.getCreator() != null) {
                predicates.add(criteriaBuilder.equal(root.get("creator"), bountySearchVo.getCreator()));
            }
            if (bountySearchVo.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + bountySearchVo.getTitle() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(bounty -> {
            BountyVo bountyVo = new BountyVo();
            BeanUtils.copyProperties(bounty, bountyVo);
            return bountyVo;
        });
    }

    public void delete(Long id, String address) {
        Bounty bounty = bountyRepository.findById(id)
            .orElseThrow(() -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        bounty.setStatus(Constants.BOUNTY_STATUS_DELETE);
        bountyRepository.save(bounty);
    }

    public BountyVo findById(Long id) {
        Bounty bounty = bountyRepository.findById(id)
            .orElseThrow(() -> new BizException(NOT_FOUND_BOUNTY.getCode(), NOT_FOUND_BOUNTY.getMsg()));
        BountyVo bountyVo = new BountyVo();
        BeanUtils.copyProperties(bounty, bountyVo);
        return bountyVo;
    }
}
