/*     */ package mcheli;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_PacketEffectExplosion
/*     */   extends MCH_Packet
/*     */ {
/*  21 */   ExplosionParam prm = new ExplosionParam(this);
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  26 */     return 268437520;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  34 */       this.prm.posX = data.readDouble();
/*  35 */       this.prm.posY = data.readDouble();
/*  36 */       this.prm.posZ = data.readDouble();
/*  37 */       this.prm.size = data.readFloat();
/*  38 */       this.prm.exploderID = data.readInt();
/*  39 */       this.prm.inWater = (data.readByte() != 0);
/*  40 */       this.prm.readAffectedPositions(data);
/*     */     }
/*  42 */     catch (Exception e) {
/*     */       
/*  44 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  53 */       dos.writeDouble(this.prm.posX);
/*  54 */       dos.writeDouble(this.prm.posY);
/*  55 */       dos.writeDouble(this.prm.posZ);
/*  56 */       dos.writeFloat(this.prm.size);
/*  57 */       dos.writeInt(this.prm.exploderID);
/*  58 */       dos.writeByte(this.prm.inWater ? 1 : 0);
/*  59 */       this.prm.writeAffectedPositions(dos);
/*     */     }
/*  61 */     catch (IOException e) {
/*     */       
/*  63 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ExplosionParam create() {
/*  69 */     return (new MCH_PacketEffectExplosion()).aaa();
/*     */   }
/*     */ 
/*     */   
/*     */   private ExplosionParam aaa() {
/*  74 */     return new ExplosionParam(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void send(ExplosionParam param) {
/*  79 */     if (param != null) {
/*     */       
/*  81 */       MCH_PacketEffectExplosion s = new MCH_PacketEffectExplosion();
/*  82 */       s.prm = param;
/*  83 */       W_Network.sendToAllPlayers(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class ExplosionParam
/*     */   {
/*     */     public double posX;
/*     */     
/*     */     public double posY;
/*     */     
/*     */     public double posZ;
/*     */     public float size;
/*     */     public int exploderID;
/*     */     public boolean inWater;
/*     */     private List<BlockPos> affectedPositions;
/*     */     
/*     */     public ExplosionParam(MCH_PacketEffectExplosion paramMCH_PacketEffectExplosion) {}
/*     */     
/*     */     public void setAffectedPositions(List<BlockPos> affectedPositions) {
/* 103 */       this.affectedPositions = Lists.newArrayList(affectedPositions);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<BlockPos> getAffectedBlockPositions() {
/* 108 */       return this.affectedPositions;
/*     */     }
/*     */ 
/*     */     
/*     */     void writeAffectedPositions(DataOutputStream dos) throws IOException {
/* 113 */       dos.writeInt(this.affectedPositions.size());
/*     */       
/* 115 */       for (BlockPos blockpos : this.affectedPositions) {
/*     */         
/* 117 */         dos.writeInt(blockpos.func_177958_n());
/* 118 */         dos.writeInt(blockpos.func_177956_o());
/* 119 */         dos.writeInt(blockpos.func_177952_p());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void readAffectedPositions(ByteArrayDataInput data) {
/* 125 */       int i = data.readInt();
/*     */       
/* 127 */       this.affectedPositions = Lists.newArrayListWithCapacity(i);
/*     */       
/* 129 */       for (int i1 = 0; i1 < i; i1++) {
/*     */         
/* 131 */         int j1 = data.readInt();
/* 132 */         int k1 = data.readInt();
/* 133 */         int l1 = data.readInt();
/* 134 */         this.affectedPositions.add(new BlockPos(j1, k1, l1));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_PacketEffectExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */