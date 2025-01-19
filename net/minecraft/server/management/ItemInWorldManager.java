/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemInWorldManager
/*     */ {
/*     */   public World theWorld;
/*     */   public EntityPlayerMP thisPlayerMP;
/*  33 */   private WorldSettings.GameType gameType = WorldSettings.GameType.NOT_SET;
/*     */ 
/*     */   
/*     */   private boolean isDestroyingBlock;
/*     */   
/*     */   private int initialDamage;
/*     */   
/*  40 */   private BlockPos field_180240_f = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   private int curblockDamage;
/*     */ 
/*     */   
/*     */   private boolean receivedFinishDiggingPacket;
/*     */   
/*  48 */   private BlockPos field_180241_i = BlockPos.ORIGIN;
/*     */   private int initialBlockDamage;
/*  50 */   private int durabilityRemainingOnBlock = -1;
/*     */   
/*     */   public ItemInWorldManager(World worldIn) {
/*  53 */     this.theWorld = worldIn;
/*     */   }
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/*  57 */     this.gameType = type;
/*  58 */     type.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
/*  59 */     this.thisPlayerMP.sendPlayerAbilities();
/*  60 */     this.thisPlayerMP.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] { this.thisPlayerMP }));
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/*  64 */     return this.gameType;
/*     */   }
/*     */   
/*     */   public boolean survivalOrAdventure() {
/*  68 */     return this.gameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  75 */     return this.gameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeGameType(WorldSettings.GameType type) {
/*  82 */     if (this.gameType == WorldSettings.GameType.NOT_SET) {
/*  83 */       this.gameType = type;
/*     */     }
/*     */     
/*  86 */     setGameType(this.gameType);
/*     */   }
/*     */   
/*     */   public void updateBlockRemoving() {
/*  90 */     this.curblockDamage++;
/*     */     
/*  92 */     if (this.receivedFinishDiggingPacket) {
/*  93 */       int i = this.curblockDamage - this.initialBlockDamage;
/*  94 */       Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
/*     */       
/*  96 */       if (block.getMaterial() == Material.air) {
/*  97 */         this.receivedFinishDiggingPacket = false;
/*     */       } else {
/*  99 */         float f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (i + 1);
/* 100 */         int j = (int)(f * 10.0F);
/*     */         
/* 102 */         if (j != this.durabilityRemainingOnBlock) {
/* 103 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, j);
/* 104 */           this.durabilityRemainingOnBlock = j;
/*     */         } 
/*     */         
/* 107 */         if (f >= 1.0F) {
/* 108 */           this.receivedFinishDiggingPacket = false;
/* 109 */           tryHarvestBlock(this.field_180241_i);
/*     */         } 
/*     */       } 
/* 112 */     } else if (this.isDestroyingBlock) {
/* 113 */       Block block1 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
/*     */       
/* 115 */       if (block1.getMaterial() == Material.air) {
/* 116 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
/* 117 */         this.durabilityRemainingOnBlock = -1;
/* 118 */         this.isDestroyingBlock = false;
/*     */       } else {
/* 120 */         int k = this.curblockDamage - this.initialDamage;
/* 121 */         float f1 = block1.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (k + 1);
/* 122 */         int l = (int)(f1 * 10.0F);
/*     */         
/* 124 */         if (l != this.durabilityRemainingOnBlock) {
/* 125 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, l);
/* 126 */           this.durabilityRemainingOnBlock = l;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(BlockPos pos, EnumFacing side) {
/* 137 */     if (isCreative()) {
/* 138 */       if (!this.theWorld.extinguishFire(null, pos, side)) {
/* 139 */         tryHarvestBlock(pos);
/*     */       }
/*     */     } else {
/* 142 */       Block block = this.theWorld.getBlockState(pos).getBlock();
/*     */       
/* 144 */       if (this.gameType.isAdventure()) {
/* 145 */         if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/*     */           return;
/*     */         }
/*     */         
/* 149 */         if (!this.thisPlayerMP.isAllowEdit()) {
/* 150 */           ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
/*     */           
/* 152 */           if (itemstack == null) {
/*     */             return;
/*     */           }
/*     */           
/* 156 */           if (!itemstack.canDestroy(block)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       this.theWorld.extinguishFire(null, pos, side);
/* 163 */       this.initialDamage = this.curblockDamage;
/* 164 */       float f = 1.0F;
/*     */       
/* 166 */       if (block.getMaterial() != Material.air) {
/* 167 */         block.onBlockClicked(this.theWorld, pos, (EntityPlayer)this.thisPlayerMP);
/* 168 */         f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, pos);
/*     */       } 
/*     */       
/* 171 */       if (block.getMaterial() != Material.air && f >= 1.0F) {
/* 172 */         tryHarvestBlock(pos);
/*     */       } else {
/* 174 */         this.isDestroyingBlock = true;
/* 175 */         this.field_180240_f = pos;
/* 176 */         int i = (int)(f * 10.0F);
/* 177 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, i);
/* 178 */         this.durabilityRemainingOnBlock = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blockRemoving(BlockPos pos) {
/* 184 */     if (pos.equals(this.field_180240_f)) {
/* 185 */       int i = this.curblockDamage - this.initialDamage;
/* 186 */       Block block = this.theWorld.getBlockState(pos).getBlock();
/*     */       
/* 188 */       if (block.getMaterial() != Material.air) {
/* 189 */         float f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, pos) * (i + 1);
/*     */         
/* 191 */         if (f >= 0.7F) {
/* 192 */           this.isDestroyingBlock = false;
/* 193 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, -1);
/* 194 */           tryHarvestBlock(pos);
/* 195 */         } else if (!this.receivedFinishDiggingPacket) {
/* 196 */           this.isDestroyingBlock = false;
/* 197 */           this.receivedFinishDiggingPacket = true;
/* 198 */           this.field_180241_i = pos;
/* 199 */           this.initialBlockDamage = this.initialDamage;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelDestroyingBlock() {
/* 209 */     this.isDestroyingBlock = false;
/* 210 */     this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeBlock(BlockPos pos) {
/* 217 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 218 */     iblockstate.getBlock().onBlockHarvested(this.theWorld, pos, iblockstate, (EntityPlayer)this.thisPlayerMP);
/* 219 */     boolean flag = this.theWorld.setBlockToAir(pos);
/*     */     
/* 221 */     if (flag) {
/* 222 */       iblockstate.getBlock().onBlockDestroyedByPlayer(this.theWorld, pos, iblockstate);
/*     */     }
/*     */     
/* 225 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryHarvestBlock(BlockPos pos) {
/* 232 */     if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/* 233 */       return false;
/*     */     }
/* 235 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 236 */     TileEntity tileentity = this.theWorld.getTileEntity(pos);
/*     */     
/* 238 */     if (this.gameType.isAdventure()) {
/* 239 */       if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/* 240 */         return false;
/*     */       }
/*     */       
/* 243 */       if (!this.thisPlayerMP.isAllowEdit()) {
/* 244 */         ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
/*     */         
/* 246 */         if (itemstack == null) {
/* 247 */           return false;
/*     */         }
/*     */         
/* 250 */         if (!itemstack.canDestroy(iblockstate.getBlock())) {
/* 251 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     this.theWorld.playAuxSFXAtEntity((EntityPlayer)this.thisPlayerMP, 2001, pos, Block.getStateId(iblockstate));
/* 257 */     boolean flag1 = removeBlock(pos);
/*     */     
/* 259 */     if (isCreative()) {
/* 260 */       this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange(this.theWorld, pos));
/*     */     } else {
/* 262 */       ItemStack itemstack1 = this.thisPlayerMP.getCurrentEquippedItem();
/* 263 */       boolean flag = this.thisPlayerMP.canHarvestBlock(iblockstate.getBlock());
/*     */       
/* 265 */       if (itemstack1 != null) {
/* 266 */         itemstack1.onBlockDestroyed(this.theWorld, iblockstate.getBlock(), pos, (EntityPlayer)this.thisPlayerMP);
/*     */         
/* 268 */         if (itemstack1.stackSize == 0) {
/* 269 */           this.thisPlayerMP.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */       
/* 273 */       if (flag1 && flag) {
/* 274 */         iblockstate.getBlock().harvestBlock(this.theWorld, (EntityPlayer)this.thisPlayerMP, pos, iblockstate, tileentity);
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/* 286 */     if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/* 287 */       return false;
/*     */     }
/* 289 */     int i = stack.stackSize;
/* 290 */     int j = stack.getMetadata();
/* 291 */     ItemStack itemstack = stack.useItemRightClick(worldIn, player);
/*     */     
/* 293 */     if (itemstack != stack || (itemstack != null && (itemstack.stackSize != i || itemstack.getMaxItemUseDuration() > 0 || itemstack.getMetadata() != j))) {
/* 294 */       player.inventory.mainInventory[player.inventory.currentItem] = itemstack;
/*     */       
/* 296 */       if (isCreative()) {
/* 297 */         itemstack.stackSize = i;
/*     */         
/* 299 */         if (itemstack.isItemStackDamageable()) {
/* 300 */           itemstack.setItemDamage(j);
/*     */         }
/*     */       } 
/*     */       
/* 304 */       if (itemstack.stackSize == 0) {
/* 305 */         player.inventory.mainInventory[player.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 308 */       if (!player.isUsingItem()) {
/* 309 */         ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
/*     */       }
/*     */       
/* 312 */       return true;
/*     */     } 
/* 314 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean activateBlockOrUseItem(EntityPlayer player, World worldIn, ItemStack stack, BlockPos pos, EnumFacing side, float offsetX, float offsetY, float offsetZ) {
/* 323 */     if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/* 324 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 326 */       if (tileentity instanceof ILockableContainer) {
/* 327 */         Block block = worldIn.getBlockState(pos).getBlock();
/* 328 */         ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
/*     */         
/* 330 */         if (ilockablecontainer instanceof net.minecraft.tileentity.TileEntityChest && block instanceof BlockChest) {
/* 331 */           ilockablecontainer = ((BlockChest)block).getLockableContainer(worldIn, pos);
/*     */         }
/*     */         
/* 334 */         if (ilockablecontainer != null) {
/* 335 */           player.displayGUIChest((IInventory)ilockablecontainer);
/* 336 */           return true;
/*     */         } 
/* 338 */       } else if (tileentity instanceof IInventory) {
/* 339 */         player.displayGUIChest((IInventory)tileentity);
/* 340 */         return true;
/*     */       } 
/*     */       
/* 343 */       return false;
/*     */     } 
/* 345 */     if (!player.isSneaking() || player.getHeldItem() == null) {
/* 346 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 348 */       if (iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, side, offsetX, offsetY, offsetZ)) {
/* 349 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 353 */     if (stack == null)
/* 354 */       return false; 
/* 355 */     if (isCreative()) {
/* 356 */       int j = stack.getMetadata();
/* 357 */       int i = stack.stackSize;
/* 358 */       boolean flag = stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
/* 359 */       stack.setItemDamage(j);
/* 360 */       stack.stackSize = i;
/* 361 */       return flag;
/*     */     } 
/* 363 */     return stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(WorldServer serverWorld) {
/* 372 */     this.theWorld = (World)serverWorld;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\ItemInWorldManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */