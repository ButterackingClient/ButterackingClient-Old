/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class TileEntityItemStackRenderer
/*    */ {
/* 21 */   public static TileEntityItemStackRenderer instance = new TileEntityItemStackRenderer();
/* 22 */   private TileEntityChest field_147717_b = new TileEntityChest(0);
/* 23 */   private TileEntityChest field_147718_c = new TileEntityChest(1);
/* 24 */   private TileEntityEnderChest enderChest = new TileEntityEnderChest();
/* 25 */   private TileEntityBanner banner = new TileEntityBanner();
/* 26 */   private TileEntitySkull skull = new TileEntitySkull();
/*    */   
/*    */   public void renderByItem(ItemStack itemStackIn) {
/* 29 */     if (itemStackIn.getItem() == Items.banner) {
/* 30 */       this.banner.setItemValues(itemStackIn);
/* 31 */       TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.banner, 0.0D, 0.0D, 0.0D, 0.0F);
/* 32 */     } else if (itemStackIn.getItem() == Items.skull) {
/* 33 */       GameProfile gameprofile = null;
/*    */       
/* 35 */       if (itemStackIn.hasTagCompound()) {
/* 36 */         NBTTagCompound nbttagcompound = itemStackIn.getTagCompound();
/*    */         
/* 38 */         if (nbttagcompound.hasKey("SkullOwner", 10)) {
/* 39 */           gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/* 40 */         } else if (nbttagcompound.hasKey("SkullOwner", 8) && nbttagcompound.getString("SkullOwner").length() > 0) {
/* 41 */           gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/* 42 */           gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 43 */           nbttagcompound.removeTag("SkullOwner");
/* 44 */           nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*    */         } 
/*    */       } 
/*    */       
/* 48 */       if (TileEntitySkullRenderer.instance != null) {
/* 49 */         GlStateManager.pushMatrix();
/* 50 */         GlStateManager.translate(-0.5F, 0.0F, -0.5F);
/* 51 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 52 */         GlStateManager.disableCull();
/* 53 */         TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 0.0F, itemStackIn.getMetadata(), gameprofile, -1);
/* 54 */         GlStateManager.enableCull();
/* 55 */         GlStateManager.popMatrix();
/*    */       } 
/*    */     } else {
/* 58 */       Block block = Block.getBlockFromItem(itemStackIn.getItem());
/*    */       
/* 60 */       if (block == Blocks.ender_chest) {
/* 61 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.enderChest, 0.0D, 0.0D, 0.0D, 0.0F);
/* 62 */       } else if (block == Blocks.trapped_chest) {
/* 63 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */       } else {
/* 65 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityItemStackRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */