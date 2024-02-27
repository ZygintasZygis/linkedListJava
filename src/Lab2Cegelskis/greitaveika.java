/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Cegelskis;

import Laboras2demo.Automobilis;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import studijosKTU.*;

public class greitaveika {

	Knyga[] knyguBaze1;
	ListKTU<Knyga> knyguList = new ListKTU<>();
	Random ag = new Random();  // atsitiktinių generatorius
	int[] tiriamiKiekiai = {2_000, 4_000, 8_000, 16_000};
//    pabandykite, gal Jūsų kompiuteris įveiks šiuos eksperimentus
//    paieškokite ekstremalaus apkrovimo be burbuliuko metodo
//    static int[] tiriamiKiekiai = {10_000, 20_000, 40_000, 80_000};

	void generuotiKnygas(int kiekis) {
		String[][] am = { // galimų automobilių markių ir jų modelių masyvas
			{"Knyga1", "Kategorija1", "50", "Autorius1", "100", "220"},
			{"Knyga2", "Kategorija2", "55", "Autorius2", "400", "2022"},
			{"Knyga3", "Kategorija3", "30", "Autorius3", "300", "2017"},
			{"Knyga4", "Kategorija4", "10", "Autorius4", "80", "2015"},
			{"Knyga5", "Kategorija5", "60", "Autorius5", "500", "2005"},
			{"Knyga6", "Kategorija6", "5",  "Autorius6", "600", "2010"},
                        {"Knyga7", "Kategorija7", "2",  "Autorius7", "800", "2011"}
		};
		knyguBaze1 = new Knyga[kiekis];
		ag.setSeed(2016);
		for (int i = 0; i < kiekis; i++) {
                        String n = null;
                        String cat = null;
                        double price = 0;
                        String aut = null;
                        int page = 0;
                        int year = 0;
                        int j = 0;
                        while(kiekis != 0){
                            n = am[i][j];
                            System.out.println("" + j);
                            j++;
                            cat = am[i][j];
                            System.out.println("" + j);
                            j++;
                            price = Double.parseDouble(am[i][j]);
                            System.out.println("" + j);
                            j++;
                            aut = am[i][j];
                            System.out.println("" + j);
                            j++;
                            page = Integer.parseInt(am[i][j]);
                            System.out.println("" + j);
                            j++;
                            year = Integer.parseInt(am[i][j]);
                            System.out.println("" + j);
                            kiekis--;
                        }                                              
			knyguBaze1[i] = new Knyga(n, cat, 10 + ag.nextDouble(20), aut, 
                                100 + ag.nextInt(800), 1990 + ag.nextInt(35));
					
		}
		Collections.shuffle(Arrays.asList(knyguBaze1));
		knyguList.clear();
		for (Knyga a : knyguBaze1) {
			knyguList.add(a);
		}
	}

	void paprastasTyrimas(int elementųKiekis) {
		// Paruošiamoji tyrimo dalis
                Knyga book = new Knyga();
		long t0 = System.nanoTime();
		generuotiKnygas(elementųKiekis);
		ListKTU<Knyga> knyguList2 = knyguList.clone();
		ListKTU<Knyga> knyguList3 = knyguList.clone();
		ListKTU<Knyga> knyguList4 = knyguList.clone();
                ListKTU<Knyga> knyguList5 = knyguList.clone();
		long t1 = System.nanoTime();
		System.gc();
		System.gc();
		System.gc();
		long t2 = System.nanoTime();

		//  Greitaveikos bandymai ir laiko matavimai
		knyguList.sortJava();
		long t3 = System.nanoTime();
		knyguList2.sortJava(book.pagalPuslapiuSkaiciu);
		long t4 = System.nanoTime();
		knyguList3.sortBuble();
		long t5 = System.nanoTime();
		knyguList4.sortBuble(book.pagalPuslapiuSkaiciu);
		long t6 = System.nanoTime();
                knyguList5.sortSelection();
                long t7 = System.nanoTime();
		Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", elementųKiekis,
				(t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
				(t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9, (t7 - t6) / 1e9);
	}

	void tyrimoPasirinkimas() {
		long memTotal = Runtime.getRuntime().totalMemory();
		Ks.oun("memTotal= " + memTotal);
		// Pasižiūrime kaip generuoja automobilius (20) vienetų)
		generuotiKnygas(20);
		for (Knyga a : knyguList) {
			Ks.oun(a);
		}
		Ks.oun("1 - Pasiruošimas tyrimui - duomenų generavimas");
		Ks.oun("2 - Pasiruošimas tyrimui - šiukšlių surinkimas");
		Ks.oun("3 - Rūšiavimas sisteminiu greitu būdu be Comparator");
		Ks.oun("4 - Rūšiavimas sisteminiu greitu būdu su Comparator");
		Ks.oun("5 - Rūšiavimas List burbuliuku be Comparator");
		Ks.oun("6 - Rūšiavimas List burbuliuku su Comparator");
                Ks.oun("7 - Rūšiavimas List Selection metodu pagal spausdinimo metus");
		Ks.ouf("%6d %7d %7d %7d %7d %7d %7d %7d \n", 0, 1, 2, 3, 4, 5, 6, 7);
		for (int n : tiriamiKiekiai) {
			paprastasTyrimas(n);
		}
		Ks.oun("Rikiavimo metodų greitaveikos tyrimas baigtas.");
		
		// Čia gali būti ir kitokio tyrimo metodo (jūsų sugalvoto) iškvietimas.
	}

	public static void main(String[] args) {
		// suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
		Locale.setDefault(new Locale("LT"));
		greitaveika gr = new greitaveika();
                gr.tyrimoPasirinkimas();
	}
}
