package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.Database.bulbasaur
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding

class SelectionActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_POKEMON_ID = "EXTRA_POKEMON_ID"
        const val EXTRA_SCREEN = "EXTRA_SCREEN"

        fun newIntent(context: Context, id: Long, screen: Int): Intent =
            Intent(context, SelectionActivity::class.java)
                .putExtras( bundleOf(
                    EXTRA_POKEMON_ID to id, EXTRA_SCREEN to screen)
                )

    }

    private lateinit var binding: SelectionActivityBinding
    private lateinit var a: Array <RadioButton>
    private var pokemon : Pokemon? = null
    private var screen = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectionActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        getIntentData()
        setUpViews()
    }

    private fun setUpViews() {
        val pikachu : Pokemon = Database.getPokemonById(0)!!
        binding.rdbttPikachu.tag = pikachu.id
        val giratina : Pokemon = Database.getPokemonById(1)!!
        binding.rdbttGiratina.tag = giratina.id
        val cubone : Pokemon = Database.getPokemonById(2)!!
        binding.rdbttCubone.tag = cubone.id
        val gyarados : Pokemon = Database.getPokemonById(3)!!
        binding.rdbttGyarados.tag = gyarados.id
        val feebas : Pokemon = Database.getPokemonById(4)!!
        binding.rdbttFeebas.tag = feebas.id
        val bulbasaur : Pokemon = Database.getPokemonById(5)!!
        binding.rdbttBulbasaur.tag = bulbasaur.id

        a = arrayOf(binding.rdbttBulbasaur, binding.rdbttCubone, binding.rdbttPikachu,
            binding.rdbttGiratina, binding.rdbttGyarados, binding.rdbttFeebas)

        selectButton(a)

        binding.rdbttFeebas.setOnClickListener { updateViews(binding.rdbttFeebas, a) }
        binding.rdbttGyarados.setOnClickListener { updateViews(binding.rdbttGyarados, a) }
        binding.rdbttGiratina.setOnClickListener { updateViews(binding.rdbttGiratina, a) }
        binding.rdbttPikachu.setOnClickListener { updateViews(binding.rdbttPikachu, a) }
        binding.rdbttBulbasaur.setOnClickListener { updateViews(binding.rdbttBulbasaur, a) }
        binding.rdbttCubone.setOnClickListener { updateViews(binding.rdbttCubone, a) }

    }

    private fun updateViews( radioButton: RadioButton, array: Array <RadioButton>) {
        radioButton.isChecked = true
        pokemon = getPokemonById(radioButton.tag as Long)
        for (compoundButton in array){
            if(compoundButton != radioButton){
                compoundButton.isChecked = false
            }
        }
    }

    override fun onBackPressed() {
        val result = Intent().putExtras(bundleOf(EXTRA_POKEMON_ID to pokemon!!.id, EXTRA_SCREEN to screen))
        setResult(RESULT_OK, result)
        super.onBackPressed()
    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON_ID)) {
            throw RuntimeException(
                "SelectionActivity needs to receive pokemon id as extra")
        }
        screen=intent.getIntExtra(EXTRA_SCREEN,1)
        pokemon = Database.getPokemonById(intent.getLongExtra(EXTRA_POKEMON_ID, 0))
    }

    private fun selectButton(array: Array <RadioButton>){
        for (compoundButton in array){
            if(compoundButton.tag == pokemon!!.id){
                compoundButton.isChecked = true
            }
        }
    }

}