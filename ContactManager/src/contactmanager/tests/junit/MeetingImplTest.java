package contactmanager.tests.junit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import contactmanager.Contact;
import contactmanager.ContactImpl;
import contactmanager.MeetingImpl;

public class MeetingImplTest {

	private ContactImpl newContact;
	private MeetingImpl meeting;
	private Calendar cal;
	private int meetingId;
	private Set<Contact> contactSet;
	

	@Before
	public void beforeTest() {
		newContact = new ContactImpl("nancy", "notes");
		meeting = new MeetingImpl (cal, contactSet);
		meetingId = meeting.getId();
		cal = meeting.getDate();
		contactSet = new HashSet<Contact>();
		contactSet.add(newContact);
	}

	@Test
	public void test() {
		
		assertEquals (meeting.getId(), meetingId);
		assertTrue("true", newContact instanceof Contact);
		assertEquals (meeting.getDate(), cal);

		// test if contacts was added correctly
		for (Contact c : contactSet) {
			assertEquals("nancy", c.getName());
		}
	}

	@After
	public void afterTest() {
		meeting = null;
		newContact = null;
		cal=null;
		contactSet=null;
	}
}
