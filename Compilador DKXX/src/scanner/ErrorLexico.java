/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 *
 * @author Felix
 */
public class ErrorLexico {
    private String token;
    private int linea;
    private int columna;

    public ErrorLexico(String token, int linea, int columna) {
        this.token = token;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public String toString(){
        return "\t - \"" + token + "\" encontrado en linea " + linea + " columna " + columna;
    }
}
