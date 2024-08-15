/*     */ package mcheli.eval.eval.lex;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.ShareExpValue;
/*     */ import mcheli.eval.util.CharUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Lex
/*     */ {
/*     */   protected List<String>[] opeList;
/*     */   protected String string;
/*  19 */   protected int pos = 0;
/*     */   
/*  21 */   protected int len = 0;
/*     */   
/*  23 */   protected int type = -1;
/*     */   
/*     */   public static final int TYPE_WORD = 2147483632;
/*     */   
/*     */   public static final int TYPE_NUM = 2147483633;
/*     */   
/*     */   public static final int TYPE_OPE = 2147483634;
/*     */   
/*     */   public static final int TYPE_STRING = 2147483635;
/*     */   
/*     */   public static final int TYPE_CHAR = 2147483636;
/*     */   
/*     */   public static final int TYPE_EOF = 2147483647;
/*     */   
/*     */   public static final int TYPE_ERR = -1;
/*     */   
/*     */   protected String ope;
/*     */   protected ShareExpValue expShare;
/*     */   protected String SPC_CHAR;
/*     */   protected String NUMBER_CHAR;
/*     */   
/*     */   protected boolean isSpace(int pos) {
/*     */     if (pos >= this.string.length()) {
/*     */       return true;
/*     */     }
/*     */     char c = this.string.charAt(pos);
/*     */     return (this.SPC_CHAR.indexOf(c) >= 0);
/*     */   }
/*     */   
/*     */   protected Lex(String str, List<String>[] lists, AbstractExpression paren, ShareExpValue exp)
/*     */   {
/*  54 */     this.SPC_CHAR = " \t\r\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     this.NUMBER_CHAR = "._"; this.string = str;
/*     */     this.opeList = lists;
/*     */     this.expShare = exp;
/*     */     if (this.expShare.paren == null)
/*  76 */       this.expShare.paren = paren;  } protected boolean isSpecialNumber(int pos) { if (pos >= this.string.length())
/*  77 */       return false; 
/*  78 */     char c = this.string.charAt(pos);
/*  79 */     return (this.NUMBER_CHAR.indexOf(c) >= 0); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String isOperator(int pos) {
/*  84 */     for (int i = this.opeList.length - 1; i >= 0; i--) {
/*     */       
/*  86 */       if (pos + i < this.string.length()) {
/*     */         
/*  88 */         List<String> list = this.opeList[i];
/*  89 */         if (list != null)
/*  90 */           for (int j = 0; j < list.size(); j++) {
/*     */             
/*  92 */             String ope = list.get(j);
/*  93 */             int k = 0;
/*     */             
/*     */             while (true) {
/*  96 */               if (k <= i) {
/*     */                 
/*  98 */                 char c = this.string.charAt(pos + k);
/*  99 */                 char o = ope.charAt(k);
/* 100 */                 if (c != o)
/*     */                   break; 
/* 102 */                 k++;
/*     */                 continue;
/*     */               } 
/* 105 */               return ope;
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     } 
/* 110 */     return null; }
/*     */   protected boolean isNumberTop(int pos) { if (pos >= this.string.length())
/*     */       return false; 
/*     */     char c = this.string.charAt(pos);
/*     */     return ('0' <= c && c <= '9'); } protected boolean isStringTop(int pos) {
/* 115 */     if (pos >= this.string.length())
/* 116 */       return false; 
/* 117 */     char c = this.string.charAt(pos);
/* 118 */     return (c == '"');
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isStringEnd(int pos) {
/* 123 */     return isStringTop(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCharTop(int pos) {
/* 128 */     if (pos >= this.string.length())
/* 129 */       return false; 
/* 130 */     char c = this.string.charAt(pos);
/* 131 */     return (c == '\'');
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCharEnd(int pos) {
/* 136 */     return isCharTop(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check() {
/* 143 */     for (; isSpace(this.pos); this.pos++) {
/*     */       
/* 145 */       if (this.pos >= this.string.length()) {
/*     */         
/* 147 */         this.type = Integer.MAX_VALUE;
/* 148 */         this.len = 0;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 153 */     if (isStringTop(this.pos)) {
/*     */       
/* 155 */       processString();
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     if (isCharTop(this.pos)) {
/*     */       
/* 161 */       processChar();
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     String ope = isOperator(this.pos);
/* 166 */     if (ope != null) {
/*     */       
/* 168 */       this.type = 2147483634;
/* 169 */       this.ope = ope;
/* 170 */       this.len = ope.length();
/*     */       
/*     */       return;
/*     */     } 
/* 174 */     boolean number = isNumberTop(this.pos);
/* 175 */     this.type = number ? 2147483633 : 2147483632;
/* 176 */     for (this.len = 1; !isSpace(this.pos + this.len); this.len++) {
/*     */ 
/*     */       
/* 179 */       if ((!number || !isSpecialNumber(this.pos + this.len)) && 
/*     */         
/* 181 */         isOperator(this.pos + this.len) != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processString() {
/* 188 */     int[] ret = new int[1];
/* 189 */     this.type = 2147483635;
/* 190 */     this.len = 1;
/*     */     
/*     */     do {
/* 193 */       this.len += getCharLen(this.pos + this.len, ret);
/* 194 */       if (this.pos + this.len >= this.string.length()) {
/*     */         
/* 196 */         this.type = Integer.MAX_VALUE;
/*     */         
/*     */         break;
/*     */       } 
/* 200 */     } while (!isStringEnd(this.pos + this.len));
/* 201 */     this.len++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChar() {
/* 207 */     int[] ret = new int[1];
/* 208 */     this.type = 2147483636;
/* 209 */     this.len = 1;
/*     */     
/*     */     do {
/* 212 */       this.len += getCharLen(this.pos + this.len, ret);
/* 213 */       if (this.pos + this.len >= this.string.length()) {
/*     */         
/* 215 */         this.type = Integer.MAX_VALUE;
/*     */         
/*     */         break;
/*     */       } 
/* 219 */     } while (!isCharEnd(this.pos + this.len));
/* 220 */     this.len++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getCharLen(int pos, int[] ret) {
/* 226 */     CharUtil.escapeChar(this.string, pos, this.string.length(), ret);
/* 227 */     return ret[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public Lex next() {
/* 232 */     this.pos += this.len;
/* 233 */     check();
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 239 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOperator() {
/* 244 */     return this.ope;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperator(String ope) {
/* 249 */     if (this.type == 2147483634)
/* 250 */       return this.ope.equals(ope); 
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWord() {
/* 256 */     return this.string.substring(this.pos, this.pos + this.len);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/* 261 */     return this.string;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPos() {
/* 266 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShareExpValue getShare() {
/* 271 */     return this.expShare;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\lex\Lex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */