/*     */ package mcheli.__helper.client.model;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import mcheli.__helper.client.renderer.item.IItemModelRenderer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.apache.commons.lang3.tuple.Pair;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_BakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   private final IBakedModel bakedModel;
/*     */   private final IItemModelRenderer renderer;
/*     */   private final ItemOverrideList overrides;
/*     */   
/*     */   public MCH_BakedModel(IBakedModel bakedModel, IItemModelRenderer renderer) {
/*  36 */     this.bakedModel = bakedModel;
/*  37 */     this.renderer = renderer;
/*  38 */     this.overrides = new MCH_ItemOverrideList(bakedModel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BakedQuad> func_188616_a(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/*  44 */     return this.bakedModel.func_188616_a(state, side, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_177555_b() {
/*  50 */     return this.bakedModel.func_177555_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_177556_c() {
/*  56 */     return this.bakedModel.func_177556_c();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_188618_c() {
/*  62 */     if (this.renderer.shouldRenderer(PooledModelParameters.getTargetRendererStack(), 
/*  63 */         PooledModelParameters.getTransformType()))
/*     */     {
/*  65 */       return true;
/*     */     }
/*  67 */     return this.bakedModel.func_188618_c();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite func_177554_e() {
/*  73 */     return this.bakedModel.func_177554_e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemCameraTransforms func_177552_f() {
/*  80 */     return this.bakedModel.func_177552_f();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemOverrideList func_188617_f() {
/*  86 */     return this.overrides;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion(IBlockState state) {
/*  92 */     return this.bakedModel.isAmbientOcclusion(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
/*  98 */     PooledModelParameters.setTransformType(cameraTransformType);
/*  99 */     Pair<? extends IBakedModel, Matrix4f> pair = this.bakedModel.handlePerspective(cameraTransformType);
/*     */     
/* 101 */     return Pair.of(new MCH_BakedModel((IBakedModel)pair.getLeft(), this.renderer), pair.getRight());
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\MCH_BakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */