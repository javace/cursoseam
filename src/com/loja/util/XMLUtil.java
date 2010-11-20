package com.loja.util;

import com.thoughtworks.xstream.XStream;

public class XMLUtil {
	
	public String toXML(Object element) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(element);
		return xml;
	}
	
	public Object fromXML(String element) {
		XStream xstream = new XStream();
		return xstream.fromXML(element);
	}

}
