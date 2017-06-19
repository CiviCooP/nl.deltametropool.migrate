package nl.deltametropool.migrate;

import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

@Component
public class CommentProcessor extends CiviApiProcessor {
	
	@Override
	public String doProcess(String item) throws Exception {
		
		String result = item;
		String comment = JsonPath.read(item, "row.comment");
		if(comment!=null &&comment.length()>0){
		  parameters.put("entity_id", JsonPath.read(item, "contact.id"));
		  parameters.put("note", comment.replace("QuOtE", "\""));
		  parameters.put("entity_table", "civicrm_contact");
		  parameters.put("title", "Notitite vanuit CMS");
		  
		  String response = processApi("Note");
		  result = Utils.jsonAppend(result, "PersComment", response);
		} else {
			result = Utils.jsonAppend(result,"PersComment", "{}");
		}
		return result;	
	}

}
