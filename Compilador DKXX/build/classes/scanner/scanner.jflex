package scanner;

%%

%public
%class Scanner
%standalone

// Declaraciones

entero = [\-\+]?[1-9][0-9]* | [\-\+]?0
variable = [A-Za-z_][A-Za-z_0-9]*

WS = [ \t\r\n] // Separadores de tokens.

%%
// Reglas y Acciones

// palabras resevadas
";"         {System.out.println("punto y coma");}

// aritmeticos
"="         {System.out.println("asignacion");}
"+"         {System.out.println("suma");}
"-"         {System.out.println("resta");}
"*"         {System.out.println("multiplicacion");}
"/"         {System.out.println("division");}

// parentesis y brackets
"("         {System.out.println("lparentesis");}
")"         {System.out.println("rparentesis");}
"{"         {System.out.println("lllave");}
"}"         {System.out.println("rllave");}

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
"main"      {System.out.println("principal");}
"declare"   {System.out.println("declaraciones");}

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
"output"    {System.out.println("salida");}
"input"     {System.out.println("entrada");}

// no terminales
{entero}    {System.out.println("entero " + yytext());}
{variable}  {System.out.println("variable " + yytext());}
{WS}        {}







