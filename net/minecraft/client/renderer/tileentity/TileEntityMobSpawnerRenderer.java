/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*    */ 
/*    */ public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
/*    */   public void renderTileEntityAt(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage) {
/* 11 */     GlStateManager.pushMatrix();
/* 12 */     GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
/* 13 */     renderMob(te.getSpawnerBaseLogic(), x, y, z, partialTicks);
/* 14 */     GlStateManager.popMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void renderMob(MobSpawnerBaseLogic mobSpawnerLogic, double posX, double posY, double posZ, float partialTicks) {
/* 21 */     Entity entity = mobSpawnerLogic.func_180612_a(mobSpawnerLogic.getSpawnerWorld());
/*    */     
/* 23 */     if (entity != null) {
/* 24 */       float f = 0.4375F;
/* 25 */       GlStateManager.translate(0.0F, 0.4F, 0.0F);
/* 26 */       GlStateManager.rotate((float)(mobSpawnerLogic.getPrevMobRotation() + (mobSpawnerLogic.getMobRotation() - mobSpawnerLogic.getPrevMobRotation()) * partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 27 */       GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
/* 28 */       GlStateManager.translate(0.0F, -0.4F, 0.0F);
/* 29 */       GlStateManager.scale(f, f, f);
/* 30 */       entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
/* 31 */       Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityMobSpawnerRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */