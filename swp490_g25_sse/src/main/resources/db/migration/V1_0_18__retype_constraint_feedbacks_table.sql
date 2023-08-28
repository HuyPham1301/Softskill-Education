alter table feedbacks 
       drop constraint FK_feedbacks_courses;
      
alter table feedbacks 
       drop constraint FK_feedbacks_students;

alter table feedbacks 
add constraint FK_feedbacks_courses 
foreign key (course_id) 
references courses ON DELETE CASCADE;

alter table feedbacks 
add constraint FK_feedbacks_students 
foreign key (student_id) 
references students ON DELETE CASCADE;

