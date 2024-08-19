package mcheli;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Reflection;
import mcheli.wrapper.W_Vec3;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_Lib {
  private static HashMap<String, Material> mapMaterial = new HashMap<>();
  
  public static void init() {
    mapMaterial.clear();
    mapMaterial.put("air", Material.AIR);
    mapMaterial.put("grass", Material.GRASS);
    mapMaterial.put("ground", Material.GROUND);
    mapMaterial.put("wood", Material.WOOD);
    mapMaterial.put("rock", Material.ROCK);
    mapMaterial.put("iron", Material.IRON);
    mapMaterial.put("anvil", Material.ANVIL);
    mapMaterial.put("water", Material.WATER);
    mapMaterial.put("lava", Material.LAVA);
    mapMaterial.put("leaves", Material.LEAVES);
    mapMaterial.put("plants", Material.PLANTS);
    mapMaterial.put("vine", Material.VINE);
    mapMaterial.put("sponge", Material.SPONGE);
    mapMaterial.put("cloth", Material.CLOTH);
    mapMaterial.put("fire", Material.FIRE);
    mapMaterial.put("sand", Material.SAND);
    mapMaterial.put("circuits", Material.CIRCUITS);
    mapMaterial.put("carpet", Material.CARPET);
    mapMaterial.put("glass", Material.GLASS);
    mapMaterial.put("redstoneLight", Material.REDSTONE_LIGHT);
    mapMaterial.put("tnt", Material.TNT);
    mapMaterial.put("coral", Material.CORAL);
    mapMaterial.put("ice", Material.ICE);
    mapMaterial.put("packedIce", Material.PACKED_ICE);
    mapMaterial.put("snow", Material.SNOW);
    mapMaterial.put("craftedSnow", Material.CRAFTED_SNOW);
    mapMaterial.put("cactus", Material.CACTUS);
    mapMaterial.put("clay", Material.CLAY);
    mapMaterial.put("gourd", Material.GOURD);
    mapMaterial.put("dragonEgg", Material.DRAGON_EGG);
    mapMaterial.put("portal", Material.PORTAL);
    mapMaterial.put("cake", Material.CAKE);
    mapMaterial.put("web", Material.WEB);
    mapMaterial.put("piston", Material.PISTON);
  }
  
  public static Material getMaterialFromName(String name) {
    if (mapMaterial.containsKey(name))
      return mapMaterial.get(name); 
    return null;
  }
  
  public static Vec3d calculateFaceNormal(Vec3d[] vertices) {
    Vec3d v1 = new Vec3d((vertices[1]).xCoord - (vertices[0]).xCoord, (vertices[1]).yCoord - (vertices[0]).yCoord, (vertices[1]).zCoord - (vertices[0]).zCoord);
    Vec3d v2 = new Vec3d((vertices[2]).xCoord - (vertices[0]).xCoord, (vertices[2]).yCoord - (vertices[0]).yCoord, (vertices[2]).zCoord - (vertices[0]).zCoord);
    return v1.crossProduct(v2).normalize();
  }
  
  public static double parseDouble(String s) {
    return (s == null) ? 0.0D : Double.parseDouble(s.replace(',', '.'));
  }
  
  public static float RNG(float a, float min, float max) {
    return (a > max) ? max : ((a < min) ? min : a);
  }
  
  public static double RNG(double a, double min, double max) {
    return (a > max) ? max : ((a < min) ? min : a);
  }
  
  public static float smooth(float rot, float prevRot, float tick) {
    return prevRot + (rot - prevRot) * tick;
  }
  
  public static float smoothRot(float rot, float prevRot, float tick) {
    if (rot - prevRot < -180.0F) {
      prevRot -= 360.0F;
    } else if (prevRot - rot < -180.0F) {
      prevRot += 360.0F;
    } 
    return prevRot + (rot - prevRot) * tick;
  }
  
  public static double getRotateDiff(double base, double target) {
    base = getRotate360(base);
    target = getRotate360(target);
    if (target - base < -180.0D) {
      target += 360.0D;
    } else if (target - base > 180.0D) {
      base += 360.0D;
    } 
    return target - base;
  }
  
  public static float getPosAngle(double tx, double tz, double cx, double cz) {
    double length_A = Math.sqrt(tx * tx + tz * tz);
    double length_B = Math.sqrt(cx * cx + cz * cz);
    double cos_sita = (tx * cx + tz * cz) / length_A * length_B;
    double sita = Math.acos(cos_sita);
    return (float)(sita * 180.0D / Math.PI);
  }
  
  public static void applyEntityHurtResistantTimeConfig(Entity entity) {
    if (entity instanceof EntityLivingBase) {
      EntityLivingBase elb = (EntityLivingBase)entity;
      double h_time = MCH_Config.HurtResistantTime.prmDouble * elb.hurtResistantTime;
      elb.hurtResistantTime = (int)h_time;
    } 
  }
  
  public static int round(double d) {
    return (int)(d + 0.5D);
  }
  
  public static Vec3d Rot2Vec3(float yaw, float pitch) {
    return new Vec3d((-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F)), 
        -MathHelper.sin(pitch / 180.0F * 3.1415927F), (
        MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F)));
  }
  
  public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch) {
    Vec3d v = new Vec3d(x, y, z);
    v = v.rotatePitch(pitch / 180.0F * 3.1415927F);
    v = v.rotateYaw(yaw / 180.0F * 3.1415927F);
    return v;
  }
  
  public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch, float roll) {
    Vec3d v = new Vec3d(x, y, z);
    v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
    v = v.rotatePitch(pitch / 180.0F * 3.1415927F);
    v = v.rotateYaw(yaw / 180.0F * 3.1415927F);
    return v;
  }
  
  public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch) {
    Vec3d v = new Vec3d(vin.xCoord, vin.yCoord, vin.zCoord);
    v = v.rotatePitch(pitch / 180.0F * 3.1415927F);
    v = v.rotateYaw(yaw / 180.0F * 3.1415927F);
    return v;
  }
  
  public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch, float roll) {
    Vec3d v = new Vec3d(vin.xCoord, vin.yCoord, vin.zCoord);
    v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
    v = v.rotatePitch(pitch / 180.0F * 3.1415927F);
    v = v.rotateYaw(yaw / 180.0F * 3.1415927F);
    return v;
  }
  
  public static Vec3d _Rot2Vec3(float yaw, float pitch, float roll) {
    return new Vec3d((-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F)), 
        -MathHelper.sin(pitch / 180.0F * 3.1415927F), (
        MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F)));
  }
  
  public static double getRotate360(double r) {
    r %= 360.0D;
    return (r >= 0.0D) ? r : (r + 360.0D);
  }
  
  public static void Log(String format, Object... data) {
    String side = MCH_MOD.proxy.isRemote() ? "[Client]" : "[Server]";
    System.out.printf("[" + getTime() + "][mcheli]" + side + " " + format + "\n", data);
  }
  
  public static void Log(World world, String format, Object... data) {
    if (world != null) {
      Log((world.isRemote ? "[ClientWorld]" : "[ServerWorld]") + " " + format, data);
    } else {
      Log("[UnknownWorld]" + format, data);
    } 
  }
  
  public static void Log(Entity entity, String format, Object... data) {
    if (entity != null) {
      Log(entity.world, format, data);
    } else {
      Log((World)null, format, data);
    } 
  }
  
  public static void DbgLog(boolean isRemote, String format, Object... data) {
    if (MCH_Config.DebugLog)
      if (isRemote) {
        if (getClientPlayer() instanceof net.minecraft.entity.player.EntityPlayer);
        System.out.println(String.format(format, data));
      } else {
        System.out.println(String.format(format, data));
      }  
  }
  
  public static void DbgLog(World w, String format, Object... data) {
    DbgLog(w.isRemote, format, data);
  }
  
  public static String getTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    return sdf.format(new Date());
  }
  
  public static final String[] AZIMUTH_8 = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
  
  public static final int AZIMUTH_8_ANG = 360 / AZIMUTH_8.length;
  
  public static String getAzimuthStr8(int dir) {
    dir %= 360;
    if (dir < 0)
      dir += 360; 
    dir /= AZIMUTH_8_ANG;
    return AZIMUTH_8[dir];
  }
  
  public static void rotatePoints(double[] points, float r) {
    r = r / 180.0F * 3.1415927F;
    for (int i = 0; i + 1 < points.length; i += 2) {
      double x = points[i + 0];
      double y = points[i + 1];
      points[i + 0] = x * MathHelper.cos(r) - y * MathHelper.sin(r);
      points[i + 1] = x * MathHelper.sin(r) + y * MathHelper.cos(r);
    } 
  }
  
  public static void rotatePoints(ArrayList<MCH_Vector2> points, float r) {
    r = r / 180.0F * 3.1415927F;
    for (int i = 0; i + 1 < points.size(); i += 2) {
      double x = ((MCH_Vector2)points.get(i + 0)).x;
      double y = ((MCH_Vector2)points.get(i + 0)).y;
      ((MCH_Vector2)points.get(i + 0)).x = x * MathHelper.cos(r) - y * MathHelper.sin(r);
      ((MCH_Vector2)points.get(i + 0)).y = x * MathHelper.sin(r) + y * MathHelper.cos(r);
    } 
  }
  
  public static String[] listupFileNames(String path) {
    File dir = new File(path);
    return dir.list();
  }
  
  public static boolean isBlockInWater(World w, int x, int y, int z) {
    int[][] offset = { { 0, -1, 0 }, { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 } };
    if (y <= 0)
      return false; 
    for (int[] o : offset) {
      if (W_WorldFunc.isBlockWater(w, x + o[0], y + o[1], z + o[2]))
        return true; 
    } 
    return false;
  }
  
  public static int getBlockIdY(World w, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
    Block block = getBlockY(w, posX, posY, posZ, size, lenY, canColliableOnly);
    if (block == null)
      return 0; 
    return W_Block.getIdFromBlock(block);
  }
  
  public static int getBlockIdY(Entity entity, int size, int lenY) {
    return getBlockIdY(entity, size, lenY, true);
  }
  
  public static int getBlockIdY(Entity entity, int size, int lenY, boolean canColliableOnly) {
    Block block = getBlockY(entity, size, lenY, canColliableOnly);
    if (block == null)
      return 0; 
    return W_Block.getIdFromBlock(block);
  }
  
  public static Block getBlockY(Entity entity, int size, int lenY, boolean canColliableOnly) {
    return getBlockY(entity.world, entity.posX, entity.posY, entity.posZ, size, lenY, canColliableOnly);
  }
  
  public static Block getBlockY(World world, Vec3d pos, int size, int lenY, boolean canColliableOnly) {
    return getBlockY(world, pos.xCoord, pos.yCoord, pos.zCoord, size, lenY, canColliableOnly);
  }
  
  public static Block getBlockY(World world, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
    if (lenY == 0)
      return W_Blocks.AIR; 
    int px = (int)(posX + 0.5D);
    int py = (int)(posY + 0.5D);
    int pz = (int)(posZ + 0.5D);
    int cntY = (lenY > 0) ? lenY : -lenY;
    for (int y = 0; y < cntY; y++) {
      if (py + y < 0 || py + y > 255)
        return W_Blocks.AIR; 
      for (int x = -size / 2; x <= size / 2; x++) {
        for (int z = -size / 2; z <= size / 2; z++) {
          IBlockState iblockstate = world.getBlockState(new BlockPos(px + x, py + ((lenY > 0) ? y : -y), pz + z));
          Block block = W_WorldFunc.getBlock(world, px + x, py + ((lenY > 0) ? y : -y), pz + z);
          if (block != null && block != W_Blocks.AIR)
            if (canColliableOnly) {
              if (block.canCollideCheck(iblockstate, true))
                return block; 
            } else {
              return block;
            }  
        } 
      } 
    } 
    return W_Blocks.AIR;
  }
  
  public static Vec3d getYawPitchFromVec(Vec3d v) {
    return getYawPitchFromVec(v.xCoord, v.yCoord, v.zCoord);
  }
  
  public static Vec3d getYawPitchFromVec(double x, double y, double z) {
    double p = MathHelper.sqrt(x * x + z * z);
    float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI);
    float roll = (float)(Math.atan2(y, p) * 180.0D / Math.PI);
    return new Vec3d(0.0D, yaw, roll);
  }
  
  public static float getAlpha(int argb) {
    return (argb >> 24) / 255.0F;
  }
  
  public static float getRed(int argb) {
    return (argb >> 16 & 0xFF) / 255.0F;
  }
  
  public static float getGreen(int argb) {
    return (argb >> 8 & 0xFF) / 255.0F;
  }
  
  public static float getBlue(int argb) {
    return (argb & 0xFF) / 255.0F;
  }
  
  public static void enableFirstPersonItemRender() {
    switch (MCH_Config.DisableItemRender.prmInt) {
      case 2:
        MCH_ItemRendererDummy.disableDummyItemRenderer();
        break;
      case 3:
        W_Reflection.restoreCameraZoom();
        break;
    } 
  }
  
  public static void disableFirstPersonItemRender(ItemStack itemStack) {
    if (itemStack.func_190926_b() && itemStack.getItem() instanceof net.minecraft.item.ItemMapBase)
      if (!(W_McClient.getRenderEntity() instanceof MCH_ViewEntityDummy))
        return;  
    disableFirstPersonItemRender();
  }
  
  public static void disableFirstPersonItemRender() {
    switch (MCH_Config.DisableItemRender.prmInt) {
      case 1:
        W_Reflection.setItemRendererMainHand(new ItemStack((Item)MCH_MOD.invisibleItem));
        break;
      case 2:
        MCH_ItemRendererDummy.enableDummyItemRenderer();
        break;
      case 3:
        W_Reflection.setCameraZoom(1.01F);
        break;
    } 
  }
  
  public static Entity getClientPlayer() {
    return MCH_MOD.proxy.getClientPlayer();
  }
  
  public static void setRenderViewEntity(EntityLivingBase entity) {
    if (MCH_Config.ReplaceRenderViewEntity.prmBool)
      W_McClient.setRenderEntity(entity); 
  }
  
  public static Entity getRenderViewEntity() {
    return W_McClient.getRenderEntity();
  }
  
  public static boolean isFlansModEntity(Entity entity) {
    if (entity == null)
      return false; 
    String className = entity.getClass().getName();
    return (entity != null && (className.indexOf("EntityVehicle") >= 0 || className.indexOf("EntityPlane") >= 0 || className
      .indexOf("EntityMecha") >= 0 || className.indexOf("EntityAAGun") >= 0));
  }
}
