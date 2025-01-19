/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.IHopper;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartHopper
/*     */   extends EntityMinecartContainer
/*     */   implements IHopper {
/*     */   private boolean isBlocked = true;
/*  25 */   private int transferTicker = -1;
/*  26 */   private BlockPos field_174900_c = BlockPos.ORIGIN;
/*     */   
/*     */   public EntityMinecartHopper(World worldIn) {
/*  29 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityMinecartHopper(World worldIn, double x, double y, double z) {
/*  33 */     super(worldIn, x, y, z);
/*     */   }
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  37 */     return EntityMinecart.EnumMinecartType.HOPPER;
/*     */   }
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  41 */     return Blocks.hopper.getDefaultState();
/*     */   }
/*     */   
/*     */   public int getDefaultDisplayTileOffset() {
/*  45 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  52 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/*  59 */     if (!this.worldObj.isRemote) {
/*  60 */       playerIn.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/*  70 */     boolean flag = !receivingPower;
/*     */     
/*  72 */     if (flag != getBlocked()) {
/*  73 */       setBlocked(flag);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBlocked() {
/*  81 */     return this.isBlocked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocked(boolean p_96110_1_) {
/*  88 */     this.isBlocked = p_96110_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  95 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 102 */     return this.posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 109 */     return this.posY + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 116 */     return this.posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 123 */     super.onUpdate();
/*     */     
/* 125 */     if (!this.worldObj.isRemote && isEntityAlive() && getBlocked()) {
/* 126 */       BlockPos blockpos = new BlockPos(this);
/*     */       
/* 128 */       if (blockpos.equals(this.field_174900_c)) {
/* 129 */         this.transferTicker--;
/*     */       } else {
/* 131 */         setTransferTicker(0);
/*     */       } 
/*     */       
/* 134 */       if (!canTransfer()) {
/* 135 */         setTransferTicker(0);
/*     */         
/* 137 */         if (func_96112_aD()) {
/* 138 */           setTransferTicker(4);
/* 139 */           markDirty();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_96112_aD() {
/* 146 */     if (TileEntityHopper.captureDroppedItems(this)) {
/* 147 */       return true;
/*     */     }
/* 149 */     List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), EntitySelectors.selectAnything);
/*     */     
/* 151 */     if (list.size() > 0) {
/* 152 */       TileEntityHopper.putDropInInventoryAllSlots((IInventory)this, list.get(0));
/*     */     }
/*     */     
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/* 160 */     super.killMinecart(source);
/*     */     
/* 162 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 163 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.hopper), 1, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 171 */     super.writeEntityToNBT(tagCompound);
/* 172 */     tagCompound.setInteger("TransferCooldown", this.transferTicker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 179 */     super.readEntityFromNBT(tagCompund);
/* 180 */     this.transferTicker = tagCompund.getInteger("TransferCooldown");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransferTicker(int p_98042_1_) {
/* 187 */     this.transferTicker = p_98042_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTransfer() {
/* 194 */     return (this.transferTicker > 0);
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 198 */     return "minecraft:hopper";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 202 */     return (Container)new ContainerHopper(playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */