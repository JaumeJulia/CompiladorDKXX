package Main;

import ArbolSintactico.ArbolSintactico;
import CodigoIntermedio.CodigoTresDirecciones;
import Ensamblador.Codigo68k;
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
 * @author felix
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Indica un fitxer amb les dades d'entrada");
            System.exit(0);
        }
        try {
            FileReader in = new FileReader(args[0]);
            Scanner scanner = new Scanner(in);
            SymbolFactory sf = new ComplexSymbolFactory();
            parser parser = new parser(scanner, sf);
            parser.parse();
            ArbolSintactico arbol = parser.getArbol();
            CodigoTresDirecciones ctd = arbol.GenerarCTD();
            
            String nombre = FileName(args[0]);
            WriteFile("out/" + nombre + ".ctd", ctd.toString());
            WriteFile("out/" + nombre + ".tv", ctd.printTv());
            WriteFile("out/" + nombre + ".tp", ctd.printTp());
            
            Codigo68k c68k = new Codigo68k();
            c68k.generar(ctd);
            WriteFile("out/" + nombre + ".X68", c68k.toString());
            
        } catch (FileNotFoundException e) {
            System.err.println("El fitxer d'entrada '" + args[0] + "' no existeix");
        } catch (IOException e) {
            System.err.println("Error processant el fitxer d'entrada");
        }
    }

    public static void WriteFile(String Path, String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(Path));
            writer.write(data);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String FileName(String path){
         int idxI = path.lastIndexOf("/",path.length());
         int idxF = path.lastIndexOf(".",path.length());
         return path.substring(idxI + 1, idxF);
    }

}
