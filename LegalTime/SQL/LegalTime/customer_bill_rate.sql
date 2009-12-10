
-- Security Grants


GRANT ALL ON TABLE customer_bill_rate TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_CUSTOMERBILLRATE', now(), 'Allows users to select customer_bill_rate'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_CUSTOMERBILLRATE', now(), 'Allows users to add records to customer_bill_rate');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_CUSTOMERBILLRATE', now(), 'Allows users to update records in customer_bill_rate');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_CUSTOMERBILLRATE', now(), 'Allows users to delete records from customer_bill_rate');
select * from security_privilege where priv_name in ('SELECT_CUSTOMERBILLRATE','INSERT_CUSTOMERBILLRATE','UPDATE_CUSTOMERBILLRATE','DELETE_CUSTOMERBILLRATE');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 17);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 18);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 19);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 20);


select * from security_privilege


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_bill_rate_sq(text, integer, text, text)

-- DROP FUNCTION customer_bill_rate_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION customer_bill_rate_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF customer_bill_rate AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERBILLRATE' );
    end if;
-- bill_rate, last_update, user_id, customer_id, client_id, customer_bill_rate_id    
    return query select * from customer_bill_rate where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION customer_bill_rate_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_bill_rate_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from customer_bill_rate_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_bill_rate_bypk(text, integer, text, text ,    integer)

-- DROP FUNCTION customer_bill_rate_pybk(text, integer, text, text ,    integer);

CREATE OR REPLACE FUNCTION customer_bill_rate_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  customerBillRateId_  integer)
  RETURNS customer_bill_rate AS
$BODY$
  Declare
    result customer_bill_rate;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERBILLRATE' );
    end if;
-- bill_rate, last_update, user_id, customer_id, client_id, customer_bill_rate_id    
     select * into result from customer_bill_rate where   customer_bill_rate_id = customerBillRateId_  and client_id = clientId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION customer_bill_rate_bypk(text, integer, text, text,    integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_bill_rate_bypk(text, integer, text, text,    integer) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_bill_rate_iq(text, integer, text , float8, text, int4)

-- DROP FUNCTION customer_bill_rate_iq( text, integer, text , float8, text, int4));

create or replace function customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , billRate_ float8, userId_ text, customerId_ int4)
  returns customer_bill_rate as
$body$
  declare
    newrow customer_bill_rate;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMERBILLRATE' );
    end if;
    insert into customer_bill_rate(client_id , bill_rate , last_update , user_id , customer_id ) 
	values (clientid_ , billRate_, now(), userId_, customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_bill_rate_iq(text, integer, text, text , float8, text, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_bill_rate_iq(text, integer, text, text , float8, text, int4) TO GROUP legaltime_full;

select * from customer_bill_rate_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 'text' 
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_bill_rate_uq(text, integer, text , float8, timestamp, text, int4,  integer)

-- DROP FUNCTION customer_bill_rate_uq( text, integer, text , float8, timestamp, text, int4,  integer);


create or replace function customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , billRate_ float8, lastUpdate_ timestamp, userId_ text, customerId_ int4, customerBillRateId_ integer)
  returns customer_bill_rate as
$body$
  declare
    updatedrow customer_bill_rate;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMERBILLRATE' );
    end if;
	update customer_bill_rate set
          bill_rate = billRate_ 
         , last_update = now()
         , user_id = userId_ 
         , customer_id = customerId_ 
                  
	where          
	   customer_bill_rate_id = customerBillRateId_          
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
alter function customer_bill_rate_uq(text, integer, text, text , float8, timestamp, text, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_bill_rate_uq(text, integer, text, text , float8, timestamp, text, int4,  integer) TO GROUP legaltime_full;


select * from customer_bill_rate_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
, <last_update>
 , 'text' 
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_bill_rate_dq(text, integer, text ,  integer, timestamp)

-- DROP FUNCTION customer_bill_rate_dq( text, integer, text , integer, timestamp);

create or replace function customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , customerBillRateId_ integer, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMERBILLRATE' );
    end if;
	delete from customer_bill_rate where
	   customer_bill_rate_id = customerBillRateId_ 
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
alter function customer_bill_rate_dq(text, integer, text, text , integer, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_bill_rate_dq(text, integer, text, text , integer, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+


create or replace function add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerId_ integer)
returns boolean as
$BODY$
declare
row user_info;
begin
 if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'INSERT_CUSTOMERBILLRATE' );
    end if;
FOR row in select user_id, default_bill_rate from user_info where client_id = clientid_ LOOP
 insert into customer_bill_rate(client_id , bill_rate , last_update , user_id , customer_id ) 
	values (clientid_ , row.default_bill_rate, now(), row.user_id, customerId_);
END LOOP;
return true;
end;
$BODY$
 LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION add_default_bill_rates_for_customer(text, integer, text, text, integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION add_default_bill_rates_for_customer(text, integer, text, text, integer) TO legaltime_full;  

select * from add_default_bill_rates_for_customer('ALREADY_AUTH', 1, 'bmartin', '', 3);