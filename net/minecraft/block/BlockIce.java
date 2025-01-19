/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockIce extends BlockBreakable {
/*    */   public BlockIce() {
/* 21 */     super(Material.ice, false);
/* 22 */     this.slipperiness = 0.98F;
/* 23 */     setTickRandomly(true);
/* 24 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 28 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*    */   }
/*    */   
/*    */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 32 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 33 */     player.addExhaustion(0.025F);
/*    */     
/* 35 */     if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier((EntityLivingBase)player)) {
/* 36 */       ItemStack itemstack = createStackedBlock(state);
/*    */       
/* 38 */       if (itemstack != null) {
/* 39 */         spawnAsEntity(worldIn, pos, itemstack);
/*    */       }
/*    */     } else {
/* 42 */       if (worldIn.provider.doesWaterVaporize()) {
/* 43 */         worldIn.setBlockToAir(pos);
/*    */         
/*    */         return;
/*    */       } 
/* 47 */       int i = EnchantmentHelper.getFortuneModifier((EntityLivingBase)player);
/* 48 */       dropBlockAsItem(worldIn, pos, state, i);
/* 49 */       Material material = worldIn.getBlockState(pos.down()).getBlock().getMaterial();
/*    */       
/* 51 */       if (material.blocksMovement() || material.isLiquid()) {
/* 52 */         worldIn.setBlockState(pos, Blocks.flowing_water.getDefaultState());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 61 */     return 0;
/*    */   }
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 65 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - getLightOpacity()) {
/* 66 */       if (worldIn.provider.doesWaterVaporize()) {
/* 67 */         worldIn.setBlockToAir(pos);
/*    */       } else {
/* 69 */         dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 70 */         worldIn.setBlockState(pos, Blocks.water.getDefaultState());
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public int getMobilityFlag() {
/* 76 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */