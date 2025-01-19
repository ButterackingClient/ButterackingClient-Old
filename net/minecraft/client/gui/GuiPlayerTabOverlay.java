/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class GuiPlayerTabOverlay
/*     */   extends Gui {
/*  26 */   private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator(null));
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final GuiIngame guiIngame;
/*     */   
/*     */   private IChatComponent footer;
/*     */   
/*     */   private IChatComponent header;
/*     */   
/*     */   private long lastTimeOpened;
/*     */   
/*     */   private boolean isBeingRendered;
/*     */ 
/*     */   
/*     */   public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
/*  43 */     this.mc = mcIn;
/*  44 */     this.guiIngame = guiIngameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/*  51 */     return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerList(boolean willBeRendered) {
/*  59 */     if (willBeRendered && !this.isBeingRendered) {
/*  60 */       this.lastTimeOpened = Minecraft.getSystemTime();
/*     */     }
/*     */     
/*  63 */     this.isBeingRendered = willBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
/*     */     int l;
/*  70 */     NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
/*  71 */     List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
/*  72 */     int i = 0;
/*  73 */     int j = 0;
/*     */     
/*  75 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/*  76 */       int k = this.mc.fontRendererObj.getStringWidth(getPlayerName(networkplayerinfo));
/*  77 */       i = Math.max(i, k);
/*     */       
/*  79 */       if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/*  80 */         k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
/*  81 */         j = Math.max(j, k);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     list = list.subList(0, Math.min(list.size(), 80));
/*  86 */     int l3 = list.size();
/*  87 */     int i4 = l3;
/*     */     
/*     */     int j4;
/*  90 */     for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
/*  91 */       j4++;
/*     */     }
/*     */     
/*  94 */     boolean flag = !(!this.mc.isIntegratedServerRunning() && !this.mc.getNetHandler().getNetworkManager().getIsencrypted());
/*     */ 
/*     */     
/*  97 */     if (scoreObjectiveIn != null) {
/*  98 */       if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/*  99 */         l = 90;
/*     */       } else {
/* 101 */         l = j;
/*     */       } 
/*     */     } else {
/* 104 */       l = 0;
/*     */     } 
/*     */     
/* 107 */     int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
/* 108 */     int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
/* 109 */     int k1 = 10;
/* 110 */     int l1 = i1 * j4 + (j4 - 1) * 5;
/* 111 */     List<String> list1 = null;
/* 112 */     List<String> list2 = null;
/*     */     
/* 114 */     if (this.header != null) {
/* 115 */       list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
/*     */       
/* 117 */       for (String s : list1) {
/* 118 */         l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
/*     */       }
/*     */     } 
/*     */     
/* 122 */     if (this.footer != null) {
/* 123 */       list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
/*     */       
/* 125 */       for (String s2 : list2) {
/* 126 */         l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s2));
/*     */       }
/*     */     } 
/*     */     
/* 130 */     if (list1 != null) {
/* 131 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT, -2147483648);
/*     */       
/* 133 */       for (String s3 : list1) {
/* 134 */         int i2 = this.mc.fontRendererObj.getStringWidth(s3);
/* 135 */         this.mc.fontRendererObj.drawStringWithShadow(s3, (width / 2 - i2 / 2), k1, -1);
/* 136 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */       
/* 139 */       k1++;
/*     */     } 
/*     */     
/* 142 */     drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, -2147483648);
/*     */     
/* 144 */     for (int k4 = 0; k4 < l3; k4++) {
/* 145 */       int l4 = k4 / i4;
/* 146 */       int i5 = k4 % i4;
/* 147 */       int j2 = j1 + l4 * i1 + l4 * 5;
/* 148 */       int k2 = k1 + i5 * 9;
/* 149 */       drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
/* 150 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 151 */       GlStateManager.enableAlpha();
/* 152 */       GlStateManager.enableBlend();
/* 153 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */       
/* 155 */       if (k4 < list.size()) {
/* 156 */         NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
/* 157 */         String s1 = getPlayerName(networkplayerinfo1);
/* 158 */         GameProfile gameprofile = networkplayerinfo1.getGameProfile();
/*     */         
/* 160 */         if (flag) {
/* 161 */           EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
/* 162 */           boolean flag1 = (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm")));
/* 163 */           this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
/* 164 */           int l2 = 8 + (flag1 ? 8 : 0);
/* 165 */           int i3 = 8 * (flag1 ? -1 : 1);
/* 166 */           Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, l2, 8, i3, 8, 8, 64.0F, 64.0F);
/*     */           
/* 168 */           if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
/* 169 */             int j3 = 8 + (flag1 ? 8 : 0);
/* 170 */             int k3 = 8 * (flag1 ? -1 : 1);
/* 171 */             Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, j3, 8, k3, 8, 8, 64.0F, 64.0F);
/*     */           } 
/*     */           
/* 174 */           j2 += 9;
/*     */         } 
/*     */         
/* 177 */         if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR) {
/* 178 */           s1 = EnumChatFormatting.ITALIC + s1;
/* 179 */           this.mc.fontRendererObj.drawStringWithShadow(s1, j2, k2, -1862270977);
/*     */         } else {
/* 181 */           this.mc.fontRendererObj.drawStringWithShadow(s1, j2, k2, -1);
/*     */         } 
/*     */         
/* 184 */         if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR) {
/* 185 */           int k5 = j2 + i + 1;
/* 186 */           int l5 = k5 + l;
/*     */           
/* 188 */           if (l5 - k5 > 5) {
/* 189 */             drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
/*     */           }
/*     */         } 
/*     */         
/* 193 */         drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (list2 != null) {
/* 198 */       k1 = k1 + i4 * 9 + 1;
/* 199 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, -2147483648);
/*     */       
/* 201 */       for (String s4 : list2) {
/* 202 */         int j5 = this.mc.fontRendererObj.getStringWidth(s4);
/* 203 */         this.mc.fontRendererObj.drawStringWithShadow(s4, (width / 2 - j5 / 2), k1, -1);
/* 204 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
/* 210 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 211 */     this.mc.getTextureManager().bindTexture(icons);
/* 212 */     int i = 0;
/* 213 */     int j = 0;
/*     */     
/* 215 */     if (networkPlayerInfoIn.getResponseTime() < 0) {
/* 216 */       j = 5;
/* 217 */     } else if (networkPlayerInfoIn.getResponseTime() < 150) {
/* 218 */       j = 0;
/* 219 */     } else if (networkPlayerInfoIn.getResponseTime() < 300) {
/* 220 */       j = 1;
/* 221 */     } else if (networkPlayerInfoIn.getResponseTime() < 600) {
/* 222 */       j = 2;
/* 223 */     } else if (networkPlayerInfoIn.getResponseTime() < 1000) {
/* 224 */       j = 3;
/*     */     } else {
/* 226 */       j = 4;
/*     */     } 
/*     */     
/* 229 */     this.zLevel += 100.0F;
/* 230 */     drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + i * 10, 176 + j * 8, 10, 8);
/* 231 */     this.zLevel -= 100.0F;
/*     */   }
/*     */   
/*     */   private void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
/* 235 */     int i = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();
/*     */     
/* 237 */     if (p_175247_1_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/* 238 */       this.mc.getTextureManager().bindTexture(icons);
/*     */       
/* 240 */       if (this.lastTimeOpened == p_175247_6_.func_178855_p()) {
/* 241 */         if (i < p_175247_6_.func_178835_l()) {
/* 242 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 243 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 20));
/* 244 */         } else if (i > p_175247_6_.func_178835_l()) {
/* 245 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 246 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 10));
/*     */         } 
/*     */       }
/*     */       
/* 250 */       if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.lastTimeOpened != p_175247_6_.func_178855_p()) {
/* 251 */         p_175247_6_.func_178836_b(i);
/* 252 */         p_175247_6_.func_178857_c(i);
/* 253 */         p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/*     */       } 
/*     */       
/* 256 */       p_175247_6_.func_178843_c(this.lastTimeOpened);
/* 257 */       p_175247_6_.func_178836_b(i);
/* 258 */       int j = MathHelper.ceiling_float_int(Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
/* 259 */       int k = Math.max(MathHelper.ceiling_float_int((i / 2)), Math.max(MathHelper.ceiling_float_int((p_175247_6_.func_178860_m() / 2)), 10));
/* 260 */       boolean flag = (p_175247_6_.func_178858_o() > this.guiIngame.getUpdateCounter() && (p_175247_6_.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L);
/*     */       
/* 262 */       if (j > 0) {
/* 263 */         float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / k, 9.0F);
/*     */         
/* 265 */         if (f > 3.0F) {
/* 266 */           for (int l = j; l < k; l++) {
/* 267 */             drawTexturedModalRect(p_175247_4_ + l * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */           }
/*     */           
/* 270 */           for (int j1 = 0; j1 < j; j1++) {
/* 271 */             drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */             
/* 273 */             if (flag) {
/* 274 */               if (j1 * 2 + 1 < p_175247_6_.func_178860_m()) {
/* 275 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 70, 0, 9, 9);
/*     */               }
/*     */               
/* 278 */               if (j1 * 2 + 1 == p_175247_6_.func_178860_m()) {
/* 279 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 79, 0, 9, 9);
/*     */               }
/*     */             } 
/*     */             
/* 283 */             if (j1 * 2 + 1 < i) {
/* 284 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 160 : 52, 0, 9, 9);
/*     */             }
/*     */             
/* 287 */             if (j1 * 2 + 1 == i)
/* 288 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 169 : 61, 0, 9, 9); 
/*     */           } 
/*     */         } else {
/*     */           String str;
/* 292 */           float f1 = MathHelper.clamp_float(i / 20.0F, 0.0F, 1.0F);
/* 293 */           int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
/* 294 */           float f2 = i / 2.0F;
/*     */           
/* 296 */           if (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(String.valueOf(f2) + "hp") >= p_175247_4_) {
/* 297 */             str = String.valueOf(f2) + "hp";
/*     */           }
/*     */           
/* 300 */           this.mc.fontRendererObj.drawStringWithShadow(str, ((p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRendererObj.getStringWidth(str) / 2), p_175247_2_, i1);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 304 */       String s1 = EnumChatFormatting.YELLOW + i;
/* 305 */       this.mc.fontRendererObj.drawStringWithShadow(s1, (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s1)), p_175247_2_, 16777215);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFooter(IChatComponent footerIn) {
/* 310 */     this.footer = footerIn;
/*     */   }
/*     */   
/*     */   public void setHeader(IChatComponent headerIn) {
/* 314 */     this.header = headerIn;
/*     */   }
/*     */   
/*     */   public void resetFooterHeader() {
/* 318 */     this.header = null;
/* 319 */     this.footer = null;
/*     */   }
/*     */   
/*     */   static class PlayerComparator
/*     */     implements Comparator<NetworkPlayerInfo> {
/*     */     private PlayerComparator() {}
/*     */     
/*     */     public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 327 */       ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
/* 328 */       ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
/* 329 */       return ComparisonChain.start().compareTrueFirst((p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR), (p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */