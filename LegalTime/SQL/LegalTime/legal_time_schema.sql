--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- Name: add_default_bill_rates_for_customer(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
declare
row user_info;
begin
 if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'INSERT_CUSTOMERBILLRATE' );
    end if;
FOR row in select * from user_info where client_id = clientid_ LOOP
 insert into customer_bill_rate(client_id , bill_rate , last_update , user_id , customer_id ) 
	values (clientid_ , row.default_bill_rate, now(), row.user_id, customerId_);
END LOOP;
return true;
end;
$$;


ALTER FUNCTION public.add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) OWNER TO postgres;

--
-- Name: assess_all_monthly_charges(text, integer, text, text, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) RETURNS integer
    LANGUAGE plpgsql
    AS $$
  Declare
  monthlyCustomer customer;
  customerCount integer;
  Begin
  customerCount:=0;
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'INSERT_LABORREGISTER' );
    end if;
	for monthlyCustomer in select * from customer where  bill_type ='MONTHLY'  and monthly_bill_rate >0 and active_yn ='Y' loop
	   insert into labor_register(client_id , user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id ) 
	values (clientid_ , securityuserid_, null, monthlyCustomer.monthly_bill_rate, true, assessDt_, assessDt_, assessDt_, 60, 'Monthly Retainer Fee', now(), monthlyCustomer.customer_id) ;
	customerCount:=customerCount+1;

	end loop;

    return customerCount;
  End;
$$;


ALTER FUNCTION public.assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) OWNER TO postgres;

--
-- Name: assess_all_monthly_charges_and_invoice(text, integer, text, text, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) RETURNS text
    LANGUAGE plpgsql
    AS $$
  Declare
    monthlyCustomer customer;
    customerCount integer;
    invoiceList text;
    newInvoiceId integer;
  Begin
  invoiceList:='';
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'INSERT_LABORREGISTER' );
    end if;
	for monthlyCustomer in select * from customer where  bill_type ='MONTHLY'  and monthly_bill_rate >0 and active_yn ='Y' loop
	   insert into labor_register(client_id , user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id ) 
		values (clientid_ , securityuserid_, null, monthlyCustomer.monthly_bill_rate, true, assessDt_, assessDt_, assessDt_, 60, 'Monthly Retainer Fee', now(), monthlyCustomer.customer_id) ;

	   select * into newInvoiceId from create_customer_invoice_all_eligible('ALREADY_AUTH', clientid_ , securityuserid_ , sessionid_ , monthlyCustomer.customer_id, assessdt_, false ) ;
	   	
	   invoiceList :=invoiceList || newInvoiceId ||',';  

	end loop;

    return trim(trailing ',' from invoiceList);
  End;
$$;


ALTER FUNCTION public.assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) OWNER TO postgres;

--
-- Name: create_customer_invoice_all_eligible(text, integer, text, text, integer, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) RETURNS integer
    LANGUAGE plpgsql
    AS $$
  declare
  begin
  -- this is a wrapper function to preserve the integrity of the the default to invoice expenses, note the true as the last argument.
  return create_customer_invoice_all_eligible(alreadyauth_ , clientid_ , securityuserid_ , sessionid_ , customerid_ , invoicedt_ , true);
  end;

$$;


ALTER FUNCTION public.create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) OWNER TO postgres;

--
-- Name: create_customer_invoice_all_eligible(text, integer, text, text, integer, date, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) RETURNS integer
    LANGUAGE plpgsql
    AS $$
  declare
    newInvoice invoice;
    eligibleLaborRegisterItem labor_register;
    expenseRegisterItem expense_register;
    invoiceAmt double precision;
    prevBalance double precision;
    laborCount integer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_INVOICE' );
    end if;
    --
    invoiceAmt =0;
    prevBalance = 0;
    select count(*) into laborCount from labor_register where customer_id = customerid_ and invoiceable = true and invoice_id is null;

    if laborCount =0 then
    return 0;
    end if;
    
    select sum(tran_amt) into prevBalance from customer_account_register where customer_id = customerid_;
    if prevBalance is null then
	prevBalance=0;
    end if;
    insert into invoice(client_id, customer_id, last_update, invoice_dt, generated_dt, invoice_total_amt, prev_balance_due) values(clientid_,customerid_,now(),invoiceDt_,now(),null,prevBalance )
	returning * into newInvoice;

    update labor_register set invoice_id = newInvoice.invoice_id where customer_id = customerid_ and invoiceable = true and invoice_id is null;
    if invoiceExpenses then
	update expense_register set invoice_id = newInvoice.invoice_id where customer_id = customerid_ and invoiceable = true and invoice_id is null;
    end if;
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
$$;


ALTER FUNCTION public.create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: customer_account_register; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE customer_account_register (
    customer_account_register_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    effective_dt date,
    description text,
    tran_amt double precision,
    tran_type character varying(5),
    ref_id integer
);


ALTER TABLE public.customer_account_register OWNER TO postgres;

--
-- Name: customer_account_register_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) RETURNS customer_account_register
    LANGUAGE plpgsql
    AS $$
  Declare
    result customer_account_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERACCOUNTREGISTER' );
    end if;
-- ref_id, tran_type, tran_amt, description, effective_dt, last_update, customer_id, client_id, customer_account_register_id    
     select * into result from customer_account_register where   customer_account_register_id = customerAccountRegisterId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_account_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMERACCOUNTREGISTER' );
    end if;
	delete from customer_account_register where
	   customer_account_register_id = customerAccountRegisterId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: customer_account_register_iq(text, integer, text, text, character varying, double precision, text, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) RETURNS customer_account_register
    LANGUAGE plpgsql
    AS $$
  declare
    newrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMERACCOUNTREGISTER' );
    end if;
    insert into customer_account_register(client_id , tran_type , tran_amt , description , effective_dt , last_update , customer_id ) 
	values (clientid_ , tranType_, tranAmt_, description_, effectiveDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_account_register_iq(text, integer, text, text, integer, character varying, double precision, text, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) RETURNS customer_account_register
    LANGUAGE plpgsql
    AS $$
  declare
    newrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMERACCOUNTREGISTER' );
    end if;
    insert into customer_account_register(client_id , ref_id , tran_type , tran_amt , description , effective_dt , last_update , customer_id ) 
	values (clientid_ , refId_, tranType_, tranAmt_, description_, effectiveDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_account_register_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF customer_account_register
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERACCOUNTREGISTER' );
    end if;
-- ref_id, tran_type, tran_amt, description, effective_dt, last_update, customer_id, client_id, customer_account_register_id    
    return query select * from customer_account_register where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: customer_account_register_uq(text, integer, text, text, character varying, double precision, text, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) RETURNS customer_account_register
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMERACCOUNTREGISTER' );
    end if;
	update customer_account_register set
          tran_type = tranType_ 
         , tran_amt = tranAmt_ 
         , description = description_ 
         , effective_dt = effectiveDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   customer_account_register_id = customerAccountRegisterId_          
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
$_$;


ALTER FUNCTION public.customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) OWNER TO postgres;

--
-- Name: customer_account_register_uq(text, integer, text, text, integer, character varying, double precision, text, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) RETURNS customer_account_register
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow customer_account_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMERACCOUNTREGISTER' );
    end if;
	update customer_account_register set
          ref_id = refId_ 
         , tran_type = tranType_ 
         , tran_amt = tranAmt_ 
         , description = description_ 
         , effective_dt = effectiveDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   customer_account_register_id = customerAccountRegisterId_          
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
$_$;


ALTER FUNCTION public.customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) OWNER TO postgres;

--
-- Name: customer_bill_rate; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE customer_bill_rate (
    customer_bill_rate_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    user_id text NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    bill_rate double precision
);


ALTER TABLE public.customer_bill_rate OWNER TO postgres;

--
-- Name: customer_bill_rate_bypk(text, integer, text, text, integer, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) RETURNS customer_bill_rate
    LANGUAGE plpgsql
    AS $$
  Declare
    result customer_bill_rate;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, userId_,sessionId_) ;
    	perform isUserAuthorized(clientId_,userId_,'SELECT_CUSTOMERBILLRATE' );
    end if;
-- bill_rate, last_update, user_id, customer_id, client_id, customer_bill_rate_id    
     select * into result from customer_bill_rate where   customer_bill_rate_id = customerBillRateId_  and client_id = clientId_  and customer_id = customerId_  and user_id = userId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) OWNER TO postgres;

--
-- Name: customer_bill_rate_bypk(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) RETURNS customer_bill_rate
    LANGUAGE plpgsql
    AS $$
  Declare
    result customer_bill_rate;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERBILLRATE' );
    end if;
-- bill_rate, last_update, user_id, customer_id, client_id, customer_bill_rate_id    
     select * into result from customer_bill_rate where   customer_bill_rate_id = customerBillRateId_  and client_id = clientId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) OWNER TO postgres;

--
-- Name: customer_bill_rate_dq(text, integer, text, text, integer, integer, text, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMERBILLRATE' );
    end if;
	delete from customer_bill_rate where
	   customer_bill_rate_id = customerBillRateId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	  and user_id = userId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$$;


ALTER FUNCTION public.customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: customer_bill_rate_dq(text, integer, text, text, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMERBILLRATE' );
    end if;
	delete from customer_bill_rate where
	   customer_bill_rate_id = customerBillRateId_ 
	  and client_id = clientId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$$;


ALTER FUNCTION public.customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: customer_bill_rate_iq(text, integer, text, text, double precision, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) RETURNS customer_bill_rate
    LANGUAGE plpgsql
    AS $$
  declare
    newrow customer_bill_rate;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMERBILLRATE' );
    end if;
    insert into customer_bill_rate(client_id , bill_rate , last_update , user_id , customer_id ) 
	values (clientid_ , billRate_, now(), userId_, customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_bill_rate_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF customer_bill_rate
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMERBILLRATE' );
    end if;
-- bill_rate, last_update, user_id, customer_id, client_id, customer_bill_rate_id    
    return query select * from customer_bill_rate where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: customer_bill_rate_uq(text, integer, text, text, double precision, timestamp without time zone, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) RETURNS customer_bill_rate
    LANGUAGE plpgsql
    AS $$
  declare
    updatedrow customer_bill_rate;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMERBILLRATE' );
    end if;
	update customer_bill_rate set
          bill_rate = billRate_ 
         , last_update = now()
         , user_id = userId_ 
         , customer_id = customerId_ 
                  
	where          
	   customer_bill_rate_id = customerBillRateId_          
	  and client_id = clientId_            
	   and   last_update = lastUpdate_
	returning * into updatedrow;

	if found then
	  return updatedrow;
	else 
	  raise exception 'Update Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$$;


ALTER FUNCTION public.customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE customer (
    customer_id integer NOT NULL,
    client_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    first_name character varying(50),
    last_name character varying(50),
    address text,
    city character varying(50),
    state character(2),
    zip character varying(10),
    work_phone character varying(30),
    home_phone character varying(30),
    fax character varying(30),
    email character varying(100),
    client_since_dt date,
    note text,
    bill_type character varying(25),
    monthly_bill_rate double precision,
    active_yn character(1),
    mortgage_amount double precision,
    contingency_rate double precision,
    suspend_invoice character(1) DEFAULT 'N'::bpchar
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_bypk(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) RETURNS customer
    LANGUAGE plpgsql
    AS $$
  Declare
    result customer;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
     select * into result from customer where   customer_id = customerId_  and client_id = clientId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_dq(text, integer, text, text, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_CUSTOMER' );
    end if;
	delete from customer where
	   customer_id = customerId_ 
	  and client_id = clientId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$$;


ALTER FUNCTION public.customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: customer_invoiced_sq(text, integer, text, text, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) RETURNS SETOF customer
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
    return query select distinct customer.* 
		from  customer left join invoice  on customer.customer_id =invoice.customer_id 
		where customer.client_id = clientId_ and invoice.invoice_dt = invoiceDt_;
  End;
$$;


ALTER FUNCTION public.customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) OWNER TO postgres;

--
-- Name: customer_iq(text, integer, text, text, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) RETURNS customer
    LANGUAGE plpgsql
    AS $$
--THIS PROCEDURE HAS BEEN CUSTOMIZED
--  * Adds default bill rates upon insert
  declare
    newrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMER' );
    end if;
    insert into customer(client_id , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update ) 
	values (clientid_ , contingencyRate_, mortgageAmount_, activeYn_, monthlyBillRate_, billType_, note_, clientSinceDt_, email_, fax_, homePhone_, workPhone_, zip_, state_, city_, address_, lastName_, firstName_, now()) 
	returning * into newrow;
	perform add_default_bill_rates_for_customer('ALREADY_AUTH', clientid_, securityuserid_, sessionid_, newrow.customer_id);
      return newrow;
  end;
$$;


ALTER FUNCTION public.customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) OWNER TO postgres;

--
-- Name: customer_iq(text, integer, text, text, character, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) RETURNS customer
    LANGUAGE plpgsql
    AS $$
--THIS PROCEDURE HAS BEEN CUSTOMIZED
--  * Adds default bill rates upon insert
  declare
    newrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_CUSTOMER' );
    end if;
    insert into customer(client_id , suspend_invoice, contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update ) 
	values (clientid_ , suspendinvoice_, contingencyRate_, mortgageAmount_, activeYn_, monthlyBillRate_, billType_, note_, clientSinceDt_, email_, fax_, homePhone_, workPhone_, zip_, state_, city_, address_, lastName_, firstName_, now()) 
	returning * into newrow;
	perform add_default_bill_rates_for_customer('ALREADY_AUTH', clientid_, securityuserid_, sessionid_, newrow.customer_id);
      return newrow;
  end;
$$;


ALTER FUNCTION public.customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) OWNER TO postgres;

--
-- Name: customer_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF customer
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_CUSTOMER' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, note, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, last_name, first_name, last_update, client_id, customer_id    
    return query select * from customer where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: customer_uq(text, integer, text, text, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying, timestamp without time zone, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) RETURNS customer
    LANGUAGE plpgsql
    AS $$
  declare
    updatedrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMER' );
    end if;
	update customer set
          contingency_rate = contingencyRate_ 
         , mortgage_amount = mortgageAmount_ 
         , active_yn = activeYn_ 
         , monthly_bill_rate = monthlyBillRate_ 
         , bill_type = billType_ 
         , note = note_ 
         , client_since_dt = clientSinceDt_ 
         , email = email_ 
         , fax = fax_ 
         , home_phone = homePhone_ 
         , work_phone = workPhone_ 
         , zip = zip_ 
         , state = state_ 
         , city = city_ 
         , address = address_ 
         , last_name = lastName_ 
         , first_name = firstName_ 
         , last_update = now()
                  
	where          
	   customer_id = customerId_          
	  and client_id = clientId_            
	   and   last_update = lastUpdate_
	returning * into updatedrow;

	if found then
	  return updatedrow;
	else 
	  raise exception 'Update Failed - The record may have been changed or deleted before the attempt.';
	end if;

  end;
$$;


ALTER FUNCTION public.customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) OWNER TO postgres;

--
-- Name: customer_uq(text, integer, text, text, character, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying, timestamp without time zone, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) RETURNS customer
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow customer;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_CUSTOMER' );
    end if;
	update customer set
          suspend_invoice = suspendInvoice_ 
         , contingency_rate = contingencyRate_ 
         , mortgage_amount = mortgageAmount_ 
         , active_yn = activeYn_ 
         , monthly_bill_rate = monthlyBillRate_ 
         , bill_type = billType_ 
         , note = note_ 
         , client_since_dt = clientSinceDt_ 
         , email = email_ 
         , fax = fax_ 
         , home_phone = homePhone_ 
         , work_phone = workPhone_ 
         , zip = zip_ 
         , state = state_ 
         , city = city_ 
         , address = address_ 
         , last_name = lastName_ 
         , first_name = firstName_ 
         , last_update = now()
                  
	where          
	   customer_id = customerId_          
	  and client_id = clientId_            
	   and   last_update = lastUpdate_
	returning * into updatedrow;

	if found then
	  return updatedrow;
	else 
	  raise exception 'Update Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) OWNER TO postgres;

--
-- Name: expense_invoice_item; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE expense_invoice_item (
    expense_invoice_item_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    expense_dt date,
    expense_description text,
    amount double precision,
    invoice_id integer
);


ALTER TABLE public.expense_invoice_item OWNER TO postgres;

--
-- Name: expense_invoice_item_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) RETURNS expense_invoice_item
    LANGUAGE plpgsql
    AS $$
  Declare
    result expense_invoice_item;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEINVOICEITEM' );
    end if;
-- invoice_id, amount, expense_description, expense_dt, last_update, customer_id, client_id, expense_invoice_item_id    
     select * into result from expense_invoice_item where   expense_invoice_item_id = expenseInvoiceItemId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: expense_invoice_item_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_EXPENSEINVOICEITEM' );
    end if;
	delete from expense_invoice_item where
	   expense_invoice_item_id = expenseInvoiceItemId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: expense_invoice_item_iq(text, integer, text, text, integer, double precision, text, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) RETURNS expense_invoice_item
    LANGUAGE plpgsql
    AS $$
  declare
    newrow expense_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_EXPENSEINVOICEITEM' );
    end if;
    insert into expense_invoice_item(client_id , invoice_id , amount , expense_description , expense_dt , last_update , customer_id ) 
	values (clientid_ , invoiceId_, amount_, expenseDescription_, expenseDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: expense_invoice_item_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF expense_invoice_item
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEINVOICEITEM' );
    end if;
-- invoice_id, amount, expense_description, expense_dt, last_update, customer_id, client_id, expense_invoice_item_id    
    return query select * from expense_invoice_item where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: expense_invoice_item_uq(text, integer, text, text, integer, double precision, text, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) RETURNS expense_invoice_item
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow expense_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_EXPENSEINVOICEITEM' );
    end if;
	update expense_invoice_item set
          invoice_id = invoiceId_ 
         , amount = amount_ 
         , expense_description = expenseDescription_ 
         , expense_dt = expenseDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   expense_invoice_item_id = expenseInvoiceItemId_          
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
$_$;


ALTER FUNCTION public.expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) OWNER TO postgres;

--
-- Name: expense_register; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE expense_register (
    expense_register_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    description text,
    amount double precision,
    invoice_id integer,
    invoiceable boolean,
    expense_dt date
);


ALTER TABLE public.expense_register OWNER TO postgres;

--
-- Name: expense_register_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) RETURNS expense_register
    LANGUAGE plpgsql
    AS $$
  Declare
    result expense_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEREGISTER' );
    end if;
-- invoiceable, invoice_id, amount, description, last_update, customer_id, client_id, expense_register_id    
     select * into result from expense_register where   expense_register_id = expenseRegisterId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: expense_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_EXPENSEREGISTER' );
    end if;
	delete from expense_register where
	   expense_register_id = expenseRegisterId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: expense_register_iq(text, integer, text, text, date, boolean, integer, double precision, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) RETURNS expense_register
    LANGUAGE plpgsql
    AS $$
  declare
    newrow expense_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_EXPENSEREGISTER' );
    end if;
    insert into expense_register(client_id , expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id ) 
	values (clientid_ , expenseDt_, invoiceable_, invoiceId_, amount_, description_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) OWNER TO postgres;

--
-- Name: expense_register_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF expense_register
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_EXPENSEREGISTER' );
    end if;
-- invoiceable, invoice_id, amount, description, last_update, customer_id, client_id, expense_register_id    
    return query select * from expense_register where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: expense_register_uq(text, integer, text, text, date, boolean, integer, double precision, text, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) RETURNS expense_register
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow expense_register;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_EXPENSEREGISTER' );
    end if;
	update expense_register set
          expense_dt = expenseDt_ 
         , invoiceable = invoiceable_ 
         , invoice_id = invoiceId_ 
         , amount = amount_ 
         , description = description_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   expense_register_id = expenseRegisterId_          
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
$_$;


ALTER FUNCTION public.expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) OWNER TO postgres;

--
-- Name: followup; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE followup (
    followup_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer,
    last_update timestamp(3) without time zone,
    due_dt date,
    open_dt date,
    close_dt date,
    followup_description text,
    assigned_user_id text
);


ALTER TABLE public.followup OWNER TO postgres;

--
-- Name: followup_bypk(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) RETURNS followup
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) OWNER TO postgres;

--
-- Name: followup_dq(text, integer, text, text, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: followup_iq(text, integer, text, text, text, text, date, date, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) RETURNS followup
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: followup_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF followup
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_FOLLOWUP' );
    end if;
-- assigned_user_id, followup_description, close_dt, open_dt, due_dt, last_update, customer_id, client_id, followup_id    
    return query select * from followup where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: followup_uq(text, integer, text, text, text, text, date, date, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) RETURNS followup
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) OWNER TO postgres;

--
-- Name: security_user; Type: TABLE; Schema: public; Owner: legaltime_user; Tablespace: 
--

