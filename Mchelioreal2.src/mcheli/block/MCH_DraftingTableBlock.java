package mcheli.block;

import java.util.Random;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.__helper.block.EnumDirection8;
import mcheli.__helper.block.properties.PropertyDirection8;
import mcheli.wrapper.W_BlockContainer;
import mcheli.wrapper.W_Item;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MCH_DraftingTableBlock extends W_BlockContainer implements ITileEntityProvider {
  public static final PropertyDirection8 DIRECTION8 = PropertyDirection8.create("direction8");
  
  private final boolean isLighting;
  
  public MCH_DraftingTableBlock(int blockId, boolean isOn) {
    super(blockId, Material.field_151573_f);
    func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.NORTH));
    func_149672_a(SoundType.field_185852_e);
    func_149711_c(0.2F);
    this.isLighting = isOn;
    if (isOn)
      func_149715_a(1.0F); 
  }
  
  public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9) {
    if (!world.field_72995_K)
      if (!player.func_70093_af()) {
        MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", new Object[] { Integer.valueOf(pos.func_177958_n()), Integer.valueOf(pos.func_177956_o()), Integer.valueOf(pos.func_177952_p()) });
        player.openGui(MCH_MOD.instance, 4, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
      } else {
        EnumDirection8 dir = (EnumDirection8)state.func_177229_b((IProperty)DIRECTION8);
        MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", new Object[] { Integer.valueOf((int)dir.getAngle()), this.isLighting ? "OFF->ON" : "ON->OFF" });
        if (this.isLighting) {
          world.func_180501_a(pos, MCH_MOD.blockDraftingTable.func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)dir), 2);
        } else {
          world.func_180501_a(pos, MCH_MOD.blockDraftingTableLit
              .func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)dir), 2);
        } 
        world.func_184133_a(null, pos, SoundEvents.field_187909_gi, SoundCategory.BLOCKS, 0.3F, 0.5F);
      }  
    return true;
  }
  
  public TileEntity func_149915_a(World world, int a) {
    return new MCH_DraftingTableTileEntity();
  }
  
  public TileEntity createNewTileEntity(World world) {
    return new MCH_DraftingTableTileEntity();
  }
  
  public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return true;
  }
  
  public boolean func_149686_d(IBlockState state) {
    return false;
  }
  
  public boolean func_149662_c(IBlockState state) {
    return false;
  }
  
  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    return true;
  }
  
  public boolean canRenderInPass(int pass) {
    return false;
  }
  
  public EnumPushReaction func_149656_h(IBlockState state) {
    return EnumPushReaction.DESTROY;
  }
  
  public IBlockState func_180642_a(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return func_176223_P().func_177226_a((IProperty)DIRECTION8, 
        (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(placer.field_70177_z)));
  }
  
  public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemStack) {
    float pyaw = (float)MCH_Lib.getRotate360(entity.field_70177_z);
    pyaw += 22.5F;
    int yaw = (int)(pyaw / 45.0F);
    if (yaw < 0)
      yaw = yaw % 8 + 8; 
    world.func_180501_a(pos, state
        .func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(entity.field_70177_z))), 2);
    MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", new Object[] { Integer.valueOf(yaw) });
  }
  
  public boolean func_149710_n(IBlockState state) {
    return true;
  }
  
  public Item func_180660_a(IBlockState state, Random rand, int fortune) {
    return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
  }
  
  public ItemStack func_185473_a(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack((Block)MCH_MOD.blockDraftingTable);
  }
  
  protected ItemStack createStackedBlock(int meta) {
    return new ItemStack((Block)MCH_MOD.blockDraftingTable);
  }
  
  public int func_176201_c(IBlockState state) {
    return ((EnumDirection8)state.func_177229_b((IProperty)DIRECTION8)).getIndex();
  }
  
  public IBlockState func_176203_a(int meta) {
    return func_176223_P().func_177226_a((IProperty)DIRECTION8, (Comparable)EnumDirection8.getFront(meta));
  }
  
  protected BlockStateContainer func_180661_e() {
    return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)DIRECTION8 });
  }
}
