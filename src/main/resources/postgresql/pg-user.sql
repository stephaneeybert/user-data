create user useraccount with encrypted password 'mypassword';
alter user useraccount with superuser;

revoke connect on database useraccount from public;
grant connect on database useraccount to useraccount;
grant all privileges on database useraccount to useraccount;
grant all privileges on all tables in schema public to useraccount;
grant all privileges on all sequences in schema public to useraccount;
revoke connect on database useraccounttest from public;
grant connect on database useraccounttest to useraccount;
grant all privileges on database useraccounttest to useraccount;
grant all privileges on all tables in schema public to useraccount;
grant all privileges on all sequences in schema public to useraccount;
