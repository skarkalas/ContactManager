package contactmanager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * A class to represent meetings. Meetings have unique IDs, scheduled date and a
 * list of participating contacts
 */

public class MeetingImpl implements Meeting, Serializable, Comparable<Meeting>  {
	private static final long serialVersionUID = 3218056569802584430L;
	private static final int STARTING_ID = 10000;
	private static int uniqueId;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;

	static{
		uniqueId=STARTING_ID;
	}
	
	public MeetingImpl(Calendar date, Set<Contact> contacts) {
		this (uniqueId++, date,contacts);
	}
	
	protected MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.date = date;
		this.contacts = contacts;
		this.id = id;
	}
	/**
	 * Returns the id of the meeting
	 * 
	 * @return the id of the meeting
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Return the date of the meeting.
	 * 
	 * @return the date of the meeting.
	 */
	@Override
	public Calendar getDate() {
		date = Calendar.getInstance();
		return date;
	}

	/**
	 * Return the details of people that attended the meeting. The list contains
	 * a minimum of one contact (if there were just two people: the user and the
	 * contact) and may contain an arbitrary number of them.
	 * 
	 * @return the details of people that attended the meeting.
	 */
	@Override
	public Set<Contact> getContacts() {	
		return contacts;
	}

	@Override
	public int compareTo(Meeting m) {
		return this.getDate().compareTo(m.getDate());
	}
	@Override
	public boolean equals(Object obj) {
		boolean result;
		if (obj instanceof MeetingImpl) {
			MeetingImpl other = (MeetingImpl) obj; 
			result = this.getId() == other.getId();
		}
		else {
			result  = false;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}








