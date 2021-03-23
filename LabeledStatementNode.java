public class LabeledStatementNode extends StatementNode {
    private String label;
    private StatementNode statement;

    public LabeledStatementNode(String l, StatementNode s) {
        this.label = l;
        this.statement = s;
    }

    public String getLabel() {
        return this.label;
    }

    public StatementNode getStatement() {
        return this.statement;
    }

    public String toString() {
        return "LabeledStatement("+label+": "+statement.toString()+")";
    }
    
}
