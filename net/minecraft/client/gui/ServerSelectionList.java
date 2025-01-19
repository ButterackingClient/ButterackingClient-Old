/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ServerList;
/*    */ import net.minecraft.client.network.LanServerDetector;
/*    */ 
/*    */ public class ServerSelectionList
/*    */   extends GuiListExtended
/*    */ {
/*    */   private final GuiMultiplayer owner;
/* 13 */   private final List<ServerListEntryNormal> serverListInternet = Lists.newArrayList();
/* 14 */   private final List<ServerListEntryLanDetected> serverListLan = Lists.newArrayList();
/* 15 */   private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
/* 16 */   private int selectedSlotIndex = -1;
/*    */   
/*    */   public ServerSelectionList(GuiMultiplayer ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/* 19 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/* 20 */     this.owner = ownerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/* 27 */     if (index < this.serverListInternet.size()) {
/* 28 */       return this.serverListInternet.get(index);
/*    */     }
/* 30 */     index -= this.serverListInternet.size();
/*    */     
/* 32 */     if (index == 0) {
/* 33 */       return this.lanScanEntry;
/*    */     }
/* 35 */     index--;
/* 36 */     return this.serverListLan.get(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getSize() {
/* 42 */     return this.serverListInternet.size() + 1 + this.serverListLan.size();
/*    */   }
/*    */   
/*    */   public void setSelectedSlotIndex(int selectedSlotIndexIn) {
/* 46 */     this.selectedSlotIndex = selectedSlotIndexIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 53 */     return (slotIndex == this.selectedSlotIndex);
/*    */   }
/*    */   
/*    */   public int func_148193_k() {
/* 57 */     return this.selectedSlotIndex;
/*    */   }
/*    */   
/*    */   public void func_148195_a(ServerList p_148195_1_) {
/* 61 */     this.serverListInternet.clear();
/*    */     
/* 63 */     for (int i = 0; i < p_148195_1_.countServers(); i++) {
/* 64 */       this.serverListInternet.add(new ServerListEntryNormal(this.owner, p_148195_1_.getServerData(i)));
/*    */     }
/*    */   }
/*    */   
/*    */   public void func_148194_a(List<LanServerDetector.LanServer> p_148194_1_) {
/* 69 */     this.serverListLan.clear();
/*    */     
/* 71 */     for (LanServerDetector.LanServer lanserverdetector$lanserver : p_148194_1_) {
/* 72 */       this.serverListLan.add(new ServerListEntryLanDetected(this.owner, lanserverdetector$lanserver));
/*    */     }
/*    */   }
/*    */   
/*    */   protected int getScrollBarX() {
/* 77 */     return super.getScrollBarX() + 30;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getListWidth() {
/* 84 */     return super.getListWidth() + 85;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ServerSelectionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */