package kz.magnum.app.ui.singleViews.badges

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Typography

@Composable
fun NoDataBadge(titleText: String, messageText: String?) {

    val context = LocalContext.current
    val donut = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.donut}")
            .size(Size.ORIGINAL)
            .build()
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 32.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = donut,
            contentDescription = "no data image",
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Fit
        )
        TranslatedText(key = titleText, style = Typography.displayMedium, textAlign = TextAlign.Center, modifier = Modifier)
        messageText?.let {
            TranslatedText(key = it, style = Typography.bodyLarge, textAlign = TextAlign.Center, modifier = Modifier)
        }
    }
}