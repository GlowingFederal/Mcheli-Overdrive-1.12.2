/*     */ package mcheli.eval.eval.var;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapVariable
/*     */   implements Variable
/*     */ {
/*     */   protected Map<Object, Object> map;
/*     */   
/*     */   public MapVariable() {
/*  20 */     this(new HashMap<>());
/*     */   }
/*     */ 
/*     */   
/*     */   public MapVariable(Map<Object, Object> varMap) {
/*  25 */     this.map = varMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMap(Map<Object, Object> varMap) {
/*  30 */     this.map = varMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Object, Object> getMap() {
/*  35 */     return this.map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object name, Object obj) {
/*  41 */     this.map.put(name, obj);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject(Object name) {
/*  47 */     return this.map.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong(Object val) {
/*  53 */     return ((Number)val).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble(Object val) {
/*  59 */     return ((Number)val).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject(Object array, int index) {
/*  65 */     return Array.get(array, index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object array, int index, Object val) {
/*  71 */     Array.set(array, index, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject(Object obj, String field) {
/*     */     try {
/*  79 */       Class<?> c = obj.getClass();
/*  80 */       Field f = c.getField(field);
/*  81 */       return f.get(obj);
/*     */     }
/*  83 */     catch (RuntimeException e) {
/*     */       
/*  85 */       throw e;
/*     */     }
/*  87 */     catch (Exception e) {
/*     */       
/*  89 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object obj, String field, Object val) {
/*     */     try {
/*  98 */       Class<?> c = obj.getClass();
/*  99 */       Field f = c.getField(field);
/* 100 */       f.set(obj, val);
/*     */     }
/* 102 */     catch (RuntimeException e) {
/*     */       
/* 104 */       throw e;
/*     */     }
/* 106 */     catch (Exception e) {
/*     */       
/* 108 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\var\MapVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */