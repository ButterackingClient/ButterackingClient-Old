/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.BlockModelShapes;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockPart;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
/*     */ import net.minecraft.client.renderer.texture.IIconCreator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IRegistry;
/*     */ import net.minecraft.util.RegistrySimple;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.ITransformation;
/*     */ import net.minecraftforge.client.model.TRSRTransformation;
/*     */ import net.minecraftforge.fml.common.registry.RegistryDelegate;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.StrUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ModelBakery
/*     */ {
/*  57 */   private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])new ResourceLocation[] { new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots") });
/*  58 */   private static final Logger LOGGER = LogManager.getLogger();
/*  59 */   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
/*  60 */   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap();
/*  61 */   private static final Joiner JOINER = Joiner.on(" -> ");
/*     */   private final IResourceManager resourceManager;
/*  63 */   private final Map<ResourceLocation, TextureAtlasSprite> sprites = Maps.newHashMap();
/*  64 */   private final Map<ResourceLocation, ModelBlock> models = Maps.newLinkedHashMap();
/*  65 */   private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants = Maps.newLinkedHashMap();
/*     */   private final TextureMap textureMap;
/*     */   private final BlockModelShapes blockModelShapes;
/*  68 */   private final FaceBakery faceBakery = new FaceBakery();
/*  69 */   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*  70 */   private RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry = new RegistrySimple();
/*  71 */   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  72 */   private static final ModelBlock MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  73 */   private static final ModelBlock MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  74 */   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  75 */   private Map<String, ResourceLocation> itemLocations = Maps.newLinkedHashMap();
/*  76 */   private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions = Maps.newHashMap();
/*  77 */   private Map<Item, List<String>> variantNames = Maps.newIdentityHashMap();
/*  78 */   private static Map<RegistryDelegate<Item>, Set<String>> customVariantNames = Maps.newHashMap();
/*     */   
/*     */   public ModelBakery(IResourceManager p_i46085_1_, TextureMap p_i46085_2_, BlockModelShapes p_i46085_3_) {
/*  81 */     this.resourceManager = p_i46085_1_;
/*  82 */     this.textureMap = p_i46085_2_;
/*  83 */     this.blockModelShapes = p_i46085_3_;
/*     */   }
/*     */   
/*     */   public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
/*  87 */     loadVariantItemModels();
/*  88 */     loadModelsCheck();
/*  89 */     loadSprites();
/*  90 */     bakeItemModels();
/*  91 */     bakeBlockModels();
/*  92 */     return (IRegistry<ModelResourceLocation, IBakedModel>)this.bakedRegistry;
/*     */   }
/*     */   
/*     */   private void loadVariantItemModels() {
/*  96 */     loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
/*  97 */     this.variants.put(MODEL_MISSING, new ModelBlockDefinition.Variants(MODEL_MISSING.getVariant(), Lists.newArrayList((Object[])new ModelBlockDefinition.Variant[] { new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1) })));
/*  98 */     ResourceLocation resourcelocation = new ResourceLocation("item_frame");
/*  99 */     ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(resourcelocation);
/* 100 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "normal"));
/* 101 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "map"));
/* 102 */     loadVariantModels();
/* 103 */     loadItemModels();
/*     */   }
/*     */   
/*     */   private void loadVariants(Collection<ModelResourceLocation> p_177591_1_) {
/* 107 */     for (ModelResourceLocation modelresourcelocation : p_177591_1_) {
/*     */       try {
/* 109 */         ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(modelresourcelocation);
/*     */         
/*     */         try {
/* 112 */           registerVariant(modelblockdefinition, modelresourcelocation);
/* 113 */         } catch (Exception exception) {
/* 114 */           LOGGER.warn("Unable to load variant: " + modelresourcelocation.getVariant() + " from " + modelresourcelocation, exception);
/*     */         } 
/* 116 */       } catch (Exception exception1) {
/* 117 */         LOGGER.warn("Unable to load definition " + modelresourcelocation, exception1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerVariant(ModelBlockDefinition p_177569_1_, ModelResourceLocation p_177569_2_) {
/* 123 */     this.variants.put(p_177569_2_, p_177569_1_.getVariants(p_177569_2_.getVariant()));
/*     */   }
/*     */   
/*     */   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation p_177586_1_) {
/* 127 */     ResourceLocation resourcelocation = getBlockStateLocation(p_177586_1_);
/* 128 */     ModelBlockDefinition modelblockdefinition = this.blockDefinitions.get(resourcelocation);
/*     */     
/* 130 */     if (modelblockdefinition == null) {
/* 131 */       List<ModelBlockDefinition> list = Lists.newArrayList();
/*     */       
/*     */       try {
/* 134 */         for (IResource iresource : this.resourceManager.getAllResources(resourcelocation)) {
/* 135 */           InputStream inputstream = null;
/*     */           
/*     */           try {
/* 138 */             inputstream = iresource.getInputStream();
/* 139 */             ModelBlockDefinition modelblockdefinition1 = ModelBlockDefinition.parseFromReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/* 140 */             list.add(modelblockdefinition1);
/* 141 */           } catch (Exception exception) {
/* 142 */             throw new RuntimeException("Encountered an exception when loading model definition of '" + p_177586_1_ + "' from: '" + iresource.getResourceLocation() + "' in resourcepack: '" + iresource.getResourcePackName() + "'", exception);
/*     */           } finally {
/* 144 */             IOUtils.closeQuietly(inputstream);
/*     */           } 
/*     */         } 
/* 147 */       } catch (IOException ioexception) {
/* 148 */         throw new RuntimeException("Encountered an exception when loading model definition of model " + resourcelocation.toString(), ioexception);
/*     */       } 
/*     */       
/* 151 */       modelblockdefinition = new ModelBlockDefinition(list);
/* 152 */       this.blockDefinitions.put(resourcelocation, modelblockdefinition);
/*     */     } 
/*     */     
/* 155 */     return modelblockdefinition;
/*     */   }
/*     */   
/*     */   private ResourceLocation getBlockStateLocation(ResourceLocation p_177584_1_) {
/* 159 */     return new ResourceLocation(p_177584_1_.getResourceDomain(), "blockstates/" + p_177584_1_.getResourcePath() + ".json");
/*     */   }
/*     */   
/*     */   private void loadVariantModels() {
/* 163 */     for (ModelResourceLocation modelresourcelocation : this.variants.keySet()) {
/* 164 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)this.variants.get(modelresourcelocation)).getVariants()) {
/* 165 */         ResourceLocation resourcelocation = modelblockdefinition$variant.getModelLocation();
/*     */         
/* 167 */         if (this.models.get(resourcelocation) == null)
/*     */           try {
/* 169 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 170 */             this.models.put(resourcelocation, modelblock);
/* 171 */           } catch (Exception exception) {
/* 172 */             LOGGER.warn("Unable to load block model: '" + resourcelocation + "' for variant: '" + modelresourcelocation + "'", exception);
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private ModelBlock loadModel(ResourceLocation p_177594_1_) throws IOException {
/*     */     Reader reader;
/*     */     ModelBlock modelblock;
/* 180 */     String s = p_177594_1_.getResourcePath();
/*     */     
/* 182 */     if ("builtin/generated".equals(s))
/* 183 */       return MODEL_GENERATED; 
/* 184 */     if ("builtin/compass".equals(s))
/* 185 */       return MODEL_COMPASS; 
/* 186 */     if ("builtin/clock".equals(s))
/* 187 */       return MODEL_CLOCK; 
/* 188 */     if ("builtin/entity".equals(s)) {
/* 189 */       return MODEL_ENTITY;
/*     */     }
/*     */ 
/*     */     
/* 193 */     if (s.startsWith("builtin/")) {
/* 194 */       String s1 = s.substring("builtin/".length());
/* 195 */       String s2 = BUILT_IN_MODELS.get(s1);
/*     */       
/* 197 */       if (s2 == null) {
/* 198 */         throw new FileNotFoundException(p_177594_1_.toString());
/*     */       }
/*     */       
/* 201 */       reader = new StringReader(s2);
/*     */     } else {
/* 203 */       p_177594_1_ = getModelLocation(p_177594_1_);
/* 204 */       IResource iresource = this.resourceManager.getResource(p_177594_1_);
/* 205 */       reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 211 */       ModelBlock modelblock1 = ModelBlock.deserialize(reader);
/* 212 */       modelblock1.name = p_177594_1_.toString();
/* 213 */       modelblock = modelblock1;
/* 214 */       String s3 = TextureUtils.getBasePath(p_177594_1_.getResourcePath());
/* 215 */       fixModelLocations(modelblock1, s3);
/*     */     } finally {
/* 217 */       reader.close();
/*     */     } 
/*     */     
/* 220 */     return modelblock;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getModelLocation(ResourceLocation p_177580_1_) {
/* 225 */     ResourceLocation resourcelocation = p_177580_1_;
/* 226 */     String s = p_177580_1_.getResourcePath();
/*     */     
/* 228 */     if (!s.startsWith("mcpatcher") && !s.startsWith("optifine")) {
/* 229 */       return new ResourceLocation(p_177580_1_.getResourceDomain(), "models/" + p_177580_1_.getResourcePath() + ".json");
/*     */     }
/* 231 */     if (!s.endsWith(".json")) {
/* 232 */       resourcelocation = new ResourceLocation(p_177580_1_.getResourceDomain(), String.valueOf(s) + ".json");
/*     */     }
/*     */     
/* 235 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadItemModels() {
/* 240 */     registerVariantNames();
/*     */     
/* 242 */     for (Item item : Item.itemRegistry) {
/* 243 */       for (String s : getVariantNames(item)) {
/* 244 */         ResourceLocation resourcelocation = getItemLocation(s);
/* 245 */         this.itemLocations.put(s, resourcelocation);
/*     */         
/* 247 */         if (this.models.get(resourcelocation) == null) {
/*     */           try {
/* 249 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 250 */             this.models.put(resourcelocation, modelblock);
/* 251 */           } catch (Exception exception) {
/* 252 */             LOGGER.warn("Unable to load item model: '" + resourcelocation + "' for item: '" + Item.itemRegistry.getNameForObject(item) + "'", exception);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadItemModel(String p_loadItemModel_1_, ResourceLocation p_loadItemModel_2_, ResourceLocation p_loadItemModel_3_) {
/* 260 */     this.itemLocations.put(p_loadItemModel_1_, p_loadItemModel_2_);
/*     */     
/* 262 */     if (this.models.get(p_loadItemModel_2_) == null) {
/*     */       try {
/* 264 */         ModelBlock modelblock = loadModel(p_loadItemModel_2_);
/* 265 */         this.models.put(p_loadItemModel_2_, modelblock);
/* 266 */       } catch (Exception exception) {
/* 267 */         LOGGER.warn("Unable to load item model: '{}' for item: '{}'", new Object[] { p_loadItemModel_2_, p_loadItemModel_3_ });
/* 268 */         LOGGER.warn(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerVariantNames() {
/* 274 */     this.variantNames.clear();
/* 275 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
/* 276 */     this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
/* 277 */     this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
/* 278 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
/* 279 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.sand), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
/* 280 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
/* 281 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
/* 282 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
/* 283 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
/* 284 */     this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
/* 285 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.tallgrass), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
/* 286 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.deadbush), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
/* 287 */     this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
/* 288 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.yellow_flower), Lists.newArrayList((Object[])new String[] { "dandelion" }));
/* 289 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.red_flower), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
/* 290 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
/* 291 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab2), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
/* 292 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
/* 293 */     this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
/* 294 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
/* 295 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.wooden_slab), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
/* 296 */     this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
/* 297 */     this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
/* 298 */     this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
/* 299 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
/* 300 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass_pane), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
/* 301 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves2), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
/* 302 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
/* 303 */     this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
/* 304 */     this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
/* 305 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.double_plant), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
/* 306 */     this.variantNames.put(Items.bow, Lists.newArrayList((Object[])new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
/* 307 */     this.variantNames.put(Items.coal, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
/* 308 */     this.variantNames.put(Items.fishing_rod, Lists.newArrayList((Object[])new String[] { "fishing_rod", "fishing_rod_cast" }));
/* 309 */     this.variantNames.put(Items.fish, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
/* 310 */     this.variantNames.put(Items.cooked_fish, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
/* 311 */     this.variantNames.put(Items.dye, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
/* 312 */     this.variantNames.put(Items.potionitem, Lists.newArrayList((Object[])new String[] { "bottle_drinkable", "bottle_splash" }));
/* 313 */     this.variantNames.put(Items.skull, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
/* 314 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
/* 315 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
/* 316 */     this.variantNames.put(Items.oak_door, Lists.newArrayList((Object[])new String[] { "oak_door" }));
/*     */     
/* 318 */     for (Map.Entry<RegistryDelegate<Item>, Set<String>> entry : customVariantNames.entrySet()) {
/* 319 */       this.variantNames.put((Item)((RegistryDelegate)entry.getKey()).get(), Lists.newArrayList(((Set)entry.getValue()).iterator()));
/*     */     }
/*     */     
/* 322 */     CustomItems.update();
/* 323 */     CustomItems.loadModels(this);
/*     */   }
/*     */   
/*     */   private List<String> getVariantNames(Item p_177596_1_) {
/* 327 */     List<String> list = this.variantNames.get(p_177596_1_);
/*     */     
/* 329 */     if (list == null) {
/* 330 */       list = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(p_177596_1_)).toString());
/*     */     }
/*     */     
/* 333 */     return list;
/*     */   }
/*     */   
/*     */   private ResourceLocation getItemLocation(String p_177583_1_) {
/* 337 */     ResourceLocation resourcelocation = new ResourceLocation(p_177583_1_);
/*     */     
/* 339 */     if (Reflector.ForgeHooksClient.exists()) {
/* 340 */       resourcelocation = new ResourceLocation(p_177583_1_.replaceAll("#.*", ""));
/*     */     }
/*     */     
/* 343 */     return new ResourceLocation(resourcelocation.getResourceDomain(), "item/" + resourcelocation.getResourcePath());
/*     */   }
/*     */   
/*     */   private void bakeBlockModels() {
/* 347 */     for (ModelResourceLocation modelresourcelocation : this.variants.keySet()) {
/* 348 */       WeightedBakedModel.Builder weightedbakedmodel$builder = new WeightedBakedModel.Builder();
/* 349 */       int i = 0;
/*     */       
/* 351 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)this.variants.get(modelresourcelocation)).getVariants()) {
/* 352 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 354 */         if (modelblock != null && modelblock.isResolved()) {
/* 355 */           i++;
/* 356 */           weightedbakedmodel$builder.add(bakeModel(modelblock, modelblockdefinition$variant.getRotation(), modelblockdefinition$variant.isUvLocked()), modelblockdefinition$variant.getWeight()); continue;
/*     */         } 
/* 358 */         LOGGER.warn("Missing model for: " + modelresourcelocation);
/*     */       } 
/*     */ 
/*     */       
/* 362 */       if (i == 0) {
/* 363 */         LOGGER.warn("No weighted models for: " + modelresourcelocation); continue;
/* 364 */       }  if (i == 1) {
/* 365 */         this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.first()); continue;
/*     */       } 
/* 367 */       this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.build());
/*     */     } 
/*     */ 
/*     */     
/* 371 */     for (Map.Entry<String, ResourceLocation> entry : this.itemLocations.entrySet()) {
/* 372 */       ResourceLocation resourcelocation = entry.getValue();
/* 373 */       ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(entry.getKey(), "inventory");
/*     */       
/* 375 */       if (Reflector.ModelLoader_getInventoryVariant.exists()) {
/* 376 */         modelresourcelocation1 = (ModelResourceLocation)Reflector.call(Reflector.ModelLoader_getInventoryVariant, new Object[] { entry.getKey() });
/*     */       }
/*     */       
/* 379 */       ModelBlock modelblock1 = this.models.get(resourcelocation);
/*     */       
/* 381 */       if (modelblock1 != null && modelblock1.isResolved()) {
/* 382 */         if (isCustomRenderer(modelblock1)) {
/* 383 */           this.bakedRegistry.putObject(modelresourcelocation1, new BuiltInModel(modelblock1.getAllTransforms())); continue;
/*     */         } 
/* 385 */         this.bakedRegistry.putObject(modelresourcelocation1, bakeModel(modelblock1, ModelRotation.X0_Y0, false));
/*     */         continue;
/*     */       } 
/* 388 */       LOGGER.warn("Missing model for: " + resourcelocation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getVariantsTextureLocations() {
/* 394 */     Set<ResourceLocation> set = Sets.newHashSet();
/* 395 */     List<ModelResourceLocation> list = Lists.newArrayList(this.variants.keySet());
/* 396 */     Collections.sort(list, new Comparator<ModelResourceLocation>() {
/*     */           public int compare(ModelResourceLocation p_compare_1_, ModelResourceLocation p_compare_2_) {
/* 398 */             return p_compare_1_.toString().compareTo(p_compare_2_.toString());
/*     */           }
/*     */         });
/*     */     
/* 402 */     for (ModelResourceLocation modelresourcelocation : list) {
/* 403 */       ModelBlockDefinition.Variants modelblockdefinition$variants = this.variants.get(modelresourcelocation);
/*     */       
/* 405 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : modelblockdefinition$variants.getVariants()) {
/* 406 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 408 */         if (modelblock == null) {
/* 409 */           LOGGER.warn("Missing model for: " + modelresourcelocation); continue;
/*     */         } 
/* 411 */         set.addAll(getTextureLocations(modelblock));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 416 */     set.addAll(LOCATIONS_BUILTIN_TEXTURES);
/* 417 */     return set;
/*     */   }
/*     */   
/*     */   public IBakedModel bakeModel(ModelBlock modelBlockIn, ModelRotation modelRotationIn, boolean uvLocked) {
/* 421 */     return bakeModel(modelBlockIn, modelRotationIn, uvLocked);
/*     */   }
/*     */   
/*     */   protected IBakedModel bakeModel(ModelBlock p_bakeModel_1_, ITransformation p_bakeModel_2_, boolean p_bakeModel_3_) {
/* 425 */     TextureAtlasSprite textureatlassprite = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName("particle")));
/* 426 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(p_bakeModel_1_)).setTexture(textureatlassprite);
/*     */     
/* 428 */     for (BlockPart blockpart : p_bakeModel_1_.getElements()) {
/* 429 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/* 430 */         BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
/* 431 */         TextureAtlasSprite textureatlassprite1 = this.sprites.get(new ResourceLocation(p_bakeModel_1_.resolveTextureName(blockpartface.texture)));
/* 432 */         boolean flag = true;
/*     */         
/* 434 */         if (Reflector.ForgeHooksClient.exists()) {
/* 435 */           flag = TRSRTransformation.isInteger(p_bakeModel_2_.getMatrix());
/*     */         }
/*     */         
/* 438 */         if (blockpartface.cullFace != null && flag) {
/* 439 */           simplebakedmodel$builder.addFaceQuad(p_bakeModel_2_.rotate(blockpartface.cullFace), makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_)); continue;
/*     */         } 
/* 441 */         simplebakedmodel$builder.addGeneralQuad(makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, p_bakeModel_2_, p_bakeModel_3_));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 446 */     return simplebakedmodel$builder.makeBakedModel();
/*     */   }
/*     */   
/*     */   private BakedQuad makeBakedQuad(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, ModelRotation p_177589_5_, boolean p_177589_6_) {
/* 450 */     return Reflector.ForgeHooksClient.exists() ? makeBakedQuad(p_177589_1_, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_6_) : this.faceBakery.makeBakedQuad(p_177589_1_.positionFrom, p_177589_1_.positionTo, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.partRotation, p_177589_6_, p_177589_1_.shade);
/*     */   }
/*     */   
/*     */   protected BakedQuad makeBakedQuad(BlockPart p_makeBakedQuad_1_, BlockPartFace p_makeBakedQuad_2_, TextureAtlasSprite p_makeBakedQuad_3_, EnumFacing p_makeBakedQuad_4_, ITransformation p_makeBakedQuad_5_, boolean p_makeBakedQuad_6_) {
/* 454 */     return this.faceBakery.makeBakedQuad(p_makeBakedQuad_1_.positionFrom, p_makeBakedQuad_1_.positionTo, p_makeBakedQuad_2_, p_makeBakedQuad_3_, p_makeBakedQuad_4_, p_makeBakedQuad_5_, p_makeBakedQuad_1_.partRotation, p_makeBakedQuad_6_, p_makeBakedQuad_1_.shade);
/*     */   }
/*     */   
/*     */   private void loadModelsCheck() {
/* 458 */     loadModels();
/*     */     
/* 460 */     for (ModelBlock modelblock : this.models.values()) {
/* 461 */       modelblock.getParentFromMap(this.models);
/*     */     }
/*     */     
/* 464 */     ModelBlock.checkModelHierarchy(this.models);
/*     */   }
/*     */   
/*     */   private void loadModels() {
/* 468 */     Deque<ResourceLocation> deque = Queues.newArrayDeque();
/* 469 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 471 */     for (ResourceLocation resourcelocation : this.models.keySet()) {
/* 472 */       set.add(resourcelocation);
/* 473 */       ResourceLocation resourcelocation1 = ((ModelBlock)this.models.get(resourcelocation)).getParentLocation();
/*     */       
/* 475 */       if (resourcelocation1 != null) {
/* 476 */         deque.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 480 */     while (!deque.isEmpty()) {
/* 481 */       ResourceLocation resourcelocation2 = deque.pop();
/*     */       
/*     */       try {
/* 484 */         if (this.models.get(resourcelocation2) != null) {
/*     */           continue;
/*     */         }
/*     */         
/* 488 */         ModelBlock modelblock = loadModel(resourcelocation2);
/* 489 */         this.models.put(resourcelocation2, modelblock);
/* 490 */         ResourceLocation resourcelocation3 = modelblock.getParentLocation();
/*     */         
/* 492 */         if (resourcelocation3 != null && !set.contains(resourcelocation3)) {
/* 493 */           deque.add(resourcelocation3);
/*     */         }
/* 495 */       } catch (Exception var6) {
/* 496 */         LOGGER.warn("In parent chain: " + JOINER.join(getParentPath(resourcelocation2)) + "; unable to load model: '" + resourcelocation2 + "'");
/*     */       } 
/*     */       
/* 499 */       set.add(resourcelocation2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<ResourceLocation> getParentPath(ResourceLocation p_177573_1_) {
/* 504 */     List<ResourceLocation> list = Lists.newArrayList((Object[])new ResourceLocation[] { p_177573_1_ });
/* 505 */     ResourceLocation resourcelocation = p_177573_1_;
/*     */     
/* 507 */     while ((resourcelocation = getParentLocation(resourcelocation)) != null) {
/* 508 */       list.add(0, resourcelocation);
/*     */     }
/*     */     
/* 511 */     return list;
/*     */   }
/*     */   
/*     */   private ResourceLocation getParentLocation(ResourceLocation p_177576_1_) {
/* 515 */     for (Map.Entry<ResourceLocation, ModelBlock> entry : this.models.entrySet()) {
/* 516 */       ModelBlock modelblock = entry.getValue();
/*     */       
/* 518 */       if (modelblock != null && p_177576_1_.equals(modelblock.getParentLocation())) {
/* 519 */         return entry.getKey();
/*     */       }
/*     */     } 
/*     */     
/* 523 */     return null;
/*     */   }
/*     */   
/*     */   private Set<ResourceLocation> getTextureLocations(ModelBlock p_177585_1_) {
/* 527 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 529 */     for (BlockPart blockpart : p_177585_1_.getElements()) {
/* 530 */       for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/* 531 */         ResourceLocation resourcelocation = new ResourceLocation(p_177585_1_.resolveTextureName(blockpartface.texture));
/* 532 */         set.add(resourcelocation);
/*     */       } 
/*     */     } 
/*     */     
/* 536 */     set.add(new ResourceLocation(p_177585_1_.resolveTextureName("particle")));
/* 537 */     return set;
/*     */   }
/*     */   
/*     */   private void loadSprites() {
/* 541 */     final Set<ResourceLocation> set = getVariantsTextureLocations();
/* 542 */     set.addAll(getItemsTextureLocations());
/* 543 */     set.remove(TextureMap.LOCATION_MISSING_TEXTURE);
/* 544 */     IIconCreator iiconcreator = new IIconCreator() {
/*     */         public void registerSprites(TextureMap iconRegistry) {
/* 546 */           for (ResourceLocation resourcelocation : set) {
/* 547 */             TextureAtlasSprite textureatlassprite = iconRegistry.registerSprite(resourcelocation);
/* 548 */             ModelBakery.this.sprites.put(resourcelocation, textureatlassprite);
/*     */           } 
/*     */         }
/*     */       };
/* 552 */     this.textureMap.loadSprites(this.resourceManager, iiconcreator);
/* 553 */     this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
/*     */   }
/*     */   
/*     */   private Set<ResourceLocation> getItemsTextureLocations() {
/* 557 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 559 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/* 560 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 562 */       if (modelblock != null) {
/* 563 */         set.add(new ResourceLocation(modelblock.resolveTextureName("particle")));
/*     */         
/* 565 */         if (hasItemModel(modelblock)) {
/* 566 */           for (String s : ItemModelGenerator.LAYERS) {
/* 567 */             ResourceLocation resourcelocation2 = new ResourceLocation(modelblock.resolveTextureName(s));
/*     */             
/* 569 */             if (modelblock.getRootModel() == MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/* 570 */               TextureAtlasSprite.setLocationNameCompass(resourcelocation2.toString());
/* 571 */             } else if (modelblock.getRootModel() == MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/* 572 */               TextureAtlasSprite.setLocationNameClock(resourcelocation2.toString());
/*     */             } 
/*     */             
/* 575 */             set.add(resourcelocation2);
/*     */           }  continue;
/* 577 */         }  if (!isCustomRenderer(modelblock)) {
/* 578 */           for (BlockPart blockpart : modelblock.getElements()) {
/* 579 */             for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/* 580 */               ResourceLocation resourcelocation1 = new ResourceLocation(modelblock.resolveTextureName(blockpartface.texture));
/* 581 */               set.add(resourcelocation1);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 588 */     return set;
/*     */   }
/*     */   
/*     */   private boolean hasItemModel(ModelBlock p_177581_1_) {
/* 592 */     if (p_177581_1_ == null) {
/* 593 */       return false;
/*     */     }
/* 595 */     ModelBlock modelblock = p_177581_1_.getRootModel();
/* 596 */     return !(modelblock != MODEL_GENERATED && modelblock != MODEL_COMPASS && modelblock != MODEL_CLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isCustomRenderer(ModelBlock p_177587_1_) {
/* 601 */     if (p_177587_1_ == null) {
/* 602 */       return false;
/*     */     }
/* 604 */     ModelBlock modelblock = p_177587_1_.getRootModel();
/* 605 */     return (modelblock == MODEL_ENTITY);
/*     */   }
/*     */ 
/*     */   
/*     */   private void bakeItemModels() {
/* 610 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/* 611 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 613 */       if (hasItemModel(modelblock)) {
/* 614 */         ModelBlock modelblock1 = makeItemModel(modelblock);
/*     */         
/* 616 */         if (modelblock1 != null) {
/* 617 */           modelblock1.name = resourcelocation.toString();
/*     */         }
/*     */         
/* 620 */         this.models.put(resourcelocation, modelblock1); continue;
/* 621 */       }  if (isCustomRenderer(modelblock)) {
/* 622 */         this.models.put(resourcelocation, modelblock);
/*     */       }
/*     */     } 
/*     */     
/* 626 */     for (TextureAtlasSprite textureatlassprite : this.sprites.values()) {
/* 627 */       if (!textureatlassprite.hasAnimationMetadata()) {
/* 628 */         textureatlassprite.clearFramesTextureData();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private ModelBlock makeItemModel(ModelBlock p_177582_1_) {
/* 634 */     return this.itemModelGenerator.makeItemModel(this.textureMap, p_177582_1_);
/*     */   }
/*     */   
/*     */   public ModelBlock getModelBlock(ResourceLocation p_getModelBlock_1_) {
/* 638 */     ModelBlock modelblock = this.models.get(p_getModelBlock_1_);
/* 639 */     return modelblock;
/*     */   }
/*     */   
/*     */   public static void fixModelLocations(ModelBlock p_fixModelLocations_0_, String p_fixModelLocations_1_) {
/* 643 */     ResourceLocation resourcelocation = fixModelLocation(p_fixModelLocations_0_.getParentLocation(), p_fixModelLocations_1_);
/*     */     
/* 645 */     if (resourcelocation != p_fixModelLocations_0_.getParentLocation()) {
/* 646 */       Reflector.setFieldValue(p_fixModelLocations_0_, Reflector.ModelBlock_parentLocation, resourcelocation);
/*     */     }
/*     */     
/* 649 */     Map<String, String> map = (Map<String, String>)Reflector.getFieldValue(p_fixModelLocations_0_, Reflector.ModelBlock_textures);
/*     */     
/* 651 */     if (map != null) {
/* 652 */       for (Map.Entry<String, String> entry : map.entrySet()) {
/* 653 */         String s = entry.getValue();
/* 654 */         String s1 = fixResourcePath(s, p_fixModelLocations_1_);
/*     */         
/* 656 */         if (s1 != s) {
/* 657 */           entry.setValue(s1);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static ResourceLocation fixModelLocation(ResourceLocation p_fixModelLocation_0_, String p_fixModelLocation_1_) {
/* 664 */     if (p_fixModelLocation_0_ != null && p_fixModelLocation_1_ != null) {
/* 665 */       if (!p_fixModelLocation_0_.getResourceDomain().equals("minecraft")) {
/* 666 */         return p_fixModelLocation_0_;
/*     */       }
/* 668 */       String s = p_fixModelLocation_0_.getResourcePath();
/* 669 */       String s1 = fixResourcePath(s, p_fixModelLocation_1_);
/*     */       
/* 671 */       if (s1 != s) {
/* 672 */         p_fixModelLocation_0_ = new ResourceLocation(p_fixModelLocation_0_.getResourceDomain(), s1);
/*     */       }
/*     */       
/* 675 */       return p_fixModelLocation_0_;
/*     */     } 
/*     */     
/* 678 */     return p_fixModelLocation_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixResourcePath(String p_fixResourcePath_0_, String p_fixResourcePath_1_) {
/* 683 */     p_fixResourcePath_0_ = TextureUtils.fixResourcePath(p_fixResourcePath_0_, p_fixResourcePath_1_);
/* 684 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".json");
/* 685 */     p_fixResourcePath_0_ = StrUtils.removeSuffix(p_fixResourcePath_0_, ".png");
/* 686 */     return p_fixResourcePath_0_;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static void addVariantName(Item p_addVariantName_0_, String... p_addVariantName_1_) {
/* 691 */     RegistryDelegate<Item> registrydelegate = (RegistryDelegate)Reflector.getFieldValue(p_addVariantName_0_, Reflector.ForgeItem_delegate);
/*     */     
/* 693 */     if (customVariantNames.containsKey(registrydelegate)) {
/* 694 */       ((Set)customVariantNames.get(registrydelegate)).addAll(Lists.newArrayList((Object[])p_addVariantName_1_));
/*     */     } else {
/* 696 */       customVariantNames.put(registrydelegate, Sets.newHashSet((Object[])p_addVariantName_1_));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <T extends ResourceLocation> void registerItemVariants(Item p_registerItemVariants_0_, ResourceLocation... p_registerItemVariants_1_) {
/* 701 */     RegistryDelegate<Item> registrydelegate = (RegistryDelegate)Reflector.getFieldValue(p_registerItemVariants_0_, Reflector.ForgeItem_delegate);
/*     */     
/* 703 */     if (!customVariantNames.containsKey(registrydelegate))
/* 704 */       customVariantNames.put(registrydelegate, Sets.newHashSet());  byte b;
/*     */     int i;
/*     */     ResourceLocation[] arrayOfResourceLocation;
/* 707 */     for (i = (arrayOfResourceLocation = p_registerItemVariants_1_).length, b = 0; b < i; ) { ResourceLocation resourcelocation = arrayOfResourceLocation[b];
/* 708 */       ((Set<String>)customVariantNames.get(registrydelegate)).add(resourcelocation.toString());
/*     */       b++; }
/*     */   
/*     */   }
/*     */   static {
/* 713 */     BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
/* 714 */     MODEL_GENERATED.name = "generation marker";
/* 715 */     MODEL_COMPASS.name = "compass generation marker";
/* 716 */     MODEL_CLOCK.name = "class generation marker";
/* 717 */     MODEL_ENTITY.name = "block entity marker";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\model\ModelBakery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */