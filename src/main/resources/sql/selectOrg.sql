select distinct org.id
,      naam
,      bezoek_adres
,      bezoek_plaats
,      bezoek_pc
,      bezoek_land 
,      post_adres
,      post_plaats
,      post_pc
,      post_land
,      tel1
,      tel2
,      mail
,      website
,      replace(org.comment,'"','QuOtE') "comment"
,      website
from   org 
join   org_batch on (org_batch.id = org.id or org_batch.id=org.parent_id)
where  naam is not null and length(naam)>0
and    org.deleted=0

