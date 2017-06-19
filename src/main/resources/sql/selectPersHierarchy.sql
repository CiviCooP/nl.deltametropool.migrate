select distinct rel_org_pers.pers_id
,      rel_org_pers.org_id
,      pers.is_afdeling 
,      rel_org_pers.functie
,      rel_org_pers.visible
,      rel_org_pers.selected
,      replace(pers.comment,'"','QuOtE') "comment"
from   rel_org_pers
join   org_batch    on (org_batch.id = rel_org_pers.org_id)
join   pers         on (pers.id=rel_org_pers.pers_id and pers.deleted=0)
join   org          on (org_batch.id = rel_org_pers.org_id and org.deleted=0)