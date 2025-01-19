/*    */ package net.optifine.render;
/*    */ 
/*    */ import net.optifine.util.LinkedList;
/*    */ 
/*    */ public class VboRange {
/*  6 */   private int position = -1;
/*  7 */   private int size = 0;
/*  8 */   private LinkedList.Node<VboRange> node = new LinkedList.Node(this);
/*    */   
/*    */   public int getPosition() {
/* 11 */     return this.position;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 15 */     return this.size;
/*    */   }
/*    */   
/*    */   public int getPositionNext() {
/* 19 */     return this.position + this.size;
/*    */   }
/*    */   
/*    */   public void setPosition(int position) {
/* 23 */     this.position = position;
/*    */   }
/*    */   
/*    */   public void setSize(int size) {
/* 27 */     this.size = size;
/*    */   }
/*    */   
/*    */   public LinkedList.Node<VboRange> getNode() {
/* 31 */     return this.node;
/*    */   }
/*    */   
/*    */   public VboRange getPrev() {
/* 35 */     LinkedList.Node<VboRange> node = this.node.getPrev();
/* 36 */     return (node == null) ? null : (VboRange)node.getItem();
/*    */   }
/*    */   
/*    */   public VboRange getNext() {
/* 40 */     LinkedList.Node<VboRange> node = this.node.getNext();
/* 41 */     return (node == null) ? null : (VboRange)node.getItem();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return this.position + "/" + this.size + "/" + (this.position + this.size);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\VboRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */