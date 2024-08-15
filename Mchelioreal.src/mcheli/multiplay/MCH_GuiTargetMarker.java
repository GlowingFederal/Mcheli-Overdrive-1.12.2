/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_MarkEntityPos;
/*     */ import mcheli.MCH_ServerSettings;
/*     */ import mcheli.__helper.entity.ITargetMarkerObject;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiTargetMarker
/*     */   extends MCH_Gui
/*     */ {
/*  42 */   private static FloatBuffer matModel = BufferUtils.createFloatBuffer(16);
/*  43 */   private static FloatBuffer matProjection = BufferUtils.createFloatBuffer(16);
/*  44 */   private static IntBuffer matViewport = BufferUtils.createIntBuffer(16);
/*  45 */   private static ArrayList<MCH_MarkEntityPos> entityPos = new ArrayList<>();
/*  46 */   private static HashMap<Integer, Integer> spotedEntity = new HashMap<>();
/*     */   
/*     */   private static Minecraft s_minecraft;
/*     */   
/*     */   public MCH_GuiTargetMarker(Minecraft minecraft) {
/*  51 */     super(minecraft);
/*  52 */     s_minecraft = minecraft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  58 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  70 */     return (player != null && player.field_70170_p != null);
/*     */   }
/*     */   
/*  73 */   private static int spotedEntityCountdown = 0;
/*     */ 
/*     */   
/*     */   public static void onClientTick() {
/*  77 */     if (!Minecraft.func_71410_x().func_147113_T())
/*     */     {
/*  79 */       spotedEntityCountdown++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (spotedEntityCountdown >= 20) {
/*     */       
/*  86 */       spotedEntityCountdown = 0;
/*     */       
/*  88 */       for (Integer key : spotedEntity.keySet()) {
/*     */         
/*  90 */         int count = ((Integer)spotedEntity.get(key)).intValue();
/*     */         
/*  92 */         if (count > 0)
/*     */         {
/*  94 */           spotedEntity.put(key, Integer.valueOf(count - 1));
/*     */         }
/*     */       } 
/*     */       
/*  98 */       for (Iterator<Integer> i = spotedEntity.values().iterator(); i.hasNext();) {
/*     */         
/* 100 */         if (((Integer)i.next()).intValue() <= 0)
/*     */         {
/* 102 */           i.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSpotedEntity(@Nullable Entity entity) {
/* 110 */     if (entity == null)
/*     */     {
/* 112 */       return false;
/*     */     }
/*     */     
/* 115 */     int entityId = entity.func_145782_y();
/*     */     
/* 117 */     for (Iterator<Integer> i$ = spotedEntity.keySet().iterator(); i$.hasNext(); ) {
/*     */       
/* 119 */       int key = ((Integer)i$.next()).intValue();
/*     */       
/* 121 */       if (key == entityId)
/* 122 */         return true; 
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addSpotedEntity(int entityId, int count) {
/* 129 */     if (spotedEntity.containsKey(Integer.valueOf(entityId))) {
/*     */       
/* 131 */       int now = ((Integer)spotedEntity.get(Integer.valueOf(entityId))).intValue();
/*     */       
/* 133 */       if (count > now)
/*     */       {
/* 135 */         spotedEntity.put(Integer.valueOf(entityId), Integer.valueOf(count));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 140 */       spotedEntity.put(Integer.valueOf(entityId), Integer.valueOf(count));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addMarkEntityPos(int reserve, ITargetMarkerObject target, double x, double y, double z) {
/* 148 */     addMarkEntityPos(reserve, target, x, y, z, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addMarkEntityPos(int reserve, ITargetMarkerObject target, double x, double y, double z, boolean nazo) {
/* 155 */     if (!isEnableEntityMarker()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     MCH_TargetType spotType = MCH_TargetType.NONE;
/* 161 */     EntityPlayerSP entityPlayerSP = s_minecraft.field_71439_g;
/* 162 */     Entity entity = target.getEntity();
/*     */     
/* 164 */     if (entity instanceof MCH_EntityAircraft) {
/*     */       
/* 166 */       MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/*     */       
/* 168 */       if (ac.isMountedEntity((Entity)entityPlayerSP)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 173 */       if (ac.isMountedSameTeamEntity((EntityLivingBase)entityPlayerSP))
/*     */       {
/* 175 */         spotType = MCH_TargetType.SAME_TEAM_PLAYER;
/*     */       }
/*     */     }
/* 178 */     else if (entity instanceof EntityPlayer) {
/*     */       
/* 180 */       if (entity == entityPlayerSP || entity.func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat || entity
/* 181 */         .func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 186 */       if (entityPlayerSP.func_96124_cp() != null && entityPlayerSP.func_184191_r(entity))
/*     */       {
/* 188 */         spotType = MCH_TargetType.SAME_TEAM_PLAYER;
/*     */       }
/*     */     } 
/*     */     
/* 192 */     if (spotType == MCH_TargetType.NONE && isSpotedEntity(entity))
/*     */     {
/* 194 */       spotType = MCH_Multiplay.canSpotEntity((Entity)entityPlayerSP, ((EntityPlayer)entityPlayerSP).field_70165_t, ((EntityPlayer)entityPlayerSP).field_70163_u + entityPlayerSP
/* 195 */           .func_70047_e(), ((EntityPlayer)entityPlayerSP).field_70161_v, entity, false);
/*     */     }
/*     */     
/* 198 */     if (reserve == 100)
/*     */     {
/* 200 */       spotType = MCH_TargetType.POINT;
/*     */     }
/*     */     
/* 203 */     if (spotType != MCH_TargetType.NONE) {
/*     */ 
/*     */       
/* 206 */       MCH_MarkEntityPos e = new MCH_MarkEntityPos(spotType.ordinal(), target);
/*     */       
/* 208 */       GL11.glGetFloat(2982, matModel);
/* 209 */       GL11.glGetFloat(2983, matProjection);
/* 210 */       GL11.glGetInteger(2978, matViewport);
/*     */       
/* 212 */       if (nazo) {
/*     */         
/* 214 */         GLU.gluProject((float)z, (float)y, (float)x, matModel, matProjection, matViewport, e.pos);
/* 215 */         float yy = e.pos.get(1);
/* 216 */         GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, e.pos);
/* 217 */         e.pos.put(1, yy);
/*     */       }
/*     */       else {
/*     */         
/* 221 */         GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, e.pos);
/*     */       } 
/*     */       
/* 224 */       entityPos.add(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearMarkEntityPos() {
/* 230 */     entityPos.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEnableEntityMarker() {
/* 235 */     return (MCH_Config.DisplayEntityMarker.prmBool && (
/* 236 */       Minecraft.func_71410_x().func_71356_B() || MCH_ServerSettings.enableEntityMarker) && MCH_Config.EntityMarkerSize.prmDouble > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/* 243 */     GL11.glLineWidth((scaleFactor * 2));
/*     */     
/* 245 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 250 */     GL11.glDisable(3042);
/*     */     
/* 252 */     if (isEnableEntityMarker())
/*     */     {
/* 254 */       drawMark();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void drawMark() {
/* 260 */     int[] COLOR_TABLE = { 0, -808464433, -805371904, -805306624, -822018049, -805351649, -65536, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     int scale = (scaleFactor > 0) ? scaleFactor : 2;
/*     */     
/* 267 */     GL11.glEnable(3042);
/* 268 */     GL11.glDisable(3553);
/* 269 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 271 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 272 */     GL11.glDepthMask(false);
/*     */     
/* 274 */     int DW = this.field_146297_k.field_71443_c;
/*     */     
/* 276 */     int DSW = this.field_146297_k.field_71443_c / scale;
/* 277 */     int DSH = this.field_146297_k.field_71440_d / scale;
/* 278 */     double x = 9999.0D;
/* 279 */     double z = 9999.0D;
/* 280 */     double y = 9999.0D;
/* 281 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 282 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/* 284 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 286 */       if (i == 0)
/*     */       {
/*     */         
/* 289 */         builder.func_181668_a((i == 0) ? 4 : 1, DefaultVertexFormats.field_181706_f);
/*     */       }
/*     */       
/* 292 */       for (MCH_MarkEntityPos e : entityPos) {
/*     */         
/* 294 */         int color = COLOR_TABLE[e.type];
/*     */         
/* 296 */         x = (e.pos.get(0) / scale);
/* 297 */         z = e.pos.get(2);
/* 298 */         y = (e.pos.get(1) / scale);
/*     */         
/* 300 */         if (z < 1.0D) {
/*     */           
/* 302 */           y = DSH - y;
/*     */         }
/* 304 */         else if (x < (DW / 2)) {
/*     */           
/* 306 */           x = 10000.0D;
/*     */         }
/* 308 */         else if (x >= (DW / 2)) {
/*     */           
/* 310 */           x = -10000.0D;
/*     */         } 
/*     */         
/* 313 */         if (i == 0) {
/*     */           
/* 315 */           double size = MCH_Config.EntityMarkerSize.prmDouble;
/*     */           
/* 317 */           if (e.type < MCH_TargetType.POINT.ordinal() && z < 1.0D && x >= 0.0D && x <= DSW && y >= 0.0D && y <= DSH)
/*     */           {
/*     */ 
/*     */             
/* 321 */             drawTriangle1(builder, x, y, size, color);
/*     */           }
/*     */           continue;
/*     */         } 
/* 325 */         if (e.type == MCH_TargetType.POINT.ordinal() && e.getTarget() != null) {
/*     */           
/* 327 */           ITargetMarkerObject target = e.getTarget();
/* 328 */           double MARK_SIZE = MCH_Config.BlockMarkerSize.prmDouble;
/*     */           
/* 330 */           if (z < 1.0D && x >= 0.0D && x <= (DSW - 20) && y >= 0.0D && y <= (DSH - 40)) {
/*     */ 
/*     */             
/* 333 */             double dist = this.field_146297_k.field_71439_g.func_70011_f(target.getX(), target.getY(), target.getZ());
/*     */             
/* 335 */             GL11.glEnable(3553);
/* 336 */             drawCenteredString(String.format("%.0fm", new Object[] {
/*     */                     
/* 338 */                     Double.valueOf(dist)
/*     */                   }), (int)x, (int)(y + MARK_SIZE * 1.1D + 16.0D), color);
/*     */             
/* 341 */             if (x >= (DSW / 2 - 20) && x <= (DSW / 2 + 20) && y >= (DSH / 2 - 20) && y <= (DSH / 2 + 20)) {
/*     */               
/* 343 */               drawString(String.format("x : %.0f", new Object[] {
/*     */ 
/*     */                       
/* 346 */                       Double.valueOf(target.getX())
/*     */                     }), (int)(x + MARK_SIZE + 18.0D), (int)y - 12, color);
/* 348 */               drawString(String.format("y : %.0f", new Object[] {
/*     */ 
/*     */                       
/* 351 */                       Double.valueOf(target.getY())
/*     */                     }), (int)(x + MARK_SIZE + 18.0D), (int)y - 4, color);
/* 353 */               drawString(String.format("z : %.0f", new Object[] {
/*     */ 
/*     */                       
/* 356 */                       Double.valueOf(target.getZ())
/*     */                     }), (int)(x + MARK_SIZE + 18.0D), (int)y + 4, color);
/*     */             } 
/* 359 */             GL11.glDisable(3553);
/*     */ 
/*     */ 
/*     */             
/* 363 */             builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 364 */             drawRhombus(builder, 15, x, y, this.field_73735_i, MARK_SIZE, color);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 369 */             builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 370 */             double S = 30.0D;
/*     */             
/* 372 */             if (x < S) {
/*     */ 
/*     */               
/* 375 */               drawRhombus(builder, 1, S, (DSH / 2), this.field_73735_i, MARK_SIZE, color);
/*     */             }
/* 377 */             else if (x > DSW - S) {
/*     */ 
/*     */               
/* 380 */               drawRhombus(builder, 4, DSW - S, (DSH / 2), this.field_73735_i, MARK_SIZE, color);
/*     */             } 
/* 382 */             if (y < S) {
/*     */ 
/*     */               
/* 385 */               drawRhombus(builder, 8, (DSW / 2), S, this.field_73735_i, MARK_SIZE, color);
/*     */             }
/* 387 */             else if (y > DSH - S * 2.0D) {
/*     */ 
/*     */               
/* 390 */               drawRhombus(builder, 2, (DSW / 2), DSH - S * 2.0D, this.field_73735_i, MARK_SIZE, color);
/*     */             } 
/*     */           } 
/* 393 */           tessellator.func_78381_a();
/*     */         } 
/*     */       } 
/*     */       
/* 397 */       if (i == 0)
/*     */       {
/* 399 */         tessellator.func_78381_a();
/*     */       }
/*     */     } 
/*     */     
/* 403 */     GL11.glDepthMask(true);
/* 404 */     GL11.glEnable(3553);
/* 405 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRhombus(BufferBuilder builder, int dir, double x, double y, double z, double size, int color) {
/* 412 */     size *= 2.0D;
/*     */     
/* 414 */     int red = color >> 16 & 0xFF;
/* 415 */     int green = color >> 8 & 0xFF;
/* 416 */     int blue = color >> 0 & 0xFF;
/* 417 */     int alpha = color >> 24 & 0xFF;
/* 418 */     double M = size / 3.0D;
/*     */     
/* 420 */     if ((dir & 0x1) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       builder.func_181662_b(x - size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 427 */       builder.func_181662_b(x - size + M, y - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 428 */       builder.func_181662_b(x - size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 429 */       builder.func_181662_b(x - size + M, y + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 432 */     if ((dir & 0x4) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 438 */       builder.func_181662_b(x + size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 439 */       builder.func_181662_b(x + size - M, y - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 440 */       builder.func_181662_b(x + size, y, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 441 */       builder.func_181662_b(x + size - M, y + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 444 */     if ((dir & 0x8) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 450 */       builder.func_181662_b(x, y - size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 451 */       builder.func_181662_b(x + M, y - size + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 452 */       builder.func_181662_b(x, y - size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 453 */       builder.func_181662_b(x - M, y - size + M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */     
/* 456 */     if ((dir & 0x2) != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       builder.func_181662_b(x, y + size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 463 */       builder.func_181662_b(x + M, y + size - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 464 */       builder.func_181662_b(x, y + size, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 465 */       builder.func_181662_b(x - M, y + size - M, z).func_181669_b(red, green, blue, alpha).func_181675_d();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTriangle1(BufferBuilder builder, double x, double y, double size, int color) {
/* 476 */     int red = color >> 16 & 0xFF;
/* 477 */     int green = color >> 8 & 0xFF;
/* 478 */     int blue = color >> 0 & 0xFF;
/* 479 */     int alpha = color >> 24 & 0xFF;
/* 480 */     builder.func_181662_b(x + size / 2.0D, y - 10.0D - size, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 481 */     builder.func_181662_b(x - size / 2.0D, y - 10.0D - size, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 482 */     builder.func_181662_b(x + 0.0D, y - 10.0D, this.field_73735_i).func_181669_b(red, green, blue, alpha).func_181675_d();
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
/*     */   public static void markPoint(int px, int py, int pz) {
/* 498 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/* 500 */     if (entityPlayerSP != null && ((EntityPlayer)entityPlayerSP).field_70170_p != null)
/*     */     {
/* 502 */       if (py < 1000) {
/*     */         
/* 504 */         MCH_ParticlesUtil.spawnMarkPoint((EntityPlayer)entityPlayerSP, 0.5D + px, 1.0D + py, 0.5D + pz);
/*     */       }
/*     */       else {
/*     */         
/* 508 */         MCH_ParticlesUtil.clearMarkPoint();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_GuiTargetMarker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */