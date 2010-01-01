

-- Security Grants
GRANT ALL ON TABLE invoice TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_INVOICE', now(), 'Allows users to select invoice'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_INVOICE', now(), 'Allows users to add records to invoice');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_INVOICE', now(), 'Allows users to update records in invoice');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_INVOICE', now(), 'Allows users to delete records from invoice');
select * from security_privilege where priv_name in ('SELECT_INVOICE','INSERT_INVOICE','UPDATE_INVOICE','DELETE_INVOICE');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 37);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 38);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 39);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 40);

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: invoice_sq(text, integer, text, text)

-- DROP FUNCTION invoice_sq(text, integer, text, text);

CREATE OR REPLACE FUNCTION invoice_sq(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text)
  RETURNS SETOF invoice AS
$BODY$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_INVOICE' );
    end if;
-- prev_balance_due, invoice_total_amt, generated_dt, invoice_dt, last_update, customer_id, client_id, invoice_id    
    return query select * from invoice where client_id = clientId_;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION invoice_sq(text, integer, text, text) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION invoice_sq(text, integer, text, text) TO GROUP legaltime_full;

select * from invoice_sq('ALREADY_AUTH', 1, 'test', 'test');






--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function: invoice_bypk(text, integer, text, text ,    int4 ,    int4 )

-- DROP FUNCTION invoice_pybk(text, integer, text, text ,    int4 ,    int4 );

CREATE OR REPLACE FUNCTION invoice_bypk(alreadyAuth_ text, clientid_ integer, securityuserid_ text, sessionid_ text ,  invoiceId_  int4 ,  customerId_  int4 )
  RETURNS invoice AS
$BODY$
  Declare
    result invoice;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_INVOICE' );
    end if;
-- prev_balance_due, invoice_total_amt, generated_dt, invoice_dt, last_update, customer_id, client_id, invoice_id    
     select * into result from invoice where   invoice_id = invoiceId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION invoice_bypk(text, integer, text, text,    int4 ,    int4 ) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION invoice_bypk(text, integer, text, text,    int4 ,    int4 ) TO GROUP legaltime_full;



--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  invoice_iq(text, integer, text , float8, float8, date, date, int4, int4)

-- DROP FUNCTION invoice_iq( text, integer, text , float8, float8, date, date, int4, int4));

create or replace function invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , prevBalanceDue_ float8, invoiceTotalAmt_ float8, generatedDt_ date, invoiceDt_ date, customerId_ int4, invoiceId_ int4)
  returns invoice as
$body$
  declare
    newrow invoice;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_INVOICE' );
    end if;
    insert into invoice(client_id , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , invoice_id ) 
	values (clientid_ , prevBalanceDue_, invoiceTotalAmt_, generatedDt_, invoiceDt_, now(), customerId_, invoiceId_) 
	returning * into newrow;
      return newrow;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function invoice_iq(text, integer, text, text , float8, float8, date, date, int4, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION invoice_iq(text, integer, text, text , float8, float8, date, date, int4, int4) TO GROUP legaltime_full;

select * from invoice_iq('ALREADY_AUTH', 1, 'test', 'test'  , 1
 , 1
 , now()
 , now()
 , 1
 , 1
 );

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  invoice_uq(text, integer, text , float8, float8, date, date, timestamp, int4, int4)

-- DROP FUNCTION invoice_uq( text, integer, text , float8, float8, date, date, timestamp, int4, int4);


create or replace function invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text , prevBalanceDue_ float8, invoiceTotalAmt_ float8, generatedDt_ date, invoiceDt_ date, lastUpdate_ timestamp, customerId_ int4, invoiceId_ int4)
  returns invoice as
$body$
  declare
    updatedrow invoice;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_INVOICE' );
    end if;
	update invoice set
          prev_balance_due = prevBalanceDue_ 
         , invoice_total_amt = invoiceTotalAmt_ 
         , generated_dt = generatedDt_ 
         , invoice_dt = invoiceDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  , invoice_id = invoiceId_ 

	where          
	   invoice_id = invoiceId_          
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
alter function invoice_uq(text, integer, text, text , float8, float8, date, date, timestamp, int4, int4) owner to postgres;
GRANT EXECUTE ON FUNCTION invoice_uq(text, integer, text, text , float8, float8, date, date, timestamp, int4, int4) TO GROUP legaltime_full;


select * from invoice_uq('ALREADY_AUTH', 1, 'test', 'test'  , 'text' 
 , 'text' 
 , 'text' 
 , 'text' 
, <last_update>
, 1
, 1
 );


