package dev.claucookielabs.pasbuk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @startuml
 * abstract class AbstractList
 * interface List
 * Student "0..*" - "1..*" Course
 * Pepito *-- Head
 * Collection --|> List
 * AbstractList --> Item
 * @enduml
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
