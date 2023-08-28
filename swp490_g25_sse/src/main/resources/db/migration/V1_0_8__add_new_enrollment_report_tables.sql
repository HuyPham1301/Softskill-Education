    create table test_results (
       id bigint identity not null,
        isFinished bit,
        mark int,
        enrollment_id bigint,
        test_id bigint,
        primary key (id)
    );

    create table lecture_results (
       id bigint identity not null,
        isFinished bit,
        enrollment_id bigint,
        lecture_id bigint,
        primary key (id)
    );

    alter table lecture_results 
       add constraint FK_lecture_results_student_course_enrollments 
       foreign key (enrollment_id) 
       references student_course_enrollments;

    alter table lecture_results 
       add constraint FK_lecture_results_lectures 
       foreign key (lecture_id) 
       references lectures;

    alter table test_results 
       add constraint FK_test_results_student_course_enrollments
       foreign key (enrollment_id) 
       references student_course_enrollments;

    alter table test_results 
       add constraint FK_test_results_tests
       foreign key (test_id) 
       references tests;
