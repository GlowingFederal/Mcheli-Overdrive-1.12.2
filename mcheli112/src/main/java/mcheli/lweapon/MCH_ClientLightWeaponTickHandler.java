package mcheli.lweapon;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import mcheli.MCH_ClientTickHandlerBase;
import mcheli.MCH_Config;
import mcheli.MCH_Key;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponCreator;
import mcheli.weapon.MCH_WeaponGuidanceSystem;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class MCH_ClientLightWeaponTickHandler extends MCH_ClientTickHandlerBase {
  private static FloatBuffer screenPos = BufferUtils.createFloatBuffer(3);
  
  private static FloatBuffer screenPosBB = BufferUtils.createFloatBuffer(3);
  
  private static FloatBuffer matModel = BufferUtils.createFloatBuffer(16);
  
  private static FloatBuffer matProjection = BufferUtils.createFloatBuffer(16);
  
  private static IntBuffer matViewport = BufferUtils.createIntBuffer(16);
  
  protected boolean isHeldItem = false;
  
  protected boolean isBeforeHeldItem = false;
  
  protected EntityPlayer prevThePlayer = null;
  
  protected ItemStack prevItemStack = ItemStack.field_190927_a;
  
  public MCH_Key KeyAttack;
  
  public MCH_Key KeyUseWeapon;
  
  public MCH_Key KeySwWeaponMode;
  
  public MCH_Key KeyZoom;
  
  public MCH_Key KeyCameraMode;
  
  public MCH_Key[] Keys;
  
  protected static MCH_WeaponBase weapon;
  
  public static int reloadCount;
  
  public static int lockonSoundCount;
  
  public static int weaponMode;
  
  public static int selectedZoom;
  
  public static Entity markEntity = null;
  
  public static Vec3d markPos = Vec3d.field_186680_a;
  
  public static MCH_WeaponGuidanceSystem gs = new MCH_WeaponGuidanceSystem();
  
  public static double lockRange = 120.0D;
  
  public MCH_ClientLightWeaponTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft);
    updateKeybind(config);
    gs.canLockInAir = false;
    gs.canLockOnGround = false;
    gs.canLockInWater = false;
    gs.setLockCountMax(40);
    gs.lockRange = 120.0D;
    lockonSoundCount = 0;
    initWeaponParam((EntityPlayer)null);
  }
  
  public static void markEntity(Entity entity, double x, double y, double z) {
    if (gs.getLockingEntity() == entity) {
      GL11.glGetFloat(2982, matModel);
      GL11.glGetFloat(2983, matProjection);
      GL11.glGetInteger(2978, matViewport);
      GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, screenPos);
      MCH_AircraftInfo i = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getAcInfo() : null;
      float w = (entity.field_70130_N > entity.field_70131_O) ? entity.field_70130_N : ((i != null) ? i.markerWidth : entity.field_70131_O);
      float h = (i != null) ? i.markerHeight : entity.field_70131_O;
      GLU.gluProject((float)x + w, (float)y + h, (float)z + w, matModel, matProjection, matViewport, screenPosBB);
      markEntity = entity;
    } 
  }
  
  @Nullable
  public static Vec3d getMartEntityPos() {
    if (gs.getLockingEntity() == markEntity && markEntity != null)
      return new Vec3d(screenPos.get(0), screenPos.get(1), screenPos.get(2)); 
    return null;
  }
  
  @Nullable
  public static Vec3d getMartEntityBBPos() {
    if (gs.getLockingEntity() == markEntity && markEntity != null)
      return new Vec3d(screenPosBB.get(0), screenPosBB.get(1), screenPosBB.get(2)); 
    return null;
  }
  
  public void initWeaponParam(EntityPlayer player) {
    reloadCount = 0;
    weaponMode = 0;
    selectedZoom = 0;
  }
  
  public void updateKeybind(MCH_Config config) {
    this.KeyAttack = new MCH_Key(MCH_Config.KeyAttack.prmInt);
    this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
    this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
    this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
    this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
    this.Keys = new MCH_Key[] { this.KeyAttack, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeyZoom, this.KeyCameraMode };
  }
  
  protected void onTick(boolean inGUI) {
    for (MCH_Key k : this.Keys)
      k.update(); 
    this.isBeforeHeldItem = this.isHeldItem;
    EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
    if (this.prevThePlayer == null || this.prevThePlayer != entityPlayerSP) {
      initWeaponParam((EntityPlayer)entityPlayerSP);
      this.prevThePlayer = (EntityPlayer)entityPlayerSP;
    } 
    ItemStack is = (entityPlayerSP != null) ? entityPlayerSP.func_184614_ca() : ItemStack.field_190927_a;
    if (entityPlayerSP == null || entityPlayerSP.func_184187_bx() instanceof mcheli.gltd.MCH_EntityGLTD || entityPlayerSP
      .func_184187_bx() instanceof MCH_EntityAircraft)
      is = ItemStack.field_190927_a; 
    if (gs.getLockingEntity() == null)
      markEntity = null; 
    if (!is.func_190926_b() && is.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
      MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.func_77973_b();
      if (this.prevItemStack.func_190926_b() || (!this.prevItemStack.func_77969_a(is) && 
        !this.prevItemStack.func_77977_a().equals(is.func_77977_a()))) {
        initWeaponParam((EntityPlayer)entityPlayerSP);
        weapon = MCH_WeaponCreator.createWeapon(((EntityPlayer)entityPlayerSP).field_70170_p, MCH_ItemLightWeaponBase.getName(is), Vec3d.field_186680_a, 0.0F, 0.0F, null, false);
        if (weapon != null && weapon.getInfo() != null && weapon.getGuidanceSystem() != null)
          gs = weapon.getGuidanceSystem(); 
      } 
      if (weapon == null || gs == null)
        return; 
      gs.setWorld(((EntityPlayer)entityPlayerSP).field_70170_p);
      gs.lockRange = lockRange;
      if (entityPlayerSP.func_184612_cw() > 10) {
        selectedZoom %= (weapon.getInfo()).zoom.length;
        W_Reflection.setCameraZoom((weapon.getInfo()).zoom[selectedZoom]);
      } else {
        W_Reflection.restoreCameraZoom();
      } 
      if (is.func_77960_j() < is.func_77958_k()) {
        if (entityPlayerSP.func_184612_cw() > 10) {
          gs.lock((Entity)entityPlayerSP);
          if (gs.getLockCount() > 0)
            if (lockonSoundCount > 0) {
              lockonSoundCount--;
            } else {
              lockonSoundCount = 7;
              lockonSoundCount = (int)(lockonSoundCount * (1.0D - (gs.getLockCount() / gs.getLockCountMax())));
              if (lockonSoundCount < 3)
                lockonSoundCount = 2; 
              W_McClient.MOD_playSoundFX("lockon", 1.0F, 1.0F);
            }  
        } else {
          W_Reflection.restoreCameraZoom();
          gs.clearLock();
        } 
        reloadCount = 0;
      } else {
        lockonSoundCount = 0;
        if (W_EntityPlayer.hasItem((EntityPlayer)entityPlayerSP, (Item)lweapon.bullet) && entityPlayerSP.func_184605_cv() <= 0) {
          if (reloadCount == 10)
            W_McClient.MOD_playSoundFX("fim92_reload", 1.0F, 1.0F); 
          if (reloadCount < 40) {
            reloadCount++;
            if (reloadCount == 40)
              onCompleteReload(); 
          } 
        } else {
          reloadCount = 0;
        } 
        gs.clearLock();
      } 
      if (!inGUI)
        playerControl((EntityPlayer)entityPlayerSP, is, (MCH_ItemLightWeaponBase)is.func_77973_b()); 
      this.isHeldItem = MCH_ItemLightWeaponBase.isHeld((EntityPlayer)entityPlayerSP);
    } else {
      lockonSoundCount = 0;
      reloadCount = 0;
      this.isHeldItem = false;
    } 
    if (this.isBeforeHeldItem != this.isHeldItem) {
      MCH_Lib.DbgLog(true, "LWeapon cancel", new Object[0]);
      if (!this.isHeldItem) {
        if (getPotionNightVisionDuration((EntityPlayer)entityPlayerSP) < 250) {
          MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
          pc.camMode = 1;
          W_Network.sendToServer((W_PacketBase)pc);
          entityPlayerSP.func_184589_d(MobEffects.field_76439_r);
        } 
        W_Reflection.restoreCameraZoom();
      } 
    } 
    this.prevItemStack = is;
    gs.update();
  }
  
  protected void onCompleteReload() {
    MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
    pc.cmpReload = 1;
    W_Network.sendToServer((W_PacketBase)pc);
  }
  
  protected void playerControl(EntityPlayer player, ItemStack is, MCH_ItemLightWeaponBase item) {
    MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
    boolean send = false;
    boolean autoShot = false;
    if (MCH_Config.LWeaponAutoFire.prmBool)
      if (is.func_77960_j() < is.func_77958_k() && gs.isLockComplete())
        autoShot = true;  
    if (this.KeySwWeaponMode.isKeyDown() && weapon.numMode > 1) {
      weaponMode = (weaponMode + 1) % weapon.numMode;
      W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
    } 
    if (this.KeyAttack.isKeyPress() || autoShot) {
      boolean result = false;
      if (is.func_77960_j() < is.func_77958_k())
        if (gs.isLockComplete()) {
          boolean canFire = true;
          if (weaponMode > 0 && gs.getTargetEntity() != null) {
            double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
            double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
            canFire = (Math.sqrt(dx * dx + dz * dz) >= 40.0D);
          } 
          if (canFire) {
            pc.useWeapon = true;
            pc.useWeaponOption1 = W_Entity.getEntityId(gs.lastLockEntity);
            pc.useWeaponOption2 = weaponMode;
            pc.useWeaponPosX = player.field_70165_t;
            pc.useWeaponPosY = player.field_70163_u + player.func_70047_e();
            pc.useWeaponPosZ = player.field_70161_v;
            gs.clearLock();
            send = true;
            result = true;
          } 
        }  
      if (this.KeyAttack.isKeyDown())
        if (!result && player.func_184612_cw() > 5)
          playSoundNG();  
    } 
    if (this.KeyZoom.isKeyDown()) {
      int prevZoom = selectedZoom;
      selectedZoom = (selectedZoom + 1) % (weapon.getInfo()).zoom.length;
      if (prevZoom != selectedZoom)
        playSound("zoom", 0.5F, 1.0F); 
    } 
    if (this.KeyCameraMode.isKeyDown()) {
      PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
      MCH_Lib.DbgLog(true, "LWeapon NV %s", new Object[] { (pe != null) ? "ON->OFF" : "OFF->ON" });
      if (pe != null) {
        player.func_184589_d(MobEffects.field_76439_r);
        pc.camMode = 1;
        send = true;
        W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
      } else if (player.func_184612_cw() > 60) {
        pc.camMode = 2;
        send = true;
        W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
      } else {
        playSoundNG();
      } 
    } 
    if (send)
      W_Network.sendToServer((W_PacketBase)pc); 
  }
  
  public static int getPotionNightVisionDuration(EntityPlayer player) {
    PotionEffect cpe = player.func_70660_b(MobEffects.field_76439_r);
    return (player == null || cpe == null) ? 0 : cpe.func_76459_b();
  }
}
