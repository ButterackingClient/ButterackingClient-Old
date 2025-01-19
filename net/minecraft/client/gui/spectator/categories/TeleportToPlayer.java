/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ 
/*    */ import com.google.common.collect.ComparisonChain;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Ordering;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiSpectator;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*    */ import net.minecraft.client.gui.spectator.PlayerMenuObject;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ public class TeleportToPlayer
/*    */   implements ISpectatorMenuView, ISpectatorMenuObject
/*    */ {
/* 24 */   private static final Ordering<NetworkPlayerInfo> field_178674_a = Ordering.from(new Comparator<NetworkPlayerInfo>() {
/*    */         public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 26 */           return ComparisonChain.start().compare(p_compare_1_.getGameProfile().getId(), p_compare_2_.getGameProfile().getId()).result();
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   public TeleportToPlayer() {
/* 32 */     this(field_178674_a.sortedCopy(Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()));
/*    */   }
/*    */   private final List<ISpectatorMenuObject> field_178673_b;
/*    */   public TeleportToPlayer(Collection<NetworkPlayerInfo> p_i45493_1_) {
/* 36 */     this.field_178673_b = Lists.newArrayList();
/*    */     
/* 38 */     for (NetworkPlayerInfo networkplayerinfo : field_178674_a.sortedCopy(p_i45493_1_)) {
/* 39 */       if (networkplayerinfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
/* 40 */         this.field_178673_b.add(new PlayerMenuObject(networkplayerinfo.getGameProfile()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<ISpectatorMenuObject> func_178669_a() {
/* 46 */     return this.field_178673_b;
/*    */   }
/*    */   
/*    */   public IChatComponent func_178670_b() {
/* 50 */     return (IChatComponent)new ChatComponentText("Select a player to teleport to");
/*    */   }
/*    */   
/*    */   public void func_178661_a(SpectatorMenu menu) {
/* 54 */     menu.func_178647_a(this);
/*    */   }
/*    */   
/*    */   public IChatComponent getSpectatorName() {
/* 58 */     return (IChatComponent)new ChatComponentText("Teleport to player");
/*    */   }
/*    */   
/*    */   public void func_178663_a(float p_178663_1_, int alpha) {
/* 62 */     Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
/* 63 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*    */   }
/*    */   
/*    */   public boolean func_178662_A_() {
/* 67 */     return !this.field_178673_b.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\spectator\categories\TeleportToPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */