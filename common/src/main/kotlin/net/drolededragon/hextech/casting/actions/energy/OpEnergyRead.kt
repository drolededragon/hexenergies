package net.drolededragon.hextech.casting.actions.energy

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import net.minecraft.core.BlockPos
import net.drolededragon.hextech.energy.HextechEnergyAbstractions

object OpEnergyRead : ConstMediaAction {
    override val argc = 1
    override val mediaCost = 0L // No media cost for reading energy
    
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val position = args.getVec3(0, argc)
        val blockPos = BlockPos(position.x.toInt(), position.y.toInt(), position.z.toInt())
        
        if (!env.world.isInWorldBounds(blockPos)) {
            throw MishapBadLocation(position, "hextech.energy_read.out_of_bounds")
        }
        
        // Check if there's an energy capability at this position
        if (!HextechEnergyAbstractions.canReceiveEnergy(env.world, blockPos)) {
            return listOf(NullIota())
        }
        
        // Get current energy and max energy
        val currentEnergy = HextechEnergyAbstractions.getEnergyStored(env.world, blockPos)
        val maxEnergy = HextechEnergyAbstractions.getMaxEnergyStored(env.world, blockPos)
        
        // Return current energy and max energy as doubles
        return listOf(
            DoubleIota(currentEnergy.toDouble()),
            DoubleIota(maxEnergy.toDouble())
        )
    }
}