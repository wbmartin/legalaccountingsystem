-- View: vw_customer_hourly_bill_rate

-- DROP VIEW vw_customer_hourly_bill_rate;

CREATE OR REPLACE VIEW vw_customer_hourly_bill_rate AS 
 SELECT cbr.client_id, cbr.customer_bill_rate_id, u.user_id, u.display_name, cbr.bill_rate, cbr.customer_id, cbr.last_update
   FROM user_info u
   JOIN customer_bill_rate cbr ON u.client_id = cbr.client_id AND u.user_id = cbr.user_id;

ALTER TABLE vw_customer_hourly_bill_rate OWNER TO postgres;
GRANT ALL ON TABLE vw_customer_hourly_bill_rate TO postgres;
GRANT ALL ON TABLE vw_customer_hourly_bill_rate TO legaltime_full;





-- Security Grants
GRANT ALL ON TABLE vw_customer_hourly_bill_rate TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_VWCUSTOMERHOURLYBILLRATE', now(), 'Allows users to select vw_customer_hourly_bill_rate'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_VWCUSTOMERHOURLYBILLRATE', now(), 'Allows users to delete records from vw_customer_hourly_bill_rate');
select * from security_privilege where priv_name in ('SELECT_VWCUSTOMERHOURLYBILLRATE','INSERT_VWCUSTOMERHOURLYBILLRATE','UPDATE_VWCUSTOMERHOURLYBILLRATE','DELETE_VWCUSTOMERHOURLYBILLRATE');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 9);

delete from security_profile_grant where security_privilege_id in (10,11,12)
delete from security_privilege where security_privilege_id in (10,11,12)

CREATE OR REPLACE FUNCTION vw_customer_hourly_bill_rate_sq(alreadyAuth_ text, clientid_ integer, userid_ text, sessionid_ text)
  RETURNS SETOF vw_customer_hourly_bill_rate AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, userId_,sessionId_) ;
    	perform isUserAuthorized(clientId_,userId_,'SELECT_VWCUSTOMERHOURLYBILLRATE' );
    end if;
-- last_update, customer_id, bill_rate, display_name, user_id, customer_bill_rate_id, client_id    
    return query select * from vw_customer_hourly_bill_rate where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION vw_customer_hourly_bill_rate_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_customer_hourly_bill_rate_sq(text, integer, text, text) TO GROUP legaltime_full;
