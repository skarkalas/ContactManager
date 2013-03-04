 package contactmanager;

import java.io.Serializable;

/**
* A contact is a person we are making business with or may do in the future. 
* Contacts have an ID (unique), a name (probably unique, but maybe not), 
* and notes that the user may want to save about them.
*/

public class ContactImpl implements Contact, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int uniqueId;
	private int id;
	private String name;
	private String notes;
	
	static{
		uniqueId = 10000;
	}

	public ContactImpl(String name) {
		this.id = uniqueId++;
		this.name = name;
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
	public String getNotes (){
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
	
}  

