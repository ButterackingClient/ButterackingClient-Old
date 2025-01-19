/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class LinkedList<T>
/*     */ {
/*     */   private Node<T> first;
/*     */   private Node<T> last;
/*     */   private int size;
/*     */   
/*     */   public void addFirst(Node<T> tNode) {
/*  12 */     checkNoParent(tNode);
/*     */     
/*  14 */     if (isEmpty()) {
/*  15 */       this.first = tNode;
/*  16 */       this.last = tNode;
/*     */     } else {
/*  18 */       Node<T> node = this.first;
/*  19 */       tNode.setNext(node);
/*  20 */       node.setPrev(tNode);
/*  21 */       this.first = tNode;
/*     */     } 
/*     */     
/*  24 */     tNode.setParent(this);
/*  25 */     this.size++;
/*     */   }
/*     */   
/*     */   public void addLast(Node<T> tNode) {
/*  29 */     checkNoParent(tNode);
/*     */     
/*  31 */     if (isEmpty()) {
/*  32 */       this.first = tNode;
/*  33 */       this.last = tNode;
/*     */     } else {
/*  35 */       Node<T> node = this.last;
/*  36 */       tNode.setPrev(node);
/*  37 */       node.setNext(tNode);
/*  38 */       this.last = tNode;
/*     */     } 
/*     */     
/*  41 */     tNode.setParent(this);
/*  42 */     this.size++;
/*     */   }
/*     */   
/*     */   public void addAfter(Node<T> nodePrev, Node<T> tNode) {
/*  46 */     if (nodePrev == null) {
/*  47 */       addFirst(tNode);
/*  48 */     } else if (nodePrev == this.last) {
/*  49 */       addLast(tNode);
/*     */     } else {
/*  51 */       checkParent(nodePrev);
/*  52 */       checkNoParent(tNode);
/*  53 */       Node<T> nodeNext = nodePrev.getNext();
/*  54 */       nodePrev.setNext(tNode);
/*  55 */       tNode.setPrev(nodePrev);
/*  56 */       nodeNext.setPrev(tNode);
/*  57 */       tNode.setNext(nodeNext);
/*  58 */       tNode.setParent(this);
/*  59 */       this.size++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Node<T> remove(Node<T> tNode) {
/*  64 */     checkParent(tNode);
/*  65 */     Node<T> prev = tNode.getPrev();
/*  66 */     Node<T> next = tNode.getNext();
/*     */     
/*  68 */     if (prev != null) {
/*  69 */       prev.setNext(next);
/*     */     } else {
/*  71 */       this.first = next;
/*     */     } 
/*     */     
/*  74 */     if (next != null) {
/*  75 */       next.setPrev(prev);
/*     */     } else {
/*  77 */       this.last = prev;
/*     */     } 
/*     */     
/*  80 */     tNode.setPrev(null);
/*  81 */     tNode.setNext(null);
/*  82 */     tNode.setParent(null);
/*  83 */     this.size--;
/*  84 */     return tNode;
/*     */   }
/*     */   
/*     */   public void moveAfter(Node<T> nodePrev, Node<T> node) {
/*  88 */     remove(node);
/*  89 */     addAfter(nodePrev, node);
/*     */   }
/*     */   
/*     */   public boolean find(Node<T> nodeFind, Node<T> nodeFrom, Node<T> nodeTo) {
/*  93 */     checkParent(nodeFrom);
/*     */     
/*  95 */     if (nodeTo != null) {
/*  96 */       checkParent(nodeTo);
/*     */     }
/*     */     
/*     */     Node<T> node;
/*     */     
/* 101 */     for (node = nodeFrom; node != null && node != nodeTo; node = node.getNext()) {
/* 102 */       if (node == nodeFind) {
/* 103 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     if (node != nodeTo) {
/* 108 */       throw new IllegalArgumentException("Sublist is not linked, from: " + nodeFrom + ", to: " + nodeTo);
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkParent(Node<T> node) {
/* 115 */     if (node.parent != this) {
/* 116 */       throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkNoParent(Node<T> node) {
/* 121 */     if (node.parent != null) {
/* 122 */       throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean contains(Node<T> node) {
/* 127 */     return (node.parent == this);
/*     */   }
/*     */   
/*     */   public Iterator<Node<T>> iterator() {
/* 131 */     Iterator<Node<T>> iterator = new Iterator<Node<T>>() {
/* 132 */         LinkedList.Node<T> node = LinkedList.this.getFirst();
/*     */         
/*     */         public boolean hasNext() {
/* 135 */           return (this.node != null);
/*     */         }
/*     */         
/*     */         public LinkedList.Node<T> next() {
/* 139 */           LinkedList.Node<T> node = this.node;
/*     */           
/* 141 */           if (this.node != null) {
/* 142 */             this.node = this.node.next;
/*     */           }
/*     */           
/* 145 */           return node;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 149 */           throw new UnsupportedOperationException("remove");
/*     */         }
/*     */       };
/* 152 */     return iterator;
/*     */   }
/*     */   
/*     */   public Node<T> getFirst() {
/* 156 */     return this.first;
/*     */   }
/*     */   
/*     */   public Node<T> getLast() {
/* 160 */     return this.last;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 164 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 168 */     return (this.size <= 0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 172 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 174 */     for (Iterator<Node<T>> it = iterator(); it.hasNext(); ) {
/* 175 */       Node<T> node = it.next();
/* 176 */       if (stringbuffer.length() > 0) {
/* 177 */         stringbuffer.append(", ");
/*     */       }
/* 179 */       stringbuffer.append(node.getItem());
/*     */     } 
/*     */     
/* 182 */     return this.size + " [" + stringbuffer.toString() + "]";
/*     */   }
/*     */   
/*     */   public static class Node<T>
/*     */   {
/*     */     private final T item;
/*     */     private Node<T> prev;
/*     */     private Node<T> next;
/*     */     private LinkedList<T> parent;
/*     */     
/*     */     public Node(T item) {
/* 193 */       this.item = item;
/*     */     }
/*     */     
/*     */     public T getItem() {
/* 197 */       return this.item;
/*     */     }
/*     */     
/*     */     public Node<T> getPrev() {
/* 201 */       return this.prev;
/*     */     }
/*     */     
/*     */     public Node<T> getNext() {
/* 205 */       return this.next;
/*     */     }
/*     */     
/*     */     private void setPrev(Node<T> prev) {
/* 209 */       this.prev = prev;
/*     */     }
/*     */     
/*     */     private void setNext(Node<T> next) {
/* 213 */       this.next = next;
/*     */     }
/*     */     
/*     */     private void setParent(LinkedList<T> parent) {
/* 217 */       this.parent = parent;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 221 */       return (String)this.item;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\LinkedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */