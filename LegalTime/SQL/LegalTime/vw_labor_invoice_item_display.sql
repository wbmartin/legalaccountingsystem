create or replace view vw_labor_invoice_item_display as
 select labor_invoice_item_id, inv_item.client_id, customer_id, inv_item.last_update, activity_dt, 
	activity_description, invoice_id, hours_billed, bill_rate, inv_item.user_id, user_info.display_name
 from labor_invoice_item as inv_item left join user_info on inv_item.user_id = user_info.user_id
 order by activity_dt;

 select * from vw_labor_invoice_item_display
 
 
CREATE OR REPLACE FUNCTION vw_labor_invoice_item_display_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF vw_labor_invoice_item_display AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- display_name, user_id, bill_rate, hours_billed, invoice_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
    return query select * from vw_labor_invoice_item_display where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION vw_labor_invoice_item_display_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION vw_labor_invoice_item_display_sq(text, integer, text, text) TO GROUP legaltime_full;

