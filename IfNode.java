public class IfNode extends StatementNode{
    private Node boolExp;

    public IfNode(Node b) {
        this.boolExp = b;
    }

    public Node getBool() {
        return this.boolExp;
    }

    public String toString() {
        return "If("+this.boolExp+")";
    }
}
