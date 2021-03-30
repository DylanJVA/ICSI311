import java.util.*;
public class FunctionNode extends Node{
    private String fname;
    private List<Node> expList;

    public FunctionNode(String f, List<Node> e) {
        this.fname = f;
        this.expList = e;
    }

    public String getFunction() {
        return this.fname;
    }

    public List<Node> getFunctionList() {
        return this.expList;
    }

    public String toString() {
        String s = "Function("+this.fname+", ";
        for (Node n : this.expList) {
            s += n.toString()+", ";
        }
        return s.substring(0, s.length()-2) + ")";
    }
}
