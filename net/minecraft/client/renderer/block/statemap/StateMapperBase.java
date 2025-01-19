/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ 
/*    */ 
/*    */ public abstract class StateMapperBase
/*    */   implements IStateMapper
/*    */ {
/* 14 */   protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();
/*    */   
/*    */   public String getPropertyString(Map<IProperty, Comparable> p_178131_1_) {
/* 17 */     StringBuilder stringbuilder = new StringBuilder();
/*    */     
/* 19 */     for (Map.Entry<IProperty, Comparable> entry : p_178131_1_.entrySet()) {
/* 20 */       if (stringbuilder.length() != 0) {
/* 21 */         stringbuilder.append(",");
/*    */       }
/*    */       
/* 24 */       IProperty iproperty = entry.getKey();
/* 25 */       Comparable comparable = entry.getValue();
/* 26 */       stringbuilder.append(iproperty.getName());
/* 27 */       stringbuilder.append("=");
/* 28 */       stringbuilder.append(iproperty.getName(comparable));
/*    */     } 
/*    */     
/* 31 */     if (stringbuilder.length() == 0) {
/* 32 */       stringbuilder.append("normal");
/*    */     }
/*    */     
/* 35 */     return stringbuilder.toString();
/*    */   }
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
/* 39 */     for (IBlockState iblockstate : blockIn.getBlockState().getValidStates()) {
/* 40 */       this.mapStateModelLocations.put(iblockstate, getModelResourceLocation(iblockstate));
/*    */     }
/*    */     
/* 43 */     return this.mapStateModelLocations;
/*    */   }
/*    */   
/*    */   protected abstract ModelResourceLocation getModelResourceLocation(IBlockState paramIBlockState);
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\statemap\StateMapperBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */