package data.scripts.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class srss_principles_core extends BaseHullMod {
    
    // Expanded Magazines
    private static final float AMMO_BONUS = 100f;
    
    // Expanded Missile Racks
    private static final float MISSILE_AMMO_BONUS = 200f;
    
    // Hardened Subsystems
    private static final float PEAK_BONUS_PERCENT = 80f;
    private static final float DEGRADE_REDUCTION_PERCENT = 25f;
    
    // Solar Shielding
    private static final float CORONA_EFFECT_MULT = 0.25f;
    private static final float ENERGY_DAMAGE_MULT = 0.9f;
    
    // Accelerated Shield Emitter
    private static final float SHIELD_BONUS_TURN = 100f;
    private static final float SHIELD_BONUS_UNFOLD = 200f;
    
    // Advanced Turret Gyros
    private static final float TURRET_SPEED_BONUS = 75f;
    
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        // Expanded Magazines Effects
        stats.getBallisticAmmoBonus().modifyPercent(id, AMMO_BONUS);
        stats.getEnergyAmmoBonus().modifyPercent(id, AMMO_BONUS);
        
        // Expanded Missile Racks Effects
        stats.getMissileAmmoBonus().modifyPercent(id, MISSILE_AMMO_BONUS);
        
        // Hardened Subsystems Effects
        stats.getPeakCRDuration().modifyPercent(id, PEAK_BONUS_PERCENT);
        stats.getCRLossPerSecondPercent().modifyMult(id, 1f - DEGRADE_REDUCTION_PERCENT / 100f);
        
        // Solar Shielding Effects
        stats.getEnergyDamageTakenMult().modifyMult(id, ENERGY_DAMAGE_MULT);
        stats.getEnergyShieldDamageTakenMult().modifyMult(id, ENERGY_DAMAGE_MULT);
        stats.getDynamic().getStat(Stats.CORONA_EFFECT_MULT).modifyMult(id, CORONA_EFFECT_MULT);
        
        // Accelerated Shield Emitter Effects
        stats.getShieldTurnRateMult().modifyPercent(id, SHIELD_BONUS_TURN);
        stats.getShieldUnfoldRateMult().modifyPercent(id, SHIELD_BONUS_UNFOLD);
        
        // Advanced Turret Gyros Effects
        stats.getWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);
        stats.getBeamWeaponTurnRateBonus().modifyPercent(id, TURRET_SPEED_BONUS);
    }
    
    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        switch (index) {
            case 0: return "" + (int) AMMO_BONUS + "%";
            case 1: return "" + (int) MISSILE_AMMO_BONUS + "%";
            case 2: return "" + (int) PEAK_BONUS_PERCENT + "%";
            case 3: return "" + (int) DEGRADE_REDUCTION_PERCENT + "%";
            case 4: return "" + (int) (CORONA_EFFECT_MULT * 100) + "%";
            case 5: return "" + (int) (ENERGY_DAMAGE_MULT * 100) + "%";
            case 6: return "" + (int) SHIELD_BONUS_TURN + "%";
            case 7: return "" + (int) SHIELD_BONUS_UNFOLD + "%";
            case 8: return "" + (int) TURRET_SPEED_BONUS + "%";
            default: return null;
        }
    }
    
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship != null && ship.getHullSpec() != null;
    }
    
    public String getUnapplicableReason(ShipAPI ship) {
        return "Not applicable to this ship.";
    }
}
