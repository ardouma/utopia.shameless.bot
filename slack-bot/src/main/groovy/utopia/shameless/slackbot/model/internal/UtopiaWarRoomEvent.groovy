package utopia.shameless.slackbot.model.internal

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeSuper = true)
@EqualsAndHashCode(callSuper = true)
class UtopiaWarRoomEvent extends UtopiaEvent {
    UtopianUser attacker
    UtopianUser defender
    Integer damage
    Boolean anno
}
