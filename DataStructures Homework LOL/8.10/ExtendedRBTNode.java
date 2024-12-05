public class ExtendedRBTNode extends RBTNode {
    private int subtreeKeyCount;

    // Constructor initializes the subtreeKeyCount to 1
    public ExtendedRBTNode(int nodeKey) {
        super(nodeKey);
        subtreeKeyCount = 1;
    }

    @Override
    public int getSubtreeKeyCount() {
        return subtreeKeyCount;
    }

    /**
     * Updates the subtreeKeyCount based on the current left and right children.
     * This method uses an iterative approach to traverse up the tree and update counts.
     */
    public void updateSubtreeKeyCount() {
        ExtendedRBTNode current = this;

        while (current != null) {
            int leftCount = 0;
            int rightCount = 0;

            // Get the count of keys in the left subtree
            if (current.getLeft() instanceof ExtendedRBTNode) {
                leftCount = ((ExtendedRBTNode) current.getLeft()).getSubtreeKeyCount();
            }

            // Get the count of keys in the right subtree
            if (current.getRight() instanceof ExtendedRBTNode) {
                rightCount = ((ExtendedRBTNode) current.getRight()).getSubtreeKeyCount();
            }

            int newCount = 1 + leftCount + rightCount;

            // If the count hasn't changed, no need to continue
            if (current.subtreeKeyCount == newCount) {
                break;
            }

            // Update the current node's subtreeKeyCount
            current.subtreeKeyCount = newCount;

            // Move to the parent node for further updates
            BSTNode parentNode = current.getParent();

            // Safety check to prevent setting parent to itself
            if (parentNode instanceof ExtendedRBTNode && parentNode != current) {
                current = (ExtendedRBTNode) parentNode;
            } else {
                break;
            }
        }
    }

    @Override
    public void setLeft(BSTNode newLeftChild) {
        super.setLeft(newLeftChild);
        updateSubtreeKeyCount();
    }

    @Override
    public void setRight(BSTNode newRightChild) {
        super.setRight(newRightChild);
        updateSubtreeKeyCount();
    }

    @Override
    public void setKey(int newKey) {
        super.setKey(newKey);
        // No structural change, so subtreeKeyCount remains the same
    }
}