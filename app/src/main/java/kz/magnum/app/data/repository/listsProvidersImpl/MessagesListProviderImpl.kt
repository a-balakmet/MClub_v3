package kz.magnum.app.data.repository.listsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.dto.MessageDto
import kz.magnum.app.data.remote.dto.toMessage
import kz.magnum.app.data.repository.ListProviderImpl
import kz.magnum.app.domain.models.Message

class MessagesListProviderImpl(getListApi: GetListApi<MessageDto>): ListProviderImpl<MessageDto, Message>(getListApi) {
    override val mapper = MessageDto::toMessage
}