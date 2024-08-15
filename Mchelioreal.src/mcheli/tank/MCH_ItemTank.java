/*    */ package mcheli.tank;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.aircraft.MCH_AircraftInfo;
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import mcheli.aircraft.MCH_ItemAircraft;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ItemTank
/*    */   extends MCH_ItemAircraft
/*    */ {
/*    */   public MCH_ItemTank(int par1) {
/* 21 */     super(par1);
/* 22 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 29 */     return MCH_TankInfoManager.getFromItem((Item)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityTank createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 36 */     MCH_TankInfo info = MCH_TankInfoManager.getFromItem((Item)this);
/*    */     
/* 38 */     if (info == null) {
/*    */       
/* 40 */       MCH_Lib.Log(world, "##### MCH_EntityTank Tank info null %s", new Object[] {
/*    */             
/* 42 */             func_77658_a()
/*    */           });
/* 44 */       return null;
/*    */     } 
/* 46 */     MCH_EntityTank tank = new MCH_EntityTank(world);
/*    */ 
/*    */     
/* 49 */     tank.func_70107_b(x, y, z);
/* 50 */     tank.field_70169_q = x;
/* 51 */     tank.field_70167_r = y;
/* 52 */     tank.field_70166_s = z;
/* 53 */     tank.camera.setPosition(x, y, z);
/* 54 */     tank.setTypeName(info.name);
/*    */     
/* 56 */     if (!world.field_72995_K)
/*    */     {
/* 58 */       tank.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 61 */     return tank;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_ItemTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */