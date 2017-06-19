package nl.deltametropool.migrate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BatchRowMapper implements RowMapper <String> {
	
	private static String QOUTE ="\"";

	@Override
	public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		StringBuffer buffy = new StringBuffer();	
		boolean first = true;
		for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
			if(first){
				first = false;
			} else {
			    buffy.append(", ");
			}
			buffy.append(QOUTE);
			String columnName = resultSet.getMetaData().getColumnName(i);
			buffy.append(columnName);
			buffy.append(QOUTE);
			buffy.append(" : ");
			buffy.append(QOUTE);
			buffy.append( resultSet.getString(i));
			buffy.append(QOUTE);
		}				
		return Utils.jsonCreate("row","{"+buffy.toString()+"}");
	}

}
