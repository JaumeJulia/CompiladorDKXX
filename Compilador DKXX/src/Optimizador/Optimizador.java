/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Optimizador;

import CodigoIntermedio.CodigoTresDirecciones;
import CodigoIntermedio.Instruccion;
import CodigoIntermedio.Operador;
import CodigoIntermedio.Variable;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Optimizador {

    private ArrayList<Instruccion> instrucciones;
    private Instruccion act, sig;
    private CodigoTresDirecciones ctd;

    public Optimizador(CodigoTresDirecciones ctd) {
        this.ctd = ctd;
        this.instrucciones = ctd.getCodigo();
    }

    public void optimizar() {

        int despues, antes;
        do {
            antes = instrucciones.size();
            conjuntoOptimizaciones();
            despues = instrucciones.size();
        } while (antes != despues);

        this.ctd.setCodigo(instrucciones);

    }

    private void conjuntoOptimizaciones() {

        int i = 0;
        while (i != instrucciones.size() - 1) {

            act = instrucciones.get(i);
            sig = instrucciones.get(i + 1);

            if (RbrancamentsAdjacents(i, act, sig)) {
            } else if (SaltoSobreSalto(i, act, sig)) {
            } else if (AsignacionDiferida(i, act, sig)) {
            }
// else if (CodigoInaccesible(i, act, sig)) {
//             i++;
//            } 
            else if (OperacionesConstantes(i, act)) {
            } else if (ReducciondeFuerza(i, act)) {
            } else {
                i++;
            }

        }

    }

    //ELIMINA ASIGCIONES QUE SEAN CONSECUTIVAS
    private boolean AsignacionDiferida(int i, Instruccion act, Instruccion sig) { //falta un caso nuevo de los asigna con los in y outs con variables temporales

        if (act.getOperacion() == Operador.ASIG && sig.getOperacion() == Operador.ASIG) {

            //CASO DONDE ASIGMOS UN VALOR A UNA VARIABLE Y DEPUES LE ASIGMOS OTRO DIRECTAMENTE 
            if (act.getDest().equals(sig.getDest())) {
                instrucciones.remove(i);
                return true;
            }

            //CASO DONDE ASIGMOS UN VALOR A UNA VARIABLE PARA LUEGO ASIGRSELO A OTRO DIRECTAMENTE        
            if (act.getDest().equals(sig.getOp1())) {
                ctd.borrarVariable(act.getDest());
                act.modificarIntruccion(act.getOperacion(), act.getOp1(), act.getOp2(), sig.getDest());
                instrucciones.remove(i + 1);
                return true;
            }

            //CASO PARA OPERACIONES ENTRE 2 VARIABLES ASIGDAS ANTERIORMENTE
            if (i != 0) {
                Instruccion ant = instrucciones.get(i - 1);
                if (!ant.esArtim()) {
                    Instruccion ter = instrucciones.get(i + 2);
                    if (ter.esArtim() || ter.esCondicional()) {
                        boolean retorno = false;
                        if (esTemporal(sig.getDest()) && ter.getOp2().equals(sig.getDest())) {
                            ctd.borrarVariable(sig.getDest());
                            instrucciones.remove(i + 1);
                            ter.modificarIntruccion(ter.getOperacion(), ter.getOp1(), sig.getOp1(), ter.getDest());
                            retorno = true;
                        }
                        if (esTemporal(act.getDest()) && ter.getOp1().equals(act.getDest())) {
                            ctd.borrarVariable(act.getDest());
                            instrucciones.remove(i);
                            ter.modificarIntruccion(ter.getOperacion(), act.getOp1(), ter.getOp2(), ter.getDest());
                            retorno = true;
                        }
                        if (retorno) {
                            return true;
                        }
                    }
                }
            }
        }

        //CASO DONDE ASIGMOS UN VALOR A UNA VARIABLE TEMPORAL PARA LUEGO DARSELA A UNA OPERACION
        if (act.getOperacion() == Operador.ASIG && (sig.esArtim() || sig.esCondicional() || sig.esParam())) {

            if (esTemporal(act.getDest())) {
                if (act.getDest().equals(sig.getOp1())) {
                    ctd.borrarVariable(act.getDest());
                    sig.modificarIntruccion(sig.getOperacion(), act.getOp1(), sig.getOp2(), sig.getDest());
                    instrucciones.remove(i);
                    return true;
                }
                if (act.getDest().equals(sig.getOp2())) {
                    ctd.borrarVariable(act.getDest());
                    sig.modificarIntruccion(sig.getOperacion(), sig.getOp1(), act.getOp1(), sig.getDest());
                    instrucciones.remove(i);
                    return true;
                }
                if (act.getDest().equals(sig.getDest())) {
                    ctd.borrarVariable(act.getDest());
                    sig.modificarIntruccion(sig.getOperacion(), sig.getOp1(), sig.getOp2(), act.getOp1());
                    instrucciones.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    //ELIMINA CODIGO QUE NO SE EJECUTA (TODO CODIGO QUE ESTE DEPUES DE UN GOTO Y NO TENGA UNA EITUETA DE SALTO ATRAS ES CODIGO INACCESIBLE)
    private boolean CodigoInaccesible(int i, Instruccion act, Instruccion sig) {
        int aux;
        if ((act.getOperacion() == Operador.GOTO && !act.getDest().equals("run")) ) {
            aux = i + 1;
            boolean salir = true;
            while (salir) {//MIRAMOS LAS LINIAS DE CODIGO QUE ESTAN DEPUESD EL GOTO PARA VER SI SE ACCEDE O NO 
                Instruccion ini = instrucciones.get(aux);
                if (ini.getOperacion() == Operador.SKIP) {
                    int j = 0;
                    while (salir && j < instrucciones.size() - 1) {//MIRAMOS SI SE USA EL SKIP O NO PARA BORRARLO

                        Instruccion ins = instrucciones.get(j);
                        if (!ins.getOperacion().equals(Operador.SKIP) && ins.getDest().equals(ini.getDest())) {
                            salir = false;
                        }
                        j++;
                    }
                }
                if (salir) {//SOLO SERA FALSE CUANDO ENCONTREMOS UNA ETIQUETA QUE SE USE
                    instrucciones.remove(aux);
                }

            }
            return true;
        }
        return false;
    }

    //ELIMINA UN SALTO DE CODIGO DEPUES DE UAN ETIQUETA A LA QUE SE HA SALTADO PREVIAMENTE --- BRANCAMENTS SOBRE BRANCAMENTS
    private boolean SaltoSobreSalto(int i, Instruccion act, Instruccion sig) {
        if (sig.getOperacion() == Operador.GOTO && !sig.getDest().equals("run") && act.getOperacion() == Operador.SKIP) {
            String[] eti = {act.getDest(), sig.getDest()};
            instrucciones.remove(i);
            for (int j = 0; j < instrucciones.size() - 1; j++) {//RECORREMOS EL CODIGO PARA VER A QUE INSTRUCCIONS SALTA EL GOTO
                Instruccion ins = instrucciones.get(j);
                if (ins.getDest().equals(eti[0])) {
                    ins.modificarIntruccion(ins.getOperacion(), ins.getOp1(), ins.getOp2(), eti[1]);
                }
            }
            return true;
        }
        return false;
    }

    //ELIMINIA LOS LOS SALTOS DEPUES DE LOS IF PARA OPTIMIZARLOS
    private boolean RbrancamentsAdjacents(int i, Instruccion act, Instruccion sig) {

        if (act.esCondicional() && sig.getOperacion() == Operador.GOTO) {

            if (act.getOp1() != null && act.getOp2() != null) {//NO ME ACUERDO DE PORQUE ESTA EST IF CREO QUE SE PUEDE QUITAR PERO NO LO SE EXACTAMENTE, puede que para que no pete
                String op1 = act.getOp1();
                String op2 = act.getOp2();
                String dest = sig.getDest();

                switch (act.getOperacion()) { //CAMBIAMOS EL IF AL INVERSO PARA QUITAR EL GOTO

                    case NIGUALES:
                        act.modificarIntruccion(Operador.IGUALES, op1, op2, dest);
                        break;

                    case IGUALES:
                        act.modificarIntruccion(Operador.NIGUALES, op1, op2, dest);
                        break;

                    case MAYORQUE:
                        act.modificarIntruccion(Operador.MENORIGU, op1, op2, dest);
                        break;

                    case MAYORIGU:
                        act.modificarIntruccion(Operador.MENORQUE, op1, op2, dest);
                        break;

                    case MENORQUE:
                        act.modificarIntruccion(Operador.MAYORIGU, op1, op2, dest);
                        break;

                    case MENORIGU:
                        act.modificarIntruccion(Operador.MAYORQUE, op1, op2, dest);
                        break;
                }
                instrucciones.remove(i + 1);
                return true;
            }
        }
        return false;
    }

    //NO SE COMO FUNCIONA 
    private boolean ReducciondeFuerza(int i, Instruccion act) {

        if (act.getOperacion().equals(Operador.MULT)) {
            Variable op1 = ctd.getVariable(act.getOp1());
            if (op1 == null) {
                int x = Integer.valueOf(act.getOp1());
                if (x != 0 && ((x & (x - 1)) == 0)) {
                    x = x / 2;
                    act.modificarIntruccion(Operador.DESPLAZAR_BITS, String.valueOf(x), act.getOp2(), act.getDest());
                    return true;
                }
            }
            Variable op2 = ctd.getVariable(act.getOp2());
            if (op2 == null) {
                int x = Integer.valueOf(act.getOp2());
                if (x != 0 && ((x & (x - 1)) == 0)) {
                    x = x / 2;
                    act.modificarIntruccion(Operador.DESPLAZAR_BITS, String.valueOf(x), act.getOp1(), act.getDest());
                    return true;
                }
            }
        } else if (act.getOperacion().equals(Operador.DIV)) {
            Variable op2 = ctd.getVariable(act.getOp2());
            if (op2 == null) {
                int x = Integer.valueOf(act.getOp2());
                if (x != 0 && ((x & (x - 1)) == 0)) {
                    x = -(x / 2);
                    act.modificarIntruccion(Operador.DESPLAZAR_BITS, String.valueOf(x), act.getOp1(), act.getDest());
                    return true;
                }
            }
        }
        return false;
    }

    //ELIMINAMOS LAS OPERACIONES QUE SEAN CONSTANTES, ES DECIR, AQUELLAS OPERACIONES DE LAS CUALES SABEMOS AMBOS OPERANDOS A LA HORA DE COMPOILACION
    private boolean OperacionesConstantes(int i, Instruccion act) {
        Variable op1 = ctd.getVariable(act.getOp1());
        int aux;
        if (op1 == null) {
            Variable op2 = ctd.getVariable(act.getOp2());
            if (op2 == null) {
                switch (act.getOperacion()) {
                    case SUMA:
                        Variable dest = ctd.getVariable(act.getDest());

                        aux = Integer.valueOf(act.getOp1()) + Integer.valueOf(act.getOp2());
                        act.modificarIntruccion(Operador.ASIG, String.valueOf(aux), null, act.getDest());

                        return true;
                    case RESTA:
                        aux = Integer.valueOf(act.getOp1()) - Integer.valueOf(act.getOp2());
                        act.modificarIntruccion(Operador.ASIG, String.valueOf(aux), null, act.getDest());
                        return true;
                    case DIV:
                        aux = Integer.valueOf(act.getOp1()) / Integer.valueOf(act.getOp2());
                        act.modificarIntruccion(Operador.ASIG, String.valueOf(aux), null, act.getDest());
                        return true;
                    case MULT:
                        aux = Integer.valueOf(act.getOp1()) * Integer.valueOf(act.getOp2());
                        act.modificarIntruccion(Operador.ASIG, String.valueOf(aux), null, act.getDest());
                        return true;
                    case AND:
                        if (act.getOp1().equals("-1") && act.getOp2().equals("-1")) {
                            act.modificarIntruccion(Operador.ASIG, "-1", null, act.getDest());
                        } else {
                            act.modificarIntruccion(Operador.ASIG, "0", null, act.getDest());
                        }
                        return true;
                    case OR:
                        if (act.getOp1().equals("0") && act.getOp2().equals("0")) {
                            act.modificarIntruccion(Operador.ASIG, "0", null, act.getDest());
                        } else {
                            act.modificarIntruccion(Operador.ASIG, "-1", null, act.getDest());
                        }
                        return true;

                    case IGUALES:
                        if (act.getOp1().equals(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                    case MAYORQUE:
                        if (Integer.valueOf(act.getOp1()) > Integer.valueOf(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                    case MENORQUE:
                        if (Integer.valueOf(act.getOp1()) < Integer.valueOf(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                    case MAYORIGU:
                        if (Integer.valueOf(act.getOp1()) >= Integer.valueOf(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                    case MENORIGU:
                        if (Integer.valueOf(act.getOp1()) <= Integer.valueOf(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                    case NIGUALES:
                        if (!act.getOp1().equals(act.getOp2())) {
                            act.modificarIntruccion(Operador.GOTO, null, null, act.getDest());
                        } else {
                            instrucciones.remove(i);
                        }
                        return true;
                }
            }
        }
        return false;
    }

    private boolean esTemporal(String variable) {
        if (variable.startsWith("t")) {
            try {
                String aux = variable.substring(1);
                Integer.parseInt(aux);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
