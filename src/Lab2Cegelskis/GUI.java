/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Cegelskis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author zygis
 */
public class GUI extends JFrame{
    private final JMenuBar meniuBaras = new JMenuBar();
    private Container konteineris = new Container();
    private final JTextArea textArea = new JTextArea(20,50);
    private final JScrollPane scrollPane = new JScrollPane(textArea);
    private final JLabel antraste = new JLabel("Rezultatai");
    private final JPanel panel = new JPanel();
    private JMenuItem puslapiai;
    private JMenuItem kategorija;
    VeiksmaiSuKnygomis veiksmaiSuKnygomis = new VeiksmaiSuKnygomis();
    private JLabel perspejimas = new JLabel("Tai tik mazas GUI pavyzdys");
    
    public GUI(){
        Locale.setDefault(Locale.US);
        Font f = new Font("Courier New", Font.PLAIN, 12);
        textArea.setFont(f);
        meniuIdiegimas();
        //puslapiai.setEnabled(false); //neaktyvi, nes dar nera duomenu(nenuskaitytas failas)
        //kategorija.setEnabled(false); //neaktyvi
    }
    
    private void meniuIdiegimas(){
        setJMenuBar(meniuBaras);
        JMenu failai = new JMenu("Failai");
        meniuBaras.add(failai);
        JMenu bandymaiSuKnygomis = new JMenu("Bandymai su knygomis");
        bandymaiSuKnygomis.setMnemonic('a');
        meniuBaras.add(bandymaiSuKnygomis);
        JMenu pagalba = new JMenu("Pagalba");
        meniuBaras.add(pagalba);
        
        //Grupe Failai
        JMenuItem skaityti = new JMenuItem("Skaityti is failo...");
        failai.add(skaityti);
        skaityti.addActionListener(this::skaitytiFaila);
        JMenuItem baigti = new JMenuItem("Pabaiga");
        failai.add(baigti);
        baigti.setMnemonic('b'); //alt + b
        baigti.addActionListener((ActionEvent e) -> System.exit(0));
        
        //Grupe bandymai su knygomis
        kategorija = new JMenuItem("Atrinkti pagal kategorija");
        bandymaiSuKnygomis.add(kategorija);
        kategorija.addActionListener(this::atrankaPagalKategorija);
        
        puslapiai = new JMenuItem("Rikiuoti pagal puslapiu skaiciu ir kaina");
        bandymaiSuKnygomis.add(puslapiai);
        puslapiai.addActionListener(new ActionListenerImp1());
        
        JMenuItem trinimas = new JMenuItem("Trinti pagal indeksa");
        bandymaiSuKnygomis.add(trinimas);
        trinimas.addActionListener(this::removeElement);
        
        JMenuItem arYra = new JMenuItem("Patikrinti ar yra knyga");
        bandymaiSuKnygomis.add(arYra);
        arYra.addActionListener(this::doesContain);
        
        JMenuItem pakeisti = new JMenuItem("Pakeisti elementa kitu");
        bandymaiSuKnygomis.add(pakeisti);
        pakeisti.addActionListener(this::setNew);
        
        JMenuItem pridetiIViduri = new JMenuItem("Prideti i viduri");
        bandymaiSuKnygomis.add(pridetiIViduri);
        pridetiIViduri.addActionListener(this::addAtMid);
        
        //PAGALBA
        JMenuItem skaiciuotuvas = new JMenuItem("Skaiciuotuvas");
        pagalba.add(skaiciuotuvas);
        skaiciuotuvas.addActionListener(this:: veiksmaiGreitojiPagalba);
        
        panel.setLayout(new BorderLayout());
        panel.add(antraste, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        perspejimas.setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().add(perspejimas); //po failo skaitymo zyme perspejimas isnyks
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kad veiktu kryziukas uzdarant langa
    }
    
    public void skaitytiFaila(ActionEvent e){
        JFileChooser fc = new JFileChooser(".");
        fc.setDialogTitle("Atidaryti faila skaitymui");
        fc.setApproveButtonText("Atidaryti");
        int rez = fc.showOpenDialog(GUI.this);
        if(rez == JFileChooser.APPROVE_OPTION){
            if(!panel.isShowing()){
                //Jei JPanel komponentas dar neidetas i JFrame
                konteineris = getContentPane();
                konteineris.remove(perspejimas);
                konteineris.setLayout(new FlowLayout());
                konteineris.add(panel);               
            }
            File f1 = fc.getSelectedFile();
            String klaidosKodas = veiksmaiSuKnygomis.loadAndPrint(f1, textArea);
            if(klaidosKodas != null){
                JOptionPane.showMessageDialog(GUI.this, klaidosKodas, "Skaitymas - rasymas",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            puslapiai.setEnabled(true);
            kategorija.setEnabled(true);
                        
        } else if(rez == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(GUI.this, "Skaitymo atsisakyta(paspaustas esc)",
                    "Skaitymas - rasymas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void atrankaPagalKategorija(ActionEvent e){
        String category = JOptionPane.showInputDialog(GUI.this, "Iveskite kategorija: ", 
                "Tekstas", JOptionPane.WARNING_MESSAGE);
        
//        if(kategorija = null){
//            return;
//        }


       textArea.append("\n Atrinktos" + category + "kategorijos knygoos." + System.lineSeparator());
       veiksmaiSuKnygomis.atrinktiPagalKategorija(category, textArea);
       
       JFrame frame2 = new JFrame();
       JPanel panel2 = new JPanel();
       panel.setLayout(new BorderLayout());
       JTable lentele = new JTable();
       String stulpeliuVardai[] = {"Pavadinimas", "Kategorija", "Kaina", "Autorius",
           "Puslapiu skaicius", "Spausdinimo metai"};
       DefaultTableModel lentelesModelis = (DefaultTableModel) lentele.getModel();
       JScrollPane juosta = new JScrollPane(lentele);
       JLabel a = new JLabel("Atrinktos" + category + "kategorijos knygos", SwingConstants.CENTER);
       panel2.add(a, BorderLayout.NORTH);
       panel2.add(juosta, BorderLayout.CENTER);
       
       lentelesModelis.setColumnIdentifiers(stulpeliuVardai);
       boolean b = veiksmaiSuKnygomis.atrinktiPagalKategorijaLentele(lentelesModelis, category);
       TableRowSorter rikiuoklis = new TableRowSorter(lentelesModelis);
       lentele.setRowSorter(rikiuoklis);
       
       if(b){
           frame2.add(panel2);
           frame2.setSize(400,450);
           Dimension dydis = GUI.this.getSize();
           Point vieta = GUI.this.getLocation();
           frame2.setLocation((vieta.x + dydis.width / 2) - frame2.getSize().width / 2,
					(vieta.y + dydis.height / 2) - frame2.getSize().height / 2);
			frame2.setVisible(true);
       }else{
           JOptionPane.showMessageDialog(GUI.this, "<" + category + "> kategorijos knygu nerasta",
                   "Atranka", JOptionPane.WARNING_MESSAGE);
       } 
    }
    
    public void removeElement(ActionEvent e){
        int first = 0; //Nes masyvas visada pradedamas nuo 0 elemento
        int last = veiksmaiSuKnygomis.knyguSarasas.size() - 1;
        String indString = JOptionPane.showInputDialog(GUI.this, "Iveskite norimo istrinti elemento indeksa, kai"
                + "indeksu intervalas yra nuo: " + "" + first + " iki" + "" + last, "Tekstas", JOptionPane.WARNING_MESSAGE);
        int ind = Integer.parseInt(indString);
        veiksmaiSuKnygomis.knyguSarasas.remove(ind);
        textArea.append("\n          Sarasas po isrinimo: \n");
        for(Knyga book : veiksmaiSuKnygomis.knyguSarasas){
            textArea.append(book.toString() + "\n");
        }
    }
    public void doesContain(ActionEvent e){
        String bookName = JOptionPane.showInputDialog(GUI.this, "Iveskite knyga, kuria norite patikrinti ar yra sarase: ", 
                "Tekstas", JOptionPane.WARNING_MESSAGE);
        boolean a = veiksmaiSuKnygomis.knyguSarasas.contains(bookName);
        if(a){
            JOptionPane.showMessageDialog(GUI.this, "Knyga pagal pavadinima rasta",
                    "Ar yra tokia knyga?", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(GUI.this, "Knyga pagal pavadinima nerasta",
                    "Ar yra tokia knyga?", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void setNew(ActionEvent e){
        String line = JOptionPane.showInputDialog(GUI.this, "Iveskite naujos knygos duomenis: \n" +
                "Knygos pavadinimas, kategorija, kaina, autorius, puslapiu skaicius ir spausdinimo metai",
                "Tekstas", JOptionPane.INFORMATION_MESSAGE);
        String line2 = JOptionPane.showInputDialog(GUI.this, "Iveskite knygos indeksa, kuria norite \n"+
                "sukeisti vietomis su naujai ivesta", "Tekstas", JOptionPane.INFORMATION_MESSAGE);
        int ind = Integer.parseInt(line2);
        Knyga book = new Knyga(line);
        veiksmaiSuKnygomis.knyguSarasas.set(ind, book);
        textArea.append("\n          Sarasas po pakeitimo:\n");
            for(int i = 0; i < veiksmaiSuKnygomis.knyguSarasas.size(); i++){
                Knyga book2 = veiksmaiSuKnygomis.knyguSarasas.get(i);
                textArea.append(book2.toString() + "\n");
            }
    }
    
    public void addAtMid(ActionEvent e){
        String line = JOptionPane.showInputDialog(GUI.this, "Iveskite dar viena knyga kuria norite prideti i saraso viduri:\n" +
                "Knygos pavadinimas, kategorija, kaina, autorius, puslapiu skaicius ir spausdinimo metai", 
                "tekstas", JOptionPane.INFORMATION_MESSAGE);
        Knyga book = new Knyga(line);
        veiksmaiSuKnygomis.knyguSarasas.addMid(book);
        textArea.append("\n          Sarasas kai i vidury buvo pridetas elementas:\n");
            for(int i = 0; i < veiksmaiSuKnygomis.knyguSarasas.size(); i++){
                Knyga book2 = veiksmaiSuKnygomis.knyguSarasas.get(i);
                textArea.append(book2.toString() + "\n");
            }
    }
    
    public void veiksmaiGreitojiPagalba(ActionEvent e) {
		// Galimos programos vardo (exe failo) suformavimo alternatyvos:
		// Pirma:
		//String programa = System.getenv("windir") + File.separatorChar +
		//					 "system32" + File.separatorChar + "calc.exe";

		// Kita:
		String programa = "C:\\WINDOWS\\system32\\calc.exe";
		try {
			Process p = Runtime.getRuntime().exec(programa);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(GUI.this,
					"Vykdomasis failas <" + programa + "> nerastas",
					"Klaida", JOptionPane.ERROR_MESSAGE);
		}
	}
    
    
    public static void main(String[] args){
        GUI meniuSasaja = new GUI();
        meniuSasaja.setSize(500,400);
        meniuSasaja.setLocation(200,200);
        meniuSasaja.setTitle("Vartotojo sasajos su meniu pavyzdys");
        meniuSasaja.setVisible(true);
    }
    
    private class ActionListenerImp1 implements ActionListener{
        
        public ActionListenerImp1() {}
        
        @Override
        public void actionPerformed(ActionEvent e){
            veiksmaiSuKnygomis.rikiuotiPagalPuslapiuSkaiciuKaina();
            textArea.append("\n          Surikiuotas pradinis sarasas:\n");
            for(int i = 0; i < veiksmaiSuKnygomis.knyguSarasas.size(); i++){
                Knyga book = veiksmaiSuKnygomis.knyguSarasas.get(i);
                textArea.append(book.toString() + "\n");
            }
        }
    }
}
