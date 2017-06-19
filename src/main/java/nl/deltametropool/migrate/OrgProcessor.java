package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class OrgProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String legal_name = JsonPath.read(item, "row.naam");		
		String external_identifier = "O"+JsonPath.read(item, "row.id");
		parameters.put("contact_type", "Organization");
		parameters.put("external_identifier",external_identifier);
		parameters.put("organization_name",legal_name);		
		parameters.put("preferred_language", "nl_NL");
		String response = processApi("Contact");
		return Utils.jsonAppend(item, "contact", response);	
	}

}
