

-- Security Grants
GRANT ALL ON TABLE followup TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_FOLLOWUP', now(), 'Allows users to select followup'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_FOLLOWUP', now(), 'Allows users to add records to followup');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_FOLLOWUP', now(), 'Allows users to update records in followup');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_FOLLOWUP', now(), 'Allows users to delete records from followup');
select * from security_privilege where priv_name in ('SELECT_FOLLOWUP','INSERT_FOLLOWUP','UPDATE_FOLLOWUP','DELETE_FOLLOWUP');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: followup_sq(text, integer, text, text)

-- DROP FUNCTION followup_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION followup_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF followup AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_FOLLOWUP' );
    end if;
-- assigned_user_id, followup_description, close_dt, open_dt, due_dt, last_update, customer_id, client_id, followup_id    
    return query select * from followup where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION followup_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION followup_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from followup_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: followup_bypk(text, integer, text, text ,    integer)

-- DROP FUNCTION followup_pybk(text, integer, text, text ,    integer);

CREATE OR REPLACE FUNCTION followup_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  followupId_  integer)
  RETURNS followup AS
$BODY$
  Declare
    result followup;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_FOLLOWUP' );
    end if;
-- assigned_user_id, followup_description, close_dt, open_dt, due_dt, last_update, customer_id, client_id, followup_id    
     select * into result from followup where   followup_id = followupId_  and client_id = clientId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION followup_bypk(text, integer, text, text,    integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION followup_bypk(text, integer, text, text,    integer) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  followup_iq(text, integer, text , text, text, date, date, date, int4)

-- DROP FUNCTION followup_iq( text, integer, text , text, text, date, date, date, int4));

create or replace function followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , assignedUserId_ text, followupDescription_ text, closeDt_ date, openDt_ date, dueDt_ date, customerId_ int4)
  returns followup as
$body$
  declare
    newrow followup;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_FOLLOWUP' );
    end if;
    insert into followup(client_id , assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id ) 
	values (clientid_ , assignedUserId_, followupDescription_, closeDt_, openDt_, dueDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function followup_iq(text, integer, text, text , text, text, date, date, date, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION followup_iq(text, integer, text, text , text, text, date, date, date, int4) TO GROUP legaltime_full;

select * from followup_iq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , now()
 , now()
 , now()
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  followup_uq(text, integer, text , text, text, date, date, date, timestamp, int4,  integer)

-- DROP FUNCTION followup_uq( text, integer, text , text, text, date, date, date, timestamp, int4,  integer);


create or replace function followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , assignedUserId_ text, followupDescription_ text, closeDt_ date, openDt_ date, dueDt_ date, lastUpdate_ timestamp, customerId_ int4, followupId_ integer)
  returns followup as
$body$
  declare
    updatedrow followup;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_FOLLOWUP' );
    end if;
	update followup set
          assigned_user_id = assignedUserId_ 
         , followup_description = followupDescription_ 
         , close_dt = closeDt_ 
         , open_dt = openDt_ 
         , due_dt = dueDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   followup_id = followupId_          
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
alter function followup_uq(text, integer, text, text , text, text, date, date, date, timestamp, int4,  integer) owner to postgres;
GRANT EXECUTE ON FUNCTION followup_uq(text, integer, text, text , text, text, date, date, date, timestamp, int4,  integer) TO GROUP legaltime_full;


select * from followup_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  followup_dq(text, integer, text ,  integer, timestamp)

-- DROP FUNCTION followup_dq( text, integer, text , integer, timestamp);

create or replace function followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , followupId_ integer, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_FOLLOWUP' );
    end if;
	delete from followup where
	   followup_id = followupId_ 
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
alter function followup_dq(text, integer, text, text , integer, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION followup_dq(text, integer, text, text , integer, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
