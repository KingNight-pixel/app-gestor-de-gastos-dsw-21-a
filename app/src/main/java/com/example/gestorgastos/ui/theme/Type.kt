package com.example.gestorgastos.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.gestorgastos.R

// ===== Fuente personalizada: Poppins (Google Fonts descargable) =====
// La fuente se descarga una sola vez mediante Google Play Services y
// queda en cache. Si no hay conexion, la app usa la fuente del sistema.
private val proveedorFuentes = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val fuentePoppins = GoogleFont("Poppins")

val PoppinsFamily = androidx.compose.ui.text.font.FontFamily(
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.Light),
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.Normal),
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.Medium),
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.SemiBold),
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.Bold),
    Font(googleFont = fuentePoppins, fontProvider = proveedorFuentes, weight = FontWeight.ExtraBold)
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.ExtraBold, fontSize = 57.sp),
    headlineLarge = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    headlineMedium = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp),
    titleLarge = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = PoppinsFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp)
)
