package org.example

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import org.example.theme.WaitlessTheme
import org.example.controller.UserController
import org.example.model.UserModel
import org.example.utils.LocationUtils
import org.example.viewmodel.UserViewModel
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    companion object {
        const val targetLat = 43.470630
        const val targetLon = -80.541380
    }

    // Declare userViewModel at the class level
    private lateinit var userViewModel: UserViewModel

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        println(isGranted)
        if (isGranted) {

            // Permission granted, verify user location
            verifyUserLocation()
        } else {
            // Permission denied, show message
            Toast.makeText(this, "Location permission is required to start a workout.", Toast.LENGTH_LONG).show()
            //userViewModel.updateCanStartWorkout(false)
        }
    }

    private fun checkAndRequestLocationPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                println("Permission already granted.")
                verifyUserLocation()
            }
            else -> {
                println("Permission not granted, so requesting launch")
                runBlocking {  locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)  }
                runBlocking { verifyUserLocation() }
            }
        }
    }

    private fun verifyUserLocation() {
        // Use LocationUtils to verify the user's location
        LocationUtils.getUserLocation(this) { location ->
            if (location != null && LocationUtils.isWithinLocationRange(location, targetLat, targetLon)) {
                println("User is at the gym.")
                // User is within range; start the workout.
                runOnUiThread {
                    Toast.makeText(this, "You are at the gym.", Toast.LENGTH_LONG).show()
                }
                userViewModel.updateCanStartWorkout(true)
            } else {
                println("User is not at the gym.")
                // User is not at the gym, update the showDialog value in ViewModel.
                runOnUiThread {
                    Toast.makeText(this, "You are not at the gym.", Toast.LENGTH_LONG).show()
                    userViewModel.updateCanStartWorkout(false)
                    //userViewModel.updateShowDialog(true)
                }
                userViewModel.updateCanStartWorkout(false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userModel = UserModel()
        userViewModel = UserViewModel(userModel) // Initialize userViewModel
        val userController = UserController(userModel)

        checkAndRequestLocationPermissions()

        setContent {
            WaitlessTheme {
                WaitlessApp(userViewModel, userController)
            }
        }

    }
}
