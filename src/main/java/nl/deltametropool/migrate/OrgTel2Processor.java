package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class OrgTel2Processor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String tel2 = JsonPath.read(item, "row.tel2");
		if(tel2!=null &&tel2.length()>0){
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("phone", tel2);
		  parameters.put("location_type_id", "3");
		  String response = processApi("Phone");
		  result = Utils.jsonAppend(result, "tel2", response);
		} else {
			result = Utils.jsonAppend(result,"tel2", "{}");
		}
		return result;	
	}

}
