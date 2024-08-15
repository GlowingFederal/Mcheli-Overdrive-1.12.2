/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import mcheli.MCH_Packet;
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
/*     */ public abstract class MCH_PacketPlayerControlBase
/*     */   extends MCH_Packet
/*     */ {
/*  45 */   public byte isUnmount = 0;
/*  46 */   public byte switchMode = -1;
/*  47 */   public byte switchCameraMode = 0;
/*  48 */   public byte switchWeapon = -1;
/*  49 */   public byte useFlareType = 0;
/*     */   public boolean useWeapon = false;
/*  51 */   public int useWeaponOption1 = 0;
/*  52 */   public int useWeaponOption2 = 0;
/*  53 */   public double useWeaponPosX = 0.0D;
/*  54 */   public double useWeaponPosY = 0.0D;
/*  55 */   public double useWeaponPosZ = 0.0D;
/*     */   public boolean throttleUp = false;
/*     */   public boolean throttleDown = false;
/*     */   public boolean moveLeft = false;
/*     */   public boolean moveRight = false;
/*     */   public boolean openGui;
/*  61 */   public byte switchHatch = 0;
/*  62 */   public byte switchFreeLook = 0;
/*  63 */   public byte switchGear = 0;
/*     */   public boolean ejectSeat = false;
/*  65 */   public byte putDownRack = 0;
/*     */   
/*     */   public boolean switchSearchLight = false;
/*     */   
/*     */   public boolean useBrake = false;
/*     */   
/*     */   public boolean switchGunnerStatus = false;
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  76 */       short bf = data.readShort();
/*  77 */       this.useWeapon = getBit(bf, 0);
/*  78 */       this.throttleUp = getBit(bf, 1);
/*  79 */       this.throttleDown = getBit(bf, 2);
/*  80 */       this.moveLeft = getBit(bf, 3);
/*  81 */       this.moveRight = getBit(bf, 4);
/*  82 */       this.switchSearchLight = getBit(bf, 5);
/*  83 */       this.ejectSeat = getBit(bf, 6);
/*  84 */       this.openGui = getBit(bf, 7);
/*  85 */       this.useBrake = getBit(bf, 8);
/*  86 */       this.switchGunnerStatus = getBit(bf, 9);
/*     */       
/*  88 */       bf = (short)data.readByte();
/*  89 */       this.putDownRack = (byte)(bf >> 6 & 0x3);
/*  90 */       this.isUnmount = (byte)(bf >> 4 & 0x3);
/*  91 */       this.useFlareType = (byte)(bf >> 0 & 0xF);
/*     */       
/*  93 */       this.switchMode = data.readByte();
/*  94 */       this.switchWeapon = data.readByte();
/*  95 */       if (this.useWeapon) {
/*     */         
/*  97 */         this.useWeaponOption1 = data.readInt();
/*  98 */         this.useWeaponOption2 = data.readInt();
/*  99 */         this.useWeaponPosX = data.readDouble();
/* 100 */         this.useWeaponPosY = data.readDouble();
/* 101 */         this.useWeaponPosZ = data.readDouble();
/*     */       } 
/*     */       
/* 104 */       bf = (short)data.readByte();
/* 105 */       this.switchCameraMode = (byte)(bf >> 6 & 0x3);
/* 106 */       this.switchHatch = (byte)(bf >> 4 & 0x3);
/* 107 */       this.switchFreeLook = (byte)(bf >> 2 & 0x3);
/* 108 */       this.switchGear = (byte)(bf >> 0 & 0x3);
/*     */     }
/* 110 */     catch (Exception e) {
/*     */       
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/* 121 */       short bf = 0;
/* 122 */       bf = setBit(bf, 0, this.useWeapon);
/* 123 */       bf = setBit(bf, 1, this.throttleUp);
/* 124 */       bf = setBit(bf, 2, this.throttleDown);
/* 125 */       bf = setBit(bf, 3, this.moveLeft);
/* 126 */       bf = setBit(bf, 4, this.moveRight);
/* 127 */       bf = setBit(bf, 5, this.switchSearchLight);
/* 128 */       bf = setBit(bf, 6, this.ejectSeat);
/* 129 */       bf = setBit(bf, 7, this.openGui);
/* 130 */       bf = setBit(bf, 8, this.useBrake);
/* 131 */       bf = setBit(bf, 9, this.switchGunnerStatus);
/* 132 */       dos.writeShort(bf);
/*     */       
/* 134 */       bf = (short)(byte)((this.putDownRack & 0x3) << 6 | (this.isUnmount & 0x3) << 4 | this.useFlareType & 0xF);
/*     */       
/* 136 */       dos.writeByte(bf);
/*     */       
/* 138 */       dos.writeByte(this.switchMode);
/* 139 */       dos.writeByte(this.switchWeapon);
/*     */       
/* 141 */       if (this.useWeapon) {
/*     */         
/* 143 */         dos.writeInt(this.useWeaponOption1);
/* 144 */         dos.writeInt(this.useWeaponOption2);
/* 145 */         dos.writeDouble(this.useWeaponPosX);
/* 146 */         dos.writeDouble(this.useWeaponPosY);
/* 147 */         dos.writeDouble(this.useWeaponPosZ);
/*     */       } 
/*     */       
/* 150 */       bf = (short)(byte)((this.switchCameraMode & 0x3) << 6 | (this.switchHatch & 0x3) << 4 | (this.switchFreeLook & 0x3) << 2 | (this.switchGear & 0x3) << 0);
/*     */ 
/*     */       
/* 153 */       dos.writeByte(bf);
/*     */     }
/* 155 */     catch (IOException e) {
/*     */       
/* 157 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketPlayerControlBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */