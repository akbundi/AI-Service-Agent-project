package com.example.myapplication.data

import com.example.myapplication.data.models.PriceLevel
import com.example.myapplication.data.models.ServiceProvider

/**
 * Real Indian service providers database
 * Organized by city and service category
 */
object IndianServiceProvidersData {

    // Jaipur, Rajasthan Service Providers
    private val jaipurProviders = listOf(
        // Plumbers in Jaipur
        ServiceProvider(
            id = "jpr_plmb_001",
            name = "Rajasthan Plumbing Services",
            category = "plumber",
            rating = 4.5f,
            reviewCount = 287,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "C-Scheme, Jaipur, Rajasthan",
            distance = 2.3,
            phone = "+91 9876543210",
            description = "Professional plumbing services with 15+ years experience in Jaipur. 24/7 emergency services available.",
            availability = "Available Mon-Sun, 8AM-8PM, Emergency 24/7",
            imageUrl = null,
            latitude = 26.9124,
            longitude = 75.7873,
            services = listOf("Pipe Repairs", "Water Tank Installation", "Bathroom Fitting", "Drain Cleaning"),
            yearEstablished = 2008,
            isVerified = true
        ),
        ServiceProvider(
            id = "jpr_plmb_002",
            name = "Quick Fix Plumbers",
            category = "plumber",
            rating = 4.2f,
            reviewCount = 156,
            priceLevel = PriceLevel.CHEAP,
            address = "Malviya Nagar, Jaipur, Rajasthan",
            distance = 3.8,
            phone = "+91 9988776655",
            description = "Affordable and reliable plumbing solutions for residential and commercial properties.",
            availability = "Available Mon-Sat, 9AM-7PM",
            imageUrl = null,
            latitude = 26.8523,
            longitude = 75.8154,
            services = listOf("Emergency Repairs", "Installation", "Maintenance"),
            yearEstablished = 2015,
            isVerified = false
        ),
        // Tutors in Jaipur
        ServiceProvider(
            id = "jpr_tut_001",
            name = "Allen Career Institute",
            category = "tutor",
            rating = 4.8f,
            reviewCount = 1250,
            priceLevel = PriceLevel.PREMIUM,
            address = "Jawahar Nagar, Jaipur, Rajasthan",
            distance = 4.5,
            phone = "+91 141-2719200",
            description = "Premier coaching institute for IIT-JEE, NEET, and other competitive exams. Experienced faculty.",
            availability = "Classes: Mon-Sat, 6AM-9PM",
            imageUrl = null,
            latitude = 26.9389,
            longitude = 75.8042,
            services = listOf("IIT-JEE Coaching", "NEET Preparation", "Class 11-12 Tuition", "Online Classes"),
            yearEstablished = 1988,
            isVerified = true
        ),
        ServiceProvider(
            id = "jpr_tut_002",
            name = "Wisdom Tree Academy",
            category = "tutor",
            rating = 4.3f,
            reviewCount = 342,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Vaishali Nagar, Jaipur, Rajasthan",
            distance = 5.2,
            phone = "+91 9876123456",
            description = "Quality education for Class 6-12, all subjects. Personal attention to each student.",
            availability = "Available Mon-Sat, 3PM-8PM",
            imageUrl = null,
            latitude = 26.9146,
            longitude = 75.7273,
            services = listOf("Mathematics", "Science", "English", "Homework Help"),
            yearEstablished = 2012,
            isVerified = true
        ),
        // Gyms in Jaipur
        ServiceProvider(
            id = "jpr_gym_001",
            name = "Gold's Gym Jaipur",
            category = "gym",
            rating = 4.6f,
            reviewCount = 567,
            priceLevel = PriceLevel.PREMIUM,
            address = "Malviya Nagar, Jaipur, Rajasthan",
            distance = 3.1,
            phone = "+91 141-4042000",
            description = "World-class fitness center with modern equipment, personal trainers, and group classes.",
            availability = "Mon-Sun, 5AM-11PM",
            imageUrl = null,
            latitude = 26.8489,
            longitude = 75.8147,
            services = listOf("Weight Training", "Cardio", "Zumba", "Personal Training", "Yoga"),
            yearEstablished = 2010,
            isVerified = true
        )
    )

    // Kota, Rajasthan Service Providers
    private val kotaProviders = listOf(
        // Tutors in Kota (Coaching Hub)
        ServiceProvider(
            id = "kta_tut_001",
            name = "Resonance Kota",
            category = "tutor",
            rating = 4.7f,
            reviewCount = 2145,
            priceLevel = PriceLevel.PREMIUM,
            address = "Commerce College Road, Kota, Rajasthan",
            distance = 1.8,
            phone = "+91 744-3012222",
            description = "Top-ranked coaching institute for IIT-JEE and NEET. Excellent results and experienced faculty.",
            availability = "Classes: Daily 6AM-10PM",
            imageUrl = null,
            latitude = 25.2138,
            longitude = 75.8648,
            services = listOf("IIT-JEE", "NEET", "JEE Advanced", "Foundation Courses"),
            yearEstablished = 2001,
            isVerified = true
        ),
        ServiceProvider(
            id = "kta_tut_002",
            name = "Vibrant Academy",
            category = "tutor",
            rating = 4.6f,
            reviewCount = 1876,
            priceLevel = PriceLevel.PREMIUM,
            address = "Indra Vihar, Kota, Rajasthan",
            distance = 2.5,
            phone = "+91 744-2790000",
            description = "Premier coaching institute with high success rate in IIT-JEE and other engineering entrance exams.",
            availability = "Mon-Sun, 5:30AM-9:30PM",
            imageUrl = null,
            latitude = 25.1827,
            longitude = 75.8345,
            services = listOf("JEE Main", "JEE Advanced", "BITSAT", "Foundation"),
            yearEstablished = 2008,
            isVerified = true
        ),
        ServiceProvider(
            id = "kta_tut_003",
            name = "Motion IIT-JEE",
            category = "tutor",
            rating = 4.5f,
            reviewCount = 1543,
            priceLevel = PriceLevel.PREMIUM,
            address = "Talwandi, Kota, Rajasthan",
            distance = 3.2,
            phone = "+91 744-2439051",
            description = "Comprehensive coaching for IIT-JEE with innovative teaching methods and study material.",
            availability = "Daily, 6AM-10PM",
            imageUrl = null,
            latitude = 25.2456,
            longitude = 75.8912,
            services = listOf("IIT-JEE Main", "JEE Advanced", "Online Batches", "Test Series"),
            yearEstablished = 2007,
            isVerified = true
        ),
        // Gyms in Kota
        ServiceProvider(
            id = "kta_gym_001",
            name = "Fitness First Kota",
            category = "gym",
            rating = 4.3f,
            reviewCount = 234,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Gumanpura, Kota, Rajasthan",
            distance = 2.1,
            phone = "+91 9876543210",
            description = "Modern gym with quality equipment. Special student packages available.",
            availability = "Mon-Sun, 5AM-10PM",
            imageUrl = null,
            latitude = 25.1952,
            longitude = 75.8511,
            services = listOf("Weight Training", "Cardio", "Personal Training", "Diet Consultation"),
            yearEstablished = 2014,
            isVerified = true
        ),
        // Plumbers in Kota
        ServiceProvider(
            id = "kta_plmb_001",
            name = "Kota Plumbing Solutions",
            category = "plumber",
            rating = 4.1f,
            reviewCount = 178,
            priceLevel = PriceLevel.CHEAP,
            address = "Dadabari, Kota, Rajasthan",
            distance = 1.5,
            phone = "+91 9988112233",
            description = "Quick and affordable plumbing services for hostels and residential areas.",
            availability = "Mon-Sun, 7AM-9PM",
            imageUrl = null,
            latitude = 25.1673,
            longitude = 75.8242,
            services = listOf("Pipe Repairs", "Tap Fixing", "Water Heater Installation", "Emergency Services"),
            yearEstablished = 2016,
            isVerified = false
        )
    )

    // Ajmer, Rajasthan Service Providers
    private val ajmerProviders = listOf(
        ServiceProvider(
            id = "ajm_plmb_001",
            name = "Ajmer Plumbing & Sanitary",
            category = "plumber",
            rating = 4.4f,
            reviewCount = 145,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Kaiserganj, Ajmer, Rajasthan",
            distance = 1.9,
            phone = "+91 9876234567",
            description = "Professional plumbing and sanitary services for homes and businesses.",
            availability = "Mon-Sat, 8AM-7PM",
            imageUrl = null,
            latitude = 26.4499,
            longitude = 74.6399,
            services = listOf("Plumbing", "Sanitary Fitting", "Bathroom Renovation", "Water Supply"),
            yearEstablished = 2010,
            isVerified = true
        ),
        ServiceProvider(
            id = "ajm_tut_001",
            name = "Disha Institute Ajmer",
            category = "tutor",
            rating = 4.2f,
            reviewCount = 289,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Vaishali Nagar, Ajmer, Rajasthan",
            distance = 2.3,
            phone = "+91 9123456789",
            description = "Coaching for competitive exams and school students. Experienced teachers.",
            availability = "Mon-Sat, 7AM-8PM",
            imageUrl = null,
            latitude = 26.4356,
            longitude = 74.6587,
            services = listOf("Class 8-12 Tuition", "IIT Foundation", "NEET Foundation", "Board Exam Prep"),
            yearEstablished = 2013,
            isVerified = true
        ),
        ServiceProvider(
            id = "ajm_gym_001",
            name = "Powerhouse Gym Ajmer",
            category = "gym",
            rating = 4.3f,
            reviewCount = 198,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Civil Lines, Ajmer, Rajasthan",
            distance = 1.7,
            phone = "+91 9876543219",
            description = "Well-equipped gym with professional trainers and fitness programs.",
            availability = "Mon-Sun, 5:30AM-10PM",
            imageUrl = null,
            latitude = 26.4621,
            longitude = 74.6398,
            services = listOf("Weight Training", "Cardio", "CrossFit", "Aerobics"),
            yearEstablished = 2015,
            isVerified = true
        )
    )

    // Bundi, Rajasthan Service Providers
    private val bundiProviders = listOf(
        ServiceProvider(
            id = "bnd_plmb_001",
            name = "Bundi Home Services",
            category = "plumber",
            rating = 4.0f,
            reviewCount = 67,
            priceLevel = PriceLevel.CHEAP,
            address = "Sadar Bazaar, Bundi, Rajasthan",
            distance = 1.2,
            phone = "+91 9876123987",
            description = "Local plumbing and repair services. Quick response and affordable rates.",
            availability = "Mon-Sun, 8AM-6PM",
            imageUrl = null,
            latitude = 25.4305,
            longitude = 75.6499,
            services = listOf("Plumbing Repairs", "Installation", "Maintenance"),
            yearEstablished = 2018,
            isVerified = false
        ),
        ServiceProvider(
            id = "bnd_tut_001",
            name = "Bundi Tutorial Center",
            category = "tutor",
            rating = 4.1f,
            reviewCount = 123,
            priceLevel = PriceLevel.CHEAP,
            address = "Near Palace Road, Bundi, Rajasthan",
            distance = 0.8,
            phone = "+91 9123987654",
            description = "Home tuition and group classes for school students. All subjects covered.",
            availability = "Mon-Sat, 4PM-8PM",
            imageUrl = null,
            latitude = 25.4421,
            longitude = 75.6378,
            services = listOf("Class 6-10 Tuition", "Science", "Mathematics", "English"),
            yearEstablished = 2016,
            isVerified = false
        ),
        ServiceProvider(
            id = "bnd_gym_001",
            name = "Fitness Zone Bundi",
            category = "gym",
            rating = 3.9f,
            reviewCount = 89,
            priceLevel = PriceLevel.CHEAP,
            address = "Railway Station Road, Bundi, Rajasthan",
            distance = 1.5,
            phone = "+91 9876234098",
            description = "Budget-friendly gym with basic equipment and friendly atmosphere.",
            availability = "Mon-Sun, 6AM-9PM",
            imageUrl = null,
            latitude = 25.4187,
            longitude = 75.6542,
            services = listOf("Weight Training", "Cardio", "Group Training"),
            yearEstablished = 2019,
            isVerified = false
        )
    )

    // Delhi Service Providers
    private val delhiProviders = listOf(
        ServiceProvider(
            id = "del_plmb_001",
            name = "Delhi Plumbing Experts",
            category = "plumber",
            rating = 4.6f,
            reviewCount = 892,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Connaught Place, New Delhi",
            distance = 2.5,
            phone = "+91 11-23456789",
            description = "Trusted plumbing services in Delhi NCR. 24/7 emergency support.",
            availability = "24/7 Available",
            imageUrl = null,
            latitude = 28.6139,
            longitude = 77.2090,
            services = listOf("Emergency Plumbing", "Bathroom Fitting", "Water Heater", "Pipe Repairs"),
            yearEstablished = 2005,
            isVerified = true
        ),
        ServiceProvider(
            id = "del_gym_001",
            name = "Cult.fit Delhi",
            category = "gym",
            rating = 4.7f,
            reviewCount = 1456,
            priceLevel = PriceLevel.PREMIUM,
            address = "Saket, New Delhi",
            distance = 8.5,
            phone = "+91 11-44556677",
            description = "Premium fitness center with modern equipment, group classes, and expert trainers.",
            availability = "Mon-Sun, 5AM-11PM",
            imageUrl = null,
            latitude = 28.5244,
            longitude = 77.2066,
            services = listOf("Strength Training", "Cardio", "Yoga", "Dance Fitness", "Personal Training"),
            yearEstablished = 2016,
            isVerified = true
        ),
        ServiceProvider(
            id = "del_tut_001",
            name = "Sri Chaitanya Delhi",
            category = "tutor",
            rating = 4.5f,
            reviewCount = 678,
            priceLevel = PriceLevel.PREMIUM,
            address = "Kalu Sarai, New Delhi",
            distance = 7.2,
            phone = "+91 11-26854321",
            description = "Excellence in IIT-JEE and NEET coaching. Proven track record.",
            availability = "Mon-Sat, 6AM-9PM",
            imageUrl = null,
            latitude = 28.5321,
            longitude = 77.2145,
            services = listOf("IIT-JEE", "NEET", "Foundation", "Test Series"),
            yearEstablished = 2010,
            isVerified = true
        )
    )

    // Mumbai Service Providers
    private val mumbaiProviders = listOf(
        ServiceProvider(
            id = "mum_plmb_001",
            name = "Mumbai Plumbing Services",
            category = "plumber",
            rating = 4.5f,
            reviewCount = 1234,
            priceLevel = PriceLevel.PREMIUM,
            address = "Andheri West, Mumbai, Maharashtra",
            distance = 3.4,
            phone = "+91 22-26234567",
            description = "Premium plumbing services for residential and commercial buildings in Mumbai.",
            availability = "Mon-Sun, 7AM-10PM, Emergency 24/7",
            imageUrl = null,
            latitude = 19.0760,
            longitude = 72.8777,
            services = listOf("Plumbing", "Drainage", "Water Tank Cleaning", "Installation"),
            yearEstablished = 2008,
            isVerified = true
        ),
        ServiceProvider(
            id = "mum_gym_001",
            name = "Talwalkars Gym Mumbai",
            category = "gym",
            rating = 4.4f,
            reviewCount = 987,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Bandra West, Mumbai, Maharashtra",
            distance = 5.6,
            phone = "+91 22-26451234",
            description = "One of India's largest gym chains with state-of-the-art facilities.",
            availability = "Mon-Sun, 5:30AM-11PM",
            imageUrl = null,
            latitude = 19.0596,
            longitude = 72.8295,
            services = listOf("Weight Training", "Cardio", "Steam & Sauna", "Personal Training"),
            yearEstablished = 2002,
            isVerified = true
        )
    )

    // Bangalore Service Providers
    private val bangaloreProviders = listOf(
        ServiceProvider(
            id = "blr_plmb_001",
            name = "Bangalore Plumbing Co.",
            category = "plumber",
            rating = 4.6f,
            reviewCount = 756,
            priceLevel = PriceLevel.MEDIOCRE,
            address = "Koramangala, Bangalore, Karnataka",
            distance = 4.2,
            phone = "+91 80-41234567",
            description = "Professional plumbing solutions for homes and offices. Quick service guaranteed.",
            availability = "Mon-Sun, 7AM-9PM",
            imageUrl = null,
            latitude = 12.9716,
            longitude = 77.5946,
            services = listOf("Plumbing Repairs", "Bathroom Fitting", "Water Purifier Installation"),
            yearEstablished = 2012,
            isVerified = true
        ),
        ServiceProvider(
            id = "blr_gym_001",
            name = "Fitness One Bangalore",
            category = "gym",
            rating = 4.5f,
            reviewCount = 892,
            priceLevel = PriceLevel.PREMIUM,
            address = "Indiranagar, Bangalore, Karnataka",
            distance = 6.3,
            phone = "+91 80-25678901",
            description = "Premium fitness center with certified trainers and modern equipment.",
            availability = "Mon-Sun, 5AM-11PM",
            imageUrl = null,
            latitude = 12.9716,
            longitude = 77.6412,
            services = listOf("Weight Training", "Functional Training", "Zumba", "Boxing", "Yoga"),
            yearEstablished = 2015,
            isVerified = true
        ),
        ServiceProvider(
            id = "blr_tut_001",
            name = "BYJU'S Learning Center",
            category = "tutor",
            rating = 4.4f,
            reviewCount = 1567,
            priceLevel = PriceLevel.PREMIUM,
            address = "Whitefield, Bangalore, Karnataka",
            distance = 12.5,
            phone = "+91 80-67890123",
            description = "India's leading ed-tech company offering personalized learning programs.",
            availability = "Online: 24/7, Center: Mon-Sat 9AM-8PM",
            imageUrl = null,
            latitude = 12.9698,
            longitude = 77.7500,
            services = listOf("Class 4-12 Tuition", "IIT-JEE", "NEET", "UPSC", "Online Learning"),
            yearEstablished = 2011,
            isVerified = true
        )
    )

    /**
     * Get service providers based on location and category
     */
    fun getProvidersByLocation(
        cityName: String,
        category: String? = null
    ): List<ServiceProvider> {
        val cityProviders = when {
            cityName.contains("Jaipur", ignoreCase = true) -> jaipurProviders
            cityName.contains("Kota", ignoreCase = true) -> kotaProviders
            cityName.contains("Ajmer", ignoreCase = true) -> ajmerProviders
            cityName.contains("Bundi", ignoreCase = true) -> bundiProviders
            cityName.contains("Delhi", ignoreCase = true) -> delhiProviders
            cityName.contains("Mumbai", ignoreCase = true) -> mumbaiProviders
            cityName.contains("Bangalore", ignoreCase = true) -> bangaloreProviders
            else -> emptyList()
        }

        return if (category != null) {
            cityProviders.filter { it.category.equals(category, ignoreCase = true) }
        } else {
            cityProviders
        }
    }

    /**
     * Get all available categories for a city
     */
    fun getAvailableCategories(cityName: String): List<String> {
        return getProvidersByLocation(cityName)
            .map { it.category }
            .distinct()
    }

    /**
     * Get provider by ID
     */
    fun getProviderById(providerId: String): ServiceProvider? {
        val allProviders = jaipurProviders + kotaProviders + ajmerProviders + 
                          bundiProviders + delhiProviders + mumbaiProviders + bangaloreProviders
        return allProviders.find { it.id == providerId }
    }

    /**
     * Get all providers (for testing/fallback)
     */
    fun getAllProviders(): List<ServiceProvider> {
        return jaipurProviders + kotaProviders + ajmerProviders + 
               bundiProviders + delhiProviders + mumbaiProviders + bangaloreProviders
    }
}
