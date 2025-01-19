/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockVine;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenVines extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 14 */     for (; position.getY() < 128; position = position.up()) {
/* 15 */       if (worldIn.isAirBlock(position)) {
/* 16 */         byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.Plane.HORIZONTAL.facings()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 17 */           if (Blocks.vine.canPlaceBlockOnSide(worldIn, position, enumfacing)) {
/* 18 */             IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty((IProperty)BlockVine.NORTH, Boolean.valueOf((enumfacing == EnumFacing.NORTH))).withProperty((IProperty)BlockVine.EAST, Boolean.valueOf((enumfacing == EnumFacing.EAST))).withProperty((IProperty)BlockVine.SOUTH, Boolean.valueOf((enumfacing == EnumFacing.SOUTH))).withProperty((IProperty)BlockVine.WEST, Boolean.valueOf((enumfacing == EnumFacing.WEST)));
/* 19 */             worldIn.setBlockState(position, iblockstate, 2); break;
/*    */           } 
/*    */           b++; }
/*    */       
/*    */       } else {
/* 24 */         position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
/*    */       } 
/*    */     } 
/*    */     
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenVines.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */