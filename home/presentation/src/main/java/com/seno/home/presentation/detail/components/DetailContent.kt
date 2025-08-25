package com.seno.home.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.seno.core.presentation.utils.openUrl
import com.seno.home.domain.model.MovieDetail

@Composable
internal fun DetailContent(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.backdropPath?.let { "${com.seno.core.presentation.BuildConfig.BASE_IMAGE_URL}$it" })
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(MaterialTheme.shapes.large),
            loading = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
                }
            },
            error = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.BrokenImage,
                        contentDescription = "Image error",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            success = { SubcomposeAsyncImageContent() }
        )

        Text(movie.title, style = MaterialTheme.typography.headlineSmall)

        movie.tagline?.takeIf { it.isNotBlank() }?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        MetaRow(
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            runtime = movie.runtime
        )

        if (movie.genres.isNotEmpty()) {
            GenresRow(genres = movie.genres.map { it.name })
        }

        if (movie.overview.isNotBlank()) {
            Text("Overview", style = MaterialTheme.typography.titleMedium)
            Text(
                movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        movie.homepage?.takeIf { it.isNotBlank() }?.let { url ->
            OutlinedButton(
                onClick = {
                   context.openUrl(url)
                }
            ) {
                Text("Visit Homepage")
            }
        }
    }
}