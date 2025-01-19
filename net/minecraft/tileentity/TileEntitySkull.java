/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class TileEntitySkull
/*     */   extends TileEntity
/*     */ {
/*     */   private int skullType;
/*     */   private int skullRotation;
/*  19 */   private GameProfile playerProfile = null;
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  22 */     super.writeToNBT(compound);
/*  23 */     compound.setByte("SkullType", (byte)(this.skullType & 0xFF));
/*  24 */     compound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
/*     */     
/*  26 */     if (this.playerProfile != null) {
/*  27 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  28 */       NBTUtil.writeGameProfile(nbttagcompound, this.playerProfile);
/*  29 */       compound.setTag("Owner", (NBTBase)nbttagcompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  34 */     super.readFromNBT(compound);
/*  35 */     this.skullType = compound.getByte("SkullType");
/*  36 */     this.skullRotation = compound.getByte("Rot");
/*     */     
/*  38 */     if (this.skullType == 3) {
/*  39 */       if (compound.hasKey("Owner", 10)) {
/*  40 */         this.playerProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
/*  41 */       } else if (compound.hasKey("ExtraType", 8)) {
/*  42 */         String s = compound.getString("ExtraType");
/*     */         
/*  44 */         if (!StringUtils.isNullOrEmpty(s)) {
/*  45 */           this.playerProfile = new GameProfile(null, s);
/*  46 */           updatePlayerProfile();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public GameProfile getPlayerProfile() {
/*  53 */     return this.playerProfile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/*  61 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  62 */     writeToNBT(nbttagcompound);
/*  63 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 4, nbttagcompound);
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/*  67 */     this.skullType = type;
/*  68 */     this.playerProfile = null;
/*     */   }
/*     */   
/*     */   public void setPlayerProfile(GameProfile playerProfile) {
/*  72 */     this.skullType = 3;
/*  73 */     this.playerProfile = playerProfile;
/*  74 */     updatePlayerProfile();
/*     */   }
/*     */   
/*     */   private void updatePlayerProfile() {
/*  78 */     this.playerProfile = updateGameprofile(this.playerProfile);
/*  79 */     markDirty();
/*     */   }
/*     */   
/*     */   public static GameProfile updateGameprofile(GameProfile input) {
/*  83 */     if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
/*  84 */       if (input.isComplete() && input.getProperties().containsKey("textures"))
/*  85 */         return input; 
/*  86 */       if (MinecraftServer.getServer() == null) {
/*  87 */         return input;
/*     */       }
/*  89 */       GameProfile gameprofile = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(input.getName());
/*     */       
/*  91 */       if (gameprofile == null) {
/*  92 */         return input;
/*     */       }
/*  94 */       Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), null);
/*     */       
/*  96 */       if (property == null) {
/*  97 */         gameprofile = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameprofile, true);
/*     */       }
/*     */       
/* 100 */       return gameprofile;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return input;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkullType() {
/* 109 */     return this.skullType;
/*     */   }
/*     */   
/*     */   public int getSkullRotation() {
/* 113 */     return this.skullRotation;
/*     */   }
/*     */   
/*     */   public void setSkullRotation(int rotation) {
/* 117 */     this.skullRotation = rotation;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntitySkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */