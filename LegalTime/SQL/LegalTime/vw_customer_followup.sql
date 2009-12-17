create or replace view vw_customer_followup AS 
select 
	followup.followup_id AS followup_id,
	followup.due_dt AS due_dt,
	followup.open_dt AS open_dt,
	followup.close_dt AS close_dt,
	followup.followup_description AS followup_description,
	customer.first_name AS first_name,
	customer.last_name AS last_name 
from (followup left join customer on((followup.customer_id = customer.customer_id))) 
where followup.close_dt is null 
order by followup.due_dt

ALTER TABLE vw_customer_hourly_bill_rate OWNER TO postgres;
GRANT ALL ON TABLE vw_customer_followup TO postgres;
GRANT ALL ON TABLE vw_customer_followup TO legaltime_full;