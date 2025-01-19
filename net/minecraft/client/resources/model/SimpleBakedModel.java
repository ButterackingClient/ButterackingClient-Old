/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BreakingFour;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class SimpleBakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   protected final List<BakedQuad> generalQuads;
/*     */   protected final List<List<BakedQuad>> faceQuads;
/*     */   protected final boolean ambientOcclusion;
/*     */   protected final boolean gui3d;
/*     */   protected final TextureAtlasSprite texture;
/*     */   protected final ItemCameraTransforms cameraTransforms;
/*     */   
/*     */   public SimpleBakedModel(List<BakedQuad> generalQuadsIn, List<List<BakedQuad>> faceQuadsIn, boolean ambientOcclusionIn, boolean gui3dIn, TextureAtlasSprite textureIn, ItemCameraTransforms cameraTransformsIn) {
/*  23 */     this.generalQuads = generalQuadsIn;
/*  24 */     this.faceQuads = faceQuadsIn;
/*  25 */     this.ambientOcclusion = ambientOcclusionIn;
/*  26 */     this.gui3d = gui3dIn;
/*  27 */     this.texture = textureIn;
/*  28 */     this.cameraTransforms = cameraTransformsIn;
/*     */   }
/*     */   
/*     */   public List<BakedQuad> getFaceQuads(EnumFacing facing) {
/*  32 */     return this.faceQuads.get(facing.ordinal());
/*     */   }
/*     */   
/*     */   public List<BakedQuad> getGeneralQuads() {
/*  36 */     return this.generalQuads;
/*     */   }
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  40 */     return this.ambientOcclusion;
/*     */   }
/*     */   
/*     */   public boolean isGui3d() {
/*  44 */     return this.gui3d;
/*     */   }
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  52 */     return this.texture;
/*     */   }
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  56 */     return this.cameraTransforms;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final List<BakedQuad> builderGeneralQuads;
/*     */     private final List<List<BakedQuad>> builderFaceQuads;
/*     */     private final boolean builderAmbientOcclusion;
/*     */     private TextureAtlasSprite builderTexture;
/*     */     private boolean builderGui3d;
/*     */     private ItemCameraTransforms builderCameraTransforms;
/*     */     
/*     */     public Builder(ModelBlock model) {
/*  68 */       this(model.isAmbientOcclusion(), model.isGui3d(), model.getAllTransforms());
/*     */     }
/*     */     
/*     */     public Builder(IBakedModel bakedModel, TextureAtlasSprite texture) {
/*  72 */       this(bakedModel.isAmbientOcclusion(), bakedModel.isGui3d(), bakedModel.getItemCameraTransforms());
/*  73 */       this.builderTexture = bakedModel.getParticleTexture(); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  75 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  76 */         addFaceBreakingFours(bakedModel, texture, enumfacing);
/*     */         b++; }
/*     */       
/*  79 */       addGeneralBreakingFours(bakedModel, texture);
/*     */     }
/*     */     
/*     */     private void addFaceBreakingFours(IBakedModel bakedModel, TextureAtlasSprite texture, EnumFacing facing) {
/*  83 */       for (BakedQuad bakedquad : bakedModel.getFaceQuads(facing)) {
/*  84 */         addFaceQuad(facing, (BakedQuad)new BreakingFour(bakedquad, texture));
/*     */       }
/*     */     }
/*     */     
/*     */     private void addGeneralBreakingFours(IBakedModel p_177647_1_, TextureAtlasSprite texture) {
/*  89 */       for (BakedQuad bakedquad : p_177647_1_.getGeneralQuads()) {
/*  90 */         addGeneralQuad((BakedQuad)new BreakingFour(bakedquad, texture));
/*     */       }
/*     */     }
/*     */     
/*     */     private Builder(boolean ambientOcclusion, boolean gui3d, ItemCameraTransforms cameraTransforms) {
/*  95 */       this.builderGeneralQuads = Lists.newArrayList();
/*  96 */       this.builderFaceQuads = Lists.newArrayListWithCapacity(6); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  98 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  99 */         this.builderFaceQuads.add(Lists.newArrayList());
/*     */         b++; }
/*     */       
/* 102 */       this.builderAmbientOcclusion = ambientOcclusion;
/* 103 */       this.builderGui3d = gui3d;
/* 104 */       this.builderCameraTransforms = cameraTransforms;
/*     */     }
/*     */     
/*     */     public Builder addFaceQuad(EnumFacing facing, BakedQuad quad) {
/* 108 */       ((List<BakedQuad>)this.builderFaceQuads.get(facing.ordinal())).add(quad);
/* 109 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addGeneralQuad(BakedQuad quad) {
/* 113 */       this.builderGeneralQuads.add(quad);
/* 114 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setTexture(TextureAtlasSprite texture) {
/* 118 */       this.builderTexture = texture;
/* 119 */       return this;
/*     */     }
/*     */     
/*     */     public IBakedModel makeBakedModel() {
/* 123 */       if (this.builderTexture == null) {
/* 124 */         throw new RuntimeException("Missing particle!");
/*     */       }
/* 126 */       return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\model\SimpleBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */