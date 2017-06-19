package nl.deltametropool.migrate;



import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class OrgVisitAddressProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String bezoek_adres = JsonPath.read(item, "row.bezoek_adres");
		if(bezoek_adres!=null && bezoek_adres.length()>0){
	      String bezoek_land = findCountryIsoCode(JsonPath.read(item, "row.bezoek_land"));		
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("street_address", bezoek_adres);
		  parameters.put("location_type_id", "Primair");
		  parameters.put("city", JsonPath.read(item, "row.bezoek_plaats"));
		  parameters.put("postal_code", JsonPath.read(item, "row.bezoek_pc"));
		  parameters.put("country_id", bezoek_land);
		  String response = processApi("Address");
		  result = Utils.jsonAppend(result, "visitAddress", response);
		} else {
			result = Utils.jsonAppend(result,"visitAddress", "{}");
		}
		return result;	
	}

}
