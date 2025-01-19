/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActiveRenderInfo
/*     */ {
/*  22 */   private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final FloatBuffer OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
/*  38 */   private static Vec3 position = new Vec3(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationX;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationXZ;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationZ;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationYZ;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationXY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateRenderInfo(EntityPlayer entityplayerIn, boolean p_74583_1_) {
/*  69 */     GlStateManager.getFloat(2982, MODELVIEW);
/*  70 */     GlStateManager.getFloat(2983, PROJECTION);
/*  71 */     GL11.glGetInteger(2978, VIEWPORT);
/*  72 */     float f = ((VIEWPORT.get(0) + VIEWPORT.get(2)) / 2);
/*  73 */     float f1 = ((VIEWPORT.get(1) + VIEWPORT.get(3)) / 2);
/*  74 */     GLU.gluUnProject(f, f1, 0.0F, MODELVIEW, PROJECTION, VIEWPORT, OBJECTCOORDS);
/*  75 */     position = new Vec3(OBJECTCOORDS.get(0), OBJECTCOORDS.get(1), OBJECTCOORDS.get(2));
/*  76 */     int i = p_74583_1_ ? 1 : 0;
/*  77 */     float f2 = entityplayerIn.rotationPitch;
/*  78 */     float f3 = entityplayerIn.rotationYaw;
/*  79 */     rotationX = MathHelper.cos(f3 * 3.1415927F / 180.0F) * (1 - i * 2);
/*  80 */     rotationZ = MathHelper.sin(f3 * 3.1415927F / 180.0F) * (1 - i * 2);
/*  81 */     rotationYZ = -rotationZ * MathHelper.sin(f2 * 3.1415927F / 180.0F) * (1 - i * 2);
/*  82 */     rotationXY = rotationX * MathHelper.sin(f2 * 3.1415927F / 180.0F) * (1 - i * 2);
/*  83 */     rotationXZ = MathHelper.cos(f2 * 3.1415927F / 180.0F);
/*     */   }
/*     */   
/*     */   public static Vec3 projectViewFromEntity(Entity p_178806_0_, double p_178806_1_) {
/*  87 */     double d0 = p_178806_0_.prevPosX + (p_178806_0_.posX - p_178806_0_.prevPosX) * p_178806_1_;
/*  88 */     double d1 = p_178806_0_.prevPosY + (p_178806_0_.posY - p_178806_0_.prevPosY) * p_178806_1_;
/*  89 */     double d2 = p_178806_0_.prevPosZ + (p_178806_0_.posZ - p_178806_0_.prevPosZ) * p_178806_1_;
/*  90 */     double d3 = d0 + position.xCoord;
/*  91 */     double d4 = d1 + position.yCoord;
/*  92 */     double d5 = d2 + position.zCoord;
/*  93 */     return new Vec3(d3, d4, d5);
/*     */   }
/*     */   
/*     */   public static Block getBlockAtEntityViewpoint(World worldIn, Entity p_180786_1_, float p_180786_2_) {
/*  97 */     Vec3 vec3 = projectViewFromEntity(p_180786_1_, p_180786_2_);
/*  98 */     BlockPos blockpos = new BlockPos(vec3);
/*  99 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 100 */     Block block = iblockstate.getBlock();
/*     */     
/* 102 */     if (block.getMaterial().isLiquid()) {
/* 103 */       float f = 0.0F;
/*     */       
/* 105 */       if (iblockstate.getBlock() instanceof BlockLiquid) {
/* 106 */         f = BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue()) - 0.11111111F;
/*     */       }
/*     */       
/* 109 */       float f1 = (blockpos.getY() + 1) - f;
/*     */       
/* 111 */       if (vec3.yCoord >= f1) {
/* 112 */         block = worldIn.getBlockState(blockpos.up()).getBlock();
/*     */       }
/*     */     } 
/*     */     
/* 116 */     return block;
/*     */   }
/*     */   
/*     */   public static Vec3 getPosition() {
/* 120 */     return position;
/*     */   }
/*     */   
/*     */   public static float getRotationX() {
/* 124 */     return rotationX;
/*     */   }
/*     */   
/*     */   public static float getRotationXZ() {
/* 128 */     return rotationXZ;
/*     */   }
/*     */   
/*     */   public static float getRotationZ() {
/* 132 */     return rotationZ;
/*     */   }
/*     */   
/*     */   public static float getRotationYZ() {
/* 136 */     return rotationYZ;
/*     */   }
/*     */   
/*     */   public static float getRotationXY() {
/* 140 */     return rotationXY;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ActiveRenderInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */