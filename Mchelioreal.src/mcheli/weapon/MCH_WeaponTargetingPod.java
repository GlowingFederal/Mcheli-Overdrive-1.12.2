/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import mcheli.multiplay.MCH_Multiplay;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponTargetingPod
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponTargetingPod(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 20 */     super(w, v, yaw, pitch, nm, wi);
/* 21 */     this.interval = -90;
/*    */     
/* 23 */     if (w.field_72995_K) {
/* 24 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 30 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 32 */       MCH_WeaponInfo info = getInfo();
/*    */       
/* 34 */       if ((info.target & 0x40) != 0) {
/*    */         
/* 36 */         if (MCH_Multiplay.markPoint((EntityPlayer)prm.user, prm.posX, prm.posY, prm.posZ))
/*    */         {
/* 38 */           playSound(prm.user);
/*    */         }
/*    */         else
/*    */         {
/* 42 */           playSound(prm.user, "ng");
/*    */         }
/*    */       
/* 45 */       } else if (MCH_Multiplay.spotEntity((EntityLivingBase)prm.user, (MCH_EntityAircraft)prm.entity, prm.posX, prm.posY, prm.posZ, info.target, info.length, info.markTime, info.angle)) {
/*    */ 
/*    */         
/* 48 */         playSound(prm.entity);
/*    */       }
/*    */       else {
/*    */         
/* 52 */         playSound(prm.entity, "ng");
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponTargetingPod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */