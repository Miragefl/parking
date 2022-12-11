drop table if exists parking;
create table parking
(
    id              int(11) auto_increment comment '主键',
    parking_code    varchar(32) not null comment '停车场编号',
    parking_name    varchar(64) not null comment '停车场名称',
    parking_lot_num int(5)      not null comment '停车场泊位数',
    is_del          int(1)      not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time     datetime             default current_timestamp comment '创建时间',
    update_time     datetime             default current_timestamp comment '更新时间',
    primary key (id)
) comment '停车场基本信息表';

drop table if exists parking_region;
create table parking_region
(
    id                 int(11) auto_increment comment '主键',
    region_code        varchar(32) not null comment '停车场区域编号',
    region_name        varchar(64) not null comment '停车场区域名称',
    parent_region_code varchar(32) not null comment '父停车场区域编号',
    parking_code       varchar(32) not null comment '停车场编号',
    region_lot_num     int(5)      not null comment '停车场泊位数',
    is_del             int(1)      not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time        datetime             default current_timestamp comment '创建时间',
    update_time        datetime             default current_timestamp comment '更新时间',
    primary key (id)
) comment '停车区域表';

drop table if exists parking_gateway;
create table parking_gateway
(
    id           int(11) auto_increment comment '主键',
    parking_code varchar(32) not null comment '停车场编号',
    gateway_code varchar(32) not null comment '通道编号',
    gateway_name varchar(64) not null comment '通道名称',
    gateway_type int(1)      not null comment '通道类型 1：入口 2：出口',
    is_del       int(1)      not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time  datetime             default current_timestamp comment '创建时间',
    update_time  datetime             default current_timestamp comment '更新时间',
    primary key (id)
) comment '停车场通道表';

drop table if exists dict_code;
create table dict_code
(
    code_key    varchar(32) not null comment '字典KEY',
    code_name   varchar(32) not null comment '字典值名称',
    code_value  varchar(64) not null comment '字典值',
    is_valid    int(1)      not null default 0 comment '有效标识 0: 有效 1：无效',
    is_del      int(1)      not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time datetime             default current_timestamp comment '创建时间',
    update_time datetime             default current_timestamp comment '更新时间'
) comment '系统字典值';

drop table if exists parking_configuration;
create table parking_configuration
(
    id           int(11) auto_increment comment '主键',
    parking_code varchar(32) comment '停车场编号',
    region_code  varchar(32) comment '停车场区域编号',
    config_key   varchar(32) comment '配置项key',
    config_name  varchar(64) comment '配置项名称',
    config_value varchar(32) comment '具体配置项',
    is_del       int(1) not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time  datetime        default current_timestamp comment '创建时间',
    update_time  datetime        default current_timestamp comment '更新时间',
    primary key (id),
    unique key (config_key)
) comment '停车场配置表';

drop table if exists parking_hardware;
create table parking_hardware
(
    id           int(11) auto_increment comment '主键',
    hw_code      varchar(32) not null comment '硬件设备编号',
    hw_name      varchar(32) comment '硬件名称',
    parking_code varchar(32) comment '停车场编号',
    gateway_code varchar(32) comment '通道编号',
    gateway_type int(1)      not null comment '通道类型 1：入口 2：出口',
    hw_type      int(1)      not null comment '设备类型 LED，CAMERA',
    hw_param     json        not null comment '设备配置参数',
    brand        varchar(32) comment '设备品牌',
    is_del       int(1)      not null default 0 comment '删除标识 0：未删除 1：已删除',
    create_time  datetime             default current_timestamp comment '创建时间',
    update_time  datetime             default current_timestamp comment '更新时间',
    primary key (id)
) comment '停车场硬件设备表';


drop table if exists identify_log;
create table identify_log
(
    id                int(11) auto_increment comment '主键',
    log_serial_no     varchar(32) not null comment '识别日志唯一编号',
    parking_serial_no varchar(32) not null comment '停车流水编号',
    parking_code      varchar(32) not null comment '停车场编号',
    parking_name      varchar(64) comment '停车场名称',
    region_code       varchar(32) comment '停车场区域编号',
    gateway_code      varchar(32) comment '通道编号',
    gateway_type      int(1)      not null comment '通道类型 1：入口 2：出口',
    car_plate_no      varchar(10) not null comment '车牌号',
    car_plate_color   varchar(16) not null comment '车牌颜色',
    parking_time      datetime    not null comment '停车时间',
    log_type          tinyint(4)  not null default 1 comment '记录类型 1:识别记录 2：流水记录',
    create_time       datetime             default current_timestamp comment '创建时间',
    update_time       datetime             default current_timestamp comment '更新时间',
    primary key (id)
) comment '通道记录表';
