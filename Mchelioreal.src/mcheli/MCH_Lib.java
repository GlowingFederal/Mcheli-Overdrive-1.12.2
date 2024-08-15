/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import mcheli.wrapper.W_Vec3;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Lib
/*     */ {
/*  36 */   private static HashMap<String, Material> mapMaterial = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static void init() {
/*  40 */     mapMaterial.clear();
/*  41 */     mapMaterial.put("air", Material.field_151579_a);
/*  42 */     mapMaterial.put("grass", Material.field_151577_b);
/*  43 */     mapMaterial.put("ground", Material.field_151578_c);
/*  44 */     mapMaterial.put("wood", Material.field_151575_d);
/*  45 */     mapMaterial.put("rock", Material.field_151576_e);
/*  46 */     mapMaterial.put("iron", Material.field_151573_f);
/*  47 */     mapMaterial.put("anvil", Material.field_151574_g);
/*  48 */     mapMaterial.put("water", Material.field_151586_h);
/*  49 */     mapMaterial.put("lava", Material.field_151587_i);
/*  50 */     mapMaterial.put("leaves", Material.field_151584_j);
/*  51 */     mapMaterial.put("plants", Material.field_151585_k);
/*  52 */     mapMaterial.put("vine", Material.field_151582_l);
/*  53 */     mapMaterial.put("sponge", Material.field_151583_m);
/*  54 */     mapMaterial.put("cloth", Material.field_151580_n);
/*  55 */     mapMaterial.put("fire", Material.field_151581_o);
/*  56 */     mapMaterial.put("sand", Material.field_151595_p);
/*  57 */     mapMaterial.put("circuits", Material.field_151594_q);
/*  58 */     mapMaterial.put("carpet", Material.field_151593_r);
/*  59 */     mapMaterial.put("glass", Material.field_151592_s);
/*  60 */     mapMaterial.put("redstoneLight", Material.field_151591_t);
/*  61 */     mapMaterial.put("tnt", Material.field_151590_u);
/*  62 */     mapMaterial.put("coral", Material.field_151589_v);
/*  63 */     mapMaterial.put("ice", Material.field_151588_w);
/*  64 */     mapMaterial.put("packedIce", Material.field_151598_x);
/*  65 */     mapMaterial.put("snow", Material.field_151597_y);
/*  66 */     mapMaterial.put("craftedSnow", Material.field_151596_z);
/*  67 */     mapMaterial.put("cactus", Material.field_151570_A);
/*  68 */     mapMaterial.put("clay", Material.field_151571_B);
/*  69 */     mapMaterial.put("gourd", Material.field_151572_C);
/*  70 */     mapMaterial.put("dragonEgg", Material.field_151566_D);
/*  71 */     mapMaterial.put("portal", Material.field_151567_E);
/*  72 */     mapMaterial.put("cake", Material.field_151568_F);
/*  73 */     mapMaterial.put("web", Material.field_151569_G);
/*  74 */     mapMaterial.put("piston", Material.field_76233_E);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Material getMaterialFromName(String name) {
/*  79 */     if (mapMaterial.containsKey(name))
/*     */     {
/*  81 */       return mapMaterial.get(name);
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d calculateFaceNormal(Vec3d[] vertices) {
/*  88 */     Vec3d v1 = new Vec3d((vertices[1]).field_72450_a - (vertices[0]).field_72450_a, (vertices[1]).field_72448_b - (vertices[0]).field_72448_b, (vertices[1]).field_72449_c - (vertices[0]).field_72449_c);
/*     */     
/*  90 */     Vec3d v2 = new Vec3d((vertices[2]).field_72450_a - (vertices[0]).field_72450_a, (vertices[2]).field_72448_b - (vertices[0]).field_72448_b, (vertices[2]).field_72449_c - (vertices[0]).field_72449_c);
/*     */ 
/*     */     
/*  93 */     return v1.func_72431_c(v2).func_72432_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String s) {
/*  98 */     return (s == null) ? 0.0D : Double.parseDouble(s.replace(',', '.'));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float RNG(float a, float min, float max) {
/* 103 */     return (a > max) ? max : ((a < min) ? min : a);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double RNG(double a, double min, double max) {
/* 108 */     return (a > max) ? max : ((a < min) ? min : a);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float smooth(float rot, float prevRot, float tick) {
/* 113 */     return prevRot + (rot - prevRot) * tick;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float smoothRot(float rot, float prevRot, float tick) {
/* 118 */     if (rot - prevRot < -180.0F) {
/*     */       
/* 120 */       prevRot -= 360.0F;
/*     */     }
/* 122 */     else if (prevRot - rot < -180.0F) {
/*     */       
/* 124 */       prevRot += 360.0F;
/*     */     } 
/* 126 */     return prevRot + (rot - prevRot) * tick;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRotateDiff(double base, double target) {
/* 131 */     base = getRotate360(base);
/* 132 */     target = getRotate360(target);
/*     */     
/* 134 */     if (target - base < -180.0D) {
/*     */       
/* 136 */       target += 360.0D;
/*     */     }
/* 138 */     else if (target - base > 180.0D) {
/*     */       
/* 140 */       base += 360.0D;
/*     */     } 
/*     */     
/* 143 */     return target - base;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getPosAngle(double tx, double tz, double cx, double cz) {
/* 148 */     double length_A = Math.sqrt(tx * tx + tz * tz);
/* 149 */     double length_B = Math.sqrt(cx * cx + cz * cz);
/*     */     
/* 151 */     double cos_sita = (tx * cx + tz * cz) / length_A * length_B;
/*     */     
/* 153 */     double sita = Math.acos(cos_sita);
/*     */     
/* 155 */     return (float)(sita * 180.0D / Math.PI);
/*     */   }
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
/*     */   public static void applyEntityHurtResistantTimeConfig(Entity entity) {
/* 199 */     if (entity instanceof EntityLivingBase) {
/*     */       
/* 201 */       EntityLivingBase elb = (EntityLivingBase)entity;
/* 202 */       double h_time = MCH_Config.HurtResistantTime.prmDouble * elb.field_70172_ad;
/* 203 */       elb.field_70172_ad = (int)h_time;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int round(double d) {
/* 209 */     return (int)(d + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d Rot2Vec3(float yaw, float pitch) {
/* 214 */     return new Vec3d((-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)), 
/* 215 */         -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F), (
/* 216 */         MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch) {
/* 221 */     Vec3d v = new Vec3d(x, y, z);
/*     */ 
/*     */     
/* 224 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 225 */     v = v.func_178785_b(yaw / 180.0F * 3.1415927F);
/* 226 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d RotVec3(double x, double y, double z, float yaw, float pitch, float roll) {
/* 231 */     Vec3d v = new Vec3d(x, y, z);
/*     */ 
/*     */ 
/*     */     
/* 235 */     v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
/* 236 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 237 */     v = v.func_178785_b(yaw / 180.0F * 3.1415927F);
/* 238 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch) {
/* 243 */     Vec3d v = new Vec3d(vin.field_72450_a, vin.field_72448_b, vin.field_72449_c);
/*     */ 
/*     */     
/* 246 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 247 */     v = v.func_178785_b(yaw / 180.0F * 3.1415927F);
/* 248 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d RotVec3(Vec3d vin, float yaw, float pitch, float roll) {
/* 253 */     Vec3d v = new Vec3d(vin.field_72450_a, vin.field_72448_b, vin.field_72449_c);
/*     */ 
/*     */ 
/*     */     
/* 257 */     v = W_Vec3.rotateRoll(roll / 180.0F * 3.1415927F, v);
/* 258 */     v = v.func_178789_a(pitch / 180.0F * 3.1415927F);
/* 259 */     v = v.func_178785_b(yaw / 180.0F * 3.1415927F);
/* 260 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d _Rot2Vec3(float yaw, float pitch, float roll) {
/* 265 */     return new Vec3d((-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)), 
/* 266 */         -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F), (
/* 267 */         MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getRotate360(double r) {
/* 272 */     r %= 360.0D;
/* 273 */     return (r >= 0.0D) ? r : (r + 360.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void Log(String format, Object... data) {
/* 278 */     String side = MCH_MOD.proxy.isRemote() ? "[Client]" : "[Server]";
/*     */     
/* 280 */     System.out.printf("[" + getTime() + "][mcheli]" + side + " " + format + "\n", data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void Log(World world, String format, Object... data) {
/* 285 */     if (world != null) {
/* 286 */       Log((world.field_72995_K ? "[ClientWorld]" : "[ServerWorld]") + " " + format, data);
/*     */     } else {
/* 288 */       Log("[UnknownWorld]" + format, data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void Log(Entity entity, String format, Object... data) {
/* 293 */     if (entity != null) {
/* 294 */       Log(entity.field_70170_p, format, data);
/*     */     } else {
/*     */       
/* 297 */       Log((World)null, format, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void DbgLog(boolean isRemote, String format, Object... data) {
/* 303 */     if (MCH_Config.DebugLog)
/*     */     {
/*     */       
/* 306 */       if (isRemote) {
/*     */ 
/*     */         
/* 309 */         if (getClientPlayer() instanceof net.minecraft.entity.player.EntityPlayer);
/*     */ 
/*     */ 
/*     */         
/* 313 */         System.out.println(String.format(format, data));
/*     */       }
/*     */       else {
/*     */         
/* 317 */         System.out.println(String.format(format, data));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void DbgLog(World w, String format, Object... data) {
/* 324 */     DbgLog(w.field_72995_K, format, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTime() {
/* 329 */     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
/*     */     
/* 331 */     return sdf.format(new Date());
/*     */   }
/*     */   
/* 334 */   public static final String[] AZIMUTH_8 = new String[] { "S", "SW", "W", "NW", "N", "NE", "E", "SE" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 339 */   public static final int AZIMUTH_8_ANG = 360 / AZIMUTH_8.length;
/*     */ 
/*     */   
/*     */   public static String getAzimuthStr8(int dir) {
/* 343 */     dir %= 360;
/* 344 */     if (dir < 0)
/* 345 */       dir += 360; 
/* 346 */     dir /= AZIMUTH_8_ANG;
/* 347 */     return AZIMUTH_8[dir];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void rotatePoints(double[] points, float r) {
/* 352 */     r = r / 180.0F * 3.1415927F;
/* 353 */     for (int i = 0; i + 1 < points.length; i += 2) {
/*     */       
/* 355 */       double x = points[i + 0];
/* 356 */       double y = points[i + 1];
/* 357 */       points[i + 0] = x * MathHelper.func_76134_b(r) - y * MathHelper.func_76126_a(r);
/* 358 */       points[i + 1] = x * MathHelper.func_76126_a(r) + y * MathHelper.func_76134_b(r);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void rotatePoints(ArrayList<MCH_Vector2> points, float r) {
/* 364 */     r = r / 180.0F * 3.1415927F;
/* 365 */     for (int i = 0; i + 1 < points.size(); i += 2) {
/*     */       
/* 367 */       double x = ((MCH_Vector2)points.get(i + 0)).x;
/* 368 */       double y = ((MCH_Vector2)points.get(i + 0)).y;
/* 369 */       ((MCH_Vector2)points.get(i + 0)).x = x * MathHelper.func_76134_b(r) - y * MathHelper.func_76126_a(r);
/* 370 */       ((MCH_Vector2)points.get(i + 0)).y = x * MathHelper.func_76126_a(r) + y * MathHelper.func_76134_b(r);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] listupFileNames(String path) {
/* 376 */     File dir = new File(path);
/* 377 */     return dir.list();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBlockInWater(World w, int x, int y, int z) {
/* 382 */     int[][] offset = { { 0, -1, 0 }, { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 } };
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
/* 407 */     if (y <= 0)
/* 408 */       return false; 
/* 409 */     for (int[] o : offset) {
/*     */       
/* 411 */       if (W_WorldFunc.isBlockWater(w, x + o[0], y + o[1], z + o[2]))
/*     */       {
/* 413 */         return true;
/*     */       }
/*     */     } 
/* 416 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBlockIdY(World w, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
/* 422 */     Block block = getBlockY(w, posX, posY, posZ, size, lenY, canColliableOnly);
/* 423 */     if (block == null)
/* 424 */       return 0; 
/* 425 */     return W_Block.func_149682_b(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlockIdY(Entity entity, int size, int lenY) {
/* 430 */     return getBlockIdY(entity, size, lenY, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlockIdY(Entity entity, int size, int lenY, boolean canColliableOnly) {
/* 435 */     Block block = getBlockY(entity, size, lenY, canColliableOnly);
/* 436 */     if (block == null)
/* 437 */       return 0; 
/* 438 */     return W_Block.func_149682_b(block);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block getBlockY(Entity entity, int size, int lenY, boolean canColliableOnly) {
/* 443 */     return getBlockY(entity.field_70170_p, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, size, lenY, canColliableOnly);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block getBlockY(World world, Vec3d pos, int size, int lenY, boolean canColliableOnly) {
/* 448 */     return getBlockY(world, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, size, lenY, canColliableOnly);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlockY(World world, double posX, double posY, double posZ, int size, int lenY, boolean canColliableOnly) {
/* 454 */     if (lenY == 0)
/*     */     {
/* 456 */       return W_Blocks.field_150350_a;
/*     */     }
/*     */     
/* 459 */     int px = (int)(posX + 0.5D);
/* 460 */     int py = (int)(posY + 0.5D);
/* 461 */     int pz = (int)(posZ + 0.5D);
/*     */     
/* 463 */     int cntY = (lenY > 0) ? lenY : -lenY;
/*     */     
/* 465 */     for (int y = 0; y < cntY; y++) {
/*     */       
/* 467 */       if (py + y < 0 || py + y > 255)
/*     */       {
/* 469 */         return W_Blocks.field_150350_a;
/*     */       }
/* 471 */       for (int x = -size / 2; x <= size / 2; x++) {
/*     */         
/* 473 */         for (int z = -size / 2; z <= size / 2; z++) {
/*     */ 
/*     */           
/* 476 */           IBlockState iblockstate = world.func_180495_p(new BlockPos(px + x, py + ((lenY > 0) ? y : -y), pz + z));
/* 477 */           Block block = W_WorldFunc.getBlock(world, px + x, py + ((lenY > 0) ? y : -y), pz + z);
/*     */           
/* 479 */           if (block != null && block != W_Blocks.field_150350_a)
/*     */           {
/* 481 */             if (canColliableOnly) {
/*     */ 
/*     */               
/* 484 */               if (block.func_176209_a(iblockstate, true))
/*     */               {
/* 486 */                 return block;
/*     */               
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 492 */               return block;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 499 */     return W_Blocks.field_150350_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d getYawPitchFromVec(Vec3d v) {
/* 504 */     return getYawPitchFromVec(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d getYawPitchFromVec(double x, double y, double z) {
/* 509 */     double p = MathHelper.func_76133_a(x * x + z * z);
/* 510 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI);
/* 511 */     float roll = (float)(Math.atan2(y, p) * 180.0D / Math.PI);
/* 512 */     return new Vec3d(0.0D, yaw, roll);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getAlpha(int argb) {
/* 517 */     return (argb >> 24) / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRed(int argb) {
/* 522 */     return (argb >> 16 & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getGreen(int argb) {
/* 527 */     return (argb >> 8 & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getBlue(int argb) {
/* 532 */     return (argb & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableFirstPersonItemRender() {
/* 537 */     switch (MCH_Config.DisableItemRender.prmInt) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 543 */         MCH_ItemRendererDummy.disableDummyItemRenderer();
/*     */         break;
/*     */       
/*     */       case 3:
/* 547 */         W_Reflection.restoreCameraZoom();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableFirstPersonItemRender(ItemStack itemStack) {
/* 554 */     if (itemStack.func_190926_b() && itemStack.func_77973_b() instanceof net.minecraft.item.ItemMapBase)
/*     */     {
/* 556 */       if (!(W_McClient.getRenderEntity() instanceof MCH_ViewEntityDummy)) {
/*     */         return;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 562 */     disableFirstPersonItemRender();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableFirstPersonItemRender() {
/* 567 */     switch (MCH_Config.DisableItemRender.prmInt) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 572 */         W_Reflection.setItemRendererMainHand(new ItemStack((Item)MCH_MOD.invisibleItem));
/*     */         break;
/*     */       
/*     */       case 2:
/* 576 */         MCH_ItemRendererDummy.enableDummyItemRenderer();
/*     */         break;
/*     */       
/*     */       case 3:
/* 580 */         W_Reflection.setCameraZoom(1.01F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Entity getClientPlayer() {
/* 587 */     return MCH_MOD.proxy.getClientPlayer();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRenderViewEntity(EntityLivingBase entity) {
/* 592 */     if (MCH_Config.ReplaceRenderViewEntity.prmBool)
/*     */     {
/* 594 */       W_McClient.setRenderEntity(entity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Entity getRenderViewEntity() {
/* 600 */     return W_McClient.getRenderEntity();
/*     */   }
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
/*     */   
/*     */   public static boolean isFlansModEntity(Entity entity) {
/* 671 */     if (entity == null)
/* 672 */       return false; 
/* 673 */     String className = entity.getClass().getName();
/* 674 */     return (entity != null && (className.indexOf("EntityVehicle") >= 0 || className.indexOf("EntityPlane") >= 0 || className
/* 675 */       .indexOf("EntityMecha") >= 0 || className.indexOf("EntityAAGun") >= 0));
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Lib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */