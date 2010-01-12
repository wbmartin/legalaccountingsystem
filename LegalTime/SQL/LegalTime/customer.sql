--This has been modified
--  * Added customer_invoice


-- Security Grants
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_CUSTOMER', now(), 'Allows users to select customer'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_CUSTOMER', now(), 'Allows users to add records to customer');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_CUSTOMER', now(), 'Allows users to update records in customer');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_CUSTOMER', now(), 'Allows users to delete records from customer');
select * from security_privilege where priv_name in ('SELECT_CUSTOMER','INSERT_CUSTOMER','UPDATE_CUSTOMER','DELETE_CUSTOMER');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 1);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 2);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 3);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 4);



-- Security Grants
GRANT ALL ON TABLE customer TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_CUSTOMER', now(), 'Allows users to select customer'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_CUSTOMER', now(), 'Allows users to add records to customer');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_CUSTOMER', now(), 'Allows users to update records in customer');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_CUSTOMER', now(), 'Allows users to delete records from customer');
select * from security_privilege where priv_name in ('SELECT_CUSTOMER','INSERT_CUSTOMER','UPDATE_CUSTOMER','DELETE_CUSTOMER');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_sq(text, integer, text, text)

-- DROP FUNCTION customer_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION customer_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF customer AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
    return query select * from customer where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION customer_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from customer_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_bypk(text, integer, text, text ,    integer)

-- DROP FUNCTION customer_pybk(text, integer, text, text ,    integer);

CREATE OR REPLACE FUNCTION customer_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  customerId_  integer)
  RETURNS customer AS
$BODY$
  Declare
    result customer;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
     select * into result from customer where   customer_id = customerId_  and client_id = clientId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION customer_bypk(text, integer, text, text,    integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_bypk(text, integer, text, text,    integer) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_iq(text, integer, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar)

-- DROP FUNCTION customer_iq( text, integer, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar));

create or replace function customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , contingencyRate_ float8, mortgageAmount_ float8, activeYn_ bpchar, monthlyBillRate_ float8, billType_ varchar, note_ text, clientSinceDt_ date, email_ varchar, fax_ varchar, homePhone_ varchar, workPhone_ varchar, zip_ varchar, state_ bpchar, city_ varchar, address_ text, lastName_ varchar, firstName_ varchar)
  returns customer as
$body$
--THIS PROCEDURE HAS BEEN CUSTOMIZED
--  * Adds default bill rates upon insert
  declare
    newrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMER' );
    end if;
    insert into customer(client_id , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update ) 
	values (clientid_ , contingencyRate_, mortgageAmount_, activeYn_, monthlyBillRate_, billType_, note_, clientSinceDt_, email_, fax_, homePhone_, workPhone_, zip_, state_, city_, address_, lastName_, firstName_, now()) 
	returning * into newrow;
	perform add_default_bill_rates_for_customer('ALREADY_AUTH', clientid_, userid_, sessionid_, newrow.customer_id);
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_iq(text, integer, text, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_iq(text, integer, text, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar) TO GROUP legaltime_full;

select * from customer_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 1
 ,'t'
 , 1
 , 'text' 
 , 'text' 
 , now()
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 ,'t'
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_uq(text, integer, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar, timestamp,  integer)

-- DROP FUNCTION customer_uq( text, integer, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar, timestamp,  integer);


create or replace function customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , contingencyRate_ float8, mortgageAmount_ float8, activeYn_ bpchar, monthlyBillRate_ float8, billType_ varchar, note_ text, clientSinceDt_ date, email_ varchar, fax_ varchar, homePhone_ varchar, workPhone_ varchar, zip_ varchar, state_ bpchar, city_ varchar, address_ text, lastName_ varchar, firstName_ varchar, lastUpdate_ timestamp, customerId_ integer)
  returns customer as
$body$
  declare
    updatedrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMER' );
    end if;
	update customer set
          contingency_rate = contingencyRate_ 
         , mortgage_amount = mortgageAmount_ 
         , active_yn = activeYn_ 
         , monthly_bill_rate = monthlyBillRate_ 
         , bill_type = billType_ 
         , note = note_ 
         , client_since_dt = clientSinceDt_ 
         , email = email_ 
         , fax = fax_ 
         , home_phone = homePhone_ 
         , work_phone = workPhone_ 
         , zip = zip_ 
         , state = state_ 
         , city = city_ 
         , address = address_ 
         , last_name = lastName_ 
         , first_name = firstName_ 
         , last_update = now()
                  
	where          
	   customer_id = customerId_          
	  and client_id = clientId_            
	   and   last_update = lastUpdate_
	returning * into updatedrow;

	if found then
	  return updatedrow;
	else 
	  raise exception 'Update Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_uq(text, integer, text, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar, timestamp,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_uq(text, integer, text, text , float8, float8, bpchar, float8, varchar, text, date, varchar, varchar, varchar, varchar, varchar, bpchar, varchar, text, varchar, varchar, timestamp,  integer) TO GROUP legaltime_full;


select * from customer_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 't'
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 't'
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_dq(text, integer, text ,  integer, timestamp)

-- DROP FUNCTION customer_dq( text, integer, text , integer, timestamp);

create or replace function customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , customerId_ integer, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMER' );
    end if;
	delete from customer where
	   customer_id = customerId_ 
	  and client_id = clientId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_dq(text, integer, text, text , integer, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_dq(text, integer, text, text , integer, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 






CREATE OR REPLACE FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text,  invoiceDt_ date)
  RETURNS SETOF customer AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
    return query select distinct customer.* 
		from  customer left join invoice  on customer.customer_id =invoice.customer_id 
		where customer.client_id = clientId_ and invoice.invoice_dt = invoiceDt_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION customer_invoiced_sq(text, integer, text, text, date) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_invoiced_sq(text, integer, text, text, date) TO public;
GRANT EXECUTE ON FUNCTION customer_invoiced_sq(text, integer, text, text, date) TO postgres;
GRANT EXECUTE ON FUNCTION customer_invoiced_sq(text, integer, text, text, date) TO legaltime_full;

select * from customer_invoiced_sq('ALREADY_AUTH',1,'','','2010-01-09')


























