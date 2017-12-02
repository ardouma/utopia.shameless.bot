package utopia.shameless.slackbot.model.internal

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = "_id")
@EqualsAndHashCode(excludes = "_id")
abstract class UtopiaEvent {
    String _id
    UtopiaDate date
    Date eventTime
    UtopiaEventType eventType
    UtopiaEventCategoryType eventCategory

}
