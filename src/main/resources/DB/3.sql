drop table distribute_info;

create table distribute_info (
    id bigint auto_increment primary key,
    creator_id bigint not null,
    distribute_name varchar(255) not null,
    distribute_nonce int not null,
    expire_time bigint not null,
    chain_id bigint not null,
    token_address varchar(255) not null,
    total_amount bigint not null,
    status tinyint(4) not null,
    update_time datetime null,
    create_time datetime not null,
    creator_name varchar(255) null,
    UNIQUE KEY `uin_nonce` (`distribute_nonce`),
    UNIQUE KEY `uin_name` (`distribute_name`),
    KEY `index_creator` (`creator_id`)
) engine = InnoDB;

drop table distribute_member_info;

create table distribute_member_info (
    id bigint auto_increment primary key,
    distribute_id bigint not null,
    member_id bigint not null,
    distribute_amount bigint not null,
    status tinyint(4) not null,
    update_time datetime null,
    create_time datetime not null,
    creator_name varchar(255) null,
    UNIQUE KEY `uin_distribute_member` (`distribute_id`, `member_id`),
    KEY `index_member` (`member_id`)
) engine = InnoDB;