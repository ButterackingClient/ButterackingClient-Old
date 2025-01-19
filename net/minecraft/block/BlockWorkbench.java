/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerWorkbench;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.IInteractionObject;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockWorkbench extends Block {
/*    */   protected BlockWorkbench() {
/* 21 */     super(Material.wood);
/* 22 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */   
/*    */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 26 */     if (worldIn.isRemote) {
/* 27 */       return true;
/*    */     }
/* 29 */     playerIn.displayGui(new InterfaceCraftingTable(worldIn, pos));
/* 30 */     playerIn.triggerAchievement(StatList.field_181742_Z);
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public static class InterfaceCraftingTable
/*    */     implements IInteractionObject {
/*    */     private final World world;
/*    */     private final BlockPos position;
/*    */     
/*    */     public InterfaceCraftingTable(World worldIn, BlockPos pos) {
/* 40 */       this.world = worldIn;
/* 41 */       this.position = pos;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 45 */       return null;
/*    */     }
/*    */     
/*    */     public boolean hasCustomName() {
/* 49 */       return false;
/*    */     }
/*    */     
/*    */     public IChatComponent getDisplayName() {
/* 53 */       return (IChatComponent)new ChatComponentTranslation(String.valueOf(Blocks.crafting_table.getUnlocalizedName()) + ".name", new Object[0]);
/*    */     }
/*    */     
/*    */     public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 57 */       return (Container)new ContainerWorkbench(playerInventory, this.world, this.position);
/*    */     }
/*    */     
/*    */     public String getGuiID() {
/* 61 */       return "minecraft:crafting_table";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */