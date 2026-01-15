package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import code.Ascenseur;
import code.Direction;
import code.Porte;
import code.Simulateur;
import code.Usager;

/**
 * Driver qui permet d'executer les tests unitaires sur les usagers du
 * simulateur d'ascenseur.
 */
public class TestUsager extends Simulateur {

    /** Test unitaire sur un usager nominal (étage 1 vers étage 3). */
    @Test
    public void testUsager1() {
        System.out.println("\n-> Test 1: usager nominal (1 vers 3)\n");

        // Créer un usager au 1er étage qui veut aller au 3e
        Usager u = new Usager("U1", 1, 3, this);
        u.start();

        // Au départ, aucun appel au 1er étage
        assertEquals(Direction.NONE, appels[0]);
        System.out.println("(a) Initialement aucun appel à l'étage 1.");

        // Attendre que l'usager émette l'appel (boucle 100ms)
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // L'appel UP doit être enregistré au 1er étage
        assertEquals(Direction.UP, appels[0]);
        System.out.println("(b) Appel UP au 1er étage émis par l'usager.");

        // Préparer la porte du 1er étage et l'ascenseur en direction UP
        portes[0] = new Porte(1, 800, this);
        portes[0].start();
        ascenseur = new Ascenseur(1, this);
        ascenseur.dir = Direction.UP;

        // Signaler l'arrêt à l'étage 1 pour ouvrir la porte
        etageArret = 1;

        // Attendre que l'usager entre et signale la destination 3
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // La destination (3e étage) doit être enregistrée
        assertTrue(destinations[2]);
        System.out.println("(c) Destination 3 signalée par l'usager.");

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
        System.out.println("(d) Usager a atteint la destination et a terminé.");
    }

    /** Test unitaire sur un usager avec distraction. */
    @Test
    public void testUsager2() {
        System.out.println("\n-> Test 2: usager avec distraction (1 vers 3)\n");

        // Créer un usager au 1er étage qui veut aller au 3e, avec une distraction de
        // 100ms
        Usager u = new Usager("U2", 1, 3, 100, this);
        u.start();

        // Au départ, aucun appel au 1er étage
        assertEquals(Direction.NONE, appels[0]);
        System.out.println("(a) Initialement aucun appel à l'étage 1.");

        // Attendre que l'usager émette l'appel (boucle 100ms + possibilité de
        // distraction)
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // L'appel UP doit être enregistré au 1er étage
        assertEquals(Direction.UP, appels[0]);
        System.out.println("(b) Appel UP au 1er étage émis par l'usager (après possible distraction).");

        // Préparer la porte du 1er étage et l'ascenseur en direction UP
        portes[0] = new Porte(1, 800, this);
        portes[0].start();
        ascenseur = new Ascenseur(1, this);
        ascenseur.dir = Direction.UP;

        // Signaler l'arrêt à l'étage 1 pour ouvrir la porte
        etageArret = 1;

        // Attendre que l'usager entre et signale la destination 3
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // La destination (3e étage) doit être enregistrée
        assertTrue(destinations[2]);
        System.out.println("(c) Destination 3 signalée par l'usager.");

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
        System.out.println("(d) Usager a atteint la destination et a terminé (malgré la distraction).");
    }

    /** Test unitaire sur un usager descendant (étage 3 vers étage 1). */
    @Test
    public void testUsager3() {
        System.out.println("\n-> Test 3: usager descendant (3 vers 1)\n");

        // Créer un usager au 3e étage qui veut aller au 1er
        Usager u = new Usager("U3", 3, 1, this);
        u.start();

        // Au départ, aucun appel au 3e étage
        assertEquals(Direction.NONE, appels[2]);
        System.out.println("(a) Initialement aucun appel à l'étage 3.");

        // Attendre que l'usager émette l'appel (boucle 100ms)
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // L'appel DOWN doit être enregistré au 3e étage
        assertEquals(Direction.DOWN, appels[2]);
        System.out.println("(b) Appel DOWN au 3e étage émis par l'usager.");

        // Préparer la porte du 3e étage et l'ascenseur en direction DOWN
        portes[2] = new Porte(3, 800, this);
        portes[2].start();
        ascenseur = new Ascenseur(3, this);
        ascenseur.dir = Direction.DOWN;

        // Signaler l'arrêt à l'étage 3 pour ouvrir la porte
        etageArret = 3;

        // Attendre que l'usager entre et signale la destination 1
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // La destination (1er étage) doit être enregistrée
        assertTrue(destinations[0]);
        System.out.println("(c) Destination 1 signalée par l'usager.");

        // Préparer la porte du 1er étage
        portes[0] = new Porte(1, 800, this);
        portes[0].start();

        // Signaler l'arrêt à l'étage 1 pour ouvrir la porte à destination
        etageArret = 1;

        // Attendre que l'usager atteigne sa destination et termine
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            System.out.print("Erreur dans Thread.sleep\n");
        }

        // Vérifier qu'un usager a terminé
        assertEquals(1, usager_term);
        System.out.println("(d) Usager a atteint la destination et a terminé.");
    }
}
