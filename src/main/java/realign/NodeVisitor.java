package realign;

public interface NodeVisitor {
    public void enter(Node n);
    public void exit(Node n);
    public void visit(Node n);
}
