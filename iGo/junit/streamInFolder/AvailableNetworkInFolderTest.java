package streamInFolder;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import streamInFolder.graphReaderFolder.AvailableNetworkInFolder;

public class AvailableNetworkInFolderTest {

	protected AvailableNetworkInFolder aNetwork;
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		aNetwork = new AvailableNetworkInFolder("my_name","my_path");
		//aNetwork.setDescription("DESCRIPTION");
		
	}
	
	/**
	 * Test du getter du name
	 */
	@Test
	public void nameGetter() {
		assertTrue(aNetwork.getName().compareTo("my_name")==0);
	}
	
	/**
	 * Test du getter du path
	 */
	@Test
	public void pathGetter() {
		assertTrue(aNetwork.getPath().compareTo("my_path")==0);
	}
	
	/**
	 * Test du getter du fichier
	 */
	@Test
	public void fileGetter() {
		assertTrue(aNetwork.getFichier().getPath().compareTo("my_path")==0);
	}
}
