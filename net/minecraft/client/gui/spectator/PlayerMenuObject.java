/*    */ package net.minecraft.client.gui.spectator;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C18PacketSpectate;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PlayerMenuObject implements ISpectatorMenuObject {
/*    */   private final GameProfile profile;
/*    */   
/*    */   public PlayerMenuObject(GameProfile profileIn) {
/* 18 */     this.profile = profileIn;
/* 19 */     this.resourceLocation = AbstractClientPlayer.getLocationSkin(profileIn.getName());
/* 20 */     AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation, profileIn.getName());
/*    */   }
/*    */   private final ResourceLocation resourceLocation;
/*    */   public void func_178661_a(SpectatorMenu menu) {
/* 24 */     Minecraft.getMinecraft().getNetHandler().addToSendQueue((Packet)new C18PacketSpectate(this.profile.getId()));
/*    */   }
/*    */   
/*    */   public IChatComponent getSpectatorName() {
/* 28 */     return (IChatComponent)new ChatComponentText(this.profile.getName());
/*    */   }
/*    */   
/*    */   public void func_178663_a(float p_178663_1_, int alpha) {
/* 32 */     Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
/* 33 */     GlStateManager.color(1.0F, 1.0F, 1.0F, alpha / 255.0F);
/* 34 */     Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/* 35 */     Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/*    */   }
/*    */   
/*    */   public boolean func_178662_A_() {
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\spectator\PlayerMenuObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */