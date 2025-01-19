/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelLargeChest;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {
/*  14 */   private static final ResourceLocation textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
/*  15 */   private static final ResourceLocation textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
/*  16 */   private static final ResourceLocation textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
/*  17 */   private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
/*  18 */   private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
/*  19 */   private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
/*  20 */   private ModelChest simpleChest = new ModelChest();
/*  21 */   private ModelChest largeChest = (ModelChest)new ModelLargeChest();
/*     */   private boolean isChristmas;
/*     */   
/*     */   public TileEntityChestRenderer() {
/*  25 */     Calendar calendar = Calendar.getInstance();
/*     */     
/*  27 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
/*  28 */       this.isChristmas = true; 
/*     */   }
/*     */   
/*     */   public void renderTileEntityAt(TileEntityChest te, double x, double y, double z, float partialTicks, int destroyStage) {
/*     */     int i;
/*  33 */     GlStateManager.enableDepth();
/*  34 */     GlStateManager.depthFunc(515);
/*  35 */     GlStateManager.depthMask(true);
/*     */ 
/*     */     
/*  38 */     if (!te.hasWorldObj()) {
/*  39 */       i = 0;
/*     */     } else {
/*  41 */       Block block = te.getBlockType();
/*  42 */       i = te.getBlockMetadata();
/*     */       
/*  44 */       if (block instanceof BlockChest && i == 0) {
/*  45 */         ((BlockChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
/*  46 */         i = te.getBlockMetadata();
/*     */       } 
/*     */       
/*  49 */       te.checkForAdjacentChests();
/*     */     } 
/*     */     
/*  52 */     if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null) {
/*     */       ModelChest modelchest;
/*     */       
/*  55 */       if (te.adjacentChestXPos == null && te.adjacentChestZPos == null) {
/*  56 */         modelchest = this.simpleChest;
/*     */         
/*  58 */         if (destroyStage >= 0) {
/*  59 */           bindTexture(DESTROY_STAGES[destroyStage]);
/*  60 */           GlStateManager.matrixMode(5890);
/*  61 */           GlStateManager.pushMatrix();
/*  62 */           GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  63 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  64 */           GlStateManager.matrixMode(5888);
/*  65 */         } else if (this.isChristmas) {
/*  66 */           bindTexture(textureChristmas);
/*  67 */         } else if (te.getChestType() == 1) {
/*  68 */           bindTexture(textureTrapped);
/*     */         } else {
/*  70 */           bindTexture(textureNormal);
/*     */         } 
/*     */       } else {
/*  73 */         modelchest = this.largeChest;
/*     */         
/*  75 */         if (destroyStage >= 0) {
/*  76 */           bindTexture(DESTROY_STAGES[destroyStage]);
/*  77 */           GlStateManager.matrixMode(5890);
/*  78 */           GlStateManager.pushMatrix();
/*  79 */           GlStateManager.scale(8.0F, 4.0F, 1.0F);
/*  80 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  81 */           GlStateManager.matrixMode(5888);
/*  82 */         } else if (this.isChristmas) {
/*  83 */           bindTexture(textureChristmasDouble);
/*  84 */         } else if (te.getChestType() == 1) {
/*  85 */           bindTexture(textureTrappedDouble);
/*     */         } else {
/*  87 */           bindTexture(textureNormalDouble);
/*     */         } 
/*     */       } 
/*     */       
/*  91 */       GlStateManager.pushMatrix();
/*  92 */       GlStateManager.enableRescaleNormal();
/*     */       
/*  94 */       if (destroyStage < 0) {
/*  95 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/*  98 */       GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
/*  99 */       GlStateManager.scale(1.0F, -1.0F, -1.0F);
/* 100 */       GlStateManager.translate(0.5F, 0.5F, 0.5F);
/* 101 */       int j = 0;
/*     */       
/* 103 */       if (i == 2) {
/* 104 */         j = 180;
/*     */       }
/*     */       
/* 107 */       if (i == 3) {
/* 108 */         j = 0;
/*     */       }
/*     */       
/* 111 */       if (i == 4) {
/* 112 */         j = 90;
/*     */       }
/*     */       
/* 115 */       if (i == 5) {
/* 116 */         j = -90;
/*     */       }
/*     */       
/* 119 */       if (i == 2 && te.adjacentChestXPos != null) {
/* 120 */         GlStateManager.translate(1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 123 */       if (i == 5 && te.adjacentChestZPos != null) {
/* 124 */         GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */       }
/*     */       
/* 127 */       GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/* 128 */       GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/* 129 */       float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
/*     */       
/* 131 */       if (te.adjacentChestZNeg != null) {
/* 132 */         float f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;
/*     */         
/* 134 */         if (f1 > f) {
/* 135 */           f = f1;
/*     */         }
/*     */       } 
/*     */       
/* 139 */       if (te.adjacentChestXNeg != null) {
/* 140 */         float f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;
/*     */         
/* 142 */         if (f2 > f) {
/* 143 */           f = f2;
/*     */         }
/*     */       } 
/*     */       
/* 147 */       f = 1.0F - f;
/* 148 */       f = 1.0F - f * f * f;
/* 149 */       modelchest.chestLid.rotateAngleX = -(f * 3.1415927F / 2.0F);
/* 150 */       modelchest.renderAll();
/* 151 */       GlStateManager.disableRescaleNormal();
/* 152 */       GlStateManager.popMatrix();
/* 153 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 155 */       if (destroyStage >= 0) {
/* 156 */         GlStateManager.matrixMode(5890);
/* 157 */         GlStateManager.popMatrix();
/* 158 */         GlStateManager.matrixMode(5888);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */