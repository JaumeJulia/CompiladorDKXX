/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

/**
 *
 * @author Felix
 */
public class Instruccion {

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

    public Operador getOperacion() {
        return operacion;
    }

    public void setOperacion(Operador operacion) {
        this.operacion = operacion;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void modificarIntruccion(Operador operacion, String op1, String op2, String dest) {

        this.operacion = operacion;
        this.op1 = op1;
        this.op2 = op2;
        this.dest = dest;

    }

    public boolean esCondicional() {
        return operacion == Operador.MENORQUE || operacion == Operador.MAYORQUE
                || operacion == Operador.MENORIGU || operacion == Operador.MAYORIGU
                || operacion == Operador.IGUALES || operacion == Operador.NIGUALES;
    }

    public boolean esArtim() {
        return operacion == Operador.SUMA || operacion == Operador.RESTA || operacion == Operador.MULT
                || operacion == Operador.DIV || operacion == Operador.AND || operacion == Operador.OR;
    }

    public boolean esParam() {
        return operacion == Operador.PARAM;
    }

    @Override
    public String toString() {
        return "[" + operacion.toString() + ", " + op1 + ", " + op2 + ", " + dest + "]";
    }
}