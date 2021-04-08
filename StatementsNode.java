import java.util.*;
import java.lang.String;
public class StatementsNode extends Node {
    private List<StatementNode> statements;

    public StatementsNode(List<StatementNode> s) {
        this.statements = s;
    }

    public List<StatementNode> getStatements() {
        return this.statements;
    }

    public void addToList(List<StatementNode> s) {
        statements.addAll(s);
    }

    public String toString() {
        String s = "Statements(";
        for (Node statement : statements) s += statement.toString()+", ";
        return s.substring(0, s.length()-2)+")";
    }
}