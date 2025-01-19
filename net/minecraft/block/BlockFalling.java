/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockFalling extends Block {
/*    */   public static boolean fallInstantly;
/*    */   
/*    */   public BlockFalling() {
/* 17 */     super(Material.sand);
/* 18 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */   
/*    */   public BlockFalling(Material materialIn) {
/* 22 */     super(materialIn);
/*    */   }
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 26 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 33 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*    */   }
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 37 */     if (!worldIn.isRemote) {
/* 38 */       checkFallable(worldIn, pos);
/*    */     }
/*    */   }
/*    */   
/*    */   private void checkFallable(World worldIn, BlockPos pos) {
/* 43 */     if (canFallInto(worldIn, pos.down()) && pos.getY() >= 0) {
/* 44 */       int i = 32;
/*    */       
/* 46 */       if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/* 47 */         if (!worldIn.isRemote) {
/* 48 */           EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, worldIn.getBlockState(pos));
/* 49 */           onStartFalling(entityfallingblock);
/* 50 */           worldIn.spawnEntityInWorld((Entity)entityfallingblock);
/*    */         } 
/*    */       } else {
/* 53 */         worldIn.setBlockToAir(pos);
/*    */         
/*    */         BlockPos blockpos;
/* 56 */         for (blockpos = pos.down(); canFallInto(worldIn, blockpos) && blockpos.getY() > 0; blockpos = blockpos.down());
/*    */ 
/*    */ 
/*    */         
/* 60 */         if (blockpos.getY() > 0) {
/* 61 */           worldIn.setBlockState(blockpos.up(), getDefaultState());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onStartFalling(EntityFallingBlock fallingEntity) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int tickRate(World worldIn) {
/* 74 */     return 2;
/*    */   }
/*    */   
/*    */   public static boolean canFallInto(World worldIn, BlockPos pos) {
/* 78 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 79 */     Material material = block.blockMaterial;
/* 80 */     return !(block != Blocks.fire && material != Material.air && material != Material.water && material != Material.lava);
/*    */   }
/*    */   
/*    */   public void onEndFalling(World worldIn, BlockPos pos) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFalling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */