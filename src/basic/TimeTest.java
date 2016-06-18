package basic;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.TimeSetException;

public class TimeTest {
	@Test
	public void test() {
	}
	@Test
	public void testtoString()
	{
		Time time;
		try {
			time= new Time(5,20);
			assertEquals("05:20",time.toString());
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testcompareTo()
	{
		Time time1, time2,time3;
		try {
			time1 = new Time(5,20);
			time2 = new Time(5,21);
			time3 = new Time(5,19);
			assertEquals(1,time1.compareTo(time3));
			assertEquals(0,time1.compareTo(time1));
			assertEquals(-1,time1.compareTo(time2));
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testtoTime()
	{
		Time time;
		try {
			time= Time.toTime("06:29");
			assertEquals("06:29",time.toString());
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testtimeForward()
	{
		Time time;
		try {
			time= Time.toTime("06:29");
			time.timeForward(32);
			assertEquals("07:01",time.toString());
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testtimeBackward(){
		Time time;
		try {
			time= new Time(7,20);
			time.timeBackward(30);
			assertEquals("06:50",time.toString());
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testduration()
	{
		Time time1,time2;
		try {
			time1= new Time(6,20);
			time2= new Time(6,50);
			assertEquals(30,Time.duration(time1,time2));
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
