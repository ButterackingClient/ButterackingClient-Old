/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NBTUtil
/*     */ {
/*     */   public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
/*     */     UUID uuid;
/*  15 */     String s = null;
/*  16 */     String s1 = null;
/*     */     
/*  18 */     if (compound.hasKey("Name", 8)) {
/*  19 */       s = compound.getString("Name");
/*     */     }
/*     */     
/*  22 */     if (compound.hasKey("Id", 8)) {
/*  23 */       s1 = compound.getString("Id");
/*     */     }
/*     */     
/*  26 */     if (StringUtils.isNullOrEmpty(s) && StringUtils.isNullOrEmpty(s1)) {
/*  27 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  32 */       uuid = UUID.fromString(s1);
/*  33 */     } catch (Throwable var12) {
/*  34 */       uuid = null;
/*     */     } 
/*     */     
/*  37 */     GameProfile gameprofile = new GameProfile(uuid, s);
/*     */     
/*  39 */     if (compound.hasKey("Properties", 10)) {
/*  40 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");
/*     */       
/*  42 */       for (String s2 : nbttagcompound.getKeySet()) {
/*  43 */         NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);
/*     */         
/*  45 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  46 */           NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  47 */           String s3 = nbttagcompound1.getString("Value");
/*     */           
/*  49 */           if (nbttagcompound1.hasKey("Signature", 8)) {
/*  50 */             gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
/*     */           } else {
/*  52 */             gameprofile.getProperties().put(s2, new Property(s2, s3));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return gameprofile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
/*  66 */     if (!StringUtils.isNullOrEmpty(profile.getName())) {
/*  67 */       tagCompound.setString("Name", profile.getName());
/*     */     }
/*     */     
/*  70 */     if (profile.getId() != null) {
/*  71 */       tagCompound.setString("Id", profile.getId().toString());
/*     */     }
/*     */     
/*  74 */     if (!profile.getProperties().isEmpty()) {
/*  75 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/*  77 */       for (String s : profile.getProperties().keySet()) {
/*  78 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/*  80 */         for (Property property : profile.getProperties().get(s)) {
/*  81 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  82 */           nbttagcompound1.setString("Value", property.getValue());
/*     */           
/*  84 */           if (property.hasSignature()) {
/*  85 */             nbttagcompound1.setString("Signature", property.getSignature());
/*     */           }
/*     */           
/*  88 */           nbttaglist.appendTag(nbttagcompound1);
/*     */         } 
/*     */         
/*  91 */         nbttagcompound.setTag(s, nbttaglist);
/*     */       } 
/*     */       
/*  94 */       tagCompound.setTag("Properties", nbttagcompound);
/*     */     } 
/*     */     
/*  97 */     return tagCompound;
/*     */   }
/*     */   
/*     */   public static boolean func_181123_a(NBTBase p_181123_0_, NBTBase p_181123_1_, boolean p_181123_2_) {
/* 101 */     if (p_181123_0_ == p_181123_1_)
/* 102 */       return true; 
/* 103 */     if (p_181123_0_ == null)
/* 104 */       return true; 
/* 105 */     if (p_181123_1_ == null)
/* 106 */       return false; 
/* 107 */     if (!p_181123_0_.getClass().equals(p_181123_1_.getClass()))
/* 108 */       return false; 
/* 109 */     if (p_181123_0_ instanceof NBTTagCompound) {
/* 110 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_181123_0_;
/* 111 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)p_181123_1_;
/*     */       
/* 113 */       for (String s : nbttagcompound.getKeySet()) {
/* 114 */         NBTBase nbtbase1 = nbttagcompound.getTag(s);
/*     */         
/* 116 */         if (!func_181123_a(nbtbase1, nbttagcompound1.getTag(s), p_181123_2_)) {
/* 117 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 121 */       return true;
/* 122 */     }  if (p_181123_0_ instanceof NBTTagList && p_181123_2_) {
/* 123 */       NBTTagList nbttaglist = (NBTTagList)p_181123_0_;
/* 124 */       NBTTagList nbttaglist1 = (NBTTagList)p_181123_1_;
/*     */       
/* 126 */       if (nbttaglist.tagCount() == 0) {
/* 127 */         return (nbttaglist1.tagCount() == 0);
/*     */       }
/* 129 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 130 */         NBTBase nbtbase = nbttaglist.get(i);
/* 131 */         boolean flag = false;
/*     */         
/* 133 */         for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/* 134 */           if (func_181123_a(nbtbase, nbttaglist1.get(j), p_181123_2_)) {
/* 135 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 140 */         if (!flag) {
/* 141 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 145 */       return true;
/*     */     } 
/*     */     
/* 148 */     return p_181123_0_.equals(p_181123_1_);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */