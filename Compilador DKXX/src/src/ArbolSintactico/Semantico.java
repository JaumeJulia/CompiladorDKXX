/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

import ArbolSintactico.ArbolSintactico.*;

/**
 *
 * @author Felix
 */
public class Semantico {

    public Expresion PrioridadOperacion(Expresion e) {
        // Se verifica que haya dos operaciones.
        if (e.oper != null && e.oper.expr.oper != null) {
            Operaciones OpActual = e.oper.getOper();
            Operaciones OpAnterior = e.oper.expr.oper.getOper();
            if (OpActual.compareTo(OpAnterior) < 0) {
                Operacion op = e.oper.expr.oper;
                e.oper.expr.oper = null;
                Expresion new_e = new Expresion(e, op);
                return new_e;
            }
        }
        return e;
    }
}
