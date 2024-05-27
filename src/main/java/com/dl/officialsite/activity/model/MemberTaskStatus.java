package com.dl.officialsite.activity.model;

import com.dl.officialsite.activity.constant.TaskTypeEnum;
import lombok.Data;

@Data
public class MemberTaskStatus {
    private TaskTypeEnum taskType;
    private String taskName;
    private String target;
    private String targetUrl;
    private String description;
    private String viewExplorer;

    private boolean requiredAuthorization = true;
    private boolean finished = false;

    public MemberTaskStatus() {
    }

    public MemberTaskStatus(TaskTypeEnum taskType, String taskName, String target, String targetUrl, String description,
                            String viewExplorer) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.target = target;
        this.targetUrl = targetUrl;
        this.description = description;
        this.viewExplorer = viewExplorer;
    }
}