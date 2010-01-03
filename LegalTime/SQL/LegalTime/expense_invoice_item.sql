

-- Security Grants
GRANT ALL ON TABLE expense_invoice_item TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_EXPENSEINVOICEITEM', now(), 'Allows users to select expense_invoice_item'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_EXPENSEINVOICEITEM', now(), 'Allows users to add records to expense_invoice_item');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_EXPENSEINVOICEITEM', now(), 'Allows users to update records in expense_invoice_item');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_EXPENSEINVOICEITEM', now(), 'Allows users to delete records from expense_invoice_item');
select * from security_privilege where priv_name in ('SELECT_EXPENSEINVOICEITEM','INSERT_EXPENSEINVOICEITEM','UPDATE_EXPENSEINVOICEITEM','DELETE_EXPENSEINVOICEITEM');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 49);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 50);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 51);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 52);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: expense_invoice_item_sq(text, integer, text, text)

-- DROP FUNCTION expense_invoice_item_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION expense_invoice_item_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF expense_invoice_item AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEINVOICEITEM' );
    end if;
-- invoice_id, amount, expense_description, expense_dt, last_update, customer_id, client_id, expense_invoice_item_id    
    return query select * from expense_invoice_item where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION expense_invoice_item_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION expense_invoice_item_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from expense_invoice_item_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: expense_invoice_item_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION expense_invoice_item_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION expense_invoice_item_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  expenseInvoiceItemId_  integer,  customerId_  int4 )
  RETURNS expense_invoice_item AS
$BODY$
  Declare
    result expense_invoice_item;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEINVOICEITEM' );
    end if;
-- invoice_id, amount, expense_description, expense_dt, last_update, customer_id, client_id, expense_invoice_item_id    
     select * into result from expense_invoice_item where   expense_invoice_item_id = expenseInvoiceItemId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION expense_invoice_item_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION expense_invoice_item_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  expense_invoice_item_iq(text, integer, text , int4, float8, text, date, int4)

-- DROP FUNCTION expense_invoice_item_iq( text, integer, text , int4, float8, text, date, int4));

create or replace function expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , invoiceId_ int4, amount_ float8, expenseDescription_ text, expenseDt_ date, customerId_ int4)
  returns expense_invoice_item as
$body$
  declare
    newrow expense_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_EXPENSEINVOICEITEM' );
    end if;
    insert into expense_invoice_item(client_id , invoice_id , amount , expense_description , expense_dt , last_update , customer_id ) 
	values (clientid_ , invoiceId_, amount_, expenseDescription_, expenseDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function expense_invoice_item_iq(text, integer, text, text , int4, float8, text, date, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_invoice_item_iq(text, integer, text, text , int4, float8, text, date, int4) TO GROUP legaltime_full;

select * from expense_invoice_item_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 1
 , 'text' 
 , now()
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  expense_invoice_item_uq(text, integer, text , int4, float8, text, date, timestamp, int4,  integer)

-- DROP FUNCTION expense_invoice_item_uq( text, integer, text , int4, float8, text, date, timestamp, int4,  integer);


create or replace function expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , invoiceId_ int4, amount_ float8, expenseDescription_ text, expenseDt_ date, lastUpdate_ timestamp, customerId_ int4, expenseInvoiceItemId_ integer)
  returns expense_invoice_item as
$body$
  declare
    updatedrow expense_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_EXPENSEINVOICEITEM' );
    end if;
	update expense_invoice_item set
          invoice_id = invoiceId_ 
         , amount = amount_ 
         , expense_description = expenseDescription_ 
         , expense_dt = expenseDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   expense_invoice_item_id = expenseInvoiceItemId_          
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
alter function expense_invoice_item_uq(text, integer, text, text , int4, float8, text, date, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_invoice_item_uq(text, integer, text, text , int4, float8, text, date, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from expense_invoice_item_uq('ALREADY_AUTH', 1, 'test', 'test' , 1
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  expense_invoice_item_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION expense_invoice_item_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , expenseInvoiceItemId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_EXPENSEINVOICEITEM' );
    end if;
	delete from expense_invoice_item where
	   expense_invoice_item_id = expenseInvoiceItemId_ 
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
alter function expense_invoice_item_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION expense_invoice_item_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
