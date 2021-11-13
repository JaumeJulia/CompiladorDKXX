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

ws           = [' '|'\t']+
endline      = ['\r'|'\n'|"\r\n"]+

//WS = [ \t\r\n] // Separadores de tokens.

%{

    private Complex symbol(int type) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type);
    }

    private Symbol symbol(int type, Object value) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type, value);
    }

%}

%%
// Reglas y Acciones

// palabras resevadas
";"         { return new symbol(ParserSym.PUNTYCOMA);}

// aritmeticos
"+"         { return new symbol(ParserSym.SUMA); }
"-"         { return new symbol(ParserSym.RESTA); }
"*"         { return new symbol(ParserSym.MULT); }
"/"         { return new symbol(ParserSym.DIV); }

// parentesis y brackets
"("         { return new symbol(ParserSym.LPAREN); }
")"         { return new symbol(ParserSym.RPAREN); }
"{"         { return new symbol(ParserSym.LKEY); }
"}"         { return new symbol(ParserSym.RKEY); }

// logicos
"true"      { return new symbol(ParserSym.BOLEAN, 1.0); }
"false"     { return new symbol(ParserSym.BOLEAN, 0.0); }
"<="        { return new symbol(ParserSym.MENORIGU); }
">="        { return new symbol(ParserSym.MAYORIGU); }
">"         { return new symbol(ParserSym.MAYORQUE); }
"<"         { return new symbol(ParserSym.MENORQUE); }
"=="        { return new symbol(ParserSym.IGUALES); }
"!="        { return new symbol(ParserSym.NIGUALES); }
"!"         { return new symbol(ParserSym.NOT); }
"&"         { return new symbol(ParserSym.AND); }
"|"         { return new symbol(ParserSym.OR); }

"="         { return new symbol(ParserSym.IGUAL); }

// separador del programa
"main"      { return new symbol(ParserSym.MAIN); }
"declare"   { return new symbol(ParserSym.DECLARE); }

// condicional y bucle
"if"        { return new symbol(ParserSym.IF); }
"while"     { return new symbol(ParserSym.WHILE); }

// variables y funciones
"int"       { return new symbol(ParserSym.INT); }
"bool"      { return new symbol(ParserSym.BOOL); }
"const"     { return new symbol(ParserSym.CONST); }
"function"  { return new symbol(ParserSym.FUNCTION); }
"return"    { return new symbol(ParserSym.RETRN); }

// entrada y salida
"output"    { return new symbol(ParserSym.OUT); }
"input"     { return new symbol(ParserSym.IN); }

// no terminales
{entero}    { return new symbol(ParserSym.VALOR, this.yytext()); }
{variable}  { return new symbol(ParserSym.ID, this.yytext()); }
{ws}        {}
//{endline}   {}


//Gestión de errores
//Acciones post identificación
//Gestión de comentarios
