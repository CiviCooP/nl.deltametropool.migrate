package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class ActivePhoneProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String phone = JsonPath.read(item, "row.tel1");
		String is_afdeling = JsonPath.read(item, "row.is_afdeling");
		String pers_id = JsonPath.read(item, "row.pers_id");
		if(phone!=null &&phone.length()>0){
		  String contact_id= this.findByExternalIdentifier(pers_id,is_afdeling);		
		  parameters.put("contact_id", contact_id);
		  parameters.put("phone", phone);
		  parameters.put("location_type_id", "Werk");
		  parameters.put("is_primary", "1");
		  String response = processApi("Phone");
		  result = Utils.jsonAppend(result, "Phone", response);
		} else {
			result = Utils.jsonAppend(result,"Phone", "{}");
		}
		return result;	
	}

}
