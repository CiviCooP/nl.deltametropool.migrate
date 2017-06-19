package nl.deltametropool.migrate;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class OrgPostAddressProcessor extends CiviApiProcessor {
	
	Logger logger = LoggerFactory.getLogger(OrgPostAddressProcessor.class);
	
	@Override
	public String doProcess(String item) throws Exception {
		try{
		String result = item;
		String post_adres = JsonPath.read(item, "row.post_adres");
		
		if(post_adres!=null && post_adres.length()>0){
		  String post_land  = findCountryIsoCode(JsonPath.read(item, "row.post_land"));		
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("street_address", post_adres);
		  parameters.put("location_type_id", "Postadres");
		  parameters.put("city", JsonPath.read(item, "row.post_plaats"));
		  parameters.put("postal_code", JsonPath.read(item, "row.post_pc"));
		  parameters.put("country_id", post_land);
		  String response = processApi("Address");
		  result = Utils.jsonAppend(result, "postAddress", response);
		} else {
			result = Utils.jsonAppend(result,"postAddress", "{}");
		}
		return result;	
		}
		catch(Exception e){
		   logger.info(e.toString());	
		   logger.info(item);	
		   return null;
		}
	}

}
