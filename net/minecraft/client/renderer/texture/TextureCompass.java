/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureCompass
/*    */   extends TextureAtlasSprite
/*    */ {
/*    */   public double currentAngle;
/*    */   public double angleDelta;
/*    */   public static String locationSprite;
/*    */   
/*    */   public TextureCompass(String iconName) {
/* 21 */     super(iconName);
/* 22 */     locationSprite = iconName;
/*    */   }
/*    */   
/*    */   public void updateAnimation() {
/* 26 */     Minecraft minecraft = Minecraft.getMinecraft();
/*    */     
/* 28 */     if (minecraft.theWorld != null && minecraft.thePlayer != null) {
/* 29 */       updateCompass((World)minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, minecraft.thePlayer.rotationYaw, false, false);
/*    */     } else {
/* 31 */       updateCompass((World)null, 0.0D, 0.0D, 0.0D, true, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateCompass(World worldIn, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_) {
/* 39 */     if (!this.framesTextureData.isEmpty()) {
/* 40 */       double d0 = 0.0D;
/*    */       
/* 42 */       if (worldIn != null && !p_94241_8_) {
/* 43 */         BlockPos blockpos = worldIn.getSpawnPoint();
/* 44 */         double d1 = blockpos.getX() - p_94241_2_;
/* 45 */         double d2 = blockpos.getZ() - p_94241_4_;
/* 46 */         p_94241_6_ %= 360.0D;
/* 47 */         d0 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d2, d1));
/*    */         
/* 49 */         if (!worldIn.provider.isSurfaceWorld()) {
/* 50 */           d0 = Math.random() * Math.PI * 2.0D;
/*    */         }
/*    */       } 
/*    */       
/* 54 */       if (p_94241_9_) {
/* 55 */         this.currentAngle = d0;
/*    */       } else {
/*    */         double d3;
/*    */         
/* 59 */         for (d3 = d0 - this.currentAngle; d3 < -3.141592653589793D; d3 += 6.283185307179586D);
/*    */ 
/*    */ 
/*    */         
/* 63 */         while (d3 >= Math.PI) {
/* 64 */           d3 -= 6.283185307179586D;
/*    */         }
/*    */         
/* 67 */         d3 = MathHelper.clamp_double(d3, -1.0D, 1.0D);
/* 68 */         this.angleDelta += d3 * 0.1D;
/* 69 */         this.angleDelta *= 0.8D;
/* 70 */         this.currentAngle += this.angleDelta;
/*    */       } 
/*    */       
/*    */       int i;
/*    */       
/* 75 */       for (i = (int)((this.currentAngle / 6.283185307179586D + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size());
/*    */ 
/*    */ 
/*    */       
/* 79 */       if (i != this.frameCounter) {
/* 80 */         this.frameCounter = i;
/* 81 */         TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureCompass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */