/*    */ package mcheli;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Queue<T>
/*    */ {
/*    */   private int current;
/*    */   private List<T> list;
/*    */   
/*    */   public MCH_Queue(int filterLength, T initVal) {
/* 20 */     if (filterLength <= 0)
/*    */     {
/* 22 */       filterLength = 1;
/*    */     }
/* 24 */     this.list = new ArrayList<>();
/* 25 */     for (int i = 0; i < filterLength; i++)
/* 26 */       this.list.add(initVal); 
/* 27 */     this.current = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear(T clearVal) {
/* 32 */     for (int i = 0; i < size(); i++) {
/* 33 */       this.list.set(i, clearVal);
/*    */     }
/*    */   }
/*    */   
/*    */   public void put(T t) {
/* 38 */     this.list.set(this.current, t);
/*    */     
/* 40 */     this.current++;
/* 41 */     this.current %= size();
/*    */   }
/*    */ 
/*    */   
/*    */   private int getIndex(int offset) {
/* 46 */     offset %= size();
/* 47 */     int index = this.current + offset;
/* 48 */     if (index < 0)
/* 49 */       return index + size(); 
/* 50 */     return index % size();
/*    */   }
/*    */ 
/*    */   
/*    */   public T oldest() {
/* 55 */     return this.list.get(getIndex(1));
/*    */   }
/*    */ 
/*    */   
/*    */   public T get(int i) {
/* 60 */     return this.list.get(i);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 65 */     return this.list.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Queue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */