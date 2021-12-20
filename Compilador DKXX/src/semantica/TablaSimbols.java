/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import semantica.Descripcion.*;

/**
 *
 * @author pujol
 */
public class TablaSimbols {

    private int nivel;

    private HashMap<Integer, DataExpansion> tExpansion;
    private HashMap<String, DataDescripcion> tDescripcion;
    private HashMap<Integer, Integer> tAmbito;  //guarda la ultima clave de la tExpansion indexados por niveles en la tAmbito

    public TablaSimbols() {
        nivel = 1;
        tExpansion = new HashMap<>();
        tDescripcion = new HashMap<>();
        tAmbito = new HashMap<>();
    }

    public void vaciar() {
        nivel = 0;
        tDescripcion.clear();
        tAmbito.put(nivel, 0);
        tAmbito.clear();
        nivel = 1;
        tAmbito.put(nivel, 0);
    }

//    public void poner(String simbol) {
//        DataDescripcion dataDesc;
//        if (!tDescripcion.containsKey(simbol)) {
//            dataDesc = new DataDescripcion(simbol, nivel);
//            tDescripcion.put(simbol, dataDesc);
//        } else {
//            dataDesc = tDescripcion.get(simbol);
//            if (dataDesc.getNivel() == nivel) {
//                //error
//            } else {
//                tAmbito.replace(nivel, tAmbito.get(nivel) + 1);
//                tAmbit.add(tAmbit.indexOf(nivel), nivel + 1);
//                tExpansion.put(tAmbito.get(nivel) + 1,
//                        new DataExpansion(simbol, nivel,
//                                dataDesc.getDescripcion()));
//            }
//        }
//
//    }
    public void poner_simbolo(String simbol, Descripcion d) {
        DataDescripcion dataDesc;
        if (tDescripcion.containsKey(simbol)) {
            dataDesc = tDescripcion.get(simbol);
            if (dataDesc.getNivel() == nivel) {
                System.out.println("Se ha producido una redifinición con un mismo identificador: "+simbol);
            }
            tAmbito.put(nivel, tAmbito.get(nivel) + 1);
            tExpansion.put(tAmbito.get(nivel), new DataExpansion(simbol, nivel, d));
        }
        dataDesc = new DataDescripcion(d, nivel);
        tDescripcion.put(simbol, dataDesc);
    }

    public void entrarBloque() {
        nivel = nivel + 1;
        tAmbito.put(nivel, tAmbito.get(nivel - 1));
    }

    public Descripcion consulta(String id) {
        return tDescripcion.get(id).getDescripcion();
    }

    public void salirBloque() {
        if (nivel <= 0) {
            System.out.println("Error de compilación en la tabla de símbolos");
        }

        int puntero_expansion_final = tAmbito.get(nivel - 1);
        for (int i = tAmbito.get(nivel); i < puntero_expansion_final; i++) {
            tDescripcion.put(tExpansion.get(i).getId(),
                    new DataDescripcion(tExpansion.get(i).getDescripcion(),
                            tExpansion.get(i).getNivel()));
        }
        nivel--;
    }

    public void ponerParam(String idprog, String idparam, Descripcion d) {
        d = tDescripcion.get(idprog).getDescripcion();
        if (d.getTipoDescripcion().equals(tipo_descripcion.dproc)) {
            System.out.println(idprog + " no es un procedimiento");
        }
        //continuará
    }
}
