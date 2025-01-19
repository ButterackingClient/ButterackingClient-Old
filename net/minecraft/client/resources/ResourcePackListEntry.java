/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
/*  17 */   private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
/*  18 */   private static final IChatComponent field_183020_d = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible", new Object[0]);
/*  19 */   private static final IChatComponent field_183021_e = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible.old", new Object[0]);
/*  20 */   private static final IChatComponent field_183022_f = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible.new", new Object[0]);
/*     */   protected final Minecraft mc;
/*     */   protected final GuiScreenResourcePacks resourcePacksGUI;
/*     */   
/*     */   public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn) {
/*  25 */     this.resourcePacksGUI = resourcePacksGUIIn;
/*  26 */     this.mc = Minecraft.getMinecraft();
/*     */   }
/*     */   
/*     */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*  30 */     int i = func_183019_a();
/*     */     
/*  32 */     if (i != 1) {
/*  33 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  34 */       Gui.drawRect(x - 1, y - 1, x + listWidth - 9, y + slotHeight + 1, -8978432);
/*     */     } 
/*     */     
/*  37 */     func_148313_c();
/*  38 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  39 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/*  40 */     String s = func_148312_b();
/*  41 */     String s1 = func_148311_a();
/*     */     
/*  43 */     if ((this.mc.gameSettings.touchscreen || isSelected) && func_148310_d()) {
/*  44 */       this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
/*  45 */       Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
/*  46 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  47 */       int j = mouseX - x;
/*  48 */       int k = mouseY - y;
/*     */       
/*  50 */       if (i < 1) {
/*  51 */         s = field_183020_d.getFormattedText();
/*  52 */         s1 = field_183021_e.getFormattedText();
/*  53 */       } else if (i > 1) {
/*  54 */         s = field_183020_d.getFormattedText();
/*  55 */         s1 = field_183022_f.getFormattedText();
/*     */       } 
/*     */       
/*  58 */       if (func_148309_e()) {
/*  59 */         if (j < 32) {
/*  60 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         } else {
/*  62 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       } else {
/*  65 */         if (func_148308_f()) {
/*  66 */           if (j < 16) {
/*  67 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           } else {
/*  69 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/*  73 */         if (func_148314_g()) {
/*  74 */           if (j < 32 && j > 16 && k < 16) {
/*  75 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           } else {
/*  77 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/*  81 */         if (func_148307_h()) {
/*  82 */           if (j < 32 && j > 16 && k > 16) {
/*  83 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           } else {
/*  85 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     int i1 = this.mc.fontRendererObj.getStringWidth(s);
/*     */     
/*  93 */     if (i1 > 157) {
/*  94 */       s = String.valueOf(this.mc.fontRendererObj.trimStringToWidth(s, 157 - this.mc.fontRendererObj.getStringWidth("..."))) + "...";
/*     */     }
/*     */     
/*  97 */     this.mc.fontRendererObj.drawStringWithShadow(s, (x + 32 + 2), (y + 1), 16777215);
/*  98 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(s1, 157);
/*     */     
/* 100 */     for (int l = 0; l < 2 && l < list.size(); l++) {
/* 101 */       this.mc.fontRendererObj.drawStringWithShadow(list.get(l), (x + 32 + 2), (y + 12 + 10 * l), 8421504);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract int func_183019_a();
/*     */   
/*     */   protected abstract String func_148311_a();
/*     */   
/*     */   protected abstract String func_148312_b();
/*     */   
/*     */   protected abstract void func_148313_c();
/*     */   
/*     */   protected boolean func_148310_d() {
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean func_148309_e() {
/* 118 */     return !this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */   
/*     */   protected boolean func_148308_f() {
/* 122 */     return this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */   
/*     */   protected boolean func_148314_g() {
/* 126 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 127 */     int i = list.indexOf(this);
/* 128 */     return (i > 0 && ((ResourcePackListEntry)list.get(i - 1)).func_148310_d());
/*     */   }
/*     */   
/*     */   protected boolean func_148307_h() {
/* 132 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 133 */     int i = list.indexOf(this);
/* 134 */     return (i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).func_148310_d());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 141 */     if (func_148310_d() && p_148278_5_ <= 32) {
/* 142 */       if (func_148309_e()) {
/* 143 */         this.resourcePacksGUI.markChanged();
/* 144 */         int j = func_183019_a();
/*     */         
/* 146 */         if (j != 1) {
/* 147 */           String s1 = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
/* 148 */           String s = I18n.format("resourcePack.incompatible.confirm." + ((j > 1) ? "new" : "old"), new Object[0]);
/* 149 */           this.mc.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback() {
/*     */                   public void confirmClicked(boolean result, int id) {
/* 151 */                     List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
/* 152 */                     ResourcePackListEntry.this.mc.displayGuiScreen((GuiScreen)ResourcePackListEntry.this.resourcePacksGUI);
/*     */                     
/* 154 */                     if (result) {
/* 155 */                       list2.remove(ResourcePackListEntry.this);
/* 156 */                       ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(0, ResourcePackListEntry.this);
/*     */                     } 
/*     */                   }
/* 159 */                 }s1, s, 0));
/*     */         } else {
/* 161 */           this.resourcePacksGUI.getListContaining(this).remove(this);
/* 162 */           this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
/*     */         } 
/*     */         
/* 165 */         return true;
/*     */       } 
/*     */       
/* 168 */       if (p_148278_5_ < 16 && func_148308_f()) {
/* 169 */         this.resourcePacksGUI.getListContaining(this).remove(this);
/* 170 */         this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
/* 171 */         this.resourcePacksGUI.markChanged();
/* 172 */         return true;
/*     */       } 
/*     */       
/* 175 */       if (p_148278_5_ > 16 && p_148278_6_ < 16 && func_148314_g()) {
/* 176 */         List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
/* 177 */         int k = list1.indexOf(this);
/* 178 */         list1.remove(this);
/* 179 */         list1.add(k - 1, this);
/* 180 */         this.resourcePacksGUI.markChanged();
/* 181 */         return true;
/*     */       } 
/*     */       
/* 184 */       if (p_148278_5_ > 16 && p_148278_6_ > 16 && func_148307_h()) {
/* 185 */         List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 186 */         int i = list.indexOf(this);
/* 187 */         list.remove(this);
/* 188 */         list.add(i + 1, this);
/* 189 */         this.resourcePacksGUI.markChanged();
/* 190 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\ResourcePackListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */