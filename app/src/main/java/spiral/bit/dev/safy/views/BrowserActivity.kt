package spiral.bit.dev.safy.views

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.databinding.ActivityBrowserBinding
import spiral.bit.dev.safy.utils.MyWebViewClient

class BrowserActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var browserBinding: ActivityBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        browserBinding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(browserBinding.root)

        webView = browserBinding.browserWebView
        webView.webViewClient = MyWebViewClient()
        webView.loadUrl("https://duckduckgo.com/")
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.clearHistory()
        webView.clearCache(true)
        webView.clearSslPreferences()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}