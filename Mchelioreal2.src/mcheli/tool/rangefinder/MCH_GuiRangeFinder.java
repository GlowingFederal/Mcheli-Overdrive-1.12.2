package mcheli.tool.rangefinder;

import mcheli.MCH_Config;
import mcheli.MCH_KeyName;
import mcheli.MCH_Lib;
import mcheli.gui.MCH_Gui;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_GuiRangeFinder extends MCH_Gui {
  public MCH_GuiRangeFinder(Minecraft minecraft) {
    super(minecraft);
  }
  
  public void func_73866_w_() {
    super.func_73866_w_();
  }
  
  public boolean func_73868_f() {
    return false;
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    return MCH_ItemRangeFinder.canUse(player);
  }
  
  public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
    if (isThirdPersonView)
      return; 
    GL11.glLineWidth(scaleFactor);
    if (!isDrawGui(player))
      return; 
    GL11.glDisable(3042);
    if (MCH_ItemRangeFinder.isUsingScope(player))
      drawRF(player); 
  }
  
  void drawRF(EntityPlayer player) {
    GL11.glEnable(3042);
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
    int srcBlend = GL11.glGetInteger(3041);
    int dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(770, 771);
    W_McClient.MOD_bindTexture("textures/gui/rangefinder.png");
    double size = 512.0D;
    while (size < this.field_146294_l || size < this.field_146295_m)
      size *= 2.0D; 
    drawTexturedModalRectRotate(-(size - this.field_146294_l) / 2.0D, -(size - this.field_146295_m) / 2.0D, size, size, 0.0D, 0.0D, 256.0D, 256.0D, 0.0F);
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
    double factor = size / 512.0D;
    double SCALE_FACTOR = scaleFactor * factor;
    double CX = (this.field_146297_k.field_71443_c / 2);
    double CY = (this.field_146297_k.field_71440_d / 2);
    double px = (CX - 80.0D * SCALE_FACTOR) / SCALE_FACTOR;
    double py = (CY + 55.0D * SCALE_FACTOR) / SCALE_FACTOR;
    GL11.glPushMatrix();
    GL11.glScaled(factor, factor, factor);
    ItemStack item = player.func_184614_ca();
    int damage = (int)((item.func_77958_k() - item.func_77960_j()) / item.func_77958_k() * 100.0D);
    drawDigit(String.format("%3d", new Object[] { Integer.valueOf(damage) }), (int)px, (int)py, 13, (damage > 0) ? -15663328 : -61424);
    if (damage <= 0) {
      drawString("Please craft", (int)px + 40, (int)py + 0, -65536);
      drawString("redstone", (int)px + 40, (int)py + 10, -65536);
    } 
    px = (CX - 20.0D * SCALE_FACTOR) / SCALE_FACTOR;
    if (damage > 0) {
      Vec3d vs = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
      Vec3d ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
      ve = vs.func_72441_c(ve.field_72450_a * 300.0D, ve.field_72448_b * 300.0D, ve.field_72449_c * 300.0D);
      RayTraceResult mop = player.field_70170_p.func_72901_a(vs, ve, true);
      if (mop != null && mop.field_72313_a != RayTraceResult.Type.MISS) {
        int range = (int)player.func_70011_f(mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
        drawDigit(String.format("%4d", new Object[] { Integer.valueOf(range) }), (int)px, (int)py, 13, -15663328);
      } else {
        drawDigit(String.format("----", new Object[0]), (int)px, (int)py, 13, -61424);
      } 
    } 
    py -= 4.0D;
    px -= 80.0D;
    func_73734_a((int)px, (int)py, (int)px + 30, (int)py + 2, -15663328);
    func_73734_a((int)px, (int)py, (int)px + MCH_ItemRangeFinder.rangeFinderUseCooldown / 2, (int)py + 2, -61424);
    drawString(String.format("x%.1f", new Object[] { Float.valueOf(MCH_ItemRangeFinder.zoom) }), (int)px, (int)py - 20, -1);
    px += 130.0D;
    int mode = MCH_ItemRangeFinder.mode;
    drawString(">", (int)px, (int)py - 30 + mode * 10, -1);
    px += 10.0D;
    drawString("Players/Vehicles", (int)px, (int)py - 30, (mode == 0) ? -1 : -12566464);
    drawString("Monsters/Mobs", (int)px, (int)py - 20, (mode == 1) ? -1 : -12566464);
    drawString("Mark Point", (int)px, (int)py - 10, (mode == 2) ? -1 : -12566464);
    GL11.glPopMatrix();
    px = (CX - 160.0D * SCALE_FACTOR) / scaleFactor;
    py = (CY - 100.0D * SCALE_FACTOR) / scaleFactor;
    if (px < 10.0D)
      px = 10.0D; 
    if (py < 10.0D)
      py = 10.0D; 
    String s = "Spot      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyAttack.prmInt);
    drawString(s, (int)px, (int)py + 0, -1);
    s = "Zoom in   : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
    drawString(s, (int)px, (int)py + 10, (MCH_ItemRangeFinder.zoom < 10.0F) ? -1 : -12566464);
    s = "Zoom out : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
    drawString(s, (int)px, (int)py + 20, (MCH_ItemRangeFinder.zoom > 1.2F) ? -1 : -12566464);
    s = "Mode      : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
    drawString(s, (int)px, (int)py + 30, -1);
  }
}
