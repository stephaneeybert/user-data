use mysql;
grant all privileges on useraccount.* to useraccount@'127.0.0.1' identified by 'mypassword';
grant all privileges on useraccounttest.* to useraccount@'127.0.0.1' identified by 'mypassword';
flush privileges;

