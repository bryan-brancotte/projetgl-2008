package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import iGoMaster.Algo;
import algorithm.GraphAlgo;

public class MainAlgo {

	public static void main(String[] args) {
		
		//GraphNetworkBuilder gb = new GraphNetworkBuilder();
		//GraphNetwork g = gb.getInstance();
		
		KindRoute.addKind("TGV");
		KindRoute k = KindRoute.getKindFromString("TGV");

		Route r1 = new Route("1",k);
		System.out.println(r1);
		Station s1 = new Station(1,"Paris");
		s1.addRoute(r1);
		Station s2 = new Station(1,"Marseille");
		s2.addRoute(r1);
		
		try {
			Junction j = new Junction(r1,s1,r1,s2,100,5,false,false);
			s1.addJunction(j);
		} catch (StationNotOnRoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImpossibleValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphAlgo graph = GraphAlgo.getInstance();
		
		graph.refreshGraph(s1);
		
	}
}
