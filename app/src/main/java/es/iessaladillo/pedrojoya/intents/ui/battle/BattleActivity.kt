package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.ClipData.newIntent
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding.inflate
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity

private const val RC_POKEMON_SELECTION: Int = 1
private const val RC_POKEMON_FIGHT: Int = 1

class BattleActivity : AppCompatActivity() {

    private lateinit var binding: BattleActivityBinding
    private lateinit var pokemon1 : Pokemon
    private lateinit var pokemon2 : Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BattleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {

        pokemon1 = Database.getRandomPokemon()
        pokemon2 = Database.getRandomPokemon()
        binding.imgPokemon1.setImageDrawable(getDrawable(pokemon1.idImage))
        binding.imgPokemon2.setImageDrawable(getDrawable(pokemon2.idImage))
        binding.lblPokemon1.text = getText(pokemon1.idName)
        binding.lblPokemon2.text = getText(pokemon2.idName)
        binding.viewPokemon1.setOnClickListener { selectPokemon(pokemon1, 1) }
        binding.viewPokemon2.setOnClickListener { selectPokemon(pokemon2, 2) }
        binding.bttFight.setOnClickListener{ fight() }
    }

    private fun selectPokemon(pokemon : Pokemon, screen : Int) {
        val intent = SelectionActivity.newIntent(this, pokemon.id, screen)
        startActivityForResult(intent, RC_POKEMON_FIGHT)
    }

    private fun fight() {
        val winner : Pokemon
        if(pokemon1.strenght > pokemon2.strenght){
            winner = pokemon1
        }else{
            winner = pokemon2
        }
        val intent = WinnerActivity.newIntent(this, winner.id)
        startActivityForResult(intent, RC_POKEMON_SELECTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == RC_POKEMON_SELECTION && intent != null) {
            extractResult(intent)
        }
    }

    private fun extractResult(intent: Intent) {
        if (!intent.hasExtra(SelectionActivity.EXTRA_POKEMON_ID) || !intent.hasExtra(SelectionActivity.EXTRA_SCREEN) ) {
            throw RuntimeException(
                "BattleActivity must receive pokemonId and screen in result intent")
        }

        if (intent.getIntExtra(SelectionActivity.EXTRA_SCREEN, 1)==1){
            pokemon1 = Database.getPokemonById(
                intent.getLongExtra(
                    SelectionActivity.EXTRA_POKEMON_ID,
                    0
                )
            )!!
            binding.imgPokemon1.setImageDrawable(getDrawable(pokemon1.idImage))
            binding.lblPokemon1.text = getText(pokemon1.idName)

        }else{
            pokemon2 = Database.getPokemonById(
                intent.getLongExtra(
                    SelectionActivity.EXTRA_POKEMON_ID,
                    0
                )
            )!!
            binding.imgPokemon2.setImageDrawable(getDrawable(pokemon2.idImage))
            binding.lblPokemon2.text = getText(pokemon2.idName)
        }


    }

}