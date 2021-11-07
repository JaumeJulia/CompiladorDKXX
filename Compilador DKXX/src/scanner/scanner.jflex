package scanner;

import java.io.*;
import java_cup.runtime.*;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import parser.ParserSym;

%%

%cup

%public
%class Scanner
%int
// %standalone

// Declaraciones

entero = [\-\+]?[1-9][0-9]* | [\-\+]?0
variable = [A-Za-z_][A-Za-z_0-9]*

WS = [ \t\r\n] // Separadores de tokens.

%{

    private Complex symbol(int type) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type);
    }

    private Symbol symbol(int type, Object value) {
        return new ComplexSymbol(ParserSym.terminalNames[type], type, value);
    }

}%

%%
// Reglas y Acciones

// palabras resevadas
";"         { return new symbol(ParserSym.PUNTYCOMA);}

// aritmeticos
"="         {System.out.println("asignacion");}
"+"         { return new symbol(ParserSym.SUMA); }
"-"         {System.out.println("resta");}
"*"         {System.out.println("multiplicacion");}
"/"         {System.out.println("division");}

// parentesis y brackets
"("         { return new symbol(ParserSym.LPAREN); }
")"         { return new symbol(ParserSym.RPAREN); }
"{"         { return new symbol(ParserSym.LKEY); }
"}"         { return new symbol(ParserSym.RKEY); }

// logicos
"true"      {System.out.println("Verdadero");}
"false"     {System.out.println("Falso");}
">"         {System.out.println("Mayor");}
"<"         {System.out.println("Menor");}
"<="        {System.out.println("Menor o igual");}
">="        {System.out.println("Mayor o igual");}
"=="        {System.out.println("Iguales");}
"!="        {System.out.println("no Iguales");}
"!"         {System.out.println("no");}
"&"         {System.out.println("and");}
"|"         {System.out.println("or");}

// separador del programa
"main"      { return new symbol(ParserSym.MAIN); }
"declare"   { return new symbol(ParserSym.DECLARE); }

// condicional y bucle
"if"        {System.out.println("SI");}
"while"     {System.out.println("Mientras");}

// variables y funciones
"int"       {System.out.println("int");}
"bool"      {System.out.println("bool");}
"const"     {System.out.println("const");}
"function"  {System.out.println("funcion");}
"return"    {System.out.println("retorno");}

// entrada y salida
"output"    { return new symbol(ParserSym.OUT); }
"input"     { System.out.println("entrada"); }

// no terminales
{entero}    { return new symbol(ParserSym.VALOR, this.yytext()); }
{variable}  { System.out.println("variable " + yytext()); }
{WS}        {}


//Gestión de errores
//Acciones post identificación
//Gestión de comentarios
