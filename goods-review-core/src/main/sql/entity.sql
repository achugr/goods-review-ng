use goodsreview

drop table if exists ENTITY
create table ENTITY(
	ENTITY_ID primary key not null auto_increment,
	ENTITY_TYPE_ID int not null,
	ENTITY_ATTRS longtext not null
);

create table ENTITY_AUX(
    AUX_ID primary key not null auto_increment,
    ENTITY_ID int not null,
    ENTITY_HASH tinytext not null,
    ENTITY_URL tinytext not null
);