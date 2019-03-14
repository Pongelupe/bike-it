package br.com.pongelupe.bikeit.services;

import java.io.IOException;

public interface IPropertyService {

	String getProp(String propKey);
	
	void setProp(String propKey, String value);
	
	void setPropEager(String propKey, String value);
	
	void commitChanges() throws IOException;
}
