import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {

    //this is just saying all live data in this class is going to need to be executed immediately and synchronously
    //normally live data runs async when the data that it wraps is updated.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun test_set_quantity_12_cupcakes() {
        val orderViewModel = OrderViewModel()
        //it will complain that you are making changes to something that is live data but not observing the changes so this is needed
        orderViewModel.orderQuantity.observeForever( {})
        orderViewModel.setQuantity(12)

        assertEquals(12, orderViewModel.orderQuantity.value)

    }

    @Test
    fun test_price_updates_12_cupcakes_selected()  {
        val orderViewModel = OrderViewModel()
        orderViewModel.setQuantity(12)
        //transformations are only triggered when there are active observers on the live data so observe forever is also needed otherwise price.value will be null
        orderViewModel.price.observeForever {}
        assertEquals("$27.00", orderViewModel.price.value)


    }



}