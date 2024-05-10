package com.example.b10933024_hw2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.b10933024_hw2.ui.theme.B10933024_hw2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            B10933024_hw2Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "sightseeing_list") {
                    composable("sightseeing_list") {
                        Surface() {
                            SightseeingList(navController)
                        }
                    }
                    composable("sight_detail/{sightName}") { backStackEntry ->
                        Surface() {
                            val sightName = backStackEntry.arguments?.getString("sightName")
                            sightName?.let {
                                val description = getDescriptionForSight(it) // 获取景点的详细介绍
                                SightDetail(it, description) {
                                    navController.navigateUp()
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SightseeingList(navController: NavHostController) {
    val sights = listOf(
        "Eiffel Tower" to "Iconic landmark in Paris, France.",
        "Statue of Liberty" to "Symbol of freedom and democracy in New York City, USA.",
        "Taj Mahal" to "Mausoleum in Agra, India, built by the Mughal emperor Shah Jahan.",
        "Great Wall of China" to "Series of fortifications made of stone, brick, tamped earth, wood, and other materials.",
        "Machu Picchu" to "Inca citadel situated in the Andes Mountains in Peru.",
        "Pyramids of Giza" to "Ancient pyramid complex located in Egypt.",
        "Colosseum" to "Iconic amphitheater in Rome, Italy.",
        "Santorini" to "Greek island known for its stunning views, sunsets, and white-washed buildings.",
        "Petra" to "Historical and archaeological city in southern Jordan."
    )
    LazyColumn {
        items(sights) { (name, description) ->
            SightListItem(name = name, description = description) {
                navController.navigate("sight_detail/$name")
            }
        }
    }
}


@Composable
fun SightListItem(name: String, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Composable
fun SightDetail(sightName: String, description: String, navigateUp: () -> Unit) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "回上一頁"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            val imageResId = when (sightName) {
                "Eiffel Tower" -> R.drawable.image1
                "Statue of Liberty" -> R.drawable.image2
                "Taj Mahal" -> R.drawable.image3
                "Great Wall of China" -> R.drawable.image4
                "Machu Picchu" -> R.drawable.image5
                "Pyramids of Giza" -> R.drawable.image6
                "Colosseum" -> R.drawable.image7
                "Santorini" -> R.drawable.image8
                "Petra" -> R.drawable.image9
                else -> R.drawable.image1
            }
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Sight Image",
                modifier = Modifier.size(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val intent = Intent(context, MapsActivity::class.java).apply {
                    putExtra("latitude", getLatitudeForSight(sightName))
                    putExtra("longitude", getLongitudeForSight(sightName))
                }
                context.startActivity(intent)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "查看位置")
        }

    }
}


private fun getDescriptionForSight(sightName: String): String {
    return when (sightName) {
        "Eiffel Tower" -> "The Eiffel Tower, located in Paris, France, is one of the most recognizable structures in the world. Completed in 1889, it was initially met with skepticism but has since become a symbol of Paris and France itself. Designed by Gustave Eiffel, the tower stands at a height of 330 meters (1,083 feet) and was the tallest man-made structure in the world until the completion of the Chrysler Building in New York City in 1930. Visitors can ascend the tower via elevators or stairs to enjoy breathtaking views of Paris from its observation decks."
        "Statue of Liberty" -> "A gift from France to the United States, the Statue of Liberty stands proudly on Liberty Island in New York Harbor. Designed by Frédéric Auguste Bartholdi and dedicated in 1886, it has come to symbolize freedom and democracy. The statue is a towering figure, standing at over 93 meters (305 feet) from the base to the tip of the torch. Visitors can take ferries to the island and explore the statue's pedestal and museum, learning about its history and significance."
        "Taj Mahal" -> "Located in Agra, India, the Taj Mahal is a masterpiece of Mughal architecture and one of the most stunning examples of love ever created. Commissioned by Emperor Shah Jahan in memory of his wife Mumtaz Mahal, the Taj Mahal was completed in 1653. It is renowned for its white marble dome and intricate inlay work, which includes semi-precious stones. Visitors to the Taj Mahal can admire its beauty from various angles, stroll through its gardens, and explore the surrounding complex, which includes a mosque and guest house."
        "Great Wall of China" -> "The Great Wall of China is a series of fortifications made of stone, brick, tamped earth, wood, and other materials, built along the northern borders of China to protect against invasions. Construction began as early as the 7th century BC, with major renovations and extensions occurring during the Ming Dynasty (1368–1644). Stretching over 21,196 kilometers (13,170 miles), the wall is one of the most impressive architectural feats in history. Visitors can explore various sections of the wall, some restored and easily accessible, while others offer more rugged terrain and stunning views."
        "Machu Picchu" -> "Nestled high in the Andes Mountains of Peru, Machu Picchu is an ancient Incan citadel dating back to the 15th century. Built atop a ridge above the Urubamba River valley, it is renowned for its sophisticated dry-stone walls and panoramic views. Believed to have been constructed as a royal estate or sacred religious site, Machu Picchu was abandoned during the Spanish Conquest and remained hidden from the outside world until its rediscovery by American historian Hiram Bingham in 1911. Today, visitors can trek the Inca Trail or take a train to Aguas Calientes before ascending to Machu Picchu to explore its terraces, temples, and ceremonial plazas."
        "Pyramids of Giza" -> "The Pyramids of Giza, located on the outskirts of Cairo, Egypt, are perhaps the most iconic ancient monuments in the world. Built as tombs for the pharaohs Khufu, Khafre, and Menkaure over 4,500 years ago, these massive structures are a testament to the ingenuity and engineering prowess of the ancient Egyptians. The largest and most famous of the three pyramids is the Great Pyramid of Khufu, which is believed to have been constructed over a period of 20 years using tens of thousands of workers. Visitors can explore the pyramids, marvel at their grandeur, and learn about ancient Egyptian civilization at the nearby Sphinx and the Giza Plateau."
        "Colosseum" -> "The Colosseum, also known as the Flavian Amphitheatre, is an ancient Roman stadium located in the heart of Rome, Italy. Completed in AD 80, it was used for gladiatorial contests, public spectacles, and other events during the Roman Empire. With a seating capacity of up to 80,000 spectators, the Colosseum was an architectural marvel of its time, featuring a complex system of underground tunnels and chambers. Today, visitors can explore the Colosseum's ruins, imagining the roar of the crowds and the clash of swords that once filled its arena."
        "Santorini" -> "Santorini is a breathtaking Greek island located in the southern Aegean Sea, known for its stunning sunsets, white-washed buildings, and crystal-clear waters. Formed by a volcanic eruption in the 16th century BC, Santorini is famous for its dramatic cliffs and picturesque villages, such as Oia and Fira. Visitors can explore ancient ruins, relax on black sand beaches, and savor delicious Greek cuisine while taking in the island's enchanting beauty."
        "Petra" -> "Petra is an ancient city carved into the rose-red cliffs of southern Jordan, dating back to the 6th century BC. Once the capital of the Nabatean Kingdom, Petra flourished as a center of trade and culture, strategically located along important caravan routes. Its most famous structure is Al-Khazneh (the Treasury), a magnificent temple carved into the rock face. Visitors to Petra can wander through its narrow canyon (known as the Siq), marvel at its rock-cut tombs and temples, and discover the rich history and architectural wonders of this UNESCO World Heritage Site."
        else -> ""
    }
}


private fun getLatitudeForSight(sightName: String): Double {
    return when (sightName) {
        "Eiffel Tower" -> 48.8584
        "Statue of Liberty" -> 40.6892
        "Taj Mahal" -> 27.1751
        "Great Wall of China" -> 40.4319
        "Machu Picchu" -> -13.1631
        "Pyramids of Giza" -> 29.9792
        "Colosseum" -> 41.8902
        "Santorini" -> 36.3932
        "Petra" -> 30.3285
        else -> 0.0
    }
}

private fun getLongitudeForSight(sightName: String): Double {
    return when (sightName) {
        "Eiffel Tower" -> 2.2945
        "Statue of Liberty" -> -74.0445
        "Taj Mahal" -> 78.0421
        "Great Wall of China" -> 116.5704
        "Machu Picchu" -> -72.5450
        "Pyramids of Giza" -> 31.1342
        "Colosseum" -> 12.4922
        "Santorini" -> 25.4615
        "Petra" -> 35.4444
        else -> 0.0
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    B10933024_hw2Theme {
        SightseeingList(rememberNavController())
    }
}

