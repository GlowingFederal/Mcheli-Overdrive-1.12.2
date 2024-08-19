package mcheli.uav;

import java.io.IOException;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.helicopter.MCH_HeliInfoManager;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.plane.MCP_PlaneInfoManager;
import mcheli.tank.MCH_TankInfo;
import mcheli.tank.MCH_TankInfoManager;
import mcheli.wrapper.W_GuiContainer;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class MCH_GuiUavStation extends W_GuiContainer {
  final MCH_EntityUavStation uavStation;
  
  static final int BX = 20;
  
  static final int BY = 22;
  
  private GuiButton buttonContinue;
  
  public MCH_GuiUavStation(InventoryPlayer inventoryPlayer, MCH_EntityUavStation uavStation) {
    super(new MCH_ContainerUavStation(inventoryPlayer, uavStation));
    this.uavStation = uavStation;
  }
  
  protected void drawGuiContainerForegroundLayer(int param1, int param2) {
    MCH_TankInfo mCH_TankInfo;
    if (this.uavStation == null)
      return; 
    ItemStack item = this.uavStation.getStackInSlot(0);
    MCH_AircraftInfo info = null;
    if (!item.func_190926_b() && item.getItem() instanceof mcheli.plane.MCP_ItemPlane)
      MCP_PlaneInfo mCP_PlaneInfo = MCP_PlaneInfoManager.getFromItem(item.getItem()); 
    if (!item.func_190926_b() && item.getItem() instanceof mcheli.helicopter.MCH_ItemHeli)
      MCH_HeliInfo mCH_HeliInfo = MCH_HeliInfoManager.getFromItem(item.getItem()); 
    if (!item.func_190926_b() && item.getItem() instanceof mcheli.tank.MCH_ItemTank)
      mCH_TankInfo = MCH_TankInfoManager.getFromItem(item.getItem()); 
    if (item.func_190926_b() || (mCH_TankInfo != null && ((MCH_AircraftInfo)mCH_TankInfo).isUAV)) {
      if (this.uavStation.getKind() <= 1) {
        drawString("UAV Station", 8, 6, 16777215);
      } else if (item.func_190926_b() || ((MCH_AircraftInfo)mCH_TankInfo).isSmallUAV) {
        drawString("UAV Controller", 8, 6, 16777215);
      } else {
        drawString("Small UAV only", 8, 6, 16711680);
      } 
    } else if (!item.func_190926_b()) {
      drawString("Not UAV", 8, 6, 16711680);
    } 
    drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 16777215);
    drawString(String.format("X.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavX) }), 58, 15, 16777215);
    drawString(String.format("Y.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavY) }), 58, 37, 16777215);
    drawString(String.format("Z.%+2d", new Object[] { Integer.valueOf(this.uavStation.posUavZ) }), 58, 59, 16777215);
  }
  
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    W_McClient.MOD_bindTexture("textures/gui/uav_station.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int x = (this.width - this.xSize) / 2;
    int y = (this.height - this.ySize) / 2;
    drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
  }
  
  protected void actionPerformed(GuiButton btn) throws IOException {
    if (btn != null && btn.enabled)
      if (btn.id == 256) {
        if (this.uavStation != null && !this.uavStation.isDead && this.uavStation
          .getLastControlAircraft() != null && 
          !(this.uavStation.getLastControlAircraft()).isDead) {
          MCH_UavPacketStatus data = new MCH_UavPacketStatus();
          data.posUavX = (byte)this.uavStation.posUavX;
          data.posUavY = (byte)this.uavStation.posUavY;
          data.posUavZ = (byte)this.uavStation.posUavZ;
          data.continueControl = true;
          W_Network.sendToServer((W_PacketBase)data);
        } 
        this.buttonContinue.enabled = false;
      } else {
        int[] pos = { this.uavStation.posUavX, this.uavStation.posUavY, this.uavStation.posUavZ };
        int i = btn.id >> 4 & 0xF;
        int j = (btn.id & 0xF) - 1;
        int[] BTN = { -10, -1, 1, 10 };
        pos[i] = pos[i] + BTN[j];
        if (pos[i] < -50)
          pos[i] = -50; 
        if (pos[i] > 50)
          pos[i] = 50; 
        if (this.uavStation.posUavX != pos[0] || this.uavStation.posUavY != pos[1] || this.uavStation.posUavZ != pos[2]) {
          MCH_UavPacketStatus data = new MCH_UavPacketStatus();
          data.posUavX = (byte)pos[0];
          data.posUavY = (byte)pos[1];
          data.posUavZ = (byte)pos[2];
          W_Network.sendToServer((W_PacketBase)data);
        } 
      }  
  }
  
  public void initGui() {
    super.initGui();
    this.buttonList.clear();
    int x = this.width / 2 - 5;
    int y = this.height / 2 - 76;
    String[] BTN = { "-10", "-1", "+1", "+10" };
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 4; col++) {
        int id = row << 4 | col + 1;
        this.buttonList.add(new GuiButton(id, x + col * 20, y + row * 22, 20, 20, BTN[col]));
      } 
    } 
    this.buttonContinue = new GuiButton(256, x - 80 + 3, y + 44, 50, 20, "Continue");
    this.buttonContinue.enabled = false;
    if (this.uavStation != null && !this.uavStation.isDead)
      if (this.uavStation.getAndSearchLastControlAircraft() != null)
        this.buttonContinue.enabled = true;  
    this.buttonList.add(this.buttonContinue);
  }
}
