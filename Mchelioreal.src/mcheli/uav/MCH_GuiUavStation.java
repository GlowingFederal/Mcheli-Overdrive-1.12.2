/*     */ package mcheli.uav;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.plane.MCP_PlaneInfoManager;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.tank.MCH_TankInfoManager;
/*     */ import mcheli.wrapper.W_GuiContainer;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ public class MCH_GuiUavStation
/*     */   extends W_GuiContainer
/*     */ {
/*     */   final MCH_EntityUavStation uavStation;
/*     */   static final int BX = 20;
/*     */   static final int BY = 22;
/*     */   private GuiButton buttonContinue;
/*     */   
/*     */   public MCH_GuiUavStation(InventoryPlayer inventoryPlayer, MCH_EntityUavStation uavStation) {
/*  40 */     super(new MCH_ContainerUavStation(inventoryPlayer, uavStation));
/*  41 */     this.uavStation = uavStation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int param1, int param2) {
/*     */     MCH_TankInfo mCH_TankInfo;
/*  47 */     if (this.uavStation == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  52 */     ItemStack item = this.uavStation.func_70301_a(0);
/*  53 */     MCH_AircraftInfo info = null;
/*     */ 
/*     */     
/*  56 */     if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.plane.MCP_ItemPlane)
/*     */     {
/*  58 */       MCP_PlaneInfo mCP_PlaneInfo = MCP_PlaneInfoManager.getFromItem(item.func_77973_b());
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.helicopter.MCH_ItemHeli)
/*     */     {
/*  64 */       MCH_HeliInfo mCH_HeliInfo = MCH_HeliInfoManager.getFromItem(item.func_77973_b());
/*     */     }
/*     */ 
/*     */     
/*  68 */     if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.tank.MCH_ItemTank)
/*     */     {
/*  70 */       mCH_TankInfo = MCH_TankInfoManager.getFromItem(item.func_77973_b());
/*     */     }
/*     */ 
/*     */     
/*  74 */     if (item.func_190926_b() || (mCH_TankInfo != null && ((MCH_AircraftInfo)mCH_TankInfo).isUAV)) {
/*     */       
/*  76 */       if (this.uavStation.getKind() <= 1)
/*     */       {
/*  78 */         drawString("UAV Station", 8, 6, 16777215);
/*     */       
/*     */       }
/*  81 */       else if (item.func_190926_b() || ((MCH_AircraftInfo)mCH_TankInfo).isSmallUAV)
/*     */       {
/*  83 */         drawString("UAV Controller", 8, 6, 16777215);
/*     */       }
/*     */       else
/*     */       {
/*  87 */         drawString("Small UAV only", 8, 6, 16711680);
/*     */       }
/*     */     
/*     */     }
/*  91 */     else if (!item.func_190926_b()) {
/*     */       
/*  93 */       drawString("Not UAV", 8, 6, 16711680);
/*     */     } 
/*     */ 
/*     */     
/*  97 */     drawString(I18n.func_135052_a("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 16777215);
/*     */     
/*  99 */     drawString(String.format("X.%+2d", new Object[] {
/*     */             
/* 101 */             Integer.valueOf(this.uavStation.posUavX)
/*     */           }), 58, 15, 16777215);
/* 103 */     drawString(String.format("Y.%+2d", new Object[] {
/*     */             
/* 105 */             Integer.valueOf(this.uavStation.posUavY)
/*     */           }), 58, 37, 16777215);
/* 107 */     drawString(String.format("Z.%+2d", new Object[] {
/*     */             
/* 109 */             Integer.valueOf(this.uavStation.posUavZ)
/*     */           }), 58, 59, 16777215);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 116 */     W_McClient.MOD_bindTexture("textures/gui/uav_station.png");
/* 117 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 119 */     int x = (this.field_146294_l - this.field_146999_f) / 2;
/* 120 */     int y = (this.field_146295_m - this.field_147000_g) / 2;
/*     */     
/* 122 */     func_73729_b(x, y, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton btn) throws IOException {
/* 129 */     if (btn != null && btn.field_146124_l)
/*     */     {
/* 131 */       if (btn.field_146127_k == 256) {
/*     */         
/* 133 */         if (this.uavStation != null && !this.uavStation.field_70128_L && this.uavStation
/* 134 */           .getLastControlAircraft() != null && 
/* 135 */           !(this.uavStation.getLastControlAircraft()).field_70128_L) {
/*     */ 
/*     */           
/* 138 */           MCH_UavPacketStatus data = new MCH_UavPacketStatus();
/*     */           
/* 140 */           data.posUavX = (byte)this.uavStation.posUavX;
/* 141 */           data.posUavY = (byte)this.uavStation.posUavY;
/* 142 */           data.posUavZ = (byte)this.uavStation.posUavZ;
/* 143 */           data.continueControl = true;
/* 144 */           W_Network.sendToServer((W_PacketBase)data);
/*     */         } 
/*     */         
/* 147 */         this.buttonContinue.field_146124_l = false;
/*     */       }
/*     */       else {
/*     */         
/* 151 */         int[] pos = { this.uavStation.posUavX, this.uavStation.posUavY, this.uavStation.posUavZ };
/*     */ 
/*     */ 
/*     */         
/* 155 */         int i = btn.field_146127_k >> 4 & 0xF;
/* 156 */         int j = (btn.field_146127_k & 0xF) - 1;
/* 157 */         int[] BTN = { -10, -1, 1, 10 };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 162 */         pos[i] = pos[i] + BTN[j];
/*     */         
/* 164 */         if (pos[i] < -50) {
/* 165 */           pos[i] = -50;
/*     */         }
/* 167 */         if (pos[i] > 50)
/*     */         {
/* 169 */           pos[i] = 50;
/*     */         }
/*     */         
/* 172 */         if (this.uavStation.posUavX != pos[0] || this.uavStation.posUavY != pos[1] || this.uavStation.posUavZ != pos[2]) {
/*     */ 
/*     */           
/* 175 */           MCH_UavPacketStatus data = new MCH_UavPacketStatus();
/*     */           
/* 177 */           data.posUavX = (byte)pos[0];
/* 178 */           data.posUavY = (byte)pos[1];
/* 179 */           data.posUavZ = (byte)pos[2];
/* 180 */           W_Network.sendToServer((W_PacketBase)data);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 189 */     super.func_73866_w_();
/*     */     
/* 191 */     this.field_146292_n.clear();
/*     */     
/* 193 */     int x = this.field_146294_l / 2 - 5;
/* 194 */     int y = this.field_146295_m / 2 - 76;
/* 195 */     String[] BTN = { "-10", "-1", "+1", "+10" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     for (int row = 0; row < 3; row++) {
/*     */       
/* 202 */       for (int col = 0; col < 4; col++) {
/*     */         
/* 204 */         int id = row << 4 | col + 1;
/*     */         
/* 206 */         this.field_146292_n.add(new GuiButton(id, x + col * 20, y + row * 22, 20, 20, BTN[col]));
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     this.buttonContinue = new GuiButton(256, x - 80 + 3, y + 44, 50, 20, "Continue");
/* 211 */     this.buttonContinue.field_146124_l = false;
/*     */     
/* 213 */     if (this.uavStation != null && !this.uavStation.field_70128_L)
/*     */     {
/* 215 */       if (this.uavStation.getAndSearchLastControlAircraft() != null)
/*     */       {
/* 217 */         this.buttonContinue.field_146124_l = true;
/*     */       }
/*     */     }
/*     */     
/* 221 */     this.field_146292_n.add(this.buttonContinue);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_GuiUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */