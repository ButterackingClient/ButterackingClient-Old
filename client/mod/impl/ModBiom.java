/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class ModBiom extends HudMod {
/*    */   public ModBiom() {
/* 10 */     super("Biome", 5, 15, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 15 */     if (!(Client.getInstance()).isGuiCovered) {
/* 16 */       return fr.getStringWidth("[Biome] : Desert");
/*    */     }
/* 18 */     return fr.getStringWidth("Desert");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw() {
/* 24 */     BlockPos blockpos = new BlockPos((mc.getRenderViewEntity()).posX, (mc.getRenderViewEntity().getEntityBoundingBox()).minY, (mc.getRenderViewEntity()).posZ);
/* 25 */     Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockpos);
/* 26 */     if (!(Client.getInstance()).isGuiCovered) {
/* 27 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Biome" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + (chunk.getBiome(blockpos, mc.theWorld.getWorldChunkManager())).biomeName, x(), y(), -1);
/*    */     } else {
/* 29 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + (chunk.getBiome(blockpos, mc.theWorld.getWorldChunkManager())).biomeName, x(), y(), -1);
/*    */     } 
/* 31 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 36 */     draw();
/* 37 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModBiom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */