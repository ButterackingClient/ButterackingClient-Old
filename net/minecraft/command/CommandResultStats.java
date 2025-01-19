/*     */ package net.minecraft.command;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandResultStats
/*     */ {
/*  17 */   private static final int NUM_RESULT_TYPES = (Type.values()).length;
/*  18 */   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] entitiesID;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] objectives;
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandResultStats() {
/*  31 */     this.entitiesID = STRING_RESULT_TYPES;
/*  32 */     this.objectives = STRING_RESULT_TYPES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommandStatScore(final ICommandSender sender, Type resultTypeIn, int scorePoint) {
/*  41 */     String s = this.entitiesID[resultTypeIn.getTypeID()];
/*     */     
/*  43 */     if (s != null) {
/*  44 */       String s1; ICommandSender icommandsender = new ICommandSender() {
/*     */           public String getName() {
/*  46 */             return sender.getName();
/*     */           }
/*     */           
/*     */           public IChatComponent getDisplayName() {
/*  50 */             return sender.getDisplayName();
/*     */           }
/*     */           
/*     */           public void addChatMessage(IChatComponent component) {
/*  54 */             sender.addChatMessage(component);
/*     */           }
/*     */           
/*     */           public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  58 */             return true;
/*     */           }
/*     */           
/*     */           public BlockPos getPosition() {
/*  62 */             return sender.getPosition();
/*     */           }
/*     */           
/*     */           public Vec3 getPositionVector() {
/*  66 */             return sender.getPositionVector();
/*     */           }
/*     */           
/*     */           public World getEntityWorld() {
/*  70 */             return sender.getEntityWorld();
/*     */           }
/*     */           
/*     */           public Entity getCommandSenderEntity() {
/*  74 */             return sender.getCommandSenderEntity();
/*     */           }
/*     */           
/*     */           public boolean sendCommandFeedback() {
/*  78 */             return sender.sendCommandFeedback();
/*     */           }
/*     */           
/*     */           public void setCommandStat(CommandResultStats.Type type, int amount) {
/*  82 */             sender.setCommandStat(type, amount);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/*     */       try {
/*  88 */         s1 = CommandBase.getEntityName(icommandsender, s);
/*  89 */       } catch (EntityNotFoundException var11) {
/*     */         return;
/*     */       } 
/*     */       
/*  93 */       String s2 = this.objectives[resultTypeIn.getTypeID()];
/*     */       
/*  95 */       if (s2 != null) {
/*  96 */         Scoreboard scoreboard = sender.getEntityWorld().getScoreboard();
/*  97 */         ScoreObjective scoreobjective = scoreboard.getObjective(s2);
/*     */         
/*  99 */         if (scoreobjective != null && 
/* 100 */           scoreboard.entityHasObjective(s1, scoreobjective)) {
/* 101 */           Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/* 102 */           score.setScorePoints(scorePoint);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatsFromNBT(NBTTagCompound tagcompound) {
/* 110 */     if (tagcompound.hasKey("CommandStats", 10)) {
/* 111 */       NBTTagCompound nbttagcompound = tagcompound.getCompoundTag("CommandStats"); byte b; int i;
/*     */       Type[] arrayOfType;
/* 113 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/* 114 */         String s = String.valueOf(commandresultstats$type.getTypeName()) + "Name";
/* 115 */         String s1 = String.valueOf(commandresultstats$type.getTypeName()) + "Objective";
/*     */         
/* 117 */         if (nbttagcompound.hasKey(s, 8) && nbttagcompound.hasKey(s1, 8)) {
/* 118 */           String s2 = nbttagcompound.getString(s);
/* 119 */           String s3 = nbttagcompound.getString(s1);
/* 120 */           setScoreBoardStat(this, commandresultstats$type, s2, s3);
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   public void writeStatsToNBT(NBTTagCompound tagcompound) {
/* 127 */     NBTTagCompound nbttagcompound = new NBTTagCompound(); byte b; int i;
/*     */     Type[] arrayOfType;
/* 129 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/* 130 */       String s = this.entitiesID[commandresultstats$type.getTypeID()];
/* 131 */       String s1 = this.objectives[commandresultstats$type.getTypeID()];
/*     */       
/* 133 */       if (s != null && s1 != null) {
/* 134 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Name", s);
/* 135 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Objective", s1);
/*     */       } 
/*     */       b++; }
/*     */     
/* 139 */     if (!nbttagcompound.hasNoTags()) {
/* 140 */       tagcompound.setTag("CommandStats", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScoreBoardStat(CommandResultStats stats, Type resultType, String entityID, String objectiveName) {
/* 151 */     if (entityID != null && entityID.length() != 0 && objectiveName != null && objectiveName.length() != 0) {
/* 152 */       if (stats.entitiesID == STRING_RESULT_TYPES || stats.objectives == STRING_RESULT_TYPES) {
/* 153 */         stats.entitiesID = new String[NUM_RESULT_TYPES];
/* 154 */         stats.objectives = new String[NUM_RESULT_TYPES];
/*     */       } 
/*     */       
/* 157 */       stats.entitiesID[resultType.getTypeID()] = entityID;
/* 158 */       stats.objectives[resultType.getTypeID()] = objectiveName;
/*     */     } else {
/* 160 */       removeScoreBoardStat(stats, resultType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeScoreBoardStat(CommandResultStats resultStatsIn, Type resultTypeIn) {
/* 168 */     if (resultStatsIn.entitiesID != STRING_RESULT_TYPES && resultStatsIn.objectives != STRING_RESULT_TYPES) {
/* 169 */       resultStatsIn.entitiesID[resultTypeIn.getTypeID()] = null;
/* 170 */       resultStatsIn.objectives[resultTypeIn.getTypeID()] = null;
/* 171 */       boolean flag = true; byte b; int i;
/*     */       Type[] arrayOfType;
/* 173 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/* 174 */         if (resultStatsIn.entitiesID[commandresultstats$type.getTypeID()] != null && resultStatsIn.objectives[commandresultstats$type.getTypeID()] != null) {
/* 175 */           flag = false;
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */       
/* 180 */       if (flag) {
/* 181 */         resultStatsIn.entitiesID = STRING_RESULT_TYPES;
/* 182 */         resultStatsIn.objectives = STRING_RESULT_TYPES;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addAllStats(CommandResultStats resultStatsIn) {
/*     */     byte b;
/*     */     int i;
/*     */     Type[] arrayOfType;
/* 191 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/* 192 */       setScoreBoardStat(this, commandresultstats$type, resultStatsIn.entitiesID[commandresultstats$type.getTypeID()], resultStatsIn.objectives[commandresultstats$type.getTypeID()]);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/* 197 */   public enum Type { SUCCESS_COUNT(0, "SuccessCount"),
/* 198 */     AFFECTED_BLOCKS(1, "AffectedBlocks"),
/* 199 */     AFFECTED_ENTITIES(2, "AffectedEntities"),
/* 200 */     AFFECTED_ITEMS(3, "AffectedItems"),
/* 201 */     QUERY_RESULT(4, "QueryResult");
/*     */     
/*     */     final int typeID;
/*     */     final String typeName;
/*     */     
/*     */     Type(int id, String name) {
/* 207 */       this.typeID = id;
/* 208 */       this.typeName = name;
/*     */     }
/*     */     
/*     */     public int getTypeID() {
/* 212 */       return this.typeID;
/*     */     }
/*     */     
/*     */     public String getTypeName() {
/* 216 */       return this.typeName;
/*     */     }
/*     */     
/*     */     public static String[] getTypeNames() {
/* 220 */       String[] astring = new String[(values()).length];
/* 221 */       int i = 0; byte b; int j;
/*     */       Type[] arrayOfType;
/* 223 */       for (j = (arrayOfType = values()).length, b = 0; b < j; ) { Type commandresultstats$type = arrayOfType[b];
/* 224 */         astring[i++] = commandresultstats$type.getTypeName();
/*     */         b++; }
/*     */       
/* 227 */       return astring; } public static Type getTypeByName(String name) {
/*     */       byte b;
/*     */       int i;
/*     */       Type[] arrayOfType;
/* 231 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/* 232 */         if (commandresultstats$type.getTypeName().equals(name)) {
/* 233 */           return commandresultstats$type;
/*     */         }
/*     */         b++; }
/*     */       
/* 237 */       return null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandResultStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */