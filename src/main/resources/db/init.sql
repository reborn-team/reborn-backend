alter table article
drop
foreign key FK6l9vkfd5ixw8o8kph5rj1k7gu

alter table article_image
drop
foreign key FKt3rm1gwoysmll8kpy7lt1vpwc

alter table comment
drop
foreign key FK5yx0uphgjc6ik6hb82kkw501y

alter table comment
drop
foreign key FKmrrrpi513ssu63i2783jyiv9m

alter table comment
drop
foreign key FKde3rfu96lep00br5ov0mdieyt

alter table gym
drop
foreign key FK6yycr433ipvwi3y41mpmk1sk7

alter table my_workout
drop
foreign key FKcgrdo4mnqorh0s3secj8t6sgx

alter table my_workout
drop
foreign key FKpskvfk39v9j524ukv3kl7co4f

alter table record
drop
foreign key FKne9rbcmcgmn8a8oys42ow9inr

alter table workout
drop
foreign key FKjw2b7cejitb4sfno8yi21p98l

alter table workout_image
drop
foreign key FKai9p90frtg57y9acu670uurra


drop table if exists article

drop table if exists article_image

drop table if exists comment

drop table if exists gym

drop table if exists member
drop table if exists my_workout

drop table if exists record

drop table if exists workout

drop table if exists workout_image


create table article
(
    article_id    bigint  not null auto_increment,
    created_date  datetime(6),
    modified_date datetime(6),
    content       varchar(255),
    title         varchar(255),
    view_count    integer not null,
    member_id     bigint,
    primary key (article_id)
) engine=InnoDB

create table article_image
(
    id               bigint not null auto_increment,
    origin_file_name varchar(255),
    upload_file_name varchar(255),
    article_id       bigint,
    primary key (id)
) engine=InnoDB

create table comment
(
    comment_id    bigint not null auto_increment,
    created_date  datetime(6),
    modified_date datetime(6),
    content       varchar(255),
    article_id    bigint,
    member_id     bigint,
    parent_id     bigint,
    primary key (comment_id)
) engine=InnoDB

create table gym
(
    gym_id        bigint not null auto_increment,
    created_date  datetime(6),
    modified_date datetime(6),
    addr          varchar(255),
    lat           double precision,
    lng           double precision,
    place         varchar(255),
    member_id     bigint,
    primary key (gym_id)
) engine=InnoDB

create table member
(
    member_id      bigint       not null auto_increment,
    created_date   datetime(6),
    modified_date  datetime(6),
    detail_address varchar(255),
    road_name      varchar(255),
    zipcode        varchar(255),
    email          varchar(255) not null,
    member_role    varchar(255),
    nickname       varchar(255) not null,
    password       varchar(255) not null,
    phone          varchar(255),
    primary key (member_id)
) engine=InnoDB

create table my_workout
(
    my_workout_id bigint not null auto_increment,
    created_date  datetime(6),
    modified_date datetime(6),
    member_id     bigint,
    workout_id    bigint,
    primary key (my_workout_id)
) engine=InnoDB

create table record
(
    record_id        bigint       not null auto_increment,
    created_date     datetime(6),
    modified_date    datetime(6),
    total            bigint       not null,
    workout_category varchar(255) not null,
    my_workout_id    bigint,
    primary key (record_id)
) engine=InnoDB

create table workout
(
    workout_id       bigint       not null auto_increment,
    created_date     datetime(6),
    modified_date    datetime(6),
    content          varchar(255) not null,
    workout_category varchar(255) not null,
    workout_name     varchar(255) not null,
    member_id        bigint,
    primary key (workout_id)
) engine=InnoDB

create table workout_image
(
    workout_image_id bigint       not null auto_increment,
    origin_file_name varchar(255) not null,
    upload_file_name varchar(255) not null,
    workout_id       bigint,
    primary key (workout_image_id)
) engine=InnoDB


alter table gym
    add constraint UK_e7njffpdst9ctx0omajx5jnbt unique (place)

alter table member
    add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email)


alter table member
    add constraint UK_hh9kg6jti4n1eoiertn2k6qsc unique (nickname)


alter table article
    add constraint FK6l9vkfd5ixw8o8kph5rj1k7gu
        foreign key (member_id)
            references member (member_id)

alter table article_image
    add constraint FKt3rm1gwoysmll8kpy7lt1vpwc
        foreign key (article_id)
            references article (article_id)

alter table comment
    add constraint FK5yx0uphgjc6ik6hb82kkw501y
        foreign key (article_id)
            references article (article_id)

alter table comment
    add constraint FKmrrrpi513ssu63i2783jyiv9m
        foreign key (member_id)
            references member (member_id)

alter table comment
    add constraint FKde3rfu96lep00br5ov0mdieyt
        foreign key (parent_id)
            references comment (comment_id)

alter table gym
    add constraint FK6yycr433ipvwi3y41mpmk1sk7
        foreign key (member_id)
            references member (member_id)

alter table my_workout
    add constraint FKcgrdo4mnqorh0s3secj8t6sgx
        foreign key (member_id)
            references member (member_id)

alter table my_workout
    add constraint FKpskvfk39v9j524ukv3kl7co4f
        foreign key (workout_id)
            references workout (workout_id)

alter table record
    add constraint FKne9rbcmcgmn8a8oys42ow9inr
        foreign key (my_workout_id)
            references my_workout (my_workout_id)
            on delete cascade

alter table workout
    add constraint FKjw2b7cejitb4sfno8yi21p98l
        foreign key (member_id)
            references member (member_id)

alter table workout_image
    add constraint FKai9p90frtg57y9acu670uurra
        foreign key (workout_id)
            references workout (workout_id)