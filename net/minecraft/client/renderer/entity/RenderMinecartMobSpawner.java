/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
/*    */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class RenderMinecartMobSpawner extends RenderMinecart<EntityMinecartMobSpawner> {
/*    */   public RenderMinecartMobSpawner(RenderManager renderManagerIn) {
/* 10 */     super(renderManagerIn);
/*    */   }
/*    */   
/*    */   protected void func_180560_a(EntityMinecartMobSpawner minecart, float partialTicks, IBlockState state) {
/* 14 */     super.func_180560_a(minecart, partialTicks, state);
/*    */     
/* 16 */     if (state.getBlock() == Blocks.mob_spawner)
/* 17 */       TileEntityMobSpawnerRenderer.renderMob(minecart.func_98039_d(), minecart.posX, minecart.posY, minecart.posZ, partialTicks); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderMinecartMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */