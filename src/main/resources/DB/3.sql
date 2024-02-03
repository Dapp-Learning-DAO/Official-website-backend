drop table if exists token_info;

create table token_info (
    id bigint auto_increment primary key,
    chain_id varchar(255) not null,
    token_address varchar(255) not null,
    token_name varchar(255) not null,
    token_symbol varchar(255) not null,
    token_decimal int(11) not null,
    status tinyint(4) not null default 0,
    update_time datetime null,
    create_time datetime not null,
    UNIQUE KEY `uin_chain_address` (`chain_id`, `token_address`),
    KEY `index_status` (`status`)
) engine = InnoDB;

drop table if exists distribute_info;
create table distribute_info (
    id bigint auto_increment primary key,
    creator_id bigint not null,
    name varchar(255) not null,
    claimer_count int(11) default null,
    message varchar(255) not null,
    contract_address varchar(255) default null,
    contract_key varchar(255) default null,
    merkle_root varchar(255) default null,
    expire_time bigint default null,
    chain_id varchar(255) not null,
    token_id bigint not null,
    total_amount bigint not null,
    distribute_type tinyint(4) not null,
    status tinyint(4) not null,
    update_time datetime null,
    create_time datetime not null,
    UNIQUE KEY `uin_chain_creator_message` (
        `chain_id`,
        `creator_id`,
        `message`
    ),
    UNIQUE KEY `uin_chain_contract` (`chain_id`, `contract_address`),
    UNIQUE KEY `uin_chain_contract_key` (`chain_id`, `contract_key`),
    KEY `index_type` (`distribute_type`),
    KEY `index_status` (`status`)
) engine = InnoDB;

drop table if exists distribute_claimer;

create table distribute_claimer (
    id bigint auto_increment primary key,
    chain_id varchar(255) not null,
    distribute_id bigint not null,
    claimer_id bigint null,
    distribute_amount  decimal(30,9) not null,
    status tinyint(4) not null,
    update_time datetime not null,
    create_time datetime not null,
    UNIQUE KEY `uin_distribute_member` (`distribute_id`, `claimer_id`),
    KEY `index_claimer` (`claimer_id`),
    KEY `index_status` (`status`)
) engine = InnoDB;
