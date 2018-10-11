import org.junit.Assert;
import org.junit.Test;


public class MainClassTest {

  MainClass mc = new MainClass();


  @Test

  public void testGetLocalNumber(){

    Assert.assertEquals("GetLocalNumber doesn't return 14", 14, mc.getLocalNumber());
  }

  @Test

  public void testGetClassNumber() {

    Assert.assertTrue("the number returns less than 45", (mc.getClassNumber() > 45) == true);
  }


   @Test
  public void testGetClassString() {

     Assert.assertTrue("String doesn't contain word hello", (mc.getClassString().contains("Hello") || mc.getClassString().contains("hello")  )== true);
  }



}



