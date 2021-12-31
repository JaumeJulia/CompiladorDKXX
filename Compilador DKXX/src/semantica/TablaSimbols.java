/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

import semantica.Descripcion.*;
import java.util.HashMap;

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
                System.exit(0);
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

//    public void salirBloque() {
//        if (nivel <= 0) {
//            System.out.println("Error de compilación en la tabla de símbolos");
//            System.exit(1);
//        }
//
//        int puntero_expansion_final = tAmbito.get(nivel - 1);
//        for (int i = tAmbito.get(nivel); i < puntero_expansion_final; i++) {
//            tDescripcion.put(tExpansion.get(i).getId(),
//                    new DataDescripcion(tExpansion.get(i).getDescripcion(),
//                            tExpansion.get(i).getNivel()));
//        }
//        nivel--;
//    }
    
    public void salirBloque() {
        if (nivel <= 0) {
            System.out.println("Error de compilación en la tabla de símbolos");
            System.exit(1);
        }

        int idxte_inicial = tAmbito.get(nivel);
        tAmbito.put(nivel, nivel-1);
        int idxte_final = tAmbito.get(nivel);
        
        while(idxte_inicial > idxte_final){
            if(tExpansion.get(idxte_inicial).getNivel() != -1){
                tDescripcion.put(tExpansion.get(idxte_inicial).getId(),
                    new DataDescripcion(tExpansion.get(idxte_inicial).getDescripcion(),
                        tExpansion.get(idxte_inicial).getNivel(),
                        tExpansion.get(idxte_inicial).getNext()));
            }
            idxte_inicial--;
        }
    }

    public void poner_param(String idprog, String idparam, Descripcion d) {
        d = tDescripcion.get(idprog).getDescripcion();
        if (!d.getTd().equals(tipo_descripcion.dprog)) {
            System.out.println(idprog + " no es un procedimiento");
            System.exit(0);
        }
        int idxte = tDescripcion.get(idprog).getFirst();
        int idxtep = 0;
        
        while(idxte != 0 && !tExpansion.get(idxte).getId().equals(idparam)){
            idxtep = idxte;
            idxte = tExpansion.get(idxte).getNext();
        }
        if(idxte != 0){
            System.out.println("Se ha redeclarado un parámetro con un mismo identificador: "+idparam);
            System.exit(0);
        }
        idxte = tAmbito.get(nivel);
        idxte++;
        tAmbito.put(nivel, idxte);
        tExpansion.put(idxte, new DataExpansion(idprog,idparam,-1,d,0));
        if(idxtep == 0){
            tDescripcion.get(idprog).setFirst(idxte);
        } else {
            tExpansion.get(idxtep).setNext(idxte);
        }
    }
    
    public int first_arg(String id){
        Descripcion d = tDescripcion.get(id).getDescripcion();
        if(d.getTd() != tipo_descripcion.darg || !d.getId().equals(id)){
            System.out.println(id+" no tiene parámetros");
            System.exit(1);
        }
        return tDescripcion.get(id).getFirst();
    }
    
    public int next_arg(int idx){
        if(tExpansion.get(idx).getNext() == 0){
            System.out.println("El índice "+idx+" no apunta aningún argumento");
            System.exit(idx);
        }
        return tExpansion.get(idx).getNext();
    }
    
    public boolean last_arg(int idx){
        return tExpansion.get(idx).getNext() == 0;
    }
    
    public Descripcion consulta_arg(int idx){
        return tExpansion.get(idx).getDescripcion();
    }    
}
