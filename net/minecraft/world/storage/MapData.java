/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S34PacketMaps;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapData
/*     */   extends WorldSavedData
/*     */ {
/*     */   public int xCenter;
/*     */   public int zCenter;
/*     */   public byte dimension;
/*     */   public byte scale;
/*  31 */   public byte[] colors = new byte[16384];
/*  32 */   public List<MapInfo> playersArrayList = Lists.newArrayList();
/*  33 */   private Map<EntityPlayer, MapInfo> playersHashMap = Maps.newHashMap();
/*  34 */   public Map<String, Vec4b> mapDecorations = Maps.newLinkedHashMap();
/*     */   
/*     */   public MapData(String mapname) {
/*  37 */     super(mapname);
/*     */   }
/*     */   
/*     */   public void calculateMapCenter(double x, double z, int mapScale) {
/*  41 */     int i = 128 * (1 << mapScale);
/*  42 */     int j = MathHelper.floor_double((x + 64.0D) / i);
/*  43 */     int k = MathHelper.floor_double((z + 64.0D) / i);
/*  44 */     this.xCenter = j * i + i / 2 - 64;
/*  45 */     this.zCenter = k * i + i / 2 - 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  52 */     this.dimension = nbt.getByte("dimension");
/*  53 */     this.xCenter = nbt.getInteger("xCenter");
/*  54 */     this.zCenter = nbt.getInteger("zCenter");
/*  55 */     this.scale = nbt.getByte("scale");
/*  56 */     this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
/*  57 */     int i = nbt.getShort("width");
/*  58 */     int j = nbt.getShort("height");
/*     */     
/*  60 */     if (i == 128 && j == 128) {
/*  61 */       this.colors = nbt.getByteArray("colors");
/*     */     } else {
/*  63 */       byte[] abyte = nbt.getByteArray("colors");
/*  64 */       this.colors = new byte[16384];
/*  65 */       int k = (128 - i) / 2;
/*  66 */       int l = (128 - j) / 2;
/*     */       
/*  68 */       for (int i1 = 0; i1 < j; i1++) {
/*  69 */         int j1 = i1 + l;
/*     */         
/*  71 */         if (j1 >= 0 || j1 < 128) {
/*  72 */           for (int k1 = 0; k1 < i; k1++) {
/*  73 */             int l1 = k1 + k;
/*     */             
/*  75 */             if (l1 >= 0 || l1 < 128) {
/*  76 */               this.colors[l1 + j1 * 128] = abyte[k1 + i1 * i];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  88 */     nbt.setByte("dimension", this.dimension);
/*  89 */     nbt.setInteger("xCenter", this.xCenter);
/*  90 */     nbt.setInteger("zCenter", this.zCenter);
/*  91 */     nbt.setByte("scale", this.scale);
/*  92 */     nbt.setShort("width", (short)128);
/*  93 */     nbt.setShort("height", (short)128);
/*  94 */     nbt.setByteArray("colors", this.colors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVisiblePlayers(EntityPlayer player, ItemStack mapStack) {
/* 101 */     if (!this.playersHashMap.containsKey(player)) {
/* 102 */       MapInfo mapdata$mapinfo = new MapInfo(player);
/* 103 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 104 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 107 */     if (!player.inventory.hasItemStack(mapStack)) {
/* 108 */       this.mapDecorations.remove(player.getName());
/*     */     }
/*     */     
/* 111 */     for (int i = 0; i < this.playersArrayList.size(); i++) {
/* 112 */       MapInfo mapdata$mapinfo1 = this.playersArrayList.get(i);
/*     */       
/* 114 */       if (!mapdata$mapinfo1.entityplayerObj.isDead && (mapdata$mapinfo1.entityplayerObj.inventory.hasItemStack(mapStack) || mapStack.isOnItemFrame())) {
/* 115 */         if (!mapStack.isOnItemFrame() && mapdata$mapinfo1.entityplayerObj.dimension == this.dimension) {
/* 116 */           updateDecorations(0, mapdata$mapinfo1.entityplayerObj.worldObj, mapdata$mapinfo1.entityplayerObj.getName(), mapdata$mapinfo1.entityplayerObj.posX, mapdata$mapinfo1.entityplayerObj.posZ, mapdata$mapinfo1.entityplayerObj.rotationYaw);
/*     */         }
/*     */       } else {
/* 119 */         this.playersHashMap.remove(mapdata$mapinfo1.entityplayerObj);
/* 120 */         this.playersArrayList.remove(mapdata$mapinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     if (mapStack.isOnItemFrame()) {
/* 125 */       EntityItemFrame entityitemframe = mapStack.getItemFrame();
/* 126 */       BlockPos blockpos = entityitemframe.getHangingPosition();
/* 127 */       updateDecorations(1, player.worldObj, "frame-" + entityitemframe.getEntityId(), blockpos.getX(), blockpos.getZ(), (entityitemframe.facingDirection.getHorizontalIndex() * 90));
/*     */     } 
/*     */     
/* 130 */     if (mapStack.hasTagCompound() && mapStack.getTagCompound().hasKey("Decorations", 9)) {
/* 131 */       NBTTagList nbttaglist = mapStack.getTagCompound().getTagList("Decorations", 10);
/*     */       
/* 133 */       for (int j = 0; j < nbttaglist.tagCount(); j++) {
/* 134 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
/*     */         
/* 136 */         if (!this.mapDecorations.containsKey(nbttagcompound.getString("id")))
/* 137 */           updateDecorations(nbttagcompound.getByte("type"), player.worldObj, nbttagcompound.getString("id"), nbttagcompound.getDouble("x"), nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot")); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateDecorations(int type, World worldIn, String entityIdentifier, double worldX, double worldZ, double rotation) {
/*     */     byte b2;
/* 144 */     int i = 1 << this.scale;
/* 145 */     float f = (float)(worldX - this.xCenter) / i;
/* 146 */     float f1 = (float)(worldZ - this.zCenter) / i;
/* 147 */     byte b0 = (byte)(int)((f * 2.0F) + 0.5D);
/* 148 */     byte b1 = (byte)(int)((f1 * 2.0F) + 0.5D);
/* 149 */     int j = 63;
/*     */ 
/*     */     
/* 152 */     if (f >= -j && f1 >= -j && f <= j && f1 <= j) {
/* 153 */       rotation += (rotation < 0.0D) ? -8.0D : 8.0D;
/* 154 */       b2 = (byte)(int)(rotation * 16.0D / 360.0D);
/*     */       
/* 156 */       if (this.dimension < 0) {
/* 157 */         int k = (int)(worldIn.getWorldInfo().getWorldTime() / 10L);
/* 158 */         b2 = (byte)(k * k * 34187121 + k * 121 >> 15 & 0xF);
/*     */       } 
/*     */     } else {
/* 161 */       if (Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
/* 162 */         this.mapDecorations.remove(entityIdentifier);
/*     */         
/*     */         return;
/*     */       } 
/* 166 */       type = 6;
/* 167 */       b2 = 0;
/*     */       
/* 169 */       if (f <= -j) {
/* 170 */         b0 = (byte)(int)((j * 2) + 2.5D);
/*     */       }
/*     */       
/* 173 */       if (f1 <= -j) {
/* 174 */         b1 = (byte)(int)((j * 2) + 2.5D);
/*     */       }
/*     */       
/* 177 */       if (f >= j) {
/* 178 */         b0 = (byte)(j * 2 + 1);
/*     */       }
/*     */       
/* 181 */       if (f1 >= j) {
/* 182 */         b1 = (byte)(j * 2 + 1);
/*     */       }
/*     */     } 
/*     */     
/* 186 */     this.mapDecorations.put(entityIdentifier, new Vec4b((byte)type, b0, b1, b2));
/*     */   }
/*     */   
/*     */   public Packet getMapPacket(ItemStack mapStack, World worldIn, EntityPlayer player) {
/* 190 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/* 191 */     return (mapdata$mapinfo == null) ? null : mapdata$mapinfo.getPacket(mapStack);
/*     */   }
/*     */   
/*     */   public void updateMapData(int x, int y) {
/* 195 */     markDirty();
/*     */     
/* 197 */     for (MapInfo mapdata$mapinfo : this.playersArrayList) {
/* 198 */       mapdata$mapinfo.update(x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   public MapInfo getMapInfo(EntityPlayer player) {
/* 203 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/*     */     
/* 205 */     if (mapdata$mapinfo == null) {
/* 206 */       mapdata$mapinfo = new MapInfo(player);
/* 207 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 208 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 211 */     return mapdata$mapinfo;
/*     */   }
/*     */   
/*     */   public class MapInfo {
/*     */     public final EntityPlayer entityplayerObj;
/*     */     private boolean field_176105_d = true;
/* 217 */     private int minX = 0;
/* 218 */     private int minY = 0;
/* 219 */     private int maxX = 127;
/* 220 */     private int maxY = 127;
/*     */     private int field_176109_i;
/*     */     public int field_82569_d;
/*     */     
/*     */     public MapInfo(EntityPlayer player) {
/* 225 */       this.entityplayerObj = player;
/*     */     }
/*     */     
/*     */     public Packet getPacket(ItemStack stack) {
/* 229 */       if (this.field_176105_d) {
/* 230 */         this.field_176105_d = false;
/* 231 */         return (Packet)new S34PacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(), MapData.this.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
/*     */       } 
/* 233 */       return (this.field_176109_i++ % 5 == 0) ? (Packet)new S34PacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(), MapData.this.colors, 0, 0, 0, 0) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(int x, int y) {
/* 238 */       if (this.field_176105_d) {
/* 239 */         this.minX = Math.min(this.minX, x);
/* 240 */         this.minY = Math.min(this.minY, y);
/* 241 */         this.maxX = Math.max(this.maxX, x);
/* 242 */         this.maxY = Math.max(this.maxY, y);
/*     */       } else {
/* 244 */         this.field_176105_d = true;
/* 245 */         this.minX = x;
/* 246 */         this.minY = y;
/* 247 */         this.maxX = x;
/* 248 */         this.maxY = y;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\MapData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */