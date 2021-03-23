import java.util.*;
public class DataNode extends StatementNode {
    private List<Node> data;
    public DataNode(List<Node> d) {
        this.data = d;
    }

    public List<Node> getData() {
        return this.data;
    }

    public String toString() {
        String s = "Data(";
        for (Node d : data)
            s += d.toString()+", ";
        return s.substring(0, s.length()-2) + ")";
    }
}