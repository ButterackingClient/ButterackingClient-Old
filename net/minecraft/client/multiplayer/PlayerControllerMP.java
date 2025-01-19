/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerControllerMP
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private final NetHandlerPlayClient netClientHandler;
/*  38 */   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack currentItemHittingBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float curBlockDamageMP;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float stepSoundTickCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockHitDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHittingBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
/*     */ 
/*     */   
/*     */   private int currentPlayerItem;
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/*  76 */     this.mc = mcIn;
/*  77 */     this.netClientHandler = netHandler;
/*     */   }
/*     */   
/*     */   public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP playerController, BlockPos pos, EnumFacing facing) {
/*  81 */     if (!mcIn.theWorld.extinguishFire((EntityPlayer)mcIn.thePlayer, pos, facing)) {
/*  82 */       playerController.onPlayerDestroyBlock(pos, facing);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCapabilities(EntityPlayer player) {
/*  92 */     this.currentGameType.configurePlayerCapabilities(player.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  99 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/* 108 */     this.currentGameType = type;
/* 109 */     this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flipPlayer(EntityPlayer playerIn) {
/* 116 */     playerIn.rotationYaw = -180.0F;
/*     */   }
/*     */   
/*     */   public boolean shouldDrawHUD() {
/* 120 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side) {
/* 127 */     if (this.currentGameType.isAdventure()) {
/* 128 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 129 */         return false;
/*     */       }
/*     */       
/* 132 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 133 */         Block block = this.mc.theWorld.getBlockState(pos).getBlock();
/* 134 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 136 */         if (itemstack == null) {
/* 137 */           return false;
/*     */         }
/*     */         
/* 140 */         if (!itemstack.canDestroy(block)) {
/* 141 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/* 147 */       return false;
/*     */     }
/* 149 */     World world = this.mc.theWorld;
/* 150 */     IBlockState iblockstate = world.getBlockState(pos);
/* 151 */     Block block1 = iblockstate.getBlock();
/*     */     
/* 153 */     if (block1.getMaterial() == Material.air) {
/* 154 */       return false;
/*     */     }
/* 156 */     world.playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/* 157 */     boolean flag = world.setBlockToAir(pos);
/*     */     
/* 159 */     if (flag) {
/* 160 */       block1.onBlockDestroyedByPlayer(world, pos, iblockstate);
/*     */     }
/*     */     
/* 163 */     this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
/*     */     
/* 165 */     if (!this.currentGameType.isCreative()) {
/* 166 */       ItemStack itemstack1 = this.mc.thePlayer.getCurrentEquippedItem();
/*     */       
/* 168 */       if (itemstack1 != null) {
/* 169 */         itemstack1.onBlockDestroyed(world, block1, pos, (EntityPlayer)this.mc.thePlayer);
/*     */         
/* 171 */         if (itemstack1.stackSize == 0) {
/* 172 */           this.mc.thePlayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 186 */     if (this.currentGameType.isAdventure()) {
/* 187 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 188 */         return false;
/*     */       }
/*     */       
/* 191 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 192 */         Block block = this.mc.theWorld.getBlockState(loc).getBlock();
/* 193 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 195 */         if (itemstack == null) {
/* 196 */           return false;
/*     */         }
/*     */         
/* 199 */         if (!itemstack.canDestroy(block)) {
/* 200 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     if (!this.mc.theWorld.getWorldBorder().contains(loc)) {
/* 206 */       return false;
/*     */     }
/* 208 */     if (this.currentGameType.isCreative()) {
/* 209 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 210 */       clickBlockCreative(this.mc, this, loc, face);
/* 211 */       this.blockHitDelay = 5;
/* 212 */     } else if (!this.isHittingBlock || !isHittingPosition(loc)) {
/* 213 */       if (this.isHittingBlock) {
/* 214 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
/*     */       }
/*     */       
/* 217 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 218 */       Block block1 = this.mc.theWorld.getBlockState(loc).getBlock();
/* 219 */       boolean flag = (block1.getMaterial() != Material.air);
/*     */       
/* 221 */       if (flag && this.curBlockDamageMP == 0.0F) {
/* 222 */         block1.onBlockClicked(this.mc.theWorld, loc, (EntityPlayer)this.mc.thePlayer);
/*     */       }
/*     */       
/* 225 */       if (flag && block1.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, this.mc.thePlayer.worldObj, loc) >= 1.0F) {
/* 226 */         onPlayerDestroyBlock(loc, face);
/*     */       } else {
/* 228 */         this.isHittingBlock = true;
/* 229 */         this.currentBlock = loc;
/* 230 */         this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
/* 231 */         this.curBlockDamageMP = 0.0F;
/* 232 */         this.stepSoundTickCounter = 0.0F;
/* 233 */         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockRemoving() {
/* 245 */     if (this.isHittingBlock) {
/* 246 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/* 247 */       this.isHittingBlock = false;
/* 248 */       this.curBlockDamageMP = 0.0F;
/* 249 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 254 */     syncCurrentPlayItem();
/*     */     
/* 256 */     if (this.blockHitDelay > 0) {
/* 257 */       this.blockHitDelay--;
/* 258 */       return true;
/* 259 */     }  if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(posBlock)) {
/* 260 */       this.blockHitDelay = 5;
/* 261 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
/* 262 */       clickBlockCreative(this.mc, this, posBlock, directionFacing);
/* 263 */       return true;
/* 264 */     }  if (isHittingPosition(posBlock)) {
/* 265 */       Block block = this.mc.theWorld.getBlockState(posBlock).getBlock();
/*     */       
/* 267 */       if (block.getMaterial() == Material.air) {
/* 268 */         this.isHittingBlock = false;
/* 269 */         return false;
/*     */       } 
/* 271 */       this.curBlockDamageMP += block.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, this.mc.thePlayer.worldObj, posBlock);
/*     */       
/* 273 */       if (this.stepSoundTickCounter % 4.0F == 0.0F) {
/* 274 */         this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0F) / 8.0F, block.stepSound.getFrequency() * 0.5F, posBlock.getX() + 0.5F, posBlock.getY() + 0.5F, posBlock.getZ() + 0.5F));
/*     */       }
/*     */       
/* 277 */       this.stepSoundTickCounter++;
/*     */       
/* 279 */       if (this.curBlockDamageMP >= 1.0F) {
/* 280 */         this.isHittingBlock = false;
/* 281 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
/* 282 */         onPlayerDestroyBlock(posBlock, directionFacing);
/* 283 */         this.curBlockDamageMP = 0.0F;
/* 284 */         this.stepSoundTickCounter = 0.0F;
/* 285 */         this.blockHitDelay = 5;
/*     */       } 
/*     */       
/* 288 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 289 */       return true;
/*     */     } 
/*     */     
/* 292 */     return clickBlock(posBlock, directionFacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockReachDistance() {
/* 300 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/*     */   }
/*     */   
/*     */   public void updateController() {
/* 304 */     syncCurrentPlayItem();
/*     */     
/* 306 */     if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
/* 307 */       this.netClientHandler.getNetworkManager().processReceivedPackets();
/*     */     } else {
/* 309 */       this.netClientHandler.getNetworkManager().checkDisconnected();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isHittingPosition(BlockPos pos) {
/* 314 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 315 */     boolean flag = (this.currentItemHittingBlock == null && itemstack == null);
/*     */     
/* 317 */     if (this.currentItemHittingBlock != null && itemstack != null) {
/* 318 */       flag = (itemstack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata()));
/*     */     }
/*     */     
/* 321 */     return (pos.equals(this.currentBlock) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void syncCurrentPlayItem() {
/* 328 */     int i = this.mc.thePlayer.inventory.currentItem;
/*     */     
/* 330 */     if (i != this.currentPlayerItem) {
/* 331 */       this.currentPlayerItem = i;
/* 332 */       this.netClientHandler.addToSendQueue((Packet)new C09PacketHeldItemChange(this.currentPlayerItem));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec) {
/* 337 */     syncCurrentPlayItem();
/* 338 */     float f = (float)(hitVec.xCoord - hitPos.getX());
/* 339 */     float f1 = (float)(hitVec.yCoord - hitPos.getY());
/* 340 */     float f2 = (float)(hitVec.zCoord - hitPos.getZ());
/* 341 */     boolean flag = false;
/*     */     
/* 343 */     if (!this.mc.theWorld.getWorldBorder().contains(hitPos)) {
/* 344 */       return false;
/*     */     }
/* 346 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 347 */       IBlockState iblockstate = worldIn.getBlockState(hitPos);
/*     */       
/* 349 */       if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, (EntityPlayer)player, side, f, f1, f2)) {
/* 350 */         flag = true;
/*     */       }
/*     */       
/* 353 */       if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
/* 354 */         ItemBlock itemblock = (ItemBlock)heldStack.getItem();
/*     */         
/* 356 */         if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, (EntityPlayer)player, heldStack)) {
/* 357 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), player.inventory.getCurrentItem(), f, f1, f2));
/*     */     
/* 364 */     if (!flag && this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 365 */       if (heldStack == null)
/* 366 */         return false; 
/* 367 */       if (this.currentGameType.isCreative()) {
/* 368 */         int i = heldStack.getMetadata();
/* 369 */         int j = heldStack.stackSize;
/* 370 */         boolean flag1 = heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/* 371 */         heldStack.setItemDamage(i);
/* 372 */         heldStack.stackSize = j;
/* 373 */         return flag1;
/*     */       } 
/* 375 */       return heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/*     */     } 
/*     */     
/* 378 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn) {
/* 387 */     if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 388 */       return false;
/*     */     }
/* 390 */     syncCurrentPlayItem();
/* 391 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
/* 392 */     int i = itemStackIn.stackSize;
/* 393 */     ItemStack itemstack = itemStackIn.useItemRightClick(worldIn, playerIn);
/*     */     
/* 395 */     if (itemstack != itemStackIn || (itemstack != null && itemstack.stackSize != i)) {
/* 396 */       playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = itemstack;
/*     */       
/* 398 */       if (itemstack.stackSize == 0) {
/* 399 */         playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 402 */       return true;
/*     */     } 
/* 404 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter statWriter) {
/* 410 */     return new EntityPlayerSP(this.mc, worldIn, this.netClientHandler, statWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
/* 417 */     syncCurrentPlayItem();
/* 418 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
/*     */     
/* 420 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 421 */       playerIn.attackTargetEntityWithCurrentItem(targetEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity) {
/* 429 */     syncCurrentPlayItem();
/* 430 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
/* 431 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity entityIn, MovingObjectPosition movingObject) {
/* 442 */     syncCurrentPlayItem();
/* 443 */     Vec3 vec3 = new Vec3(movingObject.hitVec.xCoord - entityIn.posX, movingObject.hitVec.yCoord - entityIn.posY, movingObject.hitVec.zCoord - entityIn.posZ);
/* 444 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(entityIn, vec3));
/* 445 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && entityIn.interactAt(player, vec3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn) {
/* 452 */     short short1 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
/* 453 */     ItemStack itemstack = playerIn.openContainer.slotClick(slotId, mouseButtonClicked, mode, playerIn);
/* 454 */     this.netClientHandler.addToSendQueue((Packet)new C0EPacketClickWindow(windowId, slotId, mouseButtonClicked, mode, itemstack, short1));
/* 455 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEnchantPacket(int windowID, int button) {
/* 466 */     this.netClientHandler.addToSendQueue((Packet)new C11PacketEnchantItem(windowID, button));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
/* 473 */     if (this.currentGameType.isCreative()) {
/* 474 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(slotId, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketDropItem(ItemStack itemStackIn) {
/* 482 */     if (this.currentGameType.isCreative() && itemStackIn != null) {
/* 483 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(-1, itemStackIn));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onStoppedUsingItem(EntityPlayer playerIn) {
/* 488 */     syncCurrentPlayItem();
/* 489 */     this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 490 */     playerIn.stopUsingItem();
/*     */   }
/*     */   
/*     */   public boolean gameIsSurvivalOrAdventure() {
/* 494 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotCreative() {
/* 501 */     return !this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCreativeMode() {
/* 508 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedReach() {
/* 515 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 522 */     return (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse);
/*     */   }
/*     */   
/*     */   public boolean isSpectatorMode() {
/* 526 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getCurrentGameType() {
/* 530 */     return this.currentGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsHittingBlock() {
/* 537 */     return this.isHittingBlock;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\PlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */