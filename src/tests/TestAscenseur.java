package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import code.Ascenseur;
import code.Direction;
import code.Simulateur;

/**
 * Tests structurels de la méthode Ascenseur::choisirDirection()
 * Critère de couverture : tous les nœuds
 */
public class TestAscenseur extends Simulateur {

    /**
     * Test 1 : direction NONE avec appel au-dessus
     * Chemin : [3, 5, 7, 8, 20]
     * Résultat attendu : UP
     */
    @Test
    public void testChoisirDirection_chemin1() {
        System.out.println("\n-> Test 1 : choisirDirection - chemin 1\n");

        ascenseur = new Ascenseur(1, this);
        appels[1] = Direction.UP;

        System.out.println("(a) L'" + ascenseur + " est au 1er étage");
        System.out.println("    appelAuDessus = true");

        assertEquals(Direction.UP, ascenseur.choisirDirection());

        System.out.println("(b) La direction choisie est bien UP");
    }

    /**
     * Test 2 : direction NONE avec appel en dessous
     * Chemin : [3, 5, 7, 9, 10, 20]
     * Résultat attendu : DOWN
     */
    @Test
    public void testChoisirDirection_chemin2() {
        System.out.println("\n-> Test 2 : choisirDirection - chemin 2\n");

        ascenseur = new Ascenseur(3, this);
        appels[0] = Direction.DOWN;

        System.out.println("(a) L'" + ascenseur + " est au 3ème étage");
        System.out.println("    appelEnDessous = true");

        assertEquals(Direction.DOWN, ascenseur.choisirDirection());

        System.out.println("(b) La direction choisie est bien DOWN");
    }

    /**
     * Test 3 : direction DOWN avec appel en dessous
     * Chemin : [3, 5, 12, 14, 15, 20]
     * Résultat attendu : DOWN
     */
    @Test
    public void testChoisirDirection_chemin3() {
        System.out.println("\n-> Test 3 : choisirDirection - chemin 3\n");

        ascenseur = new Ascenseur(4, this);
        ascenseur.dir = Direction.DOWN;
        appels[1] = Direction.DOWN;

        System.out.println("(a) L'" + ascenseur + " est au 4ème étage");
        System.out.println("    direction courante = DOWN");
        System.out.println("    appelEnDessous = true");

        assertEquals(Direction.DOWN, ascenseur.choisirDirection());

        System.out.println("(b) La direction choisie est bien DOWN");
    }

    /**
     * Test 4 : direction DOWN avec appel au-dessus
     * Chemin : [3, 5, 12, 14, 16, 17, 20]
     * Résultat attendu : UP
     */
    @Test
    public void testChoisirDirection_chemin4() {
        System.out.println("\n-> Test 4 : choisirDirection - chemin 4\n");

        ascenseur = new Ascenseur(1, this);
        ascenseur.dir = Direction.DOWN;
        appels[2] = Direction.UP;

        System.out.println("(a) L'" + ascenseur + " est au 1er étage");
        System.out.println("    direction courante = DOWN");
        System.out.println("    appelAuDessus = true");

        assertEquals(Direction.UP, ascenseur.choisirDirection());

        System.out.println("(b) La direction choisie est bien UP");
    }
}
