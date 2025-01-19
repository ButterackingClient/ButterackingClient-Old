/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.WeightedRandomChestContent;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGeneratorBonusChest
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final List<WeightedRandomChestContent> chestItems;
/*    */   private final int itemsToGenerateInBonusChest;
/*    */   
/*    */   public WorldGeneratorBonusChest(List<WeightedRandomChestContent> p_i45634_1_, int p_i45634_2_) {
/* 24 */     this.chestItems = p_i45634_1_;
/* 25 */     this.itemsToGenerateInBonusChest = p_i45634_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     Block block;
/* 31 */     while (((block = worldIn.getBlockState(position).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && position.getY() > 1) {
/* 32 */       position = position.down();
/*    */     }
/*    */     
/* 35 */     if (position.getY() < 1) {
/* 36 */       return false;
/*    */     }
/* 38 */     position = position.up();
/*    */     
/* 40 */     for (int i = 0; i < 4; i++) {
/* 41 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(3) - rand.nextInt(3), rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 43 */       if (worldIn.isAirBlock(blockpos) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos.down())) {
/* 44 */         worldIn.setBlockState(blockpos, Blocks.chest.getDefaultState(), 2);
/* 45 */         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*    */         
/* 47 */         if (tileentity instanceof net.minecraft.tileentity.TileEntityChest) {
/* 48 */           WeightedRandomChestContent.generateChestContents(rand, this.chestItems, (IInventory)tileentity, this.itemsToGenerateInBonusChest);
/*    */         }
/*    */         
/* 51 */         BlockPos blockpos1 = blockpos.east();
/* 52 */         BlockPos blockpos2 = blockpos.west();
/* 53 */         BlockPos blockpos3 = blockpos.north();
/* 54 */         BlockPos blockpos4 = blockpos.south();
/*    */         
/* 56 */         if (worldIn.isAirBlock(blockpos2) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos2.down())) {
/* 57 */           worldIn.setBlockState(blockpos2, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 60 */         if (worldIn.isAirBlock(blockpos1) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos1.down())) {
/* 61 */           worldIn.setBlockState(blockpos1, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 64 */         if (worldIn.isAirBlock(blockpos3) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos3.down())) {
/* 65 */           worldIn.setBlockState(blockpos3, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 68 */         if (worldIn.isAirBlock(blockpos4) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos4.down())) {
/* 69 */           worldIn.setBlockState(blockpos4, Blocks.torch.getDefaultState(), 2);
/*    */         }
/*    */         
/* 72 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGeneratorBonusChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */