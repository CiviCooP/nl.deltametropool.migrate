select distinct org.id
,      org.parent_id
from   org
join   org_batch oba on (oba.id = org.id)
join   org_batch obb on (obb.id = org.parent_id)
join   org parent on (parent.id=org.parent_id and parent.deleted=0)
where  org.parent_id is not null and org.parent_id!=0 and org.deleted=0