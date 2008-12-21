package algo;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Junction;
import graphNetwork.PathInGraph;
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
		sncf = gnb.getCurrentGraphNetwork();
		gnb.reset();
		try {
			Route m1 = gnb.addRoute("m1", "METRO");
			Route m2 = gnb.addRoute("m1", "METRO");
			Route m3 = gnb.addRoute("m1", "METRO");
			Route m4 = gnb.addRoute("m1", "METRO");
			Route m5 = gnb.addRoute("m1", "METRO");
			Route m6 = gnb.addRoute("m1", "METRO");
			Route m7 = gnb.addRoute("m1", "METRO");
			Route m8 = gnb.addRoute("m1", "METRO");
			Route m9 = gnb.addRoute("m1", "METRO");
			Route m10 = gnb.addRoute("m1", "METRO");
			Route m11 = gnb.addRoute("m1", "METRO");
			Route m12 = gnb.addRoute("m1", "METRO");
			Route m13 = gnb.addRoute("m1", "METRO");
			Route m14 = gnb.addRoute("m1", "METRO");

			gnb.addStationToRoute(m1, gnb.addStation(1, "Paris"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(2,
					"Grande Arche de la Défense"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(3,
					"Esplanade de la Défense"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(4, "Pont de Neuilly"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(5, "Les Sablons"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(6, "Porte Maillot"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(7, "Argentine"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(8,
					"Charles de Gaulle, Étoile"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(9, "George V"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(10,
					"Franklin D. Roosevelt"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(11,
					"Champs Élysées, Clémenceau"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(12, "Concorde"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(13, "Tuileries"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(14,
					"Palais Royal, Musée du Louvre"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(15, "Louvre, Rivoli"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(16, "Châtelet"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(17, "Hôtel de Ville"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(18,
					"Saint-Paul, Le Marais"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(19, "Bastille"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(20, "Gare de Lyon"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(21, "Reuilly Diderot"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(22, "Nation"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(23, "Porte de Vincennes"),
					0);
			gnb.addStationToRoute(m1, gnb.addStation(24,
					"Saint-Mandé, Tourelle"), 0);
			gnb.addStationToRoute(m1, gnb.addStation(25, "Bérault"), 0);
			gnb.addStationToRoute(m1, gnb
					.addStation(26, "Château de Vincennes"), 0);

			gnb.addStationToRoute(m2, gnb.addStation(27, "Porte Dauphine"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(28, "Victor Hugo"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(29,
					"Charles de Gaulle, Étoile"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(30, "Ternes"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(31, "Courcelles"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(32, "Monceau"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(33, "Villiers"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(34, "Rome"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(35, "Place de Clichy"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(36, "Blanche"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(37, "Pigalle"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(38, "Anvers"), 0);
			gnb.addStationToRoute(m2,
					gnb.addStation(39, "Barbès Rochechouart"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(40, "La Chapelle"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(41, "Stalingrad"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(42, "Jaurès"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(43, "Colonel Fabien"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(44, "Belleville"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(45, "Couronnes"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(46, "Ménilmontant"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(47, "Père Lachaise"), 0);
			gnb
					.addStationToRoute(m2, gnb.addStation(48,
							"Philippe-Auguste"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(49, "Alexandre Dumas"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(50, "Avron"), 0);
			gnb.addStationToRoute(m2, gnb.addStation(51, "Nation"), 0);

			gnb.addStationToRoute(m3, gnb.addStation(52,
					"Pont de Levallois, Bécon"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(53, "Anatole France"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(54, "Louise Michel"), 0);
			gnb.addStationToRoute(m3,
					gnb.addStation(55, "Porte de Champerret"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(56, "Péreire"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(57, "Wagram"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(58, "Malesherbes"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(59, "Villiers"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(60, "Europe"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(61, "Saint-Lazare"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(62, "Havre Caumartin"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(63, "Opéra"), 0);
			gnb
					.addStationToRoute(m3, gnb.addStation(64,
							"Quatre Septembre"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(65, "Bourse"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(66, "Sentier"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(67, "Réaumur Sébastopol"),
					0);
			gnb.addStationToRoute(m3, gnb.addStation(68, "Arts et Métiers"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(69, "Temple"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(70, "République"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(71, "Parmentier"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(72, "Rue Saint-Maur"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(73, "Père Lachaise"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(74, "Gambetta"), 0);
			gnb.addStationToRoute(m3, gnb.addStation(75, "Porte de Bagnolet"),
					0);
			gnb.addStationToRoute(m3, gnb.addStation(76, "Galliéni"), 0);

			gnb.addStationToRoute(m4, gnb.addStation(77,
					"Porte de Clignancourt"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(78, "Simplon"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(79,
					"Marcadet Poissonniers"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(80, "Château Rouge"), 0);
			gnb.addStationToRoute(m4,
					gnb.addStation(81, "Barbès Rochechouart"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(82, "Gare du Nord"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(83, "Gare de l'Est"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(84, "Château d'Eau"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(85,
					"Strasbourg Saint-Denis"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(86, "Réaumur Sébastopol"),
					0);
			gnb.addStationToRoute(m4, gnb.addStation(87, "Étienne Marcel"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(88, "Les Halles"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(89, "Châtelet"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(90, "Cité"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(91, "Saint-Michel"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(92, "Odéon"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(93,
					"Saint-Germain-des-Prés"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(94, "Saint-Sulpice"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(95, "Saint-Placide"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(96,
					"Montparnasse Bienvenue"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(97, "Vavin"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(98, "Raspail"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(99, "Denfert Rochereau"),
					0);
			gnb
					.addStationToRoute(m4, gnb.addStation(100,
							"Mouton-Duvernet"), 0);
			gnb.addStationToRoute(m4, gnb.addStation(101, "Alésia"), 0);
			gnb
					.addStationToRoute(m4, gnb.addStation(102,
							"Porte d'Orléans"), 0);

			gnb.addStationToRoute(m5, gnb.addStation(103,
					"Bobigny, Pablo Picasso"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(104,
					"Bobigny-Pantin, Raymond Queneau"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(105, "Église de Pantin"),
					0);
			gnb.addStationToRoute(m5, gnb.addStation(106, "Hoche"), 0);
			gnb
					.addStationToRoute(m5, gnb.addStation(107,
							"Porte de Pantin"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(108, "Ourcq"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(109, "Laumière"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(110, "Jaurès"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(111, "Stalingrad"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(112, "Gare du Nord"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(113, "Gare de l'Est"), 0);
			gnb.addStationToRoute(m5,
					gnb.addStation(114, "Jacques Bonsergent"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(115, "République"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(116, "Oberkampf"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(117, "Richard Lenoir"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(118, "Bréguet-Sabin"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(119, "Bastille"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(120, "Quai de la Rapée"),
					0);
			gnb.addStationToRoute(m5, gnb.addStation(121, "Gare d'Austerlitz"),
					0);
			gnb.addStationToRoute(m5, gnb.addStation(122, "Saint-Marcel"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(123, "Campo-Formio"), 0);
			gnb.addStationToRoute(m5, gnb.addStation(124, "Place d'Italie"), 0);

			gnb.addStationToRoute(m6, gnb.addStation(125,
					"Charles de Gaulle, Étoile"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(126, "Kléber"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(127, "Boissière"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(128, "Trocadéro"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(129, "Passy"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(130, "Bir-Hakeim"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(131, "Dupleix"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(132,
					"La Motte Picquet, Grenelle"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(133, "Cambronne"), 0);
			gnb
					.addStationToRoute(m6, gnb.addStation(134,
							"Sèvres Lecourbe"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(135, "Pasteur"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(136,
					"Montparnasse Bienvenue"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(137, "Edgar Quinet"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(138, "Raspail"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(139, "Denfert Rochereau"),
					0);
			gnb.addStationToRoute(m6, gnb.addStation(140, "Saint-Jacques"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(141, "Glacière"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(142, "Corvisart"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(143, "Place d'Italie"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(144, "Nationale"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(145, "Chevaleret"), 0);
			gnb
					.addStationToRoute(m6, gnb.addStation(146,
							"Quai de la Gare"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(147, "Bercy"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(148, "Dugommier"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(149, "Daumesnil"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(150, "Bel Air"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(151, "Picpus"), 0);
			gnb.addStationToRoute(m6, gnb.addStation(152, "Nation"), 0);

			gnb.addStationToRoute(m7, gnb.addStation(153,
					"La Courneuve, 8 Mai 1945"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(154,
					"Fort d'Aubervilliers"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(155,
					"Aubervilliers-Pantin, Quatre Chemins"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(156,
					"Porte de la Villette"), 0);
			gnb
					.addStationToRoute(m7, gnb.addStation(157,
							"Corentin-Cariou"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(158, "Crimée"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(159, "Riquet"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(160, "Stalingrad"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(161, "Louis Blanc"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(162, "Château Landon"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(163, "Gare de l'Est"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(164, "Poissonnière"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(165, "Cadet"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(166, "Le Peletier"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(167,
					"Chaussée d'Antin, La Fayette"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(168, "Opéra"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(169, "Pyramides"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(170,
					"Palais Royal, Musée du Louvre"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(171, "Pont-Neuf"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(172, "Châtelet"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(173, "Pont-Marie"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(174, "Sully Morland"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(175, "Jussieu"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(176, "Place Monge"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(177, "Censier Daubenton"),
					0);
			gnb.addStationToRoute(m7, gnb.addStation(178, "Les Gobelins"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(179, "Place d'Italie"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(180, "Tolbiac"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(181, "Maison Blanche"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(182, "Porte d'Italie"), 0);
			gnb
					.addStationToRoute(m7, gnb.addStation(183,
							"Porte de Choisy"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(184, "Porte d'Ivry"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(185, "Pierre Curie"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(186, "Mairie d'Ivry"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(187, "Maison Blanche"), 0);
			gnb.addStationToRoute(m7,
					gnb.addStation(188, "le Kremlin-Bicêtre"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(189,
					"Villejuif, Léo Lagrange"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(190,
					"Villejuif, P. Vaillant Couturier"), 0);
			gnb.addStationToRoute(m7, gnb.addStation(191,
					"Villejuif, Louis Aragon"), 0);

			gnb.addStationToRoute(m8, gnb.addStation(192, "Place Balard"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(193, "Lourmel"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(194, "Boucicaut"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(195, "Félix Faure"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(196, "Commerce"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(197,
					"La Motte Picquet, Grenelle"), 0);
			gnb
					.addStationToRoute(m8, gnb.addStation(198,
							"École Militaire"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(199, "La Tour-Maubourg"),
					0);
			gnb.addStationToRoute(m8, gnb.addStation(200, "Invalides"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(201, "Concorde"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(202, "Madeleine"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(203, "Opéra"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(204, "Richelieu Drouot"),
					0);
			gnb.addStationToRoute(m8, gnb.addStation(205,
					"Rue Montmartre, Grands Boulevards"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(206, "Bonne Nouvelle"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(207,
					"Strasbourg Saint-Denis"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(208, "République"), 0);
			gnb.addStationToRoute(m8,
					gnb.addStation(209, "Filles du Calvaire"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(210,
					"Saint-Sébastien-Froissart"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(211, "Chemin Vert"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(212, "Bastille"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(213, "Ledru Rollin"), 0);
			gnb.addStationToRoute(m8,
					gnb.addStation(214, "Faidherbe-Chaligny"), 0);
			gnb
					.addStationToRoute(m8, gnb.addStation(215,
							"Reuilly Diderot"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(216, "Montgallet"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(217, "Daumesnil"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(218, "Michel Bizot"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(219, "Porte Dorée"), 0);
			gnb.addStationToRoute(m8,
					gnb.addStation(220, "Porte de Charenton"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(221, "Liberté"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(222, "Charenton-Écoles"),
					0);
			gnb.addStationToRoute(m8, gnb.addStation(223,
					"École Vétérinaire de Maisons-Alfort"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(224,
					"Maisons-Alfort, Stade"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(225,
					"Maisons-Alfort les Juilliottes"), 0);
			gnb.addStationToRoute(m8, gnb.addStation(226,
					"Créteil-l'Echat, Hôpital Henri Mondor"), 0);
			gnb.addStationToRoute(m8,
					gnb.addStation(227, "Créteil-Université"), 0);
			gnb.addStationToRoute(m8,
					gnb.addStation(228, "Créteil-Préfecture"), 0);

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
			assertTrue("Erreur dans la vérification des id unique", false);
		} catch (MissingResourceException e) {
			assertTrue("un objet est null dans linkstation", false);
		} catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
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
		assertTrue("mauvais nombre de jonctions, ici " + nbJunctions
				+ " et normalement 7", nbJunctions == 7);

	}

	@Test
	public void CréationGraph() {
		GraphNetwork gn = gnb.getCurrentGraphNetwork();
		PathInGraphCollectionBuilder pc = gn
				.getInstancePathInGraphCollectionBuilder();
		PathInGraphConstraintBuilder pcb = pc.getPathInGraphConstraintBuilder();

		pcb.setMainCriterious(CriteriousForLowerPath.TIME);
		pcb.setMinorCriterious(CriteriousForLowerPath.CHANGE);
		pcb.setOrigin(gn.getStation(3));
		pcb.setDestination(gn.getStation(8));

		PathInGraphResultBuilder prb = pc.getPathInGraphResultBuilder();

		bob = new Dijkstra();

		PathInGraph p = bob.findPath(prb);

		Iterator<Junction> it = p.getJunctions();
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
	}

	@Test
	public void CalculPlusCourtChemin() {

	}

}
