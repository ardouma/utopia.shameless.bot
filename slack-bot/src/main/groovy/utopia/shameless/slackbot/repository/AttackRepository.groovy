package utopia.shameless.slackbot.repository

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import utopia.shameless.slackbot.model.internal.UtopiaWarRoomEvent

@Repository
class AttackRepository extends EventRepository<UtopiaWarRoomEvent>{
    @Override
    String getCollectionName() {
        return "attacks"
    }

    @Override
    Query buildQuery(UtopiaWarRoomEvent event) {
        Query query = new Query()
        query.addCriteria(Criteria.byExample())
        return null
    }
}
