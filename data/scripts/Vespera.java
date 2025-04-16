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

public class Vespera {

    public Vespera() {}

    // =========================================================================
    // SYSTEM GENERATION
    // =========================================================================
    
    public void generate(SectorAPI sector) {
        try {
            // =================================================================
            // SYSTEM SETUP
            // =================================================================
            StarSystemAPI system = sector.createStarSystem("Vespera");
            LocationAPI hyper = Global.getSector().getHyperspace();

            // Main nebula
            SectorEntityToken vespera = Misc.addNebulaFromPNG(
                "data/campaign/terrain/vespera_nebula.png",
                0, 0,
                system,
                "terrain", "nebula_blue",
                4, 4, StarAge.YOUNG
            );

            // Primary star
            PlanetAPI vespera_star = system.initStar(
                "vespera",
                "star_blue_giant",
                900,
                200,
                5f,
                50,
                2.0f
            );
            
            // System-wide settings
            system.setBackgroundTextureFilename("graphics/backgrounds/background5.jpg");
            system.autogenerateHyperspaceJumpPoints(true, true);
            system.setLightColor(new Color(245, 250, 255));

            // =================================================================
            // BELTS & RINGS
            // =================================================================
            system.addAsteroidBelt(vespera_star, 5, 6300, 256f, 300, 256f, Terrain.ASTEROID_BELT, null );
            system.addRingBand(vespera_star, "misc", "rings_ice0", 512f, 1, Color.BLUE, 512f, 6300, 512f, Terrain.RING, "Vespera Ring Belt");

            // =================================================================
            // PLANETS AND MOONS
            // =================================================================
            
            // Solara (Rocky Metallic)
            PlanetAPI planet0 = system.addPlanet(
                "solara", vespera_star, "Solara", "rocky_metallic", 
                177, 170, 4300, 365
            );
            planet0.getSpec().setPlanetColor(new Color(116,185,238,255));
            planet0.getSpec().setIconColor(new Color(116,185,238,255));
            planet0.getSpec().setAtmosphereColor(new Color(100, 200, 255, 200));
            planet0.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
            planet0.getSpec().setGlowColor(new Color(30, 100, 200, 255));
            planet0.getSpec().setUseReverseLightForGlow(true);
            planet0.applySpecChanges();
            planet0.setCustomDescriptionId("planet_solara");

            // Nimoria (Terran with Moon)
            PlanetAPI planet1 = system.addPlanet(
                "nimoria", vespera_star, "Nimoria", "terran", 
                24, 130, 8000, 325
            );
            planet1.setCustomDescriptionId("planet_nimoria");

            // Amaris (Nimoria's Moon)
            PlanetAPI planet1a = system.addPlanet(
                "amaris", planet1, "Amaris", "barren2", 
                14, 50, 700, 31
            );
            planet1a.setCustomDescriptionId("planet_amaris");

            // Vesperis (Arid with Rings)
            PlanetAPI planet2 = system.addPlanet(
                "vesperis", vespera_star, "Vesperis", "water", 
                38, 290, 11000, 365
            );
            planet2.getSpec().setPlanetColor(new Color(0, 190,255,255));
            planet2.getSpec().setIconColor(new Color(0, 190,255,255));
            planet2.getSpec().setAtmosphereColor(new Color(100, 200, 255, 200));
            planet2.getSpec().setCloudColor(new Color(230, 230, 230, 200));
            planet2.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
            planet2.getSpec().setGlowColor(new Color(30, 100, 200, 255));
            planet2.getSpec().setUseReverseLightForGlow(true);
            planet2.applySpecChanges();
            planet2.setCustomDescriptionId("planet_vesperis");
            system.addRingBand(planet2, "misc", "rings_ice0", 256f, 1, Color.BLUE, 256f, 1000, 256f, Terrain.RING, "Vesperis Ice Rings");

            // Aetheris (Main Habitable Planet)
            PlanetAPI planet3 = system.addPlanet(
                "aetheris", vespera_star, "Aetheris", "terran", 
                24, 180, 17000, 681
            );
            planet3.getSpec().setPlanetColor(new Color(200, 255, 245, 255));
            planet3.getSpec().setIconColor(new Color(200, 255, 245, 255));
            planet3.getSpec().setAtmosphereColor(new Color(220, 250, 240, 150));
            planet3.getSpec().setCloudColor(new Color(220, 250, 240, 200));
            planet3.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "aurorae"));
            planet3.getSpec().setGlowColor(new Color(30, 100, 200, 255));
            planet3.getSpec().setUseReverseLightForGlow(true);
            planet3.applySpecChanges();
            planet3.setCustomDescriptionId("planet_aetheris");

            // Aetheris Market Conditions
            Misc.initConditionMarket(planet3);
            planet3.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
            planet3.getMarket().addCondition(Conditions.MILD_CLIMATE);
            planet3.getMarket().addCondition(Conditions.FARMLAND_RICH);
            planet3.getMarket().addCondition(Conditions.ORE_MODERATE);
            planet3.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
            planet3.getMarket().addCondition(Conditions.HABITABLE);
            planet3.getMarket().addCondition(Conditions.ORGANICS_ABUNDANT);

            // Ferronox (Aetheris Moon 1)
            PlanetAPI planet3a = system.addPlanet(
                "ferronox", planet3, "Ferronox", "barren", 
                34, 50, 1000, 31
            );
            Misc.initConditionMarket(planet3a);
            planet3a.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
            planet3a.getMarket().addCondition(Conditions.LOW_GRAVITY);
            planet3a.getMarket().addCondition(Conditions.NO_ATMOSPHERE);

            // Celestra (Aetheris Moon 2)
            PlanetAPI planet3b = system.addPlanet(
                "celestra", planet3, "Celestra", "frozen2", 
                5, 60, 800, 62
            );
            Misc.initConditionMarket(planet3b);
            planet3b.getMarket().setSurveyLevel(MarketAPI.SurveyLevel.FULL);
            planet3b.getMarket().addCondition(Conditions.COLD);
            planet3b.getMarket().addCondition(Conditions.LOW_GRAVITY);
            planet3b.getMarket().addCondition(Conditions.NO_ATMOSPHERE);

            // =================================================================
            // MAGNETIC FIELD
            // =================================================================
            SectorEntityToken planet3_field = system.addTerrain(
                Terrain.MAGNETIC_FIELD,
                new MagneticFieldParams(
                    planet3.getRadius() + 150f,
                    (planet3.getRadius() + 150f) / 2f,
                    planet3,
                    planet3.getRadius() + 50f,
                    planet3.getRadius() + 50f + 200f,
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
            planet3_field.setCircularOrbit(planet3, 0, 0, 100);

            // =================================================================
            // ORBITAL ENTITIES
            // =================================================================
            
            // Vesperis Comm Relay
            SectorEntityToken entity1 = system.addCustomEntity(
                "vespera_comm", "Vesperis Comm", 
                "comm_relay", "solvaris"
            );
            entity1.setCircularOrbitPointingDown(planet2, 0, 780, 31);

            // Aetheris Sensor Array
            SectorEntityToken entity3 = system.addCustomEntity(
                "vespera_sensor", "Aetheris Sensor", 
                "sensor_array", "solvaris"
            );
            entity3.setCircularOrbitPointingDown(planet3, 0, 1000, 100);

            // Solvaris Gate
            SectorEntityToken entity4 = system.addCustomEntity(
                "vespera_gate", "Solvaris Gate", 
                "inactive_gate", null
            );
            entity4.setCircularOrbitPointingDown(vespera_star, 0, 21000, 600);
            entity4.setCustomDescriptionId("solvaris_gate");

            // Gate Nav Buoy
            SectorEntityToken entity2 = system.addCustomEntity(
                "vespera_buoy", "Gate Buoy", 
                "nav_buoy", "solvaris"
            );
            entity2.setCircularOrbitPointingDown(entity4, 0, 1000, 31);

            // Coronal Tap (Vespera's Jump Point)
            SectorEntityToken entity5 = system.addCustomEntity(
                "vespera_tap", "Coronal Tap", 
                "coronal_tap", null
            );
            entity5.setCircularOrbitPointingDown(vespera_star, 0, 1100, 31);

            // =================================================================
            // NEBULA CLOUDS
            // =================================================================
            String nebulaPattern = 
                "          " +
                " x   xxxx " +
                "   xxx    " +
                "  xx  xx  " +
                "  xxxxx   " +
                "  xxxxx x " +
                "   xxxx   " +
                "x  xxxxx  " +
                "  xxxxxxx " +
                "    xxx   ";

            // L4 Nebula
            SectorEntityToken L4_nebula = system.addTerrain(
                Terrain.NEBULA,
                new BaseTiledTerrain.TileParams(
                    nebulaPattern,
                    10, 10,
                    "terrain", "nebula_blue",
                    4, 4, null
                )
            );
            L4_nebula.setCircularOrbit(vespera_star, 60 + 60, 11500, 820);

            // L5 Nebula
            SectorEntityToken L5_nebula = system.addTerrain(
                Terrain.NEBULA,
                new BaseTiledTerrain.TileParams(
                    nebulaPattern,
                    10, 10,
                    "terrain", "nebula_blue",
                    4, 4, null
                )
            );
            L5_nebula.setCircularOrbit(vespera_star, 60 - 60, 11500, 820);

            // =================================================================
            // CLEANUP
            // =================================================================
            cleanup(system);

        } catch (Exception e) {
            Global.getLogger(Vespera.class).error("Error generating Vespera system: ", e);
        }
    }

    // =========================================================================
    // HYPERSPACE CLEANUP
    // =========================================================================
    
    void cleanup(StarSystemAPI system) {
        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 5f;
        float radius = system.getMaxRadiusInHyperspace();

        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 5f, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}