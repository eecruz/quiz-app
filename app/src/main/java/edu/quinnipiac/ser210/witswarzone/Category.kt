/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

// enum class for categories and their pics
enum class Category(val display: String, val apiInput: String, val drawableID: Int)
{
    GENERAL("General", "GENERAL", R.drawable.general),
    ARTLITERATURE("Art & Literature", "ARTLITERATURE", R.drawable.art),
    LANGUAGE("Language", "LANGUAGE", R.drawable.language),
    SCIENCENATURE("Science & Nature", "SCIENCENATURE", R.drawable.science),
    FOODDRINK("Food & Drink", "FOODDRINK", R.drawable.food),
    PEOPLEPLACES("People & Places", "PEOPLEPLACES", R.drawable.people),
    GEOGRAPHY("Geography", "GEOGRAPHY", R.drawable.geography),
    HISTORYHOLIDAYS("History & Holidays", "HISTORYHOLIDAYS", R.drawable.history),
    ENTERTAINMENT("Entertainment", "ENTERTAINMENT", R.drawable.entertainment),
    TOYSGAMES("Toys & Games", "TOYSGAMES", R.drawable.toys),
    MUSIC("Music", "MUSIC", R. drawable.music),
    MATHEMATICS("Mathematics", "MATHEMATICS", R.drawable.math),
    RELIGIONMYTHOLOGY("Religion & Mythology", "RELIGIONMYTHOLOGY", R.drawable.religion),
    SPORTSLEISURE("Sports & Leisure", "SPORTSLEISURE", R.drawable.sports);
}