select count(1) from org
where  parent_id in (select id from org_batch)
and    id not in (select id from org_batch)