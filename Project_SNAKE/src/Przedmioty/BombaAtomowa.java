import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca bombe atomowa.
 * Zawiera informacje o karze punktowej ( w procentach ) jaka daje bomba atomowa
 * @author Ksawery
 */
public class BombaAtomowa extends Przedmiot {
	
	int karaBombaAtomowa = 20 ; // kara w procentach jaka daje bomba atomowa

	public BombaAtomowa() {
		
		ImageIcon ba = new ImageIcon(this.getClass().getResource("/obrazki/bombaAtomowa.jpg")) ; // wczytywanie obrazka bomby atomowej
		obrazekPrzedmiotu = ba.getImage() ;
		liczbaOdlamkow = 30 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne bomby atomowej.
	 * Moze ona sie pojawic w kazdym miejscu na planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		polozenieX = ( losuj.nextInt( 37 ) + 1 ) * 10 ;
		polozenieY = ( losuj.nextInt( 21 ) + 1 ) * 10 ;
		
	}

	/**
	 * Metoda zwraca kare procentowa, jaka daje bomba atomowa
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return karaBombaAtomowa ;
	}

	
}
