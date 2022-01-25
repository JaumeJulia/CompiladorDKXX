/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import ArbolSintactico.ArbolSintactico.Operaciones;
import ArbolSintactico.Tipo;
import java.util.ArrayList;

/**
 *
 * @author felix
 */
public class CodigoTresDirecciones {

    private ArrayList<Instruccion> codigo = new ArrayList<>();
    private ArrayList<Instruccion> constante = new ArrayList<>(); //Declaracion var.
    private boolean declaracion = false;

    private ArrayList<Variable> tv = new ArrayList<>();
    private ArrayList<Procedimiento> tp = new ArrayList<>();
    private ArrayList<Parametro> param; // ArrayList para montar procedimientos.

    private int ne = 0; // numero de etiquetas.
    private int nv = 0; // Numero de variable.
    private int np = 0; // Numero de procedimentos.
    private int npa = 0; // numero de procedimiento activo.

    public String newEtiqueta() {
        ne++;
        return "e" + ne;
    }

    // Añade una nueva variable a la tv.
    public String newVariable(Tipo tipo, String nombre) {
        if (nombre == null) {
            nv++;
            tv.add(new Variable("t" + nv, tipo, npa, true));
            return "t" + nv;
        } else {
            int i = 0;
            while (i < tv.size()) {
                Variable v = tv.get(i);
                if (v.id.equals(nombre) && v.procedimiento == npa) {
                    return nombre + "_" + npa;
                } else {
                    i++;
                }
            }
        }
        tv.add(new Variable(nombre, tipo, npa, false));
        return nombre + "_" + npa;
    }

    // Devuelve el nombre de una variable para el codigo con el ambito.
    public String getVarName(String nombre) {
        for (Variable v : tv) {
            if (v.id.equals(nombre) && v.procedimiento == npa) {
                if(v.isTemporal()){
                    return nombre;
                }
                return nombre + "_" + npa;
            }
        }
        for (Variable v : tv) {
            if (v.id.equals(nombre) && v.procedimiento == 0) {
                if(v.isTemporal()){
                    return nombre;
                }
                return nombre + "_" + 0;
            }
        }
        return null;
    }

    // Inicia un nuevo procedimiento.
    public void newProcedimiento(String nombre, Tipo retorno) {
        np++;
        npa = np;
        tp.add(new Procedimiento(nombre, npa, retorno, null));
    }

    // Añade los parametros al procedimiento.
    public void newProcedimientoadd(ArrayList<Parametro> parametros) {
        Procedimiento p = tp.get(tp.size() - 1);
        p.parametros = parametros;
    }

    // Nueva lista de parametros.
    public void newListaParametros() {
        param = new ArrayList<>();
    }

    // Añade los parametros a la lista de parametros.
    public void addParametro(Tipo t, String id) {
        Parametro p = new Parametro(t, id);
        newVariable(t, id);
        param.add(p);
    }

    // Devuelve la lista de parametros.
    public ArrayList<Parametro> closeListaParametros() {
        return param;
    }

    // Cierre de un procedimiento.
    public void closeProcedimiento() {
        npa = 0;
    }

    // Devuelve el retorno de un procedimiento.
    public Tipo getReturnProcedimiento(String id) {
        for (int i = 0; i < tp.size(); i++) {
            if (tp.get(i).getNombre().equals(id)) {
                return tp.get(i).getRetorno();
            }
        }
        return null;
    }

    // Genera la instruccion en trres direcciones.
    public void generar(Operador a, String op1, String op2, String dest) {
        if (declaracion && npa == 0) {
            constante.add(new Instruccion(a, op1, op2, dest));
        } else {
            codigo.add(new Instruccion(a, op1, op2, dest));
        }
    }

    // Inicio de declaraciones del programa.
    public void startdeclaration() {
        generar(Operador.GOTO, null, null, "run");
        this.declaracion = true;
    }

    // FFin de declaraciones del programa.
    public void enddeclaracion() {
        this.declaracion = false;
        generar(Operador.SKIP, null, null, "run");
        codigo.addAll(constante);
        constante.clear();
    }

    // Traduce el operador del arbol a la instruccion de tres direcciones.
    public Operador traductorOperacion(Operaciones op) {
        switch (op) {
            case MULT:
                return Operador.MULT;
            case DIV:
                return Operador.DIV;
            case SUMA:
                return Operador.SUMA;
            case RESTA:
                return Operador.RESTA;
            case MAYORQUE:
                return Operador.MAYORQUE;
            case MENORQUE:
                return Operador.MENORQUE;
            case MAYORIGU:
                return Operador.MAYORIGU;
            case MENORIGU:
                return Operador.MENORIGU;
            case IGUALES:
                return Operador.IGUALES;
            case NIGUALES:
                return Operador.NIGUALES;
            case OR:
                return Operador.OR;
            case AND:
                return Operador.AND;
            default:
                return null;
        }
    }

    // Devuelve el tipo de resultado de la operacion.
    public Tipo getTipoOperacion(Operador op) {
        if (op == Operador.MULT || op == Operador.DIV || op == Operador.SUMA || op == Operador.RESTA) {
            return Tipo.INT;
        } else {
            return Tipo.BOOLEAN;
        }
    }

    public ArrayList<Variable> getTv() {
        return tv;
    }

    public String printTv() {
        String s = "";
        for (int i = 0; i < tv.size(); i++) {
            s += tv.get(i).toString() + "\n";
        }
        return s;
    }

    public String printTp() {
        String s = "";
        for (int i = 0; i < tp.size(); i++) {
            s += tp.get(i).toString() + "\n";
        }
        return s;
    }

    public ArrayList<Instruccion> getCodigo() {
        return codigo;
    }

    public Procedimiento getPro(String id) {
        for (int i = 0; i < tp.size(); i++) {
            if (tp.get(i).getNombre().equals(id)) {
                return tp.get(i);
            }
        }
        return null;
    }

    public void setCodigo(ArrayList<Instruccion> codigo) {
        this.codigo = codigo;
    }

    public void borrarVariable(String nombre) {
        for (int i = 0; i < tv.size(); i++) {
            if (tv.get(i).getID().equals(nombre)) {
                tv.remove(i);
                break;
            }

        }
    }

    // Devuelve la variable solicitada.
    public Variable getVar(String id) {
        int div = id.lastIndexOf("_");
        if (div >= 0) {
            int capa = Integer.parseInt(id.substring(div + 1));
            String nombre = id.substring(0, div);
            for (int i = 0; i < tv.size(); i++) {
                if (tv.get(i).id.equals(nombre) && tv.get(i).procedimiento == capa) {
                    return tv.get(i);
                }
            }
        } else {
            for (int i = 0; i < tv.size(); i++) {
                if (tv.get(i).id.equals(id)) {
                    return tv.get(i);
                }
            }
        }
        return null;
    }

    public Variable getVariable(String id) {
        for (int i = 0; i < tv.size(); i++) {
            if (tv.get(i).id.equals(id)) {
                return tv.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < codigo.size(); i++) {
            s += codigo.get(i).toString() + "\n";
        }
        return s;
    }
}
