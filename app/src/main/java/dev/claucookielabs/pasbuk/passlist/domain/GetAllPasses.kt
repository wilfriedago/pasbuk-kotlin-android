package dev.claucookielabs.pasbuk.passlist.domain

import dev.claucookielabs.pasbuk.common.data.repository.PassesRepository
import dev.claucookielabs.pasbuk.common.domain.BaseRequest
import dev.claucookielabs.pasbuk.common.domain.UseCase
import dev.claucookielabs.pasbuk.common.domain.model.Passbook

class GetAllPasses(private val passesRepository: PassesRepository) :
    UseCase<BaseRequest, List<Passbook>> {

    override suspend fun execute(request: BaseRequest): List<Passbook> {
        return passesRepository.mockPasses()
    }

}
