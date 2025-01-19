/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.TextureAnimations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.MemoryMonitor;
/*     */ import net.optifine.util.NativeMemory;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiOverlayDebug
/*     */   extends Gui {
/*     */   private final Minecraft mc;
/*     */   private final FontRenderer fontRenderer;
/*  41 */   private String debugOF = null;
/*  42 */   private List<String> debugInfoLeft = null;
/*  43 */   private List<String> debugInfoRight = null;
/*  44 */   private long updateInfoLeftTimeMs = 0L;
/*  45 */   private long updateInfoRightTimeMs = 0L;
/*     */   
/*     */   public GuiOverlayDebug(Minecraft mc) {
/*  48 */     this.mc = mc;
/*  49 */     this.fontRenderer = mc.fontRendererObj;
/*     */   }
/*     */   
/*     */   public void renderDebugInfo(ScaledResolution scaledResolutionIn) {
/*  53 */     this.mc.mcProfiler.startSection("debug");
/*  54 */     GlStateManager.pushMatrix();
/*  55 */     renderDebugInfoLeft();
/*  56 */     renderDebugInfoRight(scaledResolutionIn);
/*  57 */     GlStateManager.popMatrix();
/*     */     
/*  59 */     if (this.mc.gameSettings.lastServer) {
/*  60 */       renderLagometer();
/*     */     }
/*     */     
/*  63 */     this.mc.mcProfiler.endSection();
/*     */   }
/*     */   
/*     */   private boolean isReducedDebug() {
/*  67 */     return !(!this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo);
/*     */   }
/*     */   
/*     */   protected void renderDebugInfoLeft() {
/*  71 */     List<String> list = this.debugInfoLeft;
/*     */     
/*  73 */     if (list == null || System.currentTimeMillis() > this.updateInfoLeftTimeMs) {
/*  74 */       list = call();
/*  75 */       this.debugInfoLeft = list;
/*  76 */       this.updateInfoLeftTimeMs = System.currentTimeMillis() + 100L;
/*     */     } 
/*     */     
/*  79 */     for (int i = 0; i < list.size(); i++) {
/*  80 */       String s = list.get(i);
/*     */       
/*  82 */       if (!Strings.isNullOrEmpty(s)) {
/*  83 */         int j = this.fontRenderer.FONT_HEIGHT;
/*  84 */         int k = this.fontRenderer.getStringWidth(s);
/*  85 */         int l = 2;
/*  86 */         int i1 = 2 + j * i;
/*  87 */         drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
/*  88 */         this.fontRenderer.drawString(s, 2, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderDebugInfoRight(ScaledResolution scaledRes) {
/*  94 */     List<String> list = this.debugInfoRight;
/*     */     
/*  96 */     if (list == null || System.currentTimeMillis() > this.updateInfoRightTimeMs) {
/*  97 */       list = getDebugInfoRight();
/*  98 */       this.debugInfoRight = list;
/*  99 */       this.updateInfoRightTimeMs = System.currentTimeMillis() + 100L;
/*     */     } 
/*     */     
/* 102 */     for (int i = 0; i < list.size(); i++) {
/* 103 */       String s = list.get(i);
/*     */       
/* 105 */       if (!Strings.isNullOrEmpty(s)) {
/* 106 */         int j = this.fontRenderer.FONT_HEIGHT;
/* 107 */         int k = this.fontRenderer.getStringWidth(s);
/* 108 */         int l = scaledRes.getScaledWidth() - 2 - k;
/* 109 */         int i1 = 2 + j * i;
/* 110 */         drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
/* 111 */         this.fontRenderer.drawString(s, l, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> call() {
/* 118 */     BlockPos blockpos = new BlockPos((this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity().getEntityBoundingBox()).minY, (this.mc.getRenderViewEntity()).posZ);
/*     */     
/* 120 */     if (this.mc.debug != this.debugOF) {
/* 121 */       StringBuffer stringbuffer = new StringBuffer(this.mc.debug);
/* 122 */       int i = Config.getFpsMin();
/* 123 */       int j = this.mc.debug.indexOf(" fps ");
/*     */       
/* 125 */       if (j >= 0) {
/* 126 */         stringbuffer.insert(j, "/" + i);
/*     */       }
/*     */       
/* 129 */       if (Config.isSmoothFps()) {
/* 130 */         stringbuffer.append(" sf");
/*     */       }
/*     */       
/* 133 */       if (Config.isFastRender()) {
/* 134 */         stringbuffer.append(" fr");
/*     */       }
/*     */       
/* 137 */       if (Config.isAnisotropicFiltering()) {
/* 138 */         stringbuffer.append(" af");
/*     */       }
/*     */       
/* 141 */       if (Config.isAntialiasing()) {
/* 142 */         stringbuffer.append(" aa");
/*     */       }
/*     */       
/* 145 */       if (Config.isRenderRegions()) {
/* 146 */         stringbuffer.append(" reg");
/*     */       }
/*     */       
/* 149 */       if (Config.isShaders()) {
/* 150 */         stringbuffer.append(" sh");
/*     */       }
/*     */       
/* 153 */       this.mc.debug = stringbuffer.toString();
/* 154 */       this.debugOF = this.mc.debug;
/*     */     } 
/*     */     
/* 157 */     StringBuilder stringbuilder = new StringBuilder();
/* 158 */     TextureMap texturemap = Config.getTextureMap();
/* 159 */     stringbuilder.append(", A: ");
/*     */     
/* 161 */     if (SmartAnimations.isActive()) {
/* 162 */       stringbuilder.append(texturemap.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
/* 163 */       stringbuilder.append("/");
/*     */     } 
/*     */     
/* 166 */     stringbuilder.append(texturemap.getCountAnimations() + TextureAnimations.getCountAnimations());
/* 167 */     String s1 = stringbuilder.toString();
/*     */     
/* 169 */     if (isReducedDebug()) {
/* 170 */       return Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF) }) });
/*     */     }
/* 172 */     Entity entity = this.mc.getRenderViewEntity();
/* 173 */     EnumFacing enumfacing = entity.getHorizontalFacing();
/* 174 */     String s = "Invalid";
/*     */     
/* 176 */     switch (enumfacing) {
/*     */       case NORTH:
/* 178 */         s = "Towards negative Z";
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 182 */         s = "Towards positive Z";
/*     */         break;
/*     */       
/*     */       case WEST:
/* 186 */         s = "Towards negative X";
/*     */         break;
/*     */       
/*     */       case EAST:
/* 190 */         s = "Towards positive X";
/*     */         break;
/*     */     } 
/* 193 */     List<String> list = Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf((this.mc.getRenderViewEntity()).posX), Double.valueOf((this.mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((this.mc.getRenderViewEntity()).posZ) }), String.format("Block: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) }), String.format("Chunk: %d %d %d in %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF), Integer.valueOf(blockpos.getX() >> 4), Integer.valueOf(blockpos.getY() >> 4), Integer.valueOf(blockpos.getZ() >> 4) }), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[] { enumfacing, s, Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationYaw)), Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationPitch)) }) });
/*     */     
/* 195 */     if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos)) {
/* 196 */       Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
/* 197 */       list.add("Biome: " + (chunk.getBiome(blockpos, this.mc.theWorld.getWorldChunkManager())).biomeName);
/* 198 */       list.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
/* 199 */       DifficultyInstance difficultyinstance = this.mc.theWorld.getDifficultyForLocation(blockpos);
/*     */       
/* 201 */       if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
/* 202 */         EntityPlayerMP entityplayermp = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
/*     */         
/* 204 */         if (entityplayermp != null) {
/* 205 */           DifficultyInstance difficultyinstance1 = this.mc.getIntegratedServer().getDifficultyAsync(entityplayermp.worldObj, new BlockPos((Entity)entityplayermp));
/*     */           
/* 207 */           if (difficultyinstance1 != null) {
/* 208 */             difficultyinstance = difficultyinstance1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 213 */       list.add(String.format("Local Difficulty: %.2f (Day %d)", new Object[] { Float.valueOf(difficultyinstance.getAdditionalDifficulty()), Long.valueOf(this.mc.theWorld.getWorldTime() / 24000L) }));
/*     */     } 
/*     */     
/* 216 */     if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
/* 217 */       list.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
/*     */     }
/*     */     
/* 220 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/* 221 */       BlockPos blockpos1 = this.mc.objectMouseOver.getBlockPos();
/* 222 */       list.add(String.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ()) }));
/*     */     } 
/*     */     
/* 225 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> getDebugInfoRight() {
/* 230 */     long i = Runtime.getRuntime().maxMemory();
/* 231 */     long j = Runtime.getRuntime().totalMemory();
/* 232 */     long k = Runtime.getRuntime().freeMemory();
/* 233 */     long l = j - k;
/* 234 */     List<String> list = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(this.mc.isJava64bit() ? 64 : 32) }), String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)), Long.valueOf(bytesToMb(i)) }), String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }), "", String.format("CPU: %s", new Object[] { OpenGlHelper.getCpu() }), "", String.format("Display: %dx%d (%s)", new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GL11.glGetString(7936) }), GL11.glGetString(7937), GL11.glGetString(7938) });
/* 235 */     long i1 = NativeMemory.getBufferAllocated();
/* 236 */     long j1 = NativeMemory.getBufferMaximum();
/* 237 */     String s = "Native: " + bytesToMb(i1) + "/" + bytesToMb(j1) + "MB";
/* 238 */     list.add(4, s);
/* 239 */     list.set(5, "GC: " + MemoryMonitor.getAllocationRateMb() + "MB/s");
/*     */     
/* 241 */     if (Reflector.FMLCommonHandler_getBrandings.exists()) {
/* 242 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 243 */       list.add("");
/* 244 */       list.addAll((Collection<? extends String>)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.valueOf(false) }));
/*     */     } 
/*     */     
/* 247 */     if (isReducedDebug()) {
/* 248 */       return list;
/*     */     }
/* 250 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/* 251 */       BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 252 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*     */       
/* 254 */       if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
/* 255 */         iblockstate = iblockstate.getBlock().getActualState(iblockstate, (IBlockAccess)this.mc.theWorld, blockpos);
/*     */       }
/*     */       
/* 258 */       list.add("");
/* 259 */       list.add(String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock())));
/*     */       
/* 261 */       for (Map.Entry<IProperty, Comparable> entry : (Iterable<Map.Entry<IProperty, Comparable>>)iblockstate.getProperties().entrySet()) {
/* 262 */         String s1 = ((Comparable)entry.getValue()).toString();
/*     */         
/* 264 */         if (entry.getValue() == Boolean.TRUE) {
/* 265 */           s1 = EnumChatFormatting.GREEN + s1;
/* 266 */         } else if (entry.getValue() == Boolean.FALSE) {
/* 267 */           s1 = EnumChatFormatting.RED + s1;
/*     */         } 
/*     */         
/* 270 */         list.add(String.valueOf(((IProperty)entry.getKey()).getName()) + ": " + s1);
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderLagometer() {}
/*     */ 
/*     */   
/*     */   private int getFrameColor(int p_181552_1_, int p_181552_2_, int p_181552_3_, int p_181552_4_) {
/* 282 */     return (p_181552_1_ < p_181552_3_) ? blendColors(-16711936, -256, p_181552_1_ / p_181552_3_) : blendColors(-256, -65536, (p_181552_1_ - p_181552_3_) / (p_181552_4_ - p_181552_3_));
/*     */   }
/*     */   
/*     */   private int blendColors(int p_181553_1_, int p_181553_2_, float p_181553_3_) {
/* 286 */     int i = p_181553_1_ >> 24 & 0xFF;
/* 287 */     int j = p_181553_1_ >> 16 & 0xFF;
/* 288 */     int k = p_181553_1_ >> 8 & 0xFF;
/* 289 */     int l = p_181553_1_ & 0xFF;
/* 290 */     int i1 = p_181553_2_ >> 24 & 0xFF;
/* 291 */     int j1 = p_181553_2_ >> 16 & 0xFF;
/* 292 */     int k1 = p_181553_2_ >> 8 & 0xFF;
/* 293 */     int l1 = p_181553_2_ & 0xFF;
/* 294 */     int i2 = MathHelper.clamp_int((int)(i + (i1 - i) * p_181553_3_), 0, 255);
/* 295 */     int j2 = MathHelper.clamp_int((int)(j + (j1 - j) * p_181553_3_), 0, 255);
/* 296 */     int k2 = MathHelper.clamp_int((int)(k + (k1 - k) * p_181553_3_), 0, 255);
/* 297 */     int l2 = MathHelper.clamp_int((int)(l + (l1 - l) * p_181553_3_), 0, 255);
/* 298 */     return i2 << 24 | j2 << 16 | k2 << 8 | l2;
/*     */   }
/*     */   
/*     */   private static long bytesToMb(long bytes) {
/* 302 */     return bytes / 1024L / 1024L;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiOverlayDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */