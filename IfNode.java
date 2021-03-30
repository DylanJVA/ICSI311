public class IfNode extends StatementNode{
    private Node boolExp;
    private String label;

    public IfNode(Node b, String l) {
        this.boolExp = b;
        this.label = l;
    }

    public Node getBool() {
        return this.boolExp;
    }

    public String getLabel() {
        return this.label;
    }

    public String toString() {
        return "If("+this.boolExp+", "+this.label+")";
    }
}
