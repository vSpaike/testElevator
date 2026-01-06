package code;

/**
 * Classe qui decrit l'etat et le comportement simule d'un ascenseur virtuel.
 */
public class Ascenseur extends Thread 
{
    /** L'etage courante sur lequel l'ascenseur se situe. */ 
	public int etage;
    /** La direction courante vers laquelle l'ascenceur se deplace. */ 
	public Direction dir;
    /** L'instance du simulateur qui gere le syteme. */
	private Simulateur sim;
    
    static private boolean DEBUG = Constantes.DEBUG;
    
    
    /** Constructeur. */
    public Ascenseur (int etage, Simulateur sim)
    {
		this.etage = etage;
		this.sim = sim;
		this.dir = Direction.NONE;
    }
        
    
    public void run()
    {
 		while(true)
		{	
			// [1] Si l'ascenceur n'a aucune direction, prendre celle de l'appel a l'etage
			if (dir == Direction.NONE && sim.appels[etage-1] != Direction.NONE)
				dir = sim.appels[etage-1];
			
		    // [2] Ouvrir si necessaire
		    if( (sim.appels[etage-1] != Direction.NONE && dir == sim.appels[etage-1]) 
		    		|| sim.destinations[etage-1])
		    {				
				// 2.1 Signaler l'arret
				sim.ajouter_evenement("+ Ascenseur:	\t+ arret a l'etage "+etage+"\n");
				sim.sig_arretAscenseur(etage);
				
				// 2.2 Attendre de pouvoir redemarrer
				while (!sim.check_peutRedemarrer())
				    try { Thread.sleep(100); }
				    catch(InterruptedException e) { System.out.print("Erreur dans Thread.sleep\n"); }					

				sim.ajouter_evenement("+ Ascenseur: \t\t+ fin de l'arret\n");

				// 2.3 Effacer appel ou destination pour l'etage courant
				if (sim.appels[etage-1] == dir)
					sim.appels[etage-1] = Direction.NONE;
				sim.destinations[etage-1] = false;		
		    }

		    // [3] Choisir la direction de l'ascenseur
		    dir = choisirDirection();
	    	sim.ajouter_evenement("+ Ascenseur:	\t+ direction: "+dir+"\n");

	    	// [4] Changer d'etage
		    if (dir == Direction.UP)
			    etage++;
		    else if (dir == Direction.DOWN)
			    etage--;

		    sim.ajouter_evenement("+ Ascenseur:	\t+ etage: "+etage+"\n");
	
		    // [5] Renverser la direction aux extremes
		    if (etage == Constantes.ETAGES)
		    	dir = Direction.DOWN;
		    else if (etage == 1)
		    	dir = Direction.UP;
	
		    try { Thread.sleep(100); }
		    catch(InterruptedException e) { System.out.print("Erreur dans Thread.sleep\n"); }
		}
    }
    
    
    /**
     * La methode choisirDirection() retourne la prochaine direction que 
     * l'ascenseur devrait prendre pour emmener un usager a destination
     * ou encore laisser entrer un usager en attente.
     */    
    private Direction choisirDirection()
    {
        Direction ret = Direction.NONE;
        
        if (etage == -1)
        {
            return Direction.NONE;
        }
        
        // [3.1] Si la direction est UP ou NONE
        if (this.dir == Direction.UP || this.dir == Direction.NONE)
        {
            // [3.1.1] S'il existe un appel au-dessus ou une destination au-dessus
            if (appelAuDessus(etage) || destinationAuDessus(etage))
            {
                ret = Direction.UP;
            }
            // [3.1.2] S'il existe un appel en dessous ou une destination en dessous
            else if (appelEnDessous(etage) || destinationEnDessous(etage))
            {
                ret = Direction.DOWN;
            }
            // [3.1.3] Sinon rester à l'étage (NONE)
            else
            {
                ret = Direction.NONE;
            }
        }
        // [3.2] Si la direction est DOWN
        else if (this.dir == Direction.DOWN)
        {
            // [3.2.1] S'il existe un appel en dessous ou une destination en dessous
            if (appelEnDessous(etage) || destinationEnDessous(etage))
            {
                ret = Direction.DOWN;
            }
            // [3.2.2] S'il existe un appel au-dessus ou une destination au-dessus
            else if (appelAuDessus(etage) || destinationAuDessus(etage))
            {
                ret = Direction.UP;
            }
            // [3.2.3] Sinon rester à l'étage (NONE)
            else
            {
                ret = Direction.NONE;
            }
        }
        
        if (DEBUG)
        {
            System.out.println(" >> choisirDirection() : direction actuelle = " + 
                              this.dir + ", nouvelle direction = " + ret);
        }
        
        return ret;
    }
    
    private boolean appelAuDessus(int etageCourant)
    {
    	boolean ret = false;
    	for (int i = Constantes.ETAGES-1; i >= etageCourant; i--)
    	{
    		if (sim.appels[i] != Direction.NONE)
    			ret = true;
    	}
    	return ret;
    }
    
    private boolean appelEnDessous(int etageCourant)
    {
    	boolean ret = false;
    	for (int i = 0; i < etageCourant - 1; i++)
    	{
    		if (sim.appels[i] != Direction.NONE)
    			ret = true;
    	}
    	return ret;
    }
    
    private boolean destinationAuDessus(int etageCourant)
    {
        boolean ret = false;
        for (int i = etageCourant; i < Constantes.ETAGES; i++)
        {
            if (sim.destinations[i] == true)
                ret = true;
        }
        return ret;
    }

    private boolean destinationEnDessous(int etageCourant)
    {
        boolean ret = false;
        for (int i = 0; i < etageCourant - 1; i++)
        {
            if (sim.destinations[i] == true)
                ret = true;
        }
        return ret;
    }
    
    public String toString()
	{
		return "Ascenseur[" +
				"etage=" + etage +
				", direction=" + dir + 
				"]";
	}
} 