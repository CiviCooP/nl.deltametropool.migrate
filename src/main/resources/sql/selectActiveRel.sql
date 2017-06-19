select distinct pers_id
,               is_afdeling
,               rel.email1
,               rel.email2
,               rel.tel1
,               rel.org_id
from   rel_org_pers rel
join   org_batch    on (org_batch.id = rel.org_id)
join   pers         on (pers.id=rel.pers_id)
where  rel.selected = 1

