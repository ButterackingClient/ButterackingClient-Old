/*    */ package net.minecraft.server.integrated;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.ServerConfigurationManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegratedPlayerList
/*    */   extends ServerConfigurationManager
/*    */ {
/*    */   private NBTTagCompound hostPlayerData;
/*    */   
/*    */   public IntegratedPlayerList(IntegratedServer server) {
/* 18 */     super(server);
/* 19 */     setViewDistance(10);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writePlayerData(EntityPlayerMP playerIn) {
/* 26 */     if (playerIn.getName().equals(getServerInstance().getServerOwner())) {
/* 27 */       this.hostPlayerData = new NBTTagCompound();
/* 28 */       playerIn.writeToNBT(this.hostPlayerData);
/*    */     } 
/*    */     
/* 31 */     super.writePlayerData(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/* 38 */     return (profile.getName().equalsIgnoreCase(getServerInstance().getServerOwner()) && getPlayerByUsername(profile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(address, profile);
/*    */   }
/*    */   
/*    */   public IntegratedServer getServerInstance() {
/* 42 */     return (IntegratedServer)super.getServerInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagCompound getHostPlayerData() {
/* 49 */     return this.hostPlayerData;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\integrated\IntegratedPlayerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */