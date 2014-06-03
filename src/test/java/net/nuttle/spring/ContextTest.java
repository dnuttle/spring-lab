package net.nuttle.spring;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.nuttle.spring.beans.AddressBean;
import net.nuttle.spring.beans.NameBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 */
public class ContextTest {
  
  private static AbstractApplicationContext context;

  @BeforeClass
  public static void setup() {
    context = new ClassPathXmlApplicationContext("/net/nuttle/beans.xml");
  }
  
  @AfterClass
  public static void cleanup() {
    context.registerShutdownHook();
  }
  
  @Test
  public void testSingletonBean() {
    NameBean name1 = (NameBean)context.getBean("name");
    NameBean name2 = (NameBean)context.getBean("name");
    name2.setName("Not the original name");
    assertEquals(name1.getName(), name2.getName());
  }
  
  @Test
  public void testPrototypeBean() {
    AddressBean addr1 = (AddressBean)context.getBean("address");
    AddressBean addr2 = (AddressBean)context.getBean("address");
    addr2.setAddress("Not the original address");
    assertNotEquals(addr1.getAddress(), addr2.getAddress());
  }
  
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testNotEnoughConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-no-constructor-args");
  }
  
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testTooManyConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-too-many-constructor-args");
  }
  
  @SuppressWarnings("unused")
  @Test(expected = NoSuchBeanDefinitionException.class)
  public void testUndefinedBean() {
    NameBean name = (NameBean)context.getBean("no-such-bean-definition");
  }
  
  @Test
  public void testProperty() {
    AddressBean addr = (AddressBean)context.getBean("address");
    assertEquals(addr.getAddress(), "An address");
    addr.setAddress("New address");
    assertEquals(addr.getAddress(), "New address");
  }
  
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testInvalidProperty() {
    NameBean name = (NameBean)context.getBean("name-invalid-property");
  }
  
  @Test
  public void testTwoConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-two-constructor-args");
    assertEquals(name.getId(), "ID10001");
    assertEquals(name.getName(), "The name value");
  }
}
