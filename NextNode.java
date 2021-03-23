public class NextNode extends StatementNode{
    private VariableNode var;

    public NextNode(VariableNode v) {
        this.var = v;
    }

    public VariableNode getVar() {
        return this.var;
    }

    public String toString() {
        return "Next("+var.toString()+")";
    }
}
