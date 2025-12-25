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
import kotlinx.coroutines.launch
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
                    color = MaterialTheme.colorScheme.primary
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
private fun Dot(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(if (selected) 10.dp else 8.dp)
            .clip(CircleShape)
            .background(
                if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
    )
}

/**
 * CONTENIDO DE CADA PÁGINA
 * - Centrado
 * - Subido levemente para efecto onboarding profesional
 */
@Composable
fun OnboardingPageContent(
    page: OnboardingPage
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-40).dp),
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
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
