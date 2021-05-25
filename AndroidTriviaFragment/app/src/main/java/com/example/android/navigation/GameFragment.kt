/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    enum class QuestionType(val type: Int) {
        SINGLE_CHOICE(1), MULTI_CHOICE(2), TRUE_OR_FALSE(3)
    }

    data class Question(
        val text: String,
        val answers: List<String>,
        val questionType: QuestionType = QuestionType.SINGLE_CHOICE,
        val numberOfCorrectAnswersForMulti: Int? = null,
        val hasPictures: Boolean = false,
        val pictureResource: Int? = null,
        val continuedText: String? = null
    )

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android Jetpack?",
                answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
        Question(text = "What is the base class for layouts?",
                answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
        Question(text = "What layout do you use for complex screens?",
                answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
        Question(text = "What do you use to push structured data into a layout?",
                answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")),
        Question(text = "What method do you use to inflate layouts in fragments?",
                answers = listOf("onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
        Question(text = "What's the build system for Android?",
                answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
        Question(text = "Which class do you use to create a vector drawable?",
                answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
        Question(text = "Which one of these is an Android navigation component?",
                answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
        Question(text = "Which XML element lets you register an activity with the launcher activity?",
                answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
        Question(text = "What do you use to mark a layout for data binding?",
                answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "How do you enable your project to use navigation components?",
                answers = listOf("Add dependencies for navigation-fragment-ktx and navigation-ui-ktx in the build.gradle (module) file.", "Make sure every Activity class extends the class NavigationActivity.", "Use the NavigationController class as the launch Activity.", "Add <uses-navigation> to the Android manifest file." )),
        Question(text = "Where are the possible routes through your app defined?",
                answers = listOf("In a file (often called navigation.xml) in the res > navigation folder.", "In a file (often called navigation.xml) in the res > layout folder.", "In a file (often called navigation.xml) in the app > navigation folder.",  "In the android-manifest.xml file in the <navigation> element.")),
        Question(
            text = "Your app contains a physics simulation that requires heavy computation to display. Then the user gets a phone call. Which of the following is true?",
            answers = listOf(
                "During the phone call, you should stop computing the positions of objects in the physics simulation.",
                "During the phone call, you should continue computing the positions of objects in the physics simulation.",
                "N/A",
                "N/A"
            )
        ),
        Question(
            text = "Which lifecycle method should you override to pause the simulation when the app is not on the screen?",
            answers = listOf(
                "onStop()",
                "onDestroy()",
                "onPause()",
                "onSaveInstanceState()"
            )
        ),

        Question(
            text = "To make a class lifecycle-aware through the Android lifecycle library, which interface should the class implement?",
            answers = listOf(
                "LifecycleOwner",
                "Lifecycle",
                "Lifecycle.Event",
                "LifecycleObserver"
            )
        ),

        Question(
            text = "Under which circumstances does the onCreate() method in your activity receive a Bundle with data in it (that is, the Bundle is not null)?",
            answers = listOf(
                "The activity is restarted after the device is rotated.",
                "The activity is started from scratch.",
                "The activity is resumed after returning from the background.",
                "The device is rebooted."
            )
        ),

        Question(
            text = "How do you enable your project to use navigation components?",
            answers = listOf(
                "Add dependencies for navigation-fragment-ktx and navigation-ui-ktx in the build.gradle (module) file.",
                "Make sure every Activity class extends the class NavigationActivity.",
                "Use the NavigationController class as the launch Activity.",
                "Add <uses-navigation> to the Android manifest file."
            ),
        ),
        Question(
            text = "Where are the possible routes through your app defined?",
            answers = listOf(
                "In a file (often called navigation.xml) in the res > navigation folder.",
                "In a file (often called navigation.xml) in the res > layout folder.",
                "In a file (often called navigation.xml) in the app > navigation folder.",
                "In the android-manifest.xml file in the <navigation> element."
            ),
        ),

        Question(
            text = "Which of the following statements about the NavHostFragment are true? Select all that apply.",
            answers = listOf(
                "You add the NavHostFragment to the main layout by adding a <fragment> whose name is androidx.navigation.fragment.NavHostFragment.",
                "You can click the NavHostFragment in the Project view to open the navigation graph.",
                "As the user moves between destinations defined in the navigation graph, the NavHostFragment swaps the fragments in and out as necessary.",
                "You must create a single NavHostFragment subclass and implement the onNavigate() method to handle different kinds of navigation (such as button clicks)."
            ),
            QuestionType.MULTI_CHOICE, 3
        ),
        Question(
            text = "Which of the following statements about the navigation graph are true? Select all that apply.",
            answers = listOf(
                "You create a potential path through the app from one Fragment to another by connecting the fragments in the navigation graph.",
                "The type of a connection between fragments is Action.",
                "To open the navigation graph, you double-click the navigation file (navigation.xml) in the Android Studio Project pane, then click the Design tab.",
                "You must add the type=\"navigation\" attribute to every <fragment> that is included in the navigation graph."
            ),
            QuestionType.MULTI_CHOICE, 3
        ),
        Question(
            text = "Where do you set the ID of a Fragment to be used in navigation?",
            answers = listOf(
                "You need to set the ID in both the navigation file for the app and the layout file for the Fragment.",
                "In the project's navigation file, either by setting the ID attribute in the navigation graph or in the navigation XML file in the res > navigation folder.",
                "In the Fragment's layout file, either by setting the ID attribute in the design editor or in the layout XML file in the res > layout folder.",
                "Set the ID variable in the relevant Fragment class."
            )
        ),
        Question(text = "The News app has a NewsFragment that displays a Show headlines button. The goal is that when the user clicks this button, the app navigates to the HeadlinesFragment.\n" +
                "\n" +
                "Assume that you've added a connection from the NewsFragment to the HeadlinesFragment in the navigation graph, as shown here:",
            answers = listOf("In the onclickListener for the Show headlines button, call navigate() on the navigation controller, passing the action that connects the NewsFragment to the HeadlinesFragment.", "Actually, you don't need to do anything else. It's enough to set the navigation paths in the navigation graph.", "In the onclickListener for the Show headlines button, call navigate() on the navigation controller, passing the class name of the destination Fragment (in this case HeadlinesFragment) .", "In the onclickListener for the Show headlines button, call navigateTo() on the container Fragment, passing the class name of the destination Fragment (in this case HeadlinesFragment) ."),
            hasPictures = true, pictureResource = R.drawable.question6, continuedText = "What else do you need to do so that when the user taps the Show headlines button, the app navigates to the HeadlinesFragment?"
        ),
        Question(text = "When users navigate through an app, sometimes they want to retrace their steps back through the screens they have already visited.\n" +

"Assume the following:\n" +

"fragmentA is connected to fragmentB by action_FragmentA_to_FragmentB.\n" +
"fragmentB is connected to fragmentC by action_FragmentB_to_FragmentC",
            answers = listOf("The destination of the action_FragmentA_to_FragmentB action specifies that when the user is at FragmentA, the next destination in the app is FragmentB.", "The popUpTo attribute of the action can modify where the app navigates to if the user taps the system Back button.", "The destination of the action_FragmentA_to_FragmentB action sets the next destination that the user goes to, whether the user taps a button in the app or taps the Back button at the bottom of the screen.", "The popUpTo attribute of the action can modify where the user goes next as they navigate forward through the app."),
            questionType = QuestionType.MULTI_CHOICE, numberOfCorrectAnswersForMulti = 2, hasPictures = true, pictureResource = R.drawable.question7, "Which of the following statements are true regarding navigating forward and backward through the app? (Choose all that apply.)"),
        Question(text = "When users navigate through an app, sometimes they want to retrace their steps back through the screens they have already visited. However, you can use the popUpTo and popUpToInclusive attributes of an action to modify the path backward through the app.\n" +

"Assume the following:\n" +
                "\n" +
"fragmentA is connected to fragmentB by action_FragmentA_to_FragmentB.\n" +
                "\n" +
"fragmentB is connected to fragmentC by action_FragmentB_to_FragmentC.",
            answers = listOf("For action_FragmentA_to_FragmentB, set popUpTo to none and popUpToInclusive to no value. (You can omit both attributes.) For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to false.", "For action_FragmentA_to_FragmentB, set popUpTo to none and popUpToInclusive to no value. (You can omit both attributes.) For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true.", "For action_FragmentA_to_FragmentB, set popUpTo to fragmentA and popUpToInclusive to true. For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true.", "For action_FragmentA_to_FragmentB, set popUpTo to fragmentB and popUpToInclusive to no value. For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true."),
            hasPictures =  true, pictureResource = R.drawable.question8, continuedText = "The user navigates from fragmentA to fragmentB to fragmentC, then taps the system Back button. In this situation, let's say you want the app to navigate back to fragmentA (instead of fragmentB). What's the correct way to set the popUpTo and popUpToInclusive attributes?"),
        Question(
            text = "Assume that the action action_headlinesFragment_to_newsArticle in the destination graph has a popUpTo value of newsFragment:",
            answers = listOf(
                "If popUpToInclusive is true: Android navigates out of the app because there is nothing left in the back stack for this app.",
                "If popUpToInclusive is false: The app goes back to the news home screen.",
                "If popUpToInclusive is true: The app goes back to the news home screen.",
                "If popUpToInclusive is false: The app goes back to the headlines screen."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 2,
            hasPictures = true,
            pictureResource = R.drawable.question9,
            continuedText = "Assume that the user opens the app and navigates through the screens in the following sequence (without using the Back button):\n" +
                    "\n" +
                    "Open app into News home > Headlines > News details\n" +
                    "\n" +
                    "When the user is viewing the News detail screen, what happens If they tap the system Back button at the bottom of the screen?\n" +
                    "\n" +
                    "Select all that apply (remembering that popUpTo is newsFragment)."
        ),
        Question(
            text = "Where do you define the items for a menu?",
            answers = listOf(
                "In a menu_name.xml file in the res > menu folder, add an <item> tag for each menu item. Create separate XML files for each separate menu.",
                "It depends on where the menu will be shown. For a navigation drawer menu, add an <item> tag for each menu item in the menu.xml file in res > drawer folder. For the options menu, add an <item> tag for each menu item in the menu.xml file in res > options folder.",
                "In the layout file for the Fragment or Activity that displays the menu, add a <menu> tag that contains <item> tags for each item.",
                "In the android_manifest.xml file, add a <menus> tag that contains a <menu> tag for each menu. For each <menu> tag, add an <item> tag for each menu item."
            )
        ),
        Question(
            text = "To enable the options menu in your app, you need to define the menu items. Then what do you need to do in the Activity or Fragment where the options menu is to appear?\n" +
                    "\n" +
                    "Select all that apply:",
            answers = listOf(
                "Call setHasOptionsMenu(true) in onCreate() for an Activity, or in onCreateView() for a Fragment.",
                "Implement onCreateOptionsMenu() in the Activity or Fragment to create the menu.",
                "Implement onOptionsItemSelected() in the Activity or Fragment to determine what happens when a user selects a menu item in the options menu.",
                "Set the onClick attribute in the menu's XML file to onShowOptionsMenu, unless you are implementing a custom onClick listener for the options menu, in which case specify the name of the custom onClick listener instead."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 3
        ),

        Question(
            text = "What do you need to do to enable a navigation drawer in your app? You can assume that your project is using the navigation graph and that you've already defined the menu items.\n" +
                    "\n" +
                    "Select all that apply:",
            answers = listOf(
                "Use <DrawerLayout> as the root view in the relevant layout file, and add a <NavigationView> tag to that layout.",
                "In the <NavigationView> in the layout, set the android:menu attribute to the navigation drawer menu.",
                "In the navigation XML file, make sure that the navigation menu has an ID.",
                "Use <Navigation> as the root view in the relevant layout file, and add a <NavigationView> tag to that layout."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 2
        ),
        Question(
            text = "Following on from the previous question, you need to write some code to enable the navigation drawer to be displayed when the user swipes in from the left side of the screen?\n" +
                    "\n" +
                    "In onCreate() within the Activity that creates the navigation controller, what is the right code to add?",
            answers = listOf(
                "NavigationUI.setupWithNavController(navigationView, navigationController)",
                "NavigationUI.setupWithNavController(navigationLayoutID navigationMenuID)",
                "NavigationDrawer.setupWithNavInterface(navigationView, navigationController)",
                "NavigationDrawer.setupWithNavController(navigationView, navigationMenuID)"
            )
        ),
        Question(
            text = "How do you add support for the Up button at the top of the screen to enable users to get back to the app's home screen from anywhere in the app? What do you need to do in the relevant Activity?\n" +
                    "\n" +
                    "Select all that apply:",
            answers = listOf(
                "Link the navigation controller to the app bar using NavigationUI.setupActionBarWithNavController(context,navigationController)",
                "Override onSupportNavigateUp() method to call navigateUp() on the navigation controller.",
                "In the res > menu folder, create the up_menu.xml file.",
                "In the navigation graph, make sure the starting Fragment has an ID of HomeFragment."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 2
        ),
        Question(
            text = "If you pass arguments from Fragment A to Fragment B without using Safe Args to make sure your arguments are type-safe, which of the following problems can occur that might cause the app to crash when the app runs? Select all that apply.",
            answers = listOf(
                "Fragment B requests data that Fragment A doesn't send to it.",
                "Fragment A might send data that's a different type than Fragment B needs. For example, Fragment A might send a string but Fragment B requests an integer, resulting in a return value of zero.",
                "Fragment A might send data that Fragment B hasn't requested.",
                "Fragment A uses different parameter names than Fragment B requests."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 3
        ),
        Question(
            text = "What does the Safe Args Gradle plugin do? Select all that apply:",
            answers = listOf(
                "Generates simple object and builder classes for type-safe access to arguments specified for destinations and actions.",
                "Makes it so you don't need to use Android bundles in your code.",
                "Generates a method for each action that you've defined in the navigation graph.",
                "Creates Navigation classes that you can edit to simplify the passing of parameters between fragments."
            ),
            QuestionType.MULTI_CHOICE,
            numberOfCorrectAnswersForMulti = 3
        ),
        Question(
            text = "What's an implicit intent?",
            answers = listOf(
                "A task that your app initiates without knowing which app or Activity will handle the task.",
                "A task that your app initiates in one Fragment in your app, and that's completed in another Fragment.",
                "A task that your app always completes by showing the user a chooser.",
                "An implicit intent is the same thing as the action that you set between destinations in the navigation graph."
            )
        ),
        Question(
            text = "What is the activity lifecycle? When does it begin and end?",
            answers = listOf(
                "The activity lifecycle is a set of states, or 'states of being', through which an activity migrates. The activity lifecycle begins when the activity is first created and ends when the activity is destroyed.",
                "The activity lifecycle is what is referred to when talking about the properties of an activity. It begins when the app saves its first user state, and ends when the user removes stored information",
                "The activity lifecycle is the default state of an app's out-of-the-box state. It starts when the app is downloaded and ends when the app is deleted",
                "The activity lifecycle refers to the navigation or \"cycle\" an activity goes through while it's navHost switches between fragments. It begins when when the navHost is initialized in the corresponding layout and never ends."
            )
        ),
        Question(
            text = "UI Controllers are responsible for holding and processing all the data needed for the UI. It should never access your view hierarchy (like view binding object) or hold a reference to the activity or the fragment. True or False?",
            answers = listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),

        Question(
            text = "ViewModel is responsible for drawing views and data to the screen and responding\n" +
                    " to the user events. True or False?",
            answers = listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "ViewModel is responsible for holding and processing all the data needed for the UI. It should never\n" +
                    " access your view hierarchy (like view binding object) or hold a reference to the activity or the fragment.\n" +
                    " True or False?",
            answers = listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "Activities and Fragments are responsible for drawing views and data to the screen and responding\n" +
                    " to the user events. True or False?",
            answers = listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "UI Controllers are responsible for drawing views and data to the screen and responding to the user events.\n" +
                    "True or False?",
            answers = listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "To implement ViewModel in your app, create a Singleton object named ViewModel inside the UI Controller \n" +
                    "\tand store the data used by the UI within it. True or False?",
            answers = listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "To implement ViewModel in your app, extend the ViewModel class, which is from the architecture\n" +
                    " components library, and store app data within that class. True or False?",
            answers = listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "ViewModel objects are automatically destroyed (just like the activity\n" +
                    " or a fragment instance) during configuration changes to quickly make room for predefined data\n" +
                    " for the specific configuration change to be injected via a ViewModel Injector. \n" +
                    " True or False?",
            answers = listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question("ViewModel objects are automatically retained (they are not destroyed like the activity\n" +
                " or a fragment instance) during configuration changes so that data they hold can be accessed\n" +
                " by the backend logic during configuration changes and Android OS-activated UI Controller destruction. \n" +
                " True or False?",
        listOf("False", "True"),
        QuestionType.TRUE_OR_FALSE
        ),
        Question("ViewModel objects are automatically retained (they are not destroyed like the activity\n" +
                " or a fragment instance) during configuration changes so that data they hold is immediately\n" +
                " available to the next activity or fragment instance. True or False?",
            listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question("The ViewModel views the app related data that is destroyed when activity or fragment\n" +
                " is destroyed and recreated by the Android framework. True or False?",
            listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question("The ViewModel stores the app related data that isn't destroyed when activity\n" +
                " or fragment is destroyed and recreated by the Android framework. True or False?",
            listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question("The ViewModel is a model of the app's data that is stored in the DataBase.",
            listOf("False", "True"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question("The ViewModel is a model of the app's data that is displayed in the views.",
            listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
            text = "Where should the decision making logic of your app go?",
            answers = listOf("ViewModel", "Repository", "UI Controller", "Database"),
            QuestionType.SINGLE_CHOICE
        ),
        Question(
            text = "The Android system can destroy UI controllers at any time based on certain user interactions\n" +
                    " or because of system conditions like low memory. Because these events aren't under your control,\n" +
                    " you shouldn't store any app data or state in UI controllers.",
            answers = listOf("True", "False"),
            QuestionType.TRUE_OR_FALSE
        ),
        Question(
        text = "How do UI Controllers control the UI? Select all that apply.",
        answers = listOf(
            "They draw views on the screen",
            "They capture user events",
            "They store data from within the app",
            "They handle decision making logic"
        ),
            QuestionType.MULTI_CHOICE,
            2
            ),
        Question(
            text = "In an app, what are the UI Controllers? Select all that apply.",
            answers = listOf(
                "Activities",
                "Fragments",
                "Layouts",
                "Nav Controllers"
            ),
            QuestionType.MULTI_CHOICE,
            2
        ),
        Question(
            text = "What are Models?",
            answers = listOf(
                "Components that are responsible for handling the data for an app.",
                "Components that are responsible for how the app will look in different state changes.",
                "Components that provide a general overview of what the app will do",
                "Components that have the responsibility of showcasing the app to the user."
            )
        ),
        Question(
            text = "What is the \"separation of concerns\" design principle?",
            answers = listOf(
                "The app should be divided into classes, each with separate responsibilities.",
                "The app should separate classes by Dependency uses in order to separate responsibilities.",
                "The app's data should be primitive objects that are separate from the functions that transform them.",
                "The app should have a full list of components to provide to the Android OS that will assist the OS with separating the ways it handles the internals of the app."
            )
        ),
        Question(
            text = "What are the most common architectural principles? Select all that apply.",
            answers = listOf(
                "separation of concerns",
                "driving the UI with a model",
                "binding data to xml layouts",
                "Version Control Tendencies"
            ),
            QuestionType.MULTI_CHOICE,
            2
        ),
        Question(
            text = "What are the main classes (or components) in Android Architecture?",
            answers = listOf(
                "UI Controller, ViewModel, LiveData, Room",
                "Main Function, Navigation, DataBinding, savedInstanceState()",
                "Data Class, Sealed Class, Enums Class, Open Class, Abstract Class",
                "Nav Drawer, activity_main.xml, Menu Resource Class, Dependency classes"
            )
        ),
        Question(
            text = "What is a Backing Property in Kotlin?",
            answers = listOf(
                "A backing property allows you to return something from a getter other than the exact object.",
                "A backing property allows the UI Controller to access read/write properties of the object.",
                "A backing property provides a backup variable for the object to store previous states.",
                "A backing property is an object that can store all of the states of the objects in the file."
            )
        ),
        Question(
            text = "What should always be true about the data in your ViewModel?",
            answers = listOf(
                "mutable data within the ViewModel should never be exposed or modifiable by an outside class.",
                "Mutable data inside the ViewModel should always be private.",
                "Data should be able to go through changes that are implemented by other classes.",
                "Data should stay mutable so that other classes can change its values."
            )
        )
    )

    private lateinit var binding: FragmentGameBinding


    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 10)
    private var isMulti: Boolean = false
    private var isTOF: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->

            if (isMulti && currentQuestion.numberOfCorrectAnswersForMulti == null) {
                val checkedIds: List<CheckBox> = listOf(
                    binding.firstAnswerCheckBox,
                    binding.secondAnswerCheckBox,
                    binding.thirdAnswerCheckBox,
                    binding.fourthAnswerCheckBox
                )
                // Do nothing if nothing is checked (id == -1)
                val correctAnswers = currentQuestion.numberOfCorrectAnswersForMulti
                var myCorrectAnswers: Int = 0
                for (checked in checkedIds) {
                    for (answer in currentQuestion.answers) {
                        if (checked.isChecked && currentQuestion.answers.indexOf(answer) < correctAnswers!! && checked.text == answer) {
                            myCorrectAnswers++
                        }
                    }

                }
                if (correctAnswers == myCorrectAnswers) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(questionIndex, numQuestions))

                    }
                } else {
                    findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())

                }

            } else if (isTOF) {
                val checkedId = binding.TOFRadioGroup.checkedRadioButtonId

                if (-1 != checkedId) {
                    var answerIndex = 0
                    when (checkedId) {
                        R.id.false_button -> answerIndex = 1
                    }
                    if (answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIndex++
                        // Advance to the next question
                        if (questionIndex < numQuestions) {
                            currentQuestion = questions[questionIndex]
                            setQuestion()
                            binding.invalidateAll()
                        } else {
                            // We've won!  Navigate to the gameWonFragment.
                            findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(questionIndex, numQuestions))
                        }
                    } else {
                        // Game over! A wrong answer sends us to the gameOverFragment.
                        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())

                    }

                }
            } else {
                val checkedId = binding.questionRadioGroup.checkedRadioButtonId
                // Do nothing if nothing is checked (id == -1)
                if (-1 != checkedId) {
                    var answerIndex = 0
                    when (checkedId) {
                        R.id.secondAnswerRadioButton -> answerIndex = 1
                        R.id.thirdAnswerRadioButton -> answerIndex = 2
                        R.id.fourthAnswerRadioButton -> answerIndex = 3
                    }
                    // The first answer in the original question is always the correct one, so if our
                    // answer matches, we have the correct answer.
                    if (answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIndex++
                        // Advance to the next question
                        if (questionIndex < numQuestions) {
                            currentQuestion = questions[questionIndex]
                            setQuestion()
                            binding.invalidateAll()
                        } else {
                            // We've won!  Navigate to the gameWonFragment.
                            findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(questionIndex, numQuestions))
                        }
                    } else {
                        // Game over! A wrong answer sends us to the gameOverFragment.
                        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())

                    }
                }

            }
        }


        return binding.root
    }

    private fun setSingleOrMultiTypeQuestions() {
        when (currentQuestion.questionType) {
            QuestionType.MULTI_CHOICE -> {
                binding.questionRadioGroup.visibility = View.GONE
                binding.checkBoxLinearLayout.visibility = View.VISIBLE
                isMulti = true
                isTOF = false
            }
            QuestionType.TRUE_OR_FALSE -> {
                binding.TOFRadioGroup.visibility = View.VISIBLE
                binding.questionRadioGroup.visibility = View.GONE
                binding.checkBoxLinearLayout.visibility = View.GONE
                isMulti = false
                isTOF = true
            }
            else -> {
                binding.TOFRadioGroup.visibility = View.GONE
                binding.questionRadioGroup.visibility = View.VISIBLE
                binding.checkBoxLinearLayout.visibility = View.GONE
                isMulti = false
                isTOF = false

            }
        }
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {


        binding.checkBoxLinearLayout.children.forEach { child ->
            val thisChild = child as CheckBox
            thisChild.isChecked = false
        }

        currentQuestion = questions[questionIndex]


        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        setSingleOrMultiTypeQuestions()
        setHasPicture()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
    private fun setHasPicture() {
        if (currentQuestion.hasPictures) {
            binding.questionText.textSize = 24F
            binding.questionPicture.visibility = View.VISIBLE
            binding.questionPicture.setImageResource(currentQuestion.pictureResource!!)
            binding.questionTextContinued.visibility = View.VISIBLE
            binding.questionTextContinued.text = currentQuestion.continuedText
        } else {
            binding.questionText.textSize = 30F
            binding.questionPicture.visibility = View.GONE
            binding.questionTextContinued.visibility = View.GONE
        }
    }
}
