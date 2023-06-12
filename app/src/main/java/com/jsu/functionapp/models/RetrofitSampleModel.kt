package com.jsu.functionapp.models

data class RetrofitSampleModel(
    var SampleIdx:String? = null,
    var jsonValue:ArrayList<Lists>? = null
) {

    inner class Lists {
        var SampleCd: String? = null

    }

}
