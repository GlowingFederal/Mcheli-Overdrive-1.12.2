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
  
  public void initGui() {
    super.initGui();
  }
  
  public boolean doesGuiPauseGame() {
    return false;
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    if (MCH_ItemLightWeaponBase.isHeld(player)) {
      Entity re = player.getRidingEntity();
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
      PotionEffect pe = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
      if (pe != null)
        drawNightVisionNoise(); 
      GL11.glEnable(3042);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      int srcBlend = GL11.glGetInteger(3041);
      int dstBlend = GL11.glGetInteger(3040);
      GL11.glBlendFunc(770, 771);
      double dist = 0.0D;
      if (gs.getTargetEntity() != null) {
        double dx = (gs.getTargetEntity()).posX - player.posX;
        double dz = (gs.getTargetEntity()).posZ - player.posZ;
        dist = Math.sqrt(dx * dx + dz * dz);
      } 
      boolean canFire = (MCH_ClientLightWeaponTickHandler.weaponMode == 0 || dist >= 40.0D || gs.getLockCount() <= 0);
      if ("fgm148".equalsIgnoreCase(MCH_ItemLightWeaponBase.getName(player.getHeldItemMainhand()))) {
        drawGuiFGM148(player, gs, canFire, player.getHeldItemMainhand());
        drawKeyBind(-805306369, true);
      } else {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        W_McClient.MOD_bindTexture("textures/gui/stinger.png");
        double size = 512.0D;
        while (size < this.width || size < this.height)
          size *= 2.0D; 
        drawTexturedModalRectRotate(-(size - this.width) / 2.0D, -(size - this.height) / 2.0D - 20.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
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
    drawTexturedModalRectRotate(0.0D, 0.0D, this.width, this.height, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
  }
  
  void drawLock(int color, int colorLock, int cntLock, int cntMax) {
    int posX = this.centerX;
    int posY = this.centerY + 20;
    drawRect(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, color);
    float lock = cntLock / cntMax;
    drawRect(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * lock), posY + 20 + 1 + 1 + 3, -2161656);
  }
  
  void drawRange(EntityPlayer player, MCH_WeaponGuidanceSystem gs, boolean canFire, int color1, int color2) {
    String msgLockDist = "[--.--]";
    int color = color2;
    if (gs.getLockCount() > 0) {
      Entity target = gs.getLockingEntity();
      if (target != null) {
        double dx = target.posX - player.posX;
        double dz = target.posZ - player.posZ;
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
    double fac = (this.width / 800.0D < this.height / 700.0D) ? (this.width / 800.0D) : (this.height / 700.0D);
    int size = (int)(1024.0D * fac);
    size = size / 64 * 64;
    fac = size / 1024.0D;
    double left = (-(size - this.width) / 2);
    double top = (-(size - this.height) / 2 - 20);
    double right = left + size;
    double bottom = top + size;
    Vec3d pos = MCH_ClientLightWeaponTickHandler.getMartEntityPos();
    if (gs.getLockCount() > 0) {
      int scale = (scaleFactor > 0) ? scaleFactor : 2;
      if (pos == null)
        pos = new Vec3d((this.width / 2 * scale), (this.height / 2 * scale), 0.0D); 
      double IX = 280.0D * fac;
      double IY = 370.0D * fac;
      double cx = pos.x / scale;
      double cy = this.height - pos.y / scale;
      double sx = MCH_Lib.RNG(cx, left + IX, right - IX);
      double sy = MCH_Lib.RNG(cy, top + IY, bottom - IY);
      if (gs.getLockCount() >= gs.getLockCountMax() / 2)
        drawLine(new double[] { -1.0D, sy, (this.width + 1), sy, sx, -1.0D, sx, (this.height + 1) }, -1593835521); 
      if (player.ticksExisted % 6 >= 3) {
        pos = MCH_ClientLightWeaponTickHandler.getMartEntityBBPos();
        if (pos == null)
          pos = new Vec3d(((this.width / 2 - 65) * scale), ((this.height / 2 + 50) * scale), 0.0D); 
        double bx = pos.x / scale;
        double by = this.height - pos.y / scale;
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
    drawRect(-1, -1, (int)left + 1, this.height + 1, -16777216);
    drawRect((int)right - 1, -1, this.width + 1, this.height + 1, -16777216);
    drawRect(-1, -1, this.width + 1, (int)top + 1, -16777216);
    drawRect(-1, (int)bottom - 1, this.width + 1, this.height + 1, -16777216);
    GL11.glEnable(3042);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    W_McClient.MOD_bindTexture("textures/gui/javelin.png");
    drawTexturedModalRectRotate(left, top, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
    W_McClient.MOD_bindTexture("textures/gui/javelin2.png");
    PotionEffect pe = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
    if (pe == null) {
      double x = 247.0D;
      double y = 211.0D;
      double w = 380.0D;
      double h = 350.0D;
      drawTexturedRect(left + x * fac, top + y * fac, (w - x) * fac, (h - y) * fac, x, y, w - x, h - y, 1024.0D, 1024.0D);
    } 
    if (player.getItemInUseMaxCount() <= 60) {
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
    if (itemStack.getMetadata() >= itemStack.getMaxDamage()) {
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
