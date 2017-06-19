package nl.deltametropool.migrate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

 abstract class CiviApiProcessor implements ItemProcessor<String,String>{
	protected final Logger log = LoggerFactory.getLogger(CiviApiProcessor.class);

	@Autowired CiviBatchProperties civiBatchProperties;
	Parameters parameters = new Parameters();
	
	static String GET="get";
	static String CREATE="create";
	static String EMAIL="email";
	static String CONTACT="contact";
	
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	
	private String url(String entity,String action){
		return  civiBatchProperties.getSite()
		+       "/sites/all/modules/civicrm/extern/rest.php?entity="
		+       entity
		+       "&action=" + action
		+       "&json=1"
	    +       "&api_key=" + civiBatchProperties.getYourKey()
		+       "&key="+civiBatchProperties.getSiteKey();
	}
	
	abstract String doProcess(String item) throws Exception;
	
	String formatException(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter  pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return "\""+sw.toString()+"\"";
		
	}
	
	public String process(String item) throws Exception{
		try{
		  if(JsonPath.read(item, "$.[?(@.is_error!=0)]").toString().length()>0){
		       return doProcess(item);
		  } else {
			  log.info("-----------------Error--------------------------------");
			  log.info(item);
			  return item;
		   }
		} catch (Exception e){
			log.info(item);
			log.info(formatException(e));
			return  Utils.jsonAppend(item,"endWithException", formatException(e));	
		}
	}
	
	protected String processApi(String api) throws Exception{
		//log.info(url(api,CREATE)+parameters.construct());
		return restTemplate.postForObject(url(api,CREATE)+parameters.construct(),null,String.class);
	}
	
	protected String findByExternalIdentifier(String identifier) throws Exception{
		Parameters findParameters = new Parameters();
		findParameters.put("external_identifier", identifier);
		String result = restTemplate.postForObject(url("contact",GET)+findParameters.construct(),null,String.class);
		return JsonPath.read(result,"id").toString();
	}
	
	protected String findByExternalIdentifier(String identifier,String is_afdeling) throws Exception{
		String external_identifier;
		if(is_afdeling.equalsIgnoreCase("0")){
			external_identifier = "P"+ identifier;
		} else {
			external_identifier = "A"+ identifier;
		}
		
		Parameters findParameters = new Parameters();
		findParameters.put("external_identifier", external_identifier);
		String result = restTemplate.postForObject(url("contact",GET)+findParameters.construct(),null,String.class);
		return JsonPath.read(result,"id").toString();
	}
	
	protected String findCountryIsoCode(String countryName) throws Exception {
		if(countryName==null||countryName.length()==0){
			return "NL";
		} else {				
		  Parameters findParameters = new Parameters();
		  findParameters.put("name", countryName);
		  String result = restTemplate.postForObject(url("country",GET)+findParameters.construct(),null,String.class);
		  List<String> iso_codes = (List<String>) JsonPath.read(result,"$..iso_code");
		  return iso_codes.get(0);
		}
	}
	
}	
		
