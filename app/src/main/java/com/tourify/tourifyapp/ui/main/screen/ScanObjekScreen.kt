package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelCheckEmailFactory
import com.tourify.tourifyapp.data.factory.ViewModelScanningObjectFactory
import com.tourify.tourifyapp.data.viewmodels.CheckEmailViewModel
import com.tourify.tourifyapp.data.viewmodels.ScanningObjectViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.BoxLoading
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailObject
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailWisata
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.fonts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.O)
@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@OptIn(ExperimentalGetImage::class) @Composable
fun ScanObjekScreen(
    context: Context,
    navController: NavController,
    scanningObjectViewModel: ScanningObjectViewModel = viewModel(
        factory = ViewModelScanningObjectFactory(
            Injection.provideScanningObjectRepository()
        )
    )
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    fun takePhotoAndSendToServer(
        filenameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        scanningObjectViewModel: ScanningObjectViewModel,
        context: Context
    ) {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                Log.e("kilo", "Take photo error:", exception)
                onError(exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val inputStream = context.contentResolver.openInputStream(savedUri)
                val bytes = inputStream?.readBytes()
                bytes?.let {
                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile)
                    val imagePart = MultipartBody.Part.createFormData("file", photoFile.name, requestFile)
                    scanningObjectViewModel.scanningObject(imagePart)
                    val capturedBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                    capturedImage = capturedBitmap
                }
            }
        })
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var onShowDetailObject by rememberSaveable { mutableStateOf(false) }
    val modalDetailBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scanningObjectResult by scanningObjectViewModel.uiState.collectAsState(initial = UiState.Loading)
    var image by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(isLoading) {
        delay(4000)
        isLoading = false
        onShowDetailObject = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            AndroidView(
                factory = { previewView },
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onClick = { },
                        onLongClick = { },
                        onDoubleClick = { }
                    )
            )
            if (isLoading) {
                BoxLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorTransparent)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, top = 36.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flash_on),
                        contentDescription = "Flash",
                        tint = ColorWhite,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { }
                    )
                    Text(
                        text = "Mengidentifikasi Objek (AI)",
                        style = StyleText.copy(
                            color = ColorWhite,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = "Tutup",
                        tint = ColorWhite,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { navController.popBackStack() }
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, bottom = 36.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(shape = Shapes.small)
                            .clickable { },
                        contentAlignment = Alignment.Center,
                        content = {
                            capturedImage?.let { bitmap ->
                                image = bitmap
                                Image(
                                    bitmap.asImageBitmap(),
                                    contentDescription = "Captured Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(shape = RoundedCornerShape(100))
                            .background(ColorTransparent)
                            .clickable {
                                isLoading = true
                                takePhotoAndSendToServer(
                                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                    imageCapture = imageCapture,
                                    outputDirectory = context.filesDir,
                                    executor = Executors.newSingleThreadExecutor(),
                                    scanningObjectViewModel = scanningObjectViewModel,
                                    context = context
                                )
                            },
                        contentAlignment = Alignment.Center,
                        content = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_button_camera),
                                contentDescription = "Camera",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(shape = RoundedCornerShape(100))
                            .background(ColorSecondary.copy(0.5f))
                            .clickable { },
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_change_camera),
                                contentDescription = "Change Camera",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(26.dp)
                                    .align(Alignment.Center),
                                tint = ColorWhite
                            )
                        }
                    )
                }
            )
        }
    )
    if (onShowDetailObject) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                onShowDetailObject = false
            },
            sheetState = modalDetailBottomState,
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                ModalBottomSheetDetailObject(
                    image = image
                )
            }
        )
    }
}



