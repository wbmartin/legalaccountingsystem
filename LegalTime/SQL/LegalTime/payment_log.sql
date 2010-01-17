--This file has been modified:
-- * payment_reversal
-- Security Grants
GRANT ALL ON TABLE payment_log TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_PAYMENTLOG', now(), 'Allows users to select payment_log'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_PAYMENTLOG', now(), 'Allows users to add records to payment_log');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_PAYMENTLOG', now(), 'Allows users to select payment_log'); 

INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_PAYMENTLOG', now(), 'Allows users to update records in payment_log');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_PAYMENTLOG', now(), 'Allows users to delete records from payment_log');
select * from security_privilege where priv_name in ('SELECT_PAYMENTLOG','INSERT_PAYMENTLOG','UPDATE_PAYMENTLOG','DELETE_PAYMENTLOG');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 62);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 63);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 64);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 65);


INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'REVERSE_PAYMENTL', now(), 'Allows users to select payment_log'); 
select * from security_privilege
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 70);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: payment_log_sq(text, integer, text, text)

-- DROP FUNCTION payment_log_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION payment_log_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF payment_log AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_PAYMENTLOG' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
    return query select * from payment_log where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION payment_log_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION payment_log_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from payment_log_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: payment_log_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION payment_log_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION payment_log_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  paymentLogId_  integer,  customerId_  int4 )
  RETURNS payment_log AS
$BODY$
  Declare
    result payment_log;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_PAYMENTLOG' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
     select * into result from payment_log where   payment_log_id = paymentLogId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION payment_log_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION payment_log_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  payment_log_iq(text, integer, text , int4, int4, float8, text, date, int4)

-- DROP FUNCTION payment_log_iq( text, integer, text , int4, int4, float8, text, date, int4));

create or replace function payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , customerAccountRegisterId_ int4, invoiceId_ int4, amount_ float8, description_ text, effectiveDt_ date, customerId_ int4)
  returns payment_log as
$body$
  declare
    newrow payment_log;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_PAYMENTLOG' );
    end if;
    insert into payment_log(client_id , customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id ) 
	values (clientid_ , customerAccountRegisterId_, invoiceId_, amount_, description_, effectiveDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function payment_log_iq(text, integer, text, text , int4, int4, float8, text, date, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION payment_log_iq(text, integer, text, text , int4, int4, float8, text, date, int4) TO GROUP legaltime_full;

select * from payment_log_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 1
 , 1
 , 'text' 
 , now()
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  payment_log_uq(text, integer, text , int4, int4, float8, text, date, timestamp, int4,  integer)

-- DROP FUNCTION payment_log_uq( text, integer, text , int4, int4, float8, text, date, timestamp, int4,  integer);


create or replace function payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , customerAccountRegisterId_ int4, invoiceId_ int4, amount_ float8, description_ text, effectiveDt_ date, lastUpdate_ timestamp, customerId_ int4, paymentLogId_ integer)
  returns payment_log as
$body$
  declare
    updatedrow payment_log;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_PAYMENTLOG' );
    end if;
	update payment_log set
          customer_account_register_id = customerAccountRegisterId_ 
         , invoice_id = invoiceId_ 
         , amount = amount_ 
         , description = description_ 
         , effective_dt = effectiveDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   payment_log_id = paymentLogId_          
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
alter function payment_log_uq(text, integer, text, text , int4, int4, float8, text, date, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION payment_log_uq(text, integer, text, text , int4, int4, float8, text, date, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from payment_log_uq('ALREADY_AUTH', 1, 'test', 'test' , 1
, 1
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  payment_log_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION payment_log_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , paymentLogId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_PAYMENTLOG' );
    end if;
	delete from payment_log where
	   payment_log_id = paymentLogId_ 
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
alter function payment_log_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION payment_log_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
-- Function: payment_log_sq(text, integer, text, text)

-- DROP FUNCTION payment_reversal(text, integer, text, text, integer);

CREATE OR REPLACE FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentLogId_ integer)
  RETURNS boolean AS
$BODY$
  Declare
  payLogToBeRvsd payment_log;
  carToBeRvsd customer_account_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'REVERSE_PAYMENT' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
     select * into payLogToBeRvsd from payment_log where client_id = clientId_ and payment_log_id = paymentLogId_;
    insert into payment_log (invoice_id, amount, description, effective_dt, last_update, customer_id, client_id)
      values(payLogToBeRvsd.invoice_id, payLogToBeRvsd.amount, 'Payment Reversed', payLogToBeRvsd.effective_dt, now(), payLogToBeRvsd.customer_id, payLogToBeRvsd.client_id);
    INSERT INTO customer_account_register( client_id, customer_id, last_update,  effective_dt, description, 				tran_amt, tran_type, ref_id)
				 VALUES (clientid_,    payLogToBeRvsd.customer_id, now(),        payLogToBeRvsd.effective_dt, 	'Payment REVERSAL' , payLogToBeRvsd.amount * -1 ,'PAYR', null);
  
    return true;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION payment_reversal(text, integer, text, text, integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION payment_reversal(text, integer, text, text, integer) TO public;
GRANT EXECUTE ON FUNCTION payment_reversal(text, integer, text, text, integer) TO postgres;
GRANT EXECUTE ON FUNCTION payment_reversal(text, integer, text, text, integer) TO legaltime_full;

--select * from payment_log


--select * from customer_account_register;
--select * from payment_reversal('ALREADY_AUTH' , 1 , '', '', 1);
