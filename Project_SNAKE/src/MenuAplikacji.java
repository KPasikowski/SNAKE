import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

/**
 * Klasa reprezentujaca glowne okno aplikacji
 * znajduje się w niej menu calej gry
 * @author Ksawery
 */
public class MenuAplikacji extends JFrame {
	
 	/**	panel wyswietlajacy glowny obrazek gry */
	JPanel panelPoczatkowy = new JPanel() ;
	/** panel menu  */
	JPanel menu = new JPanel() ;
	JButton graj = new JButton( "graj" ) ; 
	JButton opcje = new JButton( "opcje" ) ;
	JButton wyniki = new JButton( "wyniki" ) ;
	JButton instrukcja = new JButton( "instrukcja" ) ;
	JLabel[] separatory = new JLabel[4] ;
	JLabel obrazSnake = new JLabel( new ImageIcon("snake.jpg") ) ;
	TrybGry trybGry ;
	Opcje obiektOpcje ;
	WynikiLogowanie obiektWyniki ;
	Instrukcja obiektInstrukcja ;
	
	public MenuAplikacji() throws IOException{
		
		super("Snake") ;
		setLayout( null ) ;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4-getSize().width/4, dim.height/4-getSize().height/4) ;
		menu.setLayout( new GridLayout( 8, 1, 10, 2 ) ) ;
		panelPoczatkowy.setLayout( new BorderLayout() ) ;
		for( int i = 0; i < 4; i++ ) {
			separatory[i] = new JLabel(".  .  .  .  .  .  .  .  .  .  .  .  ") ;
			separatory[i].setHorizontalAlignment( JLabel.CENTER ) ;
			separatory[i].setVerticalAlignment( JLabel.CENTER ) ;
		}
		trybGry = new TrybGry( this ) ;
		obiektOpcje = new Opcje( this ) ;
		obiektWyniki = new WynikiLogowanie( trybGry, obiektOpcje ) ;
		setFocusable( true ) ;
		rysujOkno() ;
		dodajSluchaczy() ;
		
	}
	
	/**
	 * dodaje sluchaczy do wszystkich przyciskow znajdujacych się w panelu menu
	 * oraz przycisku "exit" okna aplikacji
	 */
	public void dodajSluchaczy() {
		
		graj.addActionListener( new ActionListener() { 
			public void actionPerformed( ActionEvent e ) { // wyswietla panel logowania 
				obiektWyniki.wyczyscOkno() ;
				obiektWyniki.logowanie() ;
			}
		}) ;
		opcje.addActionListener( new ActionListener() { // wyswietla opcje gry
			public void actionPerformed( ActionEvent e ) {
				remove( menu ) ;
				repaint() ;
				add( obiektOpcje ) ;
				setVisible(true) ;
			}
		}) ;
		wyniki.addActionListener( new ActionListener() { // wyswietla osobne okno z top 10 wynikow
			public void actionPerformed( ActionEvent e ) {
				obiektWyniki.wynikiWyswietl() ;
			}
		}) ;
		instrukcja.addActionListener( new ActionListener() { // wyswietla instrukcje gry
			public void actionPerformed( ActionEvent e ) {
				obiektInstrukcja = new Instrukcja() ;
				obiektInstrukcja.pokazInstrukcje() ;
			}
		}) ;
		addWindowListener( new WindowAdapter() { // przy zamknieciu aplikacji zapisuje wyniki do plikow txt
			public void windowClosing( WindowEvent e ) {
				try {
					obiektWyniki.zapisDoPliku() ;
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				e.getWindow().dispose();
			}
		});
	}
	
	/**
	 * Metoda ta jest wywolywana po nacisnieciu przycisku "wroc" w opcjach lub w trybie gry. 
	 * Zdejmuje wszystkie komponenty znajdujace sie aktualnie w ramce aplikacji
	 * i wywoluje metodę rysujaca GUI menu aplikacji
	 */
	public void wroc() {
		getContentPane().removeAll() ;
		getContentPane().repaint();
		rysujOkno() ;
	}

	/**
	 * Metoda ta rysuje GUI menu aplikacji 
	 */
	public void rysujOkno() {
		menu.add( separatory[0] ) ;
		menu.add(  graj ) ;
		menu.add( separatory[1] ) ;
		menu.add( opcje ) ;
		menu.add( separatory[2] ) ;
		menu.add( wyniki ) ;
		menu.add( separatory[3] ) ;
		menu.add( instrukcja ) ;
		panelPoczatkowy.add( obrazSnake ) ;
		menu.setBounds( 534, 0, 150, 372 ) ;
		panelPoczatkowy.setBounds( 0, 0, 534, 372 ) ;
		add( menu ) ;
		add( panelPoczatkowy ) ;
		
	}
	
}
