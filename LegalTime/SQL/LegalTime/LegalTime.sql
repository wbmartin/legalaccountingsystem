DROP TABLE public.sys_code;
DROP TABLE public.followup;
DROP TABLE public.customer_bill_rate;
DROP TABLE public.labor_invoice_item;
DROP TABLE public.payment_log;
DROP TABLE public.expense_invoice_item;
DROP TABLE public.labor_register;
DROP TABLE public.expense_register;
DROP TABLE public.invoice;
DROP TABLE public.customer_account_register;
DROP TABLE public.customer;
DROP TABLE public.client_pref;
DROP TABLE public.user_info;
DROP TABLE public.tran_type;

CREATE TABLE public.tran_type (
       client_id INTEGER NOT NULL
     , tran_type VARCHAR(5) NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT now()
     , description VARCHAR(20)
);

CREATE TABLE public.user_info (
       user_id TEXT NOT NULL
     , client_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT now()
     , default_bill_rate DOUBLE PRECISION
     , email_addr VARCHAR(255)
     , display_name VARCHAR(25)
);

CREATE TABLE public.client_pref (
       client_id SERIAL NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , client_name TEXT
);

CREATE TABLE public.customer (
       customer_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , first_name VARCHAR(50)
     , last_name VARCHAR(50)
     , address TEXT
     , city VARCHAR(50)
     , state CHAR(2)
     , zip VARCHAR(10)
     , work_phone VARCHAR(30)
     , home_phone VARCHAR(30)
     , fax VARCHAR(30)
     , email VARCHAR(100)
     , client_since_dt DATE
     , note TEXT
     , bill_type VARCHAR(25)
     , monthly_bill_rate DOUBLE PRECISION
     , active_yn CHAR(1)
     , mortgage_amount DOUBLE PRECISION
     , contingency_rate DOUBLE PRECISION
);

CREATE TABLE public.customer_account_register (
       client_account_register_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT now()
     , effective_dt CHAR(10)
     , description TEXT
     , tran_amt DOUBLE PRECISION
     , tran_type VARCHAR(5)
);

CREATE TABLE public.invoice (
       invoice_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , invoice_dt DATE
     , generated_dt DATE
     , invoice_total_amt DOUBLE PRECISION
     , prev_balance_due DOUBLE PRECISION
);

CREATE TABLE public.expense_register (
       expense_register_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT now()
     , description TEXT
     , amount DOUBLE PRECISION
     , invoice_id INTEGER
     , invoiceable BOOL
);

CREATE TABLE public.labor_register (
       labor_register_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE DEFAULT now()
     , description TEXT
     , minute_count INTEGER
     , start_time TIMESTAMP
     , end_time TIMESTAMP
     , activity_date DATE
     , invoiceable BOOL
     , bill_rate DOUBLE PRECISION
     , invoice_id INTEGER
     , user_id TEXT
);

CREATE TABLE public.expense_invoice_item (
       expense_invoice_item_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , expense_dt DATE
     , expense_description TEXT
     , amount DOUBLE PRECISION
     , invoice_id INTEGER
);

CREATE TABLE public.payment_log (
       payment_log_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , effective_dt DATE
     , description TEXT
     , amount DOUBLE PRECISION
     , invoice_id INTEGER
     , client_account_register_id INTEGER
);

CREATE TABLE public.labor_invoice_item (
       labor_invoice_item_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , customer_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , activity_dt DATE
     , activity_description TEXT
     , user_id TEXT
     , invoice_id INTEGER NOT NULL
     , hours_billed DOUBLE PRECISION
     , bill_rate DOUBLE PRECISION
);

CREATE TABLE public.customer_bill_rate (
       customer_bill_rate_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , customer_id INTEGER
     , user_id TEXT
     , bill_rate DOUBLE PRECISION
);

CREATE TABLE public.followup (
       followup_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , customer_id INTEGER
     , due_dt DATE
     , open_dt DATE
     , close_dt DATE
     , followup_description TEXT
     , assigned_user_id TEXT
);

CREATE TABLE public.sys_code (
       sys_code_id SERIAL NOT NULL
     , client_id INTEGER NOT NULL
     , last_update TIMESTAMP(3) WITHOUT TIME ZONE
     , code_type VARCHAR(5)
     , code_id VARCHAR(5)
     , description VARCHAR(255)
     , system_or_user VARCHAR(20)
);

ALTER TABLE public.tran_type
  ADD CONSTRAINT PK_TRAN_TYPE
      PRIMARY KEY (client_id, tran_type);

ALTER TABLE public.user_info
  ADD CONSTRAINT PK_USER_INFO
      PRIMARY KEY (user_id, client_id);

ALTER TABLE public.client_pref
  ADD CONSTRAINT PK_CLIENT_PREF
      PRIMARY KEY (client_id);

ALTER TABLE public.customer
  ADD CONSTRAINT PK_CUSTOMER
      PRIMARY KEY (customer_id, client_id);

ALTER TABLE public.customer_account_register
  ADD CONSTRAINT PK_CUSTOMER_ACCOUNT_REGISTER
      PRIMARY KEY (client_account_register_id, client_id, customer_id);

ALTER TABLE public.invoice
  ADD CONSTRAINT PK_INVOICE
      PRIMARY KEY (invoice_id, client_id, customer_id);

ALTER TABLE public.expense_register
  ADD CONSTRAINT PK_EXPENSE_REGISTER
      PRIMARY KEY (expense_register_id, client_id, customer_id);

ALTER TABLE public.labor_register
  ADD CONSTRAINT PK_LABOR_REGISTER
      PRIMARY KEY (labor_register_id, client_id, customer_id);

ALTER TABLE public.expense_invoice_item
  ADD CONSTRAINT PK_EXPENSE_INVOICE_ITEM
      PRIMARY KEY (expense_invoice_item_id, client_id, customer_id);

ALTER TABLE public.payment_log
  ADD CONSTRAINT PK_PAYMENT_LOG
      PRIMARY KEY (payment_log_id, client_id, customer_id);

ALTER TABLE public.labor_invoice_item
  ADD CONSTRAINT PK_LABOR_INVOICE_ITEM
      PRIMARY KEY (labor_invoice_item_id, client_id, customer_id);

ALTER TABLE public.customer_bill_rate
  ADD CONSTRAINT PK_CUSTOMER_BILL_RATE
      PRIMARY KEY (customer_bill_rate_id, client_id);

ALTER TABLE public.followup
  ADD CONSTRAINT PK_FOLLOWUP
      PRIMARY KEY (followup_id, client_id);

ALTER TABLE public.sys_code
  ADD CONSTRAINT PK_SYS_CODE
      PRIMARY KEY (sys_code_id, client_id);

ALTER TABLE public.customer
  ADD CONSTRAINT FK_customer_1
      FOREIGN KEY (client_id)
      REFERENCES public.client_pref (client_id);

ALTER TABLE public.customer_account_register
  ADD CONSTRAINT FK_client_account_register_2
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.customer_account_register
  ADD CONSTRAINT FK_client_account_register_3
      FOREIGN KEY (client_id, tran_type)
      REFERENCES public.tran_type (client_id, tran_type);

ALTER TABLE public.invoice
  ADD CONSTRAINT FK_invoice_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.expense_register
  ADD CONSTRAINT FK_expense_register_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.labor_register
  ADD CONSTRAINT FK_labor_register_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.labor_register
  ADD CONSTRAINT FK_labor_register_2
      FOREIGN KEY (user_id, client_id)
      REFERENCES public.user_info (user_id, client_id);

ALTER TABLE public.labor_register
  ADD CONSTRAINT FK_labor_register_3
      FOREIGN KEY (invoice_id, client_id, customer_id)
      REFERENCES public.invoice (invoice_id, client_id, customer_id);

ALTER TABLE public.expense_invoice_item
  ADD CONSTRAINT FK_expense_invoice_item_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.expense_invoice_item
  ADD CONSTRAINT FK_expense_invoice_item_2
      FOREIGN KEY (invoice_id, client_id, customer_id)
      REFERENCES public.invoice (invoice_id, client_id, customer_id);

ALTER TABLE public.payment_log
  ADD CONSTRAINT FK_payment_log_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.payment_log
  ADD CONSTRAINT FK_payment_log_2
      FOREIGN KEY (client_account_register_id, client_id, customer_id)
      REFERENCES public.customer_account_register (client_account_register_id, client_id, customer_id);

ALTER TABLE public.payment_log
  ADD CONSTRAINT FK_payment_log_3
      FOREIGN KEY (invoice_id, client_id, customer_id)
      REFERENCES public.invoice (invoice_id, client_id, customer_id);

ALTER TABLE public.labor_invoice_item
  ADD CONSTRAINT FK_laborinvoiceitem_customer
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.labor_invoice_item
  ADD CONSTRAINT FK_laborinvoiceitem_invoice
      FOREIGN KEY (invoice_id, client_id, customer_id)
      REFERENCES public.invoice (invoice_id, client_id, customer_id);

ALTER TABLE public.customer_bill_rate
  ADD CONSTRAINT FK_customer_bill_rate_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.customer_bill_rate
  ADD CONSTRAINT FK_customer_bill_rate_2
      FOREIGN KEY (user_id, client_id)
      REFERENCES public.user_info (user_id, client_id);

ALTER TABLE public.followup
  ADD CONSTRAINT FK_followup_1
      FOREIGN KEY (customer_id, client_id)
      REFERENCES public.customer (customer_id, client_id);

ALTER TABLE public.sys_code
  ADD CONSTRAINT FK_syscode_client
      FOREIGN KEY (client_id)
      REFERENCES public.client_pref (client_id);

