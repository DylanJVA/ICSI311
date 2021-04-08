public class NextNode extends StatementNode{
    private VariableNode var;
    private Node nextNode;

    public NextNode(VariableNode v) {
        this.var = v;
    }

    public VariableNode getVar() {
        return this.var;
    }


    public void setNext(Node n) {
        this.nextNode=n;
    }

    public Node getNext() {
        return this.nextNode;
    }

    public String toString() {
        return "Next("+var.toString()+")";
    }
}
