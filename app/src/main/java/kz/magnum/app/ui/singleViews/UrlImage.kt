package kz.magnum.app.ui.singleViews

import aab.lib.commons.ui.animations.ShimmerBox
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import kz.magnum.app.R

@Composable
fun UrlImage(
    link: String,
    modifier: Modifier,
    imageHeight: Dp = 128.dp,
    contentScale: ContentScale = ContentScale.Fit
) {

    val context = LocalContext.current
    val onion = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${context.applicationContext.packageName}/${R.raw.onion}")
            .size(Size.ORIGINAL)
            .build()
    )

    SubcomposeAsyncImage(
        model = link,
        contentDescription = null,
        modifier = modifier,
        loading = { ShimmerBox(modifier = modifier.size(imageHeight)) },
        error = {
            Box(modifier = modifier.fillMaxWidth()) {
                Row (modifier = Modifier.fillMaxWidth()){
                    Spacer(modifier = Modifier.weight(1F))
                    Image(
                        painter = onion,
                        contentDescription = "error image",
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().height(imageHeight)
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }

            }
        },
        contentScale = contentScale,
    )
}