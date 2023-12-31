package alain.niyonema.babara3d.renderer

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.view.Choreographer
import android.view.SurfaceView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.filament.Skybox
import com.google.android.filament.View
import com.google.android.filament.android.UiHelper
import com.google.android.filament.utils.KTX1Loader
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.random.Random

class Renderer3d {

    @SuppressLint("ClickableViewAccessibility")
    fun onSurface(surfaceView: SurfaceView, lifecycle: Lifecycle) {
        _lifecycle = lifecycle
        _choreographer = Choreographer.getInstance()
        _uiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK).apply {
            isOpaque = false
        }

        _lifecycle.addObserver(lifecycleObserver)

        _surfaceView = surfaceView
        _modelViewer = ModelViewer(surfaceView, uiHelper = _uiHelper)

        _modelViewer.scene.skybox = Skybox.Builder().build(_modelViewer.engine)
        _modelViewer.scene.skybox?.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        _modelViewer.view.blendMode = View.BlendMode.TRANSLUCENT
        _modelViewer.renderer.clearOptions = _modelViewer.renderer.clearOptions.apply {
            clear = true
        }

        loadGlb()
        _isLoaded = true
    }

    private fun loadGlb() {
        val buffer = am.open("models/3DAnimationRobotData.glb").use {
            val bytes = ByteArray(it.available())
            it.read(bytes)
            ByteBuffer.allocateDirect(bytes.size).apply {
                order(ByteOrder.nativeOrder())
                put(bytes)
                rewind()
            }
        }

        _modelViewer.apply {
            loadModelGlb(buffer)
            transformToUnitCube()
        }
    }

    fun turnOn() {
        if (_isLoaded) {
            _modelViewer.animator?.animationCount?.let {
                _currentAnimationIndex = Random.nextInt(it)
            }
            loadIndirectLight()
            _isTurnOn = true
            _choreographer.removeFrameCallback(frameCallback)
            _choreographer.postFrameCallback(frameCallback)
        }
    }

    fun turnOff() {
        if (_isLoaded) {
            removeIndirectLight()
            _isTurnOn = false
            _choreographer.removeFrameCallback(frameCallback)
            _choreographer.postFrameCallback(frameCallback)
        }
    }

    fun destroy() {
        if (_isLoaded) {
            _isLoaded = false
            _choreographer.removeFrameCallback(frameCallback)
            _lifecycle.removeObserver(lifecycleObserver)
        }
    }

    private fun removeIndirectLight() {
        _modelViewer.scene.indirectLight = null
    }

    private lateinit var _choreographer: Choreographer
    private lateinit var _uiHelper: UiHelper
    private lateinit var _surfaceView: SurfaceView
    private lateinit var _modelViewer: ModelViewer
    private lateinit var _lifecycle: Lifecycle
    private var _isLoaded = false
    private var _isTurnOn = true
    private var _currentAnimationIndex = 0

    private val am: AssetManager get() = _surfaceView.context.assets

    private val frameCallback = object : Choreographer.FrameCallback {
        private val startTime = System.nanoTime()
        override fun doFrame(currentTime: Long) {
            val seconds = (currentTime - startTime).toDouble() / 1_000_000_000
            _choreographer.postFrameCallback(this)
            _modelViewer.animator?.apply {
                if (animationCount > 0 && _isTurnOn) {
                    applyAnimation(_currentAnimationIndex, seconds.toFloat())
                }
                updateBoneMatrices()
            }
            _modelViewer.render(currentTime)
        }
    }

    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            _choreographer.postFrameCallback(frameCallback)
        }

        override fun onPause(owner: LifecycleOwner) {
            _choreographer.removeFrameCallback(frameCallback)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            destroy()
            super.onDestroy(owner)
        }
    }

    private fun loadIndirectLight() {
        val buffer = am.open("environments/scenes_2k_ibl.ktx").use {
            val bytes = ByteArray(it.available())
            it.read(bytes)
            ByteBuffer.allocateDirect(bytes.size).apply {
                order(ByteOrder.nativeOrder())
                put(bytes)
                rewind()
            }
        }

        KTX1Loader.createIndirectLight(_modelViewer.engine, buffer).apply {
            intensity = 50_000f
            _modelViewer.scene.indirectLight = this
        }
    }

    companion object {
        init {
            Utils.init()
        }
    }

}