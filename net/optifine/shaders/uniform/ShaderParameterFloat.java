/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public enum ShaderParameterFloat {
/*   9 */   BIOME("biome"),
/*  10 */   TEMPERATURE("temperature"),
/*  11 */   RAINFALL("rainfall"),
/*  12 */   HELD_ITEM_ID(Shaders.uniform_heldItemId),
/*  13 */   HELD_BLOCK_LIGHT_VALUE(Shaders.uniform_heldBlockLightValue),
/*  14 */   HELD_ITEM_ID2(Shaders.uniform_heldItemId2),
/*  15 */   HELD_BLOCK_LIGHT_VALUE2(Shaders.uniform_heldBlockLightValue2),
/*  16 */   WORLD_TIME(Shaders.uniform_worldTime),
/*  17 */   WORLD_DAY(Shaders.uniform_worldDay),
/*  18 */   MOON_PHASE(Shaders.uniform_moonPhase),
/*  19 */   FRAME_COUNTER(Shaders.uniform_frameCounter),
/*  20 */   FRAME_TIME(Shaders.uniform_frameTime),
/*  21 */   FRAME_TIME_COUNTER(Shaders.uniform_frameTimeCounter),
/*  22 */   SUN_ANGLE(Shaders.uniform_sunAngle),
/*  23 */   SHADOW_ANGLE(Shaders.uniform_shadowAngle),
/*  24 */   RAIN_STRENGTH(Shaders.uniform_rainStrength),
/*  25 */   ASPECT_RATIO(Shaders.uniform_aspectRatio),
/*  26 */   VIEW_WIDTH(Shaders.uniform_viewWidth),
/*  27 */   VIEW_HEIGHT(Shaders.uniform_viewHeight),
/*  28 */   NEAR(Shaders.uniform_near),
/*  29 */   FAR(Shaders.uniform_far),
/*  30 */   WETNESS(Shaders.uniform_wetness),
/*  31 */   EYE_ALTITUDE(Shaders.uniform_eyeAltitude),
/*  32 */   EYE_BRIGHTNESS(Shaders.uniform_eyeBrightness, new String[] { "x", "y" }),
/*  33 */   TERRAIN_TEXTURE_SIZE(Shaders.uniform_terrainTextureSize, new String[] { "x", "y" }),
/*  34 */   TERRRAIN_ICON_SIZE(Shaders.uniform_terrainIconSize),
/*  35 */   IS_EYE_IN_WATER(Shaders.uniform_isEyeInWater),
/*  36 */   NIGHT_VISION(Shaders.uniform_nightVision),
/*  37 */   BLINDNESS(Shaders.uniform_blindness),
/*  38 */   SCREEN_BRIGHTNESS(Shaders.uniform_screenBrightness),
/*  39 */   HIDE_GUI(Shaders.uniform_hideGUI),
/*  40 */   CENTER_DEPT_SMOOTH(Shaders.uniform_centerDepthSmooth),
/*  41 */   ATLAS_SIZE(Shaders.uniform_atlasSize, new String[] { "x", "y" }),
/*  42 */   CAMERA_POSITION(Shaders.uniform_cameraPosition, new String[] { "x", "y", "z" }),
/*  43 */   PREVIOUS_CAMERA_POSITION(Shaders.uniform_previousCameraPosition, new String[] { "x", "y", "z" }),
/*  44 */   SUN_POSITION(Shaders.uniform_sunPosition, new String[] { "x", "y", "z" }),
/*  45 */   MOON_POSITION(Shaders.uniform_moonPosition, new String[] { "x", "y", "z" }),
/*  46 */   SHADOW_LIGHT_POSITION(Shaders.uniform_shadowLightPosition, new String[] { "x", "y", "z" }),
/*  47 */   UP_POSITION(Shaders.uniform_upPosition, new String[] { "x", "y", "z" }),
/*  48 */   SKY_COLOR(Shaders.uniform_skyColor, new String[] { "r", "g", "b" }),
/*  49 */   GBUFFER_PROJECTION(Shaders.uniform_gbufferProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  50 */   GBUFFER_PROJECTION_INVERSE(Shaders.uniform_gbufferProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  51 */   GBUFFER_PREVIOUS_PROJECTION(Shaders.uniform_gbufferPreviousProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  52 */   GBUFFER_MODEL_VIEW(Shaders.uniform_gbufferModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  53 */   GBUFFER_MODEL_VIEW_INVERSE(Shaders.uniform_gbufferModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  54 */   GBUFFER_PREVIOUS_MODEL_VIEW(Shaders.uniform_gbufferPreviousModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  55 */   SHADOW_PROJECTION(Shaders.uniform_shadowProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  56 */   SHADOW_PROJECTION_INVERSE(Shaders.uniform_shadowProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  57 */   SHADOW_MODEL_VIEW(Shaders.uniform_shadowModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  58 */   SHADOW_MODEL_VIEW_INVERSE(Shaders.uniform_shadowModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" });
/*     */   
/*     */   private String name;
/*     */   private ShaderUniformBase uniform;
/*     */   private String[] indexNames1;
/*     */   private String[] indexNames2;
/*     */   
/*     */   ShaderParameterFloat(String name) {
/*  66 */     this.name = name;
/*     */   }
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform) {
/*  70 */     this.name = uniform.getName();
/*  71 */     this.uniform = uniform;
/*     */     
/*  73 */     if (!instanceOf(uniform, new Class[] { ShaderUniform1f.class, ShaderUniform1i.class })) {
/*  74 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform, String[] indexNames1) {
/*  79 */     this.name = uniform.getName();
/*  80 */     this.uniform = uniform;
/*  81 */     this.indexNames1 = indexNames1;
/*     */     
/*  83 */     if (!instanceOf(uniform, new Class[] { ShaderUniform2i.class, ShaderUniform2f.class, ShaderUniform3f.class, ShaderUniform4f.class })) {
/*  84 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform, String[] indexNames1, String[] indexNames2) {
/*  89 */     this.name = uniform.getName();
/*  90 */     this.uniform = uniform;
/*  91 */     this.indexNames1 = indexNames1;
/*  92 */     this.indexNames2 = indexNames2;
/*     */     
/*  94 */     if (!instanceOf(uniform, new Class[] { ShaderUniformM4.class })) {
/*  95 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName() {
/* 100 */     return this.name;
/*     */   }
/*     */   
/*     */   public ShaderUniformBase getUniform() {
/* 104 */     return this.uniform;
/*     */   }
/*     */   
/*     */   public String[] getIndexNames1() {
/* 108 */     return this.indexNames1;
/*     */   }
/*     */   
/*     */   public String[] getIndexNames2() {
/* 112 */     return this.indexNames2;
/*     */   }
/*     */   
/*     */   public float eval(int index1, int index2) {
/* 116 */     if (this.indexNames1 == null || (index1 >= 0 && index1 <= this.indexNames1.length)) {
/* 117 */       if (this.indexNames2 == null || (index2 >= 0 && index2 <= this.indexNames2.length)) {
/* 118 */         BlockPos blockpos2; BiomeGenBase biomegenbase2; BlockPos blockpos1; BiomeGenBase biomegenbase1; BlockPos pos; BiomeGenBase biome; switch (this) {
/*     */           case BIOME:
/* 120 */             blockpos2 = Shaders.getCameraPosition();
/* 121 */             biomegenbase2 = Shaders.getCurrentWorld().getBiomeGenForCoords(blockpos2);
/* 122 */             return biomegenbase2.biomeID;
/*     */           
/*     */           case TEMPERATURE:
/* 125 */             blockpos1 = Shaders.getCameraPosition();
/* 126 */             biomegenbase1 = Shaders.getCurrentWorld().getBiomeGenForCoords(blockpos1);
/* 127 */             return (biomegenbase1 != null) ? biomegenbase1.getFloatTemperature(blockpos1) : 0.0F;
/*     */           
/*     */           case RAINFALL:
/* 130 */             pos = Shaders.getCameraPosition();
/* 131 */             biome = Shaders.getCurrentWorld().getBiomeGenForCoords(pos);
/* 132 */             return (biome != null) ? biome.getFloatRainfall() : 0.0F;
/*     */         } 
/*     */         
/* 135 */         if (this.uniform instanceof ShaderUniform1f)
/* 136 */           return ((ShaderUniform1f)this.uniform).getValue(); 
/* 137 */         if (this.uniform instanceof ShaderUniform1i)
/* 138 */           return ((ShaderUniform1i)this.uniform).getValue(); 
/* 139 */         if (this.uniform instanceof ShaderUniform2i)
/* 140 */           return ((ShaderUniform2i)this.uniform).getValue()[index1]; 
/* 141 */         if (this.uniform instanceof ShaderUniform2f)
/* 142 */           return ((ShaderUniform2f)this.uniform).getValue()[index1]; 
/* 143 */         if (this.uniform instanceof ShaderUniform3f)
/* 144 */           return ((ShaderUniform3f)this.uniform).getValue()[index1]; 
/* 145 */         if (this.uniform instanceof ShaderUniform4f)
/* 146 */           return ((ShaderUniform4f)this.uniform).getValue()[index1]; 
/* 147 */         if (this.uniform instanceof ShaderUniformM4) {
/* 148 */           return ((ShaderUniformM4)this.uniform).getValue(index1, index2);
/*     */         }
/* 150 */         throw new IllegalArgumentException("Unknown uniform type: " + this);
/*     */       } 
/*     */ 
/*     */       
/* 154 */       Config.warn("Invalid index2, parameter: " + this + ", index: " + index2);
/* 155 */       return 0.0F;
/*     */     } 
/*     */     
/* 158 */     Config.warn("Invalid index1, parameter: " + this + ", index: " + index1);
/* 159 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean instanceOf(Object obj, Class... classes) {
/* 164 */     if (obj == null) {
/* 165 */       return false;
/*     */     }
/* 167 */     Class<?> oclass = obj.getClass();
/*     */     
/* 169 */     for (int i = 0; i < classes.length; i++) {
/* 170 */       Class oclass1 = classes[i];
/*     */       
/* 172 */       if (oclass1.isAssignableFrom(oclass)) {
/* 173 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 177 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderParameterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */