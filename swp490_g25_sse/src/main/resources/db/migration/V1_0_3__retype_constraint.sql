
alter table tests 
       drop constraint FK_tests_week_index_order;
      
alter table tests 
       drop constraint FK_tests_week_name;
      
alter table lectures 
       drop constraint FK_lectures_week_index_order;
      
alter table lectures 
       drop constraint FK_lectures_week_name;

alter table lectures 
       add constraint FK_lectures_courseId_week_index_order unique (course_id, week, index_order);

alter table lectures 
       add constraint FK_lectures_courseId_week_name unique (course_id, week, name);

alter table tests 
       add constraint FK_tests_courseId_week_index_order unique (course_id, week, index_order);

alter table tests 
       add constraint FK_tests_courseId_week_name unique (course_id, week, name);