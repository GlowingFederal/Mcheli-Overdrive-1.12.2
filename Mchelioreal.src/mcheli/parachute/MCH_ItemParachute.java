/*    */ package mcheli.parachute;
/*    */ 
/*    */ import mcheli.wrapper.W_Item;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ItemParachute
/*    */   extends W_Item
/*    */ {
/*    */   public MCH_ItemParachute(int par1) {
/* 21 */     super(par1);
/* 22 */     this.field_77777_bU = 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/* 29 */     ItemStack itemstack = player.func_184586_b(handIn);
/*    */     
/* 31 */     if (!world.field_72995_K && player.func_184187_bx() == null)
/*    */     {
/* 33 */       if (!player.field_70122_E) {
/*    */         
/* 35 */         double x = player.field_70165_t + 0.5D;
/* 36 */         double y = player.field_70163_u + 3.5D;
/* 37 */         double z = player.field_70161_v + 0.5D;
/* 38 */         MCH_EntityParachute entity = new MCH_EntityParachute(world, x, y, z);
/*    */         
/* 40 */         entity.field_70177_z = player.field_70177_z;
/* 41 */         entity.field_70159_w = player.field_70159_w;
/* 42 */         entity.field_70181_x = player.field_70181_x;
/* 43 */         entity.field_70179_y = player.field_70179_y;
/* 44 */         entity.field_70143_R = player.field_70143_R;
/* 45 */         player.field_70143_R = 0.0F;
/* 46 */         entity.user = (Entity)player;
/* 47 */         entity.setType(1);
/*    */         
/* 49 */         world.func_72838_d((Entity)entity);
/*    */       } 
/*    */     }
/*    */     
/* 53 */     if (!player.field_71075_bZ.field_75098_d)
/*    */     {
/*    */       
/* 56 */       itemstack.func_190918_g(1);
/*    */     }
/*    */ 
/*    */     
/* 60 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\parachute\MCH_ItemParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */