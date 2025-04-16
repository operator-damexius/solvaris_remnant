package data.missions.srss_shiptestmission;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

	public void defineMission(MissionDefinitionAPI api) {

		// Set up the fleets
		api.initFleet(FleetSide.PLAYER, "SRSS", FleetGoal.ATTACK, false, 10);
		api.initFleet(FleetSide.ENEMY, "", FleetGoal.ATTACK, true, 10);

		// Set a blurb for each fleet
		api.setFleetTagline(FleetSide.PLAYER, "The Solvaris Remnant.");
		api.setFleetTagline(FleetSide.ENEMY, "The Test Target.");

		// These show up as items in the bulleted list under
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Test out Solvaris Remnant ships.");

		// Set up the player's fleet
		api.addToFleet(FleetSide.PLAYER, "srss_forbearance_standard", FleetMemberType.SHIP,"SRSS Foundation", true);
		api.addToFleet(FleetSide.PLAYER, "srss_endurance_standard", FleetMemberType.SHIP,"SRSS Focus", true);
		api.addToFleet(FleetSide.PLAYER, "srss_perpetuation_standard", FleetMemberType.SHIP,"SRSS Cross", true);
		api.addToFleet(FleetSide.PLAYER, "srss_stamina_standard", FleetMemberType.SHIP,"SRSS Multitude", true);
		api.addToFleet(FleetSide.PLAYER, "srss_determinant_stardard", FleetMemberType.SHIP,"SRSS Assault", true);
		api.addToFleet(FleetSide.PLAYER, "srss_determinant_fuel_stardard", FleetMemberType.SHIP,"SRSS Fountain", true);
		api.addToFleet(FleetSide.PLAYER, "srss_continuance_standard", FleetMemberType.SHIP,"SRSS Burst", true);
        api.addToFleet(FleetSide.PLAYER, "srss_austerity_standard", FleetMemberType.SHIP,"SRSS Impact", true);


		// Set up the enemy fleet
		api.addToFleet(FleetSide.ENEMY, "conquest_Standard", FleetMemberType.SHIP,"AUTO Compound", true);
		api.addToFleet(FleetSide.ENEMY, "conquest_Standard", FleetMemberType.SHIP,"AUTO Color", true);

		// Set up the map.
		float width = 24000f;
		float height = 18000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);

		float minX = -width/2;
		float minY = -height/2;

		for (int i = 0; i < 15; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 900f;
			api.addNebula(x, y, radius);
		}

	}

}
