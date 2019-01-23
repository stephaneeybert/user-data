set foreign_key_checks = 0;

drop table if exists user_account_id_seq;
create sequence user_account_id_seq start with 1 increment by 10;
drop table if exists user_account;
create table user_account (
  -- id bigint(20) unsigned not null auto_increment,
  id bigint(20) unsigned not null default (next value for user_account_id_seq),
  version int(10) unsigned not null,
  created_on datetime,
  updated_on datetime,
  firstname varchar(255) not null,
  lastname varchar(255) not null,
  password varchar(100),
  password_salt varchar(50),
  readable_password varchar(50),
  email varchar(50) not null,
  confirmed_email bit(1) not null check (confirmed_email in (0, 1)),
  work_phone varchar(20),
  unique key email (email),
  primary key (id)
);

drop table if exists user_role_id_seq;
create sequence user_role_id_seq start with 1 increment by 10;
drop table if exists user_role;
create table user_role (
  -- id bigint(20) unsigned not null auto_increment,
  id bigint(20) unsigned not null default (next value for user_role_id_seq),
  version int(10) unsigned not null,
  created_on datetime,
  updated_on datetime,
  user_account_id bigint(20) unsigned not null,
  role varchar(50) not null,
  key user_account_id (user_account_id),
  unique key user_role_u1 (user_account_id, role),
  constraint user_role_fk1 foreign key (user_account_id) references user_account (id),
  primary key (id)
);
