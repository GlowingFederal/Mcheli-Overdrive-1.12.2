/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WordExpression
/*     */   extends AbstractExpression
/*     */ {
/*     */   protected String word;
/*     */   
/*     */   protected WordExpression(String str) {
/*  15 */     this.word = str;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WordExpression(WordExpression from, ShareExpValue s) {
/*  20 */     super(from, s);
/*  21 */     this.word = from.word;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getWord() {
/*  27 */     return this.word;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setWord(String word) {
/*  33 */     this.word = word;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getCols() {
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getFirstPos() {
/*  45 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void search() {
/*  51 */     this.share.srch.search(this);
/*  52 */     if (this.share.srch.end())
/*     */       return; 
/*  54 */     this.share.srch.search0(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/*  60 */     return this.share.repl.replace0(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/*  66 */     return this.share.repl.replaceVar0(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  72 */     if (obj instanceof WordExpression) {
/*     */       
/*  74 */       WordExpression e = (WordExpression)obj;
/*  75 */       if (getClass() == e.getClass())
/*     */       {
/*  77 */         return this.word.equals(e.word);
/*     */       }
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  86 */     return this.word.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/*  92 */     StringBuffer sb = new StringBuffer();
/*  93 */     for (int i = 0; i < n; i++)
/*     */     {
/*  95 */       sb.append(' ');
/*     */     }
/*  97 */     sb.append(this.word);
/*  98 */     System.out.println(sb.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return this.word;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\WordExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */