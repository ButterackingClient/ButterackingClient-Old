/*     */ package net.minecraft.init;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.BlockPumpkin;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.BlockTNT;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBucket;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.LoggingPrintStream;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bootstrap
/*     */ {
/*  58 */   private static final PrintStream SYSOUT = System.out;
/*     */ 
/*     */   
/*     */   private static boolean alreadyRegistered = false;
/*     */ 
/*     */   
/*  64 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegistered() {
/*  70 */     return alreadyRegistered;
/*     */   }
/*     */   
/*     */   static void registerDispenserBehaviors() {
/*  74 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense() {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position) {
/*  76 */             EntityArrow entityarrow = new EntityArrow(worldIn, position.getX(), position.getY(), position.getZ());
/*  77 */             entityarrow.canBePickedUp = 1;
/*  78 */             return (IProjectile)entityarrow;
/*     */           }
/*     */         });
/*  81 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense() {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position) {
/*  83 */             return (IProjectile)new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/*  86 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense() {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position) {
/*  88 */             return (IProjectile)new EntitySnowball(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/*  91 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense() {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position) {
/*  93 */             return (IProjectile)new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */           
/*     */           protected float func_82498_a() {
/*  97 */             return super.func_82498_a() * 0.5F;
/*     */           }
/*     */           
/*     */           protected float func_82500_b() {
/* 101 */             return super.func_82500_b() * 1.25F;
/*     */           }
/*     */         });
/* 104 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem() {
/* 105 */           private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
/*     */           
/*     */           public ItemStack dispense(IBlockSource source, final ItemStack stack) {
/* 108 */             return ItemPotion.isSplash(stack.getMetadata()) ? (new BehaviorProjectileDispense() {
/*     */                 protected IProjectile getProjectileEntity(World worldIn, IPosition position) {
/* 110 */                   return (IProjectile)new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
/*     */                 }
/*     */                 
/*     */                 protected float func_82498_a() {
/* 114 */                   return super.func_82498_a() * 0.5F;
/*     */                 }
/*     */                 
/*     */                 protected float func_82500_b() {
/* 118 */                   return super.func_82500_b() * 1.25F;
/*     */                 }
/* 120 */               }).dispense(source, stack) : this.field_150843_b.dispense(source, stack);
/*     */           }
/*     */         });
/* 123 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem() {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 125 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 126 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 127 */             double d1 = (source.getBlockPos().getY() + 0.2F);
/* 128 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 129 */             Entity entity = ItemMonsterPlacer.spawnCreature(source.getWorld(), stack.getMetadata(), d0, d1, d2);
/*     */             
/* 131 */             if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName()) {
/* 132 */               ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
/*     */             }
/*     */             
/* 135 */             stack.splitStack(1);
/* 136 */             return stack;
/*     */           }
/*     */         });
/* 139 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem() {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 141 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 142 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 143 */             double d1 = (source.getBlockPos().getY() + 0.2F);
/* 144 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 145 */             EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(source.getWorld(), d0, d1, d2, stack);
/* 146 */             source.getWorld().spawnEntityInWorld((Entity)entityfireworkrocket);
/* 147 */             stack.splitStack(1);
/* 148 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 152 */             source.getWorld().playAuxSFX(1002, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 155 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem() {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 157 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 158 */             IPosition iposition = BlockDispenser.getDispensePosition(source);
/* 159 */             double d0 = iposition.getX() + (enumfacing.getFrontOffsetX() * 0.3F);
/* 160 */             double d1 = iposition.getY() + (enumfacing.getFrontOffsetY() * 0.3F);
/* 161 */             double d2 = iposition.getZ() + (enumfacing.getFrontOffsetZ() * 0.3F);
/* 162 */             World world = source.getWorld();
/* 163 */             Random random = world.rand;
/* 164 */             double d3 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetX();
/* 165 */             double d4 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetY();
/* 166 */             double d5 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetZ();
/* 167 */             world.spawnEntityInWorld((Entity)new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
/* 168 */             stack.splitStack(1);
/* 169 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 173 */             source.getWorld().playAuxSFX(1009, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 176 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem() {
/* 177 */           private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */             double d3;
/* 180 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 181 */             World world = source.getWorld();
/* 182 */             double d0 = source.getX() + (enumfacing.getFrontOffsetX() * 1.125F);
/* 183 */             double d1 = source.getY() + (enumfacing.getFrontOffsetY() * 1.125F);
/* 184 */             double d2 = source.getZ() + (enumfacing.getFrontOffsetZ() * 1.125F);
/* 185 */             BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 186 */             Material material = world.getBlockState(blockpos).getBlock().getMaterial();
/*     */ 
/*     */             
/* 189 */             if (Material.water.equals(material)) {
/* 190 */               d3 = 1.0D;
/*     */             } else {
/* 192 */               if (!Material.air.equals(material) || !Material.water.equals(world.getBlockState(blockpos.down()).getBlock().getMaterial())) {
/* 193 */                 return this.field_150842_b.dispense(source, stack);
/*     */               }
/*     */               
/* 196 */               d3 = 0.0D;
/*     */             } 
/*     */             
/* 199 */             EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
/* 200 */             world.spawnEntityInWorld((Entity)entityboat);
/* 201 */             stack.splitStack(1);
/* 202 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 206 */             source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 209 */     BehaviorDefaultDispenseItem behaviorDefaultDispenseItem = new BehaviorDefaultDispenseItem() {
/* 210 */         private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
/*     */         
/*     */         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 213 */           ItemBucket itembucket = (ItemBucket)stack.getItem();
/* 214 */           BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */           
/* 216 */           if (itembucket.tryPlaceContainedLiquid(source.getWorld(), blockpos)) {
/* 217 */             stack.setItem(Items.bucket);
/* 218 */             stack.stackSize = 1;
/* 219 */             return stack;
/*     */           } 
/* 221 */           return this.field_150841_b.dispense(source, stack);
/*     */         }
/*     */       };
/*     */     
/* 225 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, behaviorDefaultDispenseItem);
/* 226 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, behaviorDefaultDispenseItem);
/* 227 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
/* 228 */           private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */             Item item;
/* 231 */             World world = source.getWorld();
/* 232 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 233 */             IBlockState iblockstate = world.getBlockState(blockpos);
/* 234 */             Block block = iblockstate.getBlock();
/* 235 */             Material material = block.getMaterial();
/*     */ 
/*     */             
/* 238 */             if (Material.water.equals(material) && block instanceof BlockLiquid && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/* 239 */               item = Items.water_bucket;
/*     */             } else {
/* 241 */               if (!Material.lava.equals(material) || !(block instanceof BlockLiquid) || ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() != 0) {
/* 242 */                 return super.dispenseStack(source, stack);
/*     */               }
/*     */               
/* 245 */               item = Items.lava_bucket;
/*     */             } 
/*     */             
/* 248 */             world.setBlockToAir(blockpos);
/*     */             
/* 250 */             if (--stack.stackSize == 0) {
/* 251 */               stack.setItem(item);
/* 252 */               stack.stackSize = 1;
/* 253 */             } else if (((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
/* 254 */               this.field_150840_b.dispense(source, new ItemStack(item));
/*     */             } 
/*     */             
/* 257 */             return stack;
/*     */           }
/*     */         });
/* 260 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem() {
/*     */           private boolean field_150839_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 264 */             World world = source.getWorld();
/* 265 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */             
/* 267 */             if (world.isAirBlock(blockpos)) {
/* 268 */               world.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*     */               
/* 270 */               if (stack.attemptDamageItem(1, world.rand)) {
/* 271 */                 stack.stackSize = 0;
/*     */               }
/* 273 */             } else if (world.getBlockState(blockpos).getBlock() == Blocks.tnt) {
/* 274 */               Blocks.tnt.onBlockDestroyedByPlayer(world, blockpos, Blocks.tnt.getDefaultState().withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true)));
/* 275 */               world.setBlockToAir(blockpos);
/*     */             } else {
/* 277 */               this.field_150839_b = false;
/*     */             } 
/*     */             
/* 280 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 284 */             if (this.field_150839_b) {
/* 285 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             } else {
/* 287 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 291 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem() {
/*     */           private boolean field_150838_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 295 */             if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(stack.getMetadata())) {
/* 296 */               World world = source.getWorld();
/* 297 */               BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */               
/* 299 */               if (ItemDye.applyBonemeal(stack, world, blockpos)) {
/* 300 */                 if (!world.isRemote) {
/* 301 */                   world.playAuxSFX(2005, blockpos, 0);
/*     */                 }
/*     */               } else {
/* 304 */                 this.field_150838_b = false;
/*     */               } 
/*     */               
/* 307 */               return stack;
/*     */             } 
/* 309 */             return super.dispenseStack(source, stack);
/*     */           }
/*     */ 
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 314 */             if (this.field_150838_b) {
/* 315 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             } else {
/* 317 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 321 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem() {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 323 */             World world = source.getWorld();
/* 324 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 325 */             EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, null);
/* 326 */             world.spawnEntityInWorld((Entity)entitytntprimed);
/* 327 */             world.playSoundAtEntity((Entity)entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/* 328 */             stack.stackSize--;
/* 329 */             return stack;
/*     */           }
/*     */         });
/* 332 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem() {
/*     */           private boolean field_179240_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 336 */             World world = source.getWorld();
/* 337 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 338 */             BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 339 */             BlockSkull blockskull = Blocks.skull;
/*     */             
/* 341 */             if (world.isAirBlock(blockpos) && blockskull.canDispenserPlace(world, blockpos, stack)) {
/* 342 */               if (!world.isRemote) {
/* 343 */                 world.setBlockState(blockpos, blockskull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)EnumFacing.UP), 3);
/* 344 */                 TileEntity tileentity = world.getTileEntity(blockpos);
/*     */                 
/* 346 */                 if (tileentity instanceof TileEntitySkull) {
/* 347 */                   if (stack.getMetadata() == 3) {
/* 348 */                     GameProfile gameprofile = null;
/*     */                     
/* 350 */                     if (stack.hasTagCompound()) {
/* 351 */                       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */                       
/* 353 */                       if (nbttagcompound.hasKey("SkullOwner", 10)) {
/* 354 */                         gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/* 355 */                       } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/* 356 */                         String s = nbttagcompound.getString("SkullOwner");
/*     */                         
/* 358 */                         if (!StringUtils.isNullOrEmpty(s)) {
/* 359 */                           gameprofile = new GameProfile(null, s);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                     
/* 364 */                     ((TileEntitySkull)tileentity).setPlayerProfile(gameprofile);
/*     */                   } else {
/* 366 */                     ((TileEntitySkull)tileentity).setType(stack.getMetadata());
/*     */                   } 
/*     */                   
/* 369 */                   ((TileEntitySkull)tileentity).setSkullRotation(enumfacing.getOpposite().getHorizontalIndex() * 4);
/* 370 */                   Blocks.skull.checkWitherSpawn(world, blockpos, (TileEntitySkull)tileentity);
/*     */                 } 
/*     */                 
/* 373 */                 stack.stackSize--;
/*     */               } 
/*     */             } else {
/* 376 */               this.field_179240_b = false;
/*     */             } 
/*     */             
/* 379 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 383 */             if (this.field_179240_b) {
/* 384 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             } else {
/* 386 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 390 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem() {
/*     */           private boolean field_179241_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 394 */             World world = source.getWorld();
/* 395 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 396 */             BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.pumpkin;
/*     */             
/* 398 */             if (world.isAirBlock(blockpos) && blockpumpkin.canDispenserPlace(world, blockpos)) {
/* 399 */               if (!world.isRemote) {
/* 400 */                 world.setBlockState(blockpos, blockpumpkin.getDefaultState(), 3);
/*     */               }
/*     */               
/* 403 */               stack.stackSize--;
/*     */             } else {
/* 405 */               this.field_179241_b = false;
/*     */             } 
/*     */             
/* 408 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 412 */             if (this.field_179241_b) {
/* 413 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             } else {
/* 415 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() {
/* 425 */     if (!alreadyRegistered) {
/* 426 */       alreadyRegistered = true;
/*     */       
/* 428 */       if (LOGGER.isDebugEnabled()) {
/* 429 */         redirectOutputToLog();
/*     */       }
/*     */       
/* 432 */       Block.registerBlocks();
/* 433 */       BlockFire.init();
/* 434 */       Item.registerItems();
/* 435 */       StatList.init();
/* 436 */       registerDispenserBehaviors();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void redirectOutputToLog() {
/* 444 */     System.setErr((PrintStream)new LoggingPrintStream("STDERR", System.err));
/* 445 */     System.setOut((PrintStream)new LoggingPrintStream("STDOUT", SYSOUT));
/*     */   }
/*     */   
/*     */   public static void printToSYSOUT(String p_179870_0_) {
/* 449 */     SYSOUT.println(p_179870_0_);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\init\Bootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */