/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class VertexFormatElement {
/*   7 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final EnumType type;
/*     */   private final EnumUsage usage;
/*     */   private int index;
/*     */   private int elementCount;
/*     */   
/*     */   public VertexFormatElement(int indexIn, EnumType typeIn, EnumUsage usageIn, int count) {
/*  14 */     if (!func_177372_a(indexIn, usageIn)) {
/*  15 */       LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
/*  16 */       this.usage = EnumUsage.UV;
/*     */     } else {
/*  18 */       this.usage = usageIn;
/*     */     } 
/*     */     
/*  21 */     this.type = typeIn;
/*  22 */     this.index = indexIn;
/*  23 */     this.elementCount = count;
/*     */   }
/*     */   
/*     */   private final boolean func_177372_a(int p_177372_1_, EnumUsage p_177372_2_) {
/*  27 */     return !(p_177372_1_ != 0 && p_177372_2_ != EnumUsage.UV);
/*     */   }
/*     */   
/*     */   public final EnumType getType() {
/*  31 */     return this.type;
/*     */   }
/*     */   
/*     */   public final EnumUsage getUsage() {
/*  35 */     return this.usage;
/*     */   }
/*     */   
/*     */   public final int getElementCount() {
/*  39 */     return this.elementCount;
/*     */   }
/*     */   
/*     */   public final int getIndex() {
/*  43 */     return this.index;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  47 */     return String.valueOf(this.elementCount) + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
/*     */   }
/*     */   
/*     */   public final int getSize() {
/*  51 */     return this.type.getSize() * this.elementCount;
/*     */   }
/*     */   
/*     */   public final boolean isPositionElement() {
/*  55 */     return (this.usage == EnumUsage.POSITION);
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  59 */     if (this == p_equals_1_)
/*  60 */       return true; 
/*  61 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*  62 */       VertexFormatElement vertexformatelement = (VertexFormatElement)p_equals_1_;
/*  63 */       return (this.elementCount != vertexformatelement.elementCount) ? false : ((this.index != vertexformatelement.index) ? false : ((this.type != vertexformatelement.type) ? false : ((this.usage == vertexformatelement.usage))));
/*     */     } 
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  70 */     int i = this.type.hashCode();
/*  71 */     i = 31 * i + this.usage.hashCode();
/*  72 */     i = 31 * i + this.index;
/*  73 */     i = 31 * i + this.elementCount;
/*  74 */     return i;
/*     */   }
/*     */   
/*     */   public enum EnumType {
/*  78 */     FLOAT(4, "Float", 5126),
/*  79 */     UBYTE(1, "Unsigned Byte", 5121),
/*  80 */     BYTE(1, "Byte", 5120),
/*  81 */     USHORT(2, "Unsigned Short", 5123),
/*  82 */     SHORT(2, "Short", 5122),
/*  83 */     UINT(4, "Unsigned Int", 5125),
/*  84 */     INT(4, "Int", 5124);
/*     */     
/*     */     private final int size;
/*     */     private final String displayName;
/*     */     private final int glConstant;
/*     */     
/*     */     EnumType(int sizeIn, String displayNameIn, int glConstantIn) {
/*  91 */       this.size = sizeIn;
/*  92 */       this.displayName = displayNameIn;
/*  93 */       this.glConstant = glConstantIn;
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  97 */       return this.size;
/*     */     }
/*     */     
/*     */     public String getDisplayName() {
/* 101 */       return this.displayName;
/*     */     }
/*     */     
/*     */     public int getGlConstant() {
/* 105 */       return this.glConstant;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumUsage {
/* 110 */     POSITION("Position"),
/* 111 */     NORMAL("Normal"),
/* 112 */     COLOR("Vertex Color"),
/* 113 */     UV("UV"),
/* 114 */     MATRIX("Bone Matrix"),
/* 115 */     BLEND_WEIGHT("Blend Weight"),
/* 116 */     PADDING("Padding");
/*     */     
/*     */     private final String displayName;
/*     */     
/*     */     EnumUsage(String displayNameIn) {
/* 121 */       this.displayName = displayNameIn;
/*     */     }
/*     */     
/*     */     public String getDisplayName() {
/* 125 */       return this.displayName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\vertex\VertexFormatElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */