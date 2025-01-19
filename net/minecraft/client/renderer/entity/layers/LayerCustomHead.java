/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerCustomHead
/*     */   implements LayerRenderer<EntityLivingBase>
/*     */ {
/*     */   private final ModelRenderer field_177209_a;
/*     */   
/*     */   public LayerCustomHead(ModelRenderer p_i46120_1_) {
/*  29 */     this.field_177209_a = p_i46120_1_;
/*     */   }
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  33 */     ItemStack itemstack = entitylivingbaseIn.getCurrentArmor(3);
/*     */     
/*  35 */     if (itemstack != null && itemstack.getItem() != null) {
/*  36 */       Item item = itemstack.getItem();
/*  37 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  38 */       GlStateManager.pushMatrix();
/*     */       
/*  40 */       if (entitylivingbaseIn.isSneaking()) {
/*  41 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  44 */       boolean flag = !(!(entitylivingbaseIn instanceof net.minecraft.entity.passive.EntityVillager) && (!(entitylivingbaseIn instanceof EntityZombie) || !((EntityZombie)entitylivingbaseIn).isVillager()));
/*     */       
/*  46 */       if (!flag && entitylivingbaseIn.isChild()) {
/*  47 */         float f = 2.0F;
/*  48 */         float f1 = 1.4F;
/*  49 */         GlStateManager.scale(f1 / f, f1 / f, f1 / f);
/*  50 */         GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*     */       } 
/*     */       
/*  53 */       this.field_177209_a.postRender(0.0625F);
/*  54 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  56 */       if (item instanceof net.minecraft.item.ItemBlock) {
/*  57 */         float f2 = 0.625F;
/*  58 */         GlStateManager.translate(0.0F, -0.25F, 0.0F);
/*  59 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*  60 */         GlStateManager.scale(f2, -f2, -f2);
/*     */         
/*  62 */         if (flag) {
/*  63 */           GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */         }
/*     */         
/*  66 */         minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
/*  67 */       } else if (item == Items.skull) {
/*  68 */         float f3 = 1.1875F;
/*  69 */         GlStateManager.scale(f3, -f3, -f3);
/*     */         
/*  71 */         if (flag) {
/*  72 */           GlStateManager.translate(0.0F, 0.0625F, 0.0F);
/*     */         }
/*     */         
/*  75 */         GameProfile gameprofile = null;
/*     */         
/*  77 */         if (itemstack.hasTagCompound()) {
/*  78 */           NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*     */           
/*  80 */           if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*  81 */             gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*  82 */           } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/*  83 */             String s = nbttagcompound.getString("SkullOwner");
/*     */             
/*  85 */             if (!StringUtils.isNullOrEmpty(s)) {
/*  86 */               gameprofile = TileEntitySkull.updateGameprofile(new GameProfile(null, s));
/*  87 */               nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  92 */         TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1);
/*     */       } 
/*     */       
/*  95 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerCustomHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */