/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ 
/*    */ public class BlockStateHelper
/*    */   implements Predicate<IBlockState>
/*    */ {
/*    */   private final BlockState blockstate;
/* 16 */   private final Map<IProperty, Predicate> propertyPredicates = Maps.newHashMap();
/*    */   
/*    */   private BlockStateHelper(BlockState blockStateIn) {
/* 19 */     this.blockstate = blockStateIn;
/*    */   }
/*    */   
/*    */   public static BlockStateHelper forBlock(Block blockIn) {
/* 23 */     return new BlockStateHelper(blockIn.getBlockState());
/*    */   }
/*    */   
/*    */   public boolean apply(IBlockState p_apply_1_) {
/* 27 */     if (p_apply_1_ != null && p_apply_1_.getBlock().equals(this.blockstate.getBlock())) {
/* 28 */       for (Map.Entry<IProperty, Predicate> entry : this.propertyPredicates.entrySet()) {
/* 29 */         Object object = p_apply_1_.getValue(entry.getKey());
/*    */         
/* 31 */         if (!((Predicate)entry.getValue()).apply(object)) {
/* 32 */           return false;
/*    */         }
/*    */       } 
/*    */       
/* 36 */       return true;
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public <V extends Comparable<V>> BlockStateHelper where(IProperty<V> property, Predicate<? extends V> is) {
/* 43 */     if (!this.blockstate.getProperties().contains(property)) {
/* 44 */       throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
/*    */     }
/* 46 */     this.propertyPredicates.put(property, is);
/* 47 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\state\pattern\BlockStateHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */