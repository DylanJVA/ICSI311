public class VariableNode extends Node {
    private String var;
    public VariableNode(String s) {
        this.var = s;
    }

    public String getVarName() {
        return this.var;
    }
    public String toString() {
        return "Variable("+this.var+")";
    }
}
