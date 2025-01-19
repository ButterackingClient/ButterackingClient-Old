/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ 
/*    */ public class RegionRenderCacheBuilder {
/*  6 */   private final WorldRenderer[] worldRenderers = new WorldRenderer[(EnumWorldBlockLayer.values()).length];
/*    */   
/*    */   public RegionRenderCacheBuilder() {
/*  9 */     this.worldRenderers[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(2097152);
/* 10 */     this.worldRenderers[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(131072);
/* 11 */     this.worldRenderers[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(131072);
/* 12 */     this.worldRenderers[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(262144);
/*    */   }
/*    */   
/*    */   public WorldRenderer getWorldRendererByLayer(EnumWorldBlockLayer layer) {
/* 16 */     return this.worldRenderers[layer.ordinal()];
/*    */   }
/*    */   
/*    */   public WorldRenderer getWorldRendererByLayerId(int id) {
/* 20 */     return this.worldRenderers[id];
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\RegionRenderCacheBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */