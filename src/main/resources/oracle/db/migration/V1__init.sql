create table user_account (
  id number(10) not null,
  version number(10) not null,
  firstname varchar2(255) not null,
  lastname varchar2(255) not null,
  password varchar2(100),
  password_salt varchar2(50),
  readable_password varchar2(50),
  email varchar2(255) not null,
  confirmed_email number(1) not null check (confirmed_email in (0, 1)),
  work_phone varchar2(20),
  constraint user_u1 unique (email),
  constraint user_pk primary key (id)
);
create sequence user_account_id_seq increment by 10 start with 1 nomaxvalue nocycle cache 10;
create or replace trigger tr_id_inc_user 
before insert 
on user_account
for each row
declare
begin
  if (:new.id is null)
  then
    select user_account_id_seq.nextval into :new.id from dual;
  end if;
end;
/

create table user_role (
  id number(10) not null,
  version number(10) not null,
  user_account_id number(10) not null,
  role varchar2(50) not null,
  constraint user_role_pk primary key (id),
  constraint user_role_u1 unique (user_account_id, role),
  constraint user_role_fk1 foreign key (user_account_id) references user_account (id)
);
create sequence user_role_id_seq increment by 10 start with 1 nomaxvalue nocycle cache 10;
create or replace trigger tr_id_inc_user_role 
before insert 
on user_role
for each row
declare
begin
  if (:new.id is null)
  then
    select user_role_id_seq.nextval into :new.id from dual;
  end if;
end;
/

quit;
