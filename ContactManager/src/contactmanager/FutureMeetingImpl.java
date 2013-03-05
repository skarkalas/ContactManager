package contactmanager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A meeting to be held in the future
	 */
	
	public FutureMeetingImpl(Calendar date, Set<Contact> contacts) {
		super(date, contacts);
	}

	
}