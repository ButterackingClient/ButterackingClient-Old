/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ public class DefaultTexture extends AbstractTexture {
/*    */   public DefaultTexture() {
/*  8 */     loadTexture(null);
/*    */   }
/*    */   
/*    */   public void loadTexture(IResourceManager resourcemanager) {
/* 12 */     int[] aint = ShadersTex.createAIntImage(1, -1);
/* 13 */     ShadersTex.setupTexture(getMultiTexID(), aint, 1, 1, false, false);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\DefaultTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */