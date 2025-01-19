/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiOptionsRowList
/*     */   extends GuiListExtended
/*     */ {
/*  11 */   private final List<Row> field_148184_k = Lists.newArrayList();
/*     */   
/*     */   public GuiOptionsRowList(Minecraft mcIn, int p_i45015_2_, int p_i45015_3_, int p_i45015_4_, int p_i45015_5_, int p_i45015_6_, GameSettings.Options... p_i45015_7_) {
/*  14 */     super(mcIn, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
/*  15 */     this.field_148163_i = false;
/*     */     
/*  17 */     for (int i = 0; i < p_i45015_7_.length; i += 2) {
/*  18 */       GameSettings.Options gamesettings$options = p_i45015_7_[i];
/*  19 */       GameSettings.Options gamesettings$options1 = (i < p_i45015_7_.length - 1) ? p_i45015_7_[i + 1] : null;
/*  20 */       GuiButton guibutton = func_148182_a(mcIn, p_i45015_2_ / 2 - 155, 0, gamesettings$options);
/*  21 */       GuiButton guibutton1 = func_148182_a(mcIn, p_i45015_2_ / 2 - 155 + 160, 0, gamesettings$options1);
/*  22 */       this.field_148184_k.add(new Row(guibutton, guibutton1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private GuiButton func_148182_a(Minecraft mcIn, int p_148182_2_, int p_148182_3_, GameSettings.Options p_148182_4_) {
/*  27 */     if (p_148182_4_ == null) {
/*  28 */       return null;
/*     */     }
/*  30 */     int i = p_148182_4_.returnEnumOrdinal();
/*  31 */     return p_148182_4_.getEnumFloat() ? new GuiOptionSlider(i, p_148182_2_, p_148182_3_, p_148182_4_) : new GuiOptionButton(i, p_148182_2_, p_148182_3_, p_148182_4_, mcIn.gameSettings.getKeyBinding(p_148182_4_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Row getListEntry(int index) {
/*  39 */     return this.field_148184_k.get(index);
/*     */   }
/*     */   
/*     */   protected int getSize() {
/*  43 */     return this.field_148184_k.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  50 */     return 400;
/*     */   }
/*     */   
/*     */   protected int getScrollBarX() {
/*  54 */     return super.getScrollBarX() + 32;
/*     */   }
/*     */   
/*     */   public static class Row implements GuiListExtended.IGuiListEntry {
/*  58 */     private final Minecraft field_148325_a = Minecraft.getMinecraft();
/*     */     private final GuiButton field_148323_b;
/*     */     private final GuiButton field_148324_c;
/*     */     
/*     */     public Row(GuiButton p_i45014_1_, GuiButton p_i45014_2_) {
/*  63 */       this.field_148323_b = p_i45014_1_;
/*  64 */       this.field_148324_c = p_i45014_2_;
/*     */     }
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*  68 */       if (this.field_148323_b != null) {
/*  69 */         this.field_148323_b.yPosition = y;
/*  70 */         this.field_148323_b.drawButton(this.field_148325_a, mouseX, mouseY);
/*     */       } 
/*     */       
/*  73 */       if (this.field_148324_c != null) {
/*  74 */         this.field_148324_c.yPosition = y;
/*  75 */         this.field_148324_c.drawButton(this.field_148325_a, mouseX, mouseY);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/*  80 */       if (this.field_148323_b.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
/*  81 */         if (this.field_148323_b instanceof GuiOptionButton) {
/*  82 */           this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
/*  83 */           this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
/*     */         } 
/*     */         
/*  86 */         return true;
/*  87 */       }  if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
/*  88 */         if (this.field_148324_c instanceof GuiOptionButton) {
/*  89 */           this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
/*  90 */           this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
/*     */         } 
/*     */         
/*  93 */         return true;
/*     */       } 
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 100 */       if (this.field_148323_b != null) {
/* 101 */         this.field_148323_b.mouseReleased(x, y);
/*     */       }
/*     */       
/* 104 */       if (this.field_148324_c != null)
/* 105 */         this.field_148324_c.mouseReleased(x, y); 
/*     */     }
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiOptionsRowList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */