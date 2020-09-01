package net.lucode.hackware.magicindicatordemo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import net.lucode.hackware.magicindicatordemo.R

/**
 * Created by hackware on 2016/9/13.
 */
class TestFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView = view.findViewById<View>(R.id.text_view) as TextView
        val bundle = arguments
        if (bundle != null) {
            textView.text = bundle.getString(EXTRA_TEXT)
        }
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}