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

delete  from red_packet;
alter table red_packet MODIFY COLUMN id bigint;


alter table red_packet drop index  name;