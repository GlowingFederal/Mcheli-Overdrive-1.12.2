/*    */ package mcheli.helicopter;
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
/*    */ public class MCH_ItemHeli
/*    */   extends MCH_ItemAircraft
/*    */ {
/*    */   public MCH_ItemHeli(int par1) {
/* 21 */     super(par1);
/* 22 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 29 */     return MCH_HeliInfoManager.getFromItem((Item)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_EntityHeli createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 36 */     MCH_HeliInfo info = MCH_HeliInfoManager.getFromItem((Item)this);
/*    */     
/* 38 */     if (info == null) {
/*    */       
/* 40 */       MCH_Lib.Log(world, "##### MCH_ItemHeli Heli info null %s", new Object[] {
/*    */             
/* 42 */             func_77658_a()
/*    */           });
/* 44 */       return null;
/*    */     } 
/*    */     
/* 47 */     MCH_EntityHeli heli = new MCH_EntityHeli(world);
/*    */ 
/*    */     
/* 50 */     heli.func_70107_b(x, y, z);
/* 51 */     heli.field_70169_q = x;
/* 52 */     heli.field_70167_r = y;
/* 53 */     heli.field_70166_s = z;
/* 54 */     heli.camera.setPosition(x, y, z);
/* 55 */     heli.setTypeName(info.name);
/*    */     
/* 57 */     if (!world.field_72995_K)
/*    */     {
/* 59 */       heli.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 62 */     return heli;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_ItemHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */