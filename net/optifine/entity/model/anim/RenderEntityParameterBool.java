/*     */ package net.optifine.entity.model.anim;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ 
/*     */ public enum RenderEntityParameterBool implements IExpressionBool {
/*     */   private String name;
/*     */   private RenderManager renderManager;
/*  12 */   IS_ALIVE("is_alive"),
/*  13 */   IS_BURNING("is_burning"),
/*  14 */   IS_CHILD("is_child"),
/*  15 */   IS_GLOWING("is_glowing"),
/*  16 */   IS_HURT("is_hurt"),
/*  17 */   IS_IN_LAVA("is_in_lava"),
/*  18 */   IS_IN_WATER("is_in_water"),
/*  19 */   IS_INVISIBLE("is_invisible"),
/*  20 */   IS_ON_GROUND("is_on_ground"),
/*  21 */   IS_RIDDEN("is_ridden"),
/*  22 */   IS_RIDING("is_riding"),
/*  23 */   IS_SNEAKING("is_sneaking"),
/*  24 */   IS_SPRINTING("is_sprinting"),
/*  25 */   IS_WET("is_wet");
/*     */   private static final RenderEntityParameterBool[] VALUES;
/*     */   
/*     */   static {
/*  29 */     VALUES = values();
/*     */   }
/*     */   RenderEntityParameterBool(String name) {
/*  32 */     this.name = name;
/*  33 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  37 */     return this.name;
/*     */   }
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  41 */     return ExpressionType.BOOL;
/*     */   }
/*     */   
/*     */   public boolean eval() {
/*  45 */     Render render = this.renderManager.renderRender;
/*     */     
/*  47 */     if (render == null) {
/*  48 */       return false;
/*     */     }
/*  50 */     if (render instanceof RendererLivingEntity) {
/*  51 */       RendererLivingEntity rendererlivingentity = (RendererLivingEntity)render;
/*  52 */       EntityLivingBase entitylivingbase = rendererlivingentity.renderEntity;
/*     */       
/*  54 */       if (entitylivingbase == null) {
/*  55 */         return false;
/*     */       }
/*     */       
/*  58 */       switch (this) {
/*     */         case null:
/*  60 */           return entitylivingbase.isEntityAlive();
/*     */         
/*     */         case IS_BURNING:
/*  63 */           return entitylivingbase.isBurning();
/*     */         
/*     */         case IS_CHILD:
/*  66 */           return entitylivingbase.isChild();
/*     */         
/*     */         case IS_HURT:
/*  69 */           return (entitylivingbase.hurtTime > 0);
/*     */         
/*     */         case IS_IN_LAVA:
/*  72 */           return entitylivingbase.isInLava();
/*     */         
/*     */         case IS_IN_WATER:
/*  75 */           return entitylivingbase.isInWater();
/*     */         
/*     */         case IS_INVISIBLE:
/*  78 */           return entitylivingbase.isInvisible();
/*     */         
/*     */         case IS_ON_GROUND:
/*  81 */           return entitylivingbase.onGround;
/*     */         
/*     */         case IS_RIDDEN:
/*  84 */           return (entitylivingbase.riddenByEntity != null);
/*     */         
/*     */         case IS_RIDING:
/*  87 */           return entitylivingbase.isRiding();
/*     */         
/*     */         case IS_SNEAKING:
/*  90 */           return entitylivingbase.isSneaking();
/*     */         
/*     */         case IS_SPRINTING:
/*  93 */           return entitylivingbase.isSprinting();
/*     */         
/*     */         case IS_WET:
/*  96 */           return entitylivingbase.isWet();
/*     */       } 
/*     */     
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RenderEntityParameterBool parse(String str) {
/* 105 */     if (str == null) {
/* 106 */       return null;
/*     */     }
/* 108 */     for (int i = 0; i < VALUES.length; i++) {
/* 109 */       RenderEntityParameterBool renderentityparameterbool = VALUES[i];
/*     */       
/* 111 */       if (renderentityparameterbool.getName().equals(str)) {
/* 112 */         return renderentityparameterbool;
/*     */       }
/*     */     } 
/*     */     
/* 116 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\RenderEntityParameterBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */