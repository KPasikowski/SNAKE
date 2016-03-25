import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Klasa reprezentujaca owoc jablko.
 * Zawiera informacje o bonusie punktowym jablka
 * @author Ksawery
 */
public class Jablko extends Przedmiot { 

	int bonusJablko = 5 ; // bonus jaki daje jablko
	
	public Jablko() {
		
		ImageIcon jb = new ImageIcon(this.getClass().getResource("/obrazki/jablko.png")) ; // wczytywanie obrazka jablka
		obrazekPrzedmiotu = jb.getImage() ;
		liczbaOdlamkow = 0 ;
		ustalPoczatkoweWspOwocu() ;
		
	}
	
	public void ustalPoczatkoweWspOwocu() {
		polozenieX = 400 ;
		polozenieY = 300 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne jablka.
	 * Moze ono sie pojawic jedynie w okolicach srodka planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		
		polozenieX = ( losuj.nextInt( 31 ) + 11 ) * 10 ;
		polozenieY = ( losuj.nextInt( 15 ) + 11 ) * 10 ;
		
	}

	/**
	 * Metoda zwraca bonus punktowy, jaki daje jablko
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return bonusJablko ;
		
	}



}
