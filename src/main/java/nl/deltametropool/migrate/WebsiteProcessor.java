package nl.deltametropool.migrate;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class WebsiteProcessor extends CiviApiProcessor {
	
	
	
	@Override
	public String doProcess(String item) throws Exception {
		
		
		String result = item;
		String website = JsonPath.read(item, "row.website");
		if(website!=null &&website.length()>0){
			
		  log.info("Processing WEBSITE "+website);	
			
	      if(!website.toLowerCase().startsWith("http"
	      		+ "://") && !website.toLowerCase().startsWith("https://")){
	    	  website = "http://"+website;
	      }
			
		  parameters.put("contact_id", JsonPath.read(item, "contact.id"));
		  parameters.put("url", website);
		  parameters.put("website_type_id", "1");
		  String response = processApi("Website");
		  result = Utils.jsonAppend(result, "Website", response);
		} else {
			result = Utils.jsonAppend(result,"Website", "{}");
		}
		return result;	
	}

}
