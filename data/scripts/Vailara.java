package data.scripts;

import java.awt.Color;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.procgen.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.BaseTiledTerrain;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin.MagneticFieldParams;
import com.fs.starfarer.api.impl.campaign.ids.Terrain;
import com.fs.starfarer.api.util.Misc;

public class Vailara {

    public Vailara() {}

    // =========================================================================
    // SYSTEM GENERATION
    // =========================================================================
    
    public void generate(SectorAPI sector) {
        try {
            // Create system and hyperspace reference
            StarSystemAPI system = sector.createStarSystem("Vailara");
            LocationAPI hyper = Global.getSector().getHyperspace();

            // =================================================================
            // PRIMARY STAR AND NEBULA
            // =================================================================
            SectorEntityToken vailara = Misc.addNebulaFromPNG(
                "data/campaign/terrain/vailara_nebula.png",
                0, 0, 
                system, 
                "terrain", "nebula_blue", 
                4, 4, StarAge.YOUNG
            );

            PlanetAPI vailara_star = system.initStar(
                "vailara", "star_white", 
                600f, 400, 10f, 100, 2.0f
            );
            
            system.setLightColor(new Color(245, 250, 255));
            system.autogenerateHyperspaceJumpPoints(true, true);
            system.setBackgroundTextureFilename("graphics/backgrounds/background5.jpg");

            // =================================================================
            // PLANETS
            // =================================================================
            
            // Nyxara (Arid World)
            PlanetAPI planet0 = system.addPlanet(
                "nyxara", vailara_star, "Nyxara", "arid", 
                10, 110, 2150, 110
            );
            planet0.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "aurorae"));
            planet0.getSpec().setGlowColor(new Color(240, 220, 130,255));
            planet0.getSpec().setUseReverseLightForGlow(true);
            planet0.applySpecChanges();
            planet0.setInteractionImage("illustrations", "mine");
            planet0.setCustomDescriptionId("planet_nyxara");

            // Eldara (Ice Giant with Rings)
            PlanetAPI planet1 = system.addPlanet(
                "eldara", vailara_star, "Eldara", "ice_giant", 
                98, 400, 4750, 200
            );
            system.addRingBand(planet1, "misc", "rings_dust0", 256f, 2, Color.white, 256f, 650, 128f, Terrain.RING, "Eldara Rings");
            system.addRingBand(planet1, "misc", "rings_ice0", 256f, 2, Color.white, 256f, 650, 128f, Terrain.RING, "Eldara Rings");
            planet1.getSpec().setPlanetColor(new Color(200, 255, 245, 255));
            planet1.getSpec().setAtmosphereColor(new Color(220, 250, 240, 150));
            planet1.getSpec().setCloudColor(new Color(220, 250, 240, 200));
            planet1.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "aurorae"));
            planet1.getSpec().setGlowColor(new Color(0, 255, 205, 62));
            planet1.getSpec().setUseReverseLightForGlow(true);
            planet1.applySpecChanges();
            planet1.setCustomDescriptionId("planet_eldara");
            
            // Eldara Market Conditions
            Misc.initConditionMarket(planet1);
            planet1.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
            planet1.getMarket().addCondition(Conditions.COLD);
            planet1.getMarket().addCondition(Conditions.EXTREME_WEATHER);
            planet1.getMarket().addCondition(Conditions.DENSE_ATMOSPHERE);
            planet1.getMarket().addCondition(Conditions.HIGH_GRAVITY);
            planet1.getMarket().addCondition(Conditions.VOLATILES_PLENTIFUL);

            // Orvion (Eldara's Moon)
            PlanetAPI planet1a = system.addPlanet(
                "orvion", planet1, "Orvion", "tundra", 
                38, 70, 900, 74
            );
            planet1a.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "banded"));
            planet1a.getSpec().setGlowColor(new Color(0, 200, 255, 120));
            planet1a.getSpec().setUseReverseLightForGlow(true);
            planet1a.applySpecChanges();
            planet1a.setCustomDescriptionId("planet_orvion");

            // Zyphir (Ruined Arid World)
            PlanetAPI planet2 = system.addPlanet(
                "zyphir", vailara_star, "Zyphir", "desert", 
                20, 180, 7300, 340
            );
            planet2.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "banded"));
            planet2.getSpec().setGlowColor(new Color(0, 200, 255, 180));
            planet2.getSpec().setUseReverseLightForGlow(true);
            planet2.getSpec().setPlanetColor(new Color(156, 46, 53, 255));
            planet2.getSpec().setIconColor(new Color(156, 46, 53, 255));
            planet2.applySpecChanges();
            planet2.setCustomDescriptionId("planet_zyphir");
            
            // Zyphir Market Conditions
            Misc.initConditionMarket(planet2);
            planet2.getMarket().addCondition(Conditions.DECIVILIZED);
            planet2.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
            planet2.getMarket().getFirstCondition(Conditions.RUINS_WIDESPREAD).setSurveyed(true);
            planet2.getMarket().addCondition(Conditions.ORE_RICH);
            planet2.getMarket().addCondition(Conditions.FARMLAND_ADEQUATE);
            planet2.getMarket().addCondition(Conditions.ORGANICS_TRACE);

            // =================================================================
            // ENTITIES AND STRUCTURES
            // =================================================================
            
            // Eldara Relay
            SectorEntityToken entity1 = system.addCustomEntity(
                "eldara_relay", "Eldara Relay", 
                "comm_relay", "solvaris"
            );
            entity1.setCircularOrbitPointingDown(planet1, 0, 1000, 100);

            // Zyphir Sensor Array
            SectorEntityToken entity2 = system.addCustomEntity(
                "zyphir_sensor", "Zyphir Sensor", 
                "sensor_array", "solvaris"
            );
            entity2.setCircularOrbitPointingDown(planet2, 180 - 60, 900, 100);

            // Vailara Nav Buoy
            SectorEntityToken entity3 = system.addCustomEntity(
                "vailara_bouy", "Vailara Bouy", 
                "nav_buoy", "solvaris"
            );
            entity3.setCircularOrbitPointingDown(vailara_star, 60 + 60, 10000, 300);

            // =================================================================
            // TERRAIN FEATURES
            // =================================================================
            
            // Eldara Magnetic Field
            SectorEntityToken planet1_field = system.addTerrain(
                Terrain.MAGNETIC_FIELD,
                new MagneticFieldParams(
                    planet1.getRadius() + 150f,
                    (planet1.getRadius() + 150f) / 2f,
                    planet1,
                    planet1.getRadius() + 50f,
                    planet1.getRadius() + 50f + 200f,
                    new Color(66,97,143,50),
                    0.5f,
                    new Color(66,97,143,255),
                    new Color(98,121,165,255),
                    new Color(149,166,209,255),
                    new Color(173,186,221,255),
                    new Color(213,227,254,255),
                    new Color(230,239,255,255),
                    new Color(255,255,255,255)
                )
            );
            planet1_field.setCircularOrbit(planet1, 0, 0, 100);

            // L4 Nebula
            SectorEntityToken L4_nebula = system.addTerrain(Terrain.NEBULA, 
                new BaseTiledTerrain.TileParams(
                    "          " +
                    " x   xxxx " +
                    "   xxx    " +
                    "  xx  xx  " +
                    "  xxxxx   " +
                    "  xxxxx x " +
                    "   xxxx   " +
                    "x  xxxxx  " +
                    "  xxxxxxx " +
                    "    xxx   ",
                    10, 10,
                    "terrain", "nebula_blue", 4, 4, null
                )
            );
            L4_nebula.setCircularOrbit(vailara_star, 60 + 60, 11500, 820);

            // L5 Nebula
            SectorEntityToken L5_nebula = system.addTerrain(Terrain.NEBULA, 
                new BaseTiledTerrain.TileParams(
                    "          " +
                    " x   xxxx " +
                    "   xxx    " +
                    "  xx  xx  " +
                    "  xxxxx   " +
                    "  xxxxx x " +
                    "   xxxx   " +
                    "x  xxxxx  " +
                    "  xxxxxxx " +
                    "    xxx   ",
                    10, 10,
                    "terrain", "nebula_blue", 4, 4, null
                )
            );
            L5_nebula.setCircularOrbit(vailara_star, 60 - 60, 11500, 820);

            // =================================================================
            // CLEANUP
            // =================================================================
            cleanup(system);

        } catch (Exception e) {
            Global.getLogger(Vailara.class).error("Error generating Vailara system: ", e);
        }
    }

    // =========================================================================
    // HYPERSPACE CLEANUP
    // =========================================================================
    
    void cleanup(StarSystemAPI system) {
        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 2f, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}
