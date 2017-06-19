package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class EmployerProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String is_afdeling = JsonPath.read(item, "row.is_afdeling");
		if(is_afdeling.equalsIgnoreCase("0")){
		   String pers_id = JsonPath.read(item, "row.pers_id");
		   String org_id = JsonPath.read(item, "row.org_id");
		   String contact_id= this.findByExternalIdentifier('P'+pers_id);
		   String current_employer= this.findByExternalIdentifier('O'+org_id);				
		   parameters.put("contact_id", contact_id);
		   parameters.put("employer_id", current_employer);		
		   String response = processApi("Contact");
		   result = Utils.jsonAppend(result, "CurrentEmployer", response);		
	    } else {
	    	result = Utils.jsonAppend(result, "CurrentEmployer", "{}");
	    }
		return result;	
	}

}
