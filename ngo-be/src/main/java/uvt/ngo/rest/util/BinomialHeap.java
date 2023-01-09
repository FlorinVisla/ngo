package uvt.ngo.rest.util;

import uvt.ngo.rest.entity.NGO;

/**
 * Class for Binomial heap.
 * It's main scope is to retrieve
 * the priority of NGOs (based on NGO::priority)
 */
public class BinomialHeap {

    private BinomialHeapNode Nodes;

    public BinomialHeap() {
        Nodes = null;
    }

    /**
     * unused method(for now), that helps with the deletion of a value
     */
    public void delete(int value) {

        if ((Nodes != null)
                && (Nodes.findANodeWithKey(value) != null)) {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }


    /**
     * Inserts NGO into heap, based on priority
     */
    public void insert(NGO value) {

        if (value.getPriority() >= 0) {
            BinomialHeapNode temp
                    = new BinomialHeapNode(value);
            if (Nodes == null) {
                Nodes = temp;
            } else {
                unionNodes(temp);
            }
        }
    }

    /**
     * Merges the binomial trees, in order to fix the heap
     **/
    private void merge(BinomialHeapNode binHeap) {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;

        while ((temp1 != null) && (temp2 != null)) {

            if (temp1.degree == temp2.degree) {

                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } else {

                if (temp1.degree < temp2.degree) {

                    if ((temp1.sibling == null)
                            || (temp1.sibling.degree
                            > temp2.degree)) {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    } else {
                        temp1 = temp1.sibling;
                    }
                } else {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;

                    if (tmp == Nodes) {
                        Nodes = temp1;
                    }
                }
            }
        }

        if (temp1 == null) {
            temp1 = Nodes;

            while (temp1.sibling != null) {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        }
    }

    /**
     * Union for nodes(merges together)
     */
    private void unionNodes(BinomialHeapNode binHeap) {
        merge(binHeap);

        BinomialHeapNode prevTemp = null, temp = Nodes,
                nextTemp = Nodes.sibling;

        while (nextTemp != null) {

            if ((temp.degree != nextTemp.degree)
                    || ((nextTemp.sibling != null)
                    && (nextTemp.sibling.degree
                    == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {

                if (temp.key <= nextTemp.key) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } else {

                    if (prevTemp == null) {
                        Nodes = nextTemp;
                    } else {
                        prevTemp.sibling = nextTemp;
                    }

                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }

    /**
     * gets the minimum value from the heap
     */
    public int findMinimum() {
        return Nodes.findMinNode().key;
    }

    /**
     * Extract the node with the minimum key
     *
     * @return
     */
    public int extractMin() {
        if (Nodes == null)
            return -1;

        BinomialHeapNode temp = Nodes, prevTemp = null;
        BinomialHeapNode minNode = Nodes.findMinNode();

        while (temp.key != minNode.key) {
            prevTemp = temp;
            temp = temp.sibling;
        }

        if (prevTemp == null) {
            Nodes = temp.sibling;
        } else {
            prevTemp.sibling = temp.sibling;
        }

        temp = temp.child;
        BinomialHeapNode fakeNode = temp;

        while (temp != null) {
            temp.parent = null;
            temp = temp.sibling;
        }

        if ((Nodes == null) && (fakeNode == null)) {
        } else {
            if ((Nodes == null) && (fakeNode != null)) {
                Nodes = fakeNode.reverse(null);
            } else {
                if ((Nodes != null) && (fakeNode == null)) {
                } else {
                    unionNodes(fakeNode.reverse(null));
                }
            }
        }

        return minNode.key;
    }

    /**
     * Method for decreasing the value(with a new value)
     */
    public void decreaseKeyValue(int old_value,
                                 int new_value) {
        BinomialHeapNode temp
                = Nodes.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.key = new_value;
        BinomialHeapNode tempParent = temp.parent;

        while ((tempParent != null)
                && (temp.key < tempParent.key)) {
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;

            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }
}