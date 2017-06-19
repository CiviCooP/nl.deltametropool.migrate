package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PrivatePicksProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String is_afdeling = JsonPath.read(item, "row.is_afdeling");		
		String contact_id = this.findByExternalIdentifier(JsonPath.read(item, "row.pers_id"),is_afdeling);				
		parameters.put("group_id", "Private_Picks_11");
	    parameters.put("contact_id",contact_id);				  
		String response = processApi("GroupContact");
		result = Utils.jsonAppend(result, "PrivatPicks", response);
		
		return result;	
	}

}
