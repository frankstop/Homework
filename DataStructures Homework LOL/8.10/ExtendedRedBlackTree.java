public class ExtendedRedBlackTree extends RedBlackTree {
    // Each node in an ExtendedRedBlackTree is an ExtendedRBTNode
    @Override
    protected BSTNode makeNewNode(int key) {
        return new ExtendedRBTNode(key);
    }

    /**
     * Retrieves the nth smallest key in the tree.
     * @param n The index of the desired key (0-based).
     * @return The nth smallest key.
     * @throws IllegalArgumentException if n is out of bounds.
     */
    @Override
    public int getNthKey(int n) {
        if (getRoot() == null) {
            throw new IllegalArgumentException("The tree is empty.");
        }

        // Ensure the root is an ExtendedRBTNode
        if (!(getRoot() instanceof ExtendedRBTNode)) {
            throw new IllegalStateException("Root is not an instance of ExtendedRBTNode.");
        }

        ExtendedRBTNode current = (ExtendedRBTNode) getRoot();

        while (current != null) {
            int leftCount = 0;

            // Get the count of keys in the left subtree
            if (current.getLeft() instanceof ExtendedRBTNode) {
                leftCount = ((ExtendedRBTNode) current.getLeft()).getSubtreeKeyCount();
            }

            if (n < leftCount) {
                // The desired key is in the left subtree
                current = (ExtendedRBTNode) current.getLeft();
            } else if (n == leftCount) {
                // The current node is the nth key
                return current.getKey();
            } else {
                // The desired key is in the right subtree
                n = n - leftCount - 1;
                current = (ExtendedRBTNode) current.getRight();
            }
        }

        // If n is out of bounds
        throw new IllegalArgumentException("n is out of bounds.");
    }

    /**
     * Override rotateLeft to ensure subtreeKeyCount is updated after rotation.
     */
    @Override
    protected BSTNode rotateLeft(BSTNode node) {
        BSTNode rotatedNode = super.rotateLeft(node);

        // After rotation, update subtreeKeyCount for the involved nodes
        if (rotatedNode instanceof ExtendedRBTNode) {
            ((ExtendedRBTNode) rotatedNode).updateSubtreeKeyCount();
        }

        return rotatedNode;
    }

    /**
     * Override rotateRight to ensure subtreeKeyCount is updated after rotation.
     */
    @Override
    protected BSTNode rotateRight(BSTNode node) {
        BSTNode rotatedNode = super.rotateRight(node);

        // After rotation, update subtreeKeyCount for the involved nodes
        if (rotatedNode instanceof ExtendedRBTNode) {
            ((ExtendedRBTNode) rotatedNode).updateSubtreeKeyCount();
        }

        return rotatedNode;
    }

    // The constructor remains unchanged
    public ExtendedRedBlackTree() {
        super();
    }
}