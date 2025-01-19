/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class StateMap
/*    */   extends StateMapperBase
/*    */ {
/*    */   private final IProperty<?> name;
/*    */   private final String suffix;
/*    */   private final List<IProperty<?>> ignored;
/*    */   
/*    */   private StateMap(IProperty<?> name, String suffix, List<IProperty<?>> ignored) {
/* 22 */     this.name = name;
/* 23 */     this.suffix = suffix;
/* 24 */     this.ignored = ignored;
/*    */   }
/*    */   protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
/*    */     String s;
/* 28 */     Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*    */ 
/*    */     
/* 31 */     if (this.name == null) {
/* 32 */       s = ((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock())).toString();
/*    */     } else {
/* 34 */       s = this.name.getName(map.remove(this.name));
/*    */     } 
/*    */     
/* 37 */     if (this.suffix != null) {
/* 38 */       s = String.valueOf(s) + this.suffix;
/*    */     }
/*    */     
/* 41 */     for (IProperty<?> iproperty : this.ignored) {
/* 42 */       map.remove(iproperty);
/*    */     }
/*    */     
/* 45 */     return new ModelResourceLocation(s, getPropertyString(map));
/*    */   }
/*    */   
/*    */   public static class Builder {
/*    */     private IProperty<?> name;
/*    */     private String suffix;
/* 51 */     private final List<IProperty<?>> ignored = Lists.newArrayList();
/*    */     
/*    */     public Builder withName(IProperty<?> builderPropertyIn) {
/* 54 */       this.name = builderPropertyIn;
/* 55 */       return this;
/*    */     }
/*    */     
/*    */     public Builder withSuffix(String builderSuffixIn) {
/* 59 */       this.suffix = builderSuffixIn;
/* 60 */       return this;
/*    */     }
/*    */     
/*    */     public Builder ignore(IProperty... p_178442_1_) {
/* 64 */       Collections.addAll(this.ignored, (IProperty<?>[])p_178442_1_);
/* 65 */       return this;
/*    */     }
/*    */     
/*    */     public StateMap build() {
/* 69 */       return new StateMap(this.name, this.suffix, this.ignored, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\statemap\StateMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */