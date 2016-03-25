
/**
 * Klasa ta przechowuje informacje o graczu :
 * jego nazwe oraz osiagniety wynik
 * @author Ksawery
 */
public class Gracz implements Comparable<Gracz> {

	String nazwa ; // nazwa gracza
	int wynik ; // uzyskany wynik gracza
	
	public Gracz() {
		
		wynik = 0 ;
		
	}

	/** 
	 * Metoda porownuje dwa obiekty typu Gracz wzgledem wyniku
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Gracz o) {
		if( wynik > o.wynik )
			return -1 ;
		else if( wynik < o.wynik )
			return 1 ;
		else
			return 0 ;
	}
	
	
	
}
