package fr.quinquenaire.projet12joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import fr.quinquenaire.projet12joiefull.presentation.theme.JoiefullTheme
import fr.quinquenaire.projet12joiefull.presentation.ui.CatalogItemsApp

/**
 * Activity principale — point d'entrée de l'application.
 *
 * @AndroidEntryPoint : indique à Hilt qu'il doit injecter
 * les dépendances dans cette Activity (et ses Composables enfants).
 *
 * enableEdgeToEdge() : l'app dessine sous la barre de statut
 * et la barre de navigation système (design moderne Android).
 *
 * calculateWindowSizeClass() : détecte la taille de l'écran
 * pour adapter le layout (téléphone vs tablette).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            JoiefullTheme() {
                //TODO
            }
        }
    }
}
