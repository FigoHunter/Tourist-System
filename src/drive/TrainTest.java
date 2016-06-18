package drive;

import static org.junit.Assert.*;

import org.junit.Test;

import basic.Vector2;

public class TrainTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	@Test
	public void testLocationSetterGetter()
	{
		Train train = new Train("a",1);
		Vector2 vector = new Vector2(1,2);
		train.setLocation(vector);
		assertEquals(vector,train.getLocation());
	}
	@Test
	public void testNumSetterGetter()
	{
		Train train = new Train("a",1);
		train.setTrainNum("b");
		assertEquals("b",train.getTrainNum());
	}
}
