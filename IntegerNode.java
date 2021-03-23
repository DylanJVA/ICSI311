public class IntegerNode extends Node{
    private int num;

    public IntegerNode(int n) {
        this.num = n;
    }
    public int getInt() {
        return this.num;
    }
    public String toString() {
        return "Int("+this.num+")";
    }
}
