DROP TABLE IF EXISTS News;

DROP TABLE IF EXISTS Pays;
DROP TABLE IF EXISTS Devise;


CREATE TABLE News(
	id BIGINT auto_increment,
	title VARCHAR(64),
	text VARCHAR(64),
	pub_date VARCHAR(64),
	PRIMARY KEY(id));
	
INSERT INTO News (title,text,pub_date) VALUES ('news_1','text of news1' , '2023-10-06');
INSERT INTO News (title,text,pub_date) VALUES ('news_2','text of news2' , '2023-10-07');
INSERT INTO News (title,text,pub_date) VALUES ('news_3','text of news3' , '2023-10-07');

CREATE TABLE Devise(
	code VARCHAR(8),
	nom VARCHAR(64),
	d_change double,
	PRIMARY KEY(code));	 

CREATE TABLE Pays(
	codePays VARCHAR(8),
	capitale VARCHAR(64),
	nomPays VARCHAR(64),
	ref_devise VARCHAR(64),
	PRIMARY KEY(codePays));	 


ALTER TABLE Pays ADD CONSTRAINT Pays_avec_devise_valide 
FOREIGN KEY (ref_devise) REFERENCES Devise(code);

INSERT INTO Devise (code,d_change,nom) VALUES ('EUR',1.11570,'euro');
INSERT INTO Devise (code,d_change,nom) VALUES ('JPY',0.00961816 ,'yen');
INSERT INTO Devise (code,d_change,nom) VALUES ('USD',1.0,'dollar');
INSERT INTO Devise (code,d_change,nom) VALUES ('GBP',1.32940,'livre');

INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Paris','fr','France','EUR');
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Berlin','de','Allemagne','EUR');
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Rome','it','Italie','EUR');      
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Londres','uk','Royaumes unis','GBP');           
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Washingtown','us','USA','USD');     
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Pekin','china','Chine','USD');         
 INSERT INTO Pays (capitale,codePays,nomPays,ref_devise)
               VALUES ('Tokyo','JP','Japon','JPY');                 

SELECT * FROM News;

SELECT * FROM Devise;
SELECT * FROM Pays;





