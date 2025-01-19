/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSlime extends BlockBreakable {
/*    */   public BlockSlime() {
/* 13 */     super(Material.clay, false, MapColor.grassColor);
/* 14 */     setCreativeTab(CreativeTabs.tabDecorations);
/* 15 */     this.slipperiness = 0.8F;
/*    */   }
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 19 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 26 */     if (entityIn.isSneaking()) {
/* 27 */       super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*    */     } else {
/* 29 */       entityIn.fall(fallDistance, 0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLanded(World worldIn, Entity entityIn) {
/* 38 */     if (entityIn.isSneaking()) {
/* 39 */       super.onLanded(worldIn, entityIn);
/* 40 */     } else if (entityIn.motionY < 0.0D) {
/* 41 */       entityIn.motionY = -entityIn.motionY;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
/* 49 */     if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking()) {
/* 50 */       double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
/* 51 */       entityIn.motionX *= d0;
/* 52 */       entityIn.motionZ *= d0;
/*    */     } 
/*    */     
/* 55 */     super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */