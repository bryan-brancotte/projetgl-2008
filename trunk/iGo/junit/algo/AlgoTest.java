package algo;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Junction;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo;
import iGoMaster.Algo.CriteriousForLowerPath;
import iGoMaster.exception.NoRouteForStationException;
import iGoMaster.exception.ServiceNotAccessibleException;
import iGoMaster.exception.StationNotAccessibleException;
import iGoMaster.exception.VoidPathException;

import java.util.Iterator;
import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;

public class AlgoTest {

	protected GraphNetworkBuilder gnb;
	protected GraphNetwork sncf;
	protected PathInGraphResultBuilder pig;
	protected Algo bob;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AlgoTest.class);
	}

	@Before
	public void constructionDUnReseauSansProbleme() {
		gnb = new GraphNetworkBuilder();
		GraphNetwork g = gnb.getCurrentGraphNetwork();
		sncf = gnb.getCurrentGraphNetwork();
		gnb.reset();
		try {
			Route m1 = gnb.addRoute("m1", "METRO");
			Route m2 = gnb.addRoute("m2", "METRO");
			Route m3 = gnb.addRoute("m3", "METRO");
			Route m4 = gnb.addRoute("m4", "METRO");
			Route m5 = gnb.addRoute("m5", "METRO");
			Route m6 = gnb.addRoute("m6", "METRO");
			Route m7 = gnb.addRoute("m7", "METRO");
			Route m8 = gnb.addRoute("m8", "METRO");
			Route m9 = gnb.addRoute("m9", "METRO");
			Route m10 = gnb.addRoute("m10", "METRO");
			Route m11 = gnb.addRoute("m11", "METRO");
			Route m12 = gnb.addRoute("m12", "METRO");
			Route m13 = gnb.addRoute("m13", "METRO");
			Route m14 = gnb.addRoute("m14", "METRO");

			gnb.addStationToRoute(m1, gnb.addStation(1, "Paris"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(2, "Grande Arche de la Défense"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(3, "Esplanade de la Défense"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(4, "Pont de Neuilly"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(5, "Les Sablons"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(6, "Porte Maillot"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(7, "Argentine"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(8, "Charles de Gaulle, Étoile"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(9, "George V"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(10, "Franklin D. Roosevelt"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(11, "Champs Élysées, Clémenceau"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(12, "Concorde"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(13, "Tuileries"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(14, "Palais Royal, Musée du Louvre"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(15, "Louvre, Rivoli"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(16, "Châtelet"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(17, "Hôtel de Ville"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(18, "Saint-Paul, Le Marais"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(19, "Bastille"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(20, "Gare de Lyon"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(21, "Reuilly Diderot"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(22, "Nation"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(23, "Porte de Vincennes"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(24, "Saint-Mandé, Tourelle"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(25, "Bérault"), 1);
			gnb.addStationToRoute(m1, gnb.addStation(26, "Château de Vincennes"), 1);

			gnb.addStationToRoute(m2, gnb.addStation(27, "Porte Dauphine"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(28, "Victor Hugo"), 1);
			gnb.addStationToRoute(m2, g.getStation(8), 1);
			gnb.linkStationBidirectional(m1, g.getStation(8), m2, g.getStation(8), 0, 1, false);
			gnb.addStationToRoute(m2, gnb.addStation(30, "Ternes"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(31, "Courcelles"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(32, "Monceau"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(33, "Villiers"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(34, "Rome"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(35, "Place de Clichy"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(36, "Blanche"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(37, "Pigalle"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(38, "Anvers"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(39, "Barbès Rochechouart"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(40, "La Chapelle"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(41, "Stalingrad"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(42, "Jaurès"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(43, "Colonel Fabien"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(44, "Belleville"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(45, "Couronnes"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(46, "Ménilmontant"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(47, "Père Lachaise"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(48, "Philippe-Auguste"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(49, "Alexandre Dumas"), 1);
			gnb.addStationToRoute(m2, gnb.addStation(50, "Avron"), 1);
			gnb.addStationToRoute(m2, g.getStation(22), 1);
			gnb.linkStationBidirectional(m1, g.getStation(22), m2, g.getStation(22), 0, 1, false);

			gnb.addStationToRoute(m3, gnb.addStation(52, "Pont de Levallois, Bécon"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(53, "Anatole France"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(54, "Louise Michel"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(55, "Porte de Champerret"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(56, "Péreire"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(57, "Wagram"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(58, "Malesherbes"), 1);
			gnb.addStationToRoute(m3, g.getStation(32), 1);
			gnb.linkStationBidirectional(m2, g.getStation(32), m3, g.getStation(32), 0, 1, false);
			gnb.addStationToRoute(m3, gnb.addStation(60, "Europe"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(61, "Saint-Lazare"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(62, "Havre Caumartin"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(63, "Opéra"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(64, "Quatre Septembre"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(65, "Bourse"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(66, "Sentier"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(67, "Réaumur Sébastopol"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(68, "Arts et Métiers"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(69, "Temple"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(70, "République"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(71, "Parmentier"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(72, "Rue Saint-Maur"), 1);
			gnb.addStationToRoute(m3, g.getStation(47), 1);
			gnb.linkStationBidirectional(m2, g.getStation(47), m3, g.getStation(47), 0, 1, false);
			gnb.addStationToRoute(m3, gnb.addStation(74, "Gambetta"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(75, "Porte de Bagnolet"), 1);
			gnb.addStationToRoute(m3, gnb.addStation(76, "Galliéni"), 1);

			gnb.addStationToRoute(m4, gnb.addStation(77, "Porte de Clignancourt"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(78, "Simplon"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(79, "Marcadet Poissonniers"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(80, "Château Rouge"), 1);
			gnb.addStationToRoute(m4, g.getStation(39), 1);
			gnb.linkStationBidirectional(m2, g.getStation(39), m4, g.getStation(39), 0, 1, false);
			gnb.addStationToRoute(m4, gnb.addStation(82, "Gare du Nord"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(83, "Gare de l'Est"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(84, "Château d'Eau"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(85, "Strasbourg Saint-Denis"), 1);
			gnb.addStationToRoute(m4, g.getStation(67), 1);
			gnb.linkStationBidirectional(m3, g.getStation(67), m4, g.getStation(67), 0, 1, false);
			gnb.addStationToRoute(m4, gnb.addStation(87, "Étienne Marcel"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(88, "Les Halles"), 1);
			gnb.addStationToRoute(m4, g.getStation(16), 1);
			gnb.linkStationBidirectional(m1, g.getStation(16), m4, g.getStation(16), 0, 1, false);
			gnb.addStationToRoute(m4, gnb.addStation(90, "Cité"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(91, "Saint-Michel"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(92, "Odéon"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(93, "Saint-Germain-des-Prés"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(94, "Saint-Sulpice"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(95, "Saint-Placide"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(96, "Montparnasse Bienvenue"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(97, "Vavin"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(98, "Raspail"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(99, "Denfert Rochereau"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(100, "Mouton-Duvernet"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(101, "Alésia"), 1);
			gnb.addStationToRoute(m4, gnb.addStation(102, "Porte d'Orléans"), 1);

			gnb.addStationToRoute(m5, gnb.addStation(103, "Bobigny, Pablo Picasso"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(104, "Bobigny-Pantin, Raymond Queneau"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(105, "Église de Pantin"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(106, "Hoche"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(107, "Porte de Pantin"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(108, "Ourcq"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(109, "Laumière"), 1);
			gnb.addStationToRoute(m5, g.getStation(42), 1);
			gnb.linkStationBidirectional(m2, g.getStation(42), m5, g.getStation(42), 0, 1, false);
			gnb.addStationToRoute(m5, g.getStation(41), 1);
			gnb.linkStationBidirectional(m2, g.getStation(41), m5, g.getStation(41), 0, 1, false);
			gnb.addStationToRoute(m5, g.getStation(82), 1);
			gnb.linkStationBidirectional(m4, g.getStation(82), m5, g.getStation(82), 0, 1, false);
			gnb.addStationToRoute(m5, g.getStation(83), 1);
			gnb.linkStationBidirectional(m4, g.getStation(83), m5, g.getStation(83), 0, 1, false);
			gnb.addStationToRoute(m5, gnb.addStation(114, "Jacques Bonsergent"), 1);
			gnb.addStationToRoute(m5, g.getStation(70), 1);
			gnb.linkStationBidirectional(m3, g.getStation(70), m5, g.getStation(70), 0, 1, false);
			gnb.addStationToRoute(m5, gnb.addStation(116, "Oberkampf"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(117, "Richard Lenoir"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(118, "Bréguet-Sabin"), 1);
			gnb.addStationToRoute(m5, g.getStation(19), 1);
			gnb.linkStationBidirectional(m1, g.getStation(19), m5, g.getStation(19), 0, 1, false);
			gnb.addStationToRoute(m5, gnb.addStation(120, "Quai de la Rapée"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(121, "Gare d'Austerlitz"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(122, "Saint-Marcel"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(123, "Campo-Formio"), 1);
			gnb.addStationToRoute(m5, gnb.addStation(124, "Place d'Italie"), 1);

			gnb.addStationToRoute(m6, g.getStation(8), 1);
			gnb.linkStationBidirectional(m1, g.getStation(8), m6, g.getStation(8), 0, 1, false);
			gnb.linkStationBidirectional(m2, g.getStation(8), m6, g.getStation(8), 0, 1, false);
			gnb.addStationToRoute(m6, gnb.addStation(126, "Kléber"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(127, "Boissière"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(128, "Trocadéro"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(129, "Passy"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(130, "Bir-Hakeim"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(131, "Dupleix"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(132, "La Motte Picquet, Grenelle"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(133, "Cambronne"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(134, "Sèvres Lecourbe"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(135, "Pasteur"), 1);
			gnb.addStationToRoute(m6, g.getStation(96), 1);
			gnb.linkStationBidirectional(m4, g.getStation(96), m6, g.getStation(96), 0, 1, false);
			gnb.addStationToRoute(m6, gnb.addStation(137, "Edgar Quinet"), 1);
			gnb.addStationToRoute(m6, g.getStation(98), 1);
			gnb.linkStationBidirectional(m4, g.getStation(98), m6, g.getStation(98), 0, 1, false);
			gnb.addStationToRoute(m6, g.getStation(99), 1);
			gnb.linkStationBidirectional(m4, g.getStation(99), m6, g.getStation(99), 0, 1, false);
			gnb.addStationToRoute(m6, gnb.addStation(140, "Saint-Jacques"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(141, "Glacière"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(142, "Corvisart"), 1);
			gnb.addStationToRoute(m6, g.getStation(124), 1);
			gnb.linkStationBidirectional(m5, g.getStation(124), m6, g.getStation(124), 0, 1, false);
			gnb.addStationToRoute(m6, gnb.addStation(144, "Nationale"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(145, "Chevaleret"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(146, "Quai de la Gare"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(147, "Bercy"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(148, "Dugommier"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(149, "Daumesnil"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(150, "Bel Air"), 1);
			gnb.addStationToRoute(m6, gnb.addStation(151, "Picpus"), 1);
			gnb.addStationToRoute(m6, g.getStation(22), 1);
			gnb.linkStationBidirectional(m1, g.getStation(22), m6, g.getStation(22), 0, 1, false);
			gnb.linkStationBidirectional(m2, g.getStation(22), m6, g.getStation(22), 0, 1, false);

			gnb.addStationToRoute(m7, gnb.addStation(153, "La Courneuve, 8 Mai 1945"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(154, "Fort d'Aubervilliers"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(155, "Aubervilliers-Pantin, Quatre Chemins"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(156, "Porte de la Villette"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(157, "Corentin-Cariou"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(158, "Crimée"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(159, "Riquet"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(160, "Stalingrad"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(161, "Louis Blanc"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(162, "Château Landon"), 1);
			gnb.addStationToRoute(m7, g.getStation(83), 1);
			gnb.linkStationBidirectional(m4, g.getStation(83), m7, g.getStation(83), 0, 1, false);
			gnb.linkStationBidirectional(m5, g.getStation(83), m7, g.getStation(83), 0, 1, false);
			gnb.addStationToRoute(m7, gnb.addStation(164, "Poissonnière"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(165, "Cadet"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(166, "Le Peletier"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(167, "Chaussée d'Antin, La Fayette"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(168, "Opéra"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(169, "Pyramides"), 1);
			gnb.addStationToRoute(m7, g.getStation(14), 1);
			gnb.linkStationBidirectional(m1, g.getStation(14), m7, g.getStation(14), 0, 1, false);
			gnb.addStationToRoute(m7, gnb.addStation(171, "Pont-Neuf"), 1);
			gnb.addStationToRoute(m7, g.getStation(16), 1);
			gnb.linkStationBidirectional(m1, g.getStation(16), m7, g.getStation(16), 0, 1, false);
			gnb.linkStationBidirectional(m4, g.getStation(16), m7, g.getStation(16), 0, 1, false);
			gnb.addStationToRoute(m7, gnb.addStation(173, "Pont-Marie"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(174, "Sully Morland"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(175, "Jussieu"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(176, "Place Monge"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(177, "Censier Daubenton"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(178, "Les Gobelins"), 1);
			gnb.addStationToRoute(m7, g.getStation(124), 1);
			gnb.linkStationBidirectional(m5, g.getStation(124), m7, g.getStation(124), 0, 1, false);
			gnb.linkStationBidirectional(m6, g.getStation(124), m7, g.getStation(124), 0, 1, false);
			gnb.addStationToRoute(m7, gnb.addStation(180, "Tolbiac"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(181, "Maison Blanche"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(182, "Porte d'Italie"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(183, "Porte de Choisy"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(184, "Porte d'Ivry"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(185, "Pierre Curie"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(186, "Mairie d'Ivry"), 1);
			gnb.addStationToRoute(m7, g.getStation(181), 1);
			gnb.linkStationBidirectional(m7, g.getStation(181), m7, g.getStation(181), 0, 1, false);
			gnb.addStationToRoute(m7, gnb.addStation(188, "le Kremlin-Bicêtre"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(189, "Villejuif, Léo Lagrange"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(190, "Villejuif, P. Vaillant Couturier"), 1);
			gnb.addStationToRoute(m7, gnb.addStation(191, "Villejuif, Louis Aragon"), 1);

			gnb.addStationToRoute(m8, gnb.addStation(192, "Place Balard"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(193, "Lourmel"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(194, "Boucicaut"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(195, "Félix Faure"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(196, "Commerce"), 1);
			gnb.addStationToRoute(m8, g.getStation(132), 1);
			gnb.linkStationBidirectional(m6, g.getStation(132), m8, g.getStation(132), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(198, "École Militaire"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(199, "La Tour-Maubourg"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(200, "Invalides"), 1);
			gnb.addStationToRoute(m8, g.getStation(12), 1);
			gnb.linkStationBidirectional(m1, g.getStation(12), m8, g.getStation(12), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(202, "Madeleine"), 1);
			gnb.addStationToRoute(m8, g.getStation(168), 1);
			gnb.linkStationBidirectional(m7, g.getStation(168), m8, g.getStation(168), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(204, "Richelieu Drouot"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(205, "Rue Montmartre, Grands Boulevards"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(206, "Bonne Nouvelle"), 1);
			gnb.addStationToRoute(m8, g.getStation(85), 1);
			gnb.linkStationBidirectional(m4, g.getStation(85), m8, g.getStation(85), 0, 1, false);
			gnb.addStationToRoute(m8, g.getStation(70), 1);
			gnb.linkStationBidirectional(m3, g.getStation(70), m8, g.getStation(70), 0, 1, false);
			gnb.linkStationBidirectional(m5, g.getStation(70), m8, g.getStation(70), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(209, "Filles du Calvaire"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(210, "Saint-Sébastien-Froissart"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(211, "Chemin Vert"), 1);
			gnb.addStationToRoute(m8, g.getStation(19), 1);
			gnb.linkStationBidirectional(m1, g.getStation(19), m8, g.getStation(19), 0, 1, false);
			gnb.linkStationBidirectional(m5, g.getStation(19), m8, g.getStation(19), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(213, "Ledru Rollin"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(214, "Faidherbe-Chaligny"), 1);
			gnb.addStationToRoute(m8, g.getStation(21), 1);
			gnb.linkStationBidirectional(m1, g.getStation(21), m8, g.getStation(21), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(216, "Montgallet"), 1);
			gnb.addStationToRoute(m8, g.getStation(149), 1);
			gnb.linkStationBidirectional(m6, g.getStation(149), m8, g.getStation(149), 0, 1, false);
			gnb.addStationToRoute(m8, gnb.addStation(218, "Michel Bizot"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(219, "Porte Dorée"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(220, "Porte de Charenton"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(221, "Liberté"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(222, "Charenton-Écoles"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(223, "École Vétérinaire de Maisons-Alfort"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(224, "Maisons-Alfort, Stade"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(225, "Maisons-Alfort les Juilliottes"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(226, "Créteil-l'Echat, Hôpital Henri Mondor"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(227, "Créteil-Université"), 1);
			gnb.addStationToRoute(m8, gnb.addStation(228, "Créteil-Préfecture"), 1);

			gnb.addStationToRoute(m9, gnb.addStation(229, "Pont de Sèvres"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(230, "Billancourt"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(231, "Marcel Sembat"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(232, "Porte de Saint-Cloud"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(233, "Exelmans"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(234, "Michel Ange Molitor"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(235, "Michel Ange Auteuil"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(236, "Jasmin"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(237, "Ranelagh"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(238, "La Muette"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(239, "Rue de la Pompe"), 1);
			gnb.addStationToRoute(m9, g.getStation(128), 1);
			gnb.linkStationBidirectional(m6, g.getStation(128), m9, g.getStation(128), 0, 1, false);
			gnb.addStationToRoute(m9, gnb.addStation(241, "Iéna"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(242, "Alma Marceau"), 1);
			gnb.addStationToRoute(m9, g.getStation(10), 1);
			gnb.linkStationBidirectional(m1, g.getStation(10), m9, g.getStation(10), 0, 1, false);
			gnb.addStationToRoute(m9, gnb.addStation(244, "Saint-Philippe du Roule"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(245, "Miromesnil"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(246, "Saint-Augustin"), 1);
			gnb.addStationToRoute(m9, g.getStation(62), 1);
			gnb.linkStationBidirectional(m3, g.getStation(62), m9, g.getStation(62), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(167), 1);
			gnb.linkStationBidirectional(m7, g.getStation(167), m9, g.getStation(167), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(204), 1);
			gnb.linkStationBidirectional(m8, g.getStation(204), m9, g.getStation(204), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(205), 1);
			gnb.linkStationBidirectional(m8, g.getStation(205), m9, g.getStation(205), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(206), 1);
			gnb.linkStationBidirectional(m8, g.getStation(206), m9, g.getStation(206), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(85), 1);
			gnb.linkStationBidirectional(m4, g.getStation(85), m9, g.getStation(85), 0, 1, false);
			gnb.linkStationBidirectional(m8, g.getStation(85), m9, g.getStation(85), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(70), 1);
			gnb.linkStationBidirectional(m3, g.getStation(70), m9, g.getStation(70), 0, 1, false);
			gnb.linkStationBidirectional(m5, g.getStation(70), m9, g.getStation(70), 0, 1, false);
			gnb.linkStationBidirectional(m8, g.getStation(70), m9, g.getStation(70), 0, 1, false);
			gnb.addStationToRoute(m9, g.getStation(116), 1);
			gnb.linkStationBidirectional(m5, g.getStation(116), m9, g.getStation(116), 0, 1, false);
			gnb.addStationToRoute(m9, gnb.addStation(255, "Saint-Ambroise"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(256, "Voltaire"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(257, "Charonne"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(258, "Rue des Boulets"), 1);
			gnb.addStationToRoute(m9, g.getStation(22), 1);
			gnb.linkStationBidirectional(m1, g.getStation(22), m9, g.getStation(22), 0, 1, false);
			gnb.addStationToRoute(m9, gnb.addStation(260, "Buzenval"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(261, "Maraîchers"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(262, "Porte de Montreuil"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(263, "Robespierre"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(264, "Croix de Chavaux"), 1);
			gnb.addStationToRoute(m9, gnb.addStation(265, "Mairie de Montreuil"), 1);

			gnb.addStationToRoute(m10, gnb.addStation(266, "Porte d'Auteuil"), 1);
			gnb.addStationToRoute(m10, g.getStation(234), 1);
			gnb.linkStationBidirectional(m9, g.getStation(234), m10, g.getStation(234), 0, 1, false);
			gnb.addStationToRoute(m10, gnb.addStation(268, "Boulogne, Pont de Saint-Cloud, Rond Point Rhin et Danude"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(269, "Boulogne, Jean Jaurès"), 1);
			gnb.addStationToRoute(m10, g.getStation(234), 1);
			gnb.linkStationBidirectional(m9, g.getStation(234), m10, g.getStation(234), 0, 1, false);
			gnb.addStationToRoute(m10, gnb.addStation(271, "Chardon Lagâche"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(272, "Mirabeau"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(273, "Javel"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(274, "Charles Michels"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(275, "Avenue Émile Zola"), 1);
			gnb.addStationToRoute(m10, g.getStation(132), 1);
			gnb.linkStationBidirectional(m6, g.getStation(132), m10, g.getStation(132), 0, 1, false);
			gnb.linkStationBidirectional(m8, g.getStation(132), m10, g.getStation(132), 0, 1, false);
			gnb.addStationToRoute(m10, gnb.addStation(277, "Ségur"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(278, "Duroc"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(279, "Vaneau"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(280, "Sèvres Babylone"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(281, "Mabillon"), 1);
			gnb.addStationToRoute(m10, g.getStation(92), 1);
			gnb.linkStationBidirectional(m4, g.getStation(92), m10, g.getStation(92), 0, 1, false);
			gnb.addStationToRoute(m10, gnb.addStation(283, "Cluny, La Sorbonne"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(284, "Maubert Mutualité"), 1);
			gnb.addStationToRoute(m10, gnb.addStation(285, "Cardinal Lemoine"), 1);
			gnb.addStationToRoute(m10, g.getStation(175), 1);
			gnb.linkStationBidirectional(m7, g.getStation(175), m10, g.getStation(175), 0, 1, false);
			gnb.addStationToRoute(m10, g.getStation(121), 1);
			gnb.linkStationBidirectional(m5, g.getStation(121), m10, g.getStation(121), 0, 1, false);

			gnb.addStationToRoute(m11, gnb.addStation(288, "Mairie des Lilas"), 1);
			gnb.addStationToRoute(m11, gnb.addStation(289, "Porte des Lilas"), 1);
			gnb.addStationToRoute(m11, gnb.addStation(290, "Télégraphe"), 1);
			gnb.addStationToRoute(m11, gnb.addStation(291, "Place des Fêtes"), 1);
			gnb.addStationToRoute(m11, gnb.addStation(292, "Jourdain"), 1);
			gnb.addStationToRoute(m11, gnb.addStation(293, "Pyrénées"), 1);
			gnb.addStationToRoute(m11, g.getStation(44), 1);
			gnb.linkStationBidirectional(m2, g.getStation(44), m11, g.getStation(44), 0, 1, false);
			gnb.addStationToRoute(m11, gnb.addStation(295, "Goncourt"), 1);
			gnb.addStationToRoute(m11, g.getStation(70), 1);
			gnb.linkStationBidirectional(m3, g.getStation(70), m11, g.getStation(70), 0, 1, false);
			gnb.addStationToRoute(m11, g.getStation(68), 1);
			gnb.linkStationBidirectional(m3, g.getStation(68), m11, g.getStation(68), 0, 1, false);
			gnb.addStationToRoute(m11, gnb.addStation(298, "Rambuteau"), 1);
			gnb.addStationToRoute(m11, g.getStation(17), 1);
			gnb.linkStationBidirectional(m1, g.getStation(17), m11, g.getStation(17), 0, 1, false);
			gnb.addStationToRoute(m11, g.getStation(16), 1);
			gnb.linkStationBidirectional(m1, g.getStation(16), m11, g.getStation(16), 0, 1, false);
			gnb.linkStationBidirectional(m4, g.getStation(16), m11, g.getStation(16), 0, 1, false);
			gnb.linkStationBidirectional(m7, g.getStation(16), m11, g.getStation(16), 0, 1, false);

			// TODO A FINIR J'AI MAL AUX YEUX ^^

			gnb.addStationToRoute(m12, gnb.addStation(301, "Porte de la Chapelle"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(302, "Marx Dormoy"), 1);
			gnb.addStationToRoute(m12, g.getStation(79), 1);
			gnb.addStationToRoute(m12, gnb.addStation(304, "Jules Joffrin"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(305, "Lamarck Caulaincourt"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(306, "Abbesses"), 1);
			gnb.addStationToRoute(m12, g.getStation(37), 1);
			gnb.addStationToRoute(m12, gnb.addStation(308, "Saint-Georges"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(309, "Notre Dame de Lorette"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(310, "Trinité d'Estienne d'Orves"), 1);
			gnb.addStationToRoute(m12, g.getStation(61), 1);
			gnb.addStationToRoute(m12, g.getStation(202), 1);
			gnb.addStationToRoute(m12, g.getStation(12), 1);
			gnb.addStationToRoute(m12, gnb.addStation(314, "Assemblée Nationale"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(315, "Solférino"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(316, "Rue du Bac"), 1);
			gnb.addStationToRoute(m12, g.getStation(280), 1);
			gnb.addStationToRoute(m12, gnb.addStation(318, "Rennes"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(319, "Notre-Dame-des-Champs"), 1);
			gnb.addStationToRoute(m12, g.getStation(96), 1);
			gnb.addStationToRoute(m12, gnb.addStation(321, "Falguière"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(322, "Pasteur"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(323, "Volontaires"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(324, "Vaugirard"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(325, "Convention"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(326, "Porte de Versailles"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(327, "Corentin Celton"), 1);
			gnb.addStationToRoute(m12, gnb.addStation(328, "Mairie d'Issy"), 1);

			gnb.addStationToRoute(m13, gnb.addStation(329, "Gabriel Péri, Asnières-Gennevilliers"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(330, "Mairie de Clichy"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(331, "Porte de Clichy"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(332, "Brochant"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(333, "La Fourche"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(334, "Saint-Denis-Université"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(335, "Basilique de Saint-Denis"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(336, "Saint-Denis-Porte de Paris"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(337, "Carrefour Pleyel"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(338, "Mairie de Saint-Ouen"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(339, "Garibaldi"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(340, "Porte de Saint-Ouen"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(341, "Guy Môquet"), 1);
			gnb.addStationToRoute(m13, g.getStation(333), 1);
			gnb.addStationToRoute(m13, g.getStation(35), 1);
			gnb.addStationToRoute(m13, gnb.addStation(344, "Liège"), 1);
			gnb.addStationToRoute(m13, g.getStation(61), 1);
			gnb.addStationToRoute(m13, g.getStation(245), 1);
			gnb.addStationToRoute(m13, g.getStation(11), 1);
			gnb.addStationToRoute(m13, g.getStation(200), 1);
			gnb.addStationToRoute(m13, gnb.addStation(349, "Varenne"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(350, "Saint-Francois Xavier"), 1);
			gnb.addStationToRoute(m13, g.getStation(278), 1);
			gnb.addStationToRoute(m13, g.getStation(96), 1);
			gnb.addStationToRoute(m13, gnb.addStation(353, "Gaité"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(354, "Pernety"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(355, "Plaisance"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(356, "Porte de Vanves"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(357, "Malakoff-Plateau de Vanves"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(358, "Malakoff-Rue Étienne Dolet"), 1);
			gnb.addStationToRoute(m13, gnb.addStation(359, "Châtillon-Montrouge"), 1);

			gnb.addStationToRoute(m14, gnb.addStation(360, "Madeleine"), 1);
			gnb.addStationToRoute(m14, g.getStation(169), 1);
			gnb.addStationToRoute(m14, g.getStation(16), 1);
			gnb.addStationToRoute(m14, g.getStation(20), 1);
			gnb.addStationToRoute(m14, g.getStation(147), 1);
			gnb.addStationToRoute(m14, gnb.addStation(365, "Cour Saint-Émilion"), 1);
			gnb.addStationToRoute(m14, gnb.addStation(366, "Bibliothèque François Mitterand"), 1);

			gnb.addService(2, "Cafe");
			gnb.addService(1, "Coffre");
			for (int i = 30; i < 70; i++) {
				gnb.addServiceToStation(g.getStation(i), g.getService(2));
			}
			for (int i = 30; i < 69; i++) {
				gnb.addServiceToStation(g.getStation(i),g.getService(1));
			}
				

			gnb.defineEntryCost(sncf.getKindFromString("RER"), 4);
			/*
			 * gnb.linkStationBidirectional(rerC, massyPal, rerB, massyPal, 0,
			 * 42, false); gnb.linkStationBidirectional(rerC,
			 * sncf.getStation(9), rerB, sncf.getStation(1),2, 9, true);
			 * 
			 * gnb.addServiceToStation(sncf.getStation(9), gnb.addService(1,
			 * "Journaux")); gnb.addServiceToStation(sncf.getStation(9),
			 * gnb.addService(2, "Cafe"));
			 * gnb.addServiceToStation(sncf.getStation(9), gnb.addService(3,
			 * "Handi")); gnb.addServiceToStation(sncf.getStation(2),
			 * sncf.getService(3)); gnb.addServiceToStation(sncf.getStation(10),
			 * sncf.getService(3)); gnb.addServiceToStation(sncf.getStation(4),
			 * sncf.getService(3)); gnb.addServiceToStation(sncf.getStation(5),
			 * sncf.getService(3)); gnb.addServiceToStation(sncf.getStation(1),
			 * sncf.getService(1)); gnb.addServiceToStation(sncf.getStation(8),
			 * sncf.getService(3));
			 */
		} catch (ViolationOfUnicityInIdentificationException e) {
			e.printStackTrace();
			assertTrue("Erreur dans la vérification des id unique", false);
		} catch (MissingResourceException e) {
			assertTrue("un objet est null dans linkstation", false);
		} catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (StationNotOnRoadException e) {
			e.printStackTrace();
		}

		assertTrue("Construction sans problème", true);
	}

	@Test
	public void verifGraphOriente() {
		Station massy = sncf.getStation(10);
		Iterator<Junction> it = massy.getJunctions();
		int nbJunctions = 0;
		while (it.hasNext()) {
			it.next();
			nbJunctions++;
		}
		/*
		 * Massy Palaiseau(RerB)<=>Le Guichet(RerB) : 0.0$ in 9 minutes Massy
		 * Palaiseau(RerC)<=>Choisy(RerC) : 0.0$ in 15 minutes Massy
		 * Palaiseau(RerC)=>Massy Palaiseau(RerB) : 0.0$ in 3 minutes Massy
		 * Palaiseau(RerB)=>Massy Palaiseau(RerC) : 0.0$ in 3 minutes
		 * Juvisy(RerC)<=>Massy Palaiseau(RerC) : 0.0$ in 25 minutes Massy
		 * Palaiseau(RerB)<=>Croix de Berny(RerB) Massy
		 * Palaiseau(RerC)<=>Orly(RerC)
		 * 
		 * Manque :
		 * 
		 * Plus rien !
		 */
		// assertTrue("mauvais nombre de jonctions, ici " + nbJunctions +
		// " et normalement 7", nbJunctions == 7);
	}

	@Test
	public void CréationGraph() throws NoRouteForStationException {
		GraphNetwork gn = gnb.getCurrentGraphNetwork();
		PathInGraphCollectionBuilder pc = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphConstraintBuilder pcb = pc.getPathInGraphConstraintBuilder();

		pcb.setMainCriterious(CriteriousForLowerPath.TIME);
		pcb.setMinorCriterious(CriteriousForLowerPath.CHANGE);
		pcb.setOrigin(gn.getStation(102));
		pcb.setDestination(gn.getStation(103));
		pcb.addStepStations(gn.getStation(77));
		pcb.addStepStations(gn.getStation(27));
		pcb.addSeviceOnce(gn.getService(2));
		pcb.addSeviceOnce(gn.getService(1));

		PathInGraphResultBuilder prb = pc.getPathInGraphResultBuilder();

		bob = new Dijkstra();

		long begin = System.currentTimeMillis();
		try {
			bob.findPath(prb);
		} catch (VoidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceNotAccessibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StationNotAccessibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StationNotOnRoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		Iterator<Junction> it = prb.getCurrentPathInGraph().getJunctions();
		int time = 0;
		int changes = 0;
		while (it.hasNext()) {
			Junction j = it.next();
			time += j.getTimeBetweenStations();
			if (!j.isRouteLink())
				changes++;
			System.out.println(j.toString());
		}
		System.out.println("---------------------------------------");
		System.out.println("Time : " + time + " minutes");
		System.out.println(changes + " changements");
		System.out.println("---------------------------------------");
		System.out.println((end - begin) + " ms pour création du graph et calcul de l'itinéraire");
	}

	@Test
	public void CalculPlusCourtChemin() {

	}

}
