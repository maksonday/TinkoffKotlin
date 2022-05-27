package ru.tinkoff.fintech.homework.lesson9

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import io.mockk.clearAllMocks
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.boot.test.context.SpringBootTest
import ru.tinkoff.fintech.homework.lesson9.domain.DomainController
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain
import ru.tinkoff.fintech.homework.lesson9.domain.service.external.DomainRegistrationService
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
class AsyncTest(private val domainController: DomainController) : FeatureSpec() {
    @MockkBean
    private lateinit var domainRegSvc: DomainRegistrationService

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    private val localTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
    private val extraYearTime: String = localTime.replace("^(\\d+)".toRegex(), "2023")

    override fun beforeEach(testCase: TestCase) {
        coEvery { domainRegSvc.createDomain(any(), any()) } answers {
            Domain(firstArg(), secondArg(), localTime, localTime, extraYearTime, extraYearTime)
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("create domain and request its info"){
            scenario("success") {
                val result = domainController.createDomain("pro.ru")

                result should { it ->
                    it.item should {
                        if (it != null) {
                            it.id shouldBe null
                            it.name shouldBe "pro.ru"
                        }
                    }
                    it.comment shouldBe "OK"
                }

                withContext(Dispatchers.IO) {
                    sleep(1000)
                }

                val domain = domainController.getDomain(1)

                domain should { it ->
                    it.item should {
                        if (it != null) {
                            it.id shouldBe 1
                            it.name shouldBe "pro.ru"
                            it.created shouldBe localTime
                            it.fetched shouldBe localTime
                            it.paidTill shouldBe extraYearTime
                            it.freeDate shouldBe extraYearTime
                        }
                    }
                }
            }
            scenario("fail - domain registration not finished or changes not committed to db yet") {
                val result = domainController.createDomain("cat.ru")

                result should { it ->
                    it.item should {
                        if (it != null) {
                            it.id shouldBe null
                            it.name shouldBe "cat.ru"
                        }
                    }
                    it.comment shouldBe "OK"
                }

                val domain = domainController.getDomain(2)

                domain should {
                    it.item shouldBe null
                    it.comment shouldMatch "(Domain with this ID doesn't exist)|(Cannot fetch domain info, please try later).".toRegex()
                }
            }
        }
    }
}