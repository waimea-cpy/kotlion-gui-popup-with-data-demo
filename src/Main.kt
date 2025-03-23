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
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var wordLabel: JLabel
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
        contentPane.preferredSize = Dimension(400, 175)
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

        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)

        wordLabel = JLabel("WORD HERE")
        wordLabel.horizontalAlignment = SwingConstants.CENTER
        wordLabel.bounds = Rectangle(25, 25, 350, 50)
        wordLabel.font = baseFont
        add(wordLabel)

        openButton = JButton("Open The Pop-Up")
        openButton.bounds = Rectangle(25, 100, 350, 50)
        openButton.font = baseFont
        openButton.addActionListener(this)     // Handle any clicks
        add(openButton)
    }


    /**
     * Update the UI controls based on the current state of the application model
     */
    fun updateView() {
        wordLabel.text = "The word is: " + app.currentWord
    }

    /**
     * Handle any UI events (e.g. button clicks)
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            openButton -> {
                examplePopUp.updateView()
                examplePopUp.isVisible = true   // And show it
            }
        }
    }
}


/**
 * Displays a modal dialog
 * The app data model is passed as an argument so
 * that the model can be accessed, as is the parent
 * window object, so that this can be accessed too
 */
class PopUpDialog(val app: App, val mainWindow: MainWindow): JDialog(), ActionListener {
    private lateinit var wordLabel: JLabel
    private lateinit var wordText: JTextField

    /**
     * Configure the UI
     */
    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window
    }

    /**
     * Setup the dialog window
     */
    private fun configureWindow() {
        title = "Example Pop-Up"
        contentPane.preferredSize = Dimension(400, 500)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    /**
     * Populate the window with controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)
        val smallFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        // Adding <html> to the label text allows it to wrap
        val message = JLabel("<html>This is an example pop-up dialog window. Like any window it can have controls, respond to events, etc. <br><br>It is a <em>modal</em> window, so it grabs the focus, and the main window can't be interacted with until this pop-up is closed. <br><br>It also has access to the app's <em>data model</em>, allowing it to access and update data values in the app. Try typing a new word and pressing Enter to see the app data being updated across the whole app...")
        message.bounds = Rectangle(25, 25, 350, 300)
        message.verticalAlignment = SwingConstants.TOP
        message.font = smallFont
        add(message)

        wordLabel = JLabel("WORD HERE")
        wordLabel.bounds = Rectangle(25, 350, 350, 50)
        wordLabel.horizontalAlignment = SwingConstants.CENTER
        wordLabel.font = baseFont
        add(wordLabel)

        wordText = JTextField()
        wordText.bounds = Rectangle(25, 425, 350, 50)
        wordText.horizontalAlignment = SwingConstants.CENTER
        wordText.font = baseFont
        wordText.addActionListener(this)
        add(wordText)
    }

    /**
     * Update the view with data from the data model
     */
    fun updateView() {
        // Accessing data from the app 'model' - the data fields in the App class
        wordLabel.text = "The word is: ${app.currentWord}"
    }

    /**
     * Handle UI actions such as button clicks
     */
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