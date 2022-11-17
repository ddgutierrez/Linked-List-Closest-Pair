/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package closestpairlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class ClosestPairList {
    static long[] tiempos = new long[12];
    static long[] comps = new long[12];
    static long sum = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        create("divide");
        //realiza 10 repeticiones para cada caso variando tamano
        for (int repeticiones = 0; repeticiones < 11; repeticiones++) {
            int tam =(int) Math.pow(3,repeticiones+1);
            //genera n coordenadas aleatorias en la funcion create
            ListaEnlazada coordenadas = create(tam);
            //llamado a bruteforce para calcular distancia minima
            //bruteforce(coordenadas, repeticiones, 0);
            //guarda tiempo de ejecucion de la repeticion
            //organiza las coordenadas
            ArrayList <Nodo> ordenar = coordenadas.sublista();
            ordenar.sort(Comparator.comparing(Nodo->Nodo.x));
            coordenadas = null;
            ListaEnlazada ordenada = new ListaEnlazada();
            for(int i=0;i<ordenar.size();i++){
               int x=ordenar.get(i).x;
               int y=ordenar.get(i).y;
               ordenada.insert(x, y);
            }
            //llama subrutina encargada de separar matriz y calcular distancia minima
            DAC(ordenada, repeticiones,1000000);
            //guarda tiempo de ejecucion
            tiempos[repeticiones] = sum/12;
            sum = 0;
            System.out.println(repeticiones);
        }
        try {
            PrintWriter out = new PrintWriter("divide.txt");
            for (int i = 0; i < tiempos.length; ++i) {
                int s = (int) Math.pow(3, i+1);
                //Escribir cada tiempo de ejecucion en el archivo
                out.println(s + " " + tiempos[i] + " " + comps[i]);
            }
            out.close();	// closes the output stream
        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }
    }

    //funcion que retorna un ArrayList con n coordenadas aleatorias
    public static ListaEnlazada create(int n) {
        ListaEnlazada creados= new ListaEnlazada();
        //ciclo para generar n coordenadas
        for (int i = 0; i < n; i++) {
            Random randomGenerator = new Random();
            Nodo nodo = new Nodo(0,0);
            //asigna las coordenadas a los puntos
            nodo.x = randomGenerator.nextInt(n);
            nodo.y = randomGenerator.nextInt(n);
            creados.insert(nodo.x,nodo.y);
        }
        return creados;
    }

    //funcion que calcula la distancia minima entre dos puntos i y j
    public static double distancias(Nodo i,Nodo j) {
        //retorna la distancia
        return Math.sqrt(Math.pow(i.x - j.x, 2) + Math.pow(i.y - j.y, 2));
    }

    //algoritmo bruteforce para encontrar distancias minimas entre todos los puntos
    public static double bruteforce(ListaEnlazada coordenadas, int index) {
        //inicializar una distancia minima
        double d_min = 10000000;
        double d = 0;
        //ciclos anidados para recorrer las coordenadas
        Nodo actual = coordenadas.PTR;
        while(actual.link!=null){
            Nodo j=actual.link;
            while(j!=null){
                //calcula distancia entre las dos coordenadas
                d=distancias(actual,j);
                //aumenta el numero de comparaciones
                comps[index]+=1;
                long startTime = System.nanoTime();
                if (d < d_min) {
                    //asigna la distancia calculada a la variable con la distancia menor
                    d_min = d;
                }
                long finalTime = System.nanoTime() - startTime;
                sum += finalTime;
                j=j.link;
            }
            actual=actual.link;
        }
        //retorna la distancia minima encontrada
        return d_min;
    }

    public static double DAC(ListaEnlazada coordenadas, int r,double d) {
        double dmin=d;
        //busca el elemento de la mitad
        int pos=coordenadas.size/2;
        if(coordenadas.size%2==1){
            pos+=1;
        }
        //en caso de que hayan mas de 3 coordenadas se subdivide
        if(coordenadas.size>3){
            //se crean listas para ambas mitades
            ListaEnlazada p1 = coordenadas.subList(1, pos);
            ListaEnlazada p2 = coordenadas.subList(pos+1, coordenadas.size);
            //se encuentra la distancia minima entre las dos sublistas
            comps[r]+=1;
            dmin=Math.min(DAC(p1,r,d),DAC(p2,r,d));
            //se encuentra la distancia minima en comparacion a las coordenadas de la mitad
            comps[r]+=1;
            dmin=Math.min(dmin,distancias(p1.get(p1.size),p2.get(1)));
        }
        else
        {
            //realiza bruteforce y encuentra minimo entre las distancias encontradas
            dmin = Math.min(dmin,bruteforce(coordenadas, r));
        }
        return dmin;
    }

    private static void create(String r) // creates a file
    {
        try {
            // defines the filename
            String fname = (r + ".txt");
            // creates a new File object
            File f = new File(fname);

            // creates the new file
            f.createNewFile();

        } catch (IOException err) {
            // complains if there is an Input/Output Error
            err.printStackTrace();
        }
        return;
    }
}
