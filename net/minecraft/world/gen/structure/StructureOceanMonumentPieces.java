/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.monster.EntityGuardian;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class StructureOceanMonumentPieces
/*      */ {
/*      */   public static void registerOceanMonumentPieces() {
/*   23 */     MapGenStructureIO.registerStructureComponent((Class)MonumentBuilding.class, "OMB");
/*   24 */     MapGenStructureIO.registerStructureComponent((Class)MonumentCoreRoom.class, "OMCR");
/*   25 */     MapGenStructureIO.registerStructureComponent((Class)DoubleXRoom.class, "OMDXR");
/*   26 */     MapGenStructureIO.registerStructureComponent((Class)DoubleXYRoom.class, "OMDXYR");
/*   27 */     MapGenStructureIO.registerStructureComponent((Class)DoubleYRoom.class, "OMDYR");
/*   28 */     MapGenStructureIO.registerStructureComponent((Class)DoubleYZRoom.class, "OMDYZR");
/*   29 */     MapGenStructureIO.registerStructureComponent((Class)DoubleZRoom.class, "OMDZR");
/*   30 */     MapGenStructureIO.registerStructureComponent((Class)EntryRoom.class, "OMEntry");
/*   31 */     MapGenStructureIO.registerStructureComponent((Class)Penthouse.class, "OMPenthouse");
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)SimpleRoom.class, "OMSimple");
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)SimpleTopRoom.class, "OMSimpleT");
/*      */   }
/*      */   
/*      */   public static class DoubleXRoom
/*      */     extends Piece {
/*      */     public DoubleXRoom() {}
/*      */     
/*      */     public DoubleXRoom(EnumFacing p_i45597_1_, StructureOceanMonumentPieces.RoomDefinition p_i45597_2_, Random p_i45597_3_) {
/*   41 */       super(1, p_i45597_1_, p_i45597_2_, 2, 1, 1);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*   45 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
/*   46 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.field_175830_k;
/*      */       
/*   48 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/*   49 */         func_175821_a(worldIn, structureBoundingBoxIn, 8, 0, structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*   50 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*   53 */       if (structureoceanmonumentpieces$roomdefinition1.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*   54 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 6, field_175828_a);
/*      */       }
/*      */       
/*   57 */       if (structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*   58 */         func_175819_a(worldIn, structureBoundingBoxIn, 8, 4, 1, 14, 4, 6, field_175828_a);
/*      */       }
/*      */       
/*   61 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
/*   62 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 3, 0, 15, 3, 7, field_175826_b, field_175826_b, false);
/*   63 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
/*   64 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 14, 3, 7, field_175826_b, field_175826_b, false);
/*   65 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, field_175828_a, field_175828_a, false);
/*   66 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 2, 0, 15, 2, 7, field_175828_a, field_175828_a, false);
/*   67 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 15, 2, 0, field_175828_a, field_175828_a, false);
/*   68 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 14, 2, 7, field_175828_a, field_175828_a, false);
/*   69 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
/*   70 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
/*   71 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
/*   72 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
/*   73 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 10, 1, 4, field_175826_b, field_175826_b, false);
/*   74 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 9, 2, 3, field_175828_a, field_175828_a, false);
/*   75 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 10, 3, 4, field_175826_b, field_175826_b, false);
/*   76 */       setBlockState(worldIn, field_175825_e, 6, 2, 3, structureBoundingBoxIn);
/*   77 */       setBlockState(worldIn, field_175825_e, 9, 2, 3, structureBoundingBoxIn);
/*      */       
/*   79 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*   80 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*   83 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*   84 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*   87 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*   88 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*   91 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*   92 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/*      */       
/*   95 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*   96 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/*      */       
/*   99 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  100 */         func_181655_a(worldIn, structureBoundingBoxIn, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/*      */       
/*  103 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DoubleXYRoom
/*      */     extends Piece {
/*      */     public DoubleXYRoom() {}
/*      */     
/*      */     public DoubleXYRoom(EnumFacing p_i45596_1_, StructureOceanMonumentPieces.RoomDefinition p_i45596_2_, Random p_i45596_3_) {
/*  112 */       super(1, p_i45596_1_, p_i45596_2_, 2, 2, 1);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  116 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
/*  117 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.field_175830_k;
/*  118 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = structureoceanmonumentpieces$roomdefinition1.field_175965_b[EnumFacing.UP.getIndex()];
/*  119 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 = structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()];
/*      */       
/*  121 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/*  122 */         func_175821_a(worldIn, structureBoundingBoxIn, 8, 0, structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*  123 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  126 */       if (structureoceanmonumentpieces$roomdefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  127 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 8, 1, 7, 8, 6, field_175828_a);
/*      */       }
/*      */       
/*  130 */       if (structureoceanmonumentpieces$roomdefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  131 */         func_175819_a(worldIn, structureBoundingBoxIn, 8, 8, 1, 14, 8, 6, field_175828_a);
/*      */       }
/*      */       
/*  134 */       for (int i = 1; i <= 7; i++) {
/*  135 */         IBlockState iblockstate = field_175826_b;
/*      */         
/*  137 */         if (i == 2 || i == 6) {
/*  138 */           iblockstate = field_175828_a;
/*      */         }
/*      */         
/*  141 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, 0, 0, i, 7, iblockstate, iblockstate, false);
/*  142 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, i, 0, 15, i, 7, iblockstate, iblockstate, false);
/*  143 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 15, i, 0, iblockstate, iblockstate, false);
/*  144 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 7, 14, i, 7, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/*  147 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 3, 2, 7, 4, field_175826_b, field_175826_b, false);
/*  148 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 4, 7, 2, field_175826_b, field_175826_b, false);
/*  149 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 5, 4, 7, 5, field_175826_b, field_175826_b, false);
/*  150 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 3, 13, 7, 4, field_175826_b, field_175826_b, false);
/*  151 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 1, 2, 12, 7, 2, field_175826_b, field_175826_b, false);
/*  152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 1, 5, 12, 7, 5, field_175826_b, field_175826_b, false);
/*  153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 3, 5, 3, 4, field_175826_b, field_175826_b, false);
/*  154 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 3, 10, 3, 4, field_175826_b, field_175826_b, false);
/*  155 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 2, 10, 7, 5, field_175826_b, field_175826_b, false);
/*  156 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 7, 2, field_175826_b, field_175826_b, false);
/*  157 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 2, 10, 7, 2, field_175826_b, field_175826_b, false);
/*  158 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 5, 7, 5, field_175826_b, field_175826_b, false);
/*  159 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 5, 10, 7, 5, field_175826_b, field_175826_b, false);
/*  160 */       setBlockState(worldIn, field_175826_b, 6, 6, 2, structureBoundingBoxIn);
/*  161 */       setBlockState(worldIn, field_175826_b, 9, 6, 2, structureBoundingBoxIn);
/*  162 */       setBlockState(worldIn, field_175826_b, 6, 6, 5, structureBoundingBoxIn);
/*  163 */       setBlockState(worldIn, field_175826_b, 9, 6, 5, structureBoundingBoxIn);
/*  164 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 3, 6, 4, 4, field_175826_b, field_175826_b, false);
/*  165 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 4, 3, 10, 4, 4, field_175826_b, field_175826_b, false);
/*  166 */       setBlockState(worldIn, field_175825_e, 5, 4, 2, structureBoundingBoxIn);
/*  167 */       setBlockState(worldIn, field_175825_e, 5, 4, 5, structureBoundingBoxIn);
/*  168 */       setBlockState(worldIn, field_175825_e, 10, 4, 2, structureBoundingBoxIn);
/*  169 */       setBlockState(worldIn, field_175825_e, 10, 4, 5, structureBoundingBoxIn);
/*      */       
/*  171 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  172 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  175 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  176 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*  179 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  180 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  183 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  184 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/*      */       
/*  187 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  188 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/*      */       
/*  191 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  192 */         func_181655_a(worldIn, structureBoundingBoxIn, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/*      */       
/*  195 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  196 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/*      */       
/*  199 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  200 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 5, 7, 4, 6, 7, false);
/*      */       }
/*      */       
/*  203 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  204 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 6, 4, false);
/*      */       }
/*      */       
/*  207 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  208 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 6, 0, false);
/*      */       }
/*      */       
/*  211 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  212 */         func_181655_a(worldIn, structureBoundingBoxIn, 11, 5, 7, 12, 6, 7, false);
/*      */       }
/*      */       
/*  215 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  216 */         func_181655_a(worldIn, structureBoundingBoxIn, 15, 5, 3, 15, 6, 4, false);
/*      */       }
/*      */       
/*  219 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DoubleYRoom
/*      */     extends Piece {
/*      */     public DoubleYRoom() {}
/*      */     
/*      */     public DoubleYRoom(EnumFacing p_i45595_1_, StructureOceanMonumentPieces.RoomDefinition p_i45595_2_, Random p_i45595_3_) {
/*  228 */       super(1, p_i45595_1_, p_i45595_2_, 1, 2, 1);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  232 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/*  233 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/*  236 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
/*      */       
/*  238 */       if (structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  239 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 8, 1, 6, 8, 6, field_175828_a);
/*      */       }
/*      */       
/*  242 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 0, 4, 7, field_175826_b, field_175826_b, false);
/*  243 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 0, 7, 4, 7, field_175826_b, field_175826_b, false);
/*  244 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 0, 6, 4, 0, field_175826_b, field_175826_b, false);
/*  245 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 6, 4, 7, field_175826_b, field_175826_b, false);
/*  246 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 4, 1, 2, 4, 2, field_175826_b, field_175826_b, false);
/*  247 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 1, 4, 2, field_175826_b, field_175826_b, false);
/*  248 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 1, 5, 4, 2, field_175826_b, field_175826_b, false);
/*  249 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 2, field_175826_b, field_175826_b, false);
/*  250 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 4, 5, 2, 4, 6, field_175826_b, field_175826_b, false);
/*  251 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 4, 5, field_175826_b, field_175826_b, false);
/*  252 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 5, 5, 4, 6, field_175826_b, field_175826_b, false);
/*  253 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 5, 6, 4, 5, field_175826_b, field_175826_b, false);
/*  254 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.field_175830_k;
/*      */       
/*  256 */       for (int i = 1; i <= 5; i += 4) {
/*  257 */         int j = 0;
/*      */         
/*  259 */         if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  260 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, i, j, 2, i + 2, j, field_175826_b, field_175826_b, false);
/*  261 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, i, j, 5, i + 2, j, field_175826_b, field_175826_b, false);
/*  262 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, field_175826_b, field_175826_b, false);
/*      */         } else {
/*  264 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, j, 7, i + 2, j, field_175826_b, field_175826_b, false);
/*  265 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, field_175828_a, field_175828_a, false);
/*      */         } 
/*      */         
/*  268 */         j = 7;
/*      */         
/*  270 */         if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  271 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, i, j, 2, i + 2, j, field_175826_b, field_175826_b, false);
/*  272 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, i, j, 5, i + 2, j, field_175826_b, field_175826_b, false);
/*  273 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, field_175826_b, field_175826_b, false);
/*      */         } else {
/*  275 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, j, 7, i + 2, j, field_175826_b, field_175826_b, false);
/*  276 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, field_175828_a, field_175828_a, false);
/*      */         } 
/*      */         
/*  279 */         int k = 0;
/*      */         
/*  281 */         if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  282 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 2, k, i + 2, 2, field_175826_b, field_175826_b, false);
/*  283 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 5, k, i + 2, 5, field_175826_b, field_175826_b, false);
/*  284 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, field_175826_b, field_175826_b, false);
/*      */         } else {
/*  286 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 0, k, i + 2, 7, field_175826_b, field_175826_b, false);
/*  287 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, field_175828_a, field_175828_a, false);
/*      */         } 
/*      */         
/*  290 */         k = 7;
/*      */         
/*  292 */         if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  293 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 2, k, i + 2, 2, field_175826_b, field_175826_b, false);
/*  294 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 5, k, i + 2, 5, field_175826_b, field_175826_b, false);
/*  295 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, field_175826_b, field_175826_b, false);
/*      */         } else {
/*  297 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 0, k, i + 2, 7, field_175826_b, field_175826_b, false);
/*  298 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, field_175828_a, field_175828_a, false);
/*      */         } 
/*      */         
/*  301 */         structureoceanmonumentpieces$roomdefinition1 = structureoceanmonumentpieces$roomdefinition;
/*      */       } 
/*      */       
/*  304 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DoubleYZRoom
/*      */     extends Piece {
/*      */     public DoubleYZRoom() {}
/*      */     
/*      */     public DoubleYZRoom(EnumFacing p_i45594_1_, StructureOceanMonumentPieces.RoomDefinition p_i45594_2_, Random p_i45594_3_) {
/*  313 */       super(1, p_i45594_1_, p_i45594_2_, 1, 2, 2);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  317 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
/*  318 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.field_175830_k;
/*  319 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()];
/*  320 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 = structureoceanmonumentpieces$roomdefinition1.field_175965_b[EnumFacing.UP.getIndex()];
/*      */       
/*  322 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/*  323 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 8, structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*  324 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  327 */       if (structureoceanmonumentpieces$roomdefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  328 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 8, 1, 6, 8, 7, field_175828_a);
/*      */       }
/*      */       
/*  331 */       if (structureoceanmonumentpieces$roomdefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  332 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 8, 8, 6, 8, 14, field_175828_a);
/*      */       }
/*      */       
/*  335 */       for (int i = 1; i <= 7; i++) {
/*  336 */         IBlockState iblockstate = field_175826_b;
/*      */         
/*  338 */         if (i == 2 || i == 6) {
/*  339 */           iblockstate = field_175828_a;
/*      */         }
/*      */         
/*  342 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, 0, 0, i, 15, iblockstate, iblockstate, false);
/*  343 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, i, 0, 7, i, 15, iblockstate, iblockstate, false);
/*  344 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 6, i, 0, iblockstate, iblockstate, false);
/*  345 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 15, 6, i, 15, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/*  348 */       for (int j = 1; j <= 7; j++) {
/*  349 */         IBlockState iblockstate1 = field_175827_c;
/*      */         
/*  351 */         if (j == 2 || j == 6) {
/*  352 */           iblockstate1 = field_175825_e;
/*      */         }
/*      */         
/*  355 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, j, 7, 4, j, 8, iblockstate1, iblockstate1, false);
/*      */       } 
/*      */       
/*  358 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  359 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  362 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  363 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  366 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  367 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  370 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  371 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/*      */       
/*  374 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  375 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/*      */       
/*  378 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  379 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/*  382 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  383 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/*      */       
/*  386 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  387 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 5, 3, 7, 6, 4, false);
/*  388 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 2, 6, 4, 5, field_175826_b, field_175826_b, false);
/*  389 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 2, 6, 3, 2, field_175826_b, field_175826_b, false);
/*  390 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 5, 6, 3, 5, field_175826_b, field_175826_b, false);
/*      */       } 
/*      */       
/*  393 */       if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  394 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 6, 4, false);
/*  395 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 2, 4, 5, field_175826_b, field_175826_b, false);
/*  396 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, 3, 2, field_175826_b, field_175826_b, false);
/*  397 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 1, 3, 5, field_175826_b, field_175826_b, false);
/*      */       } 
/*      */       
/*  400 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  401 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 5, 15, 4, 6, 15, false);
/*      */       }
/*      */       
/*  404 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  405 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 5, 11, 0, 6, 12, false);
/*  406 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 10, 2, 4, 13, field_175826_b, field_175826_b, false);
/*  407 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 1, 3, 10, field_175826_b, field_175826_b, false);
/*  408 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 13, 1, 3, 13, field_175826_b, field_175826_b, false);
/*      */       } 
/*      */       
/*  411 */       if (structureoceanmonumentpieces$roomdefinition2.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  412 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 6, 12, false);
/*  413 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 10, 6, 4, 13, field_175826_b, field_175826_b, false);
/*  414 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 10, 6, 3, 10, field_175826_b, field_175826_b, false);
/*  415 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 13, 6, 3, 13, field_175826_b, field_175826_b, false);
/*      */       } 
/*      */       
/*  418 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DoubleZRoom
/*      */     extends Piece {
/*      */     public DoubleZRoom() {}
/*      */     
/*      */     public DoubleZRoom(EnumFacing p_i45593_1_, StructureOceanMonumentPieces.RoomDefinition p_i45593_2_, Random p_i45593_3_) {
/*  427 */       super(1, p_i45593_1_, p_i45593_2_, 1, 1, 2);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  431 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
/*  432 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.field_175830_k;
/*      */       
/*  434 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/*  435 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 8, structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*  436 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  439 */       if (structureoceanmonumentpieces$roomdefinition1.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  440 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 7, field_175828_a);
/*      */       }
/*      */       
/*  443 */       if (structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/*  444 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 4, 8, 6, 4, 14, field_175828_a);
/*      */       }
/*      */       
/*  447 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 15, field_175826_b, field_175826_b, false);
/*  448 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 15, field_175826_b, field_175826_b, false);
/*  449 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
/*  450 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 15, 6, 3, 15, field_175826_b, field_175826_b, false);
/*  451 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 15, field_175828_a, field_175828_a, false);
/*  452 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 15, field_175828_a, field_175828_a, false);
/*  453 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 2, 0, field_175828_a, field_175828_a, false);
/*  454 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 15, 6, 2, 15, field_175828_a, field_175828_a, false);
/*  455 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 15, field_175826_b, field_175826_b, false);
/*  456 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 15, field_175826_b, field_175826_b, false);
/*  457 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 7, 1, 0, field_175826_b, field_175826_b, false);
/*  458 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 15, 6, 1, 15, field_175826_b, field_175826_b, false);
/*  459 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 2, field_175826_b, field_175826_b, false);
/*  460 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 1, 6, 1, 2, field_175826_b, field_175826_b, false);
/*  461 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 1, 1, 3, 2, field_175826_b, field_175826_b, false);
/*  462 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 1, 6, 3, 2, field_175826_b, field_175826_b, false);
/*  463 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 13, 1, 1, 14, field_175826_b, field_175826_b, false);
/*  464 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 13, 6, 1, 14, field_175826_b, field_175826_b, false);
/*  465 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 13, 1, 3, 14, field_175826_b, field_175826_b, false);
/*  466 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 13, 6, 3, 14, field_175826_b, field_175826_b, false);
/*  467 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 3, 6, field_175826_b, field_175826_b, false);
/*  468 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 6, 5, 3, 6, field_175826_b, field_175826_b, false);
/*  469 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 9, 2, 3, 9, field_175826_b, field_175826_b, false);
/*  470 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 5, 3, 9, field_175826_b, field_175826_b, false);
/*  471 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 6, 4, 2, 6, field_175826_b, field_175826_b, false);
/*  472 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 9, 4, 2, 9, field_175826_b, field_175826_b, false);
/*  473 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 7, 2, 2, 8, field_175826_b, field_175826_b, false);
/*  474 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 5, 2, 8, field_175826_b, field_175826_b, false);
/*  475 */       setBlockState(worldIn, field_175825_e, 2, 2, 5, structureBoundingBoxIn);
/*  476 */       setBlockState(worldIn, field_175825_e, 5, 2, 5, structureBoundingBoxIn);
/*  477 */       setBlockState(worldIn, field_175825_e, 2, 2, 10, structureBoundingBoxIn);
/*  478 */       setBlockState(worldIn, field_175825_e, 5, 2, 10, structureBoundingBoxIn);
/*  479 */       setBlockState(worldIn, field_175826_b, 2, 3, 5, structureBoundingBoxIn);
/*  480 */       setBlockState(worldIn, field_175826_b, 5, 3, 5, structureBoundingBoxIn);
/*  481 */       setBlockState(worldIn, field_175826_b, 2, 3, 10, structureBoundingBoxIn);
/*  482 */       setBlockState(worldIn, field_175826_b, 5, 3, 10, structureBoundingBoxIn);
/*      */       
/*  484 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/*  485 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  488 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  489 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  492 */       if (structureoceanmonumentpieces$roomdefinition1.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  493 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  496 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  497 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/*      */       
/*  500 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  501 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/*      */       
/*  504 */       if (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  505 */         func_181655_a(worldIn, structureBoundingBoxIn, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/*  508 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class EntryRoom
/*      */     extends Piece {
/*      */     public EntryRoom() {}
/*      */     
/*      */     public EntryRoom(EnumFacing p_i45592_1_, StructureOceanMonumentPieces.RoomDefinition p_i45592_2_) {
/*  517 */       super(1, p_i45592_1_, p_i45592_2_, 1, 1, 1);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  521 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 2, 3, 7, field_175826_b, field_175826_b, false);
/*  522 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
/*  523 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 2, 7, field_175826_b, field_175826_b, false);
/*  524 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 7, 2, 7, field_175826_b, field_175826_b, false);
/*  525 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
/*  526 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
/*  527 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
/*  528 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, field_175826_b, field_175826_b, false);
/*  529 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
/*      */       
/*  531 */       if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/*  532 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*  535 */       if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
/*  536 */         func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 1, 2, 4, false);
/*      */       }
/*      */       
/*  539 */       if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
/*  540 */         func_181655_a(worldIn, structureBoundingBoxIn, 6, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  543 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class FitSimpleRoomHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private FitSimpleRoomHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/*  552 */       return true;
/*      */     }
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/*  556 */       p_175968_2_.field_175963_d = true;
/*  557 */       return new StructureOceanMonumentPieces.SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class FitSimpleRoomTopHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private FitSimpleRoomTopHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/*  566 */       return (!p_175969_1_.field_175966_c[EnumFacing.WEST.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.EAST.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.SOUTH.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()]);
/*      */     }
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/*  570 */       p_175968_2_.field_175963_d = true;
/*  571 */       return new StructureOceanMonumentPieces.SimpleTopRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MonumentBuilding extends Piece {
/*      */     private StructureOceanMonumentPieces.RoomDefinition field_175845_o;
/*      */     private StructureOceanMonumentPieces.RoomDefinition field_175844_p;
/*  578 */     private List<StructureOceanMonumentPieces.Piece> field_175843_q = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MonumentBuilding(Random p_i45599_1_, int p_i45599_2_, int p_i45599_3_, EnumFacing p_i45599_4_) {
/*  584 */       super(0);
/*  585 */       this.coordBaseMode = p_i45599_4_;
/*      */       
/*  587 */       switch (this.coordBaseMode) {
/*      */         case NORTH:
/*      */         case SOUTH:
/*  590 */           this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
/*      */           break;
/*      */         
/*      */         default:
/*  594 */           this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
/*      */           break;
/*      */       } 
/*  597 */       List<StructureOceanMonumentPieces.RoomDefinition> list = func_175836_a(p_i45599_1_);
/*  598 */       this.field_175845_o.field_175963_d = true;
/*  599 */       this.field_175843_q.add(new StructureOceanMonumentPieces.EntryRoom(this.coordBaseMode, this.field_175845_o));
/*  600 */       this.field_175843_q.add(new StructureOceanMonumentPieces.MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, p_i45599_1_));
/*  601 */       List<StructureOceanMonumentPieces.MonumentRoomFitHelper> list1 = Lists.newArrayList();
/*  602 */       list1.add(new StructureOceanMonumentPieces.XYDoubleRoomFitHelper(null));
/*  603 */       list1.add(new StructureOceanMonumentPieces.YZDoubleRoomFitHelper(null));
/*  604 */       list1.add(new StructureOceanMonumentPieces.ZDoubleRoomFitHelper(null));
/*  605 */       list1.add(new StructureOceanMonumentPieces.XDoubleRoomFitHelper(null));
/*  606 */       list1.add(new StructureOceanMonumentPieces.YDoubleRoomFitHelper(null));
/*  607 */       list1.add(new StructureOceanMonumentPieces.FitSimpleRoomTopHelper(null));
/*  608 */       list1.add(new StructureOceanMonumentPieces.FitSimpleRoomHelper(null));
/*      */ 
/*      */       
/*  611 */       for (StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition : list) {
/*  612 */         if (!structureoceanmonumentpieces$roomdefinition.field_175963_d && !structureoceanmonumentpieces$roomdefinition.func_175961_b()) {
/*  613 */           Iterator<StructureOceanMonumentPieces.MonumentRoomFitHelper> iterator = list1.iterator();
/*      */ 
/*      */ 
/*      */           
/*  617 */           while (iterator.hasNext()) {
/*      */ 
/*      */ 
/*      */             
/*  621 */             StructureOceanMonumentPieces.MonumentRoomFitHelper structureoceanmonumentpieces$monumentroomfithelper = iterator.next();
/*      */             
/*  623 */             if (structureoceanmonumentpieces$monumentroomfithelper.func_175969_a(structureoceanmonumentpieces$roomdefinition))
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  628 */               this.field_175843_q.add(structureoceanmonumentpieces$monumentroomfithelper.func_175968_a(this.coordBaseMode, structureoceanmonumentpieces$roomdefinition, p_i45599_1_)); } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  632 */       int j = this.boundingBox.minY;
/*  633 */       int k = getXWithOffset(9, 22);
/*  634 */       int l = getZWithOffset(9, 22);
/*      */       
/*  636 */       for (StructureOceanMonumentPieces.Piece structureoceanmonumentpieces$piece : this.field_175843_q) {
/*  637 */         structureoceanmonumentpieces$piece.getBoundingBox().offset(k, j, l);
/*      */       }
/*      */       
/*  640 */       StructureBoundingBox structureboundingbox1 = StructureBoundingBox.func_175899_a(getXWithOffset(1, 1), getYWithOffset(1), getZWithOffset(1, 1), getXWithOffset(23, 21), getYWithOffset(8), getZWithOffset(23, 21));
/*  641 */       StructureBoundingBox structureboundingbox2 = StructureBoundingBox.func_175899_a(getXWithOffset(34, 1), getYWithOffset(1), getZWithOffset(34, 1), getXWithOffset(56, 21), getYWithOffset(8), getZWithOffset(56, 21));
/*  642 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175899_a(getXWithOffset(22, 22), getYWithOffset(13), getZWithOffset(22, 22), getXWithOffset(35, 35), getYWithOffset(17), getZWithOffset(35, 35));
/*  643 */       int i = p_i45599_1_.nextInt();
/*  644 */       this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, structureboundingbox1, i++));
/*  645 */       this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, structureboundingbox2, i++));
/*  646 */       this.field_175843_q.add(new StructureOceanMonumentPieces.Penthouse(this.coordBaseMode, structureboundingbox));
/*      */     }
/*      */     
/*      */     private List<StructureOceanMonumentPieces.RoomDefinition> func_175836_a(Random p_175836_1_) {
/*  650 */       StructureOceanMonumentPieces.RoomDefinition[] astructureoceanmonumentpieces$roomdefinition = new StructureOceanMonumentPieces.RoomDefinition[75];
/*      */       
/*  652 */       for (int i = 0; i < 5; i++) {
/*  653 */         for (int k = 0; k < 4; k++) {
/*  654 */           int m = 0;
/*  655 */           int l = func_175820_a(i, m, k);
/*  656 */           astructureoceanmonumentpieces$roomdefinition[l] = new StructureOceanMonumentPieces.RoomDefinition(l);
/*      */         } 
/*      */       } 
/*      */       
/*  660 */       for (int i2 = 0; i2 < 5; i2++) {
/*  661 */         for (int l2 = 0; l2 < 4; l2++) {
/*  662 */           int k3 = 1;
/*  663 */           int j4 = func_175820_a(i2, k3, l2);
/*  664 */           astructureoceanmonumentpieces$roomdefinition[j4] = new StructureOceanMonumentPieces.RoomDefinition(j4);
/*      */         } 
/*      */       } 
/*      */       
/*  668 */       for (int j2 = 1; j2 < 4; j2++) {
/*  669 */         for (int i3 = 0; i3 < 2; i3++) {
/*  670 */           int l3 = 2;
/*  671 */           int k4 = func_175820_a(j2, l3, i3);
/*  672 */           astructureoceanmonumentpieces$roomdefinition[k4] = new StructureOceanMonumentPieces.RoomDefinition(k4);
/*      */         } 
/*      */       } 
/*      */       
/*  676 */       this.field_175845_o = astructureoceanmonumentpieces$roomdefinition[field_175823_g];
/*      */       
/*  678 */       for (int k2 = 0; k2 < 5; k2++) {
/*  679 */         for (int j3 = 0; j3 < 5; j3++) {
/*  680 */           for (int i4 = 0; i4 < 3; i4++) {
/*  681 */             int l4 = func_175820_a(k2, i4, j3);
/*      */             
/*  683 */             if (astructureoceanmonumentpieces$roomdefinition[l4] != null) {
/*  684 */               byte b1; int k; EnumFacing[] arrayOfEnumFacing; for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b1 = 0; b1 < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b1];
/*  685 */                 int i1 = k2 + enumfacing.getFrontOffsetX();
/*  686 */                 int j1 = i4 + enumfacing.getFrontOffsetY();
/*  687 */                 int k1 = j3 + enumfacing.getFrontOffsetZ();
/*      */                 
/*  689 */                 if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
/*  690 */                   int l1 = func_175820_a(i1, j1, k1);
/*      */                   
/*  692 */                   if (astructureoceanmonumentpieces$roomdefinition[l1] != null) {
/*  693 */                     if (k1 != j3) {
/*  694 */                       astructureoceanmonumentpieces$roomdefinition[l4].func_175957_a(enumfacing.getOpposite(), astructureoceanmonumentpieces$roomdefinition[l1]);
/*      */                     } else {
/*  696 */                       astructureoceanmonumentpieces$roomdefinition[l4].func_175957_a(enumfacing, astructureoceanmonumentpieces$roomdefinition[l1]);
/*      */                     } 
/*      */                   }
/*      */                 } 
/*      */                 b1++; }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition;
/*  707 */       astructureoceanmonumentpieces$roomdefinition[field_175831_h].func_175957_a(EnumFacing.UP, structureoceanmonumentpieces$roomdefinition = new StructureOceanMonumentPieces.RoomDefinition(1003));
/*      */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1;
/*  709 */       astructureoceanmonumentpieces$roomdefinition[field_175832_i].func_175957_a(EnumFacing.SOUTH, structureoceanmonumentpieces$roomdefinition1 = new StructureOceanMonumentPieces.RoomDefinition(1001));
/*      */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2;
/*  711 */       astructureoceanmonumentpieces$roomdefinition[field_175829_j].func_175957_a(EnumFacing.SOUTH, structureoceanmonumentpieces$roomdefinition2 = new StructureOceanMonumentPieces.RoomDefinition(1002));
/*  712 */       structureoceanmonumentpieces$roomdefinition.field_175963_d = true;
/*  713 */       structureoceanmonumentpieces$roomdefinition1.field_175963_d = true;
/*  714 */       structureoceanmonumentpieces$roomdefinition2.field_175963_d = true;
/*  715 */       this.field_175845_o.field_175964_e = true;
/*  716 */       this.field_175844_p = astructureoceanmonumentpieces$roomdefinition[func_175820_a(p_175836_1_.nextInt(4), 0, 2)];
/*  717 */       this.field_175844_p.field_175963_d = true;
/*  718 */       (this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()]).field_175963_d = true;
/*  719 */       (this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d = true;
/*  720 */       ((this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()]).field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d = true;
/*  721 */       (this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/*  722 */       ((this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()]).field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/*  723 */       ((this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/*  724 */       (((this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()]).field_175965_b[EnumFacing.NORTH.getIndex()]).field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/*  725 */       List<StructureOceanMonumentPieces.RoomDefinition> list = Lists.newArrayList(); byte b; int j;
/*      */       StructureOceanMonumentPieces.RoomDefinition[] arrayOfRoomDefinition1;
/*  727 */       for (j = (arrayOfRoomDefinition1 = astructureoceanmonumentpieces$roomdefinition).length, b = 0; b < j; ) { StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition4 = arrayOfRoomDefinition1[b];
/*  728 */         if (structureoceanmonumentpieces$roomdefinition4 != null) {
/*  729 */           structureoceanmonumentpieces$roomdefinition4.func_175958_a();
/*  730 */           list.add(structureoceanmonumentpieces$roomdefinition4);
/*      */         } 
/*      */         b++; }
/*      */       
/*  734 */       structureoceanmonumentpieces$roomdefinition.func_175958_a();
/*  735 */       Collections.shuffle(list, p_175836_1_);
/*  736 */       int i5 = 1;
/*      */       
/*  738 */       for (StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 : list) {
/*  739 */         int j5 = 0;
/*  740 */         int k5 = 0;
/*      */         
/*  742 */         while (j5 < 2 && k5 < 5) {
/*  743 */           k5++;
/*  744 */           int l5 = p_175836_1_.nextInt(6);
/*      */           
/*  746 */           if (structureoceanmonumentpieces$roomdefinition3.field_175966_c[l5]) {
/*  747 */             int i6 = EnumFacing.getFront(l5).getOpposite().getIndex();
/*  748 */             structureoceanmonumentpieces$roomdefinition3.field_175966_c[l5] = false;
/*  749 */             (structureoceanmonumentpieces$roomdefinition3.field_175965_b[l5]).field_175966_c[i6] = false;
/*      */             
/*  751 */             if (structureoceanmonumentpieces$roomdefinition3.func_175959_a(i5++) && structureoceanmonumentpieces$roomdefinition3.field_175965_b[l5].func_175959_a(i5++)) {
/*  752 */               j5++; continue;
/*      */             } 
/*  754 */             structureoceanmonumentpieces$roomdefinition3.field_175966_c[l5] = true;
/*  755 */             (structureoceanmonumentpieces$roomdefinition3.field_175965_b[l5]).field_175966_c[i6] = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  761 */       list.add(structureoceanmonumentpieces$roomdefinition);
/*  762 */       list.add(structureoceanmonumentpieces$roomdefinition1);
/*  763 */       list.add(structureoceanmonumentpieces$roomdefinition2);
/*  764 */       return list;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  768 */       int i = Math.max(worldIn.getSeaLevel(), 64) - this.boundingBox.minY;
/*  769 */       func_181655_a(worldIn, structureBoundingBoxIn, 0, 0, 0, 58, i, 58, false);
/*  770 */       func_175840_a(false, 0, worldIn, randomIn, structureBoundingBoxIn);
/*  771 */       func_175840_a(true, 33, worldIn, randomIn, structureBoundingBoxIn);
/*  772 */       func_175839_b(worldIn, randomIn, structureBoundingBoxIn);
/*  773 */       func_175837_c(worldIn, randomIn, structureBoundingBoxIn);
/*  774 */       func_175841_d(worldIn, randomIn, structureBoundingBoxIn);
/*  775 */       func_175835_e(worldIn, randomIn, structureBoundingBoxIn);
/*  776 */       func_175842_f(worldIn, randomIn, structureBoundingBoxIn);
/*  777 */       func_175838_g(worldIn, randomIn, structureBoundingBoxIn);
/*      */       
/*  779 */       for (int j = 0; j < 7; j++) {
/*  780 */         int k = 0;
/*      */         
/*  782 */         while (k < 7) {
/*  783 */           if (k == 0 && j == 3) {
/*  784 */             k = 6;
/*      */           }
/*      */           
/*  787 */           int l = j * 9;
/*  788 */           int i1 = k * 9;
/*      */           
/*  790 */           for (int j1 = 0; j1 < 4; j1++) {
/*  791 */             for (int k1 = 0; k1 < 4; k1++) {
/*  792 */               setBlockState(worldIn, field_175826_b, l + j1, 0, i1 + k1, structureBoundingBoxIn);
/*  793 */               replaceAirAndLiquidDownwards(worldIn, field_175826_b, l + j1, -1, i1 + k1, structureBoundingBoxIn);
/*      */             } 
/*      */           } 
/*      */           
/*  797 */           if (j != 0 && j != 6) {
/*  798 */             k += 6; continue;
/*      */           } 
/*  800 */           k++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  805 */       for (int l1 = 0; l1 < 5; l1++) {
/*  806 */         func_181655_a(worldIn, structureBoundingBoxIn, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1, false);
/*  807 */         func_181655_a(worldIn, structureBoundingBoxIn, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1, false);
/*  808 */         func_181655_a(worldIn, structureBoundingBoxIn, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1, false);
/*  809 */         func_181655_a(worldIn, structureBoundingBoxIn, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1, false);
/*      */       } 
/*      */       
/*  812 */       for (StructureOceanMonumentPieces.Piece structureoceanmonumentpieces$piece : this.field_175843_q) {
/*  813 */         if (structureoceanmonumentpieces$piece.getBoundingBox().intersectsWith(structureBoundingBoxIn)) {
/*  814 */           structureoceanmonumentpieces$piece.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  818 */       return true;
/*      */     }
/*      */     
/*      */     private void func_175840_a(boolean p_175840_1_, int p_175840_2_, World worldIn, Random p_175840_4_, StructureBoundingBox p_175840_5_) {
/*  822 */       int i = 24;
/*      */       
/*  824 */       if (func_175818_a(p_175840_5_, p_175840_2_, 0, p_175840_2_ + 23, 20)) {
/*  825 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 0, 0, 0, p_175840_2_ + 24, 0, 20, field_175828_a, field_175828_a, false);
/*  826 */         func_181655_a(worldIn, p_175840_5_, p_175840_2_ + 0, 1, 0, p_175840_2_ + 24, 10, 20, false);
/*      */         
/*  828 */         for (int j = 0; j < 4; j++) {
/*  829 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j, j + 1, j, p_175840_2_ + j, j + 1, 20, field_175826_b, field_175826_b, false);
/*  830 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 7, j + 5, j + 7, p_175840_2_ + j + 7, j + 5, 20, field_175826_b, field_175826_b, false);
/*  831 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 17 - j, j + 5, j + 7, p_175840_2_ + 17 - j, j + 5, 20, field_175826_b, field_175826_b, false);
/*  832 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 24 - j, j + 1, j, p_175840_2_ + 24 - j, j + 1, 20, field_175826_b, field_175826_b, false);
/*  833 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 1, j + 1, j, p_175840_2_ + 23 - j, j + 1, j, field_175826_b, field_175826_b, false);
/*  834 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 8, j + 5, j + 7, p_175840_2_ + 16 - j, j + 5, j + 7, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/*  837 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 4, 4, 4, p_175840_2_ + 6, 4, 20, field_175828_a, field_175828_a, false);
/*  838 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 7, 4, 4, p_175840_2_ + 17, 4, 6, field_175828_a, field_175828_a, false);
/*  839 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 18, 4, 4, p_175840_2_ + 20, 4, 20, field_175828_a, field_175828_a, false);
/*  840 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 11, 8, 11, p_175840_2_ + 13, 8, 20, field_175828_a, field_175828_a, false);
/*  841 */         setBlockState(worldIn, field_175824_d, p_175840_2_ + 12, 9, 12, p_175840_5_);
/*  842 */         setBlockState(worldIn, field_175824_d, p_175840_2_ + 12, 9, 15, p_175840_5_);
/*  843 */         setBlockState(worldIn, field_175824_d, p_175840_2_ + 12, 9, 18, p_175840_5_);
/*  844 */         int j1 = p_175840_1_ ? (p_175840_2_ + 19) : (p_175840_2_ + 5);
/*  845 */         int k = p_175840_1_ ? (p_175840_2_ + 5) : (p_175840_2_ + 19);
/*      */         
/*  847 */         for (int l = 20; l >= 5; l -= 3) {
/*  848 */           setBlockState(worldIn, field_175824_d, j1, 5, l, p_175840_5_);
/*      */         }
/*      */         
/*  851 */         for (int k1 = 19; k1 >= 7; k1 -= 3) {
/*  852 */           setBlockState(worldIn, field_175824_d, k, 5, k1, p_175840_5_);
/*      */         }
/*      */         
/*  855 */         for (int l1 = 0; l1 < 4; l1++) {
/*  856 */           int i1 = p_175840_1_ ? (p_175840_2_ + 24 - 17 - l1 * 3) : (p_175840_2_ + 17 - l1 * 3);
/*  857 */           setBlockState(worldIn, field_175824_d, i1, 5, 5, p_175840_5_);
/*      */         } 
/*      */         
/*  860 */         setBlockState(worldIn, field_175824_d, k, 5, 5, p_175840_5_);
/*  861 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 11, 1, 12, p_175840_2_ + 13, 7, 12, field_175828_a, field_175828_a, false);
/*  862 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 12, 1, 11, p_175840_2_ + 12, 7, 13, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175839_b(World worldIn, Random p_175839_2_, StructureBoundingBox p_175839_3_) {
/*  867 */       if (func_175818_a(p_175839_3_, 22, 5, 35, 17)) {
/*  868 */         func_181655_a(worldIn, p_175839_3_, 25, 0, 0, 32, 8, 20, false);
/*      */         
/*  870 */         for (int i = 0; i < 4; i++) {
/*  871 */           fillWithBlocks(worldIn, p_175839_3_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, field_175826_b, field_175826_b, false);
/*  872 */           fillWithBlocks(worldIn, p_175839_3_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, field_175826_b, field_175826_b, false);
/*  873 */           setBlockState(worldIn, field_175826_b, 25, 5, 5 + i * 4, p_175839_3_);
/*  874 */           setBlockState(worldIn, field_175826_b, 26, 6, 5 + i * 4, p_175839_3_);
/*  875 */           setBlockState(worldIn, field_175825_e, 26, 5, 5 + i * 4, p_175839_3_);
/*  876 */           fillWithBlocks(worldIn, p_175839_3_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, field_175826_b, field_175826_b, false);
/*  877 */           fillWithBlocks(worldIn, p_175839_3_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, field_175826_b, field_175826_b, false);
/*  878 */           setBlockState(worldIn, field_175826_b, 32, 5, 5 + i * 4, p_175839_3_);
/*  879 */           setBlockState(worldIn, field_175826_b, 31, 6, 5 + i * 4, p_175839_3_);
/*  880 */           setBlockState(worldIn, field_175825_e, 31, 5, 5 + i * 4, p_175839_3_);
/*  881 */           fillWithBlocks(worldIn, p_175839_3_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, field_175828_a, field_175828_a, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175837_c(World worldIn, Random p_175837_2_, StructureBoundingBox p_175837_3_) {
/*  887 */       if (func_175818_a(p_175837_3_, 15, 20, 42, 21)) {
/*  888 */         fillWithBlocks(worldIn, p_175837_3_, 15, 0, 21, 42, 0, 21, field_175828_a, field_175828_a, false);
/*  889 */         func_181655_a(worldIn, p_175837_3_, 26, 1, 21, 31, 3, 21, false);
/*  890 */         fillWithBlocks(worldIn, p_175837_3_, 21, 12, 21, 36, 12, 21, field_175828_a, field_175828_a, false);
/*  891 */         fillWithBlocks(worldIn, p_175837_3_, 17, 11, 21, 40, 11, 21, field_175828_a, field_175828_a, false);
/*  892 */         fillWithBlocks(worldIn, p_175837_3_, 16, 10, 21, 41, 10, 21, field_175828_a, field_175828_a, false);
/*  893 */         fillWithBlocks(worldIn, p_175837_3_, 15, 7, 21, 42, 9, 21, field_175828_a, field_175828_a, false);
/*  894 */         fillWithBlocks(worldIn, p_175837_3_, 16, 6, 21, 41, 6, 21, field_175828_a, field_175828_a, false);
/*  895 */         fillWithBlocks(worldIn, p_175837_3_, 17, 5, 21, 40, 5, 21, field_175828_a, field_175828_a, false);
/*  896 */         fillWithBlocks(worldIn, p_175837_3_, 21, 4, 21, 36, 4, 21, field_175828_a, field_175828_a, false);
/*  897 */         fillWithBlocks(worldIn, p_175837_3_, 22, 3, 21, 26, 3, 21, field_175828_a, field_175828_a, false);
/*  898 */         fillWithBlocks(worldIn, p_175837_3_, 31, 3, 21, 35, 3, 21, field_175828_a, field_175828_a, false);
/*  899 */         fillWithBlocks(worldIn, p_175837_3_, 23, 2, 21, 25, 2, 21, field_175828_a, field_175828_a, false);
/*  900 */         fillWithBlocks(worldIn, p_175837_3_, 32, 2, 21, 34, 2, 21, field_175828_a, field_175828_a, false);
/*  901 */         fillWithBlocks(worldIn, p_175837_3_, 28, 4, 20, 29, 4, 21, field_175826_b, field_175826_b, false);
/*  902 */         setBlockState(worldIn, field_175826_b, 27, 3, 21, p_175837_3_);
/*  903 */         setBlockState(worldIn, field_175826_b, 30, 3, 21, p_175837_3_);
/*  904 */         setBlockState(worldIn, field_175826_b, 26, 2, 21, p_175837_3_);
/*  905 */         setBlockState(worldIn, field_175826_b, 31, 2, 21, p_175837_3_);
/*  906 */         setBlockState(worldIn, field_175826_b, 25, 1, 21, p_175837_3_);
/*  907 */         setBlockState(worldIn, field_175826_b, 32, 1, 21, p_175837_3_);
/*      */         
/*  909 */         for (int i = 0; i < 7; i++) {
/*  910 */           setBlockState(worldIn, field_175827_c, 28 - i, 6 + i, 21, p_175837_3_);
/*  911 */           setBlockState(worldIn, field_175827_c, 29 + i, 6 + i, 21, p_175837_3_);
/*      */         } 
/*      */         
/*  914 */         for (int j = 0; j < 4; j++) {
/*  915 */           setBlockState(worldIn, field_175827_c, 28 - j, 9 + j, 21, p_175837_3_);
/*  916 */           setBlockState(worldIn, field_175827_c, 29 + j, 9 + j, 21, p_175837_3_);
/*      */         } 
/*      */         
/*  919 */         setBlockState(worldIn, field_175827_c, 28, 12, 21, p_175837_3_);
/*  920 */         setBlockState(worldIn, field_175827_c, 29, 12, 21, p_175837_3_);
/*      */         
/*  922 */         for (int k = 0; k < 3; k++) {
/*  923 */           setBlockState(worldIn, field_175827_c, 22 - k * 2, 8, 21, p_175837_3_);
/*  924 */           setBlockState(worldIn, field_175827_c, 22 - k * 2, 9, 21, p_175837_3_);
/*  925 */           setBlockState(worldIn, field_175827_c, 35 + k * 2, 8, 21, p_175837_3_);
/*  926 */           setBlockState(worldIn, field_175827_c, 35 + k * 2, 9, 21, p_175837_3_);
/*      */         } 
/*      */         
/*  929 */         func_181655_a(worldIn, p_175837_3_, 15, 13, 21, 42, 15, 21, false);
/*  930 */         func_181655_a(worldIn, p_175837_3_, 15, 1, 21, 15, 6, 21, false);
/*  931 */         func_181655_a(worldIn, p_175837_3_, 16, 1, 21, 16, 5, 21, false);
/*  932 */         func_181655_a(worldIn, p_175837_3_, 17, 1, 21, 20, 4, 21, false);
/*  933 */         func_181655_a(worldIn, p_175837_3_, 21, 1, 21, 21, 3, 21, false);
/*  934 */         func_181655_a(worldIn, p_175837_3_, 22, 1, 21, 22, 2, 21, false);
/*  935 */         func_181655_a(worldIn, p_175837_3_, 23, 1, 21, 24, 1, 21, false);
/*  936 */         func_181655_a(worldIn, p_175837_3_, 42, 1, 21, 42, 6, 21, false);
/*  937 */         func_181655_a(worldIn, p_175837_3_, 41, 1, 21, 41, 5, 21, false);
/*  938 */         func_181655_a(worldIn, p_175837_3_, 37, 1, 21, 40, 4, 21, false);
/*  939 */         func_181655_a(worldIn, p_175837_3_, 36, 1, 21, 36, 3, 21, false);
/*  940 */         func_181655_a(worldIn, p_175837_3_, 33, 1, 21, 34, 1, 21, false);
/*  941 */         func_181655_a(worldIn, p_175837_3_, 35, 1, 21, 35, 2, 21, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175841_d(World worldIn, Random p_175841_2_, StructureBoundingBox p_175841_3_) {
/*  946 */       if (func_175818_a(p_175841_3_, 21, 21, 36, 36)) {
/*  947 */         fillWithBlocks(worldIn, p_175841_3_, 21, 0, 22, 36, 0, 36, field_175828_a, field_175828_a, false);
/*  948 */         func_181655_a(worldIn, p_175841_3_, 21, 1, 22, 36, 23, 36, false);
/*      */         
/*  950 */         for (int i = 0; i < 4; i++) {
/*  951 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, field_175826_b, field_175826_b, false);
/*  952 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, field_175826_b, field_175826_b, false);
/*  953 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, field_175826_b, field_175826_b, false);
/*  954 */           fillWithBlocks(worldIn, p_175841_3_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/*  957 */         fillWithBlocks(worldIn, p_175841_3_, 25, 16, 25, 32, 16, 32, field_175828_a, field_175828_a, false);
/*  958 */         fillWithBlocks(worldIn, p_175841_3_, 25, 17, 25, 25, 19, 25, field_175826_b, field_175826_b, false);
/*  959 */         fillWithBlocks(worldIn, p_175841_3_, 32, 17, 25, 32, 19, 25, field_175826_b, field_175826_b, false);
/*  960 */         fillWithBlocks(worldIn, p_175841_3_, 25, 17, 32, 25, 19, 32, field_175826_b, field_175826_b, false);
/*  961 */         fillWithBlocks(worldIn, p_175841_3_, 32, 17, 32, 32, 19, 32, field_175826_b, field_175826_b, false);
/*  962 */         setBlockState(worldIn, field_175826_b, 26, 20, 26, p_175841_3_);
/*  963 */         setBlockState(worldIn, field_175826_b, 27, 21, 27, p_175841_3_);
/*  964 */         setBlockState(worldIn, field_175825_e, 27, 20, 27, p_175841_3_);
/*  965 */         setBlockState(worldIn, field_175826_b, 26, 20, 31, p_175841_3_);
/*  966 */         setBlockState(worldIn, field_175826_b, 27, 21, 30, p_175841_3_);
/*  967 */         setBlockState(worldIn, field_175825_e, 27, 20, 30, p_175841_3_);
/*  968 */         setBlockState(worldIn, field_175826_b, 31, 20, 31, p_175841_3_);
/*  969 */         setBlockState(worldIn, field_175826_b, 30, 21, 30, p_175841_3_);
/*  970 */         setBlockState(worldIn, field_175825_e, 30, 20, 30, p_175841_3_);
/*  971 */         setBlockState(worldIn, field_175826_b, 31, 20, 26, p_175841_3_);
/*  972 */         setBlockState(worldIn, field_175826_b, 30, 21, 27, p_175841_3_);
/*  973 */         setBlockState(worldIn, field_175825_e, 30, 20, 27, p_175841_3_);
/*  974 */         fillWithBlocks(worldIn, p_175841_3_, 28, 21, 27, 29, 21, 27, field_175828_a, field_175828_a, false);
/*  975 */         fillWithBlocks(worldIn, p_175841_3_, 27, 21, 28, 27, 21, 29, field_175828_a, field_175828_a, false);
/*  976 */         fillWithBlocks(worldIn, p_175841_3_, 28, 21, 30, 29, 21, 30, field_175828_a, field_175828_a, false);
/*  977 */         fillWithBlocks(worldIn, p_175841_3_, 30, 21, 28, 30, 21, 29, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175835_e(World worldIn, Random p_175835_2_, StructureBoundingBox p_175835_3_) {
/*  982 */       if (func_175818_a(p_175835_3_, 0, 21, 6, 58)) {
/*  983 */         fillWithBlocks(worldIn, p_175835_3_, 0, 0, 21, 6, 0, 57, field_175828_a, field_175828_a, false);
/*  984 */         func_181655_a(worldIn, p_175835_3_, 0, 1, 21, 6, 7, 57, false);
/*  985 */         fillWithBlocks(worldIn, p_175835_3_, 4, 4, 21, 6, 4, 53, field_175828_a, field_175828_a, false);
/*      */         
/*  987 */         for (int i = 0; i < 4; i++) {
/*  988 */           fillWithBlocks(worldIn, p_175835_3_, i, i + 1, 21, i, i + 1, 57 - i, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/*  991 */         for (int j = 23; j < 53; j += 3) {
/*  992 */           setBlockState(worldIn, field_175824_d, 5, 5, j, p_175835_3_);
/*      */         }
/*      */         
/*  995 */         setBlockState(worldIn, field_175824_d, 5, 5, 52, p_175835_3_);
/*      */         
/*  997 */         for (int k = 0; k < 4; k++) {
/*  998 */           fillWithBlocks(worldIn, p_175835_3_, k, k + 1, 21, k, k + 1, 57 - k, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1001 */         fillWithBlocks(worldIn, p_175835_3_, 4, 1, 52, 6, 3, 52, field_175828_a, field_175828_a, false);
/* 1002 */         fillWithBlocks(worldIn, p_175835_3_, 5, 1, 51, 5, 3, 53, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */       
/* 1005 */       if (func_175818_a(p_175835_3_, 51, 21, 58, 58)) {
/* 1006 */         fillWithBlocks(worldIn, p_175835_3_, 51, 0, 21, 57, 0, 57, field_175828_a, field_175828_a, false);
/* 1007 */         func_181655_a(worldIn, p_175835_3_, 51, 1, 21, 57, 7, 57, false);
/* 1008 */         fillWithBlocks(worldIn, p_175835_3_, 51, 4, 21, 53, 4, 53, field_175828_a, field_175828_a, false);
/*      */         
/* 1010 */         for (int l = 0; l < 4; l++) {
/* 1011 */           fillWithBlocks(worldIn, p_175835_3_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1014 */         for (int i1 = 23; i1 < 53; i1 += 3) {
/* 1015 */           setBlockState(worldIn, field_175824_d, 52, 5, i1, p_175835_3_);
/*      */         }
/*      */         
/* 1018 */         setBlockState(worldIn, field_175824_d, 52, 5, 52, p_175835_3_);
/* 1019 */         fillWithBlocks(worldIn, p_175835_3_, 51, 1, 52, 53, 3, 52, field_175828_a, field_175828_a, false);
/* 1020 */         fillWithBlocks(worldIn, p_175835_3_, 52, 1, 51, 52, 3, 53, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */       
/* 1023 */       if (func_175818_a(p_175835_3_, 0, 51, 57, 57)) {
/* 1024 */         fillWithBlocks(worldIn, p_175835_3_, 7, 0, 51, 50, 0, 57, field_175828_a, field_175828_a, false);
/* 1025 */         func_181655_a(worldIn, p_175835_3_, 7, 1, 51, 50, 10, 57, false);
/*      */         
/* 1027 */         for (int j1 = 0; j1 < 4; j1++) {
/* 1028 */           fillWithBlocks(worldIn, p_175835_3_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, field_175826_b, field_175826_b, false);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175842_f(World worldIn, Random p_175842_2_, StructureBoundingBox p_175842_3_) {
/* 1034 */       if (func_175818_a(p_175842_3_, 7, 21, 13, 50)) {
/* 1035 */         fillWithBlocks(worldIn, p_175842_3_, 7, 0, 21, 13, 0, 50, field_175828_a, field_175828_a, false);
/* 1036 */         func_181655_a(worldIn, p_175842_3_, 7, 1, 21, 13, 10, 50, false);
/* 1037 */         fillWithBlocks(worldIn, p_175842_3_, 11, 8, 21, 13, 8, 53, field_175828_a, field_175828_a, false);
/*      */         
/* 1039 */         for (int i = 0; i < 4; i++) {
/* 1040 */           fillWithBlocks(worldIn, p_175842_3_, i + 7, i + 5, 21, i + 7, i + 5, 54, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1043 */         for (int j = 21; j <= 45; j += 3) {
/* 1044 */           setBlockState(worldIn, field_175824_d, 12, 9, j, p_175842_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1048 */       if (func_175818_a(p_175842_3_, 44, 21, 50, 54)) {
/* 1049 */         fillWithBlocks(worldIn, p_175842_3_, 44, 0, 21, 50, 0, 50, field_175828_a, field_175828_a, false);
/* 1050 */         func_181655_a(worldIn, p_175842_3_, 44, 1, 21, 50, 10, 50, false);
/* 1051 */         fillWithBlocks(worldIn, p_175842_3_, 44, 8, 21, 46, 8, 53, field_175828_a, field_175828_a, false);
/*      */         
/* 1053 */         for (int k = 0; k < 4; k++) {
/* 1054 */           fillWithBlocks(worldIn, p_175842_3_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1057 */         for (int l = 21; l <= 45; l += 3) {
/* 1058 */           setBlockState(worldIn, field_175824_d, 45, 9, l, p_175842_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1062 */       if (func_175818_a(p_175842_3_, 8, 44, 49, 54)) {
/* 1063 */         fillWithBlocks(worldIn, p_175842_3_, 14, 0, 44, 43, 0, 50, field_175828_a, field_175828_a, false);
/* 1064 */         func_181655_a(worldIn, p_175842_3_, 14, 1, 44, 43, 10, 50, false);
/*      */         
/* 1066 */         for (int i1 = 12; i1 <= 45; i1 += 3) {
/* 1067 */           setBlockState(worldIn, field_175824_d, i1, 9, 45, p_175842_3_);
/* 1068 */           setBlockState(worldIn, field_175824_d, i1, 9, 52, p_175842_3_);
/*      */           
/* 1070 */           if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45) {
/* 1071 */             setBlockState(worldIn, field_175824_d, i1, 9, 47, p_175842_3_);
/* 1072 */             setBlockState(worldIn, field_175824_d, i1, 9, 50, p_175842_3_);
/* 1073 */             setBlockState(worldIn, field_175824_d, i1, 10, 45, p_175842_3_);
/* 1074 */             setBlockState(worldIn, field_175824_d, i1, 10, 46, p_175842_3_);
/* 1075 */             setBlockState(worldIn, field_175824_d, i1, 10, 51, p_175842_3_);
/* 1076 */             setBlockState(worldIn, field_175824_d, i1, 10, 52, p_175842_3_);
/* 1077 */             setBlockState(worldIn, field_175824_d, i1, 11, 47, p_175842_3_);
/* 1078 */             setBlockState(worldIn, field_175824_d, i1, 11, 50, p_175842_3_);
/* 1079 */             setBlockState(worldIn, field_175824_d, i1, 12, 48, p_175842_3_);
/* 1080 */             setBlockState(worldIn, field_175824_d, i1, 12, 49, p_175842_3_);
/*      */           } 
/*      */         } 
/*      */         
/* 1084 */         for (int j1 = 0; j1 < 3; j1++) {
/* 1085 */           fillWithBlocks(worldIn, p_175842_3_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, field_175828_a, field_175828_a, false);
/*      */         }
/*      */         
/* 1088 */         fillWithBlocks(worldIn, p_175842_3_, 11, 8, 54, 46, 8, 54, field_175826_b, field_175826_b, false);
/* 1089 */         fillWithBlocks(worldIn, p_175842_3_, 14, 8, 44, 43, 8, 53, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void func_175838_g(World worldIn, Random p_175838_2_, StructureBoundingBox p_175838_3_) {
/* 1094 */       if (func_175818_a(p_175838_3_, 14, 21, 20, 43)) {
/* 1095 */         fillWithBlocks(worldIn, p_175838_3_, 14, 0, 21, 20, 0, 43, field_175828_a, field_175828_a, false);
/* 1096 */         func_181655_a(worldIn, p_175838_3_, 14, 1, 22, 20, 14, 43, false);
/* 1097 */         fillWithBlocks(worldIn, p_175838_3_, 18, 12, 22, 20, 12, 39, field_175828_a, field_175828_a, false);
/* 1098 */         fillWithBlocks(worldIn, p_175838_3_, 18, 12, 21, 20, 12, 21, field_175826_b, field_175826_b, false);
/*      */         
/* 1100 */         for (int i = 0; i < 4; i++) {
/* 1101 */           fillWithBlocks(worldIn, p_175838_3_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1104 */         for (int j = 23; j <= 39; j += 3) {
/* 1105 */           setBlockState(worldIn, field_175824_d, 19, 13, j, p_175838_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1109 */       if (func_175818_a(p_175838_3_, 37, 21, 43, 43)) {
/* 1110 */         fillWithBlocks(worldIn, p_175838_3_, 37, 0, 21, 43, 0, 43, field_175828_a, field_175828_a, false);
/* 1111 */         func_181655_a(worldIn, p_175838_3_, 37, 1, 22, 43, 14, 43, false);
/* 1112 */         fillWithBlocks(worldIn, p_175838_3_, 37, 12, 22, 39, 12, 39, field_175828_a, field_175828_a, false);
/* 1113 */         fillWithBlocks(worldIn, p_175838_3_, 37, 12, 21, 39, 12, 21, field_175826_b, field_175826_b, false);
/*      */         
/* 1115 */         for (int k = 0; k < 4; k++) {
/* 1116 */           fillWithBlocks(worldIn, p_175838_3_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1119 */         for (int l = 23; l <= 39; l += 3) {
/* 1120 */           setBlockState(worldIn, field_175824_d, 38, 13, l, p_175838_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1124 */       if (func_175818_a(p_175838_3_, 15, 37, 42, 43)) {
/* 1125 */         fillWithBlocks(worldIn, p_175838_3_, 21, 0, 37, 36, 0, 43, field_175828_a, field_175828_a, false);
/* 1126 */         func_181655_a(worldIn, p_175838_3_, 21, 1, 37, 36, 14, 43, false);
/* 1127 */         fillWithBlocks(worldIn, p_175838_3_, 21, 12, 37, 36, 12, 39, field_175828_a, field_175828_a, false);
/*      */         
/* 1129 */         for (int i1 = 0; i1 < 4; i1++) {
/* 1130 */           fillWithBlocks(worldIn, p_175838_3_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1133 */         for (int j1 = 21; j1 <= 36; j1 += 3)
/* 1134 */           setBlockState(worldIn, field_175824_d, j1, 13, 38, p_175838_3_); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public MonumentBuilding() {}
/*      */   }
/*      */   
/*      */   public static class MonumentCoreRoom extends Piece {
/*      */     public MonumentCoreRoom() {}
/*      */     
/*      */     public MonumentCoreRoom(EnumFacing p_i45598_1_, StructureOceanMonumentPieces.RoomDefinition p_i45598_2_, Random p_i45598_3_) {
/* 1145 */       super(1, p_i45598_1_, p_i45598_2_, 2, 2, 2);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1149 */       func_175819_a(worldIn, structureBoundingBoxIn, 1, 8, 0, 14, 8, 14, field_175828_a);
/* 1150 */       int i = 7;
/* 1151 */       IBlockState iblockstate = field_175826_b;
/* 1152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, 0, 0, i, 15, iblockstate, iblockstate, false);
/* 1153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, i, 0, 15, i, 15, iblockstate, iblockstate, false);
/* 1154 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 15, i, 0, iblockstate, iblockstate, false);
/* 1155 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 15, 14, i, 15, iblockstate, iblockstate, false);
/*      */       
/* 1157 */       for (i = 1; i <= 6; i++) {
/* 1158 */         iblockstate = field_175826_b;
/*      */         
/* 1160 */         if (i == 2 || i == 6) {
/* 1161 */           iblockstate = field_175828_a;
/*      */         }
/*      */         
/* 1164 */         for (int j = 0; j <= 15; j += 15) {
/* 1165 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, i, 0, j, i, 1, iblockstate, iblockstate, false);
/* 1166 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, i, 6, j, i, 9, iblockstate, iblockstate, false);
/* 1167 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, i, 14, j, i, 15, iblockstate, iblockstate, false);
/*      */         } 
/*      */         
/* 1170 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 1, i, 0, iblockstate, iblockstate, false);
/* 1171 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, i, 0, 9, i, 0, iblockstate, iblockstate, false);
/* 1172 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, i, 0, 14, i, 0, iblockstate, iblockstate, false);
/* 1173 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 15, 14, i, 15, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/* 1176 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 6, 9, 6, 9, field_175827_c, field_175827_c, false);
/* 1177 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 7, 8, 5, 8, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), false);
/*      */       
/* 1179 */       for (i = 3; i <= 6; i += 3) {
/* 1180 */         for (int k = 6; k <= 9; k += 3) {
/* 1181 */           setBlockState(worldIn, field_175825_e, k, i, 6, structureBoundingBoxIn);
/* 1182 */           setBlockState(worldIn, field_175825_e, k, i, 9, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1186 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 6, 5, 2, 6, field_175826_b, field_175826_b, false);
/* 1187 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 5, 2, 9, field_175826_b, field_175826_b, false);
/* 1188 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 6, 10, 2, 6, field_175826_b, field_175826_b, false);
/* 1189 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 9, 10, 2, 9, field_175826_b, field_175826_b, false);
/* 1190 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 5, 6, 2, 5, field_175826_b, field_175826_b, false);
/* 1191 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 5, 9, 2, 5, field_175826_b, field_175826_b, false);
/* 1192 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 10, 6, 2, 10, field_175826_b, field_175826_b, false);
/* 1193 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 10, 9, 2, 10, field_175826_b, field_175826_b, false);
/* 1194 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 5, 5, 6, 5, field_175826_b, field_175826_b, false);
/* 1195 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 10, 5, 6, 10, field_175826_b, field_175826_b, false);
/* 1196 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 2, 5, 10, 6, 5, field_175826_b, field_175826_b, false);
/* 1197 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 2, 10, 10, 6, 10, field_175826_b, field_175826_b, false);
/* 1198 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 1, 5, 7, 6, field_175826_b, field_175826_b, false);
/* 1199 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 7, 1, 10, 7, 6, field_175826_b, field_175826_b, false);
/* 1200 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 9, 5, 7, 14, field_175826_b, field_175826_b, false);
/* 1201 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 7, 9, 10, 7, 14, field_175826_b, field_175826_b, false);
/* 1202 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 5, 6, 7, 5, field_175826_b, field_175826_b, false);
/* 1203 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 10, 6, 7, 10, field_175826_b, field_175826_b, false);
/* 1204 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 7, 5, 14, 7, 5, field_175826_b, field_175826_b, false);
/* 1205 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 7, 10, 14, 7, 10, field_175826_b, field_175826_b, false);
/* 1206 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 2, 1, 3, field_175826_b, field_175826_b, false);
/* 1207 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 2, field_175826_b, field_175826_b, false);
/* 1208 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 2, 13, 1, 3, field_175826_b, field_175826_b, false);
/* 1209 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 2, 12, 1, 2, field_175826_b, field_175826_b, false);
/* 1210 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 12, 2, 1, 13, field_175826_b, field_175826_b, false);
/* 1211 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 13, 3, 1, 13, field_175826_b, field_175826_b, false);
/* 1212 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 12, 13, 1, 13, field_175826_b, field_175826_b, false);
/* 1213 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 13, 12, 1, 13, field_175826_b, field_175826_b, false);
/* 1214 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static interface MonumentRoomFitHelper {
/*      */     boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition param1RoomDefinition);
/*      */     
/*      */     StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing param1EnumFacing, StructureOceanMonumentPieces.RoomDefinition param1RoomDefinition, Random param1Random);
/*      */   }
/*      */   
/*      */   public static class Penthouse
/*      */     extends Piece {
/*      */     public Penthouse() {}
/*      */     
/*      */     public Penthouse(EnumFacing p_i45591_1_, StructureBoundingBox p_i45591_2_) {
/* 1229 */       super(p_i45591_1_, p_i45591_2_);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1233 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 2, 11, -1, 11, field_175826_b, field_175826_b, false);
/* 1234 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -1, 0, 1, -1, 11, field_175828_a, field_175828_a, false);
/* 1235 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, -1, 0, 13, -1, 11, field_175828_a, field_175828_a, false);
/* 1236 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 0, 11, -1, 1, field_175828_a, field_175828_a, false);
/* 1237 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 12, 11, -1, 13, field_175828_a, field_175828_a, false);
/* 1238 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 13, field_175826_b, field_175826_b, false);
/* 1239 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 0, 0, 13, 0, 13, field_175826_b, field_175826_b, false);
/* 1240 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 12, 0, 0, field_175826_b, field_175826_b, false);
/* 1241 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 13, 12, 0, 13, field_175826_b, field_175826_b, false);
/*      */       
/* 1243 */       for (int i = 2; i <= 11; i += 3) {
/* 1244 */         setBlockState(worldIn, field_175825_e, 0, 0, i, structureBoundingBoxIn);
/* 1245 */         setBlockState(worldIn, field_175825_e, 13, 0, i, structureBoundingBoxIn);
/* 1246 */         setBlockState(worldIn, field_175825_e, i, 0, 0, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1249 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 3, 4, 0, 9, field_175826_b, field_175826_b, false);
/* 1250 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 3, 11, 0, 9, field_175826_b, field_175826_b, false);
/* 1251 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 9, 0, 11, field_175826_b, field_175826_b, false);
/* 1252 */       setBlockState(worldIn, field_175826_b, 5, 0, 8, structureBoundingBoxIn);
/* 1253 */       setBlockState(worldIn, field_175826_b, 8, 0, 8, structureBoundingBoxIn);
/* 1254 */       setBlockState(worldIn, field_175826_b, 10, 0, 10, structureBoundingBoxIn);
/* 1255 */       setBlockState(worldIn, field_175826_b, 3, 0, 10, structureBoundingBoxIn);
/* 1256 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 3, 3, 0, 7, field_175827_c, field_175827_c, false);
/* 1257 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 3, 10, 0, 7, field_175827_c, field_175827_c, false);
/* 1258 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 10, 7, 0, 10, field_175827_c, field_175827_c, false);
/* 1259 */       int l = 3;
/*      */       
/* 1261 */       for (int j = 0; j < 2; j++) {
/* 1262 */         for (int k = 2; k <= 8; k += 3) {
/* 1263 */           fillWithBlocks(worldIn, structureBoundingBoxIn, l, 0, k, l, 2, k, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1266 */         l = 10;
/*      */       } 
/*      */       
/* 1269 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 10, 5, 2, 10, field_175826_b, field_175826_b, false);
/* 1270 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 10, 8, 2, 10, field_175826_b, field_175826_b, false);
/* 1271 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, -1, 7, 7, -1, 8, field_175827_c, field_175827_c, false);
/* 1272 */       func_181655_a(worldIn, structureBoundingBoxIn, 6, -1, 3, 7, -1, 4, false);
/* 1273 */       func_175817_a(worldIn, structureBoundingBoxIn, 6, 1, 6);
/* 1274 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class Piece extends StructureComponent {
/* 1279 */     protected static final IBlockState field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGH_META);
/* 1280 */     protected static final IBlockState field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKS_META);
/* 1281 */     protected static final IBlockState field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARK_META);
/* 1282 */     protected static final IBlockState field_175824_d = field_175826_b;
/* 1283 */     protected static final IBlockState field_175825_e = Blocks.sea_lantern.getDefaultState();
/* 1284 */     protected static final IBlockState field_175822_f = Blocks.water.getDefaultState();
/* 1285 */     protected static final int field_175823_g = func_175820_a(2, 0, 0);
/* 1286 */     protected static final int field_175831_h = func_175820_a(2, 2, 0);
/* 1287 */     protected static final int field_175832_i = func_175820_a(0, 1, 0);
/* 1288 */     protected static final int field_175829_j = func_175820_a(4, 1, 0);
/*      */     protected StructureOceanMonumentPieces.RoomDefinition field_175830_k;
/*      */     
/*      */     protected static final int func_175820_a(int p_175820_0_, int p_175820_1_, int p_175820_2_) {
/* 1292 */       return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
/*      */     }
/*      */     
/*      */     public Piece() {
/* 1296 */       super(0);
/*      */     }
/*      */     
/*      */     public Piece(int p_i45588_1_) {
/* 1300 */       super(p_i45588_1_);
/*      */     }
/*      */     
/*      */     public Piece(EnumFacing p_i45589_1_, StructureBoundingBox p_i45589_2_) {
/* 1304 */       super(1);
/* 1305 */       this.coordBaseMode = p_i45589_1_;
/* 1306 */       this.boundingBox = p_i45589_2_;
/*      */     }
/*      */     
/*      */     protected Piece(int p_i45590_1_, EnumFacing p_i45590_2_, StructureOceanMonumentPieces.RoomDefinition p_i45590_3_, int p_i45590_4_, int p_i45590_5_, int p_i45590_6_) {
/* 1310 */       super(p_i45590_1_);
/* 1311 */       this.coordBaseMode = p_i45590_2_;
/* 1312 */       this.field_175830_k = p_i45590_3_;
/* 1313 */       int i = p_i45590_3_.field_175967_a;
/* 1314 */       int j = i % 5;
/* 1315 */       int k = i / 5 % 5;
/* 1316 */       int l = i / 25;
/*      */       
/* 1318 */       if (p_i45590_2_ != EnumFacing.NORTH && p_i45590_2_ != EnumFacing.SOUTH) {
/* 1319 */         this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_6_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_4_ * 8 - 1);
/*      */       } else {
/* 1321 */         this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_4_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_6_ * 8 - 1);
/*      */       } 
/*      */       
/* 1324 */       switch (p_i45590_2_) {
/*      */         case NORTH:
/* 1326 */           this.boundingBox.offset(j * 8, l * 4, -(k + p_i45590_6_) * 8 + 1);
/*      */           return;
/*      */         
/*      */         case SOUTH:
/* 1330 */           this.boundingBox.offset(j * 8, l * 4, k * 8);
/*      */           return;
/*      */         
/*      */         case WEST:
/* 1334 */           this.boundingBox.offset(-(k + p_i45590_6_) * 8 + 1, l * 4, j * 8);
/*      */           return;
/*      */       } 
/*      */       
/* 1338 */       this.boundingBox.offset(k * 8, l * 4, j * 8);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */     
/*      */     protected void func_181655_a(World p_181655_1_, StructureBoundingBox p_181655_2_, int p_181655_3_, int p_181655_4_, int p_181655_5_, int p_181655_6_, int p_181655_7_, int p_181655_8_, boolean p_181655_9_) {
/* 1349 */       for (int i = p_181655_4_; i <= p_181655_7_; i++) {
/* 1350 */         for (int j = p_181655_3_; j <= p_181655_6_; j++) {
/* 1351 */           for (int k = p_181655_5_; k <= p_181655_8_; k++) {
/* 1352 */             if (!p_181655_9_ || getBlockStateFromPos(p_181655_1_, j, i, k, p_181655_2_).getBlock().getMaterial() != Material.air) {
/* 1353 */               if (getYWithOffset(i) >= p_181655_1_.getSeaLevel()) {
/* 1354 */                 setBlockState(p_181655_1_, Blocks.air.getDefaultState(), j, i, k, p_181655_2_);
/*      */               } else {
/* 1356 */                 setBlockState(p_181655_1_, field_175822_f, j, i, k, p_181655_2_);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void func_175821_a(World worldIn, StructureBoundingBox p_175821_2_, int p_175821_3_, int p_175821_4_, boolean p_175821_5_) {
/* 1365 */       if (p_175821_5_) {
/* 1366 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 2, 0, p_175821_4_ + 8 - 1, field_175828_a, field_175828_a, false);
/* 1367 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, field_175828_a, field_175828_a, false);
/* 1368 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 0, p_175821_3_ + 4, 0, p_175821_4_ + 2, field_175828_a, field_175828_a, false);
/* 1369 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 8 - 1, field_175828_a, field_175828_a, false);
/* 1370 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 2, p_175821_3_ + 4, 0, p_175821_4_ + 2, field_175826_b, field_175826_b, false);
/* 1371 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 5, field_175826_b, field_175826_b, false);
/* 1372 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 2, 0, p_175821_4_ + 3, p_175821_3_ + 2, 0, p_175821_4_ + 4, field_175826_b, field_175826_b, false);
/* 1373 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 3, p_175821_3_ + 5, 0, p_175821_4_ + 4, field_175826_b, field_175826_b, false);
/*      */       } else {
/* 1375 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, field_175828_a, field_175828_a, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void func_175819_a(World worldIn, StructureBoundingBox p_175819_2_, int p_175819_3_, int p_175819_4_, int p_175819_5_, int p_175819_6_, int p_175819_7_, int p_175819_8_, IBlockState p_175819_9_) {
/* 1380 */       for (int i = p_175819_4_; i <= p_175819_7_; i++) {
/* 1381 */         for (int j = p_175819_3_; j <= p_175819_6_; j++) {
/* 1382 */           for (int k = p_175819_5_; k <= p_175819_8_; k++) {
/* 1383 */             if (getBlockStateFromPos(worldIn, j, i, k, p_175819_2_) == field_175822_f) {
/* 1384 */               setBlockState(worldIn, p_175819_9_, j, i, k, p_175819_2_);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected boolean func_175818_a(StructureBoundingBox p_175818_1_, int p_175818_2_, int p_175818_3_, int p_175818_4_, int p_175818_5_) {
/* 1392 */       int i = getXWithOffset(p_175818_2_, p_175818_3_);
/* 1393 */       int j = getZWithOffset(p_175818_2_, p_175818_3_);
/* 1394 */       int k = getXWithOffset(p_175818_4_, p_175818_5_);
/* 1395 */       int l = getZWithOffset(p_175818_4_, p_175818_5_);
/* 1396 */       return p_175818_1_.intersectsWith(Math.min(i, k), Math.min(j, l), Math.max(i, k), Math.max(j, l));
/*      */     }
/*      */     
/*      */     protected boolean func_175817_a(World worldIn, StructureBoundingBox p_175817_2_, int p_175817_3_, int p_175817_4_, int p_175817_5_) {
/* 1400 */       int i = getXWithOffset(p_175817_3_, p_175817_5_);
/* 1401 */       int j = getYWithOffset(p_175817_4_);
/* 1402 */       int k = getZWithOffset(p_175817_3_, p_175817_5_);
/*      */       
/* 1404 */       if (p_175817_2_.isVecInside((Vec3i)new BlockPos(i, j, k))) {
/* 1405 */         EntityGuardian entityguardian = new EntityGuardian(worldIn);
/* 1406 */         entityguardian.setElder(true);
/* 1407 */         entityguardian.heal(entityguardian.getMaxHealth());
/* 1408 */         entityguardian.setLocationAndAngles(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
/* 1409 */         entityguardian.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityguardian)), null);
/* 1410 */         worldIn.spawnEntityInWorld((Entity)entityguardian);
/* 1411 */         return true;
/*      */       } 
/* 1413 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   static class RoomDefinition
/*      */   {
/*      */     int field_175967_a;
/* 1420 */     RoomDefinition[] field_175965_b = new RoomDefinition[6];
/* 1421 */     boolean[] field_175966_c = new boolean[6];
/*      */     boolean field_175963_d;
/*      */     boolean field_175964_e;
/*      */     int field_175962_f;
/*      */     
/*      */     public RoomDefinition(int p_i45584_1_) {
/* 1427 */       this.field_175967_a = p_i45584_1_;
/*      */     }
/*      */     
/*      */     public void func_175957_a(EnumFacing p_175957_1_, RoomDefinition p_175957_2_) {
/* 1431 */       this.field_175965_b[p_175957_1_.getIndex()] = p_175957_2_;
/* 1432 */       p_175957_2_.field_175965_b[p_175957_1_.getOpposite().getIndex()] = this;
/*      */     }
/*      */     
/*      */     public void func_175958_a() {
/* 1436 */       for (int i = 0; i < 6; i++) {
/* 1437 */         this.field_175966_c[i] = (this.field_175965_b[i] != null);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean func_175959_a(int p_175959_1_) {
/* 1442 */       if (this.field_175964_e) {
/* 1443 */         return true;
/*      */       }
/* 1445 */       this.field_175962_f = p_175959_1_;
/*      */       
/* 1447 */       for (int i = 0; i < 6; i++) {
/* 1448 */         if (this.field_175965_b[i] != null && this.field_175966_c[i] && (this.field_175965_b[i]).field_175962_f != p_175959_1_ && this.field_175965_b[i].func_175959_a(p_175959_1_)) {
/* 1449 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1453 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_175961_b() {
/* 1458 */       return (this.field_175967_a >= 75);
/*      */     }
/*      */     
/*      */     public int func_175960_c() {
/* 1462 */       int i = 0;
/*      */       
/* 1464 */       for (int j = 0; j < 6; j++) {
/* 1465 */         if (this.field_175966_c[j]) {
/* 1466 */           i++;
/*      */         }
/*      */       } 
/*      */       
/* 1470 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SimpleRoom
/*      */     extends Piece {
/*      */     private int field_175833_o;
/*      */     
/*      */     public SimpleRoom() {}
/*      */     
/*      */     public SimpleRoom(EnumFacing p_i45587_1_, StructureOceanMonumentPieces.RoomDefinition p_i45587_2_, Random p_i45587_3_) {
/* 1481 */       super(1, p_i45587_1_, p_i45587_2_, 1, 1, 1);
/* 1482 */       this.field_175833_o = p_i45587_3_.nextInt(3);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1486 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/* 1487 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/* 1490 */       if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/* 1491 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 6, field_175828_a);
/*      */       }
/*      */       
/* 1494 */       boolean flag = (this.field_175833_o != 0 && randomIn.nextBoolean() && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()] && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()] && this.field_175830_k.func_175960_c() > 1);
/*      */       
/* 1496 */       if (this.field_175833_o == 0) {
/* 1497 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 2, 1, 2, field_175826_b, field_175826_b, false);
/* 1498 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 2, 3, 2, field_175826_b, field_175826_b, false);
/* 1499 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 2, field_175828_a, field_175828_a, false);
/* 1500 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 2, 2, 0, field_175828_a, field_175828_a, false);
/* 1501 */         setBlockState(worldIn, field_175825_e, 1, 2, 1, structureBoundingBoxIn);
/* 1502 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 7, 1, 2, field_175826_b, field_175826_b, false);
/* 1503 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 7, 3, 2, field_175826_b, field_175826_b, false);
/* 1504 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 2, field_175828_a, field_175828_a, false);
/* 1505 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
/* 1506 */         setBlockState(worldIn, field_175825_e, 6, 2, 1, structureBoundingBoxIn);
/* 1507 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 5, 2, 1, 7, field_175826_b, field_175826_b, false);
/* 1508 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 5, 2, 3, 7, field_175826_b, field_175826_b, false);
/* 1509 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 2, 7, field_175828_a, field_175828_a, false);
/* 1510 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 2, 2, 7, field_175828_a, field_175828_a, false);
/* 1511 */         setBlockState(worldIn, field_175825_e, 1, 2, 6, structureBoundingBoxIn);
/* 1512 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 1, 7, field_175826_b, field_175826_b, false);
/* 1513 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 5, 7, 3, 7, field_175826_b, field_175826_b, false);
/* 1514 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 5, 7, 2, 7, field_175828_a, field_175828_a, false);
/* 1515 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
/* 1516 */         setBlockState(worldIn, field_175825_e, 6, 2, 6, structureBoundingBoxIn);
/*      */         
/* 1518 */         if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/* 1519 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 0, 4, 3, 0, field_175826_b, field_175826_b, false);
/*      */         } else {
/* 1521 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 0, 4, 3, 1, field_175826_b, field_175826_b, false);
/* 1522 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 0, 4, 2, 0, field_175828_a, field_175828_a, false);
/* 1523 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 1, 1, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1526 */         if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/* 1527 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 7, 4, 3, 7, field_175826_b, field_175826_b, false);
/*      */         } else {
/* 1529 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 6, 4, 3, 7, field_175826_b, field_175826_b, false);
/* 1530 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 7, 4, 2, 7, field_175828_a, field_175828_a, false);
/* 1531 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 6, 4, 1, 7, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1534 */         if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
/* 1535 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 3, 4, field_175826_b, field_175826_b, false);
/*      */         } else {
/* 1537 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 1, 3, 4, field_175826_b, field_175826_b, false);
/* 1538 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 3, 0, 2, 4, field_175828_a, field_175828_a, false);
/* 1539 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 1, 1, 4, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1542 */         if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
/* 1543 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
/*      */         } else {
/* 1545 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
/* 1546 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 3, 7, 2, 4, field_175828_a, field_175828_a, false);
/* 1547 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 3, 7, 1, 4, field_175826_b, field_175826_b, false);
/*      */         } 
/* 1549 */       } else if (this.field_175833_o == 1) {
/* 1550 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 2, 3, 2, field_175826_b, field_175826_b, false);
/* 1551 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 5, 2, 3, 5, field_175826_b, field_175826_b, false);
/* 1552 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 5, 3, 5, field_175826_b, field_175826_b, false);
/* 1553 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 2, 5, 3, 2, field_175826_b, field_175826_b, false);
/* 1554 */         setBlockState(worldIn, field_175825_e, 2, 2, 2, structureBoundingBoxIn);
/* 1555 */         setBlockState(worldIn, field_175825_e, 2, 2, 5, structureBoundingBoxIn);
/* 1556 */         setBlockState(worldIn, field_175825_e, 5, 2, 5, structureBoundingBoxIn);
/* 1557 */         setBlockState(worldIn, field_175825_e, 5, 2, 2, structureBoundingBoxIn);
/* 1558 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 1, 3, 0, field_175826_b, field_175826_b, false);
/* 1559 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 1, field_175826_b, field_175826_b, false);
/* 1560 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 7, 1, 3, 7, field_175826_b, field_175826_b, false);
/* 1561 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 3, 6, field_175826_b, field_175826_b, false);
/* 1562 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
/* 1563 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 6, 7, 3, 6, field_175826_b, field_175826_b, false);
/* 1564 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
/* 1565 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 1, 7, 3, 1, field_175826_b, field_175826_b, false);
/* 1566 */         setBlockState(worldIn, field_175828_a, 1, 2, 0, structureBoundingBoxIn);
/* 1567 */         setBlockState(worldIn, field_175828_a, 0, 2, 1, structureBoundingBoxIn);
/* 1568 */         setBlockState(worldIn, field_175828_a, 1, 2, 7, structureBoundingBoxIn);
/* 1569 */         setBlockState(worldIn, field_175828_a, 0, 2, 6, structureBoundingBoxIn);
/* 1570 */         setBlockState(worldIn, field_175828_a, 6, 2, 7, structureBoundingBoxIn);
/* 1571 */         setBlockState(worldIn, field_175828_a, 7, 2, 6, structureBoundingBoxIn);
/* 1572 */         setBlockState(worldIn, field_175828_a, 6, 2, 0, structureBoundingBoxIn);
/* 1573 */         setBlockState(worldIn, field_175828_a, 7, 2, 1, structureBoundingBoxIn);
/*      */         
/* 1575 */         if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/* 1576 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
/* 1577 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
/* 1578 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1581 */         if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/* 1582 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
/* 1583 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
/* 1584 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1587 */         if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
/* 1588 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 3, 6, field_175826_b, field_175826_b, false);
/* 1589 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 2, 6, field_175828_a, field_175828_a, false);
/* 1590 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 1, 6, field_175826_b, field_175826_b, false);
/*      */         } 
/*      */         
/* 1593 */         if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
/* 1594 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 1, 7, 3, 6, field_175826_b, field_175826_b, false);
/* 1595 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 1, 7, 2, 6, field_175828_a, field_175828_a, false);
/* 1596 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 1, 7, 1, 6, field_175826_b, field_175826_b, false);
/*      */         } 
/* 1598 */       } else if (this.field_175833_o == 2) {
/* 1599 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
/* 1600 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
/* 1601 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
/* 1602 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
/* 1603 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
/* 1604 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
/* 1605 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
/* 1606 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
/* 1607 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
/* 1608 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
/* 1609 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
/* 1610 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
/* 1611 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
/* 1612 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
/* 1613 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
/* 1614 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
/*      */         
/* 1616 */         if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/* 1617 */           func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */         }
/*      */         
/* 1620 */         if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
/* 1621 */           func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */         }
/*      */         
/* 1624 */         if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
/* 1625 */           func_181655_a(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */         }
/*      */         
/* 1628 */         if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
/* 1629 */           func_181655_a(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */         }
/*      */       } 
/*      */       
/* 1633 */       if (flag) {
/* 1634 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 3, 4, 1, 4, field_175826_b, field_175826_b, false);
/* 1635 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 3, 4, 2, 4, field_175828_a, field_175828_a, false);
/* 1636 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 3, 4, 3, 4, field_175826_b, field_175826_b, false);
/*      */       } 
/*      */       
/* 1639 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SimpleTopRoom
/*      */     extends Piece {
/*      */     public SimpleTopRoom() {}
/*      */     
/*      */     public SimpleTopRoom(EnumFacing p_i45586_1_, StructureOceanMonumentPieces.RoomDefinition p_i45586_2_, Random p_i45586_3_) {
/* 1648 */       super(1, p_i45586_1_, p_i45586_2_, 1, 1, 1);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1652 */       if (this.field_175830_k.field_175967_a / 25 > 0) {
/* 1653 */         func_175821_a(worldIn, structureBoundingBoxIn, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/* 1656 */       if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
/* 1657 */         func_175819_a(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 6, field_175828_a);
/*      */       }
/*      */       
/* 1660 */       for (int i = 1; i <= 6; i++) {
/* 1661 */         for (int j = 1; j <= 6; j++) {
/* 1662 */           if (randomIn.nextInt(3) != 0) {
/* 1663 */             int k = 2 + ((randomIn.nextInt(4) == 0) ? 0 : 1);
/* 1664 */             fillWithBlocks(worldIn, structureBoundingBoxIn, i, k, j, i, 3, j, Blocks.sponge.getStateFromMeta(1), Blocks.sponge.getStateFromMeta(1), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1669 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
/* 1670 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
/* 1671 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
/* 1672 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
/* 1673 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
/* 1674 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
/* 1675 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
/* 1676 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
/* 1677 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
/* 1678 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
/* 1679 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
/* 1680 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
/* 1681 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
/* 1682 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
/* 1683 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
/* 1684 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
/*      */       
/* 1686 */       if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
/* 1687 */         func_181655_a(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/* 1690 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WingRoom
/*      */     extends Piece {
/*      */     private int field_175834_o;
/*      */     
/*      */     public WingRoom() {}
/*      */     
/*      */     public WingRoom(EnumFacing p_i45585_1_, StructureBoundingBox p_i45585_2_, int p_i45585_3_) {
/* 1701 */       super(p_i45585_1_, p_i45585_2_);
/* 1702 */       this.field_175834_o = p_i45585_3_ & 0x1;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1706 */       if (this.field_175834_o == 0) {
/* 1707 */         for (int i = 0; i < 4; i++) {
/* 1708 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, field_175826_b, field_175826_b, false);
/*      */         }
/*      */         
/* 1711 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 6, 15, 0, 16, field_175826_b, field_175826_b, false);
/* 1712 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 6, 6, 3, 20, field_175826_b, field_175826_b, false);
/* 1713 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 16, 0, 6, 16, 3, 20, field_175826_b, field_175826_b, false);
/* 1714 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 7, 7, 1, 20, field_175826_b, field_175826_b, false);
/* 1715 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 1, 7, 15, 1, 20, field_175826_b, field_175826_b, false);
/* 1716 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 6, 9, 3, 6, field_175826_b, field_175826_b, false);
/* 1717 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 6, 15, 3, 6, field_175826_b, field_175826_b, false);
/* 1718 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 7, 9, 1, 7, field_175826_b, field_175826_b, false);
/* 1719 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
/* 1720 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 5, 13, 0, 5, field_175826_b, field_175826_b, false);
/* 1721 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 7, 12, 0, 7, field_175827_c, field_175827_c, false);
/* 1722 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 10, 8, 0, 12, field_175827_c, field_175827_c, false);
/* 1723 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, 0, 10, 14, 0, 12, field_175827_c, field_175827_c, false);
/*      */         
/* 1725 */         for (int i1 = 18; i1 >= 7; i1 -= 3) {
/* 1726 */           setBlockState(worldIn, field_175825_e, 6, 3, i1, structureBoundingBoxIn);
/* 1727 */           setBlockState(worldIn, field_175825_e, 16, 3, i1, structureBoundingBoxIn);
/*      */         } 
/*      */         
/* 1730 */         setBlockState(worldIn, field_175825_e, 10, 0, 10, structureBoundingBoxIn);
/* 1731 */         setBlockState(worldIn, field_175825_e, 12, 0, 10, structureBoundingBoxIn);
/* 1732 */         setBlockState(worldIn, field_175825_e, 10, 0, 12, structureBoundingBoxIn);
/* 1733 */         setBlockState(worldIn, field_175825_e, 12, 0, 12, structureBoundingBoxIn);
/* 1734 */         setBlockState(worldIn, field_175825_e, 8, 3, 6, structureBoundingBoxIn);
/* 1735 */         setBlockState(worldIn, field_175825_e, 14, 3, 6, structureBoundingBoxIn);
/* 1736 */         setBlockState(worldIn, field_175826_b, 4, 2, 4, structureBoundingBoxIn);
/* 1737 */         setBlockState(worldIn, field_175825_e, 4, 1, 4, structureBoundingBoxIn);
/* 1738 */         setBlockState(worldIn, field_175826_b, 4, 0, 4, structureBoundingBoxIn);
/* 1739 */         setBlockState(worldIn, field_175826_b, 18, 2, 4, structureBoundingBoxIn);
/* 1740 */         setBlockState(worldIn, field_175825_e, 18, 1, 4, structureBoundingBoxIn);
/* 1741 */         setBlockState(worldIn, field_175826_b, 18, 0, 4, structureBoundingBoxIn);
/* 1742 */         setBlockState(worldIn, field_175826_b, 4, 2, 18, structureBoundingBoxIn);
/* 1743 */         setBlockState(worldIn, field_175825_e, 4, 1, 18, structureBoundingBoxIn);
/* 1744 */         setBlockState(worldIn, field_175826_b, 4, 0, 18, structureBoundingBoxIn);
/* 1745 */         setBlockState(worldIn, field_175826_b, 18, 2, 18, structureBoundingBoxIn);
/* 1746 */         setBlockState(worldIn, field_175825_e, 18, 1, 18, structureBoundingBoxIn);
/* 1747 */         setBlockState(worldIn, field_175826_b, 18, 0, 18, structureBoundingBoxIn);
/* 1748 */         setBlockState(worldIn, field_175826_b, 9, 7, 20, structureBoundingBoxIn);
/* 1749 */         setBlockState(worldIn, field_175826_b, 13, 7, 20, structureBoundingBoxIn);
/* 1750 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 21, 7, 4, 21, field_175826_b, field_175826_b, false);
/* 1751 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 0, 21, 16, 4, 21, field_175826_b, field_175826_b, false);
/* 1752 */         func_175817_a(worldIn, structureBoundingBoxIn, 11, 2, 16);
/* 1753 */       } else if (this.field_175834_o == 1) {
/* 1754 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 18, 13, 3, 20, field_175826_b, field_175826_b, false);
/* 1755 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 18, 9, 2, 18, field_175826_b, field_175826_b, false);
/* 1756 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 0, 18, 13, 2, 18, field_175826_b, field_175826_b, false);
/* 1757 */         int j1 = 9;
/* 1758 */         int j = 20;
/* 1759 */         int k = 5;
/*      */         
/* 1761 */         for (int l = 0; l < 2; l++) {
/* 1762 */           setBlockState(worldIn, field_175826_b, j1, k + 1, j, structureBoundingBoxIn);
/* 1763 */           setBlockState(worldIn, field_175825_e, j1, k, j, structureBoundingBoxIn);
/* 1764 */           setBlockState(worldIn, field_175826_b, j1, k - 1, j, structureBoundingBoxIn);
/* 1765 */           j1 = 13;
/*      */         } 
/*      */         
/* 1768 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 7, 15, 3, 14, field_175826_b, field_175826_b, false);
/* 1769 */         j1 = 10;
/*      */         
/* 1771 */         for (int k1 = 0; k1 < 2; k1++) {
/* 1772 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 10, j1, 6, 10, field_175826_b, field_175826_b, false);
/* 1773 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 12, j1, 6, 12, field_175826_b, field_175826_b, false);
/* 1774 */           setBlockState(worldIn, field_175825_e, j1, 0, 10, structureBoundingBoxIn);
/* 1775 */           setBlockState(worldIn, field_175825_e, j1, 0, 12, structureBoundingBoxIn);
/* 1776 */           setBlockState(worldIn, field_175825_e, j1, 4, 10, structureBoundingBoxIn);
/* 1777 */           setBlockState(worldIn, field_175825_e, j1, 4, 12, structureBoundingBoxIn);
/* 1778 */           j1 = 12;
/*      */         } 
/*      */         
/* 1781 */         j1 = 8;
/*      */         
/* 1783 */         for (int l1 = 0; l1 < 2; l1++) {
/* 1784 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 7, j1, 2, 7, field_175826_b, field_175826_b, false);
/* 1785 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 14, j1, 2, 14, field_175826_b, field_175826_b, false);
/* 1786 */           j1 = 14;
/*      */         } 
/*      */         
/* 1789 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 8, 8, 3, 13, field_175827_c, field_175827_c, false);
/* 1790 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, 3, 8, 14, 3, 13, field_175827_c, field_175827_c, false);
/* 1791 */         func_175817_a(worldIn, structureBoundingBoxIn, 11, 5, 13);
/*      */       } 
/*      */       
/* 1794 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class XDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private XDoubleRoomFitHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/* 1803 */       return (p_175969_1_.field_175966_c[EnumFacing.EAST.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.EAST.getIndex()]).field_175963_d);
/*      */     }
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 1807 */       p_175968_2_.field_175963_d = true;
/* 1808 */       (p_175968_2_.field_175965_b[EnumFacing.EAST.getIndex()]).field_175963_d = true;
/* 1809 */       return new StructureOceanMonumentPieces.DoubleXRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class XYDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private XYDoubleRoomFitHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/* 1818 */       if (p_175969_1_.field_175966_c[EnumFacing.EAST.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.EAST.getIndex()]).field_175963_d && p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d) {
/* 1819 */         StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = p_175969_1_.field_175965_b[EnumFacing.EAST.getIndex()];
/* 1820 */         return (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.UP.getIndex()] && !(structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d);
/*      */       } 
/* 1822 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 1827 */       p_175968_2_.field_175963_d = true;
/* 1828 */       (p_175968_2_.field_175965_b[EnumFacing.EAST.getIndex()]).field_175963_d = true;
/* 1829 */       (p_175968_2_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/* 1830 */       ((p_175968_2_.field_175965_b[EnumFacing.EAST.getIndex()]).field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/* 1831 */       return new StructureOceanMonumentPieces.DoubleXYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class YDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private YDoubleRoomFitHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/* 1840 */       return (p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d);
/*      */     }
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 1844 */       p_175968_2_.field_175963_d = true;
/* 1845 */       (p_175968_2_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/* 1846 */       return new StructureOceanMonumentPieces.DoubleYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class YZDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private YZDoubleRoomFitHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/* 1855 */       if (p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d && p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d) {
/* 1856 */         StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()];
/* 1857 */         return (structureoceanmonumentpieces$roomdefinition.field_175966_c[EnumFacing.UP.getIndex()] && !(structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d);
/*      */       } 
/* 1859 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 1864 */       p_175968_2_.field_175963_d = true;
/* 1865 */       (p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d = true;
/* 1866 */       (p_175968_2_.field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/* 1867 */       ((p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175965_b[EnumFacing.UP.getIndex()]).field_175963_d = true;
/* 1868 */       return new StructureOceanMonumentPieces.DoubleYZRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class ZDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper {
/*      */     private ZDoubleRoomFitHelper() {}
/*      */     
/*      */     public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition p_175969_1_) {
/* 1877 */       return (p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !(p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d);
/*      */     }
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 1881 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = p_175968_2_;
/*      */       
/* 1883 */       if (!p_175968_2_.field_175966_c[EnumFacing.NORTH.getIndex()] || (p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d) {
/* 1884 */         structureoceanmonumentpieces$roomdefinition = p_175968_2_.field_175965_b[EnumFacing.SOUTH.getIndex()];
/*      */       }
/*      */       
/* 1887 */       structureoceanmonumentpieces$roomdefinition.field_175963_d = true;
/* 1888 */       (structureoceanmonumentpieces$roomdefinition.field_175965_b[EnumFacing.NORTH.getIndex()]).field_175963_d = true;
/* 1889 */       return new StructureOceanMonumentPieces.DoubleZRoom(p_175968_1_, structureoceanmonumentpieces$roomdefinition, p_175968_3_);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureOceanMonumentPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */