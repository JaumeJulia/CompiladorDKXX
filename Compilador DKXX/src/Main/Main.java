package Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;
import scanner.Scanner;
import parser.Parser;

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
            Parser parser = new Parser(scanner, sf);
            parser.parse();

        } catch (FileNotFoundException e) {
            System.err.println("El fitxer d'entrada '" + args[0] + "' no existeix");
        } catch (IOException e) {
            System.err.println("Error processant el fitxer d'entrada");
        }
    }

}
