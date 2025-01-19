/*     */ package client.gui.click;
/*     */ 
/*     */ import client.Client;
/*     */ import client.hud.HudManager;
/*     */ import client.hud.HudMod;
/*     */ import client.mod.options.OptionButton;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ public class ClickGui
/*     */   extends GuiScreen {
/*     */   ArrayList<Modbutton> modbutton;
/*     */   ArrayList<OptionButton> optionbutton;
/*     */   public static FontRenderer fr;
/*  20 */   public HudManager m = (Client.getInstance()).hudManager;
/*     */   
/*     */   public ClickGui() {
/*  23 */     this.modbutton = new ArrayList<>();
/*  24 */     this.optionbutton = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  29 */     super.initGui();
/*  30 */     addModButton(1, 1, (HudMod)this.m.clientName);
/*  31 */     addModButton(1, 2, (HudMod)this.m.modBiom);
/*  32 */     addModButton(1, 3, (HudMod)this.m.modXYZ);
/*  33 */     addModButton(1, 4, (HudMod)this.m.modFps);
/*  34 */     addModButton(1, 5, (HudMod)this.m.modCps);
/*  35 */     addModButton(1, 6, (HudMod)this.m.timer);
/*  36 */     addModButton(1, 7, (HudMod)this.m.pingMod);
/*  37 */     addModButton(1, 8, (HudMod)this.m.packDisplay);
/*  38 */     addModButton(1, 9, (HudMod)this.m.toggleSprintHud);
/*  39 */     addModButton(1, 10, (HudMod)this.m.modArmorStatus);
/*  40 */     addModButton(1, 11, (HudMod)this.m.modKeyStrokes);
/*  41 */     addModButton(1, 12, (HudMod)this.m.modPotionStatus);
/*  42 */     addModButton(1, 13, (HudMod)this.m.itemHeld);
/*  43 */     addModButton(1, 14, (HudMod)this.m.cape);
/*  44 */     addModButton(1, 15, (HudMod)this.m.cape2);
/*  45 */     addModButton(2, 1, (HudMod)this.m.hitColor);
/*  46 */     addModButton(2, 2, (HudMod)this.m.autoGG);
/*  47 */     addModButton(2, 3, (HudMod)this.m.customGui);
/*  48 */     addModButton(2, 4, (HudMod)this.m.oldSneak);
/*  49 */     addModButton(2, 5, (HudMod)this.m.oldanimations);
/*  50 */     addModButton(2, 6, (HudMod)this.m.wings);
/*  51 */     addModButton(2, 7, (HudMod)this.m.wings2);
/*  52 */     addModButton(2, 8, (HudMod)this.m.snaplook);
/*  53 */     addModButton(2, 9, (HudMod)this.m.clearChat);
/*  54 */     addModButton(2, 10, (HudMod)this.m.noHeartCam);
/*  55 */     addModButton(2, 11, (HudMod)this.m.hitbox);
/*  56 */     addModButton(2, 12, (HudMod)this.m.fullbrightmod);
/*  57 */     addModButton(2, 13, (HudMod)this.m.texFix);
/*  58 */     addWarnedModButton(2, 14, (HudMod)this.m.hitDelayFix);
/*  59 */     addModButton(2, 15, (HudMod)this.m.simpleGui);
/*  60 */     addModButton(3, 1, (HudMod)this.m.NoInventoryBG);
/*  61 */     addModButton(3, 2, (HudMod)this.m.bowdistance);
/*  62 */     addModButton(3, 3, (HudMod)this.m.blockoutline);
/*  63 */     addModButton(3, 4, (HudMod)this.m.strengthAim);
/*  64 */     addModButton(3, 5, (HudMod)this.m.oldHeadPoint);
/*  65 */     addModButton(3, 6, (HudMod)this.m.noCinematicZoom);
/*  66 */     addModButton(3, 7, (HudMod)this.m.newNausea);
/*  67 */     addModButton(3, 8, (HudMod)this.m.tntTimer);
/*  68 */     addModButton(3, 9, (HudMod)this.m.oreCounter);
/*  69 */     addModButton(3, 10, (HudMod)this.m.arrowCounter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addModButton(int row, int line, HudMod mod) {
/*  76 */     this.modbutton.add(new Modbutton(getRow(row), getLine(line), getWidth(mod), getHeight(), mod));
/*  77 */     if (mod.optionGui != null) {
/*  78 */       this.optionbutton.add(new OptionButton(getRow(row) + getWidth(mod), getLine(line), (GuiScreen)mod.optionGui));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addWarnedModButton(int row, int line, HudMod mod) {
/*  83 */     this.modbutton.add(new Modbutton(getRow(row), getLine(line), getWidth(mod), getHeight(), mod, true));
/*  84 */     if (mod.optionGui != null) {
/*  85 */       this.optionbutton.add(new OptionButton(getRow(row) + getWidth(mod), getLine(line), (GuiScreen)mod.optionGui));
/*     */     }
/*     */   }
/*     */   
/*     */   private int getRow(int row) {
/*  90 */     return 10 + (row - 1) * 85;
/*     */   }
/*     */   
/*     */   private int getLine(int line) {
/*  94 */     return 10 + (line - 1) * 15;
/*     */   }
/*     */   
/*     */   private int getWidth(HudMod m) {
/*  98 */     return this.mc.fontRendererObj.getStringWidth(m.name) + 4;
/*     */   }
/*     */   
/*     */   private int getHeight() {
/* 102 */     return this.mc.fontRendererObj.FONT_HEIGHT + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 107 */     ScaledResolution sr = new ScaledResolution(this.mc);
/* 108 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 109 */     Gui.drawRect(5, 5, getRow(4) + 10, 235, (new Color(0, 0, 0, 180)).getRGB());
/* 110 */     for (Modbutton m : this.modbutton) {
/* 111 */       m.draw(mouseX, mouseY);
/*     */     }
/* 113 */     for (OptionButton o : this.optionbutton) {
/* 114 */       o.draw(mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 120 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 121 */     for (Modbutton m : this.modbutton) {
/* 122 */       m.onClick(mouseX, mouseY, mouseButton);
/*     */     }
/* 124 */     for (OptionButton o : this.optionbutton)
/* 125 */       o.onClick(mouseX, mouseY, mouseButton); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\gui\click\ClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */