    create table roles (
       id bigint identity not null,
        name varchar(255),
        primary key (id)
    );

    create table users (
       id bigint identity not null,
        email varchar(255),
        first_name nvarchar(255),
        last_name nvarchar(255),
        password varchar(255),
        primary key (id)
    );

    create table users_roles (
       user_id bigint not null,
        role_id bigint not null
    );

    alter table users 
       add constraint FK_users_email unique (email);

    alter table users_roles 
       add constraint FK_users_roles_roles 
       foreign key (role_id) 
       references roles ON DELETE CASCADE;

    alter table users_roles 
       add constraint FK_users_roles_users 
       foreign key (user_id) 
       references users ON DELETE CASCADE;

    INSERT INTO roles (name)
    VALUES ('ROLE_ADMIN');