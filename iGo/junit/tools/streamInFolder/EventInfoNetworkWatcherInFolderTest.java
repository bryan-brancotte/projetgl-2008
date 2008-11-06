package tools.streamInFolder;

import static org.junit.Assert.assertTrue;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.exception.ImpossibleStartingException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import streamInFolder.EventInfoNetworkWatcherInFolder;

public class EventInfoNetworkWatcherInFolderTest {

	protected EventInfoNetworkWatcher watcher;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		watcher = new EventInfoNetworkWatcherInFolder("PATH");
	}

	/**
	 * Test de démarrage effectif du watcher
	 * 
	 * @throws ImpossibleStartingException si le démarage ne marche pas
	 */
	@Test
	public void demarrageDuWatcher() throws ImpossibleStartingException {
		watcher.startWatching();
		assertTrue(watcher.getStatus() == EventInfoNetWorkWatcherStatus.STARTED);
	}

	/**
	 * Test d'arrêt effectif du watcher
	 */
	@Test
	public void arretDuWatcher() {
		watcher.stopWatching();
		assertTrue(watcher.getStatus() == EventInfoNetWorkWatcherStatus.STOPPED);
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(EventInfoNetworkWatcherInFolderTest.class);
	}

}
