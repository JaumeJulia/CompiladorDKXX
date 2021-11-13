/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.symbols;

import parser.ParserSym;

/**
 *
 * @author pujol
 */
public class SymbolExpresion extends SymbolBase{
    
    public SymbolExpresion(){
        super("EXPRESION", 0.0);
    }
    
    public SymbolExpresion(Object param, SymbolOperacion Op){
        super("EXPRESION", 0.0);
        Double valor;
        
        if(Op.isEmpty()){
            valor = (Double)param;
        } else {
            switch (Op.getOperacion()) {
                case ParserSym.SUMA:
                    valor = (Double) param + (Double)Op.value;
                    break;
                case ParserSym.RESTA:
                    valor = (Double) param - (Double)Op.value;
                    break;
                default:
                    valor = 0.0;
            }
        }
        
        this.value = valor;
    }
    
}
