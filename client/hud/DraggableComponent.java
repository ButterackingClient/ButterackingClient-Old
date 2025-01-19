/*     */ package client.hud;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class DraggableComponent
/*     */   extends GuiScreen
/*     */ {
/*     */   private int x;
/*     */   private int y;
/*     */   private int width;
/*     */   private int height;
/*     */   private int color;
/*     */   private int lastX;
/*     */   private int lastY;
/*     */   private boolean dragging;
/*     */   
/*     */   public DraggableComponent(int x, int y, int width, int height, int color) {
/* 100 */     this.x = x;
/* 101 */     this.y = y;
/* 102 */     this.width = width;
/* 103 */     this.height = height;
/* 104 */     this.color = color;
/*     */   }
/*     */   
/*     */   public int getxPosition() {
/* 108 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getyPosition() {
/* 112 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setxPosition(int x) {
/* 116 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setyPosition(int y) {
/* 120 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 124 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 128 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 132 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(int color) {
/* 136 */     this.color = color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 141 */     draggingFix(mouseX, mouseY);
/* 142 */     Gui.drawRect(getxPosition(), getyPosition(), getxPosition() + getWidth(), getyPosition() + getHeight(), getColor());
/* 143 */     boolean mouseOverX = (mouseX >= getxPosition() && mouseX <= getxPosition() + getWidth());
/* 144 */     boolean mouseOverY = (mouseY >= getyPosition() && mouseY <= getyPosition() + getHeight());
/* 145 */     if (mouseOverX && mouseOverY && Mouse.isButtonDown(0) && !this.dragging) {
/* 146 */       this.lastX = this.x - mouseX;
/* 147 */       this.lastY = this.y - mouseY;
/* 148 */       this.dragging = true;
/*     */     } 
/* 150 */     if (this.x < 0) {
/* 151 */       this.x = 0;
/*     */     }
/* 153 */     if (this.y < 0) {
/* 154 */       this.y = 0;
/*     */     }
/* 156 */     if (this.x + this.width > GuiScreen.width) {
/* 157 */       this.x = GuiScreen.width - this.width;
/*     */     }
/* 159 */     if (this.y + this.height > GuiScreen.height) {
/* 160 */       this.y = GuiScreen.height - this.height;
/*     */     }
/*     */   }
/*     */   
/*     */   private void draggingFix(int mouseX, int mouseY) {
/* 165 */     Minecraft mc = Minecraft.getMinecraft();
/* 166 */     if (this.dragging) {
/* 167 */       this.x = mouseX + this.lastX;
/* 168 */       this.y = mouseY + this.lastY;
/* 169 */       if (!Mouse.isButtonDown(0))
/* 170 */         this.dragging = false; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\hud\DraggableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */