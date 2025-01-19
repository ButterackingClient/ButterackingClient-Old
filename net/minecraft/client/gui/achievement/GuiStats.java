/*     */ package net.minecraft.client.gui.achievement;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.IProgressMeter;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatCrafting;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiStats
/*     */   extends GuiScreen
/*     */   implements IProgressMeter
/*     */ {
/*     */   protected GuiScreen parentScreen;
/*     */   
/*     */   class StatsBlock
/*     */     extends Stats
/*     */   {
/*     */     public StatsBlock(Minecraft mcIn) {
/* 372 */       super(mcIn);
/* 373 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 375 */       for (StatCrafting statcrafting : StatList.objectMineStats) {
/* 376 */         boolean flag = false;
/* 377 */         int i = Item.getIdFromItem(statcrafting.func_150959_a());
/*     */         
/* 379 */         if (GuiStats.this.field_146546_t.readStat((StatBase)statcrafting) > 0) {
/* 380 */           flag = true;
/* 381 */         } else if (StatList.objectUseStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectUseStats[i]) > 0) {
/* 382 */           flag = true;
/* 383 */         } else if (StatList.objectCraftStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectCraftStats[i]) > 0) {
/* 384 */           flag = true;
/*     */         } 
/*     */         
/* 387 */         if (flag) {
/* 388 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 392 */       this.statSorter = new Comparator<StatCrafting>() {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
/* 394 */             int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
/* 395 */             int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
/* 396 */             StatBase statbase = null;
/* 397 */             StatBase statbase1 = null;
/*     */             
/* 399 */             if (GuiStats.StatsBlock.this.field_148217_o == 2) {
/* 400 */               statbase = StatList.mineBlockStatArray[j];
/* 401 */               statbase1 = StatList.mineBlockStatArray[k];
/* 402 */             } else if (GuiStats.StatsBlock.this.field_148217_o == 0) {
/* 403 */               statbase = StatList.objectCraftStats[j];
/* 404 */               statbase1 = StatList.objectCraftStats[k];
/* 405 */             } else if (GuiStats.StatsBlock.this.field_148217_o == 1) {
/* 406 */               statbase = StatList.objectUseStats[j];
/* 407 */               statbase1 = StatList.objectUseStats[k];
/*     */             } 
/*     */             
/* 410 */             if (statbase != null || statbase1 != null) {
/* 411 */               if (statbase == null) {
/* 412 */                 return 1;
/*     */               }
/*     */               
/* 415 */               if (statbase1 == null) {
/* 416 */                 return -1;
/*     */               }
/*     */               
/* 419 */               int l = (GuiStats.StatsBlock.access$0(GuiStats.StatsBlock.this)).field_146546_t.readStat(statbase);
/* 420 */               int i1 = (GuiStats.StatsBlock.access$0(GuiStats.StatsBlock.this)).field_146546_t.readStat(statbase1);
/*     */               
/* 422 */               if (l != i1) {
/* 423 */                 return (l - i1) * GuiStats.StatsBlock.this.field_148215_p;
/*     */               }
/*     */             } 
/*     */             
/* 427 */             return j - k;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 433 */       super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
/*     */       
/* 435 */       if (this.field_148218_l == 0) {
/* 436 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/*     */       } else {
/* 438 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
/*     */       } 
/*     */       
/* 441 */       if (this.field_148218_l == 1) {
/* 442 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/*     */       } else {
/* 444 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
/*     */       } 
/*     */       
/* 447 */       if (this.field_148218_l == 2) {
/* 448 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
/*     */       } else {
/* 450 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 455 */       StatCrafting statcrafting = func_148211_c(entryID);
/* 456 */       Item item = statcrafting.func_150959_a();
/* 457 */       GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, item);
/* 458 */       int i = Item.getIdFromItem(item);
/* 459 */       func_148209_a(StatList.objectCraftStats[i], p_180791_2_ + 115, p_180791_3_, (entryID % 2 == 0));
/* 460 */       func_148209_a(StatList.objectUseStats[i], p_180791_2_ + 165, p_180791_3_, (entryID % 2 == 0));
/* 461 */       func_148209_a((StatBase)statcrafting, p_180791_2_ + 215, p_180791_3_, (entryID % 2 == 0));
/*     */     }
/*     */     
/*     */     protected String func_148210_b(int p_148210_1_) {
/* 465 */       return (p_148210_1_ == 0) ? "stat.crafted" : ((p_148210_1_ == 1) ? "stat.used" : "stat.mined");
/*     */     }
/*     */   }
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
/*     */   class StatsItem
/*     */     extends Stats
/*     */   {
/*     */     public StatsItem(Minecraft mcIn) {
/* 504 */       super(mcIn);
/* 505 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 507 */       for (StatCrafting statcrafting : StatList.itemStats) {
/* 508 */         boolean flag = false;
/* 509 */         int i = Item.getIdFromItem(statcrafting.func_150959_a());
/*     */         
/* 511 */         if (GuiStats.this.field_146546_t.readStat((StatBase)statcrafting) > 0) {
/* 512 */           flag = true;
/* 513 */         } else if (StatList.objectBreakStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectBreakStats[i]) > 0) {
/* 514 */           flag = true;
/* 515 */         } else if (StatList.objectCraftStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectCraftStats[i]) > 0) {
/* 516 */           flag = true;
/*     */         } 
/*     */         
/* 519 */         if (flag) {
/* 520 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 524 */       this.statSorter = new Comparator<StatCrafting>() {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
/* 526 */             int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
/* 527 */             int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
/* 528 */             StatBase statbase = null;
/* 529 */             StatBase statbase1 = null;
/*     */             
/* 531 */             if (GuiStats.StatsItem.this.field_148217_o == 0) {
/* 532 */               statbase = StatList.objectBreakStats[j];
/* 533 */               statbase1 = StatList.objectBreakStats[k];
/* 534 */             } else if (GuiStats.StatsItem.this.field_148217_o == 1) {
/* 535 */               statbase = StatList.objectCraftStats[j];
/* 536 */               statbase1 = StatList.objectCraftStats[k];
/* 537 */             } else if (GuiStats.StatsItem.this.field_148217_o == 2) {
/* 538 */               statbase = StatList.objectUseStats[j];
/* 539 */               statbase1 = StatList.objectUseStats[k];
/*     */             } 
/*     */             
/* 542 */             if (statbase != null || statbase1 != null) {
/* 543 */               if (statbase == null) {
/* 544 */                 return 1;
/*     */               }
/*     */               
/* 547 */               if (statbase1 == null) {
/* 548 */                 return -1;
/*     */               }
/*     */               
/* 551 */               int l = (GuiStats.StatsItem.access$0(GuiStats.StatsItem.this)).field_146546_t.readStat(statbase);
/* 552 */               int i1 = (GuiStats.StatsItem.access$0(GuiStats.StatsItem.this)).field_146546_t.readStat(statbase1);
/*     */               
/* 554 */               if (l != i1) {
/* 555 */                 return (l - i1) * GuiStats.StatsItem.this.field_148215_p;
/*     */               }
/*     */             } 
/*     */             
/* 559 */             return j - k;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 565 */       super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
/*     */       
/* 567 */       if (this.field_148218_l == 0) {
/* 568 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
/*     */       } else {
/* 570 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
/*     */       } 
/*     */       
/* 573 */       if (this.field_148218_l == 1) {
/* 574 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/*     */       } else {
/* 576 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
/*     */       } 
/*     */       
/* 579 */       if (this.field_148218_l == 2) {
/* 580 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/*     */       } else {
/* 582 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 587 */       StatCrafting statcrafting = func_148211_c(entryID);
/* 588 */       Item item = statcrafting.func_150959_a();
/* 589 */       GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, item);
/* 590 */       int i = Item.getIdFromItem(item);
/* 591 */       func_148209_a(StatList.objectBreakStats[i], p_180791_2_ + 115, p_180791_3_, (entryID % 2 == 0));
/* 592 */       func_148209_a(StatList.objectCraftStats[i], p_180791_2_ + 165, p_180791_3_, (entryID % 2 == 0));
/* 593 */       func_148209_a((StatBase)statcrafting, p_180791_2_ + 215, p_180791_3_, (entryID % 2 == 0));
/*     */     }
/*     */     
/*     */     protected String func_148210_b(int p_148210_1_) {
/* 597 */       return (p_148210_1_ == 1) ? "stat.crafted" : ((p_148210_1_ == 2) ? "stat.used" : "stat.depleted");
/*     */     }
/*     */   } protected String screenTitle = "Select world"; private StatsGeneral generalStats; private StatsItem itemStats; private StatsBlock blockStats; private StatsMobsList mobStats; private StatFileWriter field_146546_t; private GuiSlot displaySlot; private boolean doesGuiPauseGame = true; public GuiStats(GuiScreen p_i1071_1_, StatFileWriter p_i1071_2_) { this.parentScreen = p_i1071_1_; this.field_146546_t = p_i1071_2_; } public void initGui() { this.screenTitle = I18n.format("gui.stats", new Object[0]); this.doesGuiPauseGame = true; this.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS)); } public void handleMouseInput() throws IOException { super.handleMouseInput(); if (this.displaySlot != null) this.displaySlot.handleMouseInput();  } public void func_175366_f() { this.generalStats = new StatsGeneral(this.mc); this.generalStats.registerScrollButtons(1, 1); this.itemStats = new StatsItem(this.mc); this.itemStats.registerScrollButtons(1, 1); this.blockStats = new StatsBlock(this.mc); this.blockStats.registerScrollButtons(1, 1); this.mobStats = new StatsMobsList(this.mc); this.mobStats.registerScrollButtons(1, 1); } public void createButtons() { this.buttonList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, I18n.format("gui.done", new Object[0]))); this.buttonList.add(new GuiButton(1, width / 2 - 160, height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0]))); GuiButton guibutton; this.buttonList.add(guibutton = new GuiButton(2, width / 2 - 80, height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0]))); GuiButton guibutton1; this.buttonList.add(guibutton1 = new GuiButton(3, width / 2, height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0]))); GuiButton guibutton2; this.buttonList.add(guibutton2 = new GuiButton(4, width / 2 + 80, height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0]))); if (this.blockStats.getSize() == 0) guibutton.enabled = false;  if (this.itemStats.getSize() == 0) guibutton1.enabled = false;  if (this.mobStats.getSize() == 0) guibutton2.enabled = false;  } protected void actionPerformed(GuiButton button) throws IOException { if (button.enabled) if (button.id == 0) { this.mc.displayGuiScreen(this.parentScreen); } else if (button.id == 1) { this.displaySlot = this.generalStats; } else if (button.id == 3) { this.displaySlot = this.itemStats; } else if (button.id == 2) { this.displaySlot = this.blockStats; } else if (button.id == 4) { this.displaySlot = this.mobStats; } else { this.displaySlot.actionPerformed(button); }   } public void drawScreen(int mouseX, int mouseY, float partialTicks) { if (this.doesGuiPauseGame) { drawDefaultBackground(); drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215); drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length)], width / 2, height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215); } else { this.displaySlot.drawScreen(mouseX, mouseY, partialTicks); drawCenteredString(this.fontRendererObj, this.screenTitle, width / 2, 20, 16777215); super.drawScreen(mouseX, mouseY, partialTicks); }  } public void doneLoading() { if (this.doesGuiPauseGame) { func_175366_f(); createButtons(); this.displaySlot = this.generalStats; this.doesGuiPauseGame = false; }  } public boolean doesGuiPauseGame() { return !this.doesGuiPauseGame; } private void drawStatsScreen(int p_146521_1_, int p_146521_2_, Item p_146521_3_) { drawButtonBackground(p_146521_1_ + 1, p_146521_2_ + 1); GlStateManager.enableRescaleNormal(); RenderHelper.enableGUIStandardItemLighting(); this.itemRender.renderItemIntoGUI(new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2); RenderHelper.disableStandardItemLighting(); GlStateManager.disableRescaleNormal(); } private void drawButtonBackground(int p_146531_1_, int p_146531_2_) { drawSprite(p_146531_1_, p_146531_2_, 0, 0); } private void drawSprite(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_) { GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); this.mc.getTextureManager().bindTexture(statIcons); float f = 0.0078125F; float f1 = 0.0078125F; int i = 18; int j = 18; Tessellator tessellator = Tessellator.getInstance(); WorldRenderer worldrenderer = tessellator.getWorldRenderer(); worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX); worldrenderer.pos((p_146527_1_ + 0), (p_146527_2_ + 18), this.zLevel).tex(((p_146527_3_ + 0) * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex(); worldrenderer.pos((p_146527_1_ + 18), (p_146527_2_ + 18), this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex(); worldrenderer.pos((p_146527_1_ + 18), (p_146527_2_ + 0), this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), ((p_146527_4_ + 0) * 0.0078125F)).endVertex(); worldrenderer.pos((p_146527_1_ + 0), (p_146527_2_ + 0), this.zLevel).tex(((p_146527_3_ + 0) * 0.0078125F), ((p_146527_4_ + 0) * 0.0078125F)).endVertex(); tessellator.draw(); } abstract class Stats extends GuiSlot { protected int field_148218_l = -1; protected List<StatCrafting> statsHolder; protected Comparator<StatCrafting> statSorter; protected int field_148217_o = -1; protected int field_148215_p; protected Stats(Minecraft mcIn) { super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 20); setShowSelectionBox(false); setHasListHeader(true, 20); } protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {} protected boolean isSelected(int slotIndex) { return false; } protected void drawBackground() { GuiStats.this.drawDefaultBackground(); } protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) { if (!Mouse.isButtonDown(0)) this.field_148218_l = -1;  if (this.field_148218_l == 0) { GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0); } else { GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18); }  if (this.field_148218_l == 1) { GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0); } else { GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18); }  if (this.field_148218_l == 2) { GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0); } else { GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18); }  if (this.field_148217_o != -1) { int i = 79; int j = 18; if (this.field_148217_o == 1) { i = 129; } else if (this.field_148217_o == 2) { i = 179; }  if (this.field_148215_p == 1) j = 36;  GuiStats.this.drawSprite(p_148129_1_ + i, p_148129_2_ + 1, j, 0); }  } protected void func_148132_a(int p_148132_1_, int p_148132_2_) { this.field_148218_l = -1; if (p_148132_1_ >= 79 && p_148132_1_ < 115) { this.field_148218_l = 0; } else if (p_148132_1_ >= 129 && p_148132_1_ < 165) { this.field_148218_l = 1; } else if (p_148132_1_ >= 179 && p_148132_1_ < 215) { this.field_148218_l = 2; }  if (this.field_148218_l >= 0) { func_148212_h(this.field_148218_l); this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F)); }  } protected final int getSize() { return this.statsHolder.size(); } protected final StatCrafting func_148211_c(int p_148211_1_) { return this.statsHolder.get(p_148211_1_); } protected abstract String func_148210_b(int param1Int); protected void func_148209_a(StatBase p_148209_1_, int p_148209_2_, int p_148209_3_, boolean p_148209_4_) { if (p_148209_1_ != null) { String s = p_148209_1_.format(GuiStats.this.field_146546_t.readStat(p_148209_1_)); GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(s), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192); } else { String s1 = "-"; GuiStats.this.drawString(GuiStats.this.fontRendererObj, s1, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(s1), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192); }  } protected void func_148142_b(int p_148142_1_, int p_148142_2_) { if (p_148142_2_ >= this.top && p_148142_2_ <= this.bottom) { int i = getSlotIndexFromScreenCoords(p_148142_1_, p_148142_2_); int j = this.width / 2 - 92 - 16; if (i >= 0) { if (p_148142_1_ < j + 40 || p_148142_1_ > j + 40 + 20) return;  StatCrafting statcrafting = func_148211_c(i); func_148213_a(statcrafting, p_148142_1_, p_148142_2_); } else { String s = ""; if (p_148142_1_ >= j + 115 - 18 && p_148142_1_ <= j + 115) { s = func_148210_b(0); } else if (p_148142_1_ >= j + 165 - 18 && p_148142_1_ <= j + 165) { s = func_148210_b(1); } else { if (p_148142_1_ < j + 215 - 18 || p_148142_1_ > j + 215) return;  s = func_148210_b(2); }  s = I18n.format(s, new Object[0]).trim(); if (s.length() > 0) { int k = p_148142_1_ + 12; int l = p_148142_2_ - 12; int i1 = GuiStats.this.fontRendererObj.getStringWidth(s); GuiStats.this.drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, -1073741824, -1073741824); GuiStats.this.fontRendererObj.drawStringWithShadow(s, k, l, -1); }  }  }  } protected void func_148213_a(StatCrafting p_148213_1_, int p_148213_2_, int p_148213_3_) { if (p_148213_1_ != null) { Item item = p_148213_1_.func_150959_a(); ItemStack itemstack = new ItemStack(item); String s = itemstack.getUnlocalizedName(); String s1 = I18n.format(String.valueOf(s) + ".name", new Object[0]).trim(); if (s1.length() > 0) { int i = p_148213_2_ + 12; int j = p_148213_3_ - 12; int k = GuiStats.this.fontRendererObj.getStringWidth(s1); GuiStats.this.drawGradientRect(i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824); GuiStats.this.fontRendererObj.drawStringWithShadow(s1, i, j, -1); }  }  } protected void func_148212_h(int p_148212_1_) { if (p_148212_1_ != this.field_148217_o) { this.field_148217_o = p_148212_1_; this.field_148215_p = -1; } else if (this.field_148215_p == -1) { this.field_148215_p = 1; } else { this.field_148217_o = -1; this.field_148215_p = 0; }  Collections.sort(this.statsHolder, this.statSorter); } } class StatsGeneral extends GuiSlot {
/*     */     public StatsGeneral(Minecraft mcIn) { super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 10); setShowSelectionBox(false); } protected int getSize() { return StatList.generalStats.size(); } protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {} protected boolean isSelected(int slotIndex) { return false; } protected int getContentHeight() { return getSize() * 10; } protected void drawBackground() { GuiStats.this.drawDefaultBackground(); } protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) { StatBase statbase = StatList.generalStats.get(entryID); GuiStats.this.drawString(GuiStats.this.fontRendererObj, statbase.getStatName().getUnformattedText(), p_180791_2_ + 2, p_180791_3_ + 1, (entryID % 2 == 0) ? 16777215 : 9474192); String s = statbase.format(GuiStats.this.field_146546_t.readStat(statbase)); GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(s), p_180791_3_ + 1, (entryID % 2 == 0) ? 16777215 : 9474192); }
/*     */   } class StatsMobsList extends GuiSlot {
/* 602 */     private final List<EntityList.EntityEggInfo> field_148222_l = Lists.newArrayList();
/*     */     
/*     */     public StatsMobsList(Minecraft mcIn) {
/* 605 */       super(mcIn, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, GuiStats.this.fontRendererObj.FONT_HEIGHT * 4);
/* 606 */       setShowSelectionBox(false);
/*     */       
/* 608 */       for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values()) {
/* 609 */         if (GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d) > 0 || GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e) > 0) {
/* 610 */           this.field_148222_l.add(entitylist$entityegginfo);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 616 */       return this.field_148222_l.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 623 */       return false;
/*     */     }
/*     */     
/*     */     protected int getContentHeight() {
/* 627 */       return getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
/*     */     }
/*     */     
/*     */     protected void drawBackground() {
/* 631 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 635 */       EntityList.EntityEggInfo entitylist$entityegginfo = this.field_148222_l.get(entryID);
/* 636 */       String s = I18n.format("entity." + EntityList.getStringFromID(entitylist$entityegginfo.spawnedID) + ".name", new Object[0]);
/* 637 */       int i = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d);
/* 638 */       int j = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e);
/* 639 */       String s1 = I18n.format("stat.entityKills", new Object[] { Integer.valueOf(i), s });
/* 640 */       String s2 = I18n.format("stat.entityKilledBy", new Object[] { s, Integer.valueOf(j) });
/*     */       
/* 642 */       if (i == 0) {
/* 643 */         s1 = I18n.format("stat.entityKills.none", new Object[] { s });
/*     */       }
/*     */       
/* 646 */       if (j == 0) {
/* 647 */         s2 = I18n.format("stat.entityKilledBy.none", new Object[] { s });
/*     */       }
/*     */       
/* 650 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
/* 651 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, (i == 0) ? 6316128 : 9474192);
/* 652 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT * 2, (j == 0) ? 6316128 : 9474192);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\achievement\GuiStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */