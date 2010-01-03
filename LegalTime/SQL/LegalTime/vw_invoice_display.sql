

-- Security Grants
GRANT ALL ON TABLE vw_invoice_display TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_VWINVOICEDISPLAY', now(), 'Allows users to select vw_invoice_display'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_VWINVOICEDISPLAY', now(), 'Allows users to add records to vw_invoice_display');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_VWINVOICEDISPLAY', now(), 'Allows users to update records in vw_invoice_display');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_VWINVOICEDISPLAY', now(), 'Allows users to delete records from vw_invoice_display');
select * from security_privilege where priv_name in ('SELECT_VWINVOICEDISPLAY','INSERT_VWINVOICEDISPLAY','UPDATE_VWINVOICEDISPLAY','DELETE_VWINVOICEDISPLAY');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 45);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 46);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 47);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 48);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: vw_invoice_display_sq(text, integer, text, text)

-- DROP FUNCTION vw_invoice_display_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION vw_invoice_display_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF vw_invoice_display AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWINVOICEDISPLAY' );
    end if;
-- display_name, last_name, first_name, prev_balance_due, invoice_total_amt, generated_dt, invoice_dt, last_update, customer_id, client_id, invoice_id    
    return query select * from vw_invoice_display where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION vw_invoice_display_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_invoice_display_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from vw_invoice_display_sq('ALREADY_AUTH', 1, 'test', 'test');





--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

select * from vw_invoice_display




-- View: vw_invoice_display

-- DROP VIEW vw_invoice_display;

CREATE OR REPLACE VIEW vw_invoice_display AS 
 SELECT invoice.invoice_id, invoice.client_id, invoice.customer_id, 
	invoice.last_update, invoice.invoice_dt, invoice.generated_dt, 
	invoice.invoice_total_amt, invoice.prev_balance_due, 
	customer.first_name, customer.last_name, (customer.last_name::text || ', '::text) || customer.first_name::text AS display_name,
	address, city, state, zip, work_phone, home_phone, 
	fax, email, client_since_dt, bill_type, monthly_bill_rate, 
	active_yn, mortgage_amount, contingency_rate
	
   FROM invoice
   LEFT JOIN customer ON invoice.customer_id = customer.customer_id
  ORDER BY invoice.invoice_dt;

ALTER TABLE vw_invoice_display OWNER TO postgres;
GRANT ALL ON TABLE vw_invoice_display TO postgres;
GRANT ALL ON TABLE vw_invoice_display TO legaltime_full;

select * from invoice

select * from labor_invoice_item