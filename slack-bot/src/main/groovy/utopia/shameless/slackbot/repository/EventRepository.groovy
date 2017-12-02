package utopia.shameless.slackbot.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import utopia.shameless.slackbot.model.internal.UtopiaEvent
import utopia.shameless.slackbot.model.internal.UtopiaEventCategoryType
import static utopia.shameless.slackbot.model.internal.UtopiaEventCategoryType.*


abstract class EventRepository<T extends UtopiaEvent> {

    @Autowired
    MongoTemplate mongo

    abstract String getCollectionName()
    abstract Query buildQuery(T event)

    void saveUtopiaEvent(T event) {
        mongo.save(event, getCollectionName())
    }

    /**
     *
     * TODO this should search the DB for possible duplicates
     * @param event
     * @return
     */
    T findEvent(T event) {
        Query query = buildQuery(event)
        return mongo.find(query, T, getCollectionName())
    }

    Collection<T> findAll(){
        return mongo.findAll(T, getCollectionName())
    }

}
