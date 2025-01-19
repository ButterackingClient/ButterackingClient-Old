/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlatLayerInfo
/*    */ {
/*    */   private final int field_175902_a;
/*    */   private IBlockState layerMaterial;
/*    */   private int layerCount;
/*    */   private int layerMinimumY;
/*    */   
/*    */   public FlatLayerInfo(int p_i45467_1_, Block p_i45467_2_) {
/* 18 */     this(3, p_i45467_1_, p_i45467_2_);
/*    */   }
/*    */   
/*    */   public FlatLayerInfo(int p_i45627_1_, int height, Block layerMaterialIn) {
/* 22 */     this.layerCount = 1;
/* 23 */     this.field_175902_a = p_i45627_1_;
/* 24 */     this.layerCount = height;
/* 25 */     this.layerMaterial = layerMaterialIn.getDefaultState();
/*    */   }
/*    */   
/*    */   public FlatLayerInfo(int p_i45628_1_, int p_i45628_2_, Block p_i45628_3_, int p_i45628_4_) {
/* 29 */     this(p_i45628_1_, p_i45628_2_, p_i45628_3_);
/* 30 */     this.layerMaterial = p_i45628_3_.getStateFromMeta(p_i45628_4_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLayerCount() {
/* 37 */     return this.layerCount;
/*    */   }
/*    */   
/*    */   public IBlockState getLayerMaterial() {
/* 41 */     return this.layerMaterial;
/*    */   }
/*    */   
/*    */   private Block getLayerMaterialBlock() {
/* 45 */     return this.layerMaterial.getBlock();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int getFillBlockMeta() {
/* 52 */     return this.layerMaterial.getBlock().getMetaFromState(this.layerMaterial);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinY() {
/* 59 */     return this.layerMinimumY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMinY(int minY) {
/* 66 */     this.layerMinimumY = minY;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/*    */     String s;
/* 72 */     if (this.field_175902_a >= 3) {
/* 73 */       ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(getLayerMaterialBlock());
/* 74 */       s = (resourcelocation == null) ? "null" : resourcelocation.toString();
/*    */       
/* 76 */       if (this.layerCount > 1) {
/* 77 */         s = String.valueOf(this.layerCount) + "*" + s;
/*    */       }
/*    */     } else {
/* 80 */       s = Integer.toString(Block.getIdFromBlock(getLayerMaterialBlock()));
/*    */       
/* 82 */       if (this.layerCount > 1) {
/* 83 */         s = String.valueOf(this.layerCount) + "x" + s;
/*    */       }
/*    */     } 
/*    */     
/* 87 */     int i = getFillBlockMeta();
/*    */     
/* 89 */     if (i > 0) {
/* 90 */       s = String.valueOf(s) + ":" + i;
/*    */     }
/*    */     
/* 93 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\FlatLayerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */