package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val  PRICE_PER_CUPCAKE = 2.00

private const val EXTRA_PRICE_FOR_SAME_DAY_PICKUP = 3.00
class OrderViewModel : ViewModel() {



    private var _orderQuantity = MutableLiveData<Int>()
    val orderQuantity: LiveData<Int> = _orderQuantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor


    private val _pickupDate = MutableLiveData<String>()
    val pickupDate: LiveData<String> = _pickupDate

    private var _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) { NumberFormat.getCurrencyInstance().format(it)}

    //options for radio button
    val dateOptions = getPickupOptions()


    init {
        resetOrder();
    }

    fun setQuantity(quantity: Int) {
        _orderQuantity.value = quantity
        calculatePrice()
    }

    fun setFlavor(flavor: String) {
        _flavor.value = flavor
    }

    fun setDate(Date: String) {
        _pickupDate.value = Date
        calculatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    //create a list of date options for pickup radio buttons
    fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>();
        val dateFormatter = SimpleDateFormat("E MMM d", Locale.getDefault());
        val calendar = Calendar.getInstance();

        //set a list of dates today and 3 days after
        repeat(4) {
            //add the current date to the list of date
            options.add(dateFormatter.format(calendar.time))
            //move the calendar one day ahead
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

     fun resetOrder() {
        _orderQuantity.value = 0
        _flavor.value = ""
        _pickupDate.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun calculatePrice() {
        _price.value = (orderQuantity.value ?: 0) * PRICE_PER_CUPCAKE
        //if same day pickup is on
        if(dateOptions[0] == pickupDate.value) {
            _price.value = _price.value!! + EXTRA_PRICE_FOR_SAME_DAY_PICKUP

        }
    }

}