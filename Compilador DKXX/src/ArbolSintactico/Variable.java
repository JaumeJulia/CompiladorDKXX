/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolSintactico;

/**
 *
 * @author felix
 */
public class Variable {

    String id;
    Tipo tipo;
    int procedimiento;

    public Variable(String id, Tipo tipo, int procedimiento) {
        this.id = id;
        this.tipo = tipo;
        this.procedimiento = procedimiento;
    }
    
    public String getID(){
        return id;
    }
}
