package com.example.myapplication.ui.screens

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.Location
import com.example.myapplication.ui.viewmodel.ServiceViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

// Indian cities data with real coordinates
data class IndianCity(
    val name: String,
    val state: String,
    val latitude: Double,
    val longitude: Double
)

val indianCities = listOf(
    // Rajasthan
    IndianCity("Jaipur", "Rajasthan", 26.9124, 75.7873),
    IndianCity("Kota", "Rajasthan", 25.2138, 75.8648),
    IndianCity("Ajmer", "Rajasthan", 26.4499, 74.6399),
    IndianCity("Bundi", "Rajasthan", 25.4305, 75.6499),
    IndianCity("Udaipur", "Rajasthan", 24.5854, 73.7125),
    IndianCity("Jodhpur", "Rajasthan", 26.2389, 73.0243),
    IndianCity("Bikaner", "Rajasthan", 28.0229, 73.3119),
    IndianCity("Alwar", "Rajasthan", 27.5530, 76.6346),
    IndianCity("Bharatpur", "Rajasthan", 27.2172, 77.4900),
    IndianCity("Sikar", "Rajasthan", 27.6119, 75.1397),
    
    // Delhi NCR
    IndianCity("New Delhi", "Delhi", 28.6139, 77.2090),
    IndianCity("Noida", "Uttar Pradesh", 28.5355, 77.3910),
    IndianCity("Gurgaon", "Haryana", 28.4595, 77.0266),
    IndianCity("Faridabad", "Haryana", 28.4089, 77.3178),
    IndianCity("Ghaziabad", "Uttar Pradesh", 28.6692, 77.4538),
    
    // Maharashtra
    IndianCity("Mumbai", "Maharashtra", 19.0760, 72.8777),
    IndianCity("Pune", "Maharashtra", 18.5204, 73.8567),
    IndianCity("Nagpur", "Maharashtra", 21.1458, 79.0882),
    IndianCity("Nashik", "Maharashtra", 19.9975, 73.7898),
    IndianCity("Aurangabad", "Maharashtra", 19.8762, 75.3433),
    
    // Karnataka
    IndianCity("Bangalore", "Karnataka", 12.9716, 77.5946),
    IndianCity("Mysore", "Karnataka", 12.2958, 76.6394),
    IndianCity("Mangalore", "Karnataka", 12.9141, 74.8560),
    IndianCity("Hubli", "Karnataka", 15.3647, 75.1240),
    
    // Tamil Nadu
    IndianCity("Chennai", "Tamil Nadu", 13.0827, 80.2707),
    IndianCity("Coimbatore", "Tamil Nadu", 11.0168, 76.9558),
    IndianCity("Madurai", "Tamil Nadu", 9.9252, 78.1198),
    IndianCity("Trichy", "Tamil Nadu", 10.7905, 78.7047),
    
    // West Bengal
    IndianCity("Kolkata", "West Bengal", 22.5726, 88.3639),
    IndianCity("Howrah", "West Bengal", 22.5958, 88.2636),
    IndianCity("Siliguri", "West Bengal", 26.7271, 88.3953),
    
    // Gujarat
    IndianCity("Ahmedabad", "Gujarat", 23.0225, 72.5714),
    IndianCity("Surat", "Gujarat", 21.1702, 72.8311),
    IndianCity("Vadodara", "Gujarat", 22.3072, 73.1812),
    IndianCity("Rajkot", "Gujarat", 22.3039, 70.8022),
    
    // Telangana & Andhra Pradesh
    IndianCity("Hyderabad", "Telangana", 17.3850, 78.4867),
    IndianCity("Visakhapatnam", "Andhra Pradesh", 17.6868, 83.2185),
    IndianCity("Vijayawada", "Andhra Pradesh", 16.5062, 80.6480),
    
    // Kerala
    IndianCity("Kochi", "Kerala", 9.9312, 76.2673),
    IndianCity("Thiruvananthapuram", "Kerala", 8.5241, 76.9366),
    IndianCity("Kozhikode", "Kerala", 11.2588, 75.7804),
    
    // Uttar Pradesh
    IndianCity("Lucknow", "Uttar Pradesh", 26.8467, 80.9462),
    IndianCity("Kanpur", "Uttar Pradesh", 26.4499, 80.3319),
    IndianCity("Agra", "Uttar Pradesh", 27.1767, 78.0081),
    IndianCity("Varanasi", "Uttar Pradesh", 25.3176, 82.9739),
    IndianCity("Meerut", "Uttar Pradesh", 28.9845, 77.7064),
    
    // Madhya Pradesh
    IndianCity("Indore", "Madhya Pradesh", 22.7196, 75.8577),
    IndianCity("Bhopal", "Madhya Pradesh", 23.2599, 77.4126),
    IndianCity("Gwalior", "Madhya Pradesh", 26.2183, 78.1828),
    IndianCity("Jabalpur", "Madhya Pradesh", 23.1815, 79.9864),
    
    // Punjab & Haryana
    IndianCity("Chandigarh", "Chandigarh", 30.7333, 76.7794),
    IndianCity("Ludhiana", "Punjab", 30.9010, 75.8573),
    IndianCity("Amritsar", "Punjab", 31.6340, 74.8723),
    IndianCity("Jalandhar", "Punjab", 31.3260, 75.5762),
    
    // Bihar & Jharkhand
    IndianCity("Patna", "Bihar", 25.5941, 85.1376),
    IndianCity("Ranchi", "Jharkhand", 23.3441, 85.3096),
    IndianCity("Jamshedpur", "Jharkhand", 22.8046, 86.2029),
    
    // Odisha
    IndianCity("Bhubaneswar", "Odisha", 20.2961, 85.8245),
    IndianCity("Cuttack", "Odisha", 20.5124, 85.8829),
    
    // Assam & Northeast
    IndianCity("Guwahati", "Assam", 26.1445, 91.7362),
    IndianCity("Imphal", "Manipur", 24.8170, 93.9368),
    
    // Uttarakhand
    IndianCity("Dehradun", "Uttarakhand", 30.3165, 78.0322),
    IndianCity("Haridwar", "Uttarakhand", 29.9457, 78.1642)
)

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: ServiceViewModel,
    onLocationSet: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showCityList by remember { mutableStateOf(false) }
    var showManualEntry by remember { mutableStateOf(false) }
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    
    val filteredCities = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            indianCities
        } else {
            indianCities.filter { city ->
                city.name.contains(searchQuery, ignoreCase = true) ||
                city.state.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Set Your Location") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!showCityList && !showManualEntry) {
                // Initial screen
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "We need your location",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "To find the best service providers near you in India",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Select Indian City button
                    Button(
                        onClick = { showCityList = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Select Indian City")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // GPS button
                    OutlinedButton(
                        onClick = {
                            if (locationPermission.status.isGranted) {
                                // Get GPS location - for demo, using Delhi
                                val mockLocation = Location(
                                    latitude = 28.6139,
                                    longitude = 77.2090,
                                    address = "New Delhi, Delhi, India"
                                )
                                viewModel.setLocation(mockLocation)
                                onLocationSet()
                            } else {
                                locationPermission.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.GpsFixed, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Use Current Location")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { showManualEntry = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enter Address Manually")
                    }
                }
                
                // Privacy note
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "🔒 Your Privacy Matters",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Your location data is processed locally using RunAnywhere SDK " +
                                    "for privacy-preserving AI recommendations. We never share your " +
                                    "exact location with third parties.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else if (showCityList) {
                // City selection screen
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Select Your City",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Search field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search city or state") },
                        placeholder = { Text("e.g., Jaipur, Rajasthan") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Cities list
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredCities) { city ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val location = Location(
                                            latitude = city.latitude,
                                            longitude = city.longitude,
                                            address = "${city.name}, ${city.state}, India"
                                        )
                                        viewModel.setLocation(location)
                                        onLocationSet()
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = city.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = city.state,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    TextButton(
                        onClick = { showCityList = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back")
                    }
                }
            } else {
                // Manual entry screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var address by remember { mutableStateOf("") }
                    
                    Text(
                        text = "Enter Your Address",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Enter your address") },
                        placeholder = { Text("City, State, India") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (address.isNotBlank()) {
                                // Use default coordinates for manual entry
                                val location = Location(
                                    latitude = 28.6139,
                                    longitude = 77.2090,
                                    address = address
                                )
                                viewModel.setLocation(location)
                                onLocationSet()
                            }
                        },
                        enabled = address.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Continue")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { showManualEntry = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}
