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
    super(blockId, Material.IRON);
    setDefaultState(this.blockState.getBaseState().withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.NORTH));
    setSoundType(SoundType.METAL);
    setHardness(0.2F);
    this.isLighting = isOn;
    if (isOn)
      setLightLevel(1.0F); 
  }
  
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9) {
    if (!world.isRemote)
      if (!player.isSneaking()) {
        MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", new Object[] { Integer.valueOf(pos.getX()), Integer.valueOf(pos.getY()), Integer.valueOf(pos.getZ()) });
        player.openGui(MCH_MOD.instance, 4, world, pos.getX(), pos.getY(), pos.getZ());
      } else {
        EnumDirection8 dir = (EnumDirection8)state.getValue((IProperty)DIRECTION8);
        MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", new Object[] { Integer.valueOf((int)dir.getAngle()), this.isLighting ? "OFF->ON" : "ON->OFF" });
        if (this.isLighting) {
          world.setBlockState(pos, MCH_MOD.blockDraftingTable.getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)dir), 2);
        } else {
          world.setBlockState(pos, MCH_MOD.blockDraftingTableLit
              .getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)dir), 2);
        } 
        world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F);
      }  
    return true;
  }
  
  public TileEntity createNewTileEntity(World world, int a) {
    return new MCH_DraftingTableTileEntity();
  }
  
  public TileEntity createNewTileEntity(World world) {
    return new MCH_DraftingTableTileEntity();
  }
  
  public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return true;
  }
  
  public boolean isFullCube(IBlockState state) {
    return false;
  }
  
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }
  
  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    return true;
  }
  
  public boolean canRenderInPass(int pass) {
    return false;
  }
  
  public EnumPushReaction getMobilityFlag(IBlockState state) {
    return EnumPushReaction.DESTROY;
  }
  
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return getDefaultState().withProperty((IProperty)DIRECTION8, 
        (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(placer.rotationYaw)));
  }
  
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemStack) {
    float pyaw = (float)MCH_Lib.getRotate360(entity.rotationYaw);
    pyaw += 22.5F;
    int yaw = (int)(pyaw / 45.0F);
    if (yaw < 0)
      yaw = yaw % 8 + 8; 
    world.setBlockState(pos, state
        .withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(entity.rotationYaw))), 2);
    MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", new Object[] { Integer.valueOf(yaw) });
  }
  
  public boolean getUseNeighborBrightness(IBlockState state) {
    return true;
  }
  
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
  }
  
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack((Block)MCH_MOD.blockDraftingTable);
  }
  
  protected ItemStack createStackedBlock(int meta) {
    return new ItemStack((Block)MCH_MOD.blockDraftingTable);
  }
  
  public int getMetaFromState(IBlockState state) {
    return ((EnumDirection8)state.getValue((IProperty)DIRECTION8)).getIndex();
  }
  
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.getFront(meta));
  }
  
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)DIRECTION8 });
  }
}
