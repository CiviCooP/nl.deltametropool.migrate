package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersHierarchyProcessor extends CiviApiProcessor {
	
	private static final String AFDELING = "15";
	private static final String WERKNEMER = "5";
	private static final String OUDWERKNEMER = "16";

	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String pers_id = JsonPath.read(item, "row.pers_id");
		String org_id = JsonPath.read(item, "row.org_id");	
		String is_afdeling = JsonPath.read(item, "row.is_afdeling");
		String functie = JsonPath.read(item, "row.functie");
		String visible = JsonPath.read(item, "row.visible");
	    if(is_afdeling.equalsIgnoreCase("0")){		
		    parameters.put("contact_id_a", this.findByExternalIdentifier("P"+pers_id));
		    parameters.put("contact_id_b", this.findByExternalIdentifier("O"+org_id));
		    if(functie!=null&&functie.length()>0){
		    	parameters.put("custom_23", functie);
		    }
		    if(visible.equalsIgnoreCase("1")){
		    	parameters.put("relationship_type_id", WERKNEMER);
		    } else {
		        parameters.put("relationship_type_id", OUDWERKNEMER);	
		    }		   
	    } else {
	    	parameters.put("contact_id_a", this.findByExternalIdentifier("A"+pers_id));
			parameters.put("contact_id_b", this.findByExternalIdentifier("O"+org_id));
			parameters.put("relationship_type_id", AFDELING);	
	    }
		String response = processApi("Relationship");
		result = Utils.jsonAppend(result, "PersHierarchyProcessor", response);		
		return result;	
	}

}
