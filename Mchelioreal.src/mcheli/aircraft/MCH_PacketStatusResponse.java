/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import mcheli.MCH_Packet;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ public class MCH_PacketStatusResponse
/*     */   extends MCH_Packet
/*     */ {
/*  27 */   public int entityID_AC = -1;
/*  28 */   public byte seatNum = -1;
/*  29 */   public byte[] weaponIDs = new byte[] { -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  38 */     return 268439649;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  46 */       this.entityID_AC = data.readInt();
/*  47 */       this.seatNum = data.readByte();
/*     */       
/*  49 */       if (this.seatNum > 0)
/*     */       {
/*  51 */         this.weaponIDs = new byte[this.seatNum];
/*  52 */         for (int i = 0; i < this.seatNum; i++)
/*     */         {
/*  54 */           this.weaponIDs[i] = data.readByte();
/*     */         }
/*     */       }
/*     */     
/*  58 */     } catch (Exception e) {
/*     */       
/*  60 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  69 */       dos.writeInt(this.entityID_AC);
/*     */       
/*  71 */       if (this.seatNum > 0 && this.weaponIDs != null && this.weaponIDs.length == this.seatNum)
/*     */       {
/*  73 */         dos.writeByte(this.seatNum);
/*  74 */         for (int i = 0; i < this.seatNum; i++)
/*     */         {
/*  76 */           dos.writeByte(this.weaponIDs[i]);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  81 */         dos.writeByte(-1);
/*     */       }
/*     */     
/*  84 */     } catch (IOException e) {
/*     */       
/*  86 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendStatus(MCH_EntityAircraft ac, EntityPlayer player) {
/*  92 */     MCH_PacketStatusResponse s = new MCH_PacketStatusResponse();
/*     */     
/*  94 */     s.setParameter(ac);
/*     */     
/*  96 */     W_Network.sendToPlayer((W_PacketBase)s, player);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParameter(MCH_EntityAircraft ac) {
/* 101 */     if (ac == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 106 */     this.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 107 */     this.seatNum = (byte)(ac.getSeatNum() + 1);
/*     */     
/* 109 */     if (this.seatNum > 0) {
/*     */       
/* 111 */       this.weaponIDs = new byte[this.seatNum];
/* 112 */       for (int i = 0; i < this.seatNum; i++)
/*     */       {
/* 114 */         this.weaponIDs[i] = (byte)ac.getWeaponIDBySeatID(i);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 119 */       this.weaponIDs = new byte[] { -1 };
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketStatusResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */