insert into users (id, join_date, name, password, ssn ) values (90001, now(), 'User01', 'test111', '700000-1100101');
insert into users (id, join_date, name, password, ssn ) values (90002, now(), 'User02', 'test111', '800000-1100102');
insert into users (id, join_date, name, password, ssn ) values (90003, now(), 'User03', 'test111', '900000-1100103');

insert into post (description, user_id) values ('My first post', 90001);
insert into post (description, user_id) values ('My second post', 90001);