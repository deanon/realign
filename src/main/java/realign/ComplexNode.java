package realign;

import java.util.ArrayList;
import java.util.List;

public class ComplexNode implements Node {

    private final List<Node> nodes = new ArrayList<Node>();
    private Bounds imaginaryBounds;

    public ComplexNode(Node node) {
	nodes.add(node);
    }

    public ComplexNode(List<Node> nodes) {
	this.nodes.addAll(nodes);
    }

    private int getRealStart() {
	return nodes.size() > 0 ?
		nodes.get(0).getBounds().getStart() : 0;
    }

    public int getRealEnd() {
	return nodes.size() > 0 ?
		nodes.get(nodes.size() - 1).getBounds().getLength() : 0;
    }

    @Override
    public boolean contains(Bounds b) {
	return b.getStart() >= getRealStart() && b.getLength() <= getRealEnd();
    }

    @Override
    public Node split(Bounds b, Bounds imaginaryBounds) {
	for (int i = 0; i < nodes.size(); i++) {
	    Node n = nodes.get(i);
	    if (n.contains(b)) {
		invalidate();
		nodes.set(i, n.split(b, imaginaryBounds));
		break;
	    }
	}
	return this;
    }

    private void invalidate() {
	imaginaryBounds = null;
    }

    @Override
    public Bounds getBounds() {
	return new Bounds(getRealStart(), getRealEnd());
    }

    @Override
    public Bounds getImaginaryBounds() {
	int start = Integer.MAX_VALUE;
	int length = 0;

	for (Node node : nodes) {
	    Bounds nodeBounds = node.getImaginaryBounds();

	    start = Math.min(start, nodeBounds.getStart());
	    length = Math.max(length, nodeBounds.getLength());
	}
	imaginaryBounds = new Bounds(start, length);

	return imaginaryBounds;
    }

    @Override
    public void accept(NodeVisitor nv) {
	nv.enter(this);
	for (Node n : nodes) {
	    n.accept(nv);
	}
	nv.exit(this);
    }
}
