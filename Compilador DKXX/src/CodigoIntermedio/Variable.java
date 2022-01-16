/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import ArbolSintactico.Tipo;

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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(int procedimiento) {
        this.procedimiento = procedimiento;
    }
    
    
    
    @Override
    public String toString(){
        return id + " " + tipo + " " + procedimiento;
    }
}
