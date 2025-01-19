/*     */ package cosmtcs;
/*     */ 
/*     */ import client.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CosmeticWings
/*     */   extends Cosmetic {
/*     */   private ModelRenderer wing;
/*     */   
/*     */   public CosmeticWings(RenderPlayer player) {
/*  17 */     super(player);
/*  18 */     this.playerRenderer = player;
/*  19 */     this.textureWidth = 256;
/*  20 */     this.textureHeight = 256;
/*  21 */     setTextureOffset("wing.skin", -56, 88);
/*  22 */     setTextureOffset("wing.bone", 112, 88);
/*  23 */     setTextureOffset("wingtip.skin", -56, 144);
/*  24 */     setTextureOffset("wingtip.bone", 112, 136);
/*  25 */     (this.wing = new ModelRenderer((ModelBase)this, "wing")).setTextureSize(256, 256);
/*  26 */     this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
/*  27 */     this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
/*  28 */     this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/*  29 */     this.wing.isHidden = true;
/*  30 */     (this.wingTip = new ModelRenderer((ModelBase)this, "wingtip")).setTextureSize(256, 256);
/*  31 */     this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
/*  32 */     this.wingTip.isHidden = true;
/*  33 */     this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
/*  34 */     this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/*  35 */     this.wing.addChild(this.wingTip);
/*     */   }
/*     */   private ModelRenderer wingTip; private RenderPlayer playerRenderer;
/*     */   
/*     */   public boolean shouldCombineTextures() {
/*  40 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
/*  45 */     if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && (Client.getInstance()).hudManager.wings.isEnabled()) {
/*  46 */       GlStateManager.pushMatrix();
/*  47 */       float f1 = ageInTicks / 75.0F;
/*  48 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("client/wing.png"));
/*  49 */       GlStateManager.disableLighting();
/*  50 */       GlStateManager.scale(0.16D, 0.16D, 0.16D);
/*  51 */       GlStateManager.translate(0.0D, -0.3D, 1.1D);
/*  52 */       GlStateManager.rotate(50.0F, -50.0F, 0.0F, 0.0F);
/*  53 */       GlStateManager.blendFunc(1, 1);
/*  54 */       if (player.isSneaking()) {
/*  55 */         GlStateManager.translate(0.0F, 0.142F, 1.2F);
/*     */       }
/*  57 */       for (int i = 0; i < 2; i++) {
/*  58 */         float f2 = f1 * 9.141593F * 1.2F;
/*  59 */         this.wing.rotateAngleX = 0.125F - (float)Math.cos(f2) * 0.2F;
/*  60 */         this.wing.rotateAngleY = 0.25F;
/*  61 */         this.wing.rotateAngleZ = (float)(Math.sin(f2) + 1.225D) * 0.45F;
/*  62 */         this.wingTip.rotateAngleZ = -((float)(Math.sin((f2 + 2.0F)) + 0.5D)) * 0.95F;
/*  63 */         this.wing.isHidden = false;
/*  64 */         this.wingTip.isHidden = false;
/*  65 */         this.wing.render(scale);
/*  66 */         this.wing.isHidden = true;
/*  67 */         this.wingTip.isHidden = true;
/*  68 */         if (i == 0) {
/*  69 */           GlStateManager.scale(-1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       } 
/*  72 */       GlStateManager.blendFunc(0, 0);
/*  73 */       GlStateManager.disableBlend();
/*  74 */       GlStateManager.popMatrix();
/*     */     } 
/*  76 */     if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && (Client.getInstance()).hudManager.wings2.isEnabled() && !(Client.getInstance()).hudManager.wings.isEnabled()) {
/*  77 */       GlStateManager.pushMatrix();
/*  78 */       float f1 = ageInTicks / 75.0F;
/*  79 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("client/wing2.png"));
/*  80 */       GlStateManager.disableLighting();
/*  81 */       GlStateManager.scale(0.16D, 0.16D, 0.16D);
/*  82 */       GlStateManager.translate(0.0D, -0.3D, 1.1D);
/*  83 */       GlStateManager.rotate(50.0F, -50.0F, 0.0F, 0.0F);
/*  84 */       GlStateManager.blendFunc(1, 1);
/*  85 */       if (player.isSneaking()) {
/*  86 */         GlStateManager.translate(0.0F, 0.142F, 1.2F);
/*     */       }
/*  88 */       for (int i = 0; i < 2; i++) {
/*  89 */         float f2 = f1 * 9.141593F * 1.2F;
/*  90 */         this.wing.rotateAngleX = 0.125F - (float)Math.cos(f2) * 0.2F;
/*  91 */         this.wing.rotateAngleY = 0.25F;
/*  92 */         this.wing.rotateAngleZ = (float)(Math.sin(f2) + 1.225D) * 0.45F;
/*  93 */         this.wingTip.rotateAngleZ = -((float)(Math.sin((f2 + 2.0F)) + 0.5D)) * 0.95F;
/*  94 */         this.wing.isHidden = false;
/*  95 */         this.wingTip.isHidden = false;
/*  96 */         this.wing.render(scale);
/*  97 */         this.wing.isHidden = true;
/*  98 */         this.wingTip.isHidden = true;
/*  99 */         if (i == 0) {
/* 100 */           GlStateManager.scale(-1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       } 
/* 103 */       GlStateManager.blendFunc(0, 0);
/* 104 */       GlStateManager.disableBlend();
/* 105 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\cosmtcs\CosmeticWings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */