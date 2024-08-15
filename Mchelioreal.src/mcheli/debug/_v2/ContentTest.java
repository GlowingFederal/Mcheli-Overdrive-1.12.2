/*    */ package mcheli.debug._v2;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import mcheli.__helper.debug.DebugBootstrap;
/*    */ import mcheli.__helper.info.ContentRegistries;
/*    */ import mcheli.__helper.info.IContentData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentTest
/*    */ {
/*    */   public static void main(String[] args) {
/* 23 */     DebugBootstrap.init();
/*    */     
/* 25 */     File debugDir = new File("./run/");
/* 26 */     List<IContentData> contents = Lists.newLinkedList();
/*    */     
/* 28 */     System.out.println(debugDir.getAbsolutePath());
/*    */     
/* 30 */     ContentRegistries.loadContents(debugDir);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v2\ContentTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */