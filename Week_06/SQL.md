create database if not exists shop_mall;

--用户表
create table if not exists users(
	user_id int(11) not null auto_increment comment '用户主键',
	user_name varchar(30) not null comment '用户名',
	password varchar(30) not null comment '密码',
	phone_number varchar(30) not null comment '手机号',
	email varchar(100) not null default '' comment '邮箱',
	gender varchar(2) not null default '' comment '性别',
	birthday bigint(64) null comment '生日',
	create_time bigint(64) not null comment '创建时间',
	update_time bigint(64) not null comment '修改时间',
	PRIMARY KEY(user_id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

--商品表
create table if not exists products(
	product_id int(11) not null auto_increment comment '商品主键',
	product_name varchar(50) not null comment '商品名称',
	product_price int(13) not null comment '价格',
	product_stock int(13) not null comment '库存',
	product_type varchar(30) not null default '' comment '类型',
	product_desc varchar(800) not null default '' comment '描述',
	product_img varchar(300) not null default '' comment '商品图片URL',
	create_time bigint(64) not null comment '创建时间',
	update_time bigint(64) not null comment '修改时间',
	PRIMARY KEY(product_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
create table if not exists orders(
	order_id int(11) not null auto_increment comment '订单主键',
	user_id int(11) not null comment '用户id',
	product_id int(11) not null comment '商品id',
	order_price int(13) not null comment '订单总金额',
	order_state int(1) not null comment '订单状态：1未支付 2已取消 3已退款 4已支付 5未发货 6已发货 7成功交易',
	pay_time bigint(64) null comment '支付时间',
	refund_time bigint(64) null comment '退款时间',
	create_time bigint(64) not null comment '创建时间',
	update_time bigint(64) not null comment '修改时间',
	PRIMARY KEY(order_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';