package br.com.pongelupe.bikeit.services;

import java.io.IOException;
import java.util.Optional;

public interface IPropertyService {

	Optional<String> getProp(String propKey);
	
	void setProp(String propKey, String value);
	
	void setPropEager(String propKey, String value);
	
	void commitChanges() throws IOException;
}