CREATE TABLE security_user (
    client_id integer NOT NULL,
    user_id text NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    security_profile_id integer NOT NULL,
    password_enc text,
    session_id text,
    session_expire_dt timestamp without time zone,
    active_yn character(1)
);


ALTER TABLE public.security_user OWNER TO legaltime_user;

--
-- Name: initsession(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION initsession(userid_ text, password_ text) RETURNS security_user
    LANGUAGE plpgsql
    AS $$
 Declare
	sessionId_  character varying;
	result security_user ;
	clientId  integer;
 Begin
 select client_id into clientId from security_user where user_id = userid_ and  password_enc = md5(password_);
 --client_id, user_id, password_enc, session_id, session_expire_dt, active_yn, last_update, security_profile_id
 select * into result from initsession(clientId, userid_ , password_ );
 return result;
  
 End;
$$;


ALTER FUNCTION public.initsession(userid_ text, password_ text) OWNER TO postgres;

--
-- Name: initsession(integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION initsession(clientid_ integer, userid_ text, password_ text) RETURNS security_user
    LANGUAGE plpgsql
    AS $$
 Declare
	sessionId_  character varying;
	result security_user ;
 Begin
  sessionId_ := to_hex(((random() * 1000)^3)::Integer ) || to_hex(((random() * 1000)^3)::Integer )
	|| to_hex(((random() * 1000)^3)::Integer )  || to_hex(((random() * 1000)^3)::Integer );
  update security_user 
  set session_id =sessionId_ , session_expire_dt = now()+ '20 min'
  where client_id = clientId_ 
    and user_id = userID_ 
    and password_enc = md5(password_)
    returning * into result ;
    result.password_enc ='';
  if found then
    return result ;
  else 
    raise exception 'Invalid Username or Password -- Access Denied';
  end if;
 End;
$$;


ALTER FUNCTION public.initsession(clientid_ integer, userid_ text, password_ text) OWNER TO postgres;

--
-- Name: invoice_all_hourly_clients(text, integer, text, text, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
	   	
	   if newInvoiceId >0 then	
		invoiceList :=invoiceList || newInvoiceId ||','; 
	   end if;  
	end loop;

    return trim(trailing ',' from invoiceList);
  End;
$$;


ALTER FUNCTION public.invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) OWNER TO postgres;

--
-- Name: invoice; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE invoice (
    invoice_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    invoice_dt date,
    generated_dt date,
    invoice_total_amt double precision,
    prev_balance_due double precision,
    reversed boolean DEFAULT false
);


ALTER TABLE public.invoice OWNER TO postgres;

--
-- Name: invoice_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) RETURNS invoice
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: invoice_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
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
$_$;


ALTER FUNCTION public.invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: invoice_iq(text, integer, text, text, double precision, double precision, date, date, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) RETURNS invoice
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) OWNER TO postgres;

--
-- Name: invoice_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF invoice
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_INVOICE' );
    end if;
-- prev_balance_due, invoice_total_amt, generated_dt, invoice_dt, last_update, customer_id, client_id, invoice_id    
    return query select * from invoice where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: invoice_uq(text, integer, text, text, double precision, double precision, date, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) RETURNS invoice
    LANGUAGE plpgsql
    AS $_$
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
$_$;


ALTER FUNCTION public.invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) OWNER TO postgres;

--
-- Name: issessionvalid(integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) RETURNS text
    LANGUAGE plpgsql
    AS $$
Declare
    
Begin

  update security_user 
  set session_expire_dt = now() + '20 min'
  where client_id = clientId_ 
    and user_id = userID_ 
    and session_id = sessionId_;
  if found then
    return 'Valid Session' ;
  else 
    raise exception 'Invalid Session -- Access Denied';
  end if;

End;
$$;


ALTER FUNCTION public.issessionvalid(clientid_ integer, userid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: isuserauthorized(integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
Declare
  rowCount integer;
 Begin
   select count(*) into rowCount 
   from vw_user_grant 
   where client_id =clientId_ and user_id =userId_ and priv_name = tran_;
   
   if rowCount <> 1 then
	raise exception 'ACCESS DENIED-- user is not authorized for this transaction';
   end if;
   return true;
 End;
$$;


ALTER FUNCTION public.isuserauthorized(clientid_ integer, userid_ text, tran_ text) OWNER TO postgres;

--
-- Name: labor_invoice_item; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE labor_invoice_item (
    labor_invoice_item_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    activity_dt date,
    activity_description text,
    user_id text,
    invoice_id integer NOT NULL,
    hours_billed double precision,
    bill_rate double precision
);


ALTER TABLE public.labor_invoice_item OWNER TO postgres;

--
-- Name: labor_invoice_item_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) RETURNS labor_invoice_item
    LANGUAGE plpgsql
    AS $$
  Declare
    result labor_invoice_item;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- bill_rate, hours_billed, invoice_id, user_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
     select * into result from labor_invoice_item where   labor_invoice_item_id = laborInvoiceItemId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: labor_invoice_item_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_LABORINVOICEITEM' );
    end if;
	delete from labor_invoice_item where
	   labor_invoice_item_id = laborInvoiceItemId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: labor_invoice_item_iq(text, integer, text, text, double precision, double precision, integer, text, text, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) RETURNS labor_invoice_item
    LANGUAGE plpgsql
    AS $$
  declare
    newrow labor_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_LABORINVOICEITEM' );
    end if;
    insert into labor_invoice_item(client_id , bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id ) 
	values (clientid_ , billRate_, hoursBilled_, invoiceId_, userId_, activityDescription_, activityDt_, now(), customerId_) 
	returning * into newrow;
      return newrow;
  end;
$$;


ALTER FUNCTION public.labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: labor_invoice_item_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF labor_invoice_item
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- bill_rate, hours_billed, invoice_id, user_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
    return query select * from labor_invoice_item where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: labor_invoice_item_uq(text, integer, text, text, double precision, double precision, integer, text, text, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) RETURNS labor_invoice_item
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow labor_invoice_item;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_LABORINVOICEITEM' );
    end if;
	update labor_invoice_item set
          bill_rate = billRate_ 
         , hours_billed = hoursBilled_ 
         , invoice_id = invoiceId_ 
         , user_id = userId_ 
         , activity_description = activityDescription_ 
         , activity_dt = activityDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   labor_invoice_item_id = laborInvoiceItemId_          
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
$_$;


ALTER FUNCTION public.labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) OWNER TO postgres;

--
-- Name: labor_register; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE labor_register (
    labor_register_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    description text,
    minute_count integer,
    start_time timestamp(3) without time zone,
    end_time timestamp(3) without time zone,
    activity_date date,
    invoiceable boolean,
    bill_rate double precision,
    invoice_id integer,
    user_id text
);


ALTER TABLE public.labor_register OWNER TO postgres;

--
-- Name: labor_register_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) RETURNS labor_register
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: labor_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
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
$_$;


ALTER FUNCTION public.labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: labor_register_iq(text, integer, text, text, text, integer, double precision, boolean, date, timestamp without time zone, timestamp without time zone, integer, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) RETURNS labor_register
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) OWNER TO postgres;

--
-- Name: labor_register_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF labor_register
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORREGISTER' );
    end if;
-- user_id, invoice_id, bill_rate, invoiceable, activity_date, end_time, start_time, minute_count, description, last_update, customer_id, client_id, labor_register_id    
    return query select * from labor_register where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: labor_register_uq(text, integer, text, text, text, integer, double precision, boolean, date, timestamp without time zone, timestamp without time zone, integer, text, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) RETURNS labor_register
    LANGUAGE plpgsql
    AS $_$
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
$_$;


ALTER FUNCTION public.labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) OWNER TO postgres;

--
-- Name: payment_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE payment_log (
    payment_log_id integer NOT NULL,
    client_id integer NOT NULL,
    customer_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    effective_dt date,
    description text,
    amount double precision,
    invoice_id integer,
    customer_account_register_id integer
);


ALTER TABLE public.payment_log OWNER TO postgres;

--
-- Name: payment_log_bypk(text, integer, text, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) RETURNS payment_log
    LANGUAGE plpgsql
    AS $$
  Declare
    result payment_log;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_PAYMENTLOG' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
     select * into result from payment_log where   payment_log_id = paymentLogId_  and client_id = clientId_  and customer_id = customerId_ ;
     return result;
  End;
$$;


ALTER FUNCTION public.payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) OWNER TO postgres;

--
-- Name: payment_log_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
  declare
    
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, userid_,sessionid_) ;
    	perform isuserauthorized(clientid_,userid_,'DELETE_PAYMENTLOG' );
    end if;
	delete from payment_log where
	   payment_log_id = paymentLogId_ 
	  and client_id = clientId_ 
	  and customer_id = customerId_ 
	   and   last_update = lastUpdate_;

	if found then
	  return true;
	else 
	  raise exception 'Delete Failed for $TABLENAME- The record may have been changed or deleted before the attempt.';
	end if;

  end;
$_$;


ALTER FUNCTION public.payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: payment_log_iq(text, integer, text, text, integer, integer, double precision, text, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) RETURNS payment_log
    LANGUAGE plpgsql
    AS $$
  declare
    newrow payment_log;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'INSERT_PAYMENTLOG' );
    end if;
    insert into payment_log(client_id , customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id ) 
	values (clientid_ , customerAccountRegisterId_, invoiceId_, amount_, description_, effectiveDt_, now(), customerId_) 
	returning * into newrow;
   insert into customer_account_register(client_id , tran_type , tran_amt , description , effective_dt , last_update , customer_id, ref_id ) 
	values (clientid_ , 'PAY', amount_*-1, description_, effectiveDt_, now(), customerId_, newrow.payment_log_id);
	
      return newrow;
  end;
$$;


ALTER FUNCTION public.payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) OWNER TO postgres;

--
-- Name: payment_log_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF payment_log
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_PAYMENTLOG' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
    return query select * from payment_log where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: payment_log_uq(text, integer, text, text, integer, integer, double precision, text, date, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) RETURNS payment_log
    LANGUAGE plpgsql
    AS $_$
  declare
    updatedrow payment_log;
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_, 'UPDATE_PAYMENTLOG' );
    end if;
	update payment_log set
          customer_account_register_id = customerAccountRegisterId_ 
         , invoice_id = invoiceId_ 
         , amount = amount_ 
         , description = description_ 
         , effective_dt = effectiveDt_ 
         , last_update = now()
         , customer_id = customerId_ 
                  
	where          
	   payment_log_id = paymentLogId_          
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
$_$;


