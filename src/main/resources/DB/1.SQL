CREATE TABLE SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) NOT NULL,
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    EXPIRY_TIME           BIGINT   NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES    BLOB         NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

--create table tb_oauth_github (
--	`pk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
--	`uid` bigint(11) not null comment '用户主键',
--    `github_username` varchar(255) not null comment 'github账号',
--	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
--	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--	PRIMARY KEY `pk_id`(`pk_id`),
--    UNIQUE INDEX `unique_uid`(`uid`),
--    UNIQUE INDEX `unique_github_username`(`github_username`)
--);
-- 原因:createName修改为creatorName
drop table application;
create table application
(
    id           bigint auto_increment
        primary key,
    create_time  datetime     not null,
    creator_name varchar(255) null,
    hiring_id    bigint       null,
    member_id    bigint       null,
    member_name  varchar(255) null,
    status       int          null,
    update_time  datetime     null
)
    engine = InnoDB;