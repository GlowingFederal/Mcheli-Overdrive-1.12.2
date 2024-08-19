package mcheli.tool;

import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.gui.MCH_Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_GuiWrench extends MCH_Gui {
  public MCH_GuiWrench(Minecraft minecraft) {
    super(minecraft);
  }
  
  public void initGui() {
    super.initGui();
  }
  
  public boolean doesGuiPauseGame() {
    return false;
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    return (player != null && player.world != null && !player.getHeldItemMainhand().func_190926_b() && player
      .getHeldItemMainhand().getItem() instanceof MCH_ItemWrench);
  }
  
  public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
    if (isThirdPersonView)
      return; 
    GL11.glLineWidth(scaleFactor);
    if (!isDrawGui(player))
      return; 
    GL11.glDisable(3042);
    MCH_EntityAircraft ac = ((MCH_ItemWrench)player.getHeldItemMainhand().getItem()).getMouseOverAircraft((EntityLivingBase)player);
    if (ac != null && ac.getMaxHP() > 0) {
      int color = ((ac.getHP() / ac.getMaxHP()) > 0.3D) ? -14101432 : -2161656;
      drawHP(color, -15433180, ac.getHP(), ac.getMaxHP());
    } 
  }
  
  void drawHP(int color, int colorBG, int hp, int hpmax) {
    int posX = this.centerX;
    int posY = this.centerY + 20;
    drawRect(posX - 20, posY + 20 + 1, posX - 20 + 40, posY + 20 + 1 + 1 + 3 + 1, colorBG);
    if (hp > hpmax)
      hp = hpmax; 
    float hpp = hp / hpmax;
    drawRect(posX - 20 + 1, posY + 20 + 1 + 1, posX - 20 + 1 + (int)(38.0D * hpp), posY + 20 + 1 + 1 + 3, color);
    int hppn = (int)(hpp * 100.0F);
    if (hp < hpmax && hppn >= 100)
      hppn = 99; 
    drawCenteredString(String.format("%d %%", new Object[] { Integer.valueOf(hppn) }), posX, posY + 30, color);
  }
}
