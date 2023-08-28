
    create table courses (
       id bigint identity not null,
        content varchar(255),
        image_url varchar(255),
        title varchar(255),
        teacher_id bigint not null,
        primary key (id)
    );

    create table lectures (
       id bigint identity not null,
        content varchar(255),
        index_order bigint,
        name bigint,
        resource_url varchar(255),
        week bigint,
        course_id bigint not null,
        primary key (id)
    );

    create table teachers (
       id bigint identity not null,
        user_id bigint,
        primary key (id)
    );

    create table tests (
       id bigint identity not null,
        content varchar(255),
        index_order bigint,
        name bigint,
        week bigint,
        course_id bigint not null,
        primary key (id)
    );

    alter table lectures 
       add constraint FK_lectures_week_index_order unique (week, index_order);

    alter table lectures 
       add constraint FK_lectures_week_name unique (week, name);

    alter table teachers 
       add constraint FK_teachers_userId unique (user_id);

    alter table tests 
       add constraint FK_tests_week_index_order unique (week, index_order);

    alter table tests 
       add constraint FK_tests_week_name unique (week, name);

    alter table courses 
       add constraint FK_courses_teachers 
       foreign key (teacher_id) 
       references teachers;

    alter table lectures 
       add constraint FK_lectures_courses 
       foreign key (course_id) 
       references courses;

    alter table teachers 
       add constraint FK_teachers_users 
       foreign key (user_id) 
       references users ON DELETE CASCADE;

    alter table tests 
       add constraint FK_tests_courses 
       foreign key (course_id) 
       references courses;