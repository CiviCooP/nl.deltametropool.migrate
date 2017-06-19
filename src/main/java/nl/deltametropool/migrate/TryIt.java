package nl.deltametropool.migrate;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

public class TryIt {

	public static void main(String[] args) throws Exception {
		String result = Utils.loadJSON("result");
		System.out.println(JsonPath.read(result,"PersHierarchyProcessor.id").toString());
		//iso_codes.get(0);

	}

}
