package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class TitleProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String label = JsonPath.read(item, "row.label");
		String value = JsonPath.read(item, "row.value");
	
		parameters.put("label", label);
		parameters.put("value",value);
		parameters.put("option_group_id","individual_prefix");
		String response = processApi("OptionValue");
		return Utils.jsonAppend(item, "Title", response);	
	}

}
