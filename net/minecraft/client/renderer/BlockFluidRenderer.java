/*     */ package net.minecraft.client.renderer;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ 
/*     */ public class BlockFluidRenderer {
/*  20 */   private TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
/*  21 */   private TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
/*     */   
/*     */   public BlockFluidRenderer() {
/*  24 */     initAtlasSprites();
/*     */   }
/*     */   
/*     */   protected void initAtlasSprites() {
/*  28 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  29 */     this.atlasSpritesLava[0] = texturemap.getAtlasSprite("minecraft:blocks/lava_still");
/*  30 */     this.atlasSpritesLava[1] = texturemap.getAtlasSprite("minecraft:blocks/lava_flow");
/*  31 */     this.atlasSpritesWater[0] = texturemap.getAtlasSprite("minecraft:blocks/water_still");
/*  32 */     this.atlasSpritesWater[1] = texturemap.getAtlasSprite("minecraft:blocks/water_flow");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
/*     */     boolean flag2;
/*     */     
/*  39 */     try { if (Config.isShaders()) {
/*  40 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccess, worldRendererIn);
/*     */       }
/*     */       
/*  43 */       BlockLiquid blockliquid = (BlockLiquid)blockStateIn.getBlock();
/*  44 */       blockliquid.setBlockBoundsBasedOnState(blockAccess, blockPosIn);
/*  45 */       TextureAtlasSprite[] atextureatlassprite = (blockliquid.getMaterial() == Material.lava) ? this.atlasSpritesLava : this.atlasSpritesWater;
/*  46 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockStateIn, blockPosIn);
/*  47 */       int i = CustomColors.getFluidColor(blockAccess, blockStateIn, blockPosIn, renderenv);
/*  48 */       float f = (i >> 16 & 0xFF) / 255.0F;
/*  49 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/*  50 */       float f2 = (i & 0xFF) / 255.0F;
/*  51 */       boolean flag = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.up(), EnumFacing.UP);
/*  52 */       boolean flag1 = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.down(), EnumFacing.DOWN);
/*  53 */       boolean[] aboolean = renderenv.getBorderFlags();
/*  54 */       aboolean[0] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.north(), EnumFacing.NORTH);
/*  55 */       aboolean[1] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.south(), EnumFacing.SOUTH);
/*  56 */       aboolean[2] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.west(), EnumFacing.WEST);
/*  57 */       aboolean[3] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.east(), EnumFacing.EAST);
/*     */       
/*  59 */       if (flag || flag1 || aboolean[0] || aboolean[1] || aboolean[2] || aboolean[3]) {
/*  60 */         boolean bool1 = false;
/*  61 */         float f3 = 0.5F;
/*  62 */         float f4 = 1.0F;
/*  63 */         float f5 = 0.8F;
/*  64 */         float f6 = 0.6F;
/*  65 */         Material material = blockliquid.getMaterial();
/*  66 */         float f7 = getFluidHeight(blockAccess, blockPosIn, material);
/*  67 */         float f8 = getFluidHeight(blockAccess, blockPosIn.south(), material);
/*  68 */         float f9 = getFluidHeight(blockAccess, blockPosIn.east().south(), material);
/*  69 */         float f10 = getFluidHeight(blockAccess, blockPosIn.east(), material);
/*  70 */         double d0 = blockPosIn.getX();
/*  71 */         double d1 = blockPosIn.getY();
/*  72 */         double d2 = blockPosIn.getZ();
/*  73 */         float f11 = 0.001F;
/*     */         
/*  75 */         if (flag) {
/*  76 */           float f13, f14, f15, f16, f17, f18, f19, f20; bool1 = true;
/*  77 */           TextureAtlasSprite textureatlassprite = atextureatlassprite[0];
/*  78 */           float f12 = (float)BlockLiquid.getFlowDirection(blockAccess, blockPosIn, material);
/*     */           
/*  80 */           if (f12 > -999.0F) {
/*  81 */             textureatlassprite = atextureatlassprite[1];
/*     */           }
/*     */           
/*  84 */           worldRendererIn.setSprite(textureatlassprite);
/*  85 */           f7 -= f11;
/*  86 */           f8 -= f11;
/*  87 */           f9 -= f11;
/*  88 */           f10 -= f11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  98 */           if (f12 < -999.0F) {
/*  99 */             f13 = textureatlassprite.getInterpolatedU(0.0D);
/* 100 */             f17 = textureatlassprite.getInterpolatedV(0.0D);
/* 101 */             f14 = f13;
/* 102 */             f18 = textureatlassprite.getInterpolatedV(16.0D);
/* 103 */             f15 = textureatlassprite.getInterpolatedU(16.0D);
/* 104 */             f19 = f18;
/* 105 */             f16 = f15;
/* 106 */             f20 = f17;
/*     */           } else {
/* 108 */             float f21 = MathHelper.sin(f12) * 0.25F;
/* 109 */             float f22 = MathHelper.cos(f12) * 0.25F;
/* 110 */             float f23 = 8.0F;
/* 111 */             f13 = textureatlassprite.getInterpolatedU((8.0F + (-f22 - f21) * 16.0F));
/* 112 */             f17 = textureatlassprite.getInterpolatedV((8.0F + (-f22 + f21) * 16.0F));
/* 113 */             f14 = textureatlassprite.getInterpolatedU((8.0F + (-f22 + f21) * 16.0F));
/* 114 */             f18 = textureatlassprite.getInterpolatedV((8.0F + (f22 + f21) * 16.0F));
/* 115 */             f15 = textureatlassprite.getInterpolatedU((8.0F + (f22 + f21) * 16.0F));
/* 116 */             f19 = textureatlassprite.getInterpolatedV((8.0F + (f22 - f21) * 16.0F));
/* 117 */             f16 = textureatlassprite.getInterpolatedU((8.0F + (f22 - f21) * 16.0F));
/* 118 */             f20 = textureatlassprite.getInterpolatedV((8.0F + (-f22 - f21) * 16.0F));
/*     */           } 
/*     */           
/* 121 */           int k2 = blockliquid.getMixedBrightnessForBlock(blockAccess, blockPosIn);
/* 122 */           int l2 = k2 >> 16 & 0xFFFF;
/* 123 */           int i3 = k2 & 0xFFFF;
/* 124 */           float f24 = f4 * f;
/* 125 */           float f25 = f4 * f1;
/* 126 */           float f26 = f4 * f2;
/* 127 */           worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 128 */           worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/* 129 */           worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 130 */           worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/*     */           
/* 132 */           if (blockliquid.shouldRenderSides(blockAccess, blockPosIn.up())) {
/* 133 */             worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 134 */             worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/* 135 */             worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 136 */             worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/*     */           } 
/*     */         } 
/*     */         
/* 140 */         if (flag1) {
/* 141 */           worldRendererIn.setSprite(atextureatlassprite[0]);
/* 142 */           float f35 = atextureatlassprite[0].getMinU();
/* 143 */           float f36 = atextureatlassprite[0].getMaxU();
/* 144 */           float f37 = atextureatlassprite[0].getMinV();
/* 145 */           float f38 = atextureatlassprite[0].getMaxV();
/* 146 */           int l1 = blockliquid.getMixedBrightnessForBlock(blockAccess, blockPosIn.down());
/* 147 */           int i2 = l1 >> 16 & 0xFFFF;
/* 148 */           int j2 = l1 & 0xFFFF;
/* 149 */           float f41 = FaceBakery.getFaceBrightness(EnumFacing.DOWN);
/* 150 */           worldRendererIn.pos(d0, d1, d2 + 1.0D).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f35, f38).lightmap(i2, j2).endVertex();
/* 151 */           worldRendererIn.pos(d0, d1, d2).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f35, f37).lightmap(i2, j2).endVertex();
/* 152 */           worldRendererIn.pos(d0 + 1.0D, d1, d2).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f36, f37).lightmap(i2, j2).endVertex();
/* 153 */           worldRendererIn.pos(d0 + 1.0D, d1, d2 + 1.0D).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f36, f38).lightmap(i2, j2).endVertex();
/* 154 */           bool1 = true;
/*     */         } 
/*     */         
/* 157 */         for (int i1 = 0; i1 < 4; i1++) {
/* 158 */           int j1 = 0;
/* 159 */           int k1 = 0;
/*     */           
/* 161 */           if (i1 == 0) {
/* 162 */             k1--;
/*     */           }
/*     */           
/* 165 */           if (i1 == 1) {
/* 166 */             k1++;
/*     */           }
/*     */           
/* 169 */           if (i1 == 2) {
/* 170 */             j1--;
/*     */           }
/*     */           
/* 173 */           if (i1 == 3) {
/* 174 */             j1++;
/*     */           }
/*     */           
/* 177 */           BlockPos blockpos = blockPosIn.add(j1, 0, k1);
/* 178 */           TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];
/* 179 */           worldRendererIn.setSprite(textureatlassprite1);
/*     */           
/* 181 */           if (aboolean[i1]) {
/*     */             float f39, f40;
/*     */ 
/*     */ 
/*     */             
/*     */             double d3, d4, d5, d6;
/*     */ 
/*     */             
/* 189 */             if (i1 == 0) {
/* 190 */               f39 = f7;
/* 191 */               f40 = f10;
/* 192 */               d3 = d0;
/* 193 */               d5 = d0 + 1.0D;
/* 194 */               d4 = d2 + f11;
/* 195 */               d6 = d2 + f11;
/* 196 */             } else if (i1 == 1) {
/* 197 */               f39 = f9;
/* 198 */               f40 = f8;
/* 199 */               d3 = d0 + 1.0D;
/* 200 */               d5 = d0;
/* 201 */               d4 = d2 + 1.0D - f11;
/* 202 */               d6 = d2 + 1.0D - f11;
/* 203 */             } else if (i1 == 2) {
/* 204 */               f39 = f8;
/* 205 */               f40 = f7;
/* 206 */               d3 = d0 + f11;
/* 207 */               d5 = d0 + f11;
/* 208 */               d4 = d2 + 1.0D;
/* 209 */               d6 = d2;
/*     */             } else {
/* 211 */               f39 = f10;
/* 212 */               f40 = f9;
/* 213 */               d3 = d0 + 1.0D - f11;
/* 214 */               d5 = d0 + 1.0D - f11;
/* 215 */               d4 = d2;
/* 216 */               d6 = d2 + 1.0D;
/*     */             } 
/*     */             
/* 219 */             bool1 = true;
/* 220 */             float f42 = textureatlassprite1.getInterpolatedU(0.0D);
/* 221 */             float f27 = textureatlassprite1.getInterpolatedU(8.0D);
/* 222 */             float f28 = textureatlassprite1.getInterpolatedV(((1.0F - f39) * 16.0F * 0.5F));
/* 223 */             float f29 = textureatlassprite1.getInterpolatedV(((1.0F - f40) * 16.0F * 0.5F));
/* 224 */             float f30 = textureatlassprite1.getInterpolatedV(8.0D);
/* 225 */             int j = blockliquid.getMixedBrightnessForBlock(blockAccess, blockpos);
/* 226 */             int k = j >> 16 & 0xFFFF;
/* 227 */             int l = j & 0xFFFF;
/* 228 */             float f31 = (i1 < 2) ? FaceBakery.getFaceBrightness(EnumFacing.NORTH) : FaceBakery.getFaceBrightness(EnumFacing.WEST);
/* 229 */             float f32 = f4 * f31 * f;
/* 230 */             float f33 = f4 * f31 * f1;
/* 231 */             float f34 = f4 * f31 * f2;
/* 232 */             worldRendererIn.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f42, f28).lightmap(k, l).endVertex();
/* 233 */             worldRendererIn.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/* 234 */             worldRendererIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
/* 235 */             worldRendererIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f42, f30).lightmap(k, l).endVertex();
/* 236 */             worldRendererIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f42, f30).lightmap(k, l).endVertex();
/* 237 */             worldRendererIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
/* 238 */             worldRendererIn.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/* 239 */             worldRendererIn.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f42, f28).lightmap(k, l).endVertex();
/*     */           } 
/*     */         } 
/*     */         
/* 243 */         worldRendererIn.setSprite(null);
/* 244 */         boolean flag3 = bool1;
/* 245 */         return flag3;
/*     */       }
/*     */        }
/*     */     finally
/*     */     
/* 250 */     { if (Config.isShaders())
/* 251 */         SVertexBuilder.popEntity(worldRendererIn);  }  if (Config.isShaders()) SVertexBuilder.popEntity(worldRendererIn);
/*     */ 
/*     */ 
/*     */     
/* 255 */     return flag2;
/*     */   }
/*     */   
/*     */   private float getFluidHeight(IBlockAccess blockAccess, BlockPos blockPosIn, Material blockMaterial) {
/* 259 */     int i = 0;
/* 260 */     float f = 0.0F;
/*     */     
/* 262 */     for (int j = 0; j < 4; j++) {
/* 263 */       BlockPos blockpos = blockPosIn.add(-(j & 0x1), 0, -(j >> 1 & 0x1));
/*     */       
/* 265 */       if (blockAccess.getBlockState(blockpos.up()).getBlock().getMaterial() == blockMaterial) {
/* 266 */         return 1.0F;
/*     */       }
/*     */       
/* 269 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/* 270 */       Material material = iblockstate.getBlock().getMaterial();
/*     */       
/* 272 */       if (material != blockMaterial) {
/* 273 */         if (!material.isSolid()) {
/* 274 */           f++;
/* 275 */           i++;
/*     */         } 
/*     */       } else {
/* 278 */         int k = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/*     */         
/* 280 */         if (k >= 8 || k == 0) {
/* 281 */           f += BlockLiquid.getLiquidHeightPercent(k) * 10.0F;
/* 282 */           i += 10;
/*     */         } 
/*     */         
/* 285 */         f += BlockLiquid.getLiquidHeightPercent(k);
/* 286 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     return 1.0F - f / i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\BlockFluidRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */