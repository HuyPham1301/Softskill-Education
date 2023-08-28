
    create table student_course_enrollments (
       id bigint identity not null,
        isFinished bit not null,
        course_id bigint not null,
        student_id bigint not null,
        primary key (id)
    );

    create table students (
       id bigint identity not null,
        user_id bigint,
        primary key (id)
    );

    alter table students 
       add constraint FK_students_userId unique (user_id);

    alter table student_course_enrollments 
       add constraint FK_student_course_enrollments_courses
       foreign key (course_id) 
       references courses;

    alter table student_course_enrollments 
       add constraint FK_student_course_enrollments_students 
       foreign key (student_id) 
       references students;

    alter table students 
       add constraint FK_students_users 
       foreign key (user_id) 
       references users ON DELETE CASCADE;