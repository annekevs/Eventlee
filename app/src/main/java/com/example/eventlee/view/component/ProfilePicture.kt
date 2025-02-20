package com.example.eventlee.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.eventlee.R

fun Modifier.applyIf(
    condition: Boolean,
    modifierFunction: Modifier.() -> Modifier,
) = this.run {
    if (condition) {
        this.modifierFunction()
    } else {
        this
    }
}

fun Modifier.circleMask(
    marginPx: Float,
    negativeSpacingPx: Float,
) = then(
    Modifier
        .graphicsLayer {
            // Ensure BlendMode.Clear strategy works
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithCache {
            val path = Path().apply {
                addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height),
                    ),
                )
            }
            onDrawWithContent {
                clipPath(path) {
                    // this draws the actual image
                    // if you don't call drawContent, it won't render anything
                    this@onDrawWithContent.drawContent()
                }
                val marginRadius = size.width / 2f + marginPx
                val offset = size.width * 1.5f - negativeSpacingPx
                drawCircle(
                    color = Color.Black,
                    radius = marginRadius,
                    center = Offset(x = offset, y = (size.height / 2f)),
                    blendMode = BlendMode.Clear,
                )
            }
        },
)

val defaultSpacing = 4.dp
private val defaultMargin = 2.dp
private val defaultImageSize = 32.dp

@Composable
fun ProfilePicture(
    user: String? = "",
    showMore: Boolean = false,
    //url: String,
    imageSize: Dp = defaultImageSize,
    margin: Dp = defaultMargin,
    negativeSpacing: Dp = defaultSpacing,
    cropped: Boolean = false,
) {
    val initialModifier = Modifier
        .clip(CircleShape)
        .size(imageSize)
        .aspectRatio(1f)

    val marginPx = LocalDensity.current.run { margin.toPx() }
    val negativeSpacingPx = LocalDensity.current.run { negativeSpacing.toPx() }

    if (showMore) {
        Image(
            modifier = initialModifier.applyIf(cropped) {
                circleMask(marginPx, negativeSpacingPx)
            },
            imageVector = Icons.Filled.AddCircle,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    } else {
        Image(
            modifier = initialModifier.applyIf(cropped) {
                circleMask(marginPx, negativeSpacingPx)
            },
            painter = painterResource(id = R.drawable.user_profile1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }



    /*
    AsyncImage(
       modifier = initialModifier.applyIf(cropped) {
          circleMask(marginPx, negativeSpacingPx)
       },
       model = ImageRequest.Builder(LocalContext.current)
          .data(url)
          .crossfade(true)
          .build(),
      contentDescription = null,
      contentScale = ContentScale.Crop,
   )
    * */
}