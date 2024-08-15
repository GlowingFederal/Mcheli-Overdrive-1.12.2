/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import mcheli.MCH_Vector2;
/*    */ import mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Radar
/*    */ {
/*    */   private World worldObj;
/* 22 */   private ArrayList<MCH_Vector2> entityList = new ArrayList<>();
/* 23 */   private ArrayList<MCH_Vector2> enemyList = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public ArrayList<MCH_Vector2> getEntityList() {
/* 27 */     return this.entityList;
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<MCH_Vector2> getEnemyList() {
/* 32 */     return this.enemyList;
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_Radar(World world) {
/* 37 */     this.worldObj = world;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 42 */     this.entityList.clear();
/* 43 */     this.enemyList.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateXZ(Entity centerEntity, int range) {
/* 48 */     if (!this.worldObj.field_72995_K) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 53 */     clear();
/*    */     
/* 55 */     List<Entity> list = centerEntity.field_70170_p.func_72839_b(centerEntity, centerEntity
/*    */         
/* 57 */         .func_174813_aQ().func_72314_b(range, range, range));
/*    */     
/* 59 */     for (int i = 0; i < list.size(); i++) {
/*    */       
/* 61 */       Entity entity = list.get(i);
/*    */       
/* 63 */       if (entity instanceof net.minecraft.entity.EntityLiving) {
/*    */         
/* 65 */         double x = entity.field_70165_t - centerEntity.field_70165_t;
/* 66 */         double z = entity.field_70161_v - centerEntity.field_70161_v;
/*    */         
/* 68 */         if (x * x + z * z < (range * range)) {
/*    */           
/* 70 */           int y = 1 + (int)entity.field_70163_u;
/* 71 */           if (y < 0) {
/* 72 */             y = 1;
/*    */           }
/* 74 */           int blockCnt = 0;
/*    */           
/* 76 */           for (; y < 200; y++) {
/*    */             
/* 78 */             if (W_WorldFunc.getBlockId(this.worldObj, (int)entity.field_70165_t, y, (int)entity.field_70161_v) != 0) {
/*    */               
/* 80 */               blockCnt++;
/*    */               
/* 82 */               if (blockCnt >= 5) {
/*    */                 break;
/*    */               }
/*    */             } 
/*    */           } 
/*    */ 
/*    */           
/* 89 */           if (blockCnt < 5)
/*    */           {
/* 91 */             if (entity instanceof net.minecraft.entity.monster.EntityMob) {
/*    */               
/* 93 */               this.enemyList.add(new MCH_Vector2(x, z));
/*    */             }
/*    */             else {
/*    */               
/* 97 */               this.entityList.add(new MCH_Vector2(x, z));
/*    */             } 
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_Radar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */