/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import client.Client;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.optifine.CustomColors;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiIngame
/*      */   extends Gui
/*      */ {
/* 1285 */   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
/* 1286 */   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
/* 1287 */   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png"); private final Random rand; private final Minecraft mc; private final RenderItem itemRenderer; private final GuiNewChat persistantChatGUI; private final GuiStreamIndicator streamIndicator; private int updateCounter; private String recordPlaying; private int recordPlayingUpFor; private boolean recordIsPlaying; public float prevVignetteBrightness; private int remainingHighlightTicks;
/*      */   private ItemStack highlightingItemStack;
/*      */   
/*      */   public GuiIngame(Minecraft mcIn) {
/* 1291 */     this.rand = new Random();
/* 1292 */     this.recordPlaying = "";
/* 1293 */     this.prevVignetteBrightness = 1.0F;
/* 1294 */     this.displayedTitle = "";
/* 1295 */     this.displayedSubTitle = "";
/* 1296 */     this.playerHealth = 0;
/* 1297 */     this.lastPlayerHealth = 0;
/* 1298 */     this.lastSystemTime = 0L;
/* 1299 */     this.healthUpdateCounter = 0L;
/* 1300 */     this.mc = mcIn;
/* 1301 */     this.itemRenderer = mcIn.getRenderItem();
/* 1302 */     this.overlayDebug = new GuiOverlayDebug(mcIn);
/* 1303 */     this.spectatorGui = new GuiSpectator(mcIn);
/* 1304 */     this.persistantChatGUI = new GuiNewChat(mcIn);
/* 1305 */     this.streamIndicator = new GuiStreamIndicator(mcIn);
/* 1306 */     this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
/* 1307 */     setDefaultTitlesTimes();
/*      */   }
/*      */   private final GuiOverlayDebug overlayDebug; private final GuiSpectator spectatorGui; private final GuiPlayerTabOverlay overlayPlayerList; private int titlesTimer; private String displayedTitle; private String displayedSubTitle; private int titleFadeIn; private int titleDisplayTime; private int titleFadeOut; private int playerHealth; private int lastPlayerHealth; private long lastSystemTime; private long healthUpdateCounter;
/*      */   public void setDefaultTitlesTimes() {
/* 1311 */     this.titleFadeIn = 10;
/* 1312 */     this.titleDisplayTime = 70;
/* 1313 */     this.titleFadeOut = 20;
/*      */   }
/*      */   
/*      */   public void renderGameOverlay(float partialTicks) {
/* 1317 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1318 */     int i = scaledresolution.getScaledWidth();
/* 1319 */     int j = scaledresolution.getScaledHeight();
/* 1320 */     this.mc.entityRenderer.setupOverlayRendering();
/* 1321 */     GlStateManager.enableBlend();
/* 1322 */     if (Config.isVignetteEnabled()) {
/* 1323 */       renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
/*      */     } else {
/* 1325 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/* 1327 */     ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);
/* 1328 */     if (this.mc.gameSettings.showDebugInfo == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
/* 1329 */       renderPumpkinOverlay(scaledresolution);
/*      */     }
/* 1331 */     if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/* 1332 */       float f = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/* 1333 */       if (f > 0.0F) {
/* 1334 */         renderPortal(f, scaledresolution);
/*      */       }
/*      */     } 
/* 1337 */     if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/* 1338 */       renderNausea();
/*      */     }
/* 1340 */     if (this.mc.playerController.isSpectator()) {
/* 1341 */       this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
/*      */     } else {
/* 1343 */       renderTooltip(scaledresolution, partialTicks);
/*      */     } 
/* 1345 */     if (!(this.mc.currentScreen instanceof client.hud.HUDConfigScreen)) {
/* 1346 */       (Client.getInstance()).hudManager.renderMods();
/*      */     }
/* 1348 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1349 */     this.mc.getTextureManager().bindTexture(icons);
/* 1350 */     GlStateManager.enableBlend();
/* 1351 */     if (showCrosshair()) {
/* 1352 */       GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
/* 1353 */       GlStateManager.enableAlpha();
/* 1354 */       drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
/*      */     } 
/* 1356 */     GlStateManager.enableAlpha();
/* 1357 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1358 */     this.mc.mcProfiler.startSection("bossHealth");
/* 1359 */     renderBossHealth();
/* 1360 */     this.mc.mcProfiler.endSection();
/* 1361 */     if (this.mc.playerController.shouldDrawHUD()) {
/* 1362 */       renderPlayerStats(scaledresolution);
/*      */     }
/* 1364 */     GlStateManager.disableBlend();
/* 1365 */     if (this.mc.thePlayer.getSleepTimer() > 0) {
/* 1366 */       this.mc.mcProfiler.startSection("sleep");
/* 1367 */       GlStateManager.disableDepth();
/* 1368 */       GlStateManager.disableAlpha();
/* 1369 */       int j2 = this.mc.thePlayer.getSleepTimer();
/* 1370 */       float f2 = j2 / 100.0F;
/* 1371 */       if (f2 > 1.0F) {
/* 1372 */         f2 = 1.0F - (j2 - 100) / 10.0F;
/*      */       }
/* 1374 */       int k = (int)(220.0F * f2) << 24 | 0x101020;
/* 1375 */       Gui.drawRect(0, 0, i, j, k);
/* 1376 */       GlStateManager.enableAlpha();
/* 1377 */       GlStateManager.enableDepth();
/* 1378 */       this.mc.mcProfiler.endSection();
/*      */     } 
/* 1380 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1381 */     int k2 = i / 2 - 91;
/* 1382 */     if (this.mc.thePlayer.isRidingHorse()) {
/* 1383 */       renderHorseJumpBar(scaledresolution, k2);
/* 1384 */     } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
/* 1385 */       renderExpBar(scaledresolution, k2);
/*      */     } 
/* 1387 */     if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
/* 1388 */       renderSelectedItem(scaledresolution);
/* 1389 */     } else if (this.mc.thePlayer.isSpectator()) {
/* 1390 */       this.spectatorGui.renderSelectedItem(scaledresolution);
/*      */     } 
/* 1392 */     if (this.mc.isDemo()) {
/* 1393 */       renderDemo(scaledresolution);
/*      */     }
/* 1395 */     if (this.mc.gameSettings.showDebugProfilerChart) {
/* 1396 */       this.overlayDebug.renderDebugInfo(scaledresolution);
/*      */     }
/* 1398 */     if (this.recordPlayingUpFor > 0) {
/* 1399 */       this.mc.mcProfiler.startSection("overlayMessage");
/* 1400 */       float f3 = this.recordPlayingUpFor - partialTicks;
/* 1401 */       int l1 = (int)(f3 * 255.0F / 20.0F);
/* 1402 */       if (l1 > 255) {
/* 1403 */         l1 = 255;
/*      */       }
/* 1405 */       if (l1 > 8) {
/* 1406 */         GlStateManager.pushMatrix();
/* 1407 */         GlStateManager.translate((i / 2), (j - 68), 0.0F);
/* 1408 */         GlStateManager.enableBlend();
/* 1409 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1410 */         int m = 16777215;
/* 1411 */         if (this.recordIsPlaying) {
/* 1412 */           m = MathHelper.hsvToRGB(f3 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*      */         }
/* 1414 */         getFontRenderer().drawString(this.recordPlaying, -getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4, m + (l1 << 24 & 0xFF000000));
/* 1415 */         GlStateManager.disableBlend();
/* 1416 */         GlStateManager.popMatrix();
/*      */       } 
/* 1418 */       this.mc.mcProfiler.endSection();
/*      */     } 
/* 1420 */     if (this.titlesTimer > 0) {
/* 1421 */       this.mc.mcProfiler.startSection("titleAndSubtitle");
/* 1422 */       float f4 = this.titlesTimer - partialTicks;
/* 1423 */       int i2 = 255;
/* 1424 */       if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
/* 1425 */         float f5 = (this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f4;
/* 1426 */         i2 = (int)(f5 * 255.0F / this.titleFadeIn);
/*      */       } 
/* 1428 */       if (this.titlesTimer <= this.titleFadeOut) {
/* 1429 */         i2 = (int)(f4 * 255.0F / this.titleFadeOut);
/*      */       }
/* 1431 */       i2 = MathHelper.clamp_int(i2, 0, 255);
/* 1432 */       if (i2 > 8) {
/* 1433 */         GlStateManager.pushMatrix();
/* 1434 */         GlStateManager.translate((i / 2), (j / 2), 0.0F);
/* 1435 */         GlStateManager.enableBlend();
/* 1436 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1437 */         GlStateManager.pushMatrix();
/* 1438 */         GlStateManager.scale(4.0F, 4.0F, 4.0F);
/* 1439 */         int j3 = i2 << 24 & 0xFF000000;
/* 1440 */         getFontRenderer().drawString(this.displayedTitle, (-getFontRenderer().getStringWidth(this.displayedTitle) / 2), -10.0F, 0xFFFFFF | j3, true);
/* 1441 */         GlStateManager.popMatrix();
/* 1442 */         GlStateManager.pushMatrix();
/* 1443 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 1444 */         getFontRenderer().drawString(this.displayedSubTitle, (-getFontRenderer().getStringWidth(this.displayedSubTitle) / 2), 5.0F, 0xFFFFFF | j3, true);
/* 1445 */         GlStateManager.popMatrix();
/* 1446 */         GlStateManager.disableBlend();
/* 1447 */         GlStateManager.popMatrix();
/*      */       } 
/* 1449 */       this.mc.mcProfiler.endSection();
/*      */     } 
/* 1451 */     Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
/* 1452 */     ScoreObjective scoreobjective = null;
/* 1453 */     ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
/* 1454 */     if (scoreplayerteam != null) {
/* 1455 */       int i3 = scoreplayerteam.getChatFormat().getColorIndex();
/* 1456 */       if (i3 >= 0) {
/* 1457 */         scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i3);
/*      */       }
/*      */     } 
/* 1460 */     ScoreObjective scoreobjective2 = (scoreobjective != null) ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
/* 1461 */     if (scoreobjective2 != null) {
/* 1462 */       renderScoreboard(scoreobjective2, scaledresolution);
/*      */     }
/* 1464 */     GlStateManager.enableBlend();
/* 1465 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1466 */     GlStateManager.disableAlpha();
/* 1467 */     GlStateManager.pushMatrix();
/* 1468 */     GlStateManager.translate(0.0F, (j - 48), 0.0F);
/* 1469 */     this.mc.mcProfiler.startSection("chat");
/* 1470 */     this.persistantChatGUI.drawChat(this.updateCounter);
/* 1471 */     this.mc.mcProfiler.endSection();
/* 1472 */     GlStateManager.popMatrix();
/* 1473 */     scoreobjective2 = scoreboard.getObjectiveInDisplaySlot(0);
/* 1474 */     if (this.mc.gameSettings.keyBindCommand.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || scoreobjective2 != null)) {
/* 1475 */       this.overlayPlayerList.updatePlayerList(true);
/* 1476 */       this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective2);
/*      */     } else {
/* 1478 */       this.overlayPlayerList.updatePlayerList(false);
/*      */     } 
/* 1480 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1481 */     GlStateManager.disableLighting();
/* 1482 */     GlStateManager.enableAlpha();
/*      */   }
/*      */   
/*      */   protected void renderTooltip(ScaledResolution sr, float partialTicks) {
/* 1486 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/* 1487 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1488 */       this.mc.getTextureManager().bindTexture(widgetsTexPath);
/* 1489 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/* 1490 */       int i = sr.getScaledWidth() / 2;
/* 1491 */       float f = this.zLevel;
/* 1492 */       this.zLevel = -90.0F;
/* 1493 */       drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
/* 1494 */       drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
/* 1495 */       this.zLevel = f;
/* 1496 */       GlStateManager.enableRescaleNormal();
/* 1497 */       GlStateManager.enableBlend();
/* 1498 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1499 */       RenderHelper.enableGUIStandardItemLighting();
/* 1500 */       for (int j = 0; j < 9; j++) {
/* 1501 */         int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
/* 1502 */         int l = sr.getScaledHeight() - 16 - 3;
/* 1503 */         renderHotbarItem(j, k, l, partialTicks, entityplayer);
/*      */       } 
/* 1505 */       RenderHelper.disableStandardItemLighting();
/* 1506 */       GlStateManager.disableRescaleNormal();
/* 1507 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderHorseJumpBar(ScaledResolution scaledRes, int x) {
/* 1512 */     this.mc.mcProfiler.startSection("jumpBar");
/* 1513 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/* 1514 */     float f = this.mc.thePlayer.getHorseJumpPower();
/* 1515 */     int i = 182;
/* 1516 */     int j = (int)(f * 183.0F);
/* 1517 */     int k = scaledRes.getScaledHeight() - 32 + 3;
/* 1518 */     drawTexturedModalRect(x, k, 0, 84, 182, 5);
/* 1519 */     if (j > 0) {
/* 1520 */       drawTexturedModalRect(x, k, 0, 89, j, 5);
/*      */     }
/* 1522 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderExpBar(ScaledResolution scaledRes, int x) {
/* 1526 */     this.mc.mcProfiler.startSection("expBar");
/* 1527 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/* 1528 */     int i = this.mc.thePlayer.xpBarCap();
/* 1529 */     if (i > 0) {
/* 1530 */       int j = 182;
/* 1531 */       int k = (int)(this.mc.thePlayer.experience * 183.0F);
/* 1532 */       int l = scaledRes.getScaledHeight() - 32 + 3;
/* 1533 */       drawTexturedModalRect(x, l, 0, 64, 182, 5);
/* 1534 */       if (k > 0) {
/* 1535 */         drawTexturedModalRect(x, l, 0, 69, k, 5);
/*      */       }
/*      */     } 
/* 1538 */     this.mc.mcProfiler.endSection();
/* 1539 */     if (this.mc.thePlayer.experienceLevel > 0) {
/* 1540 */       this.mc.mcProfiler.startSection("expLevel");
/* 1541 */       int k2 = 8453920;
/* 1542 */       if (Config.isCustomColors()) {
/* 1543 */         k2 = CustomColors.getExpBarTextColor(k2);
/*      */       }
/* 1545 */       int j = this.mc.thePlayer.experienceLevel;
/* 1546 */       int l2 = (scaledRes.getScaledWidth() - getFontRenderer().getStringWidth(j)) / 2;
/* 1547 */       int i2 = scaledRes.getScaledHeight() - 31 - 4;
/* 1548 */       int j2 = 0;
/* 1549 */       getFontRenderer().drawString(j, l2 + 1, i2, 0);
/* 1550 */       getFontRenderer().drawString(j, l2 - 1, i2, 0);
/* 1551 */       getFontRenderer().drawString(j, l2, i2 + 1, 0);
/* 1552 */       getFontRenderer().drawString(j, l2, i2 - 1, 0);
/* 1553 */       getFontRenderer().drawString(j, l2, i2, k2);
/* 1554 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderSelectedItem(ScaledResolution scaledRes) {
/* 1559 */     this.mc.mcProfiler.startSection("selectedItemName");
/* 1560 */     if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
/* 1561 */       String s = this.highlightingItemStack.getDisplayName();
/* 1562 */       if (this.highlightingItemStack.hasDisplayName()) {
/* 1563 */         s = EnumChatFormatting.ITALIC + s;
/*      */       }
/* 1565 */       int i = (scaledRes.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/* 1566 */       int j = scaledRes.getScaledHeight() - 59;
/* 1567 */       if (!this.mc.playerController.shouldDrawHUD()) {
/* 1568 */         j += 14;
/*      */       }
/* 1570 */       int k = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/* 1571 */       if (k > 255) {
/* 1572 */         k = 255;
/*      */       }
/* 1574 */       if (k > 0) {
/* 1575 */         GlStateManager.pushMatrix();
/* 1576 */         GlStateManager.enableBlend();
/* 1577 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1578 */         getFontRenderer().drawStringWithShadow(s, i, j, 16777215 + (k << 24));
/* 1579 */         GlStateManager.disableBlend();
/* 1580 */         GlStateManager.popMatrix();
/*      */       } 
/*      */     } 
/* 1583 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderDemo(ScaledResolution scaledRes) {
/* 1587 */     this.mc.mcProfiler.startSection("demo");
/* 1588 */     String s = "";
/* 1589 */     if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
/* 1590 */       s = I18n.format("demo.demoExpired", new Object[0]);
/*      */     } else {
/* 1592 */       s = I18n.format("demo.remainingTime", new Object[] { StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())) });
/*      */     } 
/* 1594 */     int i = getFontRenderer().getStringWidth(s);
/* 1595 */     getFontRenderer().drawStringWithShadow(s, (scaledRes.getScaledWidth() - i - 10), 5.0F, 16777215);
/* 1596 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected boolean showCrosshair() {
/* 1600 */     if (this.mc.gameSettings.showDebugProfilerChart && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
/* 1601 */       return false;
/*      */     }
/* 1603 */     if (!this.mc.playerController.isSpectator()) {
/* 1604 */       return true;
/*      */     }
/* 1606 */     if (this.mc.pointedEntity != null) {
/* 1607 */       return true;
/*      */     }
/* 1609 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 1610 */       BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1611 */       if (this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory) {
/* 1612 */         return true;
/*      */       }
/*      */     } 
/* 1615 */     return false;
/*      */   }
/*      */   
/*      */   public void renderStreamIndicator(ScaledResolution scaledRes) {
/* 1619 */     this.streamIndicator.render(scaledRes.getScaledWidth() - 10, 10);
/*      */   }
/*      */   
/*      */   private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
/* 1623 */     Scoreboard scoreboard = objective.getScoreboard();
/* 1624 */     Collection<Score> collection = scoreboard.getSortedScores(objective);
/* 1625 */     List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
/*      */             public boolean apply(Score p_apply_1_) {
/* 1627 */               return (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"));
/*      */             }
/*      */           }));
/* 1630 */     if (list.size() > 15) {
/* 1631 */       collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */     } else {
/* 1633 */       collection = list;
/*      */     } 
/* 1635 */     int i = getFontRenderer().getStringWidth(objective.getDisplayName());
/* 1636 */     for (Score score : collection) {
/* 1637 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/* 1638 */       String s = String.valueOf(String.valueOf(ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()))) + ": " + EnumChatFormatting.RED + score.getScorePoints();
/* 1639 */       i = Math.max(i, getFontRenderer().getStringWidth(s));
/*      */     } 
/* 1641 */     int i2 = collection.size() * (getFontRenderer()).FONT_HEIGHT;
/* 1642 */     int j1 = scaledRes.getScaledHeight() / 2 + i2 / 3;
/* 1643 */     int k1 = 3;
/* 1644 */     int l1 = scaledRes.getScaledWidth() - i - 3;
/* 1645 */     int m = 0;
/* 1646 */     for (Score score2 : collection) {
/* 1647 */       m++;
/* 1648 */       ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
/* 1649 */       String s2 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam2, score2.getPlayerName());
/* 1650 */       String s3 = EnumChatFormatting.RED + score2.getScorePoints();
/* 1651 */       int k2 = j1 - m * (getFontRenderer()).FONT_HEIGHT;
/* 1652 */       int l2 = scaledRes.getScaledWidth() - 3 + 2;
/* 1653 */       Gui.drawRect(l1 - 2, k2, l2, k2 + (getFontRenderer()).FONT_HEIGHT, 1342177280);
/* 1654 */       getFontRenderer().drawString(s2, l1, k2, 553648127);
/* 1655 */       getFontRenderer().drawString(s3, l2 - getFontRenderer().getStringWidth(s3), k2, 553648127);
/* 1656 */       if (m == collection.size()) {
/* 1657 */         String s4 = objective.getDisplayName();
/* 1658 */         Gui.drawRect(l1 - 2, k2 - (getFontRenderer()).FONT_HEIGHT - 1, l2, k2 - 1, 1610612736);
/* 1659 */         Gui.drawRect(l1 - 2, k2 - 1, l2, k2, 1342177280);
/* 1660 */         getFontRenderer().drawString(s4, l1 + i / 2 - getFontRenderer().getStringWidth(s4) / 2, k2 - (getFontRenderer()).FONT_HEIGHT, 553648127);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPlayerStats(ScaledResolution scaledRes) {
/* 1666 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/* 1667 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/* 1668 */       int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
/* 1669 */       boolean flag = (this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L);
/* 1670 */       if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
/* 1671 */         this.lastSystemTime = Minecraft.getSystemTime();
/* 1672 */         this.healthUpdateCounter = (this.updateCounter + 20);
/* 1673 */       } else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
/* 1674 */         this.lastSystemTime = Minecraft.getSystemTime();
/* 1675 */         this.healthUpdateCounter = (this.updateCounter + 10);
/*      */       } 
/* 1677 */       if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
/* 1678 */         this.playerHealth = i;
/* 1679 */         this.lastPlayerHealth = i;
/* 1680 */         this.lastSystemTime = Minecraft.getSystemTime();
/*      */       } 
/* 1682 */       this.playerHealth = i;
/* 1683 */       int j = this.lastPlayerHealth;
/* 1684 */       this.rand.setSeed((this.updateCounter * 312871));
/* 1685 */       boolean flag2 = false;
/* 1686 */       FoodStats foodstats = entityplayer.getFoodStats();
/* 1687 */       int k = foodstats.getFoodLevel();
/* 1688 */       int l = foodstats.getPrevFoodLevel();
/* 1689 */       IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/* 1690 */       int i2 = scaledRes.getScaledWidth() / 2 - 91;
/* 1691 */       int j2 = scaledRes.getScaledWidth() / 2 + 91;
/* 1692 */       int k2 = scaledRes.getScaledHeight() - 39;
/* 1693 */       float f = (float)iattributeinstance.getAttributeValue();
/* 1694 */       float f2 = entityplayer.getAbsorptionAmount();
/* 1695 */       int l2 = MathHelper.ceiling_float_int((f + f2) / 2.0F / 10.0F);
/* 1696 */       int i3 = Math.max(10 - l2 - 2, 3);
/* 1697 */       int j3 = k2 - (l2 - 1) * i3 - 10;
/* 1698 */       float f3 = f2;
/* 1699 */       int k3 = entityplayer.getTotalArmorValue();
/* 1700 */       int l3 = -1;
/* 1701 */       if (entityplayer.isPotionActive(Potion.regeneration)) {
/* 1702 */         l3 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0F);
/*      */       }
/* 1704 */       this.mc.mcProfiler.startSection("armor");
/* 1705 */       for (int i4 = 0; i4 < 10; i4++) {
/* 1706 */         if (k3 > 0) {
/* 1707 */           int j4 = i2 + i4 * 8;
/* 1708 */           if (i4 * 2 + 1 < k3) {
/* 1709 */             drawTexturedModalRect(j4, j3, 34, 9, 9, 9);
/*      */           }
/* 1711 */           if (i4 * 2 + 1 == k3) {
/* 1712 */             drawTexturedModalRect(j4, j3, 25, 9, 9, 9);
/*      */           }
/* 1714 */           if (i4 * 2 + 1 > k3) {
/* 1715 */             drawTexturedModalRect(j4, j3, 16, 9, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/* 1719 */       this.mc.mcProfiler.endStartSection("health");
/* 1720 */       for (int i5 = MathHelper.ceiling_float_int((f + f2) / 2.0F) - 1; i5 >= 0; i5--) {
/* 1721 */         int j5 = 16;
/* 1722 */         if (entityplayer.isPotionActive(Potion.poison)) {
/* 1723 */           j5 += 36;
/* 1724 */         } else if (entityplayer.isPotionActive(Potion.wither)) {
/* 1725 */           j5 += 72;
/*      */         } 
/* 1727 */         int k4 = 0;
/* 1728 */         if (flag) {
/* 1729 */           k4 = 1;
/*      */         }
/* 1731 */         int l4 = MathHelper.ceiling_float_int((i5 + 1) / 10.0F) - 1;
/* 1732 */         int i6 = i2 + i5 % 10 * 8;
/* 1733 */         int j6 = k2 - l4 * i3;
/* 1734 */         if (i <= 4) {
/* 1735 */           j6 += this.rand.nextInt(2);
/*      */         }
/* 1737 */         if (i5 == l3) {
/* 1738 */           j6 -= 2;
/*      */         }
/* 1740 */         int k5 = 0;
/* 1741 */         if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
/* 1742 */           k5 = 5;
/*      */         }
/* 1744 */         drawTexturedModalRect(i6, j6, 16 + k4 * 9, 9 * k5, 9, 9);
/* 1745 */         if (flag) {
/* 1746 */           if (i5 * 2 + 1 < j) {
/* 1747 */             drawTexturedModalRect(i6, j6, j5 + 54, 9 * k5, 9, 9);
/*      */           }
/* 1749 */           if (i5 * 2 + 1 == j) {
/* 1750 */             drawTexturedModalRect(i6, j6, j5 + 63, 9 * k5, 9, 9);
/*      */           }
/*      */         } 
/* 1753 */         if (f3 <= 0.0F) {
/* 1754 */           if (i5 * 2 + 1 < i) {
/* 1755 */             drawTexturedModalRect(i6, j6, j5 + 36, 9 * k5, 9, 9);
/*      */           }
/* 1757 */           if (i5 * 2 + 1 == i) {
/* 1758 */             drawTexturedModalRect(i6, j6, j5 + 45, 9 * k5, 9, 9);
/*      */           }
/*      */         } else {
/* 1761 */           if (f3 == f2 && f2 % 2.0F == 1.0F) {
/* 1762 */             drawTexturedModalRect(i6, j6, j5 + 153, 9 * k5, 9, 9);
/*      */           } else {
/* 1764 */             drawTexturedModalRect(i6, j6, j5 + 144, 9 * k5, 9, 9);
/*      */           } 
/* 1766 */           f3 -= 2.0F;
/*      */         } 
/*      */       } 
/* 1769 */       Entity entity = entityplayer.ridingEntity;
/* 1770 */       if (entity == null) {
/* 1771 */         this.mc.mcProfiler.endStartSection("food");
/* 1772 */         for (int k6 = 0; k6 < 10; k6++) {
/* 1773 */           int j7 = k2;
/* 1774 */           int l5 = 16;
/* 1775 */           int k7 = 0;
/* 1776 */           if (entityplayer.isPotionActive(Potion.hunger)) {
/* 1777 */             l5 += 36;
/* 1778 */             k7 = 13;
/*      */           } 
/* 1780 */           if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0) {
/* 1781 */             j7 = k2 + this.rand.nextInt(3) - 1;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1786 */           int j8 = j2 - k6 * 8 - 9;
/* 1787 */           drawTexturedModalRect(j8, j7, 16 + k7 * 9, 27, 9, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1796 */           if (k6 * 2 + 1 < k) {
/* 1797 */             drawTexturedModalRect(j8, j7, l5 + 36, 27, 9, 9);
/*      */           }
/* 1799 */           if (k6 * 2 + 1 == k) {
/* 1800 */             drawTexturedModalRect(j8, j7, l5 + 45, 27, 9, 9);
/*      */           }
/*      */         } 
/* 1803 */       } else if (entity instanceof EntityLivingBase) {
/* 1804 */         this.mc.mcProfiler.endStartSection("mountHealth");
/* 1805 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 1806 */         int i7 = (int)Math.ceil(entitylivingbase.getHealth());
/* 1807 */         float f4 = entitylivingbase.getMaxHealth();
/* 1808 */         int j9 = (int)(f4 + 0.5F) / 2;
/* 1809 */         if (j9 > 30) {
/* 1810 */           j9 = 30;
/*      */         }
/* 1812 */         int i8 = k2;
/* 1813 */         int k8 = 0;
/* 1814 */         while (j9 > 0) {
/* 1815 */           int l6 = Math.min(j9, 10);
/* 1816 */           j9 -= l6;
/* 1817 */           for (int i9 = 0; i9 < l6; i9++) {
/* 1818 */             int j10 = 52;
/* 1819 */             int k9 = 0;
/*      */ 
/*      */ 
/*      */             
/* 1823 */             int l7 = j2 - i9 * 8 - 9;
/* 1824 */             drawTexturedModalRect(l7, i8, 52 + k9 * 9, 9, 9, 9);
/* 1825 */             if (i9 * 2 + 1 + k8 < i7) {
/* 1826 */               drawTexturedModalRect(l7, i8, 88, 9, 9, 9);
/*      */             }
/* 1828 */             if (i9 * 2 + 1 + k8 == i7) {
/* 1829 */               drawTexturedModalRect(l7, i8, 97, 9, 9, 9);
/*      */             }
/*      */           } 
/* 1832 */           i8 -= 10;
/* 1833 */           k8 += 20;
/*      */         } 
/*      */       } 
/* 1836 */       this.mc.mcProfiler.endStartSection("air");
/* 1837 */       if (entityplayer.isInsideOfMaterial(Material.water)) {
/* 1838 */         int l8 = this.mc.thePlayer.getAir();
/* 1839 */         for (int k10 = MathHelper.ceiling_double_int((l8 - 2) * 10.0D / 300.0D), i10 = MathHelper.ceiling_double_int(l8 * 10.0D / 300.0D) - k10, l9 = 0; l9 < k10 + i10; l9++) {
/* 1840 */           if (l9 < k10) {
/* 1841 */             drawTexturedModalRect(j2 - l9 * 8 - 9, j3, 16, 18, 9, 9);
/*      */           } else {
/* 1843 */             drawTexturedModalRect(j2 - l9 * 8 - 9, j3, 25, 18, 9, 9);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1847 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderBossHealth() {
/* 1852 */     if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
/* 1853 */       BossStatus.statusBarTime--;
/* 1854 */       FontRenderer fontrenderer = this.mc.fontRendererObj;
/* 1855 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1856 */       int i = scaledresolution.getScaledWidth();
/* 1857 */       int j = 182;
/* 1858 */       int k = i / 2 - 91;
/* 1859 */       int l = (int)(BossStatus.healthScale * 183.0F);
/* 1860 */       int i2 = 12;
/* 1861 */       drawTexturedModalRect(k, 12, 0, 74, 182, 5);
/* 1862 */       drawTexturedModalRect(k, 12, 0, 74, 182, 5);
/* 1863 */       if (l > 0) {
/* 1864 */         drawTexturedModalRect(k, 12, 0, 79, l, 5);
/*      */       }
/* 1866 */       String s = BossStatus.bossName;
/* 1867 */       getFontRenderer().drawStringWithShadow(s, (i / 2 - getFontRenderer().getStringWidth(s) / 2), 2.0F, 16777215);
/* 1868 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1869 */       this.mc.getTextureManager().bindTexture(icons);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPumpkinOverlay(ScaledResolution scaledRes) {
/* 1874 */     GlStateManager.disableDepth();
/* 1875 */     GlStateManager.depthMask(false);
/* 1876 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1877 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1878 */     GlStateManager.disableAlpha();
/* 1879 */     this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
/* 1880 */     Tessellator tessellator = Tessellator.getInstance();
/* 1881 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1882 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1883 */     worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1884 */     worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1885 */     worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1886 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1887 */     tessellator.draw();
/* 1888 */     GlStateManager.depthMask(true);
/* 1889 */     GlStateManager.enableDepth();
/* 1890 */     GlStateManager.enableAlpha();
/* 1891 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderNausea() {
/* 1895 */     if ((Client.getInstance()).hudManager.newNausea.isEnabled()) {
/* 1896 */       GlStateManager.disableDepth();
/* 1897 */       GlStateManager.depthMask(false);
/* 1898 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1899 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1900 */       GlStateManager.disableAlpha();
/* 1901 */       this.mc.getTextureManager().bindTexture(new ResourceLocation("client/nausea.png"));
/* 1902 */       Tessellator tessellator = Tessellator.getInstance();
/* 1903 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1904 */       ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
/* 1905 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1906 */       worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1907 */       worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1908 */       worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1909 */       worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1910 */       tessellator.draw();
/* 1911 */       GlStateManager.depthMask(true);
/* 1912 */       GlStateManager.enableDepth();
/* 1913 */       GlStateManager.enableAlpha();
/* 1914 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderVignette(float lightLevel, ScaledResolution scaledRes) {
/* 1919 */     if (!Config.isVignetteEnabled()) {
/* 1920 */       GlStateManager.enableDepth();
/* 1921 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } else {
/* 1923 */       lightLevel = 1.0F - lightLevel;
/* 1924 */       lightLevel = MathHelper.clamp_float(lightLevel, 0.0F, 1.0F);
/* 1925 */       WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
/* 1926 */       float f = (float)worldborder.getClosestDistance((Entity)this.mc.thePlayer);
/* 1927 */       double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
/* 1928 */       double d2 = Math.max(worldborder.getWarningDistance(), d0);
/* 1929 */       if (f < d2) {
/* 1930 */         f = 1.0F - (float)(f / d2);
/*      */       } else {
/* 1932 */         f = 0.0F;
/*      */       } 
/* 1934 */       this.prevVignetteBrightness += (float)((lightLevel - this.prevVignetteBrightness) * 0.01D);
/* 1935 */       GlStateManager.disableDepth();
/* 1936 */       GlStateManager.depthMask(false);
/* 1937 */       GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
/* 1938 */       if (f > 0.0F) {
/* 1939 */         GlStateManager.color(0.0F, f, f, 1.0F);
/*      */       } else {
/* 1941 */         GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
/*      */       } 
/* 1943 */       this.mc.getTextureManager().bindTexture(vignetteTexPath);
/* 1944 */       Tessellator tessellator = Tessellator.getInstance();
/* 1945 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1946 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1947 */       worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1948 */       worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1949 */       worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1950 */       worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1951 */       tessellator.draw();
/* 1952 */       GlStateManager.depthMask(true);
/* 1953 */       GlStateManager.enableDepth();
/* 1954 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1955 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPortal(float timeInPortal, ScaledResolution scaledRes) {
/* 1960 */     if (timeInPortal < 1.0F) {
/* 1961 */       timeInPortal *= timeInPortal;
/* 1962 */       timeInPortal *= timeInPortal;
/* 1963 */       timeInPortal = timeInPortal * 0.8F + 0.2F;
/*      */     } 
/* 1965 */     GlStateManager.disableAlpha();
/* 1966 */     GlStateManager.disableDepth();
/* 1967 */     GlStateManager.depthMask(false);
/* 1968 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1969 */     GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
/* 1970 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1971 */     TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
/* 1972 */     float f = textureatlassprite.getMinU();
/* 1973 */     float f2 = textureatlassprite.getMinV();
/* 1974 */     float f3 = textureatlassprite.getMaxU();
/* 1975 */     float f4 = textureatlassprite.getMaxV();
/* 1976 */     Tessellator tessellator = Tessellator.getInstance();
/* 1977 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1978 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1979 */     worldrenderer.pos(0.0D, scaledRes.getScaledHeight(), -90.0D).tex(f, f4).endVertex();
/* 1980 */     worldrenderer.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0D).tex(f3, f4).endVertex();
/* 1981 */     worldrenderer.pos(scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(f3, f2).endVertex();
/* 1982 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(f, f2).endVertex();
/* 1983 */     tessellator.draw();
/* 1984 */     GlStateManager.depthMask(true);
/* 1985 */     GlStateManager.enableDepth();
/* 1986 */     GlStateManager.enableAlpha();
/* 1987 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player) {
/* 1991 */     ItemStack itemstack = player.inventory.mainInventory[index];
/* 1992 */     if (itemstack != null) {
/* 1993 */       float f = itemstack.animationsToGo - partialTicks;
/* 1994 */       if (f > 0.0F) {
/* 1995 */         GlStateManager.pushMatrix();
/* 1996 */         float f2 = 1.0F + f / 5.0F;
/* 1997 */         GlStateManager.translate((xPos + 8), (yPos + 12), 0.0F);
/* 1998 */         GlStateManager.scale(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
/* 1999 */         GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
/*      */       } 
/* 2001 */       this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
/* 2002 */       if (f > 0.0F) {
/* 2003 */         GlStateManager.popMatrix();
/*      */       }
/* 2005 */       this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateTick() {
/* 2010 */     if (this.recordPlayingUpFor > 0) {
/* 2011 */       this.recordPlayingUpFor--;
/*      */     }
/* 2013 */     if (this.titlesTimer > 0) {
/* 2014 */       this.titlesTimer--;
/* 2015 */       if (this.titlesTimer <= 0) {
/* 2016 */         this.displayedTitle = "";
/* 2017 */         this.displayedSubTitle = "";
/*      */       } 
/*      */     } 
/* 2020 */     this.updateCounter++;
/* 2021 */     this.streamIndicator.updateStreamAlpha();
/* 2022 */     if (this.mc.thePlayer != null) {
/* 2023 */       ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
/* 2024 */       if (itemstack == null) {
/* 2025 */         this.remainingHighlightTicks = 0;
/* 2026 */       } else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
/* 2027 */         if (this.remainingHighlightTicks > 0) {
/* 2028 */           this.remainingHighlightTicks--;
/*      */         }
/*      */       } else {
/* 2031 */         this.remainingHighlightTicks = 40;
/*      */       } 
/* 2033 */       this.highlightingItemStack = itemstack;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlayingMessage(String recordName) {
/* 2038 */     setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { recordName }), true);
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(String message, boolean isPlaying) {
/* 2042 */     this.recordPlaying = message;
/* 2043 */     this.recordPlayingUpFor = 60;
/* 2044 */     this.recordIsPlaying = isPlaying;
/*      */   }
/*      */   
/*      */   public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime, int timeFadeOut) {
/* 2048 */     if (title == null && subTitle == null && timeFadeIn < 0 && displayTime < 0 && timeFadeOut < 0) {
/* 2049 */       this.displayedTitle = "";
/* 2050 */       this.displayedSubTitle = "";
/* 2051 */       this.titlesTimer = 0;
/* 2052 */     } else if (title != null) {
/* 2053 */       this.displayedTitle = title;
/* 2054 */       this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/* 2055 */     } else if (subTitle != null) {
/* 2056 */       this.displayedSubTitle = subTitle;
/*      */     } else {
/* 2058 */       if (timeFadeIn >= 0) {
/* 2059 */         this.titleFadeIn = timeFadeIn;
/*      */       }
/* 2061 */       if (displayTime >= 0) {
/* 2062 */         this.titleDisplayTime = displayTime;
/*      */       }
/* 2064 */       if (timeFadeOut >= 0) {
/* 2065 */         this.titleFadeOut = timeFadeOut;
/*      */       }
/* 2067 */       if (this.titlesTimer > 0) {
/* 2068 */         this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(IChatComponent component, boolean isPlaying) {
/* 2074 */     setRecordPlaying(component.getUnformattedText(), isPlaying);
/*      */   }
/*      */   
/*      */   public GuiNewChat getChatGUI() {
/* 2078 */     return this.persistantChatGUI;
/*      */   }
/*      */   
/*      */   public int getUpdateCounter() {
/* 2082 */     return this.updateCounter;
/*      */   }
/*      */   
/*      */   public FontRenderer getFontRenderer() {
/* 2086 */     return this.mc.fontRendererObj;
/*      */   }
/*      */   
/*      */   public GuiSpectator getSpectatorGui() {
/* 2090 */     return this.spectatorGui;
/*      */   }
/*      */   
/*      */   public GuiPlayerTabOverlay getTabList() {
/* 2094 */     return this.overlayPlayerList;
/*      */   }
/*      */   
/*      */   public void resetPlayersOverlayFooterHeader() {
/* 2098 */     this.overlayPlayerList.resetFooterHeader();
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */