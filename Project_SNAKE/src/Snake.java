import java.util.* ;

/**
 * Klasa zawiera wszystkie informacje zwiazane ze Snake'em :
 * jego aktualna pozycje, w ktorym kierunku sie porusza, 
 * ile aktualnie ma czesci ciala.
 * W klasie tej rowniez przechowywany jest wynik gry.
 * @author Ksawery
 */
public class Snake {
	
	Plansza plansza ; // referencja do obiektu planszy
	
	ArrayList<Integer> wspX ; // kontener przechowujacy wspolrzedna x kazdej czesci ciala snake'a
	ArrayList<Integer> wspY ; // kontener przechowujacy wspolrzedna y kazdej czesci ciala snake'a
	int zapamietanyX0 ; // wspolrzedna x ostatniej czesci ciala snake'a z przed dwoch ruchow
	int zapamietanyY0 ; // wspolrzedna y ostatniej czesci ciala snake'a z przed dwoch ruchow
	int zapamietanyX1 ; // wspolrzedna x ostatniej czesci ciala snake'a z przed ostatniego ruchu
	int zapamietanyY1 ; // wspolrzedna y ostatniej czesci ciala snake'a z przed ostatniego ruchu
	int liczbaCzesci ; // liczba czesci ciala snake'a
	boolean gora ; // zmienna sygnalizujaca, ze snake porusza sie w gore
	boolean dol ; // ----------------------||-------------------- w dol
	boolean prawo ; // --------------------||-------------------- w prawo
	boolean lewo ; // ---------------------||-------------------- w lewo
	int wynik ; // aktutalny wynik gry
	
	/** 
	 * Konstruktor przekazuje refernecje do obiektu planszy 
	 * oraz ustawia poczatkowa pozycje Snake'a
	 * @param pl Referencja do obiektu planszy
	 */
	public Snake( Plansza pl) {
		
		plansza = pl ;
		snakePoczatkowaPozycja() ;
		
	}
	
	/**
	 * Metoda ta ustawia Snake'a w poczatkowej pozycji
	 */
	public void snakePoczatkowaPozycja() {
		wspX = new ArrayList<Integer>() ;
		wspY = new ArrayList<Integer>() ;
		liczbaCzesci = 3 ;
		zapamietanyX1 = 0 ;
		zapamietanyY1 = 0 ;
		for( int i = 0; i < liczbaCzesci; i++ ) {
			wspX.add( 200 ) ;
			wspY.add( 150 + i * 10 ) ;
		}
	}
	
	/**
	 * Metoda dodaje czesc ciala na koniec Snake'a po zdobyciu owocu 
	 */
	public void dodajCzesc() {
		liczbaCzesci += 1 ; 
		wspX.add( zapamietanyX1 ) ;
		wspY.add( zapamietanyY1 ) ;
		
	}
	
	/**
	 * Dodaje druga czesc ciala w przypadku, gdy gracz najedzie na mala bombe 
	 */
	public void dodajDruga() {
		 liczbaCzesci += 1 ; 
		 wspX.add( zapamietanyX0 ) ;
		 wspY.add( zapamietanyY0 ) ;
	 }
	
	/**
	 * Aktualizuje wynik gracza po zdobyciu owocu, zlotej monety lub najechaniu na bombe 
	 */
	public void aktualizacjaWyniku() {
		if( plansza.przedmiot == plansza.malaBomba || plansza.przedmiot == plansza.bombaAtomowa ) {
			wynik = wynik * ( 100 - plansza.przedmiot.dajBonus() ) / 100 ;
		}
		else wynik += plansza.przedmiot.dajBonus() ; 
	}
	
	/**
	 * Metoda ta skraca Snake'a o jedna czesc w przypadku zdobycia zlotej monety
	 */
	public void odejmijCzesc() {
		if( liczbaCzesci > 1 ) {
			wspX.remove( liczbaCzesci - 1 ) ;
			wspY.remove( liczbaCzesci - 1 ) ;
			liczbaCzesci -= 1 ;
		}
	}
	
	/**
	 * Metoda odpowiedzialna jest za przesuniecie Snake'a o jedno pole w odpowiednim kierunku 
	 */
	public void wykonajRuch() {
		
		zapamietanyX0 = zapamietanyX1 ;
		zapamietanyY0 = zapamietanyY1 ;
		zapamietanyX1 = wspX.get(liczbaCzesci - 1 ) ;
		zapamietanyY1 = wspY.get(liczbaCzesci - 1 ) ;
		
		for( int i = liczbaCzesci - 1; i > 0 ; i-- ) { // zmiana wspolrzednych kazdej czesci ciala snake'a
			wspX.set( i, wspX.get(i - 1) ) ;
			wspY.set( i, wspY.get(i - 1) ) ;
		}
		
		if ( gora ) { // zmiana wspolrzednych glowy snake'a
			wspX.set( 0, wspX.get(0) ) ;
			wspY.set( 0, wspY.get(0) - 10 ) ;
		}
		else if ( dol ) {
			wspX.set( 0, wspX.get(0) ) ;
			wspY.set( 0, wspY.get(0) + 10 ) ;
		}
		else if( prawo ) {
			wspX.set( 0, wspX.get(0) + 10 ) ;
			wspY.set( 0, wspY.get(0) ) ;
			
		}
		else if ( lewo ) {
			wspX.set( 0, wspX.get(0) - 10 ) ;
			wspY.set( 0, wspY.get(0) ) ;
		}	
	}
}


