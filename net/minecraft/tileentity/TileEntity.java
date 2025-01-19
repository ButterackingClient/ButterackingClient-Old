/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntity
/*     */ {
/*     */   public TileEntity() {
/*  29 */     this.pos = BlockPos.ORIGIN;
/*     */     
/*  31 */     this.blockMetadata = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private static Map<String, Class<? extends TileEntity>> nameToClassMap = Maps.newHashMap();
/*     */   private static Map<Class<? extends TileEntity>, String> classToNameMap = Maps.newHashMap();
/*     */   protected World worldObj;
/*     */   
/*     */   private static void addMapping(Class<? extends TileEntity> cl, String id) {
/*  42 */     if (nameToClassMap.containsKey(id)) {
/*  43 */       throw new IllegalArgumentException("Duplicate id: " + id);
/*     */     }
/*  45 */     nameToClassMap.put(id, cl);
/*  46 */     classToNameMap.put(cl, id);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPos pos;
/*     */   protected boolean tileEntityInvalid;
/*     */   
/*     */   public World getWorld() {
/*  54 */     return this.worldObj;
/*     */   }
/*     */   
/*     */   private int blockMetadata;
/*     */   protected Block blockType;
/*     */   
/*     */   public void setWorldObj(World worldIn) {
/*  61 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWorldObj() {
/*  68 */     return (this.worldObj != null);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  72 */     this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  76 */     String s = classToNameMap.get(getClass());
/*     */     
/*  78 */     if (s == null) {
/*  79 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*  81 */     compound.setString("id", s);
/*  82 */     compound.setInteger("x", this.pos.getX());
/*  83 */     compound.setInteger("y", this.pos.getY());
/*  84 */     compound.setInteger("z", this.pos.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TileEntity createAndLoadEntity(NBTTagCompound nbt) {
/*  92 */     TileEntity tileentity = null;
/*     */     
/*     */     try {
/*  95 */       Class<? extends TileEntity> oclass = nameToClassMap.get(nbt.getString("id"));
/*     */       
/*  97 */       if (oclass != null) {
/*  98 */         tileentity = oclass.newInstance();
/*     */       }
/* 100 */     } catch (Exception exception) {
/* 101 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 104 */     if (tileentity != null) {
/* 105 */       tileentity.readFromNBT(nbt);
/*     */     } else {
/* 107 */       logger.warn("Skipping BlockEntity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 110 */     return tileentity;
/*     */   }
/*     */   
/*     */   public int getBlockMetadata() {
/* 114 */     if (this.blockMetadata == -1) {
/* 115 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 116 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */     } 
/*     */     
/* 119 */     return this.blockMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 127 */     if (this.worldObj != null) {
/* 128 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 129 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 130 */       this.worldObj.markChunkDirty(this.pos, this);
/*     */       
/* 132 */       if (getBlockType() != Blocks.air) {
/* 133 */         this.worldObj.updateComparatorOutputLevel(this.pos, getBlockType());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceSq(double x, double y, double z) {
/* 142 */     double d0 = this.pos.getX() + 0.5D - x;
/* 143 */     double d1 = this.pos.getY() + 0.5D - y;
/* 144 */     double d2 = this.pos.getZ() + 0.5D - z;
/* 145 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 149 */     return 4096.0D;
/*     */   }
/*     */   
/*     */   public BlockPos getPos() {
/* 153 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockType() {
/* 160 */     if (this.blockType == null) {
/* 161 */       this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
/*     */     }
/*     */     
/* 164 */     return this.blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isInvalid() {
/* 176 */     return this.tileEntityInvalid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 183 */     this.tileEntityInvalid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 190 */     this.tileEntityInvalid = false;
/*     */   }
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 198 */     this.blockType = null;
/* 199 */     this.blockMetadata = -1;
/*     */   }
/*     */   
/*     */   public void addInfoToCrashReport(CrashReportCategory reportCategory) {
/* 203 */     reportCategory.addCrashSectionCallable("Name", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 205 */             return String.valueOf(TileEntity.classToNameMap.get(TileEntity.this.getClass())) + " // " + TileEntity.this.getClass().getCanonicalName();
/*     */           }
/*     */         });
/*     */     
/* 209 */     if (this.worldObj != null) {
/* 210 */       CrashReportCategory.addBlockInfo(reportCategory, this.pos, getBlockType(), getBlockMetadata());
/* 211 */       reportCategory.addCrashSectionCallable("Actual block type", new Callable<String>() {
/*     */             public String call() throws Exception {
/* 213 */               int i = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());
/*     */               
/*     */               try {
/* 216 */                 return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(i), Block.getBlockById(i).getUnlocalizedName(), Block.getBlockById(i).getClass().getCanonicalName() });
/* 217 */               } catch (Throwable var3) {
/* 218 */                 return "ID #" + i;
/*     */               } 
/*     */             }
/*     */           });
/* 222 */       reportCategory.addCrashSectionCallable("Actual block data value", new Callable<String>() {
/*     */             public String call() throws Exception {
/* 224 */               IBlockState iblockstate = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
/* 225 */               int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */               
/* 227 */               if (i < 0) {
/* 228 */                 return "Unknown? (Got " + i + ")";
/*     */               }
/* 230 */               String s = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
/* 231 */               return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), s });
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPos(BlockPos posIn) {
/* 239 */     this.pos = posIn;
/*     */   }
/*     */   
/*     */   public boolean func_183000_F() {
/* 243 */     return false;
/*     */   }
/*     */   
/*     */   static {
/* 247 */     addMapping((Class)TileEntityFurnace.class, "Furnace");
/* 248 */     addMapping((Class)TileEntityChest.class, "Chest");
/* 249 */     addMapping((Class)TileEntityEnderChest.class, "EnderChest");
/* 250 */     addMapping((Class)BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
/* 251 */     addMapping((Class)TileEntityDispenser.class, "Trap");
/* 252 */     addMapping((Class)TileEntityDropper.class, "Dropper");
/* 253 */     addMapping((Class)TileEntitySign.class, "Sign");
/* 254 */     addMapping((Class)TileEntityMobSpawner.class, "MobSpawner");
/* 255 */     addMapping((Class)TileEntityNote.class, "Music");
/* 256 */     addMapping((Class)TileEntityPiston.class, "Piston");
/* 257 */     addMapping((Class)TileEntityBrewingStand.class, "Cauldron");
/* 258 */     addMapping((Class)TileEntityEnchantmentTable.class, "EnchantTable");
/* 259 */     addMapping((Class)TileEntityEndPortal.class, "Airportal");
/* 260 */     addMapping((Class)TileEntityCommandBlock.class, "Control");
/* 261 */     addMapping((Class)TileEntityBeacon.class, "Beacon");
/* 262 */     addMapping((Class)TileEntitySkull.class, "Skull");
/* 263 */     addMapping((Class)TileEntityDaylightDetector.class, "DLDetector");
/* 264 */     addMapping((Class)TileEntityHopper.class, "Hopper");
/* 265 */     addMapping((Class)TileEntityComparator.class, "Comparator");
/* 266 */     addMapping((Class)TileEntityFlowerPot.class, "FlowerPot");
/* 267 */     addMapping((Class)TileEntityBanner.class, "Banner");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */