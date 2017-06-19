insert into pers_batch(id)
select pers_id from rel_org_pers
where  pers_id not in (select id from pers_batch)
and    org_id  in (select id from org_batch)