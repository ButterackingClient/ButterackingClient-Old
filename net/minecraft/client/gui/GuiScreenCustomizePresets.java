/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenCustomizePresets
/*     */   extends GuiScreen
/*     */ {
/*  18 */   private static final List<Info> field_175310_f = Lists.newArrayList();
/*     */   private ListPreset field_175311_g;
/*     */   private GuiButton field_175316_h;
/*     */   private GuiTextField field_175317_i;
/*     */   private GuiCustomizeWorldScreen field_175314_r;
/*  23 */   protected String field_175315_a = "Customize World Presets";
/*     */   private String field_175313_s;
/*     */   private String field_175312_t;
/*     */   
/*     */   public GuiScreenCustomizePresets(GuiCustomizeWorldScreen p_i45524_1_) {
/*  28 */     this.field_175314_r = p_i45524_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     this.buttonList.clear();
/*  37 */     Keyboard.enableRepeatEvents(true);
/*  38 */     this.field_175315_a = I18n.format("createWorld.customize.custom.presets.title", new Object[0]);
/*  39 */     this.field_175313_s = I18n.format("createWorld.customize.presets.share", new Object[0]);
/*  40 */     this.field_175312_t = I18n.format("createWorld.customize.presets.list", new Object[0]);
/*  41 */     this.field_175317_i = new GuiTextField(2, this.fontRendererObj, 50, 40, width - 100, 20);
/*  42 */     this.field_175311_g = new ListPreset();
/*  43 */     this.field_175317_i.setMaxStringLength(2000);
/*  44 */     this.field_175317_i.setText(this.field_175314_r.func_175323_a());
/*  45 */     this.buttonList.add(this.field_175316_h = new GuiButton(0, width / 2 - 102, height - 27, 100, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
/*  46 */     this.buttonList.add(new GuiButton(1, width / 2 + 3, height - 27, 100, 20, I18n.format("gui.cancel", new Object[0])));
/*  47 */     func_175304_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  54 */     super.handleMouseInput();
/*  55 */     this.field_175311_g.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  62 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  69 */     this.field_175317_i.mouseClicked(mouseX, mouseY, mouseButton);
/*  70 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  78 */     if (!this.field_175317_i.textboxKeyTyped(typedChar, keyCode)) {
/*  79 */       super.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  87 */     switch (button.id) {
/*     */       case 0:
/*  89 */         this.field_175314_r.func_175324_a(this.field_175317_i.getText());
/*  90 */         this.mc.displayGuiScreen(this.field_175314_r);
/*     */         break;
/*     */       
/*     */       case 1:
/*  94 */         this.mc.displayGuiScreen(this.field_175314_r);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 102 */     drawDefaultBackground();
/* 103 */     this.field_175311_g.drawScreen(mouseX, mouseY, partialTicks);
/* 104 */     drawCenteredString(this.fontRendererObj, this.field_175315_a, width / 2, 8, 16777215);
/* 105 */     drawString(this.fontRendererObj, this.field_175313_s, 50, 30, 10526880);
/* 106 */     drawString(this.fontRendererObj, this.field_175312_t, 50, 70, 10526880);
/* 107 */     this.field_175317_i.drawTextBox();
/* 108 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 115 */     this.field_175317_i.updateCursorCounter();
/* 116 */     super.updateScreen();
/*     */   }
/*     */   
/*     */   public void func_175304_a() {
/* 120 */     this.field_175316_h.enabled = func_175305_g();
/*     */   }
/*     */   
/*     */   private boolean func_175305_g() {
/* 124 */     return !((this.field_175311_g.field_178053_u <= -1 || this.field_175311_g.field_178053_u >= field_175310_f.size()) && this.field_175317_i.getText().length() <= 1);
/*     */   }
/*     */   
/*     */   static {
/* 128 */     ChunkProviderSettings.Factory chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
/* 129 */     ResourceLocation resourcelocation = new ResourceLocation("textures/gui/presets/water.png");
/* 130 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.waterWorld", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 131 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 132 */     resourcelocation = new ResourceLocation("textures/gui/presets/isles.png");
/* 133 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.isleLand", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 134 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 135 */     resourcelocation = new ResourceLocation("textures/gui/presets/delight.png");
/* 136 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveDelight", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 137 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
/* 138 */     resourcelocation = new ResourceLocation("textures/gui/presets/madness.png");
/* 139 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.mountains", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 140 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
/* 141 */     resourcelocation = new ResourceLocation("textures/gui/presets/drought.png");
/* 142 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.drought", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 143 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
/* 144 */     resourcelocation = new ResourceLocation("textures/gui/presets/chaos.png");
/* 145 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveChaos", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/* 146 */     chunkprovidersettings$factory = ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
/* 147 */     resourcelocation = new ResourceLocation("textures/gui/presets/luck.png");
/* 148 */     field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.goodLuck", new Object[0]), resourcelocation, chunkprovidersettings$factory));
/*     */   }
/*     */   
/*     */   static class Info {
/*     */     public String field_178955_a;
/*     */     public ResourceLocation field_178953_b;
/*     */     public ChunkProviderSettings.Factory field_178954_c;
/*     */     
/*     */     public Info(String p_i45523_1_, ResourceLocation p_i45523_2_, ChunkProviderSettings.Factory p_i45523_3_) {
/* 157 */       this.field_178955_a = p_i45523_1_;
/* 158 */       this.field_178953_b = p_i45523_2_;
/* 159 */       this.field_178954_c = p_i45523_3_;
/*     */     }
/*     */   }
/*     */   
/*     */   class ListPreset extends GuiSlot {
/* 164 */     public int field_178053_u = -1;
/*     */     
/*     */     public ListPreset() {
/* 167 */       super(GuiScreenCustomizePresets.this.mc, GuiScreenCustomizePresets.width, GuiScreenCustomizePresets.height, 80, GuiScreenCustomizePresets.height - 32, 38);
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 171 */       return GuiScreenCustomizePresets.field_175310_f.size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 175 */       this.field_178053_u = slotIndex;
/* 176 */       GuiScreenCustomizePresets.this.func_175304_a();
/* 177 */       GuiScreenCustomizePresets.this.field_175317_i.setText((GuiScreenCustomizePresets.field_175310_f.get(GuiScreenCustomizePresets.this.field_175311_g.field_178053_u)).field_178954_c.toString());
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 181 */       return (slotIndex == this.field_178053_u);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */     
/*     */     private void func_178051_a(int p_178051_1_, int p_178051_2_, ResourceLocation p_178051_3_) {
/* 188 */       int i = p_178051_1_ + 5;
/* 189 */       GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ - 1, -2039584);
/* 190 */       GuiScreenCustomizePresets.this.drawHorizontalLine(i - 1, i + 32, p_178051_2_ + 32, -6250336);
/* 191 */       GuiScreenCustomizePresets.this.drawVerticalLine(i - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
/* 192 */       GuiScreenCustomizePresets.this.drawVerticalLine(i + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
/* 193 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 194 */       this.mc.getTextureManager().bindTexture(p_178051_3_);
/* 195 */       int j = 32;
/* 196 */       int k = 32;
/* 197 */       Tessellator tessellator = Tessellator.getInstance();
/* 198 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 199 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 200 */       worldrenderer.pos((i + 0), (p_178051_2_ + 32), 0.0D).tex(0.0D, 1.0D).endVertex();
/* 201 */       worldrenderer.pos((i + 32), (p_178051_2_ + 32), 0.0D).tex(1.0D, 1.0D).endVertex();
/* 202 */       worldrenderer.pos((i + 32), (p_178051_2_ + 0), 0.0D).tex(1.0D, 0.0D).endVertex();
/* 203 */       worldrenderer.pos((i + 0), (p_178051_2_ + 0), 0.0D).tex(0.0D, 0.0D).endVertex();
/* 204 */       tessellator.draw();
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 208 */       GuiScreenCustomizePresets.Info guiscreencustomizepresets$info = GuiScreenCustomizePresets.field_175310_f.get(entryID);
/* 209 */       func_178051_a(p_180791_2_, p_180791_3_, guiscreencustomizepresets$info.field_178953_b);
/* 210 */       GuiScreenCustomizePresets.this.fontRendererObj.drawString(guiscreencustomizepresets$info.field_178955_a, p_180791_2_ + 32 + 10, p_180791_3_ + 14, 16777215);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenCustomizePresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */