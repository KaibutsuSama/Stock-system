drop table t_order if exists;

create table t_order (
    id bigint auto_increment,
    user varchar(64),
    stock_name varchar(64),
    volume int,
    price double ,
    create_time timestamp ,
    update_time timestamp,
    primary key (id)
)
