package kz.magnum.app.ui.screens.loginBlock.createProfile

import aab.lib.commons.domain.models.WindowInfo
import aab.lib.commons.ui.composableAdds.rememberWindowInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.magnum.app.domain.models.profile.ProfileEditUIEvent
import kz.magnum.app.domain.models.profile.ProfileItemType
import kz.magnum.app.ui.builders.parentViews.LoginPaneView
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.singleViews.profileViews.BirthdaySelector
import kz.magnum.app.ui.singleViews.profileViews.GenderSelector
import kz.magnum.app.ui.singleViews.profileViews.ProfileEditableField
import kz.magnum.app.ui.singleViews.buttons.EnablingButton
import kz.magnum.app.ui.singleViews.texts.ErrorText
import kz.magnum.app.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileCreationScreen(
    viewModel: ProfileCreationViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {

//    val isTablet = remember { mutableStateOf(windowSize.widthSizeClass != WindowWidthSizeClass.Compact) }
//    val configuration = LocalConfiguration.current
    val windowInfo = rememberWindowInfo()
    val isTablet = remember { mutableStateOf(windowInfo.screenWidthInfo != WindowInfo.WindowType.Compact) }
//    val screenWidth = configuration.screenWidthDp.dp
    val contentWidth = if (isTablet.value) windowInfo.screenWidth / 2 else windowInfo.screenWidth - 32.dp

    val profile by viewModel.profileFlow.collectAsStateWithLifecycle()

    LoginPaneView(
        showBackArrow = false,
        titleText = "fill_profile",
        scrollableContent = true,
        onBackPressed = { onBackPressed() },
    ) {
        Column {
            TranslatedText(
                key = "fill_profile_descr",
                style = Typography.titleMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            viewModel.texts.forEach {
                if (it.type == ProfileItemType.STRING_TYPE) {
                    ProfileEditableField(
                        title = it.title,
                        error = it.error,
                        hint = it.hint,
                        isEmail = it.parentKey == "mail",
                        onValueInput = { value ->
                            val isName = when (it.parentKey) {
                                "name" -> true
                                "surname" -> false
                                else -> null
                            }
                            viewModel.onEvent(ProfileEditUIEvent.NamesSetter(value = value, type = ProfileItemType.STRING_TYPE, isName = isName))
                        })
                }
                if (it.type == ProfileItemType.BIRTHDAY_TYPE) {
                    BirthdaySelector(
                        title = it.title,
                        hint = it.hint,
                        screenWidth = contentWidth,
                        locale = viewModel.locale,
                        onDateSelected = { value ->
                            viewModel.onEvent(ProfileEditUIEvent.NamesSetter(value = value, type = ProfileItemType.BIRTHDAY_TYPE, isName = null))
                        })
                }
                if (it.type == ProfileItemType.SEX_TYPE) {
                    GenderSelector(
                        selectedGender = profile.gender,
                        onSelection = { value ->
                            viewModel.onEvent(ProfileEditUIEvent.NamesSetter(value = value, type = ProfileItemType.SEX_TYPE, isName = null))
                        })
                }
            }
            viewModel.exchangeState.value.error?.let {
                ErrorText(error = it)
            }
            Box(modifier = Modifier.height(64.dp))
            EnablingButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isEnabled = profile.firstName != "" && profile.lastName != "" && profile.email != "" && profile.birthday != "",
                isLoading = viewModel.exchangeState.value.isLoading,
                buttonText = "save"
            ) {
                viewModel.onEvent(ProfileEditUIEvent.CompletedProfile)
            }
            Box(modifier = Modifier.height(16.dp))
        }
    }
}
