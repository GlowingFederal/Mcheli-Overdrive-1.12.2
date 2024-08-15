/*    */ package mcheli.vehicle;
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
/*    */ public class MCH_ItemVehicle
/*    */   extends MCH_ItemAircraft
/*    */ {
/*    */   public MCH_ItemVehicle(int par1) {
/* 21 */     super(par1);
/* 22 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 29 */     return MCH_VehicleInfoManager.getFromItem((Item)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityVehicle createAircraft(World world, double x, double y, double z, ItemStack item) {
/* 36 */     MCH_VehicleInfo info = MCH_VehicleInfoManager.getFromItem((Item)this);
/*    */     
/* 38 */     if (info == null) {
/*    */       
/* 40 */       MCH_Lib.Log(world, "##### MCH_ItemVehicle Vehicle info null %s", new Object[] {
/*    */             
/* 42 */             func_77658_a()
/*    */           });
/* 44 */       return null;
/*    */     } 
/*    */     
/* 47 */     MCH_EntityVehicle vehicle = new MCH_EntityVehicle(world);
/*    */ 
/*    */     
/* 50 */     vehicle.func_70107_b(x, y, z);
/* 51 */     vehicle.field_70169_q = x;
/* 52 */     vehicle.field_70167_r = y;
/* 53 */     vehicle.field_70166_s = z;
/* 54 */     vehicle.camera.setPosition(x, y, z);
/* 55 */     vehicle.setTypeName(info.name);
/*    */     
/* 57 */     if (!world.field_72995_K)
/*    */     {
/* 59 */       vehicle.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 62 */     return vehicle;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_ItemVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */