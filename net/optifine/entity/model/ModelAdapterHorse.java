/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderHorse;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class ModelAdapterHorse
/*     */   extends ModelAdapter {
/*  16 */   private static Map<String, Integer> mapPartFields = null;
/*     */   
/*     */   public ModelAdapterHorse() {
/*  19 */     super(EntityHorse.class, "horse", 0.75F);
/*     */   }
/*     */   
/*     */   protected ModelAdapterHorse(Class entityClass, String name, float shadowSize) {
/*  23 */     super(entityClass, name, shadowSize);
/*     */   }
/*     */   
/*     */   public ModelBase makeModel() {
/*  27 */     return (ModelBase)new ModelHorse();
/*     */   }
/*     */   
/*     */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/*  31 */     if (!(model instanceof ModelHorse)) {
/*  32 */       return null;
/*     */     }
/*  34 */     ModelHorse modelhorse = (ModelHorse)model;
/*  35 */     Map<String, Integer> map = getMapPartFields();
/*     */     
/*  37 */     if (map.containsKey(modelPart)) {
/*  38 */       int i = ((Integer)map.get(modelPart)).intValue();
/*  39 */       return (ModelRenderer)Reflector.getFieldValue(modelhorse, Reflector.ModelHorse_ModelRenderers, i);
/*     */     } 
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getModelRendererNames() {
/*  47 */     return new String[] { "head", "upper_mouth", "lower_mouth", "horse_left_ear", "horse_right_ear", "mule_left_ear", "mule_right_ear", "neck", "horse_face_ropes", "mane", "body", "tail_base", "tail_middle", "tail_tip", "back_left_leg", "back_left_shin", "back_left_hoof", "back_right_leg", "back_right_shin", "back_right_hoof", "front_left_leg", "front_left_shin", "front_left_hoof", "front_right_leg", "front_right_shin", "front_right_hoof", "mule_left_chest", "mule_right_chest", "horse_saddle_bottom", "horse_saddle_front", "horse_saddle_back", "horse_left_saddle_rope", "horse_left_saddle_metal", "horse_right_saddle_rope", "horse_right_saddle_metal", "horse_left_face_metal", "horse_right_face_metal", "horse_left_rein", "horse_right_rein" };
/*     */   }
/*     */   
/*     */   private static Map<String, Integer> getMapPartFields() {
/*  51 */     if (mapPartFields != null) {
/*  52 */       return mapPartFields;
/*     */     }
/*  54 */     mapPartFields = new HashMap<>();
/*  55 */     mapPartFields.put("head", Integer.valueOf(0));
/*  56 */     mapPartFields.put("upper_mouth", Integer.valueOf(1));
/*  57 */     mapPartFields.put("lower_mouth", Integer.valueOf(2));
/*  58 */     mapPartFields.put("horse_left_ear", Integer.valueOf(3));
/*  59 */     mapPartFields.put("horse_right_ear", Integer.valueOf(4));
/*  60 */     mapPartFields.put("mule_left_ear", Integer.valueOf(5));
/*  61 */     mapPartFields.put("mule_right_ear", Integer.valueOf(6));
/*  62 */     mapPartFields.put("neck", Integer.valueOf(7));
/*  63 */     mapPartFields.put("horse_face_ropes", Integer.valueOf(8));
/*  64 */     mapPartFields.put("mane", Integer.valueOf(9));
/*  65 */     mapPartFields.put("body", Integer.valueOf(10));
/*  66 */     mapPartFields.put("tail_base", Integer.valueOf(11));
/*  67 */     mapPartFields.put("tail_middle", Integer.valueOf(12));
/*  68 */     mapPartFields.put("tail_tip", Integer.valueOf(13));
/*  69 */     mapPartFields.put("back_left_leg", Integer.valueOf(14));
/*  70 */     mapPartFields.put("back_left_shin", Integer.valueOf(15));
/*  71 */     mapPartFields.put("back_left_hoof", Integer.valueOf(16));
/*  72 */     mapPartFields.put("back_right_leg", Integer.valueOf(17));
/*  73 */     mapPartFields.put("back_right_shin", Integer.valueOf(18));
/*  74 */     mapPartFields.put("back_right_hoof", Integer.valueOf(19));
/*  75 */     mapPartFields.put("front_left_leg", Integer.valueOf(20));
/*  76 */     mapPartFields.put("front_left_shin", Integer.valueOf(21));
/*  77 */     mapPartFields.put("front_left_hoof", Integer.valueOf(22));
/*  78 */     mapPartFields.put("front_right_leg", Integer.valueOf(23));
/*  79 */     mapPartFields.put("front_right_shin", Integer.valueOf(24));
/*  80 */     mapPartFields.put("front_right_hoof", Integer.valueOf(25));
/*  81 */     mapPartFields.put("mule_left_chest", Integer.valueOf(26));
/*  82 */     mapPartFields.put("mule_right_chest", Integer.valueOf(27));
/*  83 */     mapPartFields.put("horse_saddle_bottom", Integer.valueOf(28));
/*  84 */     mapPartFields.put("horse_saddle_front", Integer.valueOf(29));
/*  85 */     mapPartFields.put("horse_saddle_back", Integer.valueOf(30));
/*  86 */     mapPartFields.put("horse_left_saddle_rope", Integer.valueOf(31));
/*  87 */     mapPartFields.put("horse_left_saddle_metal", Integer.valueOf(32));
/*  88 */     mapPartFields.put("horse_right_saddle_rope", Integer.valueOf(33));
/*  89 */     mapPartFields.put("horse_right_saddle_metal", Integer.valueOf(34));
/*  90 */     mapPartFields.put("horse_left_face_metal", Integer.valueOf(35));
/*  91 */     mapPartFields.put("horse_right_face_metal", Integer.valueOf(36));
/*  92 */     mapPartFields.put("horse_left_rein", Integer.valueOf(37));
/*  93 */     mapPartFields.put("horse_right_rein", Integer.valueOf(38));
/*  94 */     return mapPartFields;
/*     */   }
/*     */ 
/*     */   
/*     */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*  99 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 100 */     RenderHorse renderhorse = new RenderHorse(rendermanager, (ModelHorse)modelBase, shadowSize);
/* 101 */     return (IEntityRenderer)renderhorse;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */