/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

/**
 *
 * @author Usuario
 */
public class Parametro {
    Tipo tipo;
    String nombre;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Parametro(Tipo tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }
    
}
