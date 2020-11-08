package es.iessaladillo.pedrojoya.intents.data.local

import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import kotlin.random.Random

// TODO: Haz que Database implemente DataSource
object Database : DataSource{

    val pikachu: Pokemon = Pokemon(0, R.drawable.pikachu, R.string.pikachu_name, 2)

    val giratina: Pokemon = Pokemon(1, R.drawable.giratina, R.string.giratina_name, 6)

    val cubone: Pokemon = Pokemon(2, R.drawable.cubone, R.string.cubone_name, 3)

    val gyarados: Pokemon = Pokemon(3, R.drawable.gyarados, R.string.gyarados_name, 5)

    val feebas: Pokemon = Pokemon(4, R.drawable.feebas, R.string.feebas_name, 1)

    val bulbasaur: Pokemon = Pokemon(5, R.drawable.bulbasur, R.string.bulbasaur_name, 4)

    val listaPokemon: List<Pokemon> = listOf(pikachu, giratina, cubone, gyarados, feebas, bulbasaur)

    override fun getRandomPokemon(): Pokemon {
        var random = Random.nextInt(6)

        return listaPokemon[random]
    }

    override fun getAllPokemons(): List<Pokemon> {
        return listaPokemon
    }

    override fun getPokemonById(id: Long): Pokemon? {
        for(i in 0..listaPokemon.size ){
            if(listaPokemon[i].id == id){
                return listaPokemon[i]
            }
        }
        return null
    }

}