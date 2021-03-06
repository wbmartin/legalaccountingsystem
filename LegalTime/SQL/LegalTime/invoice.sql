

-- Security Grants
GRANT ALL ON TABLE invoice TO GROUP legaltime_full;
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'SELECT_INVOICE', now(), 'Allows users to select invoice'); 
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'INSERT_INVOICE', now(), 'Allows users to add records to invoice');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UPDATE_INVOICE', now(), 'Allows users to update records in invoice');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'DELETE_INVOICE', now(), 'Allows users to delete records from invoice');
INSERT INTO security_privilege(client_id,  priv_name, last_update, description)    VALUES (1, 'UNWIND_INVOICE', now(), 'Allows users to unwind created invoices');

select * from security_privilege where priv_name in ('SELECT_INVOICE','INSERT_INVOICE','UPDATE_INVOICE','DELETE_INVOICE','UNWIND_INVOICE');
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 37);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 38);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 39);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 40);
INSERT INTO security_profile_grant(client_id, security_profile_id, security_privilege_id) VALUES (1, 1, 57);

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


    
    
    
    
  select * from unwind_invoice('ALREADY_AUTH', 1,'','',36)

select * from invoice where invoice_id =36
select * from labor_register where invoice_id =36
select * from expense_register where invoice_id =36
select * from labor_invoice_item where invoice_id =36
select * from expense_invoice_item where invoice_id =36
select * from customer_account_register where description like '%36%'





CREATE OR REPLACE FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer)
  RETURNS boolean AS
$BODY$
  declare
   originalInvoice invoice;
   
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'UNWIND_INVOICE' );
    end if;
    --
    select  * into originalInvoice from invoice where invoice_id = invoiceid_;
    update invoice set invoice_total_amt = 0, last_update = now() where invoice_id = invoiceid_;


    update labor_register set invoice_id = null where invoice_id = invoiceid_;
    update expense_register set invoice_id = null where invoice_id = invoiceid_;
--Labor
    update labor_invoice_item set activity_description = 'REVERSED ' || activity_description || ' ORIGINAL HOURS: ' || hours_billed, hours_billed = 0 where  invoice_id = invoiceid_;
   
--Admin Expenses
    update expense_invoice_item set expense_description = 'REVERSED ' ||expense_description || ' ORIGINAL EXPENSE' || amount, amount =0 where  invoice_id = invoiceid_;
 INSERT INTO customer_account_register( client_id, customer_id, last_update,  effective_dt, description, 				tran_amt, tran_type, ref_id)
				 VALUES (clientid_,    originalInvoice.customer_id, now(),        now(), 	'Invoice REVERSAL #' || invoiceid_, originalInvoice.invoice_total_amt * -1 ,'INVR', originalInvoice.invoice_id);

      return true;
  end;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION unwind_invoice(text, integer, text, text, integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION unwind_invoice(text, integer, text, text, integer) TO public;
GRANT EXECUTE ON FUNCTION unwind_invoice(text, integer, text, text, integer) TO postgres;
GRANT EXECUTE ON FUNCTION unwind_invoice(text, integer, text, text, integer) TO legaltime_full;




-- Function: create_customer_invoice_all_eligible(text, integer, text, text, integer, date)

-- DROP FUNCTION create_customer_invoice_all_eligible(text, integer, text, text, integer, date);

CREATE OR REPLACE FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date)
  RETURNS integer AS
$BODY$
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

     INSERT INTO customer_account_register( client_id, customer_id, last_update,  effective_dt, description, 				tran_amt, tran_type, ref_id)
				 VALUES (clientid_,    customerid_, now(),        now(), 	'Invoice #' || newInvoice.invoice_id, invoiceAmt,'INVCE', newInvoice.invoice_id);

      return newInvoice.invoice_id;
  end;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION create_customer_invoice_all_eligible(text, integer, text, text, integer, date) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION create_customer_invoice_all_eligible(text, integer, text, text, integer, date) TO public;
GRANT EXECUTE ON FUNCTION create_customer_invoice_all_eligible(text, integer, text, text, integer, date) TO postgres;
GRANT EXECUTE ON FUNCTION create_customer_invoice_all_eligible(text, integer, text, text, integer, date) TO legaltime_full;








CREATE OR REPLACE FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceDt_ date)
  RETURNS text AS
$BODY$
  Declare
  hourlyCustomer customer;
  invoiceList text;
  newInvoiceId integer;
  Begin
  invoiceList:='';
  
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_INVOICE' );
    end if;
	for hourlyCustomer in select customer.* from customer where  bill_type ='HOURLY'  and active_yn ='Y' loop
	   select * into newInvoiceId from create_customer_invoice_all_eligible('ALREADY_AUTH', clientid_ , securityuserid_ , sessionid_ , hourlyCustomer.customer_id, invoicedt_ ) ;
	   	
	   invoiceList :=invoiceList || newInvoiceId ||',';  
	end loop;

    return trim(trailing ',' from invoiceList);
  End;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100
;
ALTER FUNCTION invoice_all_hourly_clients(text, integer, text, text, date) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION invoice_all_hourly_clients(text, integer, text, text, date) TO public;
GRANT EXECUTE ON FUNCTION invoice_all_hourly_clients(text, integer, text, text, date) TO postgres;
GRANT EXECUTE ON FUNCTION invoice_all_hourly_clients(text, integer, text, text, date) TO legaltime_full;

select * from labor_register order by last_update desc



select * from invoice_all_hourly_clients('ALREADY_AUTH',1,'bmartin','','2010-1-10');

select * from labor_register;
update labor_register set invoice_id = null









