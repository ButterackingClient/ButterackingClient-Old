/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
/* 15 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*    */   private final int field_150068_a;
/*    */   
/*    */   protected BlockPressurePlateWeighted(Material p_i46379_1_, int p_i46379_2_) {
/* 19 */     this(p_i46379_1_, p_i46379_2_, p_i46379_1_.getMaterialMapColor());
/*    */   }
/*    */   
/*    */   protected BlockPressurePlateWeighted(Material p_i46380_1_, int p_i46380_2_, MapColor p_i46380_3_) {
/* 23 */     super(p_i46380_1_, p_i46380_3_);
/* 24 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWER, Integer.valueOf(0)));
/* 25 */     this.field_150068_a = p_i46380_2_;
/*    */   }
/*    */   
/*    */   protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
/* 29 */     int i = Math.min(worldIn.getEntitiesWithinAABB(Entity.class, getSensitiveAABB(pos)).size(), this.field_150068_a);
/*    */     
/* 31 */     if (i > 0) {
/* 32 */       float f = Math.min(this.field_150068_a, i) / this.field_150068_a;
/* 33 */       return MathHelper.ceiling_float_int(f * 15.0F);
/*    */     } 
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getRedstoneStrength(IBlockState state) {
/* 40 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*    */   }
/*    */   
/*    */   protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
/* 44 */     return state.withProperty((IProperty)POWER, Integer.valueOf(strength));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int tickRate(World worldIn) {
/* 51 */     return 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 58 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 65 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*    */   }
/*    */   
/*    */   protected BlockState createBlockState() {
/* 69 */     return new BlockState(this, new IProperty[] { (IProperty)POWER });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPressurePlateWeighted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */