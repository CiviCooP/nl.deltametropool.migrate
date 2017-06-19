insert into org_batch(id)
select id from org
where  parent_id in (select id from org_batch)
and    id not in (select id from org_batch)