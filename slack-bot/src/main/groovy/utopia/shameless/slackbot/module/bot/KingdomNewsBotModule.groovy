package utopia.shameless.slackbot.module.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.util.Pair
import org.springframework.stereotype.Component
import utopia.shameless.slackbot.configuration.ModuleProperties
import utopia.shameless.slackbot.model.internal.Message
import utopia.shameless.slackbot.model.internal.UtopiaEventType
import utopia.shameless.slackbot.model.internal.UtopiaWarRoomEvent
import utopia.shameless.slackbot.model.internal.UtopianUser
import utopia.shameless.slackbot.repository.AttackRepository
import utopia.shameless.slackbot.repository.KingdomRepository

import javax.annotation.PostConstruct
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class KingdomNewsBotModule extends BotModule {

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
    Pattern corePattern = Pattern.compile("^NEW KDNEWS:")

    @Autowired
    ModuleProperties moduleProperties

    @Autowired
    AttackRepository repository

    @Autowired
    KingdomRepository kdRepository

    List<Object[]>regexes = new LinkedList<>()


    @PostConstruct
    void init() {
        moduleProperties.bot.kdnews.each {
            if(it.value.inbound)
                regexes.add([it.key, Pattern.compile(it.value.inbound), true])
            if(it.value.outbound)
                regexes.add([it.key, Pattern.compile(it.value.outbound), false])
        }
        /*
        regexes.add(Pair.of(UtopiaEventType.Abduct, Pattern.compile(moduleProperties.bot.kdnews.abduct)))
        regexes.add(Pair.of(UtopiaEventType.Ambush, Pattern.compile(moduleProperties.bot.kdnews.ambush)))
        regexes.add(Pair.of(UtopiaEventType.Bounce, Pattern.compile(moduleProperties.bot.kdnews.bounce)))
        regexes.add(Pair.of(UtopiaEventType.Plunder, Pattern.compile(moduleProperties.bot.kdnews.plunder)))
        regexes.add(Pair.of(UtopiaEventType.Raze, Pattern.compile(moduleProperties.bot.kdnews.raze)))
        regexes.add(Pair.of(UtopiaEventType.TraditionalMarch, Pattern.compile(moduleProperties.bot.kdnews.traditionalMarch)))
        */
    }

    @Override
    boolean accept(Message event) {
        return corePattern.matcher(event.getMessage()).matches();
    }

    @Override
    void execute(Message msg) {
        Matcher matcher
        Object[] match = regexes.find {
            matcher = it[1].matcher(msg.message)
            matcher.matches()
        }
        UtopiaWarRoomEvent event = new UtopiaWarRoomEvent()

        event.attacker = new UtopianUser(kingdom: matcher.group("attackerKD"), province: matcher.group("attacker"))
        event.defender = new UtopianUser(kingdom: matcher.group("attackeeKd"), province: matcher.group("attackee"))
        event.damage = matcher.group("dmg") as Integer
        event.eventType = match[0]
        event.eventCategory = match[0].getCategory()
        event.date = null
        event.anno = null
        event.eventTime = new Date()

        repository.saveUtopiaEvent(event)
//        kdRepository.increamentHostility()

    }
}
