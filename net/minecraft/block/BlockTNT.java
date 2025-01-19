/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTNT extends Block {
/*  22 */   public static final PropertyBool EXPLODE = PropertyBool.create("explode");
/*     */   
/*     */   public BlockTNT() {
/*  25 */     super(Material.tnt);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)EXPLODE, Boolean.valueOf(false)));
/*  27 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  31 */     super.onBlockAdded(worldIn, pos, state);
/*     */     
/*  33 */     if (worldIn.isBlockPowered(pos)) {
/*  34 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  35 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  43 */     if (worldIn.isBlockPowered(pos)) {
/*  44 */       onBlockDestroyedByPlayer(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)));
/*  45 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/*  53 */     if (!worldIn.isRemote) {
/*  54 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
/*  55 */       entitytntprimed.fuse = worldIn.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
/*  56 */       worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  64 */     explode(worldIn, pos, state, (EntityLivingBase)null);
/*     */   }
/*     */   
/*     */   public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
/*  68 */     if (!worldIn.isRemote && (
/*  69 */       (Boolean)state.getValue((IProperty)EXPLODE)).booleanValue()) {
/*  70 */       EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), igniter);
/*  71 */       worldIn.spawnEntityInWorld((Entity)entitytntprimed);
/*  72 */       worldIn.playSoundAtEntity((Entity)entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  78 */     if (playerIn.getCurrentEquippedItem() != null) {
/*  79 */       Item item = playerIn.getCurrentEquippedItem().getItem();
/*     */       
/*  81 */       if (item == Items.flint_and_steel || item == Items.fire_charge) {
/*  82 */         explode(worldIn, pos, state.withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (EntityLivingBase)playerIn);
/*  83 */         worldIn.setBlockToAir(pos);
/*     */         
/*  85 */         if (item == Items.flint_and_steel) {
/*  86 */           playerIn.getCurrentEquippedItem().damageItem(1, (EntityLivingBase)playerIn);
/*  87 */         } else if (!playerIn.capabilities.isCreativeMode) {
/*  88 */           (playerIn.getCurrentEquippedItem()).stackSize--;
/*     */         } 
/*     */         
/*  91 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 102 */     if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
/* 103 */       EntityArrow entityarrow = (EntityArrow)entityIn;
/*     */       
/* 105 */       if (entityarrow.isBurning()) {
/* 106 */         explode(worldIn, pos, worldIn.getBlockState(pos).withProperty((IProperty)EXPLODE, Boolean.valueOf(true)), (entityarrow.shootingEntity instanceof EntityLivingBase) ? (EntityLivingBase)entityarrow.shootingEntity : null);
/* 107 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 123 */     return getDefaultState().withProperty((IProperty)EXPLODE, Boolean.valueOf(((meta & 0x1) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 130 */     return ((Boolean)state.getValue((IProperty)EXPLODE)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 134 */     return new BlockState(this, new IProperty[] { (IProperty)EXPLODE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTNT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */