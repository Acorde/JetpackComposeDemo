package com.igor.jetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker
import java.util.regex.Matcher
import java.util.regex.Pattern

enum class YouTubePlayerOrientation {
    LAND_SCAPE,
    PORTRAIT
}

@Composable
fun AppYouTubePlayer(
    modifier: Modifier = Modifier,
    videoUrl: String?,
    isExtractYouTubeId:Boolean = true,
    currentSecond: Float = 0f,
    isFullScreen: Boolean = false,
    playFromOutside: Boolean = false,
    onFullScreenButtonClick: (YouTubePlayerOrientation, Float, String) -> Unit,
    onFailedToExtractYouTubeId: () -> Unit = {}
) {

    val videoId = if(isExtractYouTubeId) extractYouTubeId(videoUrl) else videoUrl
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        videoId?.let {
            AndroidView(
                modifier = modifier,
                factory = {
                    YouTubePlayerView(it)
                }) { mVideoPlayerView ->

                val tracker = YouTubePlayerTracker()

                mVideoPlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                    override fun onYouTubePlayerEnterFullScreen() {
                        if (isFullScreen.not()) {
                            onFullScreenButtonClick(
                                YouTubePlayerOrientation.LAND_SCAPE,
                                tracker.currentSecond,
                                videoUrl!!
                            )
                        }
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        onFullScreenButtonClick(
                            YouTubePlayerOrientation.PORTRAIT,
                            tracker.currentSecond,
                            videoUrl!!
                        )
                    }
                })

                try {
                    mVideoPlayerView.initialize({ youTubePlayer: YouTubePlayer ->
                        youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady() {
                                youTubePlayer.addListener(tracker)
                                if (currentSecond != 0f || playFromOutside) {
                                    youTubePlayer.loadVideo(videoId, currentSecond)
                                } else {
                                    youTubePlayer.cueVideo(videoId, currentSecond)
                                }
                            }

                        })
                    }, true)
                } catch (e: Exception) {
                }

                if (isFullScreen) {
                    mVideoPlayerView.enterFullScreen()
                }
            }
        } ?: run {
            onFailedToExtractYouTubeId()
        }
    }
}

/**
 * Retrieving VIDEO_ID from URL
 *
 * @param ytUrl
 * @return youtube VIDEO_ID
 */
fun extractYouTubeId(ytUrl: String?): String? {
    if (ytUrl == null) {
        return null
    }
    var vId: String? = null
    val pattern = Pattern.compile(
        "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*\$",
        Pattern.CASE_INSENSITIVE
    )
    val matcher = pattern.matcher(ytUrl)
    if (matcher.matches()) {
        vId = matcher.group(1)
    }
    return vId
}

private fun extractVideoId(ytUrl: String?): String? {
    var videoId: String? = null
    val regex =
        "^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be|youtube-nocookie.com))(/(?:[\\w\\-]+\\?v=|feature=|watch\\?|e/|embed/|v/)?)([\\w\\-]+)(\\S+)?\$"
    val pattern: Pattern = Pattern.compile(
        regex ,
        Pattern.CASE_INSENSITIVE
    )
    val matcher: Matcher = pattern.matcher(ytUrl)
    if (matcher.matches()) {
        videoId = matcher.group(5)
    }
    return videoId
}