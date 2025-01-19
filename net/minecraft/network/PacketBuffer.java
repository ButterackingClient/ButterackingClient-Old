/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.buffer.ByteBufProcessor;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class PacketBuffer
/*     */   extends ByteBuf {
/*     */   public PacketBuffer(ByteBuf wrapped) {
/*  34 */     this.buf = wrapped;
/*     */   }
/*     */ 
/*     */   
/*     */   private final ByteBuf buf;
/*     */ 
/*     */   
/*     */   public static int getVarIntSize(int input) {
/*  42 */     for (int i = 1; i < 5; i++) {
/*  43 */       if ((input & -1 << i * 7) == 0) {
/*  44 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  48 */     return 5;
/*     */   }
/*     */   
/*     */   public void writeByteArray(byte[] array) {
/*  52 */     writeVarIntToBuffer(array.length);
/*  53 */     writeBytes(array);
/*     */   }
/*     */   
/*     */   public byte[] readByteArray() {
/*  57 */     byte[] abyte = new byte[readVarIntFromBuffer()];
/*  58 */     readBytes(abyte);
/*  59 */     return abyte;
/*     */   }
/*     */   
/*     */   public BlockPos readBlockPos() {
/*  63 */     return BlockPos.fromLong(readLong());
/*     */   }
/*     */   
/*     */   public void writeBlockPos(BlockPos pos) {
/*  67 */     writeLong(pos.toLong());
/*     */   }
/*     */   
/*     */   public IChatComponent readChatComponent() throws IOException {
/*  71 */     return IChatComponent.Serializer.jsonToComponent(readStringFromBuffer(32767));
/*     */   }
/*     */   
/*     */   public void writeChatComponent(IChatComponent component) throws IOException {
/*  75 */     writeString(IChatComponent.Serializer.componentToJson(component));
/*     */   }
/*     */   
/*     */   public <T extends Enum<T>> T readEnumValue(Class<T> enumClass) {
/*  79 */     return (T)((Enum[])enumClass.getEnumConstants())[readVarIntFromBuffer()];
/*     */   }
/*     */   
/*     */   public void writeEnumValue(Enum<?> value) {
/*  83 */     writeVarIntToBuffer(value.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readVarIntFromBuffer() {
/*     */     byte b0;
/*  91 */     int i = 0;
/*  92 */     int j = 0;
/*     */     
/*     */     do {
/*  95 */       b0 = readByte();
/*  96 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*     */       
/*  98 */       if (j > 5) {
/*  99 */         throw new RuntimeException("VarInt too big");
/*     */       }
/*     */     }
/* 102 */     while ((b0 & 0x80) == 128);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return i;
/*     */   }
/*     */   public long readVarLong() {
/*     */     byte b0;
/* 111 */     long i = 0L;
/* 112 */     int j = 0;
/*     */     
/*     */     do {
/* 115 */       b0 = readByte();
/* 116 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*     */       
/* 118 */       if (j > 10) {
/* 119 */         throw new RuntimeException("VarLong too big");
/*     */       }
/*     */     }
/* 122 */     while ((b0 & 0x80) == 128);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return i;
/*     */   }
/*     */   
/*     */   public void writeUuid(UUID uuid) {
/* 131 */     writeLong(uuid.getMostSignificantBits());
/* 132 */     writeLong(uuid.getLeastSignificantBits());
/*     */   }
/*     */   
/*     */   public UUID readUuid() {
/* 136 */     return new UUID(readLong(), readLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeVarIntToBuffer(int input) {
/* 146 */     while ((input & 0xFFFFFF80) != 0) {
/* 147 */       writeByte(input & 0x7F | 0x80);
/* 148 */       input >>>= 7;
/*     */     } 
/*     */     
/* 151 */     writeByte(input);
/*     */   }
/*     */   
/*     */   public void writeVarLong(long value) {
/* 155 */     while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
/* 156 */       writeByte((int)(value & 0x7FL) | 0x80);
/* 157 */       value >>>= 7L;
/*     */     } 
/*     */     
/* 160 */     writeByte((int)value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNBTTagCompoundToBuffer(NBTTagCompound nbt) {
/* 167 */     if (nbt == null) {
/* 168 */       writeByte(0);
/*     */     } else {
/*     */       try {
/* 171 */         CompressedStreamTools.write(nbt, (DataOutput)new ByteBufOutputStream(this));
/* 172 */       } catch (IOException ioexception) {
/* 173 */         throw new EncoderException(ioexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
/* 182 */     int i = readerIndex();
/* 183 */     byte b0 = readByte();
/*     */     
/* 185 */     if (b0 == 0) {
/* 186 */       return null;
/*     */     }
/* 188 */     readerIndex(i);
/* 189 */     return CompressedStreamTools.read((DataInput)new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeItemStackToBuffer(ItemStack stack) {
/* 197 */     if (stack == null) {
/* 198 */       writeShort(-1);
/*     */     } else {
/* 200 */       writeShort(Item.getIdFromItem(stack.getItem()));
/* 201 */       writeByte(stack.stackSize);
/* 202 */       writeShort(stack.getMetadata());
/* 203 */       NBTTagCompound nbttagcompound = null;
/*     */       
/* 205 */       if (stack.getItem().isDamageable() || stack.getItem().getShareTag()) {
/* 206 */         nbttagcompound = stack.getTagCompound();
/*     */       }
/*     */       
/* 209 */       writeNBTTagCompoundToBuffer(nbttagcompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack readItemStackFromBuffer() throws IOException {
/* 217 */     ItemStack itemstack = null;
/* 218 */     int i = readShort();
/*     */     
/* 220 */     if (i >= 0) {
/* 221 */       int j = readByte();
/* 222 */       int k = readShort();
/* 223 */       itemstack = new ItemStack(Item.getItemById(i), j, k);
/* 224 */       itemstack.setTagCompound(readNBTTagCompoundFromBuffer());
/*     */     } 
/*     */     
/* 227 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readStringFromBuffer(int maxLength) {
/* 235 */     int i = readVarIntFromBuffer();
/*     */     
/* 237 */     if (i > maxLength * 4)
/* 238 */       throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + (maxLength * 4) + ")"); 
/* 239 */     if (i < 0) {
/* 240 */       throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
/*     */     }
/* 242 */     String s = new String(readBytes(i).array(), Charsets.UTF_8);
/*     */     
/* 244 */     if (s.length() > maxLength) {
/* 245 */       throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
/*     */     }
/* 247 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketBuffer writeString(String string) {
/* 253 */     byte[] abyte = string.getBytes(Charsets.UTF_8);
/*     */     
/* 255 */     if (abyte.length > 32767) {
/* 256 */       throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + '翿' + ")");
/*     */     }
/* 258 */     writeVarIntToBuffer(abyte.length);
/* 259 */     writeBytes(abyte);
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 265 */     return this.buf.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int p_capacity_1_) {
/* 269 */     return this.buf.capacity(p_capacity_1_);
/*     */   }
/*     */   
/*     */   public int maxCapacity() {
/* 273 */     return this.buf.maxCapacity();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 277 */     return this.buf.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order() {
/* 281 */     return this.buf.order();
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder p_order_1_) {
/* 285 */     return this.buf.order(p_order_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap() {
/* 289 */     return this.buf.unwrap();
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 293 */     return this.buf.isDirect();
/*     */   }
/*     */   
/*     */   public int readerIndex() {
/* 297 */     return this.buf.readerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int p_readerIndex_1_) {
/* 301 */     return this.buf.readerIndex(p_readerIndex_1_);
/*     */   }
/*     */   
/*     */   public int writerIndex() {
/* 305 */     return this.buf.writerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int p_writerIndex_1_) {
/* 309 */     return this.buf.writerIndex(p_writerIndex_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
/* 313 */     return this.buf.setIndex(p_setIndex_1_, p_setIndex_2_);
/*     */   }
/*     */   
/*     */   public int readableBytes() {
/* 317 */     return this.buf.readableBytes();
/*     */   }
/*     */   
/*     */   public int writableBytes() {
/* 321 */     return this.buf.writableBytes();
/*     */   }
/*     */   
/*     */   public int maxWritableBytes() {
/* 325 */     return this.buf.maxWritableBytes();
/*     */   }
/*     */   
/*     */   public boolean isReadable() {
/* 329 */     return this.buf.isReadable();
/*     */   }
/*     */   
/*     */   public boolean isReadable(int p_isReadable_1_) {
/* 333 */     return this.buf.isReadable(p_isReadable_1_);
/*     */   }
/*     */   
/*     */   public boolean isWritable() {
/* 337 */     return this.buf.isWritable();
/*     */   }
/*     */   
/*     */   public boolean isWritable(int p_isWritable_1_) {
/* 341 */     return this.buf.isWritable(p_isWritable_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf clear() {
/* 345 */     return this.buf.clear();
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 349 */     return this.buf.markReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 353 */     return this.buf.resetReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 357 */     return this.buf.markWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 361 */     return this.buf.resetWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 365 */     return this.buf.discardReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 369 */     return this.buf.discardSomeReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int p_ensureWritable_1_) {
/* 373 */     return this.buf.ensureWritable(p_ensureWritable_1_);
/*     */   }
/*     */   
/*     */   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
/* 377 */     return this.buf.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int p_getBoolean_1_) {
/* 381 */     return this.buf.getBoolean(p_getBoolean_1_);
/*     */   }
/*     */   
/*     */   public byte getByte(int p_getByte_1_) {
/* 385 */     return this.buf.getByte(p_getByte_1_);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int p_getUnsignedByte_1_) {
/* 389 */     return this.buf.getUnsignedByte(p_getUnsignedByte_1_);
/*     */   }
/*     */   
/*     */   public short getShort(int p_getShort_1_) {
/* 393 */     return this.buf.getShort(p_getShort_1_);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int p_getUnsignedShort_1_) {
/* 397 */     return this.buf.getUnsignedShort(p_getUnsignedShort_1_);
/*     */   }
/*     */   
/*     */   public int getMedium(int p_getMedium_1_) {
/* 401 */     return this.buf.getMedium(p_getMedium_1_);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
/* 405 */     return this.buf.getUnsignedMedium(p_getUnsignedMedium_1_);
/*     */   }
/*     */   
/*     */   public int getInt(int p_getInt_1_) {
/* 409 */     return this.buf.getInt(p_getInt_1_);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int p_getUnsignedInt_1_) {
/* 413 */     return this.buf.getUnsignedInt(p_getUnsignedInt_1_);
/*     */   }
/*     */   
/*     */   public long getLong(int p_getLong_1_) {
/* 417 */     return this.buf.getLong(p_getLong_1_);
/*     */   }
/*     */   
/*     */   public char getChar(int p_getChar_1_) {
/* 421 */     return this.buf.getChar(p_getChar_1_);
/*     */   }
/*     */   
/*     */   public float getFloat(int p_getFloat_1_) {
/* 425 */     return this.buf.getFloat(p_getFloat_1_);
/*     */   }
/*     */   
/*     */   public double getDouble(int p_getDouble_1_) {
/* 429 */     return this.buf.getDouble(p_getDouble_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
/* 433 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
/* 437 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/* 441 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
/* 445 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/* 449 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
/* 453 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
/* 457 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*     */   }
/*     */   
/*     */   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
/* 461 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
/* 465 */     return this.buf.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
/* 469 */     return this.buf.setByte(p_setByte_1_, p_setByte_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
/* 473 */     return this.buf.setShort(p_setShort_1_, p_setShort_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
/* 477 */     return this.buf.setMedium(p_setMedium_1_, p_setMedium_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
/* 481 */     return this.buf.setInt(p_setInt_1_, p_setInt_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
/* 485 */     return this.buf.setLong(p_setLong_1_, p_setLong_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
/* 489 */     return this.buf.setChar(p_setChar_1_, p_setChar_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
/* 493 */     return this.buf.setFloat(p_setFloat_1_, p_setFloat_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
/* 497 */     return this.buf.setDouble(p_setDouble_1_, p_setDouble_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
/* 501 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
/* 505 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/* 509 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
/* 513 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/* 517 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
/* 521 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*     */   }
/*     */   
/*     */   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
/* 525 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*     */   }
/*     */   
/*     */   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
/* 529 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
/* 533 */     return this.buf.setZero(p_setZero_1_, p_setZero_2_);
/*     */   }
/*     */   
/*     */   public boolean readBoolean() {
/* 537 */     return this.buf.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte() {
/* 541 */     return this.buf.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte() {
/* 545 */     return this.buf.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort() {
/* 549 */     return this.buf.readShort();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort() {
/* 553 */     return this.buf.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public int readMedium() {
/* 557 */     return this.buf.readMedium();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium() {
/* 561 */     return this.buf.readUnsignedMedium();
/*     */   }
/*     */   
/*     */   public int readInt() {
/* 565 */     return this.buf.readInt();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt() {
/* 569 */     return this.buf.readUnsignedInt();
/*     */   }
/*     */   
/*     */   public long readLong() {
/* 573 */     return this.buf.readLong();
/*     */   }
/*     */   
/*     */   public char readChar() {
/* 577 */     return this.buf.readChar();
/*     */   }
/*     */   
/*     */   public float readFloat() {
/* 581 */     return this.buf.readFloat();
/*     */   }
/*     */   
/*     */   public double readDouble() {
/* 585 */     return this.buf.readDouble();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int p_readBytes_1_) {
/* 589 */     return this.buf.readBytes(p_readBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int p_readSlice_1_) {
/* 593 */     return this.buf.readSlice(p_readSlice_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
/* 597 */     return this.buf.readBytes(p_readBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
/* 601 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/* 605 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] p_readBytes_1_) {
/* 609 */     return this.buf.readBytes(p_readBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/* 613 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
/* 617 */     return this.buf.readBytes(p_readBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
/* 621 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
/* 625 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int p_skipBytes_1_) {
/* 629 */     return this.buf.skipBytes(p_skipBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
/* 633 */     return this.buf.writeBoolean(p_writeBoolean_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int p_writeByte_1_) {
/* 637 */     return this.buf.writeByte(p_writeByte_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int p_writeShort_1_) {
/* 641 */     return this.buf.writeShort(p_writeShort_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int p_writeMedium_1_) {
/* 645 */     return this.buf.writeMedium(p_writeMedium_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int p_writeInt_1_) {
/* 649 */     return this.buf.writeInt(p_writeInt_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long p_writeLong_1_) {
/* 653 */     return this.buf.writeLong(p_writeLong_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int p_writeChar_1_) {
/* 657 */     return this.buf.writeChar(p_writeChar_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float p_writeFloat_1_) {
/* 661 */     return this.buf.writeFloat(p_writeFloat_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double p_writeDouble_1_) {
/* 665 */     return this.buf.writeDouble(p_writeDouble_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
/* 669 */     return this.buf.writeBytes(p_writeBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
/* 673 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/* 677 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
/* 681 */     return this.buf.writeBytes(p_writeBytes_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/* 685 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
/* 689 */     return this.buf.writeBytes(p_writeBytes_1_);
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/* 693 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/* 697 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int p_writeZero_1_) {
/* 701 */     return this.buf.writeZero(p_writeZero_1_);
/*     */   }
/*     */   
/*     */   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
/* 705 */     return this.buf.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte p_bytesBefore_1_) {
/* 709 */     return this.buf.bytesBefore(p_bytesBefore_1_);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
/* 713 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
/* 717 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor p_forEachByte_1_) {
/* 721 */     return this.buf.forEachByte(p_forEachByte_1_);
/*     */   }
/*     */   
/*     */   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteBufProcessor p_forEachByte_3_) {
/* 725 */     return this.buf.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor p_forEachByteDesc_1_) {
/* 729 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteBufProcessor p_forEachByteDesc_3_) {
/* 733 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
/*     */   }
/*     */   
/*     */   public ByteBuf copy() {
/* 737 */     return this.buf.copy();
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
/* 741 */     return this.buf.copy(p_copy_1_, p_copy_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf slice() {
/* 745 */     return this.buf.slice();
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
/* 749 */     return this.buf.slice(p_slice_1_, p_slice_2_);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate() {
/* 753 */     return this.buf.duplicate();
/*     */   }
/*     */   
/*     */   public int nioBufferCount() {
/* 757 */     return this.buf.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 761 */     return this.buf.nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
/* 765 */     return this.buf.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
/* 769 */     return this.buf.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 773 */     return this.buf.nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
/* 777 */     return this.buf.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
/*     */   }
/*     */   
/*     */   public boolean hasArray() {
/* 781 */     return this.buf.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array() {
/* 785 */     return this.buf.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset() {
/* 789 */     return this.buf.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 793 */     return this.buf.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress() {
/* 797 */     return this.buf.memoryAddress();
/*     */   }
/*     */   
/*     */   public String toString(Charset p_toString_1_) {
/* 801 */     return this.buf.toString(p_toString_1_);
/*     */   }
/*     */   
/*     */   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
/* 805 */     return this.buf.toString(p_toString_1_, p_toString_2_, p_toString_3_);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 809 */     return this.buf.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 813 */     return this.buf.equals(p_equals_1_);
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf p_compareTo_1_) {
/* 817 */     return this.buf.compareTo(p_compareTo_1_);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 821 */     return this.buf.toString();
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int p_retain_1_) {
/* 825 */     return this.buf.retain(p_retain_1_);
/*     */   }
/*     */   
/*     */   public ByteBuf retain() {
/* 829 */     return this.buf.retain();
/*     */   }
/*     */   
/*     */   public int refCnt() {
/* 833 */     return this.buf.refCnt();
/*     */   }
/*     */   
/*     */   public boolean release() {
/* 837 */     return this.buf.release();
/*     */   }
/*     */   
/*     */   public boolean release(int p_release_1_) {
/* 841 */     return this.buf.release(p_release_1_);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\PacketBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */