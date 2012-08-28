use ba_db;

drop table if exists ENTITY;
create table ENTITY(
	ID int primary key not null auto_increment,
	ENTITY_TYPE_ID int not null,
	ENTITY_ID int not null,
	ENTITY_ATTRS longtext not null,
	ENTITY_HASH tinytext not null,
    WATCH_DATE timestamp not null
);

drop table if exists ENTITY_TYPE;
create table ENTITY_TYPE(
    TYPE_ID int not null,
    TYPE_NAME text not null,
    primary key(TYPE_ID)
);
