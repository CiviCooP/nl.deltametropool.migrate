package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class AfdelingProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String legal_name = JsonPath.read(item, "row.naam_afdeling");
		String external_identifier = "A"+JsonPath.read(item, "row.id");
		String mailblock = JsonPath.read(item, "row.mailblock");
		parameters.put("contact_type", "Organization");
		parameters.put("external_identifier",external_identifier);
		parameters.put("organization_name",legal_name);
		parameters.put("mailblock",mailblock);
		parameters.put("preferred_language", "nl_NL");
		String response = processApi("Contact");
		return Utils.jsonAppend(item, "contact", response);	
	}

}
