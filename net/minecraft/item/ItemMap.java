/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultiset;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Multiset;
/*     */ import com.google.common.collect.Multisets;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class ItemMap extends ItemMapBase {
/*     */   protected ItemMap() {
/*  28 */     setHasSubtypes(true);
/*     */   }
/*     */   
/*     */   public static MapData loadMapData(int mapId, World worldIn) {
/*  32 */     String s = "map_" + mapId;
/*  33 */     MapData mapdata = (MapData)worldIn.loadItemData(MapData.class, s);
/*     */     
/*  35 */     if (mapdata == null) {
/*  36 */       mapdata = new MapData(s);
/*  37 */       worldIn.setItemData(s, (WorldSavedData)mapdata);
/*     */     } 
/*     */     
/*  40 */     return mapdata;
/*     */   }
/*     */   
/*     */   public MapData getMapData(ItemStack stack, World worldIn) {
/*  44 */     String s = "map_" + stack.getMetadata();
/*  45 */     MapData mapdata = (MapData)worldIn.loadItemData(MapData.class, s);
/*     */     
/*  47 */     if (mapdata == null && !worldIn.isRemote) {
/*  48 */       stack.setItemDamage(worldIn.getUniqueDataId("map"));
/*  49 */       s = "map_" + stack.getMetadata();
/*  50 */       mapdata = new MapData(s);
/*  51 */       mapdata.scale = 3;
/*  52 */       mapdata.calculateMapCenter(worldIn.getWorldInfo().getSpawnX(), worldIn.getWorldInfo().getSpawnZ(), mapdata.scale);
/*  53 */       mapdata.dimension = (byte)worldIn.provider.getDimensionId();
/*  54 */       mapdata.markDirty();
/*  55 */       worldIn.setItemData(s, (WorldSavedData)mapdata);
/*     */     } 
/*     */     
/*  58 */     return mapdata;
/*     */   }
/*     */   
/*     */   public void updateMapData(World worldIn, Entity viewer, MapData data) {
/*  62 */     if (worldIn.provider.getDimensionId() == data.dimension && viewer instanceof EntityPlayer) {
/*  63 */       int i = 1 << data.scale;
/*  64 */       int j = data.xCenter;
/*  65 */       int k = data.zCenter;
/*  66 */       int l = MathHelper.floor_double(viewer.posX - j) / i + 64;
/*  67 */       int i1 = MathHelper.floor_double(viewer.posZ - k) / i + 64;
/*  68 */       int j1 = 128 / i;
/*     */       
/*  70 */       if (worldIn.provider.getHasNoSky()) {
/*  71 */         j1 /= 2;
/*     */       }
/*     */       
/*  74 */       MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer)viewer);
/*  75 */       mapdata$mapinfo.field_82569_d++;
/*  76 */       boolean flag = false;
/*     */       
/*  78 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*  79 */         if ((k1 & 0xF) == (mapdata$mapinfo.field_82569_d & 0xF) || flag) {
/*  80 */           flag = false;
/*  81 */           double d0 = 0.0D;
/*     */           
/*  83 */           for (int l1 = i1 - j1 - 1; l1 < i1 + j1; l1++) {
/*  84 */             if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
/*  85 */               int i2 = k1 - l;
/*  86 */               int j2 = l1 - i1;
/*  87 */               boolean flag1 = (i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2));
/*  88 */               int k2 = (j / i + k1 - 64) * i;
/*  89 */               int l2 = (k / i + l1 - 64) * i;
/*  90 */               HashMultiset hashMultiset = HashMultiset.create();
/*  91 */               Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));
/*     */               
/*  93 */               if (!chunk.isEmpty()) {
/*  94 */                 int i3 = k2 & 0xF;
/*  95 */                 int j3 = l2 & 0xF;
/*  96 */                 int k3 = 0;
/*  97 */                 double d1 = 0.0D;
/*     */                 
/*  99 */                 if (worldIn.provider.getHasNoSky()) {
/* 100 */                   int l3 = k2 + l2 * 231871;
/* 101 */                   l3 = l3 * l3 * 31287121 + l3 * 11;
/*     */                   
/* 103 */                   if ((l3 >> 20 & 0x1) == 0) {
/* 104 */                     hashMultiset.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT)), 10);
/*     */                   } else {
/* 106 */                     hashMultiset.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.STONE)), 100);
/*     */                   } 
/*     */                   
/* 109 */                   d1 = 100.0D;
/*     */                 } else {
/* 111 */                   BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */                   
/* 113 */                   for (int i4 = 0; i4 < i; i4++) {
/* 114 */                     for (int j4 = 0; j4 < i; j4++) {
/* 115 */                       int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
/* 116 */                       IBlockState iblockstate = Blocks.air.getDefaultState();
/*     */                       
/* 118 */                       if (k4 > 1) {
/*     */                         
/*     */                         do
/*     */                         {
/* 122 */                           k4--;
/* 123 */                           iblockstate = chunk.getBlockState((BlockPos)blockpos$mutableblockpos.set(i4 + i3, k4, j4 + j3));
/*     */                         }
/* 125 */                         while (iblockstate.getBlock().getMapColor(iblockstate) == MapColor.airColor && k4 > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */                         
/* 130 */                         if (k4 > 0 && iblockstate.getBlock().getMaterial().isLiquid())
/* 131 */                         { Block block; int l4 = k4 - 1;
/*     */                           
/*     */                           do {
/* 134 */                             block = chunk.getBlock(i4 + i3, l4--, j4 + j3);
/* 135 */                             k3++;
/*     */                           }
/* 137 */                           while (l4 > 0 && block.getMaterial().isLiquid()); } 
/* 138 */                       }  d1 += 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                         
/* 145 */                         k4 / (i * i);
/* 146 */                       hashMultiset.add(iblockstate.getBlock().getMapColor(iblockstate));
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 151 */                 k3 /= i * i;
/* 152 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + l1 & 0x1) - 0.5D) * 0.4D;
/* 153 */                 int i5 = 1;
/*     */                 
/* 155 */                 if (d2 > 0.6D) {
/* 156 */                   i5 = 2;
/*     */                 }
/*     */                 
/* 159 */                 if (d2 < -0.6D) {
/* 160 */                   i5 = 0;
/*     */                 }
/*     */                 
/* 163 */                 MapColor mapcolor = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)hashMultiset), MapColor.airColor);
/*     */                 
/* 165 */                 if (mapcolor == MapColor.waterColor) {
/* 166 */                   d2 = k3 * 0.1D + (k1 + l1 & 0x1) * 0.2D;
/* 167 */                   i5 = 1;
/*     */                   
/* 169 */                   if (d2 < 0.5D) {
/* 170 */                     i5 = 2;
/*     */                   }
/*     */                   
/* 173 */                   if (d2 > 0.9D) {
/* 174 */                     i5 = 0;
/*     */                   }
/*     */                 } 
/*     */                 
/* 178 */                 d0 = d1;
/*     */                 
/* 180 */                 if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 0x1) != 0)) {
/* 181 */                   byte b0 = data.colors[k1 + l1 * 128];
/* 182 */                   byte b1 = (byte)(mapcolor.colorIndex * 4 + i5);
/*     */                   
/* 184 */                   if (b0 != b1) {
/* 185 */                     data.colors[k1 + l1 * 128] = b1;
/* 186 */                     data.updateMapData(k1, l1);
/* 187 */                     flag = true;
/*     */                   } 
/*     */                 } 
/*     */               } 
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
/*     */   
/*     */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
/* 203 */     if (!worldIn.isRemote) {
/* 204 */       MapData mapdata = getMapData(stack, worldIn);
/*     */       
/* 206 */       if (entityIn instanceof EntityPlayer) {
/* 207 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 208 */         mapdata.updateVisiblePlayers(entityplayer, stack);
/*     */       } 
/*     */       
/* 211 */       if (isSelected) {
/* 212 */         updateMapData(worldIn, entityIn, mapdata);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Packet createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
/* 218 */     return getMapData(stack, worldIn).getMapPacket(stack, worldIn, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 225 */     if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("map_is_scaling")) {
/* 226 */       MapData mapdata = Items.filled_map.getMapData(stack, worldIn);
/* 227 */       stack.setItemDamage(worldIn.getUniqueDataId("map"));
/* 228 */       MapData mapdata1 = new MapData("map_" + stack.getMetadata());
/* 229 */       mapdata1.scale = (byte)(mapdata.scale + 1);
/*     */       
/* 231 */       if (mapdata1.scale > 4) {
/* 232 */         mapdata1.scale = 4;
/*     */       }
/*     */       
/* 235 */       mapdata1.calculateMapCenter(mapdata.xCenter, mapdata.zCenter, mapdata1.scale);
/* 236 */       mapdata1.dimension = mapdata.dimension;
/* 237 */       mapdata1.markDirty();
/* 238 */       worldIn.setItemData("map_" + stack.getMetadata(), (WorldSavedData)mapdata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 246 */     MapData mapdata = getMapData(stack, playerIn.worldObj);
/*     */     
/* 248 */     if (advanced)
/* 249 */       if (mapdata == null) {
/* 250 */         tooltip.add("Unknown map");
/*     */       } else {
/* 252 */         tooltip.add("Scaling at 1:" + (1 << mapdata.scale));
/* 253 */         tooltip.add("(Level " + mapdata.scale + "/" + '\004' + ")");
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */