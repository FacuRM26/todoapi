drop database if exists todoapi;
create database todoapi;
use todoapi;

create table clients(
client_id varchar(255) primary key,
client_secret varchar(255) not null
);
drop table if exists sessions;
create table sessions(
session_id varchar(255) primary key,
client_id varchar(255) not null,
sessionStatus enum('ACTIVE','INACTIVE'),
foreign key(client_id) references clients(client_id),
createdat timestamp not null
);
drop user if exists authappuser;
create user 'authappuser' identified by 'authapppassword';

drop PROCEDURE if exists create_session;
DELIMITER $$
create PROCEDURE create_session(in client_id varchar(255),in session_ttl int)
begin
    declare client_id_exist boolean;
    declare session_exist boolean;
    declare session_diff int;

    SELECT if(Count(*)>0,true,false)as client_id_exist FROM clients WHERE client_id = client_id;
    if client_id_exist=true then
        SELECT if(count(*)>0,true,false)into session_exist  FROM sessions WHERE session_id = client_id;
        if session_exist=true then
            SELECT minute(timediff(utc_timestamp(),created_at)) into session_diff FROM sessions where client_id = client_id;
            if session_dif<=session_ttl then
                SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where client_id = client_id;
            else
                update sessions set created_at = utc_timestamp() where client_id = client_id;
                SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where client_id = client_id;
            end if;
        else
            insert into sessions(session_id,client_id,created_at) values(client_id,uuid(),utc_timestamp());
            SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where client_id = client_id;
            end if;
    else
        SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where client_id = client_id;
    end if;
end$$
DELIMITER ;

drop PROCEDURE if exists validate_session;
DELIMITER $$
create PROCEDURE validate_session(in psession_id int)
begin
    declare session_exist boolean;
    declare session_diff int;

    SELECT if(count(*)>0,true,false)into session_exist FROM sessions WHERE session_id = psession_id;
    if session_exist=true then
        SELECT minute(timediff(utc_timestamp(),created_at)) into session_diff FROM sessions where session_id = psession_id;
        if session_diff<=30 then
            SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where session_id = psession_id;
		end if;
    else
        SELECT * ,if(minute(timediff(utc_timestamp(),created_at))<=30,"Active","Inactive") as session_status FROM sessions where session_id = psession_id;
    end if;

end$$
DELIMITER ;

insert into clients(client_id,client_secret) values('1','testpass');
insert into clients(client_id,client_secret) values('2','testpass2');
insert into clients(client_id,client_secret) values('3','testpass3');
insert into clients(client_id,client_secret) values('4','testpass4');
insert into clients(client_id,client_secret) values('5','testpass5');

insert into sessions values(10,1,'ACTIVE',utc_timestamp());
insert into sessions values(20,2,'ACTIVE',utc_timestamp());
insert into sessions values(30,3,'ACTIVE',utc_timestamp());
insert into sessions values(40,4,'ACTIVE',utc_timestamp());
insert into sessions values(50,5,'ACTIVE',utc_timestamp());

select * from sessions;




