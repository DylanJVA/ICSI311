/**
 *  Basic Class
 *  Reads a text file and prints a 'lexed and tokenized' version of each line
 * @author Dylan VanAllen
 */
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Basic {

    
    public static StatementsNode getParsedLine(List<Token> tokens) {
        try {
            Parser p = new Parser(tokens);
            StatementsNode statements = p.parse();
            return statements;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
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
            StatementsNode parsedStatements = new StatementsNode(new ArrayList<StatementNode>());
            for (int i = 0; i < lines.size(); i++)
            {
                try { //checks if lexer will encounter any illegal symbols
                    List<Token> lineTokenList = Lexer.lex(lines.get(i));
                    tokenList.add(lineTokenList);
                    StatementsNode statements = getParsedLine(tokenList.get(i));
                    parsedStatements.addToList(statements.getStatements());
                } catch (Exception e) {
                    System.out.println(e.getMessage()+" Line ignored: '"+lines.get(i)+"'.");
                    badLines.add(i);
                }

            }
            
            Interpreter interpreter = new Interpreter(parsedStatements);
            interpreter.initialize();
            System.out.println(interpreter.getStatements());

        } catch (IOException openFileException) {
            System.out.println("Could not open file with path: "+args[0]);
        }
    }
}

