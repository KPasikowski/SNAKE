import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 * Klasa odpowiedzialna za wszystkie czynności zwiazane z plansza gry.
 * Zawiera metode rysujaca cala grafike podczas gry
 * @author Ksawery
 */
public class Plansza extends JPanel {
	
	Snake snake ; // referencja do obiektu snake'a
	PanelGra panelGra ; // referencja do obiektu panelu gry
	TrybGry trybGry ; // referencja do trybu gry
	
	Image zakaz ; // obrazek obramowania planszy
	Image glowa ; // obrazek glowy snake'a
	Image cialo ; // obrazek jednego kawalka ciala snake'a
	
	Jablko jablko = new Jablko() ; 
	Pomarancza pomarancza = new Pomarancza() ; 
	Banan banan = new Banan() ;
	Arbuz arbuz = new Arbuz() ;
	ZlotaMoneta zlotaMoneta = new ZlotaMoneta() ;
	MalaBomba malaBomba = new MalaBomba() ;
	BombaAtomowa bombaAtomowa = new BombaAtomowa() ;
	Przedmiot przedmiot  ;
	Random losuj = new Random() ;
	Timer timer1 ; // timer zliczajacy 5 sekund w przypadku pojawienia sie bomby lub monety na planszy
	boolean przedmiotNaPlanszy = false ;
	ArrayList<Integer> wspOdlamkowBombyX ; // kontener przechowujacy wspolrzedne x wszystkich odlamkow bomby
	ArrayList<Integer> wspOdlamkowBombyY ; // ------------------||-------------- y ------------||-----------
	
	
	/**
	 * Konstruktor ustawia kolor planszy i tworzy zmienne typu ImageIcon pobierajace
	 * z komputera poszczegolne obrazki
	 */
	public Plansza() {
		
		setLayout( null ) ;
		setBackground( Color.BLACK ) ;
		
		// wczytywanie obrazkow z pamieci komputera
		ImageIcon zkz = new ImageIcon(this.getClass().getResource("/obrazki/zakaz.png")) ;
		zakaz = zkz.getImage() ; 
		ImageIcon gl = new ImageIcon(this.getClass().getResource("/obrazki/glowa.jpg")) ;
		glowa = gl.getImage() ; 
		ImageIcon cl = new ImageIcon(this.getClass().getResource("/obrazki/cialo.jpg")) ;
		cialo = cl.getImage() ;
		
	}
	
	/** 
	 * Metoda odziedziczona z JComponent. Rysuje cala grafike podczas gry
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint( g );
		
		// rysowanie obramowania planszy
		for( int i = 0; i < 530; i += 10 ) {
			g.drawImage( zakaz, i, 0, 10, 10, this ) ; 
			g.drawImage( zakaz, i, 362, 10, 10, this ) ;
			
		}
		for( int i = 10; i < 370 ; i += 10 ) {
			g.drawImage( zakaz, 0, i, 10, 10, this ) ; 
			g.drawImage( zakaz, 520, i, 10, 10, this ) ;
			
		}
		
		// rysowanie przedmiotu
		if( przedmiot == malaBomba || przedmiot == bombaAtomowa ) {
			g.drawImage( przedmiot.obrazekPrzedmiotu, przedmiot.polozenieX, przedmiot.polozenieY, 150, 150, this ) ;
			for( int i = 0; i < przedmiot.liczbaOdlamkow; i++ ) g.drawImage( przedmiot.obrazekPrzedmiotu, wspOdlamkowBombyX.get(i), wspOdlamkowBombyY.get(i), 10, 10, this ) ;
		}
		else 
			g.drawImage( przedmiot.obrazekPrzedmiotu, przedmiot.polozenieX, przedmiot.polozenieY, 10, 10, this ) ;
		
		// rysowanie snake'a
		for( int i = 0; i < snake.liczbaCzesci ; i++ ){
			if( i == 0 )
				g.drawImage( glowa, snake.wspX.get(i), snake.wspY.get(i), 10, 10, this ) ;
			else
				g.drawImage(cialo, snake.wspX.get(i), snake.wspY.get(i), 10, 10, this) ;
		}
		
	}
	
	/**
	 * Metoda losuje przedmiot ktory ma sie pojawic na mapie po zebraniu poprzedniego
	 */
	private void losujPrzedmiot() {
		int losowanie ;
		if( trybGry.refOkno.obiektOpcje.wybranyPoczatkujacy )
			losowanie = losuj.nextInt( 6 ) ;
		else
			losowanie = losuj.nextInt( 7 ) ;
		
		if( losowanie == 0 ) przedmiot = jablko ;
		else if( losowanie == 1 ) przedmiot = pomarancza ;
		else if( losowanie == 2 ) przedmiot = banan ;
		else if( losowanie == 3 ) przedmiot = arbuz ;
		else if( losowanie == 4 ) przedmiot = zlotaMoneta ;
		else if( losowanie == 5 ) przedmiot = malaBomba ;
		else if( losowanie == 6  ) przedmiot = bombaAtomowa ;
		
	}
	
	/**
	 * Metoda odpowiedzialna za prawidlowe umieszczenie wylosowanego przedmiotu na mapie
	 */
	private void umiescPrzedmiot() {
		boolean koliduje = true ;
			
			if( przedmiot == malaBomba || przedmiot == bombaAtomowa ) {
				while( koliduje ) {
					przedmiot.ustalWspPrzedmiotu() ;
					for( int i = 0; i < snake.liczbaCzesci; i++ ) { // sprawdza czy bomba nie zostala zaalokowana na snake'u
						if( snake.wspX.get(i)>=przedmiot.polozenieX  && snake.wspX.get(i) <= przedmiot.polozenieX + 140
							&& 	snake.wspY.get(i)>=przedmiot.polozenieY  && snake.wspY.get(i) <= przedmiot.polozenieY + 140 ){
							koliduje = true ;
							break ;
						}
						else koliduje = false ;
					}
				}
				umiescOdlamki() ;
			}
			else {
				while( koliduje ) {
					przedmiot.ustalWspPrzedmiotu() ;
					for( int i = 0; i < snake.liczbaCzesci; i++ ) { // sprawdza czy przedmiot nie zostal zaalokowany na snake'u
						if( snake.wspX.get(i) == przedmiot.polozenieX && snake.wspY.get(i) == przedmiot.polozenieY ){
							koliduje = true ;
							break ;
						}
						else koliduje = false ;
					}
				}
			}
		
		// wlaczenie timera w przypadku pojawienia sie bomby lub monety na planszy
		if( przedmiot == zlotaMoneta || przedmiot == malaBomba || przedmiot == bombaAtomowa ) wlaczOdliczanieTimera() ;
		
	}
	 
	/**
	 * Metoda umieszcza odlamki bomby na planszy
	 */
	private void umiescOdlamki() {
		wspOdlamkowBombyX = new ArrayList<Integer>() ;
		wspOdlamkowBombyY = new ArrayList<Integer>() ;
		boolean koliduje = true ;
		Random losuj = new Random() ;
		int polozenieX = 0 ;
		int polozenieY = 0 ;
		
		for( int i = 0; i < przedmiot.liczbaOdlamkow; i++ ) {
			while( koliduje ) {
				polozenieX = ( losuj.nextInt( 51 ) + 1 ) * 10 ;
				polozenieY = ( losuj.nextInt( 35 ) + 1 ) * 10 ;
				if( polozenieX >= przedmiot.polozenieX  && polozenieX <= przedmiot.polozenieX + 140 		// sprawdza  czy nie zostal
					&& 	polozenieY >= przedmiot.polozenieY  && polozenieY <= przedmiot.polozenieY + 140 ) // zaalokowany na bombie
					continue ;
				for( int k = 0; k < snake.liczbaCzesci; k++ ) { // sprawdza czy nie zostal zaalokowany w przestrzeni kwadratu 20x20
					if( k == 0 ) {								// w otoczeniu glowy snake'a
						if( polozenieX > snake.wspX.get(k) - 20 && polozenieX < snake.wspX.get(k) + 20
							&& polozenieY > snake.wspY.get(k) - 20 && polozenieY < snake.wspY.get(k) + 20 ) break ;
					}
					if( snake.wspX.get(k) == polozenieX && snake.wspY.get(k) == polozenieY ){ // sprawdza czy nie zostal ulokowany
						koliduje = true ;													  // na snake'u
						break ;
					}
					else koliduje = false ;
				}
			}
			wspOdlamkowBombyX.add( polozenieX ) ;
			wspOdlamkowBombyY.add( polozenieY ) ;
			koliduje = true ;
		}
	}
	
