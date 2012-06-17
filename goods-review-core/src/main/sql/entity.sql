use goodsreview;

drop table if exists ENTITY
create table ENTITY(
	ID primary key not null auto_increment,
	ENTITY_TYPE_ID int not null,
	ENTITY_ID int not null,
	ENTITY_ATTRS longtext not null,
	ENTITY_HASH tinytext not null,
    WATCH_DATE timestamp not null
);

create table ENTITY_TYPE(
    TYPE_ID primary key not null auto_increment,
    TYPE_NAME text not null
);