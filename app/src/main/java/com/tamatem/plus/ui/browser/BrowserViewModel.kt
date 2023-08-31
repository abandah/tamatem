package com.tamatem.plus.ui.browser

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.tamatem.plus.bases.BaseViewModel

class BrowserViewModel : BaseViewModel()  {
    var visibilityOfLoading : MutableLiveData<Int> = MutableLiveData(View.GONE)

}