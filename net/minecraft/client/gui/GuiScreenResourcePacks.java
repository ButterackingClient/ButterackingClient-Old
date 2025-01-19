/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.ResourcePackListEntry;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*     */ import net.minecraft.client.resources.ResourcePackRepository;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiScreenResourcePacks
/*     */   extends GuiScreen
/*     */ {
/*  22 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private List<ResourcePackListEntry> availableResourcePacks;
/*     */   
/*     */   private List<ResourcePackListEntry> selectedResourcePacks;
/*     */   
/*     */   private GuiResourcePackAvailable availableResourcePacksList;
/*     */   
/*     */   private GuiResourcePackSelected selectedResourcePacksList;
/*     */   
/*     */   private boolean changed = false;
/*     */ 
/*     */   
/*     */   public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
/*  39 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  47 */     this.buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  48 */     this.buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done", new Object[0])));
/*     */     
/*  50 */     if (!this.changed) {
/*  51 */       this.availableResourcePacks = Lists.newArrayList();
/*  52 */       this.selectedResourcePacks = Lists.newArrayList();
/*  53 */       ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
/*  54 */       resourcepackrepository.updateRepositoryEntriesAll();
/*  55 */       List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
/*  56 */       list.removeAll(resourcepackrepository.getRepositoryEntries());
/*     */       
/*  58 */       for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/*  59 */         this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
/*     */       }
/*     */       
/*  62 */       for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries())) {
/*  63 */         this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
/*     */       }
/*     */       
/*  66 */       this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
/*     */     } 
/*     */     
/*  69 */     this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, height, this.availableResourcePacks);
/*  70 */     this.availableResourcePacksList.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
/*  71 */     this.availableResourcePacksList.registerScrollButtons(7, 8);
/*  72 */     this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, height, this.selectedResourcePacks);
/*  73 */     this.selectedResourcePacksList.setSlotXBoundsFromLeft(width / 2 + 4);
/*  74 */     this.selectedResourcePacksList.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  81 */     super.handleMouseInput();
/*  82 */     this.selectedResourcePacksList.handleMouseInput();
/*  83 */     this.availableResourcePacksList.handleMouseInput();
/*     */   }
/*     */   
/*     */   public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
/*  87 */     return this.selectedResourcePacks.contains(p_146961_1_);
/*     */   }
/*     */   
/*     */   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
/*  91 */     return hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
/*     */   }
/*     */   
/*     */   public List<ResourcePackListEntry> getAvailableResourcePacks() {
/*  95 */     return this.availableResourcePacks;
/*     */   }
/*     */   
/*     */   public List<ResourcePackListEntry> getSelectedResourcePacks() {
/*  99 */     return this.selectedResourcePacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 106 */     if (button.enabled) {
/* 107 */       if (button.id == 2) {
/* 108 */         File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 109 */         String s = file1.getAbsolutePath();
/*     */         
/* 111 */         if (Util.getOSType() == Util.EnumOS.OSX) {
/*     */           try {
/* 113 */             logger.info(s);
/* 114 */             Runtime.getRuntime().exec(new String[] { "/usr/bin/open", s });
/*     */             return;
/* 116 */           } catch (IOException ioexception1) {
/* 117 */             logger.error("Couldn't open file", ioexception1);
/*     */           } 
/* 119 */         } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/* 120 */           String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { s });
/*     */           
/*     */           try {
/* 123 */             Runtime.getRuntime().exec(s1);
/*     */             return;
/* 125 */           } catch (IOException ioexception) {
/* 126 */             logger.error("Couldn't open file", ioexception);
/*     */           } 
/*     */         } 
/*     */         
/* 130 */         boolean flag = false;
/*     */         
/*     */         try {
/* 133 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 134 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 135 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file1.toURI() });
/* 136 */         } catch (Throwable throwable) {
/* 137 */           logger.error("Couldn't open link", throwable);
/* 138 */           flag = true;
/*     */         } 
/*     */         
/* 141 */         if (flag) {
/* 142 */           logger.info("Opening via system class!");
/* 143 */           Sys.openURL("file://" + s);
/*     */         } 
/* 145 */       } else if (button.id == 1) {
/* 146 */         if (this.changed) {
/* 147 */           List<ResourcePackRepository.Entry> list = Lists.newArrayList();
/*     */           
/* 149 */           for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
/* 150 */             if (resourcepacklistentry instanceof ResourcePackListEntryFound) {
/* 151 */               list.add(((ResourcePackListEntryFound)resourcepacklistentry).func_148318_i());
/*     */             }
/*     */           } 
/*     */           
/* 155 */           Collections.reverse(list);
/* 156 */           this.mc.getResourcePackRepository().setRepositories(list);
/* 157 */           this.mc.gameSettings.resourcePacks.clear();
/* 158 */           this.mc.gameSettings.incompatibleResourcePacks.clear();
/*     */           
/* 160 */           for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/* 161 */             this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             
/* 163 */             if (resourcepackrepository$entry.func_183027_f() != 1) {
/* 164 */               this.mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             }
/*     */           } 
/*     */           
/* 168 */           this.mc.gameSettings.saveOptions();
/* 169 */           this.mc.refreshResources();
/*     */         } 
/*     */         
/* 172 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 181 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 182 */     this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/* 183 */     this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 190 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 197 */     drawBackground(0);
/* 198 */     this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 199 */     this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 200 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), width / 2, 16, 16777215);
/* 201 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), width / 2 - 77, height - 26, 8421504);
/* 202 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChanged() {
/* 209 */     this.changed = true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenResourcePacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */