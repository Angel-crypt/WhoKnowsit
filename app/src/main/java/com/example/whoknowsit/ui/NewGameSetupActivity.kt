package com.example.whoknowsit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whoknowsit.R
import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class NewGameSetupActivity : AppCompatActivity() {
    
    private var selectedCategory: Category? = null
    private var selectedDifficulty: Difficulty? = null
    private var selectedQuantity: Int? = null
    
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var btnEasy: MaterialButton
    private lateinit var btnMedium: MaterialButton
    private lateinit var btnHard: MaterialButton
    private lateinit var btnQuantity5: MaterialButton
    private lateinit var btnQuantity10: MaterialButton
    private lateinit var btnQuantity20: MaterialButton
    private lateinit var btnStart: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_game_setup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupCategoryRecycler()
        setupDifficultyButtons()
        setupQuantityButtons()
        setupStartButton()
    }
    
    private fun setupCategoryRecycler() {
        val recyclerView = findViewById<RecyclerView>(R.id.category_recycler)
        categoryAdapter = CategoryAdapter { category ->
            selectedCategory = category
            categoryAdapter.notifyDataSetChanged()
        }
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = categoryAdapter
    }
    
    private fun setupDifficultyButtons() {
        btnEasy = findViewById(R.id.btn_easy)
        btnMedium = findViewById(R.id.btn_medium)
        btnHard = findViewById(R.id.btn_hard)
        btnEasy.setOnClickListener {
            selectDifficulty(Difficulty.EASY)
        }
        btnMedium.setOnClickListener {
            selectDifficulty(Difficulty.MEDIUM)
        }
        btnHard.setOnClickListener {
            selectDifficulty(Difficulty.HARD)
        }
    }
    
    private fun selectDifficulty(difficulty: Difficulty) {
        selectedDifficulty = difficulty
        
        resetDifficultyButtons()
        
        when (difficulty) {
            Difficulty.EASY -> {
                btnEasy.elevation = 12f
            }
            Difficulty.MEDIUM -> {
                btnMedium.elevation = 12f
            }
            Difficulty.HARD -> {
                btnHard.elevation = 12f
            }
        }
    }
    
    private fun resetDifficultyButtons() {
        btnEasy.elevation = 6f
        btnMedium.elevation = 6f
        btnHard.elevation = 6f
    }
    
    private fun setupQuantityButtons() {
        btnQuantity5 = findViewById(R.id.btn_quantity_5)
        btnQuantity10 = findViewById(R.id.btn_quantity_10)
        btnQuantity20 = findViewById(R.id.btn_quantity_20)
        
        btnQuantity5.setOnClickListener {
            selectQuantity(5)
        }
        
        btnQuantity10.setOnClickListener {
            selectQuantity(10)
        }
        
        btnQuantity20.setOnClickListener {
            selectQuantity(20)
        }
    }
    
    private fun selectQuantity(quantity: Int) {
        selectedQuantity = quantity
        
        resetQuantityButtons()
        
        when (quantity) {
            5 -> {
                btnQuantity5.elevation = 12f
            }
            10 -> {
                btnQuantity10.elevation = 12f
            }
            20 -> {
                btnQuantity20.elevation = 12f
            }
        }
    }
    
    private fun resetQuantityButtons() {
        btnQuantity5.elevation = 6f
        btnQuantity10.elevation = 6f
        btnQuantity20.elevation = 6f
    }
    
    private fun setupStartButton() {
        btnStart = findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            if (selectedCategory != null && selectedDifficulty != null && selectedQuantity != null) {
                android.widget.Toast.makeText(
                    this,
                    "Iniciando juego: ${selectedCategory?.displayName}, ${selectedDifficulty?.displayName}, ${selectedQuantity} preguntas",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                android.widget.Toast.makeText(
                    this,
                    "Por favor selecciona todas las opciones",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private inner class CategoryAdapter(
        private val onCategorySelected: (Category) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
        
        private val categories = listOf(
            Category.FILM,
            Category.HISTORY,
            Category.SCIENCE,
            Category.MUSIC,
            Category.SPORTS,
            Category.POP_CULTURE,
            Category.RANDOM
        )
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_selector, parent, false)
            return CategoryViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = categories[position]
            holder.bind(category, category == selectedCategory)
        }
        
        override fun getItemCount() = categories.size
        
        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
            private val iconView: ImageView = itemView.findViewById(R.id.category_icon)
            private val nameView: TextView = itemView.findViewById(R.id.category_name)
            
            fun bind(category: Category, isSelected: Boolean) {
                nameView.text = category.displayName
                
                // Set icon based on category
                val iconRes = when (category) {
                    Category.FILM -> R.drawable.ic_category_film
                    Category.HISTORY -> R.drawable.ic_category_history
                    Category.SCIENCE -> R.drawable.ic_category_science
                    Category.MUSIC -> R.drawable.ic_category_music
                    Category.SPORTS -> R.drawable.ic_category_sports
                    Category.POP_CULTURE -> R.drawable.ic_category_pop_culture
                    Category.RANDOM -> R.drawable.ic_category_random
                }
                iconView.setImageResource(iconRes)
                
                if (isSelected) {
                    cardView.strokeWidth = 0
                    cardView.cardElevation = 12f
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.primary)
                    )
                    nameView.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.on_primary)
                    )
                    iconView.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.on_primary)
                    )
                } else {
                    cardView.strokeWidth = 4
                    cardView.cardElevation = 8f
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.surface)
                    )
                    nameView.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.on_surface)
                    )
                    iconView.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.on_surface)
                    )
                }
                
                itemView.setOnClickListener {
                    onCategorySelected(category)
                }
            }
        }
    }
}