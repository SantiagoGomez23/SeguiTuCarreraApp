package com.example.seguitucarreraapp.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween

import onboardingPages

private val BottomButtonWidth = 72.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // ───── TOP BAR (solo Omitir) ─────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (pagerState.currentPage < onboardingPages.lastIndex) {
                Text(
                    text = "Omitir",
                    modifier = Modifier.clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(onboardingPages.lastIndex)
                        }
                    },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // ───── PAGER ─────
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            OnboardingPageContent(
                page = onboardingPages[page]
            )
        }

        // ───── CONTROLES INFERIORES (layout fijo) ─────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IZQUIERDA — ATRÁS (ancho fijo)
            Box(
                modifier = Modifier.width(BottomButtonWidth),
                contentAlignment = Alignment.CenterStart
            ) {
                if (pagerState.currentPage > 0) {
                    Text(
                        text = "Atrás",
                        modifier = Modifier.clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        },
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // CENTRO — DOTS (siempre centrados)
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                onboardingPages.forEachIndexed { index, _ ->
                    Dot(selected = pagerState.currentPage == index)
                    if (index != onboardingPages.lastIndex) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // DERECHA — SIGUIENTE / EMPEZAR (ancho fijo)
            Box(
                modifier = Modifier.width(BottomButtonWidth),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text =
                        if (pagerState.currentPage == onboardingPages.lastIndex)
                            "Empezar"
                        else
                            "Siguiente",
                    modifier = Modifier.clickable {
                        scope.launch {
                            if (pagerState.currentPage < onboardingPages.lastIndex) {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            } else {
                                onFinish()
                            }
                        }
                    },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun Dot(
    selected: Boolean
) {
    val size by animateDpAsState(
        targetValue = if (selected) 10.dp else 8.dp,
        animationSpec = tween(durationMillis = 180),
        label = "dotSize"
    )

    val color by animateColorAsState(
        targetValue =
            if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        animationSpec = tween(durationMillis = 180),
        label = "dotColor"
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}


@Composable
fun OnboardingPageContent(
    page: OnboardingPage
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 250)
        ) + slideInVertically(
            initialOffsetY = { 40 }, // entra desde abajo
            animationSpec = tween(durationMillis = 250)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 150)
        ) + slideOutVertically(
            targetOffsetY = { -20 },
            animationSpec = tween(durationMillis = 150)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (page.showLogo)
                        Color(0xFFF5F9FF) // Bienvenida
                    else
                        Color(0xFFF3F4F6) // Resto
                )
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp), // altura tipo onboarding pro
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (page.showLogo) {
                    Image(
                        painter = painterResource(
                            id = com.example.seguitucarreraapp.R.drawable.ic_logo_sin_texto
                        ),
                        contentDescription = "Logo SeguiTuCarrera",
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 24.dp)
                    )
                }

                page.imageRes?.let { image ->
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier
                            .height(220.dp)
                            .padding(bottom = 24.dp)
                    )
                }

                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color(0xFF111827),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 20.sp
                    ),
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

