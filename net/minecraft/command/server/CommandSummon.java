/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSummon
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "summon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  34 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.summon.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  48 */     if (args.length < 1) {
/*  49 */       throw new WrongUsageException("commands.summon.usage", new Object[0]);
/*     */     }
/*  51 */     String s = args[0];
/*  52 */     BlockPos blockpos = sender.getPosition();
/*  53 */     Vec3 vec3 = sender.getPositionVector();
/*  54 */     double d0 = vec3.xCoord;
/*  55 */     double d1 = vec3.yCoord;
/*  56 */     double d2 = vec3.zCoord;
/*     */     
/*  58 */     if (args.length >= 4) {
/*  59 */       d0 = parseDouble(d0, args[1], true);
/*  60 */       d1 = parseDouble(d1, args[2], false);
/*  61 */       d2 = parseDouble(d2, args[3], true);
/*  62 */       blockpos = new BlockPos(d0, d1, d2);
/*     */     } 
/*     */     
/*  65 */     World world = sender.getEntityWorld();
/*     */     
/*  67 */     if (!world.isBlockLoaded(blockpos))
/*  68 */       throw new CommandException("commands.summon.outOfWorld", new Object[0]); 
/*  69 */     if ("LightningBolt".equals(s)) {
/*  70 */       world.addWeatherEffect((Entity)new EntityLightningBolt(world, d0, d1, d2));
/*  71 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } else {
/*  73 */       Entity entity2; NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  74 */       boolean flag = false;
/*     */       
/*  76 */       if (args.length >= 5) {
/*  77 */         IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 4);
/*     */         
/*     */         try {
/*  80 */           nbttagcompound = JsonToNBT.getTagFromJson(ichatcomponent.getUnformattedText());
/*  81 */           flag = true;
/*  82 */         } catch (NBTException nbtexception) {
/*  83 */           throw new CommandException("commands.summon.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       nbttagcompound.setString("id", s);
/*     */ 
/*     */       
/*     */       try {
/*  91 */         entity2 = EntityList.createEntityFromNBT(nbttagcompound, world);
/*  92 */       } catch (RuntimeException var19) {
/*  93 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       } 
/*     */       
/*  96 */       if (entity2 == null) {
/*  97 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       }
/*  99 */       entity2.setLocationAndAngles(d0, d1, d2, entity2.rotationYaw, entity2.rotationPitch);
/*     */       
/* 101 */       if (!flag && entity2 instanceof EntityLiving) {
/* 102 */         ((EntityLiving)entity2).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity2)), null);
/*     */       }
/*     */       
/* 105 */       world.spawnEntityInWorld(entity2);
/* 106 */       Entity entity = entity2;
/*     */       
/* 108 */       for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity != null && nbttagcompound1.hasKey("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompoundTag("Riding")) {
/* 109 */         Entity entity1 = EntityList.createEntityFromNBT(nbttagcompound1.getCompoundTag("Riding"), world);
/*     */         
/* 111 */         if (entity1 != null) {
/* 112 */           entity1.setLocationAndAngles(d0, d1, d2, entity1.rotationYaw, entity1.rotationPitch);
/* 113 */           world.spawnEntityInWorld(entity1);
/* 114 */           entity.mountEntity(entity1);
/*     */         } 
/*     */         
/* 117 */         entity = entity1;
/*     */       } 
/*     */       
/* 120 */       notifyOperators(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 127 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : null);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandSummon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */