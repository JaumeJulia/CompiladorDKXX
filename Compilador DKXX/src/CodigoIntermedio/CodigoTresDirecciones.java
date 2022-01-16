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

    public String newVariable(Tipo tipo, String nombre) {

        if (nombre == null) {
            nv++;
            tv.add(new Variable("t" + nv, tipo, npa));
            return "t" + nv;
        } else {
            boolean encontrado = false;
            int i = 0;
            while (!encontrado && i < tv.size()) {
                Variable v = tv.get(i);
                if (v.id.equals(nombre) && v.procedimiento == npa) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (!encontrado) {
                tv.add(new Variable(nombre, tipo, npa));
            }

            return nombre;
        }
    }

    public void newProcedimiento(String nombre, Tipo retorno) {
        np++;
        npa = np;

        tp.add(new Procedimiento(nombre, npa, retorno, null));

    }
    
    public void newProcedimientoadd (ArrayList<Parametro> parametros){
        Procedimiento p = tp.get(tp.size() - 1);
        p.parametros = parametros;
    }

    public void newListaParametros() {
        param = new ArrayList<>();
    }

    public void addParametro(Tipo t, String id) {
        Parametro p = new Parametro(t, id);
        newVariable(t, id);
        param.add(p);
    }

    public ArrayList<Parametro> closeListaParametros() {
        return param;
    }

    public void closeProcedimiento() {
        npa = 0;
    }

    public Tipo getReturnProcedimiento(String id) {
        for (int i = 0; i < tp.size(); i++) {
            if (tp.get(i).getNombre().equals(id)) {
                return tp.get(i).getRetorno();
            }
        }
        return null;
    }

    public void generar(Operador a, String op1, String op2, String dest) {
        codigo.add(new Instruccion(a, op1, op2, dest));
    }

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
                return Operador.MENORIGU;
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

    public Variable getVar(String id, int procedimiento) {
        for (int i = 0; i < tv.size(); i++) {
            if (tv.get(i).id.equals(id) && tv.get(i).procedimiento == procedimiento) {
                return tv.get(i);
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
    
    public Procedimiento getPro(String id) {
        for (int i = 0; i < tp.size(); i++) {
            if (tp.get(i).getNombre().equals(id)) {
                return tp.get(i);
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
    
       public void setCodigo(ArrayList<Instruccion> codigo) {
        this.codigo = codigo;
    }
       
    public void borrarVariable(String nombre){

        for(int i=0;i<tv.size();i++){
            if(tv.get(i).getID().equals(nombre)){
                tv.remove(i);
                break;
            }

        }
    }

}
