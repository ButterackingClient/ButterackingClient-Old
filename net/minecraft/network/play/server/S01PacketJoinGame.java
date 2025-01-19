/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ public class S01PacketJoinGame
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*     */   private boolean hardcoreMode;
/*     */   private WorldSettings.GameType gameType;
/*     */   private int dimension;
/*     */   private EnumDifficulty difficulty;
/*     */   private int maxPlayers;
/*     */   private WorldType worldType;
/*     */   private boolean reducedDebugInfo;
/*     */   
/*     */   public S01PacketJoinGame() {}
/*     */   
/*     */   public S01PacketJoinGame(int entityIdIn, WorldSettings.GameType gameTypeIn, boolean hardcoreModeIn, int dimensionIn, EnumDifficulty difficultyIn, int maxPlayersIn, WorldType worldTypeIn, boolean reducedDebugInfoIn) {
/*  26 */     this.entityId = entityIdIn;
/*  27 */     this.dimension = dimensionIn;
/*  28 */     this.difficulty = difficultyIn;
/*  29 */     this.gameType = gameTypeIn;
/*  30 */     this.maxPlayers = maxPlayersIn;
/*  31 */     this.hardcoreMode = hardcoreModeIn;
/*  32 */     this.worldType = worldTypeIn;
/*  33 */     this.reducedDebugInfo = reducedDebugInfoIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  40 */     this.entityId = buf.readInt();
/*  41 */     int i = buf.readUnsignedByte();
/*  42 */     this.hardcoreMode = ((i & 0x8) == 8);
/*  43 */     i &= 0xFFFFFFF7;
/*  44 */     this.gameType = WorldSettings.GameType.getByID(i);
/*  45 */     this.dimension = buf.readByte();
/*  46 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/*  47 */     this.maxPlayers = buf.readUnsignedByte();
/*  48 */     this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
/*     */     
/*  50 */     if (this.worldType == null) {
/*  51 */       this.worldType = WorldType.DEFAULT;
/*     */     }
/*     */     
/*  54 */     this.reducedDebugInfo = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  61 */     buf.writeInt(this.entityId);
/*  62 */     int i = this.gameType.getID();
/*     */     
/*  64 */     if (this.hardcoreMode) {
/*  65 */       i |= 0x8;
/*     */     }
/*     */     
/*  68 */     buf.writeByte(i);
/*  69 */     buf.writeByte(this.dimension);
/*  70 */     buf.writeByte(this.difficulty.getDifficultyId());
/*  71 */     buf.writeByte(this.maxPlayers);
/*  72 */     buf.writeString(this.worldType.getWorldTypeName());
/*  73 */     buf.writeBoolean(this.reducedDebugInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  80 */     handler.handleJoinGame(this);
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*  84 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public boolean isHardcoreMode() {
/*  88 */     return this.hardcoreMode;
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/*  92 */     return this.gameType;
/*     */   }
/*     */   
/*     */   public int getDimension() {
/*  96 */     return this.dimension;
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 100 */     return this.difficulty;
/*     */   }
/*     */   
/*     */   public int getMaxPlayers() {
/* 104 */     return this.maxPlayers;
/*     */   }
/*     */   
/*     */   public WorldType getWorldType() {
/* 108 */     return this.worldType;
/*     */   }
/*     */   
/*     */   public boolean isReducedDebugInfo() {
/* 112 */     return this.reducedDebugInfo;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S01PacketJoinGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */