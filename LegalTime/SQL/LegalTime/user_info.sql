-- Security Grants
GRANT ALL ON TABLE user_info TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_USERINFO', now(), 'Allows users to select user_info'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_USERINFO', now(), 'Allows users to add records to user_info');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_USERINFO', now(), 'Allows users to update records in user_info');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_USERINFO', now(), 'Allows users to delete records from user_info');
select * from security_privilege where priv_name in ('SELECT_USERINFO','INSERT_USERINFO','UPDATE_USERINFO','DELETE_USERINFO');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 29);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 30);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 31);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 32);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: user_info_sq(text, integer, text, text)

-- DROP FUNCTION user_info_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION user_info_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF user_info AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_USERINFO' );
    end if;
-- email_addr, display_name, default_bill_rate, last_update, client_id, user_id    
    return query select * from user_info where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION user_info_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION user_info_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from user_info_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: user_info_bypk(text, integer, text, text ,    text )

-- DROP FUNCTION user_info_pybk(text, integer, text, text ,    text );

CREATE OR REPLACE FUNCTION user_info_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  userId_  text )
  RETURNS user_info AS
$BODY$
  Declare
    result user_info;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_USERINFO' );
    end if;
-- email_addr, display_name, default_bill_rate, last_update, client_id, user_id    
     select * into result from user_info where   user_id = userId_  and client_id = clientId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION user_info_bypk(text, integer, text, text,    text ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION user_info_bypk(text, integer, text, text,    text ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  user_info_iq(text, integer, text , varchar, varchar, float8, text)

-- DROP FUNCTION user_info_iq( text, integer, text , varchar, varchar, float8, text));

create or replace function user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , emailAddr_ varchar, displayName_ varchar, defaultBillRate_ float8, userId_ text)
  returns user_info as
$body$
  declare
    newrow user_info;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_USERINFO' );
    end if;
    insert into user_info(client_id , email_addr , display_name , default_bill_rate , last_update , user_id ) 
	values (clientid_ , emailAddr_, displayName_, defaultBillRate_, now(), userId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function user_info_iq(text, integer, text, text , varchar, varchar, float8, text) owner to postgres;
GRANT EXECUTE ON FUNCTION user_info_iq(text, integer, text, text , varchar, varchar, float8, text) TO GROUP legaltime_full;

select * from user_info_iq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 1
 , 'text' 
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  user_info_uq(text, integer, text , varchar, varchar, float8, timestamp, text)

-- DROP FUNCTION user_info_uq( text, integer, text , varchar, varchar, float8, timestamp, text);


create or replace function user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , emailAddr_ varchar, displayName_ varchar, defaultBillRate_ float8, lastUpdate_ timestamp, userId_ text)
  returns user_info as
$body$
  declare
    updatedrow user_info;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_USERINFO' );
    end if;
	update user_info set
          email_addr = emailAddr_ 
         , display_name = displayName_ 
         , default_bill_rate = defaultBillRate_ 
         , last_update = now()
                  , user_id = userId_ 

	where          
	   user_id = userId_          
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
alter function user_info_uq(text, integer, text, text , varchar, varchar, float8, timestamp, text) owner to postgres;
GRANT EXECUTE ON FUNCTION user_info_uq(text, integer, text, text , varchar, varchar, float8, timestamp, text) TO GROUP legaltime_full;


select * from user_info_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
 , 'text' 
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  user_info_dq(text, integer, text , userId_ text, timestamp)

-- DROP FUNCTION user_info_dq( text, integer, text , userId_ text, timestamp);

create or replace function user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , userId_ text, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_USERINFO' );
    end if;
	delete from user_info where
	   user_id = userId_ 
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
alter function user_info_dq(text, integer, text, text , userId_ text, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION user_info_dq(text, integer, text, text , userId_ text, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
