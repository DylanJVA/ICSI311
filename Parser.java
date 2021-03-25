/**
 *  Parser Class
 *  Looks through a list of lexemes and parses the tokens into an AST data structure
 * @author Dylan VanAllen
 * @version 1.0
 */

import java.util.*;

public class Parser {
    private List<Token> tokens;
    public Parser(List<Token> t) {
        this.tokens = t;
    }

    public Token matchAndRemove(Token.Type type) { //removes first token if it matches specified type
        if (!tokens.isEmpty())
        {
            if (type == tokens.get(0).getTokenType()) return tokens.remove(0);
            else return null;
        }
        else return null;
    }

    public Node factor() throws Exception {
        Token token;
        Node thisFactor;
        if ((token = matchAndRemove(Token.Type.NUMBER)) != null) //check if a number was removed, make it a Node
        {
           if (token.getTokenValue().indexOf('.') == -1) return new IntegerNode(Integer.parseInt(token.getTokenValue()));
           else return new FloatNode(Float.parseFloat(token.getTokenValue())); 
        }
        if (matchAndRemove(Token.Type.LPAREN) != null) //check if an expression has begun
        {
            thisFactor = expression();
            if (matchAndRemove(Token.Type.RPAREN) == null) throw new Exception("Unclosed parenthesis detected.");//make sure parentheses end
            else return thisFactor;
        }
        if ((token = matchAndRemove(Token.Type.IDENTIFIER)) != null)
            return new VariableNode(token.getTokenValue());
        else
        {
            return null;
        } 
    }

    public Node expression() throws Exception {
        Node thisExpression;
        thisExpression = term(); //operator 1 is first term found
        //recursively look for more terms
        if (matchAndRemove(Token.Type.PLUS) != null) return new MathOpNode(MathOpNode.Operations.add, thisExpression, expression());
        else if (matchAndRemove(Token.Type.MINUS) != null) return new MathOpNode(MathOpNode.Operations.subtract, thisExpression, expression());
        else return thisExpression;
    }

    public Node term() throws Exception {
        Node thisTerm;
        thisTerm = factor(); //operator 1 is first factor
        //recursively look for more factors
        if (matchAndRemove(Token.Type.TIMES) != null) return new MathOpNode(MathOpNode.Operations.multiply, thisTerm, term());
        else if (matchAndRemove(Token.Type.DIVIDE) != null) return new MathOpNode(MathOpNode.Operations.divide, thisTerm, term());
        else return thisTerm;
    }

    public Node statements() throws Exception {
        ArrayList<StatementNode> statementsList = new ArrayList<StatementNode>();
        StatementNode thisStatement;
        while((thisStatement = statement()) != null)
            statementsList.add(thisStatement); //adds statement to list, to ultimately create StatementsNode
        if (!statementsList.isEmpty()) return new StatementsNode(statementsList);
        else return null;
    }

    public StatementNode statement() throws Exception {
        Token removedToken;
        if ((removedToken = matchAndRemove(Token.Type.LABEL)) != null)
            return labelStatement(removedToken);
        if (matchAndRemove(Token.Type.PRINT) != null)
            return printStatement(); 
        if ((removedToken = matchAndRemove(Token.Type.IDENTIFIER)) != null) 
            return assignment(removedToken);
        if (matchAndRemove(Token.Type.READ) != null)
            return readStatement();
        if (matchAndRemove(Token.Type.DATA) != null)
            return dataStatement();
        if (matchAndRemove(Token.Type.INPUT) != null)
            return inputStatement();
        if (matchAndRemove(Token.Type.GOSUB) != null)
            return gosubStatement();
        if (matchAndRemove(Token.Type.NEXT) != null)
            return nextStatement();
        if (matchAndRemove(Token.Type.FOR) != null)
            return forStatement();
        if (matchAndRemove(Token.Type.RETURN) != null)
        {
            if (matchAndRemove(Token.Type.EndOfLine) != null) return new ReturnNode();
            else throw new Exception("RETURN must be alone on line");
        }    
        else return null;
    }

    public PrintNode printStatement() throws Exception {
        ArrayList<Node> expressionList = new ArrayList<Node>();
        Node print;
        while ((print = printList()) != null) 
        {
            expressionList.add(print); //result of print is each expression
            matchAndRemove(Token.Type.COMMA); //remove separating commas
        }   
        return new PrintNode(expressionList); //now printnode has a list of expressions
    }

    public Node printList() throws Exception
	{
		Node thisPrint = expression();
        Token s;
        if (thisPrint == null)//if expression doesnt work check for string
        {
            if ((s=matchAndRemove(Token.Type.STRING)) != null)
                return new StringNode(s.getTokenValue());
            else return null;
        }
        else return thisPrint;
	}

    public AssignmentNode assignment(Token t) throws Exception{
        if (matchAndRemove(Token.Type.EQUALS) != null)
            return new AssignmentNode(new VariableNode(t.getTokenValue()), expression());
        else return null;
    }

