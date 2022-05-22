package ru.tinkoff.fintech.homework.lesson9.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson9.configuration.ControllerExceptionHandler
import ru.tinkoff.fintech.homework.lesson9.db.DomainDao
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain
import ru.tinkoff.fintech.homework.lesson9.domain.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson9.domain.service.external.DomainRegistrationService

@Service
class DomainService(
    private val domainDao: DomainDao,
    private val domainRegistrationService: DomainRegistrationService
) {
    private val exceptionHandler = ControllerExceptionHandler()

    fun getById(id: Int): CreateResponse<Domain> = try {
        val domain = domainDao.getById(id)
        if (domain.fetched != null){
            CreateResponse(domain, null)
        }
        else{
            throw Exception("Cannot fetch domain info, please try later.")
        }
    } catch (e: Exception) {
        exceptionHandler.handleException(e)
        CreateResponse(null, e.message)
    }

    fun create(domainName: String): CreateResponse<Domain> =
        try {
            var id: Int? = null
                CoroutineScope(Dispatchers.Default).launch {
                    withContext(Dispatchers.IO) {
                        id = domainDao.create(domainName)
                    }
                    if (id != null) {
                        val responseDomain = domainRegistrationService.createDomain(id!!, domainName)
                        if (responseDomain != null) {
                            withContext(Dispatchers.IO) {
                                domainDao.update(
                                    id!!,
                                    responseDomain.created,
                                    responseDomain.fetched,
                                    responseDomain.paidTill,
                                    responseDomain.freeDate
                                )
                            }
                        } else {
                            withContext(Dispatchers.IO) {
                                domainDao.delete(id!!)
                            }
                            throw Exception("An error occurred while creating domain on registrator's side")
                        }
                    } else {
                        throw Exception("Cannot create domain: database error")
                    }
                }
                CreateResponse(Domain(id, domainName, null, null, null, null), "OK")
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
            CreateResponse(Domain(null, domainName, null, null, null, null), "Error")
        }

}