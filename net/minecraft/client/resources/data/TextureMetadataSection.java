/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class TextureMetadataSection implements IMetadataSection {
/*    */   private final boolean textureBlur;
/*    */   private final boolean textureClamp;
/*    */   private final List<Integer> listMipmaps;
/*    */   
/*    */   public TextureMetadataSection(boolean p_i45102_1_, boolean p_i45102_2_, List<Integer> p_i45102_3_) {
/* 12 */     this.textureBlur = p_i45102_1_;
/* 13 */     this.textureClamp = p_i45102_2_;
/* 14 */     this.listMipmaps = p_i45102_3_;
/*    */   }
/*    */   
/*    */   public boolean getTextureBlur() {
/* 18 */     return this.textureBlur;
/*    */   }
/*    */   
/*    */   public boolean getTextureClamp() {
/* 22 */     return this.textureClamp;
/*    */   }
/*    */   
/*    */   public List<Integer> getListMipmaps() {
/* 26 */     return Collections.unmodifiableList(this.listMipmaps);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\TextureMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */