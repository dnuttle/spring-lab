package net.nuttle.spring.beans;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
public class NameBean {
  
  private String name;
  private String id;
  private AddressBean address;
  private List<?> listValues;
  private Map<String,Integer> mapValues;
  private Set<?> setValues;
  private Properties props;
  private StringBean stringBean;

  public NameBean(String name) {
    this.name = name;
  }
  
  public NameBean(String name, String id) {
    this.name = name;
    this.id = id;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public String getId() {
    return id;
  }
  
  public void setAddress(AddressBean address) {
    this.address = address;
  }
  
  public AddressBean getAddress() {
    return address;
  }
  
  public void setListValues(List<?> values) {
    listValues = values;
  }
  
  public List<?> getListValues() {
    return listValues;
  }
  
  public void setMapValues(Map<String,Integer> values) {
    mapValues = values;
  }
  
  public Map<String,Integer> getMapValues() {
    return mapValues;
  }
  
  public void setSetValues(Set<?> values) {
    setValues= values;
  }
  
  public Set<?> getSetValues() {
    return setValues;
  }
  
  public void setProperties(Properties props) {
    this.props = props;
  }

  public String getProperty(String key) {
    return props.getProperty(key);
  }
  
  public void setStringBean(StringBean stringBean) {
    this.stringBean = stringBean;
  }
  
  public StringBean getStringBean() {
    return stringBean;
  }

}

