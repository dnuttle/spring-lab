package net.nuttle.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.nuttle.spring.beans.AddressBean;
import net.nuttle.spring.beans.NameBean;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
   * Get bean created with p-schema property
   */
  @Test
  public void testPropertyPSchema() {
    AddressBean addr = (AddressBean)context.getBean("address2");
    assertEquals(addr.getAddress(), "101 Main");
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

  /**
   * Get bean from non-static factory method
   */
  @Test
  public void testNonStaticFactoryMethod() {
    NameBean name = (NameBean)context.getBean("non-static-name");
    assertEquals(name.getName(), "Non static factory name");
  }
  
  /**
   * Get bean that gets a value from a property file
   */
  @Test
  public void testPropertyFileBean() {
    AddressBean addr = (AddressBean)context.getBean("address-property-file");
    assertEquals(addr.getAddress(), "50 Maple");
  }
  
  /**
   * Get bean with an inner bean
   */
  @Test
  public void testInnerBean() {
    NameBean name = (NameBean)context.getBean("name-with-inner");
    assertEquals(name.getAddress().getAddress(), "inner bean address");
  }
  
  /**
   * Get bean with list
   */
  @Test
  public void testBeanWithList() {
    NameBean name = (NameBean)context.getBean("name-with-list");
    assertEquals(name.getListValues().size(), 3);
    assertEquals(name.getListValues().get(2), "Third value");
  }
  
  /**
   * Get bean with map
   */
  @Test
  public void testBeanWithMap() {
    NameBean name = (NameBean)context.getBean("name-with-map");
    assertEquals(name.getMapValues().get("a"), new Integer(1));
    assertEquals(name.getMapValues().get("b"), new Integer(2));
  }
  
  /**
   * Get bean with bad map...class types it is <String,Integer>,
   * but this bean provides a string for one of the values.
   */
  @Test(expected = BeanCreationException.class)
  public void testBeanWithBadMap() {
    @SuppressWarnings("unused")
    NameBean name = (NameBean)context.getBean("name-with-bad-map");
  }
  
  /**
   * Get bean with set
   */
  @Test
  public void testBeanWithSet() {
    NameBean name = (NameBean)context.getBean("name-with-set");
    assertTrue(name.getSetValues().contains("one"));
    assertTrue(name.getSetValues().contains("two"));
  }

  /**
   * Get bean with properties
   */
  @Test
  public void testBeanWithProperties() {
    NameBean name = (NameBean)context.getBean("name-with-props");
    assertEquals(name.getProperty("name"), "Lebowski");
    assertEquals(name.getProperty("rug"), "Persian");
  }
  
  /**
   * Get bean with null value
   */
  @Test
  public void testBeanWithNull() {
    NameBean name = (NameBean)context.getBean("name-with-null");
    assertNull(name.getName());
  }
  
  /**
   * Bean has a map that is merged with a parent bean's map.
   */
  @Test
  public void testBeanWithMergedMap() {
    NameBean name = (NameBean)context.getBean("name-with-merged-map");
    assertEquals(name.getMapValues().get("one"), new Integer(2));
    assertEquals(name.getMapValues().get("two"), new Integer(1));
    assertEquals(name.getMapValues().get("three"), new Integer(2));
  }
  
  /**
   * Bean with byName autowiring
   * The NameBean must have a setter whose name is the same as the bean to be
   * autowired, e.g., if StringBean bean's name is "stringBean", NameBean must have
   * setStringBean.  The getter method does not have to match  this.
   */
  @Test
  public void testBeanAutowireByName() {
    NameBean name = (NameBean)context.getBean("name-autowire-by-name");
    assertEquals(name.getStringBean().getString(), "ABCD");
  }
  
  /**
   * Bean with byType autowiring
   * byType works only if the autowired bean has a single bean definition
   * Exception occurs because an attempt is made to autowire AddressBean,
   * but there is more than one bean of type AddressBean
   * 
   */
  @Test(expected = UnsatisfiedDependencyException.class)
  public void testBeanAutowireByType() {
    NameBean name = (NameBean)context.getBean("name-autowire-by-type");
  }
  
  /**
   * TODO: Add test that successfully autowires byType
   * TODO: Add test that tries and fails to autowire by constructor
   * TODO: Add test that successfully autowires by constructor
   */
  
}
