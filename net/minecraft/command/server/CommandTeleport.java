/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class CommandTeleport
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "tp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.tp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     Entity entity;
/*  44 */     if (args.length < 1) {
/*  45 */       throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */     }
/*  47 */     int i = 0;
/*     */ 
/*     */     
/*  50 */     if (args.length != 2 && args.length != 4 && args.length != 6) {
/*  51 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/*     */     } else {
/*  53 */       entity = getEntity(sender, args[0]);
/*  54 */       i = 1;
/*     */     } 
/*     */     
/*  57 */     if (args.length != 1 && args.length != 2) {
/*  58 */       if (args.length < i + 3)
/*  59 */         throw new WrongUsageException("commands.tp.usage", new Object[0]); 
/*  60 */       if (entity.worldObj != null) {
/*  61 */         int lvt_5_2_ = i + 1;
/*  62 */         CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(entity.posX, args[i], true);
/*  63 */         CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(entity.posY, args[lvt_5_2_++], 0, 0, false);
/*  64 */         CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(entity.posZ, args[lvt_5_2_++], true);
/*  65 */         CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate(entity.rotationYaw, (args.length > lvt_5_2_) ? args[lvt_5_2_++] : "~", false);
/*  66 */         CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate(entity.rotationPitch, (args.length > lvt_5_2_) ? args[lvt_5_2_] : "~", false);
/*     */         
/*  68 */         if (entity instanceof EntityPlayerMP) {
/*  69 */           Set<S08PacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
/*     */           
/*  71 */           if (commandbase$coordinatearg.func_179630_c()) {
/*  72 */             set.add(S08PacketPlayerPosLook.EnumFlags.X);
/*     */           }
/*     */           
/*  75 */           if (commandbase$coordinatearg1.func_179630_c()) {
/*  76 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y);
/*     */           }
/*     */           
/*  79 */           if (commandbase$coordinatearg2.func_179630_c()) {
/*  80 */             set.add(S08PacketPlayerPosLook.EnumFlags.Z);
/*     */           }
/*     */           
/*  83 */           if (commandbase$coordinatearg4.func_179630_c()) {
/*  84 */             set.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
/*     */           }
/*     */           
/*  87 */           if (commandbase$coordinatearg3.func_179630_c()) {
/*  88 */             set.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
/*     */           }
/*     */           
/*  91 */           float f = (float)commandbase$coordinatearg3.func_179629_b();
/*     */           
/*  93 */           if (!commandbase$coordinatearg3.func_179630_c()) {
/*  94 */             f = MathHelper.wrapAngleTo180_float(f);
/*     */           }
/*     */           
/*  97 */           float f1 = (float)commandbase$coordinatearg4.func_179629_b();
/*     */           
/*  99 */           if (!commandbase$coordinatearg4.func_179630_c()) {
/* 100 */             f1 = MathHelper.wrapAngleTo180_float(f1);
/*     */           }
/*     */           
/* 103 */           if (f1 > 90.0F || f1 < -90.0F) {
/* 104 */             f1 = MathHelper.wrapAngleTo180_float(180.0F - f1);
/* 105 */             f = MathHelper.wrapAngleTo180_float(f + 180.0F);
/*     */           } 
/*     */           
/* 108 */           entity.mountEntity(null);
/* 109 */           ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(commandbase$coordinatearg.func_179629_b(), commandbase$coordinatearg1.func_179629_b(), commandbase$coordinatearg2.func_179629_b(), f, f1, set);
/* 110 */           entity.setRotationYawHead(f);
/*     */         } else {
/* 112 */           float f2 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg3.func_179628_a());
/* 113 */           float f3 = (float)MathHelper.wrapAngleTo180_double(commandbase$coordinatearg4.func_179628_a());
/*     */           
/* 115 */           if (f3 > 90.0F || f3 < -90.0F) {
/* 116 */             f3 = MathHelper.wrapAngleTo180_float(180.0F - f3);
/* 117 */             f2 = MathHelper.wrapAngleTo180_float(f2 + 180.0F);
/*     */           } 
/*     */           
/* 120 */           entity.setLocationAndAngles(commandbase$coordinatearg.func_179628_a(), commandbase$coordinatearg1.func_179628_a(), commandbase$coordinatearg2.func_179628_a(), f2, f3);
/* 121 */           entity.setRotationYawHead(f2);
/*     */         } 
/*     */         
/* 124 */         notifyOperators(sender, (ICommand)this, "commands.tp.success.coordinates", new Object[] { entity.getName(), Double.valueOf(commandbase$coordinatearg.func_179628_a()), Double.valueOf(commandbase$coordinatearg1.func_179628_a()), Double.valueOf(commandbase$coordinatearg2.func_179628_a()) });
/*     */       } 
/*     */     } else {
/* 127 */       Entity entity1 = getEntity(sender, args[args.length - 1]);
/*     */       
/* 129 */       if (entity1.worldObj != entity.worldObj) {
/* 130 */         throw new CommandException("commands.tp.notSameDimension", new Object[0]);
/*     */       }
/* 132 */       entity.mountEntity(null);
/*     */       
/* 134 */       if (entity instanceof EntityPlayerMP) {
/* 135 */         ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       } else {
/* 137 */         entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       } 
/*     */       
/* 140 */       notifyOperators(sender, (ICommand)this, "commands.tp.success", new Object[] { entity.getName(), entity1.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 147 */     return (args.length != 1 && args.length != 2) ? null : getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 154 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */