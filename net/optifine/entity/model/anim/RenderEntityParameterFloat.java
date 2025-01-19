/*     */ package net.optifine.entity.model.anim;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ 
/*     */ public enum RenderEntityParameterFloat implements IExpressionFloat {
/*     */   private String name;
/*     */   private RenderManager renderManager;
/*  12 */   LIMB_SWING("limb_swing"),
/*  13 */   LIMB_SWING_SPEED("limb_speed"),
/*  14 */   AGE("age"),
/*  15 */   HEAD_YAW("head_yaw"),
/*  16 */   HEAD_PITCH("head_pitch"),
/*  17 */   SCALE("scale"),
/*  18 */   HEALTH("health"),
/*  19 */   HURT_TIME("hurt_time"),
/*  20 */   IDLE_TIME("idle_time"),
/*  21 */   MAX_HEALTH("max_health"),
/*  22 */   MOVE_FORWARD("move_forward"),
/*  23 */   MOVE_STRAFING("move_strafing"),
/*  24 */   PARTIAL_TICKS("partial_ticks"),
/*  25 */   POS_X("pos_x"),
/*  26 */   POS_Y("pos_y"),
/*  27 */   POS_Z("pos_z"),
/*  28 */   REVENGE_TIME("revenge_time"),
/*  29 */   SWING_PROGRESS("swing_progress");
/*     */   private static final RenderEntityParameterFloat[] VALUES;
/*     */   
/*     */   static {
/*  33 */     VALUES = values();
/*     */   }
/*     */   RenderEntityParameterFloat(String name) {
/*  36 */     this.name = name;
/*  37 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  41 */     return this.name;
/*     */   }
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  45 */     return ExpressionType.FLOAT;
/*     */   }
/*     */   
/*     */   public float eval() {
/*  49 */     Render render = this.renderManager.renderRender;
/*     */     
/*  51 */     if (render == null) {
/*  52 */       return 0.0F;
/*     */     }
/*  54 */     if (render instanceof RendererLivingEntity) {
/*  55 */       RendererLivingEntity rendererlivingentity = (RendererLivingEntity)render;
/*     */       
/*  57 */       switch (this) {
/*     */         case LIMB_SWING:
/*  59 */           return rendererlivingentity.renderLimbSwing;
/*     */         
/*     */         case LIMB_SWING_SPEED:
/*  62 */           return rendererlivingentity.renderLimbSwingAmount;
/*     */         
/*     */         case null:
/*  65 */           return rendererlivingentity.renderAgeInTicks;
/*     */         
/*     */         case HEAD_YAW:
/*  68 */           return rendererlivingentity.renderHeadYaw;
/*     */         
/*     */         case HEAD_PITCH:
/*  71 */           return rendererlivingentity.renderHeadPitch;
/*     */         
/*     */         case SCALE:
/*  74 */           return rendererlivingentity.renderScaleFactor;
/*     */       } 
/*     */       
/*  77 */       EntityLivingBase entitylivingbase = rendererlivingentity.renderEntity;
/*     */       
/*  79 */       if (entitylivingbase == null) {
/*  80 */         return 0.0F;
/*     */       }
/*     */       
/*  83 */       switch (this) {
/*     */         case HEALTH:
/*  85 */           return entitylivingbase.getHealth();
/*     */         
/*     */         case HURT_TIME:
/*  88 */           return entitylivingbase.hurtTime;
/*     */         
/*     */         case IDLE_TIME:
/*  91 */           return entitylivingbase.getAge();
/*     */         
/*     */         case MAX_HEALTH:
/*  94 */           return entitylivingbase.getMaxHealth();
/*     */         
/*     */         case MOVE_FORWARD:
/*  97 */           return entitylivingbase.moveForward;
/*     */         
/*     */         case MOVE_STRAFING:
/* 100 */           return entitylivingbase.moveStrafing;
/*     */         
/*     */         case POS_X:
/* 103 */           return (float)entitylivingbase.posX;
/*     */         
/*     */         case POS_Y:
/* 106 */           return (float)entitylivingbase.posY;
/*     */         
/*     */         case POS_Z:
/* 109 */           return (float)entitylivingbase.posZ;
/*     */         
/*     */         case REVENGE_TIME:
/* 112 */           return entitylivingbase.getRevengeTimer();
/*     */         
/*     */         case SWING_PROGRESS:
/* 115 */           return entitylivingbase.getSwingProgress(rendererlivingentity.renderPartialTicks);
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 120 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RenderEntityParameterFloat parse(String str) {
/* 125 */     if (str == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     for (int i = 0; i < VALUES.length; i++) {
/* 129 */       RenderEntityParameterFloat renderentityparameterfloat = VALUES[i];
/*     */       
/* 131 */       if (renderentityparameterfloat.getName().equals(str)) {
/* 132 */         return renderentityparameterfloat;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\RenderEntityParameterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */