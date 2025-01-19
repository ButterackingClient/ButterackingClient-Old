/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PlayerItemsLayer implements LayerRenderer {
/* 15 */   private RenderPlayer renderPlayer = null;
/*    */   
/*    */   public PlayerItemsLayer(RenderPlayer renderPlayer) {
/* 18 */     this.renderPlayer = renderPlayer;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ticksExisted, float headYaw, float rotationPitch, float scale) {
/* 22 */     renderEquippedItems(entityLiving, scale, partialTicks);
/*    */   }
/*    */   
/*    */   protected void renderEquippedItems(EntityLivingBase entityLiving, float scale, float partialTicks) {
/* 26 */     if (Config.isShowCapes() && 
/* 27 */       entityLiving instanceof AbstractClientPlayer) {
/* 28 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entityLiving;
/* 29 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 30 */       GlStateManager.disableRescaleNormal();
/* 31 */       GlStateManager.enableCull();
/* 32 */       ModelPlayer modelPlayer = this.renderPlayer.getMainModel();
/* 33 */       PlayerConfigurations.renderPlayerItems((ModelBiped)modelPlayer, abstractclientplayer, scale, partialTicks);
/* 34 */       GlStateManager.disableCull();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 40 */     return false;
/*    */   }
/*    */   
/*    */   public static void register(Map renderPlayerMap) {
/* 44 */     Set set = renderPlayerMap.keySet();
/* 45 */     boolean flag = false;
/*    */     
/* 47 */     for (Object object : set) {
/* 48 */       Object object1 = renderPlayerMap.get(object);
/*    */       
/* 50 */       if (object1 instanceof RenderPlayer) {
/* 51 */         RenderPlayer renderplayer = (RenderPlayer)object1;
/* 52 */         renderplayer.addLayer(new PlayerItemsLayer(renderplayer));
/* 53 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     if (!flag)
/* 58 */       Config.warn("PlayerItemsLayer not registered"); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerItemsLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */