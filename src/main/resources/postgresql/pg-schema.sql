create sequence hibernate_sequence start with 1 increment by 10;

drop sequence if exists user_account_id_seq;
create sequence user_account_id_seq start with 1 increment by 10;
drop table if exists user_account;
create table user_account (
  -- id int generated by default as identity primary key,
  id bigint default nextval('user_account_id_seq') primary key,
  version int not null,
  created_on datetime,
  updated_on datetime,
  firstname varchar(255) not null,
  lastname varchar(255) not null,
  password varchar(100),
  password_salt varchar(50),
  readable_password varchar(50),
  email varchar(50) not null,
  confirmed_email boolean not null default false,
  work_phone varchar(20),
  constraint email unique (email)
);

drop sequence if exists user_role_id_seq;
create sequence user_role_id_seq start with 1 increment by 10;
drop table if exists user_role;
create table user_role (
  -- id int generated by default as identity primary key,
  id bigint default nextval('user_role_id_seq') primary key,
  version int not null,
  created_on datetime,
  updated_on datetime,
  user_account_id bigint not null,
  role varchar(50) not null,
  constraint user_role_u1 unique (user_account_id, role),
  constraint user_role_id unique (id),
  constraint user_role_fk1 foreign key (user_account_id) references user_account (id)
);
create index user_id on user_role (user_account_id);
