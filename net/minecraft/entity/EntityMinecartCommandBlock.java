/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartCommandBlock extends EntityMinecart {
/*  16 */   private final CommandBlockLogic commandBlockLogic = new CommandBlockLogic() {
/*     */       public void updateCommand() {
/*  18 */         EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, getCommand());
/*  19 */         EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(getLastOutput()));
/*     */       }
/*     */       
/*     */       public int func_145751_f() {
/*  23 */         return 1;
/*     */       }
/*     */       
/*     */       public void func_145757_a(ByteBuf p_145757_1_) {
/*  27 */         p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
/*     */       }
/*     */       
/*     */       public BlockPos getPosition() {
/*  31 */         return new BlockPos(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY + 0.5D, EntityMinecartCommandBlock.this.posZ);
/*     */       }
/*     */       
/*     */       public Vec3 getPositionVector() {
/*  35 */         return new Vec3(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY, EntityMinecartCommandBlock.this.posZ);
/*     */       }
/*     */       
/*     */       public World getEntityWorld() {
/*  39 */         return EntityMinecartCommandBlock.this.worldObj;
/*     */       }
/*     */       
/*     */       public Entity getCommandSenderEntity() {
/*  43 */         return (Entity)EntityMinecartCommandBlock.this;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private int activatorRailCooldown = 0;
/*     */   
/*     */   public EntityMinecartCommandBlock(World worldIn) {
/*  53 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityMinecartCommandBlock(World worldIn, double x, double y, double z) {
/*  57 */     super(worldIn, x, y, z);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  61 */     super.entityInit();
/*  62 */     getDataWatcher().addObject(23, "");
/*  63 */     getDataWatcher().addObject(24, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  70 */     super.readEntityFromNBT(tagCompund);
/*  71 */     this.commandBlockLogic.readDataFromNBT(tagCompund);
/*  72 */     getDataWatcher().updateObject(23, getCommandBlockLogic().getCommand());
/*  73 */     getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(getCommandBlockLogic().getLastOutput()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  80 */     super.writeEntityToNBT(tagCompound);
/*  81 */     this.commandBlockLogic.writeDataToNBT(tagCompound);
/*     */   }
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  85 */     return EntityMinecart.EnumMinecartType.COMMAND_BLOCK;
/*     */   }
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  89 */     return Blocks.command_block.getDefaultState();
/*     */   }
/*     */   
/*     */   public CommandBlockLogic getCommandBlockLogic() {
/*  93 */     return this.commandBlockLogic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 100 */     if (receivingPower && this.ticksExisted - this.activatorRailCooldown >= 4) {
/* 101 */       getCommandBlockLogic().trigger(this.worldObj);
/* 102 */       this.activatorRailCooldown = this.ticksExisted;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 110 */     this.commandBlockLogic.tryOpenEditCommandBlock(playerIn);
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 115 */     super.onDataWatcherUpdate(dataID);
/*     */     
/* 117 */     if (dataID == 24) {
/*     */       try {
/* 119 */         this.commandBlockLogic.setLastOutput(IChatComponent.Serializer.jsonToComponent(getDataWatcher().getWatchableObjectString(24)));
/* 120 */       } catch (Throwable throwable) {}
/*     */     
/*     */     }
/* 123 */     else if (dataID == 23) {
/* 124 */       this.commandBlockLogic.setCommand(getDataWatcher().getWatchableObjectString(23));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityMinecartCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */