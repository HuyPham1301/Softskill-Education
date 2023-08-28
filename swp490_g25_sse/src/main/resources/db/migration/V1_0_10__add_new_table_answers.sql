
    create table answers (
       id bigint identity not null,
        content nvarchar(255),
        question_id bigint FOREIGN KEY REFERENCES questions(id),
        student_id bigint FOREIGN KEY REFERENCES students(id),
        primary key (id),

    );

    

   