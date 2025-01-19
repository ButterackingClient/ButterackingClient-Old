/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.IOException;
/*     */ import java.net.IDN;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenAddServer
/*     */   extends GuiScreen {
/*     */   private final GuiScreen parentScreen;
/*     */   private final ServerData serverData;
/*     */   private GuiTextField serverIPField;
/*     */   private GuiTextField serverNameField;
/*     */   private GuiButton serverResourcePacks;
/*     */   
/*  18 */   private Predicate<String> field_181032_r = new Predicate<String>() {
/*     */       public boolean apply(String p_apply_1_) {
/*  20 */         if (p_apply_1_.length() == 0) {
/*  21 */           return true;
/*     */         }
/*  23 */         String[] astring = p_apply_1_.split(":");
/*     */         
/*  25 */         if (astring.length == 0) {
/*  26 */           return true;
/*     */         }
/*     */         try {
/*  29 */           String s = IDN.toASCII(astring[0]);
/*  30 */           return true;
/*  31 */         } catch (IllegalArgumentException var4) {
/*  32 */           return false;
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenAddServer(GuiScreen p_i1033_1_, ServerData p_i1033_2_) {
/*  40 */     this.parentScreen = p_i1033_1_;
/*  41 */     this.serverData = p_i1033_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  48 */     this.serverNameField.updateCursorCounter();
/*  49 */     this.serverIPField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  57 */     Keyboard.enableRepeatEvents(true);
/*  58 */     this.buttonList.clear();
/*  59 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
/*  60 */     this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
/*  61 */     this.buttonList.add(this.serverResourcePacks = new GuiButton(2, width / 2 - 100, height / 4 + 72, String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText()));
/*  62 */     this.serverNameField = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, 66, 200, 20);
/*  63 */     this.serverNameField.setFocused(true);
/*  64 */     this.serverNameField.setText(this.serverData.serverName);
/*  65 */     this.serverIPField = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, 106, 200, 20);
/*  66 */     this.serverIPField.setMaxStringLength(128);
/*  67 */     this.serverIPField.setText(this.serverData.serverIP);
/*  68 */     this.serverIPField.setValidator(this.field_181032_r);
/*  69 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.serverIPField.getText().length() > 0 && (this.serverIPField.getText().split(":")).length > 0 && this.serverNameField.getText().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  76 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  83 */     if (button.enabled) {
/*  84 */       if (button.id == 2) {
/*  85 */         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % (ServerData.ServerResourceMode.values()).length]);
/*  86 */         this.serverResourcePacks.displayString = String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText();
/*  87 */       } else if (button.id == 1) {
/*  88 */         this.parentScreen.confirmClicked(false, 0);
/*  89 */       } else if (button.id == 0) {
/*  90 */         this.serverData.serverName = this.serverNameField.getText();
/*  91 */         this.serverData.serverIP = this.serverIPField.getText();
/*  92 */         this.parentScreen.confirmClicked(true, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 102 */     this.serverNameField.textboxKeyTyped(typedChar, keyCode);
/* 103 */     this.serverIPField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 105 */     if (keyCode == 15) {
/* 106 */       this.serverNameField.setFocused(!this.serverNameField.isFocused());
/* 107 */       this.serverIPField.setFocused(!this.serverIPField.isFocused());
/*     */     } 
/*     */     
/* 110 */     if (keyCode == 28 || keyCode == 156) {
/* 111 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 114 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.serverIPField.getText().length() > 0 && (this.serverIPField.getText().split(":")).length > 0 && this.serverNameField.getText().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 121 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 122 */     this.serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
/* 123 */     this.serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 130 */     drawDefaultBackground();
/* 131 */     drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), width / 2, 17, 16777215);
/* 132 */     drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), width / 2 - 100, 53, 10526880);
/* 133 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 94, 10526880);
/* 134 */     this.serverNameField.drawTextBox();
/* 135 */     this.serverIPField.drawTextBox();
/* 136 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenAddServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */