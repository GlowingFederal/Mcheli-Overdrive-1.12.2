/*     */ package mcheli.mob;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiSpawnGunner
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_GuiSpawnGunner(Minecraft minecraft) {
/*  30 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  36 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  49 */     return (player != null && player.field_70170_p != null && !player.func_184614_ca().func_190926_b() && player
/*  50 */       .func_184614_ca().func_77973_b() instanceof MCH_ItemSpawnGunner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  56 */     if (isThirdPersonView) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (!isDrawGui(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  66 */     GL11.glLineWidth(scaleFactor);
/*  67 */     GL11.glDisable(3042);
/*     */     
/*  69 */     draw(player, searchTarget(player));
/*     */   } private Entity searchTarget(EntityPlayer player) {
/*     */     MCH_EntityGunner mCH_EntityGunner;
/*     */     MCH_EntitySeat mCH_EntitySeat;
/*     */     MCH_EntityAircraft mCH_EntityAircraft;
/*  74 */     float f = 1.0F;
/*  75 */     float pitch = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
/*  76 */     float yaw = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
/*  77 */     double dx = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
/*     */     
/*  79 */     double dy = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + player.func_70047_e();
/*  80 */     double dz = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
/*  81 */     Vec3d vec3 = new Vec3d(dx, dy, dz);
/*  82 */     float f3 = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
/*  83 */     float f4 = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
/*  84 */     float f5 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
/*  85 */     float f6 = MathHelper.func_76126_a(-pitch * 0.017453292F);
/*  86 */     float f7 = f4 * f5;
/*  87 */     float f8 = f3 * f5;
/*  88 */     double d3 = 5.0D;
/*  89 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*  90 */     Entity target = null;
/*     */ 
/*     */ 
/*     */     
/*  94 */     List<MCH_EntityGunner> list = player.field_70170_p.func_72872_a(MCH_EntityGunner.class, player
/*  95 */         .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */     
/*  97 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  99 */       MCH_EntityGunner gunner = list.get(i);
/*     */ 
/*     */       
/* 102 */       if (gunner.func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */       {
/* 104 */         if (target == null || player.func_70068_e((Entity)gunner) < player.func_70068_e(target))
/*     */         {
/* 106 */           mCH_EntityGunner = gunner;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 111 */     if (mCH_EntityGunner != null)
/*     */     {
/* 113 */       return (Entity)mCH_EntityGunner;
/*     */     }
/*     */ 
/*     */     
/* 117 */     MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)player.func_184614_ca().func_77973_b();
/*     */     
/* 119 */     if (item.targetType == 1 && !player.field_70170_p.field_72995_K && player.func_96124_cp() == null)
/*     */     {
/* 121 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 125 */     List<MCH_EntitySeat> list1 = player.field_70170_p.func_72872_a(MCH_EntitySeat.class, player
/* 126 */         .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */ 
/*     */     
/* 129 */     for (int j = 0; j < list1.size(); j++) {
/*     */ 
/*     */       
/* 132 */       MCH_EntitySeat seat = list1.get(j);
/*     */ 
/*     */       
/* 135 */       if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat
/* 136 */         .func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */       {
/* 138 */         if (mCH_EntityGunner == null || player.func_70068_e((Entity)seat) < player.func_70068_e((Entity)mCH_EntityGunner))
/*     */         {
/*     */           
/* 141 */           if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*     */ 
/*     */             
/* 144 */             Entity entity = seat.getRiddenByEntity();
/*     */           }
/*     */           else {
/*     */             
/* 148 */             mCH_EntitySeat = seat;
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 154 */     if (mCH_EntitySeat == null) {
/*     */ 
/*     */       
/* 157 */       List<MCH_EntityAircraft> list2 = player.field_70170_p.func_72872_a(MCH_EntityAircraft.class, player
/* 158 */           .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */ 
/*     */       
/* 161 */       for (int k = 0; k < list2.size(); k++) {
/*     */ 
/*     */         
/* 164 */         MCH_EntityAircraft ac = list2.get(k);
/*     */ 
/*     */         
/* 167 */         if (!ac.isUAV() && ac.getAcInfo() != null && ac
/* 168 */           .func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */         {
/* 170 */           if (mCH_EntitySeat == null || player.func_70068_e((Entity)ac) < player.func_70068_e((Entity)mCH_EntitySeat))
/*     */           {
/* 172 */             if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*     */               
/* 174 */               Entity entity = ac.getRiddenByEntity();
/*     */             }
/*     */             else {
/*     */               
/* 178 */               mCH_EntityAircraft = ac;
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return (Entity)mCH_EntityAircraft;
/*     */   }
/*     */ 
/*     */   
/*     */   void draw(EntityPlayer player, Entity entity) {
/* 190 */     if (entity == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 195 */     GL11.glEnable(3042);
/* 196 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 197 */     int srcBlend = GL11.glGetInteger(3041);
/* 198 */     int dstBlend = GL11.glGetInteger(3040);
/* 199 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 201 */     double size = 512.0D;
/*     */     
/* 203 */     while (size < this.field_146294_l || size < this.field_146295_m)
/*     */     {
/* 205 */       size *= 2.0D;
/*     */     }
/*     */     
/* 208 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 209 */     GL11.glDisable(3042);
/*     */     
/* 211 */     double factor = size / 512.0D;
/* 212 */     double SCALE_FACTOR = scaleFactor * factor;
/* 213 */     double CX = (this.field_146297_k.field_71443_c / 2);
/* 214 */     double CY = (this.field_146297_k.field_71440_d / 2);
/* 215 */     double px = (CX - 0.0D) / SCALE_FACTOR;
/* 216 */     double py = (CY + 0.0D) / SCALE_FACTOR;
/*     */     
/* 218 */     GL11.glPushMatrix();
/*     */     
/* 220 */     if (entity instanceof MCH_EntityGunner) {
/*     */       
/* 222 */       MCH_EntityGunner gunner = (MCH_EntityGunner)entity;
/* 223 */       String seatName = "";
/*     */       
/* 225 */       if (gunner.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/* 227 */         seatName = "(seat " + (((MCH_EntitySeat)gunner.func_184187_bx()).seatID + 2) + ")";
/*     */       }
/* 229 */       else if (gunner.func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */         
/* 231 */         seatName = "(seat 1)";
/*     */       } 
/*     */ 
/*     */       
/* 235 */       String name = MCH_MOD.isTodaySep01() ? " EMB4 " : " Gunner ";
/* 236 */       drawCenteredString(gunner.getTeamName() + name + seatName, (int)px, (int)py + 20, -8355840);
/*     */       
/* 238 */       int S = 10;
/*     */       
/* 240 */       drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -8355840, 2);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 245 */     else if (entity instanceof MCH_EntitySeat) {
/*     */       
/* 247 */       MCH_EntitySeat seat = (MCH_EntitySeat)entity;
/*     */ 
/*     */       
/* 250 */       if (seat.getRiddenByEntity() == null)
/*     */       {
/* 252 */         drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -16711681);
/*     */         
/* 254 */         int S = 10;
/*     */         
/* 256 */         drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -16711681, 2);
/*     */ 
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 263 */         drawCenteredString("seat " + (seat.seatID + 2), (int)px, (int)py + 20, -65536);
/*     */         
/* 265 */         int S = 10;
/*     */         
/* 267 */         drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -65536, 2);
/*     */ 
/*     */ 
/*     */         
/* 271 */         drawLine(new double[] { px - S, py - S, px + S, py + S }, -65536);
/*     */ 
/*     */ 
/*     */         
/* 275 */         drawLine(new double[] { px + S, py - S, px - S, py + S }, -65536);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 281 */     else if (entity instanceof MCH_EntityAircraft) {
/*     */       
/* 283 */       MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/*     */       
/* 285 */       if (ac.getRiddenByEntity() == null) {
/*     */         
/* 287 */         drawCenteredString("seat 1", (int)px, (int)py + 20, -16711681);
/*     */         
/* 289 */         int S = 10;
/*     */         
/* 291 */         drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -16711681, 2);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 298 */         drawCenteredString("seat 1", (int)px, (int)py + 20, -65536);
/* 299 */         int S = 10;
/* 300 */         drawLine(new double[] { px - S, py - S, px + S, py - S, px + S, py + S, px - S, py + S }, -65536, 2);
/*     */ 
/*     */ 
/*     */         
/* 304 */         drawLine(new double[] { px - S, py - S, px + S, py + S }, -65536);
/*     */ 
/*     */ 
/*     */         
/* 308 */         drawLine(new double[] { px + S, py - S, px - S, py + S }, -65536);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\mob\MCH_GuiSpawnGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */