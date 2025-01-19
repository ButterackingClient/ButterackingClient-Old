/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public abstract class BlockRotatedPillar extends Block {
/*  9 */   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
/*    */   
/*    */   protected BlockRotatedPillar(Material materialIn) {
/* 12 */     super(materialIn, materialIn.getMaterialMapColor());
/*    */   }
/*    */   
/*    */   protected BlockRotatedPillar(Material p_i46385_1_, MapColor p_i46385_2_) {
/* 16 */     super(p_i46385_1_, p_i46385_2_);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRotatedPillar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */