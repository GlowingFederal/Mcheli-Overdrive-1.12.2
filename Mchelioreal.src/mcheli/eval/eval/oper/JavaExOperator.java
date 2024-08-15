/*     */ package mcheli.eval.eval.oper;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaExOperator
/*     */   implements Operator
/*     */ {
/*     */   boolean inLong(Object x) {
/*  16 */     if (x instanceof Long)
/*  17 */       return true; 
/*  18 */     if (x instanceof Integer)
/*  19 */       return true; 
/*  20 */     if (x instanceof Short)
/*  21 */       return true; 
/*  22 */     if (x instanceof Byte)
/*  23 */       return true; 
/*  24 */     if (x instanceof BigInteger)
/*  25 */       return true; 
/*  26 */     if (x instanceof BigDecimal)
/*  27 */       return true; 
/*  28 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   long l(Object x) {
/*  33 */     return ((Number)x).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean inDouble(Object x) {
/*  38 */     if (x instanceof Double)
/*  39 */       return true; 
/*  40 */     if (x instanceof Float)
/*  41 */       return true; 
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   double d(Object x) {
/*  47 */     return ((Number)x).doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(long n, Object x) {
/*  52 */     if (x instanceof Long)
/*     */     {
/*  54 */       return new Long(n);
/*     */     }
/*  56 */     if (x instanceof Double)
/*     */     {
/*  58 */       return new Double(n);
/*     */     }
/*  60 */     if (x instanceof Integer)
/*     */     {
/*  62 */       return new Integer((int)n);
/*     */     }
/*  64 */     if (x instanceof Short)
/*     */     {
/*  66 */       return new Short((short)(int)n);
/*     */     }
/*  68 */     if (x instanceof Byte)
/*     */     {
/*  70 */       return new Byte((byte)(int)n);
/*     */     }
/*  72 */     if (x instanceof Float)
/*     */     {
/*  74 */       return new Float((float)n);
/*     */     }
/*  76 */     if (x instanceof BigInteger)
/*     */     {
/*  78 */       return BigInteger.valueOf(n);
/*     */     }
/*  80 */     if (x instanceof BigDecimal)
/*     */     {
/*  82 */       return BigDecimal.valueOf(n);
/*     */     }
/*  84 */     if (x instanceof String)
/*     */     {
/*  86 */       return String.valueOf(n);
/*     */     }
/*  88 */     return new Long(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(long n, Object x, Object y) {
/*  93 */     if (x instanceof Byte || y instanceof Byte)
/*     */     {
/*  95 */       return new Byte((byte)(int)n);
/*     */     }
/*  97 */     if (x instanceof Short || y instanceof Short)
/*     */     {
/*  99 */       return new Short((short)(int)n);
/*     */     }
/* 101 */     if (x instanceof Integer || y instanceof Integer)
/*     */     {
/* 103 */       return new Integer((int)n);
/*     */     }
/* 105 */     if (x instanceof Long || y instanceof Long)
/*     */     {
/* 107 */       return new Long(n);
/*     */     }
/* 109 */     if (x instanceof BigInteger || y instanceof BigInteger)
/*     */     {
/* 111 */       return BigInteger.valueOf(n);
/*     */     }
/* 113 */     if (x instanceof BigDecimal || y instanceof BigDecimal)
/*     */     {
/* 115 */       return BigDecimal.valueOf(n);
/*     */     }
/* 117 */     if (x instanceof Float || y instanceof Float)
/*     */     {
/* 119 */       return new Float((float)n);
/*     */     }
/* 121 */     if (x instanceof Double || y instanceof Double)
/*     */     {
/* 123 */       return new Double(n);
/*     */     }
/* 125 */     if (x instanceof String || y instanceof String)
/*     */     {
/* 127 */       return String.valueOf(n);
/*     */     }
/* 129 */     return new Long(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(double n, Object x) {
/* 134 */     if (x instanceof Float)
/*     */     {
/* 136 */       return new Float(n);
/*     */     }
/* 138 */     if (x instanceof String)
/*     */     {
/* 140 */       return String.valueOf(n);
/*     */     }
/* 142 */     return new Double(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object n(double n, Object x, Object y) {
/* 147 */     if (x instanceof Float || y instanceof Float)
/*     */     {
/* 149 */       return new Float(n);
/*     */     }
/* 151 */     if (x instanceof Number || y instanceof Number)
/*     */     {
/* 153 */       return new Double(n);
/*     */     }
/* 155 */     if (x instanceof String || y instanceof String)
/*     */     {
/* 157 */       return String.valueOf(n);
/*     */     }
/* 159 */     return new Double(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(long n, Object x) {
/* 164 */     if (x instanceof BigDecimal)
/*     */     {
/* 166 */       return BigDecimal.valueOf(n);
/*     */     }
/* 168 */     if (x instanceof BigInteger)
/*     */     {
/* 170 */       return BigInteger.valueOf(n);
/*     */     }
/* 172 */     return new Long(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(long n, Object x, Object y) {
/* 177 */     if (x instanceof BigDecimal || y instanceof BigDecimal)
/*     */     {
/* 179 */       return BigDecimal.valueOf(n);
/*     */     }
/* 181 */     if (x instanceof BigInteger || y instanceof BigInteger)
/*     */     {
/* 183 */       return BigInteger.valueOf(n);
/*     */     }
/* 185 */     return new Long(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(double n, Object x) {
/* 190 */     if (inLong(x))
/*     */     {
/* 192 */       return new Long((long)n);
/*     */     }
/* 194 */     return new Double(n);
/*     */   }
/*     */ 
/*     */   
/*     */   Object nn(double n, Object x, Object y) {
/* 199 */     if (inLong(x) && inLong(y))
/*     */     {
/* 201 */       return new Long((long)n);
/*     */     }
/* 203 */     return new Double(n);
/*     */   }
/*     */ 
/*     */   
/*     */   RuntimeException undefined(Object x) {
/* 208 */     String c = null;
/* 209 */     if (x != null)
/* 210 */       c = x.getClass().getName(); 
/* 211 */     return new RuntimeException("未定義単項演算：" + c);
/*     */   }
/*     */ 
/*     */   
/*     */   RuntimeException undefined(Object x, Object y) {
/* 216 */     String c1 = null;
/* 217 */     String c2 = null;
/* 218 */     if (x != null)
/* 219 */       c1 = x.getClass().getName(); 
/* 220 */     if (y != null)
/* 221 */       c2 = y.getClass().getName(); 
/* 222 */     return new RuntimeException("未定義二項演算：" + c1 + " , " + c2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object power(Object x, Object y) {
/* 228 */     if (x == null && y == null)
/* 229 */       return null; 
/* 230 */     return nn(Math.pow(d(x), d(y)), x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object signPlus(Object x) {
/* 236 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object signMinus(Object x) {
/* 242 */     if (x == null)
/* 243 */       return null; 
/* 244 */     if (inLong(x))
/*     */     {
/* 246 */       return n(-l(x), x);
/*     */     }
/* 248 */     if (inDouble(x))
/*     */     {
/* 250 */       return n(-d(x), x);
/*     */     }
/* 252 */     if (x instanceof Boolean)
/*     */     {
/* 254 */       return x;
/*     */     }
/*     */     
/* 257 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object plus(Object x, Object y) {
/* 263 */     if (x == null && y == null)
/* 264 */       return null; 
/* 265 */     if (inLong(x) && inLong(y))
/*     */     {
/* 267 */       return nn(l(x) + l(y), x, y);
/*     */     }
/* 269 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 271 */       return nn(d(x) + d(y), x, y);
/*     */     }
/* 273 */     if (x instanceof String || y instanceof String)
/*     */     {
/* 275 */       return String.valueOf(x) + String.valueOf(y);
/*     */     }
/* 277 */     if (x instanceof Character || y instanceof Character)
/*     */     {
/* 279 */       return String.valueOf(x) + String.valueOf(y);
/*     */     }
/* 281 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object minus(Object x, Object y) {
/* 287 */     if (x == null && y == null)
/* 288 */       return null; 
/* 289 */     if (inLong(x) && inLong(y))
/*     */     {
/* 291 */       return nn(l(x) - l(y), x, y);
/*     */     }
/* 293 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 295 */       return nn(d(x) - d(y), x, y);
/*     */     }
/* 297 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object mult(Object x, Object y) {
/* 303 */     if (x == null && y == null)
/* 304 */       return null; 
/* 305 */     if (inLong(x) && inLong(y))
/*     */     {
/* 307 */       return nn(l(x) * l(y), x, y);
/*     */     }
/* 309 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 311 */       return nn(d(x) * d(y), x, y);
/*     */     }
/*     */     
/* 314 */     String s = null;
/* 315 */     int ct = 0;
/* 316 */     boolean str = false;
/* 317 */     if (x instanceof String && y instanceof Number) {
/*     */       
/* 319 */       s = (String)x;
/* 320 */       ct = (int)l(y);
/* 321 */       str = true;
/*     */     }
/* 323 */     else if (y instanceof String && x instanceof Number) {
/*     */       
/* 325 */       s = (String)y;
/* 326 */       ct = (int)l(x);
/* 327 */       str = true;
/*     */     } 
/* 329 */     if (str) {
/*     */       
/* 331 */       StringBuffer sb = new StringBuffer(s.length() * ct);
/* 332 */       for (int i = 0; i < ct; i++)
/*     */       {
/* 334 */         sb.append(s);
/*     */       }
/* 336 */       return sb.toString();
/*     */     } 
/*     */     
/* 339 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object div(Object x, Object y) {
/* 345 */     if (x == null && y == null)
/* 346 */       return null; 
/* 347 */     if (inLong(x) && inLong(y))
/*     */     {
/* 349 */       return nn(l(x) / l(y), x);
/*     */     }
/* 351 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 353 */       return nn(d(x) / d(y), x);
/*     */     }
/*     */     
/* 356 */     if (x instanceof String && y instanceof String) {
/*     */       
/* 358 */       String s = (String)x;
/* 359 */       String r = (String)y;
/* 360 */       return s.split(r);
/*     */     } 
/*     */     
/* 363 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object mod(Object x, Object y) {
/* 369 */     if (x == null && y == null)
/* 370 */       return null; 
/* 371 */     if (inLong(x) && inLong(y))
/*     */     {
/* 373 */       return nn(l(x) % l(y), x);
/*     */     }
/* 375 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 377 */       return nn(d(x) % d(y), x);
/*     */     }
/* 379 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitNot(Object x) {
/* 385 */     if (x == null)
/* 386 */       return null; 
/* 387 */     if (x instanceof Number)
/*     */     {
/* 389 */       return n(l(x) ^ 0xFFFFFFFFFFFFFFFFL, x);
/*     */     }
/* 391 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftLeft(Object x, Object y) {
/* 397 */     if (x == null && y == null)
/* 398 */       return null; 
/* 399 */     if (inLong(x) && inLong(y))
/*     */     {
/* 401 */       return n(l(x) << (int)l(y), x);
/*     */     }
/* 403 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 405 */       return n(d(x) * Math.pow(2.0D, d(y)), x);
/*     */     }
/* 407 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftRight(Object x, Object y) {
/* 413 */     if (x == null && y == null)
/* 414 */       return null; 
/* 415 */     if (inLong(x) && inLong(y))
/*     */     {
/* 417 */       return n(l(x) >> (int)l(y), x);
/*     */     }
/* 419 */     if (inDouble(x) && inDouble(y))
/*     */     {
/* 421 */       return n(d(x) / Math.pow(2.0D, d(y)), x);
/*     */     }
/* 423 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object shiftRightLogical(Object x, Object y) {
/* 429 */     if (x == null && y == null)
/* 430 */       return null; 
/* 431 */     if (x instanceof Byte && y instanceof Number)
/*     */     {
/* 433 */       return n((l(x) & 0xFFL) >>> (int)l(y), x);
/*     */     }
/* 435 */     if (x instanceof Short && y instanceof Number)
/*     */     {
/* 437 */       return n((l(x) & 0xFFFFL) >>> (int)l(y), x);
/*     */     }
/* 439 */     if (x instanceof Integer && y instanceof Number)
/*     */     {
/* 441 */       return n((l(x) & 0xFFFFFFFFFFFFFFFFL) >>> (int)l(y), x);
/*     */     }
/* 443 */     if (inLong(x) && y instanceof Number)
/*     */     {
/* 445 */       return n(l(x) >>> (int)l(y), x);
/*     */     }
/* 447 */     if (inDouble(x) && y instanceof Number) {
/*     */       
/* 449 */       double t = d(x);
/* 450 */       if (t < 0.0D)
/* 451 */         t = -t; 
/* 452 */       return n(t / Math.pow(2.0D, d(y)), x);
/*     */     } 
/* 454 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitAnd(Object x, Object y) {
/* 460 */     if (x == null && y == null)
/* 461 */       return null; 
/* 462 */     if (x instanceof Number && y instanceof Number)
/*     */     {
/* 464 */       return n(l(x) & l(y), x);
/*     */     }
/* 466 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitOr(Object x, Object y) {
/* 472 */     if (x == null && y == null)
/* 473 */       return null; 
/* 474 */     if (x instanceof Number && y instanceof Number)
/*     */     {
/* 476 */       return n(l(x) | l(y), x);
/*     */     }
/* 478 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bitXor(Object x, Object y) {
/* 484 */     if (x == null && y == null)
/* 485 */       return null; 
/* 486 */     if (x instanceof Number && y instanceof Number)
/*     */     {
/* 488 */       return n(l(x) ^ l(y), x);
/*     */     }
/* 490 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object not(Object x) {
/* 496 */     if (x == null)
/* 497 */       return null; 
/* 498 */     if (x instanceof Boolean)
/*     */     {
/* 500 */       return ((Boolean)x).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
/*     */     }
/* 502 */     if (x instanceof Number) {
/*     */       
/* 504 */       if (l(x) == 0L)
/*     */       {
/* 506 */         return Boolean.TRUE;
/*     */       }
/* 508 */       return Boolean.FALSE;
/*     */     } 
/*     */     
/* 511 */     throw undefined(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int compare(Object x, Object y) {
/* 520 */     if (x == null && y == null)
/* 521 */       return 0; 
/* 522 */     if (x == null && y != null)
/* 523 */       return -1; 
/* 524 */     if (x != null && y == null)
/*     */     {
/* 526 */       return 1;
/*     */     }
/* 528 */     if (inLong(x) && inLong(y)) {
/*     */       
/* 530 */       long c = l(x) - l(y);
/* 531 */       if (c == 0L)
/* 532 */         return 0; 
/* 533 */       if (c < 0L)
/*     */       {
/* 535 */         return -1;
/*     */       }
/* 537 */       return 1;
/*     */     } 
/* 539 */     if (x instanceof Number && y instanceof Number) {
/*     */       
/* 541 */       double n = d(x) - d(y);
/* 542 */       if (n == 0.0D)
/* 543 */         return 0; 
/* 544 */       return (n < 0.0D) ? -1 : 1;
/*     */     } 
/*     */     
/* 547 */     Class<?> xc = x.getClass();
/* 548 */     Class<?> yc = y.getClass();
/* 549 */     if (xc.isAssignableFrom(yc) && x instanceof Comparable)
/*     */     {
/* 551 */       return ((Comparable<Object>)x).compareTo(y);
/*     */     }
/* 553 */     if (yc.isAssignableFrom(xc) && y instanceof Comparable)
/*     */     {
/* 555 */       return -((Comparable)y).compareTo((T)x);
/*     */     }
/* 557 */     if (x.equals(y))
/* 558 */       return 0; 
/* 559 */     throw undefined(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object equal(Object x, Object y) {
/* 565 */     return (compare(x, y) == 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object notEqual(Object x, Object y) {
/* 571 */     return (compare(x, y) != 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lessThan(Object x, Object y) {
/* 577 */     return (compare(x, y) < 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lessEqual(Object x, Object y) {
/* 583 */     return (compare(x, y) <= 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object greaterThan(Object x, Object y) {
/* 589 */     return (compare(x, y) > 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object greaterEqual(Object x, Object y) {
/* 595 */     return (compare(x, y) >= 0) ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean bool(Object x) {
/* 601 */     if (x == null)
/* 602 */       return false; 
/* 603 */     if (x instanceof Boolean)
/* 604 */       return ((Boolean)x).booleanValue(); 
/* 605 */     if (x instanceof Number)
/* 606 */       return (((Number)x).longValue() != 0L); 
/* 607 */     return Boolean.valueOf(x.toString()).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object inc(Object x, int inc) {
/* 613 */     if (x == null)
/* 614 */       return null; 
/* 615 */     if (inLong(x))
/*     */     {
/* 617 */       return n(l(x) + inc, x);
/*     */     }
/* 619 */     if (inDouble(x))
/*     */     {
/* 621 */       return n(d(x) + inc, x);
/*     */     }
/* 623 */     throw undefined(x);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\oper\JavaExOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */