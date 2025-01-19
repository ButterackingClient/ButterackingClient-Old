/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiBeacon
/*     */   extends GuiContainer {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*  28 */   private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
/*     */   private IInventory tileBeacon;
/*     */   private ConfirmButton beaconConfirmButton;
/*     */   private boolean buttonsNotDrawn;
/*     */   
/*     */   public GuiBeacon(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
/*  34 */     super((Container)new ContainerBeacon((IInventory)playerInventory, tileBeaconIn));
/*  35 */     this.tileBeacon = tileBeaconIn;
/*  36 */     this.xSize = 230;
/*  37 */     this.ySize = 219;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  45 */     super.initGui();
/*  46 */     this.buttonList.add(this.beaconConfirmButton = new ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107));
/*  47 */     this.buttonList.add(new CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
/*  48 */     this.buttonsNotDrawn = true;
/*  49 */     this.beaconConfirmButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  56 */     super.updateScreen();
/*  57 */     int i = this.tileBeacon.getField(0);
/*  58 */     int j = this.tileBeacon.getField(1);
/*  59 */     int k = this.tileBeacon.getField(2);
/*     */     
/*  61 */     if (this.buttonsNotDrawn && i >= 0) {
/*  62 */       this.buttonsNotDrawn = false;
/*     */       
/*  64 */       for (int l = 0; l <= 2; l++) {
/*  65 */         int i1 = (TileEntityBeacon.effectsList[l]).length;
/*  66 */         int j1 = i1 * 22 + (i1 - 1) * 2;
/*     */         
/*  68 */         for (int k1 = 0; k1 < i1; k1++) {
/*  69 */           int l1 = (TileEntityBeacon.effectsList[l][k1]).id;
/*  70 */           PowerButton guibeacon$powerbutton = new PowerButton(l << 8 | l1, this.guiLeft + 76 + k1 * 24 - j1 / 2, this.guiTop + 22 + l * 25, l1, l);
/*  71 */           this.buttonList.add(guibeacon$powerbutton);
/*     */           
/*  73 */           if (l >= i) {
/*  74 */             guibeacon$powerbutton.enabled = false;
/*  75 */           } else if (l1 == j) {
/*  76 */             guibeacon$powerbutton.func_146140_b(true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  81 */       int i2 = 3;
/*  82 */       int j2 = (TileEntityBeacon.effectsList[i2]).length + 1;
/*  83 */       int k2 = j2 * 22 + (j2 - 1) * 2;
/*     */       
/*  85 */       for (int l2 = 0; l2 < j2 - 1; l2++) {
/*  86 */         int i3 = (TileEntityBeacon.effectsList[i2][l2]).id;
/*  87 */         PowerButton guibeacon$powerbutton2 = new PowerButton(i2 << 8 | i3, this.guiLeft + 167 + l2 * 24 - k2 / 2, this.guiTop + 47, i3, i2);
/*  88 */         this.buttonList.add(guibeacon$powerbutton2);
/*     */         
/*  90 */         if (i2 >= i) {
/*  91 */           guibeacon$powerbutton2.enabled = false;
/*  92 */         } else if (i3 == k) {
/*  93 */           guibeacon$powerbutton2.func_146140_b(true);
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       if (j > 0) {
/*  98 */         PowerButton guibeacon$powerbutton1 = new PowerButton(i2 << 8 | j, this.guiLeft + 167 + (j2 - 1) * 24 - k2 / 2, this.guiTop + 47, j, i2);
/*  99 */         this.buttonList.add(guibeacon$powerbutton1);
/*     */         
/* 101 */         if (i2 >= i) {
/* 102 */           guibeacon$powerbutton1.enabled = false;
/* 103 */         } else if (j == k) {
/* 104 */           guibeacon$powerbutton1.func_146140_b(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     this.beaconConfirmButton.enabled = (this.tileBeacon.getStackInSlot(0) != null && j > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 116 */     if (button.id == -2) {
/* 117 */       this.mc.displayGuiScreen(null);
/* 118 */     } else if (button.id == -1) {
/* 119 */       String s = "MC|Beacon";
/* 120 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 121 */       packetbuffer.writeInt(this.tileBeacon.getField(1));
/* 122 */       packetbuffer.writeInt(this.tileBeacon.getField(2));
/* 123 */       this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload(s, packetbuffer));
/* 124 */       this.mc.displayGuiScreen(null);
/* 125 */     } else if (button instanceof PowerButton) {
/* 126 */       if (((PowerButton)button).func_146141_c()) {
/*     */         return;
/*     */       }
/*     */       
/* 130 */       int j = button.id;
/* 131 */       int k = j & 0xFF;
/* 132 */       int i = j >> 8;
/*     */       
/* 134 */       if (i < 3) {
/* 135 */         this.tileBeacon.setField(1, k);
/*     */       } else {
/* 137 */         this.tileBeacon.setField(2, k);
/*     */       } 
/*     */       
/* 140 */       this.buttonList.clear();
/* 141 */       initGui();
/* 142 */       updateScreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 150 */     RenderHelper.disableStandardItemLighting();
/* 151 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
/* 152 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
/*     */     
/* 154 */     for (GuiButton guibutton : this.buttonList) {
/* 155 */       if (guibutton.isMouseOver()) {
/* 156 */         guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 161 */     RenderHelper.enableGUIStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 168 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 169 */     this.mc.getTextureManager().bindTexture(beaconGuiTextures);
/* 170 */     int i = (width - this.xSize) / 2;
/* 171 */     int j = (height - this.ySize) / 2;
/* 172 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 173 */     this.itemRender.zLevel = 100.0F;
/* 174 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), i + 42, j + 109);
/* 175 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), i + 42 + 22, j + 109);
/* 176 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), i + 42 + 44, j + 109);
/* 177 */     this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), i + 42 + 66, j + 109);
/* 178 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */   
/*     */   static class Button extends GuiButton {
/*     */     private final ResourceLocation field_146145_o;
/*     */     private final int field_146144_p;
/*     */     private final int field_146143_q;
/*     */     private boolean field_146142_r;
/*     */     
/*     */     protected Button(int p_i1077_1_, int p_i1077_2_, int p_i1077_3_, ResourceLocation p_i1077_4_, int p_i1077_5_, int p_i1077_6_) {
/* 188 */       super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
/* 189 */       this.field_146145_o = p_i1077_4_;
/* 190 */       this.field_146144_p = p_i1077_5_;
/* 191 */       this.field_146143_q = p_i1077_6_;
/*     */     }
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 195 */       if (this.visible) {
/* 196 */         mc.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
/* 197 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 198 */         this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 199 */         int i = 219;
/* 200 */         int j = 0;
/*     */         
/* 202 */         if (!this.enabled) {
/* 203 */           j += this.width * 2;
/* 204 */         } else if (this.field_146142_r) {
/* 205 */           j += this.width * 1;
/* 206 */         } else if (this.hovered) {
/* 207 */           j += this.width * 3;
/*     */         } 
/*     */         
/* 210 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, i, this.width, this.height);
/*     */         
/* 212 */         if (!GuiBeacon.beaconGuiTextures.equals(this.field_146145_o)) {
/* 213 */           mc.getTextureManager().bindTexture(this.field_146145_o);
/*     */         }
/*     */         
/* 216 */         drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean func_146141_c() {
/* 221 */       return this.field_146142_r;
/*     */     }
/*     */     
/*     */     public void func_146140_b(boolean p_146140_1_) {
/* 225 */       this.field_146142_r = p_146140_1_;
/*     */     }
/*     */   }
/*     */   
/*     */   class CancelButton extends Button {
/*     */     public CancelButton(int p_i1074_2_, int p_i1074_3_, int p_i1074_4_) {
/* 231 */       super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.beaconGuiTextures, 112, 220);
/*     */     }
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 235 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class ConfirmButton extends Button {
/*     */     public ConfirmButton(int p_i1075_2_, int p_i1075_3_, int p_i1075_4_) {
/* 241 */       super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.beaconGuiTextures, 90, 220);
/*     */     }
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 245 */       GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   class PowerButton extends Button {
/*     */     private final int field_146149_p;
/*     */     private final int field_146148_q;
/*     */     
/*     */     public PowerButton(int p_i1076_2_, int p_i1076_3_, int p_i1076_4_, int p_i1076_5_, int p_i1076_6_) {
/* 254 */       super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.inventoryBackground, 0 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
/* 255 */       this.field_146149_p = p_i1076_5_;
/* 256 */       this.field_146148_q = p_i1076_6_;
/*     */     }
/*     */     
/*     */     public void drawButtonForegroundLayer(int mouseX, int mouseY) {
/* 260 */       String s = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
/*     */       
/* 262 */       if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
/* 263 */         s = String.valueOf(s) + " II";
/*     */       }
/*     */       
/* 266 */       GuiBeacon.this.drawCreativeTabHoveringText(s, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */