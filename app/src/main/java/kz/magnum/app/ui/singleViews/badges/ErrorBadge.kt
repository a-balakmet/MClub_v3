package kz.magnum.app.ui.singleViews.badges

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aab.lib.commons.domain.models.Resource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kz.magnum.app.R
import kz.magnum.app.ui.singleViews.texts.TranslatedText
import kz.magnum.app.ui.theme.Typography

@Composable
fun ErrorBadge(error: Resource.Error) {

    val context = LocalContext.current
    val onion = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.onion}")
            .size(Size.ORIGINAL)
            .build()
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = onion,
            contentDescription = "error image",
            modifier = Modifier.fillMaxWidth().height(256.dp)
        )
        if (error.code == "translatable"){
            TranslatedText(
                key = "no_internet_connection",
                style = Typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp),
                color = Color(0xFFFF8200)
            )
        } else {
            Text(
                text = error.message,
                modifier = Modifier.padding(top = 16.dp),
                style = Typography.titleLarge,
                color = Color(0xFFFF8200)
            )
        }
    }


}