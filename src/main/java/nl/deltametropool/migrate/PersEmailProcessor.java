package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersEmailProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String mail = JsonPath.read(item, "row.mail");
		if(mail!=null &&mail.length()>0){
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("email", mail);
		  parameters.put("location_type_id", "Primair");
		  String response = processApi("Email");
		  result = Utils.jsonAppend(result, "email", response);
		} else {
			result = Utils.jsonAppend(result,"email", "{}");
		}
		return result;	
	}

}
