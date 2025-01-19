/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "spreadplayers";
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
/*  41 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  48 */     if (args.length < 6) {
/*  49 */       throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
/*     */     }
/*  51 */     int i = 0;
/*  52 */     BlockPos blockpos = sender.getPosition();
/*  53 */     double d0 = parseDouble(blockpos.getX(), args[i++], true);
/*  54 */     double d1 = parseDouble(blockpos.getZ(), args[i++], true);
/*  55 */     double d2 = parseDouble(args[i++], 0.0D);
/*  56 */     double d3 = parseDouble(args[i++], d2 + 1.0D);
/*  57 */     boolean flag = parseBoolean(args[i++]);
/*  58 */     List<Entity> list = Lists.newArrayList();
/*     */     
/*  60 */     while (i < args.length) {
/*  61 */       String s = args[i++];
/*     */       
/*  63 */       if (PlayerSelector.hasArguments(s)) {
/*  64 */         List<Entity> list1 = PlayerSelector.matchEntities(sender, s, Entity.class);
/*     */         
/*  66 */         if (list1.size() == 0) {
/*  67 */           throw new EntityNotFoundException();
/*     */         }
/*     */         
/*  70 */         list.addAll(list1); continue;
/*     */       } 
/*  72 */       EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
/*     */       
/*  74 */       if (entityPlayerMP == null) {
/*  75 */         throw new PlayerNotFoundException();
/*     */       }
/*     */       
/*  78 */       list.add(entityPlayerMP);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */     
/*  84 */     if (list.isEmpty()) {
/*  85 */       throw new EntityNotFoundException();
/*     */     }
/*  87 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(list.size()), Double.valueOf(d3), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2) }));
/*  88 */     func_110669_a(sender, list, new Position(d0, d1), d2, d3, ((Entity)list.get(0)).worldObj, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_110669_a(ICommandSender p_110669_1_, List<Entity> p_110669_2_, Position p_110669_3_, double p_110669_4_, double p_110669_6_, World worldIn, boolean p_110669_9_) throws CommandException {
/*  94 */     Random random = new Random();
/*  95 */     double d0 = p_110669_3_.field_111101_a - p_110669_6_;
/*  96 */     double d1 = p_110669_3_.field_111100_b - p_110669_6_;
/*  97 */     double d2 = p_110669_3_.field_111101_a + p_110669_6_;
/*  98 */     double d3 = p_110669_3_.field_111100_b + p_110669_6_;
/*  99 */     Position[] acommandspreadplayers$position = func_110670_a(random, p_110669_9_ ? func_110667_a(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
/* 100 */     int i = func_110668_a(p_110669_3_, p_110669_4_, worldIn, random, d0, d1, d2, d3, acommandspreadplayers$position, p_110669_9_);
/* 101 */     double d4 = func_110671_a(p_110669_2_, worldIn, acommandspreadplayers$position, p_110669_9_);
/* 102 */     notifyOperators(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers$position.length), Double.valueOf(p_110669_3_.field_111101_a), Double.valueOf(p_110669_3_.field_111100_b) });
/*     */     
/* 104 */     if (acommandspreadplayers$position.length > 1) {
/* 105 */       p_110669_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d4) }), Integer.valueOf(i) }));
/*     */     }
/*     */   }
/*     */   
/*     */   private int func_110667_a(List<Entity> p_110667_1_) {
/* 110 */     Set<Team> set = Sets.newHashSet();
/*     */     
/* 112 */     for (Entity entity : p_110667_1_) {
/* 113 */       if (entity instanceof EntityPlayer) {
/* 114 */         set.add(((EntityPlayer)entity).getTeam()); continue;
/*     */       } 
/* 116 */       set.add(null);
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return set.size();
/*     */   }
/*     */   
/*     */   private int func_110668_a(Position p_110668_1_, double p_110668_2_, World worldIn, Random p_110668_5_, double p_110668_6_, double p_110668_8_, double p_110668_10_, double p_110668_12_, Position[] p_110668_14_, boolean p_110668_15_) throws CommandException {
/* 124 */     boolean flag = true;
/* 125 */     double d0 = 3.4028234663852886E38D;
/*     */     
/*     */     int i;
/* 128 */     for (i = 0; i < 10000 && flag; i++) {
/* 129 */       flag = false;
/* 130 */       d0 = 3.4028234663852886E38D;
/*     */       
/* 132 */       for (int j = 0; j < p_110668_14_.length; j++) {
/* 133 */         Position commandspreadplayers$position = p_110668_14_[j];
/* 134 */         int k = 0;
/* 135 */         Position commandspreadplayers$position1 = new Position();
/*     */         
/* 137 */         for (int l = 0; l < p_110668_14_.length; l++) {
/* 138 */           if (j != l) {
/* 139 */             Position commandspreadplayers$position2 = p_110668_14_[l];
/* 140 */             double d1 = commandspreadplayers$position.func_111099_a(commandspreadplayers$position2);
/* 141 */             d0 = Math.min(d1, d0);
/*     */             
/* 143 */             if (d1 < p_110668_2_) {
/* 144 */               k++;
/* 145 */               commandspreadplayers$position1.field_111101_a += commandspreadplayers$position2.field_111101_a - commandspreadplayers$position.field_111101_a;
/* 146 */               commandspreadplayers$position1.field_111100_b += commandspreadplayers$position2.field_111100_b - commandspreadplayers$position.field_111100_b;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 151 */         if (k > 0) {
/* 152 */           commandspreadplayers$position1.field_111101_a /= k;
/* 153 */           commandspreadplayers$position1.field_111100_b /= k;
/* 154 */           double d2 = commandspreadplayers$position1.func_111096_b();
/*     */           
/* 156 */           if (d2 > 0.0D) {
/* 157 */             commandspreadplayers$position1.func_111095_a();
/* 158 */             commandspreadplayers$position.func_111094_b(commandspreadplayers$position1);
/*     */           } else {
/* 160 */             commandspreadplayers$position.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/*     */           } 
/*     */           
/* 163 */           flag = true;
/*     */         } 
/*     */         
/* 166 */         if (commandspreadplayers$position.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_)) {
/* 167 */           flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 171 */       if (!flag) {
/* 172 */         byte b; int k; Position[] arrayOfPosition; for (k = (arrayOfPosition = p_110668_14_).length, b = 0; b < k; ) { Position commandspreadplayers$position3 = arrayOfPosition[b];
/* 173 */           if (!commandspreadplayers$position3.func_111098_b(worldIn)) {
/* 174 */             commandspreadplayers$position3.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/* 175 */             flag = true;
/*     */           } 
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 181 */     if (i >= 10000) {
/* 182 */       throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"), new Object[] { Integer.valueOf(p_110668_14_.length), Double.valueOf(p_110668_1_.field_111101_a), Double.valueOf(p_110668_1_.field_111100_b), String.format("%.2f", new Object[] { Double.valueOf(d0) }) });
/*     */     }
/* 184 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_110671_a(List<Entity> p_110671_1_, World worldIn, Position[] p_110671_3_, boolean p_110671_4_) {
/* 189 */     double d0 = 0.0D;
/* 190 */     int i = 0;
/* 191 */     Map<Team, Position> map = Maps.newHashMap();
/*     */     
/* 193 */     for (int j = 0; j < p_110671_1_.size(); j++) {
/* 194 */       Position commandspreadplayers$position; Entity entity = p_110671_1_.get(j);
/*     */ 
/*     */       
/* 197 */       if (p_110671_4_) {
/* 198 */         Team team = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getTeam() : null;
/*     */         
/* 200 */         if (!map.containsKey(team)) {
/* 201 */           map.put(team, p_110671_3_[i++]);
/*     */         }
/*     */         
/* 204 */         commandspreadplayers$position = map.get(team);
/*     */       } else {
/* 206 */         commandspreadplayers$position = p_110671_3_[i++];
/*     */       } 
/*     */       
/* 209 */       entity.setPositionAndUpdate((MathHelper.floor_double(commandspreadplayers$position.field_111101_a) + 0.5F), commandspreadplayers$position.func_111092_a(worldIn), MathHelper.floor_double(commandspreadplayers$position.field_111100_b) + 0.5D);
/* 210 */       double d2 = Double.MAX_VALUE;
/*     */       
/* 212 */       for (int k = 0; k < p_110671_3_.length; k++) {
/* 213 */         if (commandspreadplayers$position != p_110671_3_[k]) {
/* 214 */           double d1 = commandspreadplayers$position.func_111099_a(p_110671_3_[k]);
/* 215 */           d2 = Math.min(d1, d2);
/*     */         } 
/*     */       } 
/*     */       
/* 219 */       d0 += d2;
/*     */     } 
/*     */     
/* 222 */     d0 /= p_110671_1_.size();
/* 223 */     return d0;
/*     */   }
/*     */   
/*     */   private Position[] func_110670_a(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
/* 227 */     Position[] acommandspreadplayers$position = new Position[p_110670_2_];
/*     */     
/* 229 */     for (int i = 0; i < acommandspreadplayers$position.length; i++) {
/* 230 */       Position commandspreadplayers$position = new Position();
/* 231 */       commandspreadplayers$position.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
/* 232 */       acommandspreadplayers$position[i] = commandspreadplayers$position;
/*     */     } 
/*     */     
/* 235 */     return acommandspreadplayers$position;
/*     */   }
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 239 */     return (args.length >= 1 && args.length <= 2) ? func_181043_b(args, 0, pos) : null;
/*     */   }
/*     */   
/*     */   static class Position
/*     */   {
/*     */     double field_111101_a;
/*     */     double field_111100_b;
/*     */     
/*     */     Position() {}
/*     */     
/*     */     Position(double p_i1358_1_, double p_i1358_3_) {
/* 250 */       this.field_111101_a = p_i1358_1_;
/* 251 */       this.field_111100_b = p_i1358_3_;
/*     */     }
/*     */     
/*     */     double func_111099_a(Position p_111099_1_) {
/* 255 */       double d0 = this.field_111101_a - p_111099_1_.field_111101_a;
/* 256 */       double d1 = this.field_111100_b - p_111099_1_.field_111100_b;
/* 257 */       return Math.sqrt(d0 * d0 + d1 * d1);
/*     */     }
/*     */     
/*     */     void func_111095_a() {
/* 261 */       double d0 = func_111096_b();
/* 262 */       this.field_111101_a /= d0;
/* 263 */       this.field_111100_b /= d0;
/*     */     }
/*     */     
/*     */     float func_111096_b() {
/* 267 */       return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
/*     */     }
/*     */     
/*     */     public void func_111094_b(Position p_111094_1_) {
/* 271 */       this.field_111101_a -= p_111094_1_.field_111101_a;
/* 272 */       this.field_111100_b -= p_111094_1_.field_111100_b;
/*     */     }
/*     */     
/*     */     public boolean func_111093_a(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
/* 276 */       boolean flag = false;
/*     */       
/* 278 */       if (this.field_111101_a < p_111093_1_) {
/* 279 */         this.field_111101_a = p_111093_1_;
/* 280 */         flag = true;
/* 281 */       } else if (this.field_111101_a > p_111093_5_) {
/* 282 */         this.field_111101_a = p_111093_5_;
/* 283 */         flag = true;
/*     */       } 
/*     */       
/* 286 */       if (this.field_111100_b < p_111093_3_) {
/* 287 */         this.field_111100_b = p_111093_3_;
/* 288 */         flag = true;
/* 289 */       } else if (this.field_111100_b > p_111093_7_) {
/* 290 */         this.field_111100_b = p_111093_7_;
/* 291 */         flag = true;
/*     */       } 
/*     */       
/* 294 */       return flag;
/*     */     }
/*     */     
/*     */     public int func_111092_a(World worldIn) {
/* 298 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 300 */       while (blockpos.getY() > 0) {
/* 301 */         blockpos = blockpos.down();
/*     */         
/* 303 */         if (worldIn.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/* 304 */           return blockpos.getY() + 1;
/*     */         }
/*     */       } 
/*     */       
/* 308 */       return 257;
/*     */     }
/*     */     
/*     */     public boolean func_111098_b(World worldIn) {
/* 312 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 314 */       while (blockpos.getY() > 0) {
/* 315 */         blockpos = blockpos.down();
/* 316 */         Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */         
/* 318 */         if (material != Material.air) {
/* 319 */           return (!material.isLiquid() && material != Material.fire);
/*     */         }
/*     */       } 
/*     */       
/* 323 */       return false;
/*     */     }
/*     */     
/*     */     public void func_111097_a(Random p_111097_1_, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
/* 327 */       this.field_111101_a = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_2_, p_111097_6_);
/* 328 */       this.field_111100_b = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_4_, p_111097_8_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandSpreadPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */