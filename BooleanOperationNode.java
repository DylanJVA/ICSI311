public class BooleanOperationNode extends Node{
    private Token.Type operation;
    private Node exp1,exp2;

    public BooleanOperationNode(Token.Type o, Node e1, Node e2) {
        this.operation = o;
        this.exp1=e1;
        this.exp2=e2;
    }

    public Token.Type getOperation() {
        return this.operation;
    }

    public Node getFirstExpression() {
        return this.exp1;
    }

    public Node getSecondExpression() {
        return this.exp2;
    }

    public String toString() {
        return "BooleanOp("+this.exp1.toString()+", "+this.operation+", "+this.exp2.toString()+")";
    }
}
