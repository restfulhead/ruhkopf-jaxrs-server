package name.ruhkopf.jaxrs.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;

@Component("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService 
{
	private Map<String, String> settings = new HashMap<>();

	public ConfigurationServiceImpl()
	{
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		
		for (Entry<Object, Object> entry : props.entrySet())
		{
			settings.put((String) entry.getKey(), (String) entry.getValue());
		}

	}
	
	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.ConfigurationService#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		return settings.get(key);
	}
}