ALTER FUNCTION public.payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) OWNER TO postgres;

--
-- Name: payment_reversal(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
  Declare
  payLogToBeRvsd payment_log;
  carToBeRvsd customer_account_register;
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'REVERSE_PAYMENT' );
    end if;
-- customer_account_register_id, invoice_id, amount, description, effective_dt, last_update, customer_id, client_id, payment_log_id    
     select * into payLogToBeRvsd from payment_log where client_id = clientId_ and payment_log_id = paymentLogId_;
    insert into payment_log (invoice_id, amount, description, effective_dt, last_update, customer_id, client_id)
      values(null, payLogToBeRvsd.amount*-1, 'Payment Reversed', payLogToBeRvsd.effective_dt, now(), payLogToBeRvsd.customer_id, payLogToBeRvsd.client_id);
    INSERT INTO customer_account_register( client_id, customer_id, last_update,  effective_dt, description, 				tran_amt, tran_type, ref_id)
				 VALUES (clientid_,    payLogToBeRvsd.customer_id, now(),        payLogToBeRvsd.effective_dt, 	'Payment REVERSAL' , payLogToBeRvsd.amount  ,'PAYR', null);
  
    return true;
  End;
$$;


ALTER FUNCTION public.payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) OWNER TO postgres;

--
-- Name: retrieve_last_monthly_charge(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS date
    LANGUAGE plpgsql
    AS $$
  Declare
  lastMonthlyCharge date;
  Begin

    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORREGISTER' );
    end if;

    select max(activity_date) into lastMonthlyCharge from labor_register where description ='Monthly Retainer Fee';


    return lastMonthlyCharge;
  End;
$$;


ALTER FUNCTION public.retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: unwind_invoice(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
  declare
   originalInvoice invoice;
   
  begin
    if alreadyauth_ <>'ALREADY_AUTH' then	
    	perform issessionvalid(clientid_, securityuserid_,sessionid_) ;
    	perform isuserauthorized(clientid_, securityuserid_,'UNWIND_INVOICE' );
    end if;
    --
    select  * into originalInvoice from invoice where invoice_id = invoiceid_ and reversed = false;
    if originalInvoice.invoice_id is null then
	raise exception 'Invoice Cannot Be Reversed, It may have already been reversed.';
    end if;
    update invoice set invoice_total_amt = 0, last_update = now(), reversed=true where invoice_id = invoiceid_;


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
$$;


ALTER FUNCTION public.unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) OWNER TO postgres;

--
-- Name: user_info; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_info (
    user_id text NOT NULL,
    client_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    default_bill_rate double precision,
    display_name character varying(25),
    email_addr character varying(255)
);


ALTER TABLE public.user_info OWNER TO postgres;

--
-- Name: user_info_bypk(text, integer, text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) RETURNS user_info
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) OWNER TO postgres;

--
-- Name: user_info_dq(text, integer, text, text, text, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) OWNER TO postgres;

--
-- Name: user_info_iq(text, integer, text, text, character varying, character varying, double precision, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) RETURNS user_info
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) OWNER TO postgres;

--
-- Name: user_info_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF user_info
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_USERINFO' );
    end if;
-- email_addr, display_name, default_bill_rate, last_update, client_id, user_id    
    return query select * from user_info where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: user_info_uq(text, integer, text, text, character varying, character varying, double precision, timestamp without time zone, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) RETURNS user_info
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) OWNER TO postgres;

--
-- Name: vw_customer_followup; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_customer_followup AS
    SELECT followup.followup_id, followup.client_id, followup.customer_id, followup.last_update, followup.due_dt, followup.open_dt, followup.close_dt, followup.followup_description, followup.assigned_user_id, customer.first_name, customer.last_name, (((customer.last_name)::text || ', '::text) || (customer.first_name)::text) AS display_name, usr.display_name AS usr_display FROM ((followup LEFT JOIN customer ON (((followup.customer_id = customer.customer_id) AND (followup.client_id = customer.client_id)))) LEFT JOIN user_info usr ON (((followup.assigned_user_id = usr.user_id) AND (followup.client_id = usr.client_id)))) WHERE (followup.close_dt IS NULL) ORDER BY followup.due_dt;


ALTER TABLE public.vw_customer_followup OWNER TO postgres;

--
-- Name: vw_customer_followup_bypk(text, integer, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) RETURNS vw_customer_followup
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) OWNER TO postgres;

--
-- Name: vw_customer_followup_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF vw_customer_followup
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWCUSTOMERFOLLOWUP' );
    end if;
-- last_name, first_name, followup_description, close_dt, open_dt, due_dt, followup_id    
    return query select * from vw_customer_followup where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: vw_customer_hourly_bill_rate; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_customer_hourly_bill_rate AS
    SELECT cbr.client_id, cbr.customer_bill_rate_id, u.user_id, u.display_name, cbr.bill_rate, cbr.customer_id, cbr.last_update, customer.first_name, customer.last_name, customer.bill_type FROM ((user_info u JOIN customer_bill_rate cbr ON (((u.client_id = cbr.client_id) AND (u.user_id = cbr.user_id)))) JOIN customer ON (((cbr.customer_id = customer.customer_id) AND (cbr.client_id = customer.client_id))));


ALTER TABLE public.vw_customer_hourly_bill_rate OWNER TO postgres;

--
-- Name: vw_customer_hourly_bill_rate_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) RETURNS SETOF vw_customer_hourly_bill_rate
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, userId_,sessionId_) ;
    	perform isUserAuthorized(clientId_,userId_,'SELECT_VWCUSTOMERHOURLYBILLRATE' );
    end if;
-- last_update, customer_id, bill_rate, display_name, user_id, customer_bill_rate_id, client_id    
    return query select * from vw_customer_hourly_bill_rate where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: vw_invoice_display; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_invoice_display AS
    SELECT invoice.invoice_id, invoice.client_id, invoice.customer_id, invoice.last_update, invoice.invoice_dt, invoice.generated_dt, invoice.invoice_total_amt, invoice.prev_balance_due, customer.first_name, customer.last_name, (((customer.first_name)::text || ' '::text) || (customer.last_name)::text) AS display_name, customer.address, customer.city, customer.state, customer.zip, customer.work_phone, customer.home_phone, customer.fax, customer.email, customer.client_since_dt, customer.bill_type, customer.monthly_bill_rate, customer.active_yn, customer.mortgage_amount, customer.contingency_rate FROM (invoice LEFT JOIN customer ON (((invoice.customer_id = customer.customer_id) AND (invoice.client_id = customer.client_id)))) ORDER BY invoice.invoice_dt;


ALTER TABLE public.vw_invoice_display OWNER TO postgres;

--
-- Name: vw_invoice_display_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF vw_invoice_display
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWINVOICEDISPLAY' );
    end if;
-- contingency_rate, mortgage_amount, active_yn, monthly_bill_rate, bill_type, client_since_dt, email, fax, home_phone, work_phone, zip, state, city, address, display_name, last_name, first_name, prev_balance_due, invoice_total_amt, generated_dt, invoice_dt, last_update, customer_id, client_id, invoice_id    
    return query select * from vw_invoice_display where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: vw_labor_invoice_item_display; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_labor_invoice_item_display AS
    SELECT inv_item.labor_invoice_item_id, inv_item.client_id, inv_item.customer_id, inv_item.last_update, inv_item.activity_dt, inv_item.activity_description, inv_item.invoice_id, inv_item.hours_billed, inv_item.bill_rate, inv_item.user_id, user_info.display_name FROM (labor_invoice_item inv_item LEFT JOIN user_info ON (((inv_item.user_id = user_info.user_id) AND (inv_item.client_id = user_info.client_id)))) ORDER BY inv_item.activity_dt;


ALTER TABLE public.vw_labor_invoice_item_display OWNER TO postgres;

--
-- Name: vw_labor_invoice_item_display_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF vw_labor_invoice_item_display
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_LABORINVOICEITEM' );
    end if;
-- display_name, user_id, bill_rate, hours_billed, invoice_id, activity_description, activity_dt, last_update, customer_id, client_id, labor_invoice_item_id    
    return query select * from vw_labor_invoice_item_display where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: vw_customer_balance; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_customer_balance AS
    SELECT customer_account_register.client_id, customer_account_register.customer_id, sum(customer_account_register.tran_amt) AS balance FROM customer_account_register GROUP BY customer_account_register.client_id, customer_account_register.customer_id;


ALTER TABLE public.vw_customer_balance OWNER TO postgres;

--
-- Name: vw_monthly_customer_report; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_monthly_customer_report AS
    SELECT customer.client_id, customer.customer_id, customer.last_name, customer.first_name, (vw_cb.balance / customer.monthly_bill_rate) AS months_unpaid, CASE WHEN (customer.mortgage_amount > (0)::double precision) THEN 'Yes'::text ELSE 'No'::text END AS escrow FROM (customer LEFT JOIN vw_customer_balance vw_cb ON (((customer.customer_id = vw_cb.customer_id) AND (customer.client_id = vw_cb.client_id)))) WHERE ((((customer.bill_type)::text = 'MONTHLY'::text) AND (customer.monthly_bill_rate > (0)::double precision)) AND (customer.active_yn = 'Y'::bpchar)) ORDER BY customer.last_name, customer.first_name;


ALTER TABLE public.vw_monthly_customer_report OWNER TO postgres;

--
-- Name: vw_monthly_customer_report_sq(text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) RETURNS SETOF vw_monthly_customer_report
    LANGUAGE plpgsql
    AS $$
  Declare
  
  Begin
    if alreadyAuth_ <>'ALREADY_AUTH' then
    	perform isSessionValid(clientId_, securityuserId_,sessionId_) ;
    	perform isUserAuthorized(clientId_, securityuserId_, 'SELECT_VWMONTHLYCUSTOMERREPORT' );
    end if;
-- escrow, months_unpaid, first_name, last_name, customer_id, client_id    
    return query select * from vw_monthly_customer_report where client_id = clientId_;
  End;
$$;


