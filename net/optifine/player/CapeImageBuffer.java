/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CapeImageBuffer
/*    */   extends ImageBufferDownload {
/*    */   private AbstractClientPlayer player;
/*    */   private ResourceLocation resourceLocation;
/*    */   private boolean elytraOfCape;
/*    */   
/*    */   public CapeImageBuffer(AbstractClientPlayer player, ResourceLocation resourceLocation) {
/* 15 */     this.player = player;
/* 16 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */   
/*    */   public BufferedImage parseUserSkin(BufferedImage imageRaw) {
/* 20 */     BufferedImage bufferedimage = CapeUtils.parseCape(imageRaw);
/* 21 */     this.elytraOfCape = CapeUtils.isElytraCape(imageRaw, bufferedimage);
/* 22 */     return bufferedimage;
/*    */   }
/*    */   
/*    */   public void skinAvailable() {
/* 26 */     if (this.player != null) {
/* 27 */       this.player.setLocationOfCape(this.resourceLocation);
/* 28 */       this.player.setElytraOfCape(this.elytraOfCape);
/*    */     } 
/*    */     
/* 31 */     cleanup();
/*    */   }
/*    */   
/*    */   public void cleanup() {
/* 35 */     this.player = null;
/*    */   }
/*    */   
/*    */   public boolean isElytraOfCape() {
/* 39 */     return this.elytraOfCape;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\CapeImageBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */