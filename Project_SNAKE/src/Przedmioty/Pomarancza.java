import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca owoc pomarancza.
 * Zawiera informacje o bonusie punktowym pomaranczy
 * @author Ksawery
 */
public class Pomarancza extends Przedmiot {
	
	int bonusPomarancza = 10 ; // bonus jaki daje pomarancza

	public Pomarancza() {
		
		ImageIcon pom = new ImageIcon(this.getClass().getResource("/obrazki/pomarancza.jpg")) ; // wczytywanie obrazka pomaranczy
		obrazekPrzedmiotu = pom.getImage() ;
		liczbaOdlamkow = 0 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne pomaranczy.
	 * Moze ono sie pojawic w okolicach miedzy srodkiem a obrzezem planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		int bokRamki = losuj.nextInt(4) ;
		
		if( bokRamki == 0 ) {
			polozenieX = ( losuj.nextInt( 41 ) + 6 ) * 10 ; 
			polozenieY = ( losuj.nextInt( 5 ) + 6 ) * 10 ; 
		}
		else if( bokRamki == 1 ) {
			polozenieX = ( losuj.nextInt( 5 ) + 42 ) * 10 ;
			polozenieY = ( losuj.nextInt( 25 ) + 6 ) * 10 ;
		}
		else if( bokRamki == 2 ) {
			polozenieX = ( losuj.nextInt( 41 ) + 6 ) * 10 ;
			polozenieY = ( losuj.nextInt( 5 ) + 26 ) * 10 ;
		}
		else if( bokRamki == 3 ) {
			polozenieX = ( losuj.nextInt( 5 ) + 6 ) * 10 ;
			polozenieY = ( losuj.nextInt( 25 ) + 6 ) * 10 ;
		}
		
	}

	/**
	 * Metoda zwraca bonus punktowy, jaki daje pomarancza
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return bonusPomarancza ;
	}

}
