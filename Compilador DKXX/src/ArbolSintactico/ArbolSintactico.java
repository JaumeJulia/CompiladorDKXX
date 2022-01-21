/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

import CodigoIntermedio.CodigoTresDirecciones;
import CodigoIntermedio.Operador;

/**
 *
 * @author felix
 */
public class ArbolSintactico {

    public Init raiz;
    public static CodigoTresDirecciones ctd;
    
    public ArbolSintactico() {}

    public void setRaiz(Init raiz) {
        this.raiz = raiz;
    }

    public CodigoTresDirecciones GenerarCTD() {
        ctd = new CodigoTresDirecciones();
        raiz.codigoIntermedio();
        return ctd;
    }

    public enum Operaciones {
        MULT, DIV, SUMA, RESTA, MAYORQUE, MENORQUE, MAYORIGU,
        MENORIGU, IGUALES, NIGUALES, OR, AND
    }

    public static class Init {

        Definiciones decl;
        Sentencias main;

        public Init(Definiciones d, Sentencias m) {
            this.decl = d;
            this.main = m;
        }

        public String codigoIntermedio() {
            if (decl != null) {
                ctd.startdeclaration();
                this.decl.codigoIntermedio();
                ctd.enddeclaracion();
            }
            this.main.codigoIntermedio();
            return null;
        }

    }
    
    public static class Definiciones {
        
        public Definicion def;
        public Definiciones defs;
        
        public Definiciones(Definicion def, Definiciones defs){
            this.def = def;
            this.defs = defs;
        }
        
        public String codigoIntermedio(){
            def.codigoIntermedio();
            if(defs != null){
                defs.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Definicion {

        public int idx;
        public Dfuncion fun;
        public Declaracion dec;

        public Definicion(Dfuncion f) {
            this.fun = f;
            this.idx = 0;
        }

        public Definicion(Declaracion f) {
            this.dec = f;
            this.idx = 1;
        }

        public String codigoIntermedio() {
            switch (idx) {
                case 0:
                    fun.codigoIntermedio();
                    break;
                case 1:
                    dec.codigoIntermedio();
                    break;
            }
            return null;
        }
    }

    public static class Dfuncion {

        public Id id;
        public Tipo ret;
        public Dparam dparam;
        public Sentencias sent;

        public Dfuncion(Id i, Tipo r, Dparam p, Sentencias s) {
            this.id = i;
            this.ret = r;
            this.dparam = p;
            this.sent = s;
        }

        public String codigoIntermedio() {
            String i = this.id.codigoIntermedio();
            ctd.newProcedimiento(i, ret);
            if (dparam != null) {
                ctd.newListaParametros();
                dparam.codigoIntermedio();
                ctd.newProcedimientoadd(ctd.closeListaParametros());
            }
            ctd.generar(Operador.SKIP, null, null, i);
            ctd.generar(Operador.PMB, null, null, i);
            sent.codigoIntermedio();
            ctd.generar(Operador.RTN, null, null, null);
            ctd.closeProcedimiento();
            return null;
        }
    }

    public static class Dparam {

        public Tipo tipo;
        public Id id;
        public Dparam dparam;

        public Dparam(Tipo t, Id i, Dparam d) {
            this.tipo = t;
            this.id = i;
            this.dparam = d;
        }

        public String codigoIntermedio() {
            ctd.addParametro(tipo, id.codigoIntermedio());
            if (dparam != null) {
                dparam.codigoIntermedio();
            }

            return null;
        }
    }

    public static class Return {

        public Expresion expr;

        public Return(Expresion e) {
            this.expr = e;
        }

        public String codigoIntermedio() {
            String v = expr.codigoIntermedio();
            ctd.generar(Operador.RTN, null, null, v);
            return null;
        }
    }

    public static class Sentencias {

        public Sentencia sentencia;
        public Sentencias sentencias;

        
        public Sentencias(Sentencia s, Sentencias ss) {
            this.sentencia = s;
            this.sentencias = ss;
        }

        public String codigoIntermedio() {
            sentencia.codigoIntermedio();
            if (sentencias != null) {
                sentencias.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Sentencia {

        public int idx;
        public Return ret;
        public Declaracion dec;
        public Out out;
        public IdSentencia ids;
        public While whi;
        public If sif;

        public Sentencia(Return e) {
            this.ret = e;
            this.idx = 0;
        }

        public Sentencia(Declaracion e) {
            this.dec = e;
            this.idx = 1;
        }

        public Sentencia(Out e) {
            this.out = e;
            this.idx = 2;
        }

        public Sentencia(IdSentencia e) {
            this.ids = e;
            this.idx = 3;
        }

        public Sentencia(While e) {
            this.whi = e;
            this.idx = 4;
        }

        public Sentencia(If e) {
            this.sif = e;
            this.idx = 5;
        }

        public String codigoIntermedio() {
            switch (idx) {
                case 0:
                    return ret.codigoIntermedio();
                case 1:
                    return dec.codigoIntermedio();
                case 2:
                    return out.codigoIntermedio();
                case 3:
                    return ids.codigoIntermedio();
                case 4:
                    return whi.codigoIntermedio();
                case 5:
                    return sif.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Declaracion {

        public Tipo tipo;
        public Id id;
        public Expresion expr;

        public Declaracion(Tipo t, Id i, Expresion d) {
            this.tipo = t;
            this.id = i;
            this.expr = d;
        }

        public String codigoIntermedio() {
            String nom = ctd.newVariable(tipo, id.codigoIntermedio());

            if (expr != null) {
                String e = expr.codigoIntermedio();
                ctd.generar(Operador.ASIG, e, null, nom);
            }
            return null;
        }
    }

    public static class Out {

        public Expresion expr;

        public Out(Expresion e) {
            this.expr = e;
        }

        public String codigoIntermedio() {
            String dest = expr.codigoIntermedio();
            ctd.generar(Operador.OUT, dest, null, null);
            return null;
        }
    }

    public static class In {

        public In() {
        }

        public String codigoIntermedio() {
            String dest = ctd.newVariable(Tipo.INT, null);
            ctd.generar(Operador.IN, null, null, dest);
            return dest;
        }
    }

    public static class IdSentencia {

        public SentenciaId sid;
        public Id id;

        public IdSentencia(Id i, SentenciaId e) {
            this.sid = e;
            this.id = i;
        }

        public String codigoIntermedio() {
            String i = this.id.codigoIntermedio();
            String e = sid.codigoIntermedio();
            if (e == null) {
                Tipo t = ctd.getReturnProcedimiento(i);
                if (t != null) {
                    String v = ctd.newVariable(t, null);
                    ctd.generar(Operador.CALL, v, null, i);
                    return v;
                } else {
                    ctd.generar(Operador.CALL, null, null, i);
                    return null;
                }
            } else {
                ctd.generar(Operador.ASIG, e, null, ctd.getVarName(i));
                return null;
            }
        }
    }

    public static class SentenciaId {

        public Param par;
        public Expresion expr;
        public int idx;

        public SentenciaId(Param f) {
            this.par = f;
            idx = 0;
        }

        public SentenciaId(Expresion e) {
            this.expr = e;
            idx = 1;
        }

        public String codigoIntermedio() {
            switch (idx) {
                case 0:
                    if (par != null) {
                        return par.codigoIntermedio();
                    } else {
                        return null;
                    }
                default:
                    return expr.codigoIntermedio();
            }
        }
    }

    public static class While {

        public Expresion cond;
        public Sentencias sent;

        public While(Expresion e, Sentencias s) {
            this.cond = e;
            this.sent = s;
        }

        public String codigoIntermedio() {
            String e1 = ctd.newEtiqueta();
            ctd.generar(Operador.SKIP, null, null, e1);
            String c = cond.codigoIntermedio();
            String e2 = ctd.newEtiqueta();
            String e3 = ctd.newEtiqueta();
            ctd.generar(Operador.IGUALES, c, Integer.toString(-1), e2);
            ctd.generar(Operador.GOTO, null, null, e3);
            ctd.generar(Operador.SKIP, null, null, e2);
            sent.codigoIntermedio();
            ctd.generar(Operador.GOTO, null, null, e1);
            ctd.generar(Operador.SKIP, null, null, e3);
            return null;
        }
    }

    public static class If {

        public Expresion cond;
        public Sentencias sent;

        public If(Expresion e, Sentencias s) {
            this.cond = e;
            this.sent = s;
        }

        public String codigoIntermedio() {
            String c = cond.codigoIntermedio();
            String e1 = ctd.newEtiqueta();
            String e2 = ctd.newEtiqueta();
            ctd.generar(Operador.IGUALES, c, Integer.toString(-1), e1);
            ctd.generar(Operador.GOTO, null, null, e2);
            ctd.generar(Operador.SKIP, null, null, e1);
            sent.codigoIntermedio();
            ctd.generar(Operador.SKIP, null, null, e2);
            return null;
        }
    }

    public static class Param {

        public Expresion expr;
        public Param param;

        public Param(Expresion e, Param p) {
            this.expr = e;
            this.param = p;
        }

        public String codigoIntermedio() {
            String e = expr.codigoIntermedio();
            ctd.generar(Operador.PARAM, null, null, e);
            if (param != null) {
                param.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Expresion {

        public int idx;
        public Valor v;
        public Expresion e;
        public Operacion oper;

        public Expresion(Valor v, Operacion oper) {
            this.v = v;
            this.oper = oper;
            this.idx = 0;
        }

        public Expresion(Expresion e, Operacion oper) {
            this.e = e;
            this.oper = oper;
            this.idx = 1;
        }
        
        public Operacion getOper(){
            return oper;
        }
        
        public void setOper(Operacion oper){
            this.oper = oper;
        }
        
        public boolean isExpr(){
            return idx == 1;
        }
        
        public Expresion getExpr(){
            return e;
        }

        public String codigoIntermedio() {
            String op1 = null;
            switch (idx) {
                case 0:
                    op1 = v.codigoIntermedio();
                    break;
                case 1:
                    op1 = e.codigoIntermedio();
                    break;
            }
            if (oper != null) {
                Operador op = ctd.traductorOperacion(oper.getOper());
                String op2 = oper.codigoIntermedio();
                if (ctd.getTipoOperacion(op) == Tipo.BOOLEAN) {
                    String dest = ctd.newVariable(Tipo.BOOLEAN, null);
                    String e1 = ctd.newEtiqueta();
                    String e2 = ctd.newEtiqueta();
                    String e3 = ctd.newEtiqueta();
                    ctd.generar(op, op1, op2, e1);
                    ctd.generar(Operador.GOTO, null, null, e2);
                    ctd.generar(Operador.SKIP, null, null, e1);
                    ctd.generar(Operador.ASIG, Integer.toString(-1), null, dest);
                    ctd.generar(Operador.GOTO, null, null, e3);
                    ctd.generar(Operador.SKIP, null, null, e2);
                    ctd.generar(Operador.ASIG, Integer.toString(0), null, dest);
                    ctd.generar(Operador.SKIP, null, null, e3);
                    return dest;
                } else {
                    String dest = ctd.newVariable(Tipo.INT, null);
                    ctd.generar(op, op1, op2, ctd.getVarName(dest));
                    return dest;
                }
            }
            return op1;
        }
    }

    public static class Operacion {

        public Operaciones oper;
        public Expresion expr;

        public Operacion(Operaciones o, Expresion e) {
            this.oper = o;
            this.expr = e;
        }

        public Operaciones getOper() {
            return oper;
        }
        
        public Expresion getExpr(){
            return expr;
        }
        
        public void setExpr(Expresion expr){
            this.expr = expr;
        }

        public String codigoIntermedio() {
            return expr.codigoIntermedio();
        }
    }

    public static class Valor {

        public int idx;
        public Numero num;
        public Id id;
        public Boleano bol;
        public In in;
        public IdSentencia fun;

        public Valor(Numero e) {
            this.num = e;
            this.idx = 0;
        }

        public Valor(Id i) {
            this.id = i;
            this.idx = 1;
        }

        public Valor(Boleano b) {
            this.bol = b;
            this.idx = 2;
        }

        public Valor(In in) {
            this.in = in;
            this.idx = 3;
        }

        public Valor(IdSentencia f) {
            this.fun = f;
            this.idx = 4;
        }

        public String codigoIntermedio() {
            switch (idx) {
                case 0:
                    return num.codigoIntermedio();
                case 1:
                    return ctd.getVarName(id.codigoIntermedio());
                case 2:
                    return bol.codigoIntermedio();
                case 3:
                    return in.codigoIntermedio();
                case 4:
                    return fun.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Numero {

        public int elem;

        public Numero(String elem) {
            this.elem = Integer.parseInt(elem);
        }

        public String codigoIntermedio() {
            String v = ctd.newVariable(Tipo.INT, null);
            ctd.generar(Operador.ASIG, Integer.toString(elem), null, v);
            return v;
        }
    }

    public static class Boleano {

        public boolean elem;

        public Boleano(String elem) {
            this.elem = Boolean.parseBoolean(elem);
        }

        public String codigoIntermedio() {
            String v = ctd.newVariable(Tipo.BOOLEAN, null);
            ctd.generar(Operador.ASIG, Boolean.toString(elem), null, v);
            return v;
        }
    }

    public static class Id {

        public String elem;

        public Id(String elem) {
            this.elem = elem;
        }

        public String codigoIntermedio() {
            return elem;
        }
    }
}
