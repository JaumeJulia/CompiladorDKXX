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

    public ErrorLexico(String token, int linea) {
        this.token = token;
        this.linea = linea;
    }
    
    @Override
    public String toString(){
        return "\t - \"" + token + "\" encontrado en linea " + linea;
    }
}
