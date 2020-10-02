drop table if exists t_coffee;
drop table if exists t_order;
drop table if exists t_order_coffee;
drop SEQUENCE if exists s_coffee_seq;
drop SEQUENCE if exists s_order_seq;

create table t_coffee (
    id bigint not null,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    price bigint,
    primary key (id)
);

create table t_order (
    id bigint not null,
    create_time timestamp,
    update_time timestamp,
    customer varchar(255),
    waiter varchar(255),
	barista varchar(255),
    discount integer,
    total bigint,
    state integer,
    primary key (id)
);


create table t_order_coffee (
    coffee_order_id bigint not null,
    items_id bigint not null
);

CREATE SEQUENCE s_coffee_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE s_order_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;


insert into t_coffee (id,name, price, create_time, update_time) values (nextval('s_coffee_seq'),'espresso', 2000, now(), now());
insert into t_coffee (id,name, price, create_time, update_time) values (nextval('s_coffee_seq'),'latte', 2500, now(), now());
insert into t_coffee (id,name, price, create_time, update_time) values (nextval('s_coffee_seq'),'capuccino', 2500, now(), now());
insert into t_coffee (id,name, price, create_time, update_time) values (nextval('s_coffee_seq'),'mocha', 3000, now(), now());
insert into t_coffee (id,name, price, create_time, update_time) values (nextval('s_coffee_seq'),'macchiato', 3000, now(), now());