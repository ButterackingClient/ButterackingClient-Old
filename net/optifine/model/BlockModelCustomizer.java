/*    */ package net.optifine.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.optifine.BetterGrass;
/*    */ import net.optifine.ConnectedTextures;
/*    */ import net.optifine.NaturalTextures;
/*    */ import net.optifine.SmartLeaves;
/*    */ import net.optifine.render.RenderEnv;
/*    */ 
/*    */ 
/*    */ public class BlockModelCustomizer
/*    */ {
/* 22 */   private static final List<BakedQuad> NO_QUADS = (List<BakedQuad>)ImmutableList.of();
/*    */   
/*    */   public static IBakedModel getRenderModel(IBakedModel modelIn, IBlockState stateIn, RenderEnv renderEnv) {
/* 25 */     if (renderEnv.isSmartLeaves()) {
/* 26 */       modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn);
/*    */     }
/*    */     
/* 29 */     return modelIn;
/*    */   }
/*    */   
/*    */   public static List<BakedQuad> getRenderQuads(List<BakedQuad> quads, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, EnumWorldBlockLayer layer, long rand, RenderEnv renderEnv) {
/* 33 */     if (enumfacing != null) {
/* 34 */       if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.getBlockState(posIn.offset(enumfacing)), stateIn)) {
/* 35 */         return NO_QUADS;
/*    */       }
/*    */       
/* 38 */       if (!renderEnv.isBreakingAnimation(quads) && Config.isBetterGrass()) {
/* 39 */         quads = BetterGrass.getFaceQuads(worldIn, stateIn, posIn, enumfacing, quads);
/*    */       }
/*    */     } 
/*    */     
/* 43 */     List<BakedQuad> list = renderEnv.getListQuadsCustomizer();
/* 44 */     list.clear();
/*    */     
/* 46 */     for (int i = 0; i < quads.size(); i++) {
/* 47 */       BakedQuad bakedquad = quads.get(i);
/* 48 */       BakedQuad[] abakedquad = getRenderQuads(bakedquad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
/*    */       
/* 50 */       if (i == 0 && quads.size() == 1 && abakedquad.length == 1 && abakedquad[0] == bakedquad && bakedquad.getQuadEmissive() == null) {
/* 51 */         return quads;
/*    */       }
/*    */       
/* 54 */       for (int j = 0; j < abakedquad.length; j++) {
/* 55 */         BakedQuad bakedquad1 = abakedquad[j];
/* 56 */         list.add(bakedquad1);
/*    */         
/* 58 */         if (bakedquad1.getQuadEmissive() != null) {
/* 59 */           renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(bakedquad1.getQuadEmissive(), stateIn);
/* 60 */           renderEnv.setOverlaysRendered(true);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     return list;
/*    */   }
/*    */   
/*    */   private static EnumWorldBlockLayer getEmissiveLayer(EnumWorldBlockLayer layer) {
/* 69 */     return (layer != null && layer != EnumWorldBlockLayer.SOLID) ? layer : EnumWorldBlockLayer.CUTOUT_MIPPED;
/*    */   }
/*    */   
/*    */   private static BakedQuad[] getRenderQuads(BakedQuad quad, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, long rand, RenderEnv renderEnv) {
/* 73 */     if (renderEnv.isBreakingAnimation(quad)) {
/* 74 */       return renderEnv.getArrayQuadsCtm(quad);
/*    */     }
/* 76 */     BakedQuad bakedquad = quad;
/*    */     
/* 78 */     if (Config.isConnectedTextures()) {
/* 79 */       BakedQuad[] abakedquad = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
/*    */       
/* 81 */       if (abakedquad.length != 1 || abakedquad[0] != quad) {
/* 82 */         return abakedquad;
/*    */       }
/*    */     } 
/*    */     
/* 86 */     if (Config.isNaturalTextures()) {
/* 87 */       quad = NaturalTextures.getNaturalTexture(posIn, quad);
/*    */       
/* 89 */       if (quad != bakedquad) {
/* 90 */         return renderEnv.getArrayQuadsCtm(quad);
/*    */       }
/*    */     } 
/*    */     
/* 94 */     return renderEnv.getArrayQuadsCtm(quad);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\model\BlockModelCustomizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */