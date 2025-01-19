/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*    */ import net.minecraft.network.play.server.S28PacketEffect;
/*    */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldManager
/*    */   implements IWorldAccess
/*    */ {
/*    */   private MinecraftServer mcServer;
/*    */   private WorldServer theWorldServer;
/*    */   
/*    */   public WorldManager(MinecraftServer mcServerIn, WorldServer worldServerIn) {
/* 24 */     this.mcServer = mcServerIn;
/* 25 */     this.theWorldServer = worldServerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... parameters) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdded(Entity entityIn) {
/* 36 */     this.theWorldServer.getEntityTracker().trackEntity(entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(Entity entityIn) {
/* 44 */     this.theWorldServer.getEntityTracker().untrackEntity(entityIn);
/* 45 */     this.theWorldServer.getScoreboard().func_181140_a(entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
/* 52 */     this.mcServer.getConfigurationManager().sendToAllNear(x, y, z, (volume > 1.0F) ? (16.0F * volume) : 16.0D, this.theWorldServer.provider.getDimensionId(), (Packet)new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {
/* 59 */     this.mcServer.getConfigurationManager().sendToAllNearExcept(except, x, y, z, (volume > 1.0F) ? (16.0F * volume) : 16.0D, this.theWorldServer.provider.getDimensionId(), (Packet)new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void markBlockForUpdate(BlockPos pos) {
/* 70 */     this.theWorldServer.getPlayerManager().markBlockForUpdate(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void notifyLightSet(BlockPos pos) {}
/*    */ 
/*    */   
/*    */   public void playRecord(String recordName, BlockPos blockPosIn) {}
/*    */   
/*    */   public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int data) {
/* 80 */     this.mcServer.getConfigurationManager().sendToAllNearExcept(player, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ(), 64.0D, this.theWorldServer.provider.getDimensionId(), (Packet)new S28PacketEffect(sfxType, blockPosIn, data, false));
/*    */   }
/*    */   
/*    */   public void broadcastSound(int soundID, BlockPos pos, int data) {
/* 84 */     this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S28PacketEffect(soundID, pos, data, true));
/*    */   }
/*    */   
/*    */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 88 */     for (EntityPlayerMP entityplayermp : this.mcServer.getConfigurationManager().getPlayerList()) {
/* 89 */       if (entityplayermp != null && entityplayermp.worldObj == this.theWorldServer && entityplayermp.getEntityId() != breakerId) {
/* 90 */         double d0 = pos.getX() - entityplayermp.posX;
/* 91 */         double d1 = pos.getY() - entityplayermp.posY;
/* 92 */         double d2 = pos.getZ() - entityplayermp.posZ;
/*    */         
/* 94 */         if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D)
/* 95 */           entityplayermp.playerNetServerHandler.sendPacket((Packet)new S25PacketBlockBreakAnim(breakerId, pos, progress)); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */