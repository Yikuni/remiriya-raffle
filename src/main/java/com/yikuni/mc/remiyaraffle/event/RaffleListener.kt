package com.yikuni.mc.remiyaraffle.event

import com.yikuni.mc.reflect.annotation.YikuniEvent
import com.yikuni.mc.reflect.context.menu.MenuFacade
import com.yikuni.mc.remiyaraffle.raffle.RaffleChest
import com.yikuni.mc.remiyaraffle.raffle.RaffleManager
import com.yikuni.mc.rumiyalib.utils.sender
import org.bukkit.Material
import org.bukkit.block.TileState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

@YikuniEvent
class RaffleListener: Listener {

    @EventHandler
    fun raffle(event: PlayerInteractEvent){
        if (event.clickedBlock?.type in arrayOf(Material.CHEST, Material.ENDER_CHEST, Material.SHULKER_BOX)){
            // 如果是可能作为抽奖箱的方块
            val chest = event.clickedBlock!!
            val container = (chest.state as TileState).persistentDataContainer
            val name = container[RaffleManager.key, PersistentDataType.STRING]?:return
            if (event.action == Action.RIGHT_CLICK_BLOCK) {
                event.isCancelled = true
                MenuFacade.open(event.player, "抽奖箱", name)
            }
            else if (event.action == Action.LEFT_CLICK_BLOCK && event.player.isSneaking){
                // 如果是要抽奖
                val key = event.player.inventory.itemInMainHand?:return
                val container1 = key.itemMeta?.persistentDataContainer?:return
                val s = container1[RaffleManager.key, PersistentDataType.STRING]?:return
                if (s != name){
                    return
                }
                val select = RaffleManager.raffleTable.select(RaffleChest(name))
                if (select.isEmpty()){
                    event.player.sender().error("不存在该抽奖箱")
                }else{
                    select[0].raffle(event.player)
                }
                event.isCancelled = true
            }
        }
    }
}