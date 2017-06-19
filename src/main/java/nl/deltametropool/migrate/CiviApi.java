package nl.deltametropool.migrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CiviApi {

	@Autowired CiviBatchProperties civiBatchProperties;
	
	static String GET="get";
	static String CREATE="create";
	static String EMAIL="email";
	
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	
	private String url(String entity,String action){
		return  "https://"+civiBatchProperties.getSite()
		+       "/sites/all/modules/civicrm/extern/rest.php?entity="
		+       entity
		+       "&action=" + action
		+       "&json={json}"
	    +       "&api_key=" + civiBatchProperties.getYourKey()
		+       "&key="+civiBatchProperties.getSiteKey();
	}
	
	private String seqJson(String json){
		return "{\"sequential\":1,"+json.substring(1);
	}
	
	private String toJson(Object object) throws JsonProcessingException{		
		return seqJson(mapper.writeValueAsString(object));
	}
	
}
