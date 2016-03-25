import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca owoc arbuz.
 * Zawiera informacje o bonusie punktowym arbuza
 * @author Ksawery
 */
public class Arbuz extends Przedmiot {

	int bonusArbuz = 20 ; // bonus jaki daje arbuz
	
	public Arbuz() {
		
		ImageIcon arb = new ImageIcon(this.getClass().getResource("/obrazki/arbuz.jpg")) ; // wczytywanie obrazka arbuza
		obrazekPrzedmiotu = arb.getImage() ;
		liczbaOdlamkow = 0 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne arbuza.
	 * Moze on sie pojawic jedynie na obrzezach planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		int bokRamki = losuj.nextInt(4) ;
		
		if( bokRamki == 0 ) {
			polozenieX = ( losuj.nextInt( 51 ) + 1 ) * 10 ; 
			polozenieY = ( losuj.nextInt( 2 ) + 1 ) * 10 ;
		}
		else if( bokRamki == 1 ) {
			polozenieX = ( losuj.nextInt( 2 ) + 50 ) * 10 ;
			polozenieY = ( losuj.nextInt( 35 ) + 1 ) * 10 ;
		}
		else if( bokRamki == 2 ) {
			polozenieX = ( losuj.nextInt( 51 ) + 1 ) * 10 ;
			polozenieY = ( losuj.nextInt( 2 ) + 34 ) * 10 ;
		}
		else if( bokRamki == 3 ) {
			polozenieX = ( losuj.nextInt( 2 ) + 1 ) * 10 ;
			polozenieY = ( losuj.nextInt( 35 ) + 1 ) * 10 ;
		}
		
	}

	
	/**
	 * Metoda zwraca bonus punktowy, jaki daje arbuz
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return bonusArbuz ;
	}

}