    public ReadNode readStatement() throws Exception {
        ArrayList<VariableNode> readVars = new ArrayList<VariableNode>(); //node will hold a list of variables
        VariableNode read;
        while ((read = readList()) != null) 
            readVars.add(read); 
        return new ReadNode(readVars); 
    }

    public VariableNode readList() throws Exception {
		Token t;
        VariableNode v;
        if ((t=matchAndRemove(Token.Type.IDENTIFIER))!=null) v = new VariableNode(t.getTokenValue());
        else throw new Exception("READ statement expecting variable");
        matchAndRemove(Token.Type.COMMA);
        return v;
	}

    public DataNode dataStatement() throws Exception {
        ArrayList<Node> dataList = new ArrayList<Node>(); //node will hold a list of strings, int nodes, or float nodes
        Node data;
        while ((data = dataList()) != null) 
            dataList.add(data); 
        return new DataNode(dataList); 
    }
    
    public Node dataList() throws Exception
	{
        Token s =matchAndRemove(Token.Type.STRING); //check for a string and for int nodes and float nodes
        Node thisData;
        if (s == null)
        {
            if ((s = matchAndRemove(Token.Type.NUMBER)) != null) //check if a number was removed, make it a Node
            {
                if (s.getTokenValue().indexOf('.') == -1) return new IntegerNode(Integer.parseInt(s.getTokenValue()));
                else return new FloatNode(Float.parseFloat(s.getTokenValue())); 
            }
            else return null;
        }
        else thisData = new StringNode(s.getTokenValue()); 
        matchAndRemove(Token.Type.COMMA); 
        return thisData;
	}

    public InputNode inputStatement() throws Exception {
        ArrayList<Node> inputList = new ArrayList<Node>(); //holds an optional string and then a list of variables so i used super type node
        Token s = matchAndRemove(Token.Type.STRING);
        if (s != null) inputList.add(new StringNode(s.getTokenValue()));
        matchAndRemove(Token.Type.COMMA);
        Node input;
        while ((input = inputList()) != null) 
            inputList.add(input); 
        return new InputNode(inputList); 
    }

    public VariableNode inputList() throws Exception
	{
		VariableNode v;
        Token t;
        if ((t=matchAndRemove(Token.Type.IDENTIFIER))!=null) v = new VariableNode(t.getTokenValue());//v will be each variable in INPUT
        else return null;
        matchAndRemove(Token.Type.COMMA); 
        return v;
	}

    public LabeledStatementNode labelStatement(Token t) throws Exception{
        String s = t.getTokenValue();
        StatementNode statement = statement();
        return new LabeledStatementNode(s, statement);
    }

    public ForNode forStatement() throws Exception{ //of form FOR variable = initial_value TO limit STEP increment
        VariableNode var = new VariableNode(matchAndRemove(Token.Type.IDENTIFIER).getTokenValue());
        if (matchAndRemove(Token.Type.EQUALS) == null) throw new Exception("FOR statement expecting equals token");
        Token t;
        IntegerNode n1, n2, step;
        if ((t = matchAndRemove(Token.Type.NUMBER)) != null)
        {
            if (t.getTokenValue().indexOf('.') == -1) n1 = new IntegerNode(Integer.parseInt(t.getTokenValue()));
            else throw new Exception("FOR statement may not be incremented with decimals");
        }
        else throw new Exception("FOR statement expecting assignment");
        if (matchAndRemove(Token.Type.TO) == null) throw new Exception("FOR statement expecting TO token");
        if ((t = matchAndRemove(Token.Type.NUMBER)) != null)
        {
            if (t.getTokenValue().indexOf('.') == -1) n2 = new IntegerNode(Integer.parseInt(t.getTokenValue()));
            else throw new Exception("FOR statement may not be incremented with decimals");
        }
        else throw new Exception("FOR statement expecting bounding integer");
        if (matchAndRemove(Token.Type.STEP) == null) return new ForNode(var, n1, n2, new IntegerNode(1));
        else
        {
            if ((t = matchAndRemove(Token.Type.NUMBER)) != null)
            {
                if (t.getTokenValue().indexOf('.') == -1) step = new IntegerNode(Integer.parseInt(t.getTokenValue()));
                else throw new Exception("FOR statement may not be incremented with decimals");
            }
            else throw new Exception("STEP expecting integer");
            return new ForNode(var, n1, n2, step);
        }
    }

    public NextNode nextStatement() throws Exception{
        Token t;
        if ((t=matchAndRemove(Token.Type.IDENTIFIER))!=null)
            return new NextNode(new VariableNode(t.getTokenValue()));//identifiers are variable nodes
        else throw new Exception("NEXT statement requires variable");
    
    }

    public GosubNode gosubStatement() throws Exception{
        Token t;
        if ((t = matchAndRemove(Token.Type.IDENTIFIER))!=null)
            return new GosubNode(new VariableNode(t.getTokenValue()));
        else throw new Exception("GOSUB statement requires label name");
    }

    public Node parse() throws Exception{
        return statements();
    }
}
