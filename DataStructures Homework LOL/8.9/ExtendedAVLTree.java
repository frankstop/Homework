public class ExtendedAVLTree extends AVLTree {
   // Each node in an ExtendedAVLTree is an ExtendedAVLNode
   @Override
   protected BSTNode makeNewNode(int key) {
      return new ExtendedAVLNode(key);
   }

   @Override
   protected BSTNode rotateLeft(BSTNode node) {
      BSTNode newRoot = super.rotateLeft(node);
      ((ExtendedAVLNode) node).updateHeight();
      ((ExtendedAVLNode) newRoot).updateHeight();
      return newRoot;
   }

   @Override
   protected BSTNode rotateRight(BSTNode node) {
      BSTNode newRoot = super.rotateRight(node);
      ((ExtendedAVLNode) node).updateHeight();
      ((ExtendedAVLNode) newRoot).updateHeight();
      return newRoot;
   }

   @Override
   public int getNthKey(int n) {
      if (root == null || n < 0 || n >= ((ExtendedAVLNode) root).getSubtreeKeyCount()) {
          return 0; // Or throw an exception
      }
      return getNthKeyRecursive((ExtendedAVLNode) root, n);
   }

   private int getNthKeyRecursive(ExtendedAVLNode node, int n) {
      int leftCount = node.getLeft() != null ? ((ExtendedAVLNode) node.getLeft()).getSubtreeKeyCount() : 0;
      if (n < leftCount) {
          return getNthKeyRecursive((ExtendedAVLNode) node.getLeft(), n);
      } else if (n == leftCount) {
          return node.getKey();
      } else {
          return getNthKeyRecursive((ExtendedAVLNode) node.getRight(), n - leftCount - 1);
      }
   }
}