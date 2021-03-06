package com.game.backpack.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 元宝不足消息
 */
public class ResNotEnoughGoldChangeMessage extends Message{

	//缺少元宝数量
	private int gold;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//缺少元宝数量
		writeInt(buf, this.gold);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//缺少元宝数量
		this.gold = readInt(buf);
		return true;
	}
	
	/**
	 * get 缺少元宝数量
	 * @return 
	 */
	public int getGold(){
		return gold;
	}
	
	/**
	 * set 缺少元宝数量
	 */
	public void setGold(int gold){
		this.gold = gold;
	}
	
	
	@Override
	public int getId() {
		return 104112;
	}

	@Override
	public String getQueue() {
		return null;
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//缺少元宝数量
		buf.append("gold:" + gold +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}