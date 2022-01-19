/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TablaSimbolos;

import ArbolSintactico.Tipo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Felix
 */
public class TablaSimbolos {

    private ArrayList<Simbolo> ts;
    private HashMap<Integer, Simbolo> ta;

    private int nivel; // capa actual.
    private int niveles; // capa maxima.
    private int inicioFuncion; // puntero de inicio de la funcion actual.

    public TablaSimbolos() {
        ts = new ArrayList<>();
        ta = new HashMap<>();

        nivel = 0;
        niveles = 0;
        inicioFuncion = 0;
    }

    public boolean addSimbolo(String id, Tipo tipo, TipoSub tipoSub, int pos) {
        if (tipoSub.equals(TipoSub.FUNCION)) {
            if (getFuncion(id) != null) {
                return false;
            }
            Simbolo s = new Simbolo(id, tipo, nivel, tipoSub, pos);
            ta.put(nivel, s);
            ts.add(inicioFuncion, s);
            nivel = 0;
            inicioFuncion = 0;
        } else {
            if (haySimbolo(id) != null) {
                return false;
            }
            if (tipoSub.equals(TipoSub.PARAMETRO)) {
                Simbolo s = new Simbolo(id, tipo, nivel, tipoSub, pos);
                ts.add(inicioFuncion, s);
            } else {
                Simbolo s = new Simbolo(id, tipo, nivel, tipoSub, pos);
                if (nivel == 0 && !ta.isEmpty()) {
                    ts.add(ts.indexOf(ta.get(1)), s);
                } else {
                    ts.add(s);
                }
            }
        }
        return true;
    }

    public void addnivel() {
        inicioFuncion = ts.size();
        niveles++;
        nivel = niveles;
    }

    private Simbolo haySimbolo(String id) {
        if (nivel != 0) {
            for (int i = inicioFuncion; i < ts.size(); i++) {
                Simbolo s = ts.get(i);
                if (s.getId().equals(id)) {
                    return s;
                }
            }
        } else {
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
        }
        return null;
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

    public Simbolo getParam(Simbolo f, int n) {
        int i = ts.indexOf(f);
        if (i + n < ts.size()) {
            Simbolo s = ts.get(i + n);
            if (s.getTipoSub().equals(TipoSub.PARAMETRO)) {
                return s;
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
}
