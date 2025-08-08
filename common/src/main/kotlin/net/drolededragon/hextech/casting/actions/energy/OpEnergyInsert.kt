package net.drolededragon.hextech.casting.actions.energy

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import net.minecraft.core.BlockPos
import net.drolededragon.hextech.canReceiveEnergy
import net.drolededragon.hextech.insertEnergy

object OpEnergyInsert : Action {
    override fun operate(
        env: CastingEnvironment,
        image: List<Iota>,
        continuation: SpellContinuation
    ): OperationResult {
        if (image.size < 2) {
            throw MishapBadLocation(net.minecraft.world.phys.Vec3.ZERO, "hextech.energy_insert.not_enough_args")
        }
        
        val position = image.getVec3(0, 2)
        val blockPos = BlockPos(position.x.toInt(), position.y.toInt(), position.z.toInt())
        
        if (!env.world.isInWorldBounds(blockPos)) {
            throw MishapBadLocation(position, "hextech.energy_insert.out_of_bounds")
        }
        
        // Check if we can actually insert energy at this position
        if (!canReceiveEnergy(env.world, blockPos)) {
            throw MishapBadLocation(position, "hextech.energy_insert.no_energy_receiver")
        }
        
        // Get energy amount
        val energyAmount = image.getDouble(1, 2)
        
        // Convert to long (FE uses long for energy amounts)
        val energyToInsert = maxOf(0, energyAmount.toLong())
        
        // Calculate media cost: 1 dust per 5000 FE
        val mediaCost = (energyToInsert / 5000).toLong()
        
        // Insert the energy
        val success = insertEnergy(env.world, blockPos, energyToInsert)
        
        val resultStack = image.drop(2) + listOf(if (success) Vec3Iota(position) else NullIota())
        
        return OperationResult(
            resultStack,
            continuation,
            mediaCost,
            listOf()
        )
    }
}