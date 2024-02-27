/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Cegelskis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import studijosKTU.*;

/**
 *
 * @author zygis
 */
public class VeiksmaiSuKnygomis{
    public final ListKTUx<Knyga> knyguSarasas = new ListKTUx<>(new Knyga());
    public ListKTUx<Knyga> newTemp = new ListKTUx<>(new Knyga());
    
    public void atrinktiPagalKategorija(String kategorija, JTextArea ta){
        newTemp = knyguSarasas.clone();
        for(int i = 0; i < newTemp.size(); i++){
            Knyga book = newTemp.get(i);
            if(book.getCategory().startsWith(kategorija)){
                ta.append(book.toString() + "\n");
            }
        }
    }
    public boolean atrinktiPagalKategorijaLentele(DefaultTableModel lentelėsModelis, String kategorija) {
                newTemp = knyguSarasas.clone();
		for(int i = 0; i < newTemp.size(); i++){
                    Knyga book = newTemp.get(i);
                    if(book.getCategory().startsWith(kategorija)){
                        lentelėsModelis.addRow(new Object[] {book.getName(), 
                        book.getCategory(), book.getPrice(), book.getAuthor(),
                        book.getPageNumber(), book.getPrintYear()});
                    }
                }
		return lentelėsModelis.getRowCount() > 0;
	}
    public ListKTUx<Knyga> atrinktiPagalMetus(int nuoKada, int ikiKada){
        ListKTUx<Knyga> temp = new ListKTUx<>(new Knyga());
        Iterator<Knyga> it = knyguSarasas.iterator();
        while(it.hasNext()){
            Knyga book = it.next();
            if(book.getPrintYear() >= nuoKada && book.getPrintYear() <= ikiKada){
                temp.add(book);
            }
        }
        return temp;
    }
    public void rikiuotiPagalPuslapiuSkaiciuKaina(){
        Knyga book = new Knyga();
        Object[] temp = knyguSarasas.toArray();
        List<Knyga> knygos;
        knygos = new ArrayList<>();
        for (Object temp1 : temp) {
            knygos.add((Knyga) temp1);
        }
        Collections.sort(knygos, book.pagalPuslapiuSkaiciu.thenComparing(book.pagalKaina));
        knyguSarasas.clear();
        for(int i = 0; i < knygos.size(); i++){
            Knyga c = knygos.get(i);
            knyguSarasas.add(c);
        }
    }
    public ListKTUx<Knyga> maxPriceBooks(){
       ListKTUx<Knyga> temporary = new ListKTUx(new Knyga());
       double maxPrice = 0;
       for(Knyga a : knyguSarasas){
           double price = a.getPrice();
           if(price > maxPrice){
               if(price > maxPrice){
                   temporary.clear();
                   maxPrice = price;
               }
               temporary.add(a);
           }
       }
       return temporary;
    }
    
    public String loadAndPrint(File fName, JTextArea ta) {
		String klaidosKodas = null;
                //Ar vykdomas cath blokas jei vykdomas try blokas?
		try {
			knyguSarasas.clear();
                    try (BufferedReader fReader = new BufferedReader(new FileReader(fName))) {
                        String line;
                        
                        ta.append("     D u o m e n y s iš failo <" + fName.getName() + ">\n\n");
                        
                        while ((line = fReader.readLine()) != null) {
                            knyguSarasas.add(new Knyga(line));
                        }
                        knyguSarasas.sortJava();
                        for(Knyga a : knyguSarasas){
                            ta.append(a.toString() + "\n");
                        }
                    }
		} catch (IOException e) {
			klaidosKodas = "Failo " + fName.getName() + " skaitymo klaida";
		}
		return klaidosKodas;
	}
    @Override
    public String toString(){
        String kolekcija = "";
        for(int i = 0; i < knyguSarasas.size(); i++){
            Knyga book = knyguSarasas.get(i);
            kolekcija += book.toString() + "\n";
        }
        return kolekcija;
    }
    
}