ALTER FUNCTION public.vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) OWNER TO postgres;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE client (
    client_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    client_name text
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: client_client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE client_client_id_seq
    START WITH 20
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.client_client_id_seq OWNER TO postgres;

--
-- Name: client_client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE client_client_id_seq OWNED BY client.client_id;


--
-- Name: client_pref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE client_pref (
    client_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    client_name text
);


ALTER TABLE public.client_pref OWNER TO postgres;

--
-- Name: client_pref_client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE client_pref_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.client_pref_client_id_seq OWNER TO postgres;

--
-- Name: client_pref_client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE client_pref_client_id_seq OWNED BY client_pref.client_id;


--
-- Name: customer_account_register_customer_account_register_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_account_register_customer_account_register_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.customer_account_register_customer_account_register_id_seq OWNER TO postgres;

--
-- Name: customer_account_register_customer_account_register_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_account_register_customer_account_register_id_seq OWNED BY customer_account_register.customer_account_register_id;


--
-- Name: customer_bill_rate_customer_bill_rate_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_bill_rate_customer_bill_rate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.customer_bill_rate_customer_bill_rate_id_seq OWNER TO postgres;

--
-- Name: customer_bill_rate_customer_bill_rate_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_bill_rate_customer_bill_rate_id_seq OWNED BY customer_bill_rate.customer_bill_rate_id;


--
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.customer_customer_id_seq OWNER TO postgres;

--
-- Name: customer_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_customer_id_seq OWNED BY customer.customer_id;


--
-- Name: expense_invoice_item_expense_invoice_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE expense_invoice_item_expense_invoice_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.expense_invoice_item_expense_invoice_item_id_seq OWNER TO postgres;

--
-- Name: expense_invoice_item_expense_invoice_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE expense_invoice_item_expense_invoice_item_id_seq OWNED BY expense_invoice_item.expense_invoice_item_id;


--
-- Name: expense_register_expense_register_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE expense_register_expense_register_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.expense_register_expense_register_id_seq OWNER TO postgres;

--
-- Name: expense_register_expense_register_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE expense_register_expense_register_id_seq OWNED BY expense_register.expense_register_id;


--
-- Name: followup_followup_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE followup_followup_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.followup_followup_id_seq OWNER TO postgres;

--
-- Name: followup_followup_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE followup_followup_id_seq OWNED BY followup.followup_id;


--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE invoice_invoice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.invoice_invoice_id_seq OWNER TO postgres;

--
-- Name: invoice_invoice_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE invoice_invoice_id_seq OWNED BY invoice.invoice_id;


--
-- Name: labor_invoice_item_labor_invoice_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE labor_invoice_item_labor_invoice_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.labor_invoice_item_labor_invoice_item_id_seq OWNER TO postgres;

--
-- Name: labor_invoice_item_labor_invoice_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE labor_invoice_item_labor_invoice_item_id_seq OWNED BY labor_invoice_item.labor_invoice_item_id;


--
-- Name: labor_register_labor_register_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE labor_register_labor_register_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.labor_register_labor_register_id_seq OWNER TO postgres;

--
-- Name: labor_register_labor_register_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE labor_register_labor_register_id_seq OWNED BY labor_register.labor_register_id;


--
-- Name: payment_log_payment_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE payment_log_payment_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.payment_log_payment_log_id_seq OWNER TO postgres;

--
-- Name: payment_log_payment_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payment_log_payment_log_id_seq OWNED BY payment_log.payment_log_id;


--
-- Name: security_privilege; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_privilege (
    client_id integer NOT NULL,
    security_privilege_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    priv_name text,
    description text
);


ALTER TABLE public.security_privilege OWNER TO postgres;

--
-- Name: security_privilege_security_privilege_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE security_privilege_security_privilege_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.security_privilege_security_privilege_id_seq OWNER TO postgres;

--
-- Name: security_privilege_security_privilege_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE security_privilege_security_privilege_id_seq OWNED BY security_privilege.security_privilege_id;


--
-- Name: security_profile; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_profile (
    client_id integer NOT NULL,
    security_profile_id integer NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    profile_name text
);


ALTER TABLE public.security_profile OWNER TO postgres;

--
-- Name: security_profile_grant; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_profile_grant (
    client_id integer NOT NULL,
    security_privilege_id integer NOT NULL,
    security_profile_id integer NOT NULL
);


ALTER TABLE public.security_profile_grant OWNER TO postgres;

--
-- Name: security_profile_security_profile_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE security_profile_security_profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.security_profile_security_profile_id_seq OWNER TO postgres;

--
-- Name: security_profile_security_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE security_profile_security_profile_id_seq OWNED BY security_profile.security_profile_id;


--
-- Name: simple_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE simple_log (
    added_dt timestamp without time zone DEFAULT now(),
    msg text
);


ALTER TABLE public.simple_log OWNER TO postgres;

--
-- Name: sys_code; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sys_code (
    sys_code_id integer NOT NULL,
    client_id integer NOT NULL,
    last_update timestamp(3) without time zone,
    code_type character varying(5),
    code_id character varying(5),
    description character varying(255),
    system_or_user character varying(20)
);


ALTER TABLE public.sys_code OWNER TO postgres;

--
-- Name: sys_code_sys_code_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sys_code_sys_code_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.sys_code_sys_code_id_seq OWNER TO postgres;

--
-- Name: sys_code_sys_code_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sys_code_sys_code_id_seq OWNED BY sys_code.sys_code_id;


--
-- Name: tran_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tran_type (
    client_id integer NOT NULL,
    tran_type character varying(5) NOT NULL,
    last_update timestamp(3) without time zone DEFAULT now(),
    description character varying(20)
);


ALTER TABLE public.tran_type OWNER TO postgres;

--
-- Name: vw_user_grant; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW vw_user_grant AS
    SELECT su.user_id, su.client_id, sp.profile_name, spriv.priv_name FROM (((security_user su LEFT JOIN security_profile sp ON (((su.client_id = sp.client_id) AND (su.security_profile_id = sp.security_profile_id)))) LEFT JOIN security_profile_grant spg ON (((sp.client_id = spg.client_id) AND (sp.security_profile_id = spg.security_profile_id)))) LEFT JOIN security_privilege spriv ON (((spg.client_id = spriv.client_id) AND (spg.security_privilege_id = spriv.security_privilege_id))));


ALTER TABLE public.vw_user_grant OWNER TO postgres;

--
-- Name: client_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE client ALTER COLUMN client_id SET DEFAULT nextval('client_client_id_seq'::regclass);


--
-- Name: client_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE client_pref ALTER COLUMN client_id SET DEFAULT nextval('client_pref_client_id_seq'::regclass);


--
-- Name: customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE customer ALTER COLUMN customer_id SET DEFAULT nextval('customer_customer_id_seq'::regclass);


--
-- Name: customer_account_register_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE customer_account_register ALTER COLUMN customer_account_register_id SET DEFAULT nextval('customer_account_register_customer_account_register_id_seq'::regclass);


--
-- Name: customer_bill_rate_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE customer_bill_rate ALTER COLUMN customer_bill_rate_id SET DEFAULT nextval('customer_bill_rate_customer_bill_rate_id_seq'::regclass);


--
-- Name: expense_invoice_item_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE expense_invoice_item ALTER COLUMN expense_invoice_item_id SET DEFAULT nextval('expense_invoice_item_expense_invoice_item_id_seq'::regclass);


--
-- Name: expense_register_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE expense_register ALTER COLUMN expense_register_id SET DEFAULT nextval('expense_register_expense_register_id_seq'::regclass);


--
-- Name: followup_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE followup ALTER COLUMN followup_id SET DEFAULT nextval('followup_followup_id_seq'::regclass);


--
-- Name: invoice_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE invoice ALTER COLUMN invoice_id SET DEFAULT nextval('invoice_invoice_id_seq'::regclass);


--
-- Name: labor_invoice_item_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE labor_invoice_item ALTER COLUMN labor_invoice_item_id SET DEFAULT nextval('labor_invoice_item_labor_invoice_item_id_seq'::regclass);


--
-- Name: labor_register_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE labor_register ALTER COLUMN labor_register_id SET DEFAULT nextval('labor_register_labor_register_id_seq'::regclass);


--
-- Name: payment_log_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE payment_log ALTER COLUMN payment_log_id SET DEFAULT nextval('payment_log_payment_log_id_seq'::regclass);


--
-- Name: security_privilege_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE security_privilege ALTER COLUMN security_privilege_id SET DEFAULT nextval('security_privilege_security_privilege_id_seq'::regclass);


--
-- Name: security_profile_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE security_profile ALTER COLUMN security_profile_id SET DEFAULT nextval('security_profile_security_profile_id_seq'::regclass);


--
-- Name: sys_code_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE sys_code ALTER COLUMN sys_code_id SET DEFAULT nextval('sys_code_sys_code_id_seq'::regclass);


--
-- Name: pk_client; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY client
    ADD CONSTRAINT pk_client PRIMARY KEY (client_id);


--
-- Name: pk_client_pref; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY client_pref
    ADD CONSTRAINT pk_client_pref PRIMARY KEY (client_id);


--
-- Name: pk_customer; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (customer_id, client_id);


--
-- Name: pk_customer_account_register; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customer_account_register
    ADD CONSTRAINT pk_customer_account_register PRIMARY KEY (customer_account_register_id, client_id, customer_id);


--
-- Name: pk_customer_bill_rate; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY customer_bill_rate
    ADD CONSTRAINT pk_customer_bill_rate PRIMARY KEY (customer_bill_rate_id, client_id);


--
-- Name: pk_expense_invoice_item; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY expense_invoice_item
    ADD CONSTRAINT pk_expense_invoice_item PRIMARY KEY (expense_invoice_item_id, client_id, customer_id);


--
-- Name: pk_expense_register; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY expense_register
    ADD CONSTRAINT pk_expense_register PRIMARY KEY (expense_register_id, client_id, customer_id);


--
-- Name: pk_followup; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY followup
    ADD CONSTRAINT pk_followup PRIMARY KEY (followup_id, client_id);


--
-- Name: pk_invoice; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT pk_invoice PRIMARY KEY (invoice_id, client_id, customer_id);


--
-- Name: pk_labor_invoice_item; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY labor_invoice_item
    ADD CONSTRAINT pk_labor_invoice_item PRIMARY KEY (labor_invoice_item_id, client_id, customer_id);


--
-- Name: pk_labor_register; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY labor_register
    ADD CONSTRAINT pk_labor_register PRIMARY KEY (labor_register_id, client_id, customer_id);


--
-- Name: pk_payment_log; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payment_log
    ADD CONSTRAINT pk_payment_log PRIMARY KEY (payment_log_id, client_id, customer_id);


--
-- Name: pk_security_privilege; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_privilege
    ADD CONSTRAINT pk_security_privilege PRIMARY KEY (client_id, security_privilege_id);


--
-- Name: pk_security_profile; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_profile
    ADD CONSTRAINT pk_security_profile PRIMARY KEY (client_id, security_profile_id);


--
-- Name: pk_security_profile_grant; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_profile_grant
    ADD CONSTRAINT pk_security_profile_grant PRIMARY KEY (client_id, security_privilege_id, security_profile_id);


--
-- Name: pk_security_user; Type: CONSTRAINT; Schema: public; Owner: legaltime_user; Tablespace: 
--

ALTER TABLE ONLY security_user
    ADD CONSTRAINT pk_security_user PRIMARY KEY (client_id, user_id);


--
-- Name: pk_sys_code; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sys_code
    ADD CONSTRAINT pk_sys_code PRIMARY KEY (sys_code_id, client_id);


--
-- Name: pk_tran_type; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tran_type
    ADD CONSTRAINT pk_tran_type PRIMARY KEY (client_id, tran_type);


--
-- Name: pk_user_info; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_info
    ADD CONSTRAINT pk_user_info PRIMARY KEY (user_id, client_id);


--
-- Name: fk_client_account_register_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_account_register
    ADD CONSTRAINT fk_client_account_register_2 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_client_account_register_3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_account_register
    ADD CONSTRAINT fk_client_account_register_3 FOREIGN KEY (client_id, tran_type) REFERENCES tran_type(client_id, tran_type);


--
-- Name: fk_customer_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT fk_customer_1 FOREIGN KEY (client_id) REFERENCES client_pref(client_id);


--
-- Name: fk_customer_bill_rate_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_bill_rate
    ADD CONSTRAINT fk_customer_bill_rate_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_customer_bill_rate_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer_bill_rate
    ADD CONSTRAINT fk_customer_bill_rate_2 FOREIGN KEY (user_id, client_id) REFERENCES user_info(user_id, client_id);


--
-- Name: fk_expense_invoice_item_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY expense_invoice_item
    ADD CONSTRAINT fk_expense_invoice_item_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_expense_register_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY expense_register
    ADD CONSTRAINT fk_expense_register_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_followup_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY followup
    ADD CONSTRAINT fk_followup_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_invoice_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY invoice
    ADD CONSTRAINT fk_invoice_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_labor_register_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY labor_register
    ADD CONSTRAINT fk_labor_register_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_labor_register_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY labor_register
    ADD CONSTRAINT fk_labor_register_2 FOREIGN KEY (user_id, client_id) REFERENCES user_info(user_id, client_id);


--
-- Name: fk_laborinvoiceitem_customer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY labor_invoice_item
    ADD CONSTRAINT fk_laborinvoiceitem_customer FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_laborinvoiceitem_invoice; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY labor_invoice_item
    ADD CONSTRAINT fk_laborinvoiceitem_invoice FOREIGN KEY (invoice_id, client_id, customer_id) REFERENCES invoice(invoice_id, client_id, customer_id);


--
-- Name: fk_payment_log_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_log
    ADD CONSTRAINT fk_payment_log_1 FOREIGN KEY (customer_id, client_id) REFERENCES customer(customer_id, client_id);


--
-- Name: fk_payment_log_3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_log
    ADD CONSTRAINT fk_payment_log_3 FOREIGN KEY (invoice_id, client_id, customer_id) REFERENCES invoice(invoice_id, client_id, customer_id);


--
-- Name: fk_paymentlog_customeraccountregister; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_log
    ADD CONSTRAINT fk_paymentlog_customeraccountregister FOREIGN KEY (customer_account_register_id, client_id, customer_id) REFERENCES customer_account_register(customer_account_register_id, client_id, customer_id);


--
-- Name: fk_secuirtyprofile_client; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY security_profile
    ADD CONSTRAINT fk_secuirtyprofile_client FOREIGN KEY (client_id) REFERENCES client(client_id);


--
-- Name: fk_security_profile_grant_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY security_profile_grant
    ADD CONSTRAINT fk_security_profile_grant_2 FOREIGN KEY (client_id, security_profile_id) REFERENCES security_profile(client_id, security_profile_id);


--
-- Name: fk_security_user_2; Type: FK CONSTRAINT; Schema: public; Owner: legaltime_user
--

ALTER TABLE ONLY security_user
    ADD CONSTRAINT fk_security_user_2 FOREIGN KEY (client_id, security_profile_id) REFERENCES security_profile(client_id, security_profile_id);


--
-- Name: fk_securityprivilege_client; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY security_privilege
    ADD CONSTRAINT fk_securityprivilege_client FOREIGN KEY (client_id) REFERENCES client(client_id);


--
-- Name: fk_securityprofilegrant_securityprivilege; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY security_profile_grant
    ADD CONSTRAINT fk_securityprofilegrant_securityprivilege FOREIGN KEY (client_id, security_privilege_id) REFERENCES security_privilege(client_id, security_privilege_id);


--
-- Name: fk_securityuser_client; Type: FK CONSTRAINT; Schema: public; Owner: legaltime_user
--

ALTER TABLE ONLY security_user
    ADD CONSTRAINT fk_securityuser_client FOREIGN KEY (client_id) REFERENCES client(client_id);


--
-- Name: fk_syscode_client; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sys_code
    ADD CONSTRAINT fk_syscode_client FOREIGN KEY (client_id) REFERENCES client_pref(client_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: add_default_bill_rates_for_customer(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION add_default_bill_rates_for_customer(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer) TO legaltime_full;


--
-- Name: assess_all_monthly_charges(text, integer, text, text, date); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) FROM PUBLIC;
REVOKE ALL ON FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) FROM postgres;
GRANT ALL ON FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO postgres;
GRANT ALL ON FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO PUBLIC;
GRANT ALL ON FUNCTION assess_all_monthly_charges(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO legaltime_full;


--
-- Name: assess_all_monthly_charges_and_invoice(text, integer, text, text, date); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) FROM PUBLIC;
REVOKE ALL ON FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) FROM postgres;
GRANT ALL ON FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO postgres;
GRANT ALL ON FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO PUBLIC;
GRANT ALL ON FUNCTION assess_all_monthly_charges_and_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assessdt_ date) TO legaltime_full;


