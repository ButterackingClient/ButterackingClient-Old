/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ 
/*    */ 
/*    */ public class CompiledChunk
/*    */ {
/* 14 */   public static final CompiledChunk DUMMY = new CompiledChunk() {
/*    */       protected void setLayerUsed(EnumWorldBlockLayer layer) {
/* 16 */         throw new UnsupportedOperationException();
/*    */       }
/*    */       
/*    */       public void setLayerStarted(EnumWorldBlockLayer layer) {
/* 20 */         throw new UnsupportedOperationException();
/*    */       }
/*    */       
/*    */       public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 24 */         return false;
/*    */       }
/*    */       
/*    */       public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/* 28 */         throw new UnsupportedOperationException();
/*    */       }
/*    */     };
/* 31 */   private final boolean[] layersUsed = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/* 32 */   private final boolean[] layersStarted = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*    */   private boolean empty = true;
/* 34 */   private final List<TileEntity> tileEntities = Lists.newArrayList();
/* 35 */   private SetVisibility setVisibility = new SetVisibility();
/*    */   private WorldRenderer.State state;
/* 37 */   private BitSet[] animatedSprites = new BitSet[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*    */   
/*    */   public boolean isEmpty() {
/* 40 */     return this.empty;
/*    */   }
/*    */   
/*    */   protected void setLayerUsed(EnumWorldBlockLayer layer) {
/* 44 */     this.empty = false;
/* 45 */     this.layersUsed[layer.ordinal()] = true;
/*    */   }
/*    */   
/*    */   public boolean isLayerEmpty(EnumWorldBlockLayer layer) {
/* 49 */     return !this.layersUsed[layer.ordinal()];
/*    */   }
/*    */   
/*    */   public void setLayerStarted(EnumWorldBlockLayer layer) {
/* 53 */     this.layersStarted[layer.ordinal()] = true;
/*    */   }
/*    */   
/*    */   public boolean isLayerStarted(EnumWorldBlockLayer layer) {
/* 57 */     return this.layersStarted[layer.ordinal()];
/*    */   }
/*    */   
/*    */   public List<TileEntity> getTileEntities() {
/* 61 */     return this.tileEntities;
/*    */   }
/*    */   
/*    */   public void addTileEntity(TileEntity tileEntityIn) {
/* 65 */     this.tileEntities.add(tileEntityIn);
/*    */   }
/*    */   
/*    */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 69 */     return this.setVisibility.isVisible(facing, facing2);
/*    */   }
/*    */   
/*    */   public void setVisibility(SetVisibility visibility) {
/* 73 */     this.setVisibility = visibility;
/*    */   }
/*    */   
/*    */   public WorldRenderer.State getState() {
/* 77 */     return this.state;
/*    */   }
/*    */   
/*    */   public void setState(WorldRenderer.State stateIn) {
/* 81 */     this.state = stateIn;
/*    */   }
/*    */   
/*    */   public BitSet getAnimatedSprites(EnumWorldBlockLayer p_getAnimatedSprites_1_) {
/* 85 */     return this.animatedSprites[p_getAnimatedSprites_1_.ordinal()];
/*    */   }
/*    */   
/*    */   public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/* 89 */     this.animatedSprites[p_setAnimatedSprites_1_.ordinal()] = p_setAnimatedSprites_2_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\CompiledChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */