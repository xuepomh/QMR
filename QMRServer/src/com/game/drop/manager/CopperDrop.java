package com.game.drop.manager;

import org.apache.log4j.Logger;

import com.game.backpack.structs.Item;
import com.game.drop.structs.DropItem;
import com.game.drop.structs.MapDropInfo;
import com.game.fight.structs.Fighter;
import com.game.map.bean.DropGoodsInfo;
import com.game.map.manager.MapManager;
import com.game.map.structs.Map;
import com.game.monster.manager.MonsterManager;
import com.game.monster.structs.Hatred;
import com.game.monster.structs.Monster;
import com.game.pet.struts.Pet;
import com.game.player.structs.Player;
import com.game.structs.Position;

public class CopperDrop extends DropItem {
	/**
	 * Logger for this class
	 */
	protected static final Logger logger = Logger.getLogger(CopperDrop.class);

	private int count;
	public CopperDrop(int count) {
		this.count=count;
	}
	@Override
	protected MapDropInfo buildGoodsInfo(Monster monster) {
		if (checkGradeAble(monster)) {
			Item item = Item.createMoney(count);
				Map map = MapManager.getInstance().getMap(monster);
				Position ableDropPoint = getAbleDropPoint(monster.getPosition(), map);
				DropGoodsInfo info = item.buildDropInfo(monster, ableDropPoint);
				if(MonsterManager.getInstance().isBoss(monster.getModelId())){
					Fighter killer = monster.getKiller();
					if(isOwner()&&killer!=null){
						if(killer instanceof Player){
							Player player=(Player)killer;
							info.setOwnerId(player.getId());	
						}
						if(killer instanceof Pet){
							//宠物攻击掉落
							Pet pet= (Pet) killer;
							info.setOwnerId(pet.getOwnerId());
						}
					}	
				}else{					
					Hatred maxHatred = monster.getMaxHatred();				
					if(isOwner()&&maxHatred!=null){
						if(maxHatred.getTarget() instanceof Player){
							//如果是宠物攻击 则需要另行处理
							Player player=(Player) maxHatred.getTarget();
							info.setOwnerId(player.getId());	
						}	
						if(maxHatred.getTarget() instanceof Pet){
							//宠物攻击掉落
							Pet pet= (Pet) maxHatred.getTarget();
							info.setOwnerId(pet.getOwnerId());
						}
					}	
				}
				info.setDropTime(System.currentTimeMillis());
				MapDropInfo mapDropInfo = new MapDropInfo(info, item, map,0);
				mapDropInfo.getHideSet().addAll(monster.getHideSet());
				mapDropInfo.getShowSet().addAll(monster.getShowSet());
				mapDropInfo.setShow(monster.isShow());
				return mapDropInfo;	
			
			
		}
		return null;
	}
}
