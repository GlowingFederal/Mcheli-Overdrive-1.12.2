/*    */ package mcheli;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_DamageFactor
/*    */ {
/* 16 */   private HashMap<Class<? extends Entity>, Float> map = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void clear() {
/* 20 */     this.map.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(Class<? extends Entity> c, float value) {
/* 26 */     this.map.put(c, Float.valueOf(value));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getDamageFactor(Class<? extends Entity> c) {
/* 32 */     if (this.map.containsKey(c))
/*    */     {
/* 34 */       return ((Float)this.map.get(c)).floatValue();
/*    */     }
/* 36 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDamageFactor(Entity e) {
/* 41 */     return (e != null) ? getDamageFactor((Class)e.getClass()) : 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_DamageFactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */