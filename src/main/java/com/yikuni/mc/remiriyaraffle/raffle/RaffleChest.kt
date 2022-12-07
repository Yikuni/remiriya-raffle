package com.yikuni.mc.remiriyaraffle.raffle

import com.yikuni.mc.remiriyaraffle.event.PlayerRaffleEvent
import com.yikuni.mc.rumiyalib.utils.giveItem
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class RaffleChest() {
    lateinit var name: String
    var itemList = mutableListOf<RaffleItem>()

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
                player.giveItem(it.provideItemStack())
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 0.18F, 0.6F)
                player.world.spawnParticle(Particle.TOTEM, player.location, 10, 0.5, 0.5, 0.5)
                Bukkit.getPluginManager().callEvent(PlayerRaffleEvent(player, it.provideItemStack()))
                return
            }
        }
    }

    fun addItem(itemStack: ItemStack, weight: Int){
        itemList.add(RaffleItem(itemStack, weight))
    }

    fun removeItem(index: Int){
        if(index < itemList.size && index > 0){
            itemList.removeAt(index)
        }
    }



}