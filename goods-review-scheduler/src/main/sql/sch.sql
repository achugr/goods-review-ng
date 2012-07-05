use goodsreview;

drop table if exists task;
create table task(
	id int primary key not null auto_increment,
	bean_name tinytext not null,
	params text,
	scheduling_type tinytext not null,
    scheduling_time int not null,
    last_run long,
    last_ping long
);

//TODO мы должны хрнаить еще здесь номер таска (task.id)
drop table if exists task_result;
create table task_result(
  	id int primary key not null auto_increment,
  	status tinytext not null,
    message text not null
);
