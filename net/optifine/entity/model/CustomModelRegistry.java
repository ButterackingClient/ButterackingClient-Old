/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class CustomModelRegistry
/*     */ {
/*  12 */   private static Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();
/*     */   
/*     */   private static Map<String, ModelAdapter> makeMapModelAdapters() {
/*  15 */     Map<String, ModelAdapter> map = new LinkedHashMap<>();
/*  16 */     addModelAdapter(map, new ModelAdapterArmorStand());
/*  17 */     addModelAdapter(map, new ModelAdapterBat());
/*  18 */     addModelAdapter(map, new ModelAdapterBlaze());
/*  19 */     addModelAdapter(map, new ModelAdapterBoat());
/*  20 */     addModelAdapter(map, new ModelAdapterCaveSpider());
/*  21 */     addModelAdapter(map, new ModelAdapterChicken());
/*  22 */     addModelAdapter(map, new ModelAdapterCow());
/*  23 */     addModelAdapter(map, new ModelAdapterCreeper());
/*  24 */     addModelAdapter(map, new ModelAdapterDragon());
/*  25 */     addModelAdapter(map, new ModelAdapterEnderCrystal());
/*  26 */     addModelAdapter(map, new ModelAdapterEnderman());
/*  27 */     addModelAdapter(map, new ModelAdapterEndermite());
/*  28 */     addModelAdapter(map, new ModelAdapterGhast());
/*  29 */     addModelAdapter(map, new ModelAdapterGuardian());
/*  30 */     addModelAdapter(map, new ModelAdapterHorse());
/*  31 */     addModelAdapter(map, new ModelAdapterIronGolem());
/*  32 */     addModelAdapter(map, new ModelAdapterLeadKnot());
/*  33 */     addModelAdapter(map, new ModelAdapterMagmaCube());
/*  34 */     addModelAdapter(map, new ModelAdapterMinecart());
/*  35 */     addModelAdapter(map, new ModelAdapterMinecartTnt());
/*  36 */     addModelAdapter(map, new ModelAdapterMinecartMobSpawner());
/*  37 */     addModelAdapter(map, new ModelAdapterMooshroom());
/*  38 */     addModelAdapter(map, new ModelAdapterOcelot());
/*  39 */     addModelAdapter(map, new ModelAdapterPig());
/*  40 */     addModelAdapter(map, new ModelAdapterPigZombie());
/*  41 */     addModelAdapter(map, new ModelAdapterRabbit());
/*  42 */     addModelAdapter(map, new ModelAdapterSheep());
/*  43 */     addModelAdapter(map, new ModelAdapterSilverfish());
/*  44 */     addModelAdapter(map, new ModelAdapterSkeleton());
/*  45 */     addModelAdapter(map, new ModelAdapterSlime());
/*  46 */     addModelAdapter(map, new ModelAdapterSnowman());
/*  47 */     addModelAdapter(map, new ModelAdapterSpider());
/*  48 */     addModelAdapter(map, new ModelAdapterSquid());
/*  49 */     addModelAdapter(map, new ModelAdapterVillager());
/*  50 */     addModelAdapter(map, new ModelAdapterWitch());
/*  51 */     addModelAdapter(map, new ModelAdapterWither());
/*  52 */     addModelAdapter(map, new ModelAdapterWitherSkull());
/*  53 */     addModelAdapter(map, new ModelAdapterWolf());
/*  54 */     addModelAdapter(map, new ModelAdapterZombie());
/*  55 */     addModelAdapter(map, new ModelAdapterSheepWool());
/*  56 */     addModelAdapter(map, new ModelAdapterBanner());
/*  57 */     addModelAdapter(map, new ModelAdapterBook());
/*  58 */     addModelAdapter(map, new ModelAdapterChest());
/*  59 */     addModelAdapter(map, new ModelAdapterChestLarge());
/*  60 */     addModelAdapter(map, new ModelAdapterEnderChest());
/*  61 */     addModelAdapter(map, new ModelAdapterHeadHumanoid());
/*  62 */     addModelAdapter(map, new ModelAdapterHeadSkeleton());
/*  63 */     addModelAdapter(map, new ModelAdapterSign());
/*  64 */     return map;
/*     */   }
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
/*  68 */     addModelAdapter(map, modelAdapter, modelAdapter.getName());
/*  69 */     String[] astring = modelAdapter.getAliases();
/*     */     
/*  71 */     if (astring != null) {
/*  72 */       for (int i = 0; i < astring.length; i++) {
/*  73 */         String s = astring[i];
/*  74 */         addModelAdapter(map, modelAdapter, s);
/*     */       } 
/*     */     }
/*     */     
/*  78 */     ModelBase modelbase = modelAdapter.makeModel();
/*  79 */     String[] astring1 = modelAdapter.getModelRendererNames();
/*     */     
/*  81 */     for (int j = 0; j < astring1.length; j++) {
/*  82 */       String s1 = astring1[j];
/*  83 */       ModelRenderer modelrenderer = modelAdapter.getModelRenderer(modelbase, s1);
/*     */       
/*  85 */       if (modelrenderer == null) {
/*  86 */         Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + s1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String name) {
/*  92 */     if (map.containsKey(name)) {
/*  93 */       Config.warn("Model adapter already registered for id: " + name + ", class: " + modelAdapter.getEntityClass().getName());
/*     */     }
/*     */     
/*  96 */     map.put(name, modelAdapter);
/*     */   }
/*     */   
/*     */   public static ModelAdapter getModelAdapter(String name) {
/* 100 */     return mapModelAdapters.get(name);
/*     */   }
/*     */   
/*     */   public static String[] getModelNames() {
/* 104 */     Set<String> set = mapModelAdapters.keySet();
/* 105 */     String[] astring = set.<String>toArray(new String[set.size()]);
/* 106 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\CustomModelRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */