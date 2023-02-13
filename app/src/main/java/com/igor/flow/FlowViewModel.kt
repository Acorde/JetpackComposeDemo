package com.igor.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {

    /**
     * Count down flow
     * This call COLD flow and its not starting while no one is asigne to this event, not like in HOT flow which will emit no matter it has no subscribers
     */
    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue

        emit(startingValue)

        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

    val restaurantFlow = flow {
        delay(250)
        emit("Appetizer")
        delay(1000)
        emit("Main Dish")
        delay(100)
        emit("Desert")

    }

    init {
        //collectFlowWthOperators()
        // terminalCollectFlowOperators()
        restaurantFlowExample()
    }


    private fun restaurantFlowExample() {
        viewModelScope.launch {
            restaurantFlow.onEach {
                println("FLOW $it is delivered")
            }.collect{
                println("Flow: Now eating $it")
                delay(1500)
                println("FLOW: Finished eating $it")

            }
        }
    }


    /**
     * Flat map example -> Flat multiple array
     *
     */
    private fun flatMapExample() {
        val flow1 = (1..5).asFlow()

//        viewModelScope.launch {
//            flow1.flatMapConcat { id ->
//                //getDataById(id)
//            }.collect { value ->
//                println("The value is $value")
//            }
//        }

    }


    /**
     * Terminal collect flow operators are operators
     *
     */
    private fun terminalCollectFlowOperators() {
        viewModelScope.launch {

            val reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    /**
                     * 1 + 2 + 3 + 4 + 5... = 15
                     */
                    accumulator + value
                }
            val reduceResultWithStartValueFOLD = countDownFlow
                .fold(100) { accumulator, value ->
                    /**
                     * 1 + 2 + 3 + 4 + 5... = 115
                     */
                    accumulator + value
                }

            println("The reduceResult is $reduceResult")

            val count = countDownFlow.filter { time ->
                time % 2 == 0
                //println("***collect*** The current time is $time")
            }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println(time)
                }
                .count {
                    it % 2 == 0
                }

            println("The count is $count")

        }
    }

    private fun collectFlowWthOperators() {

        countDownFlow.onEach {
            println(it) //is equivalent to  .collect { time ->}
        }

        viewModelScope.launch {

            countDownFlow.filter { time ->
                time % 2 == 0
                //println("***collect*** The current time is $time")
            }
                .map { time ->
                    time * time
                }
                .onEach { time ->
                    println(time)
                }
                .collect { time ->

                    println("The current time is $time")
                }


        }
    }

    private fun collectFlow() {
        viewModelScope.launch {

            countDownFlow.collect { time ->
                delay(1500)
                println("***collect*** The current time is $time")
            }


        }
        viewModelScope.launch {
            /**
             * collectLatest -> if interrupted while processing, not emmit and jump to next one
             */
            countDownFlow.collectLatest { time ->
                //delay(1500)
                println("...collectLatest... The current time is  $time")
            }
        }
    }
}