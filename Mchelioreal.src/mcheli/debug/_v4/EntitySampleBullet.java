/*    */ package mcheli.debug._v4;
/*    */ 
/*    */ import mcheli.weapon.MCH_EntityRocket;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySampleBullet
/*    */   extends MCH_EntityRocket
/*    */ {
/*    */   private Vec3d firstPos;
/*    */   
/*    */   public EntitySampleBullet(World par1World) {
/* 18 */     super(par1World, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0F, 0.0F, 4.0D);
/*    */     
/* 20 */     setName("hydra70");
/* 21 */     func_70105_a(1.0F, 1.0F);
/* 22 */     this.explosionPower = 3;
/* 23 */     setPower(22);
/*    */     
/* 25 */     this.firstPos = Vec3d.field_186680_a;
/* 26 */     this.accelerationFactor = 1.0D;
/* 27 */     this.delayFuse = 100;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70037_a(NBTTagCompound par1nbtTagCompound) {
/* 33 */     this.field_70159_w = 1.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkValid() {
/* 39 */     double x = this.field_70165_t - this.firstPos.field_72450_a;
/* 40 */     double z = this.field_70161_v - this.firstPos.field_72449_c;
/*    */     
/* 42 */     return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v4\EntitySampleBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */