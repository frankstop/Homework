public class ExtendedAVLNode extends AVLNode {
   private int subtreeKeyCount;

   public ExtendedAVLNode(int nodeKey) {
      super(nodeKey);
      subtreeKeyCount = 1;
   }

   @Override
   public int getSubtreeKeyCount() {
      return subtreeKeyCount;
   }

   public void updateSubtreeKeyCount() {
      int leftCount = 0;
      int rightCount = 0;
      if (getLeft() != null) {
         leftCount = ((ExtendedAVLNode) getLeft()).getSubtreeKeyCount();
      }
      if (getRight() != null) {
         rightCount = ((ExtendedAVLNode) getRight()).getSubtreeKeyCount();
      }
      subtreeKeyCount = leftCount + rightCount + 1;
   }

   @Override
   public void updateHeight() {
      super.updateHeight();
      updateSubtreeKeyCount();
   }
}