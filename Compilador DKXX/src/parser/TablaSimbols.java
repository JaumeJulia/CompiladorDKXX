/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.HashMap;

/**
 *
 * @author pujol
 */
public class TablaSimbols {

    private int nivel;

    private HashMap<Integer, DataExpansion> tExpansion;
    private HashMap<String, DataDescripcion> tDescripcion;
    private HashMap<Integer, Integer> tAmbito;

    public TablaSimbols() {
        nivel = 1;
        tExpansion = new HashMap<>();
        tDescripcion = new HashMap<>();
        tAmbito = new HashMap<>();
    }

    public void vaciar() {
        nivel = 0;
        tExpansion.clear();
        tDescripcion.clear();
        tAmbito.replace(nivel, 0);
        nivel = 1;
        tAmbito.replace(nivel, 0);
    }

    public void poner(String simbol) {
        DataDescripcion dataDesc;
        if (!tDescripcion.containsKey(simbol)) {
            dataDesc = new DataDescripcion(simbol, nivel);
            tDescripcion.put(simbol, dataDesc);
        } else {
            dataDesc = tDescripcion.get(simbol);
            if (dataDesc.getNivel() == nivel) {
                //error
            } else {
                tAmbito.replace(nivel, tAmbito.get(nivel) + 1);
                tExpansion.put(tAmbito.get(nivel) + 1, 
                        new DataExpansion(simbol, nivel, 
                                dataDesc.getDescripcion()));
            }
        }

    }

    public void entrar() {
        nivel = nivel + 1;
        tAmbito.replace(nivel, tAmbito.get(nivel-1));
    }

    public String consulta(String id) {
        return tDescripcion.get(id).getDescripcion();
    }

    public void salirBloque() {
        if(nivel <= 0){
            //error FATAL
        } else {
            for(int i = tAmbito.get(nivel); i < tAmbito.get(nivel-1); i++){
                tDescripcion.put(tExpansion.get(i).getId(),
                        new DataDescripcion(tExpansion.get(i).getDescripcion(),
                                tExpansion.get(i).getNivel()));
            }
            nivel--;
        }
    }
}
