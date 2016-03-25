import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Klasa ta reprezentuje panel opcji gry
 * @author Ksawery
 */
public class Opcje extends JPanel {
	
	MenuAplikacji refOkno ; // referencja do menu aplikacji
	JLabel wybierz = new JLabel("Wybierz poziom") ; // przycisk potwierdzajacy wybranie poziomu
	JRadioButton poczatkujacy = new JRadioButton("początkujący") ;
	JRadioButton zaawansowany = new JRadioButton("zaawansowany") ;
	ButtonGroup radioGroup = new ButtonGroup() ; // tworzy grupe dwoch JRadioButtons poczatkujacy i zaawansowany
	JButton wroc = new JButton("wróc") ;
	boolean wybranyPoczatkujacy = true ; // zmienna okreslajaca czy zostal wybrany poziom poczatkujacy
	boolean wybranyZaawansowany = false ; // --------------------||---------------------- zaawansowany
	

	/**
	 * Przekazuje referencje do menu aplikacji, dodaje sluchacza zdarzen akcji 
	 * do przyciskow oraz tworzy GUI panelu opcji
	 * @param o
	 */
	public Opcje( MenuAplikacji o ) {
		refOkno = o ;
		zbudujPanelOpcji() ;
		dodajSluchaczy() ;
		
	}
	
	/**
	 * Metoda tworzy GUI panelu opcji
	 */
	private void zbudujPanelOpcji() {
		setLayout( new GridLayout( 4, 1, 10, 20 ) ) ;
		setBounds( 534, 0, 150, 372 ) ; 
		wybierz.setHorizontalAlignment( JLabel.CENTER ) ;
		wybierz.setVerticalAlignment( JLabel.CENTER ) ;
		poczatkujacy.setSelected( true ) ;
		add( wybierz ) ;
		add( poczatkujacy ) ;
		add( zaawansowany ) ;
		add( wroc ) ;
		radioGroup.add( poczatkujacy ) ;
		radioGroup.add( zaawansowany ) ;
	}
	
	/**
	 * Metoda ta dodaje sluchacza zdarzen akcji do wszystkich przyciskow na panelu 
	 */
	private void dodajSluchaczy() {
		poczatkujacy.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				wybranyPoczatkujacy = true ;
				wybranyZaawansowany = false ;
			}
		}) ;
		zaawansowany.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				wybranyPoczatkujacy = false ;
				wybranyZaawansowany = true ;
			}
		}) ;
		wroc.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				refOkno.wroc() ;
			}
		}) ;
	}
	
}
