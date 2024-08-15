/*    */ package mcheli.__helper.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Collections;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverride;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ItemOverrideList
/*    */   extends ItemOverrideList
/*    */ {
/*    */   private final IBakedModel bakedModel;
/*    */   
/*    */   public MCH_ItemOverrideList(IBakedModel bakedModel) {
/* 28 */     super(Collections.emptyList());
/*    */     
/* 30 */     this.bakedModel = bakedModel;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   @Deprecated
/*    */   public ResourceLocation func_188021_a(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
/* 38 */     return this.bakedModel.func_188617_f().func_188021_a(stack, worldIn, entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
/* 45 */     PooledModelParameters.setItemAndUser(stack, entity);
/* 46 */     return this.bakedModel.func_188617_f().handleItemState(originalModel, stack, world, entity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ImmutableList<ItemOverride> getOverrides() {
/* 52 */     return this.bakedModel.func_188617_f().getOverrides();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\MCH_ItemOverrideList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */