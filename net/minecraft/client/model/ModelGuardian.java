/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class ModelGuardian
/*     */   extends ModelBase {
/*     */   private ModelRenderer guardianBody;
/*     */   private ModelRenderer guardianEye;
/*     */   
/*     */   public ModelGuardian() {
/*  16 */     this.textureWidth = 64;
/*  17 */     this.textureHeight = 64;
/*  18 */     this.guardianSpines = new ModelRenderer[12];
/*  19 */     this.guardianBody = new ModelRenderer(this);
/*  20 */     this.guardianBody.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
/*  21 */     this.guardianBody.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12);
/*  22 */     this.guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
/*  23 */     this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12);
/*  24 */     this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12);
/*     */     
/*  26 */     for (int i = 0; i < this.guardianSpines.length; i++) {
/*  27 */       this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
/*  28 */       this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
/*  29 */       this.guardianBody.addChild(this.guardianSpines[i]);
/*     */     } 
/*     */     
/*  32 */     this.guardianEye = new ModelRenderer(this, 8, 0);
/*  33 */     this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
/*  34 */     this.guardianBody.addChild(this.guardianEye);
/*  35 */     this.guardianTail = new ModelRenderer[3];
/*  36 */     this.guardianTail[0] = new ModelRenderer(this, 40, 0);
/*  37 */     this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
/*  38 */     this.guardianTail[1] = new ModelRenderer(this, 0, 54);
/*  39 */     this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
/*  40 */     this.guardianTail[2] = new ModelRenderer(this);
/*  41 */     this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
/*  42 */     this.guardianTail[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
/*  43 */     this.guardianBody.addChild(this.guardianTail[0]);
/*  44 */     this.guardianTail[0].addChild(this.guardianTail[1]);
/*  45 */     this.guardianTail[1].addChild(this.guardianTail[2]);
/*     */   }
/*     */   private ModelRenderer[] guardianSpines; private ModelRenderer[] guardianTail;
/*     */   public int func_178706_a() {
/*  49 */     return 54;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  56 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  57 */     this.guardianBody.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*     */     EntityLivingBase entityLivingBase;
/*  66 */     EntityGuardian entityguardian = (EntityGuardian)entityIn;
/*  67 */     float f = ageInTicks - entityguardian.ticksExisted;
/*  68 */     this.guardianBody.rotateAngleY = netHeadYaw / 57.295776F;
/*  69 */     this.guardianBody.rotateAngleX = headPitch / 57.295776F;
/*  70 */     float[] afloat = { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F };
/*  71 */     float[] afloat1 = { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F };
/*  72 */     float[] afloat2 = { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F };
/*  73 */     float[] afloat3 = { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F };
/*  74 */     float[] afloat4 = { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F };
/*  75 */     float[] afloat5 = { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F };
/*  76 */     float f1 = (1.0F - entityguardian.func_175469_o(f)) * 0.55F;
/*     */     
/*  78 */     for (int i = 0; i < 12; i++) {
/*  79 */       (this.guardianSpines[i]).rotateAngleX = 3.1415927F * afloat[i];
/*  80 */       (this.guardianSpines[i]).rotateAngleY = 3.1415927F * afloat1[i];
/*  81 */       (this.guardianSpines[i]).rotateAngleZ = 3.1415927F * afloat2[i];
/*  82 */       (this.guardianSpines[i]).rotationPointX = afloat3[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*  83 */       (this.guardianSpines[i]).rotationPointY = 16.0F + afloat4[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*  84 */       (this.guardianSpines[i]).rotationPointZ = afloat5[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*     */     } 
/*     */     
/*  87 */     this.guardianEye.rotationPointZ = -8.25F;
/*  88 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  90 */     if (entityguardian.hasTargetedEntity()) {
/*  91 */       entityLivingBase = entityguardian.getTargetedEntity();
/*     */     }
/*     */     
/*  94 */     if (entityLivingBase != null) {
/*  95 */       Vec3 vec3 = entityLivingBase.getPositionEyes(0.0F);
/*  96 */       Vec3 vec31 = entityIn.getPositionEyes(0.0F);
/*  97 */       double d0 = vec3.yCoord - vec31.yCoord;
/*     */       
/*  99 */       if (d0 > 0.0D) {
/* 100 */         this.guardianEye.rotationPointY = 0.0F;
/*     */       } else {
/* 102 */         this.guardianEye.rotationPointY = 1.0F;
/*     */       } 
/*     */       
/* 105 */       Vec3 vec32 = entityIn.getLook(0.0F);
/* 106 */       vec32 = new Vec3(vec32.xCoord, 0.0D, vec32.zCoord);
/* 107 */       Vec3 vec33 = (new Vec3(vec31.xCoord - vec3.xCoord, 0.0D, vec31.zCoord - vec3.zCoord)).normalize().rotateYaw(1.5707964F);
/* 108 */       double d1 = vec32.dotProduct(vec33);
/* 109 */       this.guardianEye.rotationPointX = MathHelper.sqrt_float((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
/*     */     } 
/*     */     
/* 112 */     this.guardianEye.showModel = true;
/* 113 */     float f2 = entityguardian.func_175471_a(f);
/* 114 */     (this.guardianTail[0]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.05F;
/* 115 */     (this.guardianTail[1]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.1F;
/* 116 */     (this.guardianTail[1]).rotationPointX = -1.5F;
/* 117 */     (this.guardianTail[1]).rotationPointY = 0.5F;
/* 118 */     (this.guardianTail[1]).rotationPointZ = 14.0F;
/* 119 */     (this.guardianTail[2]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.15F;
/* 120 */     (this.guardianTail[2]).rotationPointX = 0.5F;
/* 121 */     (this.guardianTail[2]).rotationPointY = 0.5F;
/* 122 */     (this.guardianTail[2]).rotationPointZ = 6.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */