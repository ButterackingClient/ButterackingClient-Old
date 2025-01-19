/*    */ package client.mod.impl;
/*    */ import client.MouseEvent;
/*    */ import client.event.SubscribeEvent;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class OldHeadPoint extends HudMod {
/*    */   public OldHeadPoint() {
/* 11 */     super("Old HeadPoint", 500000, 5000000, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 16 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 21 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 26 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onMouse(MouseEvent event) {
/* 31 */     if (isEnabled() && (event.dx != 0 || event.dy != 0)) {
/* 32 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).thePlayer;
/* 33 */       if (entityPlayerSP != null) {
/* 34 */         ((EntityPlayer)entityPlayerSP).prevRenderYawOffset = ((EntityPlayer)entityPlayerSP).renderYawOffset;
/* 35 */         ((EntityPlayer)entityPlayerSP).prevRotationYawHead = ((EntityPlayer)entityPlayerSP).rotationYawHead;
/* 36 */         ((EntityPlayer)entityPlayerSP).prevRotationYaw = ((EntityPlayer)entityPlayerSP).rotationYaw;
/* 37 */         ((EntityPlayer)entityPlayerSP).prevRotationPitch = ((EntityPlayer)entityPlayerSP).rotationPitch;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\OldHeadPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */