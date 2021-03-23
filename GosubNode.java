public class GosubNode extends StatementNode {
    private VariableNode go;

    public GosubNode(VariableNode g) {
        this.go = g;
    }

    public VariableNode getGoto() {
        return this.go;
    }

    public String toString() {
        return "Goto("+go.toString()+")";
    }
    
}
