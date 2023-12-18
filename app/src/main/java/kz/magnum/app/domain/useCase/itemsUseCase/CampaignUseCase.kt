package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class CampaignUseCase(repository: ItemProvider<CampaignDataDto>): ItemUseCase<CampaignDataDto>(repository)