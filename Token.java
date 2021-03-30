/**
 *  Token Class
 *  Defines acceptible token types in Type enum
 * @author Dylan VanAllen
 */
public class Token {
    public enum Type{
        SPACE, PLUS, MINUS, TIMES, DIVIDE, NUMBER, EQUALS, LPAREN, RPAREN, LESSTHAN, LESSTHANEQUAL, 
        GREATERTHAN, GREATERTHANEQUAL, NOTEQUALS, STRING,  IDENTIFIER, PRINT, LABEL, COMMA, INPUT, DATA, READ,
        FOR, GOSUB, RETURN, NEXT, STEP, TO, RANDOM, LEFT$, RIGHT$, MID$, NUM$, VAL, VAL2, IF, THEN, EndOfLine
    }
    private Type token;
    private String value = null;

    public Token(Type tokenType) {    
        this.token = tokenType;
    }

    public Token(Type tokenType, char c) {    
        this.token = tokenType;
        this.value = Character.toString(c);
    }
    public void changeValue(String s) {
        if (s != null)
        {
            if (this.value == null) this.value=s;
            else this.value += s;
        }
        else this.value = null;
    }
    public void changeTokenType(Type newType) { 
        this.token = newType;
    }
    public String getTokenValue() {
        return this.value;
    }
    public Type getTokenType() {
        return this.token;
    }

    @Override
    public String toString()
	{
        switch(this.token)
        {         
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LABEL:
                return this.getTokenType()+"("+this.getTokenValue()+")" ;
            default:
                return this.getTokenType()+"";                 
        }
	}
}
