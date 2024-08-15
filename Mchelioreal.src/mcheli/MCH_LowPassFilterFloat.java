/*    */ package mcheli;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_LowPassFilterFloat
/*    */ {
/*    */   private MCH_Queue<Float> filter;
/*    */   
/*    */   public MCH_LowPassFilterFloat(int filterLength) {
/* 15 */     this.filter = new MCH_Queue<>(filterLength, Float.valueOf(0.0F));
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 20 */     this.filter.clear(Float.valueOf(0.0F));
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(float t) {
/* 25 */     this.filter.put(Float.valueOf(t));
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAvg() {
/* 30 */     float f = 0.0F;
/*    */     
/* 32 */     for (int i = 0; i < this.filter.size(); i++)
/*    */     {
/* 34 */       f += ((Float)this.filter.get(i)).floatValue();
/*    */     }
/*    */     
/* 37 */     return f / this.filter.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_LowPassFilterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */