package nl.deltametropool.migrate;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public class Utils {
	
	private static String QOUTE ="\"";
	
	public static String jsonCreate(String label, String json){
		return "{"+QOUTE+label+QOUTE+":"+json+"}";
	}
	
	public static String jsonAppend(String message, String label, String json){
		return message.substring(0,message.length()-1)+","+QOUTE+label+QOUTE+":"+json+"}";
	}
	
	public static String loadSQL(String name) throws IOException{
		Resource resource = new ClassPathResource("sql/"+name+".sql");
		return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));	
	}
	
	public static String loadJSON(String name) throws IOException{
		Resource resource = new ClassPathResource("json/"+name+".json");
		return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));	
	}

}
