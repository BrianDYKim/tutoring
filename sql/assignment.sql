create table students
(
    id         bigint(20) auto_increment primary key,
    email      varchar(40)  not null,
    password   varchar(150) not null,
    nickname   varchar(40)  not null,
    created_at datetime     not null default now(),
    updated_at datetime     null,
    deleted_at datetime     null,
    is_deleted tinyint(1)   not null default 0
);

insert into students (email, password, nickname)
values ('student1@example.com', 'password1', '학생1');

create table courses
(
    id                 bigint(20) auto_increment primary key,
    language           ENUM ('CHINESE', 'ENGLISH') not null,
    is_voice_serves    tinyint(1)                  not null default 0,
    is_chatting_serves tinyint(1)                  not null default 0,
    is_video_serves    tinyint(1)                  not null default 0,
    course_duration    int                         not null,
    lesson_time        int                         not null,
    lessons_count      int                         not null,
    price              int                         not null,
    selling_start_date date                        not null,
    selling_end_date   date                        not null,
    is_selling         tinyint(1)                  not null default 1,
    created_at         datetime                    not null default now(),
    updated_at         datetime                    null,
    deleted_at         datetime                    null,
    is_deleted         tinyint(1)                  not null default 0
);

# courses의 index 생성
alter table `courses`
    add index `is_selling_idx` (`is_selling` asc) visible;

alter table `courses`
    add index `selling_date_idx` (`selling_start_date`, `selling_end_date`) visible;

alter table `courses`
    add index `course_type_idx` (`is_chatting_serves`, `is_voice_serves`, `is_video_serves`) visible;

alter table `courses`
    add index `language_idx` (`language`) visible;

INSERT INTO courses (language, is_voice_serves, is_chatting_serves, is_video_serves, course_duration, lesson_time,
                     lessons_count, price, selling_start_date, selling_end_date, is_selling)
VALUES ('CHINESE', 1, 1, 1, 6, 15, 90, 820000, '2023-06-01', '2023-07-15', 1),
       ('CHINESE', 0, 1, 1, 6, 10, 30, 750000, '2023-06-01', '2023-06-30', 1),
       ('CHINESE', 1, 0, 1, 3, 15, 90, 820000, '2023-06-15', '2023-07-15', 0),
       ('CHINESE', 1, 1, 0, 3, 10, 30, 650000, '2023-06-16', '2023-07-30', 1),
       ('CHINESE', 1, 0, 0, 6, 10, 30, 400000, '2023-07-01', '2023-07-30', 1),
       ('CHINESE', 0, 0, 1, 3, 15, 90, 820000, '2023-06-01', '2023-07-15', 1),
       ('CHINESE', 0, 1, 0, 6, 10, 30, 380000, '2023-06-01', '2023-08-01', 1),
       ('CHINESE', 1, 0, 0, 6, 15, 90, 500000, '2023-06-01', '2023-07-15', 1),
       ('CHINESE', 1, 0, 0, 6, 10, 30, 510000, '2023-06-01', '2023-06-30', 1),
       ('CHINESE', 1, 0, 0, 3, 15, 90, 520000, '2023-06-15', '2023-07-15', 0),
       ('CHINESE', 1, 0, 0, 3, 10, 30, 530000, '2023-06-16', '2023-07-30', 1),
       ('CHINESE', 1, 0, 0, 6, 10, 30, 540000, '2023-07-01', '2023-07-30', 1),
       ('CHINESE', 1, 0, 0, 3, 15, 90, 550000, '2023-06-01', '2023-07-15', 1),
       ('CHINESE', 0, 1, 0, 6, 10, 30, 560000, '2023-06-01', '2023-08-01', 1),
       ('ENGLISH', 1, 1, 1, 6, 15, 90, 820000, '2023-06-01', '2023-07-15', 1),
       ('ENGLISH', 0, 1, 1, 6, 10, 30, 750000, '2023-06-01', '2023-06-30', 1),
       ('ENGLISH', 1, 0, 1, 3, 15, 90, 820000, '2023-06-15', '2023-07-15', 0),
       ('ENGLISH', 1, 1, 0, 3, 10, 30, 650000, '2023-06-16', '2023-07-30', 1),
       ('ENGLISH', 1, 0, 0, 6, 10, 30, 400000, '2023-07-01', '2023-07-30', 1),
       ('ENGLISH', 0, 0, 1, 3, 15, 90, 820000, '2023-06-01', '2023-07-15', 1),
       ('ENGLISH', 0, 1, 0, 6, 10, 30, 380000, '2023-06-01', '2023-08-01', 1),
       ('ENGLISH', 1, 0, 0, 6, 15, 90, 500000, '2023-06-01', '2023-07-15', 1),
       ('ENGLISH', 1, 0, 0, 6, 10, 30, 510000, '2023-06-01', '2023-06-30', 1),
       ('ENGLISH', 1, 0, 0, 3, 15, 90, 520000, '2023-06-15', '2023-07-15', 0),
       ('ENGLISH', 1, 0, 0, 3, 10, 30, 530000, '2023-06-16', '2023-07-30', 1),
       ('ENGLISH', 1, 0, 0, 6, 10, 30, 540000, '2023-07-01', '2023-07-30', 1),
       ('ENGLISH', 1, 0, 0, 3, 15, 90, 550000, '2023-06-01', '2023-07-15', 1),
       ('ENGLISH', 0, 1, 0, 6, 10, 30, 560000, '2023-06-01', '2023-08-01', 1);


