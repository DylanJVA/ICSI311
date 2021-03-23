public class AssignmentNode extends StatementNode {
    private VariableNode varNode;
    private Node val;

    public AssignmentNode(VariableNode n, Node v) {
        this.varNode = n;
        this.val = v;
    }

    public VariableNode getVar() {
        return this.varNode;
    }

    public Node getVal() {
        return this.val;
    }

    public String toString() {
        return "Assignment("+this.varNode.toString()+", "+this.val.toString()+")";
    }
}