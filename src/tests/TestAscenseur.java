package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import code.Ascenseur;
import code.Direction;
import code.Simulateur;

/**
 * Driver qui permet d'executer les tests unitaires sur le simulateur
 * d'ascenseur.
 */
public class TestAscenseur extends Simulateur {

	@Override
	public boolean check_peutRedemarrer() {
		return true;
	}

	private void attendreCondition(BooleanSupplier condition, long timeoutMs) {
		long start = System.currentTimeMillis();
		while (!condition.getAsBoolean() && (System.currentTimeMillis() - start) < timeoutMs) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void arreterThread(Ascenseur asc) {
		asc.stop();
	}

	/** Test unitaire sur l'ascenseur. */
	@Test
	public void testChoisirDirection_chemin1() {
		System.out.println("\n-> Test 1: choisir Direction - chemin 1\n");

		ascenseur = new Ascenseur(1, this);
		appels[1] = Direction.UP;
		System.out.println("(a) L'" + ascenseur + " est au 1er etage");

		// Valider le comportement :
		assertEquals(ascenseur.choisirDirection(), Direction.UP);

		System.out.println("(b) La direction choisie est bien UP");

	}

	/** Test unitaire sur l'ascenseur. */
	@Test
	public void testChoisirDirection_chemin2() {
		System.out.println("\n-> Test 2: choisir Direction - chemin 2\n");

		ascenseur = new Ascenseur(3, this);
		appels[0] = Direction.DOWN;
		System.out.println("(a) L'" + ascenseur + " est au 3e etage");

		// Valider le comportement :
		assertEquals(ascenseur.choisirDirection(), Direction.DOWN);

		System.out.println("(b) La direction choisie est bien DOWN");

	}

	/** Test unitaire sur l'ascenseur. */
	@Test
	public void testChoisirDirection_chemin3() {
		System.out.println("\n-> Test 3: choisir Direction - chemin 3\n");
		ascenseur = new Ascenseur(2, this);
		appels[2] = Direction.NONE;
		System.out.println("(a) L'" + ascenseur + " est au 2e etage");

		// Valider le comportement :
		assertEquals(ascenseur.choisirDirection(), Direction.NONE);
		System.out.println("(b) La direction choisie est bien NONE");
	}

	/** Test unitaire sur l'ascenseur. */
	@Test
	public void testRun_effaceAppelMemeEtage() {
		System.out.println("\n-> Test 4: run - appel au meme etage\n");

		Ascenseur asc = new Ascenseur(1, this);
		appels[0] = Direction.UP;
		System.out.println("(a) L'" + asc + " est au 1er etage");
		System.out.println("    appel a l'etage 1");

		asc.start();
		attendreCondition(() -> appels[0] == Direction.NONE, 500);
		arreterThread(asc);

		assertEquals(Direction.NONE, appels[0]);
		System.out.println("(b) L'appel a ete efface");
	}

	/** Test unitaire sur l'ascenseur. */
	@Test
	public void testRun_deplacementVersEtageSuperieur() {
		System.out.println("\n-> Test 5: run - deplacement vers etage superieur\n");

		Ascenseur asc = new Ascenseur(1, this);
		destinations[1] = true;
		System.out.println("(a) L'" + asc + " est au 1er etage");
		System.out.println("    destination etage 2");

		asc.start();
		attendreCondition(() -> asc.etage >= 2, 500);
		arreterThread(asc);

		assertTrue(asc.etage >= 2);
		System.out.println("(b) L'ascenseur est monte d'au moins un etage");
	}

}
