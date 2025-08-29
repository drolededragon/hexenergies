package net.drolededragon.hexenergies.casting.actions.energy

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughMedia
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.core.BlockPos
import net.drolededragon.hexenergies.canReceiveEnergy
import net.drolededragon.hexenergies.insertEnergy

object OpEnergyInsert : ConstMediaAction {
    override val argc = 2
    override val mediaCost = 0L // Base cost, actual cost calculated dynamically
    
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val position = args.getVec3(0, argc)
        val blockPos = BlockPos(position.x.toInt(), position.y.toInt(), position.z.toInt())
        
        if (!env.world.isInWorldBounds(blockPos)) {
            throw MishapBadLocation(position, "hexenergies.energy_insert.out_of_bounds")
        }
        
        // Check if we can actually insert energy at this position
        if (!canReceiveEnergy(env.world, blockPos)) {
            throw MishapBadLocation(position, "hexenergies.energy_insert.no_energy_receiver")
        }
        
        // Get energy amount
        val energyAmount = args.getDouble(1, argc)
        
        // Convert to long (FE uses long for energy amounts)
        val energyToInsert = maxOf(0, energyAmount.toLong())
        
        // Calculate media cost: 1 dust per 5000 FE
        val requiredMedia = ((energyToInsert / 5000.0) * MediaConstants.DUST_UNIT).toLong()
        
        // Check if player has enough media
        if (requiredMedia > 0 && env.extractMedia(requiredMedia, true) < requiredMedia) {
            throw MishapNotEnoughMedia(requiredMedia)
        }
        
        // Extract the required media if needed
        if (requiredMedia > 0) {
            env.extractMedia(requiredMedia, false)
        }
        
        // Insert the energy
        val success = insertEnergy(env.world, blockPos, energyToInsert)
        
        return listOf(if (success) Vec3Iota(position) else NullIota())
    }
}