--
-- Name: create_customer_invoice_all_eligible(text, integer, text, text, integer, date); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) FROM PUBLIC;
REVOKE ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) FROM postgres;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) TO postgres;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) TO PUBLIC;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date) TO legaltime_full;


--
-- Name: create_customer_invoice_all_eligible(text, integer, text, text, integer, date, boolean); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) FROM PUBLIC;
REVOKE ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) FROM postgres;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) TO postgres;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) TO PUBLIC;
GRANT ALL ON FUNCTION create_customer_invoice_all_eligible(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer, invoicedt_ date, invoiceexpenses boolean) TO legaltime_full;


--
-- Name: customer_account_register; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE customer_account_register FROM PUBLIC;
REVOKE ALL ON TABLE customer_account_register FROM postgres;
GRANT ALL ON TABLE customer_account_register TO postgres;
GRANT ALL ON TABLE customer_account_register TO legaltime_full;


--
-- Name: customer_account_register_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: customer_account_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customeraccountregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: customer_account_register_iq(text, integer, text, text, character varying, double precision, text, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: customer_account_register_iq(text, integer, text, text, integer, character varying, double precision, text, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: customer_account_register_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: customer_account_register_uq(text, integer, text, text, character varying, double precision, text, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO legaltime_full;


--
-- Name: customer_account_register_uq(text, integer, text, text, integer, character varying, double precision, text, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_account_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, refid_ integer, trantype_ character varying, tranamt_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, customeraccountregisterid_ integer) TO legaltime_full;


--
-- Name: customer_bill_rate; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE customer_bill_rate FROM PUBLIC;
REVOKE ALL ON TABLE customer_bill_rate FROM postgres;
GRANT ALL ON TABLE customer_bill_rate TO postgres;
GRANT ALL ON TABLE customer_bill_rate TO legaltime_full;


--
-- Name: customer_bill_rate_bypk(text, integer, text, text, integer, integer, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text) TO legaltime_full;


--
-- Name: customer_bill_rate_bypk(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerbillrateid_ integer) TO legaltime_full;


--
-- Name: customer_bill_rate_dq(text, integer, text, text, integer, integer, text, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, customerid_ integer, userid_ text, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: customer_bill_rate_dq(text, integer, text, text, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerbillrateid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: customer_bill_rate_iq(text, integer, text, text, double precision, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, userid_ text, customerid_ integer) TO legaltime_full;


--
-- Name: customer_bill_rate_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: customer_bill_rate_uq(text, integer, text, text, double precision, timestamp without time zone, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bill_rate_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, lastupdate_ timestamp without time zone, userid_ text, customerid_ integer, customerbillrateid_ integer) TO legaltime_full;


--
-- Name: customer; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE customer FROM PUBLIC;
REVOKE ALL ON TABLE customer FROM postgres;
GRANT ALL ON TABLE customer TO postgres;
GRANT ALL ON TABLE customer TO legaltime_full;


--
-- Name: customer_bypk(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customerid_ integer) TO legaltime_full;


--
-- Name: customer_dq(text, integer, text, text, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION customer_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: customer_invoiced_sq(text, integer, text, text, date); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) FROM postgres;
GRANT ALL ON FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO postgres;
GRANT ALL ON FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO PUBLIC;
GRANT ALL ON FUNCTION customer_invoiced_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO legaltime_full;


--
-- Name: customer_iq(text, integer, text, text, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) FROM postgres;
GRANT ALL ON FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) TO postgres;
GRANT ALL ON FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) TO PUBLIC;
GRANT ALL ON FUNCTION customer_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying) TO legaltime_full;


--
-- Name: customer_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION customer_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: customer_uq(text, integer, text, text, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying, timestamp without time zone, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO legaltime_full;


--
-- Name: customer_uq(text, integer, text, text, character, double precision, double precision, character, double precision, character varying, text, date, character varying, character varying, character varying, character varying, character varying, character, character varying, text, character varying, character varying, timestamp without time zone, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION customer_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, suspendinvoice_ character, contingencyrate_ double precision, mortgageamount_ double precision, activeyn_ character, monthlybillrate_ double precision, billtype_ character varying, note_ text, clientsincedt_ date, email_ character varying, fax_ character varying, homephone_ character varying, workphone_ character varying, zip_ character varying, state_ character, city_ character varying, address_ text, lastname_ character varying, firstname_ character varying, lastupdate_ timestamp without time zone, customerid_ integer) TO legaltime_full;


--
-- Name: expense_invoice_item; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE expense_invoice_item FROM PUBLIC;
REVOKE ALL ON TABLE expense_invoice_item FROM postgres;
GRANT ALL ON TABLE expense_invoice_item TO postgres;
GRANT ALL ON TABLE expense_invoice_item TO legaltime_full;


--
-- Name: expense_invoice_item_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: expense_invoice_item_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION expense_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: expense_invoice_item_iq(text, integer, text, text, integer, double precision, text, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: expense_invoice_item_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION expense_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: expense_invoice_item_uq(text, integer, text, text, integer, double precision, text, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, amount_ double precision, expensedescription_ text, expensedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, expenseinvoiceitemid_ integer) TO legaltime_full;


--
-- Name: expense_register; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE expense_register FROM PUBLIC;
REVOKE ALL ON TABLE expense_register FROM postgres;
GRANT ALL ON TABLE expense_register TO postgres;
GRANT ALL ON TABLE expense_register TO legaltime_full;


--
-- Name: expense_register_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: expense_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION expense_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, expenseregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: expense_register_iq(text, integer, text, text, date, boolean, integer, double precision, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, customerid_ integer) TO legaltime_full;


--
-- Name: expense_register_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION expense_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: expense_register_uq(text, integer, text, text, date, boolean, integer, double precision, text, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) FROM postgres;
GRANT ALL ON FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) TO postgres;
GRANT ALL ON FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION expense_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, expensedt_ date, invoiceable_ boolean, invoiceid_ integer, amount_ double precision, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, expenseregisterid_ integer) TO legaltime_full;


--
-- Name: followup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE followup FROM PUBLIC;
REVOKE ALL ON TABLE followup FROM postgres;
GRANT ALL ON TABLE followup TO postgres;
GRANT ALL ON TABLE followup TO legaltime_full;


--
-- Name: followup_bypk(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) FROM postgres;
GRANT ALL ON FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO postgres;
GRANT ALL ON FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO legaltime_full;


--
-- Name: followup_dq(text, integer, text, text, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION followup_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, followupid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: followup_iq(text, integer, text, text, text, text, date, date, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION followup_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: followup_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: followup_uq(text, integer, text, text, text, text, date, date, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) FROM postgres;
GRANT ALL ON FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) TO postgres;
GRANT ALL ON FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION followup_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, assigneduserid_ text, followupdescription_ text, closedt_ date, opendt_ date, duedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, followupid_ integer) TO legaltime_full;


--
-- Name: security_user; Type: ACL; Schema: public; Owner: legaltime_user
--

REVOKE ALL ON TABLE security_user FROM PUBLIC;
REVOKE ALL ON TABLE security_user FROM legaltime_user;
GRANT ALL ON TABLE security_user TO legaltime_user;
GRANT ALL ON TABLE security_user TO legaltime_full;


--
-- Name: initsession(text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION initsession(userid_ text, password_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION initsession(userid_ text, password_ text) FROM postgres;
GRANT ALL ON FUNCTION initsession(userid_ text, password_ text) TO postgres;
GRANT ALL ON FUNCTION initsession(userid_ text, password_ text) TO PUBLIC;
GRANT ALL ON FUNCTION initsession(userid_ text, password_ text) TO legaltime_full;


--
-- Name: initsession(integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION initsession(clientid_ integer, userid_ text, password_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION initsession(clientid_ integer, userid_ text, password_ text) FROM postgres;
GRANT ALL ON FUNCTION initsession(clientid_ integer, userid_ text, password_ text) TO postgres;
GRANT ALL ON FUNCTION initsession(clientid_ integer, userid_ text, password_ text) TO PUBLIC;
GRANT ALL ON FUNCTION initsession(clientid_ integer, userid_ text, password_ text) TO legaltime_full;


--
-- Name: invoice_all_hourly_clients(text, integer, text, text, date); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) FROM postgres;
GRANT ALL ON FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO postgres;
GRANT ALL ON FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_all_hourly_clients(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoicedt_ date) TO legaltime_full;


--
-- Name: invoice; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE invoice FROM PUBLIC;
REVOKE ALL ON TABLE invoice FROM postgres;
GRANT ALL ON TABLE invoice TO postgres;
GRANT ALL ON TABLE invoice TO legaltime_full;


--
-- Name: invoice_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: invoice_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, invoiceid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: invoice_iq(text, integer, text, text, double precision, double precision, date, date, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) FROM postgres;
GRANT ALL ON FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) TO postgres;
GRANT ALL ON FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, customerid_ integer, invoiceid_ integer) TO legaltime_full;


--
-- Name: invoice_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: invoice_uq(text, integer, text, text, double precision, double precision, date, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) FROM postgres;
GRANT ALL ON FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) TO postgres;
GRANT ALL ON FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION invoice_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, prevbalancedue_ double precision, invoicetotalamt_ double precision, generateddt_ date, invoicedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, invoiceid_ integer) TO legaltime_full;


--
-- Name: issessionvalid(integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION issessionvalid(clientid_ integer, userid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: isuserauthorized(integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) FROM postgres;
GRANT ALL ON FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) TO postgres;
GRANT ALL ON FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) TO PUBLIC;
GRANT ALL ON FUNCTION isuserauthorized(clientid_ integer, userid_ text, tran_ text) TO legaltime_full;


--
-- Name: labor_invoice_item; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE labor_invoice_item FROM PUBLIC;
REVOKE ALL ON TABLE labor_invoice_item FROM postgres;
GRANT ALL ON TABLE labor_invoice_item TO postgres;
GRANT ALL ON TABLE labor_invoice_item TO legaltime_full;


--
-- Name: labor_invoice_item_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_invoice_item_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: labor_invoice_item_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION labor_invoice_item_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborinvoiceitemid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: labor_invoice_item_iq(text, integer, text, text, double precision, double precision, integer, text, text, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_invoice_item_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: labor_invoice_item_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION labor_invoice_item_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: labor_invoice_item_uq(text, integer, text, text, double precision, double precision, integer, text, text, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_invoice_item_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, billrate_ double precision, hoursbilled_ double precision, invoiceid_ integer, userid_ text, activitydescription_ text, activitydt_ date, lastupdate_ timestamp without time zone, customerid_ integer, laborinvoiceitemid_ integer) TO legaltime_full;


--
-- Name: labor_register; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE labor_register FROM PUBLIC;
REVOKE ALL ON TABLE labor_register FROM postgres;
GRANT ALL ON TABLE labor_register TO postgres;
GRANT ALL ON TABLE labor_register TO legaltime_full;


--
-- Name: labor_register_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_register_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: labor_register_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION labor_register_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, laborregisterid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: labor_register_iq(text, integer, text, text, text, integer, double precision, boolean, date, timestamp without time zone, timestamp without time zone, integer, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_register_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, customerid_ integer) TO legaltime_full;


--
-- Name: labor_register_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION labor_register_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: labor_register_uq(text, integer, text, text, text, integer, double precision, boolean, date, timestamp without time zone, timestamp without time zone, integer, text, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) FROM postgres;
GRANT ALL ON FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) TO postgres;
GRANT ALL ON FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION labor_register_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text, invoiceid_ integer, billrate_ double precision, invoiceable_ boolean, activitydate_ date, endtime_ timestamp without time zone, starttime_ timestamp without time zone, minutecount_ integer, description_ text, lastupdate_ timestamp without time zone, customerid_ integer, laborregisterid_ integer) TO legaltime_full;


