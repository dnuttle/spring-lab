package net.nuttle.spring.beans;

public class NameBeanFactory {

  public static NameBean getNameBean() {
    NameBean bean = new NameBean("factory method default");
    return bean;
  }
  
  public static NameBean getNameBean(String name) {
    NameBean nameBean = new NameBean(name);
    return nameBean;
  }
}
