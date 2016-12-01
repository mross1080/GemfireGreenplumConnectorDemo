


drop table if exists customer;
create table customer (	
	id	int,
	name text,
	income	float4
) distributed by (id);		
 
INSERT INTO customer VALUES (199, 'Alice',  123456.0);
INSERT INTO customer VALUES (274, 'Bob',  250000.0);
INSERT INTO customer VALUES (444, 'Charlie',  245457.0);
INSERT INTO customer VALUES (597, 'Dana',  345678.0);
INSERT INTO customer VALUES (622, 'Elle',  255000.0);
INSERT INTO customer VALUES (12, 'Sty',  123456.0);
INSERT INTO customer VALUES (22, 'hardy',  250000.0);
INSERT INTO customer VALUES (564, 'Charliemon',  245457.0);
INSERT INTO customer VALUES (55, 'Danabab',  345678.0);
INSERT INTO customer VALUES (67, 'Elleaa',  255000.0);

select * from customer limit 4;




