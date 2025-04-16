package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.BaseModPlugin;
import exerelin.campaign.SectorManager;

import data.scripts.Vailara;
import data.scripts.Vespera;

public class EnvisionModPlugin extends BaseModPlugin {

    @Override
    public void onNewGame() {
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        Global.getLogger(EnvisionModPlugin.class).info("Nexerelin enabled: " + haveNexerelin);
        
        if (!haveNexerelin || SectorManager.getCorvusMode()) {
            try {
                Vespera vespera = new Vespera();
                vespera.generate(Global.getSector());
                Global.getLogger(EnvisionModPlugin.class).info("Successfully generated Vespera system.");
            } catch (Exception e) {
                Global.getLogger(EnvisionModPlugin.class).error("Error generating Vespera system: ", e);
            }

            try {
                Vailara vailara = new Vailara();
                vailara.generate(Global.getSector());
                Global.getLogger(EnvisionModPlugin.class).info("Successfully generated Vailara system.");
            } catch (Exception e) {
                Global.getLogger(EnvisionModPlugin.class).error("Error generating Vailara system: ", e);
            }
        } else {
            Global.getLogger(EnvisionModPlugin.class).info("Nexerelin is enabled; Vespera and Vailara system generation skipped.");
        }
    }
}
