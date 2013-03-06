package contactmanager.tests.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Set;

import contactmanager.Contact;
import contactmanager.FutureMeetingImpl;
import contactmanager.PastMeetingImpl;

public class PastMeetingImplTest {

	private PastMeetingImpl pastMeeting;
	private Calendar date;
	private Set<Contact> contacts;
	private static final String PAST_MEETING="this is a past meeting"; 
	
	@Before 
	public void beforeTest (){
		pastMeeting =  new PastMeetingImpl(new FutureMeetingImpl(date, contacts), PAST_MEETING);
	}
	
	@Test
	public void test() {	
		assertTrue (pastMeeting.getNotes().contains(PAST_MEETING));
	}
	
	@After
	public void afterTest (){
		pastMeeting = null;
		date = null;
		contacts = null;
	}
	
}	
