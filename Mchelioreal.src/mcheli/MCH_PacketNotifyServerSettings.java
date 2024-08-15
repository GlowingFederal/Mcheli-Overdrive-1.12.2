/*     */ package mcheli;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_PacketNotifyServerSettings
/*     */   extends MCH_Packet
/*     */ {
/*     */   public boolean enableCamDistChange = true;
/*     */   public boolean enableEntityMarker = true;
/*     */   public boolean enablePVP = true;
/*  37 */   public double stingerLockRange = 120.0D;
/*     */   public boolean enableDebugBoundingBox = true;
/*     */   public boolean enableRotationLimit = false;
/*  40 */   public byte pitchLimitMax = 10;
/*  41 */   public byte pitchLimitMin = 10;
/*  42 */   public byte rollLimit = 35;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  48 */     return 268437568;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  56 */       byte b = data.readByte();
/*  57 */       this.enableCamDistChange = getBit(b, 0);
/*  58 */       this.enableEntityMarker = getBit(b, 1);
/*  59 */       this.enablePVP = getBit(b, 2);
/*  60 */       this.enableDebugBoundingBox = getBit(b, 3);
/*  61 */       this.enableRotationLimit = getBit(b, 4);
/*  62 */       this.stingerLockRange = data.readFloat();
/*  63 */       this.pitchLimitMax = data.readByte();
/*  64 */       this.pitchLimitMin = data.readByte();
/*  65 */       this.rollLimit = data.readByte();
/*     */     }
/*  67 */     catch (Exception e) {
/*     */       
/*  69 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  78 */       byte b = 0;
/*  79 */       b = setBit(b, 0, this.enableCamDistChange);
/*  80 */       b = setBit(b, 1, this.enableEntityMarker);
/*  81 */       b = setBit(b, 2, this.enablePVP);
/*  82 */       b = setBit(b, 3, this.enableDebugBoundingBox);
/*  83 */       b = setBit(b, 4, this.enableRotationLimit);
/*  84 */       dos.writeByte(b);
/*  85 */       dos.writeFloat((float)this.stingerLockRange);
/*  86 */       dos.writeByte(this.pitchLimitMax);
/*  87 */       dos.writeByte(this.pitchLimitMin);
/*  88 */       dos.writeByte(this.rollLimit);
/*     */     }
/*  90 */     catch (IOException e) {
/*     */       
/*  92 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void send(@Nullable EntityPlayerMP player) {
/*  99 */     MCH_PacketNotifyServerSettings s = new MCH_PacketNotifyServerSettings();
/* 100 */     s.enableCamDistChange = !MCH_Config.DisableCameraDistChange.prmBool;
/* 101 */     s.enableEntityMarker = MCH_Config.DisplayEntityMarker.prmBool;
/*     */     
/* 103 */     s.enablePVP = MCH_Utils.getServer().func_71219_W();
/* 104 */     s.stingerLockRange = MCH_Config.StingerLockRange.prmDouble;
/* 105 */     s.enableDebugBoundingBox = MCH_Config.EnableDebugBoundingBox.prmBool;
/* 106 */     s.enableRotationLimit = MCH_Config.EnableRotationLimit.prmBool;
/* 107 */     s.pitchLimitMax = (byte)MCH_Config.PitchLimitMax.prmInt;
/* 108 */     s.pitchLimitMin = (byte)MCH_Config.PitchLimitMin.prmInt;
/* 109 */     s.rollLimit = (byte)MCH_Config.RollLimit.prmInt;
/*     */     
/* 111 */     if (player != null) {
/*     */       
/* 113 */       W_Network.sendToPlayer(s, (EntityPlayer)player);
/*     */     }
/*     */     else {
/*     */       
/* 117 */       W_Network.sendToAllPlayers(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendAll() {
/* 123 */     send((EntityPlayerMP)null);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_PacketNotifyServerSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */