package whois.db

import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import whois.model.Event
import whois.model.EventStatus

@Profile("whois")
@Repository
interface EventRepository : PagingAndSortingRepository<Event, Int>, CrudRepository<Event, Int> {
    fun findByStatus(status : EventStatus) : List <Event>
}