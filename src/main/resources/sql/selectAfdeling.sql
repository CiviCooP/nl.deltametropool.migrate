select distinct pers.id
,      pers.naam_afdeling
,      pers.tel1
,      pers.tel2
,      pers.tel3
,      pers.mail
,      pers.prive_adres
,      pers.prive_pc
,      pers.prive_plaats
,      pers.prive_land
,      pers.manvrouw
,      pers.beroep
,      replace(pers.comment,'"','QuOtE') "comment"
,      mailblock
,      pers.website
from   rel_org_pers
join   org_batch    on (org_batch.id = rel_org_pers.org_id)
join   pers         on (pers.id=rel_org_pers.pers_id)
and pers.is_afdeling=1
and pers.deleted=0