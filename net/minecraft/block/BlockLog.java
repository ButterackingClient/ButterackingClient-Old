/*    */ package net.minecraft.block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class BlockLog extends BlockRotatedPillar {
/* 14 */   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);
/*    */   
/*    */   public BlockLog() {
/* 17 */     super(Material.wood);
/* 18 */     setCreativeTab(CreativeTabs.tabBlock);
/* 19 */     setHardness(2.0F);
/* 20 */     setStepSound(soundTypeWood);
/*    */   }
/*    */   
/*    */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 24 */     int i = 4;
/* 25 */     int j = i + 1;
/*    */     
/* 27 */     if (worldIn.isAreaLoaded(pos.add(-j, -j, -j), pos.add(j, j, j))) {
/* 28 */       for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/* 29 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*    */         
/* 31 */         if (iblockstate.getBlock().getMaterial() == Material.leaves && !((Boolean)iblockstate.getValue((IProperty)BlockLeaves.CHECK_DECAY)).booleanValue()) {
/* 32 */           worldIn.setBlockState(blockpos, iblockstate.withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(true)), 4);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 43 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
/*    */   }
/*    */   
/*    */   public enum EnumAxis implements IStringSerializable {
/* 47 */     X("x"),
/* 48 */     Y("y"),
/* 49 */     Z("z"),
/* 50 */     NONE("none");
/*    */     
/*    */     private final String name;
/*    */     
/*    */     EnumAxis(String name) {
/* 55 */       this.name = name;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 59 */       return this.name;
/*    */     }
/*    */     
/*    */     public static EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
/* 63 */       switch (axis) {
/*    */         case null:
/* 65 */           return X;
/*    */         
/*    */         case Y:
/* 68 */           return Y;
/*    */         
/*    */         case Z:
/* 71 */           return Z;
/*    */       } 
/*    */       
/* 74 */       return NONE;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getName() {
/* 79 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */