package parser.symbols;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pujol
 */
public class SymbolBase extends ComplexSymbol {
    private static int idAutoIncrement = 0;
    protected boolean empty;
    
    public SymbolBase(String variable, Double valor) {
        super(variable, idAutoIncrement++, valor);
        this.empty = false;
    }
    
    /**
     * Constructor per crear una instància buida, com a conseqüència d'un error
     * o una produció que deriva lambda.
     */
    public SymbolBase() {
        super("", idAutoIncrement++);
        empty = true;
    }
    
    /**
     * Mètode que permet determinar si la variable és buida (lambda) o bé perquè
     * hi ha un error semàntic.
     * @return true si és lambda/error false altrement
     */
    public boolean isEmpty() {
        return empty;
    }
    
 }
