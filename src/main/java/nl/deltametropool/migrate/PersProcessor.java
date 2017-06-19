package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class PersProcessor extends CiviApiProcessor {
	
	
	/*
	 * select id
,      naam_voor
,      naam_tussen
,      naam_achter
,      tel1
,      tel2
,      tel3
,      mail
,      prive_adres
,      prive_pc
,      prive_plaats
,      prive_land
,      manvrouw
,      beroep(non-Javadoc)
	 * @see nl.kainuk.dpconv.CiviApiProcessor#doProcess(java.lang.String)
	 */
	
	private String translateGender(String value){
		if(value.equalsIgnoreCase("vrouw")){
		  return "1";
		}
		if(value.equalsIgnoreCase("man")){
			  return "2";
		}
		return "";
	}
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String naam_voor = JsonPath.read(item, "row.naam_voor");
		String naam_prefix = JsonPath.read(item, "row.naam_prefix");
		String naam_letters = JsonPath.read(item, "row.naam_letters");
		String naam_tussen = JsonPath.read(item, "row.naam_tussen");
		String naam_achter = JsonPath.read(item, "row.naam_achter");
		String beroep      = JsonPath.read(item, "row.beroep");
		String manvrouw    = JsonPath.read(item, "row.manvrouw");
		String geboren     = JsonPath.read(item, "row.geboren");
		//String source      = JsonPath.read(item, "row.source");
		String mailblock   = JsonPath.read(item,"row.mailblock");
		if(geboren.equalsIgnoreCase("00000000")){
			geboren = "";
		}
		String external_identifier = "P"+JsonPath.read(item, "row.id");
		parameters.put("contact_type", "Individual");
		if(naam_prefix!=null&&naam_prefix.length()>0){
			parameters.put("prefix_id", naam_prefix);
		} else {
			parameters.put("prefix_id", "");	
		}
		parameters.put("external_identifier",external_identifier);
		parameters.put("nick_name",naam_letters);
		parameters.put("first_name",naam_voor);
		parameters.put("middle_name",naam_tussen);
		parameters.put("last_name",naam_achter);
		parameters.put("job_title",beroep);
		parameters.put("gender",translateGender(manvrouw));
		parameters.put("birth_date",geboren);
		parameters.put("is_opt_out",mailblock);
		parameters.put("preferred_language", "nl_NL");
		
		parameters.put("source","Conversie");
		
		String response = processApi("Contact");
		return Utils.jsonAppend(item, "contact", response);	
	}

}