--
-- Name: payment_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE payment_log FROM PUBLIC;
REVOKE ALL ON TABLE payment_log FROM postgres;
GRANT ALL ON TABLE payment_log TO postgres;
GRANT ALL ON TABLE payment_log TO legaltime_full;


--
-- Name: payment_log_bypk(text, integer, text, text, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION payment_log_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer) TO legaltime_full;


--
-- Name: payment_log_dq(text, integer, text, text, integer, integer, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION payment_log_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, paymentlogid_ integer, customerid_ integer, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: payment_log_iq(text, integer, text, text, integer, integer, double precision, text, date, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) FROM postgres;
GRANT ALL ON FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO postgres;
GRANT ALL ON FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION payment_log_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, customerid_ integer) TO legaltime_full;


--
-- Name: payment_log_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION payment_log_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: payment_log_uq(text, integer, text, text, integer, integer, double precision, text, date, timestamp without time zone, integer, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) FROM postgres;
GRANT ALL ON FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) TO postgres;
GRANT ALL ON FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION payment_log_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, customeraccountregisterid_ integer, invoiceid_ integer, amount_ double precision, description_ text, effectivedt_ date, lastupdate_ timestamp without time zone, customerid_ integer, paymentlogid_ integer) TO legaltime_full;


--
-- Name: payment_reversal(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) FROM postgres;
GRANT ALL ON FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) TO postgres;
GRANT ALL ON FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION payment_reversal(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, paymentlogid_ integer) TO legaltime_full;


--
-- Name: retrieve_last_monthly_charge(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION retrieve_last_monthly_charge(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: unwind_invoice(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) FROM postgres;
GRANT ALL ON FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) TO postgres;
GRANT ALL ON FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION unwind_invoice(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, invoiceid_ integer) TO legaltime_full;


--
-- Name: user_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE user_info FROM PUBLIC;
REVOKE ALL ON TABLE user_info FROM postgres;
GRANT ALL ON TABLE user_info TO postgres;
GRANT ALL ON TABLE user_info TO legaltime_full;


--
-- Name: user_info_bypk(text, integer, text, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) FROM postgres;
GRANT ALL ON FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) TO postgres;
GRANT ALL ON FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION user_info_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, userid_ text) TO legaltime_full;


--
-- Name: user_info_dq(text, integer, text, text, text, timestamp without time zone); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) FROM PUBLIC;
REVOKE ALL ON FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) FROM postgres;
GRANT ALL ON FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) TO postgres;
GRANT ALL ON FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) TO PUBLIC;
GRANT ALL ON FUNCTION user_info_dq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text, userid_ text, lastupdate_ timestamp without time zone) TO legaltime_full;


--
-- Name: user_info_iq(text, integer, text, text, character varying, character varying, double precision, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) FROM postgres;
GRANT ALL ON FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) TO postgres;
GRANT ALL ON FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION user_info_iq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, userid_ text) TO legaltime_full;


--
-- Name: user_info_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION user_info_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: user_info_uq(text, integer, text, text, character varying, character varying, double precision, timestamp without time zone, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) FROM postgres;
GRANT ALL ON FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) TO postgres;
GRANT ALL ON FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION user_info_uq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, emailaddr_ character varying, displayname_ character varying, defaultbillrate_ double precision, lastupdate_ timestamp without time zone, userid_ text) TO legaltime_full;


