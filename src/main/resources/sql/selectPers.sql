select distinct pers.id
,      pers.naam_voor
,      pers.naam_tussen
,      pers.naam_achter
,      pers.naam_prefix
,      pers.naam_letters
,      pers.tel1
,      pers.tel2
,      pers.tel3
,      pers.mail
,      pers.prive_adres
,      pers.prive_pc
,      pers.prive_plaats
,      pers.prive_land
,      pers.manvrouw
,      date_format(pers.geboren,'%Y%m%d')  "geboren"
,      pers.beroep
,      replace(pers.comment,'"','QuOtE') "comment"
,      pers.mailblock
,      pers.website
from   rel_org_pers
join   org_batch    on (org_batch.id = rel_org_pers.org_id)
join   pers         on (pers.id=rel_org_pers.pers_id)
and    pers.is_afdeling=0
and    pers.deleted=0
