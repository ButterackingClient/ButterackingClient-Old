/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockRedstoneLight
/*    */   extends Block {
/*    */   private final boolean isOn;
/*    */   
/*    */   public BlockRedstoneLight(boolean isOn) {
/* 17 */     super(Material.redstoneLight);
/* 18 */     this.isOn = isOn;
/*    */     
/* 20 */     if (isOn) {
/* 21 */       setLightLevel(1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 26 */     if (!worldIn.isRemote) {
/* 27 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/* 28 */         worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
/* 29 */       } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/* 30 */         worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 39 */     if (!worldIn.isRemote) {
/* 40 */       if (this.isOn && !worldIn.isBlockPowered(pos)) {
/* 41 */         worldIn.scheduleUpdate(pos, this, 4);
/* 42 */       } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
/* 43 */         worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 49 */     if (!worldIn.isRemote && 
/* 50 */       this.isOn && !worldIn.isBlockPowered(pos)) {
/* 51 */       worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 60 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/*    */   }
/*    */   
/*    */   public Item getItem(World worldIn, BlockPos pos) {
/* 64 */     return Item.getItemFromBlock(Blocks.redstone_lamp);
/*    */   }
/*    */   
/*    */   protected ItemStack createStackedBlock(IBlockState state) {
/* 68 */     return new ItemStack(Blocks.redstone_lamp);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */