package com.example.mjappxml.model

import android.graphics.Color
import com.example.mjappxml.R

enum class ElswordCharacters(
    val characterName: String,
    val color: String,
    val sdImage: Int,
    val jobImage: List<Int>
) {
    Elsword(
        "엘소드",
        "#FFBA333D",
        R.drawable.img_elsword_sd,
        listOf(
            R.drawable.img_elsword_sd_1,
            R.drawable.img_elsword_sd_2,
            R.drawable.img_elsword_sd_3,
            R.drawable.img_elsword_sd_4
        )
    ),
    Aisha(
        "아이샤",
        "#FF7E30A0",
        R.drawable.img_aisha_sd,
        listOf(
            R.drawable.img_aisha_sd_1,
            R.drawable.img_aisha_sd_2,
            R.drawable.img_aisha_sd_3,
            R.drawable.img_aisha_sd_4
        )
    ),
    Rena(
        "레나",
        "#FF91B23B",
        R.drawable.img_rena_sd,
        listOf(
            R.drawable.img_rena_sd_1,
            R.drawable.img_rena_sd_2,
            R.drawable.img_rena_sd_3,
            R.drawable.img_rena_sd_4
        )
    ),
    Raven(
        "레이븐",
        "#FF666B70",
        R.drawable.img_raven_sd,
        listOf(
            R.drawable.img_raven_sd_1,
            R.drawable.img_raven_sd_2,
            R.drawable.img_raven_sd_3,
            R.drawable.img_raven_sd_4
        )
    ),
    Eve(
        "이브",
        "#FFDC91A2",
        R.drawable.img_eve_sd,
        listOf(
            R.drawable.img_eve_sd_1,
            R.drawable.img_eve_sd_2,
            R.drawable.img_eve_sd_3,
            R.drawable.img_eve_sd_4
        )
    ),
    Chung(
        "청",
        "#FF58BED2",
        R.drawable.img_chung_sd,
        listOf(
            R.drawable.img_chung_sd_1,
            R.drawable.img_chung_sd_2,
            R.drawable.img_chung_sd_3,
            R.drawable.img_chung_sd_4
        )
    ),
    Ara(
        "아라",
        "#FFEB831D",
        R.drawable.img_ara_sd,
        listOf(
            R.drawable.img_ara_sd_1,
            R.drawable.img_ara_sd_2,
            R.drawable.img_ara_sd_3,
            R.drawable.img_ara_sd_4
        )
    ),
    Elesis(
        "엘레시스",
        "#FFA72F40",
        R.drawable.img_elesis_sd,
        listOf(
            R.drawable.img_elesis_sd_1,
            R.drawable.img_elesis_sd_2,
            R.drawable.img_elesis_sd_3,
            R.drawable.img_elesis_sd_4
        )
    ),
    Add(
        "애드",
        "#FF6F4AD0",
        R.drawable.img_add_sd,
        listOf(
            R.drawable.img_add_sd_1,
            R.drawable.img_add_sd_2,
            R.drawable.img_add_sd_3,
            R.drawable.img_add_sd_4
        )
    ),
    Luciel(
        "루시엘",
        "#FF2567C7",
        R.drawable.img_luciel_sd,
        listOf(
            R.drawable.img_luciel_sd_1,
            R.drawable.img_luciel_sd_2,
            R.drawable.img_luciel_sd_3,
            R.drawable.img_luciel_sd_4
        )
    ),
    Rose(
        "로제",
        "#FFF0C429",
        R.drawable.img_rose_sd,
        listOf(
            R.drawable.img_rose_sd_1,
            R.drawable.img_rose_sd_2,
            R.drawable.img_rose_sd_3,
            R.drawable.img_rose_sd_4
        )
    ),
    Ain(
        "아인",
        "#FF56C7A9",
        R.drawable.img_ain_sd,
        listOf(
            R.drawable.img_ain_sd_1,
            R.drawable.img_ain_sd_2,
            R.drawable.img_ain_sd_3,
            R.drawable.img_ain_sd_4
        )
    ),
    Laby(
        "라비",
        "#FFDE4D78",
        R.drawable.img_laby_sd,
        listOf(
            R.drawable.img_laby_sd_1,
            R.drawable.img_laby_sd_2,
            R.drawable.img_laby_sd_3,
            R.drawable.img_laby_sd_4
        )
    ),
    Noah(
        "노아",
        "#FF424AB4",
        R.drawable.img_noah_sd,
        listOf(
            R.drawable.img_noah_sd_1,
            R.drawable.img_noah_sd_2,
            R.drawable.img_noah_sd_3,
            R.drawable.img_noah_sd_4
        )
    );

    fun getCharacterColor() = Color.parseColor(color)

//    companion object {
//        fun getCharacterColor(characterName: String) =
//            ElswordCharacters.values().find { it.characterName == characterName }?.color ?: Elsword.color
//
//    }
}