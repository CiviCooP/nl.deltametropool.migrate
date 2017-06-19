select pers_id, date_format(datum,'%d-%m-%Y') datum from event_aanmelding
where  datum is not null
and    pers_id in (select pers.id
  from   rel_org_pers
  join   org_batch    on (org_batch.id = rel_org_pers.org_id)
  join   pers         on (pers.id=rel_org_pers.pers_id)
  and    pers.is_afdeling=0
  and    pers.deleted=0)
group by pers_id
