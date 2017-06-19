select distinct rel_org_pers.pers_id
,               is_afdeling
from   rel_org_pers
join   org_batch    on (org_batch.id = rel_org_pers.org_id)
join   pers         on (pers.id=rel_org_pers.pers_id and pers.deleted=0)
join   event_aanwezig on (event_aanwezig.pers_id = pers.id and event_id = 72)

