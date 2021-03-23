import java.util.*;
public class PrintNode extends StatementNode {
    private List<Node> printList;

    public PrintNode(List<Node> l) {
        this.printList = l;
    }

    public List<Node> getPrintList() {
        return this.printList;
    }

    public String toString() {
        String s = "Print(";
        for (Node n : printList)
            s += n.toString()+", ";
        return s.substring(0, s.length()-2) + ")";//remove final ", " and add ')'
    }

}