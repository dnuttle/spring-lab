package net.nuttle.spring.beans;

/**
 *
 */
public class NameBean {
  
  private String name;
  private String id;
  
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

}
