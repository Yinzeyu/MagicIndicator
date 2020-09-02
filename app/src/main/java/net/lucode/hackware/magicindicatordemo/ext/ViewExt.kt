package net.lucode.hackware.magicindicatordemo.ext

import android.annotation.SuppressLint
import android.view.View
import net.lucode.hackware.magicindicatordemo.R
/**
 * Description:
 * @author: yzy
 * @date: 2019/9/24 10:39
 */
//点击事件
@SuppressLint("CheckResult")
inline fun View.click(crossinline function: (view: View) -> Unit) {
  this.setOnClickListener {
    val tag = this.getTag(R.id.id_tag_click)
    if (tag == null || System.currentTimeMillis() - tag.toString().toLong() > 600) {
      this.setTag(R.id.id_tag_click, System.currentTimeMillis())
      function.invoke(it)
    }
  }
}
