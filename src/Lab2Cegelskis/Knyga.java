/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab2Cegelskis;

/**
 *
 * @author zygis
 */
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import studijosKTU.*;


public class Knyga extends ListKTU implements KTUable<Knyga>{
    private String name;
    private String category;
    private double price;
    private String author;
    private int pageNumber;
    private int printYear;
    
    public Knyga(){}
    
    public Knyga(String n, String c, double pr, String a, int pn, int py){
        this.name = n;
        this.category = c;
        this.price = pr;
        this.author = a;
        this.pageNumber = pn;
        this.printYear = py;
    }
    
    public Knyga(String stringData){
        this.parse(stringData);
    }
    
    @Override
    public final void parse(String dataString) {
        try{
            Scanner sc = new Scanner(dataString);
            name = sc.next();
            category = sc.next();
            setKaina(sc.nextDouble());
            author = sc.next();
            pageNumber = sc.nextInt();
            printYear = sc.nextInt();
        }catch (InputMismatchException  e) {
            System.out.println("Blogas duomenų formatas apie knyga -> " + dataString);
        } catch (NoSuchElementException e) {
            System.out.println("Trūksta duomenų apie knyga -> " + dataString);
        }            
        }
    @Override
    public String toString(){  // surenkama visa reikalinga informacija
        return String.format("%-20s %-15s %6.1f   %-15s  %6d %6d %s",
               name, category, price, author, pageNumber, 
               printYear, validate());
    }
    
    @Override
    public Knyga create(String dataString) {
        Knyga book = new Knyga();
        book.parse(dataString);
        return book;
    }

    @Override
    public String validate() {
        String klaida = new String();
        if(pageNumber == 0)
            klaida = "Knygos nera.";
        else if(printYear < 2010)
            klaida = "Knyga per sena";
        return klaida;
    }

    @Override
    public int compareTo(Knyga o) {
        int leidm = o.getPrintYear();
        if(printYear > leidm) return 1;
        if(printYear == leidm) return 0;
        if(printYear < leidm) return -1;
        return 0;
    }
    
    public String getCategory(){
        return category;
    }
    public int getPrintYear(){
        return printYear;
    }
    public int getPageNumber(){
        return pageNumber;
    }
    public String getName(){
        return name;
    }
    public Double getPrice(){
        return price;
    }
    public String getAuthor(){
        return author;
    }
   public Comparator<Knyga> pagalPuslapiuSkaiciu = (Knyga k1, Knyga k2) -> {
       int a = k1.getPageNumber();
       int b = k2.getPageNumber();
       if(a > b) return 1;
       if(a == b) return 0;
       if(a < b) return -1;
       return 0;
    };
   public Comparator<Knyga> pagalKaina = (Knyga k1, Knyga k2) -> {
       double a = k1.getPrice();
       double b = k2.getPrice();
       if(a > b) return 1;
       if(a == b) return 0;
       if(a < b) return -1;
       return 0;
   };
   public void setKaina(double p){
       this.price = p;
   }        
}
