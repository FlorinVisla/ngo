package uvt.ngo.rest.util;

import uvt.ngo.rest.entity.NGO;

class BinomialHeapNode {

    int key, degree;
    NGO ngo;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;

    // Constructor of this class
    public BinomialHeapNode(NGO k)
    {
        ngo = k;
        key = k.getPriority();
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    /**
     * Reverse shift
     * @param sibl
     * @return
     */
    public BinomialHeapNode reverse(BinomialHeapNode sibl)
    {
        BinomialHeapNode ret;
        if (sibling != null)
            ret = sibling.reverse(this);
        else
            ret = this;
        sibling = sibl;
        return ret;
    }

    /**
     * finds a node based on value
     * @param value
     * @return
     */
    public BinomialHeapNode findANodeWithKey(int value)
    {

        BinomialHeapNode temp = this, node = null;

        while (temp != null) {
            if (temp.key == value) {
                node = temp;
                break;
            }

            if (temp.child == null)
                temp = temp.sibling;

            else {
                node = temp.child.findANodeWithKey(value);
                if (node == null)
                    temp = temp.sibling;
                else
                    break;
            }
        }

        return node;
    }

    /**
     * Gets the minimum node(based on value)
     * @return
     */
    public BinomialHeapNode findMinNode()
    {

        // this keyword refers to current instance itself
        BinomialHeapNode x = this, y = this;
        int min = x.key;

        while (x != null) {
            if (x.key < min) {
                y = x;
                min = x.key;
            }

            x = x.sibling;
        }

        return y;
    }
}