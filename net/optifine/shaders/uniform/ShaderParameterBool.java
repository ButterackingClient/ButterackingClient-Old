/*     */ package net.optifine.shaders.uniform;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ 
/*     */ public enum ShaderParameterBool implements IExpressionBool {
/*     */   private String name;
/*     */   private RenderManager renderManager;
/*  11 */   IS_ALIVE("is_alive"),
/*  12 */   IS_BURNING("is_burning"),
/*  13 */   IS_CHILD("is_child"),
/*  14 */   IS_GLOWING("is_glowing"),
/*  15 */   IS_HURT("is_hurt"),
/*  16 */   IS_IN_LAVA("is_in_lava"),
/*  17 */   IS_IN_WATER("is_in_water"),
/*  18 */   IS_INVISIBLE("is_invisible"),
/*  19 */   IS_ON_GROUND("is_on_ground"),
/*  20 */   IS_RIDDEN("is_ridden"),
/*  21 */   IS_RIDING("is_riding"),
/*  22 */   IS_SNEAKING("is_sneaking"),
/*  23 */   IS_SPRINTING("is_sprinting"),
/*  24 */   IS_WET("is_wet");
/*     */   private static final ShaderParameterBool[] VALUES;
/*     */   
/*     */   static {
/*  28 */     VALUES = values();
/*     */   }
/*     */   ShaderParameterBool(String name) {
/*  31 */     this.name = name;
/*  32 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  36 */     return this.name;
/*     */   }
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  40 */     return ExpressionType.BOOL;
/*     */   }
/*     */   
/*     */   public boolean eval() {
/*  44 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  46 */     if (entity instanceof EntityLivingBase) {
/*  47 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*     */       
/*  49 */       switch (this) {
/*     */         case null:
/*  51 */           return entitylivingbase.isEntityAlive();
/*     */         
/*     */         case IS_BURNING:
/*  54 */           return entitylivingbase.isBurning();
/*     */         
/*     */         case IS_CHILD:
/*  57 */           return entitylivingbase.isChild();
/*     */         
/*     */         case IS_HURT:
/*  60 */           return (entitylivingbase.hurtTime > 0);
/*     */         
/*     */         case IS_IN_LAVA:
/*  63 */           return entitylivingbase.isInLava();
/*     */         
/*     */         case IS_IN_WATER:
/*  66 */           return entitylivingbase.isInWater();
/*     */         
/*     */         case IS_INVISIBLE:
/*  69 */           return entitylivingbase.isInvisible();
/*     */         
/*     */         case IS_ON_GROUND:
/*  72 */           return entitylivingbase.onGround;
/*     */         
/*     */         case IS_RIDDEN:
/*  75 */           return (entitylivingbase.riddenByEntity != null);
/*     */         
/*     */         case IS_RIDING:
/*  78 */           return entitylivingbase.isRiding();
/*     */         
/*     */         case IS_SNEAKING:
/*  81 */           return entitylivingbase.isSneaking();
/*     */         
/*     */         case IS_SPRINTING:
/*  84 */           return entitylivingbase.isSprinting();
/*     */         
/*     */         case IS_WET:
/*  87 */           return entitylivingbase.isWet();
/*     */       } 
/*     */     
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public static ShaderParameterBool parse(String str) {
/*  95 */     if (str == null) {
/*  96 */       return null;
/*     */     }
/*  98 */     for (int i = 0; i < VALUES.length; i++) {
/*  99 */       ShaderParameterBool shaderparameterbool = VALUES[i];
/*     */       
/* 101 */       if (shaderparameterbool.getName().equals(str)) {
/* 102 */         return shaderparameterbool;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderParameterBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */