/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntityAITasks
/*     */ {
/*  13 */   private static final Logger logger = LogManager.getLogger();
/*  14 */   private List<EntityAITaskEntry> taskEntries = Lists.newArrayList();
/*  15 */   private List<EntityAITaskEntry> executingTaskEntries = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private final Profiler theProfiler;
/*     */   
/*     */   private int tickCount;
/*     */   
/*  22 */   private int tickRate = 3;
/*     */   
/*     */   public EntityAITasks(Profiler profilerIn) {
/*  25 */     this.theProfiler = profilerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTask(int priority, EntityAIBase task) {
/*  32 */     this.taskEntries.add(new EntityAITaskEntry(priority, task));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTask(EntityAIBase task) {
/*  39 */     Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
/*     */     
/*  41 */     while (iterator.hasNext()) {
/*  42 */       EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
/*  43 */       EntityAIBase entityaibase = entityaitasks$entityaitaskentry.action;
/*     */       
/*  45 */       if (entityaibase == task) {
/*  46 */         if (this.executingTaskEntries.contains(entityaitasks$entityaitaskentry)) {
/*  47 */           entityaibase.resetTask();
/*  48 */           this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */         } 
/*     */         
/*  51 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onUpdateTasks() {
/*  57 */     this.theProfiler.startSection("goalSetup");
/*     */     
/*  59 */     if (this.tickCount++ % this.tickRate == 0) {
/*  60 */       Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  67 */       while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */         
/*  71 */         EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
/*  72 */         boolean flag = this.executingTaskEntries.contains(entityaitasks$entityaitaskentry);
/*     */         
/*  74 */         if (flag)
/*     */         {
/*     */ 
/*     */           
/*  78 */           if (!canUse(entityaitasks$entityaitaskentry) || !canContinue(entityaitasks$entityaitaskentry)) {
/*  79 */             entityaitasks$entityaitaskentry.action.resetTask();
/*  80 */             this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         }
/*  85 */         if (canUse(entityaitasks$entityaitaskentry) && entityaitasks$entityaitaskentry.action.shouldExecute()) {
/*  86 */           entityaitasks$entityaitaskentry.action.startExecuting();
/*  87 */           this.executingTaskEntries.add(entityaitasks$entityaitaskentry);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  91 */       Iterator<EntityAITaskEntry> iterator1 = this.executingTaskEntries.iterator();
/*     */       
/*  93 */       while (iterator1.hasNext()) {
/*  94 */         EntityAITaskEntry entityaitasks$entityaitaskentry1 = iterator1.next();
/*     */         
/*  96 */         if (!canContinue(entityaitasks$entityaitaskentry1)) {
/*  97 */           entityaitasks$entityaitaskentry1.action.resetTask();
/*  98 */           iterator1.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     this.theProfiler.endSection();
/* 104 */     this.theProfiler.startSection("goalTick");
/*     */     
/* 106 */     for (EntityAITaskEntry entityaitasks$entityaitaskentry2 : this.executingTaskEntries) {
/* 107 */       entityaitasks$entityaitaskentry2.action.updateTask();
/*     */     }
/*     */     
/* 110 */     this.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canContinue(EntityAITaskEntry taskEntry) {
/* 117 */     boolean flag = taskEntry.action.continueExecuting();
/* 118 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canUse(EntityAITaskEntry taskEntry) {
/* 126 */     for (EntityAITaskEntry entityaitasks$entityaitaskentry : this.taskEntries) {
/* 127 */       if (entityaitasks$entityaitaskentry != taskEntry) {
/* 128 */         if (taskEntry.priority >= entityaitasks$entityaitaskentry.priority) {
/* 129 */           if (!areTasksCompatible(taskEntry, entityaitasks$entityaitaskentry) && this.executingTaskEntries.contains(entityaitasks$entityaitaskentry))
/* 130 */             return false;  continue;
/*     */         } 
/* 132 */         if (!entityaitasks$entityaitaskentry.action.isInterruptible() && this.executingTaskEntries.contains(entityaitasks$entityaitaskentry)) {
/* 133 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean areTasksCompatible(EntityAITaskEntry taskEntry1, EntityAITaskEntry taskEntry2) {
/* 145 */     return ((taskEntry1.action.getMutexBits() & taskEntry2.action.getMutexBits()) == 0);
/*     */   }
/*     */   
/*     */   class EntityAITaskEntry {
/*     */     public EntityAIBase action;
/*     */     public int priority;
/*     */     
/*     */     public EntityAITaskEntry(int priorityIn, EntityAIBase task) {
/* 153 */       this.priority = priorityIn;
/* 154 */       this.action = task;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAITasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */