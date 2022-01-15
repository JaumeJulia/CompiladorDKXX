/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

/**
 *
 * @author Felix
 */
public class Token {
    private ComplexSymbol simbolo;
    private String valor;
    
    public Token(ComplexSymbol simbolo){
        this.simbolo = simbolo;
    }
    
    public Token(ComplexSymbol simbolo, String valor){
        this.simbolo = simbolo;
        this.valor = valor;
    }

    @Override
    public String toString(){
        if(valor != null){
            return simbolo.toString() + " Valor: " + valor;
        }
        return simbolo.toString();
    }
    
    public ComplexSymbol getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(ComplexSymbol simbolo) {
        this.simbolo = simbolo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
