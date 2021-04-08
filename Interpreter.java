import java.util.*;
public class Interpreter {
    static HashMap<String, Integer> strToInt = new HashMap<String, Integer>();
    static HashMap<String, Float> strToFloat = new HashMap<String, Float>();
    static HashMap<String, String> strToStr = new HashMap<String, String>();
    static HashMap<String, Node> strToNode = new HashMap<String, Node>();
    public static List<StatementNode> statementsList;

    public Interpreter(StatementsNode statements) {
        statementsList=new ArrayList<StatementNode>(statements.getStatements());
    }
    //walks through statements to deal with labeled statements
    public void labeledStatementWalk() {
        for (StatementNode statement : statementsList) 
        {
            if (statement.toString().contains("LabeledStatement"))
            {
                System.out.println("contains labeled statement");
                LabeledStatementNode labeledStatement = (LabeledStatementNode) statement;
                labeledStatement.accept();
            }
        }
    }
    //visitor method for labeled statements
    public static void labeledStatementVisit(LabeledStatementNode labeledStatement) {

        String label = labeledStatement.getLabel();
        StatementNode childNode = labeledStatement.getStatement();
        int i = statementsList.indexOf((StatementNode)labeledStatement);
        statementsList.set(i, childNode);
        strToNode.put(label, childNode);
    }
    //walks through statements to find a For node
    public void forWalk() {
        for (StatementNode statement : statementsList) 
        {
            if (statement.toString().contains("For"))
            {
                ForNode forNode = (ForNode) statement;
                forNode.accept();
            }
        }
    }
    //Visitor method - find Next node for For statement and node after next node
    public static void forNextVisit(ForNode forNode) {
        for (StatementNode statement : statementsList) 
        {

            if (statement.toString().contains("Next"))
            {
                NextNode nextNode = (NextNode) statement;
                nextNode.setNext((Node)statementsList.get(statementsList.indexOf((StatementNode)forNode)+1));
                if (statementsList.indexOf(statement) != statementsList.size()-1)
                    forNode.setNext((Node)statementsList.get(statementsList.indexOf(statement)+1));
                else nextNode.setNext(null);
            }
        }
    }
    

    public void dataWalk() {
        for (int i = 0; i < statementsList.size(); i++) 
        {
            if (statementsList.get(i).toString().contains("Data"))
            {
                DataNode data = (DataNode) statementsList.get(i);
                data.accept();
                statementsList.remove(i);
                //must take into account removed element
                i--;
            }
        }
    }
    public static void dataVisit(DataNode data) {
        for (Node d : data.getData()) 
        {
            if (d.toString().contains("Int")) strToInt.put(d.toString(), ((IntegerNode)d).getInt());
            else if (d.toString().contains("Float")) strToFloat.put(d.toString(), ((FloatNode)d).getFloat());
            else strToStr.put(d.toString(),((StringNode)d).getString());
        }
    }

    public static void linkWalk() {
        for (StatementNode statement : statementsList)
        {
            //sets next statement to null if its the last statement
            if (statementsList.indexOf(statement) == statementsList.size()-1)
                statement.next = null;
            //links statement to next statement    
            else
                statement.next = statementsList.get(statementsList.indexOf(statement) + 1);
        }
    }

    public void initialize() {
        
        labeledStatementWalk();
        forWalk();
        dataWalk();
        linkWalk();
    }

    public HashMap[] getMaps() {
        return new HashMap[]{strToStr, strToInt, strToFloat};
    }

    public List<StatementNode> getStatements() {
        return statementsList;
    }

}