--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
-- Function:  invoice_dq(text, integer, text , invoiceId_ int4, customerId_ int4, timestamp)

-- DROP FUNCTION invoice_dq( text, integer, text , invoiceId_ int4, customerId_ int4, timestamp);

create or replace function invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text , invoiceId_ int4, customerId_ int4, lastUpdate_ timestamp  )
  returns boolean as
$body$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_INVOICE' );
    end if;
	delete from invoice where
	   invoice_id = invoiceId_ 
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
alter function invoice_dq(text, integer, text, text , invoiceId_ int4, customerId_ int4, timestamp) owner to postgres;
GRANT EXECUTE ON FUNCTION invoice_dq(text, integer, text, text , invoiceId_ int4, customerId_ int4, timestamp) TO GROUP legaltime_full;

--=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

 

create or replace function create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoiceDt_ date )
  returns integer as
$body$
  declare
    newInvoice invoice;
    eligibleLaborRegisterItem labor_register;
    expenseRegisterItem expense_register;
    invoiceAmt double precision;
    prevBalance double precision;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_INVOICE' );
    end if;
    --
    invoiceAmt =0;
    prevBalance = 0;
    select sum(tran_amt) into prevBalance from customer_account_register where customer_id = customerid_;
    insert into invoice(client_id, customer_id, last_update, invoice_dt, generated_dt, invoice_total_amt, prev_balance_due) values(clientid_,customerid_,now(),invoiceDt_,now(),null,prevBalance )
	returning * into newInvoice;

    update labor_register set invoice_id = newInvoice.invoice_id where customer_id = customerid_ and invoiceable = true and invoice_id is null;
    update expense_register set invoice_id = newInvoice.invoice_id where customer_id = customerid_ and invoiceable = true and invoice_id is null;
--Labor
    for eligibleLaborRegisterItem in select * from labor_register where invoice_id = newInvoice.invoice_id loop
    invoiceAmt := invoiceAmt + eligibleLaborRegisterItem.minute_count*eligibleLaborRegisterItem.bill_rate/60;
       INSERT INTO labor_invoice_item(client_id, customer_id, last_update, activity_dt, 				activity_description, 			user_id, 				invoice_id, 		hours_billed,				bill_rate)
			      VALUES (clientid_,    customerid_, now(), 	eligibleLaborRegisterItem.activity_date, eligibleLaborRegisterItem.description, eligibleLaborRegisterItem.user_id, newInvoice.invoice_id, eligibleLaborRegisterItem.minute_count/60.0, eligibleLaborRegisterItem.bill_rate);
     end loop;

--Admin Expenses
    for expenseRegisterItem in select * from expense_register where invoice_id = newInvoice.invoice_id loop
    invoiceAmt := invoiceAmt + expenseRegisterItem.amount;
       INSERT INTO expense_invoice_item(client_id, customer_id, last_update, expense_dt, 				expense_description, 		invoice_id, 		amount)
			      VALUES (clientid_,    customerid_, now(), 	expenseRegisterItem.expense_dt, expenseRegisterItem.description, newInvoice.invoice_id, expenseRegisterItem.amount);
     end loop;
 
     update invoice set invoice_total_amt  = invoiceAmt where invoice_id = newInvoice.invoice_id;

     INSERT INTO customer_account_register( client_id, customer_id, last_update,  effective_dt, description, 				tran_amt, tran_type)
				 VALUES (clientid_,    customerid_, now(),        now(), 	'Invoice #' || newInvoice.invoice_id, invoiceAmt,'INVCE');

      return newInvoice.invoice_id;
  end;
$body$
  language 'plpgsql' volatile
  cost 100;
alter function create_customer_invoice_all_eligible(text,  integer, text,text, integer,date ) owner to postgres;
--GRANT EXECUTE ON FUNCTION invoice_iq(text, integer, text, text , float8, float8, date, date, int4, int4) TO GROUP legaltime_full;


select * from create_customer_invoice_all_eligible('ALREADY_AUTH', 1, 'userid', 'session' , 26, '2009-10-10');



--clear tables for testing
update labor_register set invoice_id = null;
delete  from labor_invoice_item;
update expense_register set invoice_id = null;
delete  from expense_invoice_item;
delete  from invoice;

--test points
select * from invoice
select * from labor_invoice_item;
select * from labor_register
select * from expense_invoice_item;
select * from expense_register
select * from customer_account_register




INSERT INTO expense_register( client_id, customer_id, last_update, description, 
            amount, invoice_id, invoiceable, expense_dt)
    VALUES (1, 26, now(), 'test expense',     100, null, true, now());


