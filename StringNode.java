public class StringNode extends Node {
    private String s;
    public StringNode(String q) {
        this.s = q;
    }

    public String getString() {
        return this.s;
    }
    public String toString() {
        return "String("+this.s+")";
    }
}
