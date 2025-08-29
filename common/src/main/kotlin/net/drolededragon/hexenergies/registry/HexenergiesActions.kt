package net.drolededragon.hexenergies.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import net.drolededragon.hexenergies.casting.actions.spells.OpCongratulate
import net.drolededragon.hexenergies.casting.actions.energy.OpEnergyInsert
import net.drolededragon.hexenergies.casting.actions.energy.OpEnergyRead

object HexenergiesActions : HexenergiesRegistrar<ActionRegistryEntry>(
    HexRegistries.ACTION,
    { HexActions.REGISTRY },
) {
    val CONGRATULATE = make("congratulate", HexDir.WEST, "eed", OpCongratulate)
    
    // Energy patterns following HexCasting naming conventions
    val ENERGY_INSERT = make("energy/insert", HexDir.EAST, "qqqwwwaa", OpEnergyInsert)
    val ENERGY_READ = make("energy/read", HexDir.EAST, "ddwwweee", OpEnergyRead)

    private fun make(name: String, startDir: HexDir, signature: String, action: Action) =
        make(name, startDir, signature) { action }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}
