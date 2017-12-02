package utopia.shameless.slackbot.repository

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import utopia.shameless.slackbot.model.internal.UtopiaKingdom

@Repository
@Slf4j
class KingdomRepository {

    @Autowired
    MongoTemplate mongo

    void createKingdom(UtopiaKingdom kingdom) {
        mongo.insert(kingdom)
    }

    UtopiaKingdom increamentHostility(String kingdom, Integer amount) {
        log.debug("Updating ${kingdom}'s hostility by ${amount}")
        Query query = new Query()
        query.addCriteria(Criteria.where("_id").is(kingdom))
        Update update = new Update()
        update.inc("hostility", amount)
        return mongo.findAndModify(query, update, FindAndModifyOptions.options().remove(true).upsert(true), UtopiaKingdom)
    }

    void setHostility(String kingdom, Integer amount) {
        log.debug("Setting ${kingdom}'s hostility to ${amount}")
        Query query = new Query()
        query.addCriteria(Criteria.where("_id").is(kingdom))
        Update update = new Update()
        update.set("hostility", amount)
        mongo.updateFirst(query, update)
    }

    void reduceHostilities() {
        log.info("Reducing all hostilities")
        Collection<UtopiaKingdom> kds = mongo.findAll(UtopiaKingdom)

        kds.each { kd ->
            if (kd.hostility > 0) {
                def hostility = kd.hostility
                if (kd.hostility <= 2) {
                    hostility = 0
                } else {
                    // http://wiki.utopia-game.com/index.php?title=Hostile_Meter
                    int descreaseBy = Math.min(kd.hostility / 6, 2)
                    hostility = kd.hostility - descreaseBy
                }
                setHostility(kd.kingdom, hostility)
            }
        }
    }

    Collection<UtopiaKingdom> kingdomsWithMinHostilities(Integer min) {
        Query query = new Query()
        query.addCriteria(Criteria.where("hostility").gte(min))
        return mongo.find(query, UtopiaKingdom)
    }


}
