/**
 *  Lexer Class
 *  Utilizes a state machine to look through a string and return a list of Tokens
 * @author Dylan VanAllen
 */
import java.util.*;

public class Lexer {
    public enum states
    {
        SPACE, WHOLENUMBER, OPERATE, DECLARE, PAREN, STRING, WORD, LABEL, DECIMAL, PRINT
    }
    //determines if a particular token Type needs a value
    public static boolean needsValue(Token.Type Type) {
        switch(Type)
            {         
                case NUMBER:
                case STRING:
                case IDENTIFIER:
                case LABEL:
                    return true;
                default:
                    return false;                 
            }
        }
    public static boolean charIsAllowed(states state, char c) {
        if (c == ':' || c == '$' || c == '%')
        {
            if (state == states.WORD || state == states.STRING) return true;
            else return false;
        }
        else if (c == '.' && state == states.DECIMAL) return false;
        else return true;
    }
    //looks at the current character and returns an associated state to change into
    public static states getStateFromChar(char c) throws Exception {
        if (Character.isDigit(c)) return states.WHOLENUMBER;
        else if (c == '.') return states.DECIMAL;
        else if (Character.isLetter(c) || c == '$' || c == '%') return states.WORD;
        else if (c == '+' || c == '-' || c == '*' || c == '/') return states.OPERATE;
        else if (c == '=' || c == '<' || c == '>') return states.DECLARE;
        else if (c == '(' || c == ')') return states.PAREN;
        else if (c == '"') return states.STRING;
        else if (c == ':') return states.LABEL;
        else if (c == ' ' || c == '\t') return states.SPACE;
        else if (c == ',') return states.PRINT;
        else throw new Exception("Unknown symbol entered: '" + c + "'. ");
    }
    //looks at the current character and returns an associated Token Type
    public static Token.Type getTypeFromChar(char c) throws Exception {
        if (Character.isDigit(c) || c == '.') return Token.Type.NUMBER;
        else if (Character.isLetter(c) || c == '$' || c == '%') return Token.Type.IDENTIFIER;
        else if (c == '+') return Token.Type.PLUS;
        else if (c == '-') return Token.Type.MINUS;
        else if (c == '*') return Token.Type.TIMES;
        else if (c == '/') return Token.Type.DIVIDE;
        else if (c == '=') return Token.Type.EQUALS;
        else if (c == '<') return Token.Type.LESSTHAN;
        else if (c == '>') return Token.Type.GREATERTHAN;
        else if (c == '(') return Token.Type.LPAREN;
        else if (c == ')') return Token.Type.RPAREN;
        else if (c == '"') return Token.Type.STRING;
        else if (c == ':') return Token.Type.LABEL;
        else if (c == ' ' || c == '\t') return Token.Type.SPACE;
        else if (c == ',') return Token.Type.COMMA;
        else throw new Exception("Unknown symbol entered: '" + c + "'. ");
    }
    public static List<Token> lex(String s) throws Exception{
        HashMap<String, Token.Type> knownWords = new HashMap<String, Token.Type>();
        knownWords.put("PRINT", Token.Type.PRINT);
        knownWords.put("INPUT", Token.Type.INPUT);
        knownWords.put("DATA", Token.Type.DATA);
        knownWords.put("READ", Token.Type.READ);
        knownWords.put("FOR", Token.Type.FOR);
        knownWords.put("TO", Token.Type.TO);
        knownWords.put("NEXT", Token.Type.NEXT);
        knownWords.put("GOSUB", Token.Type.GOSUB);
        knownWords.put("STEP", Token.Type.STEP);
        knownWords.put("RETURN", Token.Type.RETURN);
        knownWords.put("RANDOM", Token.Type.RANDOM);
        knownWords.put("LEFT$", Token.Type.LEFT$);
        knownWords.put("RIGHT$", Token.Type.RIGHT$);
        knownWords.put("MID$", Token.Type.MID$);
        knownWords.put("NUM$", Token.Type.NUM$);
        knownWords.put("VAL", Token.Type.VAL);
        knownWords.put("VAL%", Token.Type.VAL2);
        knownWords.put("IF", Token.Type.IF);
        knownWords.put("THEN", Token.Type.THEN);

        
        states state = states.SPACE;
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i <= s.length(); i++)
        {
            char c;
            if (i < s.length()) c = s.charAt(i);
            else c = ' '; //since the loop runs one extra time to do with the last char, a final space token is created and will be changed
            if (charIsAllowed(state, c))
            {
                if (i != s.length() && state != states.STRING && getStateFromChar(c) == states.SPACE)//ignore spaces outside strings (not if last char)
                {
                    state = states.SPACE; //set state to space
                    continue; 
                }
                switch(state)
                {
                    case SPACE: 
                    case PAREN:
                    case LABEL:
                    case PRINT:
                        state = getStateFromChar(c);
                        if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                        else tokens.add(new Token(getTypeFromChar(c)));
                        break;
                    case OPERATE:
                        state = getStateFromChar(c);
                        if (c == '-' && i<s.length() && (getStateFromChar(s.charAt(i+1)) == states.WHOLENUMBER || getStateFromChar(s.charAt(i+1)) == states.DECIMAL))
                        {
                            tokens.add(new Token(Token.Type.NUMBER, '-')); 
                            state = getStateFromChar(s.charAt(i+1));
                        }
                        else 
                        {
                            if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                            else tokens.add(new Token(getTypeFromChar(c)));
                        }
                        break;
                    case DECIMAL: //decimal state numbers to the right
                        state = getStateFromChar(c);
                        if (state == states.WHOLENUMBER)
                        {
                            tokens.get(tokens.size()-1).changeValue(Character.toString(c));
                            state = states.DECIMAL;
                        }
                        else
                        {
                            if (tokens.get(tokens.size()-1).getTokenValue().equals(".")) throw new Exception("Punctuation not allowed. ");
                            if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                            else tokens.add(new Token(getTypeFromChar(c)));
                        }
                        break; 
                    case WHOLENUMBER: //number state. If character is a number, must update last token rather than create a new one
                        state = getStateFromChar(c);
                        if (state == states.WHOLENUMBER || state == states.DECIMAL) //update the last token's value if continuing a number 
                            tokens.get(tokens.size()-1).changeValue(Character.toString(c)); 
                        else 
                        {
                            if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                            else tokens.add(new Token(getTypeFromChar(c)));
                        }
                        break;
                    case DECLARE: //declaration state. Must handle changing < and > to <=, <>, or >=
                        state = getStateFromChar(c);
                        if (state == states.DECLARE)
                        {    
                            if (s.charAt(i-1) == '<' && c == '>') tokens.get(tokens.size()-1).changeTokenType(Token.Type.NOTEQUALS);
                            else if (s.charAt(i-1) == '<' && c == '=') tokens.get(tokens.size()-1).changeTokenType(Token.Type.LESSTHANEQUAL);
                            else if (s.charAt(i-1) == '>' && c == '=') tokens.get(tokens.size()-1).changeTokenType(Token.Type.GREATERTHANEQUAL);
                        }
                        else
                        {
                            if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                            else tokens.add(new Token(getTypeFromChar(c)));
                        }
                        break;
                    case STRING: //string state. Must treat any character as part of the string except a closing "
                        if (i == s.length()-1 && c != '"') throw new Exception("Unclosed string encountered. ");
                        if (c == '"') state = states.SPACE; //do not add to the value of the string once second quote is encountered
                        else
                        {
                            state = states.STRING;
                            tokens.get(tokens.size()-1).changeValue(Character.toString(c));
                        }
                        break;
                    case WORD: //word state. Any letters followed by only a single $ or % and NO :
                        state = getStateFromChar(c);
                        if (state != states.WORD) //make sure new char isnt just more in the same word
                        {
                            if (state == states.LABEL) tokens.get(tokens.size()-1).changeTokenType(Token.Type.LABEL); //changes token to label if ':' is found
                            else
                            {
                                if (needsValue(getTypeFromChar(c)) && c != '"') tokens.add(new Token(getTypeFromChar(c), c));
                                else tokens.add(new Token(getTypeFromChar(c)));
                            }
                        }
                        else //character is part of the same word, dont create a new token just update the last one
                        {
                            tokens.get(tokens.size()-1).changeValue(Character.toString(c));
                            if (c == '$' || c == '%') state = states.SPACE; //change state to space after these chars so only one can end a word
                        }
                        break;
                }
            }
            else throw new Exception("Character not allowed: '" + c + "'. ");
        }
        tokens.get(tokens.size()-1).changeTokenType(Token.Type.EndOfLine);;//last SPACE token becomes end of line
        for (Token token : tokens) //loop back through list of tokens to make final changes
        {
            if (token.getTokenType() == Token.Type.STRING)
            {
                if (token.getTokenValue() == null) token.changeValue(""); //changes null strings to ""
            }

            if (token.getTokenType() == Token.Type.IDENTIFIER && knownWords.containsKey(token.getTokenValue()))
                token.changeTokenType(knownWords.get(token.getTokenValue()));
        }
        return tokens;
    }
}