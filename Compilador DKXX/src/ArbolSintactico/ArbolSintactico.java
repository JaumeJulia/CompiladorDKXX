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
    public CodigoTresDirecciones ctd;

    public ArbolSintactico(Init raiz) {
        this.raiz = raiz;
    }
    
    public void GenerarCTD(){
        raiz.codigoIntermedio();
    }
    

    public enum Operaciones {
        MULT, DIV, SUMA, RESTA, MAYORQUE, MENORQUE, MAYORIGU,
        MENORIGU, IGUALES, NIGUALES, OR, AND, NOT
    }

    public class Init {

        Def decl;
        Sentencias main;

        public Init(Def decl, Sentencias main) {
            this.decl = decl;
            this.main = main;
        }
        
        public void codigoIntermedio(){
            ctd.generar(Operador.GOTO, null, null, "run");
            // Añadir constantes.
            decl.codigoIntermedio();
            ctd.generar(Operador.SKIP, null, null, "run");
            main.codigoIntermedio();
        }
    }

    public class Def {

        Object elem;
        Def def;

        public Def(Object elem, Def def) {
            this.elem = elem;
            this.def = def;
        }
        
        public void codigoIntermedio(){
            elem.codigoIntermedio();
            def.codigoIntermedio();
        }
    }

    public class Dfuncion {

        String id;
        Tipo ret;
        Dparam dparam;
        Sentencias sent;

        public Dfuncion(String id, Tipo ret, Dparam dparam, Sentencias sent) {
            this.id = id;
            this.ret = ret;
            this.dparam = dparam;
            this.sent = sent;
        }
        
        public void codigoIntermedio(){
            
            // Nueva intro en proc tabla.
            
            ctd.generar(Operador.SKIP, null, null, id);
            sent.codigoIntermedio();
        }
    }

    public class Dparam {

        Tipo tipo;
        String id;
        Dparam dparam;

        public Dparam(Tipo tipo, String id, Dparam dparam) {
            this.tipo = tipo;
            this.id = id;
            this.dparam = dparam;
        }
    }

    public class Return {

        Expresion expr;

        public Return(Expresion expr) {
            this.expr = expr;
        }
    }

    public class Sentencias {

        Object elem;
        Sentencias sentencias;

        public Sentencias(Object elem, Sentencias sentencias) {
            this.elem = elem;
            this.sentencias = sentencias;
        }
    }

    public class Declaracion {

        Tipo tipo;
        String id;
        Expresion expr;

        public Declaracion(Tipo tipo, String id, Expresion expr) {
            this.tipo = tipo;
            this.id = id;
            this.expr = expr;
        }
    }

    public class Out {

        Expresion expr;

        public Out(Expresion expr) {
            this.expr = expr;
        }
    }

    public class In {

        public In() {
        }
        
    }

    public class SentenciaID {

        Object elem;
        String id;

        public SentenciaID(String id, Expresion expr) {
            this.elem = expr;
            this.id = id;
        }

        public SentenciaID(String id, Param param) {
            this.elem = param;
            this.id = id;
        }
    }

    public class While {

        Expresion cond;
        Sentencias sent;

        public While(Expresion cond, Sentencias sent) {
            this.cond = cond;
            this.sent = sent;
        }
    }

    public class If {

        Expresion cond;
        Sentencias sent;

        public If(Expresion cond, Sentencias sent) {
            this.cond = cond;
            this.sent = sent;
        }
    }

    public class Funcion {

        String id;
        Param param;

        public Funcion(String id, Param param) {
            this.id = id;
            this.param = param;
        }
    }

    public class Param {

        Expresion expr;
        Param param;

        public Param(Expresion e, Param param) {
            this.expr = e;
            this.param = param;
        }
    }

    public class Expresion {

        Object elem;
        Operacion oper;

        public Expresion(Object elem, Operacion oper) {
            this.elem = elem;
            this.oper = oper;
        }
    }

    public class Operacion {

        Operaciones oper;
        Expresion expr;

        public Operacion(Operaciones oper, Expresion expr) {
            this.oper = oper;
            this.expr = expr;
        }
    }

    public class Valor {

        Object elem;

        public Valor(Object elem) {
            this.elem = elem;
        }
    }

}
