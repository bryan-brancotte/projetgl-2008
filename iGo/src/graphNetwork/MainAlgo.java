package graphNetwork;

import java.util.ArrayList;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import iGoMaster.Algo;
import algorithm.Dijkstra;
import algorithm.GraphAlgo;

public class MainAlgo {

	public static void main(String[] args) {
		
		//GraphNetworkBuilder gb = new GraphNetworkBuilder();
		//GraphNetwork g = gb.getInstance();
		
		KindRoute.addKind("TGV");
		KindRoute k = KindRoute.getKindFromString("TGV");

		Route r1 = new Route("1",k);
		
		Station s1 = new Station(1,"Paris");
		s1.addRoute(r1);
		Station s2 = new Station(1,"Marseille");
		s2.addRoute(r1);
		
		try {
			Junction j = new Junction(r1,s1,r1,s2,100,5,false,false);
			s1.addJunction(j);
			s2.addJunction(j);
			
		} catch (StationNotOnRoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImpossibleValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphAlgo graph = GraphAlgo.getInstance();
		
		graph.refreshGraph(s1);
		
		Dijkstra algo = new Dijkstra();
		algo.setCriterious1("time");
		algo.setCriterious2("cost");
		System.out.println(s1.getName()+" => "+s2.getName());
		System.out.println("graph :");
		for (int i=0;i<graph.getListClone().size();i++) {
			System.out.print(" - "+graph.getListClone().get(i));
		}
		System.out.println();
		System.out.println("-------------------------------");
		
		ArrayList<Junction> j = algo.findPath(graph, s1, s2);
		System.out.println(j.size());
		
	}
}
