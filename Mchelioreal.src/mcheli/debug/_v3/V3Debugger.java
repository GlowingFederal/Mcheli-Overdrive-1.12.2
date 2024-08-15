/*     */ package mcheli.debug._v3;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public class V3Debugger
/*     */ {
/*  20 */   public static final Stater TOGGLE_I = new Stater(24);
/*  21 */   public static final Numeric NUM_X = new Numeric("TX", 203, 205, 0.0625D);
/*  22 */   public static final Numeric NUM_Y = new Numeric("TY", 200, 208, 0.0625D);
/*  23 */   public static final Numeric ROT_X = new Numeric("RX", 36, 37, 5.0D);
/*  24 */   public static final Numeric ROT_Y = new Numeric("RY", 49, 50, 5.0D);
/*  25 */   public static final Numeric ROT_Z = new Numeric("RZ", 22, 23, 5.0D);
/*     */ 
/*     */   
/*     */   private static boolean tickOnce;
/*     */ 
/*     */   
/*     */   static void onClient(TickEvent.ClientTickEvent event) {
/*  32 */     if (event.phase == TickEvent.Phase.END) {
/*     */       
/*  34 */       Arrays.fill(KeyStater.tickChunk, false);
/*  35 */       tickOnce = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkTick() {
/*  41 */     if (!tickOnce) {
/*     */       
/*  43 */       tickOnce = true;
/*  44 */       return true;
/*     */     } 
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static class KeyStater
/*     */   {
/*  52 */     static boolean[] tickChunk = new boolean[256];
/*     */     final int key;
/*     */     private boolean down;
/*     */     private boolean chunk;
/*     */     
/*     */     public KeyStater(int key) {
/*  58 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean press() {
/*  63 */       if (!tickChunk[this.key]) {
/*     */         
/*  65 */         boolean flag = keydown();
/*  66 */         boolean flag1 = this.chunk;
/*  67 */         this.chunk = flag;
/*  68 */         this.down = (flag && !flag1);
/*  69 */         tickChunk[this.key] = true;
/*  70 */         return this.down;
/*     */       } 
/*     */       
/*  73 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean keydown() {
/*  78 */       return Keyboard.isKeyDown(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String keyname() {
/*  83 */       return Keyboard.getKeyName(this.key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Stater
/*     */   {
/*     */     final V3Debugger.KeyStater key;
/*     */     boolean state;
/*     */     
/*     */     public Stater(int key) {
/*  94 */       this.key = new V3Debugger.KeyStater(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean state() {
/*  99 */       if (this.key.press()) {
/*     */         
/* 101 */         this.state = !this.state;
/* 102 */         V3Debugger.info("Key " + this.key.keydown() + ".state : " + this.state);
/*     */       } 
/* 104 */       return this.state;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Numeric
/*     */   {
/*     */     final V3Debugger.KeyStater incKey;
/*     */     final V3Debugger.KeyStater decKey;
/*     */     String name;
/*     */     double num;
/*     */     final double dif;
/*     */     
/*     */     public Numeric(String name, int decKey, int incKey, double dif) {
/* 117 */       this.name = name;
/* 118 */       this.decKey = new V3Debugger.KeyStater(decKey);
/* 119 */       this.incKey = new V3Debugger.KeyStater(incKey);
/* 120 */       this.dif = dif;
/*     */     }
/*     */ 
/*     */     
/*     */     public double value() {
/* 125 */       if (this.incKey.press()) {
/*     */         
/* 127 */         this.num += this.dif;
/* 128 */         V3Debugger.info("Num " + this.name + ".value : " + this.num);
/*     */       } 
/* 130 */       if (this.decKey.press()) {
/*     */         
/* 132 */         this.num -= this.dif;
/* 133 */         V3Debugger.info("Num " + this.name + ".value : " + this.num);
/*     */       } 
/*     */       
/* 136 */       return this.num;
/*     */     }
/*     */ 
/*     */     
/*     */     public float valueFloat() {
/* 141 */       return (float)value();
/*     */     }
/*     */ 
/*     */     
/*     */     public int valueInt() {
/* 146 */       return (int)value();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void info(Object o) {
/* 152 */     MCH_Utils.logger().info(o);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v3\V3Debugger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */