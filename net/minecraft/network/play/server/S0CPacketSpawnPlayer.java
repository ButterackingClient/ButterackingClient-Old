/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class S0CPacketSpawnPlayer
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*     */   private UUID playerId;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private byte yaw;
/*     */   private byte pitch;
/*     */   private int currentItem;
/*     */   private DataWatcher watcher;
/*     */   private List<DataWatcher.WatchableObject> field_148958_j;
/*     */   
/*     */   public S0CPacketSpawnPlayer() {}
/*     */   
/*     */   public S0CPacketSpawnPlayer(EntityPlayer player) {
/*  32 */     this.entityId = player.getEntityId();
/*  33 */     this.playerId = player.getGameProfile().getId();
/*  34 */     this.x = MathHelper.floor_double(player.posX * 32.0D);
/*  35 */     this.y = MathHelper.floor_double(player.posY * 32.0D);
/*  36 */     this.z = MathHelper.floor_double(player.posZ * 32.0D);
/*  37 */     this.yaw = (byte)(int)(player.rotationYaw * 256.0F / 360.0F);
/*  38 */     this.pitch = (byte)(int)(player.rotationPitch * 256.0F / 360.0F);
/*  39 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*  40 */     this.currentItem = (itemstack == null) ? 0 : Item.getIdFromItem(itemstack.getItem());
/*  41 */     this.watcher = player.getDataWatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  48 */     this.entityId = buf.readVarIntFromBuffer();
/*  49 */     this.playerId = buf.readUuid();
/*  50 */     this.x = buf.readInt();
/*  51 */     this.y = buf.readInt();
/*  52 */     this.z = buf.readInt();
/*  53 */     this.yaw = buf.readByte();
/*  54 */     this.pitch = buf.readByte();
/*  55 */     this.currentItem = buf.readShort();
/*  56 */     this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  63 */     buf.writeVarIntToBuffer(this.entityId);
/*  64 */     buf.writeUuid(this.playerId);
/*  65 */     buf.writeInt(this.x);
/*  66 */     buf.writeInt(this.y);
/*  67 */     buf.writeInt(this.z);
/*  68 */     buf.writeByte(this.yaw);
/*  69 */     buf.writeByte(this.pitch);
/*  70 */     buf.writeShort(this.currentItem);
/*  71 */     this.watcher.writeTo(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  78 */     handler.handleSpawnPlayer(this);
/*     */   }
/*     */   
/*     */   public List<DataWatcher.WatchableObject> func_148944_c() {
/*  82 */     if (this.field_148958_j == null) {
/*  83 */       this.field_148958_j = this.watcher.getAllWatched();
/*     */     }
/*     */     
/*  86 */     return this.field_148958_j;
/*     */   }
/*     */   
/*     */   public int getEntityID() {
/*  90 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public UUID getPlayer() {
/*  94 */     return this.playerId;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  98 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 102 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 106 */     return this.z;
/*     */   }
/*     */   
/*     */   public byte getYaw() {
/* 110 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public byte getPitch() {
/* 114 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public int getCurrentItemID() {
/* 118 */     return this.currentItem;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0CPacketSpawnPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */