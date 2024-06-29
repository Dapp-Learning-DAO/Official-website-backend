package com.dl.officialsite.activity;

import com.dl.officialsite.activity.config.ActivityConfig;
import com.dl.officialsite.activity.config.Task;
import com.dl.officialsite.activity.constant.TaskTypeEnum;
import com.dl.officialsite.activity.model.MemberTaskStatus;
import com.dl.officialsite.activity.service.MemberTaskService;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.nft.config.EcdsaKeyConfigService;
import com.dl.officialsite.nft.constant.ContractNameEnum;
import com.dl.officialsite.nft.constant.EcdsaKeyTypeEnum;
import com.dl.officialsite.nft.dto.SignatureDto;
import com.dl.officialsite.nft.service.WarCraftContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dl.officialsite.common.enums.CodeEnums.PARAM_ERROR;

/**
 * 活动模块
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {
    @Autowired
    private MemberTaskService memberTaskService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ActivityConfig activityConfig;
    @Autowired
    private ChainConfig chainConfig;
    @Autowired
    private EcdsaKeyConfigService ecdsaKeyConfigService;
    @Autowired
    private WarCraftContractService warCraftContractService;

    /**
     * 获取活动中用户的状态
     */
    @GetMapping("/status")
    public BaseResponse fetchMemberTasksStatus(@RequestParam(required = false) String addressForTesting, HttpSession session) {
        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return BaseResponse.successWithData(memberTaskService.getMemberTasksStatusByAddress(address, memberOptional));
    }

    /**
     * 检查用户是否完成任务
     */
    @PostMapping("/check")
    public BaseResponse checkTask(@NotNull @RequestParam("taskType") TaskTypeEnum taskType, @NotNull @RequestParam("target") String target,
                                  @RequestParam(required = false) String addressForTesting, HttpSession session) {
        if (taskType == null) {
            return BaseResponse.failWithReason("1201", "Parameter [taskType] should not be null.");
        }
        if (StringUtils.isBlank(target)) {
            return BaseResponse.failWithReason("1201", "Parameter [target] should not be null.");
        }
        Optional<Task> task = activityConfig.findTask(taskType, target);
        if (!task.isPresent()) {
            return BaseResponse.failWithReason("1201", String.format("Task [%s:%s] is not defined.", taskType, target));
        }

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        Optional<Member> memberOptional = memberRepository.findByAddress(address);
        return memberOptional.map(member -> memberTaskService.checkStatus(member, address, taskType, task.get()))
            .orElseGet(() -> BaseResponse.failWithReason("1001", "no user found")); // 用户需要注册
    }

    /**
     * 检查用户是否完成所有任务，如果完成，则生成 NFT mint 签名
     */
    @GetMapping("/mint")
    public BaseResponse verifyAndMint(@NotNull @RequestParam("chainId") String chainIdParam,
                                      @RequestParam(required = false) String addressForTesting,
                                      @RequestParam(required = false, defaultValue = "NFT") String keyType,
                                      @RequestParam(required = false, defaultValue = "WarCraft") String contractName,
                                      HttpSession session) {
        String chainId = Arrays.stream(chainConfig.getIds()).filter(id -> StringUtils.equalsIgnoreCase(chainIdParam, id))
            .findFirst()
            .orElseThrow(() -> new BizException(PARAM_ERROR.getCode(), String.format("Chain id %s not exists", chainIdParam)));

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        List<MemberTaskStatus> memberTaskStatuses = this.memberTaskService.userUnfinishedTasks(address);
        if (CollectionUtils.isNotEmpty(memberTaskStatuses)) {
            return BaseResponse.failWithReason("1203", "Have not finished all the tasks.");
        }

        SignatureDto signature = this.ecdsaKeyConfigService.sign(EcdsaKeyTypeEnum.fromValue(keyType),
            ContractNameEnum.fromValue(contractName), address, chainId);
        if (signature != null) {
            return BaseResponse.successWithData(signature);
        }
        return BaseResponse.failWithReason("1204", "Generate signature failed.");
    }

    /**
     * 检查用户的 Mint 结果
     */
    @GetMapping("/claim/result")
    public BaseResponse claimResult(@NotNull @RequestParam("chainId") String chainIdParam,
                                    @RequestParam(required = false) String addressForTesting,
                                    @RequestParam(required = false, defaultValue = "WarCraft") String contractName,
                                    HttpSession session) {
        String chainId = Arrays.stream(chainConfig.getIds()).filter(id -> StringUtils.equalsIgnoreCase(chainIdParam, id))
            .findFirst()
            .orElseThrow(() -> new BizException(PARAM_ERROR.getCode(), String.format("Chain id %s not exists", chainIdParam)));

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        return BaseResponse.successWithData(this.warCraftContractService.rank(address, ContractNameEnum.fromValue(contractName),
            chainId).getKey());
    }

    /**
     * 检查用户的 Mint 结果
     */
    @GetMapping("/v2/claim/result")
    public BaseResponse claimResultWithTokenId(@NotNull @RequestParam("chainId") String chainIdParam,
                                    @RequestParam(required = false) String addressForTesting,
                                    @RequestParam(required = false, defaultValue = "WarCraft") String contractName,
                                    HttpSession session) {
        String chainId = Arrays.stream(chainConfig.getIds()).filter(id -> StringUtils.equalsIgnoreCase(chainIdParam, id))
            .findFirst()
            .orElseThrow(() -> new BizException(PARAM_ERROR.getCode(), String.format("Chain id %s not exists", chainIdParam)));

        SessionUserInfo sessionUserInfo = HttpSessionUtils.getMember(session);
        final String address = sessionUserInfo != null ? sessionUserInfo.getAddress() : addressForTesting;

        return BaseResponse.successWithData(this.warCraftContractService.rank(address, ContractNameEnum.fromValue(contractName), chainId));
    }
}