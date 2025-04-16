package data.scripts.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.Misc;

public class srss_order_core extends BaseHullMod {

    // AdvancedGroundSupport bonuses
    private static final float GROUND_BONUS = 200f;

    // AdvancedTargetingCore bonuses
    private static final float RANGE_BONUS = 200f;
    private static final float PD_BONUS = 80f;

    // MilitarizedSubsystems bonuses
    private static final float BURN_LEVEL_BONUS = 5f;  // Changed from int to float
    private static final float MAINTENANCE_PERCENT = 100f;

    // HeavyBallisticsIntegration bonuses
    private static final float COST_REDUCTION = 10f;

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        // Apply AdvancedGroundSupport effects
        stats.getDynamic().getMod(Stats.FLEET_GROUND_SUPPORT).modifyFlat(id, GROUND_BONUS);
        
        // Apply AdvancedTargetingCore effects
        stats.getBallisticWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
        stats.getEnergyWeaponRangeBonus().modifyPercent(id, RANGE_BONUS);
        stats.getNonBeamPDWeaponRangeBonus().modifyPercent(id, PD_BONUS);
        stats.getBeamPDWeaponRangeBonus().modifyPercent(id, PD_BONUS);
        
        // Apply MilitarizedSubsystems effects
        stats.getMaxBurnLevel().modifyFlat(id, BURN_LEVEL_BONUS);
        stats.getMinCrewMod().modifyPercent(id, MAINTENANCE_PERCENT);
        
        // Apply HeavyBallisticsIntegration effects
        stats.getDynamic().getMod(Stats.LARGE_BALLISTIC_MOD).modifyFlat(id, -COST_REDUCTION);
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        return true;
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        switch (index) {
            case 0: return "" + (int) GROUND_BONUS;
            case 1: return "" + (int) RANGE_BONUS + "%";
            case 2: return "" + (int) PD_BONUS + "%";
            case 3: return "" + (int) BURN_LEVEL_BONUS;
            case 4: return "" + (int) MAINTENANCE_PERCENT + "%";
            case 5: return "" + (int) COST_REDUCTION;
            default: return null;
        }
    }
}
