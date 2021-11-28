/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.symbols;

/**
 *
 * @author pujol
 */
public class SymbolOperacion extends SymbolBase{
    private String operacion;
    private boolean empty;
    
    public SymbolOperacion(){
        super();
    }
    
    public SymbolOperacion(String operacion, SymbolExpresion valorE){
        super("OPERACION", 0.0);
        this.operacion = operacion;
        this.value = valorE.value;  
    }
    
    public String getOperacion() {
        return this.operacion;
    }
}
