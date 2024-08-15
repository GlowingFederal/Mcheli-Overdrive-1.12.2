/*    */ package mcheli;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_OStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/* 15 */   public int index = 0;
/*    */ 
/*    */   
/*    */   public static final int SIZE = 30720;
/*    */ 
/*    */   
/*    */   public void write(DataOutputStream dos) {
/*    */     try {
/*    */       int datasize;
/* 24 */       if (this.index + 30720 <= size()) {
/*    */         
/* 26 */         datasize = 30720;
/*    */       }
/*    */       else {
/*    */         
/* 30 */         datasize = size() - this.index;
/*    */       } 
/*    */       
/* 33 */       dos.writeInt(this.index);
/* 34 */       dos.writeInt(datasize);
/* 35 */       dos.writeInt(size());
/* 36 */       dos.write(this.buf, this.index, datasize);
/* 37 */       this.index += datasize;
/*    */     }
/* 39 */     catch (IOException e) {
/*    */       
/* 41 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDataEnd() {
/* 47 */     return (this.index >= size());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_OStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */