package ihm.classesExemples;

import graphNetwork.Route;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

import java.util.Iterator;
import java.util.LinkedList;

public class TravelForTravelPanelExemple implements TravelForTravelPanel, TravelForDisplayPanel {

	protected boolean fav = (((this.hashCode() >> 1) << 1) == this.hashCode());
	protected String name = "Nom (" + this.hashCode() + ")";
	protected LinkedList<SectionOfTravel> travel;

	public TravelForTravelPanelExemple() {
		super();
		travel = new LinkedList<SectionOfTravel>();
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "FT8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public String getNameChangement() {
				return "Roosvelt";
			}

			@Override
			public String getNameRoute() {
				return "1";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 5;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public int getEnddingChangementTime() {
				return 12;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Southern Stadium";
			}

			@Override
			public String getNameRoute() {
				return "8";
			}

			@Override
			public int getTimeSection() {
				return 32;
			}
		});
		travel.add(new SectionOfTravel() {

			@Override
			public float getEnddingChangementCost() {
				return 0;
			}

			@Override
			public int getStationInSection() {
				return 4;
			}

			@Override
			public int getEnddingChangementTime() {
				return 2;
			}

			@Override
			public Route getRoute() {
				return null;
			}

			@Override
			public String getNameChangement() {
				return "Market - Frankford";
			}

			@Override
			public String getNameRoute() {
				return "4";
			}

			@Override
			public int getTimeSection() {
				return 11;
			}
		});
	}

	@Override
	public String getDestination() {
		return "Market - Frankford";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOrigine() {
		return "Stadium";
	}

	@Override
	public float getTotalCost() {
		return 42.5F;
	}

	@Override
	public int getTotalTime() {
		int ret = 0;
		for (SectionOfTravel section : travel) {
			ret += section.getTimeSection() + section.getEnddingChangementTime();
		}
		return ret;
	}

	@Override
	public int getRemainingTime() {
		return 46;
	}

	@Override
	public boolean isFavorite() {
		return fav;
	}

	@Override
	public void setFavorite(boolean isFav) {
		fav = isFav;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public boolean update() {
		return false;
	}

	@Override
	public String getNextStop() {
		return "Roosvelt";
	}

	@Override
	public Iterator<SectionOfTravel> getTravel() {
		return travel.iterator();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValideFromWhereIAm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}
}
