package utopia.shameless.slackbot.module.bot;

import org.springframework.beans.factory.annotation.Autowired;
import utopia.shameless.slackbot.configuration.ModuleProperties;
import utopia.shameless.slackbot.model.internal.Event;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

public class KingdomNewsBotModule extends BotModule{

    /*
     Traditional March/Anon
NEW KDNEWS: An unknown province from The WolfPack Projects AoD (6:3) invaded Voltron DefenderOfTheUniverse (5:10) and captured 230 acres of land. [fortnight]
Traditional March
NEW KDNEWS: Phallus Deus (3:3) invaded Pickle Rick (5:10) and captured 250 acres of land. [amarantson]
Pillage
NEW KDNEWS: CrazedOne (5:8) attacked and pillaged the lands of Voltron DefenderOfTheUniverse (5:10). [fortnight]
Massacre
NEW KDNEWS: Aquila (7:16) invaded Voltron DefenderOfTheUniverse (5:10) and killed 1,228 people. [fortnight]
Ambush
NEW KDNEWS: An unknown province from Need attacker msg monarch (7:12) ambushed armies from Snow Lion (5:10) and took 99 acres of land. [borknakir]
Abduct
NEW KDNEWS: An unknown province from Colors (2:14) attacked and abducted scientists from NOWcanSMD (5:10). [cretoria]
Failed
NEW KDNEWS: Fat Sheet Bastard (7:7) attempted to invade NOWcanSMD (5:10). [cretoria]

Raze
NEW KDNEWS: Aquila (7:16) razed 67 acres of Voltron DefenderOfTheUniverse (5:10). [fortnight]
     */
    Pattern corePattern = Pattern.compile("^NEW KDNEWS:");

    @Autowired
    ModuleProperties moduleProperties;

    @PostConstruct
    public void init(){
        Pattern.compile(moduleProperties.getBot().getKdnews().getTraditionalMarch());
    }
    //Pattern traditionalMarchPattern = Pattern.compile("\((\d+:d+)\\)")

    @Override
    public boolean accept(Event event) {
        return corePattern.matcher(event.getMessage()).matches();
    }

    @Override
    public void execute(Event event) {

    }
}
