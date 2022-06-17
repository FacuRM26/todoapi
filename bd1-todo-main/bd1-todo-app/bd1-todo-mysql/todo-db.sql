
-- Crear base de datos de Todo

drop database if exists todo;

create database todo;

use todo;

-- Crear tabla todorecord

create table todorecord(
                           tid varchar(50) primary key,
                           title varchar(200) not null,
                           description varchar(255),
    -- state varchar(100) not null default 'new',
                           state enum('new', 'in_progress', 'blocked', 'finished') not null,
                           startdate timestamp not null,
                           enddate timestamp
);


insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-1', 'Mi primer todo', 'este es mi primer todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-2', 'Mi segundo todo', 'este es mi segundo todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-3', 'Mi tercer todo', 'este es mi tercer todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-4', 'Mi cuarto todo', 'este es mi cuarto todo', 'new', now(), now());
insert into todorecord(tid, title, description, state, startdate, enddate) values ('todo-5', 'Mi quinto todo', 'este es mi quinto todo', 'blocked', now(), now());

insert into todorecord values ('todo-6', 'Mi quinto todo', 'este es mi quinto todo', 'blocked', now(), now());

-- CRUD
-- Create (inserción), Read (select), Update (update), Delete (delete)

	TRUNCATe TABLE todorecord;
SELECT tid, title, description, state, startdate, enddate FROM todorecord WHERE startdate in( '2022-04-27', '2022-04-30');
select tid, title, description, state, startdate, enddate from todorecord where title like '%todo%';
update todorecord SET title='todo-prueba1', description='mi update todo',state='new',startdate=now(),enddate=now() WHERE tid='todo-1';
SELECT  MIN(startdate)  AS "Total working hours" FROM todorecord;