	/**
	 * Metoda ta wlacza Timer na czas 5-ciu sekund, po ktorym losowany jest kolejny przedmiot
	 * i nastepnie umieszczany na planszy
	 */
	private void wlaczOdliczanieTimera() {
		przedmiotNaPlanszy = true ; // zasygnalizowanie programowi ze bomba lub moneta jest na planszy
		timer1 = new Timer() ;
		timer1.schedule( new TimerTask() {
			public void run() {
				przedmiotNaPlanszy = false ;
				losujPrzedmiot() ;
				umiescPrzedmiot() ;
			}
		}, 5000 ) ;
	}
	
	/**
	 * Metoda odpowiedzialna za sprawdzenie czy gracz zdobyl przedmiot badz 
	 * natrafil na bombe lub zlota monete, jesli tak to uaktualniany jest 
	 * wynik, losowany kolejny przedmiot i umieszczany na planszy
	 */
	public void sprawdzZdobyciePrzedmiotu() {
		
		if( przedmiot == malaBomba || przedmiot == bombaAtomowa ) {

			if( bombaZdobyta() ) {
					if( przedmiot == malaBomba ) {
						wylaczTimer() ;
						snake.dodajCzesc() ;
						snake.dodajDruga() ;
						snake.aktualizacjaWyniku() ;
						panelGra.wyswietlWynik( snake.wynik ) ;
						losujPrzedmiot() ;
						umiescPrzedmiot() ;
					}
					else if( przedmiot == bombaAtomowa ) {
						wylaczTimer() ;
						snake.aktualizacjaWyniku() ;
						panelGra.wyswietlWynik( snake.wynik ) ;
						trybGry.zakonczenieGry() ;
					}
				
			}
		}
		else {
			if( snake.wspX.get(0) == przedmiot.polozenieX && snake.wspY.get(0) == przedmiot.polozenieY ) {
				
				if( przedmiot == zlotaMoneta ) {
					wylaczTimer() ;
					snake.odejmijCzesc() ;
					snake.aktualizacjaWyniku() ;
					panelGra.wyswietlWynik( snake.wynik ) ;
					losujPrzedmiot() ;
					umiescPrzedmiot() ;
				}
					
				else {
					snake.dodajCzesc() ;
					snake.aktualizacjaWyniku() ;
					panelGra.wyswietlWynik( snake.wynik ) ;
					losujPrzedmiot() ;
					umiescPrzedmiot() ;
				}
			}
		}
	}
	
	/**
	 * Metoda sprawdza czy gracz najechal na odlamek bomby badz na bombe
	 * @return Zwraca prawde jesli najechal, falsz w przeciwnym wypadku
	 */
	private boolean bombaZdobyta() {
		boolean warunek1 = false ;
		boolean warunek2 = false ;
		
		if( snake.wspX.get(0)>=przedmiot.polozenieX  && snake.wspX.get(0) <= przedmiot.polozenieX + 140
			&& 	snake.wspY.get(0)>=przedmiot.polozenieY  && snake.wspY.get(0) <= przedmiot.polozenieY + 140 )
			warunek1 = true ;
		for( int i = 0; i < przedmiot.liczbaOdlamkow; i++ ) {
			if( snake.wspX.get(0).equals(wspOdlamkowBombyX.get(i)) && snake.wspY.get(0).equals(wspOdlamkowBombyY.get(i) ) )  {
				warunek2 = true ;
				break ;
			}
		}
		return warunek1 || warunek2 ;
	}
	
	/**
	 * Metoda sprawdza, czy gracz nie wyjechal poza plansze lub czy nie uderzyl w samego siebie
	 */
	public void sprawdzKolizje() {
		
		// sprawdza czy gracz nie uderzyl w sciane planszy
		if( (snake.wspX.get(0) > 510 || snake.wspX.get(0) < 10) || (snake.wspY.get(0) > 350 || snake.wspY.get(0) < 10) ) {
			if( przedmiotNaPlanszy ) {
				wylaczTimer() ;
			}
			trybGry.zakonczenieGry() ;
		}
		for( int i = 1; i < snake.liczbaCzesci; i++ ) { // sprawdza czy gracz nie uderzyl w samego siebie

			if(((snake.wspX.get(i).equals(snake.wspX.get(0))) && (snake.wspY.get(i).equals(snake.wspY.get(0))))) {
				if( przedmiotNaPlanszy ) {
					wylaczTimer() ;
				}
				trybGry.zakonczenieGry() ;
			}
			
		}
	}

	/**
	 * Metoda wylacza Timer odliczajacy 5 sekund, ktory jest wlaczany przy pojawieniu sie bomby
	 * lub zlotej monety na planszy 
	 */
	private void wylaczTimer() {
		timer1.cancel() ;
		timer1.purge() ;
	}
}