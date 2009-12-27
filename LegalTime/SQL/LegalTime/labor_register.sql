

-- Security Grants
GRANT ALL ON TABLE labor_register TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_LABORREGISTER', now(), 'Allows users to select labor_register'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_LABORREGISTER', now(), 'Allows users to add records to labor_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_LABORREGISTER', now(), 'Allows users to update records in labor_register');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_LABORREGISTER', now(), 'Allows users to delete records from labor_register');
select * from security_privilege where priv_name in ('SELECT_LABORREGISTER','INSERT_LABORREGISTER','UPDATE_LABORREGISTER','DELETE_LABORREGISTER');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 33);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 34);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 35);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 36);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: labor_register_sq(text, integer, text, text)

-- DROP FUNCTION labor_register_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION labor_register_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF labor_register AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORREGISTER' );
    end if;
-- user_id, invoice_id, bill_rate, invoiceable, activity_date, end_time, start_time, minute_count, description, last_update, customer_id, client_id, labor_register_id    
    return query select * from labor_register where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION labor_register_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION labor_register_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from labor_register_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: labor_register_bypk(text, integer, text, text ,    integer,    int4 )

-- DROP FUNCTION labor_register_pybk(text, integer, text, text ,    integer,    int4 );

CREATE OR REPLACE FUNCTION labor_register_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  laborRegisterId_  integer,  customerId_  int4 )
  RETURNS labor_register AS
$BODY$
  Declare
    result labor_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORREGISTER' );
    end if;
-- user_id, invoice_id, bill_rate, invoiceable, activity_date, end_time, start_time, minute_count, description, last_update, customer_id, client_id, labor_register_id    
     select * into result from labor_register where   labor_register_id = laborRegisterId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION labor_register_bypk(text, integer, text, text,    integer,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION labor_register_bypk(text, integer, text, text,    integer,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_register_iq(text, integer, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, int4)

-- DROP FUNCTION labor_register_iq( text, integer, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, int4));

create or replace function labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , userId_ text, invoiceId_ int4, billRate_ float8, invoiceable_ bool, activityDate_ date, endTime_ timestamp, startTime_ timestamp, minuteCount_ int4, description_ text, customerId_ int4)
  returns labor_register as
$body$
  declare
    newrow labor_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_LABORREGISTER' );
    end if;
    insert into labor_register(client_id , user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id ) 
	values (clientid_ , userId_, invoiceId_, billRate_, invoiceable_, activityDate_, endTime_, startTime_, minuteCount_, description_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function labor_register_iq(text, integer, text, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_register_iq(text, integer, text, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, int4) TO GROUP legaltime_full;

select * from labor_register_iq(cast('ALREADY_AUTH' as, 1, 'test', 'test'  , 'text' 
 , 1
 , 1
 ,true
 , now()
 , now()
 , now()
 , 1
 , 'text' 
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_register_uq(text, integer, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, timestamp, int4,  integer)

-- DROP FUNCTION labor_register_uq( text, integer, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, timestamp, int4,  integer);


create or replace function labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , userId_ text, invoiceId_ int4, billRate_ float8, invoiceable_ bool, activityDate_ date, endTime_ timestamp, startTime_ timestamp, minuteCount_ int4, description_ text, lastUpdate_ timestamp, customerId_ int4, laborRegisterId_ integer)
  returns labor_register as
$body$
  declare
    updatedrow labor_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_LABORREGISTER' );
    end if;
	update labor_register set
          user_id = userId_ 
         , invoice_id = invoiceId_ 
         , bill_rate = billRate_ 
         , invoiceable = invoiceable_ 
         , activity_date = activityDate_ 
         , end_time = endTime_ 
         , start_time = startTime_ 
         , minute_count = minuteCount_ 
         , description = description_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   labor_register_id = laborRegisterId_          
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
alter function labor_register_uq(text, integer, text, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_register_uq(text, integer, text, text , text, int4, float8, bool, date, timestamp, timestamp, int4, text, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from labor_register_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
, 1
 , 'text' 
, true
 , 'text' 
 , now()
 , now()
, 1
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  labor_register_dq(text, integer, text ,  integer, customerId_ int4, timestamp)

-- DROP FUNCTION labor_register_dq( text, integer, text , integer, customerId_ int4, timestamp);

create or replace function labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , laborRegisterId_ integer, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_LABORREGISTER' );
    end if;
	delete from labor_register where
	   labor_register_id = laborRegisterId_ 
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
alter function labor_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION labor_register_dq(text, integer, text, text , integer, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
