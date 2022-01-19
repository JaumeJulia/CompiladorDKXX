/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import java.util.ArrayList;
import ArbolSintactico.*;
import ArbolSintactico.ArbolSintactico.*;
import TablaSimbolos.Simbolo;
import TablaSimbolos.TablaSimbolos;

/**
 *
 * @author Felix
 */
public class Semantico {

    public TablaSimbolos ts;
    public ArrayList<String> errores;

    public Semantico(TablaSimbolos ts) {
        this.ts = ts;
        this.errores = new ArrayList<>();
    }

    // Prioridad Operaciones.
    public Expresion priOp(Expresion e) {
        // Se verifica que haya dos operaciones.
        if (e.getOper() != null && e.getOper().getExpr().getOper() != null) {
            Operaciones OpActual = e.getOper().getOper();
            Operaciones OpAnterior = e.getOper().getExpr().getOper().getOper();
            // Se mira el orden de prioridad para reorganizar los valores.
            if (OpActual.compareTo(OpAnterior) < 0) {
                Operacion op = e.getOper().getExpr().getOper();
                e.getOper().getExpr().setOper(null);

                // Si hay una expresion vacia esta se elimina para evitar problemas.
                if (e.getOper().getExpr().getOper() == null && e.getOper().getExpr().isExpr()) {
                    Expresion a = e.getOper().getExpr().getExpr();
                    e.getOper().setExpr(a);
                }
                // Se llama de forma recursiva con la nueva expresion para organizar las operaciones en ella.
                Expresion new_e = new Expresion(priOp(e), op);
                return new_e;
            }
        }
        return e;
    }

    //Verificar si es funcion o asignacion.
    public void selOpId(String id, SentenciaId sid, int l) {
        if (sid.expr != null) {
            verVar(id, sid.expr, l);
        } else {
            verFunc(id, sid.par, l);
        }
    }

    //Verifica que exista el id
    public boolean verId(String id, int l) {
        Simbolo s = ts.getFuncion(id);
        if (s == null) {
            s = ts.getSimbolo(id);
        }
        if (s == null) {
            addError(1, l, id);
            return false;
        }
        return true;
    }

    //Verifica Asignacion Const
    public void verConst(String id, Expresion e, int l) {
        Simbolo s = ts.getSimbolo(id);
        if (s != null) {
            if (e == null) {
                addError(6, l, id);
            } else {
                verExpr(e, s.getTipo(), l);
            }
        } else {
            addError(1, l, id);
        }
    }

    //Verifica Asignacion
    public boolean verVar(String id, Expresion e, int l) {
        Simbolo s = ts.getSimbolo(id);
        if (s != null) {
            switch (s.getTipoSub()) {
                case PARAMETRO:
                case VARIABLE:
                    if (e != null) {
                        verExpr(e, s.getTipo(), l);
                    }
                    break;
                case FUNCION:
                    addError(5, l, id);
                    break;
                default:
                    addError(2, l, id);
                    break;
            }
        } else {
            addError(1, l, id);
        }
        return false;
    }

    //Verifica Retornos
    public boolean verRet(String id, Tipo r, Sentencias s, int l) {
        boolean e = false;
        if (r != null) {
            boolean f = false;
            while (s != null) {
                if (s.sentencia.ret != null) {
                    e = true;
                    if (!verExpr(s.sentencia.ret.expr, r, l)) {
                        f = true;
                    }
                }
                s = s.sentencias;
            }
            if (f) {
                addError(9, l, id);
            }
            if (!e) {
                addError(7, l, id);
            }
        } else {
            while (s != null) {
                if (s.sentencia.ret != null) {
                    verExpr(s.sentencia.ret.expr, Tipo.NULL, l);
                    e = true;
                }
                s = s.sentencias;
            }
            if (e) {
                addError(8, l, id);
            }
        }
        return true;
    }

    //Verifica Funcion
    public boolean verFunc(String id, Param par, int l) {
        Simbolo f = ts.getFuncion(id);
        if (f == null) {
            addError(1, l, id);
            return false;
        }
        int i = 0;
        Simbolo p = ts.getParam(f, i);
        if (par != null) {
            while (p != null && par != null) {
                if (verExpr(par.expr, p.getTipo(), l)) {
                    return false;
                }
                i++;
                par = par.param;
                p = ts.getParam(f, i);
            }
        }
        if (p != null) {
            addError(3, l, id);
            return false;
        }
        return true;
    }

    //Verifica Expresion
    public boolean verExpr(Expresion e, Tipo t, int l) {
        Tipo opt = t;
        if (e.oper != null) {
            if (e.oper.oper.compareTo(Operaciones.MAYORQUE) < 0) {
                opt = Tipo.INT;
            } else {
                opt = Tipo.BOOLEAN;
            }
            if (!opt.equals(t)) {
                addError(0, l, t.toString());
                return false;
            }
            if (e.oper.oper.compareTo(Operaciones.OR) < 0) {
                opt = Tipo.INT;
            } else {
                opt = Tipo.BOOLEAN;
            }
        }
        if (e.e == null) {
            if (!verificarValor(e.v, opt)) {
                addError(0, l, opt.toString());
                return false;
            }
        } else {
            if (!verExpr(e.e, opt, l)) {
                return false;
            }
        }
        if (e.oper != null) {
            if (!verExpr(e.oper.expr, opt, l)) {
                return false;
            }
        }
        return true;
    }

    public boolean verificarValor(Valor v, Tipo t) {
        switch (v.idx) {
            case 0:
            case 3:
                return t.equals(Tipo.INT);
            case 1:
                Simbolo s = ts.getSimbolo(v.id.elem);
                if (s != null) {
                    return s.getTipo().equals(t);
                }
                break;
            case 2:
                return t.equals(Tipo.BOOLEAN);
            case 4:
                Simbolo f = ts.getFuncion(v.fun.id.elem);
                if (f != null) {
                    return f.getTipo().equals(t);
                }
                break;
        }
        return false;
    }

    public String toStringErrores() {
        String txt = "";
        txt = errores.stream().map(s -> "\t - " + s + "\n").reduce(txt, String::concat);
        return txt;
    }

    public boolean hayErrores() {
        return !errores.isEmpty();
    }

    public void addError(int cod, int l, String aux) {
        switch (cod) {
            case 0:
                errores.add("Tipo " + aux + " no compatible en linea " + l);
                break;
            case 1:
                errores.add("No existe elemento \"" + aux + "\" en linea " + l);
                break;
            case 2:
                errores.add("Se intenta modifioar la constante \"" + aux + "\" en linea " + l);
                break;
            case 3:
                errores.add("Faltan parametros en la funcion \"" + aux + "\" en linea " + l);
                break;
            case 4:
                errores.add("Ya existe elemento \"" + aux + "\" en linea " + l);
                break;
            case 5:
                errores.add("El elemento \"" + aux + "\" es una funcion en linea " + l);
                break;
            case 6:
                errores.add("La constante \"" + aux + "\" no se le asigna valor en linea " + l);
                break;
            case 7:
                errores.add("No hay retorno en la funcion \"" + aux + "\" en linea " + l);
                break;
            case 8:
                errores.add("Hay retorno en la funcion \"" + aux + "\" pese que no se comtempla en linea " + l);
                break;
            case 9:
                errores.add("El retorno en la funcion \"" + aux + "\" no es correcto en linea " + l);
                break;
        }

    }
}
