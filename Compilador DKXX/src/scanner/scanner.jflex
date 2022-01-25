package scanner;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import parser.sym;
import java.util.ArrayList;

%%

%cup

%public
%class Scanner

%integer
%line
%column

%eofval{
    return symbol(sym.EOF);
%eofval}


entero = [\-\+]?[1-9][0-9]* | [\-\+]?0
variable = [A-Za-z_][A-Za-z_0-9]*

coment = "#" [^\r\n]* [\r\n]?

WS = [ \t\r\n] // Separadores de tokens.

%{
    private ArrayList<Token> tokens = new ArrayList<>();
    private ArrayList<ErrorLexico> errores = new ArrayList<>();

    private ComplexSymbol symbol(int type) {
        ComplexSymbol s = new ComplexSymbol(sym.terminalNames[type], type);
        s.left = yyline;
        s.right = yycolumn;
        tokens.add(new Token(s));
        return s;
    }

    private ComplexSymbol symbol(int type, Object value) {
        ComplexSymbol s = new ComplexSymbol(sym.terminalNames[type], type, value);
        s.left = yyline;
        s.right = yycolumn;
        tokens.add(new Token(s, (String) value));
        return s;
    }
    
    public String toStringTokens() {
        String s = "";
        for (Token t : tokens) {
            s += t.toString() + "\n";
        }
        return s;
    }

    //Gestion de Errores.
    private void addError(String token, int linea) {
       errores.add(new ErrorLexico(token, linea));
    }

    public boolean hayErrores(){
        return !errores.isEmpty();
    }
    
    public String toStringErrores(){
        String s = "";
        for(ErrorLexico e : errores){
            s += e.toString() + "\n";
        }
        return s;
    }
%}

%%
// Reglas y Acciones

// palabras resevadas
";"         { return symbol(sym.PUNTYCOMA);}
","         { return symbol(sym.COMA); }

// aritmeticos
"="         { return symbol(sym.IGUAL); }
"+"         { return symbol(sym.SUMA); }
"-"         { return symbol(sym.RESTA); }
"*"         { return symbol(sym.MULT); }
"/"         { return symbol(sym.DIV); }

// parentesis y brackets
"("         { return symbol(sym.LPAREN); }
")"         { return symbol(sym.RPAREN); }
"{"         { return symbol(sym.LKEY); }
"}"         { return symbol(sym.RKEY); }

// logicos
"true"      { return symbol(sym.BOLEAN, "true"); }
"false"     { return symbol(sym.BOLEAN, "false"); }
"<="        { return symbol(sym.MENORIGU); }
">="        { return symbol(sym.MAYORIGU); }
">"         { return symbol(sym.MAYORQUE); }
"<"         { return symbol(sym.MENORQUE); }
"=="        { return symbol(sym.IGUALES); }
"!="        { return symbol(sym.NIGUALES); }
"&"         { return symbol(sym.AND); }
"|"         { return symbol(sym.OR); }

// separador del programa
"main"      { return symbol(sym.MAIN); }
"declare"   { return symbol(sym.DECLARE); }

// condicional y bucle
"if"        { return symbol(sym.IF); }
"while"     { return symbol(sym.WHILE); }

// variables y funciones
"int"       { return symbol(sym.INT); }
"bool"      { return symbol(sym.BOOL); }
"const"     { return symbol(sym.CONST); }
"function"  { return symbol(sym.FUNCTION); }
"return"    { return symbol(sym.RETRN); }

// entrada y salida
"output"    { return symbol(sym.OUT); }
"input"     { return symbol(sym.IN); }

// no terminales
{entero}    { return symbol(sym.NUMERO, this.yytext()); }
{variable}  { return symbol(sym.ID, this.yytext()); }

{coment}    {}
{WS}        {}

/* Gestion Errores */
[^]         {addError(yytext(), yyline); }