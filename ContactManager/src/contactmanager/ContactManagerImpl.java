package contactmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ContactManagerImpl implements ContactManager, Serializable {

	private static final long serialVersionUID = 507553869890625432L;
	private Set<Contact> contactList;
	private SortedSet<Meeting> meetingList;

	/**
	 * strings to show messages
	 */
	private static final String MEETING_IN_PAST = "New meeting added is in the past.";
	private static final String MEETING_IN_FUTURE = "Meeting requested is in the future.";
	private static final String NO_CONTACT_COULD_BE_FOUND = "No contact could be found.";
	private static final String NO_MEETING_COULD_BE_FOUND = "No meeting could be found.";
	private static final String WRONG_ID = "Wrong ID";
	private static final String NULL_VALUE = "Null value";
	private static final String FILE_IN = "contacts.ser";

	/**
	 * a constructor to read in previous meetings, contacts
	 */
	public ContactManagerImpl() {
		readInData();
	}

	/**
	 * write in data
	 */
	@SuppressWarnings("unchecked")
	public void readInData() {
		ObjectInputStream objectIn = null;
		try {
			if (new File(FILE_IN).exists()) {
				objectIn = new ObjectInputStream(new FileInputStream(FILE_IN));
				contactList = (HashSet<Contact>) objectIn.readObject();
				meetingList = (TreeSet<Meeting>) objectIn.readObject();
			} else {
				contactList = new HashSet<Contact>();
				meetingList = new TreeSet<Meeting>();
			}
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();}
		catch (Exception e){
			e.getMessage();
			
		} finally {
			try {
				objectIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add a new meeting to be held in the future.
	 * 
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past of if any
	 * contact is unknown / non-existent
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar meetingDate) {
		Calendar currentDate = Calendar.getInstance();
		int meetingId = 0;
		
		//jump checking exceptions if started for the first time
		if (contactList.size()>0){
			// check if contact is unknown
			for (Contact contact : contacts) {
				if (!contactList.contains(contact)) {
					throw new IllegalArgumentException(NO_CONTACT_COULD_BE_FOUND);
				}
			}
			// check if meeting is set for a time in the past
			if (meetingDate.before(currentDate)) {
				throw new IllegalArgumentException(MEETING_IN_PAST);
			}
		} else {
			FutureMeeting newMeeting = new FutureMeetingImpl(meetingDate,contacts);
			meetingList.add(newMeeting);
			meetingId = newMeeting.getId();
		}
		return meetingId;
	}
				
	/**
	 * Returns the PAST meeting with the requested ID, or null if it there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	 */

	@Override
	public PastMeeting getPastMeeting(int id) {
		Calendar currentDate = Calendar.getInstance();
		for (Meeting meeting : meetingList) {
			if (meeting.getDate().after(currentDate)) {
				throw new IllegalArgumentException(MEETING_IN_FUTURE);
			} else if (meeting.getId() == id) {
				return (PastMeeting) meeting;
			}
		}
		return null;
	}

	/**
	 * Returns the FUTURE meeting with the requested ID, or null if there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		Calendar currentDate = Calendar.getInstance();
		for (Meeting meeting : meetingList) {
			if (meeting.getId() == id) {
				if (meeting.getDate().before(currentDate)) {
					throw new IllegalArgumentException(MEETING_IN_PAST);
				} else {
					return (FutureMeeting) meeting;
				}
			}
		}
		return null;
	}


	/**
	 * Returns the meeting with the requested ID, or null if there is none.
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 */
	@Override
	public Meeting getMeeting(int id) {
		for (Meeting meeting : meetingList) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;
	}

	/**
	 * Returns the list of future meetings scheduled with this contact. If there
	 * are none, the returned list will be empty. Otherwise, the list will be
	 * chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact
	 * @throws IllegalArgumentException if the contact does not exist
	 */

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		Calendar currentDate = Calendar.getInstance();
		List<Meeting> futureMeetingList = new ArrayList<Meeting>();

		if (!contactList.contains(contact)) {
			throw new IllegalArgumentException(NO_CONTACT_COULD_BE_FOUND);
		} else {
			SortedSet<Meeting> meetingForOneContact = new TreeSet<Meeting>();
			meetingForOneContact = getAllMeetingForContact(contact);
					
			for (Meeting meeting : meetingForOneContact) {
				if (meeting.getDate().after(currentDate)) {
					futureMeetingList.add(meeting);
				}
			}
		}
		return futureMeetingList;
	}

	/**
	 * Returns the list of meetings scheduled with this contact.
	 * 
	 * @param contact one of the user’s contacts
	 * @return the list of meeting(s) scheduled with this contact
	 */
	public SortedSet<Meeting> getAllMeetingForContact(Contact contact) {
		SortedSet<Meeting> allMeetingForContact = new TreeSet<Meeting>();
			for (Meeting meeting : meetingList) {
				if (meeting.getContacts().contains(contact)) {
					allMeetingForContact.add(meeting);
				}
			}
		return allMeetingForContact;
	}

	/**
	 * Returns the list of meetings that are scheduled for, or that took place
	 * on, the specified date. if there are none, the returned list will be
	 * empty. Otherwise, the list will be chronologically sorted and will not
	 * contain any duplicates.
	 * 
	 * @param date the date
	 * @return the list of meetings
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> futureMeetingList = new ArrayList<Meeting>();
		for (Meeting meeting : meetingList) {
			if (((Meeting) meeting.getContacts()).getDate().equals(date)) {
				futureMeetingList.add(meeting);
			}
		}
		return futureMeetingList;
	}

	/**
	 * Returns the list of past meetings in which this contact has participated.
	 * If there are none, the returned list will be empty. Otherwise, the list
	 * will be chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty).
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		Calendar currentDate = Calendar.getInstance();
		List<PastMeeting> pastMeetingList = new ArrayList<PastMeeting>();
		if (!contactList.contains(contact)) {
			throw new IllegalArgumentException(NO_CONTACT_COULD_BE_FOUND);
		} else {
			SortedSet<Meeting> meetingForOneContact = new TreeSet<Meeting>();
			meetingForOneContact = getAllMeetingForContact(contact);
			for (Meeting meeting : meetingForOneContact) {
				if (meeting.getDate().before(currentDate)) {
					pastMeetingList.add((PastMeeting) meeting);
				}
			}
		}
		return pastMeetingList;
	}

	/**
	 * Create a new record for a meeting that took place in the past.
	 * 
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the list of contacts is empty, 
	 * or any of the contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {

		for (Contact cont : contacts) {
			if (!contactList.contains(cont)) {
				throw new IllegalArgumentException(NULL_VALUE);
			}

			if (contacts == null) {
				throw new IllegalArgumentException(NULL_VALUE);
			} else if (contacts == null || date == null || text == null) {
				throw new NullPointerException(NULL_VALUE);
			} else {
				meetingList.add(new PastMeetingImpl(new FutureMeetingImpl(date,
						contacts), text));
			}
		}

	}

	/**
	 * Add notes to a meeting. This method is used when a future meeting takes
	 * place, and is then converted to a past meeting (with notes). It can be
	 * also used to add notes to a past meeting at a later date.
	 * 
	 * @param id the ID of the meeting
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null
	 */
	@Override
	public void addMeetingNotes(int id, String text) {
		Calendar currentDate = Calendar.getInstance();
		Set<Integer> meetingId = new HashSet<Integer>();
		//make a list of meeting ids
		for (Meeting meeting: meetingList){
			meetingId.add(meeting.getId());
		}
		
		//check if meeting exist with required ID, text is null, date is in the future
		if (!meetingId.contains (id)){
			throw new IllegalArgumentException(NO_MEETING_COULD_BE_FOUND);
		} else if (text == null){
			throw new NullPointerException(NULL_VALUE);
		} else {
			for (Meeting meeting : meetingList) {
				if (meeting.getDate().after(currentDate)){
					throw new IllegalArgumentException(MEETING_IN_FUTURE);
				} else {
					((PastMeetingImpl) meeting).addNotes(text);
				}
			}
		}
	}

	/**
	 * Create a new contact with the specified name and notes.
	 * 
	 * @param name the name of the contact.
	 * @param notes notes to be added about the contact.
	 * @throws NullPointerException if the name or the notes are null
	 */
	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException(NULL_VALUE);
		} else {
			contactList.add(new ContactImpl(name, notes));
		}
	}

	/**
	 * Returns a list containing the contacts that correspond to the IDs
	 * 
	 * @param ids an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs.
	 * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	 */

	@Override
	public Set<Contact> getContacts(int... ids) {

		SortedSet<Contact> contactIdSet = new TreeSet<Contact>();

		for (Integer id : ids) {
			for (Contact c : contactList) {
				if (id == c.getId()) {
					contactIdSet.add(c);
				} else {
					throw new IllegalArgumentException(WRONG_ID);
				}
			}
		}
		return contactIdSet;
	}

	/**
	 * Returns a list with the contacts whose name contains that string.
	 * 
	 * @param name the string to search for
	 * @return a list with the contacts whose name contains that string.
	 * @throws NullPointerException if the parameter is null
	 */

	@Override
	public Set<Contact> getContacts(String name) {

		SortedSet<Contact> contactNameSet = new TreeSet<Contact>();

		if (name == null) {
			throw new NullPointerException(NULL_VALUE);
		} else {
			for (Contact c : contactList) {
				if (c.getName().equalsIgnoreCase(name)) {
					contactNameSet.add(c);
				}
			}
		}
		return contactNameSet;
	}

	/**
	 * Save all data to disk.
	 * 
	 * This method must be executed when the program is closed and when/if the
	 * user requests it.
	 */
	@Override
	public void flush() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(FILE_IN));
			out.writeObject(contactList);
			out.writeObject(meetingList);

		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
