/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ 
/*     */ public class TileEntityBeacon
/*     */   extends TileEntityLockable
/*     */   implements ITickable, IInventory
/*     */ {
/*  36 */   public static final Potion[][] effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
/*  37 */   private final List<BeamSegment> beamSegments = Lists.newArrayList();
/*     */   
/*     */   private long beamRenderCounter;
/*     */   
/*     */   private float field_146014_j;
/*     */   
/*     */   private boolean isComplete;
/*     */   
/*  45 */   private int levels = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private int primaryEffect;
/*     */ 
/*     */ 
/*     */   
/*     */   private int secondaryEffect;
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack payment;
/*     */ 
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  67 */     if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
/*  68 */       updateBeacon();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateBeacon() {
/*  73 */     updateSegmentColors();
/*  74 */     addEffectsToPlayers();
/*     */   }
/*     */   
/*     */   private void addEffectsToPlayers() {
/*  78 */     if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
/*  79 */       double d0 = (this.levels * 10 + 10);
/*  80 */       int i = 0;
/*     */       
/*  82 */       if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
/*  83 */         i = 1;
/*     */       }
/*     */       
/*  86 */       int j = this.pos.getX();
/*  87 */       int k = this.pos.getY();
/*  88 */       int l = this.pos.getZ();
/*  89 */       AxisAlignedBB axisalignedbb = (new AxisAlignedBB(j, k, l, (j + 1), (k + 1), (l + 1))).expand(d0, d0, d0).addCoord(0.0D, this.worldObj.getHeight(), 0.0D);
/*  90 */       List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
/*     */       
/*  92 */       for (EntityPlayer entityplayer : list) {
/*  93 */         entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, 180, i, true, true));
/*     */       }
/*     */       
/*  96 */       if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {
/*  97 */         for (EntityPlayer entityplayer1 : list) {
/*  98 */           entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSegmentColors() {
/* 105 */     int i = this.levels;
/* 106 */     int j = this.pos.getX();
/* 107 */     int k = this.pos.getY();
/* 108 */     int l = this.pos.getZ();
/* 109 */     this.levels = 0;
/* 110 */     this.beamSegments.clear();
/* 111 */     this.isComplete = true;
/* 112 */     BeamSegment tileentitybeacon$beamsegment = new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
/* 113 */     this.beamSegments.add(tileentitybeacon$beamsegment);
/* 114 */     boolean flag = true;
/* 115 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 117 */     for (int i1 = k + 1; i1 < 256; ) {
/* 118 */       float[] afloat; IBlockState iblockstate = this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos.set(j, i1, l));
/*     */ 
/*     */       
/* 121 */       if (iblockstate.getBlock() == Blocks.stained_glass)
/* 122 */       { afloat = EntitySheep.getDyeRgb((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlass.COLOR)); }
/*     */       else
/* 124 */       { if (iblockstate.getBlock() != Blocks.stained_glass_pane)
/* 125 */         { if (iblockstate.getBlock().getLightOpacity() >= 15 && iblockstate.getBlock() != Blocks.bedrock) {
/* 126 */             this.isComplete = false;
/* 127 */             this.beamSegments.clear();
/*     */             
/*     */             break;
/*     */           } 
/* 131 */           tileentitybeacon$beamsegment.incrementHeight(); }
/*     */         
/*     */         else
/*     */         
/* 135 */         { afloat = EntitySheep.getDyeRgb((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR));
/*     */ 
/*     */           
/* 138 */           if (!flag)
/* 139 */             afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F };  }  i1++; }  if (!flag) afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F };
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (this.isComplete) {
/* 153 */       for (int l1 = 1; l1 <= 4; this.levels = l1++) {
/* 154 */         int i2 = k - l1;
/*     */         
/* 156 */         if (i2 < 0) {
/*     */           break;
/*     */         }
/*     */         
/* 160 */         boolean flag1 = true;
/*     */         
/* 162 */         for (int j1 = j - l1; j1 <= j + l1 && flag1; j1++) {
/* 163 */           for (int k1 = l - l1; k1 <= l + l1; k1++) {
/* 164 */             Block block = this.worldObj.getBlockState(new BlockPos(j1, i2, k1)).getBlock();
/*     */             
/* 166 */             if (block != Blocks.emerald_block && block != Blocks.gold_block && block != Blocks.diamond_block && block != Blocks.iron_block) {
/* 167 */               flag1 = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 173 */         if (!flag1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 178 */       if (this.levels == 0) {
/* 179 */         this.isComplete = false;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (!this.worldObj.isRemote && this.levels == 4 && i < this.levels) {
/* 184 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, (new AxisAlignedBB(j, k, l, j, (k - 4), l)).expand(10.0D, 5.0D, 10.0D))) {
/* 185 */         entityplayer.triggerAchievement((StatBase)AchievementList.fullBeacon);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<BeamSegment> getBeamSegments() {
/* 191 */     return this.beamSegments;
/*     */   }
/*     */   
/*     */   public float shouldBeamRender() {
/* 195 */     if (!this.isComplete) {
/* 196 */       return 0.0F;
/*     */     }
/* 198 */     int i = (int)(this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
/* 199 */     this.beamRenderCounter = this.worldObj.getTotalWorldTime();
/*     */     
/* 201 */     if (i > 1) {
/* 202 */       this.field_146014_j -= i / 40.0F;
/*     */       
/* 204 */       if (this.field_146014_j < 0.0F) {
/* 205 */         this.field_146014_j = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 209 */     this.field_146014_j += 0.025F;
/*     */     
/* 211 */     if (this.field_146014_j > 1.0F) {
/* 212 */       this.field_146014_j = 1.0F;
/*     */     }
/*     */     
/* 215 */     return this.field_146014_j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 224 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 225 */     writeToNBT(nbttagcompound);
/* 226 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 3, nbttagcompound);
/*     */   }
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 230 */     return 65536.0D;
/*     */   }
/*     */   
/*     */   private int func_183001_h(int p_183001_1_) {
/* 234 */     if (p_183001_1_ >= 0 && p_183001_1_ < Potion.potionTypes.length && Potion.potionTypes[p_183001_1_] != null) {
/* 235 */       Potion potion = Potion.potionTypes[p_183001_1_];
/* 236 */       return (potion != Potion.moveSpeed && potion != Potion.digSpeed && potion != Potion.resistance && potion != Potion.jump && potion != Potion.damageBoost && potion != Potion.regeneration) ? 0 : p_183001_1_;
/*     */     } 
/* 238 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 243 */     super.readFromNBT(compound);
/* 244 */     this.primaryEffect = func_183001_h(compound.getInteger("Primary"));
/* 245 */     this.secondaryEffect = func_183001_h(compound.getInteger("Secondary"));
/* 246 */     this.levels = compound.getInteger("Levels");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 250 */     super.writeToNBT(compound);
/* 251 */     compound.setInteger("Primary", this.primaryEffect);
/* 252 */     compound.setInteger("Secondary", this.secondaryEffect);
/* 253 */     compound.setInteger("Levels", this.levels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 260 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 267 */     return (index == 0) ? this.payment : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 274 */     if (index == 0 && this.payment != null) {
/* 275 */       if (count >= this.payment.stackSize) {
/* 276 */         ItemStack itemstack = this.payment;
/* 277 */         this.payment = null;
/* 278 */         return itemstack;
/*     */       } 
/* 280 */       this.payment.stackSize -= count;
/* 281 */       return new ItemStack(this.payment.getItem(), count, this.payment.getMetadata());
/*     */     } 
/*     */     
/* 284 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 292 */     if (index == 0 && this.payment != null) {
/* 293 */       ItemStack itemstack = this.payment;
/* 294 */       this.payment = null;
/* 295 */       return itemstack;
/*     */     } 
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 305 */     if (index == 0) {
/* 306 */       this.payment = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 314 */     return hasCustomName() ? this.customName : "container.beacon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 321 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 325 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 332 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 339 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 352 */     return !(stack.getItem() != Items.emerald && stack.getItem() != Items.diamond && stack.getItem() != Items.gold_ingot && stack.getItem() != Items.iron_ingot);
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 356 */     return "minecraft:beacon";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 360 */     return (Container)new ContainerBeacon((IInventory)playerInventory, this);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 364 */     switch (id) {
/*     */       case 0:
/* 366 */         return this.levels;
/*     */       
/*     */       case 1:
/* 369 */         return this.primaryEffect;
/*     */       
/*     */       case 2:
/* 372 */         return this.secondaryEffect;
/*     */     } 
/*     */     
/* 375 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 380 */     switch (id) {
/*     */       case 0:
/* 382 */         this.levels = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 386 */         this.primaryEffect = func_183001_h(value);
/*     */         break;
/*     */       
/*     */       case 2:
/* 390 */         this.secondaryEffect = func_183001_h(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public int getFieldCount() {
/* 395 */     return 3;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 399 */     this.payment = null;
/*     */   }
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 403 */     if (id == 1) {
/* 404 */       updateBeacon();
/* 405 */       return true;
/*     */     } 
/* 407 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */   
/*     */   public static class BeamSegment
/*     */   {
/*     */     private final float[] colors;
/*     */     private int height;
/*     */     
/*     */     public BeamSegment(float[] p_i45669_1_) {
/* 416 */       this.colors = p_i45669_1_;
/* 417 */       this.height = 1;
/*     */     }
/*     */     
/*     */     protected void incrementHeight() {
/* 421 */       this.height++;
/*     */     }
/*     */     
/*     */     public float[] getColors() {
/* 425 */       return this.colors;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 429 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */