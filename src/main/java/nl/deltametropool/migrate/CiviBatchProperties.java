// CiviBatch - CopyRight Kainuk Empowerment 2016
package nl.deltametropool.migrate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component 
@ConfigurationProperties(prefix="civibatch")
public class CiviBatchProperties {

	String yourKey;
	String siteKey;
	String site;
	
	String dbUser;
	String dbPassword;
	String dbUrl;

	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getYourKey() {
		return yourKey;
	}
	public void setYourKey(String yourKey) {
		this.yourKey = yourKey;
	}
	public String getSiteKey() {
		return siteKey;
	}
	public void setSiteKey(String siteKey) {
		this.siteKey = siteKey;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}


}
