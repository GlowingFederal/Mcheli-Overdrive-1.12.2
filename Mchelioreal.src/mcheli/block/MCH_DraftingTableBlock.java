/*     */ package mcheli.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.block.EnumDirection8;
/*     */ import mcheli.__helper.block.properties.PropertyDirection8;
/*     */ import mcheli.wrapper.W_BlockContainer;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_DraftingTableBlock
/*     */   extends W_BlockContainer
/*     */   implements ITileEntityProvider
/*     */ {
/*  41 */   public static final PropertyDirection8 DIRECTION8 = PropertyDirection8.create("direction8");
/*     */   
/*     */   private final boolean isLighting;
/*     */   
/*     */   public MCH_DraftingTableBlock(int blockId, boolean isOn) {
/*  46 */     super(blockId, Material.field_151573_f);
/*  47 */     func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.NORTH));
/*     */     
/*  49 */     func_149672_a(SoundType.field_185852_e);
/*  50 */     func_149711_c(0.2F);
/*  51 */     this.isLighting = isOn;
/*     */     
/*  53 */     if (isOn)
/*     */     {
/*  55 */       func_149715_a(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9) {
/*  64 */     if (!world.field_72995_K)
/*     */     {
/*  66 */       if (!player.func_70093_af()) {
/*     */         
/*  68 */         MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", new Object[] {
/*     */ 
/*     */ 
/*     */               
/*  72 */               Integer.valueOf(pos.func_177958_n()), Integer.valueOf(pos.func_177956_o()), Integer.valueOf(pos.func_177952_p())
/*     */             });
/*     */ 
/*     */         
/*  76 */         player.openGui(MCH_MOD.instance, 4, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  81 */         EnumDirection8 dir = (EnumDirection8)state.func_177229_b((IProperty)DIRECTION8);
/*  82 */         MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", new Object[] {
/*     */ 
/*     */               
/*  85 */               Integer.valueOf((int)dir.getAngle()), this.isLighting ? "OFF->ON" : "ON->OFF"
/*     */             });
/*     */         
/*  88 */         if (this.isLighting) {
/*     */ 
/*     */           
/*  91 */           world.func_180501_a(pos, MCH_MOD.blockDraftingTable.func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)dir), 2);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/*  97 */           world.func_180501_a(pos, MCH_MOD.blockDraftingTableLit
/*  98 */               .func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)dir), 2);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 103 */         world.func_184133_a(null, pos, SoundEvents.field_187909_gi, SoundCategory.BLOCKS, 0.3F, 0.5F);
/*     */       } 
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int a) {
/* 112 */     return new MCH_DraftingTableTileEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World world) {
/* 117 */     return new MCH_DraftingTableTileEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149686_d(IBlockState state) {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRenderInPass(int pass) {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumPushReaction func_149656_h(IBlockState state) {
/* 158 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState func_180642_a(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 165 */     return func_176223_P().func_177226_a((IProperty)DIRECTION8, 
/* 166 */         (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(placer.field_70177_z)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemStack) {
/* 174 */     float pyaw = (float)MCH_Lib.getRotate360(entity.field_70177_z);
/* 175 */     pyaw += 22.5F;
/* 176 */     int yaw = (int)(pyaw / 45.0F);
/* 177 */     if (yaw < 0) {
/* 178 */       yaw = yaw % 8 + 8;
/*     */     }
/* 180 */     world.func_180501_a(pos, state
/* 181 */         .func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(entity.field_70177_z))), 2);
/*     */     
/* 183 */     MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", new Object[] {
/*     */           
/* 185 */           Integer.valueOf(yaw)
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149710_n(IBlockState state) {
/* 193 */     return true;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item func_180660_a(IBlockState state, Random rand, int fortune) {
/* 211 */     return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_185473_a(World worldIn, BlockPos pos, IBlockState state) {
/* 220 */     return new ItemStack((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack createStackedBlock(int meta) {
/* 225 */     return new ItemStack((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_176201_c(IBlockState state) {
/* 231 */     return ((EnumDirection8)state.func_177229_b((IProperty)DIRECTION8)).getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState func_176203_a(int meta) {
/* 237 */     return func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.getFront(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/* 243 */     return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)DIRECTION8 });
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */