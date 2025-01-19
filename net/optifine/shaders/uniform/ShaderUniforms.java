/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShaderUniforms {
/*  7 */   private final List<ShaderUniformBase> listUniforms = new ArrayList<>();
/*    */   
/*    */   public void setProgram(int program) {
/* 10 */     for (int i = 0; i < this.listUniforms.size(); i++) {
/* 11 */       ShaderUniformBase shaderuniformbase = this.listUniforms.get(i);
/* 12 */       shaderuniformbase.setProgram(program);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void reset() {
/* 17 */     for (int i = 0; i < this.listUniforms.size(); i++) {
/* 18 */       ShaderUniformBase shaderuniformbase = this.listUniforms.get(i);
/* 19 */       shaderuniformbase.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public ShaderUniform1i make1i(String name) {
/* 24 */     ShaderUniform1i shaderuniform1i = new ShaderUniform1i(name);
/* 25 */     this.listUniforms.add(shaderuniform1i);
/* 26 */     return shaderuniform1i;
/*    */   }
/*    */   
/*    */   public ShaderUniform2i make2i(String name) {
/* 30 */     ShaderUniform2i shaderuniform2i = new ShaderUniform2i(name);
/* 31 */     this.listUniforms.add(shaderuniform2i);
/* 32 */     return shaderuniform2i;
/*    */   }
/*    */   
/*    */   public ShaderUniform4i make4i(String name) {
/* 36 */     ShaderUniform4i shaderuniform4i = new ShaderUniform4i(name);
/* 37 */     this.listUniforms.add(shaderuniform4i);
/* 38 */     return shaderuniform4i;
/*    */   }
/*    */   
/*    */   public ShaderUniform1f make1f(String name) {
/* 42 */     ShaderUniform1f shaderuniform1f = new ShaderUniform1f(name);
/* 43 */     this.listUniforms.add(shaderuniform1f);
/* 44 */     return shaderuniform1f;
/*    */   }
/*    */   
/*    */   public ShaderUniform3f make3f(String name) {
/* 48 */     ShaderUniform3f shaderuniform3f = new ShaderUniform3f(name);
/* 49 */     this.listUniforms.add(shaderuniform3f);
/* 50 */     return shaderuniform3f;
/*    */   }
/*    */   
/*    */   public ShaderUniform4f make4f(String name) {
/* 54 */     ShaderUniform4f shaderuniform4f = new ShaderUniform4f(name);
/* 55 */     this.listUniforms.add(shaderuniform4f);
/* 56 */     return shaderuniform4f;
/*    */   }
/*    */   
/*    */   public ShaderUniformM4 makeM4(String name) {
/* 60 */     ShaderUniformM4 shaderuniformm4 = new ShaderUniformM4(name);
/* 61 */     this.listUniforms.add(shaderuniformm4);
/* 62 */     return shaderuniformm4;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */