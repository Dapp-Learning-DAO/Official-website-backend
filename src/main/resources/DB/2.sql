-- 原因:createName修改为creatorName
drop table application;
create table application
(
    id     bigint auto_increment
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

delete  from red_packet;

alter table red_packet MODIFY COLUMN id bigint(20);

alter table red_packet MODIFY COLUMN chain_id varchar(66);

alter table red_packet drop index  name;

ALTER TABLE red_packet DROP INDEX id;


alter table red_packet add unique index id_chain(id,chain_id);

alter table red_packet MODIFY COLUMN id  varchar(66);

ALTER TABLE red_packet add  unique INDEX id(id);