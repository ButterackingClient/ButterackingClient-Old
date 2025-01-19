/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyBool;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockPressurePlate
/*    */   extends BlockBasePressurePlate {
/* 17 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*    */   private final Sensitivity sensitivity;
/*    */   
/*    */   protected BlockPressurePlate(Material materialIn, Sensitivity sensitivityIn) {
/* 21 */     super(materialIn);
/* 22 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 23 */     this.sensitivity = sensitivityIn;
/*    */   }
/*    */   
/*    */   protected int getRedstoneStrength(IBlockState state) {
/* 27 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*    */   }
/*    */   
/*    */   protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
/* 31 */     return state.withProperty((IProperty)POWERED, Boolean.valueOf((strength > 0)));
/*    */   }
/*    */   protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
/*    */     List<? extends Entity> list;
/* 35 */     AxisAlignedBB axisalignedbb = getSensitiveAABB(pos);
/*    */ 
/*    */     
/* 38 */     switch (this.sensitivity) {
/*    */       case null:
/* 40 */         list = worldIn.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
/*    */         break;
/*    */       
/*    */       case MOBS:
/* 44 */         list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*    */         break;
/*    */       
/*    */       default:
/* 48 */         return 0;
/*    */     } 
/*    */     
/* 51 */     if (!list.isEmpty()) {
/* 52 */       for (Entity entity : list) {
/* 53 */         if (!entity.doesEntityNotTriggerPressurePlate()) {
/* 54 */           return 15;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 59 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 66 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf((meta == 1)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 73 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 1 : 0;
/*    */   }
/*    */   
/*    */   protected BlockState createBlockState() {
/* 77 */     return new BlockState(this, new IProperty[] { (IProperty)POWERED });
/*    */   }
/*    */   
/*    */   public enum Sensitivity {
/* 81 */     EVERYTHING,
/* 82 */     MOBS;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */