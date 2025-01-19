/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.model.ModelBook;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnchantmentNameParts;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEnchantment
/*     */   extends GuiContainer
/*     */ {
/*  31 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static final ModelBook MODEL_BOOK = new ModelBook();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final InventoryPlayer playerInventory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private Random random = new Random();
/*     */   private ContainerEnchantment container;
/*     */   public int field_147073_u;
/*     */   public float field_147071_v;
/*     */   public float field_147069_w;
/*     */   public float field_147082_x;
/*     */   public float field_147081_y;
/*     */   public float field_147080_z;
/*     */   public float field_147076_A;
/*     */   ItemStack field_147077_B;
/*     */   private final IWorldNameable field_175380_I;
/*     */   
/*     */   public GuiEnchantment(InventoryPlayer inventory, World worldIn, IWorldNameable p_i45502_3_) {
/*  64 */     super((Container)new ContainerEnchantment(inventory, worldIn));
/*  65 */     this.playerInventory = inventory;
/*  66 */     this.container = (ContainerEnchantment)this.inventorySlots;
/*  67 */     this.field_175380_I = p_i45502_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  74 */     this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12, 5, 4210752);
/*  75 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  82 */     super.updateScreen();
/*  83 */     func_147068_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  90 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  91 */     int i = (width - this.xSize) / 2;
/*  92 */     int j = (height - this.ySize) / 2;
/*     */     
/*  94 */     for (int k = 0; k < 3; k++) {
/*  95 */       int l = mouseX - i + 60;
/*  96 */       int i1 = mouseY - j + 14 + 19 * k;
/*     */       
/*  98 */       if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && this.container.enchantItem((EntityPlayer)this.mc.thePlayer, k)) {
/*  99 */         this.mc.playerController.sendEnchantPacket(this.container.windowId, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 108 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 109 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 110 */     int i = (width - this.xSize) / 2;
/* 111 */     int j = (height - this.ySize) / 2;
/* 112 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 113 */     GlStateManager.pushMatrix();
/* 114 */     GlStateManager.matrixMode(5889);
/* 115 */     GlStateManager.pushMatrix();
/* 116 */     GlStateManager.loadIdentity();
/* 117 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 118 */     GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
/* 119 */     GlStateManager.translate(-0.34F, 0.23F, 0.0F);
/* 120 */     Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
/* 121 */     float f = 1.0F;
/* 122 */     GlStateManager.matrixMode(5888);
/* 123 */     GlStateManager.loadIdentity();
/* 124 */     RenderHelper.enableStandardItemLighting();
/* 125 */     GlStateManager.translate(0.0F, 3.3F, -16.0F);
/* 126 */     GlStateManager.scale(f, f, f);
/* 127 */     float f1 = 5.0F;
/* 128 */     GlStateManager.scale(f1, f1, f1);
/* 129 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 130 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
/* 131 */     GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 132 */     float f2 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * partialTicks;
/* 133 */     GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
/* 134 */     GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 135 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 136 */     float f3 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.25F;
/* 137 */     float f4 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.75F;
/* 138 */     f3 = (f3 - MathHelper.truncateDoubleToInt(f3)) * 1.6F - 0.3F;
/* 139 */     f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
/*     */     
/* 141 */     if (f3 < 0.0F) {
/* 142 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 145 */     if (f4 < 0.0F) {
/* 146 */       f4 = 0.0F;
/*     */     }
/*     */     
/* 149 */     if (f3 > 1.0F) {
/* 150 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 153 */     if (f4 > 1.0F) {
/* 154 */       f4 = 1.0F;
/*     */     }
/*     */     
/* 157 */     GlStateManager.enableRescaleNormal();
/* 158 */     MODEL_BOOK.render(null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
/* 159 */     GlStateManager.disableRescaleNormal();
/* 160 */     RenderHelper.disableStandardItemLighting();
/* 161 */     GlStateManager.matrixMode(5889);
/* 162 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 163 */     GlStateManager.popMatrix();
/* 164 */     GlStateManager.matrixMode(5888);
/* 165 */     GlStateManager.popMatrix();
/* 166 */     RenderHelper.disableStandardItemLighting();
/* 167 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 168 */     EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
/* 169 */     int k = this.container.getLapisAmount();
/*     */     
/* 171 */     for (int l = 0; l < 3; l++) {
/* 172 */       int i1 = i + 60;
/* 173 */       int j1 = i1 + 20;
/* 174 */       int k1 = 86;
/* 175 */       String s = EnchantmentNameParts.getInstance().generateNewRandomName();
/* 176 */       this.zLevel = 0.0F;
/* 177 */       this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 178 */       int l1 = this.container.enchantLevels[l];
/* 179 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 181 */       if (l1 == 0) {
/* 182 */         drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/*     */       } else {
/* 184 */         int m = l1;
/* 185 */         FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
/* 186 */         int i2 = 6839882;
/*     */         
/* 188 */         if ((k < l + 1 || this.mc.thePlayer.experienceLevel < l1) && !this.mc.thePlayer.capabilities.isCreativeMode) {
/* 189 */           drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/* 190 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 239, 16, 16);
/* 191 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, (i2 & 0xFEFEFE) >> 1);
/* 192 */           i2 = 4226832;
/*     */         } else {
/* 194 */           int j2 = mouseX - i + 60;
/* 195 */           int k2 = mouseY - j + 14 + 19 * l;
/*     */           
/* 197 */           if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
/* 198 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 204, 108, 19);
/* 199 */             i2 = 16777088;
/*     */           } else {
/* 201 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 166, 108, 19);
/*     */           } 
/*     */           
/* 204 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 223, 16, 16);
/* 205 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, i2);
/* 206 */           i2 = 8453920;
/*     */         } 
/*     */         
/* 209 */         fontrenderer = this.mc.fontRendererObj;
/* 210 */         fontrenderer.drawStringWithShadow(m, (j1 + 86 - fontrenderer.getStringWidth(m)), (j + 16 + 19 * l + 7), i2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 219 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 220 */     boolean flag = this.mc.thePlayer.capabilities.isCreativeMode;
/* 221 */     int i = this.container.getLapisAmount();
/*     */     
/* 223 */     for (int j = 0; j < 3; j++) {
/* 224 */       int k = this.container.enchantLevels[j];
/* 225 */       int l = this.container.enchantmentIds[j];
/* 226 */       int i1 = j + 1;
/*     */       
/* 228 */       if (isPointInRegion(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0 && l >= 0) {
/* 229 */         List<String> list = Lists.newArrayList();
/*     */         
/* 231 */         if (l >= 0 && Enchantment.getEnchantmentById(l & 0xFF) != null) {
/* 232 */           String s = Enchantment.getEnchantmentById(l & 0xFF).getTranslatedName((l & 0xFF00) >> 8);
/* 233 */           list.add(String.valueOf(EnumChatFormatting.WHITE.toString()) + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", new Object[] { s }));
/*     */         } 
/*     */         
/* 236 */         if (!flag) {
/* 237 */           if (l >= 0) {
/* 238 */             list.add("");
/*     */           }
/*     */           
/* 241 */           if (this.mc.thePlayer.experienceLevel < k) {
/* 242 */             list.add(String.valueOf(EnumChatFormatting.RED.toString()) + "Level Requirement: " + this.container.enchantLevels[j]);
/*     */           } else {
/* 244 */             String s1 = "";
/*     */             
/* 246 */             if (i1 == 1) {
/* 247 */               s1 = I18n.format("container.enchant.lapis.one", new Object[0]);
/*     */             } else {
/* 249 */               s1 = I18n.format("container.enchant.lapis.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 252 */             if (i >= i1) {
/* 253 */               list.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s1);
/*     */             } else {
/* 255 */               list.add(String.valueOf(EnumChatFormatting.RED.toString()) + s1);
/*     */             } 
/*     */             
/* 258 */             if (i1 == 1) {
/* 259 */               s1 = I18n.format("container.enchant.level.one", new Object[0]);
/*     */             } else {
/* 261 */               s1 = I18n.format("container.enchant.level.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 264 */             list.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s1);
/*     */           } 
/*     */         } 
/*     */         
/* 268 */         drawHoveringText(list, mouseX, mouseY);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_147068_g() {
/* 275 */     ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();
/*     */     
/* 277 */     if (!ItemStack.areItemStacksEqual(itemstack, this.field_147077_B)) {
/* 278 */       this.field_147077_B = itemstack;
/*     */       
/*     */       do {
/* 281 */         this.field_147082_x += (this.random.nextInt(4) - this.random.nextInt(4));
/*     */       }
/* 283 */       while (this.field_147071_v <= this.field_147082_x + 1.0F && this.field_147071_v >= this.field_147082_x - 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     this.field_147073_u++;
/* 290 */     this.field_147069_w = this.field_147071_v;
/* 291 */     this.field_147076_A = this.field_147080_z;
/* 292 */     boolean flag = false;
/*     */     
/* 294 */     for (int i = 0; i < 3; i++) {
/* 295 */       if (this.container.enchantLevels[i] != 0) {
/* 296 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     if (flag) {
/* 301 */       this.field_147080_z += 0.2F;
/*     */     } else {
/* 303 */       this.field_147080_z -= 0.2F;
/*     */     } 
/*     */     
/* 306 */     this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
/* 307 */     float f1 = (this.field_147082_x - this.field_147071_v) * 0.4F;
/* 308 */     float f = 0.2F;
/* 309 */     f1 = MathHelper.clamp_float(f1, -f, f);
/* 310 */     this.field_147081_y += (f1 - this.field_147081_y) * 0.9F;
/* 311 */     this.field_147071_v += this.field_147081_y;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */