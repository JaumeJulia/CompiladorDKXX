/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

import ArbolSintactico.CodigoTresDirecciones.Operador;

/**
 *
 * @author felix
 */
public class ArbolSintactico {

    public Init raiz;
    public static CodigoTresDirecciones ctd;

    public ArbolSintactico() {

    }

    public void setRaiz(Init raiz) {
        this.raiz = raiz;
    }

    public void GenerarCTD() {
        raiz.codigoIntermedio();
    }

    public enum Operaciones {
        MULT, DIV, SUMA, RESTA, MAYORQUE, MENORQUE, MAYORIGU,
        MENORIGU, IGUALES, NIGUALES, OR, AND, NOT
    }

    public static class Init {

        Def decl;
        Sentencias main;

        public Init(Object d, Object m) {
            this.decl = (Def) d;
            this.main = (Sentencias) m;
        }

        public void codigoIntermedio() {
            ctd.generar(Operador.GOTO, null, null, "run");
            // Añadir constantes.
            decl.codigoIntermedio();
            ctd.generar(Operador.SKIP, null, null, "run");
            //   main.codigoIntermedio();
        }

    }

    public static class Def {

        Object elem;
        Def def;

        public Def(Object f, Object d) {
            this.elem = f;
            this.def = (Def) d;
        }

        public void codigoIntermedio() {
            //      elem.codigoIntermedio();
            def.codigoIntermedio();
        }

    }

    public static class Dfuncion {

        String id;
        Tipo ret;
        Dparam dparam;
        Sentencias sent;

        public Dfuncion(String i, Object r, Object p, Object s) {
            this.id = i;
            this.ret = (Tipo) r;
            this.dparam = (Dparam) p;
            this.sent = (Sentencias) s;
        }

        public void codigoIntermedio() {

            // Nueva intro en proc tabla.
            ctd.generar(Operador.SKIP, null, null, id);
            //  sent.codigoIntermedio();
        }
    }

    public static class Dparam {

        Tipo tipo;
        String id;
        Dparam dparam;

        public Dparam(Object t, String i, Object d) {
            this.tipo = (Tipo) t;
            this.id = i;
            this.dparam = (Dparam) d;
        }
    }

    public static class Return {

        Expresion expr;

        public Return(Object e) {
            this.expr = (Expresion) e;
        }
    }

    public static class Sentencias {

        Object elem;
        Sentencias sentencias;

        public Sentencias(Object e, Object s) {
            this.elem = e;
            this.sentencias = (Sentencias) s;
        }
    }

    public static class Declaracion {

        Tipo tipo;
        String id;
        Expresion expr;

        public Declaracion(Object t, String i, Object d) {
            this.tipo = (Tipo) t;
            this.id = i;
            this.expr = (Expresion) d;
        }
    }

    public static class Out {

        Expresion expr;

        public Out(Object e) {
            this.expr = (Expresion) e;
        }
    }

    public static class In {

        public In() {
        }

    }

    public static class SentenciaID {

        Object elem;
        String id;

        public SentenciaID(String i, Object d) {
            this.elem = d;
            this.id = i;
        }
    }

    public static class While {

        Expresion cond;
        Sentencias sent;

        public While(Object e, Object s) {
            this.cond = (Expresion) e;
            this.sent = (Sentencias) s;
        }
    }

    public static class If {

        Expresion cond;
        Sentencias sent;

        public If(Object e, Object s) {
            this.cond = (Expresion) e;
            this.sent = (Sentencias) s;
        }
    }

    public static class Funcion {

        String id;
        Param param;

        public Funcion(String i, Object f) {
            this.id = i;
            this.param = (Param) f;
        }
    }

    public static class Param {

        Expresion expr;
        Param param;

        public Param(Object e, Object p) {
            this.expr = (Expresion) e;
            this.param = (Param) p;
        }
    }

    public static class Expresion {

        Object elem;
        Operacion oper;

        public Expresion(Object e, Object o) {
            this.elem = e;
            this.oper = (Operacion) o;
        }
    }

    public static class Operacion {

        Operaciones oper;
        Expresion expr;

        public Operacion(Object o, Object e) {
            this.oper = (Operaciones) o;
            this.expr = (Expresion) e;
        }
    }

    public static class Valor {

        Object elem;

        public Valor(Object elem) {
            this.elem = elem;
        }
    }

}
