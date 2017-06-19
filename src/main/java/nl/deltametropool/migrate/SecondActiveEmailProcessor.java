package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class SecondActiveEmailProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String mail = JsonPath.read(item, "row.email2");
		String is_afdeling = JsonPath.read(item, "row.is_afdeling");
		String pers_id = JsonPath.read(item, "row.pers_id");
		if(mail!=null &&mail.length()>0){
		  String contact_id= this.findByExternalIdentifier(pers_id,is_afdeling);		
		  parameters.put("contact_id", contact_id);
		  parameters.put("email", mail);
		  parameters.put("location_type_id", "Werk");		 
		  String response = processApi("Email");
		  result = Utils.jsonAppend(result, "secondemail", response);
		} else {
			result = Utils.jsonAppend(result,"secondemail", "{}");
		}
		return result;	
	}

}
