package net.nuttle.spring.beans;

public class NameBeanFactory {

  /**
   * Static factory method without args
   * @return
   */
  public static NameBean getNameBean() {
    NameBean bean = new NameBean("factory method default");
    return bean;
  }
  
  /**
   * Static factory method with arg
   * @param name
   * @return
   */
  public static NameBean getNameBean(String name) {
    NameBean nameBean = new NameBean(name);
    return nameBean;
  }
  
  /**
   * Non-static factory method
   * @param name
   * @return
   */
  public NameBean getNameBean2(String name) {
    NameBean nameBean = new NameBean(name);
    return nameBean;
  }
}
