package contactmanager.tests.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import contactmanager.ContactImpl;

public class ContactImplTest {
	private ContactImpl nancy;
	private String nancyName;
	private String nancyNotes;
	private int nancyId;
	
	@Before
	public void beforeTest (){
		nancyName = "Nancy";	
		nancyNotes = "This is about Nancy";
		nancy = new ContactImpl(nancyName, nancyNotes);
		nancyId = nancy.getId();

	}
	
	@Test
	public void test() {
		assertEquals (nancyId, nancy.getId());
		assertEquals ("Nancy", nancy.getName());
		assertEquals (nancyNotes, nancy.getNotes());
	}

	@After
	public void afterTest(){
		nancy = null;
		nancyNotes = null;
		nancyName = null;
	}
}
