alter table feedbacks 
       drop constraint FK_feedbacks_courses;
      
alter table feedbacks 
       drop constraint FK_feedbacks_students;

alter table feedbacks
       alter column content nvarchar(max);

alter table feedbacks
       alter column contentKey nvarchar(max);

alter table feedbacks add constraint FK_feedbacks_courses 
        foreign key (course_id) references courses;

alter table feedbacks add constraint FK_feedbacks_students 
        foreign key (student_id) references students;
