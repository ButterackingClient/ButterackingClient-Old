/*     */ package net.optifine.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockFaceUV;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartRotation;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class BlockModelUtils
/*     */ {
/*     */   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;
/*     */   
/*     */   public static IBakedModel makeModelCube(String spriteName, int tintIndex) {
/*  31 */     TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName);
/*  32 */     return makeModelCube(textureatlassprite, tintIndex);
/*     */   }
/*     */   
/*     */   public static IBakedModel makeModelCube(TextureAtlasSprite sprite, int tintIndex) {
/*  36 */     List list = new ArrayList();
/*  37 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  38 */     List<List<BakedQuad>> list1 = new ArrayList<>();
/*     */     
/*  40 */     for (int i = 0; i < aenumfacing.length; i++) {
/*  41 */       EnumFacing enumfacing = aenumfacing[i];
/*  42 */       List<BakedQuad> list2 = new ArrayList();
/*  43 */       list2.add(makeBakedQuad(enumfacing, sprite, tintIndex));
/*  44 */       list1.add(list2);
/*     */     } 
/*     */     
/*  47 */     return (IBakedModel)new SimpleBakedModel(list, list1, true, true, sprite, ItemCameraTransforms.DEFAULT);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel joinModelsCube(IBakedModel modelBase, IBakedModel modelAdd) {
/*  52 */     List<BakedQuad> list = new ArrayList<>();
/*  53 */     list.addAll(modelBase.getGeneralQuads());
/*  54 */     list.addAll(modelAdd.getGeneralQuads());
/*  55 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  56 */     List<List> list1 = new ArrayList();
/*     */     
/*  58 */     for (int i = 0; i < aenumfacing.length; i++) {
/*  59 */       EnumFacing enumfacing = aenumfacing[i];
/*  60 */       List list2 = new ArrayList();
/*  61 */       list2.addAll(modelBase.getFaceQuads(enumfacing));
/*  62 */       list2.addAll(modelAdd.getFaceQuads(enumfacing));
/*  63 */       list1.add(list2);
/*     */     } 
/*     */     
/*  66 */     boolean flag = modelBase.isAmbientOcclusion();
/*  67 */     boolean flag1 = modelBase.isBuiltInRenderer();
/*  68 */     TextureAtlasSprite textureatlassprite = modelBase.getParticleTexture();
/*  69 */     ItemCameraTransforms itemcameratransforms = modelBase.getItemCameraTransforms();
/*  70 */     return (IBakedModel)new SimpleBakedModel(list, list1, flag, flag1, textureatlassprite, itemcameratransforms);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad makeBakedQuad(EnumFacing facing, TextureAtlasSprite sprite, int tintIndex) {
/*  75 */     Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/*  76 */     Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
/*  77 */     BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
/*  78 */     BlockPartFace blockpartface = new BlockPartFace(facing, tintIndex, "#" + facing.getName(), blockfaceuv);
/*  79 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/*  80 */     BlockPartRotation blockpartrotation = null;
/*  81 */     boolean flag = false;
/*  82 */     boolean flag1 = true;
/*  83 */     FaceBakery facebakery = new FaceBakery();
/*  84 */     BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, sprite, facing, modelrotation, blockpartrotation, flag, flag1);
/*  85 */     return bakedquad;
/*     */   }
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
/*  89 */     TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
/*  90 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(spriteOldName);
/*  91 */     TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(spriteNewName);
/*  92 */     return makeModel(modelName, textureatlassprite, textureatlassprite1);
/*     */   }
/*     */   
/*     */   public static IBakedModel makeModel(String modelName, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/*  96 */     if (spriteOld != null && spriteNew != null) {
/*  97 */       ModelManager modelmanager = Config.getModelManager();
/*     */       
/*  99 */       if (modelmanager == null) {
/* 100 */         return null;
/*     */       }
/* 102 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(modelName, "normal");
/* 103 */       IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */       
/* 105 */       if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/* 106 */         IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
/* 107 */         EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */         
/* 109 */         for (int i = 0; i < aenumfacing.length; i++) {
/* 110 */           EnumFacing enumfacing = aenumfacing[i];
/* 111 */           List<BakedQuad> list = ibakedmodel1.getFaceQuads(enumfacing);
/* 112 */           replaceTexture(list, spriteOld, spriteNew);
/*     */         } 
/*     */         
/* 115 */         List<BakedQuad> list1 = ibakedmodel1.getGeneralQuads();
/* 116 */         replaceTexture(list1, spriteOld, spriteNew);
/* 117 */         return ibakedmodel1;
/*     */       } 
/* 119 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void replaceTexture(List<BakedQuad> quads, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
/* 128 */     List<BakedQuad> list = new ArrayList<>();
/*     */     
/* 130 */     for (BakedQuad bakedquad : quads) {
/* 131 */       BreakingFour breakingFour; if (bakedquad.getSprite() == spriteOld) {
/* 132 */         breakingFour = new BreakingFour(bakedquad, spriteNew);
/*     */       }
/*     */       
/* 135 */       list.add(breakingFour);
/*     */     } 
/*     */     
/* 138 */     quads.clear();
/* 139 */     quads.addAll(list);
/*     */   }
/*     */   
/*     */   public static void snapVertexPosition(Vector3f pos) {
/* 143 */     pos.setX(snapVertexCoord(pos.getX()));
/* 144 */     pos.setY(snapVertexCoord(pos.getY()));
/* 145 */     pos.setZ(snapVertexCoord(pos.getZ()));
/*     */   }
/*     */   
/*     */   private static float snapVertexCoord(float x) {
/* 149 */     return (x > -1.0E-6F && x < 1.0E-6F) ? 0.0F : ((x > 0.999999F && x < 1.000001F) ? 1.0F : x);
/*     */   }
/*     */   
/*     */   public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB aabb, Block.EnumOffsetType offsetType, BlockPos pos) {
/* 153 */     int i = pos.getX();
/* 154 */     int j = pos.getZ();
/* 155 */     long k = (i * 3129871) ^ j * 116129781L;
/* 156 */     k = k * k * 42317861L + k * 11L;
/* 157 */     double d0 = (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 158 */     double d1 = (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 159 */     double d2 = 0.0D;
/*     */     
/* 161 */     if (offsetType == Block.EnumOffsetType.XYZ) {
/* 162 */       d2 = (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */     }
/*     */     
/* 165 */     return aabb.offset(d0, d2, d1);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\model\BlockModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */