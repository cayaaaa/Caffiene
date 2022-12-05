package com.psi.caffeine.data

import com.psi.caffeine.R
import com.psi.caffeine.model.Cafe
import com.psi.caffeine.model.Comment

object Dummy {
    
    fun getComments() = listOf<Comment>(
        Comment(
            "Sandi Wijaksono",
            "Video yang bermanfaat!"
        ),
        Comment(
            "Abdul Aziz",
            "Permisi saya ingin bertanya. Kalau 1 + 1 = 2.\n" +
                    "Kalau aku + kamu jadi apa ya?? Kalau Anda kesal dengan pertanyaan ini, Anda bisa mengabaikannya. Terima kasih : )"
        ),
        Comment(
            "Rizky",
            "Saya suka video ini"
        )
    )
}