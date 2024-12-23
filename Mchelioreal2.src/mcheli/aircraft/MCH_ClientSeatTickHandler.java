package mcheli.aircraft;

import mcheli.MCH_ClientTickHandlerBase;
import mcheli.MCH_Config;
import mcheli.MCH_Key;
import mcheli.MCH_Lib;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MCH_ClientSeatTickHandler extends MCH_ClientTickHandlerBase {
  protected boolean isRiding = false;
  
  protected boolean isBeforeRiding = false;
  
  public MCH_Key KeySwitchNextSeat;
  
  public MCH_Key KeySwitchPrevSeat;
  
  public MCH_Key KeyParachuting;
  
  public MCH_Key KeyFreeLook;
  
  public MCH_Key KeyUnmountForce;
  
  public MCH_Key[] Keys;
  
  public MCH_ClientSeatTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft);
    updateKeybind(config);
  }
  
  public void updateKeybind(MCH_Config config) {
    this.KeySwitchNextSeat = new MCH_Key(MCH_Config.KeyExtra.prmInt);
    this.KeySwitchPrevSeat = new MCH_Key(MCH_Config.KeyGUI.prmInt);
    this.KeyParachuting = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
    this.KeyUnmountForce = new MCH_Key(42);
    this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
    this.Keys = new MCH_Key[] { this.KeySwitchNextSeat, this.KeySwitchPrevSeat, this.KeyParachuting, this.KeyUnmountForce, this.KeyFreeLook };
  }
  
  protected void onTick(boolean inGUI) {
    for (MCH_Key k : this.Keys)
      k.update(); 
    this.isBeforeRiding = this.isRiding;
    EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
    MCH_EntityAircraft ac = null;
    if (entityPlayerSP != null && entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
      MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
      if (seat.getParent() == null || seat.getParent().getAcInfo() == null)
        return; 
      ac = seat.getParent();
      if (!inGUI)
        if (!ac.isDestroyed())
          playerControl((EntityPlayer)entityPlayerSP, seat, ac);  
      this.isRiding = true;
    } else {
      this.isRiding = false;
    } 
    if (this.isBeforeRiding != this.isRiding)
      if (this.isRiding) {
        W_Reflection.setThirdPersonDistance(ac.thirdPersonDist);
      } else {
        if (entityPlayerSP == null || !(entityPlayerSP.func_184187_bx() instanceof MCH_EntityAircraft))
          W_Reflection.restoreDefaultThirdPersonDistance(); 
        MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
      }  
  }
  
  private void playerControl(EntityPlayer player, MCH_EntitySeat seat, MCH_EntityAircraft ac) {
    MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
    boolean send = false;
    if (this.KeyFreeLook.isKeyDown())
      if (ac.canSwitchGunnerFreeLook(player))
        ac.switchGunnerFreeLookMode();  
    if (this.KeyParachuting.isKeyDown())
      if (ac.canParachuting((Entity)player)) {
        pc.parachuting = true;
        send = true;
      } else if (ac.canRepelling((Entity)player)) {
        pc.parachuting = true;
        send = true;
      } else {
        playSoundNG();
      }  
    if (send)
      W_Network.sendToServer((W_PacketBase)pc); 
  }
}
