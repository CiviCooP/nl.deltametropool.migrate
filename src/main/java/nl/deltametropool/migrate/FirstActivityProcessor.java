package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class FirstActivityProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String pers_id = JsonPath.read(item, "row.pers_id");
		String datum = JsonPath.read(item, "row.datum");
		String contact_id = this.findByExternalIdentifier("P"+pers_id);				
		parameters.put("id",contact_id);
	    parameters.put("source","Eerste contact  "+datum);	
	    log.info("First Activity contact id "+contact_id);
		String response = processApi("Contact");
		result = Utils.jsonAppend(result, "FirstActivity", response);
		
		return result;	
	}

}
