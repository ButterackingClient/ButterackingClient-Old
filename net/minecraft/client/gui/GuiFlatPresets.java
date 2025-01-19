/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.FlatLayerInfo;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiFlatPresets
/*     */   extends GuiScreen {
/*  27 */   private static final List<LayerItem> FLAT_WORLD_PRESETS = Lists.newArrayList();
/*     */   
/*     */   private final GuiCreateFlatWorld parentScreen;
/*     */   
/*     */   private String presetsTitle;
/*     */   
/*     */   private String presetsShare;
/*     */   
/*     */   private String field_146436_r;
/*     */   private ListSlot field_146435_s;
/*     */   private GuiButton field_146434_t;
/*     */   private GuiTextField field_146433_u;
/*     */   
/*     */   public GuiFlatPresets(GuiCreateFlatWorld p_i46318_1_) {
/*  41 */     this.parentScreen = p_i46318_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  49 */     this.buttonList.clear();
/*  50 */     Keyboard.enableRepeatEvents(true);
/*  51 */     this.presetsTitle = I18n.format("createWorld.customize.presets.title", new Object[0]);
/*  52 */     this.presetsShare = I18n.format("createWorld.customize.presets.share", new Object[0]);
/*  53 */     this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
/*  54 */     this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, width - 100, 20);
/*  55 */     this.field_146435_s = new ListSlot();
/*  56 */     this.field_146433_u.setMaxStringLength(1230);
/*  57 */     this.field_146433_u.setText(this.parentScreen.func_146384_e());
/*  58 */     this.buttonList.add(this.field_146434_t = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
/*  59 */     this.buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  60 */     func_146426_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  67 */     super.handleMouseInput();
/*  68 */     this.field_146435_s.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  75 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  82 */     this.field_146433_u.mouseClicked(mouseX, mouseY, mouseButton);
/*  83 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  91 */     if (!this.field_146433_u.textboxKeyTyped(typedChar, keyCode)) {
/*  92 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 100 */     if (button.id == 0 && func_146430_p()) {
/* 101 */       this.parentScreen.func_146383_a(this.field_146433_u.getText());
/* 102 */       this.mc.displayGuiScreen(this.parentScreen);
/* 103 */     } else if (button.id == 1) {
/* 104 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 112 */     drawDefaultBackground();
/* 113 */     this.field_146435_s.drawScreen(mouseX, mouseY, partialTicks);
/* 114 */     drawCenteredString(this.fontRendererObj, this.presetsTitle, width / 2, 8, 16777215);
/* 115 */     drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
/* 116 */     drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
/* 117 */     this.field_146433_u.drawTextBox();
/* 118 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 125 */     this.field_146433_u.updateCursorCounter();
/* 126 */     super.updateScreen();
/*     */   }
/*     */   
/*     */   public void func_146426_g() {
/* 130 */     boolean flag = func_146430_p();
/* 131 */     this.field_146434_t.enabled = flag;
/*     */   }
/*     */   
/*     */   private boolean func_146430_p() {
/* 135 */     return !((this.field_146435_s.field_148175_k <= -1 || this.field_146435_s.field_148175_k >= FLAT_WORLD_PRESETS.size()) && this.field_146433_u.getText().length() <= 1);
/*     */   }
/*     */   
/*     */   private static void func_146425_a(String p_146425_0_, Item p_146425_1_, BiomeGenBase p_146425_2_, FlatLayerInfo... p_146425_3_) {
/* 139 */     func_175354_a(p_146425_0_, p_146425_1_, 0, p_146425_2_, (List<String>)null, p_146425_3_);
/*     */   }
/*     */   
/*     */   private static void func_146421_a(String p_146421_0_, Item p_146421_1_, BiomeGenBase p_146421_2_, List<String> p_146421_3_, FlatLayerInfo... p_146421_4_) {
/* 143 */     func_175354_a(p_146421_0_, p_146421_1_, 0, p_146421_2_, p_146421_3_, p_146421_4_);
/*     */   }
/*     */   
/*     */   private static void func_175354_a(String p_175354_0_, Item p_175354_1_, int p_175354_2_, BiomeGenBase p_175354_3_, List<String> p_175354_4_, FlatLayerInfo... p_175354_5_) {
/* 147 */     FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/*     */     
/* 149 */     for (int i = p_175354_5_.length - 1; i >= 0; i--) {
/* 150 */       flatgeneratorinfo.getFlatLayers().add(p_175354_5_[i]);
/*     */     }
/*     */     
/* 153 */     flatgeneratorinfo.setBiome(p_175354_3_.biomeID);
/* 154 */     flatgeneratorinfo.func_82645_d();
/*     */     
/* 156 */     if (p_175354_4_ != null) {
/* 157 */       for (String s : p_175354_4_) {
/* 158 */         flatgeneratorinfo.getWorldFeatures().put(s, Maps.newHashMap());
/*     */       }
/*     */     }
/*     */     
/* 162 */     FLAT_WORLD_PRESETS.add(new LayerItem(p_175354_1_, p_175354_2_, p_175354_0_, flatgeneratorinfo.toString()));
/*     */   }
/*     */   
/*     */   static {
/* 166 */     func_146421_a("Classic Flat", Item.getItemFromBlock((Block)Blocks.grass), BiomeGenBase.plains, Arrays.asList(new String[] { "village" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock) });
/* 167 */     func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 168 */     func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList(new String[] { "biome_1", "oceanmonument" }, ), new FlatLayerInfo[] { new FlatLayerInfo(90, (Block)Blocks.water), new FlatLayerInfo(5, (Block)Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 169 */     func_175354_a("Overworld", Item.getItemFromBlock((Block)Blocks.tallgrass), BlockTallGrass.EnumType.GRASS.getMeta(), BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 170 */     func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList(new String[] { "village", "biome_1" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 171 */     func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList(new String[] { "village", "biome_1" }, ), new FlatLayerInfo[] { new FlatLayerInfo(1, (Block)Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone) });
/* 172 */     func_146421_a("Desert", Item.getItemFromBlock((Block)Blocks.sand), BiomeGenBase.desert, Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }, ), new FlatLayerInfo[] { new FlatLayerInfo(8, (Block)Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/* 173 */     func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo[] { new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
/*     */   }
/*     */   
/*     */   static class LayerItem {
/*     */     public Item field_148234_a;
/*     */     public int field_179037_b;
/*     */     public String field_148232_b;
/*     */     public String field_148233_c;
/*     */     
/*     */     public LayerItem(Item p_i45518_1_, int p_i45518_2_, String p_i45518_3_, String p_i45518_4_) {
/* 183 */       this.field_148234_a = p_i45518_1_;
/* 184 */       this.field_179037_b = p_i45518_2_;
/* 185 */       this.field_148232_b = p_i45518_3_;
/* 186 */       this.field_148233_c = p_i45518_4_;
/*     */     }
/*     */   }
/*     */   
/*     */   class ListSlot extends GuiSlot {
/* 191 */     public int field_148175_k = -1;
/*     */     
/*     */     public ListSlot() {
/* 194 */       super(GuiFlatPresets.this.mc, GuiFlatPresets.width, GuiFlatPresets.height, 80, GuiFlatPresets.height - 37, 24);
/*     */     }
/*     */     
/*     */     private void func_178054_a(int p_178054_1_, int p_178054_2_, Item p_178054_3_, int p_178054_4_) {
/* 198 */       func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
/* 199 */       GlStateManager.enableRescaleNormal();
/* 200 */       RenderHelper.enableGUIStandardItemLighting();
/* 201 */       GuiFlatPresets.this.itemRender.renderItemIntoGUI(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2, p_178054_2_ + 2);
/* 202 */       RenderHelper.disableStandardItemLighting();
/* 203 */       GlStateManager.disableRescaleNormal();
/*     */     }
/*     */     
/*     */     private void func_148173_e(int p_148173_1_, int p_148173_2_) {
/* 207 */       func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
/*     */     }
/*     */     
/*     */     private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
/* 211 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 212 */       this.mc.getTextureManager().bindTexture(Gui.statIcons);
/* 213 */       float f = 0.0078125F;
/* 214 */       float f1 = 0.0078125F;
/* 215 */       int i = 18;
/* 216 */       int j = 18;
/* 217 */       Tessellator tessellator = Tessellator.getInstance();
/* 218 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 219 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 220 */       worldrenderer.pos((p_148171_1_ + 0), (p_148171_2_ + 18), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 0) * 0.0078125F), ((p_148171_4_ + 18) * 0.0078125F)).endVertex();
/* 221 */       worldrenderer.pos((p_148171_1_ + 18), (p_148171_2_ + 18), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 18) * 0.0078125F), ((p_148171_4_ + 18) * 0.0078125F)).endVertex();
/* 222 */       worldrenderer.pos((p_148171_1_ + 18), (p_148171_2_ + 0), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 18) * 0.0078125F), ((p_148171_4_ + 0) * 0.0078125F)).endVertex();
/* 223 */       worldrenderer.pos((p_148171_1_ + 0), (p_148171_2_ + 0), GuiFlatPresets.this.zLevel).tex(((p_148171_3_ + 0) * 0.0078125F), ((p_148171_4_ + 0) * 0.0078125F)).endVertex();
/* 224 */       tessellator.draw();
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 228 */       return GuiFlatPresets.FLAT_WORLD_PRESETS.size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 232 */       this.field_148175_k = slotIndex;
/* 233 */       GuiFlatPresets.this.func_146426_g();
/* 234 */       GuiFlatPresets.this.field_146433_u.setText((GuiFlatPresets.FLAT_WORLD_PRESETS.get(GuiFlatPresets.this.field_146435_s.field_148175_k)).field_148233_c);
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 238 */       return (slotIndex == this.field_148175_k);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 245 */       GuiFlatPresets.LayerItem guiflatpresets$layeritem = GuiFlatPresets.FLAT_WORLD_PRESETS.get(entryID);
/* 246 */       func_178054_a(p_180791_2_, p_180791_3_, guiflatpresets$layeritem.field_148234_a, guiflatpresets$layeritem.field_179037_b);
/* 247 */       GuiFlatPresets.this.fontRendererObj.drawString(guiflatpresets$layeritem.field_148232_b, p_180791_2_ + 18 + 5, p_180791_3_ + 6, 16777215);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiFlatPresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */