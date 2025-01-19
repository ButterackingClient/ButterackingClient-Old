/*    */ package net.optifine.player;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PlayerConfiguration {
/*  8 */   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
/*    */   private boolean initialized = false;
/*    */   
/*    */   public void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/* 12 */     if (this.initialized) {
/* 13 */       for (int i = 0; i < this.playerItemModels.length; i++) {
/* 14 */         PlayerItemModel playeritemmodel = this.playerItemModels[i];
/* 15 */         playeritemmodel.render(modelBiped, player, scale, partialTicks);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isInitialized() {
/* 21 */     return this.initialized;
/*    */   }
/*    */   
/*    */   public void setInitialized(boolean initialized) {
/* 25 */     this.initialized = initialized;
/*    */   }
/*    */   
/*    */   public PlayerItemModel[] getPlayerItemModels() {
/* 29 */     return this.playerItemModels;
/*    */   }
/*    */   
/*    */   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
/* 33 */     this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray((Object[])this.playerItemModels, playerItemModel);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */