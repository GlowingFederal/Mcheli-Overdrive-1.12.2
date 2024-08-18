package mcheli.lweapon;

import mcheli.MCH_Config;
import mcheli.MCH_KeyName;
import mcheli.MCH_Lib;
import mcheli.gui.MCH_Gui;
import mcheli.weapon.MCH_WeaponGuidanceSystem;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_GuiLightWeapon extends MCH_Gui {
  public MCH_GuiLightWeapon(Minecraft minecraft) {
    super(minecraft);
  }
  
  public void func_73866_w_() {
    super.func_73866_w_();
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    if (MCH_ItemLightWeaponBase.isHeld(player)) {
      Entity re = player.func_184187_bx();
      if (!(re instanceof mcheli.aircraft.MCH_EntityAircraft) && !(re instanceof mcheli.gltd.MCH_EntityGLTD))
        return true; 
    } 
    return false;
  }
  
  public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
    if (isThirdPersonView)
      return; 
    GL11.glLineWidth(scaleFactor);
    if (!isDrawGui(player))
      return; 
    MCH_WeaponGuidanceSystem gs = MCH_ClientLightWeaponTickHandler.gs;
    if (gs != null && MCH_ClientLightWeaponTickHandler.weapon != null && MCH_ClientLightWeaponTickHandler.weapon
      .getInfo() != null) {
      PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
      if (pe != null)
        drawNightVisionNoise(); 
      GL11.glEnable(3042);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      int srcBlend = GL11.glGetInteger(3041);
      int dstBlend = GL11.glGetInteger(3040);
      GL11.glBlendFunc(770, 771);
      double dist = 0.0D;
      if (gs.getTargetEntity() != null) {
        double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
        double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
        dist = Math.sqrt(dx * dx + dz * dz);
      } 
      boolean canFire = (MCH_ClientLightWeaponTickHandler.weaponMode == 0 || dist >= 40.0D || gs.getLockCount() <= 0);
      if ("fgm148".equalsIgnoreCase(MCH_ItemLightWeaponBase.getName(player.func_184614_ca()))) {
        drawGuiFGM148(player, gs, canFire, player.func_184614_ca());
        drawKeyBind(-805306369, true);
      } else {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        W_McClient.MOD_bindTexture("textures/gui/stinger.png");
        double size = 512.0D;
        while (size < this.field_146294_l || size < this.field_146295_m)
          size *= 2.0D; 
        drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0D, -(size - this.field_146295_m) / 2.0D - 20.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
        drawKeyBind(-805306369, false);
      } 
      GL11.glBlendFunc(srcBlend, dstBlend);
      GL11.glDisable(3042);
      drawLock(-14101432, -2161656, gs.getLockCount(), gs.getLockCountMax());
      drawRange(player, gs, canFire, -14101432, -2161656);
    } 
  }
  
  public void drawNightVisionNoise() {
    GL11.glEnable(3042);
    GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
    int srcBlend = GL11.glGetInteger(3041);
    int dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(1, 1);
    W_McClient.MOD_bindTexture("textures/gui/alpha.png");
    drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
  }
  
  void drawLock(int color, int colorLock, int cntLock, int cntMax) {
    int posX = this.centerX;
    int posY = this.centerY + 20;
    func_73734_a(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, color);
    float lock = cntLock / cntMax;
    func_73734_a(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * lock), posY + 20 + 1 + 1 + 3, -2161656);
  }
  
  void drawRange(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, int color1, int color2) {
    String msgLockDist = "[--.--]";
    int color = color2;
    if (gs.getLockCount() > 0) {
      Entity target = gs.getLockingEntity();
      if (target != null) {
        double dx = target.field_70165_t - player.field_70165_t;
        double dz = target.field_70161_v - player.field_70161_v;
        msgLockDist = String.format("[%.2f]", new Object[] { Double.valueOf(Math.sqrt(dx * dx + dz * dz)) });
        color = canFire ? color1 : color2;
        if (!MCH_Config.HideKeybind.prmBool)
          if (gs.isLockComplete()) {
            String k = MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
            drawCenteredString("Shot : " + k, this.centerX, this.centerY + 65, -805306369);
          }  
      } 
    } 
    drawCenteredString(msgLockDist, this.centerX, this.centerY + 50, color);
  }
  
  void drawGuiFGM148(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, ItemStack itemStack) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    double fac = (this.field_146294_l / 800.0D < this.field_146295_m / 700.0D) ? (this.field_146294_l / 800.0D) : (this.field_146295_m / 700.0D);
    int size = (int)(1024.0D * fac);
    size = size / 64 * 64;
    fac = size / 1024.0D;
    double left = (-(size - this.field_146294_l) / 2);
    double top = (-(size - this.field_146295_m) / 2 - 20);
    double right = left + size;
    double bottom = top + size;
    Vec3d pos = MCH_ClientLightWeaponTickHandler.getMartEntityPos();
    if (gs.getLockCount() > 0) {
      int scale = (scaleFactor > 0) ? scaleFactor : 2;
      if (pos == null)
        pos = new Vec3d((this.field_146294_l / 2 * scale), (this.field_146295_m / 2 * scale), 0.0D); 
      double IX = 280.0D * fac;
      double IY = 370.0D * fac;
      double cx = pos.field_72450_a / scale;
      double cy = this.field_146295_m - pos.field_72448_b / scale;
      double sx = MCH_Lib.RNG(cx, left + IX, right - IX);
      double sy = MCH_Lib.RNG(cy, top + IY, bottom - IY);
      if (gs.getLockCount() >= gs.getLockCountMax() / 2)
        drawLine(new double[] { -1.0D, sy, (this.field_146294_l + 1), sy, sx, -1.0D, sx, (this.field_146295_m + 1) }, -1593835521); 
      if (player.field_70173_aa % 6 >= 3) {
        pos = MCH_ClientLightWeaponTickHandler.getMartEntityBBPos();
        if (pos == null)
          pos = new Vec3d(((this.field_146294_l / 2 - 65) * scale), ((this.field_146295_m / 2 + 50) * scale), 0.0D); 
        double bx = pos.field_72450_a / scale;
        double by = this.field_146295_m - pos.field_72448_b / scale;
        double dx = Math.abs(cx - bx);
        double dy = Math.abs(cy - by);
        double p = 1.0D - gs.getLockCount() / gs.getLockCountMax();
        dx = MCH_Lib.RNG(dx, 25.0D, 70.0D);
        dy = MCH_Lib.RNG(dy, 15.0D, 70.0D);
        dx += (70.0D - dx) * p;
        dy += (70.0D - dy) * p;
        int lx = 10;
        int ly = 6;
        drawLine(new double[] { sx - dx, sy - dy + ly, sx - dx, sy - dy, sx - dx + lx, sy - dy }, -1593835521, 3);
        drawLine(new double[] { sx + dx, sy - dy + ly, sx + dx, sy - dy, sx + dx - lx, sy - dy }, -1593835521, 3);
        dy /= 6.0D;
        drawLine(new double[] { sx - dx, sy + dy - ly, sx - dx, sy + dy, sx - dx + lx, sy + dy }, -1593835521, 3);
        drawLine(new double[] { sx + dx, sy + dy - ly, sx + dx, sy + dy, sx + dx - lx, sy + dy }, -1593835521, 3);
      } 
    } 
    func_73734_a(-1, -1, (int)left + 1, this.field_146295_m + 1, -16777216);
    func_73734_a((int)right - 1, -1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
    func_73734_a(-1, -1, this.field_146294_l + 1, (int)top + 1, -16777216);
    func_73734_a(-1, (int)bottom - 1, this.field_146294_l + 1, this.field_146295_m + 1, -16777216);
    GL11.glEnable(3042);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    W_McClient.MOD_bindTexture("textures/gui/javelin.png");
    drawTexturedModalRectRotate(left, top, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
    W_McClient.MOD_bindTexture("textures/gui/javelin2.png");
    PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
    if (pe == null) {
      double x = 247.0D;
      double y = 211.0D;
      double w = 380.0D;
      double h = 350.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (player.func_184612_cw() <= 60) {
      double x = 130.0D;
      double y = 334.0D;
      double w = 257.0D;
      double h = 455.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (MCH_ClientLightWeaponTickHandler.selectedZoom == 0) {
      double x = 387.0D;
      double y = 211.0D;
      double w = 510.0D;
      double h = 350.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (MCH_ClientLightWeaponTickHandler.selectedZoom == 
      (MCH_ClientLightWeaponTickHandler.weapon.getInfo()).zoom.length - 1) {
      double x = 511.0D;
      double y = 211.0D;
      double w = 645.0D;
      double h = 350.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (gs.getLockCount() > 0) {
      double x = 643.0D;
      double y = 211.0D;
      double w = 775.0D;
      double h = 350.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (MCH_ClientLightWeaponTickHandler.weaponMode == 1) {
      double x = 768.0D;
      double y = 340.0D;
      double w = 890.0D;
      double h = 455.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } else {
      double x = 768.0D;
      double y = 456.0D;
      double w = 890.0D;
      double h = 565.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (!canFire) {
      double d1 = 379.0D;
      double d2 = 670.0D;
      double d3 = 511.0D;
      double d4 = 810.0D;
      drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
    } 
    if (itemStack.func_77960_j() >= itemStack.func_77958_k()) {
      double d1 = 512.0D;
      double d2 = 670.0D;
      double d3 = 645.0D;
      double d4 = 810.0D;
      drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
    } 
    if (gs.getLockCount() < gs.getLockCountMax()) {
      double d1 = 646.0D;
      double d2 = 670.0D;
      double d3 = 776.0D;
      double d4 = 810.0D;
      drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
    } 
    if (pe != null) {
      double d1 = 768.0D;
      double d2 = 562.0D;
      double d3 = 890.0D;
      double d4 = 694.0D;
      drawTexturedRect(left + d1 * fac, top + d2 * fac, (d3 - d1) * fac, (d4 - d2) * fac, d1, d2, d3 - d1, d4 - d2, 1024.0D, 1024.0D);
    } 
  }
  
  public void drawKeyBind(int color, boolean canSwitchMode) {
    int OffX = this.centerX + 55;
    int OffY = this.centerY + 40;
    drawString("CAM MODE :", OffX, OffY + 10, color);
    drawString("ZOOM      :", OffX, OffY + 20, color);
    if (canSwitchMode)
      drawString("MODE      :", OffX, OffY + 30, color); 
    OffX += 60;
    drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt), OffX, OffY + 10, color);
    drawString(MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt), OffX, OffY + 20, color);
    if (canSwitchMode)
      drawString(MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt), OffX, OffY + 30, color); 
  }
}
