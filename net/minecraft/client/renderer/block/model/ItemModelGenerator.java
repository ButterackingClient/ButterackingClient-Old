/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ public class ItemModelGenerator
/*     */ {
/*  16 */   public static final List<String> LAYERS = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
/*     */   
/*     */   public ModelBlock makeItemModel(TextureMap textureMapIn, ModelBlock blockModel) {
/*  19 */     Map<String, String> map = Maps.newHashMap();
/*  20 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  22 */     for (int i = 0; i < LAYERS.size(); i++) {
/*  23 */       String s = LAYERS.get(i);
/*     */       
/*  25 */       if (!blockModel.isTexturePresent(s)) {
/*     */         break;
/*     */       }
/*     */       
/*  29 */       String s1 = blockModel.resolveTextureName(s);
/*  30 */       map.put(s, s1);
/*  31 */       TextureAtlasSprite textureatlassprite = textureMapIn.getAtlasSprite((new ResourceLocation(s1)).toString());
/*  32 */       list.addAll(func_178394_a(i, s, textureatlassprite));
/*     */     } 
/*     */     
/*  35 */     if (list.isEmpty()) {
/*  36 */       return null;
/*     */     }
/*  38 */     map.put("particle", blockModel.isTexturePresent("particle") ? blockModel.resolveTextureName("particle") : map.get("layer0"));
/*  39 */     return new ModelBlock(list, map, false, false, blockModel.getAllTransforms());
/*     */   }
/*     */ 
/*     */   
/*     */   private List<BlockPart> func_178394_a(int p_178394_1_, String p_178394_2_, TextureAtlasSprite p_178394_3_) {
/*  44 */     Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/*  45 */     map.put(EnumFacing.SOUTH, new BlockPartFace(null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0)));
/*  46 */     map.put(EnumFacing.NORTH, new BlockPartFace(null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0)));
/*  47 */     List<BlockPart> list = Lists.newArrayList();
/*  48 */     list.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), map, null, true));
/*  49 */     list.addAll(func_178397_a(p_178394_3_, p_178394_2_, p_178394_1_));
/*  50 */     return list;
/*     */   }
/*     */   
/*     */   private List<BlockPart> func_178397_a(TextureAtlasSprite p_178397_1_, String p_178397_2_, int p_178397_3_) {
/*  54 */     float f = p_178397_1_.getIconWidth();
/*  55 */     float f1 = p_178397_1_.getIconHeight();
/*  56 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  58 */     for (Span itemmodelgenerator$span : func_178393_a(p_178397_1_)) {
/*  59 */       float f2 = 0.0F;
/*  60 */       float f3 = 0.0F;
/*  61 */       float f4 = 0.0F;
/*  62 */       float f5 = 0.0F;
/*  63 */       float f6 = 0.0F;
/*  64 */       float f7 = 0.0F;
/*  65 */       float f8 = 0.0F;
/*  66 */       float f9 = 0.0F;
/*  67 */       float f10 = 0.0F;
/*  68 */       float f11 = 0.0F;
/*  69 */       float f12 = itemmodelgenerator$span.func_178385_b();
/*  70 */       float f13 = itemmodelgenerator$span.func_178384_c();
/*  71 */       float f14 = itemmodelgenerator$span.func_178381_d();
/*  72 */       SpanFacing itemmodelgenerator$spanfacing = itemmodelgenerator$span.func_178383_a();
/*     */       
/*  74 */       switch (itemmodelgenerator$spanfacing) {
/*     */         case UP:
/*  76 */           f6 = f12;
/*  77 */           f2 = f12;
/*  78 */           f4 = f7 = f13 + 1.0F;
/*  79 */           f8 = f14;
/*  80 */           f3 = f14;
/*  81 */           f9 = f14;
/*  82 */           f5 = f14;
/*  83 */           f10 = 16.0F / f;
/*  84 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case null:
/*  88 */           f9 = f14;
/*  89 */           f8 = f14;
/*  90 */           f6 = f12;
/*  91 */           f2 = f12;
/*  92 */           f4 = f7 = f13 + 1.0F;
/*  93 */           f3 = f14 + 1.0F;
/*  94 */           f5 = f14 + 1.0F;
/*  95 */           f10 = 16.0F / f;
/*  96 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case LEFT:
/* 100 */           f6 = f14;
/* 101 */           f2 = f14;
/* 102 */           f7 = f14;
/* 103 */           f4 = f14;
/* 104 */           f9 = f12;
/* 105 */           f3 = f12;
/* 106 */           f5 = f8 = f13 + 1.0F;
/* 107 */           f10 = 16.0F / (f - 1.0F);
/* 108 */           f11 = 16.0F / f1;
/*     */           break;
/*     */         
/*     */         case RIGHT:
/* 112 */           f7 = f14;
/* 113 */           f6 = f14;
/* 114 */           f2 = f14 + 1.0F;
/* 115 */           f4 = f14 + 1.0F;
/* 116 */           f9 = f12;
/* 117 */           f3 = f12;
/* 118 */           f5 = f8 = f13 + 1.0F;
/* 119 */           f10 = 16.0F / (f - 1.0F);
/* 120 */           f11 = 16.0F / f1;
/*     */           break;
/*     */       } 
/* 123 */       float f15 = 16.0F / f;
/* 124 */       float f16 = 16.0F / f1;
/* 125 */       f2 *= f15;
/* 126 */       f4 *= f15;
/* 127 */       f3 *= f16;
/* 128 */       f5 *= f16;
/* 129 */       f3 = 16.0F - f3;
/* 130 */       f5 = 16.0F - f5;
/* 131 */       f6 *= f10;
/* 132 */       f7 *= f10;
/* 133 */       f8 *= f11;
/* 134 */       f9 *= f11;
/* 135 */       Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/* 136 */       map.put(itemmodelgenerator$spanfacing.getFacing(), new BlockPartFace(null, p_178397_3_, p_178397_2_, new BlockFaceUV(new float[] { f6, f8, f7, f9 }, 0)));
/*     */       
/* 138 */       switch (itemmodelgenerator$spanfacing) {
/*     */         case UP:
/* 140 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f4, f3, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case null:
/* 144 */           list.add(new BlockPart(new Vector3f(f2, f5, 7.5F), new Vector3f(f4, f5, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case LEFT:
/* 148 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f2, f5, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case RIGHT:
/* 152 */           list.add(new BlockPart(new Vector3f(f4, f3, 7.5F), new Vector3f(f4, f5, 8.5F), map, null, true));
/*     */       } 
/*     */     
/*     */     } 
/* 156 */     return list;
/*     */   }
/*     */   
/*     */   private List<Span> func_178393_a(TextureAtlasSprite p_178393_1_) {
/* 160 */     int i = p_178393_1_.getIconWidth();
/* 161 */     int j = p_178393_1_.getIconHeight();
/* 162 */     List<Span> list = Lists.newArrayList();
/*     */     
/* 164 */     for (int k = 0; k < p_178393_1_.getFrameCount(); k++) {
/* 165 */       int[] aint = p_178393_1_.getFrameTextureData(k)[0];
/*     */       
/* 167 */       for (int l = 0; l < j; l++) {
/* 168 */         for (int i1 = 0; i1 < i; i1++) {
/* 169 */           boolean flag = !func_178391_a(aint, i1, l, i, j);
/* 170 */           func_178396_a(SpanFacing.UP, list, aint, i1, l, i, j, flag);
/* 171 */           func_178396_a(SpanFacing.DOWN, list, aint, i1, l, i, j, flag);
/* 172 */           func_178396_a(SpanFacing.LEFT, list, aint, i1, l, i, j, flag);
/* 173 */           func_178396_a(SpanFacing.RIGHT, list, aint, i1, l, i, j, flag);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return list;
/*     */   }
/*     */   
/*     */   private void func_178396_a(SpanFacing p_178396_1_, List<Span> p_178396_2_, int[] p_178396_3_, int p_178396_4_, int p_178396_5_, int p_178396_6_, int p_178396_7_, boolean p_178396_8_) {
/* 182 */     boolean flag = (func_178391_a(p_178396_3_, p_178396_4_ + p_178396_1_.func_178372_b(), p_178396_5_ + p_178396_1_.func_178371_c(), p_178396_6_, p_178396_7_) && p_178396_8_);
/*     */     
/* 184 */     if (flag) {
/* 185 */       func_178395_a(p_178396_2_, p_178396_1_, p_178396_4_, p_178396_5_);
/*     */     }
/*     */   }
/*     */   
/*     */   private void func_178395_a(List<Span> p_178395_1_, SpanFacing p_178395_2_, int p_178395_3_, int p_178395_4_) {
/* 190 */     Span itemmodelgenerator$span = null;
/*     */     
/* 192 */     for (Span itemmodelgenerator$span1 : p_178395_1_) {
/* 193 */       if (itemmodelgenerator$span1.func_178383_a() == p_178395_2_) {
/* 194 */         int i = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;
/*     */         
/* 196 */         if (itemmodelgenerator$span1.func_178381_d() == i) {
/* 197 */           itemmodelgenerator$span = itemmodelgenerator$span1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 203 */     int j = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;
/* 204 */     int k = p_178395_2_.func_178369_d() ? p_178395_3_ : p_178395_4_;
/*     */     
/* 206 */     if (itemmodelgenerator$span == null) {
/* 207 */       p_178395_1_.add(new Span(p_178395_2_, k, j));
/*     */     } else {
/* 209 */       itemmodelgenerator$span.func_178382_a(k);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean func_178391_a(int[] p_178391_1_, int p_178391_2_, int p_178391_3_, int p_178391_4_, int p_178391_5_) {
/* 214 */     return (p_178391_2_ >= 0 && p_178391_3_ >= 0 && p_178391_2_ < p_178391_4_ && p_178391_3_ < p_178391_5_) ? (((p_178391_1_[p_178391_3_ * p_178391_4_ + p_178391_2_] >> 24 & 0xFF) == 0)) : true;
/*     */   }
/*     */   
/*     */   static class Span {
/*     */     private final ItemModelGenerator.SpanFacing spanFacing;
/*     */     private int field_178387_b;
/*     */     private int field_178388_c;
/*     */     private final int field_178386_d;
/*     */     
/*     */     public Span(ItemModelGenerator.SpanFacing spanFacingIn, int p_i46216_2_, int p_i46216_3_) {
/* 224 */       this.spanFacing = spanFacingIn;
/* 225 */       this.field_178387_b = p_i46216_2_;
/* 226 */       this.field_178388_c = p_i46216_2_;
/* 227 */       this.field_178386_d = p_i46216_3_;
/*     */     }
/*     */     
/*     */     public void func_178382_a(int p_178382_1_) {
/* 231 */       if (p_178382_1_ < this.field_178387_b) {
/* 232 */         this.field_178387_b = p_178382_1_;
/* 233 */       } else if (p_178382_1_ > this.field_178388_c) {
/* 234 */         this.field_178388_c = p_178382_1_;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ItemModelGenerator.SpanFacing func_178383_a() {
/* 239 */       return this.spanFacing;
/*     */     }
/*     */     
/*     */     public int func_178385_b() {
/* 243 */       return this.field_178387_b;
/*     */     }
/*     */     
/*     */     public int func_178384_c() {
/* 247 */       return this.field_178388_c;
/*     */     }
/*     */     
/*     */     public int func_178381_d() {
/* 251 */       return this.field_178386_d;
/*     */     }
/*     */   }
/*     */   
/*     */   enum SpanFacing {
/* 256 */     UP((String)EnumFacing.UP, 0, -1),
/* 257 */     DOWN((String)EnumFacing.DOWN, 0, 1),
/* 258 */     LEFT((String)EnumFacing.EAST, -1, 0),
/* 259 */     RIGHT((String)EnumFacing.WEST, 1, 0);
/*     */     
/*     */     private final EnumFacing facing;
/*     */     private final int field_178373_f;
/*     */     private final int field_178374_g;
/*     */     
/*     */     SpanFacing(EnumFacing facing, int p_i46215_4_, int p_i46215_5_) {
/* 266 */       this.facing = facing;
/* 267 */       this.field_178373_f = p_i46215_4_;
/* 268 */       this.field_178374_g = p_i46215_5_;
/*     */     }
/*     */     
/*     */     public EnumFacing getFacing() {
/* 272 */       return this.facing;
/*     */     }
/*     */     
/*     */     public int func_178372_b() {
/* 276 */       return this.field_178373_f;
/*     */     }
/*     */     
/*     */     public int func_178371_c() {
/* 280 */       return this.field_178374_g;
/*     */     }
/*     */     
/*     */     private boolean func_178369_d() {
/* 284 */       return !(this != DOWN && this != UP);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\ItemModelGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */