package com.example.whoknowsit.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
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
        
        setupButtonPressAnimation(btnEasy) {
            selectDifficulty(Difficulty.EASY)
        }
        setupButtonPressAnimation(btnMedium) {
            selectDifficulty(Difficulty.MEDIUM)
        }
        setupButtonPressAnimation(btnHard) {
            selectDifficulty(Difficulty.HARD)
        }
    }
    
    private fun setupButtonPressAnimation(button: MaterialButton, onClick: () -> Unit) {
        button.setOnTouchListener { view, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    view.animate()
                        .scaleX(0.95f)
                        .scaleY(0.95f)
                        .setDuration(100)
                        .start()
                }
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                    if (event.action == android.view.MotionEvent.ACTION_UP) {
                        onClick()
                    }
                }
            }
            true
        }
    }
    
    private fun selectDifficulty(difficulty: Difficulty) {
        selectedDifficulty = difficulty
        
        // Reset all buttons to unselected state
        setDifficultyButtonState(btnEasy, false, Difficulty.EASY)
        setDifficultyButtonState(btnMedium, false, Difficulty.MEDIUM)
        setDifficultyButtonState(btnHard, false, Difficulty.HARD)
        
        // Set selected button
        when (difficulty) {
            Difficulty.EASY -> {
                setDifficultyButtonState(btnEasy, true, Difficulty.EASY)
                animateButtonSelection(btnEasy, true)
            }
            Difficulty.MEDIUM -> {
                setDifficultyButtonState(btnMedium, true, Difficulty.MEDIUM)
                animateButtonSelection(btnMedium, true)
            }
            Difficulty.HARD -> {
                setDifficultyButtonState(btnHard, true, Difficulty.HARD)
                animateButtonSelection(btnHard, true)
            }
        }
    }
    
    private fun setDifficultyButtonState(button: MaterialButton, isSelected: Boolean, difficulty: Difficulty) {
        if (isSelected) {
            val colorRes = when (difficulty) {
                Difficulty.EASY -> R.color.success
                Difficulty.MEDIUM -> R.color.warning
                Difficulty.HARD -> R.color.error
            }
            button.backgroundTintList = ContextCompat.getColorStateList(this, colorRes)
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.strokeWidth = 0
            button.elevation = 8f
            button.scaleX = 1.05f
            button.scaleY = 1.05f
        } else {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.surface_variant)
            button.setTextColor(ContextCompat.getColor(this, R.color.primary))
            button.strokeWidth = 2
            button.strokeColor = ContextCompat.getColorStateList(this, R.color.primary)
            button.elevation = 2f
            button.scaleX = 1f
            button.scaleY = 1f
        }
    }
    
    private fun resetDifficultyButtons() {
        setDifficultyButtonState(btnEasy, false, Difficulty.EASY)
        setDifficultyButtonState(btnMedium, false, Difficulty.MEDIUM)
        setDifficultyButtonState(btnHard, false, Difficulty.HARD)
    }
    
    private fun setupQuantityButtons() {
        btnQuantity5 = findViewById(R.id.btn_quantity_5)
        btnQuantity10 = findViewById(R.id.btn_quantity_10)
        btnQuantity20 = findViewById(R.id.btn_quantity_20)
        
        setupButtonPressAnimation(btnQuantity5) {
            selectQuantity(5)
        }
        
        setupButtonPressAnimation(btnQuantity10) {
            selectQuantity(10)
        }
        
        setupButtonPressAnimation(btnQuantity20) {
            selectQuantity(20)
        }
    }
    
    private fun selectQuantity(quantity: Int) {
        selectedQuantity = quantity
        
        setQuantityButtonState(btnQuantity5, false)
        setQuantityButtonState(btnQuantity10, false)
        setQuantityButtonState(btnQuantity20, false)
        
        when (quantity) {
            5 -> {
                setQuantityButtonState(btnQuantity5, true)
                animateButtonSelection(btnQuantity5, true)
            }
            10 -> {
                setQuantityButtonState(btnQuantity10, true)
                animateButtonSelection(btnQuantity10, true)
            }
            20 -> {
                setQuantityButtonState(btnQuantity20, true)
                animateButtonSelection(btnQuantity20, true)
            }
        }
    }
    
    private fun setQuantityButtonState(button: MaterialButton, isSelected: Boolean) {
        if (isSelected) {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary)
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.strokeWidth = 0
            button.elevation = 8f
            button.scaleX = 1.05f
            button.scaleY = 1.05f
        } else {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.surface_variant)
            button.setTextColor(ContextCompat.getColor(this, R.color.primary))
            button.strokeWidth = 2
            button.strokeColor = ContextCompat.getColorStateList(this, R.color.primary)
            button.elevation = 2f
            button.scaleX = 1f
            button.scaleY = 1f
        }
    }
    
    private fun resetQuantityButtons() {
        setQuantityButtonState(btnQuantity5, false)
        setQuantityButtonState(btnQuantity10, false)
        setQuantityButtonState(btnQuantity20, false)
    }
    
    private fun animateButtonSelection(button: MaterialButton, isSelected: Boolean) {
        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", if (isSelected) 1.05f else 1f)
        val scaleY = ObjectAnimator.ofFloat(button, "scaleY", if (isSelected) 1.05f else 1f)
        val elevation = ObjectAnimator.ofFloat(button, "elevation", if (isSelected) 12f else 6f)
        
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, elevation)
        animatorSet.duration = 200
        animatorSet.interpolator = OvershootInterpolator(1.2f)
        animatorSet.start()
    }
    
    private fun setupStartButton() {
        btnStart = findViewById(R.id.btn_start)
        
        btnStart.setOnClickListener { view ->
            val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 0.95f)
            val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 0.95f)
            val scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f)
            val scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f)
            
            val scaleDown = AnimatorSet()
            scaleDown.playTogether(scaleDownX, scaleDownY)
            scaleDown.duration = 100
            
            val scaleUp = AnimatorSet()
            scaleUp.playTogether(scaleUpX, scaleUpY)
            scaleUp.duration = 100
            
            val animatorSet = AnimatorSet()
            animatorSet.play(scaleDown).before(scaleUp)
            animatorSet.start()
            
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
                    cardView.cardElevation = 8f
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.primary)
                    )
                    nameView.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                    iconView.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.white)
                    )
                    cardView.animate()
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(200)
                        .setInterpolator(OvershootInterpolator(1.2f))
                        .start()
                } else {
                    cardView.strokeWidth = 2
                    cardView.strokeColor = ContextCompat.getColor(itemView.context, R.color.primary)
                    cardView.cardElevation = 2f
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(itemView.context, R.color.surface_variant)
                    )
                    nameView.setTextColor(
                        ContextCompat.getColor(itemView.context, R.color.primary)
                    )
                    iconView.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.primary)
                    )
                    cardView.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200)
                        .start()
                }
                
                itemView.setOnClickListener { view ->
                    val scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 0.95f)
                    val scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 0.95f)
                    val scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f)
                    val scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f)
                    
                    val scaleDown = AnimatorSet()
                    scaleDown.playTogether(scaleDownX, scaleDownY)
                    scaleDown.duration = 100
                    
                    val scaleUp = AnimatorSet()
                    scaleUp.playTogether(scaleUpX, scaleUpY)
                    scaleUp.duration = 100
                    
                    val animatorSet = AnimatorSet()
                    animatorSet.play(scaleDown).before(scaleUp)
                    animatorSet.start()
                    
                    onCategorySelected(category)
                }
            }
        }
    }
}