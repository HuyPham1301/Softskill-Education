
    create table questions (
       id bigint identity not null,
        title nvarchar(255),
        tag nvarchar(255),
        content nvarchar(255),        
        student_id bigint FOREIGN KEY REFERENCES students(id),
        primary key (id),

    );

   