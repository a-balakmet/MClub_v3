package kz.magnum.app.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppStoreKeys {
    val locale = stringPreferencesKey("locale")
    val phone = stringPreferencesKey("phone")
    val user = stringPreferencesKey("user")
    val pinCode = stringPreferencesKey("pinCode")
    val isFaceId = booleanPreferencesKey("isFaceId")
    val isFingerprint = booleanPreferencesKey("isFingerprint")
    val isShowHello = booleanPreferencesKey("isShowHello")
    val pushTokenDate = stringPreferencesKey("pushTokenDate")
    val pushPermissionDate = stringPreferencesKey("pushPermissionDate")
    val stringsVersion = intPreferencesKey("stringsVersion")
    val savedId = intPreferencesKey("savedId")
    val savedQuery = stringPreferencesKey("savedQuery")
    val refresher = booleanPreferencesKey("refresher")

}

/*

FlutterSharedPreferences.xml

<map>
    <string name="flutter.phone">(701) 797-04-45</string>
    <boolean name="flutter.isFaceId" value="false" />
    <string name="flutter.accessToken">eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOjE3NzUxMDYsImV4cCI6MTY5NjA3MzU5MX0.rNnHR2QrgJnC0TRCUHMSs4w68pgGM7siceT9Nw-FFyFwN4lgNRbTewcuQjIj_diwWyH7u30mckcSJGG3kjhVC7x2jUsjgA-tffsPToKZZnQWhvkmd6wCOfNyAjXs5TlN5tLZMnZ1nODCDmdEbdfVwlZ_1aeeRzl3OKuXcMCin7gSXI6vPSKY9TVPVRtU7LweBXRrPDQT8oqL5kYVrFRk-V9_-Ctu5ie5wsPIMmhOh2wa35Y8FUxFmK2Cg3PUzoGGjQbak1VURe3CTLgIBl_3NrCViXZvwEndCUm6uMumlAykBWUNnXOHGLrwEnhGwLrmDTIuLnidTvRyVFEM6b8ePaeoy7hLYuJu4PIuOK8KRY9DGSqa7HPMjx3sk-a2Com4EJxFmNi5HzXuY4wVhmdWFfkr1TGWdk5mi9VMxV4LKrhx-zFis8MhYowG83khS5Yan7k6uXW4Fcs4FI_gF521u0k4jbkBx0TH5v0aypXk8fuCYQEYhYp_RF_oY7NPgfZzpus5jiNojNAIvEMtIe1c_xtZ9mqvTNNQ31tTZONJzHtWxtV0kBDd0o65LeA0e-9mzRZWXM7-68dT3rVdk86Goqosm6QcW0QAmpVwy4Pjy60BuRzINp6DS3s6416wpcHggnsjqRTaGPLc-ScU6F6Ql55RvqRNZqES8LJUuXtoQtk</string>
    <string name="flutter.userProfile">{&quot;id&quot;:1775106,&quot;birthday&quot;:&quot;&quot;,&quot;cityId&quot;:1,&quot;cityName&quot;:&quot;Алматы&quot;,&quot;cityLatitude&quot;:43.206191,&quot;cityLongitude&quot;:76.897995,&quot;email&quot;:&quot;&quot;,&quot;firstName&quot;:&quot;&quot;,&quot;secondName&quot;:&quot;&quot;,&quot;lastName&quot;:&quot;&quot;,&quot;gender&quot;:&quot;male&quot;,&quot;phone&quot;:&quot;7017970445&quot;,&quot;favoriteShop&quot;:null,&quot;carPlate&quot;:&quot;&quot;,&quot;printReceipt&quot;:false}</string>
    <string name="flutter.pushPermissionDate">2023-09-27</string>
    <string name="flutter.fbDate">2023-09-27</string>
    <string name="flutter.refreshToken">eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfaWQiOjE3NzUxMDYsImV4cCI6MTcxMTU4MjM5MX0.gA20pN2tWFFFGQMXqfUzDl918hcO9eyDR50UlVfj1_Vk8Cxi4u4PKgN510Gb7beMH-MTcHQuyfL9auQCGAFJOjk9QSrG0X5NBcBbOqgeXYEJjizGysSE4T0v6MaleMnEC3h69MiUfjNJ6Ofmn4qXdtY4YJLurF2ZlxZDx9RvVOBPNzMwRPolDYtdZYIS0vZpG8BukhUJER5n2mHPF-wujdiU-3WvSn04X6akob0JATLS9XFz2hSsi1vdPjcG0v-Y-HHTOmJxrGlrQm67_eZ2rrQf39s4TeVIRO6FpNeXN7S4gDbqoMbzc7GBVqVIafjOCHPwdkDpbSyca1if1I1d0X4fXlLVoKKYGGIlSP2k1rjE1ClAM7QbdqG6EF_ofWzN_Bh3OB15jgLsY1tedAmc3D6Ab2Ce0iEfHGDtb1291jxWCgmlkwhrLaYFe58Ic1XCd24qA7oAuDreij6egPJPiNoIe7AmKVZj_PFYDCM91TnEEtopvvZuNrzq97FgCVtZBUmPkR1F0eAGHgoJCi4M3RQrsnmU1XfahX2B712M5YYTekIA8nxhUEgt3qNoqwbUYDAjQMsGw4azBEMKrbWDxkvlrZfn4qrmL1w19tj0Kos1f2qnB_7xejtFrQxMgl1_5cToiQ12p5wZBUTLEDTf9vU9eLEnK7XULTCkIj63HTw</string>
    <long name="flutter.stringsDBVersion" value="445" />
    <long name="flutter.cardNo" value="0" />
    <boolean name="flutter.isFingerprint" value="false" />
    <long name="flutter.beaconsDBVersion" value="70" />
</map>

 */