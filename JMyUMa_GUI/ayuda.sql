drop user monty@localhost

select * from mysql.user

CREATE USER 'admin'@'localhost' IDENTIFIED BY '07091988'

SHOW GRANTS for 'admin'@'localhost'

GRANT CREATE,DELETE ON *.* TO 'monty'@'localhost' ;

GRANT USAGE ON *.* TO 'dummy'@'localhost'