/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.model.BlockModelUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ public class BetterGrass
/*     */ {
/*     */   private static boolean betterGrass = true;
/*     */   private static boolean betterMycelium = true;
/*     */   private static boolean betterPodzol = true;
/*     */   private static boolean betterGrassSnow = true;
/*     */   private static boolean betterMyceliumSnow = true;
/*     */   private static boolean betterPodzolSnow = true;
/*     */   private static boolean grassMultilayer = false;
/*  33 */   private static TextureAtlasSprite spriteGrass = null;
/*  34 */   private static TextureAtlasSprite spriteGrassSide = null;
/*  35 */   private static TextureAtlasSprite spriteMycelium = null;
/*  36 */   private static TextureAtlasSprite spritePodzol = null;
/*  37 */   private static TextureAtlasSprite spriteSnow = null;
/*     */   private static boolean spritesLoaded = false;
/*  39 */   private static IBakedModel modelCubeGrass = null;
/*  40 */   private static IBakedModel modelCubeMycelium = null;
/*  41 */   private static IBakedModel modelCubePodzol = null;
/*  42 */   private static IBakedModel modelCubeSnow = null;
/*     */   private static boolean modelsLoaded = false;
/*     */   private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
/*     */   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
/*     */   private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
/*     */   private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
/*     */   private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";
/*     */   
/*     */   public static void updateIcons(TextureMap textureMap) {
/*  51 */     spritesLoaded = false;
/*  52 */     modelsLoaded = false;
/*  53 */     loadProperties(textureMap);
/*     */   }
/*     */   
/*     */   public static void update() {
/*  57 */     if (spritesLoaded) {
/*  58 */       modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
/*     */       
/*  60 */       if (grassMultilayer) {
/*  61 */         IBakedModel ibakedmodel = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
/*  62 */         modelCubeGrass = BlockModelUtils.joinModelsCube(ibakedmodel, modelCubeGrass);
/*     */       } 
/*     */       
/*  65 */       modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
/*  66 */       modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
/*  67 */       modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
/*  68 */       modelsLoaded = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadProperties(TextureMap textureMap) {
/*  73 */     betterGrass = true;
/*  74 */     betterMycelium = true;
/*  75 */     betterPodzol = true;
/*  76 */     betterGrassSnow = true;
/*  77 */     betterMyceliumSnow = true;
/*  78 */     betterPodzolSnow = true;
/*  79 */     spriteGrass = textureMap.registerSprite(new ResourceLocation("blocks/grass_top"));
/*  80 */     spriteGrassSide = textureMap.registerSprite(new ResourceLocation("blocks/grass_side"));
/*  81 */     spriteMycelium = textureMap.registerSprite(new ResourceLocation("blocks/mycelium_top"));
/*  82 */     spritePodzol = textureMap.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
/*  83 */     spriteSnow = textureMap.registerSprite(new ResourceLocation("blocks/snow"));
/*  84 */     spritesLoaded = true;
/*  85 */     String s = "optifine/bettergrass.properties";
/*     */     
/*     */     try {
/*  88 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/*  90 */       if (!Config.hasResource(resourcelocation)) {
/*     */         return;
/*     */       }
/*     */       
/*  94 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/*  96 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */       
/* 100 */       boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*     */       
/* 102 */       if (flag) {
/* 103 */         Config.dbg("BetterGrass: Parsing default configuration " + s);
/*     */       } else {
/* 105 */         Config.dbg("BetterGrass: Parsing configuration " + s);
/*     */       } 
/*     */       
/* 108 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 109 */       propertiesOrdered.load(inputstream);
/* 110 */       inputstream.close();
/* 111 */       betterGrass = getBoolean((Properties)propertiesOrdered, "grass", true);
/* 112 */       betterMycelium = getBoolean((Properties)propertiesOrdered, "mycelium", true);
/* 113 */       betterPodzol = getBoolean((Properties)propertiesOrdered, "podzol", true);
/* 114 */       betterGrassSnow = getBoolean((Properties)propertiesOrdered, "grass.snow", true);
/* 115 */       betterMyceliumSnow = getBoolean((Properties)propertiesOrdered, "mycelium.snow", true);
/* 116 */       betterPodzolSnow = getBoolean((Properties)propertiesOrdered, "podzol.snow", true);
/* 117 */       grassMultilayer = getBoolean((Properties)propertiesOrdered, "grass.multilayer", false);
/* 118 */       spriteGrass = registerSprite((Properties)propertiesOrdered, "texture.grass", "blocks/grass_top", textureMap);
/* 119 */       spriteGrassSide = registerSprite((Properties)propertiesOrdered, "texture.grass_side", "blocks/grass_side", textureMap);
/* 120 */       spriteMycelium = registerSprite((Properties)propertiesOrdered, "texture.mycelium", "blocks/mycelium_top", textureMap);
/* 121 */       spritePodzol = registerSprite((Properties)propertiesOrdered, "texture.podzol", "blocks/dirt_podzol_top", textureMap);
/* 122 */       spriteSnow = registerSprite((Properties)propertiesOrdered, "texture.snow", "blocks/snow", textureMap);
/* 123 */     } catch (IOException ioexception) {
/* 124 */       Config.warn("Error reading: " + s + ", " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static TextureAtlasSprite registerSprite(Properties props, String key, String textureDefault, TextureMap textureMap) {
/* 129 */     String s = props.getProperty(key);
/*     */     
/* 131 */     if (s == null) {
/* 132 */       s = textureDefault;
/*     */     }
/*     */     
/* 135 */     ResourceLocation resourcelocation = new ResourceLocation("textures/" + s + ".png");
/*     */     
/* 137 */     if (!Config.hasResource(resourcelocation)) {
/* 138 */       Config.warn("BetterGrass texture not found: " + resourcelocation);
/* 139 */       s = textureDefault;
/*     */     } 
/*     */     
/* 142 */     ResourceLocation resourcelocation1 = new ResourceLocation(s);
/* 143 */     TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation1);
/* 144 */     return textureatlassprite;
/*     */   }
/*     */   
/*     */   public static List getFaceQuads(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 148 */     if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
/* 149 */       if (!modelsLoaded) {
/* 150 */         return quads;
/*     */       }
/* 152 */       Block block = blockState.getBlock();
/* 153 */       return (block instanceof net.minecraft.block.BlockMycelium) ? getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof BlockDirt) ? getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads) : ((block instanceof net.minecraft.block.BlockGrass) ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads));
/*     */     } 
/*     */     
/* 156 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsMycelium(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 161 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 162 */     boolean flag = !(block != Blocks.snow && block != Blocks.snow_layer);
/*     */     
/* 164 */     if (Config.isBetterGrassFancy()) {
/* 165 */       if (flag) {
/* 166 */         if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer) {
/* 167 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/* 169 */       } else if (betterMycelium && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.mycelium) {
/* 170 */         return modelCubeMycelium.getFaceQuads(facing);
/*     */       } 
/* 172 */     } else if (flag) {
/* 173 */       if (betterMyceliumSnow) {
/* 174 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/* 176 */     } else if (betterMycelium) {
/* 177 */       return modelCubeMycelium.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 180 */     return quads;
/*     */   }
/*     */   
/*     */   private static List getFaceQuadsDirt(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 184 */     Block block = getBlockAt(blockPos, EnumFacing.UP, blockAccess);
/*     */     
/* 186 */     if (blockState.getValue((IProperty)BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL) {
/* 187 */       return quads;
/*     */     }
/* 189 */     boolean flag = !(block != Blocks.snow && block != Blocks.snow_layer);
/*     */     
/* 191 */     if (Config.isBetterGrassFancy()) {
/* 192 */       if (flag) {
/* 193 */         if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer) {
/* 194 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/* 196 */       } else if (betterPodzol) {
/* 197 */         BlockPos blockpos = blockPos.down().offset(facing);
/* 198 */         IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*     */         
/* 200 */         if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) {
/* 201 */           return modelCubePodzol.getFaceQuads(facing);
/*     */         }
/*     */       } 
/* 204 */     } else if (flag) {
/* 205 */       if (betterPodzolSnow) {
/* 206 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/* 208 */     } else if (betterPodzol) {
/* 209 */       return modelCubePodzol.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 212 */     return quads;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsGrass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, List quads) {
/* 217 */     Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
/* 218 */     boolean flag = !(block != Blocks.snow && block != Blocks.snow_layer);
/*     */     
/* 220 */     if (Config.isBetterGrassFancy()) {
/* 221 */       if (flag) {
/* 222 */         if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer) {
/* 223 */           return modelCubeSnow.getFaceQuads(facing);
/*     */         }
/* 225 */       } else if (betterGrass && getBlockAt(blockPos.down(), facing, blockAccess) == Blocks.grass) {
/* 226 */         return modelCubeGrass.getFaceQuads(facing);
/*     */       } 
/* 228 */     } else if (flag) {
/* 229 */       if (betterGrassSnow) {
/* 230 */         return modelCubeSnow.getFaceQuads(facing);
/*     */       }
/* 232 */     } else if (betterGrass) {
/* 233 */       return modelCubeGrass.getFaceQuads(facing);
/*     */     } 
/*     */     
/* 236 */     return quads;
/*     */   }
/*     */   
/*     */   private static Block getBlockAt(BlockPos blockPos, EnumFacing facing, IBlockAccess blockAccess) {
/* 240 */     BlockPos blockpos = blockPos.offset(facing);
/* 241 */     Block block = blockAccess.getBlockState(blockpos).getBlock();
/* 242 */     return block;
/*     */   }
/*     */   
/*     */   private static boolean getBoolean(Properties props, String key, boolean def) {
/* 246 */     String s = props.getProperty(key);
/* 247 */     return (s == null) ? def : Boolean.parseBoolean(s);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\BetterGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */