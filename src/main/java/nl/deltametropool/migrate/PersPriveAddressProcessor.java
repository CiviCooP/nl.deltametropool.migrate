package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersPriveAddressProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String prive_adres = JsonPath.read(item, "row.prive_adres");
		String prive_land  = findCountryIsoCode(JsonPath.read(item, "row.prive_land"));
		
		if(prive_adres!=null && prive_adres.length()>0){
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("street_address", prive_adres);
		  parameters.put("location_type_id", "1");
		  parameters.put("city", JsonPath.read(item, "row.prive_plaats"));
		  parameters.put("postal_code", JsonPath.read(item, "row.prive_pc"));
		  parameters.put("city", JsonPath.read(item, "row.prive_plaats"));
		  parameters.put("country_id", prive_land);
		  String response = processApi("Address");
		  result = Utils.jsonAppend(result, "visitAddress", response);
		} else {
			result = Utils.jsonAppend(result,"visitAddress", "{}");
		}
		return result;	
	}

}
