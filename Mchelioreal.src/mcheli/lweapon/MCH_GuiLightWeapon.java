/*     */ package mcheli.lweapon;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.weapon.MCH_WeaponGuidanceSystem;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
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
/*     */ public class MCH_GuiLightWeapon
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiLightWeapon(Minecraft minecraft) {
/*  34 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  40 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  52 */     if (MCH_ItemLightWeaponBase.isHeld(player)) {
/*     */       
/*  54 */       Entity re = player.func_184187_bx();
/*     */       
/*  56 */       if (!(re instanceof mcheli.aircraft.MCH_EntityAircraft) && !(re instanceof mcheli.gltd.MCH_EntityGLTD))
/*     */       {
/*  58 */         return true;
/*     */       }
/*     */     } 
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  67 */     if (isThirdPersonView) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  72 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  74 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  79 */     MCH_WeaponGuidanceSystem gs = MCH_ClientLightWeaponTickHandler.gs;
/*     */     
/*  81 */     if (gs != null && MCH_ClientLightWeaponTickHandler.weapon != null && MCH_ClientLightWeaponTickHandler.weapon
/*  82 */       .getInfo() != null) {
/*     */ 
/*     */       
/*  85 */       PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/*     */       
/*  87 */       if (pe != null)
/*     */       {
/*  89 */         drawNightVisionNoise();
/*     */       }
/*     */       
/*  92 */       GL11.glEnable(3042);
/*  93 */       GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/*  94 */       int srcBlend = GL11.glGetInteger(3041);
/*  95 */       int dstBlend = GL11.glGetInteger(3040);
/*  96 */       GL11.glBlendFunc(770, 771);
/*     */       
/*  98 */       double dist = 0.0D;
/*     */       
/* 100 */       if (gs.getTargetEntity() != null) {
/*     */         
/* 102 */         double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
/* 103 */         double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
/* 104 */         dist = Math.sqrt(dx * dx + dz * dz);
/*     */       } 
/*     */ 
/*     */       
/* 108 */       boolean canFire = (MCH_ClientLightWeaponTickHandler.weaponMode == 0 || dist >= 40.0D || gs.getLockCount() <= 0);
/*     */ 
/*     */       
/* 111 */       if ("fgm148".equalsIgnoreCase(MCH_ItemLightWeaponBase.getName(player.func_184614_ca()))) {
/*     */ 
/*     */         
/* 114 */         drawGuiFGM148(player, gs, canFire, player.func_184614_ca());
/* 115 */         drawKeyBind(-805306369, true);
/*     */       }
/*     */       else {
/*     */         
/* 119 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 121 */         W_McClient.MOD_bindTexture("textures/gui/stinger.png");
/* 122 */         double size = 512.0D;
/* 123 */         while (size < this.field_146294_l || size < this.field_146295_m) {
/* 124 */           size *= 2.0D;
/*     */         }
/* 126 */         drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0D, -(size - this.field_146295_m) / 2.0D - 20.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/*     */         
/* 128 */         drawKeyBind(-805306369, false);
/*     */       } 
/*     */       
/* 131 */       GL11.glBlendFunc(srcBlend, dstBlend);
/* 132 */       GL11.glDisable(3042);
/*     */       
/* 134 */       drawLock(-14101432, -2161656, gs.getLockCount(), gs.getLockCountMax());
/* 135 */       drawRange(player, gs, canFire, -14101432, -2161656);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawNightVisionNoise() {
/* 141 */     GL11.glEnable(3042);
/* 142 */     GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/* 143 */     int srcBlend = GL11.glGetInteger(3041);
/* 144 */     int dstBlend = GL11.glGetInteger(3040);
/*     */     
/* 146 */     GL11.glBlendFunc(1, 1);
/*     */     
/* 148 */     W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/* 149 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/*     */ 
/*     */     
/* 152 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 153 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   void drawLock(int color, int colorLock, int cntLock, int cntMax) {
/* 158 */     int posX = this.centerX;
/* 159 */     int posY = this.centerY + 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, color);
/*     */ 
/*     */     
/* 173 */     float lock = cntLock / cntMax;
/* 174 */     func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * lock), posY + 20 + 1 + 1 + 3, -2161656);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void drawRange(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, int color1, int color2) {
/* 180 */     String msgLockDist = "[--.--]";
/* 181 */     int color = color2;
/* 182 */     if (gs.getLockCount() > 0) {
/*     */       
/* 184 */       Entity target = gs.getLockingEntity();
/* 185 */       if (target != null) {
/*     */         
/* 187 */         double dx = target.field_70165_t - player.field_70165_t;
/* 188 */         double dz = target.field_70161_v - player.field_70161_v;
/* 189 */         msgLockDist = String.format("[%.2f]", new Object[] {
/*     */               
/* 191 */               Double.valueOf(Math.sqrt(dx * dx + dz * dz))
/*     */             });
/* 193 */         color = canFire ? color1 : color2;
/*     */         
/* 195 */         if (!MCH_Config.HideKeybind.prmBool)
/*     */         {
/* 197 */           if (gs.isLockComplete()) {
/*     */             
/* 199 */             String k = MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
/* 200 */             drawCenteredString("Shot : " + k, this.centerX, this.centerY + 65, -805306369);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     drawCenteredString(msgLockDist, this.centerX, this.centerY + 50, color);
/*     */   }
/*     */ 
/*     */   
/*     */   void drawGuiFGM148(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, ItemStack itemStack) {
/* 211 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 213 */     double fac = (this.field_146294_l / 800.0D < this.field_146295_m / 700.0D) ? (this.field_146294_l / 800.0D) : (this.field_146295_m / 700.0D);
/* 214 */     int size = (int)(1024.0D * fac);
/* 215 */     size = size / 64 * 64;
/* 216 */     fac = size / 1024.0D;
/* 217 */     double left = (-(size - this.field_146294_l) / 2);
/* 218 */     double top = (-(size - this.field_146295_m) / 2 - 20);
/* 219 */     double right = left + size;
/* 220 */     double bottom = top + size;
/* 221 */     Vec3d pos = MCH_ClientLightWeaponTickHandler.getMartEntityPos();
/*     */     
/* 223 */     if (gs.getLockCount() > 0) {
/*     */       
/* 225 */       int scale = (scaleFactor > 0) ? scaleFactor : 2;
/*     */       
/* 227 */       if (pos == null)
/*     */       {
/* 229 */         pos = new Vec3d((this.field_146294_l / 2 * scale), (this.field_146295_m / 2 * scale), 0.0D);
/*     */       }
/*     */       
/* 232 */       double IX = 280.0D * fac;
/* 233 */       double IY = 370.0D * fac;
/*     */ 
/*     */       
/* 236 */       double cx = pos.field_72450_a / scale;
/* 237 */       double cy = this.field_146295_m - pos.field_72448_b / scale;
/* 238 */       double sx = MCH_Lib.RNG(cx, left + IX, right - IX);
/* 239 */       double sy = MCH_Lib.RNG(cy, top + IY, bottom - IY);
/*     */       
/* 241 */       if (gs.getLockCount() >= gs.getLockCountMax() / 2)
/*     */       {
/* 243 */         drawLine(new double[] { -1.0D, sy, (this.field_146294_l + 1), sy, sx, -1.0D, sx, (this.field_146295_m + 1) }, -1593835521);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (player.field_70173_aa % 6 >= 3) {
/*     */         
/* 251 */         pos = MCH_ClientLightWeaponTickHandler.getMartEntityBBPos();
/*     */         
/* 253 */         if (pos == null)
/*     */         {
/* 255 */           pos = new Vec3d(((this.field_146294_l / 2 - 65) * scale), ((this.field_146295_m / 2 + 50) * scale), 0.0D);
/*     */         }
/*     */         
/* 258 */         double bx = pos.field_72450_a / scale;
/* 259 */         double by = this.field_146295_m - pos.field_72448_b / scale;
/* 260 */         double dx = Math.abs(cx - bx);
/* 261 */         double dy = Math.abs(cy - by);
/*     */         
/* 263 */         double p = 1.0D - gs.getLockCount() / gs.getLockCountMax();
/*     */         
/* 265 */         dx = MCH_Lib.RNG(dx, 25.0D, 70.0D);
/* 266 */         dy = MCH_Lib.RNG(dy, 15.0D, 70.0D);
/*     */         
/* 268 */         dx += (70.0D - dx) * p;
/* 269 */         dy += (70.0D - dy) * p;
/*     */         
/* 271 */         int lx = 10;
/* 272 */         int ly = 6;
/* 273 */         drawLine(new double[] { sx - dx, sy - dy + ly, sx - dx, sy - dy, sx - dx + lx, sy - dy }, -1593835521, 3);
/*     */ 
/*     */ 
/*     */         
/* 277 */         drawLine(new double[] { sx + dx, sy - dy + ly, sx + dx, sy - dy, sx + dx - lx, sy - dy }, -1593835521, 3);
/*     */ 
/*     */ 
/*     */         
/* 281 */         dy /= 6.0D;
/* 282 */         drawLine(new double[] { sx - dx, sy + dy - ly, sx - dx, sy + dy, sx - dx + lx, sy + dy }, -1593835521, 3);
/*     */ 
/*     */ 
/*     */         
/* 286 */         drawLine(new double[] { sx + dx, sy + dy - ly, sx + dx, sy + dy, sx + dx - lx, sy + dy }, -1593835521, 3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     func_73734_a(-1, -1, (int)left + 1, this.field_146295_m + 1, -16777216);
/* 294 */     func_73734_a((int)right - 1, -1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
/* 295 */     func_73734_a(-1, -1, this.field_146294_l + 1, (int)top + 1, -16777216);
/* 296 */     func_73734_a(-1, (int)bottom - 1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
/*     */     
/* 298 */     GL11.glEnable(3042);
/* 299 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 300 */     W_McClient.MOD_bindTexture("textures/gui/javelin.png");
/* 301 */     drawTexturedModalRectRotate(left, top, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
/*     */     
/* 303 */     W_McClient.MOD_bindTexture("textures/gui/javelin2.png");
/*     */ 
/*     */     
/* 306 */     PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/*     */     
/* 308 */     if (pe == null) {
/*     */       
/* 310 */       double x = 247.0D;
/* 311 */       double y = 211.0D;
/* 312 */       double w = 380.0D;
/* 313 */       double h = 350.0D;
/* 314 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (player.func_184612_cw() <= 60) {
/*     */       
/* 321 */       double x = 130.0D;
/* 322 */       double y = 334.0D;
/* 323 */       double w = 257.0D;
/* 324 */       double h = 455.0D;
/* 325 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 329 */     if (MCH_ClientLightWeaponTickHandler.selectedZoom == 0) {
/*     */       
/* 331 */       double x = 387.0D;
/* 332 */       double y = 211.0D;
/* 333 */       double w = 510.0D;
/* 334 */       double h = 350.0D;
/* 335 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 339 */     if (MCH_ClientLightWeaponTickHandler.selectedZoom == 
/* 340 */       (MCH_ClientLightWeaponTickHandler.weapon.getInfo()).zoom.length - 1) {
/*     */       
/* 342 */       double x = 511.0D;
/* 343 */       double y = 211.0D;
/* 344 */       double w = 645.0D;
/* 345 */       double h = 350.0D;
/* 346 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 350 */     if (gs.getLockCount() > 0) {
/*     */       
/* 352 */       double x = 643.0D;
/* 353 */       double y = 211.0D;
/* 354 */       double w = 775.0D;
/* 355 */       double h = 350.0D;
/* 356 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     if (MCH_ClientLightWeaponTickHandler.weaponMode == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 371 */       double x = 768.0D;
/* 372 */       double y = 340.0D;
/* 373 */       double w = 890.0D;
/* 374 */       double h = 455.0D;
/* 375 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 380 */       double x = 768.0D;
/* 381 */       double y = 456.0D;
/* 382 */       double w = 890.0D;
/* 383 */       double h = 565.0D;
/* 384 */       drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 388 */     if (!canFire) {
/*     */       
/* 390 */       double d1 = 379.0D;
/* 391 */       double d2 = 670.0D;
/* 392 */       double d3 = 511.0D;
/* 393 */       double d4 = 810.0D;
/* 394 */       drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 398 */     if (itemStack.func_77960_j() >= itemStack.func_77958_k()) {
/*     */       
/* 400 */       double d1 = 512.0D;
/* 401 */       double d2 = 670.0D;
/* 402 */       double d3 = 645.0D;
/* 403 */       double d4 = 810.0D;
/* 404 */       drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 408 */     if (gs.getLockCount() < gs.getLockCountMax()) {
/*     */       
/* 410 */       double d1 = 646.0D;
/* 411 */       double d2 = 670.0D;
/* 412 */       double d3 = 776.0D;
/* 413 */       double d4 = 810.0D;
/* 414 */       drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
/*     */     } 
/*     */ 
/*     */     
/* 418 */     if (pe != null) {
/*     */       
/* 420 */       double d1 = 768.0D;
/* 421 */       double d2 = 562.0D;
/* 422 */       double d3 = 890.0D;
/* 423 */       double d4 = 694.0D;
/* 424 */       drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawKeyBind(int color, boolean canSwitchMode) {
/* 431 */     int OffX = this.centerX + 55;
/* 432 */     int OffY = this.centerY + 40;
/*     */     
/* 434 */     drawString("CAM MODE :", OffX, OffY + 10, color);
/* 435 */     drawString("ZOOM      :", OffX, OffY + 20, color);
/*     */     
/* 437 */     if (canSwitchMode)
/*     */     {
/* 439 */       drawString("MODE      :", OffX, OffY + 30, color);
/*     */     }
/*     */     
/* 442 */     OffX += 60;
/*     */     
/* 444 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
/* 445 */     drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, color);
/*     */     
/* 447 */     if (canSwitchMode)
/*     */     {
/* 449 */       drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, color);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\lweapon\MCH_GuiLightWeapon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */