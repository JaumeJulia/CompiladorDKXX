/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ensamblador;

import ArbolSintactico.Tipo;
import CodigoIntermedio.CodigoTresDirecciones;
import CodigoIntermedio.Instruccion;
import CodigoIntermedio.Operador;
import CodigoIntermedio.Parametro;
import CodigoIntermedio.Procedimiento;
import CodigoIntermedio.Variable;
import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class Codigo68k {

    ArrayList<String> codigo;
    CodigoTresDirecciones ctd;
    int capa;
    int capasiguiente;

    public Codigo68k() {
        codigo = new ArrayList<>();
    }

    public void generar(CodigoTresDirecciones ctd) {

        this.ctd = ctd;
        capa = 0;
        capasiguiente = 1;

        codigo.clear();
        codigo.add("\torg $1000");
        codigo.add("START:");
        codigo.add("\tJSR SCREENSIZE");

        ArrayList<Instruccion> codigointermedio = ctd.getCodigo();
        for (int i = 0; i < codigointermedio.size(); i++) {
            codigo.add("* -->" + codigointermedio.get(i).toString());
            traduccion(codigointermedio.get(i));
        }

        codigo.add("\tSIMHALT");
        codigo.add(" ");
        memoria(ctd.getTv());
        codigo.add("\tDC.W 0");
        codigo.add(" ");
        subrutinas();
        codigo.add("\tEND START");
    }

    public void traduccion(Instruccion i) {
        switch (i.getOperacion()) {
            case MULT:
                multiplicacion(i);
                break;
            case DIV:
                division(i);
                break;
            case SUMA:
                suma(i);
                break;
            case RESTA:
                resta(i);
                break;
            case MAYORQUE:
                comparar(i);
                codigo.add("\tBGT " + i.getDest());
                break;
            case MENORQUE:
                comparar(i);
                codigo.add("\tBLT " + i.getDest());
                break;
            case MAYORIGU:
                comparar(i);
                codigo.add("\tBGE " + i.getDest());
                break;
            case MENORIGU:
                comparar(i);
                codigo.add("\tBLE " + i.getDest());
                break;
            case IGUALES:
                comparar(i);
                codigo.add("\tBEQ " + i.getDest());
                break;
            case NIGUALES:
                comparar(i);
                codigo.add("\tBNE " + i.getDest());
                break;
            case OR:
                or(i);
                break;
            case AND:
                and(i);
                break;
            case GOTO:
                codigo.add("\tJMP " + i.getDest());
                break;
            case SKIP:
                codigo.add(i.getDest() + ":");
                break;
            case ASIG:
                asigna(i);
                break;
            case IN:
                in(i);
                break;
            case OUT:
                out(i);
                break;
            case RTN:
                rtn(i);
                break;
            case PMB:
                pmb(i);
                break;
            case CALL:
                call(i);
                break;
            case PARAM:
                param(i);
                break;
            case DESPLAZAR_BITS:
                break;
        }
    }

    private void multiplicacion(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tMOVE.W " + getOp(i.getOp1()) + ",D0");
        codigo.add("\tEXT.L D0");
        codigo.add("\tMOVE.W " + getOp(i.getOp2()) + ",D1");
        codigo.add("\tEXT.L D1");
        codigo.add("\tMULS.W D0,D1");
        codigo.add("\tMOVE.W D1," + identificador(d));
    }

    private void division(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tMOVE.W " + getOp(i.getOp1()) + ",D1");
        codigo.add("\tEXT.L D1");
        codigo.add("\tMOVE.W " + getOp(i.getOp2()) + ",D0");
        codigo.add("\tEXT.L D0");
        codigo.add("\tDIVS.W D0,D1");
        codigo.add("\tMOVE.W D1," + identificador(d));
    }

    private void suma(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tMOVE.W " + getOp(i.getOp1()) + ",D0");
        codigo.add("\tMOVE.W " + getOp(i.getOp2()) + ",D1");
        codigo.add("\tJSR ISUMA");
        codigo.add("\tMOVE.W D1," + identificador(d));
    }

    private void resta(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tMOVE.W " + getOp(i.getOp1()) + ",D1");
        codigo.add("\tMOVE.W " + getOp(i.getOp2()) + ",D0");
        codigo.add("\tJSR IRESTA");
        codigo.add("\tMOVE.W D1," + identificador(d));
    }

    private void comparar(Instruccion i) {
        Variable v1 = ctd.getVar(i.getOp1(), capa);
        Variable v2 = ctd.getVar(i.getOp2(), capa);
        if (v1 == null) {
            codigo.add("\tMOVE.W " + getValor(i.getOp1()) + ",D0");
        } else {
            if (v1.getTipo() == Tipo.INT) {
                codigo.add("\tMOVE.W " + identificador(v1) + ",D1");
            } else {
                codigo.add("\tMOVE.B " + identificador(v1) + ",D1");
                codigo.add("\tEXT.W D1");
            }
        }
        if (v2 == null) {
            codigo.add("\tMOVE.W " + getValor(i.getOp2()) + ",D0");
        } else {
            if (v2.getTipo() == Tipo.INT) {
                codigo.add("\tMOVE.W " + identificador(v2) + ",D0");
            } else {
                codigo.add("\tMOVE.B " + identificador(v2) + ",D0");
                codigo.add("\tEXT.W D0");
            }
        }
        codigo.add("\tCMP.W D0,D1");
    }

    private void or(Instruccion i) {
        codigo.add("\tMOVE.B " + getOp(i.getOp1()) + ",D0");
        codigo.add("\tMOVE.B " + getOp(i.getOp2()) + ",D1");
        codigo.add("\tOR.B D0,D1");
        codigo.add("\tBMI" +  i.getDest());
    }

    private void and(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tMOVE.B " + getOp(i.getOp1()) + ",D0");
        codigo.add("\tMOVE.B " + getOp(i.getOp2()) + ",D1");
        codigo.add("\tAND.B D0,D1");
        codigo.add("\tBMI " +  i.getDest());
    }

    private void asigna(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        if (d.getTipo() == Tipo.INT) {
            codigo.add("\tMOVE.W " + getOp(i.getOp1()) + "," + identificador(d));
        } else {
            codigo.add("\tMOVE.B " + getOp(i.getOp1()) + "," + identificador(d));
        }
    }

    private void in(Instruccion i) {
        Variable d = ctd.getVar(i.getDest(), capa);
        codigo.add("\tSUBA.L #2,A7");
        codigo.add("\tJSR GETINT");
        if (d.getTipo() == Tipo.INT) {
            codigo.add("\tMOVE.W (A7)+," + identificador(d));
        } else {
            codigo.add("\tMOVE.W (A7)+,D0");
            codigo.add("\tMOVE.B D0," + identificador(d));
        }
    }

    private void out(Instruccion i) {
        Variable op = ctd.getVar(i.getOp1(), capa);
        if (op != null) {
            if (op.getTipo() == Tipo.INT) {
                codigo.add("\tMOVE.W " + identificador(op) + ",-(A7)");
            } else {
                codigo.add("\tCLR.W D0");
                codigo.add("\tMOVE.B " + identificador(op) + ",D0");
                codigo.add("\tEXT.W D0");
                codigo.add("\tMOVE.W D0,-(A7)");
            }
        } else {
            codigo.add("\tMOVE.W " + getValor(i.getOp1()) + ",-(A7)");
        }
        codigo.add("\tJSR PRINTINT");
        codigo.add("\tADDA.L #4,A7");
    }

    private void rtn(Instruccion i) {
        if (i.getDest() != null) {
            Variable r = ctd.getVar(i.getDest(), capa);
            if (r != null) {
                if (r.getTipo() == Tipo.INT) {
                    codigo.add("\tMOVE.W " + identificador(r) + ",4(A7)");
                } else {
                    codigo.add("\tCLR.W D0");
                    codigo.add("\tMOVE.B " + identificador(r) + ",D0");
                    codigo.add("\tMOVE.W D0,4(A7)");
                }
            } else {
                codigo.add("\tMOVE.W " + getValor(i.getDest()) + ",4(A7)");;
            }
        } else {
            capa = 0;
        }
        codigo.add("\tRTS");
    }

    private void pmb(Instruccion i) {
        capa = capasiguiente;
        capasiguiente++;
        Procedimiento p = ctd.getPro(i.getDest());
        ArrayList<Parametro> param = p.getParametros();
        int k = 4;
        if (p.getRetorno() != null) {
            k += 2;
        }
        if (param != null) {
            int ind = param.size() - 1;
            while (ind >= 0) {
                Parametro aux = param.get(ind);
                Variable v = ctd.getVar(aux.getNombre(), capa);
                if (v.getTipo() == Tipo.BOOLEAN) {
                    codigo.add("\tMOVE.W " + k + "(A7),D0");
                    codigo.add("\tMOVE.B D0," + identificador(v));
                    k += 2;
                } else {
                    codigo.add("\tMOVE.W " + k + "(A7)," + identificador(v));
                    k += 2;
                }
                ind--;
            }
        }
        //codigo.add("\tADDA.L #" + k + ",A7");
    }

    private void call(Instruccion i) {
        Procedimiento p = ctd.getPro(i.getDest());
        if (p.getRetorno() != null) {
            codigo.add("\tSUBA.L #2,A7");
        }
        codigo.add("\tJSR " + i.getDest());
        if (p.getRetorno() != null) {
            if (p.getRetorno() == Tipo.INT) {

            } else {

            }
        }
        int k = 0;
        if (p.getParametros() != null) {
            k = 2 * p.getParametros().size();
        }
        if (k > 0) {
            if (p.getRetorno() != null) {
                Variable v = ctd.getVar(i.getOp1(), capa);
                codigo.add("\tMOVE.W (A7)+," + identificador(v));
            }
            codigo.add("\tADDA.L #" + k + ",A7");
        }
    }

    private void param(Instruccion i) {
        Variable p = ctd.getVar(i.getDest(), capa);
        if (p.getTipo() == Tipo.INT) {
            codigo.add("\tMOVE.W " + identificador(p) + ",-(A7)");
        } else {
            codigo.add("\tCLR.W D0");
            codigo.add("\tMOVE.B " + identificador(p) + ",D0");
            codigo.add("\tEXT.W D0");
            codigo.add("\tMOVE.W D0,-(A7)");
        }
    }

    private void memoria(ArrayList<Variable> av) {
        boolean bytes = false;
        for (Variable v : av) {
            if (v.getTipo() == Tipo.INT) {
                if (bytes) {
                    bytes = false;
                    codigo.add("\tDC.W 0");
                }
                codigo.add(identificador(v) + ": DS.W 1");
            } else {
                bytes = true;
                codigo.add(identificador(v) + ": DS.B 1");
            }
        }
    }

    private String getOp(String op) {
        Variable v = ctd.getVar(op, capa);
        if (v == null) {
            return getValor(op);
        }
        return identificador(v);
    }

    private String getValor(String op) {
        if (op.equals("false")) {
            return "#0";
        } else if (op.equals("true")) {
            return "#-1";
        }
        return "#" + op;
    }

    private String identificador(Variable v) {
        return v.getID() + "_" + v.getProcedimiento();
    }

    private void subrutinas() {
        codigo.add("SCREENSIZE:");
        codigo.add("\tMOVE.L #1024*$10000+768,D1");
        codigo.add("\tMOVE.B  #33,D0");
        codigo.add("\tTRAP    #15");
        codigo.add("\tRTS");

        codigo.add("ISUMA:");
        codigo.add("\tBTST.L #15,D0");
        codigo.add("\tBEQ ADD2");
        codigo.add("\tNOT.W D0");
        codigo.add("\tADDQ.W #1,D0");
        codigo.add("\tBTST.L #15,D1");
        codigo.add("\tBEQ ADD1");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tADD.W D0,D1");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tJSR ADD4");
        codigo.add("ADD1:");
        codigo.add("\tSUB.W D0,D1");
        codigo.add("\tJSR ADD4");
        codigo.add("ADD2:");
        codigo.add("\tBTST.L #15,D1");
        codigo.add("\tBEQ ADD3");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tSUB.W D1,D0");
        codigo.add("\tMOVE.W  D0,D1");
        codigo.add("\tJSR ADD4");
        codigo.add("ADD3:");
        codigo.add("\tADD.W D0,D1");
        codigo.add("ADD4:");
        codigo.add("\tRTS");

        codigo.add("IRESTA:");
        codigo.add("\tBTST.L #15,D1");
        codigo.add("\tBEQ SUB2");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tBTST.L #15,D0");
        codigo.add("\tBEQ SUB1");
        codigo.add("\tNOT.W D0");
        codigo.add("\tADDQ.W #1,D0");
        codigo.add("\tSUB.W D0,D1");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tRTS");
        codigo.add("SUB1:");
        codigo.add("\tADD.W D0,D1");
        codigo.add("\tNOT.W D1");
        codigo.add("\tADDQ.W #1,D1");
        codigo.add("\tRTS");
        codigo.add("SUB2:");
        codigo.add("\tBTST.L #15,D0");
        codigo.add("\tBEQ SUB3");
        codigo.add("\tNOT.W D0");
        codigo.add("\tADDQ.W #1,D0");
        codigo.add("\tADD.W D0,D1");
        codigo.add("\tRTS");
        codigo.add("SUB3:");
        codigo.add("\tSUB.W D0,D1");
        codigo.add("\tRTS");

        codigo.add("PRINTINT:");
        codigo.add("\tMOVE.W 4(A7),D1");
        codigo.add("\tMOVE.W D1,-(A7)");
        codigo.add("\tJSR IPRINT");
        codigo.add("\tADDA.W #2,A7");
        codigo.add("\tMOVE.L #11,D0");
        codigo.add("\tMOVE.W #$00FF,D1");
        codigo.add("\tTRAP #15");
        codigo.add("\tADD.W #1,D1");
        codigo.add("\tAND.W #$00FF,D1");
        codigo.add("\tTRAP #15");
        codigo.add("\tRTS");

        codigo.add("IPRINT:");
        codigo.add("\tCLR.L D1");
        codigo.add("\tMOVE.W 4(A7),D1");
        codigo.add("\tEXT.L D1");
        codigo.add("\tMOVE.L #3,D0");
        codigo.add("\tTRAP #15");
        codigo.add("\tRTS");

        codigo.add("GETINT:");
        codigo.add("\tMOVE.L #4,D0");
        codigo.add("\tTRAP #15");
        codigo.add("\tMOVE.W D1,4(A7)");
        codigo.add("\tRTS");
    }

    @Override
    public String toString() {
        String str = "";
        for (String s : codigo) {
            str += s + "\n";
        }
        return str;
    }
}
