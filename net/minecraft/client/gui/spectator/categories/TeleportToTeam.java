/*     */ package net.minecraft.client.gui.spectator.categories;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiSpectator;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TeleportToTeam
/*     */   implements ISpectatorMenuView, ISpectatorMenuObject
/*     */ {
/*  26 */   private final List<ISpectatorMenuObject> field_178672_a = Lists.newArrayList();
/*     */   
/*     */   public TeleportToTeam() {
/*  29 */     Minecraft minecraft = Minecraft.getMinecraft();
/*     */     
/*  31 */     for (ScorePlayerTeam scoreplayerteam : minecraft.theWorld.getScoreboard().getTeams()) {
/*  32 */       this.field_178672_a.add(new TeamSelectionObject(scoreplayerteam));
/*     */     }
/*     */   }
/*     */   
/*     */   public List<ISpectatorMenuObject> func_178669_a() {
/*  37 */     return this.field_178672_a;
/*     */   }
/*     */   
/*     */   public IChatComponent func_178670_b() {
/*  41 */     return (IChatComponent)new ChatComponentText("Select a team to teleport to");
/*     */   }
/*     */   
/*     */   public void func_178661_a(SpectatorMenu menu) {
/*  45 */     menu.func_178647_a(this);
/*     */   }
/*     */   
/*     */   public IChatComponent getSpectatorName() {
/*  49 */     return (IChatComponent)new ChatComponentText("Teleport to team member");
/*     */   }
/*     */   
/*     */   public void func_178663_a(float p_178663_1_, int alpha) {
/*  53 */     Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
/*  54 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */   }
/*     */   
/*     */   public boolean func_178662_A_() {
/*  58 */     for (ISpectatorMenuObject ispectatormenuobject : this.field_178672_a) {
/*  59 */       if (ispectatormenuobject.func_178662_A_()) {
/*  60 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   class TeamSelectionObject implements ISpectatorMenuObject {
/*     */     private final ScorePlayerTeam field_178676_b;
/*     */     private final ResourceLocation field_178677_c;
/*     */     private final List<NetworkPlayerInfo> field_178675_d;
/*     */     
/*     */     public TeamSelectionObject(ScorePlayerTeam p_i45492_2_) {
/*  73 */       this.field_178676_b = p_i45492_2_;
/*  74 */       this.field_178675_d = Lists.newArrayList();
/*     */       
/*  76 */       for (String s : p_i45492_2_.getMembershipCollection()) {
/*  77 */         NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(s);
/*     */         
/*  79 */         if (networkplayerinfo != null) {
/*  80 */           this.field_178675_d.add(networkplayerinfo);
/*     */         }
/*     */       } 
/*     */       
/*  84 */       if (!this.field_178675_d.isEmpty()) {
/*  85 */         String s1 = ((NetworkPlayerInfo)this.field_178675_d.get((new Random()).nextInt(this.field_178675_d.size()))).getGameProfile().getName();
/*  86 */         this.field_178677_c = AbstractClientPlayer.getLocationSkin(s1);
/*  87 */         AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c, s1);
/*     */       } else {
/*  89 */         this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void func_178661_a(SpectatorMenu menu) {
/*  94 */       menu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
/*     */     }
/*     */     
/*     */     public IChatComponent getSpectatorName() {
/*  98 */       return (IChatComponent)new ChatComponentText(this.field_178676_b.getTeamName());
/*     */     }
/*     */     
/*     */     public void func_178663_a(float p_178663_1_, int alpha) {
/* 102 */       int i = -1;
/* 103 */       String s = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
/*     */       
/* 105 */       if (s.length() >= 2) {
/* 106 */         i = (Minecraft.getMinecraft()).fontRendererObj.getColorCode(s.charAt(1));
/*     */       }
/*     */       
/* 109 */       if (i >= 0) {
/* 110 */         float f = (i >> 16 & 0xFF) / 255.0F;
/* 111 */         float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 112 */         float f2 = (i & 0xFF) / 255.0F;
/* 113 */         Gui.drawRect(1, 1, 15, 15, MathHelper.func_180183_b(f * p_178663_1_, f1 * p_178663_1_, f2 * p_178663_1_) | alpha << 24);
/*     */       } 
/*     */       
/* 116 */       Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
/* 117 */       GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, alpha / 255.0F);
/* 118 */       Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/* 119 */       Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/*     */     }
/*     */     
/*     */     public boolean func_178662_A_() {
/* 123 */       return !this.field_178675_d.isEmpty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\spectator\categories\TeleportToTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */