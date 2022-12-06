package com.yikuni.mc.remiyaraffle.raffle

import com.yikuni.db.main.JsonSerializeStrategy
import com.yikuni.db.main.Table
import com.yikuni.mc.remiyaraffle.RemiriyaRaffle
import org.bukkit.ChatColor
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object RaffleManager {
    lateinit var raffleTable: Table<RaffleChest>

    fun initTable(){
        val database = RemiriyaRaffle.database
        val tableName = "raffleTable"
        raffleTable = if (database.existTable(tableName)) database.getTable(tableName, RaffleChest::class.java)
                        else database.createTable(tableName, RaffleChest::class.java, JsonSerializeStrategy())
    }

    fun getAllChestNames(): List<String>{
        return raffleTable.data.map { it.name }
    }

    fun createRaffleChest(name: String): String{
        if (getRaffleChest(name) != null){
            return "该抽奖箱已存在";
        }
        raffleTable.add(RaffleChest(name))
        return "添加成功"
    }

    fun removeRaffleChest(name: String): String{
        return if (getRaffleChest(name) == null) "该抽奖箱不存在, 移除失败" else {
            raffleTable.deleteSelective(RaffleChest(name))
            "移除成功"
        }
    }

    fun addRaffleItem(chestName: String, itemStack: ItemStack, weight: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        raffleChest.addItem(itemStack, weight)
        return "添加成功"
    }

    fun addRaffleItem(chestName: String, inventory: Inventory, weight: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        inventory.forEach {
            if (it != null) raffleChest.addItem(it, weight)
        }
        return "添加成功"
    }

    fun removeRaffleItem(chestName: String, index: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        raffleChest.removeItem(index)
        return "移除成功"
    }

    fun getFormattedRaffleNameList(): String{
        val nameList = StringBuilder()
        nameList.append("${ChatColor.DARK_GREEN}======抽奖箱列表=====")
        raffleTable.data.forEach {
            nameList.append("\n${it.name}")
        }
        nameList.append("${ChatColor.DARK_GREEN}===============")
        return nameList.toString()
    }

    private fun getRaffleChest(name: String): RaffleChest?{
        return raffleTable.select(RaffleChest(name))[0]
    }
}