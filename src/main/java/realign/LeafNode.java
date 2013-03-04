package realign;

import java.util.ArrayList;
import java.util.List;

public class LeafNode implements Node {

    private final Bounds bounds;
    private final Bounds imaginaryBounds;

    public LeafNode(Bounds b, Bounds imaginaryBounds) {
	this.bounds = b;
	this.imaginaryBounds = imaginaryBounds;
    }

    @Override
    public Bounds getBounds() {
	return bounds;
    }

    @Override
    public Node split(Bounds b, Bounds imaginaryBounds) {
	if (b.equals(bounds))
	    return this;
	List<Node> list = new ArrayList<Node>();

	int start = getBounds().getStart();
	int bStart = b.getStart();

	int bLength = b.getLength();
	int length = getBounds().getLength();
	Bounds[] bs = new Bounds[] {
		new Bounds(start, start - bStart)
		, new Bounds(bStart, bLength)
		,
		new Bounds(bStart + bLength, length + start - bStart - bLength)
	};

	for (Bounds bb : bs)
	    if (!bb.isEmpty())
		list.add(new LeafNode(bb, imaginaryBounds));

	return new ComplexNode(list);
    }

    @Override
    public boolean contains(Bounds b) {
	return bounds.contain(b);
    }

    @Override
    public Bounds getImaginaryBounds() {
	return imaginaryBounds;
    }

    @Override
    public void accept(NodeVisitor nv) {
	nv.visit(this);
    }
}
