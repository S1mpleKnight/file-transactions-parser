INSERT INTO role (id, name)
VALUES
    (1,'admin'),
    (2,'user');

INSERT INTO operator (id,first_name, last_name, nickname, password, role_id)
VALUES
    (1,'Petya', 'Hechiparenko', 'krendel', 'q1w2e3r4t5y6', 2),
    (2,'Dasha', 'Deshina', 'deshda', '123qwe456rty', 2),
    (3,'Kostya', 'Shatko', 'mashina', 'asdqwe123654', 2),
    (4,'Misha', 'Prostak', 'genius', '654321ytrewq', 2),
    (5,'Boss', 'Galaxy', 'admin', 'zmpqksjdp', 1);

INSERT INTO transaction
(date_time,id,customer_id,amount,currency,status,operator_id)
VALUES
    (FROM_UNIXTIME(1619674154),'1c22f114-8251-404e-8c3f-73b70bd0ec80','a00a7fb0-3a72-454d-865d-8f6818f8dd62',34623,'usd',true, 1),
    (FROM_UNIXTIME(1619676213),'b54ca174-03f1-4a87-a1f2-dee732a8b754','4289aa66-720d-498f-979a-b9ccda3415e9',124,'usd',true, 2),
    (FROM_UNIXTIME(1619677426),'7499c7f2-c8e6-4ea2-8a02-e5e2bf5a21c7','e25f33a2-3e95-4d44-80e9-e4829bb7b5d3',942,'usd',false,1),
    (FROM_UNIXTIME(1619678337),'16fd2730-5e69-481b-95c1-28cc9d05cb94','854b378f-2681-4cbb-aa52-8383c52b61e6',7864,'usd',true,1),
    (FROM_UNIXTIME(1619676253),'b54ca174-03f1-4a87-a1f2-dee732a8b755','4289aa66-720d-498f-979a-b9ccda3415e0',1024,'usd',true,3),
    (FROM_UNIXTIME(1619676361),'fb3e82e1-8608-41cd-9148-b604f4f1c5a6','bebb0888-2a6f-4f38-b8e7-800a2a3145e7',245843,'usd',true,4),
    (FROM_UNIXTIME(1619678426),'7499c7f2-c8e6-4ea2-8a02-e5e2bf5a21c8','e25f33a2-3e95-4d44-80e9-e4829bb7b5d2',4942,'usd',false,4);