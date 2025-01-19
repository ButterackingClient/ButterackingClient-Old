/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class S29PacketSoundEffect implements Packet<INetHandlerPlayClient> {
/*    */   private String soundName;
/*    */   private int posX;
/* 14 */   private int posY = Integer.MAX_VALUE;
/*    */   
/*    */   private int posZ;
/*    */   
/*    */   private float soundVolume;
/*    */   
/*    */   private int soundPitch;
/*    */   
/*    */   public S29PacketSoundEffect(String soundNameIn, double soundX, double soundY, double soundZ, float volume, float pitch) {
/* 23 */     Validate.notNull(soundNameIn, "name", new Object[0]);
/* 24 */     this.soundName = soundNameIn;
/* 25 */     this.posX = (int)(soundX * 8.0D);
/* 26 */     this.posY = (int)(soundY * 8.0D);
/* 27 */     this.posZ = (int)(soundZ * 8.0D);
/* 28 */     this.soundVolume = volume;
/* 29 */     this.soundPitch = (int)(pitch * 63.0F);
/* 30 */     pitch = MathHelper.clamp_float(pitch, 0.0F, 255.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.soundName = buf.readStringFromBuffer(256);
/* 38 */     this.posX = buf.readInt();
/* 39 */     this.posY = buf.readInt();
/* 40 */     this.posZ = buf.readInt();
/* 41 */     this.soundVolume = buf.readFloat();
/* 42 */     this.soundPitch = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeString(this.soundName);
/* 50 */     buf.writeInt(this.posX);
/* 51 */     buf.writeInt(this.posY);
/* 52 */     buf.writeInt(this.posZ);
/* 53 */     buf.writeFloat(this.soundVolume);
/* 54 */     buf.writeByte(this.soundPitch);
/*    */   }
/*    */   
/*    */   public String getSoundName() {
/* 58 */     return this.soundName;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 62 */     return (this.posX / 8.0F);
/*    */   }
/*    */   
/*    */   public double getY() {
/* 66 */     return (this.posY / 8.0F);
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 70 */     return (this.posZ / 8.0F);
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 74 */     return this.soundVolume;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 78 */     return this.soundPitch / 63.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 85 */     handler.handleSoundEffect(this);
/*    */   }
/*    */   
/*    */   public S29PacketSoundEffect() {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S29PacketSoundEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */