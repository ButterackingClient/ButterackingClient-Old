/*    */ package net.minecraft.block.state;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.ImmutableTable;
/*    */ import com.google.common.collect.Iterables;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockStateBase
/*    */   implements IBlockState
/*    */ {
/*    */   public BlockStateBase() {
/* 28 */     this.blockId = -1;
/* 29 */     this.blockStateId = -1;
/* 30 */     this.metadata = -1;
/* 31 */     this.blockLocation = null;
/*    */   } private static final Joiner COMMA_JOINER = Joiner.on(','); private static final Function<Map.Entry<IProperty, Comparable>, String> MAP_ENTRY_TO_STRING = new Function<Map.Entry<IProperty, Comparable>, String>() { public String apply(Map.Entry<IProperty, Comparable> p_apply_1_) { if (p_apply_1_ == null)
/*    */           return "<NULL>";  IProperty iproperty = p_apply_1_.getKey();
/* 34 */         return String.valueOf(iproperty.getName()) + "=" + iproperty.getName(p_apply_1_.getValue()); } }; private int blockId; public int getBlockId() { if (this.blockId < 0) {
/* 35 */       this.blockId = Block.getIdFromBlock(getBlock());
/*    */     }
/*    */     
/* 38 */     return this.blockId; }
/*    */   
/*    */   private int blockStateId; private int metadata; private ResourceLocation blockLocation;
/*    */   public int getBlockStateId() {
/* 42 */     if (this.blockStateId < 0) {
/* 43 */       this.blockStateId = Block.getStateId(this);
/*    */     }
/*    */     
/* 46 */     return this.blockStateId;
/*    */   }
/*    */   
/*    */   public int getMetadata() {
/* 50 */     if (this.metadata < 0) {
/* 51 */       this.metadata = getBlock().getMetaFromState(this);
/*    */     }
/*    */     
/* 54 */     return this.metadata;
/*    */   }
/*    */   
/*    */   public ResourceLocation getBlockLocation() {
/* 58 */     if (this.blockLocation == null) {
/* 59 */       this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(getBlock());
/*    */     }
/*    */     
/* 62 */     return this.blockLocation;
/*    */   }
/*    */   
/*    */   public ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> getPropertyValueTable() {
/* 66 */     return null;
/*    */   }
/*    */   
/*    */   public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
/* 70 */     return withProperty(property, cyclePropertyValue(property.getAllowedValues(), getValue(property)));
/*    */   }
/*    */   
/*    */   protected static <T> T cyclePropertyValue(Collection<T> values, T currentValue) {
/* 74 */     Iterator<T> iterator = values.iterator();
/*    */     
/* 76 */     while (iterator.hasNext()) {
/* 77 */       if (iterator.next().equals(currentValue)) {
/* 78 */         if (iterator.hasNext()) {
/* 79 */           return iterator.next();
/*    */         }
/*    */         
/* 82 */         return values.iterator().next();
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     return iterator.next();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 90 */     StringBuilder stringbuilder = new StringBuilder();
/* 91 */     stringbuilder.append(Block.blockRegistry.getNameForObject(getBlock()));
/*    */     
/* 93 */     if (!getProperties().isEmpty()) {
/* 94 */       stringbuilder.append("[");
/* 95 */       COMMA_JOINER.appendTo(stringbuilder, Iterables.transform((Iterable)getProperties().entrySet(), MAP_ENTRY_TO_STRING));
/* 96 */       stringbuilder.append("]");
/*    */     } 
/*    */     
/* 99 */     return stringbuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\state\BlockStateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */