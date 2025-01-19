/*     */ package net.minecraft.world.demo;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.server.management.ItemInWorldManager;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DemoWorldManager extends ItemInWorldManager {
/*     */   private boolean field_73105_c;
/*     */   private boolean demoTimeExpired;
/*     */   
/*     */   public DemoWorldManager(World worldIn) {
/*  19 */     super(worldIn);
/*     */   }
/*     */   private int field_73104_e; private int field_73102_f;
/*     */   public void updateBlockRemoving() {
/*  23 */     super.updateBlockRemoving();
/*  24 */     this.field_73102_f++;
/*  25 */     long i = this.theWorld.getTotalWorldTime();
/*  26 */     long j = i / 24000L + 1L;
/*     */     
/*  28 */     if (!this.field_73105_c && this.field_73102_f > 20) {
/*  29 */       this.field_73105_c = true;
/*  30 */       this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(5, 0.0F));
/*     */     } 
/*     */     
/*  33 */     this.demoTimeExpired = (i > 120500L);
/*     */     
/*  35 */     if (this.demoTimeExpired) {
/*  36 */       this.field_73104_e++;
/*     */     }
/*     */     
/*  39 */     if (i % 24000L == 500L) {
/*  40 */       if (j <= 6L) {
/*  41 */         this.thisPlayerMP.addChatMessage((IChatComponent)new ChatComponentTranslation("demo.day." + j, new Object[0]));
/*     */       }
/*  43 */     } else if (j == 1L) {
/*  44 */       if (i == 100L) {
/*  45 */         this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(5, 101.0F));
/*  46 */       } else if (i == 175L) {
/*  47 */         this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(5, 102.0F));
/*  48 */       } else if (i == 250L) {
/*  49 */         this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(5, 103.0F));
/*     */       } 
/*  51 */     } else if (j == 5L && i % 24000L == 22000L) {
/*  52 */       this.thisPlayerMP.addChatMessage((IChatComponent)new ChatComponentTranslation("demo.day.warning", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendDemoReminder() {
/*  60 */     if (this.field_73104_e > 100) {
/*  61 */       this.thisPlayerMP.addChatMessage((IChatComponent)new ChatComponentTranslation("demo.reminder", new Object[0]));
/*  62 */       this.field_73104_e = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(BlockPos pos, EnumFacing side) {
/*  71 */     if (this.demoTimeExpired) {
/*  72 */       sendDemoReminder();
/*     */     } else {
/*  74 */       super.onBlockClicked(pos, side);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blockRemoving(BlockPos pos) {
/*  79 */     if (!this.demoTimeExpired) {
/*  80 */       super.blockRemoving(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryHarvestBlock(BlockPos pos) {
/*  88 */     return this.demoTimeExpired ? false : super.tryHarvestBlock(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/*  95 */     if (this.demoTimeExpired) {
/*  96 */       sendDemoReminder();
/*  97 */       return false;
/*     */     } 
/*  99 */     return super.tryUseItem(player, worldIn, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean activateBlockOrUseItem(EntityPlayer player, World worldIn, ItemStack stack, BlockPos pos, EnumFacing side, float offsetX, float offsetY, float offsetZ) {
/* 107 */     if (this.demoTimeExpired) {
/* 108 */       sendDemoReminder();
/* 109 */       return false;
/*     */     } 
/* 111 */     return super.activateBlockOrUseItem(player, worldIn, stack, pos, side, offsetX, offsetY, offsetZ);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\demo\DemoWorldManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */