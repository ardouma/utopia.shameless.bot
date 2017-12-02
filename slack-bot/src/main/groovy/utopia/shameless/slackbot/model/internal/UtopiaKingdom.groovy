package utopia.shameless.slackbot.model.internal

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.annotation.Id

@ToString
@EqualsAndHashCode
class UtopiaKingdom {
    @Id
    String kingdom

    Integer hostility

}
