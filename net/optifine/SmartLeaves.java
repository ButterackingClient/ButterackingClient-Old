/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.model.ModelUtils;
/*     */ 
/*     */ public class SmartLeaves {
/*  21 */   private static IBakedModel modelLeavesCullAcacia = null;
/*  22 */   private static IBakedModel modelLeavesCullBirch = null;
/*  23 */   private static IBakedModel modelLeavesCullDarkOak = null;
/*  24 */   private static IBakedModel modelLeavesCullJungle = null;
/*  25 */   private static IBakedModel modelLeavesCullOak = null;
/*  26 */   private static IBakedModel modelLeavesCullSpruce = null;
/*  27 */   private static List generalQuadsCullAcacia = null;
/*  28 */   private static List generalQuadsCullBirch = null;
/*  29 */   private static List generalQuadsCullDarkOak = null;
/*  30 */   private static List generalQuadsCullJungle = null;
/*  31 */   private static List generalQuadsCullOak = null;
/*  32 */   private static List generalQuadsCullSpruce = null;
/*  33 */   private static IBakedModel modelLeavesDoubleAcacia = null;
/*  34 */   private static IBakedModel modelLeavesDoubleBirch = null;
/*  35 */   private static IBakedModel modelLeavesDoubleDarkOak = null;
/*  36 */   private static IBakedModel modelLeavesDoubleJungle = null;
/*  37 */   private static IBakedModel modelLeavesDoubleOak = null;
/*  38 */   private static IBakedModel modelLeavesDoubleSpruce = null;
/*     */   
/*     */   public static IBakedModel getLeavesModel(IBakedModel model, IBlockState stateIn) {
/*  41 */     if (!Config.isTreesSmart()) {
/*  42 */       return model;
/*     */     }
/*  44 */     List list = model.getGeneralQuads();
/*  45 */     return (list == generalQuadsCullAcacia) ? modelLeavesDoubleAcacia : ((list == generalQuadsCullBirch) ? modelLeavesDoubleBirch : ((list == generalQuadsCullDarkOak) ? modelLeavesDoubleDarkOak : ((list == generalQuadsCullJungle) ? modelLeavesDoubleJungle : ((list == generalQuadsCullOak) ? modelLeavesDoubleOak : ((list == generalQuadsCullSpruce) ? modelLeavesDoubleSpruce : model)))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameLeaves(IBlockState state1, IBlockState state2) {
/*  50 */     if (state1 == state2) {
/*  51 */       return true;
/*     */     }
/*  53 */     Block block = state1.getBlock();
/*  54 */     Block block1 = state2.getBlock();
/*  55 */     return (block != block1) ? false : ((block instanceof BlockOldLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockOldLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockOldLeaf.VARIANT)) : ((block instanceof BlockNewLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockNewLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockNewLeaf.VARIANT)) : false));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateLeavesModels() {
/*  60 */     List list = new ArrayList();
/*  61 */     modelLeavesCullAcacia = getModelCull("acacia", list);
/*  62 */     modelLeavesCullBirch = getModelCull("birch", list);
/*  63 */     modelLeavesCullDarkOak = getModelCull("dark_oak", list);
/*  64 */     modelLeavesCullJungle = getModelCull("jungle", list);
/*  65 */     modelLeavesCullOak = getModelCull("oak", list);
/*  66 */     modelLeavesCullSpruce = getModelCull("spruce", list);
/*  67 */     generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
/*  68 */     generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
/*  69 */     generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
/*  70 */     generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
/*  71 */     generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
/*  72 */     generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
/*  73 */     modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
/*  74 */     modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
/*  75 */     modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
/*  76 */     modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
/*  77 */     modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
/*  78 */     modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
/*     */     
/*  80 */     if (list.size() > 0) {
/*  81 */       Config.dbg("Enable face culling: " + Config.arrayToString(list.toArray()));
/*     */     }
/*     */   }
/*     */   
/*     */   private static List getGeneralQuadsSafe(IBakedModel model) {
/*  86 */     return (model == null) ? null : model.getGeneralQuads();
/*     */   }
/*     */   
/*     */   static IBakedModel getModelCull(String type, List<String> updatedTypes) {
/*  90 */     ModelManager modelmanager = Config.getModelManager();
/*     */     
/*  92 */     if (modelmanager == null) {
/*  93 */       return null;
/*     */     }
/*  95 */     ResourceLocation resourcelocation = new ResourceLocation("blockstates/" + type + "_leaves.json");
/*     */     
/*  97 */     if (Config.getDefiningResourcePack(resourcelocation) != Config.getDefaultResourcePack()) {
/*  98 */       return null;
/*     */     }
/* 100 */     ResourceLocation resourcelocation1 = new ResourceLocation("models/block/" + type + "_leaves.json");
/*     */     
/* 102 */     if (Config.getDefiningResourcePack(resourcelocation1) != Config.getDefaultResourcePack()) {
/* 103 */       return null;
/*     */     }
/* 105 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(String.valueOf(type) + "_leaves", "normal");
/* 106 */     IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */     
/* 108 */     if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/* 109 */       List list = ibakedmodel.getGeneralQuads();
/*     */       
/* 111 */       if (list.size() == 0)
/* 112 */         return ibakedmodel; 
/* 113 */       if (list.size() != 6) {
/* 114 */         return null;
/*     */       }
/* 116 */       for (Object bakedquad0 : list) {
/* 117 */         BakedQuad bakedquad = (BakedQuad)bakedquad0;
/* 118 */         List<BakedQuad> list1 = ibakedmodel.getFaceQuads(bakedquad.getFace());
/*     */         
/* 120 */         if (list1.size() > 0) {
/* 121 */           return null;
/*     */         }
/*     */         
/* 124 */         list1.add(bakedquad);
/*     */       } 
/*     */       
/* 127 */       list.clear();
/* 128 */       updatedTypes.add(String.valueOf(type) + "_leaves");
/* 129 */       return ibakedmodel;
/*     */     } 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IBakedModel getModelDoubleFace(IBakedModel model) {
/* 140 */     if (model == null)
/* 141 */       return null; 
/* 142 */     if (model.getGeneralQuads().size() > 0) {
/* 143 */       Config.warn("SmartLeaves: Model is not cube, general quads: " + model.getGeneralQuads().size() + ", model: " + model);
/* 144 */       return model;
/*     */     } 
/* 146 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */     
/* 148 */     for (int i = 0; i < aenumfacing.length; i++) {
/* 149 */       EnumFacing enumfacing = aenumfacing[i];
/* 150 */       List<BakedQuad> list = model.getFaceQuads(enumfacing);
/*     */       
/* 152 */       if (list.size() != 1) {
/* 153 */         Config.warn("SmartLeaves: Model is not cube, side: " + enumfacing + ", quads: " + list.size() + ", model: " + model);
/* 154 */         return model;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     IBakedModel ibakedmodel = ModelUtils.duplicateModel(model);
/* 159 */     List[] alist = new List[aenumfacing.length];
/*     */     
/* 161 */     for (int k = 0; k < aenumfacing.length; k++) {
/* 162 */       EnumFacing enumfacing1 = aenumfacing[k];
/* 163 */       List<BakedQuad> list1 = ibakedmodel.getFaceQuads(enumfacing1);
/* 164 */       BakedQuad bakedquad = list1.get(0);
/* 165 */       BakedQuad bakedquad1 = new BakedQuad((int[])bakedquad.getVertexData().clone(), bakedquad.getTintIndex(), bakedquad.getFace(), bakedquad.getSprite());
/* 166 */       int[] aint = bakedquad1.getVertexData();
/* 167 */       int[] aint1 = (int[])aint.clone();
/* 168 */       int j = aint.length / 4;
/* 169 */       System.arraycopy(aint, 0 * j, aint1, 3 * j, j);
/* 170 */       System.arraycopy(aint, 1 * j, aint1, 2 * j, j);
/* 171 */       System.arraycopy(aint, 2 * j, aint1, 1 * j, j);
/* 172 */       System.arraycopy(aint, 3 * j, aint1, 0 * j, j);
/* 173 */       System.arraycopy(aint1, 0, aint, 0, aint1.length);
/* 174 */       list1.add(bakedquad1);
/*     */     } 
/*     */     
/* 177 */     return ibakedmodel;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\SmartLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */