/*      */ package net.minecraft.block;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockState;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ObjectIntIdentityMap;
/*      */ import net.minecraft.util.RegistryNamespacedDefaultedByKey;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Block
/*      */ {
/*   41 */   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
/*   42 */   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry = new RegistryNamespacedDefaultedByKey(AIR_ID);
/*   43 */   public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap();
/*      */   private CreativeTabs displayOnCreativeTab;
/*   45 */   public static final SoundType soundTypeStone = new SoundType("stone", 1.0F, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   50 */   public static final SoundType soundTypeWood = new SoundType("wood", 1.0F, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   public static final SoundType soundTypeGravel = new SoundType("gravel", 1.0F, 1.0F);
/*   56 */   public static final SoundType soundTypeGrass = new SoundType("grass", 1.0F, 1.0F);
/*   57 */   public static final SoundType soundTypePiston = new SoundType("stone", 1.0F, 1.0F);
/*   58 */   public static final SoundType soundTypeMetal = new SoundType("stone", 1.0F, 1.5F);
/*   59 */   public static final SoundType soundTypeGlass = new SoundType("stone", 1.0F, 1.0F) {
/*      */       public String getBreakSound() {
/*   61 */         return "dig.glass";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   65 */         return "step.stone";
/*      */       }
/*      */     };
/*   68 */   public static final SoundType soundTypeCloth = new SoundType("cloth", 1.0F, 1.0F);
/*   69 */   public static final SoundType soundTypeSand = new SoundType("sand", 1.0F, 1.0F);
/*   70 */   public static final SoundType soundTypeSnow = new SoundType("snow", 1.0F, 1.0F);
/*   71 */   public static final SoundType soundTypeLadder = new SoundType("ladder", 1.0F, 1.0F) {
/*      */       public String getBreakSound() {
/*   73 */         return "dig.wood";
/*      */       }
/*      */     };
/*   76 */   public static final SoundType soundTypeAnvil = new SoundType("anvil", 0.3F, 1.0F) {
/*      */       public String getBreakSound() {
/*   78 */         return "dig.stone";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   82 */         return "random.anvil_land";
/*      */       }
/*      */     };
/*   85 */   public static final SoundType SLIME_SOUND = new SoundType("slime", 1.0F, 1.0F) {
/*      */       public String getBreakSound() {
/*   87 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   91 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getStepSound() {
/*   95 */         return "mob.slime.small";
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fullBlock;
/*      */ 
/*      */   
/*      */   protected int lightOpacity;
/*      */ 
/*      */   
/*      */   protected boolean translucent;
/*      */ 
/*      */   
/*      */   protected int lightValue;
/*      */ 
/*      */   
/*      */   protected boolean useNeighborBrightness;
/*      */ 
/*      */   
/*      */   protected float blockHardness;
/*      */ 
/*      */   
/*      */   protected float blockResistance;
/*      */ 
/*      */   
/*      */   protected boolean enableStats;
/*      */ 
/*      */   
/*      */   protected boolean needsRandomTick;
/*      */ 
/*      */   
/*      */   protected boolean isBlockContainer;
/*      */ 
/*      */   
/*      */   protected double minX;
/*      */ 
/*      */   
/*      */   protected double minY;
/*      */ 
/*      */   
/*      */   protected double minZ;
/*      */ 
/*      */   
/*      */   protected double maxX;
/*      */ 
/*      */   
/*      */   protected double maxY;
/*      */   
/*      */   protected double maxZ;
/*      */   
/*      */   public SoundType stepSound;
/*      */   
/*      */   public float blockParticleGravity;
/*      */   
/*      */   protected final Material blockMaterial;
/*      */   
/*      */   protected final MapColor blockMapColor;
/*      */   
/*      */   public float slipperiness;
/*      */   
/*      */   protected final BlockState blockState;
/*      */   
/*      */   private IBlockState defaultBlockState;
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */   
/*      */   public static int getIdFromBlock(Block blockIn) {
/*  165 */     return blockRegistry.getIDForObject(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStateId(IBlockState state) {
/*  172 */     Block block = state.getBlock();
/*  173 */     return getIdFromBlock(block) + (block.getMetaFromState(state) << 12);
/*      */   }
/*      */   
/*      */   public static Block getBlockById(int id) {
/*  177 */     return (Block)blockRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IBlockState getStateById(int id) {
/*  184 */     int i = id & 0xFFF;
/*  185 */     int j = id >> 12 & 0xF;
/*  186 */     return getBlockById(i).getStateFromMeta(j);
/*      */   }
/*      */   
/*      */   public static Block getBlockFromItem(Item itemIn) {
/*  190 */     return (itemIn instanceof ItemBlock) ? ((ItemBlock)itemIn).getBlock() : null;
/*      */   }
/*      */   
/*      */   public static Block getBlockFromName(String name) {
/*  194 */     ResourceLocation resourcelocation = new ResourceLocation(name);
/*      */     
/*  196 */     if (blockRegistry.containsKey(resourcelocation)) {
/*  197 */       return (Block)blockRegistry.getObject(resourcelocation);
/*      */     }
/*      */     try {
/*  200 */       return (Block)blockRegistry.getObjectById(Integer.parseInt(name));
/*  201 */     } catch (NumberFormatException var3) {
/*  202 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFullBlock() {
/*  208 */     return this.fullBlock;
/*      */   }
/*      */   
/*      */   public int getLightOpacity() {
/*  212 */     return this.lightOpacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTranslucent() {
/*  219 */     return this.translucent;
/*      */   }
/*      */   
/*      */   public int getLightValue() {
/*  223 */     return this.lightValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseNeighborBrightness() {
/*  230 */     return this.useNeighborBrightness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Material getMaterial() {
/*  237 */     return this.blockMaterial;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapColor getMapColor(IBlockState state) {
/*  244 */     return this.blockMapColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateFromMeta(int meta) {
/*  251 */     return getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetaFromState(IBlockState state) {
/*  258 */     if (state != null && !state.getPropertyNames().isEmpty()) {
/*  259 */       throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
/*      */     }
/*  261 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  270 */     return state;
/*      */   }
/*      */   
/*      */   public Block(Material blockMaterialIn, MapColor blockMapColorIn) {
/*  274 */     this.enableStats = true;
/*  275 */     this.stepSound = soundTypeStone;
/*  276 */     this.blockParticleGravity = 1.0F;
/*  277 */     this.slipperiness = 0.6F;
/*  278 */     this.blockMaterial = blockMaterialIn;
/*  279 */     this.blockMapColor = blockMapColorIn;
/*  280 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  281 */     this.fullBlock = isOpaqueCube();
/*  282 */     this.lightOpacity = isOpaqueCube() ? 255 : 0;
/*  283 */     this.translucent = !blockMaterialIn.blocksLight();
/*  284 */     this.blockState = createBlockState();
/*  285 */     setDefaultState(this.blockState.getBaseState());
/*      */   }
/*      */   
/*      */   protected Block(Material materialIn) {
/*  289 */     this(materialIn, materialIn.getMaterialMapColor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setStepSound(SoundType sound) {
/*  296 */     this.stepSound = sound;
/*  297 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightOpacity(int opacity) {
/*  304 */     this.lightOpacity = opacity;
/*  305 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightLevel(float value) {
/*  313 */     this.lightValue = (int)(15.0F * value);
/*  314 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setResistance(float resistance) {
/*  321 */     this.blockResistance = resistance * 3.0F;
/*  322 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube() {
/*  329 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNormalCube() {
/*  337 */     return (this.blockMaterial.isOpaque() && isFullCube() && !canProvidePower());
/*      */   }
/*      */   
/*      */   public boolean isVisuallyOpaque() {
/*  341 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */   
/*      */   public boolean isFullCube() {
/*  345 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  349 */     return !this.blockMaterial.blocksMovement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRenderType() {
/*  356 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  363 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setHardness(float hardness) {
/*  370 */     this.blockHardness = hardness;
/*      */     
/*  372 */     if (this.blockResistance < hardness * 5.0F) {
/*  373 */       this.blockResistance = hardness * 5.0F;
/*      */     }
/*      */     
/*  376 */     return this;
/*      */   }
/*      */   
/*      */   protected Block setBlockUnbreakable() {
/*  380 */     setHardness(-1.0F);
/*  381 */     return this;
/*      */   }
/*      */   
/*      */   public float getBlockHardness(World worldIn, BlockPos pos) {
/*  385 */     return this.blockHardness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setTickRandomly(boolean shouldTick) {
/*  392 */     this.needsRandomTick = shouldTick;
/*  393 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickRandomly() {
/*  401 */     return this.needsRandomTick;
/*      */   }
/*      */   
/*      */   public boolean hasTileEntity() {
/*  405 */     return this.isBlockContainer;
/*      */   }
/*      */   
/*      */   protected final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
/*  409 */     this.minX = minX;
/*  410 */     this.minY = minY;
/*  411 */     this.minZ = minZ;
/*  412 */     this.maxX = maxX;
/*  413 */     this.maxY = maxY;
/*  414 */     this.maxZ = maxZ;
/*      */   }
/*      */   
/*      */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/*  418 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  419 */     int i = worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     
/*  421 */     if (i == 0 && block instanceof BlockSlab) {
/*  422 */       pos = pos.down();
/*  423 */       block = worldIn.getBlockState(pos).getBlock();
/*  424 */       return worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     } 
/*  426 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  431 */     return (side == EnumFacing.DOWN && this.minY > 0.0D) ? true : ((side == EnumFacing.UP && this.maxY < 1.0D) ? true : ((side == EnumFacing.NORTH && this.minZ > 0.0D) ? true : ((side == EnumFacing.SOUTH && this.maxZ < 1.0D) ? true : ((side == EnumFacing.WEST && this.minX > 0.0D) ? true : ((side == EnumFacing.EAST && this.maxX < 1.0D) ? true : (!worldIn.getBlockState(pos).getBlock().isOpaqueCube()))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  438 */     return worldIn.getBlockState(pos).getBlock().getMaterial().isSolid();
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  442 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  449 */     AxisAlignedBB axisalignedbb = getCollisionBoundingBox(worldIn, pos, state);
/*      */     
/*  451 */     if (axisalignedbb != null && mask.intersectsWith(axisalignedbb)) {
/*  452 */       list.add(axisalignedbb);
/*      */     }
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  457 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOpaqueCube() {
/*  464 */     return true;
/*      */   }
/*      */   
/*      */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  468 */     return isCollidable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCollidable() {
/*  475 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
/*  482 */     updateTick(worldIn, pos, state, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int tickRate(World worldIn) {
/*  507 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */   
/*      */   public int quantityDropped(Random random) {
/*  520 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  527 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
/*  534 */     float f = getBlockHardness(worldIn, pos);
/*  535 */     return (f < 0.0F) ? 0.0F : (!playerIn.canHarvestBlock(this) ? (playerIn.getToolDigEfficiency(this) / f / 100.0F) : (playerIn.getToolDigEfficiency(this) / f / 30.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture) {
/*  542 */     dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, forture);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  549 */     if (!worldIn.isRemote) {
/*  550 */       int i = quantityDroppedWithBonus(fortune, worldIn.rand);
/*      */       
/*  552 */       for (int j = 0; j < i; j++) {
/*  553 */         if (worldIn.rand.nextFloat() <= chance) {
/*  554 */           Item item = getItemDropped(state, worldIn.rand, fortune);
/*      */           
/*  556 */           if (item != null) {
/*  557 */             spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
/*  568 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops")) {
/*  569 */       float f = 0.5F;
/*  570 */       double d0 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  571 */       double d1 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  572 */       double d2 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  573 */       EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
/*  574 */       entityitem.setDefaultPickupDelay();
/*  575 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
/*  583 */     if (!worldIn.isRemote) {
/*  584 */       while (amount > 0) {
/*  585 */         int i = EntityXPOrb.getXPSplit(amount);
/*  586 */         amount -= i;
/*  587 */         worldIn.spawnEntityInWorld((Entity)new EntityXPOrb(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, i));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int damageDropped(IBlockState state) {
/*  597 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Entity exploder) {
/*  604 */     return this.blockResistance / 5.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  611 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  612 */     start = start.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  613 */     end = end.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  614 */     Vec3 vec3 = start.getIntermediateWithXValue(end, this.minX);
/*  615 */     Vec3 vec31 = start.getIntermediateWithXValue(end, this.maxX);
/*  616 */     Vec3 vec32 = start.getIntermediateWithYValue(end, this.minY);
/*  617 */     Vec3 vec33 = start.getIntermediateWithYValue(end, this.maxY);
/*  618 */     Vec3 vec34 = start.getIntermediateWithZValue(end, this.minZ);
/*  619 */     Vec3 vec35 = start.getIntermediateWithZValue(end, this.maxZ);
/*      */     
/*  621 */     if (!isVecInsideYZBounds(vec3)) {
/*  622 */       vec3 = null;
/*      */     }
/*      */     
/*  625 */     if (!isVecInsideYZBounds(vec31)) {
/*  626 */       vec31 = null;
/*      */     }
/*      */     
/*  629 */     if (!isVecInsideXZBounds(vec32)) {
/*  630 */       vec32 = null;
/*      */     }
/*      */     
/*  633 */     if (!isVecInsideXZBounds(vec33)) {
/*  634 */       vec33 = null;
/*      */     }
/*      */     
/*  637 */     if (!isVecInsideXYBounds(vec34)) {
/*  638 */       vec34 = null;
/*      */     }
/*      */     
/*  641 */     if (!isVecInsideXYBounds(vec35)) {
/*  642 */       vec35 = null;
/*      */     }
/*      */     
/*  645 */     Vec3 vec36 = null;
/*      */     
/*  647 */     if (vec3 != null && (vec36 == null || start.squareDistanceTo(vec3) < start.squareDistanceTo(vec36))) {
/*  648 */       vec36 = vec3;
/*      */     }
/*      */     
/*  651 */     if (vec31 != null && (vec36 == null || start.squareDistanceTo(vec31) < start.squareDistanceTo(vec36))) {
/*  652 */       vec36 = vec31;
/*      */     }
/*      */     
/*  655 */     if (vec32 != null && (vec36 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec36))) {
/*  656 */       vec36 = vec32;
/*      */     }
/*      */     
/*  659 */     if (vec33 != null && (vec36 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec36))) {
/*  660 */       vec36 = vec33;
/*      */     }
/*      */     
/*  663 */     if (vec34 != null && (vec36 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec36))) {
/*  664 */       vec36 = vec34;
/*      */     }
/*      */     
/*  667 */     if (vec35 != null && (vec36 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec36))) {
/*  668 */       vec36 = vec35;
/*      */     }
/*      */     
/*  671 */     if (vec36 == null) {
/*  672 */       return null;
/*      */     }
/*  674 */     EnumFacing enumfacing = null;
/*      */     
/*  676 */     if (vec36 == vec3) {
/*  677 */       enumfacing = EnumFacing.WEST;
/*      */     }
/*      */     
/*  680 */     if (vec36 == vec31) {
/*  681 */       enumfacing = EnumFacing.EAST;
/*      */     }
/*      */     
/*  684 */     if (vec36 == vec32) {
/*  685 */       enumfacing = EnumFacing.DOWN;
/*      */     }
/*      */     
/*  688 */     if (vec36 == vec33) {
/*  689 */       enumfacing = EnumFacing.UP;
/*      */     }
/*      */     
/*  692 */     if (vec36 == vec34) {
/*  693 */       enumfacing = EnumFacing.NORTH;
/*      */     }
/*      */     
/*  696 */     if (vec36 == vec35) {
/*  697 */       enumfacing = EnumFacing.SOUTH;
/*      */     }
/*      */     
/*  700 */     return new MovingObjectPosition(vec36.addVector(pos.getX(), pos.getY(), pos.getZ()), enumfacing, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideYZBounds(Vec3 point) {
/*  708 */     return (point == null) ? false : ((point.yCoord >= this.minY && point.yCoord <= this.maxY && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXZBounds(Vec3 point) {
/*  715 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXYBounds(Vec3 point) {
/*  722 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.yCoord >= this.minY && point.yCoord <= this.maxY));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer getBlockLayer() {
/*  732 */     return EnumWorldBlockLayer.SOLID;
/*      */   }
/*      */   
/*      */   public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  736 */     return canPlaceBlockOnSide(worldIn, pos, side);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  743 */     return canPlaceBlockAt(worldIn, pos);
/*      */   }
/*      */   
/*      */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  747 */     return (worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable();
/*      */   }
/*      */   
/*      */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  751 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  765 */     return getStateFromMeta(meta);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {}
/*      */   
/*      */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/*  772 */     return motion;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinX() {
/*  782 */     return this.minX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxX() {
/*  789 */     return this.maxX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinY() {
/*  796 */     return this.minY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxY() {
/*  803 */     return this.maxY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinZ() {
/*  810 */     return this.minZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxZ() {
/*  817 */     return this.maxZ;
/*      */   }
/*      */   
/*      */   public int getBlockColor() {
/*  821 */     return 16777215;
/*      */   }
/*      */   
/*      */   public int getRenderColor(IBlockState state) {
/*  825 */     return 16777215;
/*      */   }
/*      */   
/*      */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  829 */     return 16777215;
/*      */   }
/*      */   
/*      */   public final int colorMultiplier(IBlockAccess worldIn, BlockPos pos) {
/*  833 */     return colorMultiplier(worldIn, pos, 0);
/*      */   }
/*      */   
/*      */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  837 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canProvidePower() {
/*  844 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  854 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsForItemRender() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/*  864 */     player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
/*  865 */     player.addExhaustion(0.025F);
/*      */     
/*  867 */     if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier((EntityLivingBase)player)) {
/*  868 */       ItemStack itemstack = createStackedBlock(state);
/*      */       
/*  870 */       if (itemstack != null) {
/*  871 */         spawnAsEntity(worldIn, pos, itemstack);
/*      */       }
/*      */     } else {
/*  874 */       int i = EnchantmentHelper.getFortuneModifier((EntityLivingBase)player);
/*  875 */       dropBlockAsItem(worldIn, pos, state, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean canSilkHarvest() {
/*  880 */     return (isFullCube() && !this.isBlockContainer);
/*      */   }
/*      */   
/*      */   protected ItemStack createStackedBlock(IBlockState state) {
/*  884 */     int i = 0;
/*  885 */     Item item = Item.getItemFromBlock(this);
/*      */     
/*  887 */     if (item != null && item.getHasSubtypes()) {
/*  888 */       i = getMetaFromState(state);
/*      */     }
/*      */     
/*  891 */     return new ItemStack(item, 1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  898 */     return quantityDropped(random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSpawnInBlock() {
/*  911 */     return (!this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid());
/*      */   }
/*      */   
/*      */   public Block setUnlocalizedName(String name) {
/*  915 */     this.unlocalizedName = name;
/*  916 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedName() {
/*  923 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  930 */     return "tile." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/*  937 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableStats() {
/*  944 */     return this.enableStats;
/*      */   }
/*      */   
/*      */   protected Block disableStats() {
/*  948 */     this.enableStats = false;
/*  949 */     return this;
/*      */   }
/*      */   
/*      */   public int getMobilityFlag() {
/*  953 */     return this.blockMaterial.getMaterialMobility();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAmbientOcclusionLightValue() {
/*  960 */     return isBlockNormalCube() ? 0.2F : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/*  967 */     entityIn.fall(fallDistance, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLanded(World worldIn, Entity entityIn) {
/*  975 */     entityIn.motionY = 0.0D;
/*      */   }
/*      */   
/*      */   public Item getItem(World worldIn, BlockPos pos) {
/*  979 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  986 */     return damageDropped(worldIn.getBlockState(pos));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  993 */     list.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTabToDisplayOn() {
/* 1000 */     return this.displayOnCreativeTab;
/*      */   }
/*      */   
/*      */   public Block setCreativeTab(CreativeTabs tab) {
/* 1004 */     this.displayOnCreativeTab = tab;
/* 1005 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillWithRain(World worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlowerPot() {
/* 1021 */     return false;
/*      */   }
/*      */   
/*      */   public boolean requiresUpdates() {
/* 1025 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 1032 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isAssociatedBlock(Block other) {
/* 1036 */     return (this == other);
/*      */   }
/*      */   
/*      */   public static boolean isEqualTo(Block blockIn, Block other) {
/* 1040 */     return (blockIn != null && other != null) ? ((blockIn == other) ? true : blockIn.isAssociatedBlock(other)) : false;
/*      */   }
/*      */   
/*      */   public boolean hasComparatorInputOverride() {
/* 1044 */     return false;
/*      */   }
/*      */   
/*      */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 1048 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 1055 */     return state;
/*      */   }
/*      */   
/*      */   protected BlockState createBlockState() {
/* 1059 */     return new BlockState(this, new IProperty[0]);
/*      */   }
/*      */   
/*      */   public BlockState getBlockState() {
/* 1063 */     return this.blockState;
/*      */   }
/*      */   
/*      */   protected final void setDefaultState(IBlockState state) {
/* 1067 */     this.defaultBlockState = state;
/*      */   }
/*      */   
/*      */   public final IBlockState getDefaultState() {
/* 1071 */     return this.defaultBlockState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumOffsetType getOffsetType() {
/* 1078 */     return EnumOffsetType.NONE;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1082 */     return "Block{" + blockRegistry.getNameForObject(this) + "}";
/*      */   }
/*      */   
/*      */   public static void registerBlocks() {
/* 1086 */     registerBlock(0, AIR_ID, (new BlockAir()).setUnlocalizedName("air"));
/* 1087 */     registerBlock(1, "stone", (new BlockStone()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
/* 1088 */     registerBlock(2, "grass", (new BlockGrass()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
/* 1089 */     registerBlock(3, "dirt", (new BlockDirt()).setHardness(0.5F).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
/* 1090 */     Block block = (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1091 */     registerBlock(4, "cobblestone", block);
/* 1092 */     Block block1 = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("wood");
/* 1093 */     registerBlock(5, "planks", block1);
/* 1094 */     registerBlock(6, "sapling", (new BlockSapling()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
/* 1095 */     registerBlock(7, "bedrock", (new Block(Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
/* 1096 */     registerBlock(8, "flowing_water", (new BlockDynamicLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1097 */     registerBlock(9, "water", (new BlockStaticLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1098 */     registerBlock(10, "flowing_lava", (new BlockDynamicLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1099 */     registerBlock(11, "lava", (new BlockStaticLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1100 */     registerBlock(12, "sand", (new BlockSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
/* 1101 */     registerBlock(13, "gravel", (new BlockGravel()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
/* 1102 */     registerBlock(14, "gold_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
/* 1103 */     registerBlock(15, "iron_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
/* 1104 */     registerBlock(16, "coal_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
/* 1105 */     registerBlock(17, "log", (new BlockOldLog()).setUnlocalizedName("log"));
/* 1106 */     registerBlock(18, "leaves", (new BlockOldLeaf()).setUnlocalizedName("leaves"));
/* 1107 */     registerBlock(19, "sponge", (new BlockSponge()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
/* 1108 */     registerBlock(20, "glass", (new BlockGlass(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
/* 1109 */     registerBlock(21, "lapis_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
/* 1110 */     registerBlock(22, "lapis_block", (new Block(Material.iron, MapColor.lapisColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
/* 1111 */     registerBlock(23, "dispenser", (new BlockDispenser()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
/* 1112 */     Block block2 = (new BlockSandStone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("sandStone");
/* 1113 */     registerBlock(24, "sandstone", block2);
/* 1114 */     registerBlock(25, "noteblock", (new BlockNote()).setHardness(0.8F).setUnlocalizedName("musicBlock"));
/* 1115 */     registerBlock(26, "bed", (new BlockBed()).setStepSound(soundTypeWood).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
/* 1116 */     registerBlock(27, "golden_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
/* 1117 */     registerBlock(28, "detector_rail", (new BlockRailDetector()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
/* 1118 */     registerBlock(29, "sticky_piston", (new BlockPistonBase(true)).setUnlocalizedName("pistonStickyBase"));
/* 1119 */     registerBlock(30, "web", (new BlockWeb()).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
/* 1120 */     registerBlock(31, "tallgrass", (new BlockTallGrass()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
/* 1121 */     registerBlock(32, "deadbush", (new BlockDeadBush()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
/* 1122 */     registerBlock(33, "piston", (new BlockPistonBase(false)).setUnlocalizedName("pistonBase"));
/* 1123 */     registerBlock(34, "piston_head", (new BlockPistonExtension()).setUnlocalizedName("pistonBase"));
/* 1124 */     registerBlock(35, "wool", (new BlockColored(Material.cloth)).setHardness(0.8F).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
/* 1125 */     registerBlock(36, "piston_extension", new BlockPistonMoving());
/* 1126 */     registerBlock(37, "yellow_flower", (new BlockYellowFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
/* 1127 */     registerBlock(38, "red_flower", (new BlockRedFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
/* 1128 */     Block block3 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setUnlocalizedName("mushroom");
/* 1129 */     registerBlock(39, "brown_mushroom", block3);
/* 1130 */     Block block4 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
/* 1131 */     registerBlock(40, "red_mushroom", block4);
/* 1132 */     registerBlock(41, "gold_block", (new Block(Material.iron, MapColor.goldColor)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.tabBlock));
/* 1133 */     registerBlock(42, "iron_block", (new Block(Material.iron, MapColor.ironColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.tabBlock));
/* 1134 */     registerBlock(43, "double_stone_slab", (new BlockDoubleStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1135 */     registerBlock(44, "stone_slab", (new BlockHalfStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1136 */     Block block5 = (new Block(Material.rock, MapColor.redColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
/* 1137 */     registerBlock(45, "brick_block", block5);
/* 1138 */     registerBlock(46, "tnt", (new BlockTNT()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
/* 1139 */     registerBlock(47, "bookshelf", (new BlockBookshelf()).setHardness(1.5F).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
/* 1140 */     registerBlock(48, "mossy_cobblestone", (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
/* 1141 */     registerBlock(49, "obsidian", (new BlockObsidian()).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
/* 1142 */     registerBlock(50, "torch", (new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
/* 1143 */     registerBlock(51, "fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
/* 1144 */     registerBlock(52, "mob_spawner", (new BlockMobSpawner()).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
/* 1145 */     registerBlock(53, "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));
/* 1146 */     registerBlock(54, "chest", (new BlockChest(0)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
/* 1147 */     registerBlock(55, "redstone_wire", (new BlockRedstoneWire()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
/* 1148 */     registerBlock(56, "diamond_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
/* 1149 */     registerBlock(57, "diamond_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.tabBlock));
/* 1150 */     registerBlock(58, "crafting_table", (new BlockWorkbench()).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
/* 1151 */     registerBlock(59, "wheat", (new BlockCrops()).setUnlocalizedName("crops"));
/* 1152 */     Block block6 = (new BlockFarmland()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
/* 1153 */     registerBlock(60, "farmland", block6);
/* 1154 */     registerBlock(61, "furnace", (new BlockFurnace(false)).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
/* 1155 */     registerBlock(62, "lit_furnace", (new BlockFurnace(true)).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setUnlocalizedName("furnace"));
/* 1156 */     registerBlock(63, "standing_sign", (new BlockStandingSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1157 */     registerBlock(64, "wooden_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
/* 1158 */     registerBlock(65, "ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
/* 1159 */     registerBlock(66, "rail", (new BlockRail()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
/* 1160 */     registerBlock(67, "stone_stairs", (new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
/* 1161 */     registerBlock(68, "wall_sign", (new BlockWallSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1162 */     registerBlock(69, "lever", (new BlockLever()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
/* 1163 */     registerBlock(70, "stone_pressure_plate", (new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS)).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("pressurePlateStone"));
/* 1164 */     registerBlock(71, "iron_door", (new BlockDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
/* 1165 */     registerBlock(72, "wooden_pressure_plate", (new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("pressurePlateWood"));
/* 1166 */     registerBlock(73, "redstone_ore", (new BlockRedstoneOre(false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
/* 1167 */     registerBlock(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone"));
/* 1168 */     registerBlock(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
/* 1169 */     registerBlock(76, "redstone_torch", (new BlockRedstoneTorch(true)).setHardness(0.0F).setLightLevel(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
/* 1170 */     registerBlock(77, "stone_button", (new BlockButtonStone()).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("button"));
/* 1171 */     registerBlock(78, "snow_layer", (new BlockSnow()).setHardness(0.1F).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
/* 1172 */     registerBlock(79, "ice", (new BlockIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
/* 1173 */     registerBlock(80, "snow", (new BlockSnowBlock()).setHardness(0.2F).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
/* 1174 */     registerBlock(81, "cactus", (new BlockCactus()).setHardness(0.4F).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
/* 1175 */     registerBlock(82, "clay", (new BlockClay()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
/* 1176 */     registerBlock(83, "reeds", (new BlockReed()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
/* 1177 */     registerBlock(84, "jukebox", (new BlockJukebox()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
/* 1178 */     registerBlock(85, "fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fence"));
/* 1179 */     Block block7 = (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
/* 1180 */     registerBlock(86, "pumpkin", block7);
/* 1181 */     registerBlock(87, "netherrack", (new BlockNetherrack()).setHardness(0.4F).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
/* 1182 */     registerBlock(88, "soul_sand", (new BlockSoulSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
/* 1183 */     registerBlock(89, "glowstone", (new BlockGlowstone(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
/* 1184 */     registerBlock(90, "portal", (new BlockPortal()).setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setUnlocalizedName("portal"));
/* 1185 */     registerBlock(91, "lit_pumpkin", (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
/* 1186 */     registerBlock(92, "cake", (new BlockCake()).setHardness(0.5F).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
/* 1187 */     registerBlock(93, "unpowered_repeater", (new BlockRedstoneRepeater(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1188 */     registerBlock(94, "powered_repeater", (new BlockRedstoneRepeater(true)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1189 */     registerBlock(95, "stained_glass", (new BlockStainedGlass(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass"));
/* 1190 */     registerBlock(96, "trapdoor", (new BlockTrapDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
/* 1191 */     registerBlock(97, "monster_egg", (new BlockSilverfish()).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
/* 1192 */     Block block8 = (new BlockStoneBrick()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
/* 1193 */     registerBlock(98, "stonebrick", block8);
/* 1194 */     registerBlock(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.dirtColor, block3)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1195 */     registerBlock(100, "red_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.redColor, block4)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1196 */     registerBlock(101, "iron_bars", (new BlockPane(Material.iron, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron"));
/* 1197 */     registerBlock(102, "glass_pane", (new BlockPane(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
/* 1198 */     Block block9 = (new BlockMelon()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
/* 1199 */     registerBlock(103, "melon_block", block9);
/* 1200 */     registerBlock(104, "pumpkin_stem", (new BlockStem(block7)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1201 */     registerBlock(105, "melon_stem", (new BlockStem(block9)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1202 */     registerBlock(106, "vine", (new BlockVine()).setHardness(0.2F).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
/* 1203 */     registerBlock(107, "fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate"));
/* 1204 */     registerBlock(108, "brick_stairs", (new BlockStairs(block5.getDefaultState())).setUnlocalizedName("stairsBrick"));
/* 1205 */     registerBlock(109, "stone_brick_stairs", (new BlockStairs(block8.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
/* 1206 */     registerBlock(110, "mycelium", (new BlockMycelium()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
/* 1207 */     registerBlock(111, "waterlily", (new BlockLilyPad()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
/* 1208 */     Block block10 = (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1209 */     registerBlock(112, "nether_brick", block10);
/* 1210 */     registerBlock(113, "nether_brick_fence", (new BlockFence(Material.rock, MapColor.netherrackColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherFence"));
/* 1211 */     registerBlock(114, "nether_brick_stairs", (new BlockStairs(block10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
/* 1212 */     registerBlock(115, "nether_wart", (new BlockNetherWart()).setUnlocalizedName("netherStalk"));
/* 1213 */     registerBlock(116, "enchanting_table", (new BlockEnchantmentTable()).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
/* 1214 */     registerBlock(117, "brewing_stand", (new BlockBrewingStand()).setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
/* 1215 */     registerBlock(118, "cauldron", (new BlockCauldron()).setHardness(2.0F).setUnlocalizedName("cauldron"));
/* 1216 */     registerBlock(119, "end_portal", (new BlockEndPortal(Material.portal)).setHardness(-1.0F).setResistance(6000000.0F));
/* 1217 */     registerBlock(120, "end_portal_frame", (new BlockEndPortalFrame()).setStepSound(soundTypeGlass).setLightLevel(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations));
/* 1218 */     registerBlock(121, "end_stone", (new Block(Material.rock, MapColor.sandColor)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
/* 1219 */     registerBlock(122, "dragon_egg", (new BlockDragonEgg()).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setUnlocalizedName("dragonEgg"));
/* 1220 */     registerBlock(123, "redstone_lamp", (new BlockRedstoneLight(false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
/* 1221 */     registerBlock(124, "lit_redstone_lamp", (new BlockRedstoneLight(true)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
/* 1222 */     registerBlock(125, "double_wooden_slab", (new BlockDoubleWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1223 */     registerBlock(126, "wooden_slab", (new BlockHalfWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1224 */     registerBlock(127, "cocoa", (new BlockCocoa()).setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
/* 1225 */     registerBlock(128, "sandstone_stairs", (new BlockStairs(block2.getDefaultState().withProperty((IProperty)BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
/* 1226 */     registerBlock(129, "emerald_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
/* 1227 */     registerBlock(130, "ender_chest", (new BlockEnderChest()).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5F));
/* 1228 */     registerBlock(131, "tripwire_hook", (new BlockTripWireHook()).setUnlocalizedName("tripWireSource"));
/* 1229 */     registerBlock(132, "tripwire", (new BlockTripWire()).setUnlocalizedName("tripWire"));
/* 1230 */     registerBlock(133, "emerald_block", (new Block(Material.iron, MapColor.emeraldColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.tabBlock));
/* 1231 */     registerBlock(134, "spruce_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
/* 1232 */     registerBlock(135, "birch_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
/* 1233 */     registerBlock(136, "jungle_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
/* 1234 */     registerBlock(137, "command_block", (new BlockCommandBlock()).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
/* 1235 */     registerBlock(138, "beacon", (new BlockBeacon()).setUnlocalizedName("beacon").setLightLevel(1.0F));
/* 1236 */     registerBlock(139, "cobblestone_wall", (new BlockWall(block)).setUnlocalizedName("cobbleWall"));
/* 1237 */     registerBlock(140, "flower_pot", (new BlockFlowerPot()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
/* 1238 */     registerBlock(141, "carrots", (new BlockCarrot()).setUnlocalizedName("carrots"));
/* 1239 */     registerBlock(142, "potatoes", (new BlockPotato()).setUnlocalizedName("potatoes"));
/* 1240 */     registerBlock(143, "wooden_button", (new BlockButtonWood()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("button"));
/* 1241 */     registerBlock(144, "skull", (new BlockSkull()).setHardness(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
/* 1242 */     registerBlock(145, "anvil", (new BlockAnvil()).setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setUnlocalizedName("anvil"));
/* 1243 */     registerBlock(146, "trapped_chest", (new BlockChest(1)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
/* 1244 */     registerBlock(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 15, MapColor.goldColor)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_light"));
/* 1245 */     registerBlock(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 150)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
/* 1246 */     registerBlock(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1247 */     registerBlock(150, "powered_comparator", (new BlockRedstoneComparator(true)).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1248 */     registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
/* 1249 */     registerBlock(152, "redstone_block", (new BlockCompressedPowered(Material.iron, MapColor.tntColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.tabRedstone));
/* 1250 */     registerBlock(153, "quartz_ore", (new BlockOre(MapColor.netherrackColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz"));
/* 1251 */     registerBlock(154, "hopper", (new BlockHopper()).setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
/* 1252 */     Block block11 = (new BlockQuartz()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("quartzBlock");
/* 1253 */     registerBlock(155, "quartz_block", block11);
/* 1254 */     registerBlock(156, "quartz_stairs", (new BlockStairs(block11.getDefaultState().withProperty((IProperty)BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
/* 1255 */     registerBlock(157, "activator_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
/* 1256 */     registerBlock(158, "dropper", (new BlockDropper()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
/* 1257 */     registerBlock(159, "stained_hardened_clay", (new BlockColored(Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained"));
/* 1258 */     registerBlock(160, "stained_glass_pane", (new BlockStainedGlassPane()).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
/* 1259 */     registerBlock(161, "leaves2", (new BlockNewLeaf()).setUnlocalizedName("leaves"));
/* 1260 */     registerBlock(162, "log2", (new BlockNewLog()).setUnlocalizedName("log"));
/* 1261 */     registerBlock(163, "acacia_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
/* 1262 */     registerBlock(164, "dark_oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
/* 1263 */     registerBlock(165, "slime", (new BlockSlime()).setUnlocalizedName("slime").setStepSound(SLIME_SOUND));
/* 1264 */     registerBlock(166, "barrier", (new BlockBarrier()).setUnlocalizedName("barrier"));
/* 1265 */     registerBlock(167, "iron_trapdoor", (new BlockTrapDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
/* 1266 */     registerBlock(168, "prismarine", (new BlockPrismarine()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("prismarine"));
/* 1267 */     registerBlock(169, "sea_lantern", (new BlockSeaLantern(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
/* 1268 */     registerBlock(170, "hay_block", (new BlockHay()).setHardness(0.5F).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
/* 1269 */     registerBlock(171, "carpet", (new BlockCarpet()).setHardness(0.1F).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
/* 1270 */     registerBlock(172, "hardened_clay", (new BlockHardenedClay()).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened"));
/* 1271 */     registerBlock(173, "coal_block", (new Block(Material.rock, MapColor.blackColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
/* 1272 */     registerBlock(174, "packed_ice", (new BlockPackedIce()).setHardness(0.5F).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
/* 1273 */     registerBlock(175, "double_plant", new BlockDoublePlant());
/* 1274 */     registerBlock(176, "standing_banner", (new BlockBanner.BlockBannerStanding()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1275 */     registerBlock(177, "wall_banner", (new BlockBanner.BlockBannerHanging()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1276 */     registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
/* 1277 */     Block block12 = (new BlockRedSandstone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("redSandStone");
/* 1278 */     registerBlock(179, "red_sandstone", block12);
/* 1279 */     registerBlock(180, "red_sandstone_stairs", (new BlockStairs(block12.getDefaultState().withProperty((IProperty)BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
/* 1280 */     registerBlock(181, "double_stone_slab2", (new BlockDoubleStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1281 */     registerBlock(182, "stone_slab2", (new BlockHalfStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1282 */     registerBlock(183, "spruce_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFenceGate"));
/* 1283 */     registerBlock(184, "birch_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.BIRCH)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate"));
/* 1284 */     registerBlock(185, "jungle_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFenceGate"));
/* 1285 */     registerBlock(186, "dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
/* 1286 */     registerBlock(187, "acacia_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.ACACIA)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
/* 1287 */     registerBlock(188, "spruce_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFence"));
/* 1288 */     registerBlock(189, "birch_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFence"));
/* 1289 */     registerBlock(190, "jungle_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFence"));
/* 1290 */     registerBlock(191, "dark_oak_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFence"));
/* 1291 */     registerBlock(192, "acacia_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFence"));
/* 1292 */     registerBlock(193, "spruce_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
/* 1293 */     registerBlock(194, "birch_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
/* 1294 */     registerBlock(195, "jungle_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
/* 1295 */     registerBlock(196, "acacia_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
/* 1296 */     registerBlock(197, "dark_oak_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
/* 1297 */     blockRegistry.validateKey();
/*      */     
/* 1299 */     for (Block block13 : blockRegistry) {
/* 1300 */       if (block13.blockMaterial == Material.air) {
/* 1301 */         block13.useNeighborBrightness = false; continue;
/*      */       } 
/* 1303 */       boolean flag = false;
/* 1304 */       boolean flag1 = block13 instanceof BlockStairs;
/* 1305 */       boolean flag2 = block13 instanceof BlockSlab;
/* 1306 */       boolean flag3 = (block13 == block6);
/* 1307 */       boolean flag4 = block13.translucent;
/* 1308 */       boolean flag5 = (block13.lightOpacity == 0);
/*      */       
/* 1310 */       if (flag1 || flag2 || flag3 || flag4 || flag5) {
/* 1311 */         flag = true;
/*      */       }
/*      */       
/* 1314 */       block13.useNeighborBrightness = flag;
/*      */     } 
/*      */ 
/*      */     
/* 1318 */     for (Block block14 : blockRegistry) {
/* 1319 */       for (IBlockState iblockstate : block14.getBlockState().getValidStates()) {
/* 1320 */         int i = blockRegistry.getIDForObject(block14) << 4 | block14.getMetaFromState(iblockstate);
/* 1321 */         BLOCK_STATE_IDS.put(iblockstate, i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void registerBlock(int id, ResourceLocation textualID, Block block_) {
/* 1327 */     blockRegistry.register(id, textualID, block_);
/*      */   }
/*      */   
/*      */   private static void registerBlock(int id, String textualID, Block block_) {
/* 1331 */     registerBlock(id, new ResourceLocation(textualID), block_);
/*      */   }
/*      */   
/*      */   public enum EnumOffsetType {
/* 1335 */     NONE,
/* 1336 */     XZ,
/* 1337 */     XYZ;
/*      */   }
/*      */   
/*      */   public static class SoundType {
/*      */     public final String soundName;
/*      */     public final float volume;
/*      */     public final float frequency;
/*      */     
/*      */     public SoundType(String name, float volume, float frequency) {
/* 1346 */       this.soundName = name;
/* 1347 */       this.volume = volume;
/* 1348 */       this.frequency = frequency;
/*      */     }
/*      */     
/*      */     public float getVolume() {
/* 1352 */       return this.volume;
/*      */     }
/*      */     
/*      */     public float getFrequency() {
/* 1356 */       return this.frequency;
/*      */     }
/*      */     
/*      */     public String getBreakSound() {
/* 1360 */       return "dig." + this.soundName;
/*      */     }
/*      */     
/*      */     public String getStepSound() {
/* 1364 */       return "step." + this.soundName;
/*      */     }
/*      */     
/*      */     public String getPlaceSound() {
/* 1368 */       return getBreakSound();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */