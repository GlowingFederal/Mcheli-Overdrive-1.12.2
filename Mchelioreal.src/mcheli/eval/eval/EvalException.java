/*     */ package mcheli.eval.eval;
/*     */ 
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EvalException
/*     */   extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 4174576689426433715L;
/*     */   public static final int PARSE_NOT_FOUND_END_OP = 1001;
/*     */   public static final int PARSE_INVALID_OP = 1002;
/*     */   public static final int PARSE_INVALID_CHAR = 1003;
/*     */   public static final int PARSE_END_OF_STR = 1004;
/*     */   public static final int PARSE_STILL_EXIST = 1005;
/*     */   public static final int PARSE_NOT_FUNC = 1101;
/*     */   public static final int EXP_FORBIDDEN_CALL = 2001;
/*     */   public static final int EXP_NOT_VARIABLE = 2002;
/*     */   public static final int EXP_NOT_NUMBER = 2003;
/*     */   public static final int EXP_NOT_LET = 2004;
/*     */   public static final int EXP_NOT_VAR_VALUE = 2101;
/*     */   public static final int EXP_NOT_LET_VAR = 2102;
/*     */   public static final int EXP_NOT_DEF_VAR = 2103;
/*     */   public static final int EXP_NOT_DEF_OBJ = 2104;
/*     */   public static final int EXP_NOT_ARR_VALUE = 2201;
/*     */   public static final int EXP_NOT_LET_ARR = 2202;
/*     */   public static final int EXP_NOT_FLD_VALUE = 2301;
/*     */   public static final int EXP_NOT_LET_FIELD = 2302;
/*     */   public static final int EXP_FUNC_CALL_ERROR = 2401;
/*     */   protected int msg_code;
/*     */   protected String[] msg_opt;
/*     */   protected String string;
/*  36 */   protected int pos = -1;
/*     */   
/*     */   protected String word;
/*     */ 
/*     */   
/*     */   public EvalException(int msg, Lex lex) {
/*  42 */     this(msg, null, lex);
/*     */   }
/*     */ 
/*     */   
/*     */   public EvalException(int msg, String[] opt, Lex lex) {
/*  47 */     this.msg_code = msg;
/*  48 */     this.msg_opt = opt;
/*  49 */     if (lex != null) {
/*     */       
/*  51 */       this.string = lex.getString();
/*  52 */       this.pos = lex.getPos();
/*  53 */       this.word = lex.getWord();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EvalException(int msg, String word, String string, int pos, Throwable e) {
/*  59 */     while (e != null && e.getClass() == RuntimeException.class && e.getCause() != null)
/*     */     {
/*  61 */       e = e.getCause();
/*     */     }
/*     */     
/*  64 */     if (e != null)
/*  65 */       initCause(e); 
/*  66 */     this.msg_code = msg;
/*  67 */     this.string = string;
/*  68 */     this.pos = pos;
/*  69 */     this.word = word;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getErrorCode() {
/*  74 */     return this.msg_code;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getOption() {
/*  79 */     return this.msg_opt;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWord() {
/*  84 */     return this.word;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/*  89 */     return this.string;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPos() {
/*  94 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getErrCodeMessage(int code) {
/*  99 */     switch (code) {
/*     */       
/*     */       case 1001:
/* 102 */         return "演算子「%0」が在りません。";
/*     */       case 1002:
/* 104 */         return "演算子の文法エラーです。";
/*     */       case 1003:
/* 106 */         return "未対応の識別子です。";
/*     */       case 1004:
/* 108 */         return "式の解釈の途中で文字列が終了しています。";
/*     */       case 1005:
/* 110 */         return "式の解釈が終わりましたが文字列が残っています。";
/*     */       case 1101:
/* 112 */         return "関数として使用できません。";
/*     */       case 2001:
/* 114 */         return "禁止されているメソッドを呼び出しました。";
/*     */       case 2002:
/* 116 */         return "変数として使用できません。";
/*     */       case 2003:
/* 118 */         return "数値として使用できません。";
/*     */       case 2004:
/* 120 */         return "代入できません。";
/*     */       case 2101:
/* 122 */         return "変数の値が取得できません。";
/*     */       case 2102:
/* 124 */         return "変数に代入できません。";
/*     */       case 2103:
/* 126 */         return "変数が未定義です。";
/*     */       case 2104:
/* 128 */         return "オブジェクトが未定義です。";
/*     */       case 2201:
/* 130 */         return "配列の値が取得できません。";
/*     */       case 2202:
/* 132 */         return "配列に代入できません。";
/*     */       case 2301:
/* 134 */         return "フィールドの値が取得できません。";
/*     */       case 2302:
/* 136 */         return "フィールドに代入できません。";
/*     */       case 2401:
/* 138 */         return "関数の呼び出しに失敗しました。";
/*     */     } 
/* 140 */     return "エラーが発生しました。";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultFormat(String msgFmt) {
/* 145 */     StringBuffer fmt = new StringBuffer(128);
/* 146 */     fmt.append(msgFmt);
/*     */     
/* 148 */     boolean bWord = false;
/* 149 */     if (this.word != null && this.word.length() > 0) {
/*     */       
/* 151 */       bWord = true;
/* 152 */       if (this.word.equals(this.string))
/* 153 */         bWord = false; 
/*     */     } 
/* 155 */     if (bWord)
/*     */     {
/* 157 */       fmt.append(" word=「%w」");
/*     */     }
/*     */     
/* 160 */     if (this.pos >= 0)
/*     */     {
/* 162 */       fmt.append(" pos=%p");
/*     */     }
/* 164 */     if (this.string != null)
/*     */     {
/* 166 */       fmt.append(" string=「%s」");
/*     */     }
/* 168 */     if (getCause() != null)
/*     */     {
/* 170 */       fmt.append(" cause by %e");
/*     */     }
/*     */     
/* 173 */     return fmt.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 179 */     String msg = getErrCodeMessage(this.msg_code);
/* 180 */     String fmt = getDefaultFormat(msg);
/* 181 */     return toString(fmt);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(String fmt) {
/* 186 */     StringBuffer sb = new StringBuffer(256);
/* 187 */     int len = fmt.length();
/* 188 */     for (int i = 0; i < len; i++) {
/*     */       
/* 190 */       char c = fmt.charAt(i);
/* 191 */       if (c != '%') {
/*     */         
/* 193 */         sb.append(c);
/*     */       } else {
/*     */         int n;
/*     */         
/* 197 */         if (i + 1 >= len) {
/*     */           
/* 199 */           sb.append(c);
/*     */           break;
/*     */         } 
/* 202 */         c = fmt.charAt(++i);
/* 203 */         switch (c) {
/*     */           
/*     */           case '0':
/*     */           case '1':
/*     */           case '2':
/*     */           case '3':
/*     */           case '4':
/*     */           case '5':
/*     */           case '6':
/*     */           case '7':
/*     */           case '8':
/*     */           case '9':
/* 215 */             n = c - 48;
/* 216 */             if (this.msg_opt != null && n < this.msg_opt.length)
/*     */             {
/* 218 */               sb.append(this.msg_opt[n]);
/*     */             }
/*     */             break;
/*     */           case 'c':
/* 222 */             sb.append(this.msg_code);
/*     */             break;
/*     */           case 'w':
/* 225 */             sb.append(this.word);
/*     */             break;
/*     */           case 'p':
/* 228 */             sb.append(this.pos);
/*     */             break;
/*     */           case 's':
/* 231 */             sb.append(this.string);
/*     */             break;
/*     */           case 'e':
/* 234 */             sb.append(getCause());
/*     */             break;
/*     */           case '%':
/* 237 */             sb.append('%');
/*     */             break;
/*     */           default:
/* 240 */             sb.append('%');
/* 241 */             sb.append(c);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 246 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\EvalException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */