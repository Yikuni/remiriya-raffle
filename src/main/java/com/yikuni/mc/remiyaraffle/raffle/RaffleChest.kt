package com.yikuni.mc.remiyaraffle.raffle

import com.yikuni.mc.remiyaraffle.event.PlayerRaffleEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class RaffleChest() {
    lateinit var name: String
    private var itemList = mutableListOf<RaffleItem>()

    constructor(name: String) : this() {this.name = name}

    fun menu(inventory: Inventory){
        itemList.forEach {
            inventory.addItem(it.provideGuiItem())
        }
    }

    fun raffle(player: Player){
        player.inventory.itemInMainHand.amount -= 1
        var i = Random.nextInt(itemList.sumOf { it.weight })
        itemList.forEach {
            i -= it.weight
            if (i < 0){
                it.playerGetItem(player)
                Bukkit.getPluginManager().callEvent(PlayerRaffleEvent(player, it.itemStack))
                return
            }
        }
    }

    fun addItem(itemStack: ItemStack, weight: Int){
        itemList.add(RaffleItem(itemStack, weight))
    }

    fun removeItem(index: Int){
        itemList.removeAt(index)
    }



}