package nl.deltametropool.migrate;




import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersTel3Processor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String tel3 = JsonPath.read(item, "row.tel3");
		if(tel3!=null &&tel3.length()>0){
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("phone", tel3);
		  parameters.put("location_type_id", "1");
		  String response = processApi("Phone");
		  result = Utils.jsonAppend(result, "tel3", response);
		} else {
			result = Utils.jsonAppend(result,"tel3", "{}");
		}
		return result;	
	}

}
