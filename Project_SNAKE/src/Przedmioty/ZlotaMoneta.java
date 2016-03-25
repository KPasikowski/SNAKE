import java.util.Random;

import javax.swing.ImageIcon;

/**
* Klasa reprezentujaca zlota monete.
* Zawiera informacje o bonusie punktowym, jaki daje zlota moneta
* @author Ksawery
*/
public class ZlotaMoneta extends Przedmiot {
	
	int bonusZlotaMoneta = 30 ; // bonus jaki daje zlota moneta

	public ZlotaMoneta() {
		
		ImageIcon zm = new ImageIcon(this.getClass().getResource("/obrazki/zlotaMoneta.jpg")) ; // wczytywanie obrazka zlotej monety
		obrazekPrzedmiotu = zm.getImage() ;
		liczbaOdlamkow = 0 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne zlotej monety.
	 * Moze ona sie pojawic w kazdym miejscu na planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		polozenieX = ( losuj.nextInt( 51 ) + 1 ) * 10 ;
		polozenieY = ( losuj.nextInt( 35 ) + 1 ) * 10 ;
		
	}

	/**
	 * Metoda zwraca bonus punktowy, jaki daje zlota moneta
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return bonusZlotaMoneta ;
	}

}
