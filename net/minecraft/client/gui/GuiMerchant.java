/*     */ package net.minecraft.client.gui;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerMerchant;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiMerchant extends GuiContainer {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMerchant merchant;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MerchantButton nextButton;
/*     */ 
/*     */ 
/*     */   
/*     */   private MerchantButton previousButton;
/*     */ 
/*     */ 
/*     */   
/*     */   private int selectedMerchantRecipe;
/*     */ 
/*     */ 
/*     */   
/*     */   private IChatComponent chatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMerchant(InventoryPlayer p_i45500_1_, IMerchant p_i45500_2_, World worldIn) {
/*  60 */     super((Container)new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
/*  61 */     this.merchant = p_i45500_2_;
/*  62 */     this.chatComponent = p_i45500_2_.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  70 */     super.initGui();
/*  71 */     int i = (width - this.xSize) / 2;
/*  72 */     int j = (height - this.ySize) / 2;
/*  73 */     this.buttonList.add(this.nextButton = new MerchantButton(1, i + 120 + 27, j + 24 - 1, true));
/*  74 */     this.buttonList.add(this.previousButton = new MerchantButton(2, i + 36 - 19, j + 24 - 1, false));
/*  75 */     this.nextButton.enabled = false;
/*  76 */     this.previousButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  83 */     String s = this.chatComponent.getUnformattedText();
/*  84 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/*  85 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  92 */     super.updateScreen();
/*  93 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/*  95 */     if (merchantrecipelist != null) {
/*  96 */       this.nextButton.enabled = (this.selectedMerchantRecipe < merchantrecipelist.size() - 1);
/*  97 */       this.previousButton.enabled = (this.selectedMerchantRecipe > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 105 */     boolean flag = false;
/*     */     
/* 107 */     if (button == this.nextButton) {
/* 108 */       this.selectedMerchantRecipe++;
/* 109 */       MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */       
/* 111 */       if (merchantrecipelist != null && this.selectedMerchantRecipe >= merchantrecipelist.size()) {
/* 112 */         this.selectedMerchantRecipe = merchantrecipelist.size() - 1;
/*     */       }
/*     */       
/* 115 */       flag = true;
/* 116 */     } else if (button == this.previousButton) {
/* 117 */       this.selectedMerchantRecipe--;
/*     */       
/* 119 */       if (this.selectedMerchantRecipe < 0) {
/* 120 */         this.selectedMerchantRecipe = 0;
/*     */       }
/*     */       
/* 123 */       flag = true;
/*     */     } 
/*     */     
/* 126 */     if (flag) {
/* 127 */       ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
/* 128 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 129 */       packetbuffer.writeInt(this.selectedMerchantRecipe);
/* 130 */       this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload("MC|TrSel", packetbuffer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 138 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 139 */     this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 140 */     int i = (width - this.xSize) / 2;
/* 141 */     int j = (height - this.ySize) / 2;
/* 142 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 143 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/* 145 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/* 146 */       int k = this.selectedMerchantRecipe;
/*     */       
/* 148 */       if (k < 0 || k >= merchantrecipelist.size()) {
/*     */         return;
/*     */       }
/*     */       
/* 152 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/*     */       
/* 154 */       if (merchantrecipe.isRecipeDisabled()) {
/* 155 */         this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
/* 156 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 157 */         GlStateManager.disableLighting();
/* 158 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
/* 159 */         drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 168 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 169 */     MerchantRecipeList merchantrecipelist = this.merchant.getRecipes((EntityPlayer)this.mc.thePlayer);
/*     */     
/* 171 */     if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {
/* 172 */       int i = (width - this.xSize) / 2;
/* 173 */       int j = (height - this.ySize) / 2;
/* 174 */       int k = this.selectedMerchantRecipe;
/* 175 */       MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(k);
/* 176 */       ItemStack itemstack = merchantrecipe.getItemToBuy();
/* 177 */       ItemStack itemstack1 = merchantrecipe.getSecondItemToBuy();
/* 178 */       ItemStack itemstack2 = merchantrecipe.getItemToSell();
/* 179 */       GlStateManager.pushMatrix();
/* 180 */       RenderHelper.enableGUIStandardItemLighting();
/* 181 */       GlStateManager.disableLighting();
/* 182 */       GlStateManager.enableRescaleNormal();
/* 183 */       GlStateManager.enableColorMaterial();
/* 184 */       GlStateManager.enableLighting();
/* 185 */       this.itemRender.zLevel = 100.0F;
/* 186 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack, i + 36, j + 24);
/* 187 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, i + 36, j + 24);
/*     */       
/* 189 */       if (itemstack1 != null) {
/* 190 */         this.itemRender.renderItemAndEffectIntoGUI(itemstack1, i + 62, j + 24);
/* 191 */         this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack1, i + 62, j + 24);
/*     */       } 
/*     */       
/* 194 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack2, i + 120, j + 24);
/* 195 */       this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack2, i + 120, j + 24);
/* 196 */       this.itemRender.zLevel = 0.0F;
/* 197 */       GlStateManager.disableLighting();
/*     */       
/* 199 */       if (isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && itemstack != null) {
/* 200 */         renderToolTip(itemstack, mouseX, mouseY);
/* 201 */       } else if (itemstack1 != null && isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && itemstack1 != null) {
/* 202 */         renderToolTip(itemstack1, mouseX, mouseY);
/* 203 */       } else if (itemstack2 != null && isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && itemstack2 != null) {
/* 204 */         renderToolTip(itemstack2, mouseX, mouseY);
/* 205 */       } else if (merchantrecipe.isRecipeDisabled() && (isPointInRegion(83, 21, 28, 21, mouseX, mouseY) || isPointInRegion(83, 51, 28, 21, mouseX, mouseY))) {
/* 206 */         drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), mouseX, mouseY);
/*     */       } 
/*     */       
/* 209 */       GlStateManager.popMatrix();
/* 210 */       GlStateManager.enableLighting();
/* 211 */       GlStateManager.enableDepth();
/* 212 */       RenderHelper.enableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */   
/*     */   public IMerchant getMerchant() {
/* 217 */     return this.merchant;
/*     */   }
/*     */   
/*     */   static class MerchantButton extends GuiButton {
/*     */     private final boolean field_146157_o;
/*     */     
/*     */     public MerchantButton(int buttonID, int x, int y, boolean p_i1095_4_) {
/* 224 */       super(buttonID, x, y, 12, 19, "");
/* 225 */       this.field_146157_o = p_i1095_4_;
/*     */     }
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 229 */       if (this.visible) {
/* 230 */         mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
/* 231 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 232 */         boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 233 */         int i = 0;
/* 234 */         int j = 176;
/*     */         
/* 236 */         if (!this.enabled) {
/* 237 */           j += this.width * 2;
/* 238 */         } else if (flag) {
/* 239 */           j += this.width;
/*     */         } 
/*     */         
/* 242 */         if (!this.field_146157_o) {
/* 243 */           i += this.height;
/*     */         }
/*     */         
/* 246 */         drawTexturedModalRect(this.xPosition, this.yPosition, j, i, this.width, this.height);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */