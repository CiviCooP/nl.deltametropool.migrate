package nl.deltametropool.migrate;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
	
	Map<String,Object> map = new HashMap <String,Object>();
	
	void put(String name,Object value){
		map.put(name, value);
	}
	
	public String construct() throws Exception{
	   String result = "";
	   for(String key:map.keySet()){
		  result += "&"+key+"="+map.get(key).toString().trim(); 
	   }
	   return result;
			   
	}

}
