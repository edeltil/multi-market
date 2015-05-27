INSERT INTO user_market(id,username,password) VALUES (1,'akemi','testMM')

/*Fait maison*/
INSERT INTO param_provider(id,key,value) VALUES (1,'user[username]','akemi');
INSERT INTO param_provider(id,key,value) VALUES (2,'user[password]','isbel1710');

INSERT INTO provider(id,isfb,name,user_name_id,password_id,user_id) VALUES (1,false,0,1,2,1);

INSERT INTO param_provider(id,key,value,provider_id) VALUES (3,'user[remember_me]','1',1);

/*Etsy*/
INSERT INTO param_provider(id,key,value) VALUES (4,'username','riku.creation@gmail.com');
INSERT INTO param_provider(id,key,value) VALUES (5,'password','isbel1710');

INSERT INTO provider(id,isfb,name,user_name_id,password_id,user_id) VALUES (2,false,2,4,5,1);

INSERT INTO param_provider(id,key,value,provider_id) VALUES (6,'persistent','1',2);

/*Da wanda*/
INSERT INTO param_provider(id,key,value) VALUES (7,'user[email_or_username]','riku.creation@gmail.com');
INSERT INTO param_provider(id,key,value) VALUES (8,'user[password]','isbel1710');

INSERT INTO provider(id,isfb,name,user_name_id,password_id,user_id) VALUES (3,false,1,7,8,1);

INSERT INTO param_provider(id,key,value,provider_id) VALUES (9,'user[remember_me]','true',3);