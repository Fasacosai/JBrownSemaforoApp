package com.example.bjonathansemaforoapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bjonathansemaforoapp.ui.theme.BJonathanSemaforoAppTheme
import java.io.IOException
import java.util.UUID
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    private val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var btSocket : BluetoothSocket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BJonathanSemaforoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SemaforoApp(
                        onConect = { connectBT("ASEDEGE") },
                        onSend = {data -> sendData(data)}
                    )
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun connectBT(deviceName: String): Boolean {
        val btManager = getSystemService(BluetoothManager::class.java)
        val btAdapter: BluetoothAdapter? = btManager?.adapter
        if (btAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_SHORT).show()
            return false
        }
        val device = btAdapter.bondedDevices.firstOrNull { it.name == deviceName }
        if (device == null) {
            Toast.makeText(this, "Dispositivo '$deviceName' no encontrado. Vincúlalo primero.", Toast.LENGTH_LONG).show()
            return false
        }
        return try {
            btSocket = device.createRfcommSocketToServiceRecord(myUUID)
            btSocket?.connect()
            Toast.makeText(this, "Conectado a $deviceName", Toast.LENGTH_SHORT).show()
            true
        } catch (e: IOException) {
            Toast.makeText(this, "Error al conectar: ${e.message}", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun sendData(value: String){
        btSocket?.outputStream?.write(value.toByteArray())
    }

    override fun onDestroy() {
        super.onDestroy()
        btSocket?.close()
        btSocket = null
    }
}

@Composable
fun SemaforoApp (
    onConect : () -> Boolean,
    onSend : (String) -> Unit,

){
    var led1 by remember { mutableStateOf(false) }
    var led2 by remember { mutableStateOf(false) }
    var led3 by remember { mutableStateOf(false) }

    var isConected by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if(isConected) "Conectado" else "Desconectado",
            color = if(isConected) Color.Green else Color.Red
        )
        if (!isConected){
            Button(
                onClick = {
                    isConected = onConect()
                }
            ) {
                Text(
                    text = "Conectar al ASEDEGE"
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        onSend("1")
                        led1 = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {
                    Text(
                        text = "Led 1 ON"

                    )
                }
                Button(
                    onClick = {
                        onSend("2")
                        led1 = false
                    },
                    modifier = Modifier.padding(start = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Led 1 OFF"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .background(
                            if (!led1) Color.LightGray else Color.Green,
                            RoundedCornerShape(30.dp))
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        onSend("3")
                        led2 = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9D9D2E)
                    )
                ) {
                    Text(
                        text = "Led 2 ON"
                    )
                }
                Button(
                    onClick = {
                        onSend("4")
                        led2 = false
                    },
                    modifier = Modifier.padding(start = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Led 2 OFF"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .background(
                            if (!led2) Color.LightGray else Color.Yellow,
                            RoundedCornerShape(30.dp))
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        onSend("5")
                        led3 = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6E1F1F)
                    )
                ) {
                    Text(
                        text = "Led 3 ON"
                    )
                }
                Button(
                    onClick = {
                        onSend("6")
                        led3 = false
                    },
                    modifier = Modifier.padding(start = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Led 3 OFF"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .background(
                            if (!led3) Color.LightGray else Color.Red,
                            RoundedCornerShape(30.dp))
                )
            }
            Button(
                modifier = Modifier
                    .padding(top = 10.dp),
                onClick = {
                    onSend("0")
                    led1 = false
                    led2 = false
                    led3 = false
                }
            ) {
                Text(
                    text = "Apagar todos"
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Desconectar"
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    BJonathanSemaforoAppTheme {
        // SemaforoApp(
        //    onConect = {true},
        //    onSend = {}
        //)
    }
}