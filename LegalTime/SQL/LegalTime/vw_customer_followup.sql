create or replace view vw_customer_followup AS 
select 
	followup.followup_id,
	followup.client_id ,
	followup.customer_id ,
	followup.last_update,
	followup.due_dt,
	followup.open_dt,
	followup.close_dt,
	followup.followup_description,
	followup.assigned_user_id,
	customer.first_name AS first_name,
	customer.last_name AS last_name,
	customer.last_name ||', ' ||customer.first_name AS display_name,	 
	usr.display_name as usr_display 
from (followup left join customer on((followup.customer_id = customer.customer_id)))
	  left join user_info as usr
	     on followup.assigned_user_id =usr.user_id
where followup.close_dt is null 
order by followup.due_dt

ALTER TABLE vw_customer_followup OWNER TO postgres;
GRANT ALL ON TABLE vw_customer_followup TO postgres;
GRANT ALL ON TABLE vw_customer_followup TO legaltime_full;
--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
select * from vw_customer_followup
update followup set close_dt = null
--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

--=======================================================================================



-- Security Grants
GRANT ALL ON TABLE vw_customer_followup TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_VWCUSTOMERFOLLOWUP', now(), 'Allows users to select vw_customer_followup'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_VWCUSTOMERFOLLOWUP', now(), 'Allows users to add records to vw_customer_followup');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_VWCUSTOMERFOLLOWUP', now(), 'Allows users to update records in vw_customer_followup');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_VWCUSTOMERFOLLOWUP', now(), 'Allows users to delete records from vw_customer_followup');
select * from security_privilege where priv_name in ('SELECT_VWCUSTOMERFOLLOWUP','INSERT_VWCUSTOMERFOLLOWUP','UPDATE_VWCUSTOMERFOLLOWUP','DELETE_VWCUSTOMERFOLLOWUP');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 25);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 26);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 27);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 28);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: vw_customer_followup_sq(text, integer, text, text)

-- DROP FUNCTION vw_customer_followup_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION vw_customer_followup_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF vw_customer_followup AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWCUSTOMERFOLLOWUP' );
    end if;
-- last_name, first_name, followup_description, close_dt, open_dt, due_dt, followup_id    
    return query select * from vw_customer_followup where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION vw_customer_followup_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_customer_followup_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from vw_customer_followup_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: vw_customer_followup_bypk(text, integer, text, text )

-- DROP FUNCTION vw_customer_followup_pybk(text, integer, text, text );

CREATE OR REPLACE FUNCTION vw_customer_followup_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupId_ integer  )
  RETURNS vw_customer_followup AS
$BODY$
  Declare
    result vw_customer_followup;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWCUSTOMERFOLLOWUP' );
    end if;
-- last_name, first_name, followup_description, close_dt, open_dt, due_dt, followup_id    
     select * into result from vw_customer_followup where followup_id = followupId_;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION vw_customer_followup_bypk(text, integer, text, text, integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_customer_followup_bypk(text, integer, text, text, integer) TO GROUP legaltime_full;


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 
