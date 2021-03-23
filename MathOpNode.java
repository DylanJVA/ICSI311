public class MathOpNode extends Node{
    public enum Operations{
        add, subtract, multiply, divide
    }
    Node left, right;
    private Operations operation;
    
    public MathOpNode(Operations op, Node op1, Node op2) {
        this.left = op1;
        this.right = op2;
        this.operation = op;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public Operations getOp() {
        return this.operation;
    }

    public String toString() {
        char c = ' ';
        switch (this.operation)
        {
            case add: c='+'; break;
            case subtract: c='-'; break;
            case multiply: c='*'; break;
            case divide: c='/'; break;
            default: break;
        }
        return ("MathOpNode("+c+", "+this.left.toString()+", "+this.right.toString()+")");
    }
}
