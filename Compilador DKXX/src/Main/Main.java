package Main;

import ArbolSintactico.ArbolSintactico;
import CodigoIntermedio.CodigoTresDirecciones;
import Ensamblador.Codigo68k;
import Optimizacion.Optimizacion;
import Semantico.Semantico;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;
import scanner.Scanner;
import parser.parser;

/**
 *
 * @author Felix Lluis Aguilar Ferrer, Jaume Julià Vallespir, Francisco José
 * Muñoz Navarro, Antonio Pujol Villegas
 */
public class Main {

    /**
     * Inicio del compilador.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Verificacion de las entradas del compilador.
        if (args.length < 1) {
            System.err.println("-> Indica un fitxer amb les dades d'entrada");
            System.exit(0);
        }
        if (!args[0].endsWith(".dkxx")) {
            System.err.println("-> el Archivo no es del tipo dkxx");
            System.exit(0);
        }
        String nombre = FileName(args[0]);
        String out = "out/";
        if (args.length >= 2) {
            out = args[1];
        }
        System.out.println("-> Compilando programa " + nombre
                + "\n\tin: " + args[0] + "\n\tout: " + out + "\n-> Parser");

        try {
            // Scanner.
            FileReader in = new FileReader(args[0]);
            Scanner scanner = new Scanner(in);

            // Parser.
            SymbolFactory sf = new ComplexSymbolFactory();
            parser parser = new parser(scanner, sf);
            try {
                parser.parse();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Sintactico.
            Semantico semantico = parser.getSemantico();

            // Errores.
            if (scanner.hayErrores() || parser.hayErrores() || semantico.hayErrores()) {
                String s = "";
                if (scanner.hayErrores()) {
                    s += "\tErrores lexico:\n" + scanner.toStringErrores();
                }
                if (parser.hayErrores()) {
                    s += "\tErrores sintactico:\n" + parser.toStringErrores();
                }
                if (semantico.hayErrores()) {
                    s += "\tErrores semantico:\n" + semantico.toStringErrores();
                }
                s += "-> No se pudo proseguir con la compilacion";
                WriteFile(out + nombre + "_errrores.txt", s);
                System.out.println(s);
                System.exit(0);
            }

            ArbolSintactico arbol = parser.getArbol(); // Arbol Sintactico.

            WriteFile(out + nombre + "_tokens.txt", scanner.toStringTokens());
            WriteFile(out + nombre + "_tabla_simbolos.txt", semantico.ts.toString());

            System.out.println("\tOK");

            // Codigo Intermedio.
            CodigoTresDirecciones ctd = arbol.GenerarCTD();
            WriteFile(out + nombre + "_codigo_intermedio.txt", ctd.toString());
            WriteFile(out + nombre + "_tabla_variables.txt", ctd.printTv());
            WriteFile(out + nombre + "_tabla_procedimientos.txt", ctd.printTp());

            // Codigo Ensamblador.
            Codigo68k c68k = new Codigo68k();
            c68k.generar(ctd);
            WriteFile(out + nombre + "_ensamblador.X68", c68k.toString());

            // Codigo Intermedio Optimizado.
            Optimizacion opt = new Optimizacion(ctd);
            opt.optimizar();
            WriteFile(out + nombre + "_codigo_intermedio_optimizado.txt", ctd.toString());

            // Codigo Ensambladorr Optimizado.
            c68k.generar(ctd);
            WriteFile(out + nombre + "_ensamblador_optimizado.X68", c68k.toString());

            // Fin de la compilacion.
            System.out.println("-> Compilación completada");

        } catch (FileNotFoundException ex) {
            System.out.println("El archivo de entrada " + args[0] + " o el path de salida " + out + " no es valido");
        } catch (IOException ex) {
            System.out.println("No se ha podido leer o escribir el archivo");
        }
    }

    /**
     * Escritura de un string en un archivo.
     *
     * @param Path direccion de destino del archivo.
     * @param data contenido del archivo.
     * @throws IOException
     */
    public static void WriteFile(String Path, String data) throws IOException {
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(Path));
        writer.write(data);
        writer.close();
    }

    /**
     * Devuelve el nombre del archivo sin path ni extension.
     *
     * @param path direccion del archivo.
     * @return
     */
    public static String FileName(String path) {
        int idxI = path.lastIndexOf("/", path.length());
        int idxF = path.lastIndexOf(".", path.length());
        return path.substring(idxI + 1, idxF);
    }
}
