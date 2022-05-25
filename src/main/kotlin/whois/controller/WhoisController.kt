package whois.controller

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import whois.service.ClientService
import whois.service.DomainService

@RestController
@RequestMapping("/whois")
open class WhoisController(private val domainService: DomainService, private val clientService: ClientService) {
    @Transactional(timeout = 3)
    @GetMapping("/domain/{name}")
    open fun getDomain(@PathVariable name: String) = domainService.getByName(name)

    @Transactional(timeout = 3)
    @GetMapping("/client/{id}")
    open fun getClient(@PathVariable id: Int) = clientService.getById(id)

    @Transactional(timeout = 3)
    @GetMapping("/domain/prolong/{name}")
    open fun getClient(@PathVariable name: String) = domainService.prolongDomain(name)
}