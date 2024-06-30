delete from lecture_history;
delete from lecture_schedule;
delete from lecture;

alter table lecture alter column id restart with 1;
alter table lecture_schedule alter column id restart with 1;
alter table lecture_history alter column id restart with 1;

insert into lecture(lecture_name, create_datetime)
values ('항해 플러스 백엔드', '2024-06-25T12:00:00'),
       ('항해 플러스 프론트엔드', '2024-06-25T12:00:00');

insert into lecture_schedule(lecture_id, lecture_datetime, register_cnt, capacity, create_datetime)
values (1, '2024-06-25T13:00:00', 0, 30, '2024-06-25T12:00:00'),
       (1, '2024-06-26T13:00:00', 0, 10, '2024-06-25T12:00:00'),
       (1, '2024-06-27T13:00:00', 0, 30, '2024-06-25T12:00:00'),
       (1, '2024-06-28T13:00:00', 0, 5, '2024-06-25T12:00:00'),
       (2, '2024-06-27T13:00:00', 0, 30, '2024-06-25T12:00:00'),
       (2, '2024-06-28T13:00:00', 0, 5, '2024-06-25T12:00:00');
