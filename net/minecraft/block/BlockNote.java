/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityNote;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNote
/*     */   extends BlockContainer
/*     */ {
/*  20 */   private static final List<String> INSTRUMENTS = Lists.newArrayList((Object[])new String[] { "harp", "bd", "snare", "hat", "bassattack" });
/*     */   
/*     */   public BlockNote() {
/*  23 */     super(Material.wood);
/*  24 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  31 */     boolean flag = worldIn.isBlockPowered(pos);
/*  32 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  34 */     if (tileentity instanceof TileEntityNote) {
/*  35 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*     */       
/*  37 */       if (tileentitynote.previousRedstoneState != flag) {
/*  38 */         if (flag) {
/*  39 */           tileentitynote.triggerNote(worldIn, pos);
/*     */         }
/*     */         
/*  42 */         tileentitynote.previousRedstoneState = flag;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  48 */     if (worldIn.isRemote) {
/*  49 */       return true;
/*     */     }
/*  51 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  53 */     if (tileentity instanceof TileEntityNote) {
/*  54 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*  55 */       tileentitynote.changePitch();
/*  56 */       tileentitynote.triggerNote(worldIn, pos);
/*  57 */       playerIn.triggerAchievement(StatList.field_181735_S);
/*     */     } 
/*     */     
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  65 */     if (!worldIn.isRemote) {
/*  66 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  68 */       if (tileentity instanceof TileEntityNote) {
/*  69 */         ((TileEntityNote)tileentity).triggerNote(worldIn, pos);
/*  70 */         playerIn.triggerAchievement(StatList.field_181734_R);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  79 */     return (TileEntity)new TileEntityNote();
/*     */   }
/*     */   
/*     */   private String getInstrument(int id) {
/*  83 */     if (id < 0 || id >= INSTRUMENTS.size()) {
/*  84 */       id = 0;
/*     */     }
/*     */     
/*  87 */     return INSTRUMENTS.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/*  94 */     float f = (float)Math.pow(2.0D, (eventParam - 12) / 12.0D);
/*  95 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "note." + getInstrument(eventID), 3.0F, f);
/*  96 */     worldIn.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, eventParam / 24.0D, 0.0D, 0.0D, new int[0]);
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 104 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */