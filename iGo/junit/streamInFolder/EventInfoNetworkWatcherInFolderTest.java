//package streamInFolder;
//
//import static org.junit.Assert.assertTrue;
//import iGoMaster.EventInfoNetWorkWatcherStatus;
//import iGoMaster.EventInfoNetworkWatcher;
//import iGoMaster.exception.ImpossibleStartingException;
//import junit.framework.JUnit4TestAdapter;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import streamInFolder.event.EventInfoNetworkWatcherInFolderSAX;
//
//public class EventInfoNetworkWatcherInFolderTest {
//
//	protected EventInfoNetworkWatcher watcher;
//
//	@After
//	public void epilogueDateTest() {
//	}
//
//	@Before
//	public void prologueDateTest() throws ImpossibleStartingException{
//		watcher = new EventInfoNetworkWatcherInFolderSAX("PATH");
//		watcher.startWatching();
//	}
//
//	/**
//	 * Test de démarrage effectif du watcher
//	 * 
//	 * @throws ImpossibleStartingException
//	 *             si le démarage ne marche pas
//	 */
//	@Test
//	public void demarrageDuWatcher() {
//		assertTrue(watcher.getStatus() == EventInfoNetWorkWatcherStatus.STARTED);
//	}
//
//	/**
//	 * Test d'arrêt effectif du watcher
//	 */
//	@Test
//	public void arretDuWatcher() {
//		watcher.stopWatching();
//		assertTrue((watcher.getStatus() == EventInfoNetWorkWatcherStatus.STOPPED)
//				|| (watcher.getStatus() == EventInfoNetWorkWatcherStatus.NEW_UPDATE_STOPPED));
//	}
//
//	public static junit.framework.Test suite() {
//		return new JUnit4TestAdapter(EventInfoNetworkWatcherInFolderTest.class);
//	}
//
//}
