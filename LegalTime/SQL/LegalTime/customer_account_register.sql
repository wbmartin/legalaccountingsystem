

-- Security Grants
GRANT ALL ON TABLE customer_account_register TO GROUP legaltime_full;
GRANT ALL ON sequence customer_account_register_customer_account_register_id_seq TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_CUSTOMERACCOUNTREGISTER', now(), 'Allows users to select customer_account_register'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_CUSTOMERACCOUNTREGISTER', now(), 'Allows users to add records to customer_account_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_CUSTOMERACCOUNTREGISTER', now(), 'Allows users to update records in customer_account_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_CUSTOMERACCOUNTREGISTER', now(), 'Allows users to delete records from customer_account_register');
select * from security_privilege where priv_name in ('SELECT_CUSTOMERACCOUNTREGISTER','INSERT_CUSTOMERACCOUNTREGISTER','UPDATE_CUSTOMERACCOUNTREGISTER','DELETE_CUSTOMERACCOUNTREGISTER');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 58);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 59);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 60);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 61);


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_account_register_sq(text, integer, text, text)

-- DROP FUNCTION customer_account_register_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION customer_account_register_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF customer_account_register AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERACCOUNTREGISTER' );
    end if;
-- tran_type, tran_amt, description, effective_dt, last_update, customer_id, client_id, customer_account_register_id    
    return query select * from customer_account_register where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION customer_account_register_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_account_register_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from customer_account_register_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: customer_account_register_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION customer_account_register_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION customer_account_register_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  customerAccountRegisterId_  integer,  customerId_  int4 )
  RETURNS customer_account_register AS
$BODY$
  Declare
    result customer_account_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERACCOUNTREGISTER' );
    end if;
-- tran_type, tran_amt, description, effective_dt, last_update, customer_id, client_id, customer_account_register_id    
     select * into result from customer_account_register where   customer_account_register_id = customerAccountRegisterId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION customer_account_register_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION customer_account_register_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_account_register_iq(text, integer, text , varchar, float8, text, date, int4)

-- DROP FUNCTION customer_account_register_iq( text, integer, text , varchar, float8, text, date, int4));

create or replace function customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , tranType_ varchar, tranAmt_ float8, description_ text, effectiveDt_ date, customerId_ int4)
  returns customer_account_register as
$body$
  declare
    newrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMERACCOUNTREGISTER' );
    end if;
    insert into customer_account_register(client_id , tran_type , tran_amt , description , effective_dt , last_update , customer_id ) 
	values (clientid_ , tranType_, tranAmt_, description_, effectiveDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_account_register_iq(text, integer, text, text , varchar, float8, text, date, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_account_register_iq(text, integer, text, text , varchar, float8, text, date, int4) TO GROUP legaltime_full;

select * from customer_account_register_iq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 1
 , 'text' 
 , now()
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_account_register_uq(text, integer, text , varchar, float8, text, date, timestamp, int4,  integer)

-- DROP FUNCTION customer_account_register_uq( text, integer, text , varchar, float8, text, date, timestamp, int4,  integer);


create or replace function customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , tranType_ varchar, tranAmt_ float8, description_ text, effectiveDt_ date, lastUpdate_ timestamp, customerId_ int4, customerAccountRegisterId_ integer)
  returns customer_account_register as
$body$
  declare
    updatedrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMERACCOUNTREGISTER' );
    end if;
	update customer_account_register set
          tran_type = tranType_ 
         , tran_amt = tranAmt_ 
         , description = description_ 
         , effective_dt = effectiveDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   customer_account_register_id = customerAccountRegisterId_          
	  and client_id = clientId_          
	  and customer_id = customerId_            
	   and   last_update = lastUpdate_
	returning * into updatedrow;

	if found then
	  return updatedrow;
	else 
	  raise exception 'Update Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_account_register_uq(text, integer, text, text , varchar, float8, text, date, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_account_register_uq(text, integer, text, text , varchar, float8, text, date, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from customer_account_register_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  customer_account_register_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION customer_account_register_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , customerAccountRegisterId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMERACCOUNTREGISTER' );
    end if;
	delete from customer_account_register where
	   customer_account_register_id = customerAccountRegisterId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function customer_account_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION customer_account_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+







