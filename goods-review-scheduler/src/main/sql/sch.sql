use goodsreview;

drop table if exists TASK;
create table TASK(
	ID int primary key not null auto_increment,
	BEAN_NAME tinytext not null,
	PARAMS text,
	SCHEDULING_TYPE tinytext not null,
    SCHEDULING_TIME tinytext not null,
    LAST_RUN long,
    LAST_PING long
);
