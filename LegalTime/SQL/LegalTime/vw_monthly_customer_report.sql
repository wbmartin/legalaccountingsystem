-- drop view vw_monthly_customer_report
CREATE OR REPLACE VIEW vw_monthly_customer_report AS 
 SELECT customer.client_id, customer.customer_id, customer.last_name, customer.first_name,  (vw_cb.balance/ customer.monthly_bill_rate) as months_unpaid,
 case when customer.mortgage_amount >0 then 'Yes' else 'No' end as escrow
 FROM customer
   LEFT JOIN vw_customer_balance as vw_cb ON   customer.customer_id = vw_cb .customer_id and customer.client_id = vw_cb .client_id
   where customer.bill_type='MONTHLY' and monthly_bill_rate >0
   order by customer.last_name, customer.first_name  ;

ALTER TABLE vw_monthly_customer_report OWNER TO postgres;
GRANT ALL ON TABLE vw_monthly_customer_report TO postgres;
GRANT ALL ON TABLE vw_monthly_customer_report TO legaltime_full;


select * from vw_monthly_customer_report
update customer set mortgage_amount =2 where customer_id =48
--======================================================================================
--drop view vw_customer_balance
create or replace view vw_customer_balance as
	select client_id, customer_id, sum(tran_amt) as balance from customer_account_register group by client_id, customer_id;
ALTER TABLE vw_customer_balance OWNER TO postgres;
GRANT ALL ON TABLE vw_customer_balance TO postgres;
GRANT ALL ON TABLE vw_customer_balance TO legaltime_full;


select * from customer


-- Function: customer_sq(text, integer, text, text)

-- DROP FUNCTION vw_monthly_customer_report_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF customer AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
    return query select * from  monthly_customer_report where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION  monthly_customer_report_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION  monthly_customer_report_sq(text, integer, text, text) TO public;
GRANT EXECUTE ON FUNCTION  monthly_customer_report_sq(text, integer, text, text) TO postgres;
GRANT EXECUTE ON FUNCTION  monthly_customer_report_sq(text, integer, text, text) TO legaltime_full;

select * from monthly_customer_report_sq('ALREADY_AUTH',1,'','')









GRANT ALL ON TABLE vw_monthly_customer_report TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_VWMONTHLYCUSTOMERREPORT', now(), 'Allows users to select vw_monthly_customer_report'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_VWMONTHLYCUSTOMERREPORT', now(), 'Allows users to add records to vw_monthly_customer_report');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_VWMONTHLYCUSTOMERREPORT', now(), 'Allows users to update records in vw_monthly_customer_report');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_VWMONTHLYCUSTOMERREPORT', now(), 'Allows users to delete records from vw_monthly_customer_report');
select * from security_privilege where priv_name in ('SELECT_VWMONTHLYCUSTOMERREPORT','INSERT_VWMONTHLYCUSTOMERREPORT','UPDATE_VWMONTHLYCUSTOMERREPORT','DELETE_VWMONTHLYCUSTOMERREPORT');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 66);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, ?);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: vw_monthly_customer_report_sq(text, integer, text, text)

-- DROP FUNCTION vw_monthly_customer_report_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION vw_monthly_customer_report_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF vw_monthly_customer_report AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWMONTHLYCUSTOMERREPORT' );
    end if;
-- escrow, months_unpaid, first_name, last_name, customer_id, client_id    
    return query select * from vw_monthly_customer_report where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION vw_monthly_customer_report_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_monthly_customer_report_sq(text, integer, text, text) TO GROUP legaltime_full;
