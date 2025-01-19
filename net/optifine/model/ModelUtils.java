/*    */ package net.optifine.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class ModelUtils
/*    */ {
/*    */   public static void dbgModel(IBakedModel model) {
/* 14 */     if (model != null) {
/* 15 */       Config.dbg("Model: " + model + ", ao: " + model.isAmbientOcclusion() + ", gui3d: " + model.isGui3d() + ", builtIn: " + model.isBuiltInRenderer() + ", particle: " + model.getParticleTexture());
/* 16 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*    */       
/* 18 */       for (int i = 0; i < aenumfacing.length; i++) {
/* 19 */         EnumFacing enumfacing = aenumfacing[i];
/* 20 */         List list = model.getFaceQuads(enumfacing);
/* 21 */         dbgQuads(enumfacing.getName(), list, "  ");
/*    */       } 
/*    */       
/* 24 */       List list1 = model.getGeneralQuads();
/* 25 */       dbgQuads("General", list1, "  ");
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void dbgQuads(String name, List quads, String prefix) {
/* 30 */     for (Object bakedquad0 : quads) {
/* 31 */       BakedQuad bakedQuad = (BakedQuad)bakedquad0;
/* 32 */       dbgQuad(name, bakedQuad, prefix);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void dbgQuad(String name, BakedQuad quad, String prefix) {
/* 37 */     Config.dbg(String.valueOf(prefix) + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.getFace() + ", tint: " + quad.getTintIndex() + ", sprite: " + quad.getSprite());
/* 38 */     dbgVertexData(quad.getVertexData(), "  " + prefix);
/*    */   }
/*    */   
/*    */   public static void dbgVertexData(int[] vd, String prefix) {
/* 42 */     int i = vd.length / 4;
/* 43 */     Config.dbg(String.valueOf(prefix) + "Length: " + vd.length + ", step: " + i);
/*    */     
/* 45 */     for (int j = 0; j < 4; j++) {
/* 46 */       int k = j * i;
/* 47 */       float f = Float.intBitsToFloat(vd[k + 0]);
/* 48 */       float f1 = Float.intBitsToFloat(vd[k + 1]);
/* 49 */       float f2 = Float.intBitsToFloat(vd[k + 2]);
/* 50 */       int l = vd[k + 3];
/* 51 */       float f3 = Float.intBitsToFloat(vd[k + 4]);
/* 52 */       float f4 = Float.intBitsToFloat(vd[k + 5]);
/* 53 */       Config.dbg(String.valueOf(prefix) + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static IBakedModel duplicateModel(IBakedModel model) {
/* 58 */     List list = duplicateQuadList(model.getGeneralQuads());
/* 59 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/* 60 */     List<List> list1 = new ArrayList();
/*    */     
/* 62 */     for (int i = 0; i < aenumfacing.length; i++) {
/* 63 */       EnumFacing enumfacing = aenumfacing[i];
/* 64 */       List list2 = model.getFaceQuads(enumfacing);
/* 65 */       List list3 = duplicateQuadList(list2);
/* 66 */       list1.add(list3);
/*    */     } 
/*    */     
/* 69 */     SimpleBakedModel simplebakedmodel = new SimpleBakedModel(list, list1, model.isAmbientOcclusion(), model.isGui3d(), model.getParticleTexture(), model.getItemCameraTransforms());
/* 70 */     return (IBakedModel)simplebakedmodel;
/*    */   }
/*    */   
/*    */   public static List duplicateQuadList(List lists) {
/* 74 */     List<BakedQuad> list = new ArrayList();
/*    */     
/* 76 */     for (Object e : lists) {
/* 77 */       BakedQuad bakedquad = (BakedQuad)e;
/* 78 */       BakedQuad bakedquad1 = duplicateQuad(bakedquad);
/* 79 */       list.add(bakedquad1);
/*    */     } 
/*    */     
/* 82 */     return list;
/*    */   }
/*    */   
/*    */   public static BakedQuad duplicateQuad(BakedQuad quad) {
/* 86 */     BakedQuad bakedquad = new BakedQuad((int[])quad.getVertexData().clone(), quad.getTintIndex(), quad.getFace(), quad.getSprite());
/* 87 */     return bakedquad;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\model\ModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */