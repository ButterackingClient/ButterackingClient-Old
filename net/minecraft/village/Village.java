/*     */ package net.minecraft.village;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerProfileCache;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Village {
/*  30 */   private final List<VillageDoorInfo> villageDoorInfoList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private World worldObj;
/*     */ 
/*     */   
/*  36 */   private BlockPos centerHelper = BlockPos.ORIGIN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private BlockPos center = BlockPos.ORIGIN;
/*     */   
/*     */   private int villageRadius;
/*     */   
/*     */   private int lastAddDoorTimestamp;
/*     */   
/*     */   private int tickCounter;
/*     */   
/*     */   private int numVillagers;
/*     */   private int noBreedTicks;
/*  51 */   private TreeMap<String, Integer> playerReputation = new TreeMap<>();
/*  52 */   private List<VillageAggressor> villageAgressors = Lists.newArrayList();
/*     */   
/*     */   private int numIronGolems;
/*     */   
/*     */   public Village() {}
/*     */   
/*     */   public Village(World worldIn) {
/*  59 */     this.worldObj = worldIn;
/*     */   }
/*     */   
/*     */   public void setWorld(World worldIn) {
/*  63 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(int p_75560_1_) {
/*  70 */     this.tickCounter = p_75560_1_;
/*  71 */     removeDeadAndOutOfRangeDoors();
/*  72 */     removeDeadAndOldAgressors();
/*     */     
/*  74 */     if (p_75560_1_ % 20 == 0) {
/*  75 */       updateNumVillagers();
/*     */     }
/*     */     
/*  78 */     if (p_75560_1_ % 30 == 0) {
/*  79 */       updateNumIronGolems();
/*     */     }
/*     */     
/*  82 */     int i = this.numVillagers / 10;
/*     */     
/*  84 */     if (this.numIronGolems < i && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
/*  85 */       Vec3 vec3 = func_179862_a(this.center, 2, 4, 2);
/*     */       
/*  87 */       if (vec3 != null) {
/*  88 */         EntityIronGolem entityirongolem = new EntityIronGolem(this.worldObj);
/*  89 */         entityirongolem.setPosition(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  90 */         this.worldObj.spawnEntityInWorld((Entity)entityirongolem);
/*  91 */         this.numIronGolems++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vec3 func_179862_a(BlockPos p_179862_1_, int p_179862_2_, int p_179862_3_, int p_179862_4_) {
/*  97 */     for (int i = 0; i < 10; i++) {
/*  98 */       BlockPos blockpos = p_179862_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 100 */       if (func_179866_a(blockpos) && func_179861_a(new BlockPos(p_179862_2_, p_179862_3_, p_179862_4_), blockpos)) {
/* 101 */         return new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   private boolean func_179861_a(BlockPos p_179861_1_, BlockPos p_179861_2_) {
/* 109 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, p_179861_2_.down())) {
/* 110 */       return false;
/*     */     }
/* 112 */     int i = p_179861_2_.getX() - p_179861_1_.getX() / 2;
/* 113 */     int j = p_179861_2_.getZ() - p_179861_1_.getZ() / 2;
/*     */     
/* 115 */     for (int k = i; k < i + p_179861_1_.getX(); k++) {
/* 116 */       for (int l = p_179861_2_.getY(); l < p_179861_2_.getY() + p_179861_1_.getY(); l++) {
/* 117 */         for (int i1 = j; i1 < j + p_179861_1_.getZ(); i1++) {
/* 118 */           if (this.worldObj.getBlockState(new BlockPos(k, l, i1)).getBlock().isNormalCube()) {
/* 119 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNumIronGolems() {
/* 130 */     List<EntityIronGolem> list = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 131 */     this.numIronGolems = list.size();
/*     */   }
/*     */   
/*     */   private void updateNumVillagers() {
/* 135 */     List<EntityVillager> list = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 136 */     this.numVillagers = list.size();
/*     */     
/* 138 */     if (this.numVillagers == 0) {
/* 139 */       this.playerReputation.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockPos getCenter() {
/* 144 */     return this.center;
/*     */   }
/*     */   
/*     */   public int getVillageRadius() {
/* 148 */     return this.villageRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumVillageDoors() {
/* 156 */     return this.villageDoorInfoList.size();
/*     */   }
/*     */   
/*     */   public int getTicksSinceLastDoorAdding() {
/* 160 */     return this.tickCounter - this.lastAddDoorTimestamp;
/*     */   }
/*     */   
/*     */   public int getNumVillagers() {
/* 164 */     return this.numVillagers;
/*     */   }
/*     */   
/*     */   public boolean func_179866_a(BlockPos pos) {
/* 168 */     return (this.center.distanceSq((Vec3i)pos) < (this.villageRadius * this.villageRadius));
/*     */   }
/*     */   
/*     */   public List<VillageDoorInfo> getVillageDoorInfoList() {
/* 172 */     return this.villageDoorInfoList;
/*     */   }
/*     */   
/*     */   public VillageDoorInfo getNearestDoor(BlockPos pos) {
/* 176 */     VillageDoorInfo villagedoorinfo = null;
/* 177 */     int i = Integer.MAX_VALUE;
/*     */     
/* 179 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/* 180 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 182 */       if (j < i) {
/* 183 */         villagedoorinfo = villagedoorinfo1;
/* 184 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getDoorInfo(BlockPos pos) {
/* 195 */     VillageDoorInfo villagedoorinfo = null;
/* 196 */     int i = Integer.MAX_VALUE;
/*     */     
/* 198 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/* 199 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 201 */       if (j > 256) {
/* 202 */         j *= 1000;
/*     */       } else {
/* 204 */         j = villagedoorinfo1.getDoorOpeningRestrictionCounter();
/*     */       } 
/*     */       
/* 207 */       if (j < i) {
/* 208 */         villagedoorinfo = villagedoorinfo1;
/* 209 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getExistedDoor(BlockPos doorBlock) {
/* 220 */     if (this.center.distanceSq((Vec3i)doorBlock) > (this.villageRadius * this.villageRadius)) {
/* 221 */       return null;
/*     */     }
/* 223 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/* 224 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1) {
/* 225 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addVillageDoorInfo(VillageDoorInfo doorInfo) {
/* 234 */     this.villageDoorInfoList.add(doorInfo);
/* 235 */     this.centerHelper = this.centerHelper.add((Vec3i)doorInfo.getDoorBlockPos());
/* 236 */     updateVillageRadiusAndCenter();
/* 237 */     this.lastAddDoorTimestamp = doorInfo.getInsidePosY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnihilated() {
/* 244 */     return this.villageDoorInfoList.isEmpty();
/*     */   }
/*     */   
/*     */   public void addOrRenewAgressor(EntityLivingBase entitylivingbaseIn) {
/* 248 */     for (VillageAggressor village$villageaggressor : this.villageAgressors) {
/* 249 */       if (village$villageaggressor.agressor == entitylivingbaseIn) {
/* 250 */         village$villageaggressor.agressionTime = this.tickCounter;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 255 */     this.villageAgressors.add(new VillageAggressor(entitylivingbaseIn, this.tickCounter));
/*     */   }
/*     */   
/*     */   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase entitylivingbaseIn) {
/* 259 */     double d0 = Double.MAX_VALUE;
/* 260 */     VillageAggressor village$villageaggressor = null;
/*     */     
/* 262 */     for (int i = 0; i < this.villageAgressors.size(); i++) {
/* 263 */       VillageAggressor village$villageaggressor1 = this.villageAgressors.get(i);
/* 264 */       double d1 = village$villageaggressor1.agressor.getDistanceSqToEntity((Entity)entitylivingbaseIn);
/*     */       
/* 266 */       if (d1 <= d0) {
/* 267 */         village$villageaggressor = village$villageaggressor1;
/* 268 */         d0 = d1;
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     return (village$villageaggressor != null) ? village$villageaggressor.agressor : null;
/*     */   }
/*     */   
/*     */   public EntityPlayer getNearestTargetPlayer(EntityLivingBase villageDefender) {
/* 276 */     double d0 = Double.MAX_VALUE;
/* 277 */     EntityPlayer entityplayer = null;
/*     */     
/* 279 */     for (String s : this.playerReputation.keySet()) {
/* 280 */       if (isPlayerReputationTooLow(s)) {
/* 281 */         EntityPlayer entityplayer1 = this.worldObj.getPlayerEntityByName(s);
/*     */         
/* 283 */         if (entityplayer1 != null) {
/* 284 */           double d1 = entityplayer1.getDistanceSqToEntity((Entity)villageDefender);
/*     */           
/* 286 */           if (d1 <= d0) {
/* 287 */             entityplayer = entityplayer1;
/* 288 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     return entityplayer;
/*     */   }
/*     */   
/*     */   private void removeDeadAndOldAgressors() {
/* 298 */     Iterator<VillageAggressor> iterator = this.villageAgressors.iterator();
/*     */     
/* 300 */     while (iterator.hasNext()) {
/* 301 */       VillageAggressor village$villageaggressor = iterator.next();
/*     */       
/* 303 */       if (!village$villageaggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - village$villageaggressor.agressionTime) > 300) {
/* 304 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeDeadAndOutOfRangeDoors() {
/* 310 */     boolean flag = false;
/* 311 */     boolean flag1 = (this.worldObj.rand.nextInt(50) == 0);
/* 312 */     Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
/*     */     
/* 314 */     while (iterator.hasNext()) {
/* 315 */       VillageDoorInfo villagedoorinfo = iterator.next();
/*     */       
/* 317 */       if (flag1) {
/* 318 */         villagedoorinfo.resetDoorOpeningRestrictionCounter();
/*     */       }
/*     */       
/* 321 */       if (!isWoodDoor(villagedoorinfo.getDoorBlockPos()) || Math.abs(this.tickCounter - villagedoorinfo.getInsidePosY()) > 1200) {
/* 322 */         this.centerHelper = this.centerHelper.subtract((Vec3i)villagedoorinfo.getDoorBlockPos());
/* 323 */         flag = true;
/* 324 */         villagedoorinfo.setIsDetachedFromVillageFlag(true);
/* 325 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     if (flag) {
/* 330 */       updateVillageRadiusAndCenter();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isWoodDoor(BlockPos pos) {
/* 335 */     Block block = this.worldObj.getBlockState(pos).getBlock();
/* 336 */     return (block instanceof net.minecraft.block.BlockDoor) ? ((block.getMaterial() == Material.wood)) : false;
/*     */   }
/*     */   
/*     */   private void updateVillageRadiusAndCenter() {
/* 340 */     int i = this.villageDoorInfoList.size();
/*     */     
/* 342 */     if (i == 0) {
/* 343 */       this.center = new BlockPos(0, 0, 0);
/* 344 */       this.villageRadius = 0;
/*     */     } else {
/* 346 */       this.center = new BlockPos(this.centerHelper.getX() / i, this.centerHelper.getY() / i, this.centerHelper.getZ() / i);
/* 347 */       int j = 0;
/*     */       
/* 349 */       for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/* 350 */         j = Math.max(villagedoorinfo.getDistanceToDoorBlockSq(this.center), j);
/*     */       }
/*     */       
/* 353 */       this.villageRadius = Math.max(32, (int)Math.sqrt(j) + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReputationForPlayer(String p_82684_1_) {
/* 361 */     Integer integer = this.playerReputation.get(p_82684_1_);
/* 362 */     return (integer != null) ? integer.intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setReputationForPlayer(String p_82688_1_, int p_82688_2_) {
/* 369 */     int i = getReputationForPlayer(p_82688_1_);
/* 370 */     int j = MathHelper.clamp_int(i + p_82688_2_, -30, 10);
/* 371 */     this.playerReputation.put(p_82688_1_, Integer.valueOf(j));
/* 372 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerReputationTooLow(String p_82687_1_) {
/* 379 */     return (getReputationForPlayer(p_82687_1_) <= -15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readVillageDataFromNBT(NBTTagCompound compound) {
/* 386 */     this.numVillagers = compound.getInteger("PopSize");
/* 387 */     this.villageRadius = compound.getInteger("Radius");
/* 388 */     this.numIronGolems = compound.getInteger("Golems");
/* 389 */     this.lastAddDoorTimestamp = compound.getInteger("Stable");
/* 390 */     this.tickCounter = compound.getInteger("Tick");
/* 391 */     this.noBreedTicks = compound.getInteger("MTick");
/* 392 */     this.center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
/* 393 */     this.centerHelper = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
/* 394 */     NBTTagList nbttaglist = compound.getTagList("Doors", 10);
/*     */     
/* 396 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 397 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 398 */       VillageDoorInfo villagedoorinfo = new VillageDoorInfo(new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z")), nbttagcompound.getInteger("IDX"), nbttagcompound.getInteger("IDZ"), nbttagcompound.getInteger("TS"));
/* 399 */       this.villageDoorInfoList.add(villagedoorinfo);
/*     */     } 
/*     */     
/* 402 */     NBTTagList nbttaglist1 = compound.getTagList("Players", 10);
/*     */     
/* 404 */     for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/* 405 */       NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(j);
/*     */       
/* 407 */       if (nbttagcompound1.hasKey("UUID")) {
/* 408 */         PlayerProfileCache playerprofilecache = MinecraftServer.getServer().getPlayerProfileCache();
/* 409 */         GameProfile gameprofile = playerprofilecache.getProfileByUUID(UUID.fromString(nbttagcompound1.getString("UUID")));
/*     */         
/* 411 */         if (gameprofile != null) {
/* 412 */           this.playerReputation.put(gameprofile.getName(), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */         }
/*     */       } else {
/* 415 */         this.playerReputation.put(nbttagcompound1.getString("Name"), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeVillageDataToNBT(NBTTagCompound compound) {
/* 424 */     compound.setInteger("PopSize", this.numVillagers);
/* 425 */     compound.setInteger("Radius", this.villageRadius);
/* 426 */     compound.setInteger("Golems", this.numIronGolems);
/* 427 */     compound.setInteger("Stable", this.lastAddDoorTimestamp);
/* 428 */     compound.setInteger("Tick", this.tickCounter);
/* 429 */     compound.setInteger("MTick", this.noBreedTicks);
/* 430 */     compound.setInteger("CX", this.center.getX());
/* 431 */     compound.setInteger("CY", this.center.getY());
/* 432 */     compound.setInteger("CZ", this.center.getZ());
/* 433 */     compound.setInteger("ACX", this.centerHelper.getX());
/* 434 */     compound.setInteger("ACY", this.centerHelper.getY());
/* 435 */     compound.setInteger("ACZ", this.centerHelper.getZ());
/* 436 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 438 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/* 439 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 440 */       nbttagcompound.setInteger("X", villagedoorinfo.getDoorBlockPos().getX());
/* 441 */       nbttagcompound.setInteger("Y", villagedoorinfo.getDoorBlockPos().getY());
/* 442 */       nbttagcompound.setInteger("Z", villagedoorinfo.getDoorBlockPos().getZ());
/* 443 */       nbttagcompound.setInteger("IDX", villagedoorinfo.getInsideOffsetX());
/* 444 */       nbttagcompound.setInteger("IDZ", villagedoorinfo.getInsideOffsetZ());
/* 445 */       nbttagcompound.setInteger("TS", villagedoorinfo.getInsidePosY());
/* 446 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 449 */     compound.setTag("Doors", (NBTBase)nbttaglist);
/* 450 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 452 */     for (String s : this.playerReputation.keySet()) {
/* 453 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 454 */       PlayerProfileCache playerprofilecache = MinecraftServer.getServer().getPlayerProfileCache();
/* 455 */       GameProfile gameprofile = playerprofilecache.getGameProfileForUsername(s);
/*     */       
/* 457 */       if (gameprofile != null) {
/* 458 */         nbttagcompound1.setString("UUID", gameprofile.getId().toString());
/* 459 */         nbttagcompound1.setInteger("S", ((Integer)this.playerReputation.get(s)).intValue());
/* 460 */         nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 464 */     compound.setTag("Players", (NBTBase)nbttaglist1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endMatingSeason() {
/* 471 */     this.noBreedTicks = this.tickCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatingSeason() {
/* 478 */     return !(this.noBreedTicks != 0 && this.tickCounter - this.noBreedTicks < 3600);
/*     */   }
/*     */   
/*     */   public void setDefaultPlayerReputation(int p_82683_1_) {
/* 482 */     for (String s : this.playerReputation.keySet())
/* 483 */       setReputationForPlayer(s, p_82683_1_); 
/*     */   }
/*     */   
/*     */   class VillageAggressor
/*     */   {
/*     */     public EntityLivingBase agressor;
/*     */     public int agressionTime;
/*     */     
/*     */     VillageAggressor(EntityLivingBase p_i1674_2_, int p_i1674_3_) {
/* 492 */       this.agressor = p_i1674_2_;
/* 493 */       this.agressionTime = p_i1674_3_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\Village.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */