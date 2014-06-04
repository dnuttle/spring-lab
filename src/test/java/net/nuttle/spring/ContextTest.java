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
 * Some unit tests for Spring contexts
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

  /**
   * Get singleton bean
   */
  @Test
  public void testSingletonBean() {
    NameBean name1 = (NameBean)context.getBean("name");
    NameBean name2 = (NameBean)context.getBean("name");
    name2.setName("Not the original name");
    assertEquals(name1.getName(), name2.getName());
  }
  
  /**
   * Get prototype bean
   */
  @Test
  public void testPrototypeBean() {
    AddressBean addr1 = (AddressBean)context.getBean("address");
    AddressBean addr2 = (AddressBean)context.getBean("address");
    addr2.setAddress("Not the original address");
    assertNotEquals(addr1.getAddress(), addr2.getAddress());
  }

  /**
   * Get bean from definition with too few constructor args
   */
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testNotEnoughConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-no-constructor-args");
  }

  /**
   * Get bean from definition with too many constructor args
   */
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testTooManyConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-too-many-constructor-args");
  }

  /**
   * Get bean that has no definition
   */
  @SuppressWarnings("unused")
  @Test(expected = NoSuchBeanDefinitionException.class)
  public void testUndefinedBean() {
    NameBean name = (NameBean)context.getBean("no-such-bean-definition");
  }

  /**
   * Get bean created with property rather than constructor
   */
  @Test
  public void testProperty() {
    AddressBean addr = (AddressBean)context.getBean("address");
    assertEquals(addr.getAddress(), "An address");
    addr.setAddress("New address");
    assertEquals(addr.getAddress(), "New address");
  }

  /**
   * Get bean using invalid property
   */
  @SuppressWarnings("unused")
  @Test(expected = BeanCreationException.class)
  public void testInvalidProperty() {
    NameBean name = (NameBean)context.getBean("name-invalid-property");
  }

  /**
   * Get bean using two constructor args
   */
  @Test
  public void testTwoConstructorArgs() {
    NameBean name = (NameBean)context.getBean("name-two-constructor-args");
    assertEquals(name.getId(), "ID10001");
    assertEquals(name.getName(), "The name value");
  }
  
  /**
   * Get bean that refers to another bean
   */
  @Test
  public void testBeanWithRef() {
    NameBean name = (NameBean)context.getBean("name-with-address");
    assertEquals(name.getAddress().getAddress(), "An address");
  }

  /**
   * Get bean whose definition is imported from another xml file
   */
  @Test
  public void testImportedBean() {
    NameBean name = (NameBean)context.getBean("yet-another-name");
    assertEquals(name.getName(), "Name");
  }

  /**
   * Get a bean mapped by an alias
   */
  @Test
  public void testAlias() {
    NameBean name = (NameBean)context.getBean("yet-another-name");
    NameBean name2 = (NameBean)context.getBean("yet-another-alias");
    assertEquals(name, name2);
  }

  /**
   * Get bean from static factory method
   */
  @Test
  public void testFactoryMethod() {
    //method with no args
    NameBean name = (NameBean)context.getBean("name-factory");
    assertEquals(name.getName(), "factory method default");
    //method with arg
    name = (NameBean)context.getBean("name-factory-2");
    assertEquals(name.getName(), "factory name");
  }
  
}
