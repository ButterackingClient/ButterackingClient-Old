/*     */ package net.minecraft.util;public enum EnumFacing implements IStringSerializable { private final int index; private final int opposite; private final int horizontalIndex; private final String name; private final Axis axis; private final AxisDirection axisDirection; private final Vec3i directionVec; public static final EnumFacing[] VALUES; private static final EnumFacing[] HORIZONTALS; private static final Map<String, EnumFacing> NAME_LOOKUP; EnumFacing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, AxisDirection axisDirectionIn, Axis axisIn, Vec3i directionVecIn) {
/*     */     this.index = indexIn;
/*     */     this.horizontalIndex = horizontalIndexIn;
/*     */     this.opposite = oppositeIn;
/*     */     this.name = nameIn;
/*     */     this.axis = axisIn;
/*     */     this.axisDirection = axisDirectionIn;
/*     */     this.directionVec = directionVecIn;
/*     */   } public int getIndex() {
/*     */     return this.index;
/*     */   }
/*  12 */   DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
/*  13 */   UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
/*  14 */   NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
/*  15 */   SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
/*  16 */   WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
/*  17 */   EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
/*     */   public int getHorizontalIndex() { return this.horizontalIndex; }
/*     */   public AxisDirection getAxisDirection() { return this.axisDirection; }
/*     */   public EnumFacing getOpposite() { return VALUES[this.opposite]; }
/*     */   public EnumFacing rotateAround(Axis axis) { switch (axis) { case null: if (this != WEST && this != EAST)
/*     */           return rotateX();  return this;case Y: if (this != UP && this != DOWN)
/*     */           return rotateY();  return this;case Z: if (this != NORTH && this != SOUTH)
/*     */           return rotateZ();  return this; }  throw new IllegalStateException("Unable to get CW facing for axis " + axis); }
/*     */   public EnumFacing rotateY() { switch (this) { case NORTH: return EAST;
/*     */       case EAST: return SOUTH;
/*     */       case SOUTH: return WEST;
/*     */       case WEST: return NORTH; }  throw new IllegalStateException("Unable to get Y-rotated facing of " + this); }
/*     */   private EnumFacing rotateX() { switch (this) { case NORTH: return DOWN;
/*     */       default: throw new IllegalStateException("Unable to get X-rotated facing of " + this);
/*     */       case SOUTH: return UP;
/*     */       case UP: return NORTH;
/*     */       case null: break; }  return SOUTH; }
/*     */   private EnumFacing rotateZ() { switch (this) { case EAST: return DOWN;
/*     */       default: throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
/*     */       case WEST: return UP;
/*     */       case UP: return EAST;
/*     */       case null: break; }  return WEST; }
/*     */   public EnumFacing rotateYCCW() { switch (this) { case NORTH: return WEST;
/*     */       case EAST: return NORTH;
/*     */       case SOUTH: return EAST;
/*     */       case WEST: return SOUTH; }  throw new IllegalStateException("Unable to get CCW facing of " + this); }
/*     */   public int getFrontOffsetX() { return (this.axis == Axis.X) ? this.axisDirection.getOffset() : 0; }
/*     */   public int getFrontOffsetY() { return (this.axis == Axis.Y) ? this.axisDirection.getOffset() : 0; }
/*  45 */   public int getFrontOffsetZ() { return (this.axis == Axis.Z) ? this.axisDirection.getOffset() : 0; } static { VALUES = new EnumFacing[6];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     HORIZONTALS = new EnumFacing[4];
/*  51 */     NAME_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 321 */       VALUES[enumfacing.index] = enumfacing;
/*     */       
/* 323 */       if (enumfacing.getAxis().isHorizontal()) {
/* 324 */         HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
/*     */       }
/*     */       
/* 327 */       NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(), enumfacing); b++; }  } public String getName2() { return this.name; } public Axis getAxis() { return this.axis; } public static EnumFacing byName(String name) { return (name == null) ? null : NAME_LOOKUP.get(name.toLowerCase()); } public static EnumFacing getFront(int index) { return VALUES[MathHelper.abs_int(index % VALUES.length)]; } public static EnumFacing getHorizontal(int p_176731_0_) { return HORIZONTALS[MathHelper.abs_int(p_176731_0_ % HORIZONTALS.length)]; } public static EnumFacing fromAngle(double angle) { return getHorizontal(MathHelper.floor_double(angle / 90.0D + 0.5D) & 0x3); } public static EnumFacing random(Random rand) { return values()[rand.nextInt((values()).length)]; } public static EnumFacing getFacingFromVector(float p_176737_0_, float p_176737_1_, float p_176737_2_) { EnumFacing enumfacing = NORTH; float f = Float.MIN_VALUE; byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b]; float f1 = p_176737_0_ * enumfacing1.directionVec.getX() + p_176737_1_ * enumfacing1.directionVec.getY() + p_176737_2_ * enumfacing1.directionVec.getZ(); if (f1 > f) { f = f1; enumfacing = enumfacing1; }  b++; }  return enumfacing; }
/*     */   public String toString() { return this.name; }
/*     */   public String getName() { return this.name; }
/*     */   public static EnumFacing getFacingFromAxis(AxisDirection p_181076_0_, Axis p_181076_1_) { byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b]; if (enumfacing.getAxisDirection() == p_181076_0_ && enumfacing.getAxis() == p_181076_1_) return enumfacing;  b++; }  throw new IllegalArgumentException("No such direction: " + p_181076_0_ + " " + p_181076_1_); }
/*     */   public Vec3i getDirectionVec() { return this.directionVec; }
/* 332 */   public enum Axis implements Predicate<EnumFacing>, IStringSerializable { X("x", EnumFacing.Plane.HORIZONTAL),
/* 333 */     Y("y", EnumFacing.Plane.VERTICAL),
/* 334 */     Z("z", EnumFacing.Plane.HORIZONTAL);
/*     */     
/* 336 */     private static final Map<String, Axis> NAME_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final EnumFacing.Plane plane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Axis[] arrayOfAxis;
/* 378 */       for (i = (arrayOfAxis = values()).length, b = 0; b < i; ) { Axis enumfacing$axis = arrayOfAxis[b];
/* 379 */         NAME_LOOKUP.put(enumfacing$axis.getName2().toLowerCase(), enumfacing$axis); b++; } 
/*     */     } Axis(String name, EnumFacing.Plane plane) { this.name = name; this.plane = plane; } public static Axis byName(String name) { return (name == null) ? null : NAME_LOOKUP.get(name.toLowerCase()); } public String getName2() { return this.name; } public boolean isVertical() { return (this.plane == EnumFacing.Plane.VERTICAL); } public boolean isHorizontal() { return (this.plane == EnumFacing.Plane.HORIZONTAL); }
/*     */     public String toString() { return this.name; }
/*     */     public boolean apply(EnumFacing p_apply_1_) { return (p_apply_1_ != null && p_apply_1_.getAxis() == this); }
/*     */     public EnumFacing.Plane getPlane() { return this.plane; }
/*     */     public String getName() { return this.name; } }
/* 385 */   public enum AxisDirection { POSITIVE(1, "Towards positive"),
/* 386 */     NEGATIVE(-1, "Towards negative");
/*     */     
/*     */     private final int offset;
/*     */     private final String description;
/*     */     
/*     */     AxisDirection(int offset, String description) {
/* 392 */       this.offset = offset;
/* 393 */       this.description = description;
/*     */     }
/*     */     
/*     */     public int getOffset() {
/* 397 */       return this.offset;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 401 */       return this.description;
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum Plane implements Predicate<EnumFacing>, Iterable<EnumFacing> {
/* 406 */     HORIZONTAL,
/* 407 */     VERTICAL;
/*     */     
/*     */     public EnumFacing[] facings() {
/* 410 */       switch (this) {
/*     */         case null:
/* 412 */           return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
/*     */         case VERTICAL:
/* 414 */           return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
/*     */       } 
/* 416 */       throw new Error("Someone's been tampering with the universe!");
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing random(Random rand) {
/* 421 */       EnumFacing[] aenumfacing = facings();
/* 422 */       return aenumfacing[rand.nextInt(aenumfacing.length)];
/*     */     }
/*     */     
/*     */     public boolean apply(EnumFacing p_apply_1_) {
/* 426 */       return (p_apply_1_ != null && p_apply_1_.getAxis().getPlane() == this);
/*     */     }
/*     */     
/*     */     public Iterator<EnumFacing> iterator() {
/* 430 */       return (Iterator<EnumFacing>)Iterators.forArray((Object[])facings());
/*     */     }
/*     */   } }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\EnumFacing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */