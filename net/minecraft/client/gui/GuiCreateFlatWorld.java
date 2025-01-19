/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
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
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.FlatLayerInfo;
/*     */ 
/*     */ public class GuiCreateFlatWorld
/*     */   extends GuiScreen {
/*     */   private final GuiCreateWorld createWorldGui;
/*  22 */   private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
/*     */   
/*     */   private String flatWorldTitle;
/*     */   
/*     */   private String field_146394_i;
/*     */   
/*     */   private String field_146391_r;
/*     */   
/*     */   private Details createFlatWorldListSlotGui;
/*     */   private GuiButton field_146389_t;
/*     */   private GuiButton field_146388_u;
/*     */   private GuiButton field_146386_v;
/*     */   
/*     */   public GuiCreateFlatWorld(GuiCreateWorld createWorldGuiIn, String p_i1029_2_) {
/*  36 */     this.createWorldGui = createWorldGuiIn;
/*  37 */     func_146383_a(p_i1029_2_);
/*     */   }
/*     */   
/*     */   public String func_146384_e() {
/*  41 */     return this.theFlatGeneratorInfo.toString();
/*     */   }
/*     */   
/*     */   public void func_146383_a(String p_146383_1_) {
/*  45 */     this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  53 */     this.buttonList.clear();
/*  54 */     this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
/*  55 */     this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
/*  56 */     this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
/*  57 */     this.createFlatWorldListSlotGui = new Details();
/*  58 */     this.buttonList.add(this.field_146389_t = new GuiButton(2, width / 2 - 154, height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)"));
/*  59 */     this.buttonList.add(this.field_146388_u = new GuiButton(3, width / 2 - 50, height - 52, 100, 20, String.valueOf(I18n.format("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)"));
/*  60 */     this.buttonList.add(this.field_146386_v = new GuiButton(4, width / 2 - 155, height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
/*  61 */     this.buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  62 */     this.buttonList.add(new GuiButton(5, width / 2 + 5, height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
/*  63 */     this.buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  64 */     this.field_146388_u.visible = false;
/*  65 */     this.theFlatGeneratorInfo.func_82645_d();
/*  66 */     func_146375_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  73 */     super.handleMouseInput();
/*  74 */     this.createFlatWorldListSlotGui.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  81 */     int i = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_148228_k - 1;
/*     */     
/*  83 */     if (button.id == 1) {
/*  84 */       this.mc.displayGuiScreen(this.createWorldGui);
/*  85 */     } else if (button.id == 0) {
/*  86 */       this.createWorldGui.chunkProviderSettingsJson = func_146384_e();
/*  87 */       this.mc.displayGuiScreen(this.createWorldGui);
/*  88 */     } else if (button.id == 5) {
/*  89 */       this.mc.displayGuiScreen(new GuiFlatPresets(this));
/*  90 */     } else if (button.id == 4 && func_146382_i()) {
/*  91 */       this.theFlatGeneratorInfo.getFlatLayers().remove(i);
/*  92 */       this.createFlatWorldListSlotGui.field_148228_k = Math.min(this.createFlatWorldListSlotGui.field_148228_k, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
/*     */     } 
/*     */     
/*  95 */     this.theFlatGeneratorInfo.func_82645_d();
/*  96 */     func_146375_g();
/*     */   }
/*     */   
/*     */   public void func_146375_g() {
/* 100 */     boolean flag = func_146382_i();
/* 101 */     this.field_146386_v.enabled = flag;
/* 102 */     this.field_146388_u.enabled = flag;
/* 103 */     this.field_146388_u.enabled = false;
/* 104 */     this.field_146389_t.enabled = false;
/*     */   }
/*     */   
/*     */   private boolean func_146382_i() {
/* 108 */     return (this.createFlatWorldListSlotGui.field_148228_k > -1 && this.createFlatWorldListSlotGui.field_148228_k < this.theFlatGeneratorInfo.getFlatLayers().size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 115 */     drawDefaultBackground();
/* 116 */     this.createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
/* 117 */     drawCenteredString(this.fontRendererObj, this.flatWorldTitle, width / 2, 8, 16777215);
/* 118 */     int i = width / 2 - 92 - 16;
/* 119 */     drawString(this.fontRendererObj, this.field_146394_i, i, 32, 16777215);
/* 120 */     drawString(this.fontRendererObj, this.field_146391_r, i + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
/* 121 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class Details extends GuiSlot {
/* 125 */     public int field_148228_k = -1;
/*     */     
/*     */     public Details() {
/* 128 */       super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.width, GuiCreateFlatWorld.height, 43, GuiCreateFlatWorld.height - 60, 24);
/*     */     }
/*     */     
/*     */     private void func_148225_a(int p_148225_1_, int p_148225_2_, ItemStack p_148225_3_) {
/* 132 */       func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
/* 133 */       GlStateManager.enableRescaleNormal();
/*     */       
/* 135 */       if (p_148225_3_ != null && p_148225_3_.getItem() != null) {
/* 136 */         RenderHelper.enableGUIStandardItemLighting();
/* 137 */         GuiCreateFlatWorld.this.itemRender.renderItemIntoGUI(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
/* 138 */         RenderHelper.disableStandardItemLighting();
/*     */       } 
/*     */       
/* 141 */       GlStateManager.disableRescaleNormal();
/*     */     }
/*     */     
/*     */     private void func_148226_e(int p_148226_1_, int p_148226_2_) {
/* 145 */       func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
/*     */     }
/*     */     
/*     */     private void func_148224_c(int p_148224_1_, int p_148224_2_, int p_148224_3_, int p_148224_4_) {
/* 149 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 150 */       this.mc.getTextureManager().bindTexture(Gui.statIcons);
/* 151 */       float f = 0.0078125F;
/* 152 */       float f1 = 0.0078125F;
/* 153 */       int i = 18;
/* 154 */       int j = 18;
/* 155 */       Tessellator tessellator = Tessellator.getInstance();
/* 156 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 157 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 158 */       worldrenderer.pos((p_148224_1_ + 0), (p_148224_2_ + 18), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 0) * 0.0078125F), ((p_148224_4_ + 18) * 0.0078125F)).endVertex();
/* 159 */       worldrenderer.pos((p_148224_1_ + 18), (p_148224_2_ + 18), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 18) * 0.0078125F), ((p_148224_4_ + 18) * 0.0078125F)).endVertex();
/* 160 */       worldrenderer.pos((p_148224_1_ + 18), (p_148224_2_ + 0), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 18) * 0.0078125F), ((p_148224_4_ + 0) * 0.0078125F)).endVertex();
/* 161 */       worldrenderer.pos((p_148224_1_ + 0), (p_148224_2_ + 0), GuiCreateFlatWorld.this.zLevel).tex(((p_148224_3_ + 0) * 0.0078125F), ((p_148224_4_ + 0) * 0.0078125F)).endVertex();
/* 162 */       tessellator.draw();
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 166 */       return GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 170 */       this.field_148228_k = slotIndex;
/* 171 */       GuiCreateFlatWorld.this.func_146375_g();
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 175 */       return (slotIndex == this.field_148228_k);
/*     */     }
/*     */     
/*     */     protected void drawBackground() {}
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/*     */       String s1;
/* 182 */       FlatLayerInfo flatlayerinfo = GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().get(GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - entryID - 1);
/* 183 */       IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
/* 184 */       Block block = iblockstate.getBlock();
/* 185 */       Item item = Item.getItemFromBlock(block);
/* 186 */       ItemStack itemstack = (block != Blocks.air && item != null) ? new ItemStack(item, 1, block.getMetaFromState(iblockstate)) : null;
/* 187 */       String s = (itemstack == null) ? "Air" : item.getItemStackDisplayName(itemstack);
/*     */       
/* 189 */       if (item == null) {
/* 190 */         if (block != Blocks.water && block != Blocks.flowing_water) {
/* 191 */           if (block == Blocks.lava || block == Blocks.flowing_lava) {
/* 192 */             item = Items.lava_bucket;
/*     */           }
/*     */         } else {
/* 195 */           item = Items.water_bucket;
/*     */         } 
/*     */         
/* 198 */         if (item != null) {
/* 199 */           itemstack = new ItemStack(item, 1, block.getMetaFromState(iblockstate));
/* 200 */           s = block.getLocalizedName();
/*     */         } 
/*     */       } 
/*     */       
/* 204 */       func_148225_a(p_180791_2_, p_180791_3_, itemstack);
/* 205 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 16777215);
/*     */ 
/*     */       
/* 208 */       if (entryID == 0) {
/* 209 */         s1 = I18n.format("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/* 210 */       } else if (entryID == GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - 1) {
/* 211 */         s1 = I18n.format("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       } else {
/* 213 */         s1 = I18n.format("createWorld.customize.flat.layer", new Object[] { Integer.valueOf(flatlayerinfo.getLayerCount()) });
/*     */       } 
/*     */       
/* 216 */       GuiCreateFlatWorld.this.fontRendererObj.drawString(s1, p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(s1), p_180791_3_ + 3, 16777215);
/*     */     }
/*     */     
/*     */     protected int getScrollBarX() {
/* 220 */       return this.width - 70;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiCreateFlatWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */