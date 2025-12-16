create table tags
(
    tag_id int auto_increment
        primary key,
    name   varchar(50) not null
);

create table users
(
    user_id    int auto_increment
        primary key,
    first_name varchar(32)                            not null,
    last_name  varchar(32)                            not null,
    username   varchar(50)                            not null,
    email      varchar(100)                           not null,
    password   varchar(100)                           not null,
    is_admin   tinyint(1) default 0                   not null,
    created_at timestamp  default current_timestamp() not null,
    phone      varchar(32)                            null,
    avatar_url varchar(512)                           null,
    is_blocked tinyint(1) default 0                   not null,
    is_deleted tinyint(1) default 0                   not null,
    constraint users_email_uq
        unique (email),
    constraint users_pk_2
        unique (username),
    constraint chk_email_format
        check (trim(`email`) regexp '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    constraint chk_first_name_length
        check (char_length(`first_name`) between 4 and 32),
    constraint chk_last_name_length
        check (char_length(`last_name`) between 4 and 32),
    constraint chk_username_character_length_type
        check (`username` regexp '^[A-Za-z][A-Za-z0-9._-]{3,49}$')
);

create table posts
(
    post_id    int auto_increment
        primary key,
    user_id    int                                    not null,
    title      varchar(64)                            not null,
    content    text                                   not null,
    created_at timestamp  default current_timestamp() not null,
    updated_at datetime                               null,
    deleted_at datetime                               null,
    is_deleted tinyint(1) default 0                   not null,
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint chk_post_content_length
        check (char_length(`content`) between 32 and 8192),
    constraint chk_post_title_length
        check (char_length(`title`) between 16 and 64)
);

create table comments
(
    comment_id int auto_increment
        primary key,
    post_id    int                                    not null,
    user_id    int                                    not null,
    content    text                                   not null,
    created_at timestamp  default current_timestamp() not null,
    updated_at datetime                               null,
    deleted_at datetime                               null,
    is_deleted tinyint(1) default 0                   not null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint chk_comment_content_length
        check (char_length(`content`) between 1 and 8192)
);

create table likes
(
    user_id int not null,
    post_id int not null,
    primary key (user_id, post_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint posts_users_posts_post_id__fk
        foreign key (post_id) references posts (post_id)
);

create table tags_posts
(
    tag_id  int not null,
    post_id int not null,
    primary key (tag_id, post_id),
    constraint tags_posts__tag_id_fk
        foreign key (tag_id) references tags (tag_id),
    constraint tags_posts_posts_post_id_fk
        foreign key (post_id) references posts (post_id)
);