--
-- Name: vw_customer_followup; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_customer_followup FROM PUBLIC;
REVOKE ALL ON TABLE vw_customer_followup FROM postgres;
GRANT ALL ON TABLE vw_customer_followup TO postgres;
GRANT ALL ON TABLE vw_customer_followup TO legaltime_full;


--
-- Name: vw_customer_followup_bypk(text, integer, text, text, integer); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) FROM postgres;
GRANT ALL ON FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO postgres;
GRANT ALL ON FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO PUBLIC;
GRANT ALL ON FUNCTION vw_customer_followup_bypk(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text, followupid_ integer) TO legaltime_full;


--
-- Name: vw_customer_followup_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION vw_customer_followup_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: vw_customer_hourly_bill_rate; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_customer_hourly_bill_rate FROM PUBLIC;
REVOKE ALL ON TABLE vw_customer_hourly_bill_rate FROM postgres;
GRANT ALL ON TABLE vw_customer_hourly_bill_rate TO postgres;
GRANT ALL ON TABLE vw_customer_hourly_bill_rate TO legaltime_full;


--
-- Name: vw_customer_hourly_bill_rate_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION vw_customer_hourly_bill_rate_sq(alreadyauth_ text, clientid_ integer, userid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: vw_invoice_display; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_invoice_display FROM PUBLIC;
REVOKE ALL ON TABLE vw_invoice_display FROM postgres;
GRANT ALL ON TABLE vw_invoice_display TO postgres;
GRANT ALL ON TABLE vw_invoice_display TO legaltime_full;


--
-- Name: vw_invoice_display_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION vw_invoice_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: vw_labor_invoice_item_display; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_labor_invoice_item_display FROM PUBLIC;
REVOKE ALL ON TABLE vw_labor_invoice_item_display FROM postgres;
GRANT ALL ON TABLE vw_labor_invoice_item_display TO postgres;
GRANT SELECT,INSERT,REFERENCES,DELETE,TRIGGER,UPDATE ON TABLE vw_labor_invoice_item_display TO legaltime_full;


--
-- Name: vw_labor_invoice_item_display_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION vw_labor_invoice_item_display_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: vw_customer_balance; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_customer_balance FROM PUBLIC;
REVOKE ALL ON TABLE vw_customer_balance FROM postgres;
GRANT ALL ON TABLE vw_customer_balance TO postgres;
GRANT ALL ON TABLE vw_customer_balance TO legaltime_full;


--
-- Name: vw_monthly_customer_report; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_monthly_customer_report FROM PUBLIC;
REVOKE ALL ON TABLE vw_monthly_customer_report FROM postgres;
GRANT ALL ON TABLE vw_monthly_customer_report TO postgres;
GRANT ALL ON TABLE vw_monthly_customer_report TO legaltime_full;


--
-- Name: vw_monthly_customer_report_sq(text, integer, text, text); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM PUBLIC;
REVOKE ALL ON FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) FROM postgres;
GRANT ALL ON FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO postgres;
GRANT ALL ON FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO PUBLIC;
GRANT ALL ON FUNCTION vw_monthly_customer_report_sq(alreadyauth_ text, clientid_ integer, securityuserid_ text, sessionid_ text) TO legaltime_full;


--
-- Name: client; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE client FROM PUBLIC;
REVOKE ALL ON TABLE client FROM postgres;
GRANT ALL ON TABLE client TO postgres;
GRANT ALL ON TABLE client TO legaltime_full;


--
-- Name: client_client_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE client_client_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE client_client_id_seq FROM postgres;
GRANT ALL ON SEQUENCE client_client_id_seq TO postgres;
GRANT ALL ON SEQUENCE client_client_id_seq TO legaltime_full;


--
-- Name: client_pref; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE client_pref FROM PUBLIC;
REVOKE ALL ON TABLE client_pref FROM postgres;
GRANT ALL ON TABLE client_pref TO postgres;
GRANT ALL ON TABLE client_pref TO legaltime_full;


--
-- Name: client_pref_client_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE client_pref_client_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE client_pref_client_id_seq FROM postgres;
GRANT ALL ON SEQUENCE client_pref_client_id_seq TO postgres;
GRANT ALL ON SEQUENCE client_pref_client_id_seq TO legaltime_full;


--
-- Name: customer_account_register_customer_account_register_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE customer_account_register_customer_account_register_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE customer_account_register_customer_account_register_id_seq FROM postgres;
GRANT ALL ON SEQUENCE customer_account_register_customer_account_register_id_seq TO postgres;
GRANT ALL ON SEQUENCE customer_account_register_customer_account_register_id_seq TO legaltime_full;


--
-- Name: customer_bill_rate_customer_bill_rate_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE customer_bill_rate_customer_bill_rate_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE customer_bill_rate_customer_bill_rate_id_seq FROM postgres;
GRANT ALL ON SEQUENCE customer_bill_rate_customer_bill_rate_id_seq TO postgres;
GRANT ALL ON SEQUENCE customer_bill_rate_customer_bill_rate_id_seq TO legaltime_full;


--
-- Name: customer_customer_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE customer_customer_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE customer_customer_id_seq FROM postgres;
GRANT ALL ON SEQUENCE customer_customer_id_seq TO postgres;
GRANT ALL ON SEQUENCE customer_customer_id_seq TO legaltime_full;


--
-- Name: expense_invoice_item_expense_invoice_item_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE expense_invoice_item_expense_invoice_item_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE expense_invoice_item_expense_invoice_item_id_seq FROM postgres;
GRANT ALL ON SEQUENCE expense_invoice_item_expense_invoice_item_id_seq TO postgres;
GRANT ALL ON SEQUENCE expense_invoice_item_expense_invoice_item_id_seq TO legaltime_full;


--
-- Name: expense_register_expense_register_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE expense_register_expense_register_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE expense_register_expense_register_id_seq FROM postgres;
GRANT ALL ON SEQUENCE expense_register_expense_register_id_seq TO postgres;
GRANT ALL ON SEQUENCE expense_register_expense_register_id_seq TO legaltime_full;


--
-- Name: followup_followup_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE followup_followup_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE followup_followup_id_seq FROM postgres;
GRANT ALL ON SEQUENCE followup_followup_id_seq TO postgres;
GRANT ALL ON SEQUENCE followup_followup_id_seq TO legaltime_full;


--
-- Name: invoice_invoice_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE invoice_invoice_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE invoice_invoice_id_seq FROM postgres;
GRANT ALL ON SEQUENCE invoice_invoice_id_seq TO postgres;
GRANT ALL ON SEQUENCE invoice_invoice_id_seq TO legaltime_full;


--
-- Name: labor_invoice_item_labor_invoice_item_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE labor_invoice_item_labor_invoice_item_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE labor_invoice_item_labor_invoice_item_id_seq FROM postgres;
GRANT ALL ON SEQUENCE labor_invoice_item_labor_invoice_item_id_seq TO postgres;
GRANT ALL ON SEQUENCE labor_invoice_item_labor_invoice_item_id_seq TO legaltime_full;


--
-- Name: labor_register_labor_register_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE labor_register_labor_register_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE labor_register_labor_register_id_seq FROM postgres;
GRANT ALL ON SEQUENCE labor_register_labor_register_id_seq TO postgres;
GRANT ALL ON SEQUENCE labor_register_labor_register_id_seq TO legaltime_full;


--
-- Name: payment_log_payment_log_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE payment_log_payment_log_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE payment_log_payment_log_id_seq FROM postgres;
GRANT ALL ON SEQUENCE payment_log_payment_log_id_seq TO postgres;
GRANT ALL ON SEQUENCE payment_log_payment_log_id_seq TO legaltime_full;


--
-- Name: security_privilege; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE security_privilege FROM PUBLIC;
REVOKE ALL ON TABLE security_privilege FROM postgres;
GRANT ALL ON TABLE security_privilege TO postgres;
GRANT ALL ON TABLE security_privilege TO legaltime_full;


--
-- Name: security_privilege_security_privilege_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE security_privilege_security_privilege_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE security_privilege_security_privilege_id_seq FROM postgres;
GRANT ALL ON SEQUENCE security_privilege_security_privilege_id_seq TO postgres;
GRANT ALL ON SEQUENCE security_privilege_security_privilege_id_seq TO legaltime_full;


--
-- Name: security_profile; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE security_profile FROM PUBLIC;
REVOKE ALL ON TABLE security_profile FROM postgres;
GRANT ALL ON TABLE security_profile TO postgres;
GRANT ALL ON TABLE security_profile TO legaltime_full;


--
-- Name: security_profile_grant; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE security_profile_grant FROM PUBLIC;
REVOKE ALL ON TABLE security_profile_grant FROM postgres;
GRANT ALL ON TABLE security_profile_grant TO postgres;
GRANT ALL ON TABLE security_profile_grant TO legaltime_full;


--
-- Name: security_profile_security_profile_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE security_profile_security_profile_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE security_profile_security_profile_id_seq FROM postgres;
GRANT ALL ON SEQUENCE security_profile_security_profile_id_seq TO postgres;
GRANT ALL ON SEQUENCE security_profile_security_profile_id_seq TO legaltime_full;


--
-- Name: simple_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE simple_log FROM PUBLIC;
REVOKE ALL ON TABLE simple_log FROM postgres;
GRANT ALL ON TABLE simple_log TO postgres;
GRANT ALL ON TABLE simple_log TO legaltime_full;


--
-- Name: sys_code; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE sys_code FROM PUBLIC;
REVOKE ALL ON TABLE sys_code FROM postgres;
GRANT ALL ON TABLE sys_code TO postgres;
GRANT ALL ON TABLE sys_code TO legaltime_full;


--
-- Name: sys_code_sys_code_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE sys_code_sys_code_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE sys_code_sys_code_id_seq FROM postgres;
GRANT ALL ON SEQUENCE sys_code_sys_code_id_seq TO postgres;
GRANT ALL ON SEQUENCE sys_code_sys_code_id_seq TO legaltime_full;


--
-- Name: tran_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tran_type FROM PUBLIC;
REVOKE ALL ON TABLE tran_type FROM postgres;
GRANT ALL ON TABLE tran_type TO postgres;
GRANT ALL ON TABLE tran_type TO legaltime_full;


--
-- Name: vw_user_grant; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE vw_user_grant FROM PUBLIC;
REVOKE ALL ON TABLE vw_user_grant FROM postgres;
GRANT ALL ON TABLE vw_user_grant TO postgres;
GRANT ALL ON TABLE vw_user_grant TO legaltime_full;


--
-- PostgreSQL database dump complete
--

