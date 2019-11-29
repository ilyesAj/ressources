package isty.iatic5.arlo.res.util;

import java.time.LocalTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter pour le type {@link LocalTime}
 * 
 * @author Clément Lefevre
 *
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime>{

	@Override
	public LocalTime unmarshal(String v) throws Exception {
		return LocalTime.parse(v);
	}

	@Override
	public String marshal(LocalTime v) throws Exception {
		return v.toString();
	}

}
