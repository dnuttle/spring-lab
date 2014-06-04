package net.nuttle.spring.beans;

import java.util.List;

/**
 *
 */
public class NameBean {
  
  private String name;
  private String id;
  private AddressBean address;
  private List<?> listValues;
  
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

}
