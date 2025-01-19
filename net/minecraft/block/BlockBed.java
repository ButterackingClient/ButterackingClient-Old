/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class BlockBed
/*     */   extends BlockDirectional {
/*  25 */   public static final PropertyEnum<EnumPartType> PART = PropertyEnum.create("part", EnumPartType.class);
/*  26 */   public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
/*     */   
/*     */   public BlockBed() {
/*  29 */     super(Material.cloth);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)OCCUPIED, Boolean.valueOf(false)));
/*  31 */     setBedBounds();
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  35 */     if (worldIn.isRemote) {
/*  36 */       return true;
/*     */     }
/*  38 */     if (state.getValue((IProperty)PART) != EnumPartType.HEAD) {
/*  39 */       pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  40 */       state = worldIn.getBlockState(pos);
/*     */       
/*  42 */       if (state.getBlock() != this) {
/*  43 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  47 */     if (worldIn.provider.canRespawnHere() && worldIn.getBiomeGenForCoords(pos) != BiomeGenBase.hell) {
/*  48 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue()) {
/*  49 */         EntityPlayer entityplayer = getPlayerInBed(worldIn, pos);
/*     */         
/*  51 */         if (entityplayer != null) {
/*  52 */           playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
/*  53 */           return true;
/*     */         } 
/*     */         
/*  56 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(false));
/*  57 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */       
/*  60 */       EntityPlayer.EnumStatus entityplayer$enumstatus = playerIn.trySleep(pos);
/*     */       
/*  62 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*  63 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(true));
/*  64 */         worldIn.setBlockState(pos, state, 4);
/*  65 */         return true;
/*     */       } 
/*  67 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
/*  68 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
/*  69 */       } else if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
/*  70 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
/*     */       } 
/*     */       
/*  73 */       return true;
/*     */     } 
/*     */     
/*  76 */     worldIn.setBlockToAir(pos);
/*  77 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */     
/*  79 */     if (worldIn.getBlockState(blockpos).getBlock() == this) {
/*  80 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */     
/*  83 */     worldIn.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
/*  90 */     for (EntityPlayer entityplayer : worldIn.playerEntities) {
/*  91 */       if (entityplayer.isPlayerSleeping() && entityplayer.playerLocation.equals(pos)) {
/*  92 */         return entityplayer;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 111 */     setBedBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 118 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 120 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/* 121 */       if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this) {
/* 122 */         worldIn.setBlockToAir(pos);
/*     */       }
/* 124 */     } else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
/* 125 */       worldIn.setBlockToAir(pos);
/*     */       
/* 127 */       if (!worldIn.isRemote) {
/* 128 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 137 */     return (state.getValue((IProperty)PART) == EnumPartType.HEAD) ? null : Items.bed;
/*     */   }
/*     */   
/*     */   private void setBedBounds() {
/* 141 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries) {
/* 148 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 149 */     int i = pos.getX();
/* 150 */     int j = pos.getY();
/* 151 */     int k = pos.getZ();
/*     */     
/* 153 */     for (int l = 0; l <= 1; l++) {
/* 154 */       int i1 = i - enumfacing.getFrontOffsetX() * l - 1;
/* 155 */       int j1 = k - enumfacing.getFrontOffsetZ() * l - 1;
/* 156 */       int k1 = i1 + 2;
/* 157 */       int l1 = j1 + 2;
/*     */       
/* 159 */       for (int i2 = i1; i2 <= k1; i2++) {
/* 160 */         for (int j2 = j1; j2 <= l1; j2++) {
/* 161 */           BlockPos blockpos = new BlockPos(i2, j, j2);
/*     */           
/* 163 */           if (hasRoomForPlayer(worldIn, blockpos)) {
/* 164 */             if (tries <= 0) {
/* 165 */               return blockpos;
/*     */             }
/*     */             
/* 168 */             tries--;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos) {
/* 178 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !worldIn.getBlockState(pos).getBlock().getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 185 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/* 186 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMobilityFlag() {
/* 191 */     return 1;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 195 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 199 */     return Items.bed;
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 203 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/* 204 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 206 */       if (worldIn.getBlockState(blockpos).getBlock() == this) {
/* 207 */         worldIn.setBlockToAir(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 216 */     EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/* 217 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)PART, EnumPartType.HEAD).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)OCCUPIED, Boolean.valueOf(((meta & 0x4) > 0))) : getDefaultState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 225 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/* 226 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue((IProperty)FACING)));
/*     */       
/* 228 */       if (iblockstate.getBlock() == this) {
/* 229 */         state = state.withProperty((IProperty)OCCUPIED, iblockstate.getValue((IProperty)OCCUPIED));
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 240 */     int i = 0;
/* 241 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 243 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/* 244 */       i |= 0x8;
/*     */       
/* 246 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue()) {
/* 247 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 255 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)PART, (IProperty)OCCUPIED });
/*     */   }
/*     */   
/*     */   public enum EnumPartType implements IStringSerializable {
/* 259 */     HEAD("head"),
/* 260 */     FOOT("foot");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     EnumPartType(String name) {
/* 265 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 269 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 273 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */