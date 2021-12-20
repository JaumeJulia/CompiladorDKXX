/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

/**
 *
 * @author pujol
 */
public class AtributoDescripcion {
    
        public enum nom_atributo{
            nv,
            tipo,
            valor,
            dt,
            np,
            modo,
            nombre
        }
        
        public enum tipo_atributo{
            integer,
            bool,
            
        }
        
        private nom_atributo atributo;
        private tipo_atributo tipo;
        
        public AtributoDescripcion(nom_atributo atrib, tipo_atributo tipo){
            this.atributo = atrib;
            this.tipo = tipo;
        }
    
}
