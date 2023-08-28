alter table tests 
       drop constraint FK_tests_week_index_order;
      
alter table tests 
       drop constraint FK_tests_week_name;
      
alter table lectures 
       drop constraint FK_lectures_week_index_order;
      
alter table lectures 
       drop constraint FK_lectures_week_name;

ALTER TABLE lectures
ALTER COLUMN name varchar(255);

ALTER TABLE tests
ALTER COLUMN week varchar(255);

ALTER TABLE lectures
ALTER COLUMN week varchar(255);

ALTER TABLE tests
ALTER COLUMN name varchar(255);

 alter table lectures 
       add constraint FK_lectures_week_index_order unique (week, index_order);

 alter table lectures 
       add constraint FK_lectures_week_name unique (week, name);

 alter table tests 
       add constraint FK_tests_week_index_order unique (week, index_order);

 alter table tests 
       add constraint FK_tests_week_name unique (week, name);