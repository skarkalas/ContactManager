package contactmanager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * A class to represent meetings. Meetings have unique IDs, scheduled date and a
 * list of participating contacts
 */

public class MeetingImpl implements Meeting, Serializable, Comparable<Meeting>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int uniqueId;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;

	static{
		uniqueId=10000;
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
	
	public String getMeetingDate(Calendar cal){
		Date date = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
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
//		if ((this.getDate()).before(m.getDate())){
//		return -1;
//		} else {
//			if ((this.getDate()).equals(m.getDate())){
//				return 0;
//			} else {
//				return 1;
//			}
//		}
		return this.getDate().compareTo(m.getDate());
	}
}








