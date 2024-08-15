/*    */ package mcheli.__helper.info;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentParseException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 4338814389788695295L;
/*    */   private int lineNo;
/*    */   
/*    */   public ContentParseException(int lineNo) {
/* 17 */     this.lineNo = lineNo;
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentParseException(String msg, int lineNo) {
/* 22 */     super(msg);
/* 23 */     this.lineNo = lineNo;
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentParseException(Throwable cause, int lineNo) {
/* 28 */     super(cause);
/* 29 */     this.lineNo = lineNo;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLineNo() {
/* 34 */     return this.lineNo;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */