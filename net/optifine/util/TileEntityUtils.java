/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.tileentity.TileEntityDispenser;
/*    */ import net.minecraft.tileentity.TileEntityHopper;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.IWorldNameable;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityUtils
/*    */ {
/*    */   public static String getTileEntityName(IBlockAccess blockAccess, BlockPos blockPos) {
/* 19 */     TileEntity tileentity = blockAccess.getTileEntity(blockPos);
/* 20 */     return getTileEntityName(tileentity);
/*    */   }
/*    */   
/*    */   public static String getTileEntityName(TileEntity te) {
/* 24 */     if (!(te instanceof IWorldNameable)) {
/* 25 */       return null;
/*    */     }
/* 27 */     IWorldNameable iworldnameable = (IWorldNameable)te;
/* 28 */     updateTileEntityName(te);
/* 29 */     return !iworldnameable.hasCustomName() ? null : iworldnameable.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void updateTileEntityName(TileEntity te) {
/* 34 */     BlockPos blockpos = te.getPos();
/* 35 */     String s = getTileEntityRawName(te);
/*    */     
/* 37 */     if (s == null) {
/* 38 */       String s1 = getServerTileEntityRawName(blockpos);
/* 39 */       s1 = Config.normalize(s1);
/* 40 */       setTileEntityRawName(te, s1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getServerTileEntityRawName(BlockPos blockPos) {
/* 45 */     TileEntity tileentity = IntegratedServerUtils.getTileEntity(blockPos);
/* 46 */     return (tileentity == null) ? null : getTileEntityRawName(tileentity);
/*    */   }
/*    */   
/*    */   public static String getTileEntityRawName(TileEntity te) {
/* 50 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/* 51 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName); 
/* 52 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/* 53 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBrewingStand_customName); 
/* 54 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/* 55 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityEnchantmentTable_customName); 
/* 56 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace) {
/* 57 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityFurnace_customName);
/*    */     }
/* 59 */     if (te instanceof IWorldNameable) {
/* 60 */       IWorldNameable iworldnameable = (IWorldNameable)te;
/*    */       
/* 62 */       if (iworldnameable.hasCustomName()) {
/* 63 */         return iworldnameable.getName();
/*    */       }
/*    */     } 
/*    */     
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean setTileEntityRawName(TileEntity te, String name) {
/* 72 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/* 73 */       return Reflector.setFieldValue(te, Reflector.TileEntityBeacon_customName, name); 
/* 74 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/* 75 */       return Reflector.setFieldValue(te, Reflector.TileEntityBrewingStand_customName, name); 
/* 76 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/* 77 */       return Reflector.setFieldValue(te, Reflector.TileEntityEnchantmentTable_customName, name); 
/* 78 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace)
/* 79 */       return Reflector.setFieldValue(te, Reflector.TileEntityFurnace_customName, name); 
/* 80 */     if (te instanceof TileEntityChest) {
/* 81 */       ((TileEntityChest)te).setCustomName(name);
/* 82 */       return true;
/* 83 */     }  if (te instanceof TileEntityDispenser) {
/* 84 */       ((TileEntityDispenser)te).setCustomName(name);
/* 85 */       return true;
/* 86 */     }  if (te instanceof TileEntityHopper) {
/* 87 */       ((TileEntityHopper)te).setCustomName(name);
/* 88 */       return true;
/*    */     } 
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\TileEntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */