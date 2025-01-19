/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class PackMetadataSection implements IMetadataSection {
/*    */   private final IChatComponent packDescription;
/*    */   private final int packFormat;
/*    */   
/*    */   public PackMetadataSection(IChatComponent p_i1034_1_, int p_i1034_2_) {
/* 10 */     this.packDescription = p_i1034_1_;
/* 11 */     this.packFormat = p_i1034_2_;
/*    */   }
/*    */   
/*    */   public IChatComponent getPackDescription() {
/* 15 */     return this.packDescription;
/*    */   }
/*    */   
/*    */   public int getPackFormat() {
/* 19 */     return this.packFormat;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\PackMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */