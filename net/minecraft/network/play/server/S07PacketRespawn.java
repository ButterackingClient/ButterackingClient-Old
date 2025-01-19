/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ import net.minecraft.world.WorldType;
/*    */ 
/*    */ public class S07PacketRespawn
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int dimensionID;
/*    */   private EnumDifficulty difficulty;
/*    */   private WorldSettings.GameType gameType;
/*    */   private WorldType worldType;
/*    */   
/*    */   public S07PacketRespawn() {}
/*    */   
/*    */   public S07PacketRespawn(int dimensionIDIn, EnumDifficulty difficultyIn, WorldType worldTypeIn, WorldSettings.GameType gameTypeIn) {
/* 22 */     this.dimensionID = dimensionIDIn;
/* 23 */     this.difficulty = difficultyIn;
/* 24 */     this.gameType = gameTypeIn;
/* 25 */     this.worldType = worldTypeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 32 */     handler.handleRespawn(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 39 */     this.dimensionID = buf.readInt();
/* 40 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/* 41 */     this.gameType = WorldSettings.GameType.getByID(buf.readUnsignedByte());
/* 42 */     this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
/*    */     
/* 44 */     if (this.worldType == null) {
/* 45 */       this.worldType = WorldType.DEFAULT;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 53 */     buf.writeInt(this.dimensionID);
/* 54 */     buf.writeByte(this.difficulty.getDifficultyId());
/* 55 */     buf.writeByte(this.gameType.getID());
/* 56 */     buf.writeString(this.worldType.getWorldTypeName());
/*    */   }
/*    */   
/*    */   public int getDimensionID() {
/* 60 */     return this.dimensionID;
/*    */   }
/*    */   
/*    */   public EnumDifficulty getDifficulty() {
/* 64 */     return this.difficulty;
/*    */   }
/*    */   
/*    */   public WorldSettings.GameType getGameType() {
/* 68 */     return this.gameType;
/*    */   }
/*    */   
/*    */   public WorldType getWorldType() {
/* 72 */     return this.worldType;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S07PacketRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */