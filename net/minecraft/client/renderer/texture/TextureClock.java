/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class TextureClock extends TextureAtlasSprite {
/*    */   private double currentAngle;
/*    */   private double angleDelta;
/*    */   
/*    */   public TextureClock(String iconName) {
/* 11 */     super(iconName);
/*    */   }
/*    */   
/*    */   public void updateAnimation() {
/* 15 */     if (!this.framesTextureData.isEmpty()) {
/* 16 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 17 */       double d0 = 0.0D;
/*    */       
/* 19 */       if (minecraft.theWorld != null && minecraft.thePlayer != null) {
/* 20 */         d0 = minecraft.theWorld.getCelestialAngle(1.0F);
/*    */         
/* 22 */         if (!minecraft.theWorld.provider.isSurfaceWorld()) {
/* 23 */           d0 = Math.random();
/*    */         }
/*    */       } 
/*    */       
/*    */       double d1;
/*    */       
/* 29 */       for (d1 = d0 - this.currentAngle; d1 < -0.5D; d1++);
/*    */ 
/*    */ 
/*    */       
/* 33 */       while (d1 >= 0.5D) {
/* 34 */         d1--;
/*    */       }
/*    */       
/* 37 */       d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
/* 38 */       this.angleDelta += d1 * 0.1D;
/* 39 */       this.angleDelta *= 0.8D;
/* 40 */       this.currentAngle += this.angleDelta;
/*    */       
/*    */       int i;
/* 43 */       for (i = (int)((this.currentAngle + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size());
/*    */ 
/*    */ 
/*    */       
/* 47 */       if (i != this.frameCounter) {
/* 48 */         this.frameCounter = i;
/* 49 */         TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureClock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */