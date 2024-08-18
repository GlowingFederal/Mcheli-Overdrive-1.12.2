package mcheli;

import java.util.ArrayList;
import java.util.List;
import mcheli.__helper.entity.ITargetMarkerObject;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_RenderAircraft;
import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
import mcheli.multiplay.MCH_GuiTargetMarker;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
import mcheli.wrapper.W_ClientEventHook;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.opengl.GL11;

public class MCH_ClientEventHook extends W_ClientEventHook {
  MCH_TextureManagerDummy dummyTextureManager = null;
  
  public static List<MCH_EntityAircraft> haveSearchLightAircraft = new ArrayList<>();
  
  public void renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
    if (MCH_Config.DisableRenderLivingSpecials.prmBool) {
      MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)(Minecraft.func_71410_x()).field_71439_g);
      if (ac != null && ac.isMountedEntity((Entity)event.getEntity())) {
        event.setCanceled(true);
        return;
      } 
    } 
  }
  
  private static final ResourceLocation ir_strobe = new ResourceLocation("mcheli", "textures/ir_strobe.png");
  
  public void renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {}
  
  private void renderIRStrobe(EntityLivingBase entity, RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
    int cm = MCH_ClientCommonTickHandler.cameraMode;
    if (cm == 0)
      return; 
    int ticks = entity.field_70173_aa % 20;
    if (ticks >= 4)
      return; 
    float alpha = (ticks == 2 || ticks == 1) ? 1.0F : 0.5F;
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    if (entityPlayerSP == null)
      return; 
    if (!entityPlayerSP.func_184191_r((Entity)entity))
      return; 
    int j = 240;
    int k = 240;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
    RenderManager rm = event.getRenderer().func_177068_d();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    float f1 = 0.080000006F;
    GL11.glPushMatrix();
    GL11.glTranslated(event.getX(), event.getY() + (float)(entity.field_70131_O * 0.75D), event.getZ());
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    GL11.glRotatef(-rm.field_78735_i, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(rm.field_78732_j, 1.0F, 0.0F, 0.0F);
    GL11.glScalef(-f1, -f1, f1);
    GL11.glEnable(3042);
    OpenGlHelper.func_148821_a(770, 771, 1, 0);
    GL11.glEnable(3553);
    rm.field_78724_e.func_110577_a(ir_strobe);
    GL11.glAlphaFunc(516, 0.003921569F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
    int i = (int)Math.max(entity.field_70130_N, entity.field_70131_O) * 20;
    builder.func_181662_b(-i, -i, 0.1D).func_187315_a(0.0D, 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
    builder.func_181662_b(-i, i, 0.1D).func_187315_a(0.0D, 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
    builder.func_181662_b(i, i, 0.1D).func_187315_a(1.0D, 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
    builder.func_181662_b(i, -i, 0.1D).func_187315_a(1.0D, 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
    tessellator.func_78381_a();
    GL11.glEnable(2896);
    GL11.glPopMatrix();
  }
  
  public void mouseEvent(MouseEvent event) {
    if (MCH_ClientTickHandlerBase.updateMouseWheel(event.getDwheel()))
      event.setCanceled(true); 
  }
  
  private static boolean cancelRender = true;
  
  public static void setCancelRender(boolean cancel) {
    cancelRender = cancel;
  }
  
  public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
    for (MCH_EntityAircraft ac : haveSearchLightAircraft)
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, ac
          .getSearchLightValue((Entity)event.getEntity()), 240.0F); 
    if (MCH_Config.EnableModEntityRender.prmBool)
      if (cancelRender)
        if (event.getEntity().func_184187_bx() instanceof MCH_EntityAircraft || event
          .getEntity().func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat) {
          event.setCanceled(true);
          return;
        }   
    if (MCH_Config.EnableReplaceTextureManager.prmBool) {
      RenderManager rm = W_Reflection.getRenderManager((Render)event.getRenderer());
      if (rm != null && !(rm.field_78724_e instanceof MCH_TextureManagerDummy)) {
        if (this.dummyTextureManager == null)
          this.dummyTextureManager = new MCH_TextureManagerDummy(rm.field_78724_e); 
        rm.field_78724_e = this.dummyTextureManager;
      } 
    } 
  }
  
  public void renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {
    MCH_RenderAircraft.renderEntityMarker((Entity)event.getEntity());
    if (event.getEntity() instanceof ITargetMarkerObject) {
      MCH_GuiTargetMarker.addMarkEntityPos(2, (ITargetMarkerObject)event.getEntity(), event.getX(), event
          .getY() + (event.getEntity()).field_70131_O + 0.5D, event.getZ());
    } else {
      MCH_GuiTargetMarker.addMarkEntityPos(2, ITargetMarkerObject.fromEntity((Entity)event.getEntity()), event.getX(), event
          .getY() + (event.getEntity()).field_70131_O + 0.5D, event.getZ());
    } 
    MCH_ClientLightWeaponTickHandler.markEntity((Entity)event.getEntity(), event.getX(), event
        .getY() + ((event.getEntity()).field_70131_O / 2.0F), event.getZ());
  }
  
  public void renderPlayerPre(RenderPlayerEvent.Pre event) {
    if (event.getEntity() == null)
      return; 
    if (event.getEntity().func_184187_bx() instanceof MCH_EntityAircraft) {
      MCH_EntityAircraft v = (MCH_EntityAircraft)event.getEntity().func_184187_bx();
      if (v.getAcInfo() != null && (v.getAcInfo()).hideEntity) {
        event.setCanceled(true);
        return;
      } 
    } 
  }
  
  public void renderPlayerPost(RenderPlayerEvent.Post event) {}
  
  public void worldEventUnload(WorldEvent.Unload event) {}
  
  public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
    if (event.getEntity().func_70028_i(MCH_Lib.getClientPlayer())) {
      MCH_Lib.DbgLog(true, "MCH_ClientEventHook.entityJoinWorldEvent : " + event.getEntity(), new Object[0]);
      MCH_ItemRangeFinder.mode = Minecraft.func_71410_x().func_71356_B() ? 1 : 0;
      MCH_ParticlesUtil.clearMarkPoint();
    } 
  }
}
