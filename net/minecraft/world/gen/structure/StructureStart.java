/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class StructureStart {
/*  13 */   protected LinkedList<StructureComponent> components = new LinkedList<>();
/*     */   
/*     */   protected StructureBoundingBox boundingBox;
/*     */   private int chunkPosX;
/*     */   private int chunkPosZ;
/*     */   
/*     */   public StructureStart() {}
/*     */   
/*     */   public StructureStart(int chunkX, int chunkZ) {
/*  22 */     this.chunkPosX = chunkX;
/*  23 */     this.chunkPosZ = chunkZ;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/*  27 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   public LinkedList<StructureComponent> getComponents() {
/*  31 */     return this.components;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/*  38 */     Iterator<StructureComponent> iterator = this.components.iterator();
/*     */     
/*  40 */     while (iterator.hasNext()) {
/*  41 */       StructureComponent structurecomponent = iterator.next();
/*     */       
/*  43 */       if (structurecomponent.getBoundingBox().intersectsWith(structurebb) && !structurecomponent.addComponentParts(worldIn, rand, structurebb)) {
/*  44 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateBoundingBox() {
/*  53 */     this.boundingBox = StructureBoundingBox.getNewBoundingBox();
/*     */     
/*  55 */     for (StructureComponent structurecomponent : this.components) {
/*  56 */       this.boundingBox.expandTo(structurecomponent.getBoundingBox());
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeStructureComponentsToNBT(int chunkX, int chunkZ) {
/*  61 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  62 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureStartName(this));
/*  63 */     nbttagcompound.setInteger("ChunkX", chunkX);
/*  64 */     nbttagcompound.setInteger("ChunkZ", chunkZ);
/*  65 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  66 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  68 */     for (StructureComponent structurecomponent : this.components) {
/*  69 */       nbttaglist.appendTag((NBTBase)structurecomponent.createStructureBaseNBT());
/*     */     }
/*     */     
/*  72 */     nbttagcompound.setTag("Children", (NBTBase)nbttaglist);
/*  73 */     writeToNBT(nbttagcompound);
/*  74 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound tagCompound) {}
/*     */   
/*     */   public void readStructureComponentsFromNBT(World worldIn, NBTTagCompound tagCompound) {
/*  81 */     this.chunkPosX = tagCompound.getInteger("ChunkX");
/*  82 */     this.chunkPosZ = tagCompound.getInteger("ChunkZ");
/*     */     
/*  84 */     if (tagCompound.hasKey("BB")) {
/*  85 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  88 */     NBTTagList nbttaglist = tagCompound.getTagList("Children", 10);
/*     */     
/*  90 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  91 */       this.components.add(MapGenStructureIO.getStructureComponent(nbttaglist.getCompoundTagAt(i), worldIn));
/*     */     }
/*     */     
/*  94 */     readFromNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void markAvailableHeight(World worldIn, Random rand, int p_75067_3_) {
/* 104 */     int i = worldIn.getSeaLevel() - p_75067_3_;
/* 105 */     int j = this.boundingBox.getYSize() + 1;
/*     */     
/* 107 */     if (j < i) {
/* 108 */       j += rand.nextInt(i - j);
/*     */     }
/*     */     
/* 111 */     int k = j - this.boundingBox.maxY;
/* 112 */     this.boundingBox.offset(0, k, 0);
/*     */     
/* 114 */     for (StructureComponent structurecomponent : this.components) {
/* 115 */       structurecomponent.func_181138_a(0, k, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setRandomHeight(World worldIn, Random rand, int p_75070_3_, int p_75070_4_) {
/* 120 */     int i = p_75070_4_ - p_75070_3_ + 1 - this.boundingBox.getYSize();
/* 121 */     int j = 1;
/*     */     
/* 123 */     if (i > 1) {
/* 124 */       j = p_75070_3_ + rand.nextInt(i);
/*     */     } else {
/* 126 */       j = p_75070_3_;
/*     */     } 
/*     */     
/* 129 */     int k = j - this.boundingBox.minY;
/* 130 */     this.boundingBox.offset(0, k, 0);
/*     */     
/* 132 */     for (StructureComponent structurecomponent : this.components) {
/* 133 */       structurecomponent.func_181138_a(0, k, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSizeableStructure() {
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   public boolean func_175788_a(ChunkCoordIntPair pair) {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175787_b(ChunkCoordIntPair pair) {}
/*     */   
/*     */   public int getChunkPosX() {
/* 152 */     return this.chunkPosX;
/*     */   }
/*     */   
/*     */   public int getChunkPosZ() {
/* 156 */     return this.chunkPosZ;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */