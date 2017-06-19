package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class OrgHierarchyProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String id = JsonPath.read(item, "row.id");
		String parent_id = JsonPath.read(item, "row.parent_id");
		if(parent_id!=null &&parent_id.length()>0){
		  parameters.put("contact_id_a", this.findByExternalIdentifier("O"+id));
		  parameters.put("contact_id_b", this.findByExternalIdentifier("O"+parent_id));
		  parameters.put("relationship_type_id", "15");
		  parameters.put("debug", "1");
		  String response = processApi("Relationship");
		  result = Utils.jsonAppend(result, "OrgHierarchyProcessor", response);
		} else {
			result = Utils.jsonAppend(result,"OrgHierarchyProcessor", "{}");
		}
		return result;	
	}

}
