package Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import scanner.Scanner;

/**
 *
 * @author felix
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Indica un fitxer amb les dades d'entrada");
            System.exit(0);
        }
        try {
            FileReader in = new FileReader(args[0]);

            Scanner scanner = new Scanner(in);
            scanner.yylex();   // <- El mètode d'invocació per començar a parsejar el document

        } catch (FileNotFoundException e) {
            System.err.println("El fitxer d'entrada '" + args[0] + "' no existeix");
        } catch (IOException e) {
            System.err.println("Error processant el fitxer d'entrada");
        }
    }

}
