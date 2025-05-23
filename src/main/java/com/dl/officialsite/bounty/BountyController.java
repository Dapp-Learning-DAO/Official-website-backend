package com.dl.officialsite.bounty;


import com.dl.officialsite.bounty.params.ApplyBountyParam;
import com.dl.officialsite.bounty.vo.ApplyBountyVo;
import com.dl.officialsite.bounty.vo.BountyMemberVo;
import com.dl.officialsite.bounty.vo.BountySearchVo;
import com.dl.officialsite.bounty.vo.BountyVo;
import com.dl.officialsite.bounty.vo.MyBountySearchVo;
import com.dl.officialsite.common.base.BaseResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * bounty模块
 */
@RestController
@RequestMapping("/bounty")
@Slf4j
public class BountyController {

    private final BountyService bountyService;

    public BountyController(BountyService bountyService) {
        this.bountyService = bountyService;
    }
    //1 bounty 创建/更新/查询/删除（置status）
    /**
     * 创建bounty
     */
    @PostMapping
    public BaseResponse add(@RequestBody BountyVo bountyVo, @RequestParam String address) {
        return BaseResponse.successWithData(bountyService.add(bountyVo, address));
    }

    /**
     * 更新bounty
     */
    @PutMapping
    public BaseResponse update(@RequestBody BountyVo bountyVo, @RequestParam String address) {
        bountyService.update(bountyVo, address);
        return BaseResponse.successWithData(null);
    }

    /**
     * 查询bounty
     */
    @PostMapping("/list")
    public BaseResponse list(@RequestBody BountySearchVo bountySearchVo,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<BountyVo> page = bountyService.search(bountySearchVo, pageable);
        return BaseResponse.successWithData(page);
    }

    /**
     * 查询bounty详情
     */
    @GetMapping("/detail")
    public BaseResponse detail(@RequestParam Long id,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        BountyVo bountyVo = bountyService.findByIdInternal(id);
        return BaseResponse.successWithData(bountyVo);
    }

    /**
     * 查询bounty申请人下的信息
     */
    @GetMapping("/detail/member")
    public BaseResponse detailMember(@RequestParam Long id,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<BountyMemberVo> page = bountyService.findBountyMemberMapByBountyId(id,pageable);
        return BaseResponse.successWithData(page);
    }

    /**
     * 删除bounty
     */
    @DeleteMapping
    public BaseResponse delete(@RequestParam Long id, @RequestParam String address) {
        bountyService.delete(id, address);
        return BaseResponse.successWithData(null);
    }

    // 2 bounty申请和匹配
    @PostMapping("/apply")
    public BaseResponse apply(@Valid @RequestBody ApplyBountyParam applyBountyParam) {
        bountyService.apply(applyBountyParam);
        return BaseResponse.successWithData(null);
    }

    //查看自己名下的bounty
    @PostMapping("/mybounty")
    public BaseResponse myBounty(@RequestBody MyBountySearchVo myBountySearchVo,
        @RequestParam(defaultValue = "1") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<BountyVo> page = bountyService.myBounty(myBountySearchVo,pageable);
        return BaseResponse.successWithData(page);
    }

    //批准申请
    @PutMapping("/approve")
    public BaseResponse approve(@RequestParam String address,
     @RequestBody ApplyBountyVo applyBountyVo   ) {
        bountyService.approve(applyBountyVo);
        return BaseResponse.successWithData(null);
    }

    //查看自己是否投递过这个bounty
    @GetMapping("/isapply")
    public BaseResponse isApply(@RequestParam Long bountyId, @RequestParam String address) {
        return BaseResponse.successWithData(bountyService.isApply(bountyId, address));
    }

    //关联流支付与bounty
    @PostMapping("/link")
    public BaseResponse link(@RequestParam String address,@RequestBody BountyVo bountyVo) {
        bountyService.link(bountyVo, address);
        return BaseResponse.successWithData(null);
    }



}
