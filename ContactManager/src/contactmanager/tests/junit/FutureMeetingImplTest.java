package contactmanager.tests.junit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import contactmanager.Contact;
import contactmanager.FutureMeetingImpl;
import contactmanager.MeetingImpl;

public class FutureMeetingImplTest {

	private MeetingImpl futureMeeting;
	private Calendar date;
	private Set<Contact> contacts;
	
	@Before 
	public void beforeTest(){	
		futureMeeting = new FutureMeetingImpl(date, contacts);
	}	
	
	@Test
	public void test() {
		assertTrue ("futureMeeting is from FutureMeeting", futureMeeting instanceof FutureMeetingImpl);
		assertTrue ("futureMeeting is from Meeting", futureMeeting instanceof MeetingImpl);
	}
	

	@After
	public void afterTest(){
		futureMeeting = null;
		date = null;
		contacts = null;
	}

}
