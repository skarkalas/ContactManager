package contactmanager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
* A meeting that was held in the past. 
*
* It includes your notes about what happened and what was agreed. 
*/

public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable  {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String note;

		/**
		* Returns the notes from the meeting. 
		* If there are no notes, the empty string is returned. 
		*return "Meeting ID: "+getId()+" " +"\nMeeting Date: "+getMeetingDate(cal)+"\nMeeting Notes: "+notes.toString();
		* @return the notes from the meeting. 
		*/

		public PastMeetingImpl(Calendar date, Set<Contact> contacts) {
			super(date, contacts);
		}
		
		public PastMeetingImpl(FutureMeeting m, String note) {
			super (m.getId(),m.getDate(),m.getContacts());
			this.note = note;
		}

		@Override
		public String getNotes() {			
			return note;
		}

		public void addNotes(String note) {
			this.note+=note;
		}
	}



