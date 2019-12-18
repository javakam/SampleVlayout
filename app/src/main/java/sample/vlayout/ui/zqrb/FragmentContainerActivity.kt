package sample.vlayout.ui.zqrb;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import sample.vlayout.R

/**
 * Title: FragmentContainerActivity
 * <p>
 * Description:
 * </p>
 * @author Changbao
 * @date 2019/12/2  15:56
 */
class FragmentContainerActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_NAME = "FragmentContainerActivity.fragment_name"
        const val BUNDLE = "FragmentContainerActivity.bundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        openFragment()
    }

    private fun openFragment() {
        val fragmentName = intent.getStringExtra(FRAGMENT_NAME)
        val fragment: Fragment = Class.forName(fragmentName ?: "").newInstance() as Fragment
        val bundle = intent.extras

        supportFragmentManager.inTransaction {
            fragment.arguments = bundle
            add(R.id.container, fragment)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val ft: FragmentTransaction = beginTransaction()
        ft.func()
        ft.commitAllowingStateLoss()
        //supportFragmentManager.executePendingTransactions()
    }

}