create table tutors
(
    id                    bigint(20) primary key auto_increment,
    email                 varchar(40)                 not null,
    password              varchar(150)                not null,
    nickname              varchar(40)                 not null,
    language              ENUM ('CHINESE', 'ENGLISH') not null,
    is_voice_available    tinyint(1)                  not null default 0,
    is_chatting_available tinyint(1)                  not null default 0,
    is_video_available    tinyint(1)                  not null default 0,
    unsettled_amount      decimal(20, 0)              not null default 0,
    created_at            datetime                    not null default now(),
    updated_at            datetime                    null,
    deleted_at            datetime                    null,
    is_deleted            tinyint(1)                  not null default 0
);

insert into tutors (email, password, nickname, language, is_voice_available, is_chatting_available, is_video_available)
values ('tutor1@example.com', 'password1', '튜터1', 'ENGLISH', 1, 1, 0),
       ('tutor2@example.com', 'password2', '튜터2', 'ENGLISH', 1, 0, 1),
       ('tutor3@example.com', 'password3', '튜터3', 'CHINESE', 1, 1, 0),
       ('tutor4@example.com', 'password4', '튜터4', 'CHINESE', 1, 0, 1);


create table lesson_subscriptions
(
    id                    bigint(20) primary key auto_increment,
    student_id            bigint(20)                  not null,
    course_id             bigint(20)                  not null,
    purchase_price        int                         not null,
    language              ENUM ('CHINESE', 'ENGLISH') not null,
    is_voice_available    tinyint(1)                  not null default 0,
    is_chatting_available tinyint(1)                  not null default 0,
    is_video_available    tinyint(1)                  not null default 0,
    start_date            date                        not null,
    end_date              date                        not null,
    lesson_total_count    int                         not null,
    lesson_left_count     int                         not null,
    created_at            datetime                    not null default now(),
    updated_at            datetime                    null,
    deleted_at            datetime                    null,
    is_deleted            tinyint(1)                  not null default 0
);

insert into lesson_subscriptions (student_id, course_id, language, purchase_price, is_voice_available,
                                  is_chatting_available,
                                  is_video_available,
                                  lesson_total_count,
                                  lesson_left_count, start_date, end_date)
values (1, 18, 'ENGLISH', 650000, 1, 1, 0, 90, 90, '2023-06-20', '2023-09-10'),
       (1, 18, 'ENGLISH', 650000, 1, 1, 0, 90, 90, '2023-03-10', '2023-06-20');

create table lessons
(
    id                     bigint(20) auto_increment primary key,
    student_id             bigint(20) not null,
    tutor_id               bigint(20) not null,
    lesson_subscription_id bigint(20) not null,
    started_at             datetime   not null,
    finished_at            datetime   null,
    tutor_revenue          int        not null,
    created_at             datetime   not null default now(),
    updated_at             datetime   null,
    deleted_at             datetime   null,
    is_deleted             tinyint(1) not null default 0
);