create table english_dictionary
(
    sorted_word varchar(255),
    word        varchar(255) not null
        constraint english_dictionary_word_pk primary key
);

create table polish_dictionary
(
    sorted_word varchar(255),
    word        varchar(255) not null
        constraint word_pk
            primary key
);
