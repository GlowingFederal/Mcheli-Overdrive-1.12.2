/*    */ package mcheli.__helper.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import mcheli.__helper.block.EnumDirection8;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyDirection8
/*    */   extends PropertyEnum<EnumDirection8>
/*    */ {
/*    */   protected PropertyDirection8(String name, Collection<EnumDirection8> allowedValues) {
/* 22 */     super(name, EnumDirection8.class, allowedValues);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyDirection8 create(String name) {
/* 27 */     return create(name, Predicates.alwaysTrue());
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyDirection8 create(String name, Predicate<EnumDirection8> filter) {
/* 32 */     return create(name, Collections2.filter(Lists.newArrayList((Object[])EnumDirection8.values()), filter));
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyDirection8 create(String name, Collection<EnumDirection8> allowedValues) {
/* 37 */     return new PropertyDirection8(name, allowedValues);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\block\properties\PropertyDirection8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */