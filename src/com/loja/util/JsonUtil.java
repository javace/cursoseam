package com.loja.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JsonUtil {
	
	@SuppressWarnings("unchecked")
	public String toJson(Class classe, Object object) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("usuario", classe);
		return xstream.toXML(object);
	}
	
	@SuppressWarnings("unchecked")
	public Object fromJson(Class classe, String element) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias(classe.getSimpleName().toLowerCase(), classe);
		return xstream.fromXML(element);
	}

}
