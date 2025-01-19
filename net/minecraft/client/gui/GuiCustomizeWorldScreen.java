/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.primitives.Floats;
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
/*     */ 
/*     */ public class GuiCustomizeWorldScreen
/*     */   extends GuiScreen
/*     */   implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
/*     */   private GuiCreateWorld field_175343_i;
/*  20 */   protected String field_175341_a = "Customize World Settings";
/*  21 */   protected String field_175333_f = "Page 1 of 3";
/*  22 */   protected String field_175335_g = "Basic Settings";
/*  23 */   protected String[] field_175342_h = new String[4]; private GuiPageButtonList field_175349_r;
/*     */   private GuiButton field_175348_s;
/*     */   private GuiButton field_175347_t;
/*     */   private GuiButton field_175346_u;
/*     */   private GuiButton field_175345_v;
/*     */   private GuiButton field_175344_w;
/*     */   private GuiButton field_175352_x;
/*     */   private GuiButton field_175351_y;
/*     */   private GuiButton field_175350_z;
/*     */   private boolean field_175338_A = false;
/*     */   private boolean field_175340_C = false;
/*  34 */   private int field_175339_B = 0;
/*     */   
/*  36 */   private Predicate<String> field_175332_D = new Predicate<String>() {
/*     */       public boolean apply(String p_apply_1_) {
/*  38 */         Float f = Floats.tryParse(p_apply_1_);
/*  39 */         return !(p_apply_1_.length() != 0 && (f == null || !Floats.isFinite(f.floatValue()) || f.floatValue() < 0.0F));
/*     */       }
/*     */     };
/*  42 */   private ChunkProviderSettings.Factory field_175334_E = new ChunkProviderSettings.Factory();
/*     */ 
/*     */   
/*     */   private ChunkProviderSettings.Factory field_175336_F;
/*     */ 
/*     */   
/*  48 */   private Random random = new Random();
/*     */   
/*     */   public GuiCustomizeWorldScreen(GuiScreen p_i45521_1_, String p_i45521_2_) {
/*  51 */     this.field_175343_i = (GuiCreateWorld)p_i45521_1_;
/*  52 */     func_175324_a(p_i45521_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  60 */     int i = 0;
/*  61 */     int j = 0;
/*     */     
/*  63 */     if (this.field_175349_r != null) {
/*  64 */       i = this.field_175349_r.func_178059_e();
/*  65 */       j = this.field_175349_r.getAmountScrolled();
/*     */     } 
/*     */     
/*  68 */     this.field_175341_a = I18n.format("options.customizeTitle", new Object[0]);
/*  69 */     this.buttonList.clear();
/*  70 */     this.buttonList.add(this.field_175345_v = new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev", new Object[0])));
/*  71 */     this.buttonList.add(this.field_175344_w = new GuiButton(303, width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next", new Object[0])));
/*  72 */     this.buttonList.add(this.field_175346_u = new GuiButton(304, width / 2 - 187, height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults", new Object[0])));
/*  73 */     this.buttonList.add(this.field_175347_t = new GuiButton(301, width / 2 - 92, height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize", new Object[0])));
/*  74 */     this.buttonList.add(this.field_175350_z = new GuiButton(305, width / 2 + 3, height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets", new Object[0])));
/*  75 */     this.buttonList.add(this.field_175348_s = new GuiButton(300, width / 2 + 98, height - 27, 90, 20, I18n.format("gui.done", new Object[0])));
/*  76 */     this.field_175346_u.enabled = this.field_175338_A;
/*  77 */     this.field_175352_x = new GuiButton(306, width / 2 - 55, 160, 50, 20, I18n.format("gui.yes", new Object[0]));
/*  78 */     this.field_175352_x.visible = false;
/*  79 */     this.buttonList.add(this.field_175352_x);
/*  80 */     this.field_175351_y = new GuiButton(307, width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
/*  81 */     this.field_175351_y.visible = false;
/*  82 */     this.buttonList.add(this.field_175351_y);
/*     */     
/*  84 */     if (this.field_175339_B != 0) {
/*  85 */       this.field_175352_x.visible = true;
/*  86 */       this.field_175351_y.visible = true;
/*     */     } 
/*     */     
/*  89 */     func_175325_f();
/*     */     
/*  91 */     if (i != 0) {
/*  92 */       this.field_175349_r.func_181156_c(i);
/*  93 */       this.field_175349_r.scrollBy(j);
/*  94 */       func_175328_i();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 102 */     super.handleMouseInput();
/* 103 */     this.field_175349_r.handleMouseInput();
/*     */   }
/*     */   
/*     */   private void func_175325_f() {
/* 107 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry = { new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0F, 255.0F, this.field_175336_F.seaLevel), new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves", new Object[0]), true, this.field_175336_F.useCaves), new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.field_175336_F.useStrongholds), new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages", new Object[0]), true, this.field_175336_F.useVillages), new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.field_175336_F.useMineShafts), new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples", new Object[0]), true, this.field_175336_F.useTemples), new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments", new Object[0]), true, this.field_175336_F.useMonuments), new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines", new Object[0]), true, this.field_175336_F.useRavines), new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons", new Object[0]), true, this.field_175336_F.useDungeons), new GuiPageButtonList.GuiSlideEntry(157, I18n.format("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0F, 100.0F, this.field_175336_F.dungeonChance), new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.field_175336_F.useWaterLakes), new GuiPageButtonList.GuiSlideEntry(158, I18n.format("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0F, 100.0F, this.field_175336_F.waterLakeChance), new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.field_175336_F.useLavaLakes), new GuiPageButtonList.GuiSlideEntry(159, I18n.format("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0F, 100.0F, this.field_175336_F.lavaLakeChance), new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.field_175336_F.useLavaOceans), new GuiPageButtonList.GuiSlideEntry(162, I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0F, 37.0F, this.field_175336_F.fixedBiome), new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0F, 8.0F, this.field_175336_F.biomeSize), new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0F, 5.0F, this.field_175336_F.riverSize) };
/* 108 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry1 = { new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.dirtSize), new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.dirtCount), new GuiPageButtonList.GuiSlideEntry(167, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMinHeight), new GuiPageButtonList.GuiSlideEntry(168, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMaxHeight), new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.gravelSize), new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.gravelCount), new GuiPageButtonList.GuiSlideEntry(171, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMinHeight), new GuiPageButtonList.GuiSlideEntry(172, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMaxHeight), new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.graniteSize), new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.graniteCount), new GuiPageButtonList.GuiSlideEntry(175, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMinHeight), new GuiPageButtonList.GuiSlideEntry(176, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMaxHeight), new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.dioriteSize), new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.dioriteCount), new GuiPageButtonList.GuiSlideEntry(179, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMinHeight), new GuiPageButtonList.GuiSlideEntry(180, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMaxHeight), new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.andesiteSize), new GuiPageButtonList.GuiSlideEntry(182, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.andesiteCount), new GuiPageButtonList.GuiSlideEntry(183, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMinHeight), new GuiPageButtonList.GuiSlideEntry(184, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMaxHeight), new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.coalSize), new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.coalCount), new GuiPageButtonList.GuiSlideEntry(187, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.coalMinHeight), new GuiPageButtonList.GuiSlideEntry(189, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.coalMaxHeight), new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.ironSize), new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.ironCount), new GuiPageButtonList.GuiSlideEntry(192, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.ironMinHeight), new GuiPageButtonList.GuiSlideEntry(193, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.ironMaxHeight), new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.goldSize), new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.goldCount), new GuiPageButtonList.GuiSlideEntry(196, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.goldMinHeight), new GuiPageButtonList.GuiSlideEntry(197, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.goldMaxHeight), new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.redstoneSize), new GuiPageButtonList.GuiSlideEntry(199, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.redstoneCount), new GuiPageButtonList.GuiSlideEntry(200, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMinHeight), new GuiPageButtonList.GuiSlideEntry(201, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMaxHeight), new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.diamondSize), new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.diamondCount), new GuiPageButtonList.GuiSlideEntry(204, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMinHeight), new GuiPageButtonList.GuiSlideEntry(205, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMaxHeight), new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name", new Object[0]), false), new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.lapisSize), new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.lapisCount), new GuiPageButtonList.GuiSlideEntry(208, I18n.format("createWorld.customize.custom.center", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.lapisCenterHeight), new GuiPageButtonList.GuiSlideEntry(209, I18n.format("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.lapisSpread) };
/* 109 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry2 = { new GuiPageButtonList.GuiSlideEntry(100, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(101, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleY), new GuiPageButtonList.GuiSlideEntry(102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(103, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01F, 20.0F, this.field_175336_F.depthNoiseScaleExponent), new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0F, 25.0F, this.field_175336_F.baseSize), new GuiPageButtonList.GuiSlideEntry(107, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0F, 6000.0F, this.field_175336_F.coordinateScale), new GuiPageButtonList.GuiSlideEntry(108, I18n.format("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0F, 6000.0F, this.field_175336_F.heightScale), new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01F, 50.0F, this.field_175336_F.stretchY), new GuiPageButtonList.GuiSlideEntry(110, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.upperLimitScale), new GuiPageButtonList.GuiSlideEntry(111, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.lowerLimitScale), new GuiPageButtonList.GuiSlideEntry(112, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0F, 20.0F, this.field_175336_F.biomeDepthWeight), new GuiPageButtonList.GuiSlideEntry(113, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0F, 20.0F, this.field_175336_F.biomeDepthOffset), new GuiPageButtonList.GuiSlideEntry(114, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0F, 20.0F, this.field_175336_F.biomeScaleWeight), new GuiPageButtonList.GuiSlideEntry(115, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0F, 20.0F, this.field_175336_F.biomeScaleOffset) };
/* 110 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry3 = { new GuiPageButtonList.GuiLabelEntry(400, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleX) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(401, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleY) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(402, String.valueOf(I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleZ) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(403, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleX) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(404, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleZ) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(405, String.valueOf(I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleExponent) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(406, String.valueOf(I18n.format("createWorld.customize.custom.baseSize", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.baseSize) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(407, String.valueOf(I18n.format("createWorld.customize.custom.coordinateScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.coordinateScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(408, String.valueOf(I18n.format("createWorld.customize.custom.heightScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.heightScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(409, String.valueOf(I18n.format("createWorld.customize.custom.stretchY", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.stretchY) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(410, String.valueOf(I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.upperLimitScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(411, String.valueOf(I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.lowerLimitScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(412, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeDepthWeight) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(413, String.valueOf(I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeDepthOffset) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(414, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeScaleWeight) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(415, String.valueOf(I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0])) + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeScaleOffset) }), false, this.field_175332_D) };
/* 111 */     this.field_175349_r = new GuiPageButtonList(this.mc, width, height, 32, height - 32, 25, this, new GuiPageButtonList.GuiListEntry[][] { aguipagebuttonlist$guilistentry, aguipagebuttonlist$guilistentry1, aguipagebuttonlist$guilistentry2, aguipagebuttonlist$guilistentry3 });
/*     */     
/* 113 */     for (int i = 0; i < 4; i++) {
/* 114 */       this.field_175342_h[i] = I18n.format("createWorld.customize.custom.page" + i, new Object[0]);
/*     */     }
/*     */     
/* 117 */     func_175328_i();
/*     */   }
/*     */   
/*     */   public String func_175323_a() {
/* 121 */     return this.field_175336_F.toString().replace("\n", "");
/*     */   }
/*     */   
/*     */   public void func_175324_a(String p_175324_1_) {
/* 125 */     if (p_175324_1_ != null && p_175324_1_.length() != 0) {
/* 126 */       this.field_175336_F = ChunkProviderSettings.Factory.jsonToFactory(p_175324_1_);
/*     */     } else {
/* 128 */       this.field_175336_F = new ChunkProviderSettings.Factory();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_175319_a(int p_175319_1_, String p_175319_2_) {
/* 133 */     float f = 0.0F;
/*     */     
/*     */     try {
/* 136 */       f = Float.parseFloat(p_175319_2_);
/* 137 */     } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */     
/* 141 */     float f1 = 0.0F;
/*     */     
/* 143 */     switch (p_175319_1_) {
/*     */       case 132:
/* 145 */         f1 = this.field_175336_F.mainNoiseScaleX = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*     */         break;
/*     */       
/*     */       case 133:
/* 149 */         f1 = this.field_175336_F.mainNoiseScaleY = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*     */         break;
/*     */       
/*     */       case 134:
/* 153 */         f1 = this.field_175336_F.mainNoiseScaleZ = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*     */         break;
/*     */       
/*     */       case 135:
/* 157 */         f1 = this.field_175336_F.depthNoiseScaleX = MathHelper.clamp_float(f, 1.0F, 2000.0F);
/*     */         break;
/*     */       
/*     */       case 136:
/* 161 */         f1 = this.field_175336_F.depthNoiseScaleZ = MathHelper.clamp_float(f, 1.0F, 2000.0F);
/*     */         break;
/*     */       
/*     */       case 137:
/* 165 */         f1 = this.field_175336_F.depthNoiseScaleExponent = MathHelper.clamp_float(f, 0.01F, 20.0F);
/*     */         break;
/*     */       
/*     */       case 138:
/* 169 */         f1 = this.field_175336_F.baseSize = MathHelper.clamp_float(f, 1.0F, 25.0F);
/*     */         break;
/*     */       
/*     */       case 139:
/* 173 */         f1 = this.field_175336_F.coordinateScale = MathHelper.clamp_float(f, 1.0F, 6000.0F);
/*     */         break;
/*     */       
/*     */       case 140:
/* 177 */         f1 = this.field_175336_F.heightScale = MathHelper.clamp_float(f, 1.0F, 6000.0F);
/*     */         break;
/*     */       
/*     */       case 141:
/* 181 */         f1 = this.field_175336_F.stretchY = MathHelper.clamp_float(f, 0.01F, 50.0F);
/*     */         break;
/*     */       
/*     */       case 142:
/* 185 */         f1 = this.field_175336_F.upperLimitScale = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*     */         break;
/*     */       
/*     */       case 143:
/* 189 */         f1 = this.field_175336_F.lowerLimitScale = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*     */         break;
/*     */       
/*     */       case 144:
/* 193 */         f1 = this.field_175336_F.biomeDepthWeight = MathHelper.clamp_float(f, 1.0F, 20.0F);
/*     */         break;
/*     */       
/*     */       case 145:
/* 197 */         f1 = this.field_175336_F.biomeDepthOffset = MathHelper.clamp_float(f, 0.0F, 20.0F);
/*     */         break;
/*     */       
/*     */       case 146:
/* 201 */         f1 = this.field_175336_F.biomeScaleWeight = MathHelper.clamp_float(f, 1.0F, 20.0F);
/*     */         break;
/*     */       
/*     */       case 147:
/* 205 */         f1 = this.field_175336_F.biomeScaleOffset = MathHelper.clamp_float(f, 0.0F, 20.0F);
/*     */         break;
/*     */     } 
/* 208 */     if (f1 != f && f != 0.0F) {
/* 209 */       ((GuiTextField)this.field_175349_r.func_178061_c(p_175319_1_)).setText(func_175330_b(p_175319_1_, f1));
/*     */     }
/*     */     
/* 212 */     ((GuiSlider)this.field_175349_r.func_178061_c(p_175319_1_ - 132 + 100)).func_175218_a(f1, false);
/*     */     
/* 214 */     if (!this.field_175336_F.equals(this.field_175334_E)) {
/* 215 */       func_181031_a(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void func_181031_a(boolean p_181031_1_) {
/* 220 */     this.field_175338_A = p_181031_1_;
/* 221 */     this.field_175346_u.enabled = p_181031_1_;
/*     */   }
/*     */   
/*     */   public String getText(int id, String name, float value) {
/* 225 */     return String.valueOf(name) + ": " + func_175330_b(id, value);
/*     */   }
/*     */   
/*     */   private String func_175330_b(int p_175330_1_, float p_175330_2_) {
/* 229 */     switch (p_175330_1_) {
/*     */       case 100:
/*     */       case 101:
/*     */       case 102:
/*     */       case 103:
/*     */       case 104:
/*     */       case 107:
/*     */       case 108:
/*     */       case 110:
/*     */       case 111:
/*     */       case 132:
/*     */       case 133:
/*     */       case 134:
/*     */       case 135:
/*     */       case 136:
/*     */       case 139:
/*     */       case 140:
/*     */       case 142:
/*     */       case 143:
/* 248 */         return String.format("%5.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*     */       
/*     */       case 105:
/*     */       case 106:
/*     */       case 109:
/*     */       case 112:
/*     */       case 113:
/*     */       case 114:
/*     */       case 115:
/*     */       case 137:
/*     */       case 138:
/*     */       case 141:
/*     */       case 144:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/* 264 */         return String.format("%2.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 297 */         return String.format("%d", new Object[] { Integer.valueOf((int)p_175330_2_) });
/*     */       case 162:
/*     */         break;
/* 300 */     }  if (p_175330_2_ < 0.0F)
/* 301 */       return I18n.format("gui.all", new Object[0]); 
/* 302 */     if ((int)p_175330_2_ >= BiomeGenBase.hell.biomeID) {
/* 303 */       BiomeGenBase biomegenbase1 = BiomeGenBase.getBiomeGenArray()[(int)p_175330_2_ + 2];
/* 304 */       return (biomegenbase1 != null) ? biomegenbase1.biomeName : "?";
/*     */     } 
/* 306 */     BiomeGenBase biomegenbase = BiomeGenBase.getBiomeGenArray()[(int)p_175330_2_];
/* 307 */     return (biomegenbase != null) ? biomegenbase.biomeName : "?";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
/* 313 */     switch (p_175321_1_) {
/*     */       case 148:
/* 315 */         this.field_175336_F.useCaves = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 149:
/* 319 */         this.field_175336_F.useDungeons = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 150:
/* 323 */         this.field_175336_F.useStrongholds = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 151:
/* 327 */         this.field_175336_F.useVillages = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 152:
/* 331 */         this.field_175336_F.useMineShafts = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 153:
/* 335 */         this.field_175336_F.useTemples = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 154:
/* 339 */         this.field_175336_F.useRavines = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 155:
/* 343 */         this.field_175336_F.useWaterLakes = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 156:
/* 347 */         this.field_175336_F.useLavaLakes = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 161:
/* 351 */         this.field_175336_F.useLavaOceans = p_175321_2_;
/*     */         break;
/*     */       
/*     */       case 210:
/* 355 */         this.field_175336_F.useMonuments = p_175321_2_;
/*     */         break;
/*     */     } 
/* 358 */     if (!this.field_175336_F.equals(this.field_175334_E)) {
/* 359 */       func_181031_a(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onTick(int id, float value) {
/* 364 */     switch (id) {
/*     */       case 100:
/* 366 */         this.field_175336_F.mainNoiseScaleX = value;
/*     */         break;
/*     */       
/*     */       case 101:
/* 370 */         this.field_175336_F.mainNoiseScaleY = value;
/*     */         break;
/*     */       
/*     */       case 102:
/* 374 */         this.field_175336_F.mainNoiseScaleZ = value;
/*     */         break;
/*     */       
/*     */       case 103:
/* 378 */         this.field_175336_F.depthNoiseScaleX = value;
/*     */         break;
/*     */       
/*     */       case 104:
/* 382 */         this.field_175336_F.depthNoiseScaleZ = value;
/*     */         break;
/*     */       
/*     */       case 105:
/* 386 */         this.field_175336_F.depthNoiseScaleExponent = value;
/*     */         break;
/*     */       
/*     */       case 106:
/* 390 */         this.field_175336_F.baseSize = value;
/*     */         break;
/*     */       
/*     */       case 107:
/* 394 */         this.field_175336_F.coordinateScale = value;
/*     */         break;
/*     */       
/*     */       case 108:
/* 398 */         this.field_175336_F.heightScale = value;
/*     */         break;
/*     */       
/*     */       case 109:
/* 402 */         this.field_175336_F.stretchY = value;
/*     */         break;
/*     */       
/*     */       case 110:
/* 406 */         this.field_175336_F.upperLimitScale = value;
/*     */         break;
/*     */       
/*     */       case 111:
/* 410 */         this.field_175336_F.lowerLimitScale = value;
/*     */         break;
/*     */       
/*     */       case 112:
/* 414 */         this.field_175336_F.biomeDepthWeight = value;
/*     */         break;
/*     */       
/*     */       case 113:
/* 418 */         this.field_175336_F.biomeDepthOffset = value;
/*     */         break;
/*     */       
/*     */       case 114:
/* 422 */         this.field_175336_F.biomeScaleWeight = value;
/*     */         break;
/*     */       
/*     */       case 115:
/* 426 */         this.field_175336_F.biomeScaleOffset = value;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 157:
/* 475 */         this.field_175336_F.dungeonChance = (int)value;
/*     */         break;
/*     */       
/*     */       case 158:
/* 479 */         this.field_175336_F.waterLakeChance = (int)value;
/*     */         break;
/*     */       
/*     */       case 159:
/* 483 */         this.field_175336_F.lavaLakeChance = (int)value;
/*     */         break;
/*     */       
/*     */       case 160:
/* 487 */         this.field_175336_F.seaLevel = (int)value;
/*     */         break;
/*     */       
/*     */       case 162:
/* 491 */         this.field_175336_F.fixedBiome = (int)value;
/*     */         break;
/*     */       
/*     */       case 163:
/* 495 */         this.field_175336_F.biomeSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 164:
/* 499 */         this.field_175336_F.riverSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 165:
/* 503 */         this.field_175336_F.dirtSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 166:
/* 507 */         this.field_175336_F.dirtCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 167:
/* 511 */         this.field_175336_F.dirtMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 168:
/* 515 */         this.field_175336_F.dirtMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 169:
/* 519 */         this.field_175336_F.gravelSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 170:
/* 523 */         this.field_175336_F.gravelCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 171:
/* 527 */         this.field_175336_F.gravelMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 172:
/* 531 */         this.field_175336_F.gravelMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 173:
/* 535 */         this.field_175336_F.graniteSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 174:
/* 539 */         this.field_175336_F.graniteCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 175:
/* 543 */         this.field_175336_F.graniteMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 176:
/* 547 */         this.field_175336_F.graniteMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 177:
/* 551 */         this.field_175336_F.dioriteSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 178:
/* 555 */         this.field_175336_F.dioriteCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 179:
/* 559 */         this.field_175336_F.dioriteMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 180:
/* 563 */         this.field_175336_F.dioriteMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 181:
/* 567 */         this.field_175336_F.andesiteSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 182:
/* 571 */         this.field_175336_F.andesiteCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 183:
/* 575 */         this.field_175336_F.andesiteMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 184:
/* 579 */         this.field_175336_F.andesiteMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 185:
/* 583 */         this.field_175336_F.coalSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 186:
/* 587 */         this.field_175336_F.coalCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 187:
/* 591 */         this.field_175336_F.coalMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 189:
/* 595 */         this.field_175336_F.coalMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 190:
/* 599 */         this.field_175336_F.ironSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 191:
/* 603 */         this.field_175336_F.ironCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 192:
/* 607 */         this.field_175336_F.ironMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 193:
/* 611 */         this.field_175336_F.ironMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 194:
/* 615 */         this.field_175336_F.goldSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 195:
/* 619 */         this.field_175336_F.goldCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 196:
/* 623 */         this.field_175336_F.goldMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 197:
/* 627 */         this.field_175336_F.goldMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 198:
/* 631 */         this.field_175336_F.redstoneSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 199:
/* 635 */         this.field_175336_F.redstoneCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 200:
/* 639 */         this.field_175336_F.redstoneMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 201:
/* 643 */         this.field_175336_F.redstoneMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 202:
/* 647 */         this.field_175336_F.diamondSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 203:
/* 651 */         this.field_175336_F.diamondCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 204:
/* 655 */         this.field_175336_F.diamondMinHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 205:
/* 659 */         this.field_175336_F.diamondMaxHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 206:
/* 663 */         this.field_175336_F.lapisSize = (int)value;
/*     */         break;
/*     */       
/*     */       case 207:
/* 667 */         this.field_175336_F.lapisCount = (int)value;
/*     */         break;
/*     */       
/*     */       case 208:
/* 671 */         this.field_175336_F.lapisCenterHeight = (int)value;
/*     */         break;
/*     */       
/*     */       case 209:
/* 675 */         this.field_175336_F.lapisSpread = (int)value;
/*     */         break;
/*     */     } 
/* 678 */     if (id >= 100 && id < 116) {
/* 679 */       Gui gui = this.field_175349_r.func_178061_c(id - 100 + 132);
/*     */       
/* 681 */       if (gui != null) {
/* 682 */         ((GuiTextField)gui).setText(func_175330_b(id, value));
/*     */       }
/*     */     } 
/*     */     
/* 686 */     if (!this.field_175336_F.equals(this.field_175334_E)) {
/* 687 */       func_181031_a(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 695 */     if (button.enabled) {
/* 696 */       int i; switch (button.id) {
/*     */         case 300:
/* 698 */           this.field_175343_i.chunkProviderSettingsJson = this.field_175336_F.toString();
/* 699 */           this.mc.displayGuiScreen(this.field_175343_i);
/*     */           break;
/*     */         
/*     */         case 301:
/* 703 */           for (i = 0; i < this.field_175349_r.getSize(); i++) {
/* 704 */             GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.field_175349_r.getListEntry(i);
/* 705 */             Gui gui = guipagebuttonlist$guientry.func_178022_a();
/*     */             
/* 707 */             if (gui instanceof GuiButton) {
/* 708 */               GuiButton guibutton = (GuiButton)gui;
/*     */               
/* 710 */               if (guibutton instanceof GuiSlider) {
/* 711 */                 float f = ((GuiSlider)guibutton).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/* 712 */                 ((GuiSlider)guibutton).func_175219_a(MathHelper.clamp_float(f, 0.0F, 1.0F));
/* 713 */               } else if (guibutton instanceof GuiListButton) {
/* 714 */                 ((GuiListButton)guibutton).func_175212_b(this.random.nextBoolean());
/*     */               } 
/*     */             } 
/*     */             
/* 718 */             Gui gui1 = guipagebuttonlist$guientry.func_178021_b();
/*     */             
/* 720 */             if (gui1 instanceof GuiButton) {
/* 721 */               GuiButton guibutton1 = (GuiButton)gui1;
/*     */               
/* 723 */               if (guibutton1 instanceof GuiSlider) {
/* 724 */                 float f1 = ((GuiSlider)guibutton1).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/* 725 */                 ((GuiSlider)guibutton1).func_175219_a(MathHelper.clamp_float(f1, 0.0F, 1.0F));
/* 726 */               } else if (guibutton1 instanceof GuiListButton) {
/* 727 */                 ((GuiListButton)guibutton1).func_175212_b(this.random.nextBoolean());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           return;
/*     */ 
/*     */         
/*     */         case 302:
/* 735 */           this.field_175349_r.func_178071_h();
/* 736 */           func_175328_i();
/*     */           break;
/*     */         
/*     */         case 303:
/* 740 */           this.field_175349_r.func_178064_i();
/* 741 */           func_175328_i();
/*     */           break;
/*     */         
/*     */         case 304:
/* 745 */           if (this.field_175338_A) {
/* 746 */             func_175322_b(304);
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 305:
/* 752 */           this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
/*     */           break;
/*     */         
/*     */         case 306:
/* 756 */           func_175331_h();
/*     */           break;
/*     */         
/*     */         case 307:
/* 760 */           this.field_175339_B = 0;
/* 761 */           func_175331_h();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void func_175326_g() {
/* 767 */     this.field_175336_F.func_177863_a();
/* 768 */     func_175325_f();
/* 769 */     func_181031_a(false);
/*     */   }
/*     */   
/*     */   private void func_175322_b(int p_175322_1_) {
/* 773 */     this.field_175339_B = p_175322_1_;
/* 774 */     func_175329_a(true);
/*     */   }
/*     */   
/*     */   private void func_175331_h() throws IOException {
/* 778 */     switch (this.field_175339_B) {
/*     */       case 300:
/* 780 */         actionPerformed((GuiListButton)this.field_175349_r.func_178061_c(300));
/*     */         break;
/*     */       
/*     */       case 304:
/* 784 */         func_175326_g();
/*     */         break;
/*     */     } 
/* 787 */     this.field_175339_B = 0;
/* 788 */     this.field_175340_C = true;
/* 789 */     func_175329_a(false);
/*     */   }
/*     */   
/*     */   private void func_175329_a(boolean p_175329_1_) {
/* 793 */     this.field_175352_x.visible = p_175329_1_;
/* 794 */     this.field_175351_y.visible = p_175329_1_;
/* 795 */     this.field_175347_t.enabled = !p_175329_1_;
/* 796 */     this.field_175348_s.enabled = !p_175329_1_;
/* 797 */     this.field_175345_v.enabled = !p_175329_1_;
/* 798 */     this.field_175344_w.enabled = !p_175329_1_;
/* 799 */     this.field_175346_u.enabled = (this.field_175338_A && !p_175329_1_);
/* 800 */     this.field_175350_z.enabled = !p_175329_1_;
/* 801 */     this.field_175349_r.func_181155_a(!p_175329_1_);
/*     */   }
/*     */   
/*     */   private void func_175328_i() {
/* 805 */     this.field_175345_v.enabled = (this.field_175349_r.func_178059_e() != 0);
/* 806 */     this.field_175344_w.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
/* 807 */     this.field_175333_f = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.field_175349_r.func_178059_e() + 1), Integer.valueOf(this.field_175349_r.func_178057_f()) });
/* 808 */     this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
/* 809 */     this.field_175347_t.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 817 */     super.keyTyped(typedChar, keyCode);
/*     */     
/* 819 */     if (this.field_175339_B == 0) {
/* 820 */       switch (keyCode) {
/*     */         case 200:
/* 822 */           func_175327_a(1.0F);
/*     */           return;
/*     */         
/*     */         case 208:
/* 826 */           func_175327_a(-1.0F);
/*     */           return;
/*     */       } 
/*     */       
/* 830 */       this.field_175349_r.func_178062_a(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175327_a(float p_175327_1_) {
/* 836 */     Gui gui = this.field_175349_r.func_178056_g();
/*     */     
/* 838 */     if (gui instanceof GuiTextField) {
/* 839 */       float f = p_175327_1_;
/*     */       
/* 841 */       if (GuiScreen.isShiftKeyDown()) {
/* 842 */         f = p_175327_1_ * 0.1F;
/*     */         
/* 844 */         if (GuiScreen.isCtrlKeyDown()) {
/* 845 */           f *= 0.1F;
/*     */         }
/* 847 */       } else if (GuiScreen.isCtrlKeyDown()) {
/* 848 */         f = p_175327_1_ * 10.0F;
/*     */         
/* 850 */         if (GuiScreen.isAltKeyDown()) {
/* 851 */           f *= 10.0F;
/*     */         }
/*     */       } 
/*     */       
/* 855 */       GuiTextField guitextfield = (GuiTextField)gui;
/* 856 */       Float f1 = Floats.tryParse(guitextfield.getText());
/*     */       
/* 858 */       if (f1 != null) {
/* 859 */         f1 = Float.valueOf(f1.floatValue() + f);
/* 860 */         int i = guitextfield.getId();
/* 861 */         String s = func_175330_b(guitextfield.getId(), f1.floatValue());
/* 862 */         guitextfield.setText(s);
/* 863 */         func_175319_a(i, s);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 872 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 874 */     if (this.field_175339_B == 0 && !this.field_175340_C) {
/* 875 */       this.field_175349_r.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 883 */     super.mouseReleased(mouseX, mouseY, state);
/*     */     
/* 885 */     if (this.field_175340_C) {
/* 886 */       this.field_175340_C = false;
/* 887 */     } else if (this.field_175339_B == 0) {
/* 888 */       this.field_175349_r.mouseReleased(mouseX, mouseY, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 896 */     drawDefaultBackground();
/* 897 */     this.field_175349_r.drawScreen(mouseX, mouseY, partialTicks);
/* 898 */     drawCenteredString(this.fontRendererObj, this.field_175341_a, width / 2, 2, 16777215);
/* 899 */     drawCenteredString(this.fontRendererObj, this.field_175333_f, width / 2, 12, 16777215);
/* 900 */     drawCenteredString(this.fontRendererObj, this.field_175335_g, width / 2, 22, 16777215);
/* 901 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 903 */     if (this.field_175339_B != 0) {
/* 904 */       drawRect(0, 0, width, height, -2147483648);
/* 905 */       drawHorizontalLine(width / 2 - 91, width / 2 + 90, 99, -2039584);
/* 906 */       drawHorizontalLine(width / 2 - 91, width / 2 + 90, 185, -6250336);
/* 907 */       drawVerticalLine(width / 2 - 91, 99, 185, -2039584);
/* 908 */       drawVerticalLine(width / 2 + 90, 99, 185, -6250336);
/* 909 */       float f = 85.0F;
/* 910 */       float f1 = 180.0F;
/* 911 */       GlStateManager.disableLighting();
/* 912 */       GlStateManager.disableFog();
/* 913 */       Tessellator tessellator = Tessellator.getInstance();
/* 914 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 915 */       this.mc.getTextureManager().bindTexture(optionsBackground);
/* 916 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 917 */       float f2 = 32.0F;
/* 918 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 919 */       worldrenderer.pos((width / 2 - 90), 185.0D, 0.0D).tex(0.0D, 2.65625D).color(64, 64, 64, 64).endVertex();
/* 920 */       worldrenderer.pos((width / 2 + 90), 185.0D, 0.0D).tex(5.625D, 2.65625D).color(64, 64, 64, 64).endVertex();
/* 921 */       worldrenderer.pos((width / 2 + 90), 100.0D, 0.0D).tex(5.625D, 0.0D).color(64, 64, 64, 64).endVertex();
/* 922 */       worldrenderer.pos((width / 2 - 90), 100.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 64).endVertex();
/* 923 */       tessellator.draw();
/* 924 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]), width / 2, 105, 16777215);
/* 925 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1", new Object[0]), width / 2, 125, 16777215);
/* 926 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2", new Object[0]), width / 2, 135, 16777215);
/* 927 */       this.field_175352_x.drawButton(this.mc, mouseX, mouseY);
/* 928 */       this.field_175351_y.drawButton(this.mc, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiCustomizeWorldScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */