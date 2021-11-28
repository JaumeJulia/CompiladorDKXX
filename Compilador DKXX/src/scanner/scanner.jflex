package scanner;

import java.io.*;
import java_cup.runtime.*;

import java_cup.runtime.SymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import parser.ParserSym;

%%

%cup

%public
%class Scanner

%integer
%line
%column

//%eofval{
//    return symbol(ParserSym.EOF);
//%eofval}

// Declaraciones

entero = [\-\+]?[1-9][0-9]* | [\-\+]?0
variable = [A-Za-z_][A-Za-z_0-9]*

//ws           = [' '|'\t']+
//endline      = ['\r'|'\n'|"\r\n"]+

WS = [ \t\r\n] // Separadores de tokens.

%{

    private ComplexSymbol symbol(int type) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type);
    }

    private Symbol symbol(int type, Object value) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type, value);
    }

%}

%%
// Reglas y Acciones

// palabras resevadas
";"         { return symbol(ParserSym.PUNTYCOMA);}

// aritmeticos
"+"         { return symbol(ParserSym.SUMA); }
"-"         { return symbol(ParserSym.RESTA); }
"*"         { return symbol(ParserSym.MULT); }
"/"         { return symbol(ParserSym.DIV); }

// parentesis y brackets
"("         { return symbol(ParserSym.LPAREN); }
")"         { return symbol(ParserSym.RPAREN); }
"{"         { return symbol(ParserSym.LKEY); }
"}"         { return symbol(ParserSym.RKEY); }

// logicos
"true"      { return symbol(ParserSym.BOLEAN, 1.0); }
"false"     { return symbol(ParserSym.BOLEAN, 0.0); }
"<="        { return symbol(ParserSym.MENORIGU); }
">="        { return symbol(ParserSym.MAYORIGU); }
">"         { return symbol(ParserSym.MAYORQUE); }
"<"         { return symbol(ParserSym.MENORQUE); }
"=="        { return symbol(ParserSym.IGUALES); }
"!="        { return symbol(ParserSym.NIGUALES); }
"!"         { return symbol(ParserSym.NOT); }
"&"         { return symbol(ParserSym.AND); }
"|"         { return symbol(ParserSym.OR); }

"="         { return symbol(ParserSym.IGUAL); }

// separador del programa
"main"      { return symbol(ParserSym.MAIN); }
"declare"   { return symbol(ParserSym.DECLARE); }

// condicional y bucle
"if"        { return symbol(ParserSym.IF); }
"while"     { return symbol(ParserSym.WHILE); }

// variables y funciones
"int"       { return symbol(ParserSym.INT); }
"bool"      { return symbol(ParserSym.BOOL); }
"const"     { return symbol(ParserSym.CONST); }
"function"  { return symbol(ParserSym.FUNCTION); }
"return"    { return symbol(ParserSym.RETRN); }

// entrada y salida
"output"    { return symbol(ParserSym.OUT); }
"input"     { return symbol(ParserSym.IN); }

// no terminales
{entero}    { return symbol(ParserSym.NUMERO, this.yytext()); }
{variable}  { return symbol(ParserSym.ID, this.yytext()); }
{WS}        {}
//{endline}   {}


//Gestión de errores
//Acciones post identificación
//Gestión de comentarios
