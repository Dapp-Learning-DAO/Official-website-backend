package com.dl.officialsite.config.bean;

import com.dl.officialsite.config.constant.ConfigEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicUpdate
@Table(name = "server_config", schema = "dl", uniqueConstraints = {
    @UniqueConstraint(name = "unique_config_name", columnNames = {"configName"})
})
public class ServerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private ConfigEnum configName;

    @Column(length = 2048)
    private String configValue;

    @CreatedDate
    @Column(updatable = false)
    private Long createTime;

    @LastModifiedDate
    @Column(updatable = true, nullable = false)
    private Long updateTime;

    private String remark;

    private String editBy;
}
