drop database if exists todosocial;
CREATE database todosocial;
use todosocial;
drop table if exists rating;
create table rating(
	id int primary key auto_increment,
    ratingvalue int not null,
    todoid varchar(50) not null,
    clientid varchar(50) not null,
    createdat timestamp not null
);

drop table if exists reviews;
create table reviews(
	id varchar(50) primary key ,
    fecha timestamp,
    comentario varchar(200),
    clientid varchar(50),
    imageid int
    );
    
drop user if exists 'todosocialuser'@'localhost';
create user 'todosocialuser'@'localhost' identified by 'todosocialpass';
GRANT ALL PRIVILEGES ON * . * TO 'todosocialuser'@'localhost';

drop PROCEDURE if exists calculated_rating_avg;
DELIMITER $$
create PROCEDURE calculated_rating_avg(in todoidParam varchar(50))
begin
select avg(ratingvalue) as ratingAvg from rating where todoid=todoidParam;
end$$
DELIMITER ;

drop PROCEDURE if exists add_rating;
DELIMITER $$
create PROCEDURE add_rating(in ratingvalueParam int,in todoidParam varchar(50),in clienteidParam varchar(50), 
					in createdatParam date)
begin
start transaction;
insert into rating(ratingvalue,todoid,clientid,createdat) values(ratingvalueParam ,todoidParam,clienteidParam,createdatParam);
commit;
end$$
DELIMITER ;

drop PROCEDURE if exists delete_rating;
DELIMITER $$
create PROCEDURE delete_rating(in todoidParam varchar(50) )
begin
start transaction;
delete from rating where todoid=todoidParam;
commit;
end$$
DELIMITER ;

drop procedure if exists get_all;
DELIMITER $$
create PROCEDURE get_all(in todo_id varchar(50))
begin
select *  from reviews where id=todo_id;
end$$
DELIMITER ;


drop PROCEDURE if exists add_review;
DELIMITER $$
create PROCEDURE add_review(in idParam varchar(50),in fechaParam timestamp,in comentarioParam varchar(50),in clienteidParam varchar(50))
begin
start transaction;
insert into reviews (id,fecha,comentario,clientid) values(idParam ,fechaParam,comentarioParam,clienteidParam);
commit;
end$$
DELIMITER ;


drop PROCEDURE if exists delete_review;
DELIMITER $$
create PROCEDURE delete_review(in todoidParam varchar(50) )
begin
start transaction;
delete from reviews where id=todoidParam;
commit;
end$$
DELIMITER ;

drop PROCEDURE if exists update_review;
DELIMITER $$
create PROCEDURE update_review(in todoId varchar(50) ,in  createdAtParam Date ,in reviewTexParam varchar(200) )
begin
start transaction;
UPDATE reviews
SET fecha= createdAtParam,  comentario=reviewTexParam
WHERE id=todoId; 
commit;
end$$
DELIMITER ;

insert into rating(ratingvalue,todoid,clientid,createdat) values(4,"todo-id","client-id",now());
insert into rating(ratingvalue,todoid,clientid,createdat) values(4,"todo-id","client-id",now());
insert into rating(ratingvalue,todoid,clientid,createdat) values(5,"todo-id","client-id",now());
insert into rating(ratingvalue,todoid,clientid,createdat) values(6,"123","client-id",now());

insert into reviews(id,fecha,comentario,clientid) values("todo-id",now(),"comentario","client-id");

select *  from reviews;
select * from rating;
call calculated_rating_avg();
