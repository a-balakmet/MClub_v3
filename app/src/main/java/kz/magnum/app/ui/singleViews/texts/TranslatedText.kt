package kz.magnum.app.ui.singleViews.texts

import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.launch
import kz.magnum.app.ui.components.localeSwitcher.LocaleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TranslatedText(
    modifier: Modifier = Modifier,
    viewModel: LocaleViewModel = koinViewModel(),
    key: String,
    style: TextStyle,
    textAlign: TextAlign? = null,
    maxLines: Int? = null,
    isHTML: Boolean = false,
    color: Color = MaterialTheme.colorScheme.onBackground,
    suffixText: String? = null
) {

    val mutableText = remember { mutableStateOf("") }

    LaunchedEffect(viewModel.appLocale.value) {
        val translation = viewModel.getTranslation(key)
        if (translation.contains("unknown")) {
            launch {
                viewModel.database.translationDao().emitTranslation(key).collect {
                    it?.let {
                        mutableText.value =  when (viewModel.appLocale.value) {
                            "ru" -> it.ru
                            "kk" -> it.kk
                            else -> "unknown locale"
                        }
                    }
                }
            }
        } else {
            mutableText.value = translation
        }
    }

    if (isHTML) {
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(mutableText.value, HtmlCompat.FROM_HTML_MODE_COMPACT)
                it.setTextColor(color.toArgb())
            },
            modifier = modifier
        )
    } else {
        maxLines?.let {
            Text(
                text = "${mutableText.value} ${suffixText ?: ""}",
                modifier = modifier,
                style = style,
                textAlign = textAlign,
                color = color,
                overflow = TextOverflow.Ellipsis,
                maxLines = it
            )
        } ?: run {
            Text(
                text = "${mutableText.value} ${suffixText ?: ""}",
                modifier = modifier,
                style = style,
                textAlign = textAlign,
                color = color,
            )
        }
    }
}