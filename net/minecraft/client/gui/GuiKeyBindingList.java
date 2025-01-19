/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class GuiKeyBindingList
/*     */   extends GuiListExtended {
/*     */   private final GuiControls field_148191_k;
/*     */   private final Minecraft mc;
/*     */   private final GuiListExtended.IGuiListEntry[] listEntries;
/*  16 */   private int maxListLabelWidth = 0;
/*     */   
/*     */   public GuiKeyBindingList(GuiControls controls, Minecraft mcIn) {
/*  19 */     super(mcIn, GuiControls.width, GuiControls.height, 63, GuiControls.height - 32, 20);
/*  20 */     this.field_148191_k = controls;
/*  21 */     this.mc = mcIn;
/*  22 */     KeyBinding[] akeybinding = (KeyBinding[])ArrayUtils.clone((Object[])mcIn.gameSettings.mc);
/*  23 */     this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
/*  24 */     Arrays.sort((Object[])akeybinding);
/*  25 */     int i = 0;
/*  26 */     String s = null; byte b; int j;
/*     */     KeyBinding[] arrayOfKeyBinding1;
/*  28 */     for (j = (arrayOfKeyBinding1 = akeybinding).length, b = 0; b < j; ) { KeyBinding keybinding = arrayOfKeyBinding1[b];
/*  29 */       String s1 = keybinding.getKeyCategory();
/*     */       
/*  31 */       if (!s1.equals(s)) {
/*  32 */         s = s1;
/*  33 */         this.listEntries[i++] = new CategoryEntry(s1);
/*     */       } 
/*     */       
/*  36 */       int k = mcIn.fontRendererObj.getStringWidth(I18n.format(keybinding.getKeyDescription(), new Object[0]));
/*     */       
/*  38 */       if (k > this.maxListLabelWidth) {
/*  39 */         this.maxListLabelWidth = k;
/*     */       }
/*     */       
/*  42 */       this.listEntries[i++] = new KeyEntry(keybinding, null);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   protected int getSize() {
/*  47 */     return this.listEntries.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/*  54 */     return this.listEntries[index];
/*     */   }
/*     */   
/*     */   protected int getScrollBarX() {
/*  58 */     return super.getScrollBarX() + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  65 */     return super.getListWidth() + 32;
/*     */   }
/*     */   
/*     */   public class CategoryEntry implements GuiListExtended.IGuiListEntry {
/*     */     private final String labelText;
/*     */     private final int labelWidth;
/*     */     
/*     */     public CategoryEntry(String p_i45028_2_) {
/*  73 */       this.labelText = I18n.format(p_i45028_2_, new Object[0]);
/*  74 */       this.labelWidth = GuiKeyBindingList.this.mc.fontRendererObj.getStringWidth(this.labelText);
/*     */     }
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*  78 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.labelText, GuiScreen.width / 2 - this.labelWidth / 2, y + slotHeight - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
/*     */     }
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/*  82 */       return false;
/*     */     }
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   }
/*     */   
/*     */   public class KeyEntry
/*     */     implements GuiListExtended.IGuiListEntry
/*     */   {
/*     */     private final KeyBinding keybinding;
/*     */     private final String keyDesc;
/*     */     private final GuiButton btnChangeKeyBinding;
/*     */     private final GuiButton btnReset;
/*     */     
/*     */     private KeyEntry(KeyBinding p_i45029_2_) {
/*  99 */       this.keybinding = p_i45029_2_;
/* 100 */       this.keyDesc = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
/* 101 */       this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
/* 102 */       this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
/*     */     }
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/* 106 */       boolean flag = (GuiKeyBindingList.this.field_148191_k.buttonId == this.keybinding);
/* 107 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.keyDesc, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + slotHeight / 2 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
/* 108 */       this.btnReset.xPosition = x + 190;
/* 109 */       this.btnReset.yPosition = y;
/* 110 */       this.btnReset.enabled = (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault());
/* 111 */       this.btnReset.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
/* 112 */       this.btnChangeKeyBinding.xPosition = x + 105;
/* 113 */       this.btnChangeKeyBinding.yPosition = y;
/* 114 */       this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
/* 115 */       boolean flag1 = false;
/*     */       
/* 117 */       if (this.keybinding.getKeyCode() != 0) {
/* 118 */         byte b; int i; KeyBinding[] arrayOfKeyBinding; for (i = (arrayOfKeyBinding = GuiKeyBindingList.this.mc.gameSettings.mc).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/* 119 */           if (keybinding != this.keybinding && keybinding.getKeyCode() == this.keybinding.getKeyCode()) {
/* 120 */             flag1 = true;
/*     */             break;
/*     */           } 
/*     */           b++; }
/*     */       
/*     */       } 
/* 126 */       if (flag) {
/* 127 */         this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
/* 128 */       } else if (flag1) {
/* 129 */         this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
/*     */       } 
/*     */       
/* 132 */       this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
/*     */     }
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 136 */       if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
/* 137 */         GuiKeyBindingList.this.field_148191_k.buttonId = this.keybinding;
/* 138 */         return true;
/* 139 */       }  if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
/* 140 */         GuiKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
/* 141 */         KeyBinding.resetKeyBindingArrayAndHash();
/* 142 */         return true;
/*     */       } 
/* 144 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 149 */       this.btnChangeKeyBinding.mouseReleased(x, y);
/* 150 */       this.btnReset.mouseReleased(x, y);
/*     */     }
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiKeyBindingList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */