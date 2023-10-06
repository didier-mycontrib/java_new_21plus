CREATE TABLE IF NOT EXISTS News(
	id BIGINT auto_increment,
	title VARCHAR(64),
	text VARCHAR(64),
	pub_date VARCHAR(64),
	PRIMARY KEY(id));
	
	
CREATE TABLE IF NOT EXISTS Devise(
	code VARCHAR(8),
	nom VARCHAR(64),
	d_change double,
	PRIMARY KEY(code));	