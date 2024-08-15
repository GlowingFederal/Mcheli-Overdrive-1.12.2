/*    */ package mcheli.plane;
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
/*    */ public class MCP_ItemPlane
/*    */   extends MCH_ItemAircraft
/*    */ {
/*    */   public MCP_ItemPlane(int par1) {
/* 21 */     super(par1);
/* 22 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_AircraftInfo getAircraftInfo() {
/* 28 */     return MCP_PlaneInfoManager.getFromItem((Item)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCP_EntityPlane createAircraft(World world, double x, double y, double z, ItemStack itemStack) {
/* 35 */     MCP_PlaneInfo info = MCP_PlaneInfoManager.getFromItem((Item)this);
/*    */     
/* 37 */     if (info == null) {
/*    */       
/* 39 */       MCH_Lib.Log(world, "##### MCP_EntityPlane Plane info null %s", new Object[] {
/*    */             
/* 41 */             func_77658_a()
/*    */           });
/* 43 */       return null;
/*    */     } 
/*    */     
/* 46 */     MCP_EntityPlane plane = new MCP_EntityPlane(world);
/*    */ 
/*    */     
/* 49 */     plane.func_70107_b(x, y, z);
/* 50 */     plane.field_70169_q = x;
/* 51 */     plane.field_70167_r = y;
/* 52 */     plane.field_70166_s = z;
/* 53 */     plane.camera.setPosition(x, y, z);
/* 54 */     plane.setTypeName(info.name);
/*    */     
/* 56 */     if (!world.field_72995_K)
/*    */     {
/* 58 */       plane.setTextureName(info.getTextureName());
/*    */     }
/*    */     
/* 61 */     return plane;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_ItemPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */