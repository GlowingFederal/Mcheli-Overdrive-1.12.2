/*    */ package mcheli.__helper.addon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuiltinAddonPack
/*    */   extends AddonPack
/*    */ {
/* 17 */   private static BuiltinAddonPack instance = null;
/*    */ 
/*    */   
/*    */   public static BuiltinAddonPack instance() {
/* 21 */     if (instance == null)
/*    */     {
/* 23 */       instance = new BuiltinAddonPack();
/*    */     }
/*    */     
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   private BuiltinAddonPack() {
/* 31 */     super("@builtin", "MCHeli-Builtin", "1.0", null, "EMB4-MCHeli", 
/* 32 */         (List<String>)ImmutableList.of("EMB4", "Murachiki27"), "Builtin addon", "1", ImmutableMap.of());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public File getFile() {
/* 38 */     return MCH_Utils.getSource();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\BuiltinAddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */