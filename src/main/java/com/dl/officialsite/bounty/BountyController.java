package com.dl.officialsite.bounty;


import com.dl.officialsite.bounty.vo.BountySearchVo;
import com.dl.officialsite.bounty.vo.BountyVo;
import com.dl.officialsite.bounty.vo.MyBountySearchVo;
import com.dl.officialsite.common.base.BaseResponse;
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

    //todo 是否可以批量新增bounty
    //todo 是否可以批量申请bounty，一个人最多申请几个？
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
    public BaseResponse detail(@RequestParam Long id, @RequestParam String address) {
        BountyVo bountyVo = bountyService.findById(id);
        return BaseResponse.successWithData(bountyVo);
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
    public BaseResponse apply(@RequestParam Long bountyId, @RequestParam String address) {
        bountyService.apply(bountyId, address);
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
    public BaseResponse approve(@RequestParam Long bountyId, @RequestParam String memberAddress) {
        bountyService.approve(bountyId, memberAddress);
        return BaseResponse.successWithData(null);
    }




}
