DELETE FROM News;
INSERT INTO News (id,title,text,pub_date) VALUES (1,'news_1','text of news1' , '2023-10-06');
INSERT INTO News (id,title,text,pub_date) VALUES (2,'news_2','text of news2' , '2023-10-07');
INSERT INTO News (id,title,text,pub_date) VALUES (3,'news_3','text of news3' , '2023-10-07');

DELETE FROM Devise;
INSERT INTO Devise (code,d_change,nom) VALUES ('EUR',1.11570,'euro');
INSERT INTO Devise (code,d_change,nom) VALUES ('JPY',0.00961816 ,'yen');
INSERT INTO Devise (code,d_change,nom) VALUES ('USD',1.0,'dollar');
INSERT INTO Devise (code,d_change,nom) VALUES ('GBP',1.32940,'livre');