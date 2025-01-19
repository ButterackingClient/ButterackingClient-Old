/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class StatList
/*     */ {
/*  24 */   protected static Map<String, StatBase> oneShotStats = Maps.newHashMap();
/*  25 */   public static List<StatBase> allStats = Lists.newArrayList();
/*  26 */   public static List<StatBase> generalStats = Lists.newArrayList();
/*  27 */   public static List<StatCrafting> itemStats = Lists.newArrayList();
/*  28 */   public static List<StatCrafting> objectMineStats = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static StatBase leaveGameStat = (new StatBasic("stat.leaveGame", (IChatComponent)new ChatComponentTranslation("stat.leaveGame", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static StatBase minutesPlayedStat = (new StatBasic("stat.playOneMinute", (IChatComponent)new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*  39 */   public static StatBase timeSinceDeathStat = (new StatBasic("stat.timeSinceDeath", (IChatComponent)new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static StatBase distanceWalkedStat = (new StatBasic("stat.walkOneCm", (IChatComponent)new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  45 */   public static StatBase distanceCrouchedStat = (new StatBasic("stat.crouchOneCm", (IChatComponent)new ChatComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  46 */   public static StatBase distanceSprintedStat = (new StatBasic("stat.sprintOneCm", (IChatComponent)new ChatComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static StatBase distanceSwumStat = (new StatBasic("stat.swimOneCm", (IChatComponent)new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static StatBase distanceFallenStat = (new StatBasic("stat.fallOneCm", (IChatComponent)new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static StatBase distanceClimbedStat = (new StatBasic("stat.climbOneCm", (IChatComponent)new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static StatBase distanceFlownStat = (new StatBasic("stat.flyOneCm", (IChatComponent)new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static StatBase distanceDoveStat = (new StatBasic("stat.diveOneCm", (IChatComponent)new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static StatBase distanceByMinecartStat = (new StatBasic("stat.minecartOneCm", (IChatComponent)new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static StatBase distanceByBoatStat = (new StatBasic("stat.boatOneCm", (IChatComponent)new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static StatBase distanceByPigStat = (new StatBasic("stat.pigOneCm", (IChatComponent)new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  87 */   public static StatBase distanceByHorseStat = (new StatBasic("stat.horseOneCm", (IChatComponent)new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static StatBase jumpStat = (new StatBasic("stat.jump", (IChatComponent)new ChatComponentTranslation("stat.jump", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static StatBase dropStat = (new StatBasic("stat.drop", (IChatComponent)new ChatComponentTranslation("stat.drop", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static StatBase damageDealtStat = (new StatBasic("stat.damageDealt", (IChatComponent)new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.field_111202_k)).registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static StatBase damageTakenStat = (new StatBasic("stat.damageTaken", (IChatComponent)new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.field_111202_k)).registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static StatBase deathsStat = (new StatBasic("stat.deaths", (IChatComponent)new ChatComponentTranslation("stat.deaths", new Object[0]))).registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static StatBase mobKillsStat = (new StatBasic("stat.mobKills", (IChatComponent)new ChatComponentTranslation("stat.mobKills", new Object[0]))).registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static StatBase animalsBredStat = (new StatBasic("stat.animalsBred", (IChatComponent)new ChatComponentTranslation("stat.animalsBred", new Object[0]))).registerStat();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static StatBase playerKillsStat = (new StatBasic("stat.playerKills", (IChatComponent)new ChatComponentTranslation("stat.playerKills", new Object[0]))).registerStat();
/* 128 */   public static StatBase fishCaughtStat = (new StatBasic("stat.fishCaught", (IChatComponent)new ChatComponentTranslation("stat.fishCaught", new Object[0]))).registerStat();
/* 129 */   public static StatBase junkFishedStat = (new StatBasic("stat.junkFished", (IChatComponent)new ChatComponentTranslation("stat.junkFished", new Object[0]))).registerStat();
/* 130 */   public static StatBase treasureFishedStat = (new StatBasic("stat.treasureFished", (IChatComponent)new ChatComponentTranslation("stat.treasureFished", new Object[0]))).registerStat();
/* 131 */   public static StatBase timesTalkedToVillagerStat = (new StatBasic("stat.talkedToVillager", (IChatComponent)new ChatComponentTranslation("stat.talkedToVillager", new Object[0]))).registerStat();
/* 132 */   public static StatBase timesTradedWithVillagerStat = (new StatBasic("stat.tradedWithVillager", (IChatComponent)new ChatComponentTranslation("stat.tradedWithVillager", new Object[0]))).registerStat();
/* 133 */   public static StatBase field_181724_H = (new StatBasic("stat.cakeSlicesEaten", (IChatComponent)new ChatComponentTranslation("stat.cakeSlicesEaten", new Object[0]))).registerStat();
/* 134 */   public static StatBase field_181725_I = (new StatBasic("stat.cauldronFilled", (IChatComponent)new ChatComponentTranslation("stat.cauldronFilled", new Object[0]))).registerStat();
/* 135 */   public static StatBase field_181726_J = (new StatBasic("stat.cauldronUsed", (IChatComponent)new ChatComponentTranslation("stat.cauldronUsed", new Object[0]))).registerStat();
/* 136 */   public static StatBase field_181727_K = (new StatBasic("stat.armorCleaned", (IChatComponent)new ChatComponentTranslation("stat.armorCleaned", new Object[0]))).registerStat();
/* 137 */   public static StatBase field_181728_L = (new StatBasic("stat.bannerCleaned", (IChatComponent)new ChatComponentTranslation("stat.bannerCleaned", new Object[0]))).registerStat();
/* 138 */   public static StatBase field_181729_M = (new StatBasic("stat.brewingstandInteraction", (IChatComponent)new ChatComponentTranslation("stat.brewingstandInteraction", new Object[0]))).registerStat();
/* 139 */   public static StatBase field_181730_N = (new StatBasic("stat.beaconInteraction", (IChatComponent)new ChatComponentTranslation("stat.beaconInteraction", new Object[0]))).registerStat();
/* 140 */   public static StatBase field_181731_O = (new StatBasic("stat.dropperInspected", (IChatComponent)new ChatComponentTranslation("stat.dropperInspected", new Object[0]))).registerStat();
/* 141 */   public static StatBase field_181732_P = (new StatBasic("stat.hopperInspected", (IChatComponent)new ChatComponentTranslation("stat.hopperInspected", new Object[0]))).registerStat();
/* 142 */   public static StatBase field_181733_Q = (new StatBasic("stat.dispenserInspected", (IChatComponent)new ChatComponentTranslation("stat.dispenserInspected", new Object[0]))).registerStat();
/* 143 */   public static StatBase field_181734_R = (new StatBasic("stat.noteblockPlayed", (IChatComponent)new ChatComponentTranslation("stat.noteblockPlayed", new Object[0]))).registerStat();
/* 144 */   public static StatBase field_181735_S = (new StatBasic("stat.noteblockTuned", (IChatComponent)new ChatComponentTranslation("stat.noteblockTuned", new Object[0]))).registerStat();
/* 145 */   public static StatBase field_181736_T = (new StatBasic("stat.flowerPotted", (IChatComponent)new ChatComponentTranslation("stat.flowerPotted", new Object[0]))).registerStat();
/* 146 */   public static StatBase field_181737_U = (new StatBasic("stat.trappedChestTriggered", (IChatComponent)new ChatComponentTranslation("stat.trappedChestTriggered", new Object[0]))).registerStat();
/* 147 */   public static StatBase field_181738_V = (new StatBasic("stat.enderchestOpened", (IChatComponent)new ChatComponentTranslation("stat.enderchestOpened", new Object[0]))).registerStat();
/* 148 */   public static StatBase field_181739_W = (new StatBasic("stat.itemEnchanted", (IChatComponent)new ChatComponentTranslation("stat.itemEnchanted", new Object[0]))).registerStat();
/* 149 */   public static StatBase field_181740_X = (new StatBasic("stat.recordPlayed", (IChatComponent)new ChatComponentTranslation("stat.recordPlayed", new Object[0]))).registerStat();
/* 150 */   public static StatBase field_181741_Y = (new StatBasic("stat.furnaceInteraction", (IChatComponent)new ChatComponentTranslation("stat.furnaceInteraction", new Object[0]))).registerStat();
/* 151 */   public static StatBase field_181742_Z = (new StatBasic("stat.craftingTableInteraction", (IChatComponent)new ChatComponentTranslation("stat.workbenchInteraction", new Object[0]))).registerStat();
/* 152 */   public static StatBase field_181723_aa = (new StatBasic("stat.chestOpened", (IChatComponent)new ChatComponentTranslation("stat.chestOpened", new Object[0]))).registerStat();
/* 153 */   public static final StatBase[] mineBlockStatArray = new StatBase[4096];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public static final StatBase[] objectCraftStats = new StatBase[32000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public static final StatBase[] objectUseStats = new StatBase[32000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final StatBase[] objectBreakStats = new StatBase[32000];
/*     */   
/*     */   public static void init() {
/* 171 */     initMiningStats();
/* 172 */     initStats();
/* 173 */     initItemDepleteStats();
/* 174 */     initCraftableStats();
/* 175 */     AchievementList.init();
/* 176 */     EntityList.func_151514_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initCraftableStats() {
/* 184 */     Set<Item> set = Sets.newHashSet();
/*     */     
/* 186 */     for (IRecipe irecipe : CraftingManager.getInstance().getRecipeList()) {
/* 187 */       if (irecipe.getRecipeOutput() != null) {
/* 188 */         set.add(irecipe.getRecipeOutput().getItem());
/*     */       }
/*     */     } 
/*     */     
/* 192 */     for (ItemStack itemstack : FurnaceRecipes.instance().getSmeltingList().values()) {
/* 193 */       set.add(itemstack.getItem());
/*     */     }
/*     */     
/* 196 */     for (Item item : set) {
/* 197 */       if (item != null) {
/* 198 */         int i = Item.getIdFromItem(item);
/* 199 */         String s = func_180204_a(item);
/*     */         
/* 201 */         if (s != null) {
/* 202 */           objectCraftStats[i] = (new StatCrafting("stat.craftItem.", s, (IChatComponent)new ChatComponentTranslation("stat.craftItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     replaceAllSimilarBlocks(objectCraftStats);
/*     */   }
/*     */   
/*     */   private static void initMiningStats() {
/* 211 */     for (Block block : Block.blockRegistry) {
/* 212 */       Item item = Item.getItemFromBlock(block);
/*     */       
/* 214 */       if (item != null) {
/* 215 */         int i = Block.getIdFromBlock(block);
/* 216 */         String s = func_180204_a(item);
/*     */         
/* 218 */         if (s != null && block.getEnableStats()) {
/* 219 */           mineBlockStatArray[i] = (new StatCrafting("stat.mineBlock.", s, (IChatComponent)new ChatComponentTranslation("stat.mineBlock", new Object[] { (new ItemStack(block)).getChatComponent() }), item)).registerStat();
/* 220 */           objectMineStats.add((StatCrafting)mineBlockStatArray[i]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     replaceAllSimilarBlocks(mineBlockStatArray);
/*     */   }
/*     */   
/*     */   private static void initStats() {
/* 229 */     for (Item item : Item.itemRegistry) {
/* 230 */       if (item != null) {
/* 231 */         int i = Item.getIdFromItem(item);
/* 232 */         String s = func_180204_a(item);
/*     */         
/* 234 */         if (s != null) {
/* 235 */           objectUseStats[i] = (new StatCrafting("stat.useItem.", s, (IChatComponent)new ChatComponentTranslation("stat.useItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */           
/* 237 */           if (!(item instanceof net.minecraft.item.ItemBlock)) {
/* 238 */             itemStats.add((StatCrafting)objectUseStats[i]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     replaceAllSimilarBlocks(objectUseStats);
/*     */   }
/*     */   
/*     */   private static void initItemDepleteStats() {
/* 248 */     for (Item item : Item.itemRegistry) {
/* 249 */       if (item != null) {
/* 250 */         int i = Item.getIdFromItem(item);
/* 251 */         String s = func_180204_a(item);
/*     */         
/* 253 */         if (s != null && item.isDamageable()) {
/* 254 */           objectBreakStats[i] = (new StatCrafting("stat.breakItem.", s, (IChatComponent)new ChatComponentTranslation("stat.breakItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     replaceAllSimilarBlocks(objectBreakStats);
/*     */   }
/*     */   
/*     */   private static String func_180204_a(Item p_180204_0_) {
/* 263 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(p_180204_0_);
/* 264 */     return (resourcelocation != null) ? resourcelocation.toString().replace(':', '.') : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceAllSimilarBlocks(StatBase[] p_75924_0_) {
/* 271 */     mergeStatBases(p_75924_0_, (Block)Blocks.water, (Block)Blocks.flowing_water);
/* 272 */     mergeStatBases(p_75924_0_, (Block)Blocks.lava, (Block)Blocks.flowing_lava);
/* 273 */     mergeStatBases(p_75924_0_, Blocks.lit_pumpkin, Blocks.pumpkin);
/* 274 */     mergeStatBases(p_75924_0_, Blocks.lit_furnace, Blocks.furnace);
/* 275 */     mergeStatBases(p_75924_0_, Blocks.lit_redstone_ore, Blocks.redstone_ore);
/* 276 */     mergeStatBases(p_75924_0_, (Block)Blocks.powered_repeater, (Block)Blocks.unpowered_repeater);
/* 277 */     mergeStatBases(p_75924_0_, (Block)Blocks.powered_comparator, (Block)Blocks.unpowered_comparator);
/* 278 */     mergeStatBases(p_75924_0_, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
/* 279 */     mergeStatBases(p_75924_0_, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
/* 280 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_stone_slab, (Block)Blocks.stone_slab);
/* 281 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_wooden_slab, (Block)Blocks.wooden_slab);
/* 282 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_stone_slab2, (Block)Blocks.stone_slab2);
/* 283 */     mergeStatBases(p_75924_0_, (Block)Blocks.grass, Blocks.dirt);
/* 284 */     mergeStatBases(p_75924_0_, Blocks.farmland, Blocks.dirt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mergeStatBases(StatBase[] statBaseIn, Block p_151180_1_, Block p_151180_2_) {
/* 291 */     int i = Block.getIdFromBlock(p_151180_1_);
/* 292 */     int j = Block.getIdFromBlock(p_151180_2_);
/*     */     
/* 294 */     if (statBaseIn[i] != null && statBaseIn[j] == null) {
/* 295 */       statBaseIn[j] = statBaseIn[i];
/*     */     } else {
/* 297 */       allStats.remove(statBaseIn[i]);
/* 298 */       objectMineStats.remove(statBaseIn[i]);
/* 299 */       generalStats.remove(statBaseIn[i]);
/* 300 */       statBaseIn[i] = statBaseIn[j];
/*     */     } 
/*     */   }
/*     */   
/*     */   public static StatBase getStatKillEntity(EntityList.EntityEggInfo eggInfo) {
/* 305 */     String s = EntityList.getStringFromID(eggInfo.spawnedID);
/* 306 */     return (s == null) ? null : (new StatBase("stat.killEntity." + s, (IChatComponent)new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */   
/*     */   public static StatBase getStatEntityKilledBy(EntityList.EntityEggInfo eggInfo) {
/* 310 */     String s = EntityList.getStringFromID(eggInfo.spawnedID);
/* 311 */     return (s == null) ? null : (new StatBase("stat.entityKilledBy." + s, (IChatComponent)new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */   
/*     */   public static StatBase getOneShotStat(String p_151177_0_) {
/* 315 */     return oneShotStats.get(p_151177_0_);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\StatList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */