/*     */ package net.optifine.gui;
/*     */ 
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ 
/*     */ public class GuiScreenCapeOF
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   private String message;
/*     */   private long messageHideTimeMs;
/*     */   private String linkUrl;
/*     */   private GuiButtonOF buttonCopyLink;
/*     */   private FontRenderer fontRenderer;
/*     */   
/*     */   public GuiScreenCapeOF(GuiScreen parentScreenIn) {
/*  26 */     this.fontRenderer = (Config.getMinecraft()).fontRendererObj;
/*  27 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     int i = 0;
/*  36 */     this.title = I18n.format("of.options.capeOF.title", new Object[0]);
/*  37 */     i += 2;
/*  38 */     this.buttonList.add(new GuiButtonOF(210, width / 2 - 155, height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.openEditor", new Object[0])));
/*  39 */     this.buttonList.add(new GuiButtonOF(220, width / 2 - 155 + 160, height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.reloadCape", new Object[0])));
/*  40 */     i += 6;
/*  41 */     this.buttonCopyLink = new GuiButtonOF(230, width / 2 - 100, height / 6 + 24 * (i >> 1), 200, 20, I18n.format("of.options.capeOF.copyEditorLink", new Object[0]));
/*  42 */     this.buttonCopyLink.visible = (this.linkUrl != null);
/*  43 */     this.buttonList.add(this.buttonCopyLink);
/*  44 */     i += 4;
/*  45 */     this.buttonList.add(new GuiButtonOF(200, width / 2 - 100, height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  52 */     if (button.enabled) {
/*  53 */       if (button.id == 200) {
/*  54 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*     */       
/*  57 */       if (button.id == 210) {
/*     */         try {
/*  59 */           String s = this.mc.getSession().getProfile().getName();
/*  60 */           String s1 = this.mc.getSession().getProfile().getId().toString().replace("-", "");
/*  61 */           String s2 = this.mc.getSession().getToken();
/*  62 */           Random random = new Random();
/*  63 */           Random random1 = new Random(System.identityHashCode(new Object()));
/*  64 */           BigInteger biginteger = new BigInteger(128, random);
/*  65 */           BigInteger biginteger1 = new BigInteger(128, random1);
/*  66 */           BigInteger biginteger2 = biginteger.xor(biginteger1);
/*  67 */           String s3 = biginteger2.toString(16);
/*  68 */           this.mc.getSessionService().joinServer(this.mc.getSession().getProfile(), s2, s3);
/*  69 */           String s4 = "https://optifine.net/capeChange?u=" + s1 + "&n=" + s + "&s=" + s3;
/*  70 */           boolean flag = Config.openWebLink(new URI(s4));
/*     */           
/*  72 */           if (flag) {
/*  73 */             showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
/*     */           } else {
/*  75 */             showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
/*  76 */             setLinkUrl(s4);
/*     */           } 
/*  78 */         } catch (InvalidCredentialsException invalidcredentialsexception) {
/*  79 */           Config.showGuiMessage(I18n.format("of.message.capeOF.error1", new Object[0]), I18n.format("of.message.capeOF.error2", new Object[] { invalidcredentialsexception.getMessage() }));
/*  80 */           Config.warn("Mojang authentication failed");
/*  81 */           Config.warn(String.valueOf(invalidcredentialsexception.getClass().getName()) + ": " + invalidcredentialsexception.getMessage());
/*  82 */         } catch (Exception exception) {
/*  83 */           Config.warn("Error opening OptiFine cape link");
/*  84 */           Config.warn(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/*     */         } 
/*     */       }
/*     */       
/*  88 */       if (button.id == 220) {
/*  89 */         showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
/*     */         
/*  91 */         if (this.mc.thePlayer != null) {
/*  92 */           long i = 15000L;
/*  93 */           long j = System.currentTimeMillis() + i;
/*  94 */           this.mc.thePlayer.setReloadCapeTimeMs(j);
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       if (button.id == 230 && this.linkUrl != null) {
/*  99 */         setClipboardString(this.linkUrl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showMessage(String msg, long timeMs) {
/* 105 */     this.message = msg;
/* 106 */     this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
/* 107 */     setLinkUrl((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 114 */     drawDefaultBackground();
/* 115 */     drawCenteredString(this.fontRenderer, this.title, width / 2, 20, 16777215);
/*     */     
/* 117 */     if (this.message != null) {
/* 118 */       drawCenteredString(this.fontRenderer, this.message, width / 2, height / 6 + 60, 16777215);
/*     */       
/* 120 */       if (System.currentTimeMillis() > this.messageHideTimeMs) {
/* 121 */         this.message = null;
/* 122 */         setLinkUrl((String)null);
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public void setLinkUrl(String linkUrl) {
/* 130 */     this.linkUrl = linkUrl;
/* 131 */     this.buttonCopyLink.visible = (linkUrl != null);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiScreenCapeOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */