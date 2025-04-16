package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

import data.scripts.Vailara;
import data.scripts.Vespera;

public class EnvisionGen {
    // This method is called by the engine when generating the sector
    public void generate(SectorAPI sector) {
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("solvaris"); // Add the faction to the bounty system
        initFactionRelationships(sector); 
            (new Vespera()).generate(sector);
            (new Vailara()).generate(sector);
        }

    // This method initializes faction relationships
    // It is called by the generate method
    public static void initFactionRelationships(SectorAPI sector) {
        FactionAPI hegemony     =   sector.getFaction(Factions.HEGEMONY);
	    FactionAPI tritachyon   =   sector.getFaction(Factions.TRITACHYON);
	    FactionAPI pirates      =   sector.getFaction(Factions.PIRATES);
	    FactionAPI independent  =   sector.getFaction(Factions.INDEPENDENT);
	    FactionAPI kol          =   sector.getFaction(Factions.KOL);
	    FactionAPI church       =   sector.getFaction(Factions.LUDDIC_CHURCH);
	    FactionAPI path         =   sector.getFaction(Factions.LUDDIC_PATH);
	    FactionAPI player       =   sector.getFaction(Factions.PLAYER);
	    FactionAPI diktat       =   sector.getFaction(Factions.DIKTAT);
        FactionAPI league       =   sector.getFaction(Factions.PERSEAN);
        FactionAPI solvaris     =   sector.getFaction("solvaris");

        // Set faction relationships
        // -1.0f is hostile, 0.0f is neutral, 1.0f is friendly
        solvaris   .setRelationship(player         .getId(),   0.0f);
        solvaris   .setRelationship(church         .getId(),   0.0f);
        solvaris   .setRelationship(path           .getId(),  -1.0f);
        solvaris   .setRelationship(kol            .getId(),   0.0f);
        solvaris   .setRelationship(hegemony       .getId(),   0.0f);
        solvaris   .setRelationship(pirates        .getId(),   0.5f);
        solvaris   .setRelationship(diktat         .getId(),   0.0f);
        solvaris   .setRelationship(tritachyon     .getId(),   1.0f);
        solvaris   .setRelationship(independent    .getId(),   0.9f);
        solvaris   .setRelationship(league         .getId(),   0.1f);
        solvaris   .setRelationship(path           .getId(),   0.1f);   
    }
}
