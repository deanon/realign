package realign;


public interface Node { 
    
    Bounds getBounds();

    boolean contains(Bounds b);

    Node split(Bounds b, Bounds imaginaryBounds);

    Bounds getImaginaryBounds();

    void accept(NodeVisitor nv);
}
