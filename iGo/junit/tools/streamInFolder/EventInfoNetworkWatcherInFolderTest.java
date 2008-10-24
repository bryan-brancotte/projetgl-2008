package tools.streamInFolder;

import static org.junit.Assert.assertTrue;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.exception.ImpossibleStartingException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventInfoNetworkWatcherInFolderTest {

	protected EventInfoNetworkWatcher watcher;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		watcher = new EventInfoNetworkWatcherInFolder();
	}

	/**
	 * Test si la langue par défaut es bien mise si on utilise une langue inconnue
	 * @throws ImpossibleStartingException 
	 */
	@Test
	public void demarrageDuWatcher() throws ImpossibleStartingException {
		watcher.startWatching();
		assertTrue(watcher.getStatus() == EventInfoNetWorkWatcherStatus.STARTED);
	}

	/**
	 * Test si la langue par défaut es bien mise si on utilise une langue inconnue
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
