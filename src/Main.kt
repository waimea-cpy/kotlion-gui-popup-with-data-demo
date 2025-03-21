/**
 * ===============================================================
 * Kotlin GUI Pop-Up Dialog Demo with Data Model
 * ===============================================================
 *
 * 
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    var currentWord = "Cheese"

    // Functions to update the above data
    fun updateWord(newWord: String) {
        currentWord = newWord
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener, ComponentListener {

    // Fields to hold the UI elements
    private lateinit var numberLabel: JLabel
    private lateinit var openButton: JButton

    // Dialogs
    private lateinit var examplePopUp: PopUpDialog


    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the view with the model data
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Pop-Up Demo"
        contentPane.preferredSize = Dimension(600, 350)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        // Create the pop-up, passing on the app object and a link
        // back to this main window
        examplePopUp = PopUpDialog(app, this)
        examplePopUp.addComponentListener(this)

        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        numberLabel = JLabel("WORD HERE")
        numberLabel.horizontalAlignment = SwingConstants.CENTER
        numberLabel.bounds = Rectangle(50, 50, 500, 100)
        numberLabel.font = baseFont
        add(numberLabel)

        openButton = JButton("Open The Pop-Up")
        openButton.bounds = Rectangle(50,200,500,100)
        openButton.font = baseFont
        openButton.addActionListener(this)     // Handle any clicks
        add(openButton)
    }


    /**
     * Update the UI controls based on the current state of the application model
     */
    fun updateView() {
        numberLabel.text = "The word is: " + app.currentWord
    }

    /**
     * Handle any UI events (e.g. button clicks)
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            openButton -> {
                examplePopUp.updateView()       // Make sure the pop-up is up-to-date with the model
                examplePopUp.isVisible = true   // And show it
            }
        }
    }

    override fun componentResized(e: ComponentEvent?) {
        // Should never be called if not resizable!
    }

    override fun componentMoved(e: ComponentEvent?) {
        // We probably don't care about this!
    }

    override fun componentShown(e: ComponentEvent?) {
        // Make sure the dialog view is up to date
        examplePopUp.updateView()
    }

    override fun componentHidden(e: ComponentEvent?) {
        // Make sure out view is up to date with any changes
        updateView()
    }

}


/**
 * Displays a modal dialog
 * The app data model is passed as an argument so
 * that the model can be accessed
 */
class PopUpDialog(val app: App, val mainWindow: MainWindow): JDialog(), ActionListener {
    private lateinit var messageLabel: JLabel
    private lateinit var wordText: JTextField

    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window
    }

    private fun configureWindow() {
        title = "Example Pop-Up"
        contentPane.preferredSize = Dimension(500, 400)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        val bigFont = Font(Font.SANS_SERIF, Font.PLAIN, 40)

        messageLabel = JLabel("MESSAGE")
        messageLabel.bounds = Rectangle(20,20,460,260)
        messageLabel.verticalAlignment = SwingConstants.TOP
        messageLabel.font = baseFont
        add(messageLabel)

        wordText = JTextField()
        wordText.bounds = Rectangle(20,300,460,80)
        wordText.horizontalAlignment = SwingConstants.CENTER
        wordText.font = bigFont
        wordText.addActionListener(this)
        add(wordText)
    }

    fun updateView() {
        // Adding <html> to the label text allows it to wrap
        var message = "<html>This is an example pop-up dialog window."
        message += "<br><br>Just like the main window, it can have controls, respond to events, etc."

        message += "<br><br>It can also access the app data model. For example, the current word is: "
        // Accessing data from the app 'model' - the data fields in the App class
        message += app.currentWord

        message += "<br><br>Type in a new word below and press Enter to update the app model..."

        messageLabel.text = message
    }

    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            wordText -> {
                val newWord = wordText.text     // Update the app data model
                app.updateWord(newWord)
                wordText.text = ""

                updateView()                    // Update this dialog view
                mainWindow.updateView()         // And the main window
            }
        }
    }

}