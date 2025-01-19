/*      */ package net.minecraft.client;
/*      */ 
/*      */ import client.Client;
/*      */ import client.KeyEvent;
/*      */ import client.MouseEvent;
/*      */ import client.event.impl.ClientTickEvent;
/*      */ import client.gui.SplashProgress;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.properties.Property;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.audio.MusicTicker;
/*      */ import net.minecraft.client.audio.SoundHandler;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiControls;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiIngame;
/*      */ import net.minecraft.client.gui.GuiIngameMenu;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMemoryErrorScreen;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiSleepMP;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.GuiYesNoCallback;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*      */ import net.minecraft.client.gui.inventory.GuiInventory;
/*      */ import net.minecraft.client.gui.stream.GuiStreamUnavailable;
/*      */ import net.minecraft.client.main.GameConfiguration;
/*      */ import net.minecraft.client.multiplayer.GuiConnecting;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.network.NetHandlerLoginClient;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*      */ import net.minecraft.client.resources.GrassColorReloadListener;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IReloadableResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LanguageManager;
/*      */ import net.minecraft.client.resources.ResourceIndex;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.FontMetadataSection;
/*      */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.PackMetadataSection;
/*      */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.stream.IStream;
/*      */ import net.minecraft.client.stream.NullStream;
/*      */ import net.minecraft.client.stream.TwitchStream;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Bootstrap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmorStand;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.EnumConnectionState;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.handshake.client.C00Handshake;
/*      */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.IStatStringFormat;
/*      */ import net.minecraft.stats.StatFileWriter;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MinecraftError;
/*      */ import net.minecraft.util.MouseHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovementInputFromOptions;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.ScreenShotHelper;
/*      */ import net.minecraft.util.Session;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.OpenGLException;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Minecraft
/*      */   implements IThreadListener, IPlayerUsage
/*      */ {
/* 3620 */   private static final Logger logger = LogManager.getLogger();
/* 3621 */   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
/* 3622 */   public static final boolean isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
/* 3623 */   public static byte[] memoryReserve = new byte[10485760];
/* 3624 */   private static final List<DisplayMode> macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) }); private final File fileResourcepacks; private final PropertyMap twitchDetails; private final PropertyMap profileProperties; private ServerData currentServerData; private TextureManager renderEngine; private static Minecraft theMinecraft; public PlayerControllerMP playerController; private boolean fullscreen; private boolean enableGLErrorChecking; private boolean hasCrashed; private CrashReport crashReporter; public int displayWidth; public int displayHeight; private boolean connectedToRealms; private Timer timer; private PlayerUsageSnooper usageSnooper; public WorldClient theWorld; public RenderGlobal renderGlobal; private RenderManager renderManager; private RenderItem renderItem; private ItemRenderer itemRenderer; public EntityPlayerSP thePlayer; private Entity renderViewEntity; public Entity pointedEntity; public EffectRenderer effectRenderer; private final Session session; private boolean isGamePaused; public FontRenderer fontRendererObj; public FontRenderer standardGalacticFontRenderer; public GuiScreen currentScreen; public LoadingScreenRenderer loadingScreen; public EntityRenderer entityRenderer; private int leftClickCounter; private int tempDisplayWidth; private int tempDisplayHeight; private IntegratedServer theIntegratedServer; public GuiAchievement guiAchievement; public GuiIngame ingameGUI; public boolean skipRenderWorld; public MovingObjectPosition objectMouseOver; public GameSettings gameSettings; public MouseHelper mouseHelper; public final File mcDataDir; private final File fileAssets;
/*      */   private final String launchedVersion;
/*      */   
/*      */   public Minecraft(GameConfiguration gameConfig) {
/* 3628 */     this.enableGLErrorChecking = true;
/* 3629 */     this.connectedToRealms = false;
/* 3630 */     this.timer = new Timer(20.0F);
/* 3631 */     this.usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
/* 3632 */     this.systemTime = getSystemTime();
/* 3633 */     this.frameTimer = new FrameTimer();
/* 3634 */     this.startNanoTime = System.nanoTime();
/* 3635 */     this.mcProfiler = new Profiler();
/* 3636 */     this.debugCrashKeyPressTime = -1L;
/* 3637 */     this.metadataSerializer_ = new IMetadataSerializer();
/* 3638 */     this.defaultResourcePacks = Lists.newArrayList();
/* 3639 */     this.scheduledTasks = Queues.newArrayDeque();
/* 3640 */     this.field_175615_aJ = 0L;
/* 3641 */     this.mcThread = Thread.currentThread();
/* 3642 */     this.running = true;
/* 3643 */     this.debug = "";
/* 3644 */     this.field_175613_B = false;
/* 3645 */     this.field_175614_C = false;
/* 3646 */     this.field_175611_D = false;
/* 3647 */     this.renderChunksMany = true;
/* 3648 */     this.debugUpdateTime = getSystemTime();
/* 3649 */     this.prevFrameTime = -1L;
/* 3650 */     this.debugProfilerName = "root";
/* 3651 */     theMinecraft = this;
/* 3652 */     this.mcDataDir = gameConfig.folderInfo.mcDataDir;
/* 3653 */     this.fileAssets = gameConfig.folderInfo.assetsDir;
/* 3654 */     this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir;
/* 3655 */     this.launchedVersion = gameConfig.gameInfo.version;
/* 3656 */     this.twitchDetails = gameConfig.userInfo.userProperties;
/* 3657 */     this.profileProperties = gameConfig.userInfo.profileProperties;
/* 3658 */     this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(gameConfig.folderInfo.assetsDir, gameConfig.folderInfo.assetIndex)).getResourceMap());
/* 3659 */     this.proxy = (gameConfig.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfig.userInfo.proxy;
/* 3660 */     this.sessionService = (new YggdrasilAuthenticationService(gameConfig.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
/* 3661 */     this.session = gameConfig.userInfo.session;
/* 3662 */     logger.info("Setting user: " + this.session.getUsername());
/* 3663 */     logger.info("(Session ID is " + this.session.getSessionID() + ")");
/* 3664 */     this.isDemo = gameConfig.gameInfo.isDemo;
/* 3665 */     this.displayWidth = (gameConfig.displayInfo.width > 0) ? gameConfig.displayInfo.width : 1;
/* 3666 */     this.displayHeight = (gameConfig.displayInfo.height > 0) ? gameConfig.displayInfo.height : 1;
/* 3667 */     this.tempDisplayWidth = gameConfig.displayInfo.width;
/* 3668 */     this.tempDisplayHeight = gameConfig.displayInfo.height;
/* 3669 */     this.fullscreen = gameConfig.displayInfo.fullscreen;
/* 3670 */     this.jvm64bit = isJvm64bit();
/* 3671 */     this.theIntegratedServer = new IntegratedServer(this);
/* 3672 */     if (gameConfig.serverInfo.serverName != null) {
/* 3673 */       this.serverName = gameConfig.serverInfo.serverName;
/* 3674 */       this.serverPort = gameConfig.serverInfo.serverPort;
/*      */     } 
/* 3676 */     ImageIO.setUseCache(false);
/* 3677 */     Bootstrap.register();
/*      */   }
/*      */   private final Proxy proxy; private ISaveFormat saveLoader; private static int debugFPS; private int rightClickDelayTimer; private String serverName; private int serverPort; public boolean inGameHasFocus; long systemTime; private int joinPlayerCounter; public final FrameTimer frameTimer; long startNanoTime; private final boolean jvm64bit; private final boolean isDemo; private NetworkManager myNetworkManager; private boolean integratedServerIsRunning; public final Profiler mcProfiler; private long debugCrashKeyPressTime; private IReloadableResourceManager mcResourceManager; public final IMetadataSerializer metadataSerializer_; private final List<IResourcePack> defaultResourcePacks; private final DefaultResourcePack mcDefaultResourcePack; private ResourcePackRepository mcResourcePackRepository; private LanguageManager mcLanguageManager; private IStream stream; private Framebuffer framebufferMc; private TextureMap textureMapBlocks; private SoundHandler mcSoundHandler; private MusicTicker mcMusicTicker; private ResourceLocation mojangLogo; private final MinecraftSessionService sessionService; private SkinManager skinManager; private final Queue<FutureTask<?>> scheduledTasks; private long field_175615_aJ; private final Thread mcThread; private ModelManager modelManager; private BlockRendererDispatcher blockRenderDispatcher; volatile boolean running; public String debug; public boolean field_175613_B; public boolean field_175614_C; public boolean field_175611_D; public boolean renderChunksMany; long debugUpdateTime; int fpsCounter; long prevFrameTime; private String debugProfilerName;
/*      */   public void run() {
/* 3681 */     this.running = true;
/*      */     try {
/* 3683 */       startGame();
/* 3684 */     } catch (Throwable throwable) {
/* 3685 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game");
/* 3686 */       crashreport.makeCategory("Initialization");
/* 3687 */       displayCrashReport(addGraphicsAndWorldToCrashReport(crashreport)); return;
/*      */     } 
/*      */     
/*      */     try { while (true)
/*      */       { 
/* 3692 */         try { if (this.hasCrashed && 
/* 3693 */             this.crashReporter != null) {
/* 3694 */             displayCrashReport(this.crashReporter);
/*      */           } else {
/*      */ 
/*      */             
/*      */             try {
/* 3699 */               runGameLoop();
/* 3700 */             } catch (OutOfMemoryError var10) {
/* 3701 */               freeMemory();
/* 3702 */               displayGuiScreen((GuiScreen)new GuiMemoryErrorScreen());
/* 3703 */               System.gc();
/*      */             } 
/* 3705 */           }  if (!this.running)
/*      */           
/*      */           { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3718 */             shutdownMinecraftApplet(); break; }  } catch (MinecraftError minecraftError) { shutdownMinecraftApplet(); break; } catch (ReportedException reportedexception) { addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport()); freeMemory(); logger.fatal("Reported exception thrown!", (Throwable)reportedexception); displayCrashReport(reportedexception.getCrashReport()); shutdownMinecraftApplet(); break; } catch (Throwable throwable2) { CrashReport crashreport2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable2)); freeMemory(); logger.fatal("Unreported exception thrown!", throwable2); displayCrashReport(crashreport2); shutdownMinecraftApplet(); break; }  }  } finally { shutdownMinecraftApplet(); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private void startGame() throws LWJGLException, IOException {
/* 3724 */     Client.getInstance().init();
/* 3725 */     Client.getInstance().getDiscordrp().update((Client.getInstance()).clientName, "");
/* 3726 */     this.gameSettings = new GameSettings(this, this.mcDataDir);
/* 3727 */     this.defaultResourcePacks.add(this.mcDefaultResourcePack);
/* 3728 */     startTimerHackThread();
/* 3729 */     if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
/* 3730 */       this.displayWidth = this.gameSettings.overrideWidth;
/* 3731 */       this.displayHeight = this.gameSettings.overrideHeight;
/*      */     } 
/* 3733 */     logger.info("LWJGL Version: " + Sys.getVersion());
/* 3734 */     setWindowIcon();
/* 3735 */     setInitialDisplayMode();
/* 3736 */     createDisplay();
/* 3737 */     OpenGlHelper.initializeTextures();
/* 3738 */     (this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true)).setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 3739 */     registerMetadataSerializers();
/* 3740 */     this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
/* 3741 */     this.mcResourceManager = (IReloadableResourceManager)new SimpleReloadableResourceManager(this.metadataSerializer_);
/* 3742 */     this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.forceUnicodeFont);
/* 3743 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager);
/* 3744 */     refreshResources();
/* 3745 */     this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager);
/* 3746 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine);
/* 3747 */     SplashProgress.drawSplash(getTextureManager());
/* 3748 */     initStream();
/* 3749 */     this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
/* 3750 */     this.saveLoader = (ISaveFormat)new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
/* 3751 */     this.mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings);
/* 3752 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcSoundHandler);
/* 3753 */     this.mcMusicTicker = new MusicTicker(this);
/* 3754 */     this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
/* 3755 */     if (this.gameSettings.forceUnicodeFont != null) {
/* 3756 */       this.fontRendererObj.setUnicodeFlag(isUnicode());
/* 3757 */       this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
/*      */     } 
/* 3759 */     this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
/* 3760 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.fontRendererObj);
/* 3761 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer);
/* 3762 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener());
/* 3763 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener());
/* 3764 */     AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
/*      */         {
/*      */           public String formatString(String str) {
/*      */             try {
/* 3768 */               return String.format(str, new Object[] { GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindUseItem.getKeyCode()) });
/* 3769 */             } catch (Exception exception) {
/* 3770 */               return "Error: " + exception.getLocalizedMessage();
/*      */             } 
/*      */           }
/*      */         });
/* 3774 */     this.mouseHelper = new MouseHelper();
/* 3775 */     checkGLError("Pre startup");
/* 3776 */     GlStateManager.enableTexture2D();
/* 3777 */     GlStateManager.shadeModel(7425);
/* 3778 */     GlStateManager.clearDepth(1.0D);
/* 3779 */     GlStateManager.enableDepth();
/* 3780 */     GlStateManager.depthFunc(515);
/* 3781 */     GlStateManager.enableAlpha();
/* 3782 */     GlStateManager.alphaFunc(516, 0.1F);
/* 3783 */     GlStateManager.cullFace(1029);
/* 3784 */     GlStateManager.matrixMode(5889);
/* 3785 */     GlStateManager.loadIdentity();
/* 3786 */     GlStateManager.matrixMode(5888);
/* 3787 */     checkGLError("Startup");
/* 3788 */     (this.textureMapBlocks = new TextureMap("textures")).setMipmapLevels(this.gameSettings.mipmapLevels);
/* 3789 */     this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, (ITickableTextureObject)this.textureMapBlocks);
/* 3790 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 3791 */     this.textureMapBlocks.setBlurMipmapDirect(false, (this.gameSettings.mipmapLevels > 0));
/* 3792 */     SplashProgress.setProgress(2, "Client - ModelManager");
/* 3793 */     this.modelManager = new ModelManager(this.textureMapBlocks);
/* 3794 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager);
/* 3795 */     SplashProgress.setProgress(3, "Client - RenderItem");
/* 3796 */     this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
/* 3797 */     SplashProgress.setProgress(4, "Client - RenderManager");
/* 3798 */     this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
/* 3799 */     this.itemRenderer = new ItemRenderer(this);
/* 3800 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem);
/* 3801 */     SplashProgress.setProgress(5, "Client - EntityRenderer");
/* 3802 */     this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager);
/* 3803 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer);
/* 3804 */     SplashProgress.setProgress(6, "Client - BlockRenderer");
/* 3805 */     this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
/* 3806 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.blockRenderDispatcher);
/* 3807 */     SplashProgress.setProgress(7, "Client - RenderGlobal");
/* 3808 */     this.renderGlobal = new RenderGlobal(this);
/* 3809 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal);
/* 3810 */     this.guiAchievement = new GuiAchievement(this);
/* 3811 */     GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
/* 3812 */     this.effectRenderer = new EffectRenderer((World)this.theWorld, this.renderEngine);
/* 3813 */     checkGLError("Post startup");
/* 3814 */     this.ingameGUI = new GuiIngame(this);
/* 3815 */     Client.getInstance().startup();
/* 3816 */     if (this.serverName != null) {
/* 3817 */       displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this, this.serverName, this.serverPort));
/*      */     } else {
/* 3819 */       displayGuiScreen((GuiScreen)new GuiMainMenu());
/*      */     } 
/* 3821 */     this.renderEngine.deleteTexture(this.mojangLogo);
/* 3822 */     this.mojangLogo = null;
/* 3823 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 3824 */     if (this.gameSettings.fullScreen && !this.fullscreen) {
/* 3825 */       toggleFullscreen();
/*      */     }
/*      */     try {
/* 3828 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 3829 */     } catch (OpenGLException var2) {
/* 3830 */       this.gameSettings.enableVsync = false;
/* 3831 */       this.gameSettings.saveOptions();
/*      */     } 
/* 3833 */     this.renderGlobal.makeEntityOutlineShader();
/*      */   }
/*      */   
/*      */   private void registerMetadataSerializers() {
/* 3837 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/* 3838 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/* 3839 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/* 3840 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/* 3841 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/*      */   }
/*      */   
/*      */   private void initStream() {
/*      */     try {
/* 3846 */       this.stream = (IStream)new TwitchStream(this, (Property)Iterables.getFirst(this.twitchDetails.get("twitch_access_token"), null));
/* 3847 */     } catch (Throwable throwable) {
/* 3848 */       this.stream = (IStream)new NullStream(throwable);
/* 3849 */       logger.error("Couldn't initialize twitch stream");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createDisplay() throws LWJGLException {
/* 3854 */     Display.setResizable(true);
/* 3855 */     Display.setTitle((Client.getInstance()).clientName);
/*      */     try {
/* 3857 */       Display.create((new PixelFormat()).withDepthBits(24));
/* 3858 */     } catch (LWJGLException lwjglexception) {
/* 3859 */       logger.error("Couldn't set pixel format", (Throwable)lwjglexception);
/*      */       try {
/* 3861 */         Thread.sleep(1000L);
/* 3862 */       } catch (InterruptedException interruptedException) {}
/*      */       
/* 3864 */       if (this.fullscreen) {
/* 3865 */         updateDisplayMode();
/*      */       }
/* 3867 */       Display.create();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setInitialDisplayMode() throws LWJGLException {
/* 3872 */     if (this.fullscreen) {
/* 3873 */       Display.setFullscreen(true);
/* 3874 */       DisplayMode displaymode = Display.getDisplayMode();
/* 3875 */       this.displayWidth = Math.max(1, displaymode.getWidth());
/* 3876 */       this.displayHeight = Math.max(1, displaymode.getHeight());
/*      */     } else {
/* 3878 */       Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setWindowIcon() {
/* 3883 */     Util.EnumOS util$enumos = Util.getOSType();
/* 3884 */     if (util$enumos != Util.EnumOS.OSX) {
/* 3885 */       InputStream inputstream = null;
/* 3886 */       InputStream inputstream2 = null;
/*      */       try {
/* 3888 */         inputstream = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/* 3889 */         inputstream2 = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/* 3890 */         if (inputstream != null && inputstream2 != null) {
/* 3891 */           Display.setIcon(new ByteBuffer[] { readImageToBuffer(inputstream), readImageToBuffer(inputstream2) });
/*      */         }
/* 3893 */       } catch (IOException ioexception) {
/* 3894 */         logger.error("Couldn't set icon", ioexception);
/*      */         return;
/*      */       } finally {
/* 3897 */         IOUtils.closeQuietly(inputstream);
/* 3898 */         IOUtils.closeQuietly(inputstream2);
/*      */       } 
/* 3900 */       IOUtils.closeQuietly(inputstream);
/* 3901 */       IOUtils.closeQuietly(inputstream2);
/* 3902 */       IOUtils.closeQuietly(inputstream);
/* 3903 */       IOUtils.closeQuietly(inputstream2);
/* 3904 */       IOUtils.closeQuietly(inputstream);
/* 3905 */       IOUtils.closeQuietly(inputstream2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isJvm64bit() {
/* 3910 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     String[] array;
/* 3912 */     for (int length = (array = astring).length, i = 0; i < length; i++) {
/* 3913 */       String s = array[i];
/* 3914 */       String s2 = System.getProperty(s);
/* 3915 */       if (s2 != null && s2.contains("64")) {
/* 3916 */         return true;
/*      */       }
/*      */     } 
/* 3919 */     return false;
/*      */   }
/*      */   
/*      */   public Framebuffer getFramebuffer() {
/* 3923 */     return this.framebufferMc;
/*      */   }
/*      */   
/*      */   public String getVersion() {
/* 3927 */     return this.launchedVersion;
/*      */   }
/*      */   
/*      */   private void startTimerHackThread() {
/* 3931 */     Thread thread = new Thread("Timer hack thread")
/*      */       {
/*      */         public void run() {
/* 3934 */           while (Minecraft.this.running) {
/*      */             try {
/* 3936 */               Thread.sleep(2147483647L);
/* 3937 */             } catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         }
/*      */       };
/*      */     
/* 3942 */     thread.setDaemon(true);
/* 3943 */     thread.start();
/*      */   }
/*      */   
/*      */   public void crashed(CrashReport crash) {
/* 3947 */     this.hasCrashed = true;
/* 3948 */     this.crashReporter = crash;
/*      */   }
/*      */   
/*      */   public void displayCrashReport(CrashReport crashReportIn) {
/* 3952 */     File file1 = new File((getMinecraft()).mcDataDir, "crash-reports");
/* 3953 */     File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
/* 3954 */     Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
/* 3955 */     if (crashReportIn.getFile() != null) {
/* 3956 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
/* 3957 */       System.exit(-1);
/* 3958 */     } else if (crashReportIn.saveToFile(file2)) {
/* 3959 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
/* 3960 */       System.exit(-1);
/*      */     } else {
/* 3962 */       Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
/* 3963 */       System.exit(-2);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isUnicode() {
/* 3968 */     return !(!this.mcLanguageManager.isCurrentLocaleUnicode() && !this.gameSettings.logger);
/*      */   }
/*      */   
/*      */   public void refreshResources() {
/* 3972 */     List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);
/* 3973 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries()) {
/* 3974 */       list.add(resourcepackrepository$entry.getResourcePack());
/*      */     }
/* 3976 */     if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
/* 3977 */       list.add(this.mcResourcePackRepository.getResourcePackInstance());
/*      */     }
/*      */     try {
/* 3980 */       this.mcResourceManager.reloadResources(list);
/* 3981 */     } catch (RuntimeException runtimeexception) {
/* 3982 */       logger.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception);
/* 3983 */       list.clear();
/* 3984 */       list.addAll(this.defaultResourcePacks);
/* 3985 */       this.mcResourcePackRepository.setRepositories(Collections.emptyList());
/* 3986 */       this.mcResourceManager.reloadResources(list);
/* 3987 */       this.gameSettings.resourcePacks.clear();
/* 3988 */       this.gameSettings.incompatibleResourcePacks.clear();
/* 3989 */       this.gameSettings.saveOptions();
/*      */     } 
/* 3991 */     this.mcLanguageManager.parseLanguageMetadata(list);
/* 3992 */     if (this.renderGlobal != null) {
/* 3993 */       this.renderGlobal.loadRenderers();
/*      */     }
/*      */   }
/*      */   
/*      */   private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
/* 3998 */     BufferedImage bufferedimage = ImageIO.read(imageStream);
/* 3999 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 4000 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/*      */     int[] array;
/* 4002 */     for (int length = (array = aint).length, j = 0; j < length; j++) {
/* 4003 */       int i = array[j];
/* 4004 */       bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
/*      */     } 
/* 4006 */     bytebuffer.flip();
/* 4007 */     return bytebuffer;
/*      */   }
/*      */   
/*      */   private void updateDisplayMode() throws LWJGLException {
/* 4011 */     Set<DisplayMode> set = Sets.newHashSet();
/* 4012 */     Collections.addAll(set, Display.getAvailableDisplayModes());
/* 4013 */     DisplayMode displaymode = Display.getDesktopDisplayMode();
/* 4014 */     if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX) {
/* 4015 */       for (DisplayMode displaymode2 : macDisplayModes) {
/* 4016 */         boolean flag = true;
/* 4017 */         for (DisplayMode displaymode3 : set) {
/* 4018 */           if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode2.getWidth() && displaymode3.getHeight() == displaymode2.getHeight()) {
/* 4019 */             flag = false;
/*      */             break;
/*      */           } 
/*      */         } 
/* 4023 */         if (!flag) {
/* 4024 */           for (DisplayMode displaymode4 : set) {
/* 4025 */             if (displaymode4.getBitsPerPixel() == 32 && displaymode4.getWidth() == displaymode2.getWidth() / 2 && displaymode4.getHeight() == displaymode2.getHeight() / 2) {
/* 4026 */               displaymode = displaymode4;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 4033 */     Display.setDisplayMode(displaymode);
/* 4034 */     this.displayWidth = displaymode.getWidth();
/* 4035 */     this.displayHeight = displaymode.getHeight();
/*      */   }
/*      */   
/*      */   private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
/* 4039 */     ScaledResolution scaledresolution = new ScaledResolution(this);
/* 4040 */     int i = scaledresolution.getScaleFactor();
/* 4041 */     Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
/* 4042 */     framebuffer.bindFramebuffer(false);
/* 4043 */     GlStateManager.matrixMode(5889);
/* 4044 */     GlStateManager.loadIdentity();
/* 4045 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/* 4046 */     GlStateManager.matrixMode(5888);
/* 4047 */     GlStateManager.loadIdentity();
/* 4048 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 4049 */     GlStateManager.disableLighting();
/* 4050 */     GlStateManager.disableFog();
/* 4051 */     GlStateManager.disableDepth();
/* 4052 */     GlStateManager.enableTexture2D();
/* 4053 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/* 4057 */       inputstream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
/* 4058 */       textureManagerInstance.bindTexture(this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream))));
/* 4059 */     } catch (IOException ioexception) {
/* 4060 */       logger.error("Unable to load logo: " + locationMojangPng, ioexception);
/*      */     } finally {
/*      */       
/* 4063 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/* 4065 */     IOUtils.closeQuietly(inputstream);
/* 4066 */     IOUtils.closeQuietly(inputstream);
/* 4067 */     IOUtils.closeQuietly(inputstream);
/*      */     
/* 4069 */     Tessellator tessellator = Tessellator.getInstance();
/* 4070 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 4071 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 4072 */     worldrenderer.pos(0.0D, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 4073 */     worldrenderer.pos(this.displayWidth, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 4074 */     worldrenderer.pos(this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 4075 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 4076 */     tessellator.draw();
/* 4077 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 4078 */     int j = 256;
/* 4079 */     int k = 256;
/* 4080 */     draw((scaledresolution.getScaledWidth() - 256) / 2, (scaledresolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256, 255, 255, 255, 255);
/* 4081 */     GlStateManager.disableLighting();
/* 4082 */     GlStateManager.disableFog();
/* 4083 */     framebuffer.unbindFramebuffer();
/* 4084 */     framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
/* 4085 */     GlStateManager.enableAlpha();
/* 4086 */     GlStateManager.alphaFunc(516, 0.1F);
/* 4087 */     updateDisplay();
/*      */   }
/*      */   
/*      */   public void draw(int posX, int posY, int texU, int texV, int width, int height, int red, int green, int blue, int alpha) {
/* 4091 */     float f = 0.00390625F;
/* 4092 */     float f2 = 0.00390625F;
/* 4093 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/* 4094 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 4095 */     worldrenderer.pos(posX, (posY + height), 0.0D).tex((texU * 0.00390625F), ((texV + height) * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 4096 */     worldrenderer.pos((posX + width), (posY + height), 0.0D).tex(((texU + width) * 0.00390625F), ((texV + height) * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 4097 */     worldrenderer.pos((posX + width), posY, 0.0D).tex(((texU + width) * 0.00390625F), (texV * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 4098 */     worldrenderer.pos(posX, posY, 0.0D).tex((texU * 0.00390625F), (texV * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 4099 */     Tessellator.getInstance().draw();
/*      */   }
/*      */   
/*      */   public ISaveFormat getSaveLoader() {
/* 4103 */     return this.saveLoader;
/*      */   } public void displayGuiScreen(GuiScreen guiScreenIn) {
/*      */     GuiMainMenu guiMainMenu;
/*      */     GuiGameOver guiGameOver;
/* 4107 */     if (this.currentScreen != null) {
/* 4108 */       this.currentScreen.onGuiClosed();
/*      */     }
/* 4110 */     if (guiScreenIn == null && this.theWorld == null) {
/* 4111 */       guiMainMenu = new GuiMainMenu();
/* 4112 */     } else if (guiMainMenu == null && this.thePlayer.getHealth() <= 0.0F) {
/* 4113 */       guiGameOver = new GuiGameOver();
/*      */     } 
/* 4115 */     if (guiGameOver instanceof GuiMainMenu) {
/* 4116 */       this.gameSettings.showDebugProfilerChart = false;
/* 4117 */       this.ingameGUI.getChatGUI().clearChatMessages();
/*      */     } 
/* 4119 */     if ((this.currentScreen = (GuiScreen)guiGameOver) != null) {
/* 4120 */       setIngameNotInFocus();
/* 4121 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 4122 */       int i = scaledresolution.getScaledWidth();
/* 4123 */       int j = scaledresolution.getScaledHeight();
/* 4124 */       guiGameOver.setWorldAndResolution(this, i, j);
/* 4125 */       this.skipRenderWorld = false;
/*      */     } else {
/* 4127 */       this.mcSoundHandler.resumeSounds();
/* 4128 */       setIngameFocus();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkGLError(String message) {
/* 4133 */     if (this.enableGLErrorChecking) {
/* 4134 */       int i = GL11.glGetError();
/* 4135 */       if (i != 0) {
/* 4136 */         String s = GLU.gluErrorString(i);
/* 4137 */         logger.error("########## GL ERROR ##########");
/* 4138 */         logger.error("@ " + message);
/* 4139 */         logger.error(String.valueOf(String.valueOf(i)) + ": " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void shutdownMinecraftApplet() {
/*      */     try {
/* 4146 */       Client.getInstance().shutdown();
/* 4147 */       this.stream.shutdownStream();
/* 4148 */       logger.info("Stopping!");
/*      */       try {
/* 4150 */         loadWorld(null);
/* 4151 */       } catch (Throwable throwable) {}
/*      */       
/* 4153 */       this.mcSoundHandler.unloadSounds();
/*      */     } finally {
/* 4155 */       Display.destroy();
/* 4156 */       if (!this.hasCrashed) {
/* 4157 */         System.exit(0);
/*      */       }
/*      */     } 
/* 4160 */     Display.destroy();
/* 4161 */     if (!this.hasCrashed) {
/* 4162 */       System.exit(0);
/*      */     }
/* 4164 */     Display.destroy();
/* 4165 */     if (!this.hasCrashed) {
/* 4166 */       System.exit(0);
/*      */     }
/* 4168 */     Display.destroy();
/* 4169 */     if (!this.hasCrashed) {
/* 4170 */       System.exit(0);
/*      */     }
/* 4172 */     System.gc();
/*      */   }
/*      */   
/*      */   private void runGameLoop() throws IOException {
/* 4176 */     long i = System.nanoTime();
/* 4177 */     this.mcProfiler.startSection("root");
/* 4178 */     if (Display.isCreated() && Display.isCloseRequested()) {
/* 4179 */       shutdown();
/*      */     }
/* 4181 */     if (this.isGamePaused && this.theWorld != null) {
/* 4182 */       float f = this.timer.renderPartialTicks;
/* 4183 */       this.timer.updateTimer();
/* 4184 */       this.timer.renderPartialTicks = f;
/*      */     } else {
/* 4186 */       this.timer.updateTimer();
/*      */     } 
/* 4188 */     this.mcProfiler.startSection("scheduledExecutables");
/* 4189 */     synchronized (this.scheduledTasks) {
/* 4190 */       while (!this.scheduledTasks.isEmpty()) {
/* 4191 */         Util.runTask(this.scheduledTasks.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/* 4195 */     this.mcProfiler.endSection();
/* 4196 */     long l = System.nanoTime();
/* 4197 */     this.mcProfiler.startSection("tick");
/* 4198 */     for (int j = 0; j < this.timer.elapsedTicks; j++) {
/* 4199 */       runTick();
/*      */     }
/* 4201 */     this.mcProfiler.endStartSection("preRenderErrors");
/* 4202 */     long i2 = System.nanoTime() - l;
/* 4203 */     checkGLError("Pre render");
/* 4204 */     this.mcProfiler.endStartSection("sound");
/* 4205 */     this.mcSoundHandler.setListener((EntityPlayer)this.thePlayer, this.timer.renderPartialTicks);
/* 4206 */     this.mcProfiler.endSection();
/* 4207 */     this.mcProfiler.startSection("render");
/* 4208 */     GlStateManager.pushMatrix();
/* 4209 */     GlStateManager.clear(16640);
/* 4210 */     this.framebufferMc.bindFramebuffer(true);
/* 4211 */     this.mcProfiler.startSection("display");
/* 4212 */     GlStateManager.enableTexture2D();
/* 4213 */     if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
/* 4214 */       this.gameSettings.showDebugInfo = 0;
/*      */     }
/* 4216 */     this.mcProfiler.endSection();
/* 4217 */     if (!this.skipRenderWorld) {
/* 4218 */       this.mcProfiler.endStartSection("gameRenderer");
/* 4219 */       this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks, i);
/* 4220 */       this.mcProfiler.endSection();
/*      */     } 
/* 4222 */     this.mcProfiler.endSection();
/* 4223 */     if (this.gameSettings.showDebugProfilerChart && this.gameSettings.showLagometer && !this.gameSettings.thirdPersonView) {
/* 4224 */       if (!this.mcProfiler.profilingEnabled) {
/* 4225 */         this.mcProfiler.clearProfiling();
/*      */       }
/* 4227 */       this.mcProfiler.profilingEnabled = true;
/* 4228 */       displayDebugInfo(i2);
/*      */     } else {
/* 4230 */       this.mcProfiler.profilingEnabled = false;
/* 4231 */       this.prevFrameTime = System.nanoTime();
/*      */     } 
/* 4233 */     this.guiAchievement.updateAchievementWindow();
/* 4234 */     this.framebufferMc.unbindFramebuffer();
/* 4235 */     GlStateManager.popMatrix();
/* 4236 */     GlStateManager.pushMatrix();
/* 4237 */     this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
/* 4238 */     GlStateManager.popMatrix();
/* 4239 */     GlStateManager.pushMatrix();
/* 4240 */     this.entityRenderer.renderStreamIndicator(this.timer.renderPartialTicks);
/* 4241 */     GlStateManager.popMatrix();
/* 4242 */     this.mcProfiler.startSection("root");
/* 4243 */     updateDisplay();
/* 4244 */     Thread.yield();
/* 4245 */     this.mcProfiler.startSection("stream");
/* 4246 */     this.mcProfiler.startSection("update");
/* 4247 */     this.stream.func_152935_j();
/* 4248 */     this.mcProfiler.endStartSection("submit");
/* 4249 */     this.stream.func_152922_k();
/* 4250 */     this.mcProfiler.endSection();
/* 4251 */     this.mcProfiler.endSection();
/* 4252 */     checkGLError("Post render");
/* 4253 */     this.fpsCounter++;
/* 4254 */     this.isGamePaused = (isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
/* 4255 */     long k = System.nanoTime();
/* 4256 */     this.frameTimer.addFrame(k - this.startNanoTime);
/* 4257 */     this.startNanoTime = k;
/* 4258 */     while (getSystemTime() >= this.debugUpdateTime + 1000L) {
/* 4259 */       debugFPS = this.fpsCounter;
/* 4260 */       this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.renderChunksUpdated), (RenderChunk.renderChunksUpdated != 1) ? "s" : "", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "" });
/* 4261 */       RenderChunk.renderChunksUpdated = 0;
/* 4262 */       this.debugUpdateTime += 1000L;
/* 4263 */       this.fpsCounter = 0;
/* 4264 */       this.usageSnooper.addMemoryStatsToSnooper();
/* 4265 */       if (!this.usageSnooper.isSnooperRunning()) {
/* 4266 */         this.usageSnooper.startSnooper();
/*      */       }
/*      */     } 
/* 4269 */     if (isFramerateLimitBelowMax()) {
/* 4270 */       this.mcProfiler.startSection("fpslimit_wait");
/* 4271 */       Display.sync(getLimitFramerate());
/* 4272 */       this.mcProfiler.endSection();
/*      */     } 
/* 4274 */     this.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void updateDisplay() {
/* 4278 */     this.mcProfiler.startSection("display_update");
/* 4279 */     Display.update();
/* 4280 */     this.mcProfiler.endSection();
/* 4281 */     checkWindowResize();
/*      */   }
/*      */   
/*      */   protected void checkWindowResize() {
/* 4285 */     if (!this.fullscreen && Display.wasResized()) {
/* 4286 */       int i = this.displayWidth;
/* 4287 */       int j = this.displayHeight;
/* 4288 */       this.displayWidth = Display.getWidth();
/* 4289 */       this.displayHeight = Display.getHeight();
/* 4290 */       if (this.displayWidth != i || this.displayHeight != j) {
/* 4291 */         if (this.displayWidth <= 0) {
/* 4292 */           this.displayWidth = 1;
/*      */         }
/* 4294 */         if (this.displayHeight <= 0) {
/* 4295 */           this.displayHeight = 1;
/*      */         }
/* 4297 */         resize(this.displayWidth, this.displayHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getLimitFramerate() {
/* 4303 */     return (this.theWorld == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
/*      */   }
/*      */   
/*      */   public boolean isFramerateLimitBelowMax() {
/* 4307 */     return (getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax());
/*      */   }
/*      */   
/*      */   public void freeMemory() {
/*      */     try {
/* 4312 */       memoryReserve = new byte[0];
/* 4313 */       this.renderGlobal.deleteAllDisplayLists();
/* 4314 */     } catch (Throwable throwable) {}
/*      */     
/*      */     try {
/* 4317 */       System.gc();
/* 4318 */       loadWorld(null);
/* 4319 */     } catch (Throwable throwable) {}
/*      */     
/* 4321 */     System.gc();
/*      */   }
/*      */   
/*      */   private void updateDebugProfilerName(int keyCount) {
/* 4325 */     List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 4326 */     if (list != null && !list.isEmpty()) {
/* 4327 */       Profiler.Result profiler$result = list.remove(0);
/* 4328 */       if (keyCount == 0) {
/* 4329 */         if (profiler$result.field_76331_c.length() > 0) {
/* 4330 */           int i = this.debugProfilerName.lastIndexOf(".");
/* 4331 */           if (i >= 0) {
/* 4332 */             this.debugProfilerName = this.debugProfilerName.substring(0, i);
/*      */           }
/*      */         } 
/* 4335 */       } else if (--keyCount < list.size() && !((Profiler.Result)list.get(keyCount)).field_76331_c.equals("unspecified")) {
/* 4336 */         if (this.debugProfilerName.length() > 0) {
/* 4337 */           this.debugProfilerName = String.valueOf(String.valueOf(this.debugProfilerName)) + ".";
/*      */         }
/* 4339 */         this.debugProfilerName = String.valueOf(String.valueOf(this.debugProfilerName)) + ((Profiler.Result)list.get(keyCount)).field_76331_c;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void displayDebugInfo(long elapsedTicksTime) {
/* 4345 */     if (this.mcProfiler.profilingEnabled) {
/* 4346 */       List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 4347 */       Profiler.Result profiler$result = list.remove(0);
/* 4348 */       GlStateManager.clear(256);
/* 4349 */       GlStateManager.matrixMode(5889);
/* 4350 */       GlStateManager.enableColorMaterial();
/* 4351 */       GlStateManager.loadIdentity();
/* 4352 */       GlStateManager.ortho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 4353 */       GlStateManager.matrixMode(5888);
/* 4354 */       GlStateManager.loadIdentity();
/* 4355 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 4356 */       GL11.glLineWidth(1.0F);
/* 4357 */       GlStateManager.disableTexture2D();
/* 4358 */       Tessellator tessellator = Tessellator.getInstance();
/* 4359 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 4360 */       int i = 160;
/* 4361 */       int j = this.displayWidth - 160 - 10;
/* 4362 */       int k = this.displayHeight - 320;
/* 4363 */       GlStateManager.enableBlend();
/* 4364 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 4365 */       worldrenderer.pos((j - 176.0F), (k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 4366 */       worldrenderer.pos((j - 176.0F), (k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 4367 */       worldrenderer.pos((j + 176.0F), (k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 4368 */       worldrenderer.pos((j + 176.0F), (k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 4369 */       tessellator.draw();
/* 4370 */       GlStateManager.disableBlend();
/* 4371 */       double d0 = 0.0D;
/* 4372 */       for (int l = 0; l < list.size(); l++) {
/* 4373 */         Profiler.Result profiler$result2 = list.get(l);
/* 4374 */         int i2 = MathHelper.floor_double(profiler$result2.field_76332_a / 4.0D) + 1;
/* 4375 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 4376 */         int j2 = profiler$result2.getColor();
/* 4377 */         int k2 = j2 >> 16 & 0xFF;
/* 4378 */         int l2 = j2 >> 8 & 0xFF;
/* 4379 */         int i3 = j2 & 0xFF;
/* 4380 */         worldrenderer.pos(j, k, 0.0D).color(k2, l2, i3, 255).endVertex();
/* 4381 */         for (int j3 = i2; j3 >= 0; j3--) {
/* 4382 */           float f = (float)((d0 + profiler$result2.field_76332_a * j3 / i2) * Math.PI * 2.0D / 100.0D);
/* 4383 */           float f2 = MathHelper.sin(f) * 160.0F;
/* 4384 */           float f3 = MathHelper.cos(f) * 160.0F * 0.5F;
/* 4385 */           worldrenderer.pos((j + f2), (k - f3), 0.0D).color(k2, l2, i3, 255).endVertex();
/*      */         } 
/* 4387 */         tessellator.draw();
/* 4388 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/* 4389 */         for (int i4 = i2; i4 >= 0; i4--) {
/* 4390 */           float f4 = (float)((d0 + profiler$result2.field_76332_a * i4 / i2) * Math.PI * 2.0D / 100.0D);
/* 4391 */           float f5 = MathHelper.sin(f4) * 160.0F;
/* 4392 */           float f6 = MathHelper.cos(f4) * 160.0F * 0.5F;
/* 4393 */           worldrenderer.pos((j + f5), (k - f6), 0.0D).color(k2 >> 1, l2 >> 1, i3 >> 1, 255).endVertex();
/* 4394 */           worldrenderer.pos((j + f5), (k - f6 + 10.0F), 0.0D).color(k2 >> 1, l2 >> 1, i3 >> 1, 255).endVertex();
/*      */         } 
/* 4396 */         tessellator.draw();
/* 4397 */         d0 += profiler$result2.field_76332_a;
/*      */       } 
/* 4399 */       DecimalFormat decimalformat = new DecimalFormat("##0.00");
/* 4400 */       GlStateManager.enableTexture2D();
/* 4401 */       String s = "";
/* 4402 */       if (!profiler$result.field_76331_c.equals("unspecified")) {
/* 4403 */         s = String.valueOf(String.valueOf(s)) + "[0] ";
/*      */       }
/* 4405 */       if (profiler$result.field_76331_c.length() == 0) {
/* 4406 */         s = String.valueOf(String.valueOf(s)) + "ROOT ";
/*      */       } else {
/* 4408 */         s = String.valueOf(String.valueOf(s)) + profiler$result.field_76331_c + " ";
/*      */       } 
/* 4410 */       int l3 = 16777215;
/* 4411 */       this.fontRendererObj.drawStringWithShadow(s, (j - 160), (k - 80 - 16), 16777215);
/* 4412 */       this.fontRendererObj.drawStringWithShadow(s = String.valueOf(String.valueOf(decimalformat.format(profiler$result.field_76330_b))) + "%", (j + 160 - this.fontRendererObj.getStringWidth(s)), (k - 80 - 16), 16777215);
/* 4413 */       for (int k3 = 0; k3 < list.size(); k3++) {
/* 4414 */         Profiler.Result profiler$result3 = list.get(k3);
/* 4415 */         String s2 = "";
/* 4416 */         if (profiler$result3.field_76331_c.equals("unspecified")) {
/* 4417 */           s2 = String.valueOf(String.valueOf(s2)) + "[?] ";
/*      */         } else {
/* 4419 */           s2 = String.valueOf(String.valueOf(s2)) + "[" + (k3 + 1) + "] ";
/*      */         } 
/* 4421 */         s2 = String.valueOf(String.valueOf(s2)) + profiler$result3.field_76331_c;
/* 4422 */         this.fontRendererObj.drawStringWithShadow(s2, (j - 160), (k + 80 + k3 * 8 + 20), profiler$result3.getColor());
/* 4423 */         this.fontRendererObj.drawStringWithShadow(s2 = String.valueOf(String.valueOf(decimalformat.format(profiler$result3.field_76332_a))) + "%", (j + 160 - 50 - this.fontRendererObj.getStringWidth(s2)), (k + 80 + k3 * 8 + 20), profiler$result3.getColor());
/* 4424 */         this.fontRendererObj.drawStringWithShadow(s2 = String.valueOf(String.valueOf(decimalformat.format(profiler$result3.field_76330_b))) + "%", (j + 160 - this.fontRendererObj.getStringWidth(s2)), (k + 80 + k3 * 8 + 20), profiler$result3.getColor());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void shutdown() {
/* 4430 */     this.running = false;
/*      */   }
/*      */   
/*      */   public void setIngameFocus() {
/* 4434 */     if (Display.isActive() && !this.inGameHasFocus) {
/* 4435 */       this.inGameHasFocus = true;
/* 4436 */       this.mouseHelper.grabMouseCursor();
/* 4437 */       displayGuiScreen(null);
/* 4438 */       this.leftClickCounter = 10000;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setIngameNotInFocus() {
/* 4443 */     if (this.inGameHasFocus) {
/* 4444 */       KeyBinding.unPressAllKeys();
/* 4445 */       this.inGameHasFocus = false;
/* 4446 */       this.mouseHelper.ungrabMouseCursor();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void displayInGameMenu() {
/* 4451 */     if (this.currentScreen == null) {
/* 4452 */       displayGuiScreen((GuiScreen)new GuiIngameMenu());
/* 4453 */       if (isSingleplayer() && !this.theIntegratedServer.getPublic()) {
/* 4454 */         this.mcSoundHandler.pauseSounds();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void sendClickBlockToController(boolean leftClick) {
/* 4460 */     if (!leftClick) {
/* 4461 */       this.leftClickCounter = 0;
/*      */     }
/* 4463 */     if (!(Client.getInstance()).hudManager.oldanimations.isEnabled()) {
/* 4464 */       if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem()) {
/* 4465 */         if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 4466 */           BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 4467 */           if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit)) {
/* 4468 */             this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 4469 */             this.thePlayer.swingItem();
/*      */           } 
/*      */         } else {
/*      */           
/* 4473 */           this.playerController.resetBlockRemoving();
/*      */         }
/*      */       
/*      */       }
/* 4477 */     } else if ((Client.getInstance()).hudManager.oldanimations.isEnabled()) {
/* 4478 */       if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem()) {
/* 4479 */         if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 4480 */           BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 4481 */           if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit)) {
/* 4482 */             this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 4483 */             this.thePlayer.swingItem();
/*      */           } 
/*      */         } else {
/*      */           
/* 4487 */           this.playerController.resetBlockRemoving();
/*      */         }
/*      */       
/* 4490 */       } else if (this.leftClickCounter <= 0 && leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 4491 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 4492 */         if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/* 4493 */           this.thePlayer.FakeswingItem();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clickMouse() {
/* 4530 */     if (this.leftClickCounter <= 0) {
/* 4531 */       this.thePlayer.swingItem();
/* 4532 */       if (this.objectMouseOver == null) {
/* 4533 */         logger.error("Null returned as 'hitResult', this shouldn't happen!");
/* 4534 */         if (this.playerController.isNotCreative())
/* 4535 */           this.leftClickCounter = 10; 
/*      */       } else {
/*      */         BlockPos blockpos;
/* 4538 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           case ENTITY:
/* 4540 */             this.playerController.attackEntity((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit);
/*      */             return;
/*      */           
/*      */           case null:
/* 4544 */             blockpos = this.objectMouseOver.getBlockPos();
/* 4545 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/* 4546 */               this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         
/* 4552 */         if (this.playerController.isNotCreative()) {
/* 4553 */           this.leftClickCounter = 10;
/*      */         }
/*      */       } 
/*      */     } 
/* 4557 */     if ((Client.getInstance()).hudManager.hitDelayFix.isEnabled()) {
/* 4558 */       this.leftClickCounter = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private void rightClickMouse() {
/* 4563 */     if (!this.playerController.getIsHittingBlock()) {
/* 4564 */       this.rightClickDelayTimer = 4;
/* 4565 */       boolean flag = true;
/* 4566 */       ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
/* 4567 */       if (this.objectMouseOver == null) {
/* 4568 */         logger.warn("Null returned as 'hitResult', this shouldn't happen!");
/*      */       } else {
/* 4570 */         BlockPos blockpos; int i; switch (this.objectMouseOver.typeOfHit) {
/*      */           case ENTITY:
/* 4572 */             if (this.playerController.isPlayerRightClickingOnEntity((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
/* 4573 */               flag = false;
/*      */               break;
/*      */             } 
/* 4576 */             if (this.playerController.interactWithEntitySendPacket((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit)) {
/* 4577 */               flag = false;
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           case null:
/* 4583 */             blockpos = this.objectMouseOver.getBlockPos();
/* 4584 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
/*      */               break;
/*      */             }
/* 4587 */             i = (itemstack != null) ? itemstack.stackSize : 0;
/* 4588 */             if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, itemstack, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
/* 4589 */               flag = false;
/* 4590 */               this.thePlayer.swingItem();
/*      */             } 
/* 4592 */             if (itemstack == null) {
/*      */               return;
/*      */             }
/* 4595 */             if (itemstack.stackSize == 0) {
/* 4596 */               this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
/*      */               break;
/*      */             } 
/* 4599 */             if (itemstack.stackSize != i || this.playerController.isInCreativeMode()) {
/* 4600 */               this.entityRenderer.itemRenderer.resetEquippedProgress();
/*      */             }
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 4607 */       if (flag) {
/* 4608 */         ItemStack itemstack2 = this.thePlayer.inventory.getCurrentItem();
/* 4609 */         if (itemstack2 != null && this.playerController.sendUseItem((EntityPlayer)this.thePlayer, (World)this.theWorld, itemstack2)) {
/* 4610 */           this.entityRenderer.itemRenderer.resetEquippedProgress2();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void toggleFullscreen() {
/*      */     try {
/* 4618 */       this.fullscreen = !this.fullscreen;
/* 4619 */       this.gameSettings.fullScreen = this.fullscreen;
/* 4620 */       if (this.fullscreen) {
/* 4621 */         updateDisplayMode();
/* 4622 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 4623 */         this.displayHeight = Display.getDisplayMode().getHeight();
/* 4624 */         if (this.displayWidth <= 0) {
/* 4625 */           this.displayWidth = 1;
/*      */         }
/* 4627 */         if (this.displayHeight <= 0) {
/* 4628 */           this.displayHeight = 1;
/*      */         }
/*      */       } else {
/* 4631 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 4632 */         this.displayWidth = this.tempDisplayWidth;
/* 4633 */         this.displayHeight = this.tempDisplayHeight;
/* 4634 */         if (this.displayWidth <= 0) {
/* 4635 */           this.displayWidth = 1;
/*      */         }
/* 4637 */         if (this.displayHeight <= 0) {
/* 4638 */           this.displayHeight = 1;
/*      */         }
/*      */       } 
/* 4641 */       if (this.currentScreen != null) {
/* 4642 */         resize(this.displayWidth, this.displayHeight);
/*      */       } else {
/* 4644 */         updateFramebufferSize();
/*      */       } 
/* 4646 */       Display.setFullscreen(this.fullscreen);
/* 4647 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 4648 */       updateDisplay();
/* 4649 */     } catch (Exception exception) {
/* 4650 */       logger.error("Couldn't toggle fullscreen", exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void resize(int width, int height) {
/* 4655 */     this.displayWidth = Math.max(1, width);
/* 4656 */     this.displayHeight = Math.max(1, height);
/* 4657 */     if (this.currentScreen != null) {
/* 4658 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 4659 */       this.currentScreen.onResize(this, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*      */     } 
/* 4661 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 4662 */     updateFramebufferSize();
/*      */   }
/*      */   
/*      */   private void updateFramebufferSize() {
/* 4666 */     this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
/* 4667 */     if (this.entityRenderer != null) {
/* 4668 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/*      */     }
/*      */   }
/*      */   
/*      */   public MusicTicker getMusicTicker() {
/* 4673 */     return this.mcMusicTicker;
/*      */   }
/*      */   
/*      */   public void runTick() throws IOException {
/* 4677 */     if (this.rightClickDelayTimer > 0) {
/* 4678 */       this.rightClickDelayTimer--;
/*      */     }
/* 4680 */     this.mcProfiler.startSection("gui");
/* 4681 */     if (!this.isGamePaused) {
/* 4682 */       this.ingameGUI.updateTick();
/*      */     }
/* 4684 */     this.mcProfiler.endSection();
/* 4685 */     this.entityRenderer.getMouseOver(1.0F);
/* 4686 */     this.mcProfiler.startSection("gameMode");
/* 4687 */     if (!this.isGamePaused && this.theWorld != null) {
/* 4688 */       this.playerController.updateController();
/*      */     }
/* 4690 */     this.mcProfiler.endStartSection("textures");
/* 4691 */     if (!this.isGamePaused) {
/* 4692 */       this.renderEngine.tick();
/*      */     }
/* 4694 */     if (this.currentScreen == null && this.thePlayer != null) {
/* 4695 */       if (this.thePlayer.getHealth() <= 0.0F) {
/* 4696 */         displayGuiScreen(null);
/* 4697 */       } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
/* 4698 */         displayGuiScreen((GuiScreen)new GuiSleepMP());
/*      */       } 
/* 4700 */     } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
/* 4701 */       displayGuiScreen(null);
/*      */     } 
/* 4703 */     if (this.currentScreen != null) {
/* 4704 */       this.leftClickCounter = 10000;
/*      */     }
/* 4706 */     if (this.currentScreen != null) {
/*      */       try {
/* 4708 */         this.currentScreen.handleInput();
/* 4709 */       } catch (Throwable throwable1) {
/* 4710 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
/* 4711 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
/* 4712 */         crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception {
/* 4715 */                 return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */               }
/*      */             });
/* 4718 */         throw new ReportedException(crashreport);
/*      */       } 
/* 4720 */       if (this.currentScreen != null) {
/*      */         try {
/* 4722 */           this.currentScreen.updateScreen();
/* 4723 */         } catch (Throwable throwable2) {
/* 4724 */           CrashReport crashreport2 = CrashReport.makeCrashReport(throwable2, "Ticking screen");
/* 4725 */           CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Affected screen");
/* 4726 */           crashreportcategory2.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception {
/* 4729 */                   return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 4732 */           throw new ReportedException(crashreport2);
/*      */         } 
/*      */       }
/*      */     } 
/* 4736 */     if (this.currentScreen == null || this.currentScreen.allowUserInput) {
/* 4737 */       this.mcProfiler.endStartSection("mouse");
/* 4738 */       while (Mouse.next()) {
/*      */         
/* 4740 */         (new MouseEvent()).call();
/*      */         
/* 4742 */         int i = Mouse.getEventButton();
/* 4743 */         KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
/* 4744 */         if (Mouse.getEventButtonState()) {
/* 4745 */           if (this.thePlayer.isSpectator() && i == 2) {
/* 4746 */             this.ingameGUI.getSpectatorGui().func_175261_b();
/*      */           } else {
/* 4748 */             KeyBinding.onTick(i - 100);
/*      */           } 
/*      */         }
/* 4751 */         long i2 = getSystemTime() - this.systemTime;
/* 4752 */         if (i2 <= 200L) {
/* 4753 */           int j = Mouse.getEventDWheel();
/* 4754 */           if (j != 0) {
/* 4755 */             if (this.thePlayer.isSpectator()) {
/* 4756 */               j = (j < 0) ? -1 : 1;
/* 4757 */               if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
/* 4758 */                 this.ingameGUI.getSpectatorGui().func_175259_b(-j);
/*      */               } else {
/* 4760 */                 float f = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + j * 0.005F, 0.0F, 0.2F);
/* 4761 */                 this.thePlayer.capabilities.setFlySpeed(f);
/*      */               } 
/*      */             } else {
/* 4764 */               this.thePlayer.inventory.changeCurrentItem(j);
/*      */             } 
/*      */           }
/* 4767 */           if (this.currentScreen == null) {
/* 4768 */             if (this.inGameHasFocus) {
/*      */               continue;
/*      */             }
/* 4771 */             if (!Mouse.getEventButtonState()) {
/*      */               continue;
/*      */             }
/* 4774 */             setIngameFocus(); continue;
/*      */           } 
/* 4776 */           if (this.currentScreen == null) {
/*      */             continue;
/*      */           }
/* 4779 */           this.currentScreen.handleMouseInput();
/*      */         } 
/*      */       } 
/*      */       
/* 4783 */       if (this.leftClickCounter > 0) {
/* 4784 */         this.leftClickCounter--;
/*      */       }
/* 4786 */       this.mcProfiler.endStartSection("keyboard");
/* 4787 */       while (Keyboard.next()) {
/* 4788 */         int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/* 4789 */         KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());
/* 4790 */         if (Keyboard.getEventKeyState()) {
/* 4791 */           KeyBinding.onTick(k);
/*      */         }
/* 4793 */         KeyEvent keyEvent = new KeyEvent(k);
/* 4794 */         keyEvent.call();
/* 4795 */         if (this.debugCrashKeyPressTime > 0L) {
/* 4796 */           if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
/* 4797 */             throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/*      */           }
/* 4799 */           if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
/* 4800 */             this.debugCrashKeyPressTime = -1L;
/*      */           }
/* 4802 */         } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
/* 4803 */           this.debugCrashKeyPressTime = getSystemTime();
/*      */         } 
/* 4805 */         dispatchKeypresses();
/* 4806 */         if (Keyboard.getEventKeyState()) {
/* 4807 */           if (k == 62 && this.entityRenderer != null) {
/* 4808 */             this.entityRenderer.switchUseShader();
/*      */           }
/* 4810 */           if (this.currentScreen != null) {
/* 4811 */             this.currentScreen.handleKeyboardInput();
/*      */           } else {
/* 4813 */             if (k == 1) {
/* 4814 */               displayInGameMenu();
/*      */             }
/* 4816 */             if (k == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
/* 4817 */               this.ingameGUI.getChatGUI().clearChatMessages();
/*      */             }
/* 4819 */             if (k == 31 && Keyboard.isKeyDown(61)) {
/* 4820 */               refreshResources();
/*      */             }
/* 4822 */             if (k == 17) {
/* 4823 */               Keyboard.isKeyDown(61);
/*      */             }
/* 4825 */             if (k == 18) {
/* 4826 */               Keyboard.isKeyDown(61);
/*      */             }
/* 4828 */             if (k == 47) {
/* 4829 */               Keyboard.isKeyDown(61);
/*      */             }
/* 4831 */             if (k == 38) {
/* 4832 */               Keyboard.isKeyDown(61);
/*      */             }
/* 4834 */             if (k == 22) {
/* 4835 */               Keyboard.isKeyDown(61);
/*      */             }
/* 4837 */             if (k == 20 && Keyboard.isKeyDown(61)) {
/* 4838 */               refreshResources();
/*      */             }
/* 4840 */             if (k == 33 && Keyboard.isKeyDown(61)) {
/* 4841 */               this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
/*      */             }
/* 4843 */             if (k == 30 && Keyboard.isKeyDown(61)) {
/* 4844 */               this.renderGlobal.loadRenderers();
/*      */             }
/* 4846 */             if (k == 35 && Keyboard.isKeyDown(61)) {
/* 4847 */               this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
/* 4848 */               this.gameSettings.saveOptions();
/*      */             } 
/* 4850 */             if (k == 48 && Keyboard.isKeyDown(61)) {
/* 4851 */               this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
/*      */             }
/* 4853 */             if (k == 25 && Keyboard.isKeyDown(61)) {
/* 4854 */               this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
/* 4855 */               this.gameSettings.saveOptions();
/*      */             } 
/* 4857 */             if (k == 59) {
/* 4858 */               this.gameSettings.thirdPersonView = !this.gameSettings.thirdPersonView;
/*      */             }
/* 4860 */             if (k == 61) {
/* 4861 */               this.gameSettings.showDebugProfilerChart = !this.gameSettings.showDebugProfilerChart;
/* 4862 */               this.gameSettings.showLagometer = GuiScreen.isShiftKeyDown();
/* 4863 */               this.gameSettings.lastServer = GuiScreen.isAltKeyDown();
/*      */             } 
/* 4865 */             if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
/*      */ 
/*      */               
/* 4868 */               GameSettings gameSettings3 = this.gameSettings, gameSettings4 = gameSettings3, gameSettings2 = gameSettings4;
/* 4869 */               gameSettings4.showDebugInfo++;
/* 4870 */               if (this.gameSettings.showDebugInfo > 2) {
/* 4871 */                 this.gameSettings.showDebugInfo = 0;
/*      */               }
/* 4873 */               if (this.gameSettings.showDebugInfo == 0) {
/* 4874 */                 this.entityRenderer.loadEntityShader(getRenderViewEntity());
/* 4875 */               } else if (this.gameSettings.showDebugInfo == 1) {
/* 4876 */                 this.entityRenderer.loadEntityShader(null);
/*      */               } 
/* 4878 */               this.renderGlobal.setDisplayListEntitiesDirty();
/*      */             } 
/* 4880 */             if (this.gameSettings.keyBindFullscreen.isPressed()) {
/* 4881 */               this.gameSettings.debugCamEnable = !this.gameSettings.debugCamEnable;
/*      */             }
/*      */           } 
/* 4884 */           if (!this.gameSettings.showDebugProfilerChart) {
/*      */             continue;
/*      */           }
/* 4887 */           if (!this.gameSettings.showLagometer) {
/*      */             continue;
/*      */           }
/* 4890 */           if (k == 11) {
/* 4891 */             updateDebugProfilerName(0);
/*      */           }
/* 4893 */           for (int j2 = 0; j2 < 9; j2++) {
/* 4894 */             if (k == 2 + j2) {
/* 4895 */               updateDebugProfilerName(j2 + 1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 4900 */       for (int l = 0; l < 9; l++) {
/* 4901 */         if (this.gameSettings.keyBindings[l].isPressed()) {
/* 4902 */           if (this.thePlayer.isSpectator()) {
/* 4903 */             this.ingameGUI.getSpectatorGui().func_175260_a(l);
/*      */           } else {
/* 4905 */             this.thePlayer.inventory.currentItem = l;
/*      */           } 
/*      */         }
/*      */       } 
/* 4909 */       boolean flag = (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN);
/* 4910 */       while (this.gameSettings.keyBindUseItem.isPressed()) {
/* 4911 */         if (this.playerController.isRidingHorse()) {
/* 4912 */           this.thePlayer.sendHorseInventory(); continue;
/*      */         } 
/* 4914 */         getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 4915 */         displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.thePlayer));
/*      */       } 
/*      */       
/* 4918 */       while (this.gameSettings.keyBindAttack.isPressed()) {
/* 4919 */         if (!this.thePlayer.isSpectator()) {
/* 4920 */           this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
/*      */         }
/*      */       } 
/* 4923 */       while (this.gameSettings.keyBindPlayerList.isPressed() && flag) {
/* 4924 */         displayGuiScreen((GuiScreen)new GuiChat());
/*      */       }
/* 4926 */       if (this.currentScreen == null && this.gameSettings.keyBindScreenshot.isPressed() && flag) {
/* 4927 */         displayGuiScreen((GuiScreen)new GuiChat("/"));
/*      */       }
/* 4929 */       if (this.thePlayer.isUsingItem()) {
/* 4930 */         if (!this.gameSettings.keyBindDrop.isKeyDown())
/* 4931 */           this.playerController.onStoppedUsingItem((EntityPlayer)this.thePlayer);  do {
/*      */         
/* 4933 */         } while (this.gameSettings.keyBindPickBlock.isPressed()); do {
/*      */         
/* 4935 */         } while (this.gameSettings.keyBindDrop.isPressed()); do {
/*      */         
/* 4937 */         } while (this.gameSettings.keyBindChat.isPressed());
/*      */       } else {
/*      */         
/* 4940 */         while (this.gameSettings.keyBindPickBlock.isPressed()) {
/* 4941 */           clickMouse();
/*      */         }
/* 4943 */         while (this.gameSettings.keyBindDrop.isPressed()) {
/* 4944 */           rightClickMouse();
/*      */         }
/* 4946 */         while (this.gameSettings.keyBindChat.isPressed()) {
/* 4947 */           middleClickMouse();
/*      */         }
/*      */       } 
/* 4950 */       if (this.gameSettings.keyBindDrop.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
/* 4951 */         rightClickMouse();
/*      */       }
/* 4953 */       sendClickBlockToController((this.currentScreen == null && this.gameSettings.keyBindPickBlock.isKeyDown() && this.inGameHasFocus));
/*      */     } 
/* 4955 */     if (this.theWorld != null) {
/* 4956 */       if (this.thePlayer != null) {
/* 4957 */         this.joinPlayerCounter++;
/* 4958 */         if (this.joinPlayerCounter == 30) {
/* 4959 */           this.joinPlayerCounter = 0;
/* 4960 */           this.theWorld.joinEntityInSurroundings((Entity)this.thePlayer);
/*      */         } 
/*      */       } 
/* 4963 */       this.mcProfiler.endStartSection("gameRenderer");
/* 4964 */       if (!this.isGamePaused) {
/* 4965 */         this.entityRenderer.updateRenderer();
/*      */       }
/* 4967 */       this.mcProfiler.endStartSection("levelRenderer");
/* 4968 */       if (!this.isGamePaused) {
/* 4969 */         this.renderGlobal.updateClouds();
/*      */       }
/* 4971 */       this.mcProfiler.endStartSection("level");
/* 4972 */       if (!this.isGamePaused) {
/* 4973 */         if (this.theWorld.getLastLightningBolt() > 0) {
/* 4974 */           this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
/*      */         }
/* 4976 */         this.theWorld.updateEntities();
/*      */       } 
/* 4978 */     } else if (this.entityRenderer.isShaderActive()) {
/* 4979 */       this.entityRenderer.stopUseShader();
/*      */     } 
/* 4981 */     if (!this.isGamePaused) {
/* 4982 */       this.mcMusicTicker.update();
/* 4983 */       this.mcSoundHandler.update();
/*      */     } 
/* 4985 */     if (this.theWorld != null) {
/* 4986 */       if (!this.isGamePaused) {
/* 4987 */         this.theWorld.setAllowedSpawnTypes((this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         try {
/* 4989 */           this.theWorld.tick();
/* 4990 */         } catch (Throwable throwable3) {
/* 4991 */           CrashReport crashreport3 = CrashReport.makeCrashReport(throwable3, "Exception in world tick");
/* 4992 */           if (this.theWorld == null) {
/* 4993 */             CrashReportCategory crashreportcategory3 = crashreport3.makeCategory("Affected level");
/* 4994 */             crashreportcategory3.addCrashSection("Problem", "Level is null!");
/*      */           } else {
/* 4996 */             this.theWorld.addWorldInfoToCrashReport(crashreport3);
/*      */           } 
/* 4998 */           throw new ReportedException(crashreport3);
/*      */         } 
/*      */       } 
/* 5001 */       this.mcProfiler.endStartSection("animateTick");
/* 5002 */       if (!this.isGamePaused && this.theWorld != null) {
/* 5003 */         this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
/*      */       }
/* 5005 */       this.mcProfiler.endStartSection("particles");
/* 5006 */       if (!this.isGamePaused) {
/* 5007 */         this.effectRenderer.updateEffects();
/*      */       }
/* 5009 */     } else if (this.myNetworkManager != null) {
/* 5010 */       this.mcProfiler.endStartSection("pendingConnection");
/* 5011 */       this.myNetworkManager.processReceivedPackets();
/*      */     } 
/*      */     
/* 5014 */     ClientTickEvent event = new ClientTickEvent();
/* 5015 */     event.call();
/* 5016 */     this.mcProfiler.endSection();
/* 5017 */     this.systemTime = getSystemTime();
/*      */   }
/*      */   
/*      */   public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
/* 5021 */     loadWorld(null);
/* 5022 */     System.gc();
/* 5023 */     ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
/* 5024 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 5025 */     if (worldinfo == null && worldSettingsIn != null) {
/* 5026 */       worldinfo = new WorldInfo(worldSettingsIn, folderName);
/* 5027 */       isavehandler.saveWorldInfo(worldinfo);
/*      */     } 
/* 5029 */     if (worldSettingsIn == null) {
/* 5030 */       worldSettingsIn = new WorldSettings(worldinfo);
/*      */     }
/*      */     try {
/* 5033 */       (this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn)).startServerThread();
/* 5034 */       this.integratedServerIsRunning = true;
/* 5035 */     } catch (Throwable throwable) {
/* 5036 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
/* 5037 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
/* 5038 */       crashreportcategory.addCrashSection("Level ID", folderName);
/* 5039 */       crashreportcategory.addCrashSection("Level Name", worldName);
/* 5040 */       throw new ReportedException(crashreport);
/*      */     } 
/* 5042 */     this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
/* 5043 */     while (!this.theIntegratedServer.serverIsInRunLoop()) {
/* 5044 */       String s = this.theIntegratedServer.getUserMessage();
/* 5045 */       if (s != null) {
/* 5046 */         this.loadingScreen.displayLoadingString(I18n.format(s, new Object[0]));
/*      */       } else {
/* 5048 */         this.loadingScreen.displayLoadingString("");
/*      */       } 
/*      */       try {
/* 5051 */         Thread.sleep(200L);
/* 5052 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */     
/* 5055 */     displayGuiScreen(null);
/* 5056 */     SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
/* 5057 */     NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
/* 5058 */     networkmanager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkmanager, this, null));
/* 5059 */     networkmanager.sendPacket((Packet)new C00Handshake(47, socketaddress.toString(), 0, EnumConnectionState.LOGIN));
/* 5060 */     networkmanager.sendPacket((Packet)new C00PacketLoginStart(getSession().getProfile()));
/* 5061 */     this.myNetworkManager = networkmanager;
/* 5062 */     Client.getInstance().enterSpWorld();
/*      */   }
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn) {
/* 5066 */     loadWorld(worldClientIn, "");
/*      */   }
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
/* 5070 */     if (worldClientIn == null) {
/* 5071 */       NetHandlerPlayClient nethandlerplayclient = getNetHandler();
/* 5072 */       if (nethandlerplayclient != null) {
/* 5073 */         nethandlerplayclient.cleanup();
/*      */       }
/* 5075 */       if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
/* 5076 */         this.theIntegratedServer.initiateShutdown();
/* 5077 */         this.theIntegratedServer.setStaticInstance();
/*      */       } 
/* 5079 */       this.theIntegratedServer = null;
/* 5080 */       this.guiAchievement.clearAchievements();
/* 5081 */       this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
/*      */     } 
/* 5083 */     this.renderViewEntity = null;
/* 5084 */     this.myNetworkManager = null;
/* 5085 */     if (this.loadingScreen != null) {
/* 5086 */       this.loadingScreen.resetProgressAndMessage(loadingMessage);
/* 5087 */       this.loadingScreen.displayLoadingString("");
/*      */     } 
/* 5089 */     if (worldClientIn == null && this.theWorld != null) {
/* 5090 */       this.mcResourcePackRepository.clearResourcePack();
/* 5091 */       this.ingameGUI.resetPlayersOverlayFooterHeader();
/* 5092 */       setServerData(null);
/* 5093 */       this.integratedServerIsRunning = false;
/*      */     } 
/* 5095 */     this.mcSoundHandler.stopSounds();
/* 5096 */     if ((this.theWorld = worldClientIn) != null) {
/* 5097 */       if (this.renderGlobal != null) {
/* 5098 */         this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
/*      */       }
/* 5100 */       if (this.effectRenderer != null) {
/* 5101 */         this.effectRenderer.clearEffects((World)worldClientIn);
/*      */       }
/* 5103 */       if (this.thePlayer == null) {
/* 5104 */         this.thePlayer = this.playerController.func_178892_a((World)worldClientIn, new StatFileWriter());
/* 5105 */         this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/*      */       } 
/* 5107 */       this.thePlayer.preparePlayerToSpawn();
/* 5108 */       worldClientIn.spawnEntityInWorld((Entity)this.thePlayer);
/* 5109 */       this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 5110 */       this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 5111 */       this.renderViewEntity = (Entity)this.thePlayer;
/*      */     } else {
/* 5113 */       this.saveLoader.flushCache();
/* 5114 */       this.thePlayer = null;
/*      */     } 
/* 5116 */     System.gc();
/* 5117 */     this.systemTime = 0L;
/*      */   }
/*      */   
/*      */   public void setDimensionAndSpawnPlayer(int dimension) {
/* 5121 */     this.theWorld.setInitialSpawnLocation();
/* 5122 */     this.theWorld.removeAllEntities();
/* 5123 */     int i = 0;
/* 5124 */     String s = null;
/* 5125 */     if (this.thePlayer != null) {
/* 5126 */       i = this.thePlayer.getEntityId();
/* 5127 */       this.theWorld.removeEntity((Entity)this.thePlayer);
/* 5128 */       s = this.thePlayer.getClientBrand();
/*      */     } 
/* 5130 */     this.renderViewEntity = null;
/* 5131 */     EntityPlayerSP entityplayersp = this.thePlayer;
/* 5132 */     this.thePlayer = this.playerController.func_178892_a((World)this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
/* 5133 */     this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityplayersp.getDataWatcher().getAllWatched());
/* 5134 */     this.thePlayer.dimension = dimension;
/* 5135 */     this.renderViewEntity = (Entity)this.thePlayer;
/* 5136 */     this.thePlayer.preparePlayerToSpawn();
/* 5137 */     this.thePlayer.setClientBrand(s);
/* 5138 */     this.theWorld.spawnEntityInWorld((Entity)this.thePlayer);
/* 5139 */     this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/* 5140 */     this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 5141 */     this.thePlayer.setEntityId(i);
/* 5142 */     this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 5143 */     this.thePlayer.setReducedDebug(entityplayersp.hasReducedDebug());
/* 5144 */     if (this.currentScreen instanceof GuiGameOver) {
/* 5145 */       displayGuiScreen(null);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean isDemo() {
/* 5150 */     return this.isDemo;
/*      */   }
/*      */   
/*      */   public NetHandlerPlayClient getNetHandler() {
/* 5154 */     return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
/*      */   }
/*      */   
/*      */   public static boolean isGuiEnabled() {
/* 5158 */     return !(theMinecraft != null && theMinecraft.gameSettings.thirdPersonView);
/*      */   }
/*      */   
/*      */   public static boolean isFancyGraphicsEnabled() {
/* 5162 */     return (theMinecraft != null && theMinecraft.gameSettings.fancyGraphics);
/*      */   }
/*      */   
/*      */   public static boolean isAmbientOcclusionEnabled() {
/* 5166 */     return (theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0);
/*      */   }
/*      */   
/*      */   private void middleClickMouse() {
/* 5170 */     if (this.objectMouseOver != null) {
/* 5171 */       Item item; boolean flag = this.thePlayer.capabilities.isCreativeMode;
/* 5172 */       int i = 0;
/* 5173 */       boolean flag2 = false;
/* 5174 */       TileEntity tileentity = null;
/*      */       
/* 5176 */       if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 5177 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 5178 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/* 5179 */         if (block.getMaterial() == Material.air) {
/*      */           return;
/*      */         }
/* 5182 */         item = block.getItem((World)this.theWorld, blockpos);
/* 5183 */         if (item == null) {
/*      */           return;
/*      */         }
/* 5186 */         if (flag && GuiScreen.isCtrlKeyDown()) {
/* 5187 */           tileentity = this.theWorld.getTileEntity(blockpos);
/*      */         }
/* 5189 */         Block block2 = (item instanceof net.minecraft.item.ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block;
/* 5190 */         i = block2.getDamageValue((World)this.theWorld, blockpos);
/* 5191 */         flag2 = item.getHasSubtypes();
/*      */       } else {
/* 5193 */         if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
/*      */           return;
/*      */         }
/* 5196 */         if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityPainting) {
/* 5197 */           item = Items.painting;
/* 5198 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot) {
/* 5199 */           item = Items.lead;
/* 5200 */         } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
/* 5201 */           EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 5202 */           ItemStack itemstack = entityitemframe.getDisplayedItem();
/* 5203 */           if (itemstack == null) {
/* 5204 */             item = Items.item_frame;
/*      */           } else {
/* 5206 */             item = itemstack.getItem();
/* 5207 */             i = itemstack.getMetadata();
/* 5208 */             flag2 = true;
/*      */           } 
/* 5210 */         } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
/* 5211 */           EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
/* 5212 */           switch (entityminecart.getMinecartType()) {
/*      */             case FURNACE:
/* 5214 */               item = Items.furnace_minecart;
/*      */               break;
/*      */             
/*      */             case null:
/* 5218 */               item = Items.chest_minecart;
/*      */               break;
/*      */             
/*      */             case TNT:
/* 5222 */               item = Items.tnt_minecart;
/*      */               break;
/*      */             
/*      */             case HOPPER:
/* 5226 */               item = Items.hopper_minecart;
/*      */               break;
/*      */             
/*      */             case COMMAND_BLOCK:
/* 5230 */               item = Items.command_block_minecart;
/*      */               break;
/*      */             
/*      */             default:
/* 5234 */               item = Items.minecart;
/*      */               break;
/*      */           } 
/*      */         
/* 5238 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityBoat) {
/* 5239 */           item = Items.boat;
/* 5240 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityArmorStand) {
/* 5241 */           ItemArmorStand itemArmorStand = Items.armor_stand;
/*      */         } else {
/* 5243 */           item = Items.spawn_egg;
/* 5244 */           i = EntityList.getEntityID(this.objectMouseOver.entityHit);
/* 5245 */           flag2 = true;
/* 5246 */           if (!EntityList.entityEggs.containsKey(Integer.valueOf(i))) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/* 5251 */       InventoryPlayer inventoryplayer = this.thePlayer.inventory;
/* 5252 */       if (tileentity == null) {
/* 5253 */         inventoryplayer.setCurrentItem(item, i, flag2, flag);
/*      */       } else {
/* 5255 */         ItemStack itemstack2 = pickBlockWithNBT(item, i, tileentity);
/* 5256 */         inventoryplayer.setInventorySlotContents(inventoryplayer.currentItem, itemstack2);
/*      */       } 
/* 5258 */       if (flag) {
/* 5259 */         int j = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventoryplayer.currentItem;
/* 5260 */         this.playerController.sendSlotPacket(inventoryplayer.getStackInSlot(inventoryplayer.currentItem), j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private ItemStack pickBlockWithNBT(Item itemIn, int meta, TileEntity tileEntityIn) {
/* 5266 */     ItemStack itemstack = new ItemStack(itemIn, 1, meta);
/* 5267 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 5268 */     tileEntityIn.writeToNBT(nbttagcompound);
/* 5269 */     if (itemIn == Items.skull && nbttagcompound.hasKey("Owner")) {
/* 5270 */       NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Owner");
/* 5271 */       NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 5272 */       nbttagcompound3.setTag("SkullOwner", (NBTBase)nbttagcompound2);
/* 5273 */       itemstack.setTagCompound(nbttagcompound3);
/* 5274 */       return itemstack;
/*      */     } 
/* 5276 */     itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 5277 */     NBTTagCompound nbttagcompound4 = new NBTTagCompound();
/* 5278 */     NBTTagList nbttaglist = new NBTTagList();
/* 5279 */     nbttaglist.appendTag((NBTBase)new NBTTagString("(+NBT)"));
/* 5280 */     nbttagcompound4.setTag("Lore", (NBTBase)nbttaglist);
/* 5281 */     itemstack.setTagInfo("display", (NBTBase)nbttagcompound4);
/* 5282 */     return itemstack;
/*      */   }
/*      */   
/*      */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
/* 5286 */     theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5289 */             return Minecraft.this.launchedVersion;
/*      */           }
/*      */         });
/* 5292 */     theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable<String>()
/*      */         {
/*      */           public String call() {
/* 5295 */             return Sys.getVersion();
/*      */           }
/*      */         });
/* 5298 */     theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable<String>()
/*      */         {
/*      */           public String call() {
/* 5301 */             return String.valueOf(String.valueOf(GL11.glGetString(7937))) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
/*      */           }
/*      */         });
/* 5304 */     theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable<String>()
/*      */         {
/*      */           public String call() {
/* 5307 */             return OpenGlHelper.getLogText();
/*      */           }
/*      */         });
/* 5310 */     theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable<String>()
/*      */         {
/*      */           public String call() {
/* 5313 */             return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
/*      */           }
/*      */         });
/* 5316 */     theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5319 */             String s = ClientBrandRetriever.getClientModName();
/* 5320 */             return s.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + s + "'");
/*      */           }
/*      */         });
/* 5323 */     theCrash.getCategory().addCrashSectionCallable("Type", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5326 */             return "Client (map_client.txt)";
/*      */           }
/*      */         });
/* 5329 */     theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5332 */             StringBuilder stringbuilder = new StringBuilder();
/* 5333 */             for (String s : Minecraft.this.gameSettings.resourcePacks) {
/* 5334 */               if (stringbuilder.length() > 0) {
/* 5335 */                 stringbuilder.append(", ");
/*      */               }
/* 5337 */               stringbuilder.append(s);
/* 5338 */               if (Minecraft.this.gameSettings.incompatibleResourcePacks.contains(s)) {
/* 5339 */                 stringbuilder.append(" (incompatible)");
/*      */               }
/*      */             } 
/* 5342 */             return stringbuilder.toString();
/*      */           }
/*      */         });
/* 5345 */     theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5348 */             return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
/*      */           }
/*      */         });
/* 5351 */     theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception {
/* 5354 */             return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/* 5357 */     theCrash.getCategory().addCrashSectionCallable("CPU", new Callable<String>()
/*      */         {
/*      */           public String call() {
/* 5360 */             return OpenGlHelper.getCpu();
/*      */           }
/*      */         });
/* 5363 */     if (this.theWorld != null) {
/* 5364 */       this.theWorld.addWorldInfoToCrashReport(theCrash);
/*      */     }
/* 5366 */     return theCrash;
/*      */   }
/*      */   
/*      */   public static Minecraft getMinecraft() {
/* 5370 */     return theMinecraft;
/*      */   }
/*      */   
/*      */   public ListenableFuture<Object> scheduleResourcesRefresh() {
/* 5374 */     return addScheduledTask(new Runnable()
/*      */         {
/*      */           public void run() {
/* 5377 */             Minecraft.this.refreshResources();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 5384 */     playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
/* 5385 */     playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 5386 */     playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 5387 */     playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 5388 */     playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 5389 */     playerSnooper.addClientStat("current_action", getCurrentAction());
/* 5390 */     String s = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big";
/* 5391 */     playerSnooper.addClientStat("endianness", s);
/* 5392 */     playerSnooper.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 5393 */     int i = 0;
/* 5394 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries()) {
/* 5395 */       playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
/*      */     }
/* 5397 */     if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
/* 5398 */       playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/*      */     }
/*      */   }
/*      */   
/*      */   private String getCurrentAction() {
/* 5403 */     return (this.theIntegratedServer != null) ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : ((this.currentServerData != null) ? (this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer") : "out_of_game");
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 5408 */     playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
/* 5409 */     playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
/* 5410 */     playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
/* 5411 */     playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
/* 5412 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 5413 */     playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_arrays_of_arrays));
/* 5414 */     playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(contextcapabilities.GL_ARB_base_instance));
/* 5415 */     playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(contextcapabilities.GL_ARB_blend_func_extended));
/* 5416 */     playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_clear_buffer_object));
/* 5417 */     playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_color_buffer_float));
/* 5418 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(contextcapabilities.GL_ARB_compatibility));
/* 5419 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(contextcapabilities.GL_ARB_compressed_texture_pixel_storage));
/* 5420 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 5421 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 5422 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 5423 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 5424 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 5425 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 5426 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 5427 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 5428 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_clamp));
/* 5429 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_texture));
/* 5430 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers));
/* 5431 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers_blend));
/* 5432 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_elements_base_vertex));
/* 5433 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_indirect));
/* 5434 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_instanced));
/* 5435 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_attrib_location));
/* 5436 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_uniform_location));
/* 5437 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_layer_viewport));
/* 5438 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program));
/* 5439 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_shader));
/* 5440 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program_shadow));
/* 5441 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_object));
/* 5442 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_sRGB));
/* 5443 */     playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_ARB_geometry_shader4));
/* 5444 */     playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(contextcapabilities.GL_ARB_gpu_shader5));
/* 5445 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_pixel));
/* 5446 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_vertex));
/* 5447 */     playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_instanced_arrays));
/* 5448 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_alignment));
/* 5449 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_range));
/* 5450 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(contextcapabilities.GL_ARB_multisample));
/* 5451 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(contextcapabilities.GL_ARB_multitexture));
/* 5452 */     playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(contextcapabilities.GL_ARB_occlusion_query2));
/* 5453 */     playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_pixel_buffer_object));
/* 5454 */     playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_seamless_cube_map));
/* 5455 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_objects));
/* 5456 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_stencil_export));
/* 5457 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_texture_lod));
/* 5458 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow));
/* 5459 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow_ambient));
/* 5460 */     playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(contextcapabilities.GL_ARB_stencil_texturing));
/* 5461 */     playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(contextcapabilities.GL_ARB_sync));
/* 5462 */     playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_tessellation_shader));
/* 5463 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_border_clamp));
/* 5464 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_buffer_object));
/* 5465 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map));
/* 5466 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map_array));
/* 5467 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_non_power_of_two));
/* 5468 */     playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_uniform_buffer_object));
/* 5469 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_blend));
/* 5470 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_buffer_object));
/* 5471 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_program));
/* 5472 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_shader));
/* 5473 */     playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(contextcapabilities.GL_EXT_bindable_uniform));
/* 5474 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_equation_separate));
/* 5475 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_func_separate));
/* 5476 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_minmax));
/* 5477 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_subtract));
/* 5478 */     playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_EXT_draw_instanced));
/* 5479 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_multisample));
/* 5480 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_object));
/* 5481 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_sRGB));
/* 5482 */     playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_geometry_shader4));
/* 5483 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_program_parameters));
/* 5484 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_shader4));
/* 5485 */     playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(contextcapabilities.GL_EXT_multi_draw_arrays));
/* 5486 */     playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(contextcapabilities.GL_EXT_packed_depth_stencil));
/* 5487 */     playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(contextcapabilities.GL_EXT_paletted_texture));
/* 5488 */     playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(contextcapabilities.GL_EXT_rescale_normal));
/* 5489 */     playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(contextcapabilities.GL_EXT_separate_shader_objects));
/* 5490 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(contextcapabilities.GL_EXT_shader_image_load_store));
/* 5491 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(contextcapabilities.GL_EXT_shadow_funcs));
/* 5492 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(contextcapabilities.GL_EXT_shared_texture_palette));
/* 5493 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_clear_tag));
/* 5494 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_two_side));
/* 5495 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_wrap));
/* 5496 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_3d));
/* 5497 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_array));
/* 5498 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_buffer_object));
/* 5499 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_integer));
/* 5500 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_lod_bias));
/* 5501 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_sRGB));
/* 5502 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_shader));
/* 5503 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_weighting));
/* 5504 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
/* 5505 */     GL11.glGetError();
/* 5506 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
/* 5507 */     GL11.glGetError();
/* 5508 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger(34921)));
/* 5509 */     GL11.glGetError();
/* 5510 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35660)));
/* 5511 */     GL11.glGetError();
/* 5512 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(34930)));
/* 5513 */     GL11.glGetError();
/* 5514 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35071)));
/* 5515 */     GL11.glGetError();
/* 5516 */     playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/*      */   }
/*      */   
/*      */   public static int getGLMaximumTextureSize() {
/* 5520 */     for (int i = 16384; i > 0; i >>= 1) {
/* 5521 */       GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 5522 */       int j = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/* 5523 */       if (j != 0) {
/* 5524 */         return i;
/*      */       }
/*      */     } 
/* 5527 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 5532 */     return this.gameSettings.snooperEnabled;
/*      */   }
/*      */   
/*      */   public void setServerData(ServerData serverDataIn) {
/* 5536 */     this.currentServerData = serverDataIn;
/*      */   }
/*      */   
/*      */   public ServerData getCurrentServerData() {
/* 5540 */     return this.currentServerData;
/*      */   }
/*      */   
/*      */   public boolean isIntegratedServerRunning() {
/* 5544 */     return this.integratedServerIsRunning;
/*      */   }
/*      */   
/*      */   public boolean isSingleplayer() {
/* 5548 */     return (this.integratedServerIsRunning && this.theIntegratedServer != null);
/*      */   }
/*      */   
/*      */   public IntegratedServer getIntegratedServer() {
/* 5552 */     return this.theIntegratedServer;
/*      */   }
/*      */   
/*      */   public static void stopIntegratedServer() {
/* 5556 */     if (theMinecraft != null) {
/* 5557 */       IntegratedServer integratedserver = theMinecraft.getIntegratedServer();
/* 5558 */       if (integratedserver != null) {
/* 5559 */         integratedserver.stopServer();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 5565 */     return this.usageSnooper;
/*      */   }
/*      */   
/*      */   public static long getSystemTime() {
/* 5569 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*      */   }
/*      */   
/*      */   public boolean isFullScreen() {
/* 5573 */     return this.fullscreen;
/*      */   }
/*      */   
/*      */   public Session getSession() {
/* 5577 */     return this.session;
/*      */   }
/*      */   
/*      */   public PropertyMap getTwitchDetails() {
/* 5581 */     return this.twitchDetails;
/*      */   }
/*      */   
/*      */   public PropertyMap getProfileProperties() {
/* 5585 */     if (this.profileProperties.isEmpty()) {
/* 5586 */       GameProfile gameprofile = getSessionService().fillProfileProperties(this.session.getProfile(), false);
/* 5587 */       this.profileProperties.putAll((Multimap)gameprofile.getProperties());
/*      */     } 
/* 5589 */     return this.profileProperties;
/*      */   }
/*      */   
/*      */   public Proxy getProxy() {
/* 5593 */     return this.proxy;
/*      */   }
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 5597 */     return this.renderEngine;
/*      */   }
/*      */   
/*      */   public IResourceManager getResourceManager() {
/* 5601 */     return (IResourceManager)this.mcResourceManager;
/*      */   }
/*      */   
/*      */   public ResourcePackRepository getResourcePackRepository() {
/* 5605 */     return this.mcResourcePackRepository;
/*      */   }
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 5609 */     return this.mcLanguageManager;
/*      */   }
/*      */   
/*      */   public TextureMap getTextureMapBlocks() {
/* 5613 */     return this.textureMapBlocks;
/*      */   }
/*      */   
/*      */   public boolean isJava64bit() {
/* 5617 */     return this.jvm64bit;
/*      */   }
/*      */   
/*      */   public boolean isGamePaused() {
/* 5621 */     return this.isGamePaused;
/*      */   }
/*      */   
/*      */   public SoundHandler getSoundHandler() {
/* 5625 */     return this.mcSoundHandler;
/*      */   }
/*      */   
/*      */   public MusicTicker.MusicType getAmbientMusicType() {
/* 5629 */     return (this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
/*      */   }
/*      */   
/*      */   public IStream getTwitchStream() {
/* 5633 */     return this.stream;
/*      */   }
/*      */   
/*      */   public void dispatchKeypresses() {
/* 5637 */     int i = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
/* 5638 */     if (i != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)) {
/* 5639 */       if (Keyboard.getEventKeyState()) {
/* 5640 */         if (i == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
/* 5641 */           if (getTwitchStream().isBroadcasting()) {
/* 5642 */             getTwitchStream().stopBroadcasting();
/* 5643 */           } else if (getTwitchStream().isReadyToBroadcast()) {
/* 5644 */             displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback()
/*      */                   {
/*      */                     public void confirmClicked(boolean result, int id) {
/* 5647 */                       if (result) {
/* 5648 */                         Minecraft.this.getTwitchStream().func_152930_t();
/*      */                       }
/* 5650 */                       Minecraft.this.displayGuiScreen(null);
/*      */                     }
/* 5652 */                   },  I18n.format("stream.confirm_start", new Object[0]), "", 0));
/* 5653 */           } else if (getTwitchStream().func_152928_D() && getTwitchStream().func_152936_l()) {
/* 5654 */             if (this.theWorld != null) {
/* 5655 */               this.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText("Not ready to start streaming yet!"));
/*      */             }
/*      */           } else {
/* 5658 */             GuiStreamUnavailable.func_152321_a(this.currentScreen);
/*      */           } 
/* 5660 */         } else if (i == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
/* 5661 */           if (getTwitchStream().isBroadcasting()) {
/* 5662 */             if (getTwitchStream().isPaused()) {
/* 5663 */               getTwitchStream().unpause();
/*      */             } else {
/* 5665 */               getTwitchStream().pause();
/*      */             } 
/*      */           }
/* 5668 */         } else if (i == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
/* 5669 */           if (getTwitchStream().isBroadcasting()) {
/* 5670 */             getTwitchStream().requestCommercial();
/*      */           }
/* 5672 */         } else if (i == this.gameSettings.keyBindsHotbar.getKeyCode()) {
/* 5673 */           this.stream.muteMicrophone(true);
/* 5674 */         } else if (i == this.gameSettings.keyBindSpectatorOutlines.getKeyCode()) {
/* 5675 */           toggleFullscreen();
/* 5676 */         } else if (i == this.gameSettings.keyBindTogglePerspective.getKeyCode()) {
/* 5677 */           this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
/*      */         } 
/* 5679 */       } else if (i == this.gameSettings.keyBindsHotbar.getKeyCode()) {
/* 5680 */         this.stream.muteMicrophone(false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 5686 */     return this.sessionService;
/*      */   }
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 5690 */     return this.skinManager;
/*      */   }
/*      */   
/*      */   public Entity getRenderViewEntity() {
/* 5694 */     return this.renderViewEntity;
/*      */   }
/*      */   
/*      */   public void setRenderViewEntity(Entity viewingEntity) {
/* 5698 */     this.renderViewEntity = viewingEntity;
/* 5699 */     this.entityRenderer.loadEntityShader(viewingEntity);
/*      */   }
/*      */   
/*      */   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
/* 5703 */     Validate.notNull(callableToSchedule);
/* 5704 */     if (!isCallingFromMinecraftThread()) {
/* 5705 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
/* 5706 */       synchronized (this.scheduledTasks) {
/* 5707 */         this.scheduledTasks.add(listenablefuturetask);
/*      */ 
/*      */         
/* 5710 */         ListenableFutureTask<V> listenableFutureTask3 = listenablefuturetask, listenableFutureTask4 = listenableFutureTask3, listenableFutureTask2 = listenableFutureTask4;
/*      */         
/* 5712 */         return (ListenableFuture<V>)listenableFutureTask4;
/*      */       } 
/*      */     } 
/*      */     try {
/* 5716 */       return Futures.immediateFuture(callableToSchedule.call());
/* 5717 */     } catch (Exception exception) {
/* 5718 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 5724 */     Validate.notNull(runnableToSchedule);
/* 5725 */     return addScheduledTask(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 5730 */     return (Thread.currentThread() == this.mcThread);
/*      */   }
/*      */   
/*      */   public BlockRendererDispatcher getBlockRendererDispatcher() {
/* 5734 */     return this.blockRenderDispatcher;
/*      */   }
/*      */   
/*      */   public RenderManager getRenderManager() {
/* 5738 */     return this.renderManager;
/*      */   }
/*      */   
/*      */   public RenderItem getRenderItem() {
/* 5742 */     return this.renderItem;
/*      */   }
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 5746 */     return this.itemRenderer;
/*      */   }
/*      */   
/*      */   public static int getDebugFPS() {
/* 5750 */     return debugFPS;
/*      */   }
/*      */   
/*      */   public FrameTimer getFrameTimer() {
/* 5754 */     return this.frameTimer;
/*      */   }
/*      */   
/*      */   public static Map<String, String> getSessionInfo() {
/* 5758 */     Map<String, String> map = Maps.newHashMap();
/* 5759 */     map.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
/* 5760 */     map.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
/* 5761 */     map.put("X-Minecraft-Version", "1.8.9");
/* 5762 */     return map;
/*      */   }
/*      */   
/*      */   public boolean isConnectedToRealms() {
/* 5766 */     return this.connectedToRealms;
/*      */   }
/*      */   
/*      */   public void setConnectedToRealms(boolean isConnected) {
/* 5770 */     this.connectedToRealms = isConnected;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */