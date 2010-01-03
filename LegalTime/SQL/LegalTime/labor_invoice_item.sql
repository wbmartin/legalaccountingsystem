

-- Security Grants
GRANT ALL ON TABLE labor_invoice_item TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_LABORINVOICEITEM', now(), 'Allows users to select labor_invoice_item'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_LABORINVOICEITEM', now(), 'Allows users to add records to labor_invoice_item');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_LABORINVOICEITEM', now(), 'Allows users to update records in labor_invoice_item');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_LABORINVOICEITEM', now(), 'Allows users to delete records from labor_invoice_item');
select * from security_privilege where priv_name in ('SELECT_LABORINVOICEITEM','INSERT_LABORINVOICEITEM','UPDATE_LABORINVOICEITEM','DELETE_LABORINVOICEITEM');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 53);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 54);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 55);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 56);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: labor_invoice_item_sq(text, integer, text, text)

-- DROP FUNCTION labor_invoice_item_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION labor_invoice_item_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF labor_invoice_item AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- bill_rate, hours_billed, invoice_id, user_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
    return query select * from labor_invoice_item where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION labor_invoice_item_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION labor_invoice_item_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from labor_invoice_item_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: labor_invoice_item_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION labor_invoice_item_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION labor_invoice_item_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  laborInvoiceItemId_  integer,  customerId_  int4 )
  RETURNS labor_invoice_item AS
$BODY$
  Declare
    result labor_invoice_item;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- bill_rate, hours_billed, invoice_id, user_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
     select * into result from labor_invoice_item where   labor_invoice_item_id = laborInvoiceItemId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION labor_invoice_item_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION labor_invoice_item_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_invoice_item_iq(text, integer, text , float8, float8, int4, text, text, date, int4)

-- DROP FUNCTION labor_invoice_item_iq( text, integer, text , float8, float8, int4, text, text, date, int4));

create or replace function labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , billRate_ float8, hoursBilled_ float8, invoiceId_ int4, userId_ text, activityDescription_ text, activityDt_ date, customerId_ int4)
  returns labor_invoice_item as
$body$
  declare
    newrow labor_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_LABORINVOICEITEM' );
    end if;
    insert into labor_invoice_item(client_id , bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id ) 
	values (clientid_ , billRate_, hoursBilled_, invoiceId_, userId_, activityDescription_, activityDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function labor_invoice_item_iq(text, integer, text, text , float8, float8, int4, text, text, date, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_invoice_item_iq(text, integer, text, text , float8, float8, int4, text, text, date, int4) TO GROUP legaltime_full;

select * from labor_invoice_item_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 1
 , 1
 , 'text' 
 , 'text' 
 , now()
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_invoice_item_uq(text, integer, text , float8, float8, int4, text, text, date, timestamp, int4,  integer)

-- DROP FUNCTION labor_invoice_item_uq( text, integer, text , float8, float8, int4, text, text, date, timestamp, int4,  integer);


create or replace function labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , billRate_ float8, hoursBilled_ float8, invoiceId_ int4, userId_ text, activityDescription_ text, activityDt_ date, lastUpdate_ timestamp, customerId_ int4, laborInvoiceItemId_ integer)
  returns labor_invoice_item as
$body$
  declare
    updatedrow labor_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_LABORINVOICEITEM' );
    end if;
	update labor_invoice_item set
          bill_rate = billRate_ 
         , hours_billed = hoursBilled_ 
         , invoice_id = invoiceId_ 
         , user_id = userId_ 
         , activity_description = activityDescription_ 
         , activity_dt = activityDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   labor_invoice_item_id = laborInvoiceItemId_          
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
alter function labor_invoice_item_uq(text, integer, text, text , float8, float8, int4, text, text, date, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_invoice_item_uq(text, integer, text, text , float8, float8, int4, text, text, date, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from labor_invoice_item_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
, 1
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_invoice_item_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION labor_invoice_item_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , laborInvoiceItemId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_LABORINVOICEITEM' );
    end if;
	delete from labor_invoice_item where
	   labor_invoice_item_id = laborInvoiceItemId_ 
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
alter function labor_invoice_item_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_invoice_item_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
