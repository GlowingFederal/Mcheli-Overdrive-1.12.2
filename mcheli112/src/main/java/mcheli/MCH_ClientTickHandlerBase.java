package mcheli;

import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class MCH_ClientTickHandlerBase {
  protected Minecraft mc;
  
  public static float playerRotMinPitch = -90.0F;
  
  public static float playerRotMaxPitch = 90.0F;
  
  public static boolean playerRotLimitPitch = false;
  
  public static float playerRotMinYaw = -180.0F;
  
  public static float playerRotMaxYaw = 180.0F;
  
  public static boolean playerRotLimitYaw = false;
  
  private static int mouseWheel = 0;
  
  public abstract void updateKeybind(MCH_Config paramMCH_Config);
  
  public static void setRotLimitPitch(float min, float max, Entity player) {
    playerRotMinPitch = min;
    playerRotMaxPitch = max;
    playerRotLimitPitch = true;
    if (player != null)
      player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerRotMinPitch, playerRotMaxPitch); 
  }
  
  public static void setRotLimitYaw(float min, float max, Entity e) {
    playerRotMinYaw = min;
    playerRotMaxYaw = max;
    playerRotLimitYaw = true;
    if (e != null)
      if (e.rotationPitch < playerRotMinPitch) {
        e.rotationPitch = playerRotMinPitch;
        e.prevRotationPitch = playerRotMinPitch;
      } else if (e.rotationPitch > playerRotMaxPitch) {
        e.rotationPitch = playerRotMaxPitch;
        e.prevRotationPitch = playerRotMaxPitch;
      }  
  }
  
  public static void initRotLimit() {
    playerRotMinPitch = -90.0F;
    playerRotMaxPitch = 90.0F;
    playerRotLimitYaw = false;
    playerRotMinYaw = -180.0F;
    playerRotMaxYaw = 180.0F;
    playerRotLimitYaw = false;
  }
  
  public static void applyRotLimit(Entity e) {
    if (e != null) {
      if (playerRotLimitPitch)
        if (e.rotationPitch < playerRotMinPitch) {
          e.rotationPitch = playerRotMinPitch;
          e.prevRotationPitch = playerRotMinPitch;
        } else if (e.rotationPitch > playerRotMaxPitch) {
          e.rotationPitch = playerRotMaxPitch;
          e.prevRotationPitch = playerRotMaxPitch;
        }  
      if (!playerRotLimitYaw);
    } 
  }
  
  public MCH_ClientTickHandlerBase(Minecraft minecraft) {
    this.mc = minecraft;
  }
  
  public static boolean updateMouseWheel(int wheel) {
    boolean cancelEvent = false;
    if (wheel != 0 && MCH_Config.SwitchWeaponWithMouseWheel.prmBool) {
      setMouseWheel(0);
      EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
      if (entityPlayerSP != null) {
        MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
        if (ac != null) {
          int cwid = ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)entityPlayerSP));
          int nwid = ac.getNextWeaponID((Entity)entityPlayerSP, 1);
          if (cwid != nwid) {
            setMouseWheel(wheel);
            cancelEvent = true;
          } 
        } 
      } 
    } 
    return cancelEvent;
  }
  
  protected abstract void onTick(boolean paramBoolean);
  
  public static void playSoundOK() {
    W_McClient.playSoundClick(1.0F, 1.0F);
  }
  
  public static void playSoundNG() {
    W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
  }
  
  public static void playSound(String name) {
    W_McClient.MOD_playSoundFX(name, 1.0F, 1.0F);
  }
  
  public static void playSound(String name, float vol, float pitch) {
    W_McClient.MOD_playSoundFX(name, vol, pitch);
  }
  
  public static int getMouseWheel() {
    return mouseWheel;
  }
  
  public static void setMouseWheel(int mouseWheel) {
    MCH_ClientTickHandlerBase.mouseWheel = mouseWheel;
  }
}
