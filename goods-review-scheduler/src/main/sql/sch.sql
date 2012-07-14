USE GOODSREVIEW;

DROP TABLE IF EXISTS TASK;
CREATE TABLE TASK(
	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	BEAN_NAME TINYTEXT NOT NULL,
	PARAMS TEXT NOT NULL,
	SCHEDULING_TYPE TINYTEXT NOT NULL,
    SCHEDULING_TIME INT NOT NULL,
    LAST_RUN INT NOT NULL,
    LAST_PING INT NOT NULL,
    SCHEDULER_NAME TEXT
);

DROP TABLE IF EXISTS TASK_JOURNAL;
CREATE TABLE TASK_JOURNAL(
  	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  	TASK_ID INT  NOT NULL,
  	STATUS TINYTEXT NOT NULL,
    MESSAGE TEXT NOT NULL,
    TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE TASK_RESULT ADD CONSTRAINT FK_TASK_ID
FOREIGN KEY (TASK_ID)
REFERENCES TASK(ID)
ON UPDATE CASCADE ON DELETE RESTRICT;
