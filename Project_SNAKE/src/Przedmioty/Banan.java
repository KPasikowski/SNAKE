import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca owoc banan.
 * Zawiera informacje o bonusie punktowym banana
 * @author Ksawery
 */
public class Banan extends Przedmiot {
	
	int bonusBanan = 15 ; // bonus jaki daje banan

	public Banan() {
		
		ImageIcon ban = new ImageIcon(this.getClass().getResource("/obrazki/banan.jpg")) ; // wczytywanie obrazka banana
		obrazekPrzedmiotu = ban.getImage() ;
		liczbaOdlamkow = 0 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne banana.
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		int bokRamki = losuj.nextInt(4) ;
		
		if( bokRamki == 0 ) {
			polozenieX = ( losuj.nextInt( 47 ) + 3 ) * 10 ; 
			polozenieY = ( losuj.nextInt( 3 ) + 3 ) * 10 ;
		}
		else if( bokRamki == 1 ) {
			polozenieX = ( losuj.nextInt( 3 ) + 47 ) * 10 ;
			polozenieY = ( losuj.nextInt( 31 ) + 3 ) * 10 ;
		}
		else if( bokRamki == 2 ) {
			polozenieX = ( losuj.nextInt( 47 ) + 3 ) * 10 ;
			polozenieY = ( losuj.nextInt( 3 ) + 31 ) * 10 ;
		}
		else if( bokRamki == 3 ) {
			polozenieX = ( losuj.nextInt( 3 ) + 3 ) * 10 ;
			polozenieY = ( losuj.nextInt( 31 ) + 3 ) * 10 ;
		}
		
		
	}
	
	/**
	 * Metoda zwraca bonus punktowy, jaki daje banan
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return bonusBanan ;
	}

}
