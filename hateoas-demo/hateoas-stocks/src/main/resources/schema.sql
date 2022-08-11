drop table t_stocks if exists;

create table t_stocks (
    id  bigint auto_increment,
    name varchar(64),
    price double,
    create_time timestamp,
    update_time timestamp,
    primary key(id)
)
