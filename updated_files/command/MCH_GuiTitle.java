package mcheli.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mcheli.gui.MCH_Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_GuiTitle extends MCH_Gui {
  private final List<ChatLine> chatLines = new ArrayList<>();
  
  private int prevPlayerTick = 0;
  
  private int restShowTick = 0;
  
  private int showTick = 0;
  
  private float colorAlpha = 0.0F;
  
  private int position = 0;
  
  public MCH_GuiTitle(Minecraft minecraft) {
    super(minecraft);
  }
  
  public void initGui() {
    super.initGui();
  }
  
  public boolean doesGuiPauseGame() {
    return false;
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    if (this.restShowTick > 0 && this.chatLines.size() > 0)
      if (player != null && player.world != null) {
        if (this.prevPlayerTick != player.ticksExisted) {
          this.showTick++;
          this.restShowTick--;
        } 
        this.prevPlayerTick = player.ticksExisted;
      }  
    return (this.restShowTick > 0);
  }
  
  public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
    GL11.glLineWidth((scaleFactor * 2));
    GL11.glDisable(3042);
    if (scaleFactor <= 0)
      scaleFactor = 1; 
    this.colorAlpha = 1.0F;
    if (this.restShowTick > 20 && this.showTick < 5)
      this.colorAlpha = 0.2F * this.showTick; 
    if (this.showTick > 0 && this.restShowTick < 5)
      this.colorAlpha = 0.2F * this.restShowTick; 
    drawChat();
  }
  
  private String formatColors(String s) {
    return (Minecraft.getMinecraft()).gameSettings.chatColours ? s : TextFormatting.getTextWithoutFormattingCodes(s);
  }
  
  private int calculateChatboxWidth() {
    short short1 = 320;
    byte b0 = 40;
    return MathHelper.floor(this.mc.gameSettings.chatWidth * (short1 - b0) + b0);
  }
  
  public void setupTitle(ITextComponent chatComponent, int showTime, int pos) {
    int displayTime = 20;
    int line = 0;
    this.chatLines.clear();
    this.position = pos;
    this.showTick = 0;
    this.restShowTick = showTime;
    int k = MathHelper.floor(calculateChatboxWidth() / this.mc.gameSettings.chatScale);
    int l = 0;
    TextComponentString chatcomponenttext = new TextComponentString("");
    ArrayList<ITextComponent> arraylist = Lists.newArrayList();
    ArrayList<ITextComponent> arraylist1 = Lists.newArrayList((Iterable)chatComponent);
    for (int i1 = 0; i1 < arraylist1.size(); i1++) {
      ITextComponent ichatcomponent1 = arraylist1.get(i1);
      String[] splitLine = (ichatcomponent1.getUnformattedComponentText() + "").split("\n");
      int lineCnt = 0;
      for (String sLine : splitLine) {
        String s = formatColors(ichatcomponent1.getStyle().getFormattingCode() + sLine);
        int j1 = this.mc.fontRendererObj.getStringWidth(s);
        TextComponentString chatcomponenttext1 = new TextComponentString(s);
        chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
        boolean flag1 = false;
        if (l + j1 > k) {
          String s1 = this.mc.fontRendererObj.trimStringToWidth(s, k - l, false);
          String s2 = (s1.length() < s.length()) ? s.substring(s1.length()) : null;
          if (s2 != null && s2.length() > 0) {
            int k1 = s1.lastIndexOf(" ");
            if (k1 >= 0 && this.mc.fontRendererObj.getStringWidth(s.substring(0, k1)) > 0) {
              s1 = s.substring(0, k1);
              s2 = s.substring(k1);
            } 
            TextComponentString chatcomponenttext2 = new TextComponentString(s2);
            chatcomponenttext2.setStyle(ichatcomponent1.getStyle().createShallowCopy());
            arraylist1.add(i1 + 1, chatcomponenttext2);
          } 
          j1 = this.mc.fontRendererObj.getStringWidth(s1);
          chatcomponenttext1 = new TextComponentString(s1);
          chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
          flag1 = true;
        } 
        if (l + j1 <= k) {
          l += j1;
          chatcomponenttext.appendSibling((ITextComponent)chatcomponenttext1);
        } else {
          flag1 = true;
        } 
        if (flag1) {
          arraylist.add(chatcomponenttext);
          l = 0;
          chatcomponenttext = new TextComponentString("");
        } 
        lineCnt++;
        if (lineCnt < splitLine.length) {
          arraylist.add(chatcomponenttext);
          l = 0;
          chatcomponenttext = new TextComponentString("");
        } 
      } 
    } 
    arraylist.add(chatcomponenttext);
    Iterator<ITextComponent> iterator = arraylist.iterator();
    for (; iterator.hasNext(); this.chatLines.add(new ChatLine(displayTime, ichatcomponent2, line)))
      ITextComponent ichatcomponent2 = iterator.next(); 
    while (this.chatLines.size() > 100)
      this.chatLines.remove(this.chatLines.size() - 1); 
  }
  
  private int calculateChatboxHeight() {
    short short1 = 180;
    byte b0 = 20;
    return MathHelper.floor(this.mc.gameSettings.chatHeightFocused * (short1 - b0) + b0);
  }
  
  private void drawChat() {
    float charAlpha = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
    float scale = this.mc.gameSettings.chatScale * 2.0F;
    GL11.glPushMatrix();
    float posY = 0.0F;
    switch (this.position) {
      default:
        posY = (this.mc.displayHeight / 2 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
        break;
      case 1:
        posY = 0.0F;
        break;
      case 2:
        posY = (this.mc.displayHeight / scaleFactor) - this.chatLines.size() * 9.0F * scale;
        break;
      case 3:
        posY = (this.mc.displayHeight / 3 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
        break;
      case 4:
        posY = (this.mc.displayHeight * 2 / 3 / scaleFactor) - this.chatLines.size() / 2.0F * 9.0F * scale;
        break;
    } 
    GL11.glTranslatef(0.0F, posY, 0.0F);
    GL11.glScalef(scale, scale, 1.0F);
    for (int i = 0; i < this.chatLines.size(); i++) {
      ChatLine chatline = this.chatLines.get(i);
      if (chatline != null) {
        int alpha = (int)(255.0F * charAlpha * this.colorAlpha);
        int y = i * 9;
        drawRect(0, y + 9, this.mc.displayWidth, y, alpha / 2 << 24);
        GL11.glEnable(3042);
        String s = chatline.getChatComponent().getFormattedText();
        int sw = this.mc.displayWidth / 2 / scaleFactor - this.mc.fontRendererObj.getStringWidth(s);
        sw = (int)(sw / scale);
        this.mc.fontRendererObj.drawStringWithShadow(s, sw, (y + 1), 16777215 + (alpha << 24));
        GL11.glDisable(3008);
      } 
    } 
    GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
    GL11.glPopMatrix();
  }
}
