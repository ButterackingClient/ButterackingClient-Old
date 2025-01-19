/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.audio.MusicTicker;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiWinGame
/*     */   extends GuiScreen {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*  27 */   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
/*  28 */   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
/*     */   private int field_146581_h;
/*     */   private List<String> field_146582_i;
/*     */   private int field_146579_r;
/*  32 */   private float field_146578_s = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  38 */     MusicTicker musicticker = this.mc.getMusicTicker();
/*  39 */     SoundHandler soundhandler = this.mc.getSoundHandler();
/*     */     
/*  41 */     if (this.field_146581_h == 0) {
/*  42 */       musicticker.func_181557_a();
/*  43 */       musicticker.func_181558_a(MusicTicker.MusicType.CREDITS);
/*  44 */       soundhandler.resumeSounds();
/*     */     } 
/*     */     
/*  47 */     soundhandler.update();
/*  48 */     this.field_146581_h++;
/*  49 */     float f = (this.field_146579_r + height + height + 24) / this.field_146578_s;
/*     */     
/*  51 */     if (this.field_146581_h > f) {
/*  52 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  61 */     if (keyCode == 1) {
/*  62 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendRespawnPacket() {
/*  67 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*  68 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  83 */     if (this.field_146582_i == null) {
/*  84 */       this.field_146582_i = Lists.newArrayList();
/*     */       
/*     */       try {
/*  87 */         String s = "";
/*  88 */         String s1 = EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
/*  89 */         int i = 274;
/*  90 */         InputStream inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
/*  91 */         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/*  92 */         Random random = new Random(8124371L);
/*     */         
/*  94 */         while ((s = bufferedreader.readLine()) != null) {
/*     */ 
/*     */ 
/*     */           
/*  98 */           for (s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); s.contains(s1); s = String.valueOf(s2) + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3) {
/*  99 */             int j = s.indexOf(s1);
/* 100 */             String s2 = s.substring(0, j);
/* 101 */             String s3 = s.substring(j + s1.length());
/*     */           } 
/*     */           
/* 104 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 105 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 108 */         inputstream.close();
/*     */         
/* 110 */         for (int k = 0; k < 8; k++) {
/* 111 */           this.field_146582_i.add("");
/*     */         }
/*     */         
/* 114 */         inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
/* 115 */         bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/*     */         
/* 117 */         while ((s = bufferedreader.readLine()) != null) {
/* 118 */           s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
/* 119 */           s = s.replaceAll("\t", "    ");
/* 120 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 121 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 124 */         inputstream.close();
/* 125 */         this.field_146579_r = this.field_146582_i.size() * 12;
/* 126 */       } catch (Exception exception) {
/* 127 */         logger.error("Couldn't load credits", exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
/* 133 */     Tessellator tessellator = Tessellator.getInstance();
/* 134 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 135 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 136 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 137 */     int i = width;
/* 138 */     float f = 0.0F - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 139 */     float f1 = height - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 140 */     float f2 = 0.015625F;
/* 141 */     float f3 = (this.field_146581_h + p_146575_3_ - 0.0F) * 0.02F;
/* 142 */     float f4 = (this.field_146579_r + height + height + 24) / this.field_146578_s;
/* 143 */     float f5 = (f4 - 20.0F - this.field_146581_h + p_146575_3_) * 0.005F;
/*     */     
/* 145 */     if (f5 < f3) {
/* 146 */       f3 = f5;
/*     */     }
/*     */     
/* 149 */     if (f3 > 1.0F) {
/* 150 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 153 */     f3 *= f3;
/* 154 */     f3 = f3 * 96.0F / 255.0F;
/* 155 */     worldrenderer.pos(0.0D, height, this.zLevel).tex(0.0D, (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 156 */     worldrenderer.pos(i, height, this.zLevel).tex((i * f2), (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 157 */     worldrenderer.pos(i, 0.0D, this.zLevel).tex((i * f2), (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 158 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 159 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 166 */     drawWinGameScreen(mouseX, mouseY, partialTicks);
/* 167 */     Tessellator tessellator = Tessellator.getInstance();
/* 168 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 169 */     int i = 274;
/* 170 */     int j = width / 2 - i / 2;
/* 171 */     int k = height + 50;
/* 172 */     float f = -(this.field_146581_h + partialTicks) * this.field_146578_s;
/* 173 */     GlStateManager.pushMatrix();
/* 174 */     GlStateManager.translate(0.0F, f, 0.0F);
/* 175 */     this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
/* 176 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 177 */     drawTexturedModalRect(j, k, 0, 0, 155, 44);
/* 178 */     drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
/* 179 */     int l = k + 200;
/*     */     
/* 181 */     for (int i1 = 0; i1 < this.field_146582_i.size(); i1++) {
/* 182 */       if (i1 == this.field_146582_i.size() - 1) {
/* 183 */         float f1 = l + f - (height / 2 - 6);
/*     */         
/* 185 */         if (f1 < 0.0F) {
/* 186 */           GlStateManager.translate(0.0F, -f1, 0.0F);
/*     */         }
/*     */       } 
/*     */       
/* 190 */       if (l + f + 12.0F + 8.0F > 0.0F && l + f < height) {
/* 191 */         String s = this.field_146582_i.get(i1);
/*     */         
/* 193 */         if (s.startsWith("[C]")) {
/* 194 */           this.fontRendererObj.drawStringWithShadow(s.substring(3), (j + (i - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), l, 16777215);
/*     */         } else {
/* 196 */           this.fontRendererObj.fontRandom.setSeed(i1 * 4238972211L + (this.field_146581_h / 4));
/* 197 */           this.fontRendererObj.drawStringWithShadow(s, j, l, 16777215);
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       l += 12;
/*     */     } 
/*     */     
/* 204 */     GlStateManager.popMatrix();
/* 205 */     this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
/* 206 */     GlStateManager.enableBlend();
/* 207 */     GlStateManager.blendFunc(0, 769);
/* 208 */     int j1 = width;
/* 209 */     int k1 = height;
/* 210 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 211 */     worldrenderer.pos(0.0D, k1, this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 212 */     worldrenderer.pos(j1, k1, this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 213 */     worldrenderer.pos(j1, 0.0D, this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 214 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 215 */     tessellator.draw();
/* 216 */     GlStateManager.disableBlend();
/* 217 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiWinGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */