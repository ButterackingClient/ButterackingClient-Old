/*    */ package net.minecraft.network;
/*    */ 
/*    */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*    */ import net.minecraft.network.play.server.S07PacketRespawn;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ public class PacketThreadUtil
/*    */ {
/* 10 */   public static int lastDimensionId = Integer.MIN_VALUE;
/*    */   
/*    */   public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> p_180031_0_, final T p_180031_1_, IThreadListener p_180031_2_) throws ThreadQuickExitException {
/* 13 */     if (!p_180031_2_.isCallingFromMinecraftThread()) {
/* 14 */       p_180031_2_.addScheduledTask(new Runnable() {
/*    */             public void run() {
/* 16 */               PacketThreadUtil.clientPreProcessPacket(p_180031_0_);
/* 17 */               p_180031_0_.processPacket(p_180031_1_);
/*    */             }
/*    */           });
/* 20 */       throw ThreadQuickExitException.INSTANCE;
/*    */     } 
/* 22 */     clientPreProcessPacket(p_180031_0_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static void clientPreProcessPacket(Packet p_clientPreProcessPacket_0_) {
/* 27 */     if (p_clientPreProcessPacket_0_ instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
/* 28 */       Config.getRenderGlobal().onPlayerPositionSet();
/*    */     }
/*    */     
/* 31 */     if (p_clientPreProcessPacket_0_ instanceof S07PacketRespawn) {
/* 32 */       S07PacketRespawn s07packetrespawn = (S07PacketRespawn)p_clientPreProcessPacket_0_;
/* 33 */       lastDimensionId = s07packetrespawn.getDimensionID();
/* 34 */     } else if (p_clientPreProcessPacket_0_ instanceof S01PacketJoinGame) {
/* 35 */       S01PacketJoinGame s01packetjoingame = (S01PacketJoinGame)p_clientPreProcessPacket_0_;
/* 36 */       lastDimensionId = s01packetjoingame.getDimension();
/*    */     } else {
/* 38 */       lastDimensionId = Integer.MIN_VALUE;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\PacketThreadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */