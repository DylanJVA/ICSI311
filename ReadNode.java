import java.util.*;
public class ReadNode extends StatementNode{
    private List<VariableNode> vars;
    public ReadNode(List<VariableNode> v) {
        this.vars = v;
    }

    public List<VariableNode> getVars() {
        return this.vars;
    }

    public String toString() {
        String s = "Read(";
        for (VariableNode v : vars)
            s += v.toString()+", ";
        return s.substring(0, s.length()-2) + ")";
    }
}
