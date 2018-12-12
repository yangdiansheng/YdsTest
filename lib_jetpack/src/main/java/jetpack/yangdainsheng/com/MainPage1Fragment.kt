package jetpack.yangdainsheng.com

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main_1.*

class MainPage1Fragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_1,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_1.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainPage1Fragment_to_mainPage2Fragment)
        }
        button_2.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainPage1Fragment_to_mainPage3Fragment)
        }
    }
}