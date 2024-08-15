/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Packet;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
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
/*     */ 
/*     */ 
/*     */ public class MCH_PacketModList
/*     */   extends MCH_Packet
/*     */ {
/*     */   public boolean firstData = false;
/*  29 */   public int id = 0;
/*  30 */   public int num = 0;
/*  31 */   public List<String> list = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  37 */     return 536873473;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  45 */       this.firstData = (data.readByte() == 1);
/*  46 */       this.id = data.readInt();
/*  47 */       this.num = data.readInt();
/*  48 */       for (int i = 0; i < this.num; i++)
/*     */       {
/*  50 */         this.list.add(data.readUTF());
/*     */       }
/*     */     }
/*  53 */     catch (Exception e) {
/*     */       
/*  55 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  64 */       dos.writeByte(this.firstData ? 1 : 0);
/*  65 */       dos.writeInt(this.id);
/*  66 */       dos.writeInt(this.num);
/*  67 */       for (String s : this.list)
/*     */       {
/*  69 */         dos.writeUTF(s);
/*     */       }
/*     */     }
/*  72 */     catch (Exception e) {
/*     */       
/*  74 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void send(EntityPlayer player, MCH_PacketModList p) {
/*  80 */     W_Network.sendToPlayer((W_PacketBase)p, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void send(List<String> list, int id) {
/*  85 */     MCH_PacketModList p = null;
/*  86 */     int size = 0;
/*  87 */     boolean isFirst = true;
/*  88 */     for (String s : list) {
/*     */       
/*  90 */       if (p == null) {
/*     */         
/*  92 */         p = new MCH_PacketModList();
/*  93 */         p.id = id;
/*  94 */         p.firstData = isFirst;
/*  95 */         isFirst = false;
/*     */       } 
/*  97 */       p.list.add(s);
/*  98 */       size += s.length() + 2;
/*  99 */       if (size > 1024) {
/*     */         
/* 101 */         p.num = p.list.size();
/* 102 */         W_Network.sendToServer((W_PacketBase)p);
/* 103 */         p = null;
/* 104 */         size = 0;
/*     */       } 
/*     */     } 
/* 107 */     if (p != null) {
/*     */       
/* 109 */       p.num = p.list.size();
/* 110 */       W_Network.sendToServer((W_PacketBase)p);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketModList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */