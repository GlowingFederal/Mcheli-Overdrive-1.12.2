/*    */ package mcheli;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_CheckTime
/*    */ {
/* 13 */   private long startTime = 0L;
/* 14 */   public int x = 0;
/* 15 */   private int y = 0;
/* 16 */   public long[][] pointTimeList = new long[1][1];
/* 17 */   public int MAX_Y = 0;
/* 18 */   private int MAX_X = 0;
/*    */ 
/*    */   
/*    */   public MCH_CheckTime() {
/* 22 */     this.MAX_Y = 100;
/* 23 */     this.MAX_X = 40;
/* 24 */     this.pointTimeList = new long[this.MAX_Y + 1][this.MAX_X];
/* 25 */     this.y = this.MAX_Y - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 30 */     this.startTime = System.nanoTime();
/* 31 */     this.x = 0;
/* 32 */     this.y = (this.y + 1) % this.MAX_Y;
/*    */     
/* 34 */     if (this.y == 0)
/*    */     {
/* 36 */       for (int j = 0; j < this.MAX_X; j++) {
/*    */         
/* 38 */         this.pointTimeList[this.MAX_Y][j] = 0L;
/* 39 */         for (int i = 0; i < this.MAX_Y; i++)
/*    */         {
/* 41 */           this.pointTimeList[this.MAX_Y][j] = this.pointTimeList[this.MAX_Y][j] + this.pointTimeList[i][j];
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void timeStamp() {
/* 49 */     if (this.x < this.MAX_X) {
/*    */       
/* 51 */       this.pointTimeList[this.y][this.x] = System.nanoTime() - this.startTime;
/* 52 */       this.x++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_CheckTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */