/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class VertexFormat
/*     */ {
/*  11 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat(VertexFormat vertexFormatIn) {
/*  24 */     this();
/*     */     
/*  26 */     for (int i = 0; i < vertexFormatIn.getElementCount(); i++) {
/*  27 */       addElement(vertexFormatIn.getElement(i));
/*     */     }
/*     */     
/*  30 */     this.nextOffset = vertexFormatIn.getNextOffset();
/*     */   }
/*     */ 
/*     */   
/*  34 */   private final List<VertexFormatElement> elements = Lists.newArrayList();
/*  35 */   private final List<Integer> offsets = Lists.newArrayList();
/*  36 */   private int nextOffset = 0;
/*  37 */   private int colorElementOffset = -1;
/*  38 */   private List<Integer> uvOffsetsById = Lists.newArrayList();
/*  39 */   private int normalElementOffset = -1;
/*     */ 
/*     */   
/*     */   public void clear() {
/*  43 */     this.elements.clear();
/*  44 */     this.offsets.clear();
/*  45 */     this.colorElementOffset = -1;
/*  46 */     this.uvOffsetsById.clear();
/*  47 */     this.normalElementOffset = -1;
/*  48 */     this.nextOffset = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat addElement(VertexFormatElement element) {
/*  53 */     if (element.isPositionElement() && hasPosition()) {
/*  54 */       LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
/*  55 */       return this;
/*     */     } 
/*  57 */     this.elements.add(element);
/*  58 */     this.offsets.add(Integer.valueOf(this.nextOffset));
/*     */     
/*  60 */     switch (element.getUsage()) {
/*     */       case NORMAL:
/*  62 */         this.normalElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case COLOR:
/*  66 */         this.colorElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case UV:
/*  70 */         this.uvOffsetsById.add(element.getIndex(), Integer.valueOf(this.nextOffset));
/*     */         break;
/*     */     } 
/*  73 */     this.nextOffset += element.getSize();
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNormal() {
/*  79 */     return (this.normalElementOffset >= 0);
/*     */   }
/*     */   
/*     */   public int getNormalOffset() {
/*  83 */     return this.normalElementOffset;
/*     */   }
/*     */   
/*     */   public boolean hasColor() {
/*  87 */     return (this.colorElementOffset >= 0);
/*     */   }
/*     */   
/*     */   public int getColorOffset() {
/*  91 */     return this.colorElementOffset;
/*     */   }
/*     */   
/*     */   public boolean hasUvOffset(int id) {
/*  95 */     return (this.uvOffsetsById.size() - 1 >= id);
/*     */   }
/*     */   
/*     */   public int getUvOffsetById(int id) {
/*  99 */     return ((Integer)this.uvOffsetsById.get(id)).intValue();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     String s = "format: " + this.elements.size() + " elements: ";
/*     */     
/* 105 */     for (int i = 0; i < this.elements.size(); i++) {
/* 106 */       s = String.valueOf(s) + ((VertexFormatElement)this.elements.get(i)).toString();
/*     */       
/* 108 */       if (i != this.elements.size() - 1) {
/* 109 */         s = String.valueOf(s) + " ";
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return s;
/*     */   }
/*     */   
/*     */   private boolean hasPosition() {
/* 117 */     int i = 0;
/*     */     
/* 119 */     for (int j = this.elements.size(); i < j; i++) {
/* 120 */       VertexFormatElement vertexformatelement = this.elements.get(i);
/*     */       
/* 122 */       if (vertexformatelement.isPositionElement()) {
/* 123 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   public int getIntegerSize() {
/* 131 */     return getNextOffset() / 4;
/*     */   }
/*     */   
/*     */   public int getNextOffset() {
/* 135 */     return this.nextOffset;
/*     */   }
/*     */   
/*     */   public List<VertexFormatElement> getElements() {
/* 139 */     return this.elements;
/*     */   }
/*     */   
/*     */   public int getElementCount() {
/* 143 */     return this.elements.size();
/*     */   }
/*     */   
/*     */   public VertexFormatElement getElement(int index) {
/* 147 */     return this.elements.get(index);
/*     */   }
/*     */   
/*     */   public int getOffset(int p_181720_1_) {
/* 151 */     return ((Integer)this.offsets.get(p_181720_1_)).intValue();
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 155 */     if (this == p_equals_1_)
/* 156 */       return true; 
/* 157 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 158 */       VertexFormat vertexformat = (VertexFormat)p_equals_1_;
/* 159 */       return (this.nextOffset != vertexformat.nextOffset) ? false : (!this.elements.equals(vertexformat.elements) ? false : this.offsets.equals(vertexformat.offsets));
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 166 */     int i = this.elements.hashCode();
/* 167 */     i = 31 * i + this.offsets.hashCode();
/* 168 */     i = 31 * i + this.nextOffset;
/* 169 */     return i;
/*     */   }
/*     */   
/*     */   public VertexFormat() {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\vertex\VertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */