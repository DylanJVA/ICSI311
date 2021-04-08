public class ForNode extends StatementNode {
    private VariableNode var;
    private IntegerNode initialValue;
    private IntegerNode endingValue;
    private IntegerNode step;
    private Node afterNext;

    public ForNode(VariableNode v, IntegerNode n1, IntegerNode n2, IntegerNode s) {
        this.var = v;
        this.initialValue = n1;
        this.endingValue = n2;
        this.step = s;
    }

    public VariableNode getVar() {
        return this.var;
    }

    public IntegerNode getInitVal() {
        return this.initialValue;
    }

    public IntegerNode getFinalVal() {
        return this.endingValue;
    }

    public IntegerNode getStep() {
        return this.step;
    }

    public void setNext(Node n) {
        afterNext = n;
    }

    public Node getNext() {
        return this.afterNext;
    }

    public void accept() {
        Interpreter.forNextVisit(this);
    }

    public String toString() {
        return "For("+var.toString()+", "+initialValue.toString()+", "+endingValue.toString()+", "+step.toString()+")";
    }
}
