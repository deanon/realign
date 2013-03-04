package realign;

public class Bounds {

    public static final int UNKNOWN = Integer.MAX_VALUE;
    
    private final int start;
    private final int length;

    public boolean isEmpty() {
	return length == 0;
    }

    public Bounds(int start, int length) {
	this.start = start;
	this.length = length;
    }
    
    public Bounds(int length) {
	this.start = UNKNOWN;
	this.length = length;
    }
    
    public int getLength() {
	return length;
    }

    public int getStart() {
	return start;
    }

    public boolean contain(Bounds b) {

	return b.start >= start && b.length <= length - (b.start - start);
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Bounds))
	    return false;
	Bounds that = (Bounds) obj;
	return that.start == this.start && that.length == this.length;
    }
}
