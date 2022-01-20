/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import ArbolSintactico.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Felix
 */
public class TablaSimbolos {

    private ArrayList<Simbolo> ts;
    private HashMap<Integer, Simbolo> ta;
    private Stack<int[]> locSym;

    private ArrayList<String> errores;
    private int nivel;
    private int niveles;

    private int inicioFuncion;

    public TablaSimbolos() {
        ts = new ArrayList<>();
        ta = new HashMap<>();
        locSym = new Stack<>();
        errores = new ArrayList<>();

        nivel = 0;
        niveles = 0;
        inicioFuncion = 0;
    }

    public void addLoc(int row, int col) {
        int[] i = {row, col};
        locSym.push(i);
    }

    public boolean addSimbolo(String id, Tipo tipo, TipoSub tipoSub) {
        int[] i = locSym.pop();
        if (tipoSub.equals(TipoSub.FUNCION)) {
            if (getFuncion(id) != null) {
                addError(4, i[0], i[1], id);
                return false;
            }
            Simbolo s = new Simbolo(id, tipo, nivel, tipoSub, i[0], i[1]);
            ta.put(nivel, s);
            ts.add(inicioFuncion, s);
            nivel = 0;
            inicioFuncion = 0;
        } else {
            if (getSimbolo(id) != null) {
                addError(4, i[0], i[1], id);
                return false;
            }
            Simbolo s = new Simbolo(id, tipo, nivel, tipoSub, i[0], i[1]);
            if (nivel == 0 && !ta.isEmpty()) {
                ts.add(ts.indexOf(ta.get(1)), s);
            } else {
                ts.add(s);
            }
        }
        return true;
    }

    public void addnivel() {
        inicioFuncion = ts.size();
        niveles++;
        nivel = niveles;
    }

    public Simbolo getSimbolo(String id) {
        if (nivel != 0) {
            for (int i = inicioFuncion; i < ts.size(); i++) {
                Simbolo s = ts.get(i);
                if (s.getId().equals(id)) {
                    return s;
                }
            }
        }
        int max = 0;
        if (ta.isEmpty()) {
            max = ts.size();
        } else {
            max = ts.indexOf(ta.get(1));
        }
        for (int i = 0; i < max; i++) {
            Simbolo s = ts.get(i);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public Simbolo getFuncion(String id) {
        if (!ta.isEmpty()) {
            for (Simbolo s : ta.values()) {
                if (s.getId().equals(id)) {
                    return s;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String txt = "";
        for (Simbolo s : ts) {
            if (s.getNivel() > 0 && !s.getTipoSub().equals(TipoSub.FUNCION)) {
                txt += "\t" + s.toString() + "\n";
            } else {
                txt += s.toString() + "\n";
            }
        }
        return txt;
    }

    public String toStringErrores() {
        String txt = "";
        for (String s : errores) {
            txt += "\t - " + s + "\n";
        }
        return txt;
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public void addError(int cod, int row, int col, String aux) {
        switch (cod) {
            case 0:
                errores.add("Tipo " + aux + " no compatible en linea " + row + " columna " + col);
                break;
            case 1:
                errores.add("No existe elemento \"" + aux + "\" en linea " + row + " columna " + col);
                break;
            case 2:
                errores.add("Se intenta modificar la constante \"" + aux + "\" en linea " + row + " columna " + col);
                break;
            case 3:
                errores.add("Faltan parametros en la funcion \"" + aux + "\" en linea " + row + " columna " + col);
                break;
            case 4:
                errores.add("Ya existe elemento \"" + aux + "\" en linea " + row + " columna " + col);
                break;
            case 5:
                errores.add("El elemento \"" + aux + "\" es una funcion en linea " + row + " columna " + col);
                break;
            case 6:
                errores.add("La constante \"" + aux + "\" no se le asigna valor en linea " + row + " columna " + col);
                break;
        }

    }
}
