/*     */ package net.minecraft.village;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ public class VillageCollection extends WorldSavedData {
/*  21 */   private final List<BlockPos> villagerPositionsList = Lists.newArrayList(); private World worldObj;
/*  22 */   private final List<VillageDoorInfo> newDoors = Lists.newArrayList();
/*  23 */   private final List<Village> villageList = Lists.newArrayList();
/*     */   private int tickCounter;
/*     */   
/*     */   public VillageCollection(String name) {
/*  27 */     super(name);
/*     */   }
/*     */   
/*     */   public VillageCollection(World worldIn) {
/*  31 */     super(fileNameForProvider(worldIn.provider));
/*  32 */     this.worldObj = worldIn;
/*  33 */     markDirty();
/*     */   }
/*     */   
/*     */   public void setWorldsForAll(World worldIn) {
/*  37 */     this.worldObj = worldIn;
/*     */     
/*  39 */     for (Village village : this.villageList) {
/*  40 */       village.setWorld(worldIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToVillagerPositionList(BlockPos pos) {
/*  45 */     if (this.villagerPositionsList.size() <= 64 && 
/*  46 */       !positionInList(pos)) {
/*  47 */       this.villagerPositionsList.add(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  56 */     this.tickCounter++;
/*     */     
/*  58 */     for (Village village : this.villageList) {
/*  59 */       village.tick(this.tickCounter);
/*     */     }
/*     */     
/*  62 */     removeAnnihilatedVillages();
/*  63 */     dropOldestVillagerPosition();
/*  64 */     addNewDoorsToVillageOrCreateVillage();
/*     */     
/*  66 */     if (this.tickCounter % 400 == 0) {
/*  67 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeAnnihilatedVillages() {
/*  72 */     Iterator<Village> iterator = this.villageList.iterator();
/*     */     
/*  74 */     while (iterator.hasNext()) {
/*  75 */       Village village = iterator.next();
/*     */       
/*  77 */       if (village.isAnnihilated()) {
/*  78 */         iterator.remove();
/*  79 */         markDirty();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Village> getVillageList() {
/*  85 */     return this.villageList;
/*     */   }
/*     */   
/*     */   public Village getNearestVillage(BlockPos doorBlock, int radius) {
/*  89 */     Village village = null;
/*  90 */     double d0 = 3.4028234663852886E38D;
/*     */     
/*  92 */     for (Village village1 : this.villageList) {
/*  93 */       double d1 = village1.getCenter().distanceSq((Vec3i)doorBlock);
/*     */       
/*  95 */       if (d1 < d0) {
/*  96 */         float f = (radius + village1.getVillageRadius());
/*     */         
/*  98 */         if (d1 <= (f * f)) {
/*  99 */           village = village1;
/* 100 */           d0 = d1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return village;
/*     */   }
/*     */   
/*     */   private void dropOldestVillagerPosition() {
/* 109 */     if (!this.villagerPositionsList.isEmpty()) {
/* 110 */       addDoorsAround(this.villagerPositionsList.remove(0));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addNewDoorsToVillageOrCreateVillage() {
/* 115 */     for (int i = 0; i < this.newDoors.size(); i++) {
/* 116 */       VillageDoorInfo villagedoorinfo = this.newDoors.get(i);
/* 117 */       Village village = getNearestVillage(villagedoorinfo.getDoorBlockPos(), 32);
/*     */       
/* 119 */       if (village == null) {
/* 120 */         village = new Village(this.worldObj);
/* 121 */         this.villageList.add(village);
/* 122 */         markDirty();
/*     */       } 
/*     */       
/* 125 */       village.addVillageDoorInfo(villagedoorinfo);
/*     */     } 
/*     */     
/* 128 */     this.newDoors.clear();
/*     */   }
/*     */   
/*     */   private void addDoorsAround(BlockPos central) {
/* 132 */     int i = 16;
/* 133 */     int j = 4;
/* 134 */     int k = 16;
/*     */     
/* 136 */     for (int l = -i; l < i; l++) {
/* 137 */       for (int i1 = -j; i1 < j; i1++) {
/* 138 */         for (int j1 = -k; j1 < k; j1++) {
/* 139 */           BlockPos blockpos = central.add(l, i1, j1);
/*     */           
/* 141 */           if (isWoodDoor(blockpos)) {
/* 142 */             VillageDoorInfo villagedoorinfo = checkDoorExistence(blockpos);
/*     */             
/* 144 */             if (villagedoorinfo == null) {
/* 145 */               addToNewDoorsList(blockpos);
/*     */             } else {
/* 147 */               villagedoorinfo.func_179849_a(this.tickCounter);
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
/*     */   private VillageDoorInfo checkDoorExistence(BlockPos doorBlock) {
/* 159 */     for (VillageDoorInfo villagedoorinfo : this.newDoors) {
/* 160 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1) {
/* 161 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 165 */     for (Village village : this.villageList) {
/* 166 */       VillageDoorInfo villagedoorinfo1 = village.getExistedDoor(doorBlock);
/*     */       
/* 168 */       if (villagedoorinfo1 != null) {
/* 169 */         return villagedoorinfo1;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     return null;
/*     */   }
/*     */   
/*     */   private void addToNewDoorsList(BlockPos doorBlock) {
/* 177 */     EnumFacing enumfacing = BlockDoor.getFacing((IBlockAccess)this.worldObj, doorBlock);
/* 178 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 179 */     int i = countBlocksCanSeeSky(doorBlock, enumfacing, 5);
/* 180 */     int j = countBlocksCanSeeSky(doorBlock, enumfacing1, i + 1);
/*     */     
/* 182 */     if (i != j) {
/* 183 */       this.newDoors.add(new VillageDoorInfo(doorBlock, (i < j) ? enumfacing : enumfacing1, this.tickCounter));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int countBlocksCanSeeSky(BlockPos centerPos, EnumFacing direction, int limitation) {
/* 191 */     int i = 0;
/*     */     
/* 193 */     for (int j = 1; j <= 5; j++) {
/*     */       
/* 195 */       i++;
/*     */       
/* 197 */       if (this.worldObj.canSeeSky(centerPos.offset(direction, j)) && i >= limitation) {
/* 198 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 203 */     return i;
/*     */   }
/*     */   
/*     */   private boolean positionInList(BlockPos pos) {
/* 207 */     for (BlockPos blockpos : this.villagerPositionsList) {
/* 208 */       if (blockpos.equals(pos)) {
/* 209 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isWoodDoor(BlockPos doorPos) {
/* 217 */     Block block = this.worldObj.getBlockState(doorPos).getBlock();
/* 218 */     return (block instanceof BlockDoor) ? ((block.getMaterial() == Material.wood)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 225 */     this.tickCounter = nbt.getInteger("Tick");
/* 226 */     NBTTagList nbttaglist = nbt.getTagList("Villages", 10);
/*     */     
/* 228 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 229 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 230 */       Village village = new Village();
/* 231 */       village.readVillageDataFromNBT(nbttagcompound);
/* 232 */       this.villageList.add(village);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 240 */     nbt.setInteger("Tick", this.tickCounter);
/* 241 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 243 */     for (Village village : this.villageList) {
/* 244 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 245 */       village.writeVillageDataToNBT(nbttagcompound);
/* 246 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 249 */     nbt.setTag("Villages", (NBTBase)nbttaglist);
/*     */   }
/*     */   
/*     */   public static String fileNameForProvider(WorldProvider provider) {
/* 253 */     return "villages" + provider.getInternalNameSuffix();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\VillageCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */