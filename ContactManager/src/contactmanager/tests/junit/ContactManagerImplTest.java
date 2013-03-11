package contactmanager.tests.junit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import contactmanager.Contact;
import contactmanager.ContactImpl;
import contactmanager.ContactManager;
import contactmanager.ContactManagerImpl;
import contactmanager.Meeting;
import contactmanager.MeetingImpl;

public class ContactManagerImplTest {

	private ContactManager cm;
	private Set<Contact> contactList;
	private SortedSet<Meeting> meetingList;
	private Calendar meetingDate;
	
	@Test
	public void before(){

		contactList = new HashSet<Contact>();
		meetingList = new TreeSet<Meeting>();
		
		contactList.add(new ContactImpl ("nancy", "nancyNotes"));
		contactList.add(new ContactImpl ("ben", "benNotes"));
		contactList.add(new ContactImpl ("daniel", "danielNotes"));
		
		meetingDate = Calendar.getInstance();
		meetingList.add(new MeetingImpl (meetingDate, contactList));
		cm = new ContactManagerImpl();
		
	}
	
	//add non-exist contact to test illegalArgumentException 
	@Test(expected = IllegalArgumentException.class)
	public void testAddFutureMeeting() {
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl("ann","annNotes"));
		int i = cm.addFutureMeeting(contacts, meetingDate);

	}

	@Test
	public void after(){
		
	}
}


