

-- Security Grants
GRANT ALL ON TABLE expense_register TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_EXPENSEREGISTER', now(), 'Allows users to select expense_register'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_EXPENSEREGISTER', now(), 'Allows users to add records to expense_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_EXPENSEREGISTER', now(), 'Allows users to update records in expense_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_EXPENSEREGISTER', now(), 'Allows users to delete records from expense_register');
select * from security_privilege where priv_name in ('SELECT_EXPENSEREGISTER','INSERT_EXPENSEREGISTER','UPDATE_EXPENSEREGISTER','DELETE_EXPENSEREGISTER');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 41);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 42);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 43);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 44);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: expense_register_sq(text, integer, text, text)

-- DROP FUNCTION expense_register_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION expense_register_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF expense_register AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEREGISTER' );
    end if;
-- invoiceable, invoice_id, amount, description, last_update, customer_id, client_id, expense_register_id    
    return query select * from expense_register where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION expense_register_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION expense_register_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from expense_register_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: expense_register_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION expense_register_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION expense_register_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  expenseRegisterId_  integer,  customerId_  int4 )
  RETURNS expense_register AS
$BODY$
  Declare
    result expense_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEREGISTER' );
    end if;
-- invoiceable, invoice_id, amount, description, last_update, customer_id, client_id, expense_register_id    
     select * into result from expense_register where   expense_register_id = expenseRegisterId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION expense_register_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION expense_register_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



 DROP FUNCTION expense_register_iq(text, integer, text, text, boolean, integer, double precision, text, integer);
 
create or replace function expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , expenseDt_ date, invoiceable_ bool, invoiceId_ int4, amount_ float8, description_ text, customerId_ int4)
  returns expense_register as
$body$
  declare
    newrow expense_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_EXPENSEREGISTER' );
    end if;
    insert into expense_register(client_id , expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id ) 
	values (clientid_ , expenseDt_, invoiceable_, invoiceId_, amount_, description_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function expense_register_iq(text, integer, text, text , date, bool, int4, float8, text, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_register_iq(text, integer, text, text , date, bool, int4, float8, text, int4) TO GROUP legaltime_full;



DROP FUNCTION expense_register_uq(text, integer, text, text, boolean, integer, double precision, text, timestamp without time zone, integer, integer);

create or replace function expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , expenseDt_ date, invoiceable_ bool, invoiceId_ int4, amount_ float8, description_ text, lastUpdate_ timestamp, customerId_ int4, expenseRegisterId_ integer)
  returns expense_register as
$body$
  declare
    updatedrow expense_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_EXPENSEREGISTER' );
    end if;
	update expense_register set
          expense_dt = expenseDt_ 
         , invoiceable = invoiceable_ 
         , invoice_id = invoiceId_ 
         , amount = amount_ 
         , description = description_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   expense_register_id = expenseRegisterId_          
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
alter function expense_register_uq(text, integer, text, text , date, bool, int4, float8, text, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_register_uq(text, integer, text, text , date, bool, int4, float8, text, timestamp, int4,  integer) TO GROUP legaltime_full;


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  expense_register_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION expense_register_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , expenseRegisterId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_EXPENSEREGISTER' );
    end if;
	delete from expense_register where
	   expense_register_id = expenseRegisterId_ 
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
alter function expense_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
