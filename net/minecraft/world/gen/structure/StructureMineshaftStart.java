/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class StructureMineshaftStart
/*    */   extends StructureStart
/*    */ {
/*    */   public StructureMineshaftStart() {}
/*    */   
/*    */   public StructureMineshaftStart(World worldIn, Random rand, int chunkX, int chunkZ) {
/* 12 */     super(chunkX, chunkZ);
/* 13 */     StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
/* 14 */     this.components.add(structuremineshaftpieces$room);
/* 15 */     structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, rand);
/* 16 */     updateBoundingBox();
/* 17 */     markAvailableHeight(worldIn, rand, 10);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureMineshaftStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */