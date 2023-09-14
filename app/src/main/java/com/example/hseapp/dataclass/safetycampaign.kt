package com.example.hseapp.dataclass

data class safetycampaign(
    val data: List<SafetyCampaignData>
)

data class SafetyCampaignData(
    val attributes: SafetyCampaignAttributes,
    val id: Int
)

data class SafetyCampaignAttributes(
    val gambar: SafetyCampaignGambar,
    val nama: String
)

data class SafetyCampaignGambar(
    val data: SafetyCampaignGambarData
)

data class SafetyCampaignGambarData(
    val attributes: SafetyCampaignGambarAttributes,
    val id: Int
)

data class SafetyCampaignGambarAttributes(
    val url: String
)