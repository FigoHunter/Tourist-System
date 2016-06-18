package drive;

import static org.junit.Assert.*;

import org.junit.Test;

public class DriverTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	@Test
	public void testgetNum()
	{
		Driver driver = new Driver("1","Ezio");
		assertEquals("1",driver.getNum());
	}
	@Test
	public void testgetName()
	{
		Driver driver = new Driver("1","Ezio");
		assertEquals("Ezio",driver.getName());
	}
}
