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

import streamInFolder.event.EventInfoStationOnARoute;;

public class EventInfoStationOnARouteTest {

	EventInfoStationOnARoute event;
	KindEventInfoNetwork kind;
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		event = new EventInfoStationOnARoute(1,"id_route","message",2,kind);
		
	}
}
