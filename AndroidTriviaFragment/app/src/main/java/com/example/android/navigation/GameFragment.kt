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
        SINGLE_CHOICE(1), MULTI_CHOICE(2)
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
        /*Question(text = "What is Android Jetpack?",
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
                answers = listOf("In a file (often called navigation.xml) in the res > navigation folder.", "In a file (often called navigation.xml) in the res > layout folder.", "In a file (often called navigation.xml) in the app > navigation folder.",  "In the android-manifest.xml file in the <navigation> element.")),*/
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

"fragmentA is connected to fragmentB by action_FragmentA_to_FragmentB.\n" +
"fragmentB is connected to fragmentC by action_FragmentB_to_FragmentC.",
            answers = listOf("For action_FragmentA_to_FragmentB, set popUpTo to none and popUpToInclusive to no value. (You can omit both attributes.) For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to false.", "For action_FragmentA_to_FragmentB, set popUpTo to none and popUpToInclusive to no value. (You can omit both attributes.) For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true.", "For action_FragmentA_to_FragmentB, set popUpTo to fragmentA and popUpToInclusive to true. For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true.", "For action_FragmentA_to_FragmentB, set popUpTo to fragmentB and popUpToInclusive to no value. For action_FragmentB_to_FragmentC, set popUpTo to fragmentA and popUpToInclusive to true."),
            hasPictures =  true, pictureResource = R.drawable.question8, continuedText = "The user navigates from fragmentA to fragmentB to fragmentC, then taps the system Back button. In this situation, let's say you want the app to navigate back to fragmentA (instead of fragmentB). What's the correct way to set the popUpTo and popUpToInclusive attributes?"),
        /*Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
        Question(text = "What do you use to mark a layout for data binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))*/
    )

    private lateinit var binding: FragmentGameBinding


    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    private var isMulti: Boolean = false

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

            if (!isMulti && currentQuestion.numberOfCorrectAnswersForMulti == null) {
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
                            findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        }
                    } else {
                        // Game over! A wrong answer sends us to the gameOverFragment.
                        findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)

                    }
                }
            } else {
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
                            findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                        }
                    } else {
                        findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)

                    }



            }
        }


        return binding.root
    }

    private fun setSingleOrMultiTypeQuestions() {
        if (currentQuestion.questionType == QuestionType.MULTI_CHOICE) {
            binding.questionRadioGroup.visibility = View.GONE
            binding.checkBoxLinearLayout.visibility = View.VISIBLE
            isMulti = true
        } else {
            binding.questionRadioGroup.visibility = View.VISIBLE
            binding.checkBoxLinearLayout.visibility = View.GONE
            isMulti = false

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
