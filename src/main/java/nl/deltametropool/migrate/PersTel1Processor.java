package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersTel1Processor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String tel1 = JsonPath.read(item, "row.tel1");
		if(tel1!=null &&tel1.length()>0){
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("phone", tel1);
		  parameters.put("location_type_id", "1");
		  String response = processApi("Phone");
		  result = Utils.jsonAppend(result, "tel1", response);
		} else {
			result = Utils.jsonAppend(result,"tel1", "{}");
		}
		return result;	
	}

}
