/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockAir
/*    */   extends Block {
/* 13 */   private static Map mapOriginalOpacity = new IdentityHashMap<>();
/*    */   
/*    */   protected BlockAir() {
/* 16 */     super(Material.air);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRenderType() {
/* 23 */     return -1;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   public static void setLightOpacity(Block p_setLightOpacity_0_, int p_setLightOpacity_1_) {
/* 55 */     if (!mapOriginalOpacity.containsKey(p_setLightOpacity_0_)) {
/* 56 */       mapOriginalOpacity.put(p_setLightOpacity_0_, Integer.valueOf(p_setLightOpacity_0_.lightOpacity));
/*    */     }
/*    */     
/* 59 */     p_setLightOpacity_0_.lightOpacity = p_setLightOpacity_1_;
/*    */   }
/*    */   
/*    */   public static void restoreLightOpacity(Block p_restoreLightOpacity_0_) {
/* 63 */     if (mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
/* 64 */       int i = ((Integer)mapOriginalOpacity.get(p_restoreLightOpacity_0_)).intValue();
/* 65 */       setLightOpacity(p_restoreLightOpacity_0_, i);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockAir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */