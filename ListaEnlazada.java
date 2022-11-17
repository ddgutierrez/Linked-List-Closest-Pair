/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package closestpairlist;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ListaEnlazada {
    Nodo PTR;
    int size;
    public ListaEnlazada(){
        this.PTR = null;
        size=0;
    }
    
    public void insert(int x,int y){
        size++;
        if (PTR == null){
            PTR = new Nodo(x,y);
        } else {
            Nodo p = PTR;
            while(p.link != null){
                p = p.link;
            }           
            p.link = new Nodo(x,y);
        }
    }
    
    public Nodo get(int i){
        Nodo actual=PTR;
        int c=1;
        while(c<i){
            actual=actual.link;
            c++;
        }
        return actual;
    }
    
    public ListaEnlazada subList(int i,int j){
        Nodo inicio = get(i);
        ListaEnlazada sublist = new ListaEnlazada();
        int c=i;
        while(c<=j){
            sublist.insert(inicio.x,inicio.y);
            inicio = inicio.link;
            c++;
        }
        return sublist;
    }
    
    public ArrayList <Nodo> sublista(){
        ArrayList<Nodo> sublista = new ArrayList();
        Nodo actual = PTR;
        sublista.add(actual);
        while(actual.link != null){
            actual = actual.link;
            sublista.add(actual);
        }
        return sublista;
    }
    
    public void mostrar(ListaEnlazada lista, Nodo PTR){
        Nodo actual = PTR;
        while (actual != null){
            System.out.println(actual.x+" a "+actual.y);
            actual = actual.link;
        }
    }
}
