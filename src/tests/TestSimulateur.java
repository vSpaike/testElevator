package tests;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import code.Ascenseur;
import code.Constantes;
import code.Simulateur;
import code.Porte;
import code.Direction;
import code.Usager;

/**
 * Driver qui permet d'executer les tests unitaires sur le simulateur
 * d'ascenseur.
 */
public class TestSimulateur extends Simulateur {
	/** Test unitaire sur la porte. */
	@Test
	public void testPorte1() {
		System.out.println("\n-> Test 1: ouverture/fermeture d'une porte au 1er l'etage\n");

		portes[0] = new Porte(1, Constantes.DELAIPORTE, this);
		portes[0].start();

		// Valider le comportement : la porte est bien fermee?
		assertFalse(portes[0].check_porteOuverte());
		assertEquals(etageArret, -1, 0);
		System.out.println("(a) La " + portes[0] + " est bien fermee, et aucun signal d'arret.");

		// Simuler l'arrêt de l'ascenseur au 1er etage
		etageArret = 1;

		// Attendre ouverture de la porte (1/2 delai)...
		try {
			Thread.sleep(Constantes.DELAIPORTE / 2);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// Valider le comportement : la porte est ouverte?
		assertTrue(portes[0].check_porteOuverte());
		System.out.println("(b) La " + portes[0] + " est bien ouverte.");

		// Attendre fermeture de la porte (delai complet)...
		try {
			Thread.sleep(Constantes.DELAIPORTE);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// Valider le comportement : la porte s'est refermee?
		assertFalse(portes[0].check_porteOuverte());
		System.out.println("(c) La " + portes[0] + " s'est bien refermee.");
	}

	@Test
	public void testUsager1() {
		System.out.println("\n-> Test Usager 1: appel et destination\n");

		// Créer un usager au 2e étage qui veut aller au 3e
		Usager u = new Usager("A", 2, 3, this);
		u.start();

		// Au départ, aucun appel au 2e étage
		assertEquals(Direction.NONE, appels[1]);

		// Attendre que l'usager émette l'appel (boucle 100ms)
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// L'appel UP doit être enregistré au 2e étage
		assertEquals(Direction.UP, appels[1]);
		System.out.println("(a) Appel UP au 2e étage émis par l'usager.");

		// Préparer la porte du 2e étage et l'ascenseur en direction UP
		portes[1] = new Porte(2, 800, this);
		portes[1].start();
		ascenseur = new code.Ascenseur(2, this);
		ascenseur.dir = Direction.UP;

		// Signaler l'arrêt à l'étage 2 pour ouvrir la porte
		etageArret = 2;

		// Attendre que l'usager entre et signale la destination 3
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// La destination (3e étage) doit être enregistrée
		assertTrue(destinations[2]);
		System.out.println("(b) Destination 3 signalée par l'usager.");

		// Préparer la porte du 3e étage
		portes[2] = new Porte(3, 800, this);
		portes[2].start();

		// Signaler l'arrêt à l'étage 3 pour ouvrir la porte à destination
		etageArret = 3;

		// Attendre que l'usager atteigne sa destination et termine
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// Vérifier qu'un usager a terminé
		assertEquals(1, usager_term);
		System.out.println("(c) Usager a atteint la destination et a terminé.");
	}

	@Test
	public void testUsager2() {
		System.out.println("\n-> Test Usager 1: appel et destination\n");

		// Créer un usager au 2e étage qui veut aller au 3e
		Usager u = new Usager("A", 2, 3, 100,this);
		u.start();

		// Au départ, aucun appel au 2e étage
		assertEquals(Direction.NONE, appels[1]);

		// Attendre que l'usager émette l'appel (boucle 100ms)
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// L'appel UP doit être enregistré au 2e étage
		assertEquals(Direction.UP, appels[1]);
		System.out.println("(a) Appel UP au 2e étage émis par l'usager.");

		// Préparer la porte du 2e étage et l'ascenseur en direction UP
		portes[1] = new Porte(2, 800, this);
		portes[1].start();
		ascenseur = new code.Ascenseur(2, this);
		ascenseur.dir = Direction.UP;

		// Signaler l'arrêt à l'étage 2 pour ouvrir la porte
		etageArret = 2;

		// Attendre que l'usager entre et signale la destination 3
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// La destination (3e étage) doit être enregistrée
		assertTrue(destinations[2]);
		System.out.println("(b) Destination 3 signalée par l'usager.");

		// Préparer la porte du 3e étage
		portes[2] = new Porte(3, 800, this);
		portes[2].start();

		// Signaler l'arrêt à l'étage 3 pour ouvrir la porte à destination
		etageArret = 3;

		// Attendre que l'usager atteigne sa destination et termine
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}

		// Vérifier qu'un usager a terminé
		assertEquals(1, usager_term);
		System.out.println("(c) Usager a atteint la destination et a terminé.");	}

	@Test
	public void testAscenseur1() {
		System.out.println("\n-> Test Ascenseur 1: déplacement et arrêts étages\n");

		Ascenseur a = new Ascenseur(1, this);
		a.start();

		// L'ascenseur démarre au 1er étage
		assertEquals(1, a.etage);
		System.out.println("(a) Ascenseur démarre au 1er étage.");

		// Simuler un appel UP au 2e étage
		appels[1] = Direction.UP;

		// Attendre que l'ascenseur atteigne le 2e étage
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}
		assertEquals(2, a.etage);
		System.out.println("(b) Ascenseur atteint le 2e étage.");
		// Simuler l'arrêt de l'ascenseur au 2e étage
		etageArret = 2;
		// Attendre que l'ascenseur s'arrête
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}
		assertEquals(2, a.etage);
		System.out.println("(c) Ascenseur s'arrête au 2e étage.");
		// Simuler une destination au 3e étage
		destinations[2] = true;
		// Redémarrer l'ascenseur
		etageArret = -1;
		// Attendre que l'ascenseur atteigne le 3e étage
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}
		assertEquals(3, a.etage);
		System.out.println("(d) Ascenseur atteint le 3e étage.");
		// Simuler l'arrêt de l'ascenseur au 3e étage
		etageArret = 3;
		// Attendre que l'ascenseur s'arrête
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.print("Erreur dans Thread.sleep\n");
		}
		assertEquals(3, a.etage);
		System.out.println("(e) Ascenseur s'arrête au 3e étage.");
		System.out.println("(f) L'Ascenseur a effectué les déplacements et arrêts correctement.");	
	}
}

