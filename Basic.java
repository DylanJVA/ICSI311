/**
 *  Basic Class
 *  Reads a text file and prints a 'lexed and tokenized' version of each line
 * @author Dylan VanAllen
 */
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Basic {

    public static void printLexemes(List<String> lines, List<Integer> badLines, List<List<Token>> tokenList) {
        System.out.println("Input ------> Output");
            
        int numRemoved = 0;
        for (int line : badLines) //loop for removing bad lines before printing
        {
            lines.remove(line-numRemoved); //must subtract number of lines removed from index or good lines will be removed
            numRemoved++;
        }
        for (int i = 0; i < lines.size(); i++)
        {
            System.out.print(lines.get(i)+" ------> ");
            for (Token token : tokenList.get(i))
                System.out.print(token.toString()+" ");
            System.out.println();
        }
    }

    public static void printParsedLine(String line, List<Token> tokens) {
        System.out.println("Input: "+line);
            System.out.print("Lexemes: ");
            for (Token token : tokens) System.out.print(token.toString()+" ");
            System.out.println();
            try {
                Parser p = new Parser(tokens);
                System.out.println("Parsed line: "+p.parse().toString());
                System.out.println("--------------------------------------");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
    }
    public static void main(String[] args) {
        if (args.length != 1)
        {

            System.out.println("Only one argument is allowed. Exiting.");
            System.exit(0);
        }
        Path filename = Paths.get(args[0]);
        try { //checks if file can be opened
            List<String> lines = Files.readAllLines(filename);
            List<List<Token>> tokenList = new ArrayList<List<Token>>(lines.size()); //master list of token lines
            ArrayList<Integer> badLines = new ArrayList<Integer>(); //list of lines that cannot be lexed
            for (int i = 0; i < lines.size(); i++)
            {
                try { //checks if lexer will encounter any illegal symbols
                    List<Token> lineTokenList = Lexer.lex(lines.get(i));
                    tokenList.add(lineTokenList);
                    printParsedLine(lines.get(i), tokenList.get(i));
                } catch (Exception e) {
                    System.out.println(e.getMessage()+" Line ignored: '"+lines.get(i)+"'.");
                    badLines.add(i);
                }
            }
            
        } catch (IOException openFileException) {
            System.out.println("Could not open file with path: "+args[0]);
        }
    }
}

