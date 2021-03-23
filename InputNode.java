import java.util.*;
public class InputNode extends StatementNode{
    private List<Node> inputList;
    public InputNode(List<Node> i) {
        this.inputList = i;
    }

    public List<Node> getString() {
        return this.inputList;
    }
    public String toString() {
        String s = "Input(";
        for (Node n : inputList)
            s += n.toString()+", ";
        return s.substring(0, s.length()-2) + ")";
    }
}
