/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiSelectWorld
/*     */   extends GuiScreen implements GuiYesNoCallback {
/*  23 */   private static final Logger logger = LogManager.getLogger();
/*  24 */   private final DateFormat field_146633_h = new SimpleDateFormat();
/*     */   protected GuiScreen parentScreen;
/*  26 */   protected String screenTitle = "Select world";
/*     */   
/*     */   private boolean field_146634_i;
/*     */   
/*     */   private int selectedIndex;
/*     */   
/*     */   private java.util.List<SaveFormatComparator> field_146639_s;
/*     */   
/*     */   private List availableWorlds;
/*     */   private String field_146637_u;
/*     */   private String field_146636_v;
/*  37 */   private String[] field_146635_w = new String[4];
/*     */   private boolean confirmingDelete;
/*     */   private GuiButton deleteButton;
/*     */   private GuiButton selectButton;
/*     */   private GuiButton renameButton;
/*     */   private GuiButton recreateButton;
/*     */   
/*     */   public GuiSelectWorld(GuiScreen parentScreenIn) {
/*  45 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  53 */     this.screenTitle = I18n.format("selectWorld.title", new Object[0]);
/*     */     
/*     */     try {
/*  56 */       loadLevelList();
/*  57 */     } catch (AnvilConverterException anvilconverterexception) {
/*  58 */       logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*  59 */       this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
/*  64 */     this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
/*  65 */     this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
/*  66 */     this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
/*  67 */     this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
/*  68 */     this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
/*  69 */     this.availableWorlds = new List(this.mc);
/*  70 */     this.availableWorlds.registerScrollButtons(4, 5);
/*  71 */     addWorldSelectionButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  78 */     super.handleMouseInput();
/*  79 */     this.availableWorlds.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadLevelList() throws AnvilConverterException {
/*  86 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  87 */     this.field_146639_s = isaveformat.getSaveList();
/*  88 */     Collections.sort(this.field_146639_s);
/*  89 */     this.selectedIndex = -1;
/*     */   }
/*     */   
/*     */   protected String func_146621_a(int p_146621_1_) {
/*  93 */     return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
/*     */   }
/*     */   
/*     */   protected String func_146614_d(int p_146614_1_) {
/*  97 */     String s = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
/*     */     
/*  99 */     if (StringUtils.isEmpty(s)) {
/* 100 */       s = String.valueOf(I18n.format("selectWorld.world", new Object[0])) + " " + (p_146614_1_ + 1);
/*     */     }
/*     */     
/* 103 */     return s;
/*     */   }
/*     */   
/*     */   public void addWorldSelectionButtons() {
/* 107 */     this.buttonList.add(this.selectButton = new GuiButton(1, width / 2 - 154, height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/* 108 */     this.buttonList.add(new GuiButton(3, width / 2 + 4, height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/* 109 */     this.buttonList.add(this.renameButton = new GuiButton(6, width / 2 - 154, height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
/* 110 */     this.buttonList.add(this.deleteButton = new GuiButton(2, width / 2 - 76, height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/* 111 */     this.buttonList.add(this.recreateButton = new GuiButton(7, width / 2 + 4, height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/* 112 */     this.buttonList.add(new GuiButton(0, width / 2 + 82, height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/* 113 */     this.selectButton.enabled = false;
/* 114 */     this.deleteButton.enabled = false;
/* 115 */     this.renameButton.enabled = false;
/* 116 */     this.recreateButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 123 */     if (button.enabled) {
/* 124 */       if (button.id == 2) {
/* 125 */         String s = func_146614_d(this.selectedIndex);
/*     */         
/* 127 */         if (s != null) {
/* 128 */           this.confirmingDelete = true;
/* 129 */           GuiYesNo guiyesno = makeDeleteWorldYesNo(this, s, this.selectedIndex);
/* 130 */           this.mc.displayGuiScreen(guiyesno);
/*     */         } 
/* 132 */       } else if (button.id == 1) {
/* 133 */         func_146615_e(this.selectedIndex);
/* 134 */       } else if (button.id == 3) {
/* 135 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/* 136 */       } else if (button.id == 6) {
/* 137 */         this.mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(this.selectedIndex)));
/* 138 */       } else if (button.id == 0) {
/* 139 */         this.mc.displayGuiScreen(this.parentScreen);
/* 140 */       } else if (button.id == 7) {
/* 141 */         GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
/* 142 */         ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(func_146621_a(this.selectedIndex), false);
/* 143 */         WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 144 */         isavehandler.flush();
/* 145 */         guicreateworld.recreateFromExistingWorld(worldinfo);
/* 146 */         this.mc.displayGuiScreen(guicreateworld);
/*     */       } else {
/* 148 */         this.availableWorlds.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_146615_e(int p_146615_1_) {
/* 154 */     this.mc.displayGuiScreen(null);
/*     */     
/* 156 */     if (!this.field_146634_i) {
/* 157 */       this.field_146634_i = true;
/* 158 */       String s = func_146621_a(p_146615_1_);
/*     */       
/* 160 */       if (s == null) {
/* 161 */         s = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 164 */       String s1 = func_146614_d(p_146615_1_);
/*     */       
/* 166 */       if (s1 == null) {
/* 167 */         s1 = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 170 */       if (this.mc.getSaveLoader().canLoadWorld(s)) {
/* 171 */         this.mc.launchIntegratedServer(s, s1, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 177 */     if (this.confirmingDelete) {
/* 178 */       this.confirmingDelete = false;
/*     */       
/* 180 */       if (result) {
/* 181 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 182 */         isaveformat.flushCache();
/* 183 */         isaveformat.deleteWorldDirectory(func_146621_a(id));
/*     */         
/*     */         try {
/* 186 */           loadLevelList();
/* 187 */         } catch (AnvilConverterException anvilconverterexception) {
/* 188 */           logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*     */         } 
/*     */       } 
/*     */       
/* 192 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 200 */     this.availableWorlds.drawScreen(mouseX, mouseY, partialTicks);
/* 201 */     drawCenteredString(this.fontRendererObj, this.screenTitle, width / 2, 20, 16777215);
/* 202 */     super.drawScreen(mouseX, mouseY, partialTicks);
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
/*     */   public static GuiYesNo makeDeleteWorldYesNo(GuiYesNoCallback selectWorld, String name, int id) {
/* 215 */     String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
/* 216 */     String s1 = "'" + name + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
/* 217 */     String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
/* 218 */     String s3 = I18n.format("gui.cancel", new Object[0]);
/* 219 */     GuiYesNo guiyesno = new GuiYesNo(selectWorld, s, s1, s2, s3, id);
/* 220 */     return guiyesno;
/*     */   }
/*     */   
/*     */   class List extends GuiSlot {
/*     */     public List(Minecraft mcIn) {
/* 225 */       super(mcIn, GuiSelectWorld.width, GuiSelectWorld.height, 32, GuiSelectWorld.height - 64, 36);
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 229 */       return GuiSelectWorld.this.field_146639_s.size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 233 */       GuiSelectWorld.this.selectedIndex = slotIndex;
/* 234 */       boolean flag = (GuiSelectWorld.this.selectedIndex >= 0 && GuiSelectWorld.this.selectedIndex < getSize());
/* 235 */       GuiSelectWorld.this.selectButton.enabled = flag;
/* 236 */       GuiSelectWorld.this.deleteButton.enabled = flag;
/* 237 */       GuiSelectWorld.this.renameButton.enabled = flag;
/* 238 */       GuiSelectWorld.this.recreateButton.enabled = flag;
/*     */       
/* 240 */       if (isDoubleClick && flag) {
/* 241 */         GuiSelectWorld.this.func_146615_e(slotIndex);
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 246 */       return (slotIndex == GuiSelectWorld.this.selectedIndex);
/*     */     }
/*     */     
/*     */     protected int getContentHeight() {
/* 250 */       return GuiSelectWorld.this.field_146639_s.size() * 36;
/*     */     }
/*     */     
/*     */     protected void drawBackground() {
/* 254 */       GuiSelectWorld.this.drawDefaultBackground();
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 258 */       SaveFormatComparator saveformatcomparator = GuiSelectWorld.this.field_146639_s.get(entryID);
/* 259 */       String s = saveformatcomparator.getDisplayName();
/*     */       
/* 261 */       if (StringUtils.isEmpty(s)) {
/* 262 */         s = String.valueOf(GuiSelectWorld.this.field_146637_u) + " " + (entryID + 1);
/*     */       }
/*     */       
/* 265 */       String s1 = saveformatcomparator.getFileName();
/* 266 */       s1 = String.valueOf(s1) + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
/* 267 */       s1 = String.valueOf(s1) + ")";
/* 268 */       String s2 = "";
/*     */       
/* 270 */       if (saveformatcomparator.requiresConversion()) {
/* 271 */         s2 = String.valueOf(GuiSelectWorld.this.field_146636_v) + " " + s2;
/*     */       } else {
/* 273 */         s2 = GuiSelectWorld.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];
/*     */         
/* 275 */         if (saveformatcomparator.isHardcoreModeEnabled()) {
/* 276 */           s2 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
/*     */         }
/*     */         
/* 279 */         if (saveformatcomparator.getCheatsEnabled()) {
/* 280 */           s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/*     */         }
/*     */       } 
/*     */       
/* 284 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
/* 285 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
/* 286 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSelectWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */