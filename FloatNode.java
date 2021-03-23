public class FloatNode extends Node{
    private float num;

    public FloatNode(float n) {
        this.num = n;
    }
    public float getFloat() {
        return this.num;
    }
    public String toString() {
        return "Float("+this.num+")";
    }
}
