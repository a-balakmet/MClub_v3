//package kz.magnum.app.ui.components.scanner
//
//import aab.lib.commons.ui.dialogs.TwoButtonsDialog
//import aab.lib.commons.utils.openAppSettings
//import android.Manifest
//import android.app.Activity
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.annotation.OptIn
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ExperimentalGetImage
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageProxy
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.SideEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import com.google.mlkit.vision.barcode.common.Barcode
//import com.google.mlkit.vision.common.InputImage
//import java.util.concurrent.Executors
//
//@kotlin.OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ScannerView(
//    noPermissionText: String,
//    okText: String = "OK",
//    noText: String = "No",
//    onScanComplete: (String) -> Unit
//) {
//
//    val activity = LocalContext.current as Activity
//    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
//
//    if (cameraPermissionState.status.isGranted) {
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = { context ->
//                val cameraExecutor = Executors.newSingleThreadExecutor()
//                val previewView = PreviewView(context).also {
//                    it.scaleType = PreviewView.ScaleType.FILL_CENTER
//                }
//                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//                cameraProviderFuture.addListener({
//                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//                    val preview = Preview.Builder()
//                        .build()
//                        .also {
//                            it.setSurfaceProvider(previewView.surfaceProvider)
//                        }
//                    val imageCapture = ImageCapture.Builder().build()
//                    val imageAnalyzer = ImageAnalysis.Builder()
//                        .build()
//                        .also {
//                            it.setAnalyzer(cameraExecutor, BarcodeAnalyzer{ barcode ->
//                                onScanComplete(barcode)
//                                cameraExecutor.shutdown() // check for price-checker
//                            })
//                        }
//                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//                    try {
//                        cameraProvider.unbindAll()
//                        cameraProvider.bindToLifecycle(
//                            context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer)
//                    } catch(exc: Exception) {
//                        Log.e("DEBUG", "Use case binding failed", exc)
//                    }
//                }, ContextCompat.getMainExecutor(context))
//                previewView
//            }
//        )
//    } else if (cameraPermissionState.status.shouldShowRationale) {
//        TwoButtonsDialog(
//            messageText = noPermissionText,
//            leftButton = okText, rightButton = noText,
//            onClickLeft = { activity.openAppSettings() },
//            onClickRight = { onScanComplete("") }
//        )
//    } else {
//        SideEffect {
//            cameraPermissionState.run { launchPermissionRequest() }
//        }
//    }
//}