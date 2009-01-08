package streamInFolder;

import static org.junit.Assert.assertTrue;

import java.util.MissingResourceException;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.KindEventInfoNetwork;
import iGoMaster.exception.ImpossibleStartingException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import streamInFolder.graphReaderFolder.AvailableNetworkInFolder;

public class AvailableNetworkInFolderTest {

	protected AvailableNetworkInFolder aNetwork;
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		aNetwork = new AvailableNetworkInFolder("my_name","my_path");
		aNetwork.setDescription("DESCRIPTION");
		
	}
	
	/**
	 * Test du getter du name
	 */
	@Test
	public void nameGetter() {
		assertTrue(aNetwork.getName().compareTo("my_name")==0);
		aNetwork.setName("my_name2");
		assertTrue(aNetwork.getName().compareTo("my_name2")==0);
	}
	
	/**
	 * Test du getter de la description
	 */
	@Test
	public void descriptionGetter() {
		assertTrue(aNetwork.getDescription().compareTo("DESCRIPTION")==0);
		aNetwork.setDescription("DESCRITPION2");
		assertTrue(aNetwork.getDescription().compareTo("DESCRITPION2")==0);
	}
	
	/**
	 * Test du getter du path
	 */
	@Test
	public void pathGetter() {
		assertTrue(aNetwork.getPath().compareTo("my_path")==0);
		aNetwork.setPath("my_path2");
		assertTrue(aNetwork.getPath().compareTo("my_path2")==0);
	}
	
	/**
	 * Test du getter du fichier
	 */
	@Test
	public void fileGetter() {
		assertTrue(aNetwork.getFichier().getPath().compareTo("my_path")==0);
		File fichier = new File("encoeUnAUtrePath");
		aNetwork.setFichier(fichier);
		assertTrue(aNetwork.getFichier().compareTo(fichier)==0);
	}
}
