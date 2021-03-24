public class GosubNode extends StatementNode {
    private VariableNode go;

    public GosubNode(VariableNode g) {
        this.go = g;
    }

    public VariableNode getGosub() {
        return this.go;
    }

    public String toString() {
        return "Gosub("+go.toString()+")";
    }
    
}
