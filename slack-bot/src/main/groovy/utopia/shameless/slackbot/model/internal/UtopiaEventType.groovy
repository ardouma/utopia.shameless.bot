package utopia.shameless.slackbot.model.internal

import static utopia.shameless.slackbot.model.internal.UtopiaEventCategoryType.*
enum UtopiaEventType {
    TraditionalMarch(Attack),
    Ambush(Attack),
    Abduct(Attack),
    Plunder(Attack),
    Bounce(Attack),
    Raze(Attack),
    Massacre(Attack)



    final UtopiaEventCategoryType category
    UtopiaEventType(UtopiaEventCategoryType category){
        this.category = category
    }

}
