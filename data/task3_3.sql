-- TODO Task 3

drop database if exists emart;

create database emart;

use emart;

create table order_details (

    order_id varchar(128),
    order_date date,
    name varchar(128),
    address varchar(128),
    priority boolean,
    comments text,

    primary key(order_id)
);

create table line_item (

    line_item_id int auto_increment,
    product_id varchar(128),
    name varchar(128),
    quantity int,
    price decimal(10,3),
    order_id varchar(128),

    primary key(line_item_id),
    constraint fk_order_id foreign key(order_id) references order_details(order_id)
);
