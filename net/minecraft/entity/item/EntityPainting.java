/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPainting
/*     */   extends EntityHanging
/*     */ {
/*     */   public EnumArt art;
/*     */   
/*     */   public EntityPainting(World worldIn) {
/*  21 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing) {
/*  25 */     super(worldIn, pos);
/*  26 */     List<EnumArt> list = Lists.newArrayList(); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  28 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*  29 */       this.art = entitypainting$enumart;
/*  30 */       updateFacingWithBoundingBox(facing);
/*     */       
/*  32 */       if (onValidSurface()) {
/*  33 */         list.add(entitypainting$enumart);
/*     */       }
/*     */       b++; }
/*     */     
/*  37 */     if (!list.isEmpty()) {
/*  38 */       this.art = list.get(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/*  41 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing, String title) {
/*  45 */     this(worldIn, pos, facing); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  47 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*  48 */       if (entitypainting$enumart.title.equals(title)) {
/*  49 */         this.art = entitypainting$enumart;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/*  54 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  61 */     tagCompound.setString("Motive", this.art.title);
/*  62 */     super.writeEntityToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  69 */     String s = tagCompund.getString("Motive"); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  71 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*  72 */       if (entitypainting$enumart.title.equals(s)) {
/*  73 */         this.art = entitypainting$enumart;
/*     */       }
/*     */       b++; }
/*     */     
/*  77 */     if (this.art == null) {
/*  78 */       this.art = EnumArt.KEBAB;
/*     */     }
/*     */     
/*  81 */     super.readEntityFromNBT(tagCompund);
/*     */   }
/*     */   
/*     */   public int getWidthPixels() {
/*  85 */     return this.art.sizeX;
/*     */   }
/*     */   
/*     */   public int getHeightPixels() {
/*  89 */     return this.art.sizeY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {
/*  96 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*  97 */       if (brokenEntity instanceof EntityPlayer) {
/*  98 */         EntityPlayer entityplayer = (EntityPlayer)brokenEntity;
/*     */         
/* 100 */         if (entityplayer.capabilities.isCreativeMode) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       entityDropItem(new ItemStack(Items.painting), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 113 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 114 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 118 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 119 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */   
/*     */   public enum EnumArt {
/* 123 */     KEBAB("Kebab", 16, 16, 0, 0),
/* 124 */     AZTEC("Aztec", 16, 16, 16, 0),
/* 125 */     ALBAN("Alban", 16, 16, 32, 0),
/* 126 */     AZTEC_2("Aztec2", 16, 16, 48, 0),
/* 127 */     BOMB("Bomb", 16, 16, 64, 0),
/* 128 */     PLANT("Plant", 16, 16, 80, 0),
/* 129 */     WASTELAND("Wasteland", 16, 16, 96, 0),
/* 130 */     POOL("Pool", 32, 16, 0, 32),
/* 131 */     COURBET("Courbet", 32, 16, 32, 32),
/* 132 */     SEA("Sea", 32, 16, 64, 32),
/* 133 */     SUNSET("Sunset", 32, 16, 96, 32),
/* 134 */     CREEBET("Creebet", 32, 16, 128, 32),
/* 135 */     WANDERER("Wanderer", 16, 32, 0, 64),
/* 136 */     GRAHAM("Graham", 16, 32, 16, 64),
/* 137 */     MATCH("Match", 32, 32, 0, 128),
/* 138 */     BUST("Bust", 32, 32, 32, 128),
/* 139 */     STAGE("Stage", 32, 32, 64, 128),
/* 140 */     VOID("Void", 32, 32, 96, 128),
/* 141 */     SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
/* 142 */     WITHER("Wither", 32, 32, 160, 128),
/* 143 */     FIGHTERS("Fighters", 64, 32, 0, 96),
/* 144 */     POINTER("Pointer", 64, 64, 0, 192),
/* 145 */     PIGSCENE("Pigscene", 64, 64, 64, 192),
/* 146 */     BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
/* 147 */     SKELETON("Skeleton", 64, 48, 192, 64),
/* 148 */     DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);
/*     */     
/* 150 */     public static final int field_180001_A = "SkullAndRoses".length();
/*     */     
/*     */     public final String title;
/*     */     
/*     */     public final int sizeX;
/*     */     public final int sizeY;
/*     */     
/*     */     EnumArt(String titleIn, int width, int height, int textureU, int textureV) {
/* 158 */       this.title = titleIn;
/* 159 */       this.sizeX = width;
/* 160 */       this.sizeY = height;
/* 161 */       this.offsetX = textureU;
/* 162 */       this.offsetY = textureV;
/*     */     }
/*     */     
/*     */     public final int offsetX;
/*     */     public final int offsetY;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */