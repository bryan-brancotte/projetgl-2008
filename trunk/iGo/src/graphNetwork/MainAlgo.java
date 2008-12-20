package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;

import java.util.ArrayList;
import java.util.Collections;

import algorithm.Dijkstra;

public class MainAlgo {

	public static void main(String[] args) {

		// GraphNetworkBuilder gb = new GraphNetworkBuilder();
		// GraphNetwork g = gb.getInstance();

		KindRoute k = KindRoute.addKind("TGV");
		KindRoute k2 = KindRoute.addKind("RER");

		Route r1 = new Route("1", k);
		Route r2 = new Route("2", k2);

		Station s1 = new Station(1, "Paris");
		s1.addRoute(r1);
		Station s2 = new Station(2, "Marseille");
		s2.addRoute(r1);
		s2.addRoute(r2);
		Station s3 = new Station(3, "Cannes");
		s3.addRoute(r2);
		s3.addRoute(r1);

		try {
			// MarseilleTGV <-> MarseilleRER
			Junction j2 = new Junction(r1, s2, r2, s2, 100, 2, false, false);
			s2.addJunction(j2);

			// CannesTGV <-> CannesRER
			Junction j5 = new Junction(r1, s3, r2, s3, 100, 2, false, false);
			s3.addJunction(j5);

			// ParisTGV <-> MarseilleTGV
			Junction j = new Junction(r1, s1, r1, s2, 100, 5, false, false);
			s1.addJunction(j);
			s2.addJunction(j);

			// MarseilleRER <-> CannesRER
			Junction j3 = new Junction(r2, s2, r2, s3, 100, 5, false, false);
			s2.addJunction(j3);
			s3.addJunction(j3);

			// ParisTGV <-> CannesTGV
			Junction j4 = new Junction(r1, s1, r1, s3, 100, 5, false, false);
			s1.addJunction(j4);
			s3.addJunction(j4);

		} catch (StationNotOnRoadException e) {
			e.printStackTrace();
		} catch (ImpossibleValueException e) {
			e.printStackTrace();
		}

		ArrayList<Junction> junctions = null;

		long startTime = System.currentTimeMillis();

		//GraphAlgo graph = GraphAlgo.getInstance();
		//graph.refreshGraph(s1);

		Dijkstra algo = new Dijkstra();
		algo.setCriterious1("time");
		algo.setCriterious2("cost");

		//junctions = algo.findPath(graph, s1, s3);

		long stopTime = System.currentTimeMillis();

		System.out.println(s1.getName() + " => " + s3.getName());
		// System.out.println("graph : "+graph.getListClone().size()+" chemins");
		System.out.println("-------------------------------");

		Collections.reverse(junctions);
		System.out.print("DEPART => ");
		for (int i = 0; i < junctions.size(); i++) {
			System.out.print(junctions.get(i));
		}
		System.out.println("ARRIVEE");

		long runTime = stopTime - startTime;
		System.out.println("Run time: " + runTime + " ms");
		System.out.println("-------------------------------");

	}
}
