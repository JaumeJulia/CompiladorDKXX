/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import ArbolSintactico.Tipo;
import java.util.ArrayList;

/**
 *
 * @author felix
 */
public class Procedimiento {

    String nombre;
    int numeroProcedimiento;
    Tipo retorno;
    ArrayList<Parametro> parametros;

    public Procedimiento(String nombre, int numeroProcedimiento, Tipo retorno, ArrayList<Parametro> parametros) {
        this.nombre = nombre;
        this.numeroProcedimiento = numeroProcedimiento;
        this.retorno = retorno;
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        String s = "\n";
        if (parametros != null) {
            for (int i = 0; i < parametros.size(); i++) {
                s += "\t" + parametros.get(i).toString() + "\n";
            }
        }
        return nombre + " " + numeroProcedimiento + " " + retorno + " " + s;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroProcedimiento() {
        return numeroProcedimiento;
    }

    public void setNumeroProcedimiento(int numeroProcedimiento) {
        this.numeroProcedimiento = numeroProcedimiento;
    }

    public Tipo getRetorno() {
        return retorno;
    }

    public void setRetorno(Tipo retorno) {
        this.retorno = retorno;
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }
}
