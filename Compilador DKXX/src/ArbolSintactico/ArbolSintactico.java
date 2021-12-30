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

    public ArbolSintactico() {

    }

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
        MENORIGU, IGUALES, NIGUALES, OR, AND, NOT
    }

    public void gest_init(Init p) {
        gest_definicion(p.decl);
        gest_sentencias(p.main);
    }

    public void gest_def(Def p) {
        if (p.idx == 0) {
            gest_dfuncion(p.fun);
        } else {
            gest_declaracion(p.dec);
        }

        if (p.def != null) {
            gest_def(p.def);
        }
    }
    
    public void gest_definicion(Def p){
        switch (p.idx){
            case 0:
                if(p.fun != null) gest_dfuncion(p.fun);
                if(p.def != null) gest_definicion(p.def);
                break;
            case 1:
                if(p.dec != null) gest_declaracion(p.dec);
                if(p.def != null) gest_definicion(p.def);
                break;
        }
    }

    public void gest_dfuncion(Dfuncion p) {
//        gest_id(p.ret, p.id);
//        gest_dparam(p.id, p.dparam);
//        entrarBloque();
//        gest_sentencias(p.sent);
        
    }
    
    public void gest_declaracion(Declaracion p) {

    }

    public void gest_dparam(Dparam p) {
        //gestionar el tipo y el id en ts
        
        if(p.dparam != null) gest_dparam(p.dparam);
    }

    public void gest_return(Return p) {
        gest_expresion(p.expr);
    }

    public void gest_sentencias(Sentencias p) {
        if(p.sentencia != null) gest_sentencia(p.sentencia);
        if(p.sentencias != null) gest_sentencias(p.sentencias);
    }
    
    public void gest_sentencia(Sentencia p){
        //estudiar si valdría con solo hacer 'gest_expresion()' o si se pierde info así
        switch (p.idx) {
                case 0:
                   gest_return(p.ret); 
                case 1:
                    gest_declaracion(p.dec);
                case 2:
                    gest_out(p.out);
                case 3:
                    gest_idsentencia(p.ids);
                case 4:
                    gest_while(p.whi);
                case 5:
                    gest_if(p.sif);
            }  
    }

    public void gest_out(Out p) {
        gest_expresion(p.expr);
    }

    public void gest_idsentencia(IdSentencia p) {
        
    }

    public void gest_sentenciaid(SentenciaId p) {

    }

    public void gest_while(While p) {
        gest_expresion(p.cond);
        gest_sentencias(p.sent);
    }

    public void gest_if(If p) {
        gest_expresion(p.cond);
        gest_sentencias(p.sent);
    }

    public void gest_param(Param p) {
        gest_expresion(p.expr);
        if(p.param != null) gest_param(p.param);
    }

    public void gest_expresion(Expresion p) {

    }

    public void gest_operacion(Operacion p) {
        gest_expresion(p.expr);
    }

    public static class Init {

        Def decl;
        Sentencias main;

        public Init(Def d, Sentencias m) {
            this.decl = d;
            this.main = m;
        }

        public String codigoIntermedio() {
            ctd.generar(Operador.GOTO, null, null, "run");
            this.decl.codigoIntermedio();
            ctd.generar(Operador.SKIP, null, null, "run");
            this.main.codigoIntermedio();
            return null;
        }

    }

    public static class Def {

        int idx;
        Dfuncion fun;
        Declaracion dec;
        Def def;

        public Def(Dfuncion f, Def d) {
            this.fun = f;
            this.def = d;
            this.idx = 0;
        }

        public Def(Declaracion f, Def d) {
            this.dec = f;
            this.def = d;
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
            if (def != null) {
                def.codigoIntermedio();
            }
            return null;
        }
    }

    public static class Dfuncion {

        Id id;
        Tipo ret;
        Dparam dparam;
        Sentencias sent;

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

        Tipo tipo;
        Id id;
        Dparam dparam;

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
        
        Expresion expr;

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

        Sentencia sentencia;
        Sentencias sentencias;

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

        int idx;
        Return ret;
        Declaracion dec;
        Out out;
        IdSentencia ids;
        While whi;
        If sif;

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

        Tipo tipo;
        Id id;
        Expresion expr;

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

        Expresion expr;

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

        SentenciaId sid;
        Id id;

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
                ctd.generar(Operador.ASIG, e, null, i);
                return null;
            }
        }
    }

    public static class SentenciaId {

        Param par;
        Expresion expr;
        int idx;

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

        Expresion cond;
        Sentencias sent;

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

        Expresion cond;
        Sentencias sent;

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

        Expresion expr;
        Param param;

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

        Valor v;
        Expresion e;
        Operacion oper;

        public Expresion(Valor v, Operacion oper) {
            this.v = v;
            this.oper = oper;
        }

        public Expresion(Expresion e, Operacion oper) {
            this.e = e;
            this.oper = oper;
        }

        public String codigoIntermedio() {
            String op1 = v.codigoIntermedio();
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
                    ctd.generar(op, op1, op2, dest);
                    return dest;
                }
            }
            return op1;
        }
    }

    public static class Operacion {

        Operaciones oper;
        Expresion expr;

        public Operacion(Operaciones o, Expresion e) {
            this.oper = o;
            this.expr = e;
        }

        public Operaciones getOper() {
            return oper;
        }

        public String codigoIntermedio() {
            return expr.codigoIntermedio();
        }
    }

    public static class Valor {

        int idx;
        Numero num;
        Id id;
        Boleano bol;
        In in;
        IdSentencia fun;

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
                    return id.codigoIntermedio();
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

        int elem;

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

        boolean elem;

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

        String elem;

        public Id(String elem) {
            this.elem = elem;
        }

        public String codigoIntermedio() {
            return elem;
        }
    }
}
