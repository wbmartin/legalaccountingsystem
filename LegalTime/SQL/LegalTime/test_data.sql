insert into user_info(user_id, client_id, default_bill_rate, display_name, email_addr) 
  values('w.brandon.martin@gmail.com', 1,300,'Brandon','w.brandon.martin@gmail.com');
insert into user_info(user_id, client_id, default_bill_rate, display_name, email_addr) 
  values('bmartin', 1,300,'Brandon','w.brandon.martin@gmail.com');
insert into user_info(user_id, client_id, default_bill_rate, display_name, email_addr) 
  values('pmartin', 1,300,'Patrick','pmartin@erskine.edu');
insert into user_info(user_id, client_id, default_bill_rate, display_name, email_addr) 
  values('bryan', 1,300,'Bryan','byran@bogerlaw.com');

  
  select * from customer_bill_rate
  
INSERT INTO customer_bill_rate(  client_id, customer_id, user_id,  bill_rate)
    VALUES (1, 6, 'bmartin', 300);
INSERT INTO customer_bill_rate(  client_id, customer_id, user_id,  bill_rate)
    VALUES (1, 6, 'pmartin', 400);
INSERT INTO customer_bill_rate(  client_id, customer_id, user_id,  bill_rate)
    VALUES (1, 6, 'bryan', 300);  
  
  
update customer set last_update = now();
update customer set bill_type = 'HOURLY';
update customer set active_yn ='Y'
