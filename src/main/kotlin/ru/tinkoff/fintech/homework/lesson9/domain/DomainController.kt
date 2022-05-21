package ru.tinkoff.fintech.homework.lesson9.domain

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain
import ru.tinkoff.fintech.homework.lesson9.domain.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson9.domain.service.DomainService

@RestController
@RequestMapping("/domain")
class DomainController(private val domainService: DomainService) {
    @GetMapping("/info/{id}")
    fun getDomain(@PathVariable id: Int): CreateResponse<Domain> = domainService.getById(id)

    @PostMapping(value = ["/create"], consumes = ["application/json"], produces = ["application/json"])
    fun createDomain(@RequestBody domainName: String): CreateResponse<Domain> = domainService.create(domainName)
}