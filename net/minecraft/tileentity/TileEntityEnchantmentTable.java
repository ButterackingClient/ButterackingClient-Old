/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ 
/*     */ public class TileEntityEnchantmentTable
/*     */   extends TileEntity implements ITickable, IInteractionObject {
/*     */   public int tickCount;
/*     */   public float pageFlip;
/*     */   public float pageFlipPrev;
/*     */   public float field_145932_k;
/*     */   public float field_145929_l;
/*     */   public float bookSpread;
/*     */   public float bookSpreadPrev;
/*     */   public float bookRotation;
/*     */   public float bookRotationPrev;
/*     */   public float field_145924_q;
/*  28 */   private static Random rand = new Random();
/*     */   private String customName;
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  32 */     super.writeToNBT(compound);
/*     */     
/*  34 */     if (hasCustomName()) {
/*  35 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  40 */     super.readFromNBT(compound);
/*     */     
/*  42 */     if (compound.hasKey("CustomName", 8)) {
/*  43 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  51 */     this.bookSpreadPrev = this.bookSpread;
/*  52 */     this.bookRotationPrev = this.bookRotation;
/*  53 */     EntityPlayer entityplayer = this.worldObj.getClosestPlayer((this.pos.getX() + 0.5F), (this.pos.getY() + 0.5F), (this.pos.getZ() + 0.5F), 3.0D);
/*     */     
/*  55 */     if (entityplayer != null) {
/*  56 */       double d0 = entityplayer.posX - (this.pos.getX() + 0.5F);
/*  57 */       double d1 = entityplayer.posZ - (this.pos.getZ() + 0.5F);
/*  58 */       this.field_145924_q = (float)MathHelper.atan2(d1, d0);
/*  59 */       this.bookSpread += 0.1F;
/*     */       
/*  61 */       if (this.bookSpread < 0.5F || rand.nextInt(40) == 0) {
/*  62 */         float f1 = this.field_145932_k;
/*     */         
/*     */         do {
/*  65 */           this.field_145932_k += (rand.nextInt(4) - rand.nextInt(4));
/*     */         }
/*  67 */         while (f1 == this.field_145932_k);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  73 */       this.field_145924_q += 0.02F;
/*  74 */       this.bookSpread -= 0.1F;
/*     */     } 
/*     */     
/*  77 */     while (this.bookRotation >= 3.1415927F) {
/*  78 */       this.bookRotation -= 6.2831855F;
/*     */     }
/*     */     
/*  81 */     while (this.bookRotation < -3.1415927F) {
/*  82 */       this.bookRotation += 6.2831855F;
/*     */     }
/*     */     
/*  85 */     while (this.field_145924_q >= 3.1415927F) {
/*  86 */       this.field_145924_q -= 6.2831855F;
/*     */     }
/*     */     
/*  89 */     while (this.field_145924_q < -3.1415927F) {
/*  90 */       this.field_145924_q += 6.2831855F;
/*     */     }
/*     */     
/*     */     float f2;
/*     */     
/*  95 */     for (f2 = this.field_145924_q - this.bookRotation; f2 >= 3.1415927F; f2 -= 6.2831855F);
/*     */ 
/*     */ 
/*     */     
/*  99 */     while (f2 < -3.1415927F) {
/* 100 */       f2 += 6.2831855F;
/*     */     }
/*     */     
/* 103 */     this.bookRotation += f2 * 0.4F;
/* 104 */     this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0F, 1.0F);
/* 105 */     this.tickCount++;
/* 106 */     this.pageFlipPrev = this.pageFlip;
/* 107 */     float f = (this.field_145932_k - this.pageFlip) * 0.4F;
/* 108 */     float f3 = 0.2F;
/* 109 */     f = MathHelper.clamp_float(f, -f3, f3);
/* 110 */     this.field_145929_l += (f - this.field_145929_l) * 0.9F;
/* 111 */     this.pageFlip += this.field_145929_l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return hasCustomName() ? this.customName : "container.enchant";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 125 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 129 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 136 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 140 */     return (Container)new ContainerEnchantment(playerInventory, this.worldObj, this.pos);
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 144 */     return "minecraft:enchanting_table";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */