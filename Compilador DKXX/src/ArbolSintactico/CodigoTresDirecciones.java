/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

import java.util.ArrayList;

/**
 *
 * @author felix
 */
public class CodigoTresDirecciones {

    public enum Operador {
        MULT, DIV, SUMA, RESTA, MAYORQUE, MENORQUE, MAYORIGU,
        MENORIGU, IGUALES, NIGUALES, OR, AND, NOT, GOTO, SKIP
    };
    private ArrayList<Instruccion> codigo = new ArrayList<>();
    private ArrayList<Variable> tv = new ArrayList<>();
    private ArrayList<Procedimiento> tp = new ArrayList<>();

    private int ne = 0; // numero de etiquetas.
    private int nv = 0; // Numero de variable.
    private int np = 0; // Numero de procedimentos.
    private int npa = 0; // numero de procedimiento activo.

    private class Instruccion {

        private Operador operacion;
        private String op1;
        private String op2;
        private String dest;

        public Instruccion(Operador operacion, String op1, String op2, String dest) {
            this.operacion = operacion;
            this.op1 = op1;
            this.op2 = op2;
            this.dest = dest;
        }

        @Override
        public String toString() {
            return "[" + operacion.toString() + ", " + op1 + ", " + op2 + ", " + dest + "]";
        }
    }

    public String newEtiqueta() {
        ne++;
        return "e" + ne;
    }

    public String newVariable(Tipo tipo, String nombre) {
        int anp;
        if (np < 0) {
            anp = np + 1;
        } else {
            anp = np;
        }
        if (nombre == null) {
            nv++;
            nombre = "t" + nv;
            Variable var = new Variable(nombre, tipo, anp);
            tv.add(var);
        } else {
            int i = 0;
            boolean same = false;
            while (i < tv.size() && !same) {
                if (tv.get(i).getID().equals(nombre) && tv.get(i).getID().equals(anp)) {
                    same = true;
                }
                i++;
            }
            if (!same) {
                Variable var = new Variable(nombre, tipo, anp);
                tv.add(var);
            }
        }
        return nombre;
    }

    public void generar(Operador a, String op1, String op2, String dest) {
        codigo.add(new Instruccion(a, op1, op2, dest));
    }

}