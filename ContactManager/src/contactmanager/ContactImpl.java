 package contactmanager;

import java.io.Serializable;

/**
* A contact is a person we are making business with or may do in the future. 
* Contacts have an ID (unique), a name (probably unique, but maybe not), 
* and notes that the user may want to save about them.
*/

public class ContactImpl implements Contact, Serializable {
	private static final int STARTING_ID = 10000;
	private static final long serialVersionUID = -2539614867749641482L;
	private static int uniqueId;
	private int id;
	private String name;
	private String notes;
	
	static{
		uniqueId = STARTING_ID;
	}
	
	public ContactImpl(String name, String notes) {
		this.id = uniqueId++;
		this.name = name;
		this.notes = notes;
	}
	
	
	/**
	* Returns the ID of the contact. 
	*
	* @return the ID of the contact. 
	*/
	@Override
	public int getId() {
		return id;
	}
	

	/**
	* Returns the name of the contact. 
	*
	* @return the name of the contact. 
	*/
	@Override
	public String getName() {
		return name;
	}
	
	/**
	* Returns our notes about the contact, if any. 
	*
	* If we have not written anything about the contact, the empty string is returned. 
	*
	* @return a string with notes about the contact, maybe empty. 
	*/
	@Override
	public String getNotes () {
		return notes;
	}
	
	/**
	* Add notes about the contact. 
	*
	* @param note the notes to be added 
	*/
	@Override
	public void addNotes(String note) {
		notes+=note;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result;
		if (obj instanceof ContactImpl) {
			ContactImpl other = (ContactImpl) obj; 
